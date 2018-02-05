package com.redfinger.manager.modules.tools.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.RfCustom;
import com.redfinger.manager.common.domain.RfCustomExample;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.ExcelUtils;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.modules.tools.service.CustomManageService;
/** 
 * @Description 客服排班
 * @author yirongze 
 * @date 2017年8月1日 上午9:06:04 
 */
@Controller
@RequestMapping(value = "/tools/custom")
public class CustomController extends BaseController {
	@Autowired
	CustomManageService customManageService;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request,HttpServletResponse response,Model model) throws Exception {
		return this.toPage(request,response,model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfCustom> list(HttpServletRequest request,HttpServletResponse response,Model model,RfCustom bean)throws Exception {
		Map<Integer,RfCustom> oneMap = new TreeMap<>();
		Map<Integer,RfCustom> twoMap = new TreeMap<>();
		Map<Integer,RfCustom> threeMap = new TreeMap<>();
		Map<Integer,RfCustom> fourMap = new TreeMap<>();
		Map<Integer,RfCustom> fiveMap = new TreeMap<>();
		Map<Integer,RfCustom> sixMap = new TreeMap<>();
		Map<Integer,RfCustom> sevenMap = new TreeMap<>();
		Map<Integer,RfCustom> eightMap = new TreeMap<>();
		customManageService.initQuery(bean);
		RfCustomExample example= (RfCustomExample)customManageService.getExample();
		example.getMap().put("disableTimeStart", DateUtils.parseDate(getFirstDayByMonth(bean.getYear(),bean.getMonth())));
		List<RfCustom> customList = customManageService
				.andLessThanOrEqualTo("enableTime", DateUtils.parseDate(getLastDayByMonth(bean.getYear(),bean.getMonth())))
				.findDelTrue();
		for (RfCustom rfCustom : customList){
			rfCustom.setYear(bean.getYear());
			rfCustom.setMonth(bean.getMonth());
			if(rfCustom.getCustomGroup()==1){
				oneMap.put(rfCustom.getCustomId(), rfCustom);
			}else if(rfCustom.getCustomGroup()==2){
				twoMap.put(rfCustom.getCustomId(), rfCustom);
			}else if(rfCustom.getCustomGroup()==3){
				threeMap.put(rfCustom.getCustomId(), rfCustom);
			}else if(rfCustom.getCustomGroup()==4){
				fourMap.put(rfCustom.getCustomId(), rfCustom);
			}else if(rfCustom.getCustomGroup()==5){
				fiveMap.put(rfCustom.getCustomId(), rfCustom);
			}else if(rfCustom.getCustomGroup()==6){
				sixMap.put(rfCustom.getCustomId(), rfCustom);
			}else if(rfCustom.getCustomGroup()==7){
				sevenMap.put(rfCustom.getCustomId(), rfCustom);
			}else if(rfCustom.getCustomGroup()==8){
				eightMap.put(rfCustom.getCustomId(), rfCustom);
			}
		}
		Page<RfCustom> list = getList(bean,oneMap,twoMap,threeMap,fourMap,fiveMap,sixMap,sevenMap,eightMap);
		PageInfo<RfCustom> pageInfo = new PageInfo<RfCustom>(list);
		return pageInfo;
	}

	// 导出
	@RequestMapping(value = "export")
	public String export(HttpServletRequest request,HttpServletResponse response,Model model,RfCustom bean,String exportHead,String exportField,String exportName) throws Exception {
		exportField = exportField.replace("checkboxValue","orderId");
		exportHead = StringUtils.removeEnd(exportHead,",");
		exportField = StringUtils.removeEnd(exportField,",");
		response.setContentType("application/binary;charset=UTF-8");
		ServletOutputStream outputStream = response.getOutputStream();
		response.setHeader("Content-disposition","attachment; filename=" + ExcelUtils.getFileName(request,exportName) + ".xls");
		Workbook workBook = new SXSSFWorkbook();
		SXSSFSheet sheet = (SXSSFSheet) workBook.createSheet("Sheet1");
		CellStyle headStyle = getStyle(workBook,HSSFColor.GREY_25_PERCENT.index);
		// 构建表头
		String[] titles = exportHead.split(",");
		ExcelUtils.insertHead(titles,sheet,headStyle);
		// 构建表体
		PageInfo<RfCustom> pageInfo = this.list(request,response,model,bean);
		List<RfCustom> list = pageInfo.getList();
		insertPageBody(workBook,JsonUtils.ObjectToString(list),sheet,exportField.split(","),1);
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

	private static CellStyle getStyle(Workbook wb,short bg) {
		// 创建单元格样式
		CellStyle cellStyle = wb.createCellStyle();
		// 设置单元格的背景颜色
		cellStyle.setFillForegroundColor(bg);
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		// 设置单元格居中对齐
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		// 设置单元格垂直居中对齐
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		// 创建单元格内容显示不下时自动换行
		cellStyle.setWrapText(true);
		// 设置单元格字体样式
		Font font = wb.createFont();
		// 设置字体
		font.setFontName("宋体");
		font.setFontHeight((short) 200);
		cellStyle.setFont(font);
		// 设置单元格边框为细线条
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		return cellStyle;
	}

	// 设置私有的Excel单元格颜色
	@SuppressWarnings({ "unchecked","rawtypes",})
	private static void insertPageBody(Workbook workBook,String json,SXSSFSheet sheet,String[] fields,int startRow) {
		List<LinkedHashMap<String,Object>> list = JsonUtils.stringToObject(json,List.class);
		for (int j = 0; j < list.size(); j++) {
			LinkedHashMap<String,Object> m = list.get(j);
			Row bodyRow = sheet.createRow(j + startRow);
			for (int i = 0; i < fields.length; i++) {
				String key = fields[i];
				String k = key;
				Cell cell = bodyRow.createCell(i);
				if (key.indexOf(".") != -1) {
					k = key.split("\\.")[0];
				}
				Object obj = m.get(k);
				if (k.indexOf("Time") >= 0) {
					if (null != obj) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String d = sdf.format(new java.util.Date(StringUtils.toLong(obj)));
						obj = d;
					}
				}
				if (obj == null) {
					cell.setCellValue("");
				} else if (obj instanceof Map) {
					k = key.split("\\.")[1];
					if (null == ((Map) obj).get(k) || "".equals(((Map) obj).get(k))) {
						cell.setCellValue("");
					} else {
						if ("休".equals(((Map) obj).get(k).toString())) {
							cell.setCellStyle(getStyle(workBook,HSSFColor.PINK.index));
						} else if ("早".equals(((Map) obj).get(k).toString())) {
							cell.setCellStyle(getStyle(workBook,HSSFColor.LIME.index));
						} else if ("中".equals(((Map) obj).get(k).toString())) {
							cell.setCellStyle(getStyle(workBook,HSSFColor.LIGHT_YELLOW.index));
						} else if ("晚".equals(((Map) obj).get(k).toString())) {
							cell.setCellStyle(getStyle(workBook,HSSFColor.SKY_BLUE.index));
						} else if ("夜".equals(((Map) obj).get(k).toString())) {
							cell.setCellStyle(getStyle(workBook,HSSFColor.LIGHT_ORANGE.index));
						}
						cell.setCellValue(((Map) obj).get(k).toString());
					}
				} else {
					cell.setCellStyle(getStyle(workBook,HSSFColor.WHITE.index));
					cell.setCellValue(obj.toString());
				}
			}
		}
		try {
			sheet.flushRows();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Page<RfCustom> getList(RfCustom bean, Map<Integer, RfCustom> oneMap, Map<Integer, RfCustom> twoMap, Map<Integer, RfCustom> threeMap, Map<Integer, RfCustom> fourMap, Map<Integer, RfCustom> fiveMap, Map<Integer, RfCustom> sixMap, Map<Integer, RfCustom> sevenMap, Map<Integer, RfCustom> eightMap) {
		// map
		Map<String,String> map = new TreeMap<>();
		// 排班------------------
		if (bean.getMonth() != 0) {
			int lastday = getDaysByMonth(bean.getYear(),bean.getMonth());
			for (int date = 1; date <= lastday; date++) {
				int oneMapToNoonCount = (int) Math.floor(oneMap.size() / 2);
				int oneMapToDuskCount = (int) Math.floor(oneMap.size() / 2);
				int fourMapToNoonCount = (int) Math.floor(fourMap.size() / 2);
				for (Map.Entry<Integer,RfCustom> entry : oneMap.entrySet()) {
					RfCustom custom = entry.getValue();
					if (custom.getRestNum() == 0) {
						addMapToRest(custom,map,date);
					} else if (custom.getRestNum() == 1 && custom.getWorkNum() < 3) {
						addMapToDay(custom,map,date);
					} else if (custom.getRestNum() == 1 && custom.getWorkNum() < 4) {
						if (oneMapToNoonCount > 0) {
							oneMapToNoonCount--;
							addMapToNoon(custom,map,date);
						} else {// 继续早
							addMapToDay(custom,map,date);
						}
					} else if (custom.getRestNum() == 1 && custom.getWorkNum() == 4) {
						addMapToRest(custom,map,date);
					} else if (custom.getRestNum() == 2 && custom.getWorkNum() < 3) {
						addMapToNoon(custom,map,date);
					} else if (custom.getRestNum() == 2 && custom.getWorkNum() < 4) {
						if (oneMapToDuskCount > 0) {
							oneMapToDuskCount--;
							addMapToDusk(custom,map,date);
						} else {
							addMapToNoon(custom,map,date);
						}
					} else if (custom.getRestNum() == 2 && custom.getWorkNum() == 4) {
						addMapToRest(custom,map,date);
					} else if (custom.getRestNum() == 3 && custom.getWorkNum() < 4) {
						addMapToDusk(custom,map,date);
					} else if (custom.getRestNum() == 3 && custom.getWorkNum() == 4) {
						addMapToRest(custom,map,date);
					} else if (custom.getRestNum() == 4 && custom.getWorkNum() < 3) {
						addMapToDay(custom,map,date);
					} else if (custom.getRestNum() == 4 && custom.getWorkNum() < 4) {
						if (oneMapToNoonCount > 0) {
							oneMapToNoonCount--;
							addMapToNoon(custom,map,date);
						} else {// 继续早
							addMapToDay(custom,map,date);
						}
					} else if (custom.getRestNum() == 4 && custom.getWorkNum() == 4) {
						addMapToRest(custom,map,date);
					} else if (custom.getRestNum() == 5 && custom.getWorkNum() < 3) {
						addMapToNoon(custom,map,date);
					} else if (custom.getRestNum() == 5 && custom.getWorkNum() == 3) {
						addMapToRest(custom,map,date);
					} else if (custom.getRestNum() == 6 && custom.getWorkNum() < 6) {
						addMapToDusk(custom,map,date);
					}
				}
				for (Map.Entry<Integer,RfCustom> entry : twoMap.entrySet()) {
					RfCustom custom = entry.getValue();
					if (custom.getRestNum() == 0 && custom.getWorkNum() < 1) {// 转早
						addMapToDay(custom,map,date);
					} else if (custom.getRestNum() == 0 && custom.getWorkNum() == 1) {// 转休
						addMapToRest(custom,map,date);
					} else if (custom.getRestNum() == 1 && custom.getWorkNum() < 4) {// 转中
						addMapToNoon(custom,map,date);
					} else if (custom.getRestNum() == 1 && custom.getWorkNum() == 4) {// 转休
						addMapToRest(custom,map,date);
					} else if (custom.getRestNum() == 2 && custom.getWorkNum() < 4) {// 转晚
						addMapToDusk(custom,map,date);
					} else if (custom.getRestNum() == 2 && custom.getWorkNum() == 4) {// 转休
						addMapToRest(custom,map,date);
					} else if (custom.getRestNum() == 3 && custom.getWorkNum() < 4) {// 转早
						addMapToDay(custom,map,date);
					} else if (custom.getRestNum() == 3 && custom.getWorkNum() == 4) {// 转休
						addMapToRest(custom,map,date);
					} else if (custom.getRestNum() == 4 && custom.getWorkNum() < 4) {// 转中
						addMapToNoon(custom,map,date);
					} else if (custom.getRestNum() == 4 && custom.getWorkNum() == 4) {// 转休
						addMapToRest(custom,map,date);
					} else if (custom.getRestNum() == 5 && custom.getWorkNum() < 3) {// 转晚
						addMapToDusk(custom,map,date);
					} else if (custom.getRestNum() == 5 && custom.getWorkNum() == 3) {// 转休
						addMapToRest(custom,map,date);
					} else if (custom.getRestNum() == 6 && custom.getWorkNum() < 5) {// 转早
						addMapToDay(custom,map,date);
					}
				}
				for (Map.Entry<Integer,RfCustom> entry : threeMap.entrySet()) {
					RfCustom custom = entry.getValue();
					if (custom.getRestNum() == 0 && custom.getWorkNum() < 2) {// 转中
						addMapToNoon(custom,map,date);
					} else if (custom.getRestNum() == 0 && custom.getWorkNum() == 2) {// 转休
						addMapToRest(custom,map,date);
					} else if (custom.getRestNum() == 1 && custom.getWorkNum() < 4) {// 转晚
						addMapToDusk(custom,map,date);
					} else if (custom.getRestNum() == 1 && custom.getWorkNum() == 4) {// 转休
						addMapToRest(custom,map,date);
					} else if (custom.getRestNum() == 2 && custom.getWorkNum() < 4) {// 转早
						addMapToDay(custom,map,date);
					} else if (custom.getRestNum() == 2 && custom.getWorkNum() == 4) {// 转休
						addMapToRest(custom,map,date);
					} else if (custom.getRestNum() == 3 && custom.getWorkNum() < 4) {// 转中
						addMapToNoon(custom,map,date);
					} else if (custom.getRestNum() == 3 && custom.getWorkNum() == 4) {// 转休
						addMapToRest(custom,map,date);
					} else if (custom.getRestNum() == 4 && custom.getWorkNum() < 4) {// 转晚
						addMapToDusk(custom,map,date);
					} else if (custom.getRestNum() == 4 && custom.getWorkNum() == 4) {// 转休
						addMapToRest(custom,map,date);
					} else if (custom.getRestNum() == 5 && custom.getWorkNum() < 3) {// 转早
						addMapToDay(custom,map,date);
					} else if (custom.getRestNum() == 5 && custom.getWorkNum() == 3) {// 转休
						addMapToRest(custom,map,date);
					} else if (custom.getRestNum() == 6 && custom.getWorkNum() < 4) {// 转休
						addMapToNoon(custom,map,date);
					}
				}
				for (Map.Entry<Integer,RfCustom> entry : fourMap.entrySet()) {
					RfCustom custom = entry.getValue();
					if (custom.getRestNum() == 0 && custom.getWorkNum() < 3) {// 转晚
						addMapToDusk(custom,map,date);
					} else if (custom.getRestNum() == 0 && custom.getWorkNum() == 3) {// 转休
						addMapToRest(custom,map,date);
					} else if (custom.getRestNum() == 1 && custom.getWorkNum() < 4) {// 转早
						addMapToDay(custom,map,date);
					} else if (custom.getRestNum() == 1 && custom.getWorkNum() == 4) {// 转休
						addMapToRest(custom,map,date);
					} else if (custom.getRestNum() == 2 && custom.getWorkNum() < 4) {// 转中
						addMapToNoon(custom,map,date);
					} else if (custom.getRestNum() == 2 && custom.getWorkNum() == 4) {// 转休
						addMapToRest(custom,map,date);
					} else if (custom.getRestNum() == 3 && custom.getWorkNum() < 1) {// 平
						if (fourMapToNoonCount > 0) {
							fourMapToNoonCount--;
							addMapToNoon(custom,map,date);
						} else {
							addMapToDusk(custom,map,date);
						}
					} else if (custom.getRestNum() == 3 && custom.getWorkNum() < 4) {// 转晚
						addMapToDusk(custom,map,date);
					} else if (custom.getRestNum() == 3 && custom.getWorkNum() == 4) {// 转休
						addMapToRest(custom,map,date);
					} else if (custom.getRestNum() == 4 && custom.getWorkNum() < 4) {// 转早
						addMapToDay(custom,map,date);
					} else if (custom.getRestNum() == 4 && custom.getWorkNum() == 4) {// 转休
						addMapToRest(custom,map,date);
					} else if (custom.getRestNum() == 5 && custom.getWorkNum() < 3) {// 转中
						addMapToNoon(custom,map,date);
					} else if (custom.getRestNum() == 5 && custom.getWorkNum() == 3) {// 转休
						addMapToRest(custom,map,date);
					} else if (custom.getRestNum() == 6 && custom.getWorkNum() < 3) {// 转休
						if (fourMapToNoonCount > 0) {
							fourMapToNoonCount--;
							addMapToNoon(custom,map,date);
						} else {
							addMapToDusk(custom,map,date);
						}
					}
				}
				for (Map.Entry<Integer,RfCustom> entry : fiveMap.entrySet()) {
					RfCustom custom = entry.getValue();
					if (date== 1 || date== 6 || date== 11 || date== 16 || date== 21 || date== 25) {//转休
						addMapToRest(custom,map,date);
					}else{
						addMapToNight(custom,map,date);
					}
				}
				for (Map.Entry<Integer,RfCustom> entry : sixMap.entrySet()) {
					RfCustom custom = entry.getValue();
					if (date== 2 || date== 7 || date== 12 || date== 17 || date== 22 || date== 26) {//转休
						addMapToRest(custom,map,date);
					}else{
						addMapToNight(custom,map,date);
					}
				}
				for (Map.Entry<Integer,RfCustom> entry : sevenMap.entrySet()) {
					RfCustom custom = entry.getValue();
					if (date== 3 || date== 8 || date== 13 || date== 18 || date== 23 || date== 27) {//转休
						addMapToRest(custom,map,date);
					}else{
						addMapToNight(custom,map,date);
					}
				}
				for (Map.Entry<Integer,RfCustom> entry : eightMap.entrySet()) {
					RfCustom custom = entry.getValue();
					if (date== 4 || date== 9 || date== 14 || date== 19 || date== 24 || date== 28) {//转休
						addMapToRest(custom,map,date);
					}else{
						addMapToNight(custom,map,date);
					}
				}
			}
		}
		Page<RfCustom> customList = new Page<RfCustom>();
		Map<Integer,Map<String,Object>> customMap = new TreeMap<>();
		for (Map.Entry<String,String> entry : map.entrySet()) {
			Integer customId = Integer.parseInt(entry.getKey().substring(0,entry.getKey().indexOf(";")));
			String dateStr = entry.getKey().substring(entry.getKey().indexOf(";") + 1,entry.getKey().length());
			String statusStr = entry.getValue();
			Map<String,Object> tmpMap = new TreeMap<>();
			if (customMap.get(customId) != null) {
				tmpMap = customMap.get(customId);
			}
			tmpMap.put(dateStr,statusStr);
			customMap.put(customId,tmpMap);
		}
		// 排序组装数据
		addCustomList(oneMap,customList,customMap);
		addCustomList(twoMap,customList,customMap);
		addCustomList(threeMap,customList,customMap);
		addCustomList(fourMap,customList,customMap);
		addCustomList(fiveMap,customList,customMap);
		addCustomList(sixMap,customList,customMap);
		addCustomList(sevenMap,customList,customMap);
		addCustomList(eightMap,customList,customMap);
		return customList;
	}

	private void addCustomList(Map<Integer,RfCustom> map,Page<RfCustom> customList,Map<Integer,Map<String,Object>> customMap) {
		for (Map.Entry<Integer,Map<String,Object>> entry : customMap.entrySet()) {
			if (map.get(entry.getKey()) != null) {
				RfCustom custom = customManageService.get(entry.getKey());
				custom.setMap(entry.getValue());
				customList.add(custom);
			}
		}
	}
	// 新增班次到夜班
	private static void addMapToNight(RfCustom custom,Map<String,String> map,int date) {
		custom.setWorkNum(custom.getWorkNum() + 1);
		String checkDateStr = custom.getYear() + "-" + custom.getMonth() + "-" + date;
		Date checkDate =  DateUtils.parseDate(checkDateStr);
		if(custom.getEnableTime().compareTo(checkDate)==0){//按类型
			doPutMapByType(custom,map,date,"夜");
		}else if(custom.getEnableTime().compareTo(checkDate)==1){//留空白
			doPutMapByType(custom,map,date,"");
		}else if(custom.getEnableTime().compareTo(checkDate)==-1){
			if(custom.getDisableTime()!=null){
				if(custom.getDisableTime().compareTo(checkDate)==0){//留空白
					doPutMapByType(custom,map,date,"");
				}else if(custom.getDisableTime().compareTo(checkDate)==1){//按类型 
					doPutMapByType(custom,map,date,"夜");
				}else if(custom.getDisableTime().compareTo(checkDate)==-1){//留空白
					doPutMapByType(custom,map,date,"");
				}
			}else{//按类型
				doPutMapByType(custom,map,date,"夜");
			}
		}
	}

	// 新增班次到晚班
	private static void addMapToDusk(RfCustom custom,Map<String,String> map,int date) {
		custom.setWorkNum(custom.getWorkNum() + 1);
		String checkDateStr = custom.getYear() + "-" + custom.getMonth() + "-" + date;
		Date checkDate =  DateUtils.parseDate(checkDateStr);
		if(custom.getEnableTime().compareTo(checkDate)==0){//按类型
			doPutMapByType(custom,map,date,"晚");
		}else if(custom.getEnableTime().compareTo(checkDate)==1){//留空白
			doPutMapByType(custom,map,date,"");
		}else if(custom.getEnableTime().compareTo(checkDate)==-1){
			if(custom.getDisableTime()!=null){
				if(custom.getDisableTime().compareTo(checkDate)==0){//留空白
					doPutMapByType(custom,map,date,"");
				}else if(custom.getDisableTime().compareTo(checkDate)==1){//按类型 
					doPutMapByType(custom,map,date,"晚");
				}else if(custom.getDisableTime().compareTo(checkDate)==-1){//留空白
					doPutMapByType(custom,map,date,"");
				}
			}else{//按类型
				doPutMapByType(custom,map,date,"晚");
			}
		}
	}

	// 新增班次到中班
	private static void addMapToNoon(RfCustom custom,Map<String,String> map,int date) {
		custom.setWorkNum(custom.getWorkNum() + 1);
		String checkDateStr = custom.getYear() + "-" + custom.getMonth() + "-" + date;
		Date checkDate =  DateUtils.parseDate(checkDateStr);
		if(custom.getEnableTime().compareTo(checkDate)==0){//按类型
			doPutMapByType(custom,map,date,"中");
		}else if(custom.getEnableTime().compareTo(checkDate)==1){//留空白
			doPutMapByType(custom,map,date,"");
		}else if(custom.getEnableTime().compareTo(checkDate)==-1){
			if(custom.getDisableTime()!=null){
				if(custom.getDisableTime().compareTo(checkDate)==0){//留空白
					doPutMapByType(custom,map,date,"");
				}else if(custom.getDisableTime().compareTo(checkDate)==1){//按类型 
					doPutMapByType(custom,map,date,"中");
				}else if(custom.getDisableTime().compareTo(checkDate)==-1){//留空白
					doPutMapByType(custom,map,date,"");
				}
			}else{//按类型
				doPutMapByType(custom,map,date,"中");
			}
		}
	}

	// 新增班次到早班
	private static void addMapToDay(RfCustom custom,Map<String,String> map,int date) {
		custom.setWorkNum(custom.getWorkNum() + 1);
		String checkDateStr = custom.getYear() + "-" + custom.getMonth() + "-" + date;
		Date checkDate =  DateUtils.parseDate(checkDateStr);
		if(custom.getEnableTime().compareTo(checkDate)==0){//按类型
			doPutMapByType(custom,map,date,"早");
		}else if(custom.getEnableTime().compareTo(checkDate)==1){//留空白
			doPutMapByType(custom,map,date,"");
		}else if(custom.getEnableTime().compareTo(checkDate)==-1){
			if(custom.getDisableTime()!=null){
				if(custom.getDisableTime().compareTo(checkDate)==0){//留空白
					doPutMapByType(custom,map,date,"");
				}else if(custom.getDisableTime().compareTo(checkDate)==1){//按类型 
					doPutMapByType(custom,map,date,"早");
				}else if(custom.getDisableTime().compareTo(checkDate)==-1){//留空白
					doPutMapByType(custom,map,date,"");
				}
			}else{//按类型
				doPutMapByType(custom,map,date,"早");
			}
		}
	}
	
	// 新增班次到休息
	private static void addMapToRest(RfCustom custom,Map<String,String> map,int date) {
		if (date != 1) {
			custom.setWorkNum(0);
		}
		custom.setRestNum(custom.getRestNum() + 1);
		String checkDateStr = custom.getYear() + "-" + custom.getMonth() + "-" + date;
		Date checkDate =  DateUtils.parseDate(checkDateStr);
		if(custom.getEnableTime().compareTo(checkDate)==0){//按类型
			doPutMapByType(custom,map,date,"休");
		}else if(custom.getEnableTime().compareTo(checkDate)==1){//留空白
			doPutMapByType(custom,map,date,"");
		}else if(custom.getEnableTime().compareTo(checkDate)==-1){
			if(custom.getDisableTime()!=null){
				if(custom.getDisableTime().compareTo(checkDate)==0){//留空白
					doPutMapByType(custom,map,date,"");
				}else if(custom.getDisableTime().compareTo(checkDate)==1){//按类型 
					doPutMapByType(custom,map,date,"休");
				}else if(custom.getDisableTime().compareTo(checkDate)==-1){//留空白
					doPutMapByType(custom,map,date,"");
				}
			}else{//按类型
				doPutMapByType(custom,map,date,"休");
			}
		}
	}

	private static void doPutMapByType(RfCustom custom, Map<String, String> map, int date,String type) {
		map.put(custom.getCustomId() + ";" + date,type);
	}
	public static int getDaysByMonth(Integer year,Integer month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,year);
		cal.set(Calendar.MONTH,month-1);
		cal.set(Calendar.DATE,1);
		cal.roll(Calendar.DATE,-1);
		return cal.get(Calendar.DATE);
	}
	public static String getLastDayByMonth(Integer year,Integer month) {  
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }
	
	public static String getFirstDayByMonth(Integer year,Integer month) {  
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DATE,1);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }
	
	public static Integer getDayByDate(Date date) {  
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }
	
	public static Integer getMonthByDate(Date date) {  
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
        return cal.get(Calendar.MONTH)+1;
    }
	
	public static Integer getYearByDate(Date date) {  
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }
}