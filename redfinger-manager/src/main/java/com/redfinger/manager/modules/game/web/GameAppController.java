package com.redfinger.manager.modules.game.web;

import java.util.ArrayList;
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
import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.base.FilePathUtils;
import com.redfinger.manager.common.domain.RfGame;
import com.redfinger.manager.common.domain.RfGameApp;
import com.redfinger.manager.common.domain.RfGameAppRelation;
import com.redfinger.manager.common.domain.RfGameChannel;
import com.redfinger.manager.common.domain.RfMarketGameApp;
import com.redfinger.manager.common.domain.SysDict;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.FileUtils;
import com.redfinger.manager.common.utils.GameStatusUtils;
import com.redfinger.manager.common.utils.OsTypeUtils;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.base.service.DictService;
import com.redfinger.manager.modules.game.service.GameAppRelationService;
import com.redfinger.manager.modules.game.service.GameAppService;
import com.redfinger.manager.modules.game.service.GameChannelService;
import com.redfinger.manager.modules.game.service.GameService;
import com.redfinger.manager.modules.game.service.MarketGameAppService;

@Controller
@RequestMapping(value = "/game/gameApp")
public class GameAppController extends BaseController {

	@Autowired
	private GameAppService service;
	@Autowired
	private GameService gameService;
	@Autowired
	private GameAppService gameGroupService;
	@Autowired
	private DictService dictService;
	@Autowired
	private GameChannelService channelService;
	@Autowired
	private GameAppRelationService relationService;
	@Autowired
	private MarketGameAppService marketGameAppService;
	@Autowired
	private FilePathUtils filePathUtils;
	@Autowired
	private FileUtils fileUtil;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);  
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfGameApp> list(HttpServletRequest request, HttpServletResponse response, Model model, RfGameApp bean) throws Exception {
		List<RfGameApp> list = service.initQuery(bean)
				.andLike("gameAppName", bean.getGameAppName())
				.andEqualTo("softType", bean.getSoftType())
				.addOrderClause("createTime", "desc")
				.addOrderClause("gameAppId", "asc")
				.addOrderForce(bean.getSort(), bean.getOrder())	
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		PageInfo<RfGameApp> pageInfo = new PageInfo<RfGameApp>(list);
		return pageInfo;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "gameList")
	public PageInfo<RfGame> gameList(HttpServletRequest request, HttpServletResponse response, Model model, RfGame bean) throws Exception {
		List<RfGame> list = gameService.initQuery(bean)
				.andLike("gameName", bean.getGameName())
				.andEqualTo("status", YesOrNoStatus.YES)
				.andEqualTo("enableStatus", YesOrNoStatus.YES)
				.addOrderClause("createTime", "desc")
				.addOrderClause("gameId", "asc")
				.addOrderForce(bean.getSort(), bean.getOrder())	
				.pageStopTrue(bean.getPage(), bean.getRows());
		
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
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfGameApp bean) throws Exception {
		if (bean.getGameAppId() != null) {
			bean = service.get(bean.getGameAppId());
			model.addAttribute("bean", bean);
		}
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfGameApp bean) throws Exception {
		int count = service.initQuery().andEqualTo("gameAppName", bean.getGameAppName()).countDelTrue();
		if(count > 0){
			throw new BusinessException("["+bean.getGameAppName()+"]已存在，请修改后再保存");
		}
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfGameApp bean) throws Exception {
		int count = service.initQuery().andEqualTo("gameAppName", bean.getGameAppName()).andNotEqualTo("gameAppId", bean.getGameAppId()).countDelTrue();
		if(count > 0){
			throw new BusinessException("["+bean.getGameAppName()+"]已存在，请修改后再保存");
		}
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfGameApp bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfGameApp bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfGameApp bean) throws Exception {
		service.delete(request, bean);
	}
	
	@RequestMapping(value = "recommandForm")
	public String recommandForm(HttpServletRequest request, HttpServletResponse response, Model model, RfGameApp bean) throws Exception {
		Integer gameAppId = bean.getGameAppId();
		if(null == gameAppId){
			throw new BusinessException("请选择游戏应用");
		}
		
		RfGameApp gameApp = service.get(gameAppId);
		if(null == gameApp){
			throw new BusinessException("您选择游戏应用不存在");
		}
		
		if(StringUtils.equals(gameApp.getEnableStatus(), YesOrNoStatus.NO)){
			throw new BusinessException("您选择游戏应用被禁用,请重新选择");
		}
		
		if(StringUtils.equals(gameApp.getStatus(), YesOrNoStatus.NO)){
			throw new BusinessException("您选择游戏应用被删除,请重新选择");
		}
		
		List<RfGameAppRelation> rfGameAppRelationList = relationService.selectGameAppRelationByGameId(gameAppId);
		
		//List<Integer> gameIdList = Lists.newArrayList();
		List<RfGame> gameList = Lists.newArrayList();
		if (rfGameAppRelationList != null && rfGameAppRelationList.size() > 0) {
			for (RfGameAppRelation rfGameAppRelation : rfGameAppRelationList) {
				if(rfGameAppRelation != null ){
					Integer gameId = rfGameAppRelation.getGameId();
					if (gameId != null) {
						//gameIdList.add(gameId);
						List<RfGame> rfGameList = gameService.initQuery().andEqualTo("gameId", gameId).findDelTrue();
						if(rfGameList != null && rfGameList.size() > 0){
							gameList.add(rfGameList.get(0));
						}
					}
				}
			}
		}
		
		model.addAttribute("gameAppRelationList", rfGameAppRelationList);
		
		//List<RfGame> rfGameList = gameService.initQuery().andIn("gameId", gameIdList).findDelTrue();
		model.addAttribute("gameList", gameList);
		
		model.addAttribute("gameAppId", gameAppId);
		if(StringUtils.isNoneBlank(gameApp.getGameAppName())){
			model.addAttribute("gameAppName", gameApp.getGameAppName());
		}
		
		List<RfGame> games = gameService.initQuery().findStopTrue();
		model.addAttribute("games", games);
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "recommand")
	public void recommand(HttpServletRequest request, HttpServletResponse response, Model model, RfGameApp bean,Integer gameId[],Integer[] relationIds) throws Exception {
		log.info("recommand gameAppId:{},gameIds:{}",new Object[]{bean.getGameAppId(),gameId});
		if(null == bean.getGameAppId()){
			throw new BusinessException("请选择游戏应用");
		}
		List<Integer> gameIds = new ArrayList<Integer>();
		List<Integer> indexs = new ArrayList<Integer>();
		for (int i = 0; i < gameId.length; i++) {
			// -1代表已经该关系应用没有修改过
			if(null != gameId[i] && gameId[i] != -1){
				gameIds.add(gameId[i]);
				// 标记第几个关联应用修改了
				indexs.add(i);
			}else {
				// 应用关联id不为空, 应用id为空, 删除该关联应用
				if(relationIds != null && relationIds.length  > i && relationIds[i] != null && gameId[i] == null){
					// 删除关联的应用
					relationService.deleteByPrimaryKey(relationIds[i]);
				}
			}
		}
		if(gameIds.size() < 1){
			return;
			//throw new BusinessException("请选择游戏");
		}
		
		if(checkSam(gameIds)){
			throw new BusinessException("您选择的游戏有相同的,请重新选择");
		}
		
		List<RfGameAppRelation> list = new ArrayList<RfGameAppRelation>();
		
		int index = 0;
		for (Integer integer : gameIds) {
			
			int count = relationService.selectGameAppRelation(bean.getGameAppId(), integer);
			if(count > 0){
				RfGameApp gameApp = service.get(bean.getGameAppId());
				RfGame game = gameService.get(integer);
				throw new BusinessException("游戏应用:"+gameApp.getGameAppName()+"已经关联游戏:"+game.getGameName()+",请重新选择");
			}
			RfGameAppRelation relation = new RfGameAppRelation();
			relation.setGameAppId(bean.getGameAppId());
			relation.setGameId(integer);
			relation.setStatus(YesOrNoStatus.YES);
			relation.setEnableStatus(YesOrNoStatus.YES);
			// 标记是否为新增
			boolean isInsert = true;
			// 原来是否有游戏应用推荐, 有就更新
			if(relationIds != null && relationIds.length > indexs.get(index) && relationIds[indexs.get(index)] != null){
					relation.setRelationId(relationIds[indexs.get(index)]);
					relationService.update(request, relation);
					isInsert = false;
			}
			if(isInsert){
				relation.setCreater(SessionUtils.getCurrentUserId(request));
				relation.setCreateTime(new Date());
				list.add(relation);
			}
			index ++;
		}
		relationService.saveRelation(list);
	}
	 
	@RequestMapping(value = "relatedDemoMarketForm")
	public String relatedDemoMarketForm(HttpServletRequest request, HttpServletResponse response, Model model, RfGameApp bean) throws Exception {
		if(null == bean.getGameAppId()){
			throw new BusinessException("请选择游戏应用");
		}
		bean = service.get(bean.getGameAppId());
		if(null == bean){
			throw new BusinessException("您选择的游戏应用不存在");
		}
		
		List<RfMarketGameApp> list = marketGameAppService.initQuery().andEqualTo("gameAppId", bean.getGameAppId()).andEqualTo("marketType", OsTypeUtils.demoMarket).findStopTrue();
		if(null != list && list.size() > 0){
			throw new BusinessException("您选择的游戏应用已经存在试玩应用市场");
		}
		if(StringUtils.equals(bean.getEnableStatus(), YesOrNoStatus.NO)){
			throw new BusinessException("您选择的游戏应用已经被禁用，请重新选择");
		}
		
		if(StringUtils.equals(bean.getStatus(), YesOrNoStatus.NO)){
			throw new BusinessException("您选择的游戏应用已经被删除，请重新选择");
		}
		
		if(null != bean){
			model.addAttribute("bean", bean);
		}
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "relatedDemoMarket")
	public void relatedDemoMarket(HttpServletRequest request, HttpServletResponse response, Model model, RfMarketGameApp bean) throws Exception {
		if(StringUtils.isBlank(bean.getGameAppImage())){
			throw new BusinessException("请选择图片");
		}
		bean.setMarketType(OsTypeUtils.demoMarket);
		marketGameAppService.save(request, bean);
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
