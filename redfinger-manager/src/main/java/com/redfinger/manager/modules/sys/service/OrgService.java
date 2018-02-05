package com.redfinger.manager.modules.sys.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.SysOrg;
import com.redfinger.manager.common.domain.SysOrgExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.helper.ServiceAnnotation;
import com.redfinger.manager.common.helper.ServiceMethod;
import com.redfinger.manager.common.mapper.SysOrgMapper;
import com.redfinger.manager.common.utils.StringUtils;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "orgCode")
public class OrgService extends BaseService<SysOrg, SysOrgExample, SysOrgMapper> {

	@ServiceAnnotation(name = ServiceMethod.pre_save)
	public String preSave(HttpServletRequest request, SysOrg bean) {
		if (bean.getParentOrgCode() == null && "".equals(bean.getParentOrgCode())) {
			bean.setParentOrgCode("0");
		}
		if (this.get(bean.getOrgCode()) != null) {
			return "编码重复，请重新输入！";
		}
		return null;
	}

	@ServiceAnnotation(name = ServiceMethod.pre_delete)
	public String preDelete(HttpServletRequest request, SysOrg bean) {
		String[] idArray = StringUtils.split(bean.getIds(), ",");
		for (String id : idArray) {
			if (this.initQuery().andEqualTo("parentOrgCode", id).countDelTrue() > 0) {
				return "此记录有下级数据，不能删除！";
			}
		}
		return null;
	}

	@ServiceAnnotation(name = ServiceMethod.pre_stop)
	public String preStop(HttpServletRequest request, SysOrg bean) {
		String[] idArray = StringUtils.split(bean.getIds(), ",");
		for (String id : idArray) {
			if (this.initQuery().andEqualTo("parentOrgCode", id).countStopTrue() > 0) {
				return "此记录有下级数据，不能禁用！";
			}
		}
		return null;
	}

}
