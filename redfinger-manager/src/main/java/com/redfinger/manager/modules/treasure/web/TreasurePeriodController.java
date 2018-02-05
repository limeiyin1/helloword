package com.redfinger.manager.modules.treasure.web;

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
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfActivationCodeType;
import com.redfinger.manager.common.domain.RfTreasure;
import com.redfinger.manager.common.domain.RfTreasureLottery;
import com.redfinger.manager.common.domain.RfTreasurePeriod;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.FileUtils;
import com.redfinger.manager.modules.activation.service.ActivationCodeTypeService;
import com.redfinger.manager.modules.treasure.service.TreasureLotteryService;
import com.redfinger.manager.modules.treasure.service.TreasurePeriodService;
import com.redfinger.manager.modules.treasure.service.TreasureService;

@Controller
@RequestMapping(value="/treasure/periodManage")
public class TreasurePeriodController extends BaseController{
	
	@Autowired
	private TreasurePeriodService service;
	@Autowired
	private TreasureService treasureService;
	@Autowired
	private ActivationCodeTypeService codeTypeService;
	@Autowired
	private FileUtils fileUtil;
	@Autowired
	private FilePathUtils filePathUtils;
	@Autowired
	private TreasureLotteryService lotteryService;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		List<RfTreasure> treasures = treasureService.initQuery().findDelTrue();
		model.addAttribute("treasures", treasures);
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfTreasurePeriod> list(HttpServletRequest request,
			HttpServletResponse response, Model model, RfTreasure bean)
			throws Exception {
		List<RfTreasurePeriod> list = service.initQuery(bean)
				.andEqualTo("treasureId", bean.getTreasureId())
				.andLike("treasureName", bean.getTreasureName())
				.addOrderClause("periodId", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		if(null != list && list.size() > 0){
			for (RfTreasurePeriod rfTreasurePeriod : list) {
				if(null != rfTreasurePeriod.getActivationCodeType()){
					rfTreasurePeriod.getMap().put("codeType", codeTypeService.get(rfTreasurePeriod.getActivationCodeType()).getActivationTypeName());
				}
				
				if(null != rfTreasurePeriod.getTreasureId()){
					rfTreasurePeriod.getMap().put("treasureName", treasureService.get(rfTreasurePeriod.getTreasureId()).getTreasureName());
				}
				
				if(null != rfTreasurePeriod.getTreasureId() && null != rfTreasurePeriod.getPeriodId()){
				    RfTreasureLottery lottery = lotteryService.selectTreasureLottery(rfTreasurePeriod.getTreasureId(), rfTreasurePeriod.getPeriodId());
				    if(null != lottery){
				    	rfTreasurePeriod.getMap().put("lotteryNumber", lottery.getLotteryNumber());
				    }
				}
			}
		}
		PageInfo<RfTreasurePeriod> pageInfo = new PageInfo<RfTreasurePeriod>(list);
		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request,
			HttpServletResponse response, Model model, RfTreasurePeriod bean)
			throws Exception {
		if (bean.getPeriodId() == null) {

		} else {
			bean = service.get(bean.getPeriodId());
			if(null != bean.getTreasureId()){
				RfTreasure treasure = treasureService.get(bean.getTreasureId());
				if(null != treasure){
					model.addAttribute("treasureName", treasure.getTreasureName());
				}
			}
			model.addAttribute("bean", bean);
		}
		
		List<RfTreasure> treasures = treasureService.initQuery().findStopTrue();
		model.addAttribute("treasures", treasures);
		
		List<RfActivationCodeType> codeTypes = codeTypeService.initQuery().findStopTrue();
		model.addAttribute("codeTypes", codeTypes);
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response,
			Model model, RfTreasurePeriod bean,String beginTimeStr,String endTimeStr) throws Exception {
		if(null == bean.getTreasureId()){
			throw new BusinessException("总夺宝期数不能为空");
		}
		
		if(StringUtils.isBlank(beginTimeStr)){
			throw new BusinessException("请选择开始时间");
		}
		if(StringUtils.isBlank(endTimeStr)){
			throw new BusinessException("请选择结束时间");
		}
		if(StringUtils.isBlank(bean.getTreasureImg())){
			throw new BusinessException("夺宝图片不能为空");
		}
		
		bean.setPeriodStatus(ConstantsDb.periodStatusWaiting());
		bean.setStartTime(DateUtils.parseDate(beginTimeStr));
		bean.setEndTime(DateUtils.parseDate(endTimeStr));
		
		if(checkPeriodCount(bean) > 0){
			throw new BusinessException("保存失败,时间范围内存在总期夺宝相同的夺宝");
		}
		
		service.saveTreasurePeriod(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request,String beginTimeStr,String endTimeStr,
			HttpServletResponse response, Model model, RfTreasurePeriod bean)
			throws Exception {
		if(null == bean.getTreasureId()){
			throw new BusinessException("总夺宝不能为空");
		}
		
		if(StringUtils.isBlank(beginTimeStr)){
			throw new BusinessException("请选择开始时间");
		}
		if(StringUtils.isBlank(endTimeStr)){
			throw new BusinessException("请选择结束时间");
		}
		if(StringUtils.isBlank(bean.getTreasureImg())){
			throw new BusinessException("夺宝图片不能为空");
		}
		
		
		bean.setStartTime(DateUtils.parseDate(beginTimeStr));
		bean.setEndTime(DateUtils.parseDate(endTimeStr));
		
		if(checkPeriodCount(bean) > 0){
			throw new BusinessException("保存失败,时间范围内存在总期夺宝相同的夺宝");
		}
		service.updateTreasurePeriod(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response,
			Model model, RfTreasurePeriod bean) throws Exception {
		
		if(StringUtils.isNotBlank(bean.getIds())){
			String ids[] = bean.getIds().split(",");
			for (String str : ids) {
				if(StringUtils.isNotBlank(str)){
					RfTreasurePeriod period = service.get(Integer.parseInt(str));
					if(checkPeriodCount(period) > 0){
						throw new BusinessException("保存失败,时间范围内存在总期夺宝相同的夺宝");
					}
				}
			}
		}
		
		
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response,
			Model model, RfTreasurePeriod bean) throws Exception {
		
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request,
			HttpServletResponse response, Model model, RfTreasurePeriod bean)
			throws Exception {
		
		service.delete(request, bean);
	}
	
	/**
     * 上传文件
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "uploadImageRequest")
    @ResponseBody
    public  JSONObject  uploadOneImageRequest( 
    		@RequestParam(value = "fileUpdate", required = false) MultipartFile file,
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
        	if(file.getSize()>614400){//图片不能大于600kb
        		 fileMap = new HashMap<String, String>();
                 fileMap.put("flag", "false");
                 fileMap.put("msg", "文件大小不能超过600kb");
        	}else{
        		fileMap = fileUtil.saveImage(file,imageUrl,imageLinkUrl);
                fileMap.put("flag", "true");
                log.info("filePath: " + fileMap.get("filePath"));
        	}
        }else {
            fileMap = new HashMap<String, String>();
            fileMap.put("flag", "false");
            fileMap.put("msg", "文件不能为空");

        }
        JSONObject jsonObject = asyncReturnResult(response,JSONObject.fromObject(fileMap));
        log.info(jsonObject.toString());
        return jsonObject;
    }
    
    
    /**
	 * 判断分期夺宝在总期中的时间是否会有重复的
	 * @param activity
	 * @return
	 * @throws Exception
	 */
	private int checkPeriodCount(RfTreasurePeriod period) throws Exception {
		List<RfTreasurePeriod> periodList = service.initQuery().andEqualTo("treasureId", period.getTreasureId())
				.andNotEqualTo("periodId", period.getPeriodId())
				.findStopTrue();
		
		int count = 0;
		long startTime = period.getStartTime().getTime(), endTime = period.getEndTime().getTime();
		for (RfTreasurePeriod per : periodList) {
			if((startTime >= per.getStartTime().getTime() && startTime <= per.getEndTime().getTime())
				|| (endTime >= per.getStartTime().getTime() && endTime <= per.getEndTime().getTime())
				||  (startTime <= per.getStartTime().getTime() && endTime >= per.getEndTime().getTime())){
				count++;
			}
			
		}
		return count;
	}
}
