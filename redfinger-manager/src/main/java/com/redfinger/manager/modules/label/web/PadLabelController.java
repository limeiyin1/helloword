package com.redfinger.manager.modules.label.web;

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
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfLabel;
import com.redfinger.manager.common.domain.RfPadLabel;
import com.redfinger.manager.modules.label.service.LabelService;
import com.redfinger.manager.modules.label.service.PadLabelService;

@Controller
@RequestMapping(value = "/label/padLabel")
public class PadLabelController  extends BaseController{

	@Autowired
	private PadLabelService service;
	@Autowired
	private LabelService labelService;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		List<RfLabel> labels = labelService.initQuery().andEqualTo("labelType", ConstantsDb.labelTypePad()).findStopTrue();
		model.addAttribute("labels", labels);
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfPadLabel> list(HttpServletRequest request,
			HttpServletResponse response, Model model, RfPadLabel bean)
			throws Exception {
		
		List<RfPadLabel> list = service.initQuery(bean)
				.andEqualTo("labelId", bean.getLabelId())
				.andLike("padCode", bean.getPadCode())
				.addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		if(null != list && list.size() > 0 ){
			for (RfPadLabel padLabel : list) {
				padLabel.getMap().put("labelCode", labelService.get(padLabel.getLabelId()).getLabelCode());
				padLabel.getMap().put("labelName", labelService.get(padLabel.getLabelId()).getLabelName());
			}
		}
		
		PageInfo<RfPadLabel> pageInfo = new PageInfo<RfPadLabel>(list);

		return pageInfo;
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response,
			Model model, RfPadLabel bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response,
			Model model, RfPadLabel bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request,
			HttpServletResponse response, Model model, RfPadLabel bean)
			throws Exception {
		service.remove(request, bean);
	}
}
