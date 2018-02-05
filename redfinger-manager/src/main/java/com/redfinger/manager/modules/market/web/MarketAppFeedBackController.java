package com.redfinger.manager.modules.market.web;

import java.io.IOException;
import java.util.Date;
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
import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.AppFeedback;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.ExcelUtils;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.modules.base.service.ClassService;
import com.redfinger.manager.modules.game.service.GameApkService;
import com.redfinger.manager.modules.market.service.MarketAppFeedBackService;
import com.redfinger.manager.modules.member.service.UserService;

@Controller
@RequestMapping(value = "market/feedback")
public class MarketAppFeedBackController extends BaseController {

	@Autowired
	MarketAppFeedBackService feedbackService;
	@Autowired
	ClassService classService;
	@Autowired
	UserService userService;
	@Autowired
	GameApkService apkService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<AppFeedback> list(HttpServletRequest request, HttpServletResponse response, Model model, AppFeedback bean, String begin, String end) throws Exception {
		Date beginTime = null;
		Date endTime = null;
		if (StringUtils.isNotBlank(begin)) {
			beginTime = DateUtils.parseDate(begin);
		}
		if (StringUtils.isNotBlank(end)) {
			endTime = DateUtils.addDays(DateUtils.parseDate(end), 1);
		}
		List<AppFeedback> list = feedbackService.initQuery(bean).andLike("creater", bean.getCreater()).andLike("content", bean.getContent()).andEqualTo(AppFeedback.FD_STATUS, "1")
				.andGreaterThanOrEqualTo("createTime", beginTime).andLessThan("createTime", endTime).addOrderClause("reorder", " asc").addOrderForce(bean.getSort(), bean.getOrder()).pageAll(bean.getPage(), bean.getRows());
		for (AppFeedback appFeedBack : list) {
			List<String> nameList = Lists.newArrayList();
			String[] ids = appFeedBack.getClassIds().split(",");
			for (int i = 0; i < ids.length; i++) {
				nameList.add(classService.load(Integer.parseInt(ids[i])).getClassName());
			}
			appFeedBack.getMap().put("className", nameList);
			appFeedBack.getMap().put("userMobilePhone",
					userService.load(appFeedBack.getUserId()).getUserMobilePhone());
			appFeedBack.getMap().put("appApk", apkService.load(appFeedBack.getApkId()).getName());
		}
		PageInfo<AppFeedback> pageInfo = new PageInfo<AppFeedback>(list);

		return pageInfo;
	}

	@RequestMapping(value = "export")
	public String export(HttpServletRequest request, HttpServletResponse response, Model model, String exportDatas, String exportHead, String exportField, String exportName, AppFeedback bean,
			String begin, String end, String userMobilePhone, String userEmail) throws Exception {
		exportHead = StringUtils.removeEnd(exportHead, ",");
		exportField = StringUtils.removeEnd(exportField, ",");
		response.setContentType("application/binary;charset=UTF-8");
		ServletOutputStream outputStream = response.getOutputStream();
		response.setHeader("Content-disposition", "attachment;filename=" + ExcelUtils.getFileName(request, exportName) + ".xls");
		Workbook workBook = new SXSSFWorkbook();
		SXSSFSheet sheet = (SXSSFSheet) workBook.createSheet("Sheet1");
		CellStyle headStyle = ExcelUtils.getHeadStyle(workBook);
		CellStyle bodyStyle = ExcelUtils.getBodyStyle(workBook);
		ExcelUtils.insertHead(exportHead.split(","), sheet, headStyle);
		boolean keep = true;
		while (keep) {
			PageInfo<AppFeedback> pageInfo = this.list(request, response, model, bean, begin, end);
			List<AppFeedback> list = pageInfo.getList();
			if (!Collections3.isEmpty(list)) {
				ExcelUtils.insertPageBody(JsonUtils.ObjectToString(list), sheet, bodyStyle, exportField.split(","), pageInfo.getStartRow());
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

}
