package com.redfinger.manager.modules.sys.service;

import java.util.List;
import java.util.Set;

import com.redfinger.manager.common.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.SysAdmin;
import com.redfinger.manager.common.domain.SysAdminRole;
import com.redfinger.manager.common.domain.SysAdminRoleExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.SysAdminRoleMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class AdminRoleService extends BaseService<SysAdminRole, SysAdminRoleExample, SysAdminRoleMapper> {
	public void insert(HttpServletRequest request, SysAdmin bean, String roleIds) throws Exception {
		// 先删除
		List<SysAdminRole> removeList = this.initQuery().andEqualTo("adminCode", bean.getAdminCode()).findAll();
		for (SysAdminRole adminRole : removeList) {
			this.remove(request, adminRole.getId());
		}
		// 再新增
		if (StringUtils.isNoneEmpty(roleIds)) {
			Set<String> result = Sets.newHashSet();
			List<String> permissions = Lists.newArrayList(StringUtils.split(roleIds, ","));
			for (String id : permissions) {
				result.add(id);
			}
			for (String id : result) {
				SysAdminRole userRole = new SysAdminRole();
				userRole.setRoleCode(id);
				userRole.setAdminCode(bean.getAdminCode());
				this.save(request, userRole);
			}
		}

	}

}
