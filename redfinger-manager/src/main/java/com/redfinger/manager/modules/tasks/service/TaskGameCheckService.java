package com.redfinger.manager.modules.tasks.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.TaskGameCheck;
import com.redfinger.manager.common.domain.TaskGameCheckExample;
import com.redfinger.manager.common.domain.TaskUserInterim;
import com.redfinger.manager.common.domain.TaskUserInterimExample;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.DictHelper;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.TaskGameCheckMapper;
import com.redfinger.manager.common.mapper.TaskUserInterimMapper;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.StringUtils;

/**
 * 游戏下载任务审核
 * 
 * @ClassName: TaskGameCheckService
 * @author tluo
 * @date 2016年5月31日 下午2:40:22
 */
@Transactional
@Service
@PrimaryKeyAnnotation(field = "checkId")
public class TaskGameCheckService extends BaseService<TaskGameCheck, TaskGameCheckExample, TaskGameCheckMapper> {
	@Autowired
	TaskUserInterimMapper taskUserInterimMapper;

	/** 游戏下载任务审核状态：已通过 **/
	private final String CHECKSTATUS_PASS = "task_gamedownload_check.check_status@pass";
	/** 任务状态：已接取 **/
	private final String TASKSTATUS_UNDONE = "undone";
	/** 任务状态：已完成，可领取 **/
	private final String TASKSTATUS_WAITGAIN = "waitgain";

	/**
	 * 审核通过
	 * 
	 * @Title: checkPass
	 * @return void 返回类型
	 * @param id
	 * @throws Exception
	 */
	public void checkPass(HttpServletRequest request, Integer checkId) throws Exception {
		TaskGameCheck tgc = this.get(checkId);
		if (tgc == null) {
			throw new BusinessException("审核失败，请联系管理员。");
		}
		/** 审核状态已通过的状态值 **/
		String PASS_VALUE = DictHelper.getDictValueByKey(CHECKSTATUS_PASS);

		if (PASS_VALUE.equals(tgc.getCheckStatus())) {
			throw new BusinessException("审核失败，已审核通过。");
		}
		// 获取用户任务中间表数据
		TaskUserInterimExample example = new TaskUserInterimExample();
		TaskUserInterimExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(tgc.getUserId());
		criteria.andTaskIdEqualTo(tgc.getTaskId());
		criteria.andAwardStatusEqualTo(TASKSTATUS_UNDONE);
		TaskUserInterim tuInterim = taskUserInterimMapper.selectOneByExampleShowField(
				Lists.newArrayList(TaskUserInterim.FD_ID, TaskUserInterim.FD_AWARD_STATUS), example);
		if (tuInterim == null) {
			throw new BusinessException("审核失败，用户任务已不存在。");
		}
		// 更改任务状态
		tuInterim.setAwardStatus(TASKSTATUS_WAITGAIN);
		taskUserInterimMapper.updateByPrimaryKeySelective(tuInterim);
		// 更改审核状态
		String userId = SessionUtils.getCurrentUserId(request);
		TaskGameCheck rec = new TaskGameCheck();
		rec.setCheckId(tgc.getCheckId());
		rec.setCheckStatus(PASS_VALUE);
		rec.setCheckTime(new Date());
		rec.setCheckPerson(userId);
		this.update(null, rec);
	}

	/**
	 * 批量审核
	 * 
	 * @Title: batchCheckPass
	 * @return void 返回类型
	 * @param ids
	 * @throws Exception
	 */
	public void batchCheckPass(HttpServletRequest request, String ids) throws Exception {
		String[] idArray = StringUtils.split(ids, ",");
		for (String id : idArray) {
			checkPass(request, Integer.parseInt(id));

		}
	}

	/**
	 * 帐号审核通过
	 * 
	 * @Title: accountCheckPass
	 * @return void 返回类型
	 * @param request
	 * @param taskId
	 * @param account
	 * @throws Exception
	 */
	public void accountCheckPass(HttpServletRequest request, Integer taskId, String checkGameAccount) throws Exception {
		List<TaskGameCheck> list = this.initQuery().andEqualTo("taskId", taskId)
				.andEqualTo("checkGameAccount", checkGameAccount).findAll();
		if (list == null || list.size() != 1) {
			throw new BusinessException("审核失败，帐号未提交。");
		}
		TaskGameCheck tgc = list.get(0);
		/** 审核状态已通过的状态值 **/
		String PASS_VALUE = DictHelper.getDictValueByKey(CHECKSTATUS_PASS);
		if (PASS_VALUE.equals(tgc.getCheckStatus())) {
			return;
		}
		// 获取用户任务中间表数据
		TaskUserInterimExample example = new TaskUserInterimExample();
		TaskUserInterimExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(tgc.getUserId());
		criteria.andTaskIdEqualTo(tgc.getTaskId());
		criteria.andAwardStatusEqualTo(TASKSTATUS_UNDONE);
		TaskUserInterim tuInterim = taskUserInterimMapper.selectOneByExampleShowField(
				Lists.newArrayList(TaskUserInterim.FD_ID, TaskUserInterim.FD_AWARD_STATUS), example);
		if (tuInterim == null) {
			throw new BusinessException("审核失败，用户任务已不存在。");
		}
		// 更改任务状态
		tuInterim.setAwardStatus(TASKSTATUS_WAITGAIN);
		taskUserInterimMapper.updateByPrimaryKeySelective(tuInterim);
		// 更改审核状态
		String userId = SessionUtils.getCurrentUserId(request);
		TaskGameCheck rec = new TaskGameCheck();
		rec.setCheckId(tgc.getCheckId());
		rec.setCheckStatus(PASS_VALUE);
		rec.setCheckTime(new Date());
		rec.setCheckPerson(userId);
		this.update(null, rec);
	}
}
