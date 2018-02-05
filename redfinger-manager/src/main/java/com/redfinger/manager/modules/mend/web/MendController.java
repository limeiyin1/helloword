package com.redfinger.manager.modules.mend.web;

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

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.base.FilePathUtils;
import com.redfinger.manager.common.domain.RfMend;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.FileUtils;
import com.redfinger.manager.common.utils.OsTypeUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.mend.service.MendService;

@Controller
@RequestMapping(value="/mend/mend")
public class MendController  extends BaseController {

	@Autowired
	MendService service;
	@Autowired
	FileUtils fileUtil;
	@Autowired
	FilePathUtils filePathUtils;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}
   
	//页面查询的时候返回数据
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfMend> list(HttpServletRequest request, HttpServletResponse response, Model model, RfMend bean) throws Exception {
		
		List<RfMend> list = service.initQuery(bean)
				.andEqualTo("mendOsType", bean.getMendOsType())
				.andEqualTo("mendOsType", bean.getMendOsType())
				.andLike("mendVersionName", bean.getMendVersionName())		
				.andLike("mendVersionCode", bean.getMendVersionCode())	
				.andEqualTo("mendChannelCode", bean.getMendChannelCode())
				.andGreaterThanOrEqualTo("mendVersionTime",DateUtils.parseDate(bean.getBeginTimeStr()))
				.andLessThan("mendVersionTime", DateUtils.parseDateAddOneDay(bean.getEndTimeStr()))
				.addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		PageInfo<RfMend> pageInfo = new PageInfo<RfMend>(list);
		return pageInfo;
	}

	
	
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfMend bean) throws Exception {
		if (bean.getMendId() == null) {

		} else {
			bean = service.get(bean.getMendId());
		}
		model.addAttribute("bean", bean);
		Map<String,String> map = OsTypeUtils.getOsType();
		model.addAttribute("map", map);
		model.addAttribute("news", YesOrNoStatus.DICT_MAP);
		model.addAttribute("osTypes", OsTypeUtils.DICT_MAP);
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "addform")
	public String addform(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("osTypes", OsTypeUtils.DICT_MAP);
		return this.toPage(request, response, model);
	}
	
	//保存的时候出现的界面
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfMend bean,String mendVersionTimeStr) throws Exception {
		bean.setMendVersionTime(DateUtils.parseDate(mendVersionTimeStr));
		
		List<RfMend> list = service.initQuery().andEqualTo("mendOsType", bean.getMendOsType())
			.andEqualTo("mendVersionCode", bean.getMendVersionCode().trim())
			.andEqualTo("mendChannelCode", bean.getMendChannelCode())
			.findAll();
		if(null != list && list.size()>0 && !list.get(0).getMendId().equals(bean.getMendId())){
			throw new BusinessException("补丁版本编码重复");
		}
		
		service.save(request, bean);
	}
	
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfMend bean,String parentVersionTimeStr) throws Exception {
		bean.setMendVersionTime(DateUtils.parseDate(parentVersionTimeStr));
		
		List<RfMend> list = service.initQuery().andEqualTo("mendOsType", bean.getMendOsType())
			.andEqualTo("mendVersionCode", bean.getMendVersionCode().trim())
			.andEqualTo("mendChannelCode", bean.getMendChannelCode())
			.findAll();
		if(null != list && list.size()>0 && !list.get(0).getMendId().equals(bean.getMendId())){
			throw new BusinessException("补丁版本编码重复");
		}
		
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfMend bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfMend bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfMend bean) throws Exception {
		service.remove(request, bean);//改成物理删除
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
    
    
}
