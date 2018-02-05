package com.redfinger.manager.modules.information.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
import org.apache.commons.fileupload.servlet.*;

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.base.FilePathUtils;
import com.redfinger.manager.common.domain.RfInformation;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.FileUtils;
import com.redfinger.manager.common.utils.InformationTypeUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.information.service.InformationService;

@Controller
@RequestMapping(value = "/information/information")
public class InformationController  extends BaseController{
	
	@Autowired
	FileUtils fileUtil;
	@Autowired
	private FilePathUtils filePathUtils;
	@Autowired
	private InformationService service;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("maps", InformationTypeUtils.DICT_MAP);
		model.addAttribute("gcUrl", filePathUtils.getGcUrl());
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfInformation> list(HttpServletRequest request, HttpServletResponse response, Model model, 
			RfInformation bean) throws Exception {
		
		List<RfInformation> list= service.initQuery(bean)
				.andEqualTo("clientType", bean.getClientType())
				.andEqualTo("enableStatus", bean.getEnableStatus())  // 根据启用状态查询
				.andLike("informationType", bean.getInformationType())
				.andLike("informationTitle", bean.getInformationTitle())
				.addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageAll(bean.getPage(), bean.getRows());
		
		PageInfo<RfInformation> pageInfo = new PageInfo<RfInformation>(list);

		return pageInfo;
	}
	
	
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfInformation bean)
			throws Exception {
		
		if(null != bean.getId()){
			bean = service.get(bean.getId());
		}
		model.addAttribute("bean", bean);
		model.addAttribute("maps", YesOrNoStatus.DICT_MAP);
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "getClientType")
	public Map<String,Object> getClientType(HttpServletRequest request, HttpServletResponse response, Model model, 
			RfInformation bean) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		if(null != bean.getId()){
			bean = service.get(bean.getId());
		}
		map.put("clientType", bean.getClientType());
		return map;
	}
	
	/*@RequestMapping(value = "detail")
	public void detail(HttpServletRequest request, HttpServletResponse response, Model model, 
			RfInformation bean) throws Exception {
		if(null != bean.getId()){
			bean = service.get(bean.getId());
		}
		model.addAttribute("bean", bean);
		model.addAttribute("createTime", DateUtils.getDateString(bean.getCreateTime(), "yyyy-MM-dd HH:mm"));
		
	}*/
	
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, 
			RfInformation bean) throws Exception {
		service.save(request, bean);
	}
	
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, 
			RfInformation bean) throws Exception {
		service.update(request, bean);
	}
	
	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfInformation bean)
			throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfInformation bean)
			throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfInformation bean)
			throws Exception {
		service.remove(request, bean);
	}
	
	
	/**
     * 上传文件
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "uploadBigImageRequest")
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
    @RequestMapping(value = "uploadSmallImageRequest")
    @ResponseBody
    public  JSONObject  uploadSevenRequest( 
    		@RequestParam(value = "fileUpdate2", required = false) MultipartFile file,
    		HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	
    	return this.uploadImage(file, request, response, model);
    }
	
	@RequestMapping(value = "uploadJson")
    @ResponseBody
    public  JSONObject  uploadJson(@RequestParam(value = "imgFile", required = false) MultipartFile file,
    		HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		//文件保存目录路径
		String savePath = filePathUtils.getImageUrl();

		//定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

		//最大文件大小
		long maxSize = 921600;

		response.setContentType("text/html; charset=UTF-8");

		if(!ServletFileUpload.isMultipartContent(request)){
			return getError("请选择文件。");
		}
		//检查目录
		File uploadDir = new File(savePath);
		if(!uploadDir.isDirectory()){
			return getError("上传目录不存在。");
		}
		//检查目录写权限
		if(!uploadDir.canWrite()){
			return getError("上传目录没有写权限。");
		}

		String dirName = request.getParameter("dir");
		if (dirName == null) {
			dirName = "image";
		}
		if(!extMap.containsKey(dirName)){
			return getError("目录名不正确。");
		}
		//创建文件夹
		savePath += dirName + "/";
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		savePath += ymd + "/";
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}

		if(file.getSize()>maxSize){
			return getError("上传文件大小超过900KB。");
		}
		/*String fileName = file.getName();
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
			return getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。");
		}*/
		
		Map<String, String> fileMap = null;
		JSONObject obj = new JSONObject();
        if (null != file) {
        	fileMap = fileUtil.saveImage(file,filePathUtils.getImageUrl(),filePathUtils.getImageLinkUrl());
            obj.put("error", 0);
    		obj.put("url", fileMap.get("filePath"));
            log.info("filePath: " + fileMap.get("filePath"));
        }else {
        	return getError("上传失败");
        }
        return obj;
	}
	
	
	@RequestMapping(value = "fileManagerJson")
    @ResponseBody
    public  void  fileManagerJson(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		//根目录路径，可以指定绝对路径，比如 /var/www/attached/
		String rootPath = filePathUtils.getImageUrl();
		//根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
		String rootUrl  = filePathUtils.getImageLinkUrl();
		//图片扩展名
		String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};

		String dirName = request.getParameter("dir");
		if (dirName != null) {
			if(!Arrays.<String>asList(new String[]{"image", "flash", "media", "file"}).contains(dirName)){
				throw new BusinessException("Invalid Directory name.");
			}
			rootPath += dirName + "/";
			rootUrl += dirName + "/";
			File saveDirFile = new File(rootPath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
		}
		//根据path参数，设置各路径和URL
		String path = request.getParameter("path") != null ? request.getParameter("path") : "";
		String currentPath = rootPath + path;
		String currentUrl = rootUrl + path;
		String currentDirPath = path;
		String moveupDirPath = "";
		if (!"".equals(path)) {
			String str = currentDirPath.substring(0, currentDirPath.length() - 1);
			moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
		}

		//排序形式，name or size or type
		String order = request.getParameter("order") != null ? request.getParameter("order").toLowerCase() : "name";

		//不允许使用..移动到上一级目录
		if (path.indexOf("..") >= 0) {
			throw new BusinessException("不允许使用..移动到上一级目录.");
		}
		//最后一个字符不是/
		if (!"".equals(path) && !path.endsWith("/")) {
			throw new BusinessException("最后一个字符不是/.");
		}
		//目录不存在或不是目录
		File currentPathFile = new File(currentPath);
		if(!currentPathFile.isDirectory()){
			throw new BusinessException("目录不存在或不是目录.");
		}

		//遍历目录取的文件信息
		List<Hashtable> fileList = new ArrayList<Hashtable>();
		if(currentPathFile.listFiles() != null) {
			for (File file : currentPathFile.listFiles()) {
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				String fileName = file.getName();
				if(file.isDirectory()) {
					hash.put("is_dir", true);
					hash.put("has_file", (file.listFiles() != null));
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
				} else if(file.isFile()){
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", file.length());
					hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
				fileList.add(hash);
			}
		}

		if ("size".equals(order)) {
			Collections.sort(fileList, new SizeComparator());
		} else if ("type".equals(order)) {
			Collections.sort(fileList, new TypeComparator());
		} else {
			Collections.sort(fileList, new NameComparator());
		}
		JSONObject result = new JSONObject();
		result.put("moveup_dir_path", moveupDirPath);
		result.put("current_dir_path", currentDirPath);
		result.put("current_url", currentUrl);
		result.put("total_count", fileList.size());
		result.put("file_list", fileList);

		response.setContentType("application/json; charset=UTF-8");
		log.info(result.toString());
	}
	
	private JSONObject getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj;
	}
	
	private class NameComparator implements Comparator {
		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable)a;
			Hashtable hashB = (Hashtable)b;
			if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
				return 1;
			} else {
				return ((String)hashA.get("filename")).compareTo((String)hashB.get("filename"));
			}
		}
	}
	private class SizeComparator implements Comparator {
		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable)a;
			Hashtable hashB = (Hashtable)b;
			if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
				return 1;
			} else {
				if (((Long)hashA.get("filesize")) > ((Long)hashB.get("filesize"))) {
					return 1;
				} else if (((Long)hashA.get("filesize")) < ((Long)hashB.get("filesize"))) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}
	
	private class TypeComparator implements Comparator {
		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable)a;
			Hashtable hashB = (Hashtable)b;
			if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
				return 1;
			} else {
				return ((String)hashA.get("filetype")).compareTo((String)hashB.get("filetype"));
			}
		}
	}
	
	private JSONObject uploadImage(MultipartFile file,
    		HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
    	String  imageUrl = filePathUtils.getImageUrl();
    	String imageLinkUrl = filePathUtils.getImageLinkUrl();
    	
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


