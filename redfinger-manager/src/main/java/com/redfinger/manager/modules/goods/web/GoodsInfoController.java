package com.redfinger.manager.modules.goods.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.redfinger.manager.common.domain.RfGoodsInfo;
import com.redfinger.manager.common.domain.RfGoodsType;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.FileUtils;
import com.redfinger.manager.modules.goods.service.GoodsInfoService;
import com.redfinger.manager.modules.goods.service.GoodsTypeService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/goods/infoManage")
public class GoodsInfoController extends BaseController {
	@Autowired
	GoodsInfoService service;
	@Autowired
	GoodsTypeService typeService;
	@Autowired
	FileUtils fileUtil;
	@Autowired
	private FilePathUtils filePathUtils;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		List<RfGoodsType> goodsTypes = typeService.initQuery().findStopTrue();
		model.addAttribute("list", goodsTypes);
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfGoodsInfo> list(HttpServletRequest request,
			HttpServletResponse response, Model model, RfGoodsInfo bean)
			throws Exception {
		List<RfGoodsInfo> list = service.initQuery(bean)
				.andLike("infoName", bean.getInfoName())
				.andEqualTo("goodsTypeId", bean.getGoodsTypeId())
				.addOrderClause("reorder", "asc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		if(null != list && list.size()>0){
			for (RfGoodsInfo rfGoodsInfo : list) {
				if(null != rfGoodsInfo.getGoodsTypeId()){
					rfGoodsInfo.getMap().put("typeName", typeService.get(rfGoodsInfo.getGoodsTypeId()).getTypeName());
				}
			}
		}
		
		PageInfo<RfGoodsInfo> pageInfo = new PageInfo<RfGoodsInfo>(list);
		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request,
			HttpServletResponse response, Model model, RfGoodsInfo bean)
			throws Exception {
		if (bean.getInfoId() == null) {

		} else {
			bean = service.get(bean.getInfoId());
			model.addAttribute("bean", bean);
		}
		List<RfGoodsType> goodsTypes = typeService.initQuery().findStopTrue();
		model.addAttribute("list", goodsTypes);
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response,
			Model model, RfGoodsInfo bean) throws Exception {
		if(0 == bean.getGoodsTypeId()){
			if(-1 == bean.getFreeUseLimit()){
				throw new BusinessException("请选择是否限时");//普通设备需选择是否想限时
			}
		}else {
			bean.setFreeUseLimit(null);
		}
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request,
			HttpServletResponse response, Model model, RfGoodsInfo bean)
			throws Exception {
		if(0 == bean.getGoodsTypeId()){
			if(-1 == bean.getFreeUseLimit()){
				throw new BusinessException("请选择是否限时");
			}
		}else{
			bean.setFreeUseLimit(null);
		}
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response,
			Model model, RfGoodsInfo bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response,
			Model model, RfGoodsInfo bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request,
			HttpServletResponse response, Model model, RfGoodsInfo bean)
			throws Exception {
		service.remove(request, bean);
	}
	
	/**
     * 上传文件
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "uploadIconImageRequest")
    @ResponseBody
    public  JSONObject  uploadOneImageRequest( 
    		@RequestParam(value = "fileUpdateIcon", required = false) MultipartFile file,
    		HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	
    	return this.uploadImage(file, request, response, model);
    }
    
    private JSONObject uploadImage(MultipartFile file,
    		HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
    	String  imageUrl = filePathUtils.getImageUrl()+"/mpic";
    	String imageLinkUrl = filePathUtils.getImageLinkUrl()+"/mpic";
    	
    	log.info("信息图片:"+imageLinkUrl);
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
