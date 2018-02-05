package com.redfinger.manager.common.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class ExcelUtils {

	public static String getFileName(HttpServletRequest request, String pFileName) throws Exception {
		pFileName = StringUtils.remove(pFileName, " ");
		String filename = null;
		String agent = request.getHeader("USER-AGENT");
		if (null != agent) {
			if (-1 != agent.indexOf("Firefox")) {// Firefox
				filename = "=?UTF-8?B?" + (new String(org.apache.commons.codec.binary.Base64.encodeBase64(pFileName.getBytes("UTF-8")))) + "?=";
			} else if (-1 != agent.indexOf("Chrome")) {// Chrome
				filename = new String(pFileName.getBytes(), "ISO8859-1");
			} else {// IE7+
				filename = java.net.URLEncoder.encode(pFileName, "UTF-8");
				filename = StringUtils.replace(filename, "+", "%20");// 替换空格
			}
		} else {
			filename = pFileName;
		}
		return filename;
	}

	public static void exportExcel(String json, String[] titles, String[] fields, OutputStream outputStream) {
		Workbook workBook = new SXSSFWorkbook();
		SXSSFSheet sheet = (SXSSFSheet) workBook.createSheet("Sheet1");
		CellStyle headStyle = ExcelUtils.getHeadStyle(workBook);
		CellStyle bodyStyle = ExcelUtils.getBodyStyle(workBook);
		// 构建表头
		insertHead(titles, sheet, headStyle);
		// 构建表体
		insertBody(json, sheet, bodyStyle, fields);
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
	}

	public static void insertHead(String[] titles, SXSSFSheet sheet, CellStyle headStyle) {
		Row headRow = sheet.createRow(0);
		Cell cell = null;
		for (int i = 0; i < titles.length; i++) {
			cell = headRow.createCell(i);
			cell.setCellStyle(headStyle);
			cell.setCellValue(titles[i]);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void insertBody(String json, SXSSFSheet sheet, CellStyle bodyStyle, String[] fields) {
		List<LinkedHashMap<String, Object>> list = JsonUtils.stringToObject(json, List.class);
		
		for (int j = 0; j < list.size(); j++) {
			LinkedHashMap<String, Object> m = list.get(j);
			Row bodyRow = sheet.createRow(j + 1);
			for (int i = 0; i < fields.length; i++) {
				String key = fields[i];
				String k = key;
				Cell cell  = bodyRow.createCell(i);
				cell.setCellStyle(bodyStyle);
				if (key.indexOf(".") != -1) {
					k = key.split("\\.")[0];
				}
				Object obj = m.get(k);
				if (obj == null) {
					cell.setCellValue("");
				} else if (obj instanceof Map) {
					k = key.split("\\.")[1];
					cell.setCellValue(((Map) obj).get(k).toString());
				} else {
					cell.setCellValue(obj.toString());
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void insertPageBody(String json, SXSSFSheet sheet, CellStyle bodyStyle, String[] fields, int startRow) {
		List<LinkedHashMap<String, Object>> list = JsonUtils.stringToObject(json, List.class);
		for (int j = 0; j < list.size(); j++) {
			LinkedHashMap<String, Object> m = list.get(j);
			Row bodyRow = sheet.createRow(j + startRow);
			for (int i = 0; i < fields.length; i++) {
				String key = fields[i];
				String k = key;
				Cell cell = bodyRow.createCell(i);
				cell.setCellStyle(bodyStyle);
				if (key.indexOf(".") != -1) {
					k = key.split("\\.")[0];
				}
				Object obj = m.get(k);
				if(k.indexOf("Time")>=0){
				if(null!=obj){
					   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					   String d=sdf.format(new java.util.Date(StringUtils.toLong(obj))); 
					   obj=d;
				}
				}
				if (obj == null) {
					cell.setCellValue("");
				} else if (obj instanceof Map) {
					k = key.split("\\.")[1];
					if (null == ((Map) obj).get(k) || "".equals(((Map) obj).get(k))) {
						cell.setCellValue("");
					} else {
						cell.setCellValue(((Map) obj).get(k).toString());
					}
				} else {
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

	/**
	 * 设置表头的单元格样式
	 * 
	 * @return
	 */
	public static CellStyle getHeadStyle(Workbook wb) {
		// 创建单元格样式
		CellStyle cellStyle = wb.createCellStyle();
		// 设置单元格的背景颜色为淡蓝色
		cellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		// 设置单元格居中对齐
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		// 设置单元格垂直居中对齐
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		// 创建单元格内容显示不下时自动换行
		cellStyle.setWrapText(true);
		// 设置单元格字体样式
		Font font = wb.createFont();
		// 设置字体加粗
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
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

	/**
	 * 设置表体的单元格样式
	 * 
	 * @return
	 */
	public static CellStyle getBodyStyle(Workbook wb) {
		// 创建单元格样式
		CellStyle cellStyle = wb.createCellStyle();
		// 设置单元格居中对齐
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		// 设置单元格垂直居中对齐
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		// 创建单元格内容显示不下时自动换行
		cellStyle.setWrapText(true);
		// 设置单元格字体样式
		Font font = wb.createFont();
		// 设置字体加粗
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
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
}
