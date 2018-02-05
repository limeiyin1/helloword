package com.redfinger.manager.modules.fault.service.pojo;

import com.redfinger.manager.common.domain.RfFaultFeedback;

/**
 * 
 *  对RfFaultFeedback的扩展, 用于添加和修改
 * @author Kuanghom Cheng 
 * @version [V1.0, 2017年7月13日 下午2:50:10]
 */
public class RfFaultFeedbackCustom extends RfFaultFeedback {

	private static final long serialVersionUID = -673355678251887610L;
	
	/** 是否自动受理表单 */
	private String isAccept;
	/** 工单确认处理类型*/
	private String replyType;

	public String getIsAccept() {
		return isAccept;
	}

	public void setIsAccept(String isAccept) {
		this.isAccept = isAccept;
	}

	public String getReplyType() {
		return replyType;
	}

	public void setReplyType(String replyType) {
		this.replyType = replyType;
	}

	

}
