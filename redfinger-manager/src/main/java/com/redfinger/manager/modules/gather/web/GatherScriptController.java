package com.redfinger.manager.modules.gather.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfControl;
import com.redfinger.manager.common.domain.RfGatherScript;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.jsm.GatherProducer;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.modules.facility.service.ControlService;
import com.redfinger.manager.modules.gather.service.GatherScriptService;

@Controller
@RequestMapping(value = "/gather/script")
public class GatherScriptController extends BaseController {
	
	@Autowired
	GatherScriptService service;
	@Autowired
	private GatherProducer gatherProducer;
	@Autowired
	private ControlService controlService;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfGatherScript> list(HttpServletRequest request, HttpServletResponse response, Model model,RfGatherScript bean) throws Exception {
		List<RfGatherScript> list = service.initQuery().pageAll(bean.getPage(), bean.getRows());
		PageInfo<RfGatherScript> pageInfo = new PageInfo<RfGatherScript>(list);
		return pageInfo;
	}
	
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfGatherScript bean) throws Exception {
		if (bean.getScriptId() != null) {
			bean = service.get(bean.getScriptId());
			model.addAttribute("bean", bean);
		}
		return this.toPage(request, response, model);
	}
	
	
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfGatherScript bean) throws Exception {
		List<RfGatherScript> beans1 = service.initQuery().andEqualTo("scriptCode", bean.getScriptCode()).findAll();
		if(null!=beans1&&beans1.size()>0){
			throw new BusinessException("脚本编码重复");
		}
		List<RfGatherScript> beans2 = service.initQuery().andEqualTo("path", bean.getPath()).findAll();
		if(null!=beans2&&beans2.size()>0){
			throw new BusinessException("脚本路径重复");
		}
		
		if(null!=bean.getScriptId()){
			List<RfGatherScript> matchs = service.initQuery().andEqualTo("scriptId", bean.getScriptId()).singleDelTrue();
			if(null != matchs && matchs.size()>0){//如果有传id过来并且id已经使用了的，就改为updata
				bean.setScriptId(matchs.get(0).getScriptId());
				update(request, response, model, bean);
				return;
			}
		}
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfGatherScript bean) throws Exception {
		List<RfGatherScript> beans1 = service.initQuery().andEqualTo("scriptCode", bean.getScriptCode()).findAll();
		if(null!=beans1&&beans1.size()>0&&!beans1.get(0).getScriptCode().equals(bean.getScriptCode())){
			throw new BusinessException("脚本编码重复");
		}
		List<RfGatherScript> beans2 = service.initQuery().andEqualTo("path", bean.getPath()).findAll();
		if(null!=beans2&&beans2.size()>0&&!beans2.get(0).getPath().equals(bean.getPath())){
			throw new BusinessException("脚本路径重复");
		}
		service.update(request, bean);
	}
	
	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfGatherScript bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfGatherScript bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfGatherScript bean) throws Exception {
		service.remove(request, bean);
	}
	
	@RequestMapping("/test2")
	@ResponseBody
	public void test2(){
		gatherProducer.sendMessage("{\"opType\":\"monitorData\",\"deviceIp\":\"192.168.169.96\",\"scriptCode\":\"test_code2\",\"exStatus\":0,\"resultInfo\":\"{\\\"deviceInfo\\\":{\\\"deviceTemp\\\":54,\\\"CPUFrequence\\\":[{\\\"cpu0\\\":1800000},{\\\"cpu1\\\":1800000},{\\\"cpu2\\\":1800000},{\\\"cpu3\\\":1800000}],\\\"CPULoadAvg\\\":[{\\\"cpuLoadAvgOneMinute\\\":7.69},{\\\"cpuLoadAvgFiveMinute\\\":6.13},{\\\"cpuLoadAvgFifteenMinute\\\":5.27}],\\\"memUtili\\\":\\\"32.50%\\\",\\\"storageUtili\\\":\\\"41.17%\\\",\\\"kernelVersion\\\":\\\"3.10.0\\\",\\\"manageMain\\\":\\\"b7b597db9666359773b9ee6a4ccf5020\\\",\\\"manageAgent\\\":\\\"b146c7d115427915d02d3330f7013d01\\\",\\\"remotePlay\\\":\\\"20942a5a6e00e3fdd69e15722a4d2545\\\",\\\"deviceMac\\\":\\\"74:00:00:07:2c:77\\\",\\\"procInfo\\\":[{\\\"pid\\\":11912,\\\"vss\\\":\\\"503204K\\\",\\\"rss\\\":\\\"409384K\\\",\\\"pss\\\":\\\"395816K\\\",\\\"uss\\\":\\\"395472K\\\"},{\\\"pid\\\":3077,\\\"vss\\\":\\\"2032220K\\\",\\\"rss\\\":\\\"281016K\\\",\\\"pss\\\":\\\"207260K\\\",\\\"uss\\\":\\\"195044K\\\"},{\\\"pid\\\":9293,\\\"vss\\\":\\\"1152760K\\\",\\\"rss\\\":\\\"116248K\\\",\\\"pss\\\":\\\"72799K\\\",\\\"uss\\\":\\\"65948K\\\"},{\\\"pid\\\":3513,\\\"vss\\\":\\\"1670640K\\\",\\\"rss\\\":\\\"93476K\\\",\\\"pss\\\":\\\"44769K\\\",\\\"uss\\\":\\\"38884K\\\"}]},\\\"padInfo\\\":[{\\\"vmMac\\\":\\\"fe:fa:82:c0:19:f6\\\",\\\"vmIp\\\":\\\"192.168.168.105\\\"},{\\\"vmMac\\\":\\\"fe:c9:5b:b9:d2:93\\\",\\\"vmIp\\\":\\\"192.168.168.198\\\"},{\\\"vmMac\\\":\\\"fe:52:3d:59:25:fb\\\",\\\"vmIp\\\":\\\"192.168.168.197\\\"},{\\\"vmMac\\\":\\\"fe:ae:51:49:de:3a\\\",\\\"vmIp\\\":\\\"192.168.168.201\\\"}]}\"}");
	}
	
	@RequestMapping("/send")
	public void send(HttpServletRequest request, HttpServletResponse response, Model model, RfGatherScript bean){
		//bean = service.get(bean.getScriptId());
		
		List<RfControl> controlList = controlService.initQuery().andEqualTo("controlType", ConstantsDb.rfControlControlTypeManage()).andIsNotNull("controlQueueName").andNotEqualTo("controlQueueName", "").findDelTrue();
		boolean sendResult = false;
		List<String> queueNameList = Lists.newArrayList();
		if(controlList != null && controlList.size() > 0 ){
			for (RfControl control : controlList) {
				/** 2017/11/09发送jms, 去除重复的jms名称, 同名的只发送一条jms信息*/
				if(control != null && StringUtils.isNotBlank(control.getControlQueueName()) && !queueNameList.contains(control.getControlQueueName())){
					queueNameList.add(control.getControlQueueName());
					Map<String, Object> sendMap = new HashMap<String, Object>();
					sendMap.put("opType", "monitorConfig");
					String json = JsonUtils.ObjectToString(sendMap);
					log.info(json);
					gatherProducer.setDestination(new ActiveMQQueue(control.getControlQueueName()));
					gatherProducer.sendMessage(json);
					sendResult = true;
				}
			}
		}
		
		if(!sendResult){
			log.warn("opType=monitorConfig, send jms faild, control queue name is blank.");
			throw new BusinessException("发送失败!");
		}
		
	}
	

	
}