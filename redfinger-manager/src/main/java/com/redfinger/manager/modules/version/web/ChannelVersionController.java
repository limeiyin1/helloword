package com.redfinger.manager.modules.version.web;

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
import com.redfinger.manager.common.domain.RfChannelVersion;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.FileUtils;
import com.redfinger.manager.common.utils.OsTypeUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.version.service.ChannelVersionService;

@Controller
@RequestMapping(value="/channel/version")
public class ChannelVersionController   extends BaseController {

	@Autowired
	ChannelVersionService service;
	@Autowired
	FileUtils fileUtil;
	@Autowired
	FilePathUtils filePathUtils;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("osTypes", OsTypeUtils.DICT_MAP);
		model.addAttribute("yesNos", YesOrNoStatus.DICT_MAP);
		return this.toPage(request, response, model);
	}
   
	//页面查询的时候返回数据
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfChannelVersion> list(HttpServletRequest request, HttpServletResponse response, Model model, RfChannelVersion bean) throws Exception {
		List<RfChannelVersion> list = service.initQuery(bean)
				.andEqualTo("channelOsType", bean.getChannelOsType())
				.andEqualTo("enableStatus", bean.getEnableStatus())
				.andEqualTo("discoverLimit", bean.getDiscoverLimit())
				.andEqualTo("taskLimit", bean.getTaskLimit())
				.andLike("channelVersionName", bean.getChannelVersionName())		
				.andLike("channelVersionCode", bean.getChannelVersionCode())	
				.andLike("channelCode", bean.getChannelCode())
				.andGreaterThanOrEqualTo("channelVersionTime",DateUtils.parseDate(bean.getBeginTimeStr()))
				.andLessThan("channelVersionTime", DateUtils.parseDateAddOneDay(bean.getEndTimeStr()))
				.addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		PageInfo<RfChannelVersion> pageInfo = new PageInfo<RfChannelVersion>(list);
		return pageInfo;
	}
	
	
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfChannelVersion bean) throws Exception {
		if (bean.getChannelVersionId() == null) {

		} else {
			bean = service.get(bean.getChannelVersionId());
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
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfChannelVersion bean,String channelVersionTimeStr) throws Exception {
		if(StringUtils.isNotBlank(channelVersionTimeStr)){
			bean.setChannelVersionTime(DateUtils.parseDate(channelVersionTimeStr));
		}
		
		List<RfChannelVersion> list = service.initQuery().andEqualTo("channelOsType", bean.getChannelOsType())
		.andEqualTo("channelVersionCode", bean.getChannelVersionCode())
		.andEqualTo("channelCode", bean.getChannelCode())
		.findAll();
		if(null != list && list.size()>0){
			throw new BusinessException("类型为"+bean.getChannelOsType()+"的渠道的版本编码重复");
		}
		service.save(request, bean);
	}
	
	
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfChannelVersion bean,String channelVersionTimeStr) throws Exception {
		bean.setChannelVersionTime(DateUtils.parseDate(channelVersionTimeStr));
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfChannelVersion bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfChannelVersion bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfChannelVersion bean) throws Exception {
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
