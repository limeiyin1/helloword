package com.redfinger.manager.modules.facility.web;

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
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfControl;
import com.redfinger.manager.common.domain.RfControlRelation;
import com.redfinger.manager.common.domain.RfVideo;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.modules.facility.service.ControlRelationService;
import com.redfinger.manager.modules.facility.service.ControlService;
import com.redfinger.manager.modules.facility.service.IdcService;
import com.redfinger.manager.modules.facility.service.VideoService;

@Controller
@RequestMapping(value = "/facility/relation")
public class ControlRelationController extends BaseController {
	@Autowired
	IdcService idcService;
	@Autowired
	ControlRelationService service;
	@Autowired
	ControlService controlService;
	@Autowired
	VideoService videoService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfControlRelation> list(HttpServletRequest request, HttpServletResponse response, Model model, RfControlRelation bean) throws Exception {
		
		List<Integer>padControlIds = new ArrayList<>();
		List<Integer>userControlIds = new ArrayList<>();
		//获取物理节点id
		List<RfControl> rfPadControl = controlService.initQuery().andLike("controlName", bean.getPadControlName())
						.findAll();
		if (!Collections3.isEmpty(rfPadControl)) {
			for (RfControl rfPad : rfPadControl) {
				padControlIds.add(rfPad.getControlId());
			}
		}else{
			padControlIds.add(-1);
		}
		//获取用户节点id
		List<RfControl> rfUserControl = controlService.initQuery().andLike("controlName", bean.getUserControlName())
				.findAll();
		if (!Collections3.isEmpty(rfUserControl)) {
			for (RfControl rfUser : rfUserControl) {
				userControlIds.add(rfUser.getControlId());
			}
		}else{
			userControlIds.add(-1);
		}
		
		List<RfControlRelation> list = service.initQuery(bean)
		.andIn("userControlId", userControlIds)
		.andIn("padControlId", padControlIds)
		.addOrderClause("reorder", "asc")
		.addOrderForce(bean.getSort(), bean.getOrder())
		.pageDelTrue(bean.getPage(), bean.getRows());
		
		if (!Collections3.isEmpty(list)) {
			for(RfControlRelation li : list){
				li.setUserControlName(controlService.get(li.getUserControlId()).getControlName());
				li.setPadControlName(controlService.get(li.getPadControlId()).getControlName());
				li.setManageControlName(controlService.get(li.getManageControlId()).getControlName());
				li.setUserVideoName(videoService.get(li.getUserVideoId()).getVideoName());
				li.setPadVideoName(videoService.get(li.getPadVideoId()).getVideoName());
			}
		}
		PageInfo<RfControlRelation> pageInfo = new PageInfo<RfControlRelation>(list);
		return pageInfo;
		//return service.list(bean, userControlName, padControlName, controlService,videoService);
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfControlRelation bean)
			throws Exception {
		if (bean.getRelationId() != null) {
			bean = service.get(bean.getRelationId());
			model.addAttribute("bean", bean);
		}
		List<RfControl> controlList = controlService.initQuery()
				.andEqualTo("controlType", ConstantsDb.rfControlControlTypeUser()).findStopTrue();
		List<RfControl> padControlList = controlService.initQuery()
				.andEqualTo("controlType", ConstantsDb.rfControlControlTypeDevice()).findStopTrue();
		List<RfControl> manageControlList = controlService.initQuery()
				.andEqualTo("controlType", ConstantsDb.rfControlControlTypeManage()).findStopTrue();
		model.addAttribute("controlList", controlList);
		model.addAttribute("padControlList", padControlList);
		model.addAttribute("manageControlList", manageControlList);

		List<RfVideo> userVideoList = videoService.initQuery()
				.andEqualTo("videoType", ConstantsDb.rfVideoVideoTypeUser()).findStopTrue();
		List<RfVideo> padVideoList = videoService.initQuery()
				.andEqualTo("videoType", ConstantsDb.rfVideoVideoTypeDevice()).findStopTrue();
		model.addAttribute("userVideoList", userVideoList);
		model.addAttribute("padVideoList", padVideoList);

		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "getByUserControlId")
	public RfControlRelation getByUserControlId(HttpServletRequest request, HttpServletResponse response, Model model,
			RfControlRelation bean, Integer userControlId) throws Exception {
		List<RfControlRelation> controls = service.initQuery().andEqualTo("userControlId", userControlId)
				.singleDelTrue();
		RfControlRelation rfControlRelation = null;
		if (null != controls && controls.size() > 0) {
			rfControlRelation = controls.get(0);
		}
		/*
		 * rfControlRelation.setUserControlName(controlService.get(
		 * rfControlRelation.getUserControlId()).getControlName());
		 * rfControlRelation.setPadControlName(controlService.get(
		 * rfControlRelation.getPadControlId()).getControlName());
		 * rfControlRelation.setManageControlName(controlService.get(
		 * rfControlRelation.getManageControlId()).getControlName());
		 * rfControlRelation.setUserVideoName(videoService.get(rfControlRelation
		 * .getUserVideoId()).getVideoName());
		 * rfControlRelation.setPadVideoName(videoService.get(rfControlRelation.
		 * getPadVideoId()).getVideoName());
		 */
		return rfControlRelation;
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfControlRelation bean)
			throws Exception {
		if (null != bean.getUserControlId()) {
			List<RfControlRelation> controls = service.initQuery().andEqualTo("userControlId", bean.getUserControlId())
					.singleDelTrue();
			if (null != controls && controls.size() > 0) {// 如果有传id过来并且id已经使用了的，就改为updata
				bean.setRelationId(controls.get(0).getRelationId());
				update(request, response, model, bean);
				return;
			}
		}
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfControlRelation bean)
			throws Exception {
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfControlRelation bean)
			throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfControlRelation bean)
			throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfControlRelation bean)
			throws Exception {
		service.remove(request, bean);
	}

}
