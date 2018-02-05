package com.redfinger.manager.modules.log.web;

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
import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.AppLogSearch;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.ExcelUtils;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.modules.log.service.LogSearchService;
import com.redfinger.manager.modules.member.service.UserService;

@Controller
@RequestMapping(value = "/log/search")
public class LogSearchController extends BaseController {

	@Autowired
	LogSearchService searchService;
	@Autowired
	UserService userService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<AppLogSearch> list(HttpServletRequest request,
			HttpServletResponse response, Model model, AppLogSearch bean,
			String begin, String end, String userMobilePhone, String userEmail) throws Exception {
		Date beginTime = null;
		Date endTime = null;
		List<Integer> userId = Lists.newArrayList();
		if (StringUtils.isNotBlank(begin)) {
			beginTime = DateUtils.parseDate(begin);
		}
		if (StringUtils.isNotBlank(end)) {
			endTime = DateUtils.addDays(DateUtils.parseDate(end), 1);
		}
		if (StringUtils.isNotBlank(userMobilePhone)
				|| StringUtils.isNotBlank(userEmail)) {
			List<RfUser> l_user = userService.initQuery()
					.andLike("userEmail", userEmail)
					.andLike("userMobilePhone", userMobilePhone).findStopTrue();
			if (l_user.size() != 0) {
				for (RfUser user : l_user) {
					userId.add(user.getUserId());
				}
			} else
				userId.add(0);
		}
		List<AppLogSearch> list = searchService.initQuery(bean)
				.andIn("userId", userId)
				.andLike("keywords", bean.getKeywords())
				.andGreaterThanOrEqualTo("createTime", beginTime)
				.andLessThan("createTime", endTime)
				.addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageAll(bean.getPage(), bean.getRows());

		for (AppLogSearch log : list) {
			log.getMap().put(
					"userEmail",
					log.getUserId() == null ? "" : userService.load(
							log.getUserId()).getUserEmail());
			log.getMap().put(
					"userMobilePhone",
					log.getUserId() == null ? "" : userService.load(
							log.getUserId()).getUserMobilePhone());
		}
		PageInfo<AppLogSearch> pageInfo = new PageInfo<AppLogSearch>(list);
		return pageInfo;

	}
	
	@RequestMapping(value = "export")
	public String export(HttpServletRequest request,
			HttpServletResponse response, Model model, String exportDatas,
			String exportHead, String exportField, String exportName,
			AppLogSearch bean, String begin, String end,String userMobilePhone,String userEmail) throws Exception {
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
			PageInfo<AppLogSearch> pageInfo = this.list(request, response, model, bean, begin, end,userMobilePhone,userEmail);
			List<AppLogSearch> list=pageInfo.getList();
			if (!Collections3.isEmpty(list)) {
				ExcelUtils.insertPageBody(JsonUtils.ObjectToString(list),sheet,bodyStyle,exportField.split(","),pageInfo.getStartRow());
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
