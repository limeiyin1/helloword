package com.redfinger.manager.modules.batch.web;

import java.io.IOException;
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
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.TkBatchExecute;
import com.redfinger.manager.common.domain.TkBatchTask;
import com.redfinger.manager.common.helper.DictHelper;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.ExcelUtils;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.modules.batch.service.BatchExecuteService;
import com.redfinger.manager.modules.batch.service.BatchTaskService;
import com.redfinger.manager.modules.member.service.UserService;

@Controller
@RequestMapping(value="/tkbatch/execute")
public class BatchExecuteController extends BaseController {

	@Autowired
	private BatchTaskService batchService;
	@Autowired
	private BatchExecuteService executeService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
//		List<TkBatchTask> batchList = batchService.initQuery().findStopTrue();
//		model.addAttribute("batchList", batchList);
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<TkBatchExecute> list(HttpServletRequest request, HttpServletResponse response, Model model, TkBatchExecute bean, String userMobilePhone,String batchTitle) throws Exception {
		if (StringUtils.isNotBlank(userMobilePhone)) {
			List<RfUser> userList = userService.initQuery().andEqualTo("userMobilePhone", userMobilePhone).singleStopTrue();
			if (userList != null && userList.size() > 0)
				bean.setUserId(userList.get(0).getUserId());
			else
				bean.setUserId(-1);
		}
		
		List<Integer> batchIdList = Lists.newArrayList();
		if(StringUtils.isNotBlank(batchTitle)){
			List<TkBatchTask> list = batchService.initQuery().andLike("batchTitle", batchTitle).findAll();
			for (TkBatchTask tkBatchTask : list) {
				batchIdList.add(tkBatchTask.getBatchId());
			}
			if(Collections3.isEmpty(batchIdList)){
				batchIdList.add(-1);
			}
		}
		
		
		List<TkBatchExecute> list = executeService.initQuery(bean)
				.andIn("batchId", batchIdList )
//				.andEqualTo("batchId", bean.getBatchId())
				.andEqualTo("padCode", bean.getPadCode())
				.andEqualTo("userId", bean.getUserId())
				.andEqualTo("operateType", bean.getOperateType())
				.andEqualTo("userId", bean.getUserId())
				.andEqualTo("executeStatus", bean.getExecuteStatus())
				.addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		for (TkBatchExecute execute : list) {
			execute.getMap().put("batchTitle", batchService.get(execute.getBatchId()).getBatchTitle());
			
			if(execute.getUserId() != null){
				RfUser user = userService.initQuery().get(execute.getUserId());
				if(user != null){
					String phone = user.getUserMobilePhone();
					execute.getMap().put("userMobilePhone",phone == null ? user.getUserEmail() : phone );
				}
			}
		}
		PageInfo<TkBatchExecute> pageInfo = new PageInfo<TkBatchExecute>(list);
		return pageInfo;
	}
	
	
	//会员导出
	@RequestMapping(value="export")
    public String export(HttpServletRequest request,HttpServletResponse response,Model model,TkBatchExecute bean,String exportHead, String exportField, String exportName, String userMobilePhone,String batchTitle)throws Exception{
		exportHead=StringUtils.removeEnd(exportHead, ",");
	    exportField=StringUtils.removeEnd(exportField, ",");
	    response.setContentType("application/binary;charset=UTF-8");
	    ServletOutputStream outputStream=response.getOutputStream();
	    response.setHeader("Content-disposition", "attachment;filename="+ExcelUtils.getFileName(request, exportName)+".xls");
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
			PageInfo<TkBatchExecute> pageInfo = this.list(request, response, model, bean, userMobilePhone,batchTitle);
			List<TkBatchExecute> list = pageInfo.getList();
			if (!Collections3.isEmpty(list)) {
				for (TkBatchExecute tkBatchExecute : list) {
					if(StringUtils.isNotBlank(tkBatchExecute.getOperateType())){
						tkBatchExecute.setOperateType(DictHelper.getLabel("tk_batch_task.operate_type", tkBatchExecute.getOperateType()));
					}
					
					if(StringUtils.isNotBlank(tkBatchExecute.getExecuteStatus())){
						tkBatchExecute.setExecuteStatus(DictHelper.getLabel("tk_batch_execute.execute_status", tkBatchExecute.getExecuteStatus()));
					}
					
					if(StringUtils.isNotBlank(tkBatchExecute.getEnableStatus())){
						tkBatchExecute.setEnableStatus(DictHelper.getLabel("global.enable_status", tkBatchExecute.getEnableStatus()));
					}
				}
				ExcelUtils.insertPageBody(JsonUtils.ObjectToString(list), sheet, bodyStyle, exportField.split(","), pageInfo.getStartRow());
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
