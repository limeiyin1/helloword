package com.redfinger.manager.modules.notice.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.constants.ConfigConstantsDb;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.PushUmengInfoLog;
import com.redfinger.manager.common.domain.RfControl;
import com.redfinger.manager.common.domain.RfIdc;
import com.redfinger.manager.common.domain.RfNotice;
import com.redfinger.manager.common.domain.RfPad;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.RfUserPad;
import com.redfinger.manager.common.domain.ViewPadUser;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.DictHelper;
import com.redfinger.manager.common.jsm.PushNoticeProducer;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.ExcelUtils;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.common.utils.NoticeTypeUtils;
import com.redfinger.manager.common.utils.PushStatus;
import com.redfinger.manager.common.utils.PushType;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.facility.service.ControlService;
import com.redfinger.manager.modules.facility.service.IdcService;
import com.redfinger.manager.modules.facility.service.PadService;
import com.redfinger.manager.modules.facility.service.UserPadService;
import com.redfinger.manager.modules.facility.service.ViewPadUserService;
import com.redfinger.manager.modules.member.service.UserDataService;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.notice.dto.UMengInfoDto;
import com.redfinger.manager.modules.notice.service.NoticeService;

@Controller
@RequestMapping(value = "/notice/userNoticePad")
public class UserNoticePadController extends BaseController {
	@Autowired
	UserService userService;
	@Autowired
	NoticeService noticeService;
	@Autowired
	PadService padService;
	@Autowired
	UserPadService userPadService;
	@Autowired
	IdcService idcService;
	@Autowired
	ControlService controlService;
	@Autowired
	ViewPadUserService viewPadUserService;
	@Autowired
	UserDataService userDataService;
	@Autowired
	PushNoticeProducer pushNoticeProducer;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		List<RfIdc> idcList = idcService.initQuery().findStopTrue();
		model.addAttribute("idcList", idcList);
		List<RfControl> controlList = controlService.initQuery().andEqualTo("controlType", ConstantsDb.rfControlControlTypeDevice()).findStopTrue();
		model.addAttribute("controlList", controlList);//控制节点
		return this.toPage(request, response, model);

	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<ViewPadUser> list(HttpServletRequest request, HttpServletResponse response, Model model,ViewPadUser viewPadUser, String padCode2,String padControlTime,String expireTime,String userEmail,String bindMobile, Integer externalUserId) throws Exception {
		/*// 受控时间
		Date beginControlTime = null;
		Date endControlTime = null;
		if (StringUtils.isNotEmpty(bean.getControlTimeStr())) {
			bean.setControlTime(DateUtils.parseDate(bean.getControlTimeStr(), "yyyy-MM-dd"));
			beginControlTime = bean.getControlTime();
			beginControlTime = DateUtils.getFirstDate(beginControlTime);
			endControlTime = DateUtils.addDays(beginControlTime, 1);
		}*/
		
		/** 数据表用户ID*/
		Integer userId = null;
		/** 根据客户端ID查询用户ID*/
		if (externalUserId!=null) {
			RfUser user = userService.getUserByExternalUserIdOrUserPhone(null,externalUserId);
			if (user != null) {
				userId = user.getUserId();
			} else {
				userId = -1;
			}
		}
		
		List<Integer> padIds=null;
		if(StringUtils.isNotBlank(userEmail) || StringUtils.isNotBlank(bindMobile)){
			List<RfUser> userList=userService.initQuery().andLike("userEmail", userEmail).andLike("userMobilePhone",bindMobile).findAll();
			if(!CollectionUtils.isEmpty(userList)){
				if(userList.size()>600){
					 throw new BusinessException ("查询的内容过多，请精确查询条件！PS：多条件查询可缩小查询范围，更精确");
				}	
				padIds=(List<Integer>)Collections3.extractToList(userPadService.initQuery().andIn("userId", Collections3.extractToList(userList, "userId")).findStopTrue(), "padId");
				padIds = Collections3.isEmpty(padIds) ? Lists.newArrayList(-1) : padIds;
			}else{
				padIds=Lists.newArrayList(-1);
			}
			if(padIds.size()>600){
				 throw new BusinessException ("查询的内容过多，请精确查询条件！PS：多条件查询可缩小查询范围，更精确");
			}
		}
		String likePadCode=null;
		String regexStr = "^[0-9a-zA-Z]+$";
		if(StringUtils.isNotBlank(viewPadUser.getPadCode())){
			if(!viewPadUser.getPadCode().matches(regexStr)){
				likePadCode=viewPadUser.getPadCode();
				viewPadUser.setPadCode(null);
			}
		}
		if(StringUtils.isNotBlank(padCode2)){
			if(!padCode2.matches(regexStr)){
				likePadCode=padCode2;
				padCode2 = null;
			}
		}
		//设备到期时间大于当前时间为正常设备
		Date leftOnlineTime=null;
	if(viewPadUser.getLeftOnlineTimeT2()!=null){
		if(viewPadUser.getLeftOnlineTimeT2()==0){
			//过期时间大于当前时间正常使用期
	        leftOnlineTime=new Date();
		}else
		if(viewPadUser.getLeftOnlineTimeT2()==-3){
			//过期时间小于当前时间大于当前时间减去赎回期
			//leftOnlineTime=new Date();
			viewPadUser.setExpireTimeT2(new Date());
		}else
			if(viewPadUser.getLeftOnlineTimeT2()==-4){
				//过期时间小于当前时间加赎回期
				viewPadUser.setExpireTimeT2(DateUtils.addDays(new Date(),Integer.parseInt(ConfigConstantsDb.configFacilityRansom())));
			}
	}
	if(StringUtils.isEmpty(viewPadUser.getBindStatus())){
		viewPadUser.setBindStatus(ConstantsDb.rfPadBindStatusBind());
	}
		// 查询条件
	List<ViewPadUser> vlist = viewPadUserService.initQuery(viewPadUser)
		    	.andEqualTo("padName", viewPadUser.getPadName())
		    	.andEqualTo("userPadNameT2", viewPadUser.getUserPadNameT2())
		    	.andEqualTo("padSn", viewPadUser.getPadSn())
		    	.andEqualTo("vmStatus", viewPadUser.getVmStatus())
				.andEqualTo("maintStatus", viewPadUser.getMaintStatus())
				.andEqualTo("grantOpenStatus",viewPadUser.getGrantOpenStatus() )
				.andEqualTo("userIdT2", userId)
			    .andIn("padId", padIds)
			    .andLessThanOrEqualTo("expireTimeT2",DateUtils.parseDate(expireTime)  )
			    .andEqualTo("padGradeT2", viewPadUser.getPadGradeT2())
				.andGreaterThanOrEqualTo("bindTimeT2", DateUtils.parseDate(viewPadUser.getBeginTimeStr()))
				.andLessThan("bindTimeT2", DateUtils.parseDateAddOneDay(viewPadUser.getEndTimeStr()))
				.andEqualTo("padControlId", viewPadUser.getPadControlId())
				.andEqualTo("padStatus", viewPadUser.getPadStatus())
				.andEqualTo("bindStatus", viewPadUser.getBindStatus())
				.andEqualTo("enableStatus", viewPadUser.getEnableStatus())
				.andEqualTo("faultStatus", viewPadUser.getFaultStatus())
				.andEqualTo("idcId", viewPadUser.getIdcId())
				.andLike("padCode", likePadCode)
				.andGreaterThanOrEqualTo("padCode",viewPadUser.getPadCode())
				.andLessThanOrEqualTo("padCode", padCode2)
				.andLike("padIp", viewPadUser.getPadIp())
				.andLessThanOrEqualTo("padControlTimeT2",DateUtils.parseDate(padControlTime) )
				.andLessThan("expireTimeT2", viewPadUser.getExpireTimeT2())
			    .andGreaterThanOrEqualTo("expireTimeT2", leftOnlineTime)
				.addOrderClause("padId", "asc")
				.addOrderForce(viewPadUser.getSort(), viewPadUser.getOrder())
				.pageDelTrue(viewPadUser.getPage(), viewPadUser.getRows());
		long currentTime = System.currentTimeMillis();
		for (ViewPadUser vPadUser : vlist) {
			vPadUser.getMap().put("idcName", vPadUser.getIdcId() == null ? "--" : idcService.load(vPadUser.getIdcId()).getIdcName());
			vPadUser.getMap().put("controlName", vPadUser.getUserControlId() == null ? "--" : controlService.load(vPadUser.getUserControlId()).getControlName());
			vPadUser.getMap().put("padControlName", vPadUser.getPadControlId() == null ? "--" : controlService.load(vPadUser.getPadControlId()).getControlName());
			vPadUser.getMap().put("manageControlName", vPadUser.getPadManageControlId() == null ? "--" : controlService.load(vPadUser.getPadManageControlId()).getControlName());
		    List<RfUserPad> userPad = userPadService.initQuery().andEqualTo("padId", vPadUser.getPadId()).findStopTrue();
		    vPadUser.getMap().put("userId", userPad.size() != 1 ? "--" : userService.load(userPad.get(0).getUserId()).getUserId());
		    vPadUser.getMap().put("bindMobile", userPad.size() != 1 ? "--" : userService.load(userPad.get(0).getUserId()).getUserMobilePhone());
		    vPadUser.getMap().put("userEmail", userPad.size() != 1 ? "--" : userService.load(userPad.get(0).getUserId()).getUserEmail());
			if(null!= vPadUser.getExpireTimeT2()&&!("".equals(vPadUser.getExpireTimeT2()))){
				vPadUser.getMap().put("controltime", DateUtils.formatDateTime2(vPadUser.getExpireTimeT2().getTime()-currentTime));
			}
			if(null!= vPadUser.getLeftControlTimeT2()&&!("".equals(vPadUser.getLeftControlTimeT2()))){
				Integer time=vPadUser.getLeftControlTimeT2();
				Integer hour=time/(60*60);
	 			Integer  min =(time/60-hour*60);
	 			String onlinetime=hour.toString()+"小时"+min.toString()+"分钟";
	 			vPadUser.getMap().put("onlinetime", onlinetime);
			}
			
			/** 查询客户端ID*/
			if(vPadUser.getUserIdT2() != null){
				/** 根据用户ID查询客户端ID*/
				vPadUser.getMap().put("externalUserId", userService.load(vPadUser.getUserIdT2()).getExternalUserId());
			}
		}
		PageInfo<ViewPadUser> pageInfo = new PageInfo<ViewPadUser>(vlist);
		
	/*		List<Integer> padID = new ArrayList<Integer>();
		List<RfUserPad> userPad = userPadService.initQuery().findStopTrue();
		for (RfUserPad rfUserPad : userPad) {
			padID.add(rfUserPad.getPadId());
		}
	 * List<RfPad> list = padService.initQuery(bean)
				.andLike("padCode", bean.getPadCode())
				.andEqualTo("bindStatus", bean.getBindStatus())
				.andEqualTo("padStatus", bean.getPadStatus())
				.andIn("padId", padIds)
				.andEqualTo("idcId", bean.getIdcId())
				.andIn("padId", padID)
				.andLike("padIp", bean.getPadIp())
				.addOrderClause("padId", "asc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());

		for (RfUserPad rfUserPad : userPad) {
			for (RfPad rfPad : list) {
				
			}
		}
		for (RfPad rfPad : list) {
			rfPad.getMap().put("idcName", rfPad.getIdcId() == null ? "--" : idcService.load(rfPad.getIdcId()).getIdcName());
			rfPad.getMap().put("controlName", rfPad.getUserControlId() == null ? "--" : controlService.load(rfPad.getUserControlId()).getControlName());
			rfPad.getMap().put("padControlName", rfPad.getPadControlId() == null ? "--" : controlService.load(rfPad.getPadControlId()).getControlName());
			rfPad.getMap().put("manageControlName", rfPad.getPadManageControlId() == null ? "--" : controlService.load(rfPad.getPadManageControlId()).getControlName());
		    userPad = userPadService.initQuery().andEqualTo("padId", rfPad.getPadId()).findStopTrue();
			rfPad.getMap().put("userId", userPad.size() != 1 ? "--" : userService.load(userPad.get(0).getUserId()).getUserId());
			rfPad.getMap().put("bindMobile", userPad.size() != 1 ? "--" : userPad.get(0).getBindMobile());
		}*/
		//PageInfo<RfPad> pageInfo = new PageInfo<RfPad>(list);
		
		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) throws Exception {

		if (bean.getPadId() == null) {

		} else {
			bean = padService.get(bean.getPadId());

		}

		model.addAttribute("bean", bean);
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) throws Exception {
		padService.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) throws Exception {
		padService.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) throws Exception {
		padService.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) throws Exception {
		padService.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) throws Exception {
		padService.delete(request, bean);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "noticeForm")
	public String roleForm(HttpServletRequest request, HttpServletResponse response, Model model, ViewPadUser bean, String padCode2,String padControlTime,String expireTime,String userEmail,String bindMobile) {
	
		
		if (bean.getIds() == null || "".equals(bean.getIds())) {
			List<Integer> padIds=null;
			if(StringUtils.isNotBlank(userEmail) || StringUtils.isNotBlank(bindMobile)){
				List<RfUser> userList=userService.initQuery().andLike("userEmail", userEmail).andLike("userMobilePhone",bindMobile).findStopTrue();
				if(!CollectionUtils.isEmpty(userList)){
					padIds=(List<Integer>)Collections3.extractToList(userPadService.initQuery().andIn("userId", Collections3.extractToList(userList, "userId")).findStopTrue(), "padId");
				}else{
					padIds=Lists.newArrayList(-1);
				}
			}
			String likePadCode=null; 
			if(!StringUtils.isEmpty(padCode2)){
				likePadCode=bean.getPadCode();
				bean.setPadCode(null);
			}
			//设备到期时间大于当前时间为正常设备
			Date leftOnlineTime=null;
		if(bean.getLeftOnlineTimeT2()!=null){
			if(bean.getLeftOnlineTimeT2()==0){
				//过期时间大于当前时间正常使用期
		        leftOnlineTime=new Date();
			}else
			if(bean.getLeftOnlineTimeT2()==-3){
				//过期时间小于当前时间大于当前时间减去赎回期
				//leftOnlineTime=new Date();
				bean.setExpireTimeT2(new Date());
			}else
				if(bean.getLeftOnlineTimeT2()==-4){
					//过期时间小于当前时间加赎回期
					bean.setExpireTimeT2(DateUtils.addDays(new Date(),Integer.parseInt(ConfigConstantsDb.configFacilityRansom())));
				}
		}
			// 查询条件
		List<ViewPadUser> vlist = viewPadUserService.initQuery(bean)
				    .andIn("padId",padIds)
				    .andLessThanOrEqualTo("expireTimeT2",DateUtils.parseDate(expireTime)  )
				    .andEqualTo("padGradeT2", bean.getPadGradeT2())
					.andGreaterThanOrEqualTo("bindTimeT2", DateUtils.parseDate(bean.getBeginTimeStr()))
					.andLessThan("bindTimeT2", DateUtils.parseDateAddOneDay(bean.getEndTimeStr()))
					.andEqualTo("padControlId", bean.getPadControlId())
					.andEqualTo("padStatus", bean.getPadStatus())
					.andEqualTo("bindStatus", ConstantsDb.rfPadBindStatusBind())
					.andEqualTo("enableStatus", bean.getEnableStatus())
					.andEqualTo("faultStatus", bean.getFaultStatus())
					.andEqualTo("idcId", bean.getIdcId())
					.andLike("padCode", bean.getPadCode())
					.andGreaterThanOrEqualTo("padCode",likePadCode)
					.andLessThanOrEqualTo("padCode", padCode2)
					.andLike("padIp", bean.getPadIp())
					.andIsNotNull("userIdT2")
					.andLessThanOrEqualTo("padControlTimeT2",DateUtils.parseDate(padControlTime) )
					.andLessThan("expireTimeT2", bean.getExpireTimeT2())
				    .andGreaterThanOrEqualTo("expireTimeT2", leftOnlineTime)
					.addOrderClause("padId", "asc")
					.addOrderForce(bean.getSort(), bean.getOrder())
					.findStopTrue();
			// 去重复会员
			for (int i = 0; i < vlist.size() - 1; i++) {
				for (int j = vlist.size() - 1; j > i; j--) {
					if (vlist.get(j).getUserIdT2().equals(vlist.get(i).getUserIdT2())) {
						vlist.remove(j);
					}
				}
			}
			List<RfUser> userList = new ArrayList<RfUser>();
			for (ViewPadUser rfuserPad : vlist) {
				if (rfuserPad.getUserIdT2() != null) {
					userList.add(userService.initQuery().get(rfuserPad.getUserIdT2()));
				}
			}
			model.addAttribute("userList", userList);
		} else {
			String pad[] = bean.getIds().split(",");
			List<RfUserPad> userPadList = new ArrayList<RfUserPad>();
			for (String padid : pad) {
				userPadService.padUserStatus(request, Integer.parseInt(padid));
				userPadList.add(userPadService.initQuery().andEqualTo("padId", Integer.parseInt(padid)).findStopTrue().get(0));
			}
			// 去重复会员
			for (int i = 0; i < userPadList.size() - 1; i++) {
				for (int j = userPadList.size() - 1; j > i; j--) {
					if (userPadList.get(j).getUserId().equals(userPadList.get(i).getUserId())) {
						userPadList.remove(j);
					}
				}
			}
			List<RfUser> userList = new ArrayList<RfUser>();
			for (RfUserPad userPad : userPadList) {
				userList.add(userService.initQuery().get(userPad.getUserId()));
			}
			model.addAttribute("userList", userList);
		}
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "notice")
	public void notice(HttpServletRequest request, HttpServletResponse response, Model model, String uidS, RfNotice notice) throws Exception {
		if(null==notice.getNoticeTitle()||"".equals(notice.getNoticeTitle())){
			throw new BusinessException("公告标题不能为空");
		}
	    if(null==notice.getNoticeContent()||"".equals(notice.getNoticeContent())){
	    	throw new BusinessException("公告内容不能为空");
		}
		if (uidS == null||"".equals(uidS)) {
			throw new BusinessException("发送的用户数据不能为"+uidS);
		}
		uidS.trim();
		noticeService.insert(request, uidS, notice);
	}
	
	@RequestMapping(value="padCodeForm")
	public String padCodeForm(HttpServletRequest request, HttpServletResponse response, Model model,RfPad bean ){
		return this.toPage(request, response, model);
	}
	@RequestMapping(value="noticePadCode")
	public void noticePadCode(HttpServletRequest request,HttpServletResponse response ,RfNotice bean,String padCodes) throws Exception{
		noticeService.padCodeNotice(request,bean,padCodes);
	}
	
	//导出EXcel数据
	@RequestMapping(value="export")
	public String export(HttpServletRequest request, HttpServletResponse response, Model model,ViewPadUser viewPadUser, 
			String exportHead, String exportField, String exportName,
			String padCode2,String padControlTime,String expireTime,String userEmail,String bindMobile, Integer externalUserId) throws Exception{
		exportHead=StringUtils.removeEnd(exportHead, ",");
		exportField=StringUtils.removeEnd(exportField, ",");
		response.setContentType("application/binary;charset=UTF-8");
		ServletOutputStream outputStream = response.getOutputStream();
		response.setHeader("Content-disposition", "attachment; filename=" + ExcelUtils.getFileName(request, exportName)+".xls");
		// 创建一个workbook 对应一个excel应用文件
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
			viewPadUser.setPage(page);
			viewPadUser.setRows(1000);
			PageInfo<ViewPadUser> pageInfo=this.list( request,  response,  model, viewPadUser, padCode2, padControlTime, expireTime, userEmail, bindMobile, externalUserId);
			List<ViewPadUser> list=pageInfo.getList();
			if(!Collections3.isEmpty(list)){
				long currentTime = System.currentTimeMillis();
				for (ViewPadUser vPadUser : list) {
					if(StringUtils.isNotBlank(vPadUser.getPadGradeT2())){
						vPadUser.setPadGradeT2(DictHelper.getLabel("rf_user_pad.pad_grade", vPadUser.getPadGradeT2()));
					}
					vPadUser.getMap().put("idcName", vPadUser.getIdcId() == null ? "--" : idcService.load(vPadUser.getIdcId()).getIdcName());
					vPadUser.getMap().put("controlName", vPadUser.getUserControlId() == null ? "--" : controlService.load(vPadUser.getUserControlId()).getControlName());
					vPadUser.getMap().put("padControlName", vPadUser.getPadControlId() == null ? "--" : controlService.load(vPadUser.getPadControlId()).getControlName());
					vPadUser.getMap().put("manageControlName", vPadUser.getPadManageControlId() == null ? "--" : controlService.load(vPadUser.getPadManageControlId()).getControlName());
				    List<RfUserPad> userPad = userPadService.initQuery().andEqualTo("padId", vPadUser.getPadId()).findStopTrue();
				    vPadUser.getMap().put("userId", userPad.size() != 1 ? "--" : userService.load(userPad.get(0).getUserId()).getUserId());
				    vPadUser.getMap().put("bindMobile", userPad.size() != 1 ? "--" : userService.load(userPad.get(0).getUserId()).getUserMobilePhone());
				    vPadUser.getMap().put("userEmail", userPad.size() != 1 ? "--" : userService.load(userPad.get(0).getUserId()).getUserEmail());
					if(null!= vPadUser.getExpireTimeT2()&&!("".equals(vPadUser.getExpireTimeT2()))){
						vPadUser.getMap().put("controltime", DateUtils.formatDateTime2(vPadUser.getExpireTimeT2().getTime()-currentTime));
					}
					if(null!= vPadUser.getLeftControlTimeT2()&&!("".equals(vPadUser.getLeftControlTimeT2()))){
						Integer time=vPadUser.getLeftControlTimeT2();
						Integer hour=time/(60*60);
			 			Integer  min =(time/60-hour*60);
			 			String onlinetime=hour.toString()+"小时"+min.toString()+"分钟";
			 			vPadUser.getMap().put("onlinetime", onlinetime);
					}
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
	
	/**
	 * 根据条件发送公告
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @return
	 */
	@RequestMapping(value="param")
	public String param(HttpServletRequest request, HttpServletResponse response, Model model,RfNotice notice ){
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value="pushNotice")
	public void pushNotice(HttpServletRequest request, HttpServletResponse response, Model model,RfNotice notice,
			String padName,String padCode,String padIp,String isPush) throws Exception{
		if(StringUtils.isEmpty(notice.getNoticeTitle())){
			throw new BusinessException("公告标题不能为空！");
		}
		if(StringUtils.isEmpty(notice.getNoticeContent())){
			throw new BusinessException("公告内容不能为空！");
		}
		log.info("[noticeTitle{},noticeContent{}]",new Object[]{notice.getNoticeTitle(),notice.getNoticeContent()});
	
		if(StringUtils.isBlank(padName) && StringUtils.isBlank(padCode) && StringUtils.isBlank(padIp)){
			throw new BusinessException("查询条件不能为空！");
		}
		
		if(StringUtils.isEmpty(isPush)){
			isPush=YesOrNoStatus.NO;
		}
		
		notice.setCreater(SessionUtils.getCurrentUserId(request));
		notice.setCreateTime(new Date());
		notice.setStatus(YesOrNoStatus.YES);
		notice.setEnableStatus(YesOrNoStatus.YES);
		notice.setNoticeType(NoticeTypeUtils.PART);
	
		RfPad pad = new RfPad();
		pad.setPadName(padName);
		pad.setPadCode(padCode);
		pad.setPadIp(padIp);
		UMengInfoDto dto = null;
		try{
			dto = noticeService.savePadNotice(request, notice, pad,isPush,PushType.PAD_NOTICE_PUSH);
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException("保存公告报错！");
		}
		
		try{
			if(null != dto){
				Map<String,Object> params = new HashMap<String,Object>();
				if(StringUtils.isNotBlank(padName)){
					params.put("padName", padName);
				}
				if(StringUtils.isNotBlank(padCode)){
					params.put("padCode", padCode);
				}
				
				if(StringUtils.isNotBlank(padIp)){
					params.put("padIp", padIp);
				}
				String pushExist = JSONObject.fromObject(params).toString();
				
				if(YesOrNoStatus.YES.equals(isPush)){
					
					PushUmengInfoLog umengInfo = new PushUmengInfoLog();
		    		umengInfo.setTitle(notice.getNoticeTitle());
		    		umengInfo.setNoticeContent(notice.getNoticeContent());
		    		umengInfo.setPushType(PushType.PAD_NOTICE_PUSH);
		    		umengInfo.setPushExist(pushExist);
		    		umengInfo.setPushStatus(PushStatus.PUSH_FAIL);
		    		umengInfo.setStatus(YesOrNoStatus.YES);
		    		umengInfo.setEnableStatus(YesOrNoStatus.YES);
		    		umengInfo.setCreater(SessionUtils.getCurrentUserId(request));
		    		umengInfo.setCreateTime(new Date());
		    		
		    		Integer id = userDataService.insertVo(umengInfo);
		    		if(null != id){//发送jms
		    			pushNoticeProducer.sendMessage(id.toString());
		    		}else{
		    			throw new BusinessException("新增友盟推送公告日志报错！");
		    		}
					/*noticeService.sendPadUmengInfo(request,isPush, params, dto.getRfNotice(), 
							PushType.PAD_NOTICE_PUSH, pushExist,id);*/
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException("推送公告报错！");
		}
		
	}
	
}