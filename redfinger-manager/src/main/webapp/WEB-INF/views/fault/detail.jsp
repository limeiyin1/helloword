<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>故障详情</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
	
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
	<div id="tt" class="easyui-tabs">
		<div title="设备信息">
			<table id="module_submit_table">
				<tr>
					<td class="td1">设备编码：</td>
					<td class="td2">${pad.padCode }</td>
				</tr>
				<tr>
					<td class="td1">设备等级：</td>
					<td class="td2">${fns:getLabelStyle('rf_user_pad.pad_grade',userPad.padGrade)}</td>
				</tr>
				<tr>
					<td class="td1">到期时间：</td>
					<td class="td2"><fmt:formatDate value="${userPad.expireTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
				<tr>
					<td class="td1">是否绑定:</td>
					<td class="td2">${fns:getLabelStyle('rf_pad.bind_status',pad.bindStatus)}</td>
				</tr>
				<tr>
					<td class="td1">设备状态:</td>
					<td class="td2">${fns:getLabelStyle('rf_pad.pad_status',pad.padStatus)}</td>
				</tr>
				<tr>
					<td class="td1">是否可用:</td>
					<td class="td2">${fns:getLabelStyle('global.enable_status',pad.enableStatus)}</td>
				</tr>
				<tr>
					<td class="td1">是否在线:</td>
					<td class="td2">${fns:getLabelStyle('rf_pad.pad_status',pad.userPadStatus)}</td>
				</tr>
				<tr>
					<td class="td1">故障次数：</td>
					<td class="td2">${fn:length(feedbackList)}</td>
				</tr>
				<c:forEach var="one" items="${feedbackList }" varStatus="index">
					<tr>
						<td class="td1">故障（${index.count }）：</td>
						<td class="td2">[<fmt:formatDate value="${one.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>]&nbsp;&nbsp;${one.map.className }</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div title="用户信息">
			<table id="module_submit_table">
				<tr>
					<td class="td1">用户手机：</td>
					<td class="td2">${member.userMobilePhone }</td>
				</tr>
				<tr>
					<td class="td1">用户邮箱：</td>
					<td class="td2">${member.userEmail}</td>
				</tr>
			</table>
		</div>
		<div title="当前故障信息">
			<table id="module_submit_table">
				<tr>
					<td class="td1">故障状态：</td>
					<td class="td2">${fns:getLabelStyle('rf_fault_feedback.feedback_status',bean.feedbackStatus)}</td>
				</tr>
				<tr>
					<td class="td1">故障时间：</td>
					<td class="td2"><fmt:formatDate value="${bean.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
				<tr>
					<td class="td1">完成时间：</td>
					<td class="td2"><fmt:formatDate value="${bean.finishTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
				<tr>
					<td class="td1">故障类型：</td>
					<td class="td2">${bean.map.className }</td>
				</tr>
				<tr>
					<td class="td1">故障描述：</td>
					<td class="td2">${bean.feedbackContent }</td>
				</tr>
				<tr>
					<td class="td1">修复类型：</td>
					<td class="td2">${bean.map.fixName }</td>
				</tr>
				<tr>
					<td class="td1">修复内容：</td>
					<td class="td2">${bean.feedbackHandle }</td>
				</tr>
				<tr>
					<td class="td1">故障来源：</td>
					<td class="td2">${fns:getLabel('rf_fault_feedback.feedback_source',bean.feedbackSource)}</td>
				</tr>
			</table>
		</div>
	    <div title="故障处理日志">   
	        <table id="module_submit_table">
				<tr>
					<td class="td1">${bean.map.creater}</td>
					<td class="td2">[<fmt:formatDate value="${bean.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>]&nbsp;&nbsp;提交故障</td>
				</tr>
				<c:forEach var="one" items="${handleList}">
				<tr>
					<td class="td1">${one.map.creater}</td>
					<td class="td2">[<fmt:formatDate value="${one.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>]&nbsp;&nbsp;受理故障</td>
				</tr>
				<c:if test="${not empty one.modifyTime }">
				<tr>
					<td class="td1">${one.map.modifier}</td>
					<td class="td2">
						<c:if test="${one.isSolve=='1'}">[<fmt:formatDate value="${one.modifyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>]&nbsp;&nbsp;解决故障，${one.remark }</c:if>
						<c:if test="${one.isSolve=='0'}">[<fmt:formatDate value="${one.modifyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>]&nbsp;&nbsp;${one.remark }</c:if>
					</td>
				</tr>
				</c:if>
				</c:forEach>
				<c:if test="${not empty bean.finishTime }">
				<tr>
					<td class="td1">${bean.map.modifier}</td>
					<td class="td2">[<fmt:formatDate value="${bean.finishTime}" pattern="yyyy-MM-dd HH:mm:ss"/>]&nbsp;&nbsp;故障处理确认</td>
				</tr>	
				</c:if>
			</table>
		</div>
		<div title="备注">
			<table id="module_submit_table">
				<tr>
					<td class="td1" valign="top">故障备注:</td>
					<td class="td2">${bean.remark }</td>
				</tr>
			</table>
		</div>
	</div>
    </form>
    </div>
</body>
</html>



