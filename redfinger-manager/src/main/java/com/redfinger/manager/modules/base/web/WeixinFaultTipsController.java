package com.redfinger.manager.modules.base.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.constants.ConfigConstantsDb;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.modules.base.service.ConfigService;

@Controller
@RequestMapping(value = "/base/configWXfaultTips")
public class WeixinFaultTipsController extends BaseController {

	@Value("#{configProperties['wechart.url']}")
	private String WECHART_URL;

	@Autowired
	ConfigService service;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, String configValue)
			throws Exception {
		service.resetConfigWXFaultTips(configValue);

		CloseableHttpClient httpclient = HttpClients.createDefault();
		String url = WECHART_URL + "/kfrefresh?key=" + ConfigConstantsDb.weixinFaultTipsKey();
		log.info("refresh weixin tip url:"+url);
		// 创建httpget.
		HttpGet httpget = new HttpGet(url);
		// 执行get请求.
		CloseableHttpResponse resp = httpclient.execute(httpget);
		int statuCode = resp.getStatusLine().getStatusCode();
		log.info("refresh weixin tip response status code:"+statuCode);
		if (200 != statuCode) {
			throw new BusinessException("请求更新失败！");
		}
	}
}
