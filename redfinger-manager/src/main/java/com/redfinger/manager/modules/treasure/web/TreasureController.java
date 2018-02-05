package com.redfinger.manager.modules.treasure.web;

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

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.base.FilePathUtils;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfActivationCode;
import com.redfinger.manager.common.domain.RfActivationCodeType;
import com.redfinger.manager.common.domain.RfTreasure;
import com.redfinger.manager.common.domain.RfTreasurePeriod;
import com.redfinger.manager.common.domain.SysConfig;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.FileUtils;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.activation.service.ActivationCodeService;
import com.redfinger.manager.modules.activation.service.ActivationCodeTypeService;
import com.redfinger.manager.modules.base.service.ConfigService;
import com.redfinger.manager.modules.treasure.service.TreasurePeriodService;
import com.redfinger.manager.modules.treasure.service.TreasureService;

@Controller
@RequestMapping(value="/treasure/manage")
public class TreasureController extends BaseController{
	
	@Autowired
	private TreasureService service;
	@Autowired
	private ActivationCodeTypeService codeTypeService;
	@Autowired
	FileUtils fileUtil;
	@Autowired
	ConfigService sysConfigService;
	@Autowired
	private FilePathUtils filePathUtils;
	@Autowired
	private ActivationCodeService codeService;
	@Autowired
	private TreasurePeriodService periodService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfTreasure> list(HttpServletRequest request,
			HttpServletResponse response, Model model, RfTreasure bean)
			throws Exception {
		List<RfTreasure> list = service.initQuery(bean)
				.andLike("treasureName", bean.getTreasureName())
				.addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		if(null != list && list.size() > 0){
			for (RfTreasure rfTreasure : list) {
				if(null != rfTreasure.getActivationCodeType()){
					rfTreasure.getMap().put("codeType", codeTypeService.get(rfTreasure.getActivationCodeType()).getActivationTypeName());
				}
			}
		}
		PageInfo<RfTreasure> pageInfo = new PageInfo<RfTreasure>(list);
		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request,
			HttpServletResponse response, Model model, RfTreasure bean)
			throws Exception {
		if (bean.getTreasureId() == null) {

		} else {
			bean = service.get(bean.getTreasureId());
			model.addAttribute("bean", bean);

		}

		List<RfActivationCodeType> codeTypes = codeTypeService.initQuery().findStopTrue();
		model.addAttribute("codeTypes", codeTypes);
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response,
			Model model, RfTreasure bean,String beginTimeStr,String endTimeStr) throws Exception {
		if(StringUtils.isBlank(beginTimeStr)){
			throw new BusinessException("请选择开始时间");
		}
		if(StringUtils.isBlank(endTimeStr)){
			throw new BusinessException("请选择结束时间");
		}
		if(StringUtils.isBlank(bean.getTreasureImg())){
			throw new BusinessException("夺宝图片不能为空");
		}
		
		List<RfTreasure> list = service.initQuery().andEqualTo("numberPrefix", bean.getNumberPrefix()).findAll();
		if(null != list && list.size() > 0){
			throw new BusinessException("抽奖前缀已经存在");
		}
		
		List<RfActivationCode> codes = codeService.initQuery().andEqualTo("typeId", bean.getActivationCodeType())
		.andEqualTo("bindStatus", YesOrNoStatus.NO).andEqualTo("activationStatus", YesOrNoStatus.NO).findStopTrue();
		if(null == codes || codes.size() < 1){
			throw new BusinessException("您选择的激活码类型下没有激活码");
		}
		
		bean.setStartTime(DateUtils.parseDate(beginTimeStr));
		bean.setEndTime(DateUtils.parseDate(endTimeStr));
		bean.setCurrentPeriod(0);
		
		
		service.save(request, bean);
	}
	
	@RequestMapping(value = "startFirst")
	public void save(HttpServletRequest request, HttpServletResponse response,
			Model model, RfTreasure bean,Integer id) throws Exception {
		if(null == id){
			throw new BusinessException("请选择夺宝总期");
		}
		
		RfTreasure treasure = service.get(id);
		if(null != treasure){
			if(StringUtils.equals(treasure.getEnableStatus(), YesOrNoStatus.NO)){
				throw new BusinessException("此夺宝总期已经禁用,不能开启");
			}
			
			if(StringUtils.equals(treasure.getStatus(), YesOrNoStatus.NO)){
				throw new BusinessException("此夺宝总期已经删除,不能开启");
			}
			
			int count = periodService.selectCountByTreasureId(treasure.getTreasureId());
			if(count > 0){
				throw new BusinessException("此夺宝总期已经开启");
			}
			
			List<RfActivationCode> codes = codeService.initQuery().andEqualTo("typeId", treasure.getActivationCodeType())
			.andEqualTo("bindStatus", YesOrNoStatus.NO).andEqualTo("activationStatus", YesOrNoStatus.NO).findStopTrue();
			if(null == codes || codes.size() < 1){
				throw new BusinessException("此夺宝没有激活码，不能开启");
			}
			
			SysConfig config = sysConfigService.selectByConfingCode("config_treasure_period_expired_time");
			Integer hours = (null == config) ? 72 : Integer.parseInt(config.getConfigValue());
			
			RfTreasurePeriod period = new RfTreasurePeriod();
			period.setTreasureId(treasure.getTreasureId());
			period.setTreasureName("第1期");
			period.setTreasureImg(treasure.getTreasureImg());
			period.setCurrentPeriod(1);
			period.setNumberPrefix(treasure.getNumberPrefix());
			period.setNumberBase(treasure.getNumberBase());
			period.setTreasureFrequency(treasure.getTreasureFrequency());
			period.setRemainderFrequency(treasure.getTreasureFrequency());
			period.setTreasureMoney(treasure.getTreasureMoney());
			period.setActivationCodeType(treasure.getActivationCodeType());
			period.setRedCoin(treasure.getRedCoin());
			period.setStartTime(new Date());
			period.setEndTime(DateUtils.parseDate(DateUtils.getAfterDate(new Date(), hours)));
			period.setPeriodStatus(ConstantsDb.periodStatusWaiting());
			period.setStatus(YesOrNoStatus.YES);
			period.setEnableStatus(YesOrNoStatus.YES);
			period.setCreater(SessionUtils.getCurrentUserId(request));
			period.setCreateTime(new Date());
			periodService.savePeriod(period,treasure);
		}
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request,String beginTimeStr,String endTimeStr,
			HttpServletResponse response, Model model, RfTreasure bean)
			throws Exception {
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
		
		List<RfTreasure> list = service.initQuery().andEqualTo("numberPrefix", bean.getNumberPrefix())
				.andNotEqualTo("treasureId", bean.getTreasureId()).findAll();
		
		if(null != list && list.size() > 0){
			throw new BusinessException("抽奖前缀已经存在");
		}
		
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response,
			Model model, RfTreasure bean) throws Exception {
		
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response,
			Model model, RfTreasure bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request,
			HttpServletResponse response, Model model, RfTreasure bean)
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
	 * 判断夺宝总期的时间是否有重复的
	 * @param activity
	 * @return
	 * @throws Exception
	 */
	private int checkTreasureCount(RfTreasure treasure) throws Exception {
		List<RfTreasure> sureList = service.initQuery().andNotEqualTo("treasureId", treasure.getTreasureId()).findStopTrue();
		
		int count = 0;
		long startTime = treasure.getStartTime().getTime(), endTime = treasure.getEndTime().getTime();
		for (RfTreasure sure : sureList) {
			if((startTime >= sure.getStartTime().getTime() && startTime <= sure.getEndTime().getTime())
				|| (endTime >= sure.getStartTime().getTime() && endTime <= sure.getEndTime().getTime())
				||  (startTime <= sure.getStartTime().getTime() && endTime >= sure.getEndTime().getTime())){
				count++;
			}
			
		}
		return count;
	}
}
