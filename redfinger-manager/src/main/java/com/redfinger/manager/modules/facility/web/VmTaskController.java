package com.redfinger.manager.modules.facility.web;

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
import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.RfDevice;
import com.redfinger.manager.common.domain.RfPad;
import com.redfinger.manager.common.domain.RfVmTask;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.modules.facility.service.ControlService;
import com.redfinger.manager.modules.facility.service.DeviceService;
import com.redfinger.manager.modules.facility.service.PadService;
import com.redfinger.manager.modules.facility.service.VmTaskService;

@Controller
@RequestMapping(value = "/facility/vmTask")
public class VmTaskController extends BaseController  {
	@Autowired 
	VmTaskService service;
	@Autowired
	DeviceService deviceService;
	@Autowired
	PadService padService;
	@Autowired
    ControlService controlService;
	
	@RequestMapping(value="")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model)throws Exception{
		return this.toPage(request, response, model);
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfVmTask> list(HttpServletRequest request, HttpServletResponse response, Model model, RfVmTask bean,String deviceCode,String padCode) throws Exception {
	List<Integer> padId = Lists.newArrayList();
	List<Integer> deviceId = Lists.newArrayList();
		if (StringUtils.isNotBlank(padCode)) {
			List<RfPad> padlist = padService.getPadByPadCodes(padCode);
			if (null != padlist && !Collections3.isEmpty(padlist)) {
				padId = Collections3.extractToList(padlist, "padId");
			}else{
				padId.add(-1);
			}
		}
		if (null != deviceCode && !"".equals(deviceCode)) {
			List<RfDevice> devicelist = deviceService.getDeviceByPadCodes(deviceCode);
			deviceId = Collections3.extractToList(devicelist, "deviceId");
		}

		List<RfVmTask> list = service.initQuery(bean)
				.andIn("deviceId", deviceId)
		        .andIn("padId", padId)
				.andEqualTo("deviceName", bean.getTaskResultStatus())
				.andEqualTo("taskType", bean.getTaskType())
				.andEqualTo("taskStatus", bean.getTaskStatus())
				.addOrderClause("createTime", "desc nulls last")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		for (RfVmTask RfVmTask : list) {
			RfVmTask.getMap().put("manageControlName", RfVmTask.getDeviceId() == null ? "--" :deviceService.load(RfVmTask.getDeviceId())==null?"--": controlService.load(deviceService.load(RfVmTask.getDeviceId()).getDeviceManageControlId()).getControlName());
			RfVmTask.getMap().put("deviceCode",(null==RfVmTask.getDeviceId()||"".equals(RfVmTask.getDeviceId()))?"--":deviceService.load(RfVmTask.getDeviceId()).getDeviceCode());
			RfVmTask.getMap().put("padCode",(null==RfVmTask.getPadId()||"".equals(RfVmTask.getPadId()))?"--":padService.load(RfVmTask.getPadId()).getPadCode());
		}
		PageInfo<RfVmTask> pageInfo = new PageInfo<RfVmTask>(list);
		return pageInfo;
	}
	
	

	@RequestMapping(value = "vmTask")
	public void vmTask(HttpServletRequest request, HttpServletResponse response, Model model,RfVmTask bean) throws Exception {
		Date time = service.vmTask(request, bean);
		if (null != time) {
			service.jmsSend(time, bean, request);
		} else {
			throw new BusinessException("设备任务执行失败");
		}
	}


}
