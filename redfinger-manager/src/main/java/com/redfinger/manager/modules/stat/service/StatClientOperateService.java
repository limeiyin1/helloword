package com.redfinger.manager.modules.stat.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.mapper.StatClientOperateMapper;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.modules.base.service.DictService;
import com.redfinger.manager.modules.stat.base.StatDomain;
import com.redfinger.manager.modules.stat.utils.StatUtils;

/**
 * 用户客户端操作统计Service
 * 
 * @ClassName: StatClientOperateService
 * @author tluo
 * @date 2016年2月19日 下午3:44:11
 */
@Transactional
@Service
public class StatClientOperateService {

	@Autowired
	StatClientOperateMapper mapper;
	@Autowired
	DictService dictService;

	/**
	 * 统计指定时间段内每点钟用户登录数量（24小时）
	 * 
	 * @Title: statUserLoginTo24
	 * @return Map<String,Object> 返回类型
	 * @param bean
	 * @return
	 */
	@Transactional(readOnly = true)
	public Map<String, Object> statUserLoginTo24(StatDomain bean) {
		if (StringUtils.isEmpty(bean.getType()) || StringUtils.isEmpty(bean.getEnd())
				|| StringUtils.isEmpty(bean.getBegin())) {
			throw new BusinessException("查询条件不能为空");
		}
		int all = 0;
		Map<String, Object> maps = Maps.newHashMap();
		List<String> label = Arrays.asList("0点", "1点", "2点", "3点", "4点", "5点", "6点", "7点", "8点", "9点", "10点", "11点",
				"12点", "13点", "14点", "15点", "16点", "17点", "18点", "19点", "20点", "21点", "22点", "23点");
		List<Integer> number = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		maps.put("number", number);
		maps.put("label", label);
		maps.put("numAll", all);
		List<StatDomain> list = mapper.statUserLoginTo24(bean);
		if (list == null || list.size() == 0) {
			return maps;
		} else {
			for (StatDomain dto : list) {
				int mark = Integer.parseInt(dto.getLabel());
				number.set(mark, dto.getNumber());
				all += dto.getNumber();
			}
		}
		maps.put("numAll", all);
		return maps;
	}

	/**
	 * 统计指定时间段内某个按钮的操作次数（按月、周、天统计）
	 * 
	 * @Title: statUIButton
	 * @return Map<String,Object> 返回类型
	 * @param bean
	 * @return
	 */
	@Transactional(readOnly = true)
	public Map<String, Object> statUIButton(StatDomain bean) {
		if (StringUtils.isEmpty(bean.getType()) || StringUtils.isEmpty(bean.getEnd())
				|| StringUtils.isEmpty(bean.getBegin())) {
			throw new BusinessException("查询条件不能为空");
		}
		if ("".equals(bean.getWhere())) {
			bean.setWhere(null);
		}
		return StatUtils.autoStatDate(bean.getType(), bean.getBegin(), bean.getEnd(), mapper.statUIButton(bean));
	}
	/**
	 * 饼图统计用户按钮操作
	 * 
	 * @Title: statBtButton
	 * @return Map<String,Object> 返回类型
	 * @param bean
	 * @return
	 */
	public Map<String, Object> statBtButton(StatDomain bean) {
		Integer all = 0;
		if (StringUtils.isEmpty(bean.getType()) || StringUtils.isEmpty(bean.getEnd())
				|| StringUtils.isEmpty(bean.getBegin())) {
			throw new BusinessException("查询条件不能为空");
		}
		List<StatDomain> list = mapper.statBtButton(bean);
		Map<String, Object> map = Maps.newHashMap();
		List<Integer> numberList = Lists.newArrayList();
		List<String> labelList = Lists.newArrayList();
		map.put("subTitle", bean.getSubTitle());
		map.put("number", numberList);
		map.put("label", labelList);
		// 填充数据
		for (StatDomain domain : list) {
			labelList.add(dictService.initQuery().andEqualTo("dictCategory", "ui.button_stat")
					.andEqualTo("dictValue", domain.getLabel()).singleStopTrue().get(0).getDictName());
			numberList.add(domain.getNumber());
			all += domain.getNumber();
		}
		map.put("numAll", all);
		return map;
	}

	/**
	 * 统计新用户前20步操作
	 * 
	 * @Title: statNewUserUIButton
	 * @return Map<String,Object> 返回类型
	 * @param bean
	 * @return
	 */
	@Transactional(readOnly = true)
	public Map<String, Object> statNewUserUIButton(StatDomain bean) {
		if (StringUtils.isEmpty(bean.getEnd()) || StringUtils.isEmpty(bean.getBegin())) {
			throw new BusinessException("查询条件不能为空");
		}
		if ("".equals(bean.getWhere())) {
			bean.setWhere(null);
		}
		int all = 0;
		List<StatDomain> list = mapper.statNewUserUIButton(bean);
		Map<String, Object> maps = Maps.newHashMap();
		List<Integer> numberList = Lists.newArrayList();
		List<String> labelList = Lists.newArrayList();
		maps.put("number", numberList);
		maps.put("label", labelList);
		if (list != null && list.size() != 0) {
			for (StatDomain s : list) {
				labelList.add(dictService.initQuery().andEqualTo("dictCategory", "ui.button_stat")
						.andEqualTo("dictValue", s.getLabel()).singleStopTrue().get(0).getDictName());
				numberList.add(s.getNumber());
				all += s.getNumber();
			}
		}
		maps.put("numAll", all);
		return maps;
	}
}
