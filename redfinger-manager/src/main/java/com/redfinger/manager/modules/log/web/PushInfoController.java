 package com.redfinger.manager.modules.log.web;
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
import com.redfinger.manager.common.domain.PushInfo;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.facility.service.PadService;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.push.service.PushInfoService;
@Controller
@RequestMapping(value="/log/pushInfo")
public class PushInfoController extends BaseController {
	@Autowired
	PushInfoService service;
	@Autowired
	UserService userService;
	@Autowired
	PadService padService;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}
	@ResponseBody
	@RequestMapping(value="list")
	public PageInfo<PushInfo>list(HttpServletRequest request,HttpServletResponse response,Model model,PushInfo bean)throws Exception{
		List<PushInfo> list=service.initQuery(bean)
			.andEqualTo("batchId", bean.getBatchId())
			.andLike("channelId",bean.getChannelId())
			.andGreaterThanOrEqualTo("createTime",DateUtils.parseDate( bean.getBeginTimeStr()))
			.andLessThan("createTime", DateUtils.parseDateAddOneDay(bean.getEndTimeStr()))
			.addOrderForce(bean.getSort(),bean.getOrder() )
			.addOrderClause("createTime", "desc")
			.pageAll(bean.getPage(), bean.getRows());
		for(PushInfo pushInfo : list){	
			    pushInfo.getMap().put("padCode",pushInfo.getPadId()==null?"":padService.load(pushInfo.getPadId()).getPadCode()==null?"":padService.load(pushInfo.getPadId()).getPadCode());
				pushInfo.getMap().put("userPhone",pushInfo.getUserId()==null?"":userService.load(pushInfo.getUserId()).getUserMobilePhone()==null?"":userService.load(pushInfo.getUserId()).getUserMobilePhone());	
		}
		    PageInfo<PushInfo> pageInfo = new PageInfo<PushInfo>(list);
	     	return pageInfo;
	}
}
