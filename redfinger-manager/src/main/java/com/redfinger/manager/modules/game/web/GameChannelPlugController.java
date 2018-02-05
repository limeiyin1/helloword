package com.redfinger.manager.modules.game.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfGame;
import com.redfinger.manager.common.domain.RfGameChannel;
import com.redfinger.manager.common.domain.RfGameChannelPlug;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.modules.game.service.GameChannelPlugService;
import com.redfinger.manager.modules.game.service.GameChannelService;
import com.redfinger.manager.modules.game.service.GameService;

@Controller
@RequestMapping(value = "/game/plug")
public class GameChannelPlugController extends BaseController {

	@Autowired
	private GameChannelPlugService service;
	@Autowired
	private GameService gameService;
	@Autowired
	private GameChannelService gameChannelService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		List<String> softTypes = new ArrayList<String>();
		softTypes.add(ConstantsDb.toolSoftType());
		softTypes.add(ConstantsDb.softSoftType());

		List<RfGame> games = gameService.initQuery().andIn("softType", softTypes).findStopTrue();
		List<RfGameChannel> gameChannels = gameChannelService.initQuery().findStopTrue();
		model.addAttribute("games", games);
		model.addAttribute("gameChannels", gameChannels);
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfGameChannelPlug> list(HttpServletRequest request, HttpServletResponse response, Model model,
			RfGameChannelPlug bean, String channelName) throws Exception {

		List<Integer> rfGameChannelIds = new ArrayList<>();
		List<RfGameChannel> rfGameChannel = gameChannelService.initQuery().andLike("channelName", channelName)
				.findAll();
		if (null != rfGameChannel && rfGameChannel.size() > 0) {
			for (RfGameChannel rgc : rfGameChannel) {
				rfGameChannelIds.add(rgc.getChannelId());
			}
		}else{
			rfGameChannelIds.add(-1);
		}

		List<RfGameChannelPlug> list = service.initQuery(bean)
				.andIn("gameChannelId", rfGameChannelIds)
				.addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());

		if (null != list && list.size() > 0) {
			for (RfGameChannelPlug rfGameChannelPlug : list) {
				if (null != rfGameChannelPlug.getGameChannelId()) {
					RfGameChannel gameChannel = gameChannelService.get(rfGameChannelPlug.getGameChannelId());
					rfGameChannelPlug.getMap().put("channelName", gameChannel.getChannelName());
				}

				if (null != rfGameChannelPlug.getGameId()) {
					RfGame game = gameService.get(rfGameChannelPlug.getGameId());
					rfGameChannelPlug.getMap().put("gameName", game.getGameName());
				}
			}
		}

		PageInfo<RfGameChannelPlug> pageInfo = new PageInfo<RfGameChannelPlug>(list);
		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfGameChannelPlug bean)
			throws Exception {
		if (bean.getChannelPlugId() != null) {
			bean = service.get(bean.getChannelPlugId());
			model.addAttribute("bean", bean);
		}

		List<String> softTypes = new ArrayList<String>();
		softTypes.add(ConstantsDb.toolSoftType());
		softTypes.add(ConstantsDb.softSoftType());

		List<RfGame> games = gameService.initQuery().andIn("softType", softTypes).findStopTrue();
		List<RfGameChannel> gameChannels = gameChannelService.initQuery().findStopTrue();
		model.addAttribute("games", games);
		model.addAttribute("gameChannels", gameChannels);

		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfGameChannelPlug bean)
			throws Exception {
		if (null == bean.getGameChannelId()) {
			throw new BusinessException("游戏渠道不能为空");
		}

		if (null == bean.getGameId()) {
			throw new BusinessException("游戏插件不能为空");
		}

		int count = service.initQuery().andEqualTo("gameChannelId", bean.getGameChannelId())
				.andEqualTo("gameId", bean.getGameId()).countDelTrue();
		if (count > 0) {
			throw new BusinessException("此渠道对应的插件已存在，请修改后再保存");
		}
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfGameChannelPlug bean)
			throws Exception {
		int count = service.initQuery().andEqualTo("gameChannelId", bean.getGameChannelId())
				.andEqualTo("gameId", bean.getGameId()).andNotEqualTo("channelPlugId", bean.getChannelPlugId())
				.countDelTrue();
		if (null == bean.getGameChannelId()) {
			throw new BusinessException("游戏渠道不能为空");
		}

		if (null == bean.getGameId()) {
			throw new BusinessException("游戏插件不能为空");
		}

		if (count > 0) {
			throw new BusinessException("此渠道对应的插件已存在，请修改后再保存");
		}
		service.update(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfGameChannelPlug bean)
			throws Exception {
		service.remove(request, bean);
	}

}
