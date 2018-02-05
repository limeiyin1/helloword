package com.redfinger.manager.modules.stat.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.ResIp;
import com.redfinger.manager.common.domain.ResIpExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.ResIpMapper;
import com.redfinger.manager.common.mapper.StatMemberMapper;
import com.redfinger.manager.modules.stat.base.StatDomain;



@Transactional
@Service
@PrimaryKeyAnnotation(field = "ip")
public class StatMemberService extends BaseService<ResIp, ResIpExample, ResIpMapper> {

	@Autowired
	StatMemberMapper mapper;

	@Transactional(readOnly = true)
	public Map<String, Object> statIp(StatDomain bean) {
		Map<String, Object> map = Maps.newHashMap();
		List<Integer> number = Lists.newArrayList();
		List<String> label = Lists.newArrayList();
		map.put("subTitle", "客户注册IP地址归属地");
		map.put("number", number);
		map.put("label", label);
		List<StatDomain> list = mapper.statIp(bean);
		for (StatDomain domain : list) {
		    number.add(domain.getNumber());
			if (domain.getLabel() == null ||"".equals(domain.getLabel())) {
				label.add("无效IP");
			} else {
				label.add(domain.getLabel());
			}	
		}
		return map;
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> statMemberAge() {
		Map<String, Object> map = Maps.newHashMap();
		List<Integer> number = Lists.newArrayList(0, 0, 0, 0, 0, 0, 0, 0, 0);
		List<String> label = Lists.newArrayList();
		label.add("20岁以下");
		label.add("20-24岁");
		label.add("25-29岁");
		label.add("30-34岁");
		label.add("35-39岁");
		label.add("40-44岁");
		label.add("45-49岁");
		label.add("50岁以上");
		label.add("[未知]");
		map.put("subTitle", "");
		map.put("number", number);
		map.put("label", label);
		List<StatDomain> list = mapper.statMemberAge();
		for (StatDomain domain : list) {
			int pos = 0;
			if (StringUtils.isEmpty(domain.getLabel())) {
				pos = 8;
			} else {
				int age = Integer.parseInt(domain.getLabel());
				if (age < 20) {
					pos = 0;
				} else if (age >= 20 && age <= 24) {
					pos = 1;
				} else if (age >= 25 && age <= 29) {
					pos = 2;
				} else if (age >= 30 && age <= 34) {
					pos = 3;
				} else if (age >= 35 && age <= 39) {
					pos = 4;
				} else if (age >= 40 && age <= 44) {
					pos = 5;
				} else if (age >= 45 && age <= 49) {
					pos = 6;
				} else if (age >= 50) {
					pos = 7;
				}
			}
			int oldNumber = number.get(pos);
			int newNumber = oldNumber + domain.getNumber();
			number.remove(pos);
			number.add(pos, newNumber);
		}
		return map;
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> statMemberSex() {
		Map<String, Object> map = Maps.newHashMap();
		List<Integer> number = Lists.newArrayList();
		List<String> label = Lists.newArrayList();
		map.put("subTitle", "");
		map.put("number", number);
		map.put("label", label);
		String man=ConstantsDb.globalGenderMen();
		String woman=ConstantsDb.globalGenderWomen();
		List<StatDomain> list = mapper.statMemberSex();
		for (StatDomain domain : list) {
			number.add(domain.getNumber());
			if(StringUtils.isEmpty(domain.getLabel())){
				label.add("[未知]");
			}else if(domain.getLabel().equals(man)){
				label.add("男");
			}else if(domain.getLabel().equals(woman)){
				label.add("女");
			}
		}
		return map;
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> statMemberMobileCarriers() {
		Map<String, Object> map = Maps.newHashMap();
		List<Integer> number = Lists.newArrayList();
		List<String> label = Lists.newArrayList();
		map.put("subTitle", "");
		map.put("number", number);
		map.put("label", label);
		String mobile=ConstantsDb.globalMobileCarriersMobile();
		String telecom=ConstantsDb.globalMobileCarriersTelecom();
		String unicom=ConstantsDb.globalMobileCarriersUnicom();
		List<StatDomain> list = mapper.statMemberMobileCarriers();
		for (StatDomain domain : list) {
			number.add(domain.getNumber());
			if(StringUtils.isEmpty(domain.getLabel())){
				label.add("[未知]");
			}else if(domain.getLabel().equals(mobile)){
				label.add("中国移动");
			}else if(domain.getLabel().equals(telecom)){
				label.add("中国电信");
			}else if(domain.getLabel().equals(unicom)){
				label.add("中国联通");
			}
		}
		return map;
	}

}
