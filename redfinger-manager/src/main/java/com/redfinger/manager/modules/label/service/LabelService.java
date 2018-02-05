package com.redfinger.manager.modules.label.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfLabel;
import com.redfinger.manager.common.domain.RfLabelExample;
import com.redfinger.manager.common.domain.RfPad;
import com.redfinger.manager.common.domain.RfPadLabel;
import com.redfinger.manager.common.domain.RfPadLabelExample;
import com.redfinger.manager.common.domain.RfUserLabel;
import com.redfinger.manager.common.domain.RfUserLabelExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfLabelMapper;
import com.redfinger.manager.common.mapper.RfPadLabelMapper;
import com.redfinger.manager.common.mapper.RfUserLabelMapper;
import com.redfinger.manager.common.utils.YesOrNoStatus;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "labelId")
public class LabelService extends BaseService<RfLabel, RfLabelExample, RfLabelMapper> {

    @Autowired
    private RfLabelMapper mapper;
    @Autowired
    private RfUserLabelMapper userLabelMapper;
    @Autowired
    private RfPadLabelMapper padLabelMapper;
    
    /**
     * 根据标签名称查询标签数量
     * @param labelName
     * @return
     * @throws Exception
     */
    public int selectCountByLabelName(String labelName) throws Exception {
    	RfLabelExample example = new RfLabelExample();
    	example.createCriteria().andLabelNameEqualTo(labelName);
    	return mapper.countByExample(example);
    }
    
    /**
     * 根据userId删除用户标签
     * @param userIds
     * @return
     * @throws Exception
     */
    public int deleteByUserId(List<Integer> userIds) throws Exception {
    	RfUserLabelExample example = new RfUserLabelExample();
    	example.createCriteria().andUserIdIn(userIds);
    	return userLabelMapper.deleteByExample(example);
    }
    
    /**
     * 根据PadId删除设备标签
     * @param padIds
     * @return
     * @throws Exception
     */
    public int deleteByPadId(List<Integer> padIds)  throws Exception {
    	RfPadLabelExample example = new RfPadLabelExample();
    	example.createCriteria().andPadIdIn(padIds);
    	return padLabelMapper.deleteByExample(example);
    }
    
    /**
     * 根据标签id，用户id保存用户与标签的关系
     * @param labelId
     * @param userIds
     * @param creater
     * @return
     */
    public int saveUserLabel(Integer labelId,List<Integer> userIds,String creater){
    	List<RfUserLabel> list = new ArrayList<RfUserLabel>();
    	for(Integer userId : userIds){
    		RfUserLabel userLabel = new RfUserLabel();
        	userLabel.setLabelId(labelId);
        	userLabel.setUserId(userId);
        	userLabel.setCreater(creater);
        	userLabel.setCreateTime(new Date());
        	userLabel.setStatus(YesOrNoStatus.YES);
        	userLabel.setEnableStatus(YesOrNoStatus.YES);
        	list.add(userLabel);
    	}
    	return userLabelMapper.insertBatch(list);
    }
    
    /**
     * 根据标签id、设备保存标签和设备之间关系
     * @param labelId
     * @param padList
     * @param creater
     * @param padCode
     * @return
     */
    public int savePadLabel(Integer labelId,List<RfPad> padList,String creater){
    	List<RfPadLabel> list = new ArrayList<RfPadLabel>();
    	for(RfPad pad : padList){
    		RfPadLabel padLabel = new RfPadLabel();
        	padLabel.setLabelId(labelId);
        	padLabel.setPadId(pad.getPadId());
        	padLabel.setPadCode(pad.getPadCode());
        	padLabel.setCreater(creater);
        	padLabel.setCreateTime(new Date());
        	padLabel.setStatus(YesOrNoStatus.YES);
        	padLabel.setEnableStatus(YesOrNoStatus.YES);
        	list.add(padLabel);
    	}
    	return padLabelMapper.insertBatch(list);
    }
   
    /**
     * 根据userId查询用户标签关系
     * @param userId
     * @return
     */
    public List<RfUserLabel> selectByUserId(Integer userId){
    	RfUserLabelExample example = new RfUserLabelExample();
    	example.createCriteria().andUserIdEqualTo(userId);
    	return userLabelMapper.selectByExample(example);
    }
    
    /**
     * 根据padId查询设备标签关系
     * @param userId
     * @return
     */
    public List<RfPadLabel> selectByPadId(Integer padId){
    	RfPadLabelExample example = new RfPadLabelExample();
    	example.createCriteria().andPadIdEqualTo(padId);
    	return padLabelMapper.selectByExample(example);
    }
}
