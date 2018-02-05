package com.redfinger.manager.modules.gather.web;

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
import com.redfinger.manager.common.gather.domain.RfGatherFieldDefine;
import com.redfinger.manager.modules.gather.service.GatherFieldDefineService;

@Controller
@RequestMapping(value = "/gather/define")
public class GatherFieldDefineController extends BaseController {
	
	@Autowired
	GatherFieldDefineService service;
	
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfGatherFieldDefine> list(HttpServletRequest request, HttpServletResponse response, Model model,RfGatherFieldDefine bean) throws Exception {
		List<RfGatherFieldDefine> list = service.initQuery()
				.addOrderClause("fieldCode", "asc")
				.pageAll(bean.getPage(), bean.getRows());
		for(RfGatherFieldDefine li : list){
			li.setFieldName(li.getFieldCode());
		}
		PageInfo<RfGatherFieldDefine> pageInfo = new PageInfo<RfGatherFieldDefine>(list);
		return pageInfo;
	}
	
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfGatherFieldDefine bean) throws Exception {
		if (bean.getFieldCode() != null) {
			bean = service.get(bean.getFieldCode());
			model.addAttribute("bean", bean);
		}
		return this.toPage(request, response, model);
	}
	
	
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfGatherFieldDefine bean) throws Exception {
		if(null!=bean.getFieldCode()){
			List<RfGatherFieldDefine> matchs = service.initQuery().andEqualTo("fieldCode", bean.getFieldCode()).singleDelTrue();
			if(null != matchs && matchs.size()>0){//如果有传id过来并且id已经使用了的，就改为updata
				bean.setFieldCode(matchs.get(0).getFieldCode());
				update(request, response, model, bean);
				return;
			}
		}
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfGatherFieldDefine bean) throws Exception {
		service.update(request, bean);
	}
	
	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfGatherFieldDefine bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfGatherFieldDefine bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfGatherFieldDefine bean) throws Exception {
		service.remove(request, bean);
	}
	
	
}