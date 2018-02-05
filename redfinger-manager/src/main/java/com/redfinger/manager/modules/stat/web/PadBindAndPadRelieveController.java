package com.redfinger.manager.modules.stat.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.utils.ExcelUtils;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.modules.stat.base.StatDomain;
import com.redfinger.manager.modules.stat.service.PadBindService;

@Controller
@RequestMapping(value = "/stat/padBindAndPadRelieve")
public class PadBindAndPadRelieveController extends BaseController {

	@Autowired
	PadBindService service;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "getChart")
	public Map<String, Object> getChart(HttpServletRequest request, HttpServletResponse response, Model model, StatDomain bean) {
		Map<String, Object> result = new HashMap<>();
		bean.setWhere("category in ('bind')");
		Map<String, Object> bind = service.stat(bean);
		bean.setWhere("category in ('relieve')");
		Map<String, Object> relieve = service.stat(bean);
		
		result.put("bind", bind);
		result.put("relieve", relieve);
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "export")
	public String export(HttpServletRequest request, HttpServletResponse response, Model model, String exportDatas, String exportHead, String exportField, String exportName) throws Exception {
		response.setContentType("application/binary;charset=UTF-8");
		ServletOutputStream outputStream = response.getOutputStream();
		response.setHeader("Content-disposition", "attachment; filename=" + ExcelUtils.getFileName(request, exportName) + ".xls");
		Map<String, Object> map = JsonUtils.stringToObject(exportDatas, Map.class);
		
		Map<String, Object> bind = (Map<String, Object>) map.get("bind");
		Map<String, Object> relieve = (Map<String, Object>) map.get("relieve");
		
		List<String> lableList = (List<String>) bind.get("label");
		List<Integer> numberList = (List<Integer>) bind.get("number");
		List<Integer> numberList2 = (List<Integer>) relieve.get("number");
		
		List<StatDomain> list = Lists.newArrayList();
		
		for (int i = 0; i < lableList.size(); i++) {
			StatDomain domain = new StatDomain();
			domain.setNumber(numberList.get(i));
			domain.setLabel(lableList.get(i));
			domain.setId(numberList2.get(i));
			list.add(domain);
		}
		
		
		
		String json = JsonUtils.ObjectToString(list);
		ExcelUtils.exportExcel(json, exportHead.split(","), exportField.split(","), outputStream);
		return null;
	}
}
