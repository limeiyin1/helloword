package com.redfinger.manager.modules.market.web;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.base.FilePathUtils;
import com.redfinger.manager.common.domain.RfDevice;
import com.redfinger.manager.common.domain.RfPad;
import com.redfinger.manager.common.domain.RfVersion;
import com.redfinger.manager.common.domain.SysConfig;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.FileUtils;
import com.redfinger.manager.common.utils.OsTypeUtils;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.modules.base.service.ConfigService;
import com.redfinger.manager.modules.market.service.MarketVersionService;

@Controller
@RequestMapping(value = "/market/version")
public class MarketVersionController extends BaseController {
	@Autowired
	MarketVersionService service;
	@Autowired
	FileUtils fileUtil;
	@Autowired
	ConfigService sysConfigService;
	@Autowired
	FilePathUtils filePathUtils;


	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("osTypes", OsTypeUtils.DICT_MAP);
		return this.toPage(request, response, model);
	}
   
	//页面查询的时候返回数据
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfVersion> list(HttpServletRequest request, HttpServletResponse response, Model model, RfVersion bean) throws Exception {
		List<RfVersion> list = service.initQuery(bean)
				.andEqualTo("osType", bean.getOsType())
				.andLike("versionName", bean.getVersionName())		
				.andLike("versionCode", bean.getVersionCode())		
				.andLike("versionDesc", bean.getVersionDesc())
				.andLike("remark", bean.getRemark())
				.andGreaterThanOrEqualTo("versionTime",DateUtils.parseDate(bean.getBeginTimeStr()))
				.andLessThan("versionTime", DateUtils.parseDateAddOneDay(bean.getEndTimeStr()))
				.addOrderClause("createTime", "asc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		PageInfo<RfVersion> pageInfo = new PageInfo<RfVersion>(list);
		return pageInfo;
	}

	
	
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfVersion bean) throws Exception {
		if (bean.getVersionId() == null) {

		} else {
			bean = service.get(bean.getVersionId());
		}
		model.addAttribute("bean", bean);
		Map<String,String> map = OsTypeUtils.getOsType();
		model.addAttribute("map", map);
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "editBatch")
	public String editBatch(HttpServletRequest request, HttpServletResponse response, Model model, RfVersion bean) throws Exception {
		if (StringUtils.isBlank(bean.getIds())) {
			throw new BusinessException("请选择android版本的数据！");
		}
		 
		model.addAttribute("ids",bean.getIds());
		model.addAttribute("android",OsTypeUtils.ANDROID);
		model.addAttribute("map",OsTypeUtils.getOsType());
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "editBatchVersion")
	public void editBatchVersion(HttpServletRequest request, HttpServletResponse response, Model model, RfVersion bean,
			String appTime,String appName) throws Exception {
		log.info("[ids{},versionCode{},versionDesc{},versionName{},appTime{},appName{}]",
				new Object[]{bean.getIds(),bean.getVersionCode(),bean.getVersionDesc(),bean.getVersionName(),
				appTime,appName});
		if (StringUtils.isBlank(bean.getIds())) {
			throw new BusinessException("请选择android版本的数据！");
		}
		if(StringUtils.isBlank(bean.getVersionCode())){
			throw new BusinessException("请输入版本编号！");
		}
		if(StringUtils.isBlank(bean.getVersionDesc())){
			throw new BusinessException("请输入版本内容！");
		}
		if(StringUtils.isBlank(bean.getVersionName())){
			throw new BusinessException("请输入版本名称！");
		}
		if(StringUtils.isBlank(appTime)){
			throw new BusinessException("请输入app版本更新日期！");
		}
		
		String ids[] = bean.getIds().split(",");
		String appNames[] = appName.split(",");
		
		if(ids.length != appNames.length){
			throw new BusinessException("选择的版本控制和输入的版本名称数量不一致！");
		}
		
		appTime = appTime.replaceAll("-", "");
		log.info("appTime:"+appTime);
		if(StringUtils.isBlank(appName)){
			throw new BusinessException("请输入app版本名称！");
		}
		SysConfig sysConfig2 = sysConfigService.get("config_download_link_url");
		if(null == sysConfig2){
			throw new BusinessException("apk链接地址为空！");
		}
		
		String linkUrl = sysConfig2.getConfigValue();
		
		for (int i=0;i<ids.length;i++) {
			Integer id = Integer.parseInt(ids[i]);
			log.info("[versionIdId:{},appName:{}]",new Object[]{ids[i],appNames[i]});
			RfVersion rfVersion = service.get(id);
			if(OsTypeUtils.ANDROID.equals(bean.getOsType())){
				if(StringUtils.isBlank(appNames[i])){
					throw new BusinessException("app版本名称不能为空！");
				}
				
				rfVersion.setVersionCode(bean.getVersionCode());
				rfVersion.setVersionName(bean.getVersionName());
				rfVersion.setVersionDesc(bean.getVersionDesc());
				rfVersion.setDownloadUrl(linkUrl+appTime+"/"+appNames[i]);
				rfVersion.setUpdateUrl(linkUrl+appTime+"/"+appNames[i]);
				rfVersion.setModifier(SessionUtils.getCurrentUserId(request));
				rfVersion.setModifyTime(new Date());
				if(!(service.updateVersion(rfVersion)>0)){
					throw new BusinessException(rfVersion.getVersionName()+"修改失败！");
				}
			}else{
				throw new BusinessException(rfVersion.getVersionName()+"不是android的版本！");
			}
		}
		
		/*service.editBatchVersion(request, bean, appTime, appName, linkUrl);*/
	}
	
	@RequestMapping(value = "addform")
	public String addform(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<String,String> map = OsTypeUtils.getOsType();
		model.addAttribute("map", map);
		return this.toPage(request, response, model);
	}

	
	//保存的时候出现的界面
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfVersion bean) throws Exception {
		log.info("updateUrl:"+bean.getUpdateUrl()+";downloadUrl:"+bean.getDownloadUrl());
		service.save(request, bean);
	}
	
	
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfVersion bean) throws Exception {
		log.info("updateUrl:"+bean.getUpdateUrl()+";downloadUrl:"+bean.getDownloadUrl());
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfVersion bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfVersion bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfVersion bean) throws Exception {
		service.delete(request, bean);
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
    	/*SysConfig sysConfig = sysConfigService.get("config_download_url");
    	SysConfig sysConfig2 = sysConfigService.get("config_download_link_url");*/
    	
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
    	
    	//MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //List<MultipartFile> fileList = multipartRequest.getFiles("fileInput");
        Map<String, String> fileMap = null;
        if (null != file) {
        	
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
    	
    	/*SysConfig sysConfig = sysConfigService.get("config_download_url");
    	SysConfig sysConfig2 = sysConfigService.get("config_download_link_url");*/
    	
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
    	//MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //List<MultipartFile> fileList = multipartRequest.getFiles("fileInput");
        Map<String, String> fileMap = null;
        if (null != file) {
        	
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
     * 查询二维码
     * @param request
     * @param response
     * @param model
     * @param bean
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "lookCord")
	public String lookCord(HttpServletRequest request, HttpServletResponse response, Model model, RfVersion bean) throws Exception {
		bean = service.get(bean.getVersionId());
    	model.addAttribute("downloadUrl", bean.getDownloadUrl());
		return this.toPage(request, response, model);
	}

    /**
     * 绑定部分设备
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "bindPartPad")
	public String bindPartPad(HttpServletRequest request, HttpServletResponse response, Model model,RfVersion bean) throws Exception {
		model.addAttribute("versionId", bean.getVersionId());
    	return this.toPage(request, response, model);
	}
     
    @RequestMapping(value = "bindPad")
	public void bindPad(HttpServletRequest request, HttpServletResponse response, Model model,
			RfPad pad,Integer versionId,RfDevice device) throws Exception {
		log.info("[padName{},padCode{},padIp{},versionId]",
				new Object[]{pad.getPadName(),pad.getPadCode(),pad.getPadId(),versionId});
    	log.info("[deviceName{},deviceIp{]]",new Object[]{device.getDeviceName(),device.getDeviceIp()});
		if(StringUtils.isEmpty(pad.getPadName()) && 
				StringUtils.isEmpty(pad.getPadCode()) && 
				StringUtils.isEmpty(pad.getPadIp()) && 
				StringUtils.isEmpty(device.getDeviceName()) && 
				StringUtils.isEmpty(device.getDeviceIp())){
			throw new BusinessException("请填写查询条件！");
		}
		
		RfVersion versionMapper = service.get(versionId);
    	if(null == versionMapper){
    		throw new BusinessException("启动器"+versionId+"不存在");
    	}
    	service.doBindPad(request,pad,device,versionId);
	}
    
    /**
     * 绑定所有设备
     * @param request
     * @param response
     * @param model
     * @param bean
     * @throws Exception
     */
    @RequestMapping(value = "bindAllPad")
	public void bindAllPad(HttpServletRequest request, HttpServletResponse response, Model model, RfVersion bean) throws Exception {
		log.info("{versionId[]}", new Object[]{bean.getVersionId()});
		if(null == bean.getVersionId()){
			throw new BusinessException("请选择启动器");
		}
		service.doAllPad(request, bean.getVersionId());
	}
}
