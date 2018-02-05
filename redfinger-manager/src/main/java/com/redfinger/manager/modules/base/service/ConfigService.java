package com.redfinger.manager.modules.base.service;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.constants.ConfigConstantsDb;
import com.redfinger.manager.common.domain.SysConfig;
import com.redfinger.manager.common.domain.SysConfigExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.SysConfigMapper;
import com.redfinger.manager.modules.log.service.LogPadService;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "configCode")
public class ConfigService extends BaseService<SysConfig, SysConfigExample, SysConfigMapper> {

	@Autowired
	LogPadService logPadService;
	@Autowired
	SysConfigMapper sysConfigMapper;

	public void updateMap(HttpServletRequest request, SysConfig config) throws Exception {
		Iterator<String> sysConfig = config.getMap().keySet().iterator();
		SysConfig bean = new SysConfig();
		while (sysConfig.hasNext()) {
			String key = sysConfig.next().toString();
			bean.setConfigCode(key);
			bean.setConfigValue(config.getMap().get(key).toString());
			this.update(request, bean);
		}

	}
	
	public void updateConfig(HttpServletRequest request, SysConfig config) throws Exception {
		this.update(request, config);
	}

	/**
	 * 更新【系统当天剩余可以绑定普通设备数】
	 * 
	 * @throws Exception
	 */
	public synchronized void resetConfigLeftBindPadCount() throws Exception {
		String key = "config_left_bind_pad_count";
		Integer value = Integer.parseInt(ConfigConstantsDb.configMaxBindPadCount());
		SysConfig config = new SysConfig();
		config.setConfigCode(key);
		config.setConfigValue(value.toString());
		this.update(null, config);
	}

	public synchronized void cutOneConfigLeftBindPadCount() throws Exception {
		String key = "config_left_bind_pad_count";
		Integer value = Integer.parseInt(ConfigConstantsDb.configLeftBindPadCount()) - 1;
		SysConfig config = new SysConfig();
		config.setConfigCode(key);
		config.setConfigValue(value.toString());
		this.update(null, config);
	}

	public synchronized void updateConfigLeftBindPadCount(int diff) throws Exception {
		String key = "config_left_bind_pad_count";
		Integer v = Integer.parseInt(ConfigConstantsDb.configLeftBindPadCount()) + diff;
		SysConfig config = new SysConfig();
		config.setConfigCode(key);
		config.setConfigValue(v.toString());
		this.update(null, config);

	}
	// 渠道配置
	public synchronized void updateConfigChannelBindPadCount(int diff) throws Exception {
		String key = "config_channel_bind_pad_count";
		Integer v = Integer.parseInt(ConfigConstantsDb.configChannelBindPadCount()) + diff;
		SysConfig config = new SysConfig();
		config.setConfigCode(key);
		config.setConfigValue(v.toString());
		this.update(null, config);

	}
	// 体验配置
	public synchronized void updateConfigProbationalBindPadCount(int diff) throws Exception {
		String key = "config_usable_probation_pad";
		Integer v = Integer.parseInt(ConfigConstantsDb.configUsableProbationPad()) + diff;
		SysConfig config = new SysConfig();
		config.setConfigCode(key);
		config.setConfigValue(v.toString());
		this.update(null, config);

	}

	/**
	 * 更新【渠道每个时段剩余可以绑定普通设备数】
	 * 
	 * @throws Exception
	 */
	public synchronized void resetConfigChannelBindPadCount() throws Exception {
		String key = "config_channel_bind_pad_count";
		Integer value = Integer.parseInt(ConfigConstantsDb.configChannelMaxPadCount());
		SysConfig config = new SysConfig();
		config.setConfigCode(key);
		config.setConfigValue(value.toString());
		this.update(null, config);
	}
	/**
	 * 更新微信自动回复
	 * 
	 * @Title: resetConfigWXFaultTips
	 * @param @param value
	 * @param @throws Exception 设定文件
	 * @return void 返回类型
	 * @throws 异常
	 */

	public synchronized void resetConfigWXFaultTips(String value) throws Exception {
		String key = "weixin_fault_tips";
		SysConfig config = new SysConfig();
		config.setConfigCode(key);
		config.setConfigValue(value);
		this.update(null, config);
	}
	
	/**
	 * 根据编码查询
	 * @param configCode
	 * @return
	 * @throws Exception
	 */
	public SysConfig selectByConfingCode(String configCode) throws Exception {
		return sysConfigMapper.selectByPrimaryKey(configCode);
	}

}