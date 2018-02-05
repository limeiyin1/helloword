package com.redfinger.manager.modules.assess.web;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Date;
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
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.AssessContent;
import com.redfinger.manager.common.domain.AssessContentDetail;
import com.redfinger.manager.common.domain.AssessContentList;
import com.redfinger.manager.common.domain.AssessOption;
import com.redfinger.manager.common.domain.AssessProject;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.RSAUtils;
import com.redfinger.manager.modules.assess.pojo.DetailPOJO;
import com.redfinger.manager.modules.assess.service.AssessContentDetailService;
import com.redfinger.manager.modules.assess.service.AssessContentListService;
import com.redfinger.manager.modules.assess.service.AssessContentService;
import com.redfinger.manager.modules.assess.service.AssessOptionService;
import com.redfinger.manager.modules.assess.service.AssessProjectService;
import com.redfinger.manager.modules.assess.service.SysAdminService;

@Controller
@RequestMapping(value = "/assess/detail")
public class AssessContentDetailController extends BaseController {

	@Autowired
	AssessProjectService service;
	@Autowired
	AssessContentService contentService;
	@Autowired
	AssessContentListService contentListService;
	@Autowired
	SysAdminService sysAdminService;
	@Autowired
	AssessContentDetailService contentDetailService;
	@Autowired
	AssessOptionService optionService;

	private static String userPrivateKey = null;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<AssessProject> list(HttpServletRequest request, HttpServletResponse response, Model model,
			AssessProject bean) throws Exception {
		Date time = new Date();
		List<AssessProject> list = service.initQuery(bean).andLike("name", bean.getName())
				.andEqualTo("enableStatus", "1").andLessThanOrEqualTo("beginTime", time)
				.addOrderClause("createTime", "desc").andIsNotNull("secret")
				.addOrderForce(bean.getSort(), bean.getOrder()).pageAll(bean.getPage(), bean.getRows());
		PageInfo<AssessProject> pageInfo = new PageInfo<AssessProject>(list);
		return pageInfo;
	}

	/**
	 * 考卷得分详细
	 * 
	 * @Title: formScore
	 * @return String 返回类型
	 * @param request
	 * @param response
	 * @param model
	 * @param sy
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "formOption")
	public String formScore(HttpServletRequest request, HttpServletResponse response, Model model, String sy, Integer id)
			throws Exception {
		String userPublicKey = null;
		if (StringUtils.isEmpty(sy) || id == null) {
			throw new BusinessException("私钥为空，请输入正确的私钥！");
		}
		try {
			sy = sy.replace("%2B", "+").replace(" ", "");
			userPublicKey = service.get(id).getSecret();
			RSAPublicKey publicKey = RSAUtils.getPublicKey(userPublicKey);
			RSAPrivateKey privateKey = RSAUtils.getPrivateKey(sy);
			RSAUtils.decryptByPrivateKey(RSAUtils.encryptByPublicKey("verification_privateKey", publicKey), privateKey);
			userPrivateKey = sy;
			request.getSession().setAttribute("userPrivateKey", userPrivateKey); // 保存私钥与session域中
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("私钥错误，请输入正确的私钥！");
		}
		List<AssessContent> list = contentService.initQuery().andEqualTo("projectId", id).findAll();
		for (AssessContent ac : list) {
			// 考核人
			String targetAdmin = ac.getTargetAdmin();
			// 考核总分
			String assessPoint = ac.getAssessPoint();
			// 考核额外加分
			String otherPoint = "0";
			if (ac.getOtherPoint() != null) {
				otherPoint = RSAUtils.decryptByPrivateKey(ac.getOtherPoint(), RSAUtils.getPrivateKey(userPrivateKey));
			}
			if (assessPoint == null) {
				if (ac.getNeedAssessNum() == ac.getAleadyAssessNum()) {
					// 计算总分
					// 考核ID
					Integer projectId = ac.getProjectId();
					List<AssessContentList> ac_list = contentListService.initQuery().andEqualTo("projectId", projectId).andEqualTo("assessStatus", "1")
							.andEqualTo("targetAdmin", targetAdmin).findAll();
					if (ac_list != null && ac_list.size() > 0) {
						if (ac_list.size() < 3) {
							Integer point = 0;
							for (AssessContentList acl : ac_list) {
								// 解密分数
								String pointS = RSAUtils.decryptByPrivateKey(acl.getPoint(),
										RSAUtils.getPrivateKey(userPrivateKey));
								point += Integer.parseInt(pointS);
							}
							assessPoint = point / ac_list.size() + "";
							AssessContent acBean = new AssessContent();
							acBean.setId(ac.getId());
							acBean.setAssessPoint(RSAUtils.encryptByPublicKey(assessPoint,
									RSAUtils.getPublicKey(userPublicKey)));
							contentService.update(request, acBean);
						} else {
							Integer[] arr = new Integer[ac_list.size()];
							int mark = 0;
							for (AssessContentList acl : ac_list) {
								// 解密分数
								String pointS = RSAUtils.decryptByPrivateKey(acl.getPoint(),
										RSAUtils.getPrivateKey(userPrivateKey));
								arr[mark] = Integer.parseInt(pointS);
								mark++;
							}
							assessPoint = countPoint(arr) + "";
							AssessContent acBean = new AssessContent();
							acBean.setId(ac.getId());
							acBean.setAssessPoint(RSAUtils.encryptByPublicKey(assessPoint,
									RSAUtils.getPublicKey(userPublicKey)));
							contentService.update(request, acBean);
						}

					}
				} else
					assessPoint = "0";
			} else
				// 解密总分
				assessPoint = RSAUtils.decryptByPrivateKey(assessPoint, RSAUtils.getPrivateKey(userPrivateKey));

			// 解密targetAdmin
			targetAdmin = RSAUtils.decryptByPrivateKey(targetAdmin, RSAUtils.getPrivateKey(service.getPrivateKey()));
			Integer sum = Integer.parseInt(assessPoint) + Integer.parseInt(otherPoint);
			// 用户code变成用户名称
			targetAdmin = sysAdminService.get(targetAdmin).getAdminName() + " —— " + assessPoint + "(+" + otherPoint
					+ ")=" + sum;
			ac.setTargetAdmin(targetAdmin);
		}
		model.addAttribute("acList", list);
		return this.toPage(request, response, model);
	}

	/**
	 * 获取所有适用员工的考核结果
	 * 
	 * @Title: getContentList
	 * @return List<AssessContentList> 返回类型
	 * @param request
	 * @param response
	 * @param model
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getContentList")
	public List<AssessContentList> getContentList(HttpServletRequest request, HttpServletResponse response,
			Model model, Integer id) throws Exception {
		List<AssessContentList> return_list = new ArrayList<AssessContentList>();
		if (id == null) {
			return return_list;
		}
		userPrivateKey = (String) request.getSession().getAttribute("userPrivateKey");
		AssessContent ac = contentService.get(id);
		String targetAdmin = ac.getTargetAdmin();
		Integer projectId = ac.getProjectId();
		// 解密targetAdmin
		targetAdmin = RSAUtils.decryptByPrivateKey(targetAdmin, RSAUtils.getPrivateKey(service.getPrivateKey()));
		List<AssessContentList> list = contentListService.initQuery().andEqualTo("projectId", projectId).findAll();
		for (AssessContentList acl : list) {
			String acl_targetAdmin = acl.getTargetAdmin();
			acl_targetAdmin = RSAUtils.decryptByPrivateKey(acl_targetAdmin,
					RSAUtils.getPrivateKey(service.getPrivateKey()));
			if (acl_targetAdmin.equals(targetAdmin)) {
				// 用户code变成用户名称
				acl_targetAdmin = sysAdminService.get(acl_targetAdmin).getAdminName();
				String acl_assessAdmin = acl.getAssessAdmin();
				acl_assessAdmin = RSAUtils.decryptByPrivateKey(acl_assessAdmin,
						RSAUtils.getPrivateKey(service.getPrivateKey()));
				acl_assessAdmin = sysAdminService.get(acl_assessAdmin).getAdminName();
				acl.setTargetAdmin(acl_targetAdmin);
				acl.setAssessAdmin(acl_assessAdmin);
				// 解密总分
				String point = acl.getPoint();
				if (point != null) {
					// 解密总分
					point = RSAUtils.decryptByPrivateKey(point, RSAUtils.getPrivateKey(userPrivateKey));
					acl.setPoint(point);
				}
				return_list.add(acl);
			}
		}
		return return_list;

	}

	/**
	 * 获取该考核员工的考核详情
	 * 
	 * @Title: getContentDetail
	 * @return List<AssessContentDetail> 返回类型
	 * @param request
	 * @param response
	 * @param model
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getContentDetail")
	public List<DetailPOJO> getContentDetail(HttpServletRequest request, HttpServletResponse response, Model model,
			String ids) throws Exception {
		List<DetailPOJO> list = new ArrayList<DetailPOJO>();
		String[] arr_ids = ids.split(",");
		if (arr_ids.length > 0) {
			for (String id : arr_ids) {
				Integer listId = Integer.parseInt(id);
				AssessContentList acl = contentListService.get(listId);
				String assessAdmin = acl.getAssessAdmin();
				assessAdmin = RSAUtils
						.decryptByPrivateKey(assessAdmin, RSAUtils.getPrivateKey(service.getPrivateKey()));
				assessAdmin = sysAdminService.get(assessAdmin).getAdminName(); // 考核人
				List<AssessContentDetail> acd_list = contentDetailService.initQuery().andEqualTo("listId", listId)
						.addOrderClause("listId", "desc").findAll();
				for (AssessContentDetail acd : acd_list) {
					DetailPOJO po = new DetailPOJO();
					Integer optionId = acd.getOptionId();
					AssessOption ao = optionService.get(optionId);
					if (ao != null) {
						po.setOptionName(ao.getName());
						po.setOptionPoint(ao.getPoint() + "");
					}
					po.setAssessAdmin(assessAdmin);
					// 解密总分
					String point = acd.getPoint();
					point = RSAUtils.decryptByPrivateKey(point, RSAUtils.getPrivateKey(userPrivateKey));
					po.setPoint(point);
					list.add(po);
				}
			}
		}
		return list;
	}

	/**
	 * 计算总分
	 * 
	 * @Title: countPoint
	 * @return Integer 返回类型
	 * @param arr
	 * @return
	 */
	private Integer countPoint(Integer[] arr) {
		int temp = 0;
		int sun = 0;
		for (int i = 0; i < arr.length - 1; i++) {
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[i] > arr[j]) {
					temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;
				}
			}
		}

		for (int i = 1; i < arr.length - 1; i++) {
			sun += arr[i];
		}
		return sun / (arr.length - 2);

	}
}
