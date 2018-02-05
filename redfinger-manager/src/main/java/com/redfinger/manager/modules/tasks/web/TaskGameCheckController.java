package com.redfinger.manager.modules.tasks.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.TaskGameCheck;
import com.redfinger.manager.common.domain.TaskSystem;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.DictHelper;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.ExcelUtils;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.modules.tasks.service.TaskGameCheckService;
import com.redfinger.manager.modules.tasks.service.TaskSystemService;

/**
 * 游戏下载任务审核
 * 
 * @ClassName: TaskGameCheckController
 * @author tluo
 * @date 2016年5月31日 下午2:40:38
 */
@Controller
@RequestMapping(value = "/task/gameDownloadCheck")
public class TaskGameCheckController extends BaseController {
	@Autowired
	TaskGameCheckService service;
	@Autowired
	TaskSystemService taskSystemService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<TaskGameCheck> list(HttpServletRequest request, HttpServletResponse response, Model model,
			TaskGameCheck bean, String begin, String end) throws Exception {
		Date beginTime = null;
		Date endTime = null;
		if (StringUtils.isNotBlank(begin)) {
			beginTime = DateUtils.parseDate(begin);
		}
		if (StringUtils.isNotBlank(end)) {
			endTime = DateUtils.addDays(DateUtils.parseDate(end), 1);
		}
		List<TaskGameCheck> list = service.initQuery(bean).andEqualTo("userMobilePhone", bean.getUserMobilePhone())
				.andLike("checkGameAccount", bean.getCheckGameAccount()).andEqualTo("taskId", bean.getTaskId())
				.andEqualTo("checkStatus", bean.getCheckStatus()).andEqualTo("checkPerson", bean.getCheckPerson())
				.andGreaterThanOrEqualTo("createTime", beginTime).andLessThan("createTime", endTime)
				.addOrderForce(bean.getSort(), bean.getOrder()).pageAll(bean.getPage(), bean.getRows());

		// 查出所有有效的游戏下载任务
		List<TaskSystem> task_list = taskSystemService.initQuery().andLike("taskSer", "GameDownload").findAll();
		Map<Integer, String> task_map = new HashMap<Integer, String>();
		for (TaskSystem task : task_list) {
			task_map.put(task.getId(), task.getName());
		}
		for (TaskGameCheck check : list) {
			check.getMap().put("taskName",
					task_map.get(check.getTaskId()) == null ? "任务不存在" : task_map.get(check.getTaskId()));
		}

		PageInfo<TaskGameCheck> pageInfo = new PageInfo<TaskGameCheck>(list);
		return pageInfo;
	}

	// 打开帐号审核提交界面
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	// 批量审核帐号
	@RequestMapping(value = "accountCheck")
	public void accountCheck(HttpServletRequest request, HttpServletResponse response, Model model, Integer taskId,
			String accounts) throws Exception {
		if (taskId == null) {
			throw new BusinessException("任务为空，请选择相对应的任务！");
		}
		if (StringUtils.isEmpty(accounts)) {
			throw new BusinessException("审核帐号为空，请输入审核帐号，多个帐号以换行分隔。");
		}
		List<String> err_list = new ArrayList<String>();
		accounts = accounts.replaceAll(" ", "");
		String[] accountArray = StringUtils.split(accounts, "\r\n");
		for (int i = 0; i < accountArray.length; i++) {
			String account = accountArray[i];
			try {
				service.accountCheckPass(request, taskId, account);
			} catch (Exception e) {
				// 审核失败的帐号保存。
				err_list.add(account);
			}
		}
		if (err_list.size() != 0) {
			String messager = " ";
			for (String s : err_list) {
				messager += s + " ";
			}
			int len = accountArray.length - err_list.size();
			throw new BusinessException("审核帐号" + len + "个成功,失败帐号" + messager + "，请核实失败的帐号信息再次提交失败的帐号。");
		}
	}

	/**
	 * 获取任务名称下拉框
	 * 
	 * @Title: getTask
	 * @return List<TaskSystem> 返回类型
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getTask")
	public List<TaskSystem> getTask() {
		return taskSystemService.initQuery().andLike("taskSer", "GameDownload").findStopTrue();
	}

	/**
	 * 审核通过
	 * 
	 * @Title: checkPass
	 * @return void 返回类型
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @throws Exception
	 */
	@RequestMapping(value = "checkPass")
	public void checkPass(HttpServletRequest request, HttpServletResponse response, Model model, Integer id)
			throws Exception {
		service.checkPass(request, id);

	}

	/**
	 * 批量审核
	 * 
	 * @Title: BatchCheckPass
	 * @return void 返回类型
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @throws Exception
	 */
	@RequestMapping(value = "batchCheckPass")
	public void BatchCheckPass(HttpServletRequest request, HttpServletResponse response, Model model, String ids)
			throws Exception {
		service.batchCheckPass(request, ids);

	}

	/**
	 * 帐号导出
	 * 
	 * @Title: export
	 * @return String 返回类型
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @param exportHead
	 * @param exportField
	 * @param exportName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "export")
	public String export(HttpServletRequest request, HttpServletResponse response, Model model, TaskGameCheck bean,
			String exportHead, String exportField, String exportName, String begin, String end) throws Exception {
		exportHead = StringUtils.removeEnd(exportHead, ",");
		exportField = StringUtils.removeEnd(exportField, ",");
		response.setContentType("application/binary;charset=UTF-8");
		ServletOutputStream outputStream = response.getOutputStream();
		response.setHeader("Content-disposition", "attachment;filename=" + ExcelUtils.getFileName(request, exportName)
				+ ".xls");
		Workbook workBook = new SXSSFWorkbook();
		SXSSFSheet sheet = (SXSSFSheet) workBook.createSheet("Sheet1");
		CellStyle headStyle = ExcelUtils.getHeadStyle(workBook);
		CellStyle bodyStyle = ExcelUtils.getBodyStyle(workBook);
		ExcelUtils.insertHead(exportHead.split(","), sheet, headStyle);
		boolean keep = true;
		int page = 1;
		while (keep) {
			bean.setPage(page);
			bean.setRows(1000);
			PageInfo<TaskGameCheck> pageInfo = this.list(request, response, model, bean,  begin, end);
			List<TaskGameCheck> list = pageInfo.getList();
			if (!Collections3.isEmpty(list)) {
				for (TaskGameCheck TaskGameCheck : list) {
					TaskGameCheck.setCheckStatus(DictHelper.getLabel("task_gamedownload_check.check_status", TaskGameCheck.getCheckStatus()));
				}
				ExcelUtils.insertPageBody(JsonUtils.ObjectToString(list), sheet, bodyStyle, exportField.split(","),
						pageInfo.getStartRow());
				if (pageInfo.isHasNextPage()) {
					page++;
					continue;
				}
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
