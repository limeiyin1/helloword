package com.redfinger.manager.modules.plugin.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
import com.redfinger.manager.common.domain.RfChannel;
import com.redfinger.manager.common.domain.RfPlugin;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.FileUtils;
import com.redfinger.manager.common.utils.OsTypeUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.market.service.MarketChannelService;
import com.redfinger.manager.modules.plugin.service.PluginService;

@Controller
@RequestMapping(value="/plugin/plugin")
public class PluginController  extends BaseController {

	@Autowired
	PluginService service;
	@Autowired
	FileUtils fileUtil;
	@Autowired
	FilePathUtils filePathUtils;
	@Autowired
	MarketChannelService channelService;
	
	@Value("#{configProperties['plugin.pluginUrl.path']}")
	private String pluginPluginUrlPath;
	@Value("#{configProperties['plugin.pluginUrl.link.path']}")
	private String pluginPluginUrlLinkPath;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}
   
	//页面查询的时候返回数据
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfPlugin> list(HttpServletRequest request, HttpServletResponse response, Model model, RfPlugin bean) throws Exception {
		List<RfPlugin> list = service.initQuery(bean)
				.andLike("pluginVersionCode", bean.getPluginVersionCode())
				.andEqualTo("pluginChannelCode", bean.getPluginChannelCode())
				.andLike("pluginCode", bean.getPluginCode())
				.andLike("channelVersionCode", bean.getChannelVersionCode())		
				.andGreaterThanOrEqualTo("createTime",DateUtils.parseDate(bean.getBeginTimeStr()))
				.andLessThan("createTime", DateUtils.parseDateAddOneDay(bean.getEndTimeStr()))
				.addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		PageInfo<RfPlugin> pageInfo = new PageInfo<RfPlugin>(list);
		return pageInfo;
	}

	
	
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfPlugin bean) throws Exception {
		if (bean.getPluginId() == null) {

		} else {
			bean = service.get(bean.getPluginId());
		}
		model.addAttribute("bean", bean);
		model.addAttribute("yesOrNos", YesOrNoStatus.DICT_MAP);
		List<RfChannel> channelList = channelService.initQuery().andEqualTo("clientType", OsTypeUtils.ANDROID).findStopTrue();//只查询安卓的
		if(channelList.size()>0){
			model.addAttribute("channelList", channelList);
		}
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "subPluginPackageForm")
	public String subPluginPackageForm(HttpServletRequest request, HttpServletResponse response, Model model, RfPlugin bean) throws Exception {
		if (bean.getPluginId() == null) {
			
		} else {
			bean = service.get(bean.getPluginId());
		}
		model.addAttribute("bean", bean);
		model.addAttribute("yesOrNos", YesOrNoStatus.DICT_MAP);
		List<RfChannel> channelList = channelService.initQuery().andEqualTo("clientType", OsTypeUtils.ANDROID).findStopTrue();//只查询安卓的
		if(channelList.size()>0){
			model.addAttribute("channelList", channelList);
		}
		
		List<RfPlugin> pluginList = service.initQuery().andEqualTo("pluginChannelCode", "com.redfinger.app").findStopTrue();
		if(pluginList.size()>0){
			model.addAttribute("pluginList", pluginList);
		}
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "addform")
	public String addform(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("yesOrNos", YesOrNoStatus.DICT_MAP);
		List<RfChannel> channelList = channelService.initQuery().andEqualTo("clientType", OsTypeUtils.ANDROID).findStopTrue();//只查询安卓的
		if(channelList.size()>0){
			model.addAttribute("channelList", channelList);
		}
		return this.toPage(request, response, model);
	}
	
	//保存的时候出现的界面
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfPlugin bean) throws Exception {
		
		if(StringUtils.isBlank(bean.getPluginCode())){
			throw new BusinessException("插件编码不能为空");
		}
		if(!Pattern.matches("^([a-z_A-Z-0-9]+)$",bean.getPluginCode())){
			throw new BusinessException("插件编码格式错误，只能由数字、26个英文字母、下划线（_）以及横线（-）组成");
		}
		
		List<RfPlugin> list = service.initQuery().andEqualTo("pluginCode", bean.getPluginCode().trim())
				.andEqualTo("pluginChannelCode", bean.getPluginChannelCode().trim()).findAll();
		if(null != list && list.size()>0){
			throw new BusinessException("已存在编码为"+bean.getPluginCode().trim()+"的插件");
		}
		String enableStatus = bean.getEnableStatus();
		log.info(enableStatus);
		service.save(request, bean);
	}
	
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfPlugin bean,String parentVersionTimeStr) throws Exception {
		if(StringUtils.isBlank(bean.getPluginCode())){
			throw new BusinessException("插件编码不能为空");
		}
		
		if(!Pattern.matches("^([a-z_A-Z-0-9]+)$",bean.getPluginCode())){
			throw new BusinessException("插件编码格式错误，只能由数字、26个英文字母、下划线（_）以及横线（-）组成");
		}
		
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfPlugin bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfPlugin bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfPlugin bean) throws Exception {
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
    	
    	String downLoadUrl = pluginPluginUrlPath;
    	String downLoadLinkUrl = pluginPluginUrlLinkPath;
    	
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
	 * 插件分包
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @param mendVersionTimeStr
	 * @param isYes
	 * @param channelIds
	 * @throws Exception
	 */
	@RequestMapping(value = "subPlugin")
	public void subPlugin(HttpServletRequest request, HttpServletResponse response, Model model,
			RfPlugin bean, String mendVersionTimeStr, String channelIds ,String isYes,String pluginCode) throws Exception {
		
		if(StringUtils.isBlank(pluginCode)){
			throw new BusinessException("插件编码不能为空！");
		}
		
		if(StringUtils.isBlank(bean.getPluginVersionCode())){
			throw new BusinessException("插件版本号不能为空！");
		}
		
		if(StringUtils.isBlank(bean.getChannelVersionCode())){
			throw new BusinessException("渠道客户端版本编码不能为空");
		}
		if(StringUtils.isBlank(bean.getPluginUrl())){
			throw new BusinessException("插件地址不能为空");
		}
		if(!Pattern.matches("^([a-z_A-Z-0-9]+)$",bean.getPluginCode())){
			throw new BusinessException("插件编码格式错误，只能由数字、26个英文字母、下划线（_）以及横线（-）组成");
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
				list.add(Integer.parseInt(string.trim()));
			}
			if(list.size()>0){
				channelList = channelService.initQuery().andIn("channelId", list).findStopTrue();
			}
		}else if(YesOrNoStatus.YES.equals(isYes)){//所有渠道分包
			channelList = channelService.initQuery().findStopTrue();
		}
		
		if(channelList.size()<0){
			throw new BusinessException("渠道不能为空");
		}
		
		for (RfChannel rfChannel : channelList) {
			String pluginUrl = "";
			String url = "";
			String downUrl = filePathUtils.getConfigDownloadUrl();
			
			String updateUrls[] = bean.getPluginUrl().split("\\.");
			pluginUrl = getUrl(rfChannel.getChannelCode(), bean.getPluginUrl(),updateUrls[updateUrls.length-1]);
			
			String loadUrl[] = bean.getPluginUrl().split("\\/");
			url = downUrl + "/" /*+ loadUrl[loadUrl.length-3] + "/"*/ + loadUrl[loadUrl.length-2] + "/" + loadUrl[loadUrl.length-1] ;
			
			log.info("文件存放地址："+url);
			
			List<RfPlugin> pluginList = service.initQuery().andEqualTo("pluginChannelCode", rfChannel.getChannelCode().trim())
					.andEqualTo("pluginCode", pluginCode.trim()).findAll();
			if(null != pluginList && pluginList.size()>0){//分包是否为空
				for (RfPlugin plugin : pluginList) {
						plugin.setPluginChannelCode(rfChannel.getChannelCode());
						plugin.setPluginUrl(pluginUrl);
						
						service.updatePlugin(plugin, bean,request);
					
				}
			}else{
				RfPlugin plugin = new RfPlugin();
				plugin.setPluginChannelCode(rfChannel.getChannelCode());
				plugin.setPluginUrl(pluginUrl);
				
				service.savePlugin(plugin, bean,request);
			}
		}
		
	}
    
	 /**
     * 根据插件id查看插件信息
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "selectPlugin")
    @ResponseBody
    public RfPlugin selectPlugin(HttpServletRequest request, HttpServletResponse response, Model model,String pluginId){
    	
    	
    	List<RfPlugin> result = service.initQuery().andEqualTo("pluginId", Integer.valueOf(pluginId.trim())).singleDelTrue();
    	
    	RfPlugin plugin = new RfPlugin();
    	
    	if(result.size()>0){
    		plugin = result.get(0);
    	}
    	
    	return plugin;
    }
}
