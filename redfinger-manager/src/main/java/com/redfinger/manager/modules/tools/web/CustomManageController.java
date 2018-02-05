package com.redfinger.manager.modules.tools.web;

import java.util.Date;
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
import com.redfinger.manager.common.domain.RfCustom;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.RfCustomGroup;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.sys.service.AdminService;
import com.redfinger.manager.modules.tools.service.CustomManageService;

/** 
 * @Description 客服管理
 * @author yirongze 
 * @date 2017年8月1日 上午9:06:04 
 */
@Controller
@RequestMapping(value = "/tools/custom_manage")
public class CustomManageController extends BaseController {
	@Autowired
	CustomManageService service;
	@Autowired
	AdminService adminService;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request,HttpServletResponse response,Model model) throws Exception {
		model.addAttribute("yesOrNo",YesOrNoStatus.DICT_MAP);
		model.addAttribute("RfCustomGroup",RfCustomGroup.CUSTOM_GROUP_MAP);
		return this.toPage(request,response,model);
	}
	
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request,HttpServletResponse response,Model model,RfCustom bean)throws Exception {
		if (bean.getCustomId() != null) {
			bean = service.get(bean.getCustomId());
			model.addAttribute("bean",bean);
		}
		model.addAttribute("RfCustomGroup",RfCustomGroup.CUSTOM_GROUP_MAP);
		model.addAttribute("yesOrNo",YesOrNoStatus.DICT_MAP);
		return this.toPage(request,response,model);
	}
	
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request,HttpServletResponse response,Model model,RfCustom bean) throws Exception {
		if(bean==null){
			throw new BusinessException("参数传不过！请填好表单");
		}
		if(StringUtils.isNotBlank(bean.getEnableTimeStr())){
			bean.setEnableTime(DateUtils.parseDate(bean.getEnableTimeStr()));
		}
		//早中晚班组优先录入班长
		if(bean.getCustomGroup()==1||bean.getCustomGroup()==2||bean.getCustomGroup()==3||bean.getCustomGroup()==4){
			int countLeader =service
					.initQuery()
					.andEqualTo("isLeader", "1")
					.andEqualTo("customGroup", bean.getCustomGroup())
					.countDelTrue();
			if(countLeader<1&&bean.getIsLeader().equals("0")){
				throw new BusinessException("分组优先录入班长");
			}
		}
		if(StringUtils.isNotBlank(bean.getCustomName())){
			int countSameCustomName =service
					.initQuery()
					.andEqualTo("customName", bean.getCustomName())
					.countDelTrue();
			if(countSameCustomName>0){
				throw new BusinessException("不得有相同的客服名称，若要调整人员请先删除要调整的人员！");
			}
		}
		service.save(request,bean);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfCustom> list(HttpServletRequest request,HttpServletResponse response,Model model,RfCustom bean)throws Exception {
		List<RfCustom> list = service
				.initQuery(bean)
				.andLike("customName",bean.getCustomName())
				.andEqualTo("customGroup",bean.getCustomGroup())
				.andEqualTo("isLeader", bean.getIsLeader())
				.andEqualTo("enableStatus",bean.getEnableStatus())
				.addOrderClause("createTime","desc nulls last,custom_id desc")
				.addOrderForce(bean.getSort(),bean.getOrder())
            	.pageDelTrue(bean.getPage(),bean.getRows());
		PageInfo<RfCustom> pageInfo = new PageInfo<RfCustom>(list);
		return pageInfo;
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request,HttpServletResponse response,Model model,RfCustom bean) throws Exception {
		String[] idArray=StringUtils.split(bean.getIds(),",");
		Date date =new Date();
		for(String idStr:idArray){
			RfCustom custom = service.get(Integer.parseInt(idStr));
			if(DateUtils.formatDate(date,"yyyy-MM-dd").equals(DateUtils.formatDate(custom.getEnableTime(),"yyyy-MM-dd"))){
				throw new BusinessException("结束排班日期和开始排班日期不得在同一天");
			}
			if(date.before(custom.getEnableTime())){
				throw new BusinessException("结束排班日期不得在开始排班日期之前");
			}
			bean.setEnableTime(custom.getEnableTime());
			bean.setDisableTime(DateUtils.parseDate(DateUtils.formatDate(date,"yyyy-MM-dd")));
			bean.setEnableStatus(ConstantsDb.globalEnableStatusStop());
			bean.setCustomId(Integer.parseInt(idStr));
			service.update(request,bean);
		}
	}
	
	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response,Model model, RfCustom bean) throws Exception {
		String[] idArray=StringUtils.split(bean.getIds(),",");
		for(String idStr:idArray){
			RfCustom custom = service.get(Integer.parseInt(idStr));
			bean.setEnableTime(custom.getEnableTime());
			bean.setDisableTime(null);
			bean.setEnableStatus(ConstantsDb.globalEnableStatusNomarl());
			bean.setCustomId(Integer.parseInt(idStr));
			service.update(request,bean);
		}
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request,HttpServletResponse response,Model model,RfCustom bean)throws Exception {
		String[] idArray=StringUtils.split(bean.getIds(),",");
		for(String idStr:idArray){
			RfCustom custom = service.get(Integer.parseInt(idStr));
			if(custom.getDisableTime()!=null){
				if(DateUtils.getMonth().equals(DateUtils.formatDate(custom.getDisableTime(),"MM"))){
					throw new BusinessException("不能删除，因为此人此月有排班或空白展示，若需删除请次月删除！");
				}
			}
		}
		service.delete(request,bean);
	}
}