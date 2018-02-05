package com.redfinger.manager.modules.fault.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.gather.domain.RfClientNetinfo;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.ExcelUtils;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.modules.fault.service.ClientNetInfoService;
import com.redfinger.manager.modules.member.service.UserService;

@Controller
@RequestMapping(value = "/fault/netInfo")
public class ClientNetInfoController extends BaseController{
	
	@Autowired
	private UserService userService;
	@Autowired
	private ClientNetInfoService service;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfClientNetinfo> list(HttpServletRequest request, HttpServletResponse response, Model model, RfClientNetinfo bean, String serverStartTime, String serverEndTime) throws Exception {
		Date serverStartDate = StringUtils.isBlank(serverStartTime) ? null : DateUtils.parseDate(serverStartTime);
		Date serverEndDate = StringUtils.isBlank(serverEndTime) ? null : DateUtils.parseDate(serverEndTime);
		serverEndTime = request.getParameter("serverEndTime");
		/** 因为表中默认是不添加用户手机号和外部ID号的, 所以要通查询用户表来查询数据*/
		Integer userId = null;
		if (bean.getExternalUserId() != null || StringUtils.isNotBlank(bean.getUserMobilePhone())) {
			RfUser user = userService.getUserByExternalUserIdOrUserPhone(bean.getUserMobilePhone(),bean.getExternalUserId());
			if (user != null) {
				userId = user.getUserId();
			} else {
				userId = -1;
			}
		}
		
		List<RfClientNetinfo> list = service.initQuery(bean).andEqualTo("userId", userId)
							   .andEqualTo("padCode", bean.getPadCode())
							   .andGreaterThanOrEqualTo("serverTime", serverStartDate)
							   .andLessThanOrEqualTo("serverTime", serverEndDate)
							   .addOrderClause("server_time", "desc")
							   .addOrderForce(bean.getSort(), bean.getOrder()).pageDelTrue(bean.getPage(), bean.getRows());
		
		if(list != null && list.size() > 0){
			for (RfClientNetinfo clientNetinfo : list) {
				/** 查询客户端ID*/
				if(clientNetinfo.getUserId() != null){
					/** 根据用户Id查询用户*/
					RfUser user = userService.load(clientNetinfo.getUserId());
					/** 查询客户端ID*/
					if(user != null){
						clientNetinfo.setUserMobilePhone(StringUtils.isNotBlank(user.getUserMobilePhone()) ? user.getUserMobilePhone() : "");
						clientNetinfo.setExternalUserId(user.getExternalUserId());
					}
				}
				
			}
		}
		
		PageInfo<RfClientNetinfo> pageInfo = new PageInfo<RfClientNetinfo>(list);
		return pageInfo;
		
	}
	
	// 导出
		@RequestMapping(value="export")
		public String export(HttpServletRequest request, HttpServletResponse response, 
				Model model, RfClientNetinfo bean, String serverStartTime, String serverEndTime,
				String exportHead, String exportField, String exportName)throws Exception{
			exportHead=StringUtils.removeEnd(exportHead, ",");
			exportField=StringUtils.removeEnd(exportField, ",");
			response.setContentType("application/binary;charset=UTF-8");
			ServletOutputStream outputStream = response.getOutputStream();
			response.setHeader("Content-disposition", "attachment; filename=" + ExcelUtils.getFileName(request, exportName)+".xlsx");
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
				PageInfo<RfClientNetinfo> pageInfo=this.list(request, response, model, bean, serverStartTime, serverEndTime);
				List<RfClientNetinfo> list=pageInfo.getList();
				if(!Collections3.isEmpty(list)){
					for (RfClientNetinfo clientNetinfo : list) {
						if(clientNetinfo.getTcpingResult() != null){
							if(clientNetinfo.getTcpingResult() == 1){
								clientNetinfo.getMap().put("tcpingResult", "成功");
							}else if(clientNetinfo.getTcpingResult() == 0){
								clientNetinfo.getMap().put("tcpingResult", "失败");
							}else if(clientNetinfo.getTcpingResult() == -1){
								clientNetinfo.getMap().put("tcpingResult", "解析失败");
							}else{
								clientNetinfo.getMap().put("tcpingResult", clientNetinfo.getTcpingResult());
							}
						}
						
						if(clientNetinfo.getTraceResult() != null){
							if(clientNetinfo.getTraceResult() == 1){
								clientNetinfo.getMap().put("traceResult", "成功");
							}else if(clientNetinfo.getTraceResult() == 0){
								clientNetinfo.getMap().put("traceResult", "失败");
							}else if(clientNetinfo.getTraceResult() == -1){
								clientNetinfo.getMap().put("traceResult", "解析失败");
							}else{
								clientNetinfo.getMap().put("traceResult", clientNetinfo.getTcpingResult());
							}
						}
						
						if(clientNetinfo.getPingResult() != null){
							if(clientNetinfo.getPingResult() == 1){
								clientNetinfo.getMap().put("pingResult", "成功");
							}else if(clientNetinfo.getPingResult() == 0){
								clientNetinfo.getMap().put("pingResult", "失败");
							}else if(clientNetinfo.getPingResult() == -1){
								clientNetinfo.getMap().put("pingResult", "解析失败");
							}else{
								clientNetinfo.getMap().put("pingResult", clientNetinfo.getPingResult());
							}
						}
						
						if(clientNetinfo.getGatherType() != null){
							if("control".equals(clientNetinfo.getGatherType())){
								clientNetinfo.setGatherType("控制设备失败");
							}else if("request".equals(clientNetinfo.getGatherType())){
								clientNetinfo.setGatherType("请求服务失败");
							}
						}
						
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
