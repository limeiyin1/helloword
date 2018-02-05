package com.redfinger.manager.modules.version.web;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

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
import com.redfinger.manager.common.constants.Constants;
import com.redfinger.manager.common.domain.RfChannel;
import com.redfinger.manager.common.domain.RfChannelVersion;
import com.redfinger.manager.common.domain.RfMend;
import com.redfinger.manager.common.domain.RfParentVersion;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.ClientType;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.FileUtils;
import com.redfinger.manager.common.utils.OsTypeUtils;
import com.redfinger.manager.common.utils.QRCodeUtil;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.ShellUtils;
import com.redfinger.manager.common.utils.ShellUtils.CommandResult;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.base.service.ConfigService;
import com.redfinger.manager.modules.market.service.MarketChannelService;
import com.redfinger.manager.modules.mend.service.MendService;
import com.redfinger.manager.modules.version.service.ChannelVersionService;
import com.redfinger.manager.modules.version.service.ParentVersionService;

@Controller
@RequestMapping(value="/parent/version")
public class ParentVersionController  extends BaseController {

	@Autowired
	ParentVersionService service;
	@Autowired
	FileUtils fileUtil;
	@Autowired
	ConfigService sysConfigService;
	@Autowired
	FilePathUtils filePathUtils;
	@Autowired
	MarketChannelService channelService;
	@Autowired
	ChannelVersionService channelVersionService;
	@Autowired
	MendService mendService;
	
	@Value("#{configProperties['pc.subPackage.shell.path']}")
	private String pcSubPackageShellPath;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("osTypes", OsTypeUtils.DICT_MAP);
		return this.toPage(request, response, model);
	}
   
	//页面查询的时候返回数据
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfParentVersion> list(HttpServletRequest request, HttpServletResponse response, Model model, RfParentVersion bean) throws Exception {
		List<RfParentVersion> list = service.initQuery(bean)
				.andEqualTo("osType", bean.getOsType())
				.andEqualTo("discoverLimit", bean.getDiscoverLimit())
				.andEqualTo("parentVersionType", bean.getParentVersionType())
				.andLike("parentVersionName", bean.getParentVersionName())		
				.andLike("parentVersionCode", bean.getParentVersionCode())	
				.andLike("parentChannelCode", bean.getParentChannelCode())
				.andGreaterThanOrEqualTo("parentVersionTime",DateUtils.parseDate(bean.getBeginTimeStr()))
				.andLessThan("parentVersionTime", DateUtils.parseDateAddOneDay(bean.getEndTimeStr()))
				.addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		PageInfo<RfParentVersion> pageInfo = new PageInfo<RfParentVersion>(list);
		return pageInfo;
	}

	
	
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfParentVersion bean) throws Exception {
		if (bean.getParentVersionId() == null) {

		} else {
			bean = service.get(bean.getParentVersionId());
		}
		model.addAttribute("bean", bean);
		Map<String,String> map = OsTypeUtils.getOsType();
		model.addAttribute("map", map);
		model.addAttribute("news", YesOrNoStatus.DICT_MAP);
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "addform")
	public String addform(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String,String> map = OsTypeUtils.getOsType();
		model.addAttribute("map", map);
		model.addAttribute("news", YesOrNoStatus.DICT_MAP);
		return this.toPage(request, response, model);
	}

	
	//保存的时候出现的界面
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfParentVersion bean,String parentVersionTimeStr) throws Exception {
		if(StringUtils.isNotBlank(parentVersionTimeStr)){
			bean.setParentVersionTime(DateUtils.parseDate(parentVersionTimeStr));
		}
		/*if(ClientType.PC.equals(bean.getOsType())){//如果是pc，则不用区分是否有发现栏
			bean.setDiscoverLimit(null);
		}*/
		
		List<RfParentVersion> list = service.initQuery().andEqualTo("osType", bean.getOsType())
		.andEqualTo("parentVersionCode", bean.getParentVersionCode().trim())
		.andEqualTo("parentChannelCode", bean.getParentChannelCode())
		//.andEqualTo("discoverLimit", bean.getDiscoverLimit())
		.findAll();
		if(null != list && list.size()>0){
			throw new BusinessException("类型为"+bean.getOsType()+"的母包的版本编码重复");
		}
		String enableStatus = bean.getEnableStatus();
		log.info(enableStatus);
		service.save(request, bean);
		if(YesOrNoStatus.NO.equals(enableStatus)){
			bean.setEnableStatus(enableStatus);
			service.update(request, bean);
		}
	}
	
	
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfParentVersion bean,String parentVersionTimeStr) throws Exception {
		bean.setParentVersionTime(DateUtils.parseDate(parentVersionTimeStr));
		
		/*if(ClientType.PC.equals(bean.getOsType())){//如果是pc，则不用区分是否有发现栏
			bean.setDiscoverLimit(null);
		}*/
		
		List<RfParentVersion> list = service.initQuery().andEqualTo("osType", bean.getOsType())
		.andEqualTo("parentVersionCode", bean.getParentVersionCode().trim())
		.andEqualTo("parentChannelCode", bean.getParentChannelCode())
		//.andEqualTo("discoverLimit", bean.getDiscoverLimit())
		.findAll();
		if(null != list && list.size()>0 && !list.get(0).getParentVersionId().equals(bean.getParentVersionId())){
			throw new BusinessException("类型为"+bean.getOsType()+"的母包的版本编码重复");
		}
		
		
		
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfParentVersion bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfParentVersion bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfParentVersion bean) throws Exception {
		//service.delete(request, bean);
		service.remove(request, bean);//改成物理删除
	}
	
	/**
	 * 同步母包
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @throws Exception
	 */
	@RequestMapping(value = "synsPacket")
	public void synsPacket(HttpServletRequest request, HttpServletResponse response, Model model, RfParentVersion bean) throws Exception {
		Integer id = bean.getParentVersionId();
		bean = service.get(id);
		if(null == bean){
			throw new BusinessException("选择的母包不存在");
		}
		if(StringUtils.equals(YesOrNoStatus.NO, bean.getStatus()) || StringUtils.equals(YesOrNoStatus.NO, bean.getEnableStatus())){
			throw new BusinessException("选择的母包状态无效");
		}
		
		List<RfChannelVersion> list = channelVersionService.selectChannelVersion(bean.getParentChannelCode(),
				bean.getParentVersionCode(), bean.getOsType());
		
		String downUrl = filePathUtils.getConfigDownloadUrl();
		String loadUrl[] = bean.getParentVersionDownloadurl().split("\\/");
		String  url = downUrl + "/" /*+ loadUrl[loadUrl.length-3] + "/"*/ + loadUrl[loadUrl.length-2] + "/" + loadUrl[loadUrl.length-1] ;
		File file = new File(url);
		long fileSize = 0;
		if(file.exists()){
			fileSize = file.length();
		}else{
			//throw new BusinessException("路径下找不到文件");
		}
		
		
		String qrFilePath = filePathUtils.getFilePath()+"/images/channel/qr_code/";
    	String qrFileLinkUrl = filePathUtils.getFileLinkUrl()+"/channel/qr_code/";
		
		if(null != list && list.size()>0){
			for (RfChannelVersion channelVersion : list) {
				channelVersion.setFileSize(fileSizeToString(fileSize));
				
				channelVersion.setChannelCode(bean.getParentChannelCode());
				channelVersion.setChannelVersionCode(bean.getParentVersionCode());
				channelVersion.setChannelVersionName(bean.getParentVersionName());
				channelVersion.setChannelOsType(bean.getOsType());
				channelVersion.setChannelVersionMust(bean.getVersionMust());
				channelVersion.setChannelVersionNew(bean.getVersionNew());
				channelVersion.setChannelUpdateUrl(bean.getParentVersionUpdateurl());
				channelVersion.setChannelDownloadUrl(bean.getParentVersionDownloadurl());
				channelVersion.setChannelVersionDesc(bean.getParentVersionDesc());
				channelVersion.setChannelVersionTime(bean.getParentVersionTime());
				channelVersion.setStatus(bean.getStatus());
				channelVersion.setEnableStatus(bean.getEnableStatus());
				channelVersion.setModifier(SessionUtils.getCurrentUserId(request));
				channelVersion.setModifyTime(new Date());
				channelVersionService.updateChannelVersion(channelVersion);
				
				
				//TODO 这里生产二维码
				String rootPath=request.getRealPath("/");
				String fileName = QRCodeUtil.encode(channelVersion.getChannelDownloadUrl(),rootPath+"/static/images/qr_logo.png",qrFilePath,true);
				String qrUrl = qrFileLinkUrl+fileName;
				channelVersion.setQrCodeUrl(qrUrl);
				
				channelVersionService.updateChannelVersion(channelVersion);
				
			}
		}else{
			RfChannelVersion channelVersion = new RfChannelVersion();
			
			channelVersion.setFileSize(fileSizeToString(fileSize));
			channelVersion.setChannelCode(bean.getParentChannelCode());
			channelVersion.setChannelVersionCode(bean.getParentVersionCode());
			channelVersion.setChannelVersionName(bean.getParentVersionName());
			channelVersion.setChannelOsType(bean.getOsType());
			channelVersion.setChannelVersionMust(bean.getVersionMust());
			channelVersion.setChannelVersionNew(bean.getVersionNew());
			channelVersion.setChannelUpdateUrl(bean.getParentVersionUpdateurl());
			channelVersion.setChannelDownloadUrl(bean.getParentVersionDownloadurl());
			channelVersion.setChannelVersionDesc(bean.getParentVersionDesc());
			channelVersion.setChannelVersionTime(bean.getParentVersionTime());
			channelVersion.setStatus(bean.getStatus());
			channelVersion.setEnableStatus(bean.getEnableStatus());
			channelVersion.setCreater(SessionUtils.getCurrentUserId(request));
			channelVersion.setCreateTime(new Date());
			channelVersionService.saveChannelVersion(channelVersion);
			
			//TODO 这里生产二维码
			String rootPath=request.getRealPath("/");
			String fileName = QRCodeUtil.encode(channelVersion.getChannelDownloadUrl(),rootPath+"/static/images/qr_logo.png",qrFilePath,true);
			String qrUrl = qrFileLinkUrl+fileName;
			channelVersion.setQrCodeUrl(qrUrl);
			
			channelVersionService.updateChannelVersion(channelVersion);
		}
	}
	
	/**
	 * 同步补丁
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @throws Exception
	 */
	@RequestMapping(value = "synsMend")
	public void synsMend(HttpServletRequest request, HttpServletResponse response, Model model, RfParentVersion bean) throws Exception {
		Integer id = bean.getParentVersionId();
		bean = service.get(id);
		if(null == bean){
			throw new BusinessException("选择的母包不存在");
		}
		if(StringUtils.equals(YesOrNoStatus.NO, bean.getStatus()) || StringUtils.equals(YesOrNoStatus.NO, bean.getEnableStatus())){
			throw new BusinessException("选择的母包状态无效");
		}
		if(!bean.getOsType().equals(OsTypeUtils.MEND)){
			throw new BusinessException("请选择补丁");
		}
		/*List<RfMend> list = mendService.selectMend(bean.getParentChannelCode(),
				bean.getParentVersionCode(), bean.getOsType());*/
		
		List<RfMend> list = mendService.initQuery().andEqualTo("mendChannelCode", bean.getParentChannelCode()).findStopTrue();
		
		if(null != list && list.size()>0){
			for (RfMend mend : list) {
				mend.setMendChannelCode(bean.getParentChannelCode());
				mend.setMendVersionCode(bean.getParentVersionCode());
				mend.setMendVersionName(bean.getParentVersionName());
				mend.setMendOsType(bean.getOsType());
				mend.setMendUpdateUrl(bean.getParentVersionUpdateurl());
				mend.setMendDownloadUrl(bean.getParentVersionDownloadurl());
				mend.setMendVersionDesc(bean.getParentVersionDesc());
				mend.setMendVersionTime(bean.getParentVersionTime());
				mend.setStatus(bean.getStatus());
				mend.setEnableStatus(bean.getEnableStatus());
				mend.setModifier(SessionUtils.getCurrentUserId(request));
				mend.setModifyTime(new Date());
				mendService.updateMend(mend);
			}
		}else{
			RfMend mend = new RfMend();
			mend.setMendChannelCode(bean.getParentChannelCode());
			mend.setMendVersionCode(bean.getParentVersionCode());
			mend.setMendVersionName(bean.getParentVersionName());
			mend.setMendOsType(bean.getOsType());
			mend.setMendUpdateUrl(bean.getParentVersionUpdateurl());
			mend.setMendDownloadUrl(bean.getParentVersionDownloadurl());
			mend.setMendVersionDesc(bean.getParentVersionDesc());
			mend.setMendVersionTime(bean.getParentVersionTime());
			mend.setStatus(bean.getStatus());
			mend.setEnableStatus(bean.getEnableStatus());
			mend.setCreater(SessionUtils.getCurrentUserId(request));
			mend.setCreateTime(new Date());
			mendService.saveMend(mend);
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
    
    /*@RequestMapping(value = "subPackageForm")//备份
	public String subPackageForm(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("yesOrNos", YesOrNoStatus.DICT_MAP);
		
		Map<String,String> map = OsTypeUtils.getOsType();
		model.addAttribute("map", map);
		
		List<RfChannel> channels = channelService.initQuery()
				.andEqualTo("clientType", ClientType.ANDROID)//只显示客户端类型为android的
				.findStopTrue();
		model.addAttribute("channels", channels);
		
		List<RfParentVersion> versions = service.initQuery()
				.andEqualTo("osType", OsTypeUtils.ANDROID)
				.andEqualTo("parentChannelCode", Constants.CHANNEL_CODE)
				.addOrderClause("createTime", "desc").findStopTrue();
		if(null != versions && versions.size()>0){
			model.addAttribute("versions", versions);
		}else{
			throw new BusinessException("请添加母包！");
		}
		return this.toPage(request, response, model);
	}*/
    
    @RequestMapping(value = "subPackageForm")
	public String subPackageForm(HttpServletRequest request, HttpServletResponse response, Model model,int id) throws Exception {//TODO
		model.addAttribute("yesOrNos", YesOrNoStatus.DICT_MAP);
		Map<String,String> map = OsTypeUtils.getOsType();
		model.addAttribute("map", map);
		
		RfParentVersion rfParentVersion = service.get(id);
		model.addAttribute("rfParentVersion", rfParentVersion);
		
		if(ClientType.ANDROID.equals(rfParentVersion.getOsType())){
			List<RfChannel> channels = channelService.initQuery()
					.andEqualTo("clientType", rfParentVersion.getOsType())//根据母包类型获取相应的渠道
					//.andEqualTo("discoverLimit", rfParentVersion.getDiscoverLimit())//发现栏是否可用的筛选
					.andEqualTo("channelType", rfParentVersion.getParentVersionType())//添加多一个渠道类型的筛选添加
					.addOrderClause("channelCode", "asc")
					.findStopTrue();
			model.addAttribute("channels", channels);
		}else{
			List<RfChannel> channels = channelService.initQuery()
					.andEqualTo("clientType", rfParentVersion.getOsType())//根据母包类型获取相应的渠道
					.andEqualTo("channelType", rfParentVersion.getParentVersionType())//添加多一个渠道类型的筛选添加
					.addOrderClause("channelCode", "asc")
					.findStopTrue();
			model.addAttribute("channels", channels);
		}
		
		return this.toPage(request, response, model);
	}
    
    
    @RequestMapping(value = "getChannelAndVersionByClentType")
    @ResponseBody
    public Map<String, Object> getChannelAndVersionByClentType(HttpServletRequest request, HttpServletResponse response, Model model,String parentOsType){
    	List<RfChannel> channels = null;
    	List<RfParentVersion> versions = null;
    	Map<String, Object> result = new HashMap<String, Object>();
    	if(OsTypeUtils.ANDROID.equals(parentOsType)){
    		channels = channelService.initQuery()
    				.andEqualTo("clientType", ClientType.ANDROID)//只显示客户端类型为android的
    				.findStopTrue();
    		
    		versions = service.initQuery()
    				.andEqualTo("osType", OsTypeUtils.ANDROID)
    				.andEqualTo("parentChannelCode", Constants.CHANNEL_CODE)
    				.addOrderClause("createTime", "desc").findStopTrue();
    		if(null != versions && versions.size()>0){
    		}else{
    			throw new BusinessException("请添加母包！");
    		}
    		if(null==channels||channels.size()<=0){
    			throw new BusinessException("渠道为空！");
    		}
    	}else if(OsTypeUtils.WIN.equals(parentOsType)){
    		channels = channelService.initQuery()
    				.andEqualTo("clientType", ClientType.PC)//只显示客户端类型为android的
    				.findStopTrue();
    		//model.addAttribute("channels", channels);
    		
    		versions = service.initQuery()
    				.andEqualTo("osType", OsTypeUtils.WIN)
    				.andEqualTo("parentChannelCode", Constants.CHANNEL_CODE)
    				.addOrderClause("createTime", "desc").findStopTrue();
    		if(null != versions && versions.size()>0){
    			//model.addAttribute("versions", versions);
    		}else{
    			throw new BusinessException("请添加母包！");
    		}
    		if(null==channels||channels.size()<=0){
    			throw new BusinessException("渠道为空！");
    		}
    	}
    	result.put("channels", channels);
    	result.put("versions", versions);
    	return result;
    }
	
    @RequestMapping(value = "subPackage")//TODO
   	public void subPackage(HttpServletRequest request, HttpServletResponse response, Model model,
   			RfParentVersion bean, String parentVersionTimeStr, String channelCodes,String isYes,
   			String parentOsType,String discoverLimit,String taskLimit) throws Exception {
    	
    	RfParentVersion version = service.get(bean.getParentVersionId());
    	log.info("[channelCode:{},parentVersionCode:{},versionMust:{}]",new Object[]{
    			channelCodes,bean.getParentVersionCode(),bean.getVersionMust()});
		List<String> list = new ArrayList<String>();
		
		if(StringUtils.isBlank(isYes)){
			throw new BusinessException("请选择是否是对所有渠道分包");
		}
		if(YesOrNoStatus.NO.equals(isYes)){//部分渠道分包
			if(StringUtils.isBlank(channelCodes)){
				throw new BusinessException("请选择渠道！");
			}
			
			String channelCode[] = channelCodes.split("\\|");
			for (String string : channelCode) {
				list.add(string);
			}
		}else if(YesOrNoStatus.YES.equals(isYes)){//所有渠道分包
			List<RfChannel> channels = channelService.initQuery()
					.andEqualTo("clientType", parentOsType)//只选择客户端类型为android的
					//.andEqualTo("discoverLimit", version.getDiscoverLimit())
					.andEqualTo("channelType", version.getParentVersionType())
					.findStopTrue();
			for (RfChannel channel : channels) {
				list.add(channel.getChannelCode());
			}
		}
		
		/*List<RfParentVersion> lists = service.initQuery().andEqualTo("osType", parentOsType)
		.andEqualTo("parentVersionCode", bean.getParentVersionCode())
		.andEqualTo("parentChannelCode", Constants.CHANNEL_CODE)
		.addOrderClause("createTime", "desc")
		.findStopTrue();
		
		if(null == lists || lists.size() < 1){
			throw new BusinessException("版本编码为"+bean.getParentVersionCode()+"系统列表为"+parentOsType+"的母包不能为空");
		}
		RfParentVersion version = lists.get(0);*/
		
		
		
		
		if(StringUtils.isBlank(version.getParentVersionDownloadurl())){
			throw new BusinessException("版本编码为"+bean.getParentVersionCode()+"的母包的下载地址不能为空");
		}
		if(StringUtils.isBlank(version.getParentVersionUpdateurl())){
			throw new BusinessException("版本编码为"+bean.getParentVersionCode()+"的母包的更新地址不能为空");
		}
		
		String updateUrl = "";
		String downloadUrl = "";
		String url = "";
		String downUrl = filePathUtils.getConfigDownloadUrl();
		String qrFilePath = filePathUtils.getFilePath()+"/images/channel/qr_code/";
    	String qrFileLinkUrl = filePathUtils.getFileLinkUrl()+"/channel/qr_code/";
    	
		for (String channelCode : list) {//开始便利渠道
			RfChannel channel = null;
			List<RfChannel> channels = channelService.initQuery()
					.andEqualTo("channelCode", channelCode.trim())
					.andEqualTo("clientType", version.getOsType())
					.findStopTrue();
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
			.andEqualTo("channelOsType", parentOsType)
			.andEqualTo("channelCode", channelCode.trim())
			.findStopTrue();
			if(null != versions && versions.size()>0){//分包是否为空
				for (RfChannelVersion rfChannelVersion : versions) {
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("channelName", channel.getChannelName());
					map.put("channelID", channel.getChannelCode());
					map.put("channelVersion", bean.getParentVersionCode());
					map.put("discoverLimit", discoverLimit);
					map.put("taskLimit", taskLimit);
					JSONObject json = JSONObject.fromObject(map);
					String str = json.toString();
					
					long fileSize = 0;
					if(OsTypeUtils.ANDROID.equals(version.getOsType())){
						fileSize = PackagingTool.copyChannelFile(url, channel.getChannelCode(), str);
					}else if(OsTypeUtils.WIN.equals(version.getOsType())||OsTypeUtils.WIN_ST.equals(version.getOsType())){
						fileSize = pcSubPackage(url, channel.getChannelCode(),channel.getChannelName());
					}
					rfChannelVersion.setFileSize(fileSizeToString(fileSize));
					
					rfChannelVersion.setChannelVersionId(rfChannelVersion.getChannelVersionId());
					rfChannelVersion.setChannelVersionCode(bean.getParentVersionCode());
					rfChannelVersion.setChannelVersionName(bean.getParentVersionName());
					rfChannelVersion.setChannelVersionDesc(bean.getParentVersionDesc());
					rfChannelVersion.setChannelVersionMust(bean.getVersionMust());
					rfChannelVersion.setChannelVersionNew(bean.getVersionNew());
					rfChannelVersion.setChannelUpdateUrl(updateUrl);
					rfChannelVersion.setChannelDownloadUrl(downloadUrl);
					rfChannelVersion.setChannelVersionTime(DateUtils.parseDate(parentVersionTimeStr));
					rfChannelVersion.setChannelOsType(parentOsType);
					rfChannelVersion.setModifier(SessionUtils.getCurrentUserId(request));
					rfChannelVersion.setModifyTime(new Date());
					rfChannelVersion.setDiscoverLimit(discoverLimit);
					rfChannelVersion.setTaskLimit(taskLimit);
					
					if("0".equals(channel.getMaintStatus())){//当渠道“取消维护”时，下载地址设置为主页
						rfChannelVersion.setChannelDownloadUrl(version.getParentVersionDownloadurl());
						rfChannelVersion.setChannelUpdateUrl(version.getParentVersionDownloadurl());
					}
					
					//TODO 这里生产二维码
					String rootPath=request.getRealPath("/");
					String fileName = QRCodeUtil.encode(rfChannelVersion.getChannelDownloadUrl(),rootPath+"/static/images/qr_logo.png",qrFilePath,true);
					String qrUrl = qrFileLinkUrl+fileName;
					rfChannelVersion.setQrCodeUrl(qrUrl);
					
					channelVersionService.updateChannelVersion(rfChannelVersion);
				}
			}else{
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("channelName", channel.getChannelName());
				map.put("channelID", channel.getChannelCode());
				map.put("channelVersion", bean.getParentVersionCode());
				map.put("channelVersion", bean.getParentVersionCode());
				map.put("discoverLimit", discoverLimit);
				map.put("taskLimit", taskLimit);
				JSONObject json = JSONObject.fromObject(map);
				String str = json.toString();
				
				long fileSize = 0;
				if(OsTypeUtils.ANDROID.equals(version.getOsType())){
					fileSize = PackagingTool.copyChannelFile(url, channel.getChannelCode(), str);
				}else if(OsTypeUtils.WIN.equals(version.getOsType())||OsTypeUtils.WIN_ST.equals(version.getOsType())){
					fileSize = pcSubPackage(url, channel.getChannelCode(),channel.getChannelName());
				}
				
				RfChannelVersion rfChannelVersion = new RfChannelVersion();
				rfChannelVersion.setFileSize(fileSizeToString(fileSize));
				
				rfChannelVersion.setChannelCode(channelCode.trim());
				rfChannelVersion.setChannelVersionCode(bean.getParentVersionCode());
				rfChannelVersion.setChannelVersionName(bean.getParentVersionName());
				rfChannelVersion.setChannelVersionDesc(bean.getParentVersionDesc());
				rfChannelVersion.setChannelVersionMust(bean.getVersionMust());
				rfChannelVersion.setChannelVersionNew(bean.getVersionNew());
				rfChannelVersion.setChannelUpdateUrl(updateUrl);
				rfChannelVersion.setChannelDownloadUrl(downloadUrl);
				rfChannelVersion.setChannelVersionTime(DateUtils.parseDate(parentVersionTimeStr));
				rfChannelVersion.setChannelOsType(parentOsType);
				rfChannelVersion.setStatus(YesOrNoStatus.YES);
				rfChannelVersion.setEnableStatus(YesOrNoStatus.YES);
				rfChannelVersion.setCreater(SessionUtils.getCurrentUserId(request));
				rfChannelVersion.setCreateTime(new Date());
				rfChannelVersion.setDiscoverLimit(discoverLimit);
				rfChannelVersion.setTaskLimit(taskLimit);
				
				if("0".equals(channel.getMaintStatus())){//当渠道“取消维护”时，下载地址设置为主页
					rfChannelVersion.setChannelDownloadUrl(version.getParentVersionDownloadurl());
					rfChannelVersion.setChannelUpdateUrl(version.getParentVersionDownloadurl());
				}
				
				//TODO 这里生产二维码
				String rootPath=request.getRealPath("/");
				String fileName = QRCodeUtil.encode(rfChannelVersion.getChannelDownloadUrl(),rootPath+"/static/images/qr_logo.png",qrFilePath,true);
				String qrUrl = qrFileLinkUrl+fileName;
				rfChannelVersion.setQrCodeUrl(qrUrl);
				
				channelVersionService.saveChannelVersion(rfChannelVersion);
			}
		}
    }
    
    private long pcSubPackage(String url, String channelCode,String channelName){
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
			throw new BusinessException("pc分包失败，未知原因，请联系管理员");
		}finally{
			FileUtils.deleteQuietly(file);//注释将不删除
		}
		return new File(newPath).length();
    }
    
    private String fileSizeToString(long fileSize){
    	String result = null;
    	DecimalFormat df = new DecimalFormat("######0.00");
    	if(fileSize>(1024*1024)){
    		result = df.format(fileSize*1.0/1024/1024)+"MB";
    	}else if(fileSize>1024){
    		result = df.format(fileSize*1.0/1024)+"KB";
    	}else{
    		result = fileSize+"B";
    	}
    	
    	return result;
    }
    
}
