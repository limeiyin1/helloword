package com.redfinger.manager.modules.activity.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.base.FilePathUtils;
import com.redfinger.manager.common.bean.view.ViewActivityGoods;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfActivity;
import com.redfinger.manager.common.domain.RfActivityExample;
import com.redfinger.manager.common.domain.RfActivityGoods;
import com.redfinger.manager.common.domain.RfActivityGoodsExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfActivityGoodsMapper;
import com.redfinger.manager.common.mapper.RfActivityMapper;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.FileUtils;
import com.redfinger.manager.modules.activity.dto.RfActivityGoodsDto;

@Transactional
@Service
@PrimaryKeyAnnotation(field="activityId")
public class ActivityService extends BaseService<RfActivity, RfActivityExample, RfActivityMapper> {
	
	@Autowired
	private ActivityGoodsService activityGoodsService;
	@Autowired
	private FilePathUtils filePathUtils;
	@Autowired
	private FileUtils fileUtils;
	@Autowired
	private RfActivityGoodsMapper activityGoodsMapper;
	@Autowired
	private RfActivityMapper mapper;
	//套餐活动上传图片
	private String goodsUploadFileKey = "uploadActivityImg_";
	//充值活动上传图片
	private String walletUploadFileKey = "uploadWalletActivityImg_";

	public void saveActivity(HttpServletRequest request,RfActivity activity, ViewActivityGoods viewActGoods, MultipartHttpServletRequest fileRequest) throws Exception{
		activity.setActivityStartTime(DateUtils.parseDate(activity.getBeginTimeStr()));
		activity.setActivityEndTime(DateUtils.parseDate(activity.getEndTimeStr()));
		this.save(request, activity);
		
		//红豆活动
		if(ConstantsDb.activityRbc().equals(activity.getActivityCode())){
			for (RfActivityGoods actGoods : viewActGoods.getActRbcList()) {
				if(actGoods.getRbcAmount() != null){
					actGoods.setActivityId(activity.getActivityId());
					activityGoodsService.save(request, actGoods);
				}
			}
		}else if(ConstantsDb.activityGoods().equals(activity.getActivityCode())){
			Map<String, MultipartFile> fileMap = fileRequest.getFileMap();
			//套餐活动
			for (RfActivityGoods actGoods : viewActGoods.getActGoodsList()) {
				if(actGoods.getActivityPrice() != null){
					actGoods.setActivityId(activity.getActivityId());
					MultipartFile file = fileMap.get(goodsUploadFileKey+actGoods.getGoodsId());
					if(file != null && !file.isEmpty()){
						Map<String, String> uploadMap = fileUtils.saveImageFile(file, filePathUtils.getImageUrl()+"/activity", filePathUtils.getImageLinkUrl()+"/activity");
						String activityImg = uploadMap.get(FileUtils.FILE_PATH_KEY);
						actGoods.setActivityImg(activityImg);
					}
					activityGoodsService.save(request, actGoods);
				}
			}
		}else if(ConstantsDb.activityWechartLottery().equals(activity.getActivityCode())){
			//微信抽奖活动
			for (RfActivityGoods actGoods : viewActGoods.getActLotteryList()) {
				if(actGoods.getLotteryCount() != null){
					actGoods.setActivityId(activity.getActivityId());
					activityGoodsService.save(request, actGoods);
				}
			}
		}else if(ConstantsDb.activityCoupon().equals(activity.getActivityCode())){
			//优惠劵活动
			for(RfActivityGoodsDto actGoods : viewActGoods.getActCouponList()){
				String typeIds[] = actGoods.getCouponTypeIds().split("\\|");
				for (String typeId : typeIds) {
					if(StringUtils.isNotBlank(typeId)){
						RfActivityGoods rfActivityGoods = new RfActivityGoods();
						rfActivityGoods.setActivityId(activity.getActivityId());
						rfActivityGoods.setGoodsId(actGoods.getGoodsId());
						rfActivityGoods.setCouponTypeId(Integer.parseInt(typeId.trim()));
						activityGoodsService.save(request, rfActivityGoods);
					}
				}
			}
		}else if(ConstantsDb.activitySvip().equals(activity.getActivityCode())){
			//超级vip活动
			for(RfActivityGoodsDto actGoods : viewActGoods.getsVipGoodsList()){
				RfActivityGoods rfActivityGoods = new RfActivityGoods();
				rfActivityGoods.setActivityId(activity.getActivityId());
				rfActivityGoods.setGoodsId(actGoods.getGoodsId());
				activityGoodsService.save(request, rfActivityGoods);
			}
		}else if(ConstantsDb.activityTime().equals(activity.getActivityCode())){
			for (RfActivityGoods actGoods : viewActGoods.getPadTimeList()) {
				if(actGoods.getPadTime() != null){
					actGoods.setActivityId(activity.getActivityId());
					activityGoodsService.save(request, actGoods);
				}
			}			
		} else if(ConstantsDb.activityWalleRecharge().equals(activity.getActivityCode())){
			//钱包充值活动
			Map<String, MultipartFile> fileMap = fileRequest.getFileMap();
			//套餐活动
			for (RfActivityGoods actGoods : viewActGoods.getWalletGoodsList()) {
				if(actGoods.getCouponTypeId() != null || actGoods.getRbcAmount() != null){
					actGoods.setActivityId(activity.getActivityId());
					MultipartFile file = fileMap.get(walletUploadFileKey+actGoods.getGoodsId());
					if(file != null && !file.isEmpty()){
						Map<String, String> uploadMap = fileUtils.saveImageFile(file, filePathUtils.getImageUrl()+"/activity", filePathUtils.getImageLinkUrl()+"/activity");
						String activityImg = uploadMap.get(FileUtils.FILE_PATH_KEY);
						actGoods.setActivityImg(activityImg);
					}
					activityGoodsService.save(request, actGoods);
				}
			}			
		} else if(ConstantsDb.activityInviteTime().equals(activity.getActivityCode())){
			// 邀请购买设备活动
			for (RfActivityGoods actGoods : viewActGoods.getInvitePadTimeList()) {
				if(actGoods.getPadTime() != null){
					actGoods.setActivityId(activity.getActivityId());
					activityGoodsService.save(request, actGoods);
				}
			}	
		}else if(ConstantsDb.activityInviteTimeCoupon().equals(activity.getActivityCode())){
		 // 邀请登录活动
			for(RfActivityGoodsDto actGoods : viewActGoods.getInvitePadTimeCouponList()){
				// 多个优惠劵, 以|号分隔
				String typeIds[] = actGoods.getCouponTypeIds().split("\\|");
				RfActivityGoods rfActivityGoods = new RfActivityGoods();
				// 活动id
				rfActivityGoods.setActivityId(activity.getActivityId());
				// 商品id
				rfActivityGoods.setGoodsId(actGoods.getGoodsId());
				// 是否有优惠劵赠送
				if(typeIds != null && typeIds.length > 0 && StringUtils.isNotBlank(typeIds[0])){
					for (String typeId : typeIds) {
						if(StringUtils.isNotBlank(typeId)){
							// 优惠劵id
							rfActivityGoods.setCouponTypeId(Integer.parseInt(typeId.trim()));
							// 赠送的设备时长
							rfActivityGoods.setPadTime(actGoods.getPadTime());
							activityGoodsService.save(request, rfActivityGoods);
							
						}
					}
				// 无优惠劵, 有设备时长赠送
				}else if(actGoods.getPadTime() != null){
					rfActivityGoods.setPadTime(actGoods.getPadTime());
					activityGoodsService.save(request, rfActivityGoods);
				}
				
			}
		}else if(ConstantsDb.activityInviteBuyLottery().equals(activity.getActivityCode())){
			// 邀请购买设备抽奖活动
			for (RfActivityGoods actGoods : viewActGoods.getInviteLotteryCountList()) {
				if(actGoods.getLotteryCount() != null){
					actGoods.setActivityId(activity.getActivityId());
					activityGoodsService.save(request, actGoods);
				}
			}	
		}else if(ConstantsDb.activityInviteSignupBox().equals(activity.getActivityCode())){
			 // 邀请注册赠送平安礼盒
			for(RfActivityGoodsDto actGoods : viewActGoods.getInviteSignupBoxCouponList()){
				// 多个优惠劵, 以|号分隔
				String typeIds[] = actGoods.getCouponTypeIds().split("\\|");
				RfActivityGoods rfActivityGoods = new RfActivityGoods();
				// 活动id
				rfActivityGoods.setActivityId(activity.getActivityId());
				// 商品id
				rfActivityGoods.setGoodsId(actGoods.getGoodsId());
				// 是否有优惠劵赠送
				if(typeIds != null && typeIds.length > 0 && StringUtils.isNotBlank(typeIds[0])){
					for (String typeId : typeIds) {
						if(StringUtils.isNotBlank(typeId)){
							// 优惠劵id
							rfActivityGoods.setCouponTypeId(Integer.parseInt(typeId.trim()));
							// 赠送的设备时长
							rfActivityGoods.setPadTime(actGoods.getPadTime());
							rfActivityGoods.setPadTimeTwo(actGoods.getPadTimeTwo());
							activityGoodsService.save(request, rfActivityGoods);
							
						}
					}
				// 无优惠劵, 有设备时长赠送
				}else if(actGoods.getPadTime() != null){
					rfActivityGoods.setPadTime(actGoods.getPadTime());
					rfActivityGoods.setPadTimeTwo(actGoods.getPadTimeTwo());
					activityGoodsService.save(request, rfActivityGoods);
				}
				
			}
		}else if(ConstantsDb.activityInviteBuyBox().equals(activity.getActivityCode())){
			 // 邀请注册赠送圣诞礼盒
			for(RfActivityGoodsDto actGoods : viewActGoods.getInviteBuyBoxCouponList()){
				// 多个优惠劵, 以|号分隔
				String typeIds[] = actGoods.getCouponTypeIds().split("\\|");
				RfActivityGoods rfActivityGoods = new RfActivityGoods();
				// 活动id
				rfActivityGoods.setActivityId(activity.getActivityId());
				// 商品id
				rfActivityGoods.setGoodsId(actGoods.getGoodsId());
				// 是否有优惠劵赠送
				if(typeIds != null && typeIds.length > 0 && StringUtils.isNotBlank(typeIds[0])){
					for (String typeId : typeIds) {
						if(StringUtils.isNotBlank(typeId)){
							// 优惠劵id
							rfActivityGoods.setCouponTypeId(Integer.parseInt(typeId.trim()));
							// 赠送的设备时长
							rfActivityGoods.setPadTime(actGoods.getPadTime());
							rfActivityGoods.setPadTimeTwo(actGoods.getPadTimeTwo());
							activityGoodsService.save(request, rfActivityGoods);
							
						}
					}
				// 无优惠劵, 有设备时长赠送
				}else if(actGoods.getPadTime() != null){
					rfActivityGoods.setPadTime(actGoods.getPadTime());
					rfActivityGoods.setPadTimeTwo(actGoods.getPadTimeTwo());
					activityGoodsService.save(request, rfActivityGoods);
				}
				
			}
		}
	}
	
	public void updateActivity(HttpServletRequest request,RfActivity activity, ViewActivityGoods viewActGoods, MultipartHttpServletRequest fileRequest) throws Exception{
		//this.update(request, activity);
		
		mapper.updateByPrimaryKey(activity);
		
		//红豆活动
		if(ConstantsDb.activityRbc().equals(activity.getActivityCode())){
			for (RfActivityGoods actGoods : viewActGoods.getActRbcList()) {
				if(actGoods.getRbcAmount() != null){
					actGoods.setActivityId(activity.getActivityId());
					actGoods.setActivityImg(null);
					actGoods.setActivityPrice(null);
					actGoods.setLotteryCount(null);
					
					if(actGoods.getActGoodsId() == null){
						activityGoodsService.save(request, actGoods);
					}else {
						activityGoodsService.update(request, actGoods);
					}
				}
			}
		}else if(ConstantsDb.activityGoods().equals(activity.getActivityCode())){
			Map<String, MultipartFile> fileMap = fileRequest.getFileMap();
			//套餐活动
			for (RfActivityGoods actGoods : viewActGoods.getActGoodsList()) {
				actGoods.setActivityId(activity.getActivityId());
				MultipartFile file = fileMap.get(goodsUploadFileKey+actGoods.getGoodsId());
				if(file != null && StringUtils.isNotBlank(file.getOriginalFilename())){
					Map<String, String> uploadMap = fileUtils.saveImageFile(file, filePathUtils.getImageUrl()+"/activity", filePathUtils.getImageLinkUrl()+"/activity");
					String activityImg = uploadMap.get(FileUtils.FILE_PATH_KEY);
					actGoods.setActivityImg(activityImg);
				}
				
				if(actGoods.getActGoodsId() == null){
					activityGoodsService.save(request, actGoods);
				}else {
					activityGoodsService.update(request, actGoods);
				}
			}
		}else if(ConstantsDb.activityWechartLottery().equals(activity.getActivityCode())){
			//微信抽奖活动
			for (RfActivityGoods actGoods : viewActGoods.getActLotteryList()) {
				actGoods.setActivityId(activity.getActivityId());
				actGoods.setRbcAmount(null);
				actGoods.setActivityImg(null);
				actGoods.setActivityPrice(null);
				
				if(actGoods.getActGoodsId() == null){
					activityGoodsService.save(request, actGoods);
				}else {
					activityGoodsService.update(request, actGoods);
				}
			}
		}else if(ConstantsDb.activityCoupon().equals(activity.getActivityCode())){
			//优惠劵活动
			RfActivityGoodsExample example = new RfActivityGoodsExample();
			example.createCriteria().andActivityIdEqualTo(activity.getActivityId());
			activityGoodsMapper.deleteByExample(example);
			
			for(RfActivityGoodsDto actGoods : viewActGoods.getActCouponList()){
				if(!"".equals(actGoods.getCouponTypeIds()) && actGoods.getCouponTypeIds() != null){
					String typeIds[] = actGoods.getCouponTypeIds().split("\\|");
					
					for (String typeId : typeIds) {
						if(StringUtils.isNotBlank(typeId)){
							RfActivityGoods rfActivityGoods = new RfActivityGoods();
							rfActivityGoods.setActivityId(activity.getActivityId());
							rfActivityGoods.setGoodsId(actGoods.getGoodsId());
							rfActivityGoods.setCouponTypeId(Integer.parseInt(typeId.trim()));
							activityGoodsService.save(request, rfActivityGoods);
						}
					}
				}
			}
		}else if(ConstantsDb.activitySvip().equals(activity.getActivityCode())){
			//超级vip活动
			for(RfActivityGoodsDto actGoods : viewActGoods.getsVipGoodsList()){
				RfActivityGoodsExample example = new RfActivityGoodsExample();
				example.createCriteria().andActivityIdEqualTo(activity.getActivityId());
				activityGoodsMapper.deleteByExample(example);
				
				RfActivityGoods rfActivityGoods = new RfActivityGoods();
				rfActivityGoods.setActivityId(activity.getActivityId());
				rfActivityGoods.setGoodsId(actGoods.getGoodsId());
				activityGoodsService.save(request, rfActivityGoods);
			}
		}else if(ConstantsDb.activityTime().equals(activity.getActivityCode())){
			for (RfActivityGoods actGoods : viewActGoods.getPadTimeList()) {
				actGoods.setActivityId(activity.getActivityId());
				actGoods.setActivityImg(null);
				actGoods.setActivityPrice(null);
				actGoods.setLotteryCount(null);
				
				if(actGoods.getActGoodsId() == null){
					activityGoodsService.save(request, actGoods);
				}else {
					activityGoodsService.update(request, actGoods);
				}
			}
		}else if(ConstantsDb.activityWalleRecharge().equals(activity.getActivityCode())){
			//钱包充值活动
			Map<String, MultipartFile> fileMap = fileRequest.getFileMap();
			for (RfActivityGoods actGoods : viewActGoods.getWalletGoodsList()) {
				actGoods.setActivityId(activity.getActivityId());
				MultipartFile file = fileMap.get(walletUploadFileKey+actGoods.getGoodsId());
				if(file != null && !file.isEmpty()){
					Map<String, String> uploadMap = fileUtils.saveImageFile(file, filePathUtils.getImageUrl()+"/activity", filePathUtils.getImageLinkUrl()+"/activity");
					String activityImg = uploadMap.get(FileUtils.FILE_PATH_KEY);
					actGoods.setActivityImg(activityImg);
				}
				
				if(actGoods.getActGoodsId() == null){
					activityGoodsService.save(request, actGoods);
				}else {
					activityGoodsService.update(request, actGoods);
				}
			}			
		}else if(ConstantsDb.activityInviteTime().equals(activity.getActivityCode())){
			// 邀请购买设备活动
			for (RfActivityGoods actGoods : viewActGoods.getInvitePadTimeList()) {
				actGoods.setActivityId(activity.getActivityId());
				actGoods.setActivityImg(null);
				actGoods.setActivityPrice(null);
				actGoods.setLotteryCount(null);
				
				if(actGoods.getActGoodsId() == null){
					activityGoodsService.save(request, actGoods);
				}else {
					activityGoodsService.update(request, actGoods);
				}
			}
		}else if(ConstantsDb.activityInviteTimeCoupon().equals(activity.getActivityCode())){
			// 邀请登录活动 
			RfActivityGoodsExample example = new RfActivityGoodsExample();
			example.createCriteria().andActivityIdEqualTo(activity.getActivityId());
			activityGoodsMapper.deleteByExample(example);
			
			for(RfActivityGoodsDto actGoods : viewActGoods.getInvitePadTimeCouponList()){
				// 多个优惠劵, 以|号分隔
				String typeIds[] = actGoods.getCouponTypeIds().split("\\|");
				RfActivityGoods rfActivityGoods = new RfActivityGoods();
				// 活动id
				rfActivityGoods.setActivityId(activity.getActivityId());
				// 商品id
				rfActivityGoods.setGoodsId(actGoods.getGoodsId());
				// 是否有优惠劵赠送
				if(typeIds != null && typeIds.length > 0 && StringUtils.isNotBlank(typeIds[0])){
					for (String typeId : typeIds) {
						if(StringUtils.isNotBlank(typeId)){
							// 优惠劵id
							rfActivityGoods.setCouponTypeId(Integer.parseInt(typeId.trim()));
							// 赠送的设备时长
							rfActivityGoods.setPadTime(actGoods.getPadTime());
							activityGoodsService.save(request, rfActivityGoods);
							
						}
					}
				// 无优惠劵, 有设备时长赠送
				}else if(actGoods.getPadTime() != null){
					rfActivityGoods.setPadTime(actGoods.getPadTime());
					activityGoodsService.save(request, rfActivityGoods);
				}
				
			}
			
		}else if(ConstantsDb.activityInviteBuyLottery().equals(activity.getActivityCode())){
			// 邀请购买设备抽奖活动
			for (RfActivityGoods actGoods : viewActGoods.getInviteLotteryCountList()) {
				actGoods.setActivityId(activity.getActivityId());
				actGoods.setActivityImg(null);
				actGoods.setActivityPrice(null);
				
				if(actGoods.getActGoodsId() == null){
					activityGoodsService.save(request, actGoods);
				}else {
					activityGoodsService.update(request, actGoods);
				}
			}
		}else if(ConstantsDb.activityInviteSignupBox().equals(activity.getActivityCode())){
			// 邀请注册送平安盒 
			RfActivityGoodsExample example = new RfActivityGoodsExample();
			example.createCriteria().andActivityIdEqualTo(activity.getActivityId());
			activityGoodsMapper.deleteByExample(example);
			
			for(RfActivityGoodsDto actGoods : viewActGoods.getInviteSignupBoxCouponList()){
				// 多个优惠劵, 以|号分隔
				String typeIds[] = actGoods.getCouponTypeIds().split("\\|");
				RfActivityGoods rfActivityGoods = new RfActivityGoods();
				// 活动id
				rfActivityGoods.setActivityId(activity.getActivityId());
				// 商品id
				rfActivityGoods.setGoodsId(actGoods.getGoodsId());
				// 是否有优惠劵赠送
				if(typeIds != null && typeIds.length > 0 && StringUtils.isNotBlank(typeIds[0])){
					for (String typeId : typeIds) {
						if(StringUtils.isNotBlank(typeId)){
							// 优惠劵id
							rfActivityGoods.setCouponTypeId(Integer.parseInt(typeId.trim()));
							// 赠送的设备时长
							rfActivityGoods.setPadTime(actGoods.getPadTime());
							rfActivityGoods.setPadTimeTwo(actGoods.getPadTimeTwo());
							activityGoodsService.save(request, rfActivityGoods);
							
						}
					}
				// 无优惠劵, 有设备时长赠送
				}else if(actGoods.getPadTime() != null){
					rfActivityGoods.setPadTime(actGoods.getPadTime());
					rfActivityGoods.setPadTimeTwo(actGoods.getPadTimeTwo());
					activityGoodsService.save(request, rfActivityGoods);
				}
			}
		}else if(ConstantsDb.activityInviteBuyBox().equals(activity.getActivityCode())){
			// 邀请注册送圣诞盒 
			RfActivityGoodsExample example = new RfActivityGoodsExample();
			example.createCriteria().andActivityIdEqualTo(activity.getActivityId());
			activityGoodsMapper.deleteByExample(example);
			
			for(RfActivityGoodsDto actGoods : viewActGoods.getInviteBuyBoxCouponList()){
				// 多个优惠劵, 以|号分隔
				String typeIds[] = actGoods.getCouponTypeIds().split("\\|");
				RfActivityGoods rfActivityGoods = new RfActivityGoods();
				// 活动id
				rfActivityGoods.setActivityId(activity.getActivityId());
				// 商品id
				rfActivityGoods.setGoodsId(actGoods.getGoodsId());
				// 是否有优惠劵赠送
				if(typeIds != null && typeIds.length > 0 && StringUtils.isNotBlank(typeIds[0])){
					for (String typeId : typeIds) {
						if(StringUtils.isNotBlank(typeId)){
							// 优惠劵id
							rfActivityGoods.setCouponTypeId(Integer.parseInt(typeId.trim()));
							// 赠送的设备时长
							rfActivityGoods.setPadTime(actGoods.getPadTime());
							rfActivityGoods.setPadTimeTwo(actGoods.getPadTimeTwo());
							activityGoodsService.save(request, rfActivityGoods);
							
						}
					}
				// 无优惠劵, 有设备时长赠送
				}else if(actGoods.getPadTime() != null){
					rfActivityGoods.setPadTime(actGoods.getPadTime());
					rfActivityGoods.setPadTimeTwo(actGoods.getPadTimeTwo());
					activityGoodsService.save(request, rfActivityGoods);
				}
			}
		}
	}
	
}
