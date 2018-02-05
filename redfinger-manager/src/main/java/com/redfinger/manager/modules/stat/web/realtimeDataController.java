package com.redfinger.manager.modules.stat.web;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.modules.game.service.ViewGmonitorRealtimeDataService;
import com.redfinger.manager.modules.stat.base.StatDomain;

@Controller
@RequestMapping(value = "/stat/realtimeData")
public class realtimeDataController extends BaseController {
	@Autowired
	ViewGmonitorRealtimeDataService service;
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
//		  List<ViewGmonitorRealtimeData>taskList=service.initQuery().findStopTrue();
//		  model.addAttribute("taskList", taskList);
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "getChart")
	public Map<String,Object> getChart (HttpServletRequest request, HttpServletResponse response, Model model,StatDomain bean) {
	
		return service.stat(bean);
	}
}
