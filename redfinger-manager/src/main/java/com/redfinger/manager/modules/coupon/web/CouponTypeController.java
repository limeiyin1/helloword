package com.redfinger.manager.modules.coupon.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.redfinger.manager.common.domain.RfCoupon;
import com.redfinger.manager.common.domain.RfCouponType;
import com.redfinger.manager.common.domain.RfGoods;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.CouponTypeUtils;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.coupon.dto.RfCouponGood;
import com.redfinger.manager.modules.coupon.service.CouponService;
import com.redfinger.manager.modules.coupon.service.CouponTypeService;
import com.redfinger.manager.modules.goods.service.GoodsService;
@Controller
@RequestMapping(value="/coupon/type")
public class CouponTypeController  extends BaseController {

	@Autowired
	private CouponTypeService service;
	@Autowired
	private CouponService couponService;
	@Autowired
	private GoodsService goodsService;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("couponType", CouponTypeUtils.DICT_MAP);
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfCouponType> list(HttpServletRequest request, HttpServletResponse response, Model model, RfCouponType bean) throws Exception {
		
		List<RfCouponType> list = service.initQuery(bean)
				.andLike("typeName", bean.getTypeName())
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		
		PageInfo<RfCouponType> pageInfo = new PageInfo<RfCouponType>(list);
		return pageInfo;
	}
	
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfCouponType bean)
			throws Exception {
		if (bean.getTypeId() != null) {
			bean = service.get(bean.getTypeId());
			model.addAttribute("bean", bean);
		}
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, 
			RfCouponType bean) throws Exception {
		if(StringUtils.isBlank(bean.getTypeName())){
			throw new BusinessException("优惠劵类型名称不能为空");
		}
		
		if(StringUtils.isBlank(bean.getTypeCode())){
			throw new BusinessException("优惠劵类型编码不能为空");
		}
		service.save(request, bean);
	}
	
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfCouponType bean)
			throws Exception {
		if(StringUtils.isBlank(bean.getTypeName())){
			throw new BusinessException("优惠劵类型名称不能为空");
		}
		if(StringUtils.isNotBlank(bean.getTypeCode())){
			List<RfCouponType> list = service.initQuery().andEqualTo("typeCode", bean.getTypeCode().trim()).findAll();
			if(list.size()>1){
				throw new BusinessException(bean.getTypeCode()+"已经存在！");
			}
		}else{
			throw new BusinessException("优惠劵类型编码不能为空");
		}
		service.update(request, bean);
	}
	
	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfCouponType bean)
			throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfCouponType bean)
			throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfCouponType bean)
			throws Exception {
		service.delete(request, bean);
	}
	
	@RequestMapping(value = "batchEditForm")
	public String batchEditForm(HttpServletRequest request, HttpServletResponse response, Model model, RfCouponType bean)
			throws Exception {
		model.addAttribute("map", YesOrNoStatus.DICT_MAP);
		if (bean.getTypeId() != null) {
			bean = service.get(bean.getTypeId());
			
			model.addAttribute("bean", bean);
		}
		List<RfGoods> goods = goodsService.initQuery().findStopTrue();
		model.addAttribute("goods", goods);
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "getCodeList")
	public PageInfo<RfCoupon> getCodeList(HttpServletRequest request, HttpServletResponse response, Model model,
			RfCoupon bean) throws Exception {
		List<RfCoupon> list = couponService.initQuery()
				.andLike("couponCode", bean.getCouponCode())
				.andEqualTo("typeId", bean.getTypeId())
				.andEqualTo("bindStatus", bean.getBindStatus())
				.andEqualTo("couponStatus", bean.getCouponStatus())
				.addOrderClause("reorder", "asc")
				.addOrderClause("createTime", "desc")
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		if(null != list && list.size()>0){
			for (RfCoupon rfCoupon : list) {
				rfCoupon.getMap().put("couponTypeName", service.get(rfCoupon.getTypeId()).getTypeName());
				rfCoupon.getMap().put("couponStatusName", YesOrNoStatus.DICT_MAP.get(rfCoupon.getCouponStatus()));
				rfCoupon.getMap().put("bindStatusName", YesOrNoStatus.DICT_MAP.get(rfCoupon.getBindStatus()));
			}
		}
		
		PageInfo<RfCoupon> pageInfo = new PageInfo<RfCoupon>(list);
		return pageInfo;
	}
	
	@RequestMapping(value = "batchEditCoupon")
	public void batchEditCoupon(HttpServletRequest request, HttpServletResponse response, Model model, RfCouponType bean,
			String couponCondition,String batchNo,String begin,String end,String couponType,@ModelAttribute ViewCoupon viewCoupon) throws Exception {
		log.info("[typeId:{},couponCondition:{},batchNo:{},begin:{},end{}]",new Object[]{bean.getTypeId(),couponCondition,batchNo,begin,end});
		Date couponStartTime = null;
		Date couponEndTime = null;
		if (StringUtils.isNotBlank(begin)) {
			couponStartTime = DateUtils.parseDate(begin);
		}
		if (StringUtils.isNotBlank(end)) {
			couponEndTime = DateUtils.parseDate(end);
		}
		
		List<RfCouponGood> couponList = new ArrayList<RfCouponGood>();
		List<RfCouponGood> couponGoodsList = viewCoupon.getCouponGoodsList();//优惠劵
		List<RfCouponGood> discountGoodsList = viewCoupon.getDiscountGoodsList();//折扣
		if(CouponTypeUtils.COUPON.equals(couponType)){//优惠劵
			if(null != couponGoodsList && couponGoodsList.size()>0){
				for (RfCouponGood rfCouponGoods : couponGoodsList) {
					if(null != rfCouponGoods.getCouponMoney() && rfCouponGoods.getCouponMoney() > 0){
						if(rfCouponGoods.getCouponMoney() >= rfCouponGoods.getGoodsPrice()){//如果优惠金额大于产品价格
							throw new BusinessException("优惠价格不能大于产品价格！");
						}
						RfCouponGood couponGood = new RfCouponGood();
						couponGood.setGoodsId(rfCouponGoods.getGoodsId());
						couponGood.setCouponMoney(rfCouponGoods.getCouponMoney()*100);
						couponList.add(couponGood);
					}
				}
			}
		}else if(CouponTypeUtils.DISCOUNT.equals(couponType)){//折扣
			if(null != discountGoodsList && discountGoodsList.size()>0){
				for (RfCouponGood rfCouponGoods : discountGoodsList) {
					if(null != rfCouponGoods.getDiscount() && rfCouponGoods.getDiscount() > 0){
						RfCouponGood couponGoods = new RfCouponGood();
						couponGoods.setGoodsId(rfCouponGoods.getGoodsId());
						couponGoods.setCouponMoney(((100-rfCouponGoods.getDiscount())*rfCouponGoods.getGoodsPrice())/100);
						couponList.add(couponGoods);
					}
				}
			}
		}
		
		service.updateCoupon(request, bean.getTypeId(), couponCondition, batchNo, couponStartTime, couponEndTime,couponType,couponList);
	}
}
