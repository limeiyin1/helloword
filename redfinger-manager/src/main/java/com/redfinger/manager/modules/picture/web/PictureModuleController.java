package com.redfinger.manager.modules.picture.web;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.base.FilePathUtils;
import com.redfinger.manager.common.domain.RfPicture;
import com.redfinger.manager.common.domain.RfPictureModule;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.FileUtils;
import com.redfinger.manager.common.utils.PictureLinkTypeUtils;
import com.redfinger.manager.modules.base.service.ConfigService;
import com.redfinger.manager.modules.picture.base.PictureVo;
import com.redfinger.manager.modules.picture.service.PictureModuleService;
import com.redfinger.manager.modules.picture.service.PictureService;
@Controller
@RequestMapping(value="/picture/module")
public class PictureModuleController extends BaseController {
	@Autowired
	private PictureModuleService pictureModuleService;
	@Autowired
	private PictureService pictureService;
	@Autowired
	FileUtils fileUtil;
	@Autowired
	ConfigService sysConfigService;
	@Autowired
	private FilePathUtils filePathUtils;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfPictureModule> list(HttpServletRequest request, HttpServletResponse response, Model model, RfPictureModule bean) throws Exception {
		List<RfPictureModule> list = pictureModuleService.initQuery(bean)
				.andLike("moduleName", bean.getModuleName())
				.andLike("moduleCode", bean.getModuleCode())
				.andEqualTo("enableStatus", bean.getEnableStatus())
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		PageInfo<RfPictureModule> pageInfo = new PageInfo<RfPictureModule>(list);
		return pageInfo;
	}
	
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfPictureModule moduleBean)
			throws Exception {
		if (moduleBean.getModuleId() != null) {
			moduleBean = pictureModuleService.get(moduleBean.getModuleId());
			model.addAttribute("moduleBean", moduleBean);
		}
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfPictureModule bean)
			throws Exception {
		// 判断模块编码是否已经存在
		String moduleCode = bean.getModuleCode();
		List<RfPictureModule> list = pictureModuleService.initQuery().andEqualTo("moduleCode", moduleCode).singleStopTrue();
		
		if (null!=list && list.size()>0) {
			throw new BusinessException("此编码的模块已经存在！");
		}
		pictureModuleService.saveModule(request, bean);
	}
	
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfPictureModule bean)
			throws Exception {
		
		pictureModuleService.updateModule(request, bean);
	}
	
	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfPictureModule bean)
			throws Exception {
		pictureModuleService.startModule(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfPictureModule bean)
			throws Exception {
		pictureModuleService.stopModule(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfPictureModule bean)
			throws Exception {
		pictureModuleService.removeModule(request, bean);
	}
	
	@RequestMapping(value = "pictureform")
	public String pictureform(HttpServletRequest request, HttpServletResponse response, Model model, @ModelAttribute PictureVo vo)
			throws Exception {
		if (vo.getPicture().getPictureId() == null) {
			model.addAttribute("pictureBean", vo.getPicture());
			
			if(null != vo.getPicture().getModuleId()){
				RfPictureModule module = pictureModuleService.get(vo.getPicture().getModuleId());
				model.addAttribute("moduleCode", module.getModuleCode());
			}
		}else{
			RfPicture picture = pictureService.get(vo.getPicture().getPictureId());
			model.addAttribute("pictureBean", picture);
			
			if(StringUtils.isNotBlank(picture.getLinkParametersJson())){
				String linkJson = picture.getLinkParametersJson();
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
			
			if(null != picture.getModuleId()){
				RfPictureModule module = pictureModuleService.get(picture.getModuleId());
				model.addAttribute("moduleCode", module.getModuleCode());
			}
		}
		
		model.addAttribute("links", PictureLinkTypeUtils.DICT_MAP);
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "picturesave")
	public void picturesave(HttpServletRequest request, HttpServletResponse response, Model model,@ModelAttribute PictureVo vo,
			String linkType,String apkId,String apkName,String webLink) throws Exception {
		if(vo.getPicture().getPlayTime() <= 0){
			throw new BusinessException("播放时间不能小于或者等于0！");
		}
		vo.getPicture().setStartTime(DateUtils.parseDate(vo.getPicture().getBeginTimeStr()));
		vo.getPicture().setEndTime(DateUtils.parseDate(vo.getPicture().getEndTimeStr()));
		
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
			vo.getPicture().setLinkParametersJson(linkParametersJson);
		}
		
		if(vo.getPicture().getPictureId() == null){
			pictureService.savePicture(request, vo.getPicture());
		}else{
			pictureService.updatePicture(request, vo.getPicture());
		}
	}

	@RequestMapping(value = "picturestart")
	public void picturestart(HttpServletRequest request, HttpServletResponse response, Model model, RfPicture bean)
			throws Exception {
		pictureService.startPicture(request, bean);
	}

	@RequestMapping(value = "picturestop")
	public void picturestop(HttpServletRequest request, HttpServletResponse response, Model model, RfPicture bean)
			throws Exception {
		pictureService.stopPicture(request, bean);
	}

	@RequestMapping(value = "picturedelete")
	public void picturedelete(HttpServletRequest request, HttpServletResponse response, Model model, RfPicture bean)
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
    public  JSONObject  uploadTwoImageRequest( 
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
    public  JSONObject  uploadThreeImageRequest( 
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
    	
    	log.info("版本下载文件链接地址:"+imageLinkUrl);
    	log.info("是否存在上传文件，"+file.isEmpty() + "" + file.getSize());
    	response.setContentType("text/html");
        Map<String, String> fileMap = null;
        if (null != file) {
        	
            fileMap = fileUtil.saveImage(file,imageUrl,imageLinkUrl);
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
}
