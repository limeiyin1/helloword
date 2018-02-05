package com.redfinger.manager.modules.plugin.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfPlugin;
import com.redfinger.manager.common.domain.RfPluginExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfPluginMapper;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;

@Transactional
@Service
@PrimaryKeyAnnotation(field="pluginId")
public class PluginService extends BaseService<RfPlugin, RfPluginExample, RfPluginMapper> {
	@Autowired
	RfPluginMapper mapper;
	
	
	public void saveParentVersion(HttpServletRequest request,RfPlugin bean){
		
		bean.setCreater(SessionUtils.getCurrentUserId(request));
		bean.setCreateTime(new Date());
		bean.setStatus(YesOrNoStatus.YES);
		bean.setEnableStatus(YesOrNoStatus.NO);
		mapper.insertSelective(bean);
		
	}

/*	public List<RfPlugin> selectPlugin(String parentChannelCode,
			String parentVersionCode, String osType) {
		RfPluginExample example = new RfPluginExample();
		example.createCriteria().andPluginChannelCodeEqualTo(parentChannelCode)
		.andPluginVersionCodeEqualTo(parentVersionCode)
		.andPluginOsTypeEqualTo(osType).andEnableStatusEqualTo(YesOrNoStatus.YES)
		.andStatusEqualTo(YesOrNoStatus.YES);
		List<RfPlugin> list = mapper.selectByExample(example);
		return list;
	}*/
	
	public void updatePlugin(RfPlugin bean){
		mapper.updateByPrimaryKeySelective(bean);
	}
	
	public void savePlugin(RfPlugin bean){
		mapper.insertSelective(bean);
	}

	public void updatePlugin(RfPlugin plugin, RfPlugin bean,HttpServletRequest request) {
		plugin.setPluginCode(bean.getPluginCode());
		plugin.setPluginVersionCode(bean.getPluginVersionCode());
		plugin.setChannelVersionCode(bean.getChannelVersionCode());
		plugin.setPluginVersionName(bean.getPluginVersionName());
		plugin.setPluginVersionMust(bean.getPluginVersionMust());
		plugin.setPluginUrl(bean.getPluginUrl());
		plugin.setPluginDesc(bean.getPluginDesc());
		
		plugin.setModifier(SessionUtils.getCurrentUserId(request));
		plugin.setModifyTime(new Date());
		
		mapper.updateByPrimaryKeySelective(plugin);
	}

	public void savePlugin(RfPlugin plugin, RfPlugin bean,HttpServletRequest request) {
		plugin.setPluginCode(bean.getPluginCode());
		plugin.setPluginVersionCode(bean.getPluginVersionCode());
		plugin.setChannelVersionCode(bean.getChannelVersionCode());
		plugin.setPluginVersionName(bean.getPluginVersionName());
		plugin.setPluginVersionMust(bean.getPluginVersionMust());
		plugin.setPluginUrl(bean.getPluginUrl());
		plugin.setPluginDesc(bean.getPluginDesc());

		plugin.setStatus(ConstantsDb.globalEnableStatusNomarl());
		plugin.setEnableStatus(ConstantsDb.globalStatusNomarl());
		plugin.setCreater(SessionUtils.getCurrentUserId(request));
		plugin.setCreateTime(new Date());
		
		mapper.insertSelective(plugin);
	}
}
