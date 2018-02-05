package com.redfinger.manager.modules.refund.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfRefund;
import com.redfinger.manager.common.domain.RfRefundLog;
import com.redfinger.manager.common.domain.RfRefundLogExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfRefundLogMapper;
import com.redfinger.manager.common.utils.YesOrNoStatus;

@Transactional
@Service
@PrimaryKeyAnnotation(field="refundLogId")
public class RefundLogService  extends BaseService<RfRefundLog, RfRefundLogExample, RfRefundLogMapper> {

	@Autowired
	private RfRefundLogMapper refundLogMapper;
	
	public void saveRefundLog(HttpServletRequest request,RfRefund refund,String adminCode, String logType, String remark) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("refundId", refund.getRefundId());
		RfRefundLog pRefundLog = refundLogMapper.selectParentByRefundId(params);
		
		RfRefundLog refundLog = new RfRefundLog();
		if(pRefundLog != null){
			refundLog.setpRefundLogId(pRefundLog.getRefundLogId());
		}
		refundLog.setRefundId(refund.getRefundId());
		refundLog.setLogType(logType);
		refundLog.setHandleAdmin(adminCode);
		refundLog.setUserId(refund.getUserId());
		refundLog.setUserPadId(refund.getUserPadId());
		refundLog.setPadId(refund.getPadId());
		refundLog.setPadCode(refund.getPadCode());
		refundLog.setRefundStatus(refund.getRefundStatus());
		refundLog.setHandleOpinion(refund.getHandleOpinion());
		refundLog.setRefundSource(refund.getRefundSource());
		refundLog.setRefundIp(refund.getRefundIp());
		refundLog.setImei(refund.getImei());
		refundLog.setMacId(refund.getMacId());
		refundLog.setVersion(refund.getVersion());
		refundLog.setStatus(YesOrNoStatus.YES);
		refundLog.setEnableStatus(YesOrNoStatus.YES);
		refundLog.setCreater(adminCode);
		refundLog.setCreateTime(new Date());
		refundLog.setRemark(remark);
		refundLog.setOrderPrice(refund.getOrderPrice());
		refundLog.setReturnFee(refund.getReturnFee());
		refundLog.setRealFee(refund.getRealFee());
		refundLog.setOrderId(refund.getOrderId());
		refundLog.setHandleStatus(refund.getHandleStatus());
		refundLog.setRefundImg(refund.getRefundImg());
		save(request, refundLog);
	}
	
}
