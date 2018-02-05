package com.redfinger.manager.modules.log.web;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfPayTradeLog;
import com.redfinger.manager.common.domain.RfPayTradeLogExample;
import com.redfinger.manager.common.helper.DictHelper;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.ExcelUtils;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.modules.log.service.PayTradeLogService;

@Controller
@RequestMapping(value = "/log/tradelog")
public class PayTradeLogController extends BaseController {
	@Autowired
	PayTradeLogService service;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}
	@ResponseBody
	@RequestMapping(value="list")
	public PageInfo<RfPayTradeLog>list(HttpServletRequest request,HttpServletResponse response,Model model,RfPayTradeLog bean,Double tradeMoneyDouble, String tradeCondition)throws Exception{
		service.initQuery(bean).andGreaterThanOrEqualTo("tradeTime",DateUtils.parseDate( bean.getBeginTimeStr()))
			.andLessThan("tradeTime", DateUtils.parseDateAddOneDay(bean.getEndTimeStr()))
			.andLike("orderId", bean.getOrderId())
			.andLike("tradeSerialNum ", bean.getTradeSerialNum())
			.andEqualTo("payModeCode", bean.getPayModeCode())
			.andEqualTo("gatherAccount", bean.getGatherAccount())
			.andLike("tradeAccount", bean.getTradeAccount());
		
		if("success".equals(bean.getTradeStatus())){
			/**成功时tradeStatus的值可能为success，TRADE_SUCCESS，Success!，SUCCESS**/
			ArrayList<Object> tradeStatusIn = new ArrayList<>();
			tradeStatusIn.add("success");
			tradeStatusIn.add("TRADE_SUCCESS");
			tradeStatusIn.add("Success!");
			tradeStatusIn.add("SUCCESS");
			
			service.andIn("tradeStatus",tradeStatusIn );
		}else{
			service.andEqualTo("tradeStatus", bean.getTradeStatus());
		}	
		
		if(tradeMoneyDouble != null){
			int tradeMoney = (int)(tradeMoneyDouble*100);
			if(">".equals(tradeCondition)){
				service.andGreaterThan("tradeMoney", tradeMoney);
			}else if("≥".equals(tradeCondition)){
				service.andGreaterThanOrEqualTo("tradeMoney", tradeMoney);
			}else if("<".equals(tradeCondition)){
				service.andLessThan("tradeMoney", tradeMoney);
			}else if("≤".equals(tradeCondition)){
				service.andLessThanOrEqualTo("tradeMoney", tradeMoney);
			}else if("=".equals(tradeCondition)){
				service.andEqualTo("tradeMoney", tradeMoney);
			}
		}
		
		service.addOrderForce(bean.getSort(),bean.getOrder()).addOrderClause("tradeTime", "desc");
		/** 获取条件对象*/
		RfPayTradeLogExample example = (RfPayTradeLogExample) service.getExample();
		/** 根据条件分页查询数据*/
		List<RfPayTradeLog> list= service.pageDelTrue(bean.getPage(), bean.getRows());
		/** 根据条件查询交易总金额*/
		Long tradeMoneyCount = service.tradeMoneySumByExample(example);
		if(list != null && list.size() > 0){
			/** 设置交易总金额*/
			list.get(0).getMap().put("tradeMoneyCount", tradeMoneyCount == null ? 0 : tradeMoneyCount);
			for (RfPayTradeLog rfPayTradeLog : list) {
				if ("redfinger@redfinger.cn".equals(rfPayTradeLog.getGatherAccount()) && ("".equals(rfPayTradeLog.getPayModeCode()) || null == rfPayTradeLog.getPayModeCode())) {
					rfPayTradeLog.setPayModeCode(ConstantsDb.rfPayTradeLogPayModeCodeAlipay());
				}
				
			}
		}
		PageInfo<RfPayTradeLog> pageInfo = new PageInfo<RfPayTradeLog>(list);
		return pageInfo;
	}
	
	
	//导出EXcel数据
	@RequestMapping(value="export")
	public String export(HttpServletRequest request,HttpServletResponse response,Model model,RfPayTradeLog bean,String exportHead, String exportField, String exportName,Double tradeMoneyDouble, String tradeCondition) throws Exception{
		exportHead=StringUtils.removeEnd(exportHead, ",");
		exportField=StringUtils.removeEnd(exportField, ",");
		response.setContentType("application/binary;charset=UTF-8");
		ServletOutputStream outputStream = response.getOutputStream();
		response.setHeader("Content-disposition", "attachment; filename=" + ExcelUtils.getFileName(request, exportName)+".xls");
		// 创建一个workbook 对应一个excel应用文件
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
			PageInfo<RfPayTradeLog> pageInfo=this.list(request, response, model, bean,tradeMoneyDouble, tradeCondition);
			List<RfPayTradeLog> list=pageInfo.getList();
			if(!Collections3.isEmpty(list)){
				for(RfPayTradeLog log:list){
					if ("redfinger@redfinger.cn".equals(log.getGatherAccount()) && ("".equals(log.getPayModeCode()) || null == log.getPayModeCode())) {
						log.setPayModeCode(ConstantsDb.rfPayTradeLogPayModeCodeAlipay());
					}else{
					log.setPayModeCode(DictHelper.getLabel("rf_pay_trade_log.pay_mode_code", log.getPayModeCode()));
					}   
					log.setTradeMoney(log.getTradeMoney()/100);
			        log.setEnableStatus(DictHelper.getLabel("global.enable_status",log.getEnableStatus()));
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
}
