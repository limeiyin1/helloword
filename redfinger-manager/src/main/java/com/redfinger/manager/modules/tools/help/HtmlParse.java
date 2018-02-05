package com.redfinger.manager.modules.tools.help;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.filters.LinkStringFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserUtils;

public class HtmlParse {

	// 360
	public static String getApkUrlFor360(CloseableHttpClient httpclient, String url) throws Exception {
		String html = HttpClientHelp.requestGet(httpclient, url);
		Parser parser = new Parser(html);
		TagNameFilter nameFilter = new TagNameFilter("script");
		NodeList nodeList = parser.parse(nameFilter);
		Pattern pattern = Pattern.compile(".*(http://api.np.mobilem.360.cn/redirect/down/.*\")");
		for (int i = 0; i < nodeList.size(); i++) {
			Node node = nodeList.elementAt(i);
			String text = node.toPlainTextString().trim();
			if (text.length() > 0) {
				text = ParserUtils.removeEscapeCharacters(text);
				text = ParserUtils.removeTrailingBlanks(text);
				Matcher matcher = pattern.matcher(text);
				if (matcher.find()) {
					String returuLink = matcher.group(1);
					returuLink = returuLink.substring(0, returuLink.indexOf("\""));
					return returuLink;
				}
			}
		}
		return null;
	}

	// 应用宝
	public static String getApkUrlForYingyongbao(CloseableHttpClient httpclient, String url) throws Exception {
		String html = HttpClientHelp.requestGet(httpclient, url);
		Parser parser = new Parser(html);
		TagNameFilter nameFilter = new TagNameFilter("script");
		NodeList nodeList = parser.parse(nameFilter);
		Pattern pattern = Pattern.compile(".*(downUrl:\"http://imtt.dd.qq.com/.*\")");
		for (int i = 0; i < nodeList.size(); i++) {
			Node node = nodeList.elementAt(i);
			String text = node.toPlainTextString().trim();
			if (text.length() > 0) {
				text = ParserUtils.removeEscapeCharacters(text);
				text = ParserUtils.removeTrailingBlanks(text);
				Matcher matcher = pattern.matcher(text);
				if (matcher.find()) {
					String returuLink = matcher.group(1);
					returuLink = returuLink.substring(returuLink.indexOf("\"")+1,returuLink.indexOf("\"", returuLink.indexOf("\"")+1));
					return returuLink;
				}
			}
		}
		return null;
	}

	// 9游
	public static String getApkUrlForJiuYou(CloseableHttpClient httpclient, String url) throws Exception {
		String html = HttpClientHelp.requestGet(httpclient, url);
		Parser parser = new Parser(html);
		LinkStringFilter aFilter = new LinkStringFilter("http://www.9game.cn/game/");
		NodeList nodeList = parser.parse(aFilter);
		Node node = nodeList.elementAt(0);
		LinkTag tag = (LinkTag) node;
		return tag.getLink();
	}

	// 百度
	public static String getApkUrlForBaidu(CloseableHttpClient httpclient, String url) throws Exception {
		String html = HttpClientHelp.requestGet(httpclient, url);
		Parser parser = new Parser(html);
		CssSelectorNodeFilter cssFilter = new CssSelectorNodeFilter(".area-download");
		NodeList nodeList = parser.parse(cssFilter);
		Node node = nodeList.elementAt(0);
		TagNode tagNode = (TagNode) node;
		nodeList = tagNode.getChildren();
		for (int i = 0; i < nodeList.size(); i++) {
			node = nodeList.elementAt(i);
			if (StringUtils.isNotBlank(node.toHtml())) {
				LinkTag tag = (LinkTag) (node);
				return tag.getLink();
			}
		}
		return null;
	}

	// 小米
	public static String getApkUrlForXiaomi(CloseableHttpClient httpclient, String url) throws Exception {
		return url.replaceFirst("detail", "download");
	}

	// tt
	public static String getApkUrlForTt(CloseableHttpClient httpclient, String url) throws Exception {
		return url;
	}

	// oppo
	public static String getApkUrlForOppo(CloseableHttpClient httpclient, String url) throws Exception {
		String html = HttpClientHelp.requestGet(httpclient, url);
		Parser parser = new Parser(html);
		// detail_down
		CssSelectorNodeFilter cssFilter = new CssSelectorNodeFilter(".detail_down");
		NodeList nodeList = parser.parse(cssFilter);
		Node node = nodeList.elementAt(0);
		TagNode tagNode = (TagNode) node;
		String id = tagNode.getAttribute("onclick");
		id = id.replace("detailInfoDownload(", "").replace(")", "");
		return "http://store.oppomobile.com/product/download.html?id=" + id;
	}

	// 豌豆荚
	public static String getApkUrlForWandoujia(CloseableHttpClient httpclient, String url) throws Exception {
		String html = HttpClientHelp.requestGet(httpclient, url);
		Parser parser = new Parser(html);
		// qr-info
		CssSelectorNodeFilter cssFilter = new CssSelectorNodeFilter(".qr-info");
		NodeList nodeList = parser.parse(cssFilter);
		Node node = nodeList.elementAt(0);
		NodeList childNodeList = node.getChildren();
		for (int i = 0; i < childNodeList.size(); i++) {
			Node temp = childNodeList.elementAt(i);
			if (temp instanceof LinkTag) {
				LinkTag tag = (LinkTag) (temp);
				return tag.getLink();
			}
		}
		return null;
	}

	// 安智
	public static String getApkUrlForAnzhi(CloseableHttpClient httpclient, String url) throws Exception {
		String id = url.replace("http://www.anzhi.com/soft_", "").replace(".html", "");
		return "http://www.anzhi.com/dl_app.php?s=" + id;
	}

	// 拇指玩
	public static String getApkUrlForMuzhiwan(CloseableHttpClient httpclient, String url) throws Exception {
		String html = HttpClientHelp.requestGet(httpclient, url);
		Parser parser = new Parser(html);
		CssSelectorNodeFilter cssFilter = new CssSelectorNodeFilter(".local");
		NodeList nodeList = parser.parse(cssFilter);
		Node node = nodeList.elementAt(0);
		LinkTag tag = (LinkTag) (node);
		return "http://www.muzhiwan.com/" + tag.getLink();
	}

	// 应用汇
	public static String getApkUrlForYingyonghui(CloseableHttpClient httpclient, String url) throws Exception {
		String html = HttpClientHelp.requestGet(httpclient, url);
		Parser parser = new Parser(html);
		CssSelectorNodeFilter cssFilter = new CssSelectorNodeFilter(".download_app");
		NodeList nodeList = parser.parse(cssFilter);
		Node node = nodeList.elementAt(0);
		LinkTag tag = (LinkTag) (node);
		return tag.getLink();
	}

	// 华为
	public static String getApkUrlForHuawei(CloseableHttpClient httpclient, String url) throws Exception {
		String html = HttpClientHelp.requestGet(httpclient, url);
		Parser parser = new Parser(html);
		CssSelectorNodeFilter cssFilter = new CssSelectorNodeFilter("a");
		NodeList nodeList = parser.parse(cssFilter);
		for (int i = 0; i < nodeList.size(); i++) {
			Node node = nodeList.elementAt(i);
			TagNode tagNode = (TagNode) node;
			if (tagNode.getAttribute("class") != null && tagNode.getAttribute("class").equals("mkapp-btn mab-download")) {
				String link = tagNode.toHtml();
				Pattern pattern = Pattern.compile(".*(http://.*)'.*");
				Matcher matcher = pattern.matcher(link);
				if (matcher.find()) {
					String returuLink = matcher.group(1);
					return returuLink;
				}
			}
		}
		return null;
	}
	//联想
	public static String getApkUrlForLianxiang(CloseableHttpClient httpclient, String url) throws Exception {
		String html = HttpClientHelp.requestGet(httpclient, url);
		Parser parser = new Parser(html);
		CssSelectorNodeFilter cssFilter = new CssSelectorNodeFilter("#downAPK");
		NodeList nodeList = parser.parse(cssFilter);
		Node node = nodeList.elementAt(0);
		LinkTag tag = (LinkTag) (node);
		return tag.getLink();
	}
	
	//魅族
	public static String getApkUrlForMeizu(CloseableHttpClient httpclient, String url) throws Exception {
		String html = HttpClientHelp.requestGet(httpclient, url);
		Parser parser = new Parser(html);
		CssSelectorNodeFilter cssFilter = new CssSelectorNodeFilter("#downAPK");
		NodeList nodeList = parser.parse(cssFilter);
		Node node = nodeList.elementAt(0);
		LinkTag tag = (LinkTag) (node);
		return tag.getLink();
	}
	

	public static void main(String[] args) throws Exception {
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		String url = HtmlParse.getApkUrlForMeizu(httpclient, "http://game.gionee.com/Front/Game/detail/?from=gn&id=3240&keyword=%E5%A4%A9%E9%BE%99%E5%85%AB%E9%83%A8&cku=_null&action=visit&object=gamedetail3240_gn&intersrc=isearch_gn_gid3240");
		System.out.println(url);
		httpclient.close();
	}
}
