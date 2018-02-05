package com.redfinger.manager.modules.assess.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.AssessContent;
import com.redfinger.manager.common.domain.AssessProject;
import com.redfinger.manager.common.domain.SysAdmin;
import com.redfinger.manager.common.utils.RSAUtils;
import com.redfinger.manager.modules.assess.service.AssessContentService;
import com.redfinger.manager.modules.assess.service.AssessProjectService;
import com.redfinger.manager.modules.assess.service.SysAdminService;

@Controller
@RequestMapping(value = "/assess/content")
public class AssessContentController extends BaseController {

	@Autowired
	AssessContentService contentService;
	@Autowired
	AssessProjectService projectService;
	@Autowired
	SysAdminService adminService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public Map<String, Object> list(HttpServletRequest request, HttpServletResponse response, Model model,
			AssessContent bean, String name) throws Exception {
		List<AssessContent> return_list = new ArrayList<AssessContent>();
		Map<String, Object> map = Maps.newHashMap();
		Date time = new Date();
		List<AssessProject> ap_list = projectService.initQuery().andLike("name", name).andEqualTo("enableStatus", "1")
				.andLessThanOrEqualTo("beginTime", time).addOrderClause("createTime", "desc").andIsNotNull("secret")
				.findAll();
		if (ap_list == null || ap_list.size() == 0) {
			map.put("list", return_list);
			return map;
		}

		// 加载所有考核用户
		Map<String, String> adminMap = new HashMap<String, String>();
		List<SysAdmin> admin_list = adminService.initQuery().findAll();
		for (SysAdmin admin : admin_list) {
			adminMap.put(admin.getAdminCode(), admin.getAdminName());
		}
		for (AssessProject ap : ap_list) {
			String pName = ap.getName();
			Integer projectId = ap.getId();
			List<AssessContent> list = contentService.initQuery().andEqualTo("projectId", projectId).findAll();
			for (AssessContent ac : list) {
				String targetAdmin = ac.getTargetAdmin();
				// 解密用户名
				// 解密targetAdmin
				targetAdmin = RSAUtils.decryptByPrivateKey(targetAdmin,
						RSAUtils.getPrivateKey(projectService.getPrivateKey()));
				targetAdmin = adminMap.get(targetAdmin);
				ac.setTargetAdmin(targetAdmin);
				ac.getMap().put("pName", pName);
				return_list.add(ac);
			}
		}
		map.put("list", return_list);
		return map;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, AssessContent bean)
			throws Exception {
		if (bean.getId() == null) {

		} else {
			bean = contentService.get(bean.getId());
			// 解密 targetAdmin
			String targetAdmin = RSAUtils.decryptByPrivateKey(bean.getTargetAdmin(),
					RSAUtils.getPrivateKey(projectService.getPrivateKey()));
			targetAdmin = adminService.load(targetAdmin).getAdminName();
			bean.setTargetAdmin(targetAdmin);
			model.addAttribute("bean", bean);
		}
		return this.toPage(request, response, model);
	}

	/**
	 * 额外加（改）分
	 * 
	 * @Title: addPoints
	 * @return void 返回类型
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @param point
	 * @throws Exception
	 */
	@RequestMapping(value = "update")
	public void addPoints(HttpServletRequest request, HttpServletResponse response, Model model, AssessContent bean,
			Integer point) throws Exception {
		if (point == null || bean.getId() == null) {
			return;
		}
		// 获取公钥
		Integer projectId = contentService.get(bean.getId()).getProjectId();
		AssessProject ap = projectService.get(projectId);
		if (ap == null) {
			return;
		}
		String userPublicKey = ap.getSecret();
		// 加密分数
		String userPoint = RSAUtils.encryptByPublicKey(point + "", RSAUtils.getPublicKey(userPublicKey));
		bean.setOtherPoint(userPoint);
		contentService.update(request, bean);
	}
}
