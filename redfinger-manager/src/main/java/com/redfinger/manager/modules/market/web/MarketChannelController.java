package com.redfinger.manager.modules.market.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.base.FilePathUtils;
import com.redfinger.manager.common.base.PackagingTool;
import com.redfinger.manager.common.constants.CacheRedisConstant;
import com.redfinger.manager.common.constants.Constants;
import com.redfinger.manager.common.constants.MaintOrMainCancel;
import com.redfinger.manager.common.domain.RfChannel;
import com.redfinger.manager.common.domain.RfChannelGrade;
import com.redfinger.manager.common.domain.RfChannelVersion;
import com.redfinger.manager.common.domain.RfMend;
import com.redfinger.manager.common.domain.RfParentVersion;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.redis.CacheRedisService;
import com.redfinger.manager.common.utils.ClientType;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.FileUtils;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.common.utils.OsTypeUtils;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.ShellUtils;
import com.redfinger.manager.common.utils.ShellUtils.CommandResult;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.base.service.ConfigService;
import com.redfinger.manager.modules.market.service.MarketChannelGradeService;
import com.redfinger.manager.modules.market.service.MarketChannelService;
import com.redfinger.manager.modules.market.service.MarketVersionService;
import com.redfinger.manager.modules.mend.service.MendService;
import com.redfinger.manager.modules.version.service.ChannelVersionService;
import com.redfinger.manager.modules.version.service.ParentVersionService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/market/channel")
public class MarketChannelController extends BaseController {

	@Autowired
	MarketChannelService service;
	@Autowired
	MarketVersionService versionService;
	@Autowired
	ConfigService sysConfigService;
	@Autowired
	FilePathUtils filePathUtils;
	@Autowired
	ParentVersionService parentVersionService;
	@Autowired
	ChannelVersionService channelVersionService;
	@Autowired
	MendService mendService;
	@Autowired
	FileUtils fileUtil;
	@Autowired
	private CacheRedisService cacheRedisService;
	@Autowired
	private MarketChannelGradeService channelGradeService;
	
	@Value("#{configProperties['pc.subPackage.shell.path']}")
	private String pcSubPackageShellPath;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("maintStatuss", MaintOrMainCancel.DICT_MAP);
		List<RfChannelGrade> firstGradeIds = channelGradeService.initQuery()
				.andEqualTo("channelGrade", MarketChannelGradeService.FIRST_GRADE)
				.findDelTrue();
		model.addAttribute("firstGradeIds", firstGradeIds);
		return this.toPage(request, response, model);
	}
	
	//页面查询的时候返回数据
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfChannel> list(HttpServletRequest request, HttpServletResponse response, Model model, RfChannel bean) throws Exception {
		bean.getMaintStatus();
		bean.getChannelCode();
		List<RfChannel> list = service.initQuery(bean)
				.andLike("channelName", bean.getChannelName())
				.andLike("channelCode", bean.getChannelCode())//添加渠道编码模糊查询
				.andEqualTo("maintStatus", bean.getMaintStatus())//添加是否维护查询
				.andEqualTo("clientType", bean.getClientType())//添加是否维护查询
				.andEqualTo("discoverLimit", bean.getDiscoverLimit())//添加发现栏是否可用查询
				.andEqualTo("channelType", bean.getChannelType())//添加渠道类型查询
				.andEqualTo("firstGradeId", bean.getFirstGradeId())
				.andEqualTo("secondGradeId", bean.getSecondGradeId())
				.addOrderClause("createTime", "asc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		for(RfChannel channel : list){
			RfChannelGrade firstGrade =  channelGradeService.get(channel.getFirstGradeId());
			RfChannelGrade secondGrade =  channelGradeService.get(channel.getSecondGradeId());
			if(null!=firstGrade){
				channel.setFirstGradeName(firstGrade.getGradeName());
			}
			if(null!=secondGrade){
				channel.setSecondGradeName(secondGrade.getGradeName());
			}
			
		}
		
		
		PageInfo<RfChannel> pageInfo = new PageInfo<RfChannel>(list);
		return pageInfo;
	}
	
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfChannel bean) throws Exception {
		if (bean.getChannelId() == null) {

		} else {
			bean = service.get(bean.getChannelId());
		}
		
		List<RfChannelGrade> firstGradeIds = channelGradeService.initQuery()
				.andEqualTo("channelGrade", MarketChannelGradeService.FIRST_GRADE)
				.findDelTrue();
		model.addAttribute("firstGradeIds", firstGradeIds);
		
		if(bean.getFirstGradeId()!=null){
			List<RfChannelGrade>  secondGradeIds =channelGradeService.initQuery()
					.andEqualTo("parentId", bean.getFirstGradeId())
					.findDelTrue();
			model.addAttribute("secondGradeIds", secondGradeIds);
		}
		
		RfChannelGrade firstGrade =  channelGradeService.get(bean.getFirstGradeId());
		RfChannelGrade secondGrade =  channelGradeService.get(bean.getSecondGradeId());
		if(null!=firstGrade){
			bean.setFirstGradeName(firstGrade.getGradeName());
		}
		if(null!=secondGrade){
			bean.setSecondGradeName(secondGrade.getGradeName());
		}
		
		model.addAttribute("bean", bean);
		model.addAttribute("maintOrMainCancel", MaintOrMainCancel.DICT_MAP);
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "subPackageForm")
	public String subPackageForm(HttpServletRequest request, HttpServletResponse response, Model model, RfChannel bean) throws Exception {
		if (bean.getChannelId() == null) {

		} else {
			bean = service.get(bean.getChannelId());
		}
		
		model.addAttribute("yesOrNos", YesOrNoStatus.DICT_MAP);
		
		Map<String,String> map = OsTypeUtils.getOsType();
		model.addAttribute("map", map);
		model.addAttribute("channelCode", Constants.CHANNEL_CODE);
		List<RfChannel> channels = service.initQuery().findStopTrue();
		model.addAttribute("channels", channels);
		
		List<String> osTypes = new ArrayList<String>();
		osTypes.add(OsTypeUtils.WIN);
		osTypes.add(OsTypeUtils.ANDROID);
		
		List<RfParentVersion> list = parentVersionService.initQuery()
				.andIn("osType", osTypes)
				.andEqualTo("parentChannelCode", Constants.CHANNEL_CODE).addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder()).findStopTrue();
		if(null != list && list.size()>0){
			model.addAttribute("list", list);
		}else{
			throw new BusinessException("请添加母包！");
		}
		return this.toPage(request, response, model);
		
	}
	
	@RequestMapping(value = "subOnePackageForm")//TODO
	public String subOnePackageForm(HttpServletRequest request, HttpServletResponse response, Model model, RfChannel bean) throws Exception {
		if (bean.getChannelId() == null) {
			throw new BusinessException("请选择渠道！");
		} else {
			bean = service.get(bean.getChannelId());
		}
		
		model.addAttribute("bean", bean);
		
		model.addAttribute("yesOrNos", YesOrNoStatus.DICT_MAP);
		
		Map<String,String> map = OsTypeUtils.getOsType();
		model.addAttribute("map", map);
		model.addAttribute("channelCode", Constants.CHANNEL_CODE);
		
		/*List<String> osTypes = new ArrayList<String>();
		osTypes.add(OsTypeUtils.WIN);
		osTypes.add(OsTypeUtils.ANDROID);*/
		
		List<RfParentVersion> list = parentVersionService.initQuery()
		.andEqualTo("osType", bean.getClientType())
		.andEqualTo("parentVersionType", bean.getChannelType())////添加多一个渠道类型的筛选添加
		.andEqualTo("parentChannelCode", Constants.CHANNEL_CODE).addOrderClause("createTime", "desc")
		.addOrderForce(bean.getSort(), bean.getOrder()).findStopTrue();
		
		if(null != list && list.size()>0){
			model.addAttribute("list", list);
		}else{
			throw new BusinessException("请添加母包！");
		}
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "subPackage")
	public void subPackage(HttpServletRequest request, HttpServletResponse response, Model model, RfChannel bean,
			String versionName,String versionDesc,String versionCode,String osType,String versionMust,String isYes,String versionNew,
			String channelVersionTimeStr) throws Exception {
		log.info("[channelCode:{},versionId:{},versionName:{},versionDesc:{},versionCode:{},osType:{},versionMust:{}]",new Object[]{
				bean.getChannelCode(),versionName,versionDesc,versionCode,osType,versionMust});
		List<String> list = new ArrayList<String>();
		
		if(StringUtils.isBlank(isYes)){
			throw new BusinessException("请选择是否是对所有渠道分包");
		}
		if(StringUtils.isBlank(versionCode)){
			throw new BusinessException("请选择是否是对版本编号");
		}
		if(YesOrNoStatus.NO.equals(isYes)){//部分渠道分包
			if(StringUtils.isBlank(bean.getChannelCode())){
				throw new BusinessException("请选择渠道！");
			}
			
			String channelCodes[] = bean.getChannelCode().split("\\|");
			for (String string : channelCodes) {
				list.add(string);
			}
		}else if(YesOrNoStatus.YES.equals(isYes)){//所有渠道分包
			List<RfChannel> channels = service.initQuery().findStopTrue();
			for (RfChannel channel : channels) {
				list.add(channel.getChannelCode());
			}
		}
		
		/*if(null == versionId){
			throw new BusinessException("母包不能为空");
		}
		
		RfVersion version = versionService.get(versionId);*/
		
		RfParentVersion version = null;
		List<RfParentVersion> parentVersions = parentVersionService.initQuery()
				.andEqualTo("parentChannelCode", Constants.CHANNEL_CODE)
				.andEqualTo("parentVersionCode", versionCode)
				.andEqualTo("osType", osType)
				.addOrderForce(bean.getSort(), bean.getOrder()).findStopTrue();
		if(null != parentVersions && parentVersions.size() >0){
			version = parentVersions.get(0);
		}else{
			throw new BusinessException("渠道编号为："+versionCode+"的母包不能为空");
		}
		if(StringUtils.isBlank(version.getParentVersionDownloadurl())){
			throw new BusinessException("母包的下载地址不能为空");
		}
		if(StringUtils.isBlank(version.getParentVersionUpdateurl())){
			throw new BusinessException("母包的更新地址不能为空");
		}
		
		String updateUrl = "";
		String downloadUrl = "";
		String url = "";
		String downUrl = filePathUtils.getConfigDownloadUrl();
		for (String channelCode : list) {
			RfChannel channel = null;
			List<RfChannel> channels = service.initQuery().andEqualTo("channelCode", channelCode.trim()).findStopTrue();
			if(null != channels && channels.size()>0){
				channel = channels.get(0);
			}else{
				throw new BusinessException(channelCode+"您选择的渠道查询不到数据");
			}
			
			String updateUrls[] = version.getParentVersionUpdateurl().split("\\.");
			String downloadUrls[] = version.getParentVersionDownloadurl().split("\\.");
			updateUrl = getUrl(channelCode.trim(), version.getParentVersionUpdateurl(),updateUrls[updateUrls.length-1]);
			downloadUrl = getUrl(channelCode.trim(), version.getParentVersionDownloadurl(),downloadUrls[downloadUrls.length-1]);
			
			String loadUrl[] = version.getParentVersionDownloadurl().split("\\/");
			url = downUrl + "/" /*+ loadUrl[loadUrl.length-3] + "/"*/ + loadUrl[loadUrl.length-2] + "/" + loadUrl[loadUrl.length-1] ;
			
			log.info("文件存放地址："+url);
			
			List<RfChannelVersion> versions = channelVersionService.initQuery()
			.andEqualTo("channelOsType", osType)
			.andEqualTo("channelCode", channel.getChannelCode())
			.findStopTrue();
			if(null != versions && versions.size()>0){//分包是否为空
				for (RfChannelVersion rfChannelVersion : versions) {
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("channelName", channel.getChannelName());
					map.put("channelID", channel.getChannelCode());
					map.put("channelVersion", versionCode);
					JSONObject json = JSONObject.fromObject(map);
					String str = json.toString();
					log.info("channelCode:{},channelName:{}",new Object[]{channel.getChannelCode(),channel.getChannelName()});
					PackagingTool.copyChannelFile(url, channel.getChannelCode(), str);
					
					rfChannelVersion.setChannelVersionId(rfChannelVersion.getChannelVersionId());
					rfChannelVersion.setChannelVersionCode(versionCode);
					rfChannelVersion.setChannelVersionName(versionName);
					rfChannelVersion.setChannelVersionDesc(versionDesc);
					rfChannelVersion.setChannelVersionMust(versionMust);
					rfChannelVersion.setChannelVersionNew(versionNew);
					rfChannelVersion.setChannelUpdateUrl(updateUrl);
					rfChannelVersion.setChannelDownloadUrl(downloadUrl);
					rfChannelVersion.setChannelVersionTime(DateUtils.parseDate(channelVersionTimeStr));
					rfChannelVersion.setChannelOsType(osType);
					rfChannelVersion.setModifier(SessionUtils.getCurrentUserId(request));
					rfChannelVersion.setModifyTime(new Date());
					channelVersionService.updateChannelVersion(rfChannelVersion);
				}
			}else{
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("channelName", channel.getChannelName());
				map.put("channelID", channel.getChannelCode());
				map.put("channelVersion", versionCode);
				JSONObject json = JSONObject.fromObject(map);
				String str = json.toString();
				
				PackagingTool.copyChannelFile(url, channel.getChannelCode(), str);
				
				RfChannelVersion rfChannelVersion = new RfChannelVersion();
				rfChannelVersion.setChannelCode(channel.getChannelCode());
				rfChannelVersion.setChannelVersionCode(versionCode);
				rfChannelVersion.setChannelVersionName(versionName);
				rfChannelVersion.setChannelVersionDesc(versionDesc);
				rfChannelVersion.setChannelVersionMust(versionMust);
				rfChannelVersion.setChannelVersionNew(versionNew);
				rfChannelVersion.setChannelUpdateUrl(updateUrl);
				rfChannelVersion.setChannelDownloadUrl(downloadUrl);
				rfChannelVersion.setChannelVersionTime(DateUtils.parseDate(channelVersionTimeStr));
				rfChannelVersion.setChannelOsType(osType);
				rfChannelVersion.setStatus(YesOrNoStatus.YES);
				rfChannelVersion.setEnableStatus(YesOrNoStatus.YES);
				rfChannelVersion.setCreater(SessionUtils.getCurrentUserId(request));
				rfChannelVersion.setCreateTime(new Date());
				channelVersionService.saveChannelVersion(rfChannelVersion);
			}
		}
	}
	
	
	@RequestMapping(value = "subOnePackage")//TODO
	public void subPackage(HttpServletRequest request, HttpServletResponse response, Model model,
	   			RfChannelVersion bean, String channelVersionTimeStr,String channelName) throws Exception {
	    	//RfChannel channel = service.get(channelId);
    	log.info("[channelCode:{},channelVersionCode:{}]",new Object[]{
    			bean.getChannelCode(),bean.getChannelVersionCode()});
    	
    	if(StringUtils.isBlank(bean.getChannelVersionCode())){
    		throw new BusinessException("渠道版本编码不能为空！");
    	}
    	
    	if(StringUtils.isBlank(bean.getChannelCode())){
    		throw new BusinessException("渠道编码不能为空！");
    	}
    	
    	if(StringUtils.isBlank(bean.getChannelVersionCode())){
			throw new BusinessException("请选择是否是对版本编号");
		}
		
		List<RfParentVersion> lists = parentVersionService.initQuery()
		.andEqualTo("osType", bean.getChannelOsType())
		.andEqualTo("parentVersionCode", bean.getChannelVersionCode())
		.andEqualTo("parentChannelCode", Constants.CHANNEL_CODE)
		.addOrderClause("createTime", "desc")
		.findStopTrue();
		
		if(null == lists || lists.size() < 1){
			throw new BusinessException("版本编码为"+bean.getChannelVersionCode()+"系统列表为"+bean.getChannelOsType()+"的母包不能为空");
		}
		
		RfParentVersion version = lists.get(0);
		if(StringUtils.isBlank(version.getParentVersionDownloadurl())){
			throw new BusinessException("版本编码为"+bean.getChannelVersionCode()+"系统列表为"+bean.getChannelOsType()+"的母包的下载地址不能为空");
		}
		if(StringUtils.isBlank(version.getParentVersionUpdateurl())){
			throw new BusinessException("版本编码为"+bean.getChannelVersionCode()+"系统列表为"+bean.getChannelOsType()+"的母包的更新地址不能为空");
		}
		
		String updateUrl = "";
		String downloadUrl = "";
		String url = "";
		String downUrl = filePathUtils.getConfigDownloadUrl();
		
		String updateUrls[] = version.getParentVersionUpdateurl().split("\\.");
		String downloadUrls[] = version.getParentVersionDownloadurl().split("\\.");
		updateUrl = getUrl(bean.getChannelCode(), version.getParentVersionUpdateurl(),updateUrls[updateUrls.length-1]);
		downloadUrl = getUrl(bean.getChannelCode(), version.getParentVersionDownloadurl(),downloadUrls[downloadUrls.length-1]);
		
		String loadUrl[] = version.getParentVersionDownloadurl().split("\\/");
		url = downUrl + "/" /*+ loadUrl[loadUrl.length-3] + "/"*/ + loadUrl[loadUrl.length-2] + "/" + loadUrl[loadUrl.length-1] ;
		
		log.info("文件存放地址："+url);
		List<RfChannelVersion> versions = channelVersionService.initQuery()
		.andEqualTo("channelOsType", bean.getChannelOsType())
		.andEqualTo("channelCode", bean.getChannelCode())
		.findStopTrue();
		if(null != versions && versions.size()>0){//分包是否为空
			for (RfChannelVersion rfChannelVersion : versions) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("channelName", channelName);
				map.put("channelID", bean.getChannelCode());
				map.put("channelVersion", bean.getChannelVersionCode());
				JSONObject json = JSONObject.fromObject(map);
				String str = json.toString();
				
				//PackagingTool.copyChannelFile(url, bean.getChannelCode(), str);
				if(OsTypeUtils.ANDROID.equals(version.getOsType())){
					PackagingTool.copyChannelFile(url, bean.getChannelCode(), str);
				}else if(OsTypeUtils.WIN.equals(version.getOsType())||OsTypeUtils.WIN_ST.equals(version.getOsType())){
					pcSubPackage(url, bean.getChannelCode(),channelName);
				}
				
				rfChannelVersion.setChannelVersionId(rfChannelVersion.getChannelVersionId());
				rfChannelVersion.setChannelVersionCode(bean.getChannelVersionCode());
				rfChannelVersion.setChannelVersionName(bean.getChannelVersionName());
				rfChannelVersion.setChannelVersionDesc(bean.getChannelVersionDesc());
				rfChannelVersion.setChannelVersionMust(bean.getChannelVersionMust());
				rfChannelVersion.setChannelVersionNew(bean.getChannelVersionNew());
				rfChannelVersion.setChannelUpdateUrl(updateUrl);
				rfChannelVersion.setChannelDownloadUrl(downloadUrl);
				rfChannelVersion.setChannelVersionTime(DateUtils.parseDate(channelVersionTimeStr));
				rfChannelVersion.setChannelOsType(bean.getChannelOsType());
				rfChannelVersion.setModifier(SessionUtils.getCurrentUserId(request));
				rfChannelVersion.setModifyTime(new Date());
				channelVersionService.updateChannelVersion(rfChannelVersion);
			}
		}else{
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("channelName", channelName);
			map.put("channelID", bean.getChannelCode());
			map.put("channelVersion", bean.getChannelVersionCode());
			JSONObject json = JSONObject.fromObject(map);
			String str = json.toString();
			
			//PackagingTool.copyChannelFile(url, bean.getChannelCode(), str);
			if(OsTypeUtils.ANDROID.equals(version.getOsType())){
				PackagingTool.copyChannelFile(url, bean.getChannelCode(), str);
			}else if(OsTypeUtils.WIN.equals(version.getOsType())||OsTypeUtils.WIN_ST.equals(version.getOsType())){
				pcSubPackage(url, bean.getChannelCode(),channelName);
			}
			
			RfChannelVersion rfChannelVersion = new RfChannelVersion();
			rfChannelVersion.setChannelCode(bean.getChannelCode());
			rfChannelVersion.setChannelVersionCode(bean.getChannelVersionCode());
			rfChannelVersion.setChannelVersionName(bean.getChannelVersionName());
			rfChannelVersion.setChannelVersionDesc(bean.getChannelVersionDesc());
			rfChannelVersion.setChannelVersionMust(bean.getChannelVersionMust());
			rfChannelVersion.setChannelVersionNew(bean.getChannelVersionNew());
			rfChannelVersion.setChannelUpdateUrl(updateUrl);
			rfChannelVersion.setChannelDownloadUrl(downloadUrl);
			rfChannelVersion.setChannelVersionTime(DateUtils.parseDate(channelVersionTimeStr));
			rfChannelVersion.setChannelOsType(bean.getChannelOsType());
			rfChannelVersion.setStatus(YesOrNoStatus.YES);
			rfChannelVersion.setEnableStatus(YesOrNoStatus.YES);
			rfChannelVersion.setCreater(SessionUtils.getCurrentUserId(request));
			rfChannelVersion.setCreateTime(new Date());
			channelVersionService.saveChannelVersion(rfChannelVersion);
		}
    }
	
	@RequestMapping(value = "subMendPackageForm")//TODO
	public String subMendPackageForm(HttpServletRequest request, HttpServletResponse response, Model model, RfChannel bean) throws Exception {
		List<RfChannel> channelList = service.initQuery().andEqualTo("clientType", OsTypeUtils.ANDROID).findStopTrue();//只查询安卓的
		if(channelList.size()>0){
			model.addAttribute("channelList", channelList);
		}
		
		model.addAttribute("bean", bean);
		//model.addAttribute("ids", bean.getIds());
		
		Map<String,String> map = OsTypeUtils.getOsType();
		model.addAttribute("map", map);
		model.addAttribute("yesOrNos", YesOrNoStatus.DICT_MAP);
		model.addAttribute("channelCode", Constants.CHANNEL_CODE);
		
		List<RfParentVersion> list = parentVersionService.initQuery()
				.andEqualTo("osType", OsTypeUtils.MEND)
				.andEqualTo("parentChannelCode", Constants.CHANNEL_CODE).addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder()).findStopTrue();
		
		if(null != list && list.size()>0){
			model.addAttribute("list", list);
			model.addAttribute("mend",list.get(0));
		}else{
			throw new BusinessException("请添加母包！");
		}
		return this.toPage(request, response, model);
	}
	
	/**
	 * 补丁分包
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @param mendVersionTimeStr
	 * @param isYes
	 * @param channelIds
	 * @throws Exception
	 */
	@RequestMapping(value = "subMend")
	public void subMend(HttpServletRequest request, HttpServletResponse response, Model model,
			RfMend bean, String mendVersionTimeStr, String channelIds ,String isYes) throws Exception {
		
		if(StringUtils.isBlank(bean.getMendVersionCode())){
			throw new BusinessException("补丁版本编码不能为空！");
		}
		
		List<RfParentVersion> lists = parentVersionService.initQuery()
				.andEqualTo("osType", bean.getMendOsType())
				.andEqualTo("parentVersionCode", bean.getMendVersionCode())
				.andEqualTo("parentChannelCode", Constants.CHANNEL_CODE)
				.addOrderClause("createTime", "desc")
				.findStopTrue();
		
		if(null == lists || lists.size() < 1){
			throw new BusinessException("版本编码为"+bean.getMendVersionCode()+"的补丁不能为空");
		}
		
		RfParentVersion version = lists.get(0);
		if(StringUtils.isBlank(version.getParentVersionDownloadurl())){
			throw new BusinessException("版本编码为"+bean.getMendVersionCode()+"的补丁的下载地址不能为空");
		}
		if(StringUtils.isBlank(version.getParentVersionUpdateurl())){
			throw new BusinessException("版本编码为"+bean.getMendVersionCode()+"的补丁的更新地址不能为空");
		}
		
		List<RfChannel> channelList = new ArrayList<RfChannel>();
		
		if(StringUtils.isBlank(isYes)){
			throw new BusinessException("请选择是否是对所有渠道分包");
		}
		
		List<Integer> list = new ArrayList<Integer>();
		
		if(YesOrNoStatus.NO.equals(isYes)){//部分渠道分包
			if(StringUtils.isBlank(channelIds)){
				throw new BusinessException("请选择渠道！");
			}
			
			String channelIdArr[] = channelIds.split("\\|");
			for (String string : channelIdArr) {
				list.add(Integer.parseInt(string));
			}
			if(list.size()>0){
				channelList = service.initQuery().andIn("channelId", list).findStopTrue();
			}
		}else if(YesOrNoStatus.YES.equals(isYes)){//所有渠道分包
			channelList = service.initQuery().findStopTrue();
		}
		
		if(channelList.size()<0){
			throw new BusinessException("渠道不能为空");
		}
		
		for (RfChannel rfChannel : channelList) {
			String updateUrl = "";
			String downloadUrl = "";
			String url = "";
			String downUrl = filePathUtils.getConfigDownloadUrl();
			
			String updateUrlForSub = version.getParentVersionUpdateurl();
			String downloadUrlForSub = version.getParentVersionDownloadurl();
			
			if(StringUtils.isNoneBlank(bean.getMendUpdateUrl())){
				updateUrlForSub = bean.getMendUpdateUrl();
			}
			if(StringUtils.isNoneBlank(bean.getMendDownloadUrl())){
				downloadUrlForSub = bean.getMendDownloadUrl();
			}
			
			String updateUrls[] = version.getParentVersionUpdateurl().split("\\.");
			String downloadUrls[] = version.getParentVersionDownloadurl().split("\\.");
			updateUrl = getUrl(rfChannel.getChannelCode(), updateUrlForSub,updateUrls[updateUrls.length-1]);
			downloadUrl = getUrl(rfChannel.getChannelCode(), downloadUrlForSub,downloadUrls[downloadUrls.length-1]);
			
			String loadUrl[] = version.getParentVersionDownloadurl().split("\\/");
			url = downUrl + "/" /*+ loadUrl[loadUrl.length-3] + "/"*/ + loadUrl[loadUrl.length-2] + "/" + loadUrl[loadUrl.length-1] ;
			
			log.info("文件存放地址："+url);
			List<RfMend> mends = mendService.initQuery()
					.andEqualTo("mendChannelCode", rfChannel.getChannelCode())
					.andEqualTo("mendOsType", bean.getMendOsType())
					.findStopTrue();
			if(null != mends && mends.size()>0){//分包是否为空
				for (RfMend mend : mends) {
					mend.setMendVersionCode(bean.getMendVersionCode());
					mend.setMendVersionName(bean.getMendVersionName());
					mend.setMendUpdateUrl(updateUrl);
					mend.setMendDownloadUrl(downloadUrl);
					mend.setModifier(SessionUtils.getCurrentUserId(request));
					mend.setModifyTime(new Date());
					mend.setMendVersionDesc(bean.getMendVersionDesc());
					mend.setMendOsType(bean.getMendOsType());
					if(StringUtils.isNotBlank(mendVersionTimeStr)){
						mend.setMendVersionTime(DateUtils.parseDate(mendVersionTimeStr));
					}
					mendService.updateMend(mend);
				}
			}else{
				RfMend mend = new RfMend();
				mend.setMendChannelCode(rfChannel.getChannelCode());
				mend.setMendVersionCode(bean.getMendVersionCode());
				mend.setMendVersionName(bean.getMendVersionName());
				mend.setMendOsType(OsTypeUtils.MEND);
				mend.setMendUpdateUrl(updateUrl);
				mend.setMendDownloadUrl(downloadUrl);
				mend.setMendVersionDesc(bean.getMendVersionDesc());
				mend.setStatus(YesOrNoStatus.YES);
				mend.setEnableStatus(YesOrNoStatus.YES);
				mend.setCreater(SessionUtils.getCurrentUserId(request));
				mend.setCreateTime(new Date());
				mend.setMendOsType(bean.getMendOsType());
				if(StringUtils.isNotBlank(mendVersionTimeStr)){
					mend.setMendVersionTime(DateUtils.parseDate(mendVersionTimeStr));
				}
				mendService.saveMend(mend);
			}
		}
		
	}
	
	
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfChannel bean) throws Exception {
		List<RfChannel> channels = service.initQuery()
				.andEqualTo("channelCode", bean.getChannelCode())
				.andEqualTo("clientType", bean.getClientType())
				.findAll();
		if(channels.size() > 0){
			throw new BusinessException(bean.getChannelCode()+"此渠道编码已经存在！");
		}
		service.save(request, bean);
		
		// 修改redis中发现栏和任务栏开关数据
		redisDataEdit(String.valueOf(bean.getChannelId()));
	}
	
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfChannel bean) throws Exception {
		List<RfChannel> channels = service.initQuery()
				.andEqualTo("channelCode", bean.getChannelCode())
				.andEqualTo("clientType", bean.getClientType())
				.findAll();
		if(channels.size() > 0&&!channels.get(0).getChannelId().equals(bean.getChannelId())){
			throw new BusinessException(bean.getChannelCode()+"此渠道编码已经存在！");
		}
		
		service.update(request, bean);
		
		try{
			// 更新redis中任务栏和发现栏数据
			String redisData = cacheRedisService.get(CacheRedisConstant.DISCOVER_TASK_MODULE, bean.getChannelCode());
			if(StringUtils.isNoneBlank(redisData, bean.getChannelCode()) && ClientType.ANDROID.equals(bean.getClientType())){
				@SuppressWarnings("unchecked")
				Map<String, Object> data = JsonUtils.stringToObject(redisData, Map.class);
				data.put("taskLimit", bean.getTaskLimit() == null ? "0" : bean.getTaskLimit());
				data.put("discoverLimit", bean.getDiscoverLimit() == null ? "0" : bean.getDiscoverLimit());
				cacheRedisService.set(CacheRedisConstant.DISCOVER_TASK_MODULE, bean.getChannelCode(), JsonUtils.ObjectToString(data), CacheRedisConstant.TASK_DISCOVER_REDIS_EXPIRE);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfChannel bean) throws Exception {
		service.start(request, bean);
		// 修改redis中发现栏和任务栏开关数据
		redisDataEdit(bean.getIds());
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfChannel bean) throws Exception {
		service.stop(request, bean);
		/* 删除redis中的数据*/
		redisDataDel(bean.getIds());
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfChannel bean) throws Exception {
		service.delete(request, bean);
		
		/* 删除redis中的数据*/
		redisDataDel(bean.getIds());
		
	}
	
	private void pcSubPackage(String url, String channelCode,String channelName){
    	int url_last_index = url.lastIndexOf(".");
    	int channelCode_last_index = channelCode.lastIndexOf(".");
    	if(url_last_index<0){
    		throw new BusinessException("地址格式不正确");
    	}
    	String partA = url.substring(0, url_last_index);
    	String partB =  channelCode.substring(channelCode_last_index+1, channelCode.length());
    	String newPath = partA+"_"+partB+".exe";
		String command = pcSubPackageShellPath+" "+url+" "+newPath+" "+channelCode+" ";//===/home/webapps/script/main
		String tempFilePath = pcSubPackageShellPath.substring(0, pcSubPackageShellPath.lastIndexOf("/")+1)+partB;
		File file = new File(tempFilePath);
		try {
			FileUtils.writeStringToFile(file,channelName,"GBK");
			log.info("excute-command:"+command+tempFilePath);
			CommandResult commandResult = ShellUtils.execCommand(command+tempFilePath, true);
			log.info("excute-result:result:"+commandResult.result+" successMsg:"+commandResult.successMsg+" errortMsg:"+commandResult.errorMsg);
			if(0!=commandResult.result){
				throw new BusinessException("pc分包失败,未知原因，请联系管理员");
			}
			//Runtime.getRuntime().exec("dir");
		} catch (IOException e) {
			throw new BusinessException("pc分包失败");
		}finally{
			FileUtils.deleteQuietly(file);
		}
    }
	

	/**
     * 上传文件
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "uploadFileRequest")
    @ResponseBody
    public  JSONObject  uploadFileRequest( 
    		@RequestParam(value = "fileInput", required = false) MultipartFile file,
    		HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	response.setContentType("text/html;charset=UTF-8"); 
    	
    	String downLoadUrl = filePathUtils.getConfigDownloadUrl();
    	String downLoadLinkUrl = filePathUtils.getConfigDownloadLinkUrl();
    	
    	if(StringUtils.isEmpty(downLoadUrl)){
    		throw new BusinessException("版本下载文件存放的本地地址为空！");
    	}
    	log.info("版本下载文件存放的本地地址:"+downLoadUrl);
    	if(null == downLoadLinkUrl){
    		throw new BusinessException("版本下载文件链接地址为空！");
    	}
    	log.info("版本下载文件链接地址:"+downLoadLinkUrl);
    	log.info("是否存在上传文件，"+file.isEmpty() + "" + file.getSize());
    	
        Map<String, String> fileMap = null;
        if (!file.isEmpty()) {
        	
            fileMap = fileUtil.save(file,downLoadUrl,downLoadLinkUrl);
            fileMap.put("flag", "true");
            log.info("filePath: " + fileMap.get("filePath"));
        }
        else {
            fileMap = new HashMap<String, String>();
            fileMap.put("flag", "false");

        }
       /* return fileMap;*/
        JSONObject jsonObject = asyncReturnResult(response,JSONObject.fromObject(fileMap));
        log.info(jsonObject.toString());
        return jsonObject;
    }
    
    /**
     * 上传文件
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "uploadUpdateFileRequest")
    @ResponseBody
    public  JSONObject  uploadUpdateFileRequest( 
    		@RequestParam(value = "fileUpdate", required = false) MultipartFile file,
    		HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	
    	
    	String downLoadUrl = filePathUtils.getConfigDownloadUrl();
    	String downLoadLinkUrl = filePathUtils.getConfigDownloadLinkUrl();
    	
    	if(StringUtils.isEmpty(downLoadUrl)){
    		throw new BusinessException("版本下载文件存放的本地地址为空！");
    	}
    	log.info("版本下载文件存放的本地地址:"+downLoadUrl);
    	if(StringUtils.isEmpty(downLoadLinkUrl)){
    		throw new BusinessException("版本下载文件链接地址为空！");
    	}
    	log.info("版本下载文件链接地址:"+downLoadLinkUrl);
    	log.info("是否存在上传文件，"+file.isEmpty() + "" + file.getSize());
    	response.setContentType("text/html");
        Map<String, String> fileMap = null;
        if (!file.isEmpty()) {
        	
            fileMap = fileUtil.save(file,downLoadUrl,downLoadLinkUrl);
            fileMap.put("flag", "true");
            log.info("filePath: " + fileMap.get("filePath"));
        }else {
            fileMap = new HashMap<String, String>();
            fileMap.put("flag", "false");

        }
       /* return fileMap;*/
        JSONObject jsonObject = asyncReturnResult(response,JSONObject.fromObject(fileMap));
        log.info(jsonObject.toString());
        return jsonObject;
    }
    
    /**
     * 根据补丁版本编码查找补丁信息
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "selectMend")
    @ResponseBody
    public Map<String, Object> selectMend(HttpServletRequest request, HttpServletResponse response, Model model,String parentVersionCode){
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	
    	List<RfParentVersion> result = parentVersionService.initQuery().andEqualTo("parentVersionCode", parentVersionCode)
    		.andEqualTo("osType", OsTypeUtils.MEND)
    		.singleDelTrue();
    	
    	RfParentVersion mend = new RfParentVersion();
    	if(result.size()>0){
    		mend = result.get(0);
    		Date parentVersionTime = mend.getParentVersionTime();
    		map.put("mend", mend);
    		map.put("parentVersionTime", DateUtils.formatDateTime(parentVersionTime));
    	}
    	
    	/*JSONObject jsonObject = asyncReturnResult(response,JSONObject.fromObject(mend));
    	log.info(jsonObject.toString());*/
    	return map;
    }
    
    /**
     * 
     * 根据主键(多个,逗号分隔)修改redis中发现栏和开关栏的数据
     * @param ids 
     */
    public void redisDataEdit(String ids){
	    try{
	    	if(StringUtils.isBlank(ids)){
    			return;
    		}
	    	/* 根据主键修改redis中的数据*/
			String [] idArrays = ids.split(",");
			if(idArrays != null && idArrays.length > 0){
				for (String id : idArrays) {
					RfChannel channel = service.get(Integer.parseInt(id));
					if(channel != null && StringUtils.isNoneBlank(channel.getChannelCode())&& ClientType.ANDROID.equals(channel.getClientType())){
							String data = cacheRedisService.get(CacheRedisConstant.DISCOVER_TASK_MODULE, channel.getChannelCode());
							if(StringUtils.isNotBlank(data)){
								@SuppressWarnings("unchecked")
								Map<String, Object> dataMap = JsonUtils.stringToObject(data, Map.class);
								dataMap.put("discoverLimit", channel.getDiscoverLimit() == null ? "0" : channel.getDiscoverLimit());
								dataMap.put("taskLimit", channel.getTaskLimit() == null ? "0" : channel.getTaskLimit());
								cacheRedisService.set(CacheRedisConstant.DISCOVER_TASK_MODULE, channel.getChannelCode(), JsonUtils.ObjectToString(dataMap),CacheRedisConstant.TASK_DISCOVER_REDIS_EXPIRE);
							}
						
					}
					
				}
			}
	  
	    }catch(Exception e){
			e.printStackTrace();
		}
    }
    
    /**
     * 
     * 根据主键(多个,逗号分隔)删除redis中发现栏和开关栏的数据
     * @param ids 
     */
    public void redisDataDel(String ids){
    	try{
    		if(StringUtils.isBlank(ids)){
    			return;
    		}
    		// 根据主键删除redis中发再栏和开关栏的数据
			String [] idArrays = ids.split(",");
			if(idArrays != null && idArrays.length > 0){
				for (String id : idArrays) {
					RfChannel channel = service.get(Integer.parseInt(id));
					if(channel != null && StringUtils.isNoneBlank(channel.getChannelCode())&& ClientType.ANDROID.equals(channel.getClientType())){
							cacheRedisService.del(CacheRedisConstant.DISCOVER_TASK_MODULE, channel.getChannelCode());
					}
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
    }
    
    
    @ResponseBody
	@RequestMapping(value = "getSecondGradeIds")
	public List<RfChannelGrade> getSecondGradeIds(HttpServletRequest request, HttpServletResponse response, Model model, Integer firstGradeId) throws Exception {
    	List<RfChannelGrade>  channelGrades =channelGradeService.initQuery()
    			.andEqualTo("parentId", firstGradeId)
    			.andEqualTo("channelGrade", MarketChannelGradeService.SECOND_GRADE)
    			.findDelTrue();
    			
    	return channelGrades;
    }
    
    @ResponseBody
	@RequestMapping(value = "getSecondGradeIdsHaveAll")
	public List<RfChannelGrade> getSecondGradeIdsHaveAll(HttpServletRequest request, HttpServletResponse response, Model model, Integer firstGradeId) throws Exception {
    	RfChannelGrade channelGrade = new RfChannelGrade();
    	channelGrade.setGradeId(null);
    	channelGrade.setGradeName("[全部]");
    	List<RfChannelGrade> channelGrades = new ArrayList<>();
    	channelGrades.add(channelGrade);
    	channelGrades.addAll(( channelGradeService.initQuery()
    			.andEqualTo("parentId", firstGradeId)
    			.andEqualTo("channelGrade", MarketChannelGradeService.SECOND_GRADE)
    			.findDelTrue()));
    	return channelGrades;
    }
    
	
}
