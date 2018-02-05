package com.redfinger.manager.modules.stat.web;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.ToolsPad;
import com.redfinger.manager.modules.stat.service.ToolsPadService;

@Controller
@RequestMapping(value = "/stat/toolsPad")
public class ToolsPadController extends BaseController {
	@Autowired
	ToolsPadService service;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public List<ToolsPad> list(HttpServletRequest request, HttpServletResponse response, Model model,String padIps) throws Exception {
	    List<ToolsPad>list=Lists.newArrayList();
		if(padIps!=null){
		  service.saveIp(request, padIps);
		  list=service.selectToolsPad();
		}else{
			 list=service.selectToolsPad();
		}
  
		return list;
	}
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, String padIps) throws Exception {
		service.saveIp(request, padIps);
	}
}
