package com.redfinger.manager.modules.assess.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.AssessContent;
import com.redfinger.manager.common.domain.AssessContentDetail;
import com.redfinger.manager.common.domain.AssessContentExample;
import com.redfinger.manager.common.domain.AssessContentList;
import com.redfinger.manager.common.domain.AssessOption;
import com.redfinger.manager.common.domain.AssessProject;
import com.redfinger.manager.common.domain.AssessProjectOption;
import com.redfinger.manager.common.mapper.AssessContentListMapper;
import com.redfinger.manager.common.mapper.AssessContentMapper;
import com.redfinger.manager.common.utils.RSAUtils;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.modules.assess.service.AssessContentDetailService;
import com.redfinger.manager.modules.assess.service.AssessContentListService;
import com.redfinger.manager.modules.assess.service.AssessContentService;
import com.redfinger.manager.modules.assess.service.AssessOptionService;
import com.redfinger.manager.modules.assess.service.AssessProjectOptionService;
import com.redfinger.manager.modules.assess.service.AssessProjectService;
import com.redfinger.manager.modules.assess.service.SysAdminService;

@Controller
@RequestMapping(value = "/assess/list")
public class AssessContentListController extends BaseController {
	@Autowired
	AssessContentListService service;
	@Autowired
	AssessProjectOptionService projectOptionService;
	@Autowired
	AssessOptionService optionService;
	@Autowired
	AssessContentDetailService contentDetailService;
	@Autowired
	AssessProjectService assessProjectService;
	@Autowired
	SysAdminService sysAdminService;
	@Autowired
	AssessContentService contentService;
	@Autowired
	AssessContentMapper contentMapper;
	@Autowired
	AssessContentListMapper contentListMapper;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public Map<String, Object> list(HttpServletRequest request, HttpServletResponse response, Model model,
			AssessContentList bean) throws Exception {
		// 获得登录的帐号
		String user = SessionUtils.getCurrentUserId(request);
		// 带条件查询assessStatus属性值不为1的
		List<AssessContentList> list = service.initQuery(bean).andNotEqualTo("assessStatus", "1").pageAll(1, 100000);
		List<AssessContentList> nlist = new ArrayList<AssessContentList>();
		for (AssessContentList acl : list) {
			// 解密assessAdmin(考核人,私钥解密),解密后要把数据设置进去
			String assessAdmin = RSAUtils.decryptByPrivateKey(acl.getAssessAdmin(),
					RSAUtils.getPrivateKey(assessProjectService.getPrivateKey()));
			acl.setAssessAdmin(assessAdmin);

			acl.getMap().put("proname", assessProjectService.get(acl.getProjectId()).getName());
			acl.getMap().put("probegintime", assessProjectService.get(acl.getProjectId()).getBeginTime());
			acl.getMap().put("proendtime", assessProjectService.get(acl.getProjectId()).getEndTime());
			// 判断登录账号是否等于考核人
			if (user.equals(assessAdmin)) {
				// 获取project对象
				AssessProject ass = assessProjectService.get(acl.getProjectId());
				// 获取当前时间
				Date date = new Date();
				// 判断是否在考核时间范围内,通过把时间化为毫秒数比较getTime()
				if (date.getTime() > ass.getBeginTime().getTime() && date.getTime() < ass.getEndTime().getTime()
						&& "1".equals(ass.getEnableStatus())) {
					// 解密被考核人targetAdmin
					String targetAdmin = RSAUtils.decryptByPrivateKey(acl.getTargetAdmin(),
							RSAUtils.getPrivateKey(assessProjectService.getPrivateKey()));

					acl.setTargetAdmin(sysAdminService.get(targetAdmin).getAdminName());
					nlist.add(acl);
				}

			}
		}
		Map<String, Object> map = Maps.newHashMap();
		map.put("list", nlist);
		return map;
	}

	/**
	 * 根据projectid查询到它所对应的每套试题
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "formScore")
	public String formScore(HttpServletRequest request, HttpServletResponse response, Model model,
			AssessContentList bean) throws Exception {
		if (bean.getId() == null) {

		} else {
			bean = service.get(bean.getId());
			List<AssessProjectOption> list = projectOptionService.initQuery()
					.andEqualTo("projectId", bean.getProjectId()).findAll();
			List<AssessOption> optionlist = Lists.newArrayList();
			for (AssessProjectOption apo : list) {
				List<AssessOption> aolist = optionService.initQuery().andEqualTo("id", apo.getOptionId()).findStopTrue();
				if(aolist.size()!=0)
				optionlist.add(aolist.get(0));
			}
			model.addAttribute("bean", bean);
			model.addAttribute("optionlist", optionlist);

		}
		return this.toPage(request, response, model);
	}

	/**
	 * 打分,并把每套考核题的总分和每题的得分,分别加入到AssessContentList,AssessContentDetail中去
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param option
	 *            题目id
	 * @param bean
	 * @param markscope
	 *            每题分数
	 * @param ben
	 * @throws Exception
	 */
	@RequestMapping(value = "count")
	public void count(HttpServletRequest request, HttpServletResponse response, Model model, Integer[] option,
			AssessContentList bean, Integer[] markscope) throws Exception {

		bean = service.get(bean.getId());
		// 获取数据库中的公钥,进行加密
		String publickey = assessProjectService.get(bean.getProjectId()).getSecret();
		int sum = 0;
		AssessContentDetail ben = new AssessContentDetail();
		for (int i = 0; i < markscope.length; i++) {
			// 计算总分
			sum += markscope[i];
			// 加密每题的分数
			String eachpoint = RSAUtils.encryptByPublicKey(markscope[i] + "", RSAUtils.getPublicKey(publickey));
			ben.setPoint(eachpoint);
			ben.setListId(bean.getId());
			ben.setProjectId(bean.getProjectId());
			// 加入题目id
			ben.setOptionId(option[i]);

			contentDetailService.save(request, ben);
		}
		// 加密总分,int转化为字符串的话只要+""
		String totalpoint = RSAUtils.encryptByPublicKey(sum + "", RSAUtils.getPublicKey(publickey));
		// 加入总分
		bean.setPoint(totalpoint);
		bean.setAssessTime(new Date());
		bean.setAssessStatus("1");

		service.update(request, bean);

		// 每提交一次,AssessContent表中的aleadyAssessNum要加1
		String targetAdmin = bean.getTargetAdmin();
		Integer projectId = bean.getProjectId();
		AssessContentExample acExample = new AssessContentExample();
		AssessContentExample.Criteria acCriteria = acExample.createCriteria();
		acCriteria.andTargetAdminEqualTo(targetAdmin);
		acCriteria.andProjectIdEqualTo(projectId);
		AssessContent content = contentMapper.selectOneByExample(acExample);
		if (content.getAleadyAssessNum() == null) {
			content.setAleadyAssessNum(1);
		} else {
			content.setAleadyAssessNum(content.getAleadyAssessNum() + 1);
		}
		contentMapper.updateByExampleSelective(content, acExample);
	}

}
