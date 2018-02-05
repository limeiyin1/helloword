package com.redfinger.manager.modules.share.web;

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
import com.redfinger.manager.common.domain.RfShare;
import com.redfinger.manager.common.domain.RfShareExample;
import com.redfinger.manager.common.domain.RfShareExample.Criteria;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.FileUtils;
import com.redfinger.manager.modules.base.service.ConfigService;
import com.redfinger.manager.modules.share.service.ShareService;

@Controller
@RequestMapping(value = "/base/share")
public class ShareController extends BaseController {
	@Autowired
	ShareService shareService;
	@Autowired
	FileUtils fileUtil;
	@Autowired
	ConfigService sysConfigService;
	@Autowired
	private FilePathUtils filePathUtils;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		List<RfShare> shareList = shareService.initQuery().findStopTrue();
		model.addAttribute("shareList", shareList);
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfShare> list(HttpServletRequest request, HttpServletResponse response, Model model, RfShare bean,
			String begin, String end) throws Exception {
		Date beginTime = null;
		Date endTime = null;
		if (StringUtils.isNotBlank(begin)) {
			beginTime = DateUtils.parseDate(begin);
		}
		if (StringUtils.isNotBlank(end)) {
			endTime = DateUtils.addDays(DateUtils.parseDate(end), 1);
		}

		List<RfShare> list = shareService.initQuery(bean).andLike("shareTitle", bean.getShareTitle())
				.andEqualTo("client", bean.getClient()).andEqualTo("shareType", bean.getShareType())
				.andGreaterThanOrEqualTo("createTime", beginTime).andLessThan("createTime", endTime)
				.addOrderClause("createTime", "desc").addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		PageInfo<RfShare> pageInfo = new PageInfo<RfShare>(list);
		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfShare bean)
			throws Exception {
		if (bean.getShareId() == null) {
		} else {
			bean = shareService.get(bean.getShareId());
			model.addAttribute("bean", bean);
		}
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfShare bean)
			throws Exception {
		if (bean != null) {
			RfShareExample example = new RfShareExample();
			Criteria criteria = example.createCriteria();
			criteria.andClientEqualTo(bean.getClient()).andShareTypeEqualTo(bean.getShareType());
			List<RfShare> list = shareService.selectByExample(example);

			if (null == list || list.size() == 0) {
				// 分享链接来源客户端
				bean.setShareLinkUrl(bean.getShareLinkUrl());
				shareService.saveShare(request, bean);
			} else {
				// 遍历同样客户端和类型的数据
				for (RfShare rfShare : list) {
					// 如果有两种状态都正常的则存在重复数据.不允许添加
					if (StringUtils.equals(rfShare.getEnableStatus(), ConstantsDb.globalEnableStatusNomarl())
							&& StringUtils.equals(rfShare.getStatus(), ConstantsDb.globalStatusNomarl())) {
						throw new BusinessException("添加失败,已存在相同客户端,相同类型分享!");
					}
				}
				// 分享链接来源客户端
				bean.setShareLinkUrl(bean.getShareLinkUrl());
				shareService.saveShare(request, bean);
			}
		}
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfShare bean)
			throws Exception {
		// 分享链接来源客户端
		bean.setShareLinkUrl(bean.getShareLinkUrl());
		shareService.updateShare(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfShare bean)
			throws Exception {
		shareService.removeShare(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfShare bean)
			throws Exception {
		shareService.startShare(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfShare bean)
			throws Exception {
		shareService.stopShare(request, bean);
	}

	/**
	 * 上传文件
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "uploadImageRequest")
	@ResponseBody
	public JSONObject uploadImageRequest(@RequestParam(value = "fileUpdate", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		return this.uploadImage(file, request, response, model);
	}

	private JSONObject uploadImage(MultipartFile file, HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		try {
			String imageUrl = filePathUtils.getImageUrl() + "/mpic";
			String imageLinkUrl = filePathUtils.getImageLinkUrl() + "/mpic";

			log.info("图片下载文件链接地址:" + imageLinkUrl);
			log.info("是否存在上传文件，" + file.isEmpty() + "" + file.getSize());
			response.setContentType("text/html");
			Map<String, String> fileMap = null;
			if (!file.isEmpty()) {
				if (file.getSize() > 1 * 1024 * 1024) {
					fileMap = new HashMap<String, String>();
					fileMap.put("flag", "false");
					fileMap.put("msg", "文件大小不能超过1M");
				} else {
					fileMap = fileUtil.saveImage(file, imageUrl, imageLinkUrl);
					fileMap.put("flag", "true");
					log.info("filePath: " + fileMap.get("filePath"));
				}
			} else {
				fileMap = new HashMap<String, String>();
				fileMap.put("flag", "false");
			}
			/* return fileMap; */
			JSONObject jsonObject = asyncReturnResult(response, JSONObject.fromObject(fileMap));
			log.info(jsonObject.toString());
			return jsonObject;
		} catch (Exception e) {
			log.error("share upload file error:"+e.getMessage(), e);
			throw e;
		}
	}
}
