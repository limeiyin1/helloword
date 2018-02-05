package com.redfinger.manager.modules.stat.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.ToolsPad;
import com.redfinger.manager.common.domain.ToolsPadExample;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.StatToolsPadMapper;
import com.redfinger.manager.common.mapper.ToolsPadMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "padIp")
public class ToolsPadService extends BaseService<ToolsPad, ToolsPadExample, ToolsPadMapper> {
	@Autowired
	ToolsPadMapper mapper;
    @Autowired
    StatToolsPadMapper statToolsPadMapper;
    
	public void saveIp(HttpServletRequest request, String padIps) throws Exception {
		List<ToolsPad> list = Lists.newArrayList();
		List<String> strlist=Lists.newLinkedList();
		mapper.deleteByExample(new ToolsPadExample());
		if (padIps == null) {
			throw new BusinessException("请输入正确的参数！");
		}
		padIps = padIps.replaceAll(" ", "");
		String[] str = padIps.split("\n");
		for (String string : str) {
			string = string.replaceAll("\r", "");
			if(!strlist.contains(string)){
				strlist.add(string);
			}
			
		}
		for (String padIp : strlist) {
			ToolsPad bean = new ToolsPad();
			bean.setPadIp(padIp);
			list.add(bean);
		}
		mapper.insertBatch(list);
	}
	@Transactional(readOnly = true)
	public List<ToolsPad> selectToolsPad() {
		// TODO Auto-generated method stub
		return statToolsPadMapper.selectToolsPad();
	}
  
	
}
