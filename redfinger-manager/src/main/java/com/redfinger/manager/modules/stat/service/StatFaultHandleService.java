package com.redfinger.manager.modules.stat.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfFaultHandle;
import com.redfinger.manager.common.domain.RfFaultHandleExample;
import com.redfinger.manager.common.domain.SysAdminRole;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfFaultHandleMapper;
import com.redfinger.manager.common.mapper.StatFaultMapper;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.stat.base.StatDomain;
import com.redfinger.manager.modules.sys.service.AdminRoleService;
import com.redfinger.manager.modules.sys.service.AdminService;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "faultHandleId")
public class StatFaultHandleService extends BaseService<RfFaultHandle, RfFaultHandleExample, RfFaultHandleMapper> {

	@Autowired
	StatFaultMapper mapper;
	@Autowired
	AdminRoleService adminRoleService;
	@Autowired
	AdminService adminService;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Map<String, Object> statFaultAcceptByRole(StatDomain bean) {
		if (bean == null || StringUtils.isEmpty(bean.getType()) || StringUtils.isEmpty(bean.getBegin()) || StringUtils.isEmpty(bean.getEnd())) {
			throw new BusinessException("查询条件不能为空！");
		}
		Date beginDate = DateUtils.parseDate(bean.getBegin());
		Date endDate = DateUtils.parseDate(bean.getEnd());
		if (beginDate.after(endDate)) {
			throw new BusinessException("开始日期不能大于结束日期！");
		}
		Map<String, Object> map = Maps.newHashMap();
		List<Integer> number = Lists.newArrayList();
		List<String> label = Lists.newArrayList();
		map.put("subTitle", bean.getSubTitle());
		map.put("number", number);
		map.put("label", label);
		List<SysAdminRole> adminRoleList = adminRoleService.initQuery().andIn("roleCode", bean.getWhere().split(",")).findAll();
		if (!Collections3.isEmpty(adminRoleList)) {
			List<String> adminCodeList = Collections3.extractToList(adminRoleList, "adminCode");
			StringBuffer sb = new StringBuffer();
			sb.append("admin_user_id in (");
			for (String adminCode : adminCodeList) {
				sb.append("'" + adminCode + "',");
			}
			String where = sb.toString();
			where = StringUtils.removeEnd(where, ",");
			where = where + ")";
			bean.setWhere(where);
			// 查询
			List<StatDomain> list = mapper.statFaultAcceptByRole(bean);
			for (StatDomain domain : list) {
				number.add(domain.getNumber());
				label.add(adminService.get(domain.getLabel()).getAdminName());
			}
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Map<String, Object> statFualtSolveByRole(StatDomain bean) {
		if (bean == null || StringUtils.isEmpty(bean.getType()) || StringUtils.isEmpty(bean.getBegin()) || StringUtils.isEmpty(bean.getEnd())) {
			throw new BusinessException("查询条件不能为空！");
		}
		Date beginDate = DateUtils.parseDate(bean.getBegin());
		Date endDate = DateUtils.parseDate(bean.getEnd());
		if (beginDate.after(endDate)) {
			throw new BusinessException("开始日期不能大于结束日期！");
		}
		Map<String, Object> map = Maps.newHashMap();
		List<Integer> number = Lists.newArrayList();
		List<String> label = Lists.newArrayList();
		map.put("subTitle", bean.getSubTitle());
		map.put("number", number);
		map.put("label", label);
		List<SysAdminRole> adminRoleList = adminRoleService.initQuery().andIn("roleCode", bean.getWhere().split(",")).findAll();
		if (!Collections3.isEmpty(adminRoleList)) {
			List<String> adminCodeList = Collections3.extractToList(adminRoleList, "adminCode");
			StringBuffer sb = new StringBuffer();
			sb.append("admin_user_id in (");
			for (String adminCode : adminCodeList) {
				sb.append("'" + adminCode + "',");
			}
			String where = sb.toString();
			where = StringUtils.removeEnd(where, ",");
			where = where + ")";
			// 查询已经完成
			bean.setWhere(where + "and is_solve='1'");
			List<StatDomain> solveList = mapper.statFualtSolveByRole(bean);
			// 查询移交
			bean.setWhere(where + "and is_solve='0'");
			List<StatDomain> giveupList = mapper.statFualtSolveByRole(bean);

			Set<String> labelSet = Sets.newHashSet();
			labelSet.addAll(Collections3.extractToList(solveList, "label"));
			labelSet.addAll(Collections3.extractToList(giveupList, "label"));
			label.addAll(labelSet);
			// 添加已完成
			for (String s : label) {
				int n = 0; 
				for (StatDomain domain : solveList) {
					if(domain.getLabel().equals(s)){
						n = domain.getNumber();
						break;
					}
				}
				number.add(n);
			}
			//添加移交
			for (String s : label) {
				int n = 0; 
				for (StatDomain domain : giveupList) {
					if(domain.getLabel().equals(s)){
						n = domain.getNumber();
						break;
					}
				}
				number.add(n);
			}
			for(int i=0;i<label.size();i++){
				String code=label.get(i);
				label.remove(i);
				label.add(i,adminService.get(code).getAdminName());
			}
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Map<String, Object> statFualtFixTimeByRole(StatDomain bean) {
		if (bean == null || StringUtils.isEmpty(bean.getType()) || StringUtils.isEmpty(bean.getBegin()) || StringUtils.isEmpty(bean.getEnd())) {
			throw new BusinessException("查询条件不能为空！");
		}
		Date beginDate = DateUtils.parseDate(bean.getBegin());
		Date endDate = DateUtils.parseDate(bean.getEnd());
		if (beginDate.after(endDate)) {
			throw new BusinessException("开始日期不能大于结束日期！");
		}
		Map<String, Object> map = Maps.newHashMap();
		List<Integer> number = Lists.newArrayList();
		List<String> label = Lists.newArrayList();
		map.put("subTitle", bean.getSubTitle());
		map.put("number", number);
		map.put("label", label);
		List<SysAdminRole> adminRoleList = adminRoleService.initQuery().andIn("roleCode", bean.getWhere().split(",")).findAll();
		if (!Collections3.isEmpty(adminRoleList)) {
			List<String> adminCodeList = Collections3.extractToList(adminRoleList, "adminCode");
			StringBuffer sb = new StringBuffer();
			sb.append("admin_user_id in (");
			for (String adminCode : adminCodeList) {
				sb.append("'" + adminCode + "',");
			}
			String where = sb.toString();
			where = StringUtils.removeEnd(where, ",");
			where = where + ")";
			bean.setWhere(where);
			// 查询
			List<StatDomain> list = mapper.statFualtFixTimeByRole(bean);
			for (StatDomain domain : list) {
				number.add(domain.getNumber());
				label.add(adminService.get(domain.getLabel()).getAdminName());
			}
		}
		return map;
	}
}
