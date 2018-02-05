package com.redfinger.manager.modules.facility.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfControl;
import com.redfinger.manager.common.domain.RfControlRelation;
import com.redfinger.manager.common.domain.RfControlRelationExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfControlRelationMapper;
import com.redfinger.manager.common.utils.StringUtils;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "relationId")
public class ControlRelationService extends BaseService<RfControlRelation, RfControlRelationExample, RfControlRelationMapper> {
		
	@Autowired
	RfControlRelationMapper mapper;
	
	/**获取列表(带查询条件)
	 * @param bean
	 * @param userControlName
	 * @param padControlName
	 * @param controlService
	 * @return
	 */
	public PageInfo<RfControlRelation> list(RfControlRelation bean,String userControlName,String padControlName,ControlService controlService,VideoService videoService){
		List<Integer> control1=null;
		List<Integer> control2=null;
		if(StringUtils.isNoneBlank(userControlName)){
			List<RfControl> rfControls1 = controlService.initQuery().andEqualTo("controlType",ConstantsDb.rfControlControlTypeUser()).andLike("controlName", userControlName).findStopTrue();
			control1 = new ArrayList<>();
			for(RfControl con : rfControls1){
				control1.add(con.getControlId());
			}
		}
		if(StringUtils.isNotBlank(padControlName)){
			List<RfControl> rfControls2 = controlService.initQuery().andEqualTo("controlType", ConstantsDb.rfControlControlTypeDevice()).andLike("controlName", padControlName).findStopTrue();
			control2 = new ArrayList<>();
			for(RfControl con : rfControls2){
				control2.add(con.getControlId());
			}
		}
		
		List<RfControlRelation> list = this.initQuery(bean)
				.andIn("userControlId", control1)
				.andIn("padControlId", control2)
				.addOrderForce(bean.getSort(), bean.getOrder()).pageAll(bean.getPage(), bean.getRows());
				//.pageDelTrue(bean.getPage(), bean.getRows());
		//List<ControlRelationDto> controlRelationDtos = new ArrayList<>();
		for(RfControlRelation li : list){
			/*ControlRelationDto controlRelationDto = new ControlRelationDto(li.getRelationId(),li.getUserControlId() 
			, null,li.getPadControlId() , null,li.getManageControlId() , null,li.getPadVideoId() 
			,null,li.getUserVideoId() , null,li.getStatus() ,li.getCreater() ,li.getCreateTime() ,li.getModifier() ,li.getReorder() ,li.getRemark());*/
			li.setUserControlName(controlService.get(li.getUserControlId()).getControlName());
			li.setPadControlName(controlService.get(li.getPadControlId()).getControlName());
			li.setManageControlName(controlService.get(li.getManageControlId()).getControlName());
			li.setUserVideoName(videoService.get(li.getUserVideoId()).getVideoName());
			li.setPadVideoName(videoService.get(li.getPadVideoId()).getVideoName());
			//controlRelationDtos.add(controlRelationDto);
		}
		PageInfo<RfControlRelation> pageInfo = new PageInfo<RfControlRelation>(list);
		
		return pageInfo;
	}
	
}
