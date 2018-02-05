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
import com.redfinger.manager.modules.stat.service.StatLoginUserLogService;

@Controller
@RequestMapping(value = "/stat/loginLog")
public class StatLoginLogController extends BaseController {
	@Autowired
	StatLoginUserLogService statLogingService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "getChart")
	public Map<String, Object> getChart(HttpServletRequest request, HttpServletResponse response, Model model, StatDomain bean) {
		return statLogingService.stat(bean);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "export")
	public String export(HttpServletRequest request, HttpServletResponse response, Model model, String exportDatas, String exportHead, String exportField, String exportName) throws Exception {
		response.setContentType("application/binary;charset=UTF-8");
		ServletOutputStream outputStream = response.getOutputStream();
		response.setHeader("Content-disposition", "attachment; filename=" + ExcelUtils.getFileName(request, exportName) + ".xls");
		Map<String, Object> map = JsonUtils.stringToObject(exportDatas, Map.class);
		List<String> lableList = (List<String>) map.get("label");
		List<Integer> numberList = (List<Integer>) map.get("number");
		//将number数据切割成二维数组
		Object[] arr_number = numberList.toArray();
		int row = lableList.size();
		int column = numberList.size()/lableList.size();
		Integer[][] arr_2 = new Integer[row][column];
		for(int i = 0 ;i<arr_number.length ; i++){
			arr_2[i%row][i/row]=(Integer) arr_number[i];
		}
		//设计list,每个map对应一行数据
		List<HashMap<String, String>> list = Lists.newArrayList();
		String[] bodyLabel = exportField.split(",");
		//填充数据
		for(int i = 0 ;i<row;i++){
			HashMap<String, String> m= new HashMap<String, String>();
			m.put("label", lableList.get(i));
			for(int y = 0 ;y<column ;y++){
				m.put(bodyLabel[y+1], arr_2[i][y].toString());
			}
			list.add(m);
		}
		String json = JsonUtils.ObjectToString(list);
		ExcelUtils.exportExcel(json, exportHead.split(","), exportField.split(","), outputStream);
		return null;
	}
}
