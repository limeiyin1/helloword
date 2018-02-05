package com.redfinger.manager.common.gather.domain;

import java.util.Date;

import com.redfinger.manager.common.base.BaseDomain;

public class RfClientNetinfo extends BaseDomain{
    private static final long serialVersionUID = -3765149841391838326L;

	private Integer netinfoId;

    private String errorCode;

    private Integer userId;

    private String source;

    private String clientType;

    private String gatherType;

    private String clientVersion;

    private String clientIp;

    private String imei;

    private String mac;

    private String networkOperator;

    private String networkType;

    private String padCode;

    private Integer networkDelay;

    private String networkDetail;

    private Integer pingOk;

    private Integer pingResult;

    private String pingContent;

    private Integer tcpingOk;

    private Integer tcpingResult;

    private String tcpingFailDesc;

    private String tcpingContent;

    private Integer traceOk;

    private Integer traceResult;

    private String traceFailDesc;

    private String traceContent;

    private Date clientTime;

    private Date cloudReceiveTime;

    private Date cloudSendTime;

    private Date serverTime;

    private String userMobilePhone;

    private Integer externalUserId;

    private String creater;

    private Date createTime;

    private String modifier;

    private Date modifyTime;

    private String status;

    private String enableStatus;

    private Integer reorder;

    private String remark;
    
    private String cuid;

    public Integer getNetinfoId() {
        return netinfoId;
    }

    public void setNetinfoId(Integer netinfoId) {
        this.netinfoId = netinfoId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode == null ? null : errorCode.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType == null ? null : clientType.trim();
    }

    public String getGatherType() {
        return gatherType;
    }

    public void setGatherType(String gatherType) {
        this.gatherType = gatherType == null ? null : gatherType.trim();
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion == null ? null : clientVersion.trim();
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp == null ? null : clientIp.trim();
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac == null ? null : mac.trim();
    }

    public String getNetworkOperator() {
        return networkOperator;
    }

    public void setNetworkOperator(String networkOperator) {
        this.networkOperator = networkOperator == null ? null : networkOperator.trim();
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType == null ? null : networkType.trim();
    }

    public String getPadCode() {
        return padCode;
    }

    public void setPadCode(String padCode) {
        this.padCode = padCode == null ? null : padCode.trim();
    }

    public Integer getNetworkDelay() {
        return networkDelay;
    }

    public void setNetworkDelay(Integer networkDelay) {
        this.networkDelay = networkDelay;
    }

    public String getNetworkDetail() {
        return networkDetail;
    }

    public void setNetworkDetail(String networkDetail) {
        this.networkDetail = networkDetail == null ? null : networkDetail.trim();
    }

    public Integer getPingOk() {
        return pingOk;
    }

    public void setPingOk(Integer pingOk) {
        this.pingOk = pingOk;
    }

    public Integer getPingResult() {
        return pingResult;
    }

    public void setPingResult(Integer pingResult) {
        this.pingResult = pingResult;
    }

    public String getPingContent() {
        return pingContent;
    }

    public void setPingContent(String pingContent) {
        this.pingContent = pingContent == null ? null : pingContent.trim();
    }

    public Integer getTcpingOk() {
        return tcpingOk;
    }

    public void setTcpingOk(Integer tcpingOk) {
        this.tcpingOk = tcpingOk;
    }

    public Integer getTcpingResult() {
        return tcpingResult;
    }

    public void setTcpingResult(Integer tcpingResult) {
        this.tcpingResult = tcpingResult;
    }

    public String getTcpingFailDesc() {
        return tcpingFailDesc;
    }

    public void setTcpingFailDesc(String tcpingFailDesc) {
        this.tcpingFailDesc = tcpingFailDesc == null ? null : tcpingFailDesc.trim();
    }

    public String getTcpingContent() {
        return tcpingContent;
    }

    public void setTcpingContent(String tcpingContent) {
        this.tcpingContent = tcpingContent == null ? null : tcpingContent.trim();
    }

    public Integer getTraceOk() {
        return traceOk;
    }

    public void setTraceOk(Integer traceOk) {
        this.traceOk = traceOk;
    }

    public Integer getTraceResult() {
        return traceResult;
    }

    public void setTraceResult(Integer traceResult) {
        this.traceResult = traceResult;
    }

    public String getTraceFailDesc() {
        return traceFailDesc;
    }

    public void setTraceFailDesc(String traceFailDesc) {
        this.traceFailDesc = traceFailDesc == null ? null : traceFailDesc.trim();
    }

    public String getTraceContent() {
        return traceContent;
    }

    public void setTraceContent(String traceContent) {
        this.traceContent = traceContent == null ? null : traceContent.trim();
    }

    public Date getClientTime() {
        return clientTime;
    }

    public void setClientTime(Date clientTime) {
        this.clientTime = clientTime;
    }

    public Date getCloudReceiveTime() {
        return cloudReceiveTime;
    }

    public void setCloudReceiveTime(Date cloudReceiveTime) {
        this.cloudReceiveTime = cloudReceiveTime;
    }

    public Date getCloudSendTime() {
        return cloudSendTime;
    }

    public void setCloudSendTime(Date cloudSendTime) {
        this.cloudSendTime = cloudSendTime;
    }

    public Date getServerTime() {
        return serverTime;
    }

    public void setServerTime(Date serverTime) {
        this.serverTime = serverTime;
    }

    public String getUserMobilePhone() {
        return userMobilePhone;
    }

    public void setUserMobilePhone(String userMobilePhone) {
        this.userMobilePhone = userMobilePhone == null ? null : userMobilePhone.trim();
    }

    public Integer getExternalUserId() {
        return externalUserId;
    }

    public void setExternalUserId(Integer externalUserId) {
        this.externalUserId = externalUserId;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(String enableStatus) {
        this.enableStatus = enableStatus == null ? null : enableStatus.trim();
    }

    public Integer getReorder() {
        return reorder;
    }

    public void setReorder(Integer reorder) {
        this.reorder = reorder;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
    
    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid == null ? null : cuid.trim();
    }
}