package com.redfinger.manager.modules.sys.web;


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

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.base.TreeDomain;
import com.redfinger.manager.common.constants.Constants;
import com.redfinger.manager.common.constants.ResultType;
import com.redfinger.manager.common.domain.RfDevice;
import com.redfinger.manager.common.domain.SysAdmin;
import com.redfinger.manager.common.domain.SysAdminRole;
import com.redfinger.manager.common.domain.SysOrg;
import com.redfinger.manager.common.domain.SysRole;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.common.utils.ListSortUtils;
import com.redfinger.manager.common.utils.Md5Utils;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.modules.sys.service.AdminRoleService;
import com.redfinger.manager.modules.sys.service.AdminService;
import com.redfinger.manager.modules.sys.service.OrgService;
import com.redfinger.manager.modules.sys.service.RoleService;

@Controller
@RequestMapping(value = "/sys/admin")
public class AdminController extends BaseController {
	@Autowired
	AdminService service;
	@Autowired
	AdminRoleService adminRoleService;
	@Autowired
	RoleService roleService;
	@Autowired
	OrgService orgService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		List<SysOrg> sysOrgList = orgService.initQuery().findStopTrue();
		List<SysRole> roleList = roleService.initQuery().findStopTrue();
		model.addAttribute("sysOrgList", sysOrgList);
		model.addAttribute("roleList", roleList);
		return this.toPage(request, response, model);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<SysAdmin> list(HttpServletRequest request, HttpServletResponse response, Model model, SysAdmin bean,String orgCode,String roleCode) throws Exception {
		//过滤角色
		List<String> adminCodeList = Lists.newArrayList();
		if(StringUtils.isNotBlank(roleCode)){
			List<SysAdminRole> adminRoleList = adminRoleService.initQuery().andEqualTo("roleCode", roleCode).findAll();
			if(!Collections3.isEmpty(adminRoleList)){
				adminCodeList.addAll(Collections3.extractToList(adminRoleList, "adminCode"));
	  		}else{
	  			adminCodeList.add("-1");
	  		}
		}
		List<SysAdmin> list = service.initQuery(bean)
				.andEqualTo("orgCode", orgCode)
				.andIn("adminCode", adminCodeList)
				.andEqualTo("enableStatus", bean.getEnableStatus())
				.andLike("adminName", bean.getAdminName())
				.andLike("adminCode", bean.getAdminCode())
				.addOrderClause("createTime", "asc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		for (SysAdmin adminUser : list) {
			adminUser.setAdminPwd(null);
			// 部门组织
			adminUser.getMap().put("orgName", adminUser.getOrgCode() == null ? "--" : orgService.get(adminUser.getOrgCode()).getOrgName());
			// 角色
			List<SysAdminRole> adminRoleList = adminRoleService.initQuery().andEqualTo("adminCode", adminUser.getAdminCode()).findAll();
			List roleIds = Collections3.extractToList(adminRoleList, "roleCode");
			if (!Collections3.isEmpty(roleIds)) {
				List<SysRole> roleList = roleService.initQuery().andIn("roleCode", roleIds.toArray()).findStopTrue();
				adminUser.getMap().put("roleNames", Collections3.extractToString(roleList, "roleName", ","));
			}
		}
		PageInfo<SysAdmin> pageInfo = new PageInfo<SysAdmin>(list);
		return pageInfo;

	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, SysAdmin bean) throws Exception {

		if (bean.getAdminCode() == null) {

		} else {
			bean = service.get(bean.getAdminCode());

		}
		List<SysOrg> orgList = orgService.initQuery().addOrderClause("reorder", "asc").findStopTrue();
		TreeDomain treeDomain = ListSortUtils.sortListToTree(orgList, "orgCode", "parentOrgCode", "orgName");
		// 组织
		model.addAttribute("orgTree", JsonUtils.ObjectToString(treeDomain.getChildren()));
		model.addAttribute("bean", bean);
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, SysAdmin bean) throws Exception {
		if(null != bean && StringUtils.isNotBlank(bean.getAdminCode())){
			if(bean.getAdminCode().contains(",")){
				throw new BusinessException("账号不允许带有逗号");
			}
		}
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, SysAdmin bean) throws Exception {
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, SysAdmin bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, SysAdmin bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, SysAdmin bean) throws Exception {
		service.delete(request, bean);
	}

	@RequestMapping(value = "roleForm")
	public String roleForm(HttpServletRequest request, HttpServletResponse response, Model model, SysAdmin bean) {
		bean = service.get(bean.getAdminCode());
		model.addAttribute("bean", bean);
		// 所有角色
		List<SysRole> roleList = roleService.initQuery().findStopTrue();
		// 自己的角色
		List<SysAdminRole> adminRoleList = adminRoleService.initQuery().andEqualTo("adminCode", bean.getAdminCode()).findAll();
		model.addAttribute("roleList", roleList);
		model.addAttribute("adminRoleList", adminRoleList);
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "role")
	public void permission(HttpServletRequest request, HttpServletResponse response, Model model, SysAdmin bean, String roleIds) throws Exception {
		adminRoleService.insert(request, bean, roleIds);
	}
	
	@RequestMapping(value = "adminCentre")
	public String adminCentre(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String userId = SessionUtils.getCurrentUserId(request);
		model.addAttribute("bean",service.get(userId));
		return this.toPage(request, response, model);
	}
	@RequestMapping(value = "formPwd")
	public String formPwd(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String userId = SessionUtils.getCurrentUserId(request);
		model.addAttribute("bean",service.get(userId));
		return this.toPage(request, response, model);
	}
	@RequestMapping(value = "resetPwd")
	public void resetPwd(HttpServletRequest request, HttpServletResponse response, Model model,SysAdmin bean) throws Exception {
		String[] idArray=StringUtils.split(bean.getIds(),",");
		for(String idStr:idArray){
			bean.setAdminPwd(Md5Utils.MD5( idStr+ Constants.USER_PASSWORD_SALT+ Constants.USER_DEFAULT_PASSWORD));
		    bean.setAdminCode(idStr);
			service.update(request, bean);
		}
	}

	@RequestMapping(value = "alterPwd")
	public void	alterPwd(HttpServletRequest request, HttpServletResponse response, Model model,SysAdmin bean,String pwd) throws Exception {
		if(service.get(bean.getAdminCode()).getAdminPwd().equals(Md5Utils.MD5(bean.getAdminCode()+ Constants.USER_PASSWORD_SALT + bean.getAdminPwd()))){
			bean.setAdminPwd(Md5Utils.MD5( bean.getAdminCode()+ Constants.USER_PASSWORD_SALT+ pwd));
			service.update(request, bean);
		}else
		{
			throw new BusinessException("原始密码输入错误！");
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "compulsionAlterPwd")
	public Map<String, Object>	compulsionAlterPwd(HttpServletRequest request, HttpServletResponse response, Model model,SysAdmin bean,String pwd) throws Exception {
		Map<String, Object> result= new HashMap<String, Object>();
		if(StringUtils.isBlank(pwd)){
			result.put(ResultType.CODE, ResultType.FAIL);
			result.put(ResultType.MSG, "密码不能为空");
			return result;
		}
		if(Constants.USER_DEFAULT_PASSWORD.equals(pwd)){
			result.put(ResultType.CODE, ResultType.FAIL);
			result.put(ResultType.MSG, "密码不能为默认密码");
			return result;
		}
		if(!pwd.matches("^.{8,}$")){
			result.put(ResultType.CODE, ResultType.FAIL);
			result.put(ResultType.MSG, "密码长度小于8");
			return result;
		}
		if(!(pwd.matches("^.*[a-z]+.*$") && pwd.matches("^.*[0-9]+.*$") && (pwd.matches("^.*[A-Z]+.*$"))|| pwd.matches("^.*[/^/$/.//,;:'!@#%&/*/|/?/+/(/)/[/]/{/}]+.*$"))){
			result.put(ResultType.CODE, ResultType.FAIL);
			result.put(ResultType.MSG, "密码需字母加数字组合,且至少一个大写字母或特殊字符");
			return result;
		}
		
		
		
		SysAdmin userAdmin= (SysAdmin) request.getSession().getAttribute(ResultType.USER_TEMP);
		String userId = userAdmin.getAdminCode();
		bean.setAdminCode(userId);
		bean.setAdminPwd(Md5Utils.MD5(bean.getAdminCode()+ Constants.USER_PASSWORD_SALT + pwd));
		service.update(request, bean);
		
		result.put(ResultType.CODE, ResultType.SUCCESS);
		result.put(ResultType.MSG, "修改成功！");
		request.getSession().removeAttribute(ResultType.USER_TEMP);
		return result;
	}
/*	@ResponseBody
	@RequestMapping(value = "passWord")
	public String 	passWord(HttpServletRequest request, HttpServletResponse response, Model model,SysAdmin sysAdmin) throws Exception {
		
		if(service.get(SessionUtils.getCurrentUserId(request)).getAdminPwd().equals(Md5Utils.MD5(SessionUtils.getCurrentUserId(request) + Constants.USER_PASSWORD_SALT + sysAdmin.getAdminPwd()))){
			return JsonUtils.ObjectToString(1);
		}else
		{
			return JsonUtils.ObjectToString(0);
		}
		
	}*/
}
