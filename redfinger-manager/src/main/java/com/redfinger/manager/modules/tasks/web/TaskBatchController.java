package com.redfinger.manager.modules.tasks.web;


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
import com.redfinger.manager.common.domain.RfGame;
import com.redfinger.manager.common.domain.RfPadTask;
import com.redfinger.manager.common.domain.RfPadTaskBatch;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.mapper.RfDeviceMapper;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.facility.service.PadService;
import com.redfinger.manager.modules.game.service.GameService;
import com.redfinger.manager.modules.log.service.PadTaskService;
import com.redfinger.manager.modules.tasks.jms.ProducterMessageSender;
import com.redfinger.manager.modules.tasks.service.TaskBatchService;

@Controller
@RequestMapping(value = "/task/taskBatch")
public class TaskBatchController extends BaseController {
	@Autowired
	TaskBatchService service;
	@Autowired
	GameService gameService;
	@Autowired
	ProducterMessageSender pro;
	@Autowired
	PadTaskService padTaskService;
	@Autowired
	PadService padService;
	@Autowired
	RfDeviceMapper deviceMapper;
	
	
	@RequestMapping(value = "")
public String index(HttpServletRequest request,HttpServletResponse response, Model model) throws Exception {
	return this.toPage(request, response, model);
}


@ResponseBody
@RequestMapping(value = "list")
public PageInfo<RfPadTaskBatch> list(HttpServletRequest request,HttpServletResponse response, Model model, RfPadTaskBatch bean) throws Exception {
	List<RfPadTaskBatch> list = service.initQuery(bean)
			.andEqualTo("remark", bean.getRemark())
			.andLike("name", bean.getName())
			.andLike("gameName", bean.getGameName())
			.andLike("taskStatus", bean.getTaskStatus())
			.andLike("creater", bean.getCreater())
			.andLike("packageName", bean.getPackageName())
			.andGreaterThanOrEqualTo("createTime",DateUtils.parseDate(bean.getBeginTimeStr()))
			.andLessThan("createTime", DateUtils.parseDateAddOneDay(bean.getEndTimeStr()))
			.addOrderClause("createTime", "desc")
			.addOrderForce(bean.getSort(), bean.getOrder())
			.pageDelTrue(bean.getPage(), bean.getRows());
	PageInfo<RfPadTaskBatch> pageInfo = new PageInfo<RfPadTaskBatch>(list);
	return pageInfo;
}

@RequestMapping(value = "form")
public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfPadTaskBatch bean) throws Exception {
	model.addAttribute("bean",bean);
	return this.toPage(request, response, model);
}

@RequestMapping(value = "formAPP")
public String formAPP(HttpServletRequest request, HttpServletResponse response, Model model, RfPadTaskBatch bean) throws Exception {
    List<RfGame>gameList=gameService.initQuery().findStopTrue();
	model.addAttribute("gameList", gameList);
	model.addAttribute("bean",bean);
	return this.toPage(request, response, model);
}
@RequestMapping(value = "pad_install")
public void pad_install(HttpServletRequest request, HttpServletResponse response, Model model, RfPadTaskBatch bean,String gameId,String padCodes) throws Exception {
	this.save(request, response, model, bean, gameId, padCodes);
}

@RequestMapping(value = "pad_check_app_run")
public void pad_check_app_run(HttpServletRequest request, HttpServletResponse response, Model model, RfPadTaskBatch bean,String gameId,String padCodes) throws Exception {
	this.save(request, response, model, bean, gameId, padCodes);
}
@RequestMapping(value = "pad_check_app_version")
public void pad_check_app_version(HttpServletRequest request, HttpServletResponse response, Model model, RfPadTaskBatch bean,String gameId,String padCodes) throws Exception {
	this.save(request, response, model, bean, gameId, padCodes);
}
@RequestMapping(value = "pad_check_app_install")
public void pad_check_app_install(HttpServletRequest request, HttpServletResponse response, Model model, RfPadTaskBatch bean,String gameId,String padCodes) throws Exception {
	this.save(request, response, model, bean, gameId, padCodes);
}
@RequestMapping(value = "pad_uninstall")
public void pad_uninstall(HttpServletRequest request, HttpServletResponse response, Model model, RfPadTaskBatch bean,String gameId,String padCodes) throws Exception {
	this.save(request, response, model, bean, gameId, padCodes);
}
@RequestMapping(value = "pad_reboot")
public void pad_reboot(HttpServletRequest request, HttpServletResponse response, Model model, RfPadTaskBatch bean,String gameId,String padCodes) throws Exception {
	this.save(request, response, model, bean, gameId, padCodes);
}
@RequestMapping(value = "pad_screencap")
public void pad_screencap(HttpServletRequest request, HttpServletResponse response, Model model, RfPadTaskBatch bean,String gameId,String padCodes) throws Exception {
	this.save(request, response, model, bean, gameId, padCodes);
}
@RequestMapping(value = "vm_screencap")
public void vm_screencap(HttpServletRequest request, HttpServletResponse response, Model model, RfPadTaskBatch bean,String gameId,String padCodes) throws Exception {
	this.save(request, response, model, bean, gameId, padCodes);
}
@RequestMapping(value = "pad_set_time")
public void pad_set_time(HttpServletRequest request, HttpServletResponse response, Model model, RfPadTaskBatch bean,String gameId,String padCodes) throws Exception {
	this.save(request, response, model, bean, gameId, padCodes);
}
@RequestMapping(value = "pad_reset")
public void pad_reset(HttpServletRequest request, HttpServletResponse response, Model model, RfPadTaskBatch bean,String gameId,String padCodes) throws Exception {
	this.save(request, response, model, bean, gameId, padCodes);
}
@RequestMapping(value = "pad_ping")
public void pad_ping(HttpServletRequest request, HttpServletResponse response, Model model, RfPadTaskBatch bean,String gameId,String padCodes) throws Exception {
	this.save(request, response, model, bean, gameId, padCodes);
}
@RequestMapping(value = "device_ping")
public void device_ping(HttpServletRequest request, HttpServletResponse response, Model model, RfPadTaskBatch bean,String padCodes) throws Exception {
	this.saveDevice(request, response, model, bean, padCodes);
}
private void saveDevice(HttpServletRequest request, HttpServletResponse response, Model model, RfPadTaskBatch bean, String padCodes) throws Exception {
	if(padCodes==null){
		throw new BusinessException("请输入设备CODE");
	}
	service.saveBatchDevicePing(request, bean,padCodes);
	pro.send(bean.getId().toString());
	
}


@RequestMapping(value = "pad_restart")
public void pad_restart(HttpServletRequest request, HttpServletResponse response, Model model, RfPadTaskBatch bean,String gameId,String padCodes) throws Exception {
	this.save(request, response, model, bean, gameId, padCodes);
}
//卸载小米插件
@RequestMapping(value = "pad_uninstallXM")
public void pad_uninstall(HttpServletRequest request, HttpServletResponse response, Model model, RfPadTaskBatch bean,String padCodes) throws Exception {
	this.saveXiaoMi(request, response, model, bean, padCodes);
}
public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfPadTaskBatch bean,String gameId,String padCodes) throws Exception {
	if(gameId!=null){
		if(!gameId.equals("com.redfinger.gamemanage")){
			RfGame game=gameService.get(Integer.parseInt(gameId));
			if(game==null){
				throw new BusinessException("错误的游戏名");
			}
	
			bean.setGameName(game.getGameName());
			bean.setPackageName(game.getGamePackageName());
		} else {
			bean.setGameName("启动器");
			bean.setPackageName("com.redfinger.gamemanage");
		}		
	}
	if(padCodes==null){
		throw new BusinessException("请输入设备CODE");
	}
	service.saveBatch(request, bean,padCodes);
	pro.send(bean.getId().toString());
}

public void saveXiaoMi(HttpServletRequest request, HttpServletResponse response, Model model, RfPadTaskBatch bean,String padCodes) throws Exception {
		bean.setGameName("小米插件");
		bean.setPackageName("com.xiaomi.gamecenter.sdk.service");
	if(padCodes==null){
		throw new BusinessException("请输入设备CODE");
	}
	service.saveBatch(request, bean,padCodes);
	pro.send(bean.getId().toString());
}

@RequestMapping(value = "look")
public String look(HttpServletRequest request, HttpServletResponse response, Model model, RfPadTaskBatch bean) throws Exception {
	bean = service.get(bean.getId());
	model.addAttribute("bean", bean);
	return this.toPage(request, response, model);
}

@ResponseBody
@RequestMapping(value = "tasklist")
public PageInfo<RfPadTask> tasklist(HttpServletRequest request, HttpServletResponse response, Model model, RfPadTask bean) throws Exception {
	List<RfPadTask> list = padTaskService.initQuery(bean)
			.andEqualTo("taskResultStatus",bean.getTaskResultStatus())
			.andLike("padCode", bean.getPadCode())
			.andEqualTo("batchId", bean.getBatchId())
			.addOrderClause("createTime", "desc")
			.addOrderForce(bean.getSort(), bean.getOrder())
			.pageDelTrue(bean.getPage(), bean.getRows());
		for (RfPadTask rfPadTask : list) {
			if ("device_ping".equals(rfPadTask.getCommandType())) {
				rfPadTask.getMap().put("IP", deviceMapper.selectByPrimaryKey((rfPadTask.getPadId())).getDeviceIp());
			} else {
				rfPadTask.getMap().put("IP", padService.load(rfPadTask.getPadId()).getPadIp());
			}
		}
	PageInfo<RfPadTask> pageInfo = new PageInfo<RfPadTask>(list);
	return pageInfo;
}
//重启remote-play重启gamemanage
@RequestMapping(value = "pad_kill_pid")
public void pad_kill_pid(HttpServletRequest request, HttpServletResponse response, Model model,String padCodes,String remark,String... name) throws Exception {
	RfPadTaskBatch bean=new RfPadTaskBatch();
	bean.setRemark(remark);
	service.saveKillBatch(request, bean,padCodes,name);
	pro.send(bean.getId().toString());
}
//kill-agent
@RequestMapping(value="pad_kill_agent")
public void pad_kill_agent(HttpServletRequest request, HttpServletResponse response, Model model,String padCodes,String remark,String... name) throws Exception {
	RfPadTaskBatch bean=new RfPadTaskBatch();
	bean.setRemark(remark);
	service.saveKillAgent(request, bean,padCodes,name);
	pro.send(bean.getId().toString());
}
}
	