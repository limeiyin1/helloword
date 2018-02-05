package com.redfinger.manager.modules.log.web;

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
import com.redfinger.manager.common.domain.ViewLogQixiActivity;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.log.service.LogQiXiActivityService;

@Controller
@RequestMapping(value = "/log/qixi")
public class LogQiXiController extends BaseController {
	@Autowired
	LogQiXiActivityService service;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		return this.toPage(request, response, model);

	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<ViewLogQixiActivity> list(HttpServletRequest request, HttpServletResponse response, Model model, ViewLogQixiActivity bean,
			String signTimeGt,String signTimeLt) throws Exception {
	Date start = null;
	Date end = null;
	if(StringUtils.isNotBlank(signTimeGt)){
		start = DateUtils.parseDate(signTimeGt + " 00:00:00");
	}
	if(StringUtils.isNotBlank(signTimeLt)){
		end = DateUtils.parseDate(signTimeLt + " 23:59:59");
	}
		
	 service.initQuery(bean)
			.andEqualTo("inviterPhone", bean.getInviterPhone())
			.andEqualTo("inviteePhone", bean.getInviteePhone())
			.andEqualTo("padCode", bean.getPadCode())
			.andGreaterThanOrEqualTo("signTime", start)
			.andLessThanOrEqualTo("signTime", end);
		if(YesOrNoStatus.YES.equals(bean.getPadBindStatus())){
			service.andEqualTo("padBindStatus", bean.getPadBindStatus());
		}
		
		if(YesOrNoStatus.NO.equals(bean.getPadBindStatus())){
			service.andIsNull("padBindStatus");
		}
				
		List<ViewLogQixiActivity> list = service.pageDelTrue(bean.getPage(), bean.getRows());
		
		String timeStr = "";
		for (ViewLogQixiActivity qixiActivity : list) {
			Date inviteeExpireTime = qixiActivity.getInviteeExpireTime();
			Date bindTime = qixiActivity.getBindPadTime();
			if(null != inviteeExpireTime && null != bindTime){
				Long time = inviteeExpireTime.getTime() - bindTime.getTime();
				long days = time / (1000 * 60 * 60 * 24);  
				long hours = (time % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);  
				long minutes = (time % (1000 * 60 * 60)) / (1000 * 60);  
				long seconds = (time % (1000 * 60)) / 1000;  
				timeStr =  days + "天" + hours + "小时" + minutes + "分"+ seconds + "秒";  
				qixiActivity.getMap().put("timeStr", timeStr);
			}
			
		}
		
		
		PageInfo<ViewLogQixiActivity> pageInfo = new PageInfo<ViewLogQixiActivity>(list);	
		return pageInfo;
	}
}








