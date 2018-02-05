package com.redfinger.manager.modules.tasks.service;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.SysAdmin;
import com.redfinger.manager.common.domain.TkTask;
import com.redfinger.manager.common.domain.TkTaskAward;
import com.redfinger.manager.common.domain.TkTaskExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.TkTaskAwardMapper;
import com.redfinger.manager.common.mapper.TkTaskMapper;
import com.redfinger.manager.common.utils.AwardType;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.modules.tasks.web.dto.ViewTaskAward;


@Transactional
@Service
@PrimaryKeyAnnotation(field = "taskId")
public class TkTaskService extends BaseService<TkTask, TkTaskExample, TkTaskMapper> {
	
	@Autowired
	private TkTaskMapper taskMapper;
	@Autowired
	private TkTaskAwardService taskAwardService;
	@Autowired
	private TkTaskAwardMapper taskAwardMapper;
	
	public int selectCountByTaskCode(TkTask task, Integer notEqualTaskId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("taskCode", task.getTaskCode());
		params.put("taskStartTime", task.getTaskStartTime());
		params.put("taskEndTime", task.getTaskEndTime());
		params.put("status", ConstantsDb.globalStatusNomarl());
		params.put("enableStatus", ConstantsDb.globalEnableStatusNomarl());
		params.put("labelId", task.getLabelId());
		if(notEqualTaskId != null){
			params.put("notEqualTaskId", notEqualTaskId);
		}
		return taskMapper.selectCountByTaskCode(params);
	}
	
	/**
	 * 保存、修改任务
	 * @param request
	 * @param task
	 * @param viewTaskAward
	 * @throws Exception
	 */
	public void saveTask(HttpServletRequest request, TkTask task, ViewTaskAward viewTaskAward) throws Exception{
		SysAdmin admin = SessionUtils.getCurrentUser(request);
		if(task.getTaskId() == null){
			if(task.getParentTaskId() == null){
				task.setParentTaskId(-1);
			}
			String enableStatusConfig = task.getEnableStatus();
			this.save(request, task);
			
			if(!ConstantsDb.globalEnableStatusNomarl().equals(enableStatusConfig)){
				task.setEnableStatus(enableStatusConfig);
				this.update(request, task);
			}
		}else {
			this.update(request, task);
		}
		
		if(viewTaskAward != null){
			List<TkTaskAward> awardList = viewTaskAward.getTaskAwardList();
			for (TkTaskAward award : awardList) {
				award.setTaskId(task.getTaskId());
				List<TkTaskAward> awardDBList = taskAwardService.initQuery().andEqualTo("taskId", task.getTaskId()).andEqualTo("awardType", award.getAwardType()).singleStopTrue();
				TkTaskAward awardDB = awardDBList.size()>0?awardDBList.get(0):null;
				
				if(awardDB == null){
					if(((AwardType.RBC.equals(award.getAwardType()) || AwardType.SCORE.equals(award.getAwardType())) && award.getAwardNum()==null)
						|| (AwardType.ACTIVATION_CODE.equals(award.getAwardType()) && award.getForeignKey()==null)
						|| (AwardType.COUPON.equals(award.getAwardType()) && award.getForeignKey()==null)){
						continue;
					}
					taskAwardService.save(request, award);
				}else {
					award.setAwardId(awardDB.getAwardId());
					award.setModifier(admin.getAdminCode());
					award.setModifyTime(new Date());
					taskAwardMapper.updateAwardByPrimaryKey(award);
				}
			}
		}
	}
}
