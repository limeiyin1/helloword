package com.redfinger.manager.modules.order.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfGoods;
import com.redfinger.manager.common.domain.RfOrder;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.DictHelper;
import com.redfinger.manager.common.mapper.StatOrderMapper;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.ExcelUtils;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.common.utils.OrderStatus;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.modules.goods.service.GoodsService;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.order.service.OrderService;
@Controller
@RequestMapping(value="/order/order")
public class OrderController extends BaseController {
	@Autowired
	OrderService service;
	@Autowired
	UserService userService;
	@Autowired
	GoodsService goodsService;

	@Autowired
	StatOrderMapper mapper;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		List<RfGoods> goods = goodsService.initQuery().findStopTrue();
		model.addAttribute("goods", goods);
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value="list")
	public PageInfo<RfOrder>list(HttpServletRequest request,HttpServletResponse response,Model model,RfOrder bean,
			String userPhone,Integer externalUserId)throws Exception{
		List<RfUser> userlist= Lists.newArrayList();
		List<Integer> userId = Lists.newArrayList();
		if((userPhone!=null&&!"".equals(userPhone))||externalUserId!=null){
			userlist=userService.initQuery()
					.andEqualTo("userMobilePhone", userPhone)
					.andEqualTo("externalUserId", externalUserId)
					.singleStopTrue();
			if(!(Collections3.isEmpty(userlist))){
				for (RfUser user : userlist) {
					userId.add(user.getUserId());
				}
			}else{
				userId.add(0);
			}
		}
		List<RfOrder> list=null;
		list = service.initQuery(bean)
			.andGreaterThanOrEqualTo("orderCreateTime",DateUtils.parseDate( bean.getBeginTimeStr()))
			.andLessThanOrEqualTo("orderCreateTime", DateUtils.parseDateAddOneDay(bean.getEndTimeStr()))
			.andIn("userId", userId)
			.andLike("remark", bean.getRemark())
			.andLike("orderId", bean.getOrderId())
			.andEqualTo("userSource", bean.getUserSource()) //订单来源
			.andEqualTo("payModeCode", bean.getPayModeCode()) //支付方式
			.andEqualTo("orderStatus", bean.getOrderStatus())
			.andEqualTo("payModeCode", bean.getPayModeCode())
			.andEqualTo("goodsId", bean.getGoodsId())
			.andLike("padCode", bean.getPadCode())
			.andEqualTo("padGrade", bean.getPadGrade())
			.andEqualTo("orderPrice", bean.getOrderPrice())
		    .andEqualTo("orderBizType",bean.getOrderBizType())
		    .andEqualTo("owner", bean.getOwner())
			.addOrderForce(bean.getSort(),bean.getOrder() )
			.addOrderClause("realPayAmount", "desc")
			.addOrderClause("createTime", "desc")
			.pageAll(bean.getPage(), bean.getRows());
		
		 	if(null != list && list.size()>0){
		 		for (RfOrder order : list) {
					if(!("".equals(order.getUserId())||null==order.getUserId())){
						order.getMap().put("userMobilePhone",userService.load(order.getUserId())==null?"--":userService.load(order.getUserId()).getUserMobilePhone());
						order.getMap().put("userEmail", userService.load(order.getUserId())==null? "--" : userService.load(order.getUserId()).getUserEmail());
						if(order.getGoodsId() == null){
							order.getMap().put("goodsName", order.getGoodsName());
						}else{
							RfGoods goods = goodsService.get(order.getGoodsId());
							if(goods != null){
								order.getMap().put("goodsName", goods.getGoodsName());
							}
						}
					}
					
					/** 查询客户端ID*/
					if(order.getUserId() != null){
						/** 根据用户Id查询用户*/
						RfUser rfUser = userService.load(order.getUserId());
						/** 查询客户端ID*/
						order.getMap().put("externalUserId", rfUser != null ? rfUser.getExternalUserId() : "");
					}
				}
		 	}
		
		PageInfo<RfOrder> pageInfo = new PageInfo<RfOrder>(list);
		return pageInfo;
	}
	
	 // 导出
	@RequestMapping(value="export")
	public String export(HttpServletRequest request,HttpServletResponse response,Model model,RfOrder bean,String exportHead, String exportField, String exportName,String userPhone,Integer externalUserId)throws Exception{
		exportField=exportField.replace("checkboxValue", "orderId");
		exportHead=StringUtils.removeEnd(exportHead, ",");
		exportField=StringUtils.removeEnd(exportField, ",");
		response.setContentType("application/binary;charset=UTF-8");
		ServletOutputStream outputStream = response.getOutputStream();
		response.setHeader("Content-disposition", "attachment; filename=" + ExcelUtils.getFileName(request, exportName)+".xls");
		Workbook workBook = new SXSSFWorkbook();
		SXSSFSheet sheet = (SXSSFSheet) workBook.createSheet("Sheet1");
		CellStyle headStyle = ExcelUtils.getHeadStyle(workBook);
		CellStyle bodyStyle = ExcelUtils.getBodyStyle(workBook);
		// 构建表头
		ExcelUtils.insertHead(exportHead.split(","), sheet, headStyle);
		// 构建表体
		boolean keep=true;
		int page=1;
		while(keep){
			bean.setPage(page);
			bean.setRows(1000);
			PageInfo<RfOrder> pageInfo=this.list(request, response, model, bean,userPhone,externalUserId);
			List<RfOrder> list=pageInfo.getList();
			if(!Collections3.isEmpty(list)){
				for(RfOrder order:list){
					order.setOrderStatus(DictHelper.getLabel("rf_order.order_status", order.getOrderStatus()));
					order.setEnableStatus(DictHelper.getLabel("global.enable_status", order.getEnableStatus()));
					order.setOrderType(DictHelper.getLabel("rf_order.order_type", order.getOrderType()));
					order.setPayModeCode(DictHelper.getLabel("rf_order.pay_mode_code", order.getPayModeCode()));
				}
				ExcelUtils.insertPageBody(JsonUtils.ObjectToString(list),sheet,bodyStyle,exportField.split(","),pageInfo.getStartRow());
				if(pageInfo.isHasNextPage()){
					page++;
					continue;
				}
			}
			keep=false;
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
    
	@RequestMapping(value = "form")
	public String userForm(HttpServletRequest request, HttpServletResponse response, Model model, RfOrder bean) {
		if (bean.getOrderId() == null) {

		} else {
			bean = service.get(bean.getOrderId());
			model.addAttribute("bean", bean);

		}
	   RfUser user= userService.get(bean.getUserId());
	     bean.getMap().put("userEmail", user.getUserEmail());
	     bean.getMap().put("userMobilePhone",user.getUserMobilePhone());
	     bean.getMap().put("goodsName", goodsService.load(bean.getGoodsId())==null? "--" : goodsService.load(bean.getGoodsId()).getGoodsName());
		model.addAttribute("bean", bean);
		return this.toPage(request, response, model);
	}
	
	// 编辑
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfOrder bean) throws Exception {
		if(OrderStatus.ORDER_REFUNDS.equals(bean.getOrderStatus())){
			RfOrder order = service.get(bean.getOrderId());
			if(!OrderStatus.PAY_FINISH.equals(order.getOrderStatus()) && !OrderStatus.PAY_FINISH_DEDUCTION_FAILE.equals(order.getOrderStatus())){
				throw new BusinessException("只有支付成功的订单才能更改为已退款");
			}
			order.setOrderType(ConstantsDb.rfOrderOrderTypeReimburse());	//退款订单
		}
		service.update(request, bean);
	}
	
/*	*//** 等待付款 *//*
	public static final String WAIT_FOR_PAY = "0";
	*//** 付款完成 *//*
	public static final String PAY_FINISH = "1";
	*//** 订单关取消*//*
	public static final String CANCLED = "2";
	*//** 设备分配成功*//*
	public static final String ASSIGN_SUCCESS = "3";
	*//** 设备分配失败*//*
	public static final String ASSIGN_FAIL = "4";
	*//** 设备处理成功*//*
	public static final String PAD_HANDL_SUCCESS = "5";
	*//** 设备处理失败*//*
	public static final String PAD_HANDL_FAIL = "6";
*/
	@ResponseBody
	@RequestMapping(value = "floatData")
	public Map<String,Integer> floatDate(HttpServletRequest request, HttpServletResponse response, Model model, RfOrder bean){
		//完成条件
		List<String> list=new ArrayList<String>();
		list.add( ConstantsDb.rfOrderOrderStatusPayment());
		list.add(ConstantsDb.rfOrderOrderStatusHandleyes());
		list.add(ConstantsDb.rfOrderOrderStatusAssignyes());
		Map<String,Integer> map=Maps.newHashMap();
		Date time=new Date();
		bean.setEnableStatus("1");
		//当日总数
		Integer	floatExpire=service.initQuery().andIn("orderStatus", list).andGreaterThanOrEqualTo("orderCreateTime",DateUtils.parseDate( DateUtils.getDate())).countAll();
		map.put("floatExpire", floatExpire);
	
		bean.setOrderCreateTime(DateUtils.parseDate( DateUtils.getDate()));
		Integer	floatExpire2=mapper.priceByExample(bean);
		bean.setOrderCreateTime(null);
		bean.setEnableStatus("1");
		map.put("floatExpire2", floatExpire2==null?0:floatExpire2/100);
		//当前总数
		Integer	floatEnable=service.initQuery().andIn("orderStatus", list).andLessThanOrEqualTo("orderCreateTime",time).countAll();
		
		Integer	floatEnable2=mapper.priceByExample(bean);
		map.put("floatEnable2", floatEnable2==null?0:floatEnable2/100);
		map.put("floatEnable", floatEnable);
		//充值（完成的订单）
		Integer floatTotal=service.initQuery().andEqualTo("orderBizType", ConstantsDb.rfOrderOrderBizTypeCharge()).andIn("orderStatus", list).countAll();  
		map.put("floatTotal", floatTotal);
	
		bean.setOrderBizType(ConstantsDb.rfOrderOrderBizTypeCharge());
		Integer floatTotal2= mapper.priceByExample(bean);
		map.put("floatTotal2", floatTotal2==null?0:floatTotal2/100);
		//购买（完成的订单）
		Integer floatFault=service.initQuery().andIn("orderStatus", list).andEqualTo("orderBizType", ConstantsDb.rfOrderOrderBizTypePurchase()).countAll();
		map.put("floatFault", floatFault);
	
		bean.setOrderBizType( ConstantsDb.rfOrderOrderBizTypePurchase());
		Integer floatFault2=mapper.priceByExample(bean);
		map.put("floatFault2", floatFault2==null?0:floatFault2/100);
		return map;
	}
}