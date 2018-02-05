package com.redfinger.manager.modules.base.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.SysConfig;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.ConfigHelper;
import com.redfinger.manager.common.redis.RedisService;
import com.redfinger.manager.modules.base.service.ConfigService;

@Controller
@RequestMapping(value = "/base/configOnOff")
public class OnOffController extends BaseController {
	@Autowired
	ConfigService service;
	@Autowired
	RedisService redisService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String message_pad_grant_expire_on_off = ConfigHelper.getValueByCode("message_pad_grant_expire_on_off");
		model.addAttribute("message_pad_grant_expire_on_off", message_pad_grant_expire_on_off);
		
		String message_pad_expire_on_off = ConfigHelper.getValueByCode("message_pad_expire_on_off");
		model.addAttribute("message_pad_expire_on_off", message_pad_expire_on_off);
		
		String message_coupon_expire_on_off = ConfigHelper.getValueByCode("message_coupon_expire_on_off");
		model.addAttribute("message_coupon_expire_on_off", message_coupon_expire_on_off);
	
		String message_gift_expire_on_off = ConfigHelper.getValueByCode("message_gift_expire_on_off");
		model.addAttribute("message_gift_expire_on_off", message_gift_expire_on_off);
		
		String message_pad_grant_cancel_on_off = ConfigHelper.getValueByCode("message_pad_grant_cancel_on_off");
		model.addAttribute("message_pad_grant_cancel_on_off", message_pad_grant_cancel_on_off);
		
		String message_buy_excharge_cancel_on_off = ConfigHelper.getValueByCode("message_buy_excharge_cancel_on_off");
		model.addAttribute("message_buy_excharge_cancel_on_off", message_buy_excharge_cancel_on_off);
		
		String message_activation_expire_on_off = ConfigHelper.getValueByCode("message_activation_expire_on_off");
		model.addAttribute("message_activation_expire_on_off", message_activation_expire_on_off);
		
		String weixin_game_drop_on_off = ConfigHelper.getValueByCode("weixin_game_drop_on_off");
		model.addAttribute("weixin_game_drop_on_off", weixin_game_drop_on_off);
		
		String weixin_pad_expire_on_off = ConfigHelper.getValueByCode("weixin_pad_expire_on_off");
		model.addAttribute("weixin_pad_expire_on_off", weixin_pad_expire_on_off);
		
		String send_wechart_on_off = ConfigHelper.getValueByCode("send_wechart_on_off");
		model.addAttribute("send_wechart_on_off", send_wechart_on_off);
		
		String config_appcrash_weixinremind_touser = ConfigHelper.getValueByCode("config_appcrash_weixinremind_touser");
		model.addAttribute("config_appcrash_weixinremind_touser", config_appcrash_weixinremind_touser);
		
		String config_appcrash_weixinremind_todb = ConfigHelper.getValueByCode("config_appcrash_weixinremind_todb");
		model.addAttribute("config_appcrash_weixinremind_todb", config_appcrash_weixinremind_todb);
		
		String config_offline_weixinremind_todb = ConfigHelper.getValueByCode("config_offline_weixinremind_todb");
		model.addAttribute("config_offline_weixinremind_todb", config_offline_weixinremind_todb);
		
		String config_offline_weixinremind_touser = ConfigHelper.getValueByCode("config_offline_weixinremind_touser");
		model.addAttribute("config_offline_weixinremind_touser", config_offline_weixinremind_touser);
		
		String config_appcrash_weixinremind_touser_timegt = ConfigHelper.getValueByCode("config_appcrash_weixinremind_touser_timegt");
		model.addAttribute("config_appcrash_weixinremind_touser_timegt", config_appcrash_weixinremind_touser_timegt);
		
		String config_appcrash_weixinremind_touser_timelt = ConfigHelper.getValueByCode("config_appcrash_weixinremind_touser_timelt");
		model.addAttribute("config_appcrash_weixinremind_touser_timelt", config_appcrash_weixinremind_touser_timelt);
		return this.toPage(request, response, model);
		
	}

	@ResponseBody
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, SysConfig config) throws Exception {
		Map<String, Object> map = config.getMap();
		service.updateMap(request, config);
		if(null != map){
			String appcrashToDb = (String)map.get("config_appcrash_weixinremind_todb");
			String appcrashToUser = (String)map.get("config_appcrash_weixinremind_touser");
			String offlineToDb = (String)map.get("config_offline_weixinremind_todb");
			String offlineToUser = (String)map.get("config_offline_weixinremind_touser");
			try {
				if(StringUtils.isNotBlank(appcrashToDb)){
					redisService.set("config_appcrash_weixinremind_todb",appcrashToDb);
				}
				if(StringUtils.isNotBlank(appcrashToUser)){
					redisService.set("config_appcrash_weixinremind_touser",appcrashToUser);
				}
				if(StringUtils.isNotBlank(offlineToDb)){
					redisService.set("config_offline_weixinremind_todb",offlineToDb);
				}
				if(StringUtils.isNotBlank(offlineToUser)){
					redisService.set("config_offline_weixinremind_touser",offlineToUser);
				}
			} catch (Exception e) {
				throw new BusinessException("同步redis数据出错");
			}
		}
	}
}
