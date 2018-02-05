package com.redfinger.manager.modules.refund.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfGoods;
import com.redfinger.manager.common.domain.RfRefundStandard;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.modules.goods.service.GoodsService;
import com.redfinger.manager.modules.refund.service.RefundStandardService;

@Controller
@RequestMapping(value = "/refund/standard")
public class RefundStandardController  extends BaseController {

	@Autowired
	private RefundStandardService service;
	@Autowired
	private GoodsService goodsService;
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		List<RfGoods> goodsList = goodsService.initQuery().andEqualTo("goodsType",  ConstantsDb.goodsSvip())
				.addOrderClause("reorder", "asc").findDelTrue();
				model.addAttribute("goodsList", goodsList);
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfRefundStandard> list(HttpServletRequest request, HttpServletResponse response, Model model, RfRefundStandard bean) throws Exception {
		List<RfRefundStandard> list = service.initQuery(bean)
				.andEqualTo("goodsId", bean.getGoodsId())
				.addOrderClause("reorder", "asc")
				.pageDelTrue(bean.getPage(), bean.getRows());
		if(null != list && list.size()>0){
			for (RfRefundStandard rfRefundStandard : list) {
				if(null != rfRefundStandard.getGoodsId()){
					rfRefundStandard.getMap().put("goodsName", goodsService.get(rfRefundStandard.getGoodsId()).getGoodsName());
				}
			}
		}
		PageInfo<RfRefundStandard> pageInfo = new PageInfo<RfRefundStandard>(list);
		return pageInfo;
	}
	
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfRefundStandard bean) throws Exception {
		if (bean.getStandardId() != null) {
			bean = service.get(bean.getStandardId());
			bean.setCharge(bean.getCharge()/100);
		}
		model.addAttribute("bean", bean);
		List<RfGoods> goodsList = goodsService.initQuery().andEqualTo("goodsType",  ConstantsDb.goodsSvip())
		.addOrderClause("reorder", "asc").findDelTrue();
		model.addAttribute("goodsList", goodsList);
		return this.toPage(request, response, model);
	}
	
	
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfRefundStandard bean) throws Exception {
		List<RfRefundStandard> list = service.initQuery().andEqualTo("goodsId", bean.getGoodsId()).
		andGreaterThanOrEqualTo("dayEnd", bean.getDayStart()).findStopTrue();
		if(list.size()>0){
			throw new BusinessException("此开始时间的配置已经存在！");
		}
		if(bean.getDayStart() >= bean.getDayEnd()){
			throw new BusinessException("开始天数不能大于或者等于结束天数！");
		}
		bean.setCharge(bean.getCharge() * 100 );
		service.save(request, bean);
	}
	
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfRefundStandard bean) throws Exception {
		bean.setCharge(bean.getCharge() * 100 );
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfRefundStandard bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfRefundStandard bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfRefundStandard bean) throws Exception {
		service.delete(request, bean);
	}
}
