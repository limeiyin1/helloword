package com.redfinger.manager.modules.tools.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.ToolsMarketUpdate;
import com.redfinger.manager.common.domain.ToolsMarketUpdateExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.ToolsMarketUpdateMapper;
import com.redfinger.manager.modules.tools.help.HtmlParse;
import com.redfinger.manager.modules.tools.help.HttpClientHelp;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class ToolsMarketUpdateScanService extends BaseService<ToolsMarketUpdate, ToolsMarketUpdateExample, ToolsMarketUpdateMapper> {

	public void scan(List<String> ids) {
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		for (String str : ids) {
			if (StringUtils.isBlank(str)) {
				continue;
			}
			int id = Integer.parseInt(str);
			ToolsMarketUpdate marketUpdate = this.get(id);
			if (marketUpdate == null) {
				continue;
			}
			// 不需要更新
			String status = "no";
			String info = "扫描成功：";
			// 自己apk的大小
			int selfApkSize = -1;
			// 应用市场apk大小
			int marketApkSize = -1;
			String mrketApkUrl = "";

			try {
				if (!status.equals("error")) {
					selfApkSize = HttpClientHelp.getLength(httpclient, marketUpdate.getApkUrl());
				}
			} catch (Exception e) {
				info = "扫描失败：获取本地apk大小出错";
				status = "error";
				e.printStackTrace();
			}

			try {
				if (!status.equals("error")) {
					if (marketUpdate.getMarketName().equals("360应用市场")) {
						mrketApkUrl = HtmlParse.getApkUrlFor360(httpclient, marketUpdate.getMarketUrl());
					} else if (marketUpdate.getMarketName().equals("应用宝")) {
						mrketApkUrl = HtmlParse.getApkUrlForYingyongbao(httpclient, marketUpdate.getMarketUrl());
					} else if (marketUpdate.getMarketName().equals("tt")) {
						mrketApkUrl = HtmlParse.getApkUrlForTt(httpclient, marketUpdate.getMarketUrl());
					} else if (marketUpdate.getMarketName().equals("九游")) {
						mrketApkUrl = HtmlParse.getApkUrlForJiuYou(httpclient, marketUpdate.getMarketUrl());
					} else if (marketUpdate.getMarketName().equals("百度")) {
						mrketApkUrl = HtmlParse.getApkUrlForBaidu(httpclient, marketUpdate.getMarketUrl());
					} else if (marketUpdate.getMarketName().equals("小米")) {
						mrketApkUrl = HtmlParse.getApkUrlForXiaomi(httpclient, marketUpdate.getMarketUrl());
					} else if (marketUpdate.getMarketName().equals("oppo")) {
						mrketApkUrl = HtmlParse.getApkUrlForOppo(httpclient, marketUpdate.getMarketUrl());
					} else if (marketUpdate.getMarketName().equals("豌豆荚")) {
						mrketApkUrl = HtmlParse.getApkUrlForWandoujia(httpclient, marketUpdate.getMarketUrl());
					} else if (marketUpdate.getMarketName().equals("安智")) {
						mrketApkUrl = HtmlParse.getApkUrlForAnzhi(httpclient, marketUpdate.getMarketUrl());
					} else if (marketUpdate.getMarketName().equals("拇指玩")) {
						mrketApkUrl = HtmlParse.getApkUrlForMuzhiwan(httpclient, marketUpdate.getMarketUrl());
					} else if (marketUpdate.getMarketName().equals("应用汇")) {
						mrketApkUrl = HtmlParse.getApkUrlForYingyonghui(httpclient, marketUpdate.getMarketUrl());
					} else if (marketUpdate.getMarketName().equals("华为")) {
						mrketApkUrl = HtmlParse.getApkUrlForHuawei(httpclient, marketUpdate.getMarketUrl());
					} else if (marketUpdate.getMarketName().equals("联想")) {
						mrketApkUrl = HtmlParse.getApkUrlForLianxiang(httpclient, marketUpdate.getMarketUrl());
					}
				}
			} catch (Exception e) {
				info = "扫描失败：获取应用市场apk下载地址出错";
				status = "error";
				e.printStackTrace();
			}

			try {
				if (!status.equals("error")) {
					marketApkSize = HttpClientHelp.getLength(httpclient, mrketApkUrl);
				}
			} catch (Exception e) {
				info = "扫描失败：获取应用市场apk大小出错";
				status = "error";
				e.printStackTrace();
			}

			// 写信息回数据库
			if (!status.equals("error") && selfApkSize != -1 && marketApkSize != -1) {
				if (selfApkSize == marketApkSize) {
					status = "no";
				} else {
					status = "yes";
				}
			}
			ToolsMarketUpdate bean = new ToolsMarketUpdate();
			bean.setId(marketUpdate.getId());
			bean.setScanTime(new Date());
			bean.setResult(status);
			bean.setRemark(info);
			bean.setApkDownloadUrl(mrketApkUrl);
			bean.setApkSize(selfApkSize);
			bean.setMarketSize(marketApkSize);
			ToolsMarketUpdateMapper mapper = (ToolsMarketUpdateMapper) this.getMapper();
			mapper.updateByPrimaryKeySelective(bean);
		}
		try {
			httpclient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
