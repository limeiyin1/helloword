package com.redfinger.manager.modules.label.web;

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
import com.redfinger.manager.common.domain.RfLabel;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.RfUserLabel;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.modules.label.service.LabelService;
import com.redfinger.manager.modules.label.service.UserLabelService;
import com.redfinger.manager.modules.member.service.UserService;

@Controller
@RequestMapping(value = "/label/userLabel")
public class UserLabelController  extends BaseController{

	@Autowired
	private UserLabelService service;
	@Autowired
	private LabelService labelService;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		List<RfLabel> labels = labelService.initQuery().andEqualTo("labelType", ConstantsDb.labelTypeUser()).findStopTrue();
		model.addAttribute("labels", labels);
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfUserLabel> list(HttpServletRequest request,
			HttpServletResponse response, Model model, RfUserLabel bean,String userMobilePhone)
			throws Exception {
		List<Integer> userIds = new ArrayList<Integer>();
		if(StringUtils.isNotBlank(userMobilePhone)){
			List<RfUser> list = userService.initQuery()
					.andEqualTo("userMobilePhone", userMobilePhone).findAll();
			if(null != list && list.size() > 0){
				userIds = Collections3.extractToList(list, "userId");
			}else{
				userIds.add(-1);
			}
		}
		
		List<RfUserLabel> list = service.initQuery(bean)
				.andEqualTo("labelId", bean.getLabelId())
				.andIn("userId", userIds)
				.addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		if(null != list && list.size() > 0 ){
			for (RfUserLabel userLabel : list) {
				userLabel.getMap().put("userMobilePhone", userService.get(userLabel.getUserId()).getUserMobilePhone());
				userLabel.getMap().put("labelCode", labelService.get(userLabel.getLabelId()).getLabelCode());
				userLabel.getMap().put("labelName", labelService.get(userLabel.getLabelId()).getLabelName());
			}
		}
		
		PageInfo<RfUserLabel> pageInfo = new PageInfo<RfUserLabel>(list);

		return pageInfo;
	}

	
	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response,
			Model model, RfUserLabel bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response,
			Model model, RfUserLabel bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request,
			HttpServletResponse response, Model model, RfUserLabel bean)
			throws Exception {
		service.remove(request, bean);
	}
}
