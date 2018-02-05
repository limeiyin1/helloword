package com.redfinger.manager.modules.facility.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.ViewPadUser;
import com.redfinger.manager.common.domain.ViewPadUserExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.ViewPadUserMapper;
import com.redfinger.manager.common.utils.Collections3;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "padId")
public class ViewPadUserService extends BaseService<ViewPadUser, ViewPadUserExample, ViewPadUserMapper> {
	
	@Autowired
	private ViewPadUserMapper viewPadUserMapper;
	
	/**
	 * 根据padCode获取pad,因为padCode有唯一约束，所以只能是返回一条记录
	 * 
	 * @param padCode
	 * @return ViewPadUser
	 */
	@Transactional(readOnly = true)
	public ViewPadUser getPadCode(String padCode) {
		List<ViewPadUser> list = this.initQuery()
				                     .andEqualTo("padCode", padCode)
				                     .singleDelTrue();
		if (!Collections3.isEmpty(list)) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	public int countByExample(ViewPadUserExample example){
		if(example != null){
			return viewPadUserMapper.countByExample(example);
		}
		return 0;
	}

}
