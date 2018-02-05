package com.redfinger.manager.modules.goods.web;

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
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.ShopPackage;
import com.redfinger.manager.common.domain.ShopPackageCode;
import com.redfinger.manager.common.mapper.ShopPackageCodeMapper;
import com.redfinger.manager.common.mapper.ShopPackageMapper;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.game.service.GameCategoryService;
import com.redfinger.manager.modules.goods.service.GoodsShopPackageCodeService;
import com.redfinger.manager.modules.goods.service.GoodsShopPackageService;
import com.redfinger.manager.modules.member.service.UserService;

@Controller
@RequestMapping(value = "/goods/shopPackage")
public class GoodsShopPackageController extends BaseController {

	@Autowired
	GoodsShopPackageService service;
	@Autowired
	GoodsShopPackageCodeService codeService;
	@Autowired
	GameCategoryService categoryService;
	@Autowired
	ShopPackageMapper mapper;
	@Autowired
	ShopPackageCodeMapper codemapper;
	@Autowired
	UserService userservice;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<ShopPackage> list(HttpServletRequest request, HttpServletResponse response, Model model, ShopPackage bean) throws Exception {

		List<ShopPackage> list = service.initQuery(bean).andLike("name", bean.getName()).andGreaterThanOrEqualTo("createTime", bean.getBeginTime()).andLessThan("createTime", bean.getEndTime())
				.andEqualTo(ShopPackage.FD_STATUS, "1").addOrderClause("reorder", "asc").addOrderForce(bean.getSort(), bean.getOrder()).pageDelTrue(bean.getPage(), bean.getRows());
		PageInfo<ShopPackage> pageInfo = new PageInfo<ShopPackage>(list);

		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, ShopPackage bean, String beginTimeStr, String endTimeStr) throws Exception {
		if (bean.getId() == null) {

		} else {
			bean = service.get(bean.getId());
		}
		model.addAttribute("bean", bean);
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, ShopPackage bean) throws Exception {
		service.isExist(bean.getName());// 判断礼包是否已经存在
		if (!"discount".equals(bean.getCategory())) {
			bean.setOriginalPrice(-1);
		}
		bean.setBeginTime(DateUtils.parseDate(bean.getBeginTimeStr()));
		bean.setEndTime(DateUtils.parseDate(bean.getEndTimeStr()));
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, ShopPackage bean) throws Exception {
		bean.setBeginTime(DateUtils.parseDate(bean.getBeginTimeStr()));
		bean.setEndTime(DateUtils.parseDate(bean.getEndTimeStr()));
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, ShopPackage bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, ShopPackage bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, ShopPackage bean) throws Exception {
		service.delete(request, bean);
	}

	@RequestMapping(value = "remove")
	public void remove(HttpServletRequest request, HttpServletResponse response, Model model, ShopPackage packagebean, ShopPackageCode bean, String ids, String id) throws Exception {
		bean.setPackageId(Integer.parseInt(id));
		codeService.remove(request, bean);
	}
	
	@RequestMapping(value = "modify")
	public void modify(HttpServletRequest request, HttpServletResponse response, Model model, ShopPackage packagebean, ShopPackageCode bean, String ids, String id,String begin,String end) throws Exception {
		bean.setPackageId(Integer.parseInt(id));
		bean.setBeginTime(DateUtils.parseDate(begin));
		bean.setEndTime(DateUtils.parseDate(end));
		codeService.modify(request, bean);
	}

	@RequestMapping(value = "formCode")
	public String formCode(HttpServletRequest request, HttpServletResponse response, Model model, ShopPackage bean) throws Exception {
		if (bean.getId() == null) {

		} else {
			bean = service.get(bean.getId());
			model.addAttribute("bean", bean);
		}
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "batch")
	public void batch(HttpServletRequest request, HttpServletResponse response, Model model, ShopPackage bean, String codes,String check,Integer num) throws Exception {
		codeService.updateCodes(request, bean, codes,check,num);
	}

	@RequestMapping(value = "look")
	public String look(HttpServletRequest request, HttpServletResponse response, Model model, ShopPackage bean) {
		bean = service.get(bean.getId());
		model.addAttribute("bean", bean);
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "codelist")
	public PageInfo<ShopPackageCode> codelist(HttpServletRequest request, HttpServletResponse response, Model model, ShopPackage bean, ShopPackageCode codebean, String code, String userphone,String buyFlag) {
		RfUser user = new RfUser();
		if(StringUtils.isNotBlank(userphone)){
			List<RfUser> userlist = userservice.initQuery().andEqualTo("userMobilePhone", userphone).findDelTrue();
			if(userlist.size() > 0){
				user = userlist.get(0);
			}else {
				user.setUserId(-1);
			}
		}
		List<ShopPackageCode> codelist = codeService.initQuery().andLike("code", code).andEqualTo("buyFlag", buyFlag).andEqualTo("userId", user.getUserId()).andEqualTo("packageId", bean.getId())
						.addOrderForce(codebean.getSort(), codebean.getOrder()).pageAll(codebean.getPage(), codebean.getRows());
		for (ShopPackageCode shopPackageCode : codelist) {
			if (shopPackageCode.getUserId() == null || "".equals(shopPackageCode.getUserId())) {
				shopPackageCode.getMap().put("userPhone", "");
			} else {
				shopPackageCode.getMap().put("userPhone", userservice.load(shopPackageCode.getUserId()).getUserMobilePhone());
			}
		}
		PageInfo<ShopPackageCode> pageInfo = new PageInfo<ShopPackageCode>(codelist);
		return pageInfo;
	}

}
