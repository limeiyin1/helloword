package com.redfinger.manager.modules.log.web;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.constants.ActivityCode;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfGoods;
import com.redfinger.manager.common.domain.RfOrder;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.ViewLogActivity;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.ExcelUtils;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.goods.service.GoodsService;
import com.redfinger.manager.modules.log.service.LogActivityService;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.order.service.OrderService;

@Controller
@RequestMapping(value = "/log/activity")
public class LogActivityController extends BaseController {
	@Autowired
	LogActivityService service;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;
	
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		List<RfGoods> goods=goodsService.initQuery().andEqualTo("goodsType", ConstantsDb.goodsVip()).findStopTrue();//获取商品列表，批量绑定设备
		model.addAttribute("goods", goods);
		return this.toPage(request, response, model);

	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<ViewLogActivity> list(HttpServletRequest request, HttpServletResponse response, ViewLogActivity bean,
			String signTimeGt,String signTimeLt,String buyTimeGt,String buyTimeLt, Integer externalUserId) throws Exception {
		Date start = null;
		Date end = null;
		Date buyStart = null;
		Date buyEnd = null;
		Integer userId = null;
		Integer inviteeId = null;
		if(StringUtils.isNotBlank(bean.getUserPhone()) || externalUserId != null){
			RfUser user = userService.getUserByExternalUserIdOrUserPhone(bean.getUserPhone(), externalUserId);
			if(null != user){
				userId = user.getUserId();
			}else {
				 userId = -1;
			}
		}
		
		if(StringUtils.isNotBlank(signTimeGt)){
			start = DateUtils.parseDate(signTimeGt + " 00:00:00");
		}
		if(StringUtils.isNotBlank(signTimeLt)){
			end = DateUtils.parseDate(signTimeLt + " 23:59:59");
		}
		if(StringUtils.isNotBlank(buyTimeGt)){
			buyStart = DateUtils.parseDate(buyTimeGt + " 00:00:00");
		}
		if(StringUtils.isNotBlank(buyTimeLt)){
			buyEnd = DateUtils.parseDate(buyTimeLt + " 23:59:59");
		}
		
		if(StringUtils.isNotBlank(bean.getInviteePhone())){
			RfUser user = userService.getUserByUserPhone(bean.getInviteePhone());
			if(null != user){
				inviteeId = user.getUserId();
			}else {
				inviteeId = -1;
			}
		}
		
		
/*		if(StringUtils.isNotBlank(bean.getPadCode())  && StringUtils.isBlank(bean.getActivityCode()) && 
				StringUtils.isBlank(bean.getActivityName())){
			bean.setActivityCode(ActivityCode.INVITE_TIME);
		}*/
		
		service.initQuery(bean)
		.andEqualTo("userId", userId)
		.andEqualTo("activityCode", bean.getActivityCode())
		.andEqualTo("activityName", bean.getActivityName())
		.andGreaterThanOrEqualTo("recoedTime", start)
		.andLessThanOrEqualTo("recoedTime", end)
		.andGreaterThanOrEqualTo("awardTime", buyStart)
		.andLessThanOrEqualTo("awardTime", buyEnd)
		.andEqualTo("goodsId", bean.getGoodsId())
		.andEqualTo("inviteeId", inviteeId)
		.andEqualTo("couponCode", bean.getCouponCode())
		.andEqualTo("orderId", bean.getOrderId())
		.andEqualTo("padCode", bean.getPadCode())
		.andEqualTo("inviteePadCode", bean.getInviteePadCode());
		
		if(YesOrNoStatus.NO.equals(bean.getAwardStatus())){
			service.andEqualTo("awardStatus", YesOrNoStatus.NO);
		} else if (YesOrNoStatus.YES.equals(bean.getAwardStatus())) {
			List<String> awardStatusIn = new ArrayList<>();
			awardStatusIn.add(YesOrNoStatus.YES);
			awardStatusIn.add("3");
			service.andIn("awardStatus", awardStatusIn);
		}
		
		if(StringUtils.isNotBlank(bean.getIsBuy())){
			//用户/被邀请人是否购买，订单id不为空则有购买
			if(YesOrNoStatus.YES.equals(bean.getIsBuy())){
				service.andIsNotNull("orderId");
			}
			if(YesOrNoStatus.NO.equals(bean.getIsBuy())){
				service.andIsNull("orderId");
			}
		}
		
		if(null != bean.getPadAdd()){
			if(1 == bean.getPadAdd()){
				service.andIsNotNull("padAdd");
			}else if (0 == bean.getPadAdd()) {
				service.andIsNull("padAdd");
			}
		}
	 
		List<ViewLogActivity> list = service.pageDelTrue(bean.getPage(), bean.getRows());
		List<Integer> userIds = new ArrayList<>();
		List<Integer> inviteeIds = new ArrayList<>();
		List<String> orderIds = new ArrayList<>();
		if(!Collections3.isEmpty(list)){
			for (ViewLogActivity act : list) {
				if(null != act.getUserId()){
					userIds.add(act.getUserId());
				}
				if(null != act.getInviteeId()){
					inviteeIds.add(act.getInviteeId());
				}
				if(StringUtils.isNotBlank(act.getOrderId())){
					orderIds.add(act.getOrderId());
				}
			}
			
			Map<Integer, String> userPhoneMap = new HashMap<>();
			Map<Integer, String> inviteePhoneMap = new HashMap<>();
			Map<String, Integer> orderPriceMap = new HashMap<>();
			
			if(!Collections3.isEmpty(userIds)){
				List<RfUser> users = userService.initQuery().andIn("userId", userIds).findStopTrue();
				for (RfUser rfUser : users) {
					userPhoneMap.put(rfUser.getUserId(), rfUser.getUserMobilePhone());
				}
			}
			
			if(!Collections3.isEmpty(inviteeIds)){
				List<RfUser> inviteeUsers = userService.initQuery().andIn("userId", inviteeIds).findStopTrue();
				for (RfUser rfUser : inviteeUsers) {
					inviteePhoneMap.put(rfUser.getUserId(), rfUser.getUserMobilePhone());
				}
			}
			
			if(!Collections3.isEmpty(orderIds)){
				List<RfOrder> orders = orderService.initQuery().andIn("orderId", orderIds).findStopTrue();
				for (RfOrder rfOrder : orders) {
					orderPriceMap.put(rfOrder.getOrderId(), rfOrder.getOrderPrice());
				}
			}
			
			for (ViewLogActivity viewLogAct : list) {
				viewLogAct.setUserPhone(userPhoneMap.get(viewLogAct.getUserId()));
				
				if(null != viewLogAct.getInviteeId()){
					viewLogAct.setInviteePhone(inviteePhoneMap.get(viewLogAct.getInviteeId()));
				}
				
				if(null != viewLogAct.getOrderId()){
						Integer orderPrice = orderPriceMap.get(viewLogAct.getOrderId());
						orderPrice = orderPrice==null?0:orderPrice;
						DecimalFormat df = new DecimalFormat("0.0");
						String orderPriceStr = df.format((double)orderPrice/100);//保留1位小数
						viewLogAct.setOrderPrice(orderPriceStr);
				}
				
				/** 查询客户端ID*/
				if(viewLogAct.getUserId() != null){
					/** 根据用户Id查询用户*/
					RfUser rfUser = userService.load(viewLogAct.getUserId());
					/** 查询客户端ID*/
					viewLogAct.getMap().put("externalUserId", rfUser != null ? rfUser.getExternalUserId() : "");
				}
			}
		}
		
		PageInfo<ViewLogActivity> pageInfo = new PageInfo<ViewLogActivity>(list);	
		return pageInfo;
	}
	
	// 导出
	@RequestMapping(value="export")
	public String export(HttpServletRequest request, HttpServletResponse response, Model model, ViewLogActivity bean,
			String signTimeGt,String signTimeLt,String buyTimeGt,String buyTimeLt,
			String exportName, String exportHead, String exportField, Integer externalUserId) throws Exception {
		if(null != bean && StringUtils.isNotBlank(bean.getActivityName())){
			bean.setActivityName(new String(bean.getActivityName().getBytes("iso8859-1"),"utf-8"));
		}
		
		exportHead = StringUtils.removeEnd(exportHead, ",");
		exportField = StringUtils.removeEnd(exportField, ",");
		
		response.setContentType("application/binary;charset=UTF-8");
		ServletOutputStream outputStream = response.getOutputStream();
		response.setHeader("Content-disposition", "attachment; filename=" + ExcelUtils.getFileName(request, exportName) + ".xls");
		// 创建一个workbook 对应一个excel应用文件
		Workbook workBook = new SXSSFWorkbook();
		SXSSFSheet sheet = (SXSSFSheet) workBook.createSheet("Sheet1");
		CellStyle headStyle = ExcelUtils.getHeadStyle(workBook);
		CellStyle bodyStyle = ExcelUtils.getBodyStyle(workBook);
		// 构建表头
		ExcelUtils.insertHead(exportHead.split(","), sheet, headStyle);
		// 构建表体
		boolean keep = true;
		int page = 1;
		while(keep){
			bean.setPage(page);
			bean.setRows(1000);
			PageInfo<ViewLogActivity> pageInfo = this.list(request, response, bean,signTimeGt,signTimeLt,buyTimeGt,buyTimeLt, externalUserId);
			List<ViewLogActivity> list = pageInfo.getList();
			if (!Collections3.isEmpty(list)) {
				for (ViewLogActivity viewLogActivity : list) {
					viewLogActivity.setActivityCode(ActivityCode.DICT_MAP.get(viewLogActivity.getActivityCode()));
				}
				
				ExcelUtils.insertPageBody(JsonUtils.ObjectToString(list), sheet, bodyStyle, exportField.split(","), pageInfo.getStartRow());
				if (pageInfo.isHasNextPage()) {
					page++;
					continue;
				}
			}
			keep = false;
		}
		try {
			workBook.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
    }
}








