package com.redfinger.manager.modules.award.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.constants.AwardCategory;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfActivationCodeType;
import com.redfinger.manager.common.domain.RfAwardBatch;
import com.redfinger.manager.common.domain.RfCouponType;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.activation.service.ActivationCodeTypeService;
import com.redfinger.manager.modules.award.service.RfAwardBatchService;
import com.redfinger.manager.modules.coupon.service.CouponTypeService;

@Controller
@RequestMapping(value = "/award/batchManage")
public class RfAwardBatchController extends BaseController {
	@Autowired
	RfAwardBatchService service;
	@Autowired
	CouponTypeService typeService;
	@Autowired
	ActivationCodeTypeService codeTypeService;
	@Autowired
	CouponTypeService couponTypeService;
	@Autowired
	private ActivationCodeTypeService activationCodeTypeService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfAwardBatch> list(HttpServletRequest request, HttpServletResponse response, Model model,
			RfAwardBatch bean, String begin, String end) throws Exception {
		Date beginTime = null;
		Date endTime = null;
		if (StringUtils.isNotBlank(begin)) {
			beginTime = DateUtils.parseDate(begin);
		}
		if (StringUtils.isNotBlank(end)) {
			endTime = DateUtils.addDays(DateUtils.parseDate(end), 1);
		}
		List<RfAwardBatch> list = service.initQuery(bean).andEqualTo("awardGrade", bean.getAwardGrade())
				.andEqualTo("awardType", bean.getAwardType())
				.andEqualTo("awardCategory", bean.getAwardCategory()).andLike("awardName", bean.getAwardName())
				.andGreaterThanOrEqualTo("createTime", beginTime).andLessThan("createTime", endTime)
				.addOrderClause("createTime", "desc").addOrderForce(bean.getSort(), bean.getOrder())
				.pageAll(bean.getPage(), bean.getRows());
		
		for (RfAwardBatch awardBatch : list) {
			if(AwardCategory.coupon.equals(awardBatch.getAwardCategory())){
				//优惠劵类型
				RfCouponType couponType = couponTypeService.get(awardBatch.getCouponTypeId());
				if(null != couponType){
					awardBatch.getMap().put("couponName", couponType.getTypeName());
				}
			}
			
			if(AwardCategory.yearDevice.equals(awardBatch.getAwardCategory()) || AwardCategory.seasonDevice.equals(awardBatch.getAwardCategory())
					|| AwardCategory.monthDevice.equals(awardBatch.getAwardCategory()) || AwardCategory.sixMDevice.equals(awardBatch.getAwardCategory())){
				//激活码类型
				RfActivationCodeType activationCodeType = activationCodeTypeService.get(awardBatch.getActivationTypeId());
				if(null != activationCodeType){
					awardBatch.getMap().put("activationTypeName", activationCodeType.getActivationTypeName());
				}
			}
		}
		
		PageInfo<RfAwardBatch> pageInfo = new PageInfo<RfAwardBatch>(list);

		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfAwardBatch bean)
			throws Exception {
		List<RfCouponType> couponTypeList = typeService.initQuery().
				andEqualTo("status", YesOrNoStatus.YES).andEqualTo("enableStatus", YesOrNoStatus.YES).findStopTrue();//优惠劵类型
		
		List<RfActivationCodeType> codeTypeList = codeTypeService.initQuery().andEqualTo("status", YesOrNoStatus.YES).
				andEqualTo("enableStatus", YesOrNoStatus.YES).findStopTrue();//激活码类型
		
		model.addAttribute("couponTypeList", couponTypeList);
		model.addAttribute("codeTypeList", codeTypeList);
		if (bean.getAwardId() == null) {

		} else {
			
			bean = service.get(bean.getAwardId());
			
			if(StringUtils.equals(bean.getAwardCategory(), ConstantsDb.rfAwardBatchAwardCategoryYeardevice()) || 
					StringUtils.equals(bean.getAwardCategory(), ConstantsDb.rfAwardBatchAwardCategorySeasondevice()) || 
					StringUtils.equals(bean.getAwardCategory(), ConstantsDb.rfAwardBatchAwardCategoryMonthdevice())){//代表是激活码
				model.addAttribute("isActivation", YesOrNoStatus.YES);
			}
			model.addAttribute("bean", bean);

		}
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfAwardBatch bean)
			throws Exception {
		if(null == bean || StringUtils.isBlank(bean.getAwardCategory())){
			throw new BusinessException("请选择奖品类型");
		}
		
		if(bean.getAwardTotal() < bean.getAwardMargin()){
			throw new BusinessException("余量不能大于总量");
		}
		
		if(AwardCategory.rbc.equals(bean.getAwardCategory())){
			Integer rbcAmount = bean.getRbcAmount();
			if(null == rbcAmount || rbcAmount.intValue() <= 0){
				throw new BusinessException("请输入正确的红豆数量");
			}
		}
		
		if(AwardCategory.wallet.equals(bean.getAwardCategory())){
			Integer walletAmount = bean.getWalletAmount();
			if(null == walletAmount || walletAmount.intValue() <= 0){
				throw new BusinessException("请输入正确的钱包金额");
			}
		}
		
		// 每次新增的时候判断中奖率的和是否超出10000
		int sun = bean.getWinningRate();
		List<RfAwardBatch> list = service.initQuery().andEqualTo("awardType", bean.getAwardType()).findStopTrue();//根据抽奖类型判断
		for (RfAwardBatch award : list) {
			sun += award.getWinningRate();
			if(award.getAwardGrade().equals(bean.getAwardGrade())){
				throw new BusinessException("同一个奖池不能存在相同的奖品等级");
			}
		}
		if (sun > 10000) {
			throw new BusinessException("中奖率的和超出10000溢出！");
		}
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfAwardBatch bean)
			throws Exception {
		
		if(null == bean || StringUtils.isBlank(bean.getAwardCategory())){
			throw new BusinessException("请选择奖品类型");
		}
		
		if(bean.getAwardTotal() < bean.getAwardMargin()){
			throw new BusinessException("余量不能大于总量");
		}
		
		if(AwardCategory.rbc.equals(bean.getAwardCategory())){
			Integer rbcAmount = bean.getRbcAmount();
			if(null == rbcAmount || rbcAmount.intValue() <= 0){
				throw new BusinessException("请输入正确的红豆数量");
			}
		}
		
		if(AwardCategory.wallet.equals(bean.getAwardCategory())){
			Integer walletAmount = bean.getWalletAmount();
			if(null == walletAmount || walletAmount.intValue() <= 0){
				throw new BusinessException("请输入正确的钱包金额");
			}
		}
		
		int sun = bean.getWinningRate();
		List<RfAwardBatch> list = service.initQuery().andEqualTo("awardType", bean.getAwardType()).findStopTrue();
		for (RfAwardBatch award : list) {
			if(award.getAwardId() != bean.getAwardId()){
				sun += award.getWinningRate();
				if(award.getAwardGrade().equals(bean.getAwardGrade())){
					throw new BusinessException("同一个奖池不能存在相同的奖品等级");
				}
			}
		}
		if (sun > 10000) {
			throw new BusinessException("中奖率的和超出10000溢出！");
		}
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfAwardBatch bean)
			throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfAwardBatch bean)
			throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfAwardBatch bean)
			throws Exception {
		service.remove(request, bean);
	}

}
