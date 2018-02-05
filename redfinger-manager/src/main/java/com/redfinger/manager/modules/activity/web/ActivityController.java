package com.redfinger.manager.modules.activity.web;

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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.bean.view.ViewActivityGoods;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfActivity;
import com.redfinger.manager.common.domain.RfActivityGoods;
import com.redfinger.manager.common.domain.RfCouponType;
import com.redfinger.manager.common.domain.RfGoods;
import com.redfinger.manager.common.domain.RfLabel;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.activity.service.ActivityGoodsService;
import com.redfinger.manager.modules.activity.service.ActivityService;
import com.redfinger.manager.modules.coupon.service.CouponTypeService;
import com.redfinger.manager.modules.goods.service.GoodsService;
import com.redfinger.manager.modules.label.service.LabelService;

@Controller
@RequestMapping(value="/activity/activity")
public class ActivityController extends BaseController {
	@Autowired
	private ActivityService service;
	@Autowired
	private ActivityGoodsService activityGoodsService;
	@Autowired
	private GoodsService goodsService;
	@Autowired 
	private CouponTypeService couponTypeService;
	@Autowired
	private LabelService labelService;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value="list")
	public PageInfo<RfActivity> list(HttpServletRequest request,HttpServletResponse response,Model model,RfActivity bean)throws Exception{
		List<RfActivity> activityList = service.initQuery(bean).andGreaterThanOrEqualTo("activityStartTime",DateUtils.parseDate(bean.getBeginTimeStr()))
				.andLessThanOrEqualTo("activityEndTime",DateUtils.parseDate(bean.getEndTimeStr()+" 23:59:59"))
				.andLike("activityName", bean.getActivityName())
				.andEqualTo("activityCode", bean.getActivityCode())
				.andEqualTo("enableStatus", bean.getEnableStatus())
				.andEqualTo("status", ConstantsDb.globalStatusNomarl())
				.addOrderClause("reorder", "asc")
				.addOrderClause("activityId", "desc")
				.pageAll(bean.getPage(), bean.getRows());
		
		if(null != activityList && activityList.size() > 0){
			for (RfActivity rfActivity : activityList) {
				if(null != rfActivity.getLabelId()){
					RfLabel label = labelService.get(rfActivity.getLabelId());
					rfActivity.getMap().put("labelName", label.getLabelName() + "["+label.getLabelCode()+"]");
				}
			}
		}
		
		PageInfo<RfActivity> pageInfo = new PageInfo<RfActivity>(activityList);
		return pageInfo;
	}
	
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model,  RfActivity bean) throws Exception {
		if(bean.getActivityId() != null){
			bean = service.get(bean.getActivityId());
			model.addAttribute("bean", bean);
			
			if(bean != null){
				if(!ConstantsDb.activityCoupon().equals(bean.getActivityCode()) && 
						!ConstantsDb.activityInviteTimeCoupon().equals(bean.getActivityCode()) &&
						!ConstantsDb.activityInviteSignupBox().equals(bean.getActivityCode()) &&
						!ConstantsDb.activityInviteBuyBox().equals(bean.getActivityCode())){
					//活动产品关联表
					List<RfActivityGoods> actGoodsList = activityGoodsService.initQuery()
							.andEqualTo("activityId", bean.getActivityId())
							.addOrderClause("reorder", "asc").findStopTrue();
					Map<Integer, RfActivityGoods> actGoodsMap = new HashMap<Integer, RfActivityGoods>();
					for (RfActivityGoods actGoods : actGoodsList) {
						actGoodsMap.put(actGoods.getGoodsId(), actGoods);
					}
					model.addAttribute("actGoodsMap", actGoodsMap);
				}else {
					//活动产品关联表
					List<RfActivityGoods> actGoodsList = activityGoodsService.initQuery()
							.andEqualTo("activityId", bean.getActivityId())
							.addOrderClause("reorder", "asc").findStopTrue();
					Map<String, RfActivityGoods> actGoodsMap = new HashMap<String, RfActivityGoods>();
					Map<Integer, Integer> padTimesMap = new HashMap<Integer, Integer>();
					Map<Integer, Integer> padTimesTwoMap = new HashMap<Integer, Integer>();
					for (RfActivityGoods actGoods : actGoodsList) {
						padTimesMap.put(actGoods.getGoodsId(), actGoods.getPadTime() == null ? 0 : actGoods.getPadTime());
						actGoodsMap.put(actGoods.getGoodsId()+"_"+actGoods.getCouponTypeId(), actGoods);
						padTimesTwoMap.put(actGoods.getGoodsId(), actGoods.getPadTimeTwo() == null ? 0 : actGoods.getPadTimeTwo());
						log.info(actGoods.getGoodsId()+"--"+actGoods.getCouponTypeId());
					}
					model.addAttribute("actGoodsMap", actGoodsMap);
					model.addAttribute("padTimesMap", padTimesMap);
					model.addAttribute("padTimesTwoMap", padTimesTwoMap);
					
					
					//2017-9-12 09:29:13修复页面actCouponGoodsMap值为空bug
					Map<Integer,String> actCouponGoodsMap = new HashMap<>();
					for(RfActivityGoods aGoods : actGoodsList){
						int goodId = aGoods.getGoodsId();
						String couponTypeIds = actCouponGoodsMap.get(goodId);
						if(StringUtils.isEmpty(couponTypeIds)){
							actCouponGoodsMap.put(goodId, ""+aGoods.getCouponTypeId());
						}else{
							actCouponGoodsMap.put(goodId, ""+couponTypeIds+"|"+aGoods.getCouponTypeId());
						}
					}
					model.addAttribute("actCouponGoodsMap", actCouponGoodsMap);
					

					
				}
			}
		}
		
		//产品列表
		List<RfGoods> goodsList = goodsService.initQuery()
				.andNotEqualTo("goodsType", ConstantsDb.goodsSvip())
				.andNotEqualTo("goodsType", ConstantsDb.goodsWalleRechare())
				.andEqualTo("enableStatus", ConstantsDb.globalEnableStatusNomarl())
				.andEqualTo("status", ConstantsDb.globalStatusNomarl())
				.andGreaterThan("goodsPrice", 1300)
				.addOrderClause("goodsType", "asc").addOrderClause("goodsPrice", "asc").findStopTrue();
		model.addAttribute("goodsList", goodsList);
		
		
		//钱包充值套餐
		List<RfGoods> wrGoodsList = goodsService.initQuery()
				.andEqualTo("goodsType", ConstantsDb.goodsWalleRechare())
				.andEqualTo("enableStatus", ConstantsDb.globalEnableStatusNomarl())
				.andEqualTo("status", ConstantsDb.globalStatusNomarl())
				.andGreaterThan("goodsPrice", 90)
				.addOrderClause("reorder", "asc").findStopTrue();
		model.addAttribute("wrGoodsList", wrGoodsList);
		
		//产品列表，至用户超级vip活动
		List<RfGoods> svipGoodsList = goodsService.initQuery()
				.andEqualTo("enableStatus", ConstantsDb.globalEnableStatusNomarl())
				.andEqualTo("goodsType", ConstantsDb.goodsSvip())
				.andEqualTo("status", ConstantsDb.globalStatusNomarl())
				.addOrderClause("reorder", "asc").findStopTrue();
		model.addAttribute("svipGoodsList", svipGoodsList);
		
		
		List<RfGoods> inviteGoodsList = goodsService.initQuery()
				.andEqualTo("enableStatus", ConstantsDb.globalEnableStatusNomarl())
				.andIn("goodsType", new Object[]{ConstantsDb.goodsSvip(),ConstantsDb.goodsVip(),ConstantsDb.goodsGvip()})
				.andEqualTo("status", ConstantsDb.globalStatusNomarl())
				.addOrderClause("goodsType", "asc").addOrderClause("goodsPrice", "asc").findStopTrue();
		model.addAttribute("inviteGoodsList", inviteGoodsList);
	
		
		List<RfGoods> inviteSignupBoxGoodsList = goodsService.initQuery()
				.andNotEqualTo("goodsType", ConstantsDb.goodsWalleRechare())
				.andEqualTo("enableStatus", ConstantsDb.globalEnableStatusNomarl())
				.andEqualTo("status", ConstantsDb.globalStatusNomarl())
				.andGreaterThan("onlineTime", 29)
				.addOrderClause("goodsType", "asc").addOrderClause("goodsPrice", "asc").findStopTrue();
		model.addAttribute("goodsList", goodsList);
		model.addAttribute("inviteSignupBoxGoodsList", inviteSignupBoxGoodsList);
		
		//优惠劵类型
		List<RfCouponType> couponTypeList = couponTypeService.initQuery().addOrderClause("createTime", "asc").findStopTrue();
		model.addAttribute("couponTypeList", couponTypeList);
		
		//用户标签
		List<RfLabel> labelList = labelService.initQuery().andEqualTo("labelType", ConstantsDb.labelTypeUser()).andEqualTo("labelCode", ConstantsDb.labelCodeActivity()).findStopTrue();
		model.addAttribute("labelList", labelList);
		
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfActivity bean,@ModelAttribute ViewActivityGoods viewActGoods, MultipartHttpServletRequest fileRequest) throws Exception {
		bean.setActivityStartTime(DateUtils.parseDate(bean.getBeginTimeStr()));
		bean.setActivityEndTime(DateUtils.parseDate(bean.getEndTimeStr()));
		if(checkActivityCount(bean) > 0){
			throw new BusinessException("保存失败,时间范围内存在标签相同的活动");
		}
		if(bean.getActivityEndTime().getTime() - bean.getActivityStartTime().getTime() <= 0){
			throw new BusinessException("保存失败,结束时间不能小于开始时间");
		}
		service.saveActivity(request, bean, viewActGoods, fileRequest);
	}
	
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfActivity bean,@ModelAttribute ViewActivityGoods viewActGoods, MultipartHttpServletRequest fileRequest) throws Exception {
		RfActivity activity = new RfActivity();
		activity = service.get(bean.getActivityId());
		activity.setActivityCode(bean.getActivityCode());
		activity.setActivityDesc(bean.getActivityDesc());
		activity.setActivityName(bean.getActivityName());
		activity.setReorder(bean.getReorder());
		activity.setRemark(bean.getRemark());
		activity.setLabelId(bean.getLabelId());
		activity.setLimitUserFinishCount(bean.getLimitUserFinishCount());
		activity.setLotteryCount(bean.getLotteryCount());
		activity.setSystemLotteryCount(bean.getSystemLotteryCount());
		activity.setCumulativeDay(bean.getCumulativeDay());
		activity.setActivityCouponTypeId(bean.getActivityCouponTypeId());
		activity.setAwardTypeId(bean.getAwardTypeId());
		activity.setActivityNum(bean.getActivityNum());
		activity.setActivityStartTime(DateUtils.parseDate(bean.getBeginTimeStr()));
		activity.setActivityEndTime(DateUtils.parseDate(bean.getEndTimeStr()));
		if(checkActivityCount(activity) > 0){
			throw new BusinessException("修改失败,时间范围内存在标签相同的活动");
		}
		if(activity.getActivityEndTime().getTime() - activity.getActivityStartTime().getTime() <= 0){
			throw new BusinessException("保存失败,结束时间不能小于开始时间");
		}
		if(null == activity.getLabelId()){
			
		}
		service.updateActivity(request, activity, viewActGoods, fileRequest);
	}
	
	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfActivity bean) throws Exception {
		String[] activityIds = bean.getIds().split(",");
		for (String activityId : activityIds) {
			List<RfActivity> activityList = service.initQuery().andEqualTo("activityId", Integer.parseInt(activityId)).findAll();
			for (RfActivity activity : activityList) {
				if(checkActivityCount(activity) > 0){
					throw new BusinessException("启用失败,时间范围内存在标签相同的活动");
				}
			}
		}
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfActivity bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfActivity bean) throws Exception {
		service.delete(request, bean);
	}

	/**
	 * 判断活动在相同的时间内，标签是否相同
	 * @param activity
	 * @return
	 * @throws Exception
	 */
	private int checkActivityCount(RfActivity activity) throws Exception {
		List<RfActivity> activityList = service.initQuery().andEqualTo("activityCode", activity.getActivityCode())
				.andNotEqualTo("activityId", activity.getActivityId())
				.findStopTrue();
		
		int count = 0;
		long startTime = activity.getActivityStartTime().getTime(), endTime = activity.getActivityEndTime().getTime();
		Integer labelId = activity.getLabelId();
		for (RfActivity act : activityList) {
			if(null == labelId && null == act.getLabelId()){
				if((startTime >= act.getActivityStartTime().getTime() && startTime <= act.getActivityEndTime().getTime())
					|| (endTime >= act.getActivityStartTime().getTime() && endTime <= act.getActivityEndTime().getTime())
					||  (startTime <= act.getActivityStartTime().getTime() && endTime >= act.getActivityEndTime().getTime())){
					count++;
				}
			}else if(null != labelId){
				if(labelId.equals(act.getLabelId())){
					if((startTime >= act.getActivityStartTime().getTime() && startTime <= act.getActivityEndTime().getTime())
						|| (endTime >= act.getActivityStartTime().getTime() && endTime <= act.getActivityEndTime().getTime())){
						count++;
					}
				}
			}
		}
		return count;
	}
	
}