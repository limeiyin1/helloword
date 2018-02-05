package com.redfinger.manager.modules.picture.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import com.redfinger.manager.common.http.HttpClient;
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
import com.redfinger.manager.common.base.HsSdk;
import com.redfinger.manager.common.domain.RfPicture;
import com.redfinger.manager.common.domain.RfPictureModule;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.FileUtils;
import com.redfinger.manager.common.utils.PictureLinkTypeUtils;
import com.redfinger.manager.common.utils.PictureModuleCodeUtils;
import com.redfinger.manager.modules.base.service.ConfigService;
import com.redfinger.manager.modules.picture.service.PictureModuleService;
import com.redfinger.manager.modules.picture.service.PictureService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Controller
@RequestMapping(value="/picture/picture")
public class PictureController extends BaseController {
	@Autowired
	PictureService pictureService;
	@Autowired
	PictureModuleService pictureModuleService;
	@Autowired
	FileUtils fileUtil;
	@Autowired
	ConfigService sysConfigService;
	@Autowired
	private FilePathUtils filePathUtils;
	
	//游戏列表
	private static String hs_url = HsSdk.HS_SDK_URL + "/api/public/v1/game/list";
	private static Map<String, String> paramMap = new HashMap<String,String>();
	
	static{
		paramMap.put("clientid", HsSdk.HS_SDK_CLIENTID);
		paramMap.put("appid", HsSdk.HS_SDK_APPID);
		paramMap.put("hot", "1");	//排行
		paramMap.put("offset", "999");	//分页，默认999
	}
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		List<RfPictureModule> pmList=pictureModuleService.initQuery().findStopTrue();
		model.addAttribute("pmList", pmList);
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfPicture> list(HttpServletRequest request, HttpServletResponse response, Model model, RfPicture bean,String begin,String end) throws Exception {
		Date beginTime = null;
		Date endTime = null;
		if (StringUtils.isNotBlank(begin)) {
			beginTime = DateUtils.parseDate(begin);
		}
		if (StringUtils.isNotBlank(end)) {
			endTime = DateUtils.addDays(DateUtils.parseDate(end), 1);
		}
		
		List<RfPicture> list = pictureService.initQuery(bean)
				.andLike("pictureName", bean.getPictureName())
				.andEqualTo("moduleId", bean.getModuleId())
				.andEqualTo("client", bean.getClient())
				.andEqualTo("enableStatus", bean.getEnableStatus())// 根据启用禁用状态查询
				.andGreaterThanOrEqualTo("createTime", beginTime).andLessThan("createTime", endTime)
				.addOrderClause("createTime", "desc").addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		if(null!=list && list.size()>0){
			for (RfPicture rfPicture : list) {
				if(null!=rfPicture.getModuleId()){
					RfPictureModule module = pictureModuleService.load(rfPicture.getModuleId());
					if(null != module){
						rfPicture.getMap().put("moduleName", module.getModuleName());
						
						if(StringUtils.equals(module.getModuleCode(), PictureModuleCodeUtils.SY)){//首页轮播图
							rfPicture.getMap().put("androidPictureUrl", rfPicture.getOnePictureUrl());
						}else if(StringUtils.equals(module.getModuleCode(), PictureModuleCodeUtils.AD)){//如果是普通设备广告图
							rfPicture.getMap().put("pcPictureUrl", rfPicture.getOnePictureUrl());
							rfPicture.getMap().put("androidPictureUrl", rfPicture.getSevenPictureUrl());
						}else if(StringUtils.equals(module.getModuleCode(), PictureModuleCodeUtils.YD) 
								|| StringUtils.equals(module.getModuleCode(), PictureModuleCodeUtils.HDGG)
								|| StringUtils.equals(module.getModuleCode(), PictureModuleCodeUtils.RWHD)){//如果是引导页面或活动图或任务活动
							rfPicture.getMap().put("androidPictureUrl", rfPicture.getSixPictureUrl());
						}
					}
				}
			}
		}
		
		PageInfo<RfPicture> pageInfo = new PageInfo<RfPicture>(list);
		return pageInfo;
	}
	
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfPicture bean)
			throws Exception {
		List<RfPictureModule> pmList=pictureModuleService.initQuery().findStopTrue();
		model.addAttribute("pmList", pmList);
		if (bean.getPictureId() == null) {
		} else {
			bean = pictureService.get(bean.getPictureId());
			model.addAttribute("bean", bean);
			if(StringUtils.isNotBlank(bean.getLinkParametersJson())){
				String linkJson = bean.getLinkParametersJson();
				Map<String,Object> map = this.toHashMap(linkJson);
				if(map.containsKey("linkType")){
					model.addAttribute("linkType",map.get("linkType"));
				}
				
				if(map.containsKey("apkId")){
					model.addAttribute("apkId",map.get("apkId"));
				}
				if(map.containsKey("apkName")){
					model.addAttribute("apkName",map.get("apkName"));
				}
				if(map.containsKey("webLink")){
					model.addAttribute("webLink",map.get("webLink"));
				}
			}
			
			RfPictureModule module = pictureModuleService.get(bean.getModuleId());
			
			model.addAttribute("moduleCode", module.getModuleCode());
		}
		
		try {
			//从火速获取游戏列表
			HttpClient httpClient = new HttpClient(hs_url, 6000, 6000);
			int responseCode = httpClient.send(paramMap, "UTF-8");
			List<Map<String, Object>> apkList = new ArrayList<Map<String, Object>>();
			if(responseCode == 200){
				Map<String, Object> apkMap = null;
				JSONObject json = JSONObject.fromObject(httpClient.getResult());
				if(json.getInt("code") == 200){
					JSONObject dataJson = json.getJSONObject("data");
					JSONArray gamesJson = dataJson.getJSONArray("game_list");
			
					for (int i = 0,size = gamesJson.size(); i < size; i++) {
						json = gamesJson.getJSONObject(i);
						apkMap = new HashMap<String, Object>();
						apkMap.put("id", json.getInt("gameid"));
						apkMap.put("name", json.getString("gamename"));
						apkMap.put("icon", json.getString("icon"));
						apkMap.put("oneword", json.getString("oneword"));
						apkList.add(apkMap);					
					}
				}
			}
			model.addAttribute("apkList", apkList);
		} catch (Exception e) {
			log.error("get sdk game list error:"+e.getMessage(), e);
		}
		model.addAttribute("links", PictureLinkTypeUtils.DICT_MAP);
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, 
			RfPicture bean,String linkType,String apkId,String apkName,String webLink,String moduleCode) throws Exception {
		if(StringUtils.isBlank(moduleCode)){
			throw new BusinessException("图片模块不能为空！");
		}else{
			List<RfPictureModule> module = pictureModuleService.initQuery().andEqualTo("moduleCode", moduleCode).findStopTrue();
			if(!(null != module && module.size()>0)){
				throw new BusinessException("选择的图片模块无效！");
			}else{
				bean.setModuleId(module.get(0).getModuleId());
			}
		}
		if(bean.getPlayTime() <= 0){
			throw new BusinessException("播放时间不能小于或者等于0！");
		}
		bean.setStartTime(DateUtils.parseDate(bean.getBeginTimeStr()));
		bean.setEndTime(DateUtils.parseDate(bean.getEndTimeStr()));
		bean.setOnePictureUrl(bean.getSevenPictureUrl());//sevenPictureUrl 和 onePictureUrl保持一样 业务需求
		bean.setFourPictureUrl(bean.getSevenPictureUrl());
		bean.setTwoPictureUrl(bean.getSevenPictureUrl());
		bean.setSixPictureUrl(bean.getSevenPictureUrl());
		//bean.setSixSixPictureUrl(bean.getSevenPictureUrl());
		bean.setOneSevenPrictureUrl(bean.getSevenPictureUrl());
		bean.setOneFourPictureUrl(bean.getSevenPictureUrl());
		bean.setSevenFourPictureUrl(bean.getSevenPictureUrl());
		
		if(StringUtils.isNotBlank(linkType)){
			Map<String,Object> map = new HashMap<String,Object>();
			if(linkType.equals(PictureLinkTypeUtils.WEB_PAGE)){
				map.put("linkType", linkType);
				map.put("webLink", webLink);
			}else if(linkType.equals(PictureLinkTypeUtils.APK_DETAIL)){
				map.put("linkType", linkType);
				map.put("apkId", apkId);
				map.put("apkName", apkName);
			}
			JSONObject json = JSONObject.fromObject(map);
			String linkParametersJson = json.toString();
			bean.setLinkParametersJson(linkParametersJson);
		}
		pictureService.savePicture(request, bean);
	}
	
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfPicture bean,
			String linkType,String apkId,String apkName,String webLink,String moduleCode) throws Exception {
		
		if(StringUtils.isBlank(moduleCode)){
			throw new BusinessException("图片模块不能为空！");
		}else{
			List<RfPictureModule> module = pictureModuleService.initQuery().andEqualTo("moduleCode", moduleCode).findStopTrue();
			if(!(null != module && module.size()>0)){
				throw new BusinessException("选择的图片模块无效！");
			}else{
				bean.setModuleId(module.get(0).getModuleId());
			}
		}
		
		if(bean.getPlayTime() <= 0){
			throw new BusinessException("播放时间不能小于或者等于0！");
		}
		bean.setStartTime(DateUtils.parseDate(bean.getBeginTimeStr()));
		bean.setEndTime(DateUtils.parseDate(bean.getEndTimeStr()));
		if(StringUtils.isNotBlank(linkType)){
			Map<String,Object> map = new HashMap<String,Object>();
			if(linkType.equals(PictureLinkTypeUtils.WEB_PAGE)){
				map.put("linkType", linkType);
				map.put("webLink", webLink);
			}else if(linkType.equals(PictureLinkTypeUtils.APK_DETAIL)){
				map.put("linkType", linkType);
				map.put("apkId", apkId);
				map.put("apkName", apkName);
			}
			JSONObject json = JSONObject.fromObject(map);
			String linkParametersJson = json.toString();
			bean.setLinkParametersJson(linkParametersJson);
		}
		bean.setOnePictureUrl(bean.getSevenPictureUrl());//sevenPictureUrl 和 onePictureUrl保持一样 业务需求
		bean.setFourPictureUrl(bean.getSevenPictureUrl());
		bean.setTwoPictureUrl(bean.getSevenPictureUrl());
		bean.setSixPictureUrl(bean.getSevenPictureUrl());
		//bean.setSixSixPictureUrl(bean.getSevenPictureUrl());
		bean.setOneSevenPrictureUrl(bean.getSevenPictureUrl());
		bean.setOneFourPictureUrl(bean.getSevenPictureUrl());
		bean.setSevenFourPictureUrl(bean.getSevenPictureUrl());
		
		pictureService.updatePicture(request, bean);
	}
	
	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfPicture bean)
			throws Exception {
		pictureService.startPicture(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfPicture bean)
			throws Exception {
		pictureService.stopPicture(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfPicture bean)
			throws Exception {
		pictureService.removePicture(request, bean);
	}
	
	/**
     * 上传文件
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "uploadOneImageRequest")
    @ResponseBody
    public  JSONObject  uploadOneImageRequest( 
    		@RequestParam(value = "fileUpdate", required = false) MultipartFile file,
    		HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	
    	return this.uploadImage(file, request, response, model);
    }
    
    /**
     * 上传文件
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "uploadTwoImageRequest")
    @ResponseBody
    public  JSONObject  uploadSevenRequest( 
    		@RequestParam(value = "fileUpdate2", required = false) MultipartFile file,
    		HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	
    	return this.uploadImage(file, request, response, model);
    }
    
    /**
     * 上传文件
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "uploadThreeImageRequest")
    @ResponseBody
    public  JSONObject  uploadOneRequest( 
    		@RequestParam(value = "fileUpdate3", required = false) MultipartFile file,
    		HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	return this.uploadImage(file, request, response, model);
    }
    
    @RequestMapping(value = "uploadFourImageRequest")
    @ResponseBody
    public  JSONObject  uploadFourImageRequest( 
    		@RequestParam(value = "fileUpdate4", required = false) MultipartFile file,
    		HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	return this.uploadImage(file, request, response, model);
    }
    
    @RequestMapping(value = "uploadFiveImageRequest")
    @ResponseBody
    public  JSONObject  uploadFiveImageRequest( 
    		@RequestParam(value = "fileUpdate5", required = false) MultipartFile file,
    		HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	return this.uploadImage(file, request, response, model);
    }
    
    @RequestMapping(value = "uploadSixImageRequest")
    @ResponseBody
    public  JSONObject  uploadSixImageRequest( 
    		@RequestParam(value = "fileUpdate6", required = false) MultipartFile file,
    		HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	return this.uploadImage(file, request, response, model);
    }
    
    @RequestMapping(value = "uploadSevenImageRequest")
    @ResponseBody
    public  JSONObject  uploadSevenImageRequest( 
    		@RequestParam(value = "fileUpdate7", required = false) MultipartFile file,
    		HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	return this.uploadImage(file, request, response, model);
    }
    
    @RequestMapping(value = "uploadEightImageRequest")
    @ResponseBody
    public  JSONObject  uploadEightImageRequest( 
    		@RequestParam(value = "fileUpdate8", required = false) MultipartFile file,
    		HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	return this.uploadImage(file, request, response, model);
    }
    
    @RequestMapping(value = "uploadNineImageRequest")
    @ResponseBody
    public  JSONObject  uploadNineImageRequest( 
    		@RequestParam(value = "fileUpdate9", required = false) MultipartFile file,
    		HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	return this.uploadImage(file, request, response, model);
    }
    
    private JSONObject uploadImage(MultipartFile file,
    		HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
    	String  imageUrl = filePathUtils.getImageUrl()+"/mpic";
    	String imageLinkUrl = filePathUtils.getImageLinkUrl()+"/mpic";
    	
    	log.info("图片下载文件链接地址:"+imageLinkUrl);
    	log.info("是否存在上传文件，"+file.isEmpty() + "" + file.getSize());
    	response.setContentType("text/html");
        Map<String, String> fileMap = null;
        if (!file.isEmpty()) {
        	
            fileMap = fileUtil.saveImage(file,imageUrl,imageLinkUrl);
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
