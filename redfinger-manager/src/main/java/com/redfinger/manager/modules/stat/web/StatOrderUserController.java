package com.redfinger.manager.modules.stat.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.ExcelUtils;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.modules.stat.base.StatDomain;
import com.redfinger.manager.modules.stat.service.StatOrderUserService;

@Controller
@RequestMapping(value = "")
public class StatOrderUserController extends BaseController {

	@Autowired
	StatOrderUserService service;

	@RequestMapping(value = "/stat/orderUser")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "/stat/orderUser/list")
	public List<RfUser> list(HttpServletRequest request,
			HttpServletResponse response, Model model, RfUser bean,
			String begin, String end, String userE, String userPhone,String orderSequence) {
		if (StringUtils.isEmpty(begin) || StringUtils.isEmpty(end)) {
			return new ArrayList<RfUser>();
		}
		StatDomain domain = new StatDomain();
		domain.setBegin(begin);
		domain.setEnd(end);
		domain.setWhere("");
		if (!StringUtils.isEmpty(userE)) {
			domain.setWhere(domain.getWhere()+" and user_email='"+userE+"'");
		}
		if (!StringUtils.isEmpty(userPhone)) {
			domain.setWhere(domain.getWhere()+ "and user_mobile_phone='"+userPhone+"'");
		}
		switch(orderSequence){
			case "1":domain.setWhere(domain.getWhere()+"ORDER BY number asc"); break ; 
			case "2":domain.setWhere(domain.getWhere()+"ORDER BY number DESC"); break ;
			case "3":domain.setWhere(domain.getWhere()+"ORDER BY money asc"); break ; 
			case "4":domain.setWhere(domain.getWhere()+"ORDER BY money DESC"); break ; 
		}
		
		return service.statUserOrder(domain);
	}

	@RequestMapping(value = "/stat/orderUser/export")
	public String export(HttpServletRequest request,
			HttpServletResponse response, Model model, String exportDatas,
			String exportHead, String exportField, String exportName,
			RfUser bean, String begin, String end, String userE,
			String userPhone,String orderSequence) throws Exception {
		exportHead = StringUtils.removeEnd(exportHead, ",");
		exportField = StringUtils.removeEnd(exportField, ",");
		response.setContentType("application/binary;charset=UTF-8");
		ServletOutputStream outputStream = response.getOutputStream();
		response.setHeader("Content-disposition", "attachment;filename="
				+ ExcelUtils.getFileName(request, exportName) + ".xls");
		Workbook workBook = new SXSSFWorkbook();
		SXSSFSheet sheet = (SXSSFSheet) workBook.createSheet("Sheet1");
		CellStyle headStyle = ExcelUtils.getHeadStyle(workBook);
		CellStyle bodyStyle = ExcelUtils.getBodyStyle(workBook);
		ExcelUtils.insertHead(exportHead.split(","), sheet, headStyle);
		boolean keep = true;
		while (keep) {
			List<RfUser> list = this.list(request, response, model, bean,
					begin, end, userE, userPhone,orderSequence);
			if (!Collections3.isEmpty(list)) {
				ExcelUtils.insertBody(JsonUtils.ObjectToString(list), sheet,
						bodyStyle, exportField.split(","));
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	@RequestMapping(value = "/stat/payForTheFirs")
	public String payForTheFirs(HttpServletRequest request,HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}
	@ResponseBody
	@RequestMapping(value = "/stat/payForTheFirs/getChart")
	public Map<String,Object> getChart (HttpServletRequest request, HttpServletResponse response, Model model,StatDomain bean) {
		return service.statPayForTheFirstTime(bean);
	}
	
	@RequestMapping(value = "/stat/statAgainPay")
	public String statAgainPay(HttpServletRequest request,HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}
	@ResponseBody
	@RequestMapping(value = "/stat/statAgainPay/getChart")
	public Map<String,Object> statAgainPayGetChart (HttpServletRequest request, HttpServletResponse response, Model model,StatDomain bean) {
		return service.statAgainPay(bean);
	}
}
