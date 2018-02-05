package com.redfinger.manager.modules.assess.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.AssessContent;
import com.redfinger.manager.common.domain.AssessContentExample;
import com.redfinger.manager.common.domain.AssessContentList;
import com.redfinger.manager.common.domain.AssessContentListExample;
import com.redfinger.manager.common.domain.AssessOption;
import com.redfinger.manager.common.domain.AssessProject;
import com.redfinger.manager.common.domain.AssessProjectOption;
import com.redfinger.manager.common.domain.AssessProjectOptionExample;
import com.redfinger.manager.common.domain.SysAdmin;
import com.redfinger.manager.common.domain.SysOrg;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.mapper.AssessContentListMapper;
import com.redfinger.manager.common.mapper.AssessContentMapper;
import com.redfinger.manager.common.mapper.AssessProjectOptionMapper;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.RSAUtils;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.modules.assess.service.AssessContentListService;
import com.redfinger.manager.modules.assess.service.AssessContentService;
import com.redfinger.manager.modules.assess.service.AssessOptionService;
import com.redfinger.manager.modules.assess.service.AssessProjectOptionService;
import com.redfinger.manager.modules.assess.service.AssessProjectService;
import com.redfinger.manager.modules.assess.service.SysAdminService;
import com.redfinger.manager.modules.assess.service.SysOrgService;

/**
 * 考核管理Controller
 * 
 * @ClassName: AssessProjectController
 * @author tluo
 * @date 2016年3月23日 下午3:15:04
 */
@Controller
@RequestMapping(value = "/assess/manage")
public class AssessProjectController extends BaseController {

	@Autowired
	AssessProjectService service;
	@Autowired
	SysOrgService sysOrgService;
	@Autowired
	SysAdminService sysAdminService;
	@Autowired
	AssessContentService contentService;
	@Autowired
	AssessProjectOptionService projectOptionService;
	@Autowired
	AssessProjectOptionMapper mapper;
	@Autowired
	AssessOptionService optionService;
	@Autowired
	AssessContentListService contentListService;
	@Autowired
	AssessContentListMapper assessContentListMapper;
	@Autowired
	AssessContentMapper contentMapper;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<AssessProject> list(HttpServletRequest request, HttpServletResponse response, Model model,
			AssessProject bean, String begin, String end) throws Exception {
		Date beginTime = null;
		Date endTime = null;
		if (StringUtils.isNotBlank(begin)) {
			beginTime = DateUtils.parseDate(begin);
		}
		if (StringUtils.isNotBlank(end)) {
			endTime = DateUtils.addDays(DateUtils.parseDate(end), 1);
		}
		List<AssessProject> list = service.initQuery(bean).andLike("name", bean.getName())
				.andGreaterThanOrEqualTo("createTime", beginTime).andLessThan("createTime", endTime)
				.addOrderClause("createTime", "desc").addOrderForce(bean.getSort(), bean.getOrder())
				.pageAll(bean.getPage(), bean.getRows());

		PageInfo<AssessProject> pageInfo = new PageInfo<AssessProject>(list);

		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, AssessProject bean)
			throws Exception {
		if (bean.getId() == null) {

		} else {
			bean = service.get(bean.getId());
			this.verificationBegin(bean.getId());
			model.addAttribute("bean", bean);
		}
		return this.toPage(request, response, model);
	}

	/**
	 * 获取私钥
	 * 
	 * @Title: getKey
	 * @return String 返回类型
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getKey")
	public String getKey(HttpServletRequest request, HttpServletResponse response, Model model, AssessProject bean)
			throws Exception {
		if (bean.getId() == null) {

		} else {
			Date time = new Date();
			bean = service.get(bean.getId());
			// 判断考核开始将不可以再获取
			if (bean.getBeginTime().getTime() < time.getTime())
				throw new BusinessException("该考核已开始，无法再获取密钥！");

			// 获取公私密钥对
			HashMap<String, Object> map = RSAUtils.getKeys();
			String pulicKey = RSAUtils.getPublicKey(map);
			String privateKey = RSAUtils.getPrivateKey(map);

			// 保存公钥
			AssessProject upBean = new AssessProject();
			upBean.setId(bean.getId());
			upBean.setSecret(pulicKey);
			service.update(request, upBean);

			model.addAttribute("name", bean.getName() + "_私钥_" + DateUtils.formatDateTime(time));
			privateKey = privateKey.replace("+", "%2B");
			model.addAttribute("privateKey", privateKey);

		}
		return this.toPage(request, response, model);
	}

	/**
	 * 配置适用员工
	 * 
	 * @Title: setTargetAdmin
	 * @return String 返回类型
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "targetAdmin")
	public String setTargetAdmin(HttpServletRequest request, HttpServletResponse response, Model model,
			AssessProject bean) throws Exception {
		// 判断考核时间
		if (bean.getId() == null) {

		} else {
			verificationBegin(bean.getId());
			model.addAttribute("bean", bean);
		}
		return this.toPage(request, response, model);

	}

	/**
	 * 配置参与考核人
	 * 
	 * @Title: setAssessAdmin
	 * @return String 返回类型
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "assessAdmin")
	public String setAssessAdmin(HttpServletRequest request, HttpServletResponse response, Model model,
			AssessProject bean) throws Exception {
		if (bean.getId() == null) {

		} else {
			verificationBegin(bean.getId());
			// 判断是否添加适合员工
			List<AssessContent> list = contentService.initQuery().andEqualTo("projectId", bean.getId()).findAll();
			if (list == null || list.size() == 0)
				throw new BusinessException("该考核无适合员工，请先配置适合员工！");
			model.addAttribute("bean", bean);
		}
		return this.toPage(request, response, model);

	}

	/**
	 * 获取被考核人
	 * 
	 * @Title: getAssessAdmin
	 * @return List<AssessContent> 返回类型
	 * @param request
	 * @param response
	 * @param model
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getAssessAdmin")
	public List<AssessContent> getAssessAdmin(HttpServletRequest request, HttpServletResponse response, Model model,
			Integer id) throws Exception {
		List<AssessContent> list = contentService.initQuery().andEqualTo("projectId", id).findAll();
		if (list == null || list.size() == 0)
			throw new BusinessException("该考核无适合员工，请先配置适合员工！");
		for (AssessContent as : list) {
			// 解密targetAdmin
			String targetAdmin = RSAUtils.decryptByPrivateKey(as.getTargetAdmin(),
					RSAUtils.getPrivateKey(service.getPrivateKey()));
			as.setTargetAdmin(sysAdminService.load(targetAdmin).getAdminName());
		}
		return list;
	}

	/**
	 * 获取所有部门信息
	 * 
	 * @Title: getOrg
	 * @return List<SysOrg> 返回类型
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getOrg")
	public List<SysOrg> getOrg(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		List<SysOrg> list = new ArrayList<SysOrg>();
		list = sysOrgService.initQuery().findStopTrue();
		return list;

	}

	/**
	 * 获取可选（适用人、考核人）
	 * 
	 * @Title: getAdmin
	 * @return List<SysAdmin> 返回类型
	 * @param request
	 * @param response
	 * @param model
	 * @param orgCode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getAdmin")
	public List<SysAdmin> getAdmin(HttpServletRequest request, HttpServletResponse response, Model model,
			String orgCode, Integer id, Integer aid) throws Exception {
		List<String> nlist = new ArrayList<String>();
		if (id != null) {
			List<AssessContent> list = contentService.initQuery().andEqualTo("projectId", id).findAll();
			for (AssessContent as : list) {
				// 解密targetAdmin
				String targetAdmin = RSAUtils.decryptByPrivateKey(as.getTargetAdmin(),
						RSAUtils.getPrivateKey(service.getPrivateKey()));
				nlist.add(targetAdmin);
			}
		}
		if (aid != null) {
			AssessContent ac = contentService.get(aid);
			List<AssessContentList> list = contentListService.initQuery()
					.andEqualTo("targetAdmin", ac.getTargetAdmin()).findAll();
			for (AssessContentList acl : list) {
				// 解密AssessAdmin
				String AssessAdmin = RSAUtils.decryptByPrivateKey(acl.getAssessAdmin(),
						RSAUtils.getPrivateKey(service.getPrivateKey()));
				nlist.add(AssessAdmin);
			}
		}
		return sysAdminService.initQuery().andEqualTo("orgCode", orgCode).andNotIn("adminCode", nlist).findStopTrue();

	}

	/**
	 * 获取适用员工
	 * 
	 * @Title: getTarget
	 * @return List<AssessContent> 返回类型
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getTarget")
	public List<AssessContent> getTarget(HttpServletRequest request, HttpServletResponse response, Model model,
			Integer id) throws Exception {
		List<AssessContent> list = contentService.initQuery().andEqualTo("projectId", id).findAll();
		for (AssessContent as : list) {
			// 解密TargetAdmin
			String targetAdmin = RSAUtils.decryptByPrivateKey(as.getTargetAdmin(),
					RSAUtils.getPrivateKey(service.getPrivateKey()));
			String name = sysAdminService.load(targetAdmin).getAdminName();
			if (name != null) {
				as.setTargetAdmin(name);
			}
		}
		return list;
	}

	/**
	 * 获取被考核人和考核人对应关系数据
	 * 
	 * @Title: getAssess
	 * @return List<AssessContentList> 返回类型
	 * @param request
	 * @param response
	 * @param model
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getAssess")
	public List<AssessContentList> getAssess(HttpServletRequest request, HttpServletResponse response, Model model,
			Integer id) throws Exception {
		AssessContent ac = contentService.get(id);
		String targetAdmin = ac.getTargetAdmin();
		Integer projectId = ac.getProjectId();
		List<AssessContentList> list = contentListService.initQuery().andEqualTo("targetAdmin", targetAdmin)
				.andEqualTo("projectId", projectId).findAll();
		for (AssessContentList acl : list) {
			// 解密TargetAdmin
			String assessAdmin = RSAUtils.decryptByPrivateKey(acl.getAssessAdmin(),
					RSAUtils.getPrivateKey(service.getPrivateKey()));
			acl.setAssessAdmin(sysAdminService.get(assessAdmin).getAdminName());
		}
		return list;
	}

	/**
	 * 删除适用员工
	 * 
	 * @Title: removeTarget
	 * @return void 返回类型
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @throws Exception
	 */
	@RequestMapping(value = "removeTarget")
	public void removeTarget(HttpServletRequest request, HttpServletResponse response, Model model, Integer id,
			String ids) throws Exception {
		if (id == null || StringUtils.isEmpty(ids))
			throw new BusinessException("参数异常，请最少选择一条记录。");
		verificationBegin(id);
		String[] arr_ids = ids.split(",");
		for (int i = 0; i < arr_ids.length; i++) {
			Integer sId = Integer.parseInt(arr_ids[i]);
			String targetAdmin = contentService.get(sId).getTargetAdmin();
			contentService.remove(request, sId);
			// 删除适用员工的考核员工
			AssessContentListExample exmple = new AssessContentListExample();
			AssessContentListExample.Criteria criteria = exmple.createCriteria();
			criteria.andTargetAdminEqualTo(targetAdmin);
			assessContentListMapper.deleteByExample(exmple);
		}

	}

	/**
	 * 添加适用员工
	 * 
	 * @Title: addTarget
	 * @return void 返回类型
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @throws Exception
	 */
	@RequestMapping(value = "addTarget")
	public void addTarget(HttpServletRequest request, HttpServletResponse response, Model model, Integer id, String ids)
			throws Exception {

		if (id == null || StringUtils.isEmpty(ids))
			throw new BusinessException("参数异常，请最少选择一条记录。");
		verificationBegin(id);
		String[] arr_ids = ids.split(",");
		for (int i = 0; i < arr_ids.length; i++) {
			// 加密TargetAdmin
			String targetAdmin = RSAUtils.encryptByPublicKey(arr_ids[i], RSAUtils.getPublicKey(service.getPublicKey()));
			AssessContent up = new AssessContent();
			up.setProjectId(id);
			up.setProjectName(service.get(id).getName());
			up.setTargetAdmin(targetAdmin);
			contentService.save(request, up);
		}
	}

	/**
	 * 添加考核人
	 * 
	 * @Title: addAssess
	 * @return void 返回类型
	 * @param request
	 * @param response
	 * @param model
	 * @param id
	 * @param ids
	 * @throws Exception
	 */
	@RequestMapping(value = "addAssess")
	public void addAssess(HttpServletRequest request, HttpServletResponse response, Model model, Integer id, String ids)
			throws Exception {
		if (id == null || StringUtils.isEmpty(ids))
			throw new BusinessException("参数异常，请最少选择一条记录。");
		AssessContent ac = contentService.get(id);
		Integer projectId = ac.getProjectId();
		String targetAdmin = ac.getTargetAdmin();
		String[] arr_ids = ids.split(",");
		for (int i = 0; i < arr_ids.length; i++) {
			// 加密TargetAdmin
			String assessAdmin = RSAUtils.encryptByPublicKey(arr_ids[i], RSAUtils.getPublicKey(service.getPublicKey()));
			AssessContentList up = new AssessContentList();
			up.setAssessAdmin(assessAdmin);
			up.setTargetAdmin(targetAdmin);
			up.setAssessStatus("0");
			up.setProjectId(ac.getProjectId());
			contentListService.save(request, up);
		}
		// 计算需要考核次数并修改
		countNeedAssessdNum(projectId, targetAdmin);
	}

	/**
	 * 删除考核人
	 * 
	 * @Title: removeAssess
	 * @return void 返回类型
	 * @param request
	 * @param response
	 * @param model
	 * @param ids
	 * @throws Exception
	 */
	@RequestMapping(value = "removeAssess")
	public void removeAssess(HttpServletRequest request, HttpServletResponse response, Model model, String ids)
			throws Exception {
		if (StringUtils.isEmpty(ids))
			throw new BusinessException("参数异常，请最少选择一条记录。");
		String[] arr_ids = ids.split(",");
		AssessContentList acl = contentListService.get(Integer.parseInt(arr_ids[0]));
		Integer projectId = acl.getProjectId();
		String targetAdmin = acl.getTargetAdmin();
		for (int i = 0; i < arr_ids.length; i++) {

			Integer id = Integer.parseInt(arr_ids[i]);
			contentListService.remove(request, id);
		}
		// 计算需要考核次数并修改
		countNeedAssessdNum(projectId, targetAdmin);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, AssessProject bean)
			throws Exception {
		Date beginTime = null;
		Date endTime = null;
		if (StringUtils.isNotBlank(bean.getBeginTimeStr())) {
			beginTime = DateUtils.parseDate(bean.getBeginTimeStr());
			bean.setBeginTime(beginTime);
		}
		if (StringUtils.isNotBlank(bean.getEndTimeStr())) {
			endTime = DateUtils.parseDate(bean.getEndTimeStr());
			bean.setEndTime(endTime);
		}
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, AssessProject bean)
			throws Exception {
		Date beginTime = null;
		Date endTime = null;
		if (StringUtils.isNotBlank(bean.getBeginTimeStr())) {
			beginTime = DateUtils.parseDate(bean.getBeginTimeStr());
			bean.setBeginTime(beginTime);
		}
		if (StringUtils.isNotBlank(bean.getEndTimeStr())) {
			endTime = DateUtils.parseDate(bean.getEndTimeStr());
			bean.setEndTime(endTime);
		}
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, AssessProject bean)
			throws Exception {
		String[] arr_ids = bean.getIds().split(",");
		for (int i = 0; i < arr_ids.length; i++) {
			Integer id = Integer.parseInt(arr_ids[i]);
			AssessProject up = service.get(id);
			if (StringUtils.isEmpty(up.getSecret()))
				throw new BusinessException("公钥为空_操作失败，请先获取私钥！");
		}
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, AssessProject bean)
			throws Exception {
		verificationBegin(bean.getIds());
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, AssessProject bean)
			throws Exception {
		Date time = new Date();
		String[] arr_ids = bean.getIds().split(",");
		for (int i = 0; i < arr_ids.length; i++) {
			Integer id = Integer.parseInt(arr_ids[i]);
			AssessProject up = service.get(id);
			if (up.getEndTime().getTime() < time.getTime())
				throw new BusinessException("该考核已结束，无法操作！");
		}
		service.remove(request, bean);
	}

	@RequestMapping(value = "optionForm")
	public String formCode(HttpServletRequest request, HttpServletResponse response, Model model, AssessProject bean)
			throws Exception {
		bean = service.get(bean.getId());
		this.verificationBegin(bean.getId());
		model.addAttribute("bean", bean);
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "optionList")
	public List<AssessOption> optionList(HttpServletRequest request, HttpServletResponse response, Model model,
			AssessProject bean, String name) throws Exception {
		List<AssessProjectOption> list1 = projectOptionService.initQuery().andEqualTo("ProjectId", bean.getId())
				.findAll();
		List<Integer> ids = Lists.newArrayList();
		for (AssessProjectOption l : list1) {
			ids.add(l.getOptionId());
		}
		List<AssessOption> list2 = optionService.initQuery().andLike("name", name).andNotIn("id", ids)
				.addOrderClause("reorder", " asc").addOrderForce(bean.getSort(), bean.getOrder()).findStopTrue();
		for (AssessOption l : list2) {
			ids.add(l.getId());
		}
		List<AssessOption> list3 = optionService.initQuery().andLike("keyword", name).andNotIn("id", ids)
				.addOrderClause("reorder", " asc").addOrderForce(bean.getSort(), bean.getOrder()).findStopTrue();
		List<AssessOption> list = Lists.newArrayList();
		for (AssessOption l : list2) {
			list.add(l);
		}
		for (AssessOption l : list3) {
			list.add(l);
		}

		return list;
	}

	/**
	 * 获取考核题目
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "myOptionList")
	public List<AssessOption> myOptionList(HttpServletRequest request, HttpServletResponse response, Model model,
			AssessProject bean) throws Exception {
		List<AssessProjectOption> list = projectOptionService.initQuery().andEqualTo("ProjectId", bean.getId())
				.findAll();
		List<Integer> ids = Lists.newArrayList();
		ids.add(-1);
		for (AssessProjectOption l : list) {
			ids.add(l.getOptionId());
		}
		List<AssessOption> opList = optionService.initQuery().andIn("id", ids).findStopTrue();
		return opList;
	}

	/**
	 * 添加考核题目
	 * @param request
	 * @param response
	 * @param model
	 * @param ids
	 * @param bean
	 * @param projectOption
	 * @throws Exception
	 */
	@RequestMapping(value = "addOption")
	public void addOption(HttpServletRequest request, HttpServletResponse response, Model model, String ids,
			AssessProject bean, AssessProjectOption projectOption) throws Exception {
		String[] arr = ids.split(",");
		projectOption.setProjectId(bean.getId());
		String userId = SessionUtils.getCurrentUserId(request);
		projectOption.setCreater(userId);
		for (String id : arr) {
			Date currentDate = new Date();
			projectOption.setCreateTime(currentDate);
			projectOption.setOptionId(Integer.parseInt(id));
			mapper.insertSelective(projectOption);
		}

	}

	/**
	 * 删除考核题目
	 * @param request
	 * @param response
	 * @param model
	 * @param ids
	 * @param bean
	 * @param projectOption
	 * @throws Exception
	 */
	@RequestMapping(value = "removeOption")
	public void removeOption(HttpServletRequest request, HttpServletResponse response, Model model, String ids,
			AssessProject bean, AssessProjectOption projectOption) throws Exception {
		String[] arr = ids.split(",");
		for (String id : arr) {
			AssessProjectOptionExample example = new AssessProjectOptionExample();
			example.createCriteria().andProjectIdEqualTo(bean.getId()).andOptionIdEqualTo(Integer.parseInt(id));
			mapper.deleteByExample(example);
		}
	}

	/**
	 * 复制考核项目
	 * @param request
	 * @param response
	 * @param model
	 * @param ids
	 * @param bean
	 * @param projectOption
	 * @throws Exception
	 */
	@RequestMapping(value = "copyForm")
	public void copyFrom(HttpServletRequest request, HttpServletResponse response, Model model, String ids,
			AssessProject bean, AssessProjectOption projectOption) throws Exception {
		String[] arr = ids.split(",");
		for (String i : arr) {
			Integer id = Integer.parseInt(i);
			bean = service.get(id);
			bean.setId(null);
			bean.setEnableStatus("0");
			Date date = new Date();
			Date begin = DateUtils.addMonths(date, 1);
			Date end = DateUtils.addDays(begin, 10);
			bean.setBeginTime(begin);
			bean.setEndTime(end);
			String name = bean.getName() + "副本";
			bean.setName(name);
			bean.setSecret(null);
			service.save(request, bean);
			List<AssessProjectOption> projectOptionList = projectOptionService.initQuery().andEqualTo("projectId", id)
					.findAll();
			List<AssessContent> content = contentService.initQuery().andEqualTo("projectId", id).findAll();
			List<AssessContentList> contentList = contentListService.initQuery().andEqualTo("projectId", id).findAll();

			if (projectOptionList.size() != 0)
				for (AssessProjectOption l : projectOptionList) {
					List<AssessOption> option=optionService.initQuery().andEqualTo("id", l.getOptionId()).findStopTrue();
					if(option.size()==0) continue;
					l.setId(null);
					l.setProjectId(bean.getId());
					l.setCreateTime(new Date());
					projectOptionService.save(request, l);
				}

			if (content.size() != 0)
				for (AssessContent l : content) {
					l.setId(null);
					l.setProjectId(bean.getId());
					l.setAssessPoint(null);
					l.setAleadyAssessNum(null);
					l.setOtherPoint(null);
					contentService.save(request, l);
				}
			if (contentList.size() != 0)
				for (AssessContentList l : contentList) {
					l.setId(null);
					l.setProjectId(bean.getId());
					l.setAssessStatus("0");
					l.setPoint(null);
					l.setAssessTime(null);
					l.setCreateTime(new Date());
					contentListService.save(request, l);
				}

		}
	}

	/**
	 * 验证考核状态是否为可操作
	 * 
	 * @Title: verificationBegin
	 * @return void 返回类型
	 * @param id
	 * @throws Exception
	 */
	public void verificationBegin(Integer id) throws Exception {
		Date time = new Date();
		AssessProject bean = service.get(id);
		// 判断考核开始将不可以再获取
		if (bean.getBeginTime().getTime() < time.getTime())
			throw new BusinessException("该考核已开始，无法操作！");
		if (bean.getEndTime().getTime() < time.getTime())
			throw new BusinessException("该考核已结束，无法操作！");
	}

	/**
	 * 验证考核状态是否为可操作
	 * 
	 * @Title: verificationBegin
	 * @return void 返回类型
	 * @param ids
	 * @throws Exception
	 */
	public void verificationBegin(String ids) throws Exception {
		Date time = new Date();
		String[] arr_ids = ids.split(",");
		for (int i = 0; i < arr_ids.length; i++) {
			Integer id = Integer.parseInt(arr_ids[i]);
			AssessProject bean = service.get(id);
			// 判断考核开始将不可以再获取
			if (bean.getBeginTime().getTime() < time.getTime())
				throw new BusinessException("该考核已开始，无法操作！");
			if (bean.getEndTime().getTime() < time.getTime())
				throw new BusinessException("该考核已结束，无法操作！");
		}
	}

	/***
	 * 计算需要考核的次数
	 * 
	 * @Title: countNeedAssessdNum
	 * @return void 返回类型
	 * @param projectId
	 *            考核Id
	 * @param targetAdmin
	 *            未解密的考核人
	 */
	public void countNeedAssessdNum(Integer projectId, String targetAdmin) {
		// 计算需要考核次数并修改
		AssessContentListExample example = new AssessContentListExample();
		AssessContentListExample.Criteria criteria = example.createCriteria();
		criteria.andProjectIdEqualTo(projectId);
		criteria.andTargetAdminEqualTo(targetAdmin);
		Integer count = assessContentListMapper.countByExample(example);
		// 更新考核结果表中需要考核的次数
		AssessContent record = new AssessContent();
		record.setNeedAssessNum(count);
		AssessContentExample acExample = new AssessContentExample();
		AssessContentExample.Criteria acCriteria = acExample.createCriteria();
		acCriteria.andProjectIdEqualTo(projectId);
		acCriteria.andTargetAdminEqualTo(targetAdmin);
		contentMapper.updateByExampleSelective(record, acExample);

	}
}
