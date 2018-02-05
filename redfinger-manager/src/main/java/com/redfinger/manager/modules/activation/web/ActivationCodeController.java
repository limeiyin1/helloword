package com.redfinger.manager.modules.activation.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.RfActivationCode;
import com.redfinger.manager.common.domain.RfActivationCodeType;
import com.redfinger.manager.common.domain.RfOrder;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.DictHelper;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.ExcelUtils;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.common.utils.NumberUtils;
import com.redfinger.manager.common.utils.PadTypeUtils;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.activation.service.ActivationCodeService;
import com.redfinger.manager.modules.activation.service.ActivationCodeTypeService;

@Controller
@RequestMapping(value="/activation/code")
public class ActivationCodeController extends BaseController {
	@Autowired
	private ActivationCodeService service;
	@Autowired
	private ActivationCodeTypeService activationCodeTypeService;
	
	
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		List<RfActivationCodeType> typeList = activationCodeTypeService.initQuery().findStopTrue();
		model.addAttribute("typeList", typeList);
		model.addAttribute("map", YesOrNoStatus.DICT_MAP);
		//向请求域中添加设备类型列表
		model.addAttribute("padType", PadTypeUtils.DICT_MAP);
		return this.toPage(request, response, model);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfActivationCode> list(HttpServletRequest request, HttpServletResponse response, Model model, RfActivationCode bean) throws Exception {
		List<RfActivationCode> list = service.initQuery(bean)
				.andEqualTo("padType", bean.getPadType())
				.andEqualTo("typeId", bean.getTypeId())
				.andEqualTo("activationStatus", bean.getActivationStatus())
				.andEqualTo("bindStatus", bean.getBindStatus()) // 根据绑定状态查询
				.andEqualTo("enableStatus", bean.getEnableStatus()) // 根据启用状态查询
				.andEqualTo("padType", bean.getPadType()) // 根据设备类型查询
				.andLike("activationCode", bean.getActivationCode())
				.addOrderClause("codeId", "desc")
				.pageDelTrue(bean.getPage(), bean.getRows());
		if(null != list && list.size()>0){
			for (RfActivationCode rfActivationCode : list) {
				if(null != rfActivationCode.getTypeId()){
					RfActivationCodeType rfActivationCodeType = activationCodeTypeService.get(rfActivationCode.getTypeId());
					rfActivationCode.getMap().put("activationTypeName", rfActivationCodeType.getActivationTypeName());
					rfActivationCode.getMap().put("batchNumber", rfActivationCodeType.getActivationTypeCode());
				}
				if(StringUtils.isNotBlank(rfActivationCode.getPadType())){
					rfActivationCode.getMap().put("padTypeName", PadTypeUtils.DICT_MAP.get(rfActivationCode.getPadType()));
				}
				if(StringUtils.isNotBlank(rfActivationCode.getActivationStatus())){
					rfActivationCode.getMap().put("activationStatusName", YesOrNoStatus.DICT_MAP.get(rfActivationCode.getActivationStatus()));
				}
				
				if(null != rfActivationCode.getPadTime()){
					rfActivationCode.getMap().put("padUsetime",NumberUtils.dividedTime(rfActivationCode.getPadTime()));
				}
			}
		}
		PageInfo<RfActivationCode> pageInfo = new PageInfo<RfActivationCode>(list);
		return pageInfo;
	}
	
	
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfActivationCode bean)
			throws Exception {
		if (bean.getCodeId() != null) {
			bean = service.get(bean.getCodeId());
			if(null != bean.getPadTime()){
				bean.setPadTime(NumberUtils.dividedTime(bean.getPadTime()));
			}
			model.addAttribute("bean", bean);
		}
		model.addAttribute("typeList", activationCodeTypeService.initQuery().findStopTrue());
		model.addAttribute("map", YesOrNoStatus.DICT_MAP);
		model.addAttribute("padType", PadTypeUtils.DICT_MAP);
		System.out.println("hello, padType...");
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, 
			String begin, String end,RfActivationCode bean) throws Exception {
		Date beginTime = null;
		Date endTime = null;
		if (StringUtils.isNotBlank(begin)) {
			beginTime = DateUtils.parseDate(begin);
		}
		if (StringUtils.isNotBlank(end)) {
			endTime = DateUtils.parseDate(end);
		}
		if(null != bean.getPadTime()){
			bean.setPadTime(NumberUtils.multiplyTime(bean.getPadTime()));
		}
		bean.setBindStatus(YesOrNoStatus.NO);
		bean.setStartTime(beginTime);
		bean.setEndTime(endTime);
		service.save(request, bean);
	}
	
	
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, 
			String begin, String end, RfActivationCode bean,String userRegisterTimeStr)
			throws Exception {
		Date beginTime = null;
		Date endTime = null;
		Date userRegisterTime = null;
		if (StringUtils.isNotBlank(begin)) {
			beginTime = DateUtils.parseDate(begin);
		}
		if (StringUtils.isNotBlank(end)) {
			endTime = DateUtils.parseDate(end);
		}
		if(null != bean.getPadTime()){
			bean.setPadTime(NumberUtils.multiplyTime(bean.getPadTime()));
		}
		if (StringUtils.isNotBlank(userRegisterTimeStr)) {
			userRegisterTime = DateUtils.parseDate(userRegisterTimeStr);
		}
		bean.setUserRegisterTime(userRegisterTime);
		bean.setStartTime(beginTime);
		bean.setEndTime(endTime);
		service.updateCode(request, bean);
	}
	
	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfActivationCode bean)
			throws Exception {
		service.startCode(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfActivationCode bean)
			throws Exception {
		service.stopCode(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfActivationCode bean)
			throws Exception {
		service.removeCode(request, bean);
	}
	
	@RequestMapping(value = "addActivation")
	public String addActivation(HttpServletRequest request, HttpServletResponse response, Model model, RfActivationCode bean){
		List<RfActivationCodeType> typeList = activationCodeTypeService.initQuery().findStopTrue();
		model.addAttribute("typeList", typeList);
		model.addAttribute("map", YesOrNoStatus.DICT_MAP);
		model.addAttribute("padType", PadTypeUtils.DICT_MAP);
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "saveActivation")
	public void saveActivation(HttpServletRequest request, HttpServletResponse response, Model model, RfActivationCode bean,
			String begin, String end, int activationNumber, String activationCodePrefix,String userRegisterTimeStr) throws Exception{
		log.info("激活码前缀："+activationCodePrefix +",激活码个数："+activationNumber);
		RfActivationCodeType activationCodeType = null;
		if(null != bean.getTypeId()){
			activationCodeType = activationCodeTypeService.get(bean.getTypeId());
		}else{
			throw new BusinessException("激活码类型不能为空！");
		}
		
		if(activationNumber < 1){
			throw new BusinessException("激活码个数不能小于1！");
		}
		
		List<RfActivationCode> codes = service.initQuery().andLikeSuffix("activationCode", activationCodePrefix).findStopTrue();
		if(null != codes && codes.size()>0){
			throw new BusinessException("已经存在前缀为"+activationCodePrefix+"的激活码！");
		}
		List<RfActivationCode> list = new ArrayList<RfActivationCode>();
		Date beginTime = null;
		Date endTime = null;
		Date userRegisterTime = null;
		if (StringUtils.isNotBlank(begin)) {
			beginTime = DateUtils.parseDate(begin);
		}
		if (StringUtils.isNotBlank(end)) {
			endTime = DateUtils.parseDate(end);
		}
		if (StringUtils.isNotBlank(userRegisterTimeStr)) {
			userRegisterTime = DateUtils.parseDate(userRegisterTimeStr);
		}
		for(int i=0;i<activationNumber;i++){
			RfActivationCode code = new RfActivationCode();
			code.setActivationCode(activationCodePrefix+generateStr());
			code.setActivationStatus(YesOrNoStatus.NO);
			code.setTypeId(bean.getTypeId());
			code.setPadType(activationCodeType.getPadType());
			if(null != activationCodeType.getPadTime()){
				code.setPadTime(activationCodeType.getPadTime());
			}
			code.setBindStatus(YesOrNoStatus.NO);
			code.setStartTime(beginTime);
			code.setEndTime(endTime);
			code.setRemark(bean.getRemark());
			code.setStatus(YesOrNoStatus.YES);
			code.setEnableStatus(YesOrNoStatus.YES);
			code.setCreater(SessionUtils.getCurrentUserId(request));
			code.setCreateTime(new Date());
			code.setUserRegisterTime(userRegisterTime);
			list.add(code);
		}
		if(null != list && list.size() >0 ){
			service.saveCodeList(request,list,activationCodePrefix,activationNumber);
		}
	}
	 // 导出
	 @RequestMapping(value="export")
	 public String export(HttpServletRequest request,HttpServletResponse response,Model model,RfActivationCode bean,String exportHead, String exportField, String exportName,String userPhone,Integer externalUserId)throws Exception{
			exportField=exportField.replace("checkboxValue", "padId");
			exportHead=StringUtils.removeEnd(exportHead, ",");
			exportField=StringUtils.removeEnd(exportField, ",");
			response.setContentType("application/binary;charset=UTF-8");
			ServletOutputStream outputStream = response.getOutputStream();
			response.setHeader("Content-disposition", "attachment; filename=" + ExcelUtils.getFileName(request, exportName)+".xls");
			Workbook workBook = new SXSSFWorkbook();
			SXSSFSheet sheet = (SXSSFSheet) workBook.createSheet("Sheet1");
			CellStyle headStyle = ExcelUtils.getHeadStyle(workBook);
			CellStyle bodyStyle = ExcelUtils.getBodyStyle(workBook);
			// 构建表头
			ExcelUtils.insertHead(exportHead.split(","), sheet, headStyle);
			// 构建表体
			boolean keep=true;
			int page=1;
			while(keep){
				bean.setPage(page);
				bean.setRows(1000);
				PageInfo<RfActivationCode> pageInfo=this.list(request, response, model, bean);
				List<RfActivationCode> list=pageInfo.getList();
				if(!Collections3.isEmpty(list)){
					for(RfActivationCode activationCode:list){
						activationCode.setStatus(DictHelper.getLabel("global.status", activationCode.getStatus()));
						activationCode.setEnableStatus(DictHelper.getLabel("global.enable_status", activationCode.getEnableStatus()));
						activationCode.setBindStatus(DictHelper.getLabel("rf_activation.activation_bind", activationCode.getBindStatus()));
						
					}
					ExcelUtils.insertPageBody(JsonUtils.ObjectToString(list),sheet,bodyStyle,exportField.split(","),pageInfo.getStartRow());
					if(pageInfo.isHasNextPage()){
						page++;
						continue;
					}
				}
				keep=false;
			}
			try {
				workBook.write(outputStream);
				outputStream.flush();
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
	    }  
}
