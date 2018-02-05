package com.redfinger.manager.modules.market.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfDevice;
import com.redfinger.manager.common.domain.RfPad;
import com.redfinger.manager.common.domain.RfVersion;
import com.redfinger.manager.common.domain.RfVersionExample;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfPadVersionMapper;
import com.redfinger.manager.common.mapper.RfVersionMapper;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "versionId")
public class MarketVersionService extends BaseService<RfVersion, RfVersionExample, RfVersionMapper> {

	@Autowired
	RfPadVersionMapper padVersionMapper;
	@Autowired
	RfVersionMapper rfVersionMapper;
	
	public void editBatchVersion(HttpServletRequest request,RfVersion bean,
			String appTime,String appName,String linkUrl){
		if(StringUtils.isNotBlank(bean.getIds())){
			String ids[] = bean.getIds().split(",");
			for (String id : ids) {
				if(StringUtils.isNotBlank(id)){
					RfVersion rfVersion = this.get(Integer.parseInt(id));
					if(null != rfVersion){
						rfVersion.setVersionCode(bean.getVersionCode());
						rfVersion.setVersionName(bean.getVersionName());
						rfVersion.setVersionDesc(bean.getVersionName());
						rfVersion.setDownloadUrl(linkUrl+appTime+"/"+appName);
						rfVersion.setUpdateUrl(linkUrl+appTime+"/"+appName);
						rfVersion.setModifier(SessionUtils.getCurrentUserId(request));
						rfVersion.setModifyTime(new Date());
						if(!(rfVersionMapper.updateByPrimaryKey(rfVersion)>0)){
							throw new BusinessException("批量修改android版本失败！");
						}
					}
				}
			}
		}
	}
	
	public void doBindPad(HttpServletRequest request,RfPad pad,RfDevice device,Integer versionId){
		RfVersion version = this.get(versionId);
		if(null == version){
			throw new BusinessException("启动器"+versionId+"不存在");
		}
		version.setModifier(SessionUtils.getCurrentUserId(request));
		version.setModifyTime(new Date());
		version.setIsAll(YesOrNoStatus.NO);
		if(!(rfVersionMapper.updateByPrimaryKey(version)>0)){
			throw new BusinessException("修改启动器不成功！");
		}
		
		padVersionMapper.deleteByVersionId(versionId);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("enableStatus", YesOrNoStatus.YES);
		map.put("status", YesOrNoStatus.YES);
		map.put("versionId", versionId);
		if(StringUtils.isNotEmpty(pad.getPadName())){
			map.put("padName", pad.getPadName());
		}
		if(StringUtils.isNotEmpty(pad.getPadCode())){
			map.put("padCode", pad.getPadCode());
		}
		if(StringUtils.isNotEmpty(pad.getPadIp())){
			map.put("padIp", pad.getPadIp());
		}
		
		if(StringUtils.isNotEmpty(device.getDeviceName())){
			map.put("deviceName", device.getDeviceName());
		}
		
		if(StringUtils.isNotEmpty(device.getDeviceIp())){
			map.put("deviceIp", device.getDeviceIp());
		}
		map.put("creater", SessionUtils.getCurrentUserId(request));
		
		padVersionMapper.insertByPad(map);
	}
	
	public void doAllPad(HttpServletRequest request,Integer versionId){
		RfVersion version = this.get(versionId);
		if(null == version){
			throw new BusinessException("启动器"+versionId+"不存在");
		}
		version.setModifier(SessionUtils.getCurrentUserId(request));
		version.setModifyTime(new Date());
		version.setIsAll(YesOrNoStatus.YES);
		if(!(rfVersionMapper.updateByPrimaryKey(version)>0)){
			throw new BusinessException("修改启动器不成功！");
		}
		
		padVersionMapper.deleteByVersionId(versionId);
	}
	
	public int updateVersion(RfVersion rfVersion){
		return rfVersionMapper.updateByPrimaryKey(rfVersion);
	}
	
	public int saveVersion(RfVersion rfVersion){
		return rfVersionMapper.insertSelective(rfVersion);
	}
}
