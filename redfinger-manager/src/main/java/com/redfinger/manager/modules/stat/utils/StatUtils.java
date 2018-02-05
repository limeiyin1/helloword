package com.redfinger.manager.modules.stat.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.stat.base.StatDomain;

public class StatUtils {

	@SuppressWarnings("unchecked")
	public static void transferDateResultForNumber(StatDomain bean, Map<String, Object> map, String labelName,
			String numberName) {
		List<String> label = (List<String>) map.get(labelName);
		List<Integer> number = (List<Integer>) map.get(numberName);
		Date beginDate = DateUtils.parseDate(bean.getBegin());
		Date endDate = DateUtils.parseDate(bean.getEnd());
		if (bean.getType().equals("day")) {
			endDate = DateUtils.addDays(endDate, 1);
		}
		List<String> _label = Lists.newArrayList();
		List<Integer> _number = Lists.newArrayList();
		for (Date date = beginDate; date.before(endDate); date = add(bean, date)) {
			_number.add(0);
			if (bean.getType().equals("month")) {
				_label.add(DateUtils.formatDate(date).substring(0, 7));
			} else {
				_label.add(DateUtils.formatDate(date));
			}
		}
		for (int i = 0; i < _label.size(); i++) {
			for (int j = 0; j < label.size(); j++) {
				if (_label.get(i).equals(label.get(j))) {
					_number.remove(i);
					_number.add(i, number.get(j));

				}
			}
		}
		map.put(labelName, _label);
		map.put(numberName, _number);
	}

	private static Date add(StatDomain bean, Date date) {
		if (bean.getType().equals("month")) {
			return DateUtils.addMonths(date, 1);
		} else if (bean.getType().equals("day")) {
			return DateUtils.addDays(date, 1);
		} else if (bean.getType().equals("week")) {
			return DateUtils.addWeeks(date, 1);
		} else {
			return date;
		}
	}

	/**
	 * 
	 * @Title: autoStatDate
	 * @Description: 自动统计数据
	 * @param @param type 统计类型（按天、周、月统计）
	 * @param @param begin 统计开始日期
	 * @param @param end 统计结束日期
	 * @param @param listDate 要统计的list数据，结构必须是(StatDomain==> number：数据 label：日期)
	 * @param @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@SafeVarargs
	public static Map<String, Object> autoStatDate(String type, String begin, String end, List<?>... listDate) {
		// 设计返回Map
		int all = 0;
		Map<String, Object> maps = Maps.newHashMap();
		List<Integer> numberAll = Lists.newArrayList();
		List<String> label = Lists.newArrayList();
		maps.put("number", numberAll);
		maps.put("label", label);
		try {
			// 根据type初始化Map（即初始化number and label）容器
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			Date sta_date = sdf.parse(begin);
			Date end_date = sdf.parse(end);

			cal.setTime(sta_date);
			while (cal.getTime().getTime() <= end_date.getTime()) {
				if (type != null && type != "") {

					label.add(sdf.format(cal.getTime()));

					if ("day".equals(type)) {

						cal.add(Calendar.DAY_OF_MONTH, 1);

					} else if ("week".equals(type)) {

						cal.add(Calendar.DAY_OF_MONTH, 7);

					} else if ("month".equals(type)) {

						cal.add(Calendar.MONTH, 1);
					}
				}
			}
			// 遍历List<StatDomain>...listDate，添加数据到numberAll 容器中;
			for (List<?> list_sd : listDate) {
				List<Integer> number = Lists.newArrayList();
				for (int i = 0; i < label.size(); i++) {
					number.add(0);
				}// 初始化完毕

				for (Object domain : list_sd) {
					String key = ((StatDomain) domain).getLabel();
					int count = ((StatDomain) domain).getNumber();
					all += count;
					int mark = 0;// 记录下标
					for (int i = 0; i < label.size() - 1; i++) {
						if (sdf.parse(key).getTime() >= sdf.parse(label.get(label.size() - 1)).getTime()) {
							mark = label.size() - 1;
							break;
						}
						if (sdf.parse(key).getTime() >= sdf.parse(label.get(i)).getTime()
								&& sdf.parse(key).getTime() < sdf.parse(label.get(i + 1)).getTime()) {
							mark = i;
							break;
						}
					}
					number.set(mark, number.get(mark) + count);
				}
				numberAll.addAll(number);
			}
			maps.put("numAll", all);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return maps;
	}
}
