package com.redfinger.manager.modules.coupon.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.bean.view.ViewCoupon;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfCoupon;
import com.redfinger.manager.common.domain.RfCouponGoods;
import com.redfinger.manager.common.domain.RfCouponType;
import com.redfinger.manager.common.domain.RfGoods;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.CouponTypeUtils;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.coupon.dto.RfCouponGood;
import com.redfinger.manager.modules.coupon.service.CouponGoodsService;
import com.redfinger.manager.modules.coupon.service.CouponService;
import com.redfinger.manager.modules.coupon.service.CouponTypeService;
import com.redfinger.manager.modules.goods.service.GoodsService;

@Controller
@RequestMapping(value="/coupon/coupon")
public class CouponController extends BaseController {

	@Autowired
	CouponService service;
	@Autowired
	CouponTypeService typeService;
	@Autowired
	GoodsService goodsService;
	@Autowired
	CouponGoodsService couponGoodsService;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		List<RfCouponType> list = typeService.initQuery().findStopTrue();
		model.addAttribute("couponTypes", list);
		model.addAttribute("map", YesOrNoStatus.DICT_MAP);
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfCoupon> list(HttpServletRequest request, HttpServletResponse response, Model model, RfCoupon bean) throws Exception {
		
		List<RfCoupon> list = service.initQuery(bean)
				.andLike("couponCode", bean.getCouponCode())
				.andEqualTo("typeId", bean.getTypeId())
				.andEqualTo("couponStatus", bean.getCouponStatus())
				.andEqualTo("bindStatus", bean.getBindStatus())
				.andEqualTo("enableStatus", bean.getEnableStatus()) // 根据启用/禁用状态查询
				.addOrderClause("couponId", "desc")
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		if(null != list && list.size()>0){
			for (RfCoupon rfCoupon : list) {
				rfCoupon.getMap().put("couponTypeName", typeService.get(rfCoupon.getTypeId()).getTypeName());
				rfCoupon.getMap().put("couponStatusName", YesOrNoStatus.DICT_MAP.get(rfCoupon.getCouponStatus()));
				rfCoupon.getMap().put("bindStatusName", YesOrNoStatus.DICT_MAP.get(rfCoupon.getBindStatus()));
			}
		}
		
		PageInfo<RfCoupon> pageInfo = new PageInfo<RfCoupon>(list);
		return pageInfo;
	}
	
	
	@RequestMapping(value = "addCoupon")
	public String addCoupon(HttpServletRequest request, HttpServletResponse response, Model model, RfCoupon bean){
		List<RfCouponType> list = typeService.initQuery().findStopTrue();
		List<RfGoods> goods = goodsService.initQuery()
				.andNotEqualTo("goodsType", ConstantsDb.goodsSvip())
				.andNotEqualTo("goodsType", ConstantsDb.goodsWalleRechare())
				.andEqualTo("enableStatus", ConstantsDb.globalEnableStatusNomarl())
				.andEqualTo("status", ConstantsDb.globalStatusNomarl())
				.andGreaterThan("goodsPrice", 1300)
				.addOrderClause("goodsType", "asc").addOrderClause("goodsPrice", "asc").findStopTrue();
		model.addAttribute("couponTypes", list);
		model.addAttribute("goods", goods);
		model.addAttribute("maps", CouponTypeUtils.DICT_MAP);
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "saveCoupon")
	public void saveCoupon(HttpServletRequest request, HttpServletResponse response, Model model, RfCoupon bean,
			String begin, String end, int couponNumber, String couponCodePrefix,@ModelAttribute ViewCoupon viewCoupon,
			String goodsId, Double money,String discountGoodsId,Integer discount ) throws Exception{
		log.info("优惠劵前缀："+couponCodePrefix +",优惠劵个数："+couponNumber);
		RfCouponType rfCouponType = null;
		if(null != bean.getTypeId()){
			rfCouponType = typeService.get(bean.getTypeId());
		}else{
			throw new BusinessException("优惠劵类型不能为空！");
		}
		
		if(couponNumber < 1){
			throw new BusinessException("优惠劵个数不能小于1！");
		}
		
		List<RfCoupon> codes = service.initQuery().andLikeSuffix("couponCode", couponCodePrefix).findStopTrue();
		if(null != codes && codes.size()>0){
			throw new BusinessException("已经存在前缀为"+couponCodePrefix+"的优惠劵！");
		}
		List<RfCoupon> list = new ArrayList<RfCoupon>();
		Date beginTime = null;
		Date endTime = null;
		if (StringUtils.isNotBlank(begin)) {
			beginTime = DateUtils.parseDate(begin);
		}
		if (StringUtils.isNotBlank(end)) {
			endTime = DateUtils.parseDate(end);
		}
		for(int i=0;i<couponNumber;i++){
			RfCoupon code = new RfCoupon();
			code.setCouponCode(couponCodePrefix+generateStr());
			code.setCouponName(bean.getCouponName());
			code.setCouponMoney(bean.getCouponMoney());
			code.setCouponStatus(YesOrNoStatus.NO);
			code.setCouponType(bean.getCouponType());
			code.setBindStatus(YesOrNoStatus.NO);
			code.setTypeId(bean.getTypeId());
			code.setCouponCondition(bean.getCouponCondition());
			code.setBatchNo(couponCodePrefix + "0001");
			code.setCouponStartTime(beginTime);
			code.setCouponEndTime(endTime);
			code.setRemark(bean.getRemark());
			code.setStatus(YesOrNoStatus.YES);
			code.setEnableStatus(YesOrNoStatus.YES);
			code.setCreater(SessionUtils.getCurrentUserId(request));
			code.setCreateTime(new Date());
			list.add(code);
		}
		if(null != list && list.size() >0 ){
			List<RfCouponGood> couponList = new ArrayList<RfCouponGood>();
			List<RfCouponGood> couponGoodsList = viewCoupon.getCouponGoodsList();//优惠劵
			List<RfCouponGood> discountGoodsList = viewCoupon.getDiscountGoodsList();//折扣
			if(CouponTypeUtils.COUPON.equals(bean.getCouponType())){//优惠劵
				if(StringUtils.isBlank(goodsId)){
					throw new BusinessException("请选择产品！");
				}
				
				if(null == money){
					throw new BusinessException("请填写优惠金额！");
				}
				
				String goodsIds[] = goodsId.split("\\|");
				for (String id : goodsIds) {
					Integer goodId = Integer.parseInt(id.trim());
					RfGoods goods = goodsService.get(goodId);
					if((money * 100) >= goods.getGoodsPrice()){//如果优惠金额大于产品价格
						throw new BusinessException("优惠价格不能大于"+goods.getGoodsName()+"的产品价格！");
					}
					RfCouponGood couponGood = new RfCouponGood();
					couponGood.setGoodsId(goodId);
					Double couponMoney = money*100;
					couponGood.setCouponMoney(couponMoney.intValue());
					couponList.add(couponGood);
				}
				
			}else if(CouponTypeUtils.DISCOUNT.equals(bean.getCouponType())){//折扣
				if(StringUtils.isBlank(goodsId)){
					throw new BusinessException("请选择产品！");
				}
				
				if(null == discount){
					throw new BusinessException("请填写折扣！");
				}
				
				String goodsIds[] = goodsId.split("\\|");
				for (String id : goodsIds) {
					Integer goodId = Integer.parseInt(id.trim());
					RfGoods goods = goodsService.get(goodId);
					RfCouponGood couponGood = new RfCouponGood();
					couponGood.setGoodsId(goodId);
					couponGood.setCouponMoney(((100-discount)*goods.getGoodsPrice())/100);
					couponList.add(couponGood);
				}
				
			}
			
			for (RfCouponGood rfCouponGoods : couponList) {
				log.info("[goodsId:{},couponAmount:{}]", 
						new Object[]{rfCouponGoods.getGoodsId(),rfCouponGoods.getCouponMoney()});
			}
			if(null != couponList && couponList.size()>0){
				service.saveCodeList(request, list,couponList);
			}
		}
	}
	
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfCoupon bean)
			throws Exception {
		if (bean.getCouponId() != null) {
			bean = service.get(bean.getCouponId());
			model.addAttribute("bean", bean);
		}
		List<RfCouponGoods> list = couponGoodsService.initQuery()
				.andEqualTo("couponId", bean.getCouponId()).findStopTrue();
		
		Map<Integer, RfCouponGoods> couGoodsMap = new HashMap<Integer, RfCouponGoods>();
		if(null != list && list.size() > 0){
			if(CouponTypeUtils.COUPON.equals(bean.getCouponType())){//优惠劵
				Double couponMoney = Double.parseDouble(list.get(0).getCouponMoney().toString());
				Double bai = 100d;
				Double money = couponMoney / bai;
				model.addAttribute("money", money);
				for (RfCouponGoods rfCouponGoods : list) {
					couGoodsMap.put(rfCouponGoods.getGoodsId(), rfCouponGoods);
				}
			}else if(CouponTypeUtils.DISCOUNT.equals(bean.getCouponType())){//折扣
				RfGoods goods = goodsService.get(list.get(0).getGoodsId());
				model.addAttribute("discount", ((goods.getGoodsPrice() - list.get(0).getCouponMoney()) * 100) / goods.getGoodsPrice());
				
				for (RfCouponGoods rfCouponGoods : list) {
					couGoodsMap.put(rfCouponGoods.getGoodsId(), rfCouponGoods);
				}
			}
		}
		
		List<RfGoods> goods = goodsService.initQuery()
				.andNotEqualTo("goodsType", ConstantsDb.goodsSvip())
				.andNotEqualTo("goodsType", ConstantsDb.goodsWalleRechare())
				.andEqualTo("enableStatus", ConstantsDb.globalEnableStatusNomarl())
				.andEqualTo("status", ConstantsDb.globalStatusNomarl())
				.andGreaterThan("goodsPrice", 1300)
				.addOrderClause("goodsType", "asc").addOrderClause("goodsPrice", "asc").findStopTrue();
		model.addAttribute("goods", goods);
		model.addAttribute("couponGoodsMap", couGoodsMap);
		model.addAttribute("typeList", typeService.initQuery().findStopTrue());
		model.addAttribute("map", YesOrNoStatus.DICT_MAP);
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfCoupon bean, 
			String begin, String end, @ModelAttribute ViewCoupon viewCoupon,
			String goodsId, Double money,String discountGoodsId,Integer discount)
			throws Exception {
		Date beginTime = null;
		Date endTime = null;
		if (StringUtils.isNotBlank(begin)) {
			beginTime = DateUtils.parseDate(begin);
			bean.setCouponStartTime(beginTime);
		}
		if (StringUtils.isNotBlank(end)) {
			endTime = DateUtils.parseDate(end);
			bean.setCouponEndTime(endTime);
		}
		
		if(null != bean.getCouponId()){
			RfCoupon coupon = service.get(bean.getCouponId());
			
			if(YesOrNoStatus.YES.equals(coupon.getCouponStatus())){
				throw new BusinessException(coupon.getCouponCode()+"已经被使用！");
			}
			
			if(YesOrNoStatus.YES.equals(coupon.getBindStatus())){
				throw new BusinessException(coupon.getCouponCode()+"已经被绑定！");
			}
		}
		
		List<RfCouponGood> couponList = new ArrayList<RfCouponGood>();
		List<RfCouponGood> couponGoodsList = viewCoupon.getCouponGoodsList();//优惠劵
		List<RfCouponGood> discountGoodsList = viewCoupon.getDiscountGoodsList();//折扣
		if(CouponTypeUtils.COUPON.equals(bean.getCouponType())){//优惠劵
			
			if(StringUtils.isBlank(goodsId)){
				throw new BusinessException("请选择产品！");
			}
			
			if(null == money){
				throw new BusinessException("请填写优惠金额！");
			}
			
			String goodsIds[] = goodsId.split("\\|");
			for (String id : goodsIds) {
				Integer goodId = Integer.parseInt(id.trim());
				RfGoods goods = goodsService.get(goodId);
				if((money * 100) > goods.getGoodsPrice()){//如果优惠金额大于产品价格
					throw new BusinessException("优惠价格不能大于"+goods.getGoodsName()+"的产品价格！");
				}
				RfCouponGood couponGood = new RfCouponGood();
				couponGood.setGoodsId(goodId);
				Double couponMoney = money*100;
				couponGood.setCouponMoney(couponMoney.intValue());
				couponList.add(couponGood);
			}
			
		}else if(CouponTypeUtils.DISCOUNT.equals(bean.getCouponType())){//折扣
			
			if(StringUtils.isBlank(goodsId)){
				throw new BusinessException("请选择产品！");
			}
			
			if(null == discount){
				throw new BusinessException("请填写折扣！");
			}
			
			String goodsIds[] = goodsId.split("\\|");
			for (String id : goodsIds) {
				Integer goodId = Integer.parseInt(id.trim());
				RfGoods goods = goodsService.get(goodId);
				RfCouponGood couponGood = new RfCouponGood();
				couponGood.setGoodsId(goodId);
				couponGood.setCouponMoney(((100-discount)*goods.getGoodsPrice())/100);
				couponList.add(couponGood);
			}
		}
		
		for (RfCouponGood rfCouponGoods : couponList) {
			log.info("[goodsId:{},couponAmount:{}]", 
					new Object[]{rfCouponGoods.getGoodsId(),rfCouponGoods.getCouponMoney()});
		}
		service.updateCoupont(request, bean, couponList);
	}
	
	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfCoupon bean)
			throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfCoupon bean)
			throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfCoupon bean)
			throws Exception {
		service.delete(request, bean);
	}
}
