package com.redfinger.manager.modules.game.web;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfGame;
import com.redfinger.manager.common.domain.RfGameApp;
import com.redfinger.manager.common.domain.RfGameChannel;
import com.redfinger.manager.common.domain.SysDict;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.DictHelper;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.ExcelUtils;
import com.redfinger.manager.common.utils.GameStatusUtils;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.ShellRemoteUtil;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.base.service.ClassService;
import com.redfinger.manager.modules.base.service.DictService;
import com.redfinger.manager.modules.game.service.GameAppService;
import com.redfinger.manager.modules.game.service.GameChannelService;
import com.redfinger.manager.modules.game.service.GameService;
import com.redfinger.manager.modules.game.service.RfMarketGameService;

@Controller
@RequestMapping(value = "/game/manage")
public class GameController extends BaseController {

	@Value("#{configProperties['game.shell.path']}")
	private String gameShellPath;
	@Value("#{configProperties['game.file.path']}")
	private String gameFilePath;
	@Value("#{configProperties['game.shell.host']}")
	private String gameShellHost;
	@Value("#{configProperties['game.shell.user']}")
	private String gameShellUser;
	@Value("#{configProperties['game.shell.passwd']}")
	private String gameShellPasswd;
	
	@Autowired
	GameService service;
	@Autowired
	ClassService classService;
	@Autowired
	GameAppService gameGroupService;
	@Autowired
	DictService dictService;
	@Autowired
	GameChannelService channelService;
	@Autowired
	RfMarketGameService marketGameService;
	
	

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		List<RfGameApp> gameApps = gameGroupService.initQuery().findStopTrue();
		model.addAttribute("gameApps", gameApps);
		
		//是与否
		model.addAttribute("yesOrNo", YesOrNoStatus.DICT_MAP);
		model.addAttribute("currentUserId", SessionUtils.getCurrentUserId(request));
		return this.toPage(request, response, model);  
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfGame> list(HttpServletRequest request, HttpServletResponse response, Model model, RfGame bean) throws Exception {
		List<RfGame> list = service.initQuery(bean)
				.andLike("gamePackageName", bean.getGamePackageName())
				.andLike("gameName", bean.getGameName())
				.andLike("gameVersion", bean.getGameVersion())
				.andEqualTo("versionCode", bean.getVersionCode())
				.andEqualTo("softType", bean.getSoftType())
				.andEqualTo("isHot", bean.getIsHot())
				.andEqualTo("gameAppId", bean.getGameAppId())
				.andEqualTo("deviceVersion", bean.getDeviceVersion())
				.andEqualTo("enableStatus", bean.getEnableStatus())
				.addOrderClause("createTime", "desc")
				.addOrderClause("gameId", "asc")
				.addOrderForce(bean.getSort(), bean.getOrder())	
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		List<SysDict> softTypeList = dictService.initQuery().andEqualTo("dictCategory", "rf_game.soft_type").findDelTrue();
		Map<String, SysDict> softTypeMap = new HashMap<String, SysDict>();
		for (SysDict dict : softTypeList) {
			softTypeMap.put(dict.getDictValue(), dict);
		}
		
		for(RfGame game : list){
			if(game.getGameAppId() != null){
				RfGameApp gameApp = gameGroupService.get(game.getGameAppId());
				if(gameApp != null){
					game.getMap().put("gameAppName", gameApp.getGameAppName());
				}
			}
			
			if(game.getChannelId() != null){
				RfGameChannel gameChannel = channelService.get(game.getChannelId());
				if(gameChannel != null){
					game.getMap().put("gameChannelName", gameChannel.getChannelName());
				}
			}
			
			if(StringUtils.isNotBlank(game.getSoftType())){
				SysDict dict = softTypeMap.get(game.getSoftType());
				if(dict != null){
					game.getMap().put("softType", dict.getDictName());
				}
			}
			
			if(StringUtils.isNotEmpty(game.getGameStatus())){
				game.getMap().put("gameStatusName", GameStatusUtils.DICT_MAP.get(game.getGameStatus()));
			}
		}
		PageInfo<RfGame> pageInfo = new PageInfo<RfGame>(list);
		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfGame bean) throws Exception {
		if (bean.getGameId() != null) {
			bean = service.get(bean.getGameId());
			model.addAttribute("bean", bean);
		}else{
			//新增时默认工具类型的免责声明
			RfGame game = service.getGameBySoftType(ConstantsDb.toolSoftType());
			if(null != game && StringUtils.isNotBlank(game.getDisclaimer())){
				bean.setDisclaimer(game.getDisclaimer());
			}
		}
		
		List<RfGameChannel> gameChannelList = channelService.initQuery().findStopTrue();
		model.addAttribute("gameChannelList", gameChannelList);
		
		List<RfGameApp> gameAppList = gameGroupService.initQuery().findStopTrue();
		model.addAttribute("gameAppList", gameAppList);
		
		List<SysDict> softTypeList = dictService.initQuery().andEqualTo("dictCategory", "rf_game.soft_type").findDelTrue();
		model.addAttribute("softTypeList", softTypeList);
		//classService.getGameClassTree(ConstantsDb.rfClassClassTypeGame(), ConstantsDb.rfClassClassTypeTools())
		 model.addAttribute("ClassList", classService.getGameClassList(ConstantsDb.rfClassClassTypeGame(), ConstantsDb.rfClassClassTypeTools()));
		
		//游戏状态
		model.addAttribute("status", GameStatusUtils.DICT_MAP);
		//是与否
		model.addAttribute("yesOrNo", YesOrNoStatus.DICT_MAP);
		
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfGame bean, String md5ShaSum) throws Exception {
		bean.setOpenFrequency(0);
		bean.setDowncount(0);
		/*if(StringUtils.isBlank(bean.getMd5())){
			throw new BusinessException("MD5值不能为空");
		}
		if(StringUtils.isBlank(bean.getSha())){
			throw new BusinessException("SHA1值不能为空");
		}
		if(bean.getVersionCode() == null){
			throw new BusinessException("游戏版本号不能为空");
		}*/
		bean.setMd5("");
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfGame bean, String md5ShaSum) throws Exception {
		/*if(StringUtils.isBlank(bean.getMd5())){
			throw new BusinessException("MD5值不能为空");
		}
		if(StringUtils.isBlank(bean.getSha())){
			throw new BusinessException("SHA1值不能为空");
		}
		if(bean.getVersionCode() == null){
			throw new BusinessException("游戏版本号不能为空");
		}*/
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfGame bean) throws Exception {
		service.startGame(request,bean.getIds());
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfGame bean) throws Exception {
		
		service.stopGame(request,bean.getIds());
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfGame bean) throws Exception {
		service.delete(request, bean);
	}
	
	@RequestMapping(value = "md5ShaSum")
	public void md5ShaSum(HttpServletRequest request, HttpServletResponse response, Model model, RfGame bean) throws Exception {
		bean = service.initQuery().get(bean.getGameId());
		try {
			updateMd5Sha(bean); //远程计算md5、sha，用于文件秒传
		} catch (Exception e) {
			log.error("计算MD5和SHA失败:"+e.getMessage(), e);
			throw new BusinessException("计算MD5和SHA失败，请联系管理员");
		}
		service.update(request, bean);
	}
	
	public RfGame updateMd5Sha(RfGame game) throws Exception{
		if(StringUtils.isBlank(game.getGameFile())){
			game.setMd5("");
			game.setSha("");
			return null;
		}
		
		String command = (StringUtils.isNotBlank(gameShellPath) ? ("cd " + gameShellPath + ";") : "") + " ./run_file_info.sh " + gameFilePath+"/"+game.getGameFile();
		log.info("md5 and sha1 host:{} user:{} command:{}", new Object[]{gameShellHost, gameShellUser, command});
		String result = ShellRemoteUtil.execShell(command, gameShellHost, gameShellUser, gameShellPasswd);
		log.info("{} md5 and sha1 result:{}, host:{}, user:{}", game.getGameFile(), result, gameShellHost, gameShellUser);
		String md5 = result.split("#")[0];
		String sha = result.split("#")[1];
		String versionCode = result.split("#")[2];
		game.setMd5(md5.toUpperCase());
		game.setSha(sha);
		game.setVersionCode(Integer.parseInt(versionCode));
		return game;
	}
	
	@ResponseBody
	@RequestMapping(value = "updateMd5ShaVersionCode")
	public Map<String,Object> updateMd5ShaVersionCode(RfGame game){
		Map<String,Object> params = new HashMap<String,Object>();
		if(StringUtils.isBlank(game.getGameFile())){
			game.setMd5("");
			game.setSha("");
			game.setVersionCode(null);
			return null;
		}
		try{
			String command = (StringUtils.isNotBlank(gameShellPath)?("cd " + gameShellPath + ";") : "") + " ./run_file_info.sh " + gameFilePath+"/"+game.getGameFile();
			log.info("md5 and sha1 host:{} user:{} command:{}", new Object[]{gameShellHost, gameShellUser, command});
			String result = ShellRemoteUtil.execShell(command, gameShellHost, gameShellUser, gameShellPasswd);
			log.info("{} md5 and sha1 result:{}, host:{}, user:{}", game.getGameFile(), result, gameShellHost, gameShellUser);
			String md5 = result.split("#")[0];
			String sha = result.split("#")[1];
			String versionCode = result.split("#")[2];
			params.put("md5", md5.toUpperCase());
			params.put("sha", sha);
			params.put("versionCode", Integer.parseInt(versionCode));
			return params;
		}catch(Exception e){
			log.error("计算MD5、SHA和文件版本号失败:"+e.getMessage(), e);
			params.put("result", "计算MD5、SHA和文件版本号失败，请重新填写下载地址");
			return params;	
		}
	}
	
	 // 导出
	@RequestMapping(value="export")
	public String export(HttpServletRequest request, HttpServletResponse response, Model model, RfGame bean,String exportHead, String exportField, String exportName)throws Exception{
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
			bean.setPage(page);
			bean.setRows(1000);
			
			if(StringUtils.isNotBlank(bean.getGameName())){
				//解决乱码问题
				bean.setGameName(URLDecoder.decode(bean.getGameName(),"utf-8"));
			}
			
			PageInfo<RfGame> pageInfo=this.list(request, response, model, bean);
			List<RfGame> list=pageInfo.getList();
			if(!Collections3.isEmpty(list)){
				for(RfGame game:list){
					game.setEnableStatus(DictHelper.getLabel("global.enable_status", game.getEnableStatus()));
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
	
	
	@ResponseBody
	@RequestMapping(value = "selectGameApp")
	public Map<String,Object> selectGameApp(String gameAppName) throws Exception{
		Map<String,Object> params = new HashMap<String,Object>();
		if(StringUtils.isBlank(gameAppName)){
			return null;
		}
		gameAppName = URLDecoder.decode(gameAppName, "UTF-8");
		List<RfGameApp> list = gameGroupService.selectRfGameAppByName(gameAppName);
		if(null == list){
			list = gameGroupService.initQuery().findStopTrue();
		}
		params.put("list", list);
		return params;
	}

	
	@ResponseBody
	@RequestMapping(value = "selectGameChannel")
	public Map<String,Object> selectGameChannel(String channelName) throws Exception{
		Map<String,Object> params = new HashMap<String,Object>();
		if(StringUtils.isBlank(channelName)){
			return null;
		}
		channelName = URLDecoder.decode(channelName, "UTF-8");
		List<RfGameChannel> list = null;
		
		list = channelService.selectByChannelName(channelName);
		if(null == list){
			list = channelService.initQuery().singleStopTrue();
		}
		params.put("list", list);
		return params;
	}
	
	
	@RequestMapping(value = "relatedDemoMarket")
	public void relatedDemoMarket(HttpServletRequest request, HttpServletResponse response, Model model, RfGame bean) throws Exception {
		String[] idArray=StringUtils.split(bean.getIds(),",");
		if(idArray.length == 0 || idArray == null){
			throw new BusinessException("请选择游戏应用");
		}
		marketGameService.saveMarkGameAppForIds(idArray, request);
	}
}
