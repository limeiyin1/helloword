package com.redfinger.manager.modules.batch.web;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.RfExport;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.modules.batch.service.ExportService;


@Controller
@RequestMapping(value = "/tkbatch/export")
public class ExportController extends BaseController {

	@Autowired
	private  ExportService service;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfExport> list(HttpServletRequest request, HttpServletResponse response, Model model, RfExport bean,String createTime2) throws Exception {
		List<RfExport> list = service.initQuery(bean)
				.andEqualTo("type", bean.getType())
				.andEqualTo("exportStatus", bean.getExportStatus())
				.andLike("creater", bean.getCreater())
				.andLike("taskName", bean.getTaskName())
				.andGreaterThanOrEqualTo("createTime", DateUtils.parseDate(createTime2))
				.andLessThan("createTime", DateUtils.parseDateAddOneDay(createTime2))//这里之前不知为何用多出用到的方法是parseAddOneDate，现改回parseDate
				.addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		for(RfExport rfExport : list){
			if(!"2".equals(rfExport.getExportStatus())){
				rfExport.setUrl(null);
			}
		}
				
		PageInfo<RfExport> pageInfo = new PageInfo<RfExport>(list);
		return pageInfo;
	}
	
	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfExport bean)
			throws Exception {
		
		String userId = SessionUtils.getCurrentUserId(request);
		RfExport export = new RfExport();
		String[] idArray=StringUtils.split(bean.getIds(),",");
		List<String> paths = new ArrayList<>();
		for(String idStr:idArray){
			export = service.get(Integer.parseInt(idStr));
			if(!userId.equals(export.getCreater())){
				throw new BusinessException("仅可删除本账户导出文件");
			}
			paths.add(export.getPath());
		}
		service.delete(request, bean);
		for(String path : paths){//删除文件
			File file = new File(path);
			if(file.exists()){
				file.delete();
			}
		}
		
	}
	
 
}
