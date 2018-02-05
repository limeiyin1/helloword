<%@page import="com.redfinger.manager.common.domain.RfRefund"%>
<%@page import="com.redfinger.manager.common.utils.RefundHandleStatus"%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>退款处理</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script>
	<script type="text/javascript">
		$('.easyui-form').form({url:host+'refundHandle', success:callback});
		
		$.extend($.fn.validatebox.defaults.rules, {  
			selectValueRequired: {
				validator: function(value,param){ 
					return $(param[0]).find("option:contains('"+value+"')").val() != '';  
				},  
				message: '该选项为必选项'  
			}  
		});
		
		var choiceRefundStatus = function(){
			var handleStatus = $("#handleStatus").combobox('getValue');
			if(handleStatus=='<%=RefundHandleStatus.REFUND %>'){
				$("tr[name=refundImgTr]").removeClass("hide");
			}else{
				$("tr[name=refundImgTr]").addClass("hide");
			}
			if(handleStatus=='<%=RefundHandleStatus.REFUND %>' || handleStatus=='<%=RefundHandleStatus.BACK %>' || handleStatus=='<%=RefundHandleStatus.CANCEL %>'){
				$("tr[name=refundNoticeTr]").removeClass("hide");
			}else{
				$("tr[name=refundNoticeTr]").addClass("hide");
			}
		}
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" name="refund_submit_form" class="easyui-form" method="post" enctype="multipart/form-data">
	<input type="hidden" name="refundId" value="${refund.refundId}">
	<table id="module_submit_table">
		<tr>
			<td class="td1">用户账号:</td>
			<td class="td2">${user.userMobilePhone }</td>
		</tr>
		<tr>
			<td class="td1">设备编号:</td>
			<td class="td2">${refund.padCode }</td>
		</tr>
		<tr>
			<td class="td1">当前退款状态:</td>
			<td class="td2">${fns:getLabel("rf_refund.handle_status",refund.handleStatus)}</td>
		</tr>
		<tr>
			<td class="td1">申请退款时间:</td>
			<td class="td2"><fmt:formatDate value="${refund.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		</tr>
		<tr>
			<td class="td1">订单金额(元):</td>
			<td class="td2">${refund.orderPrice/100 }</td>
		</tr>
		<tr>
			<td class="td1">应收手续费(元):</td>
			<td class="td2">${refund.realFee/100 }</td>
		</tr>
		<tr>
			<td class="td1">应退金额(元):</td>
			<td class="td2">${refund.returnFee/100 }</td>
		</tr>
		<tr>
			<td class="td1">备注:</td>
			<td class="td2">${refund.remark }</td>
		</tr>
		<tr>
			<td class="td1">处理方式:</td>
			<td class="td2">
				<select class="easyui-combobox" name="handleStatus" id="handleStatus" data-options="editable:false,onSelect:choiceRefundStatus" validType="selectValueRequired['#handleStatus']">
		            <option value="">全部</option>
					<option value="<%=RefundHandleStatus.BACK %>">退回给用户</option>
					<option value="<%=RefundHandleStatus.CANCEL %>">取消退款申请</option>
					<%
					RfRefund refund = (RfRefund)request.getAttribute("refund");
					if(RefundHandleStatus.APPLE_ACCEPT.equals(refund.getHandleStatus())){
					%>
					<option value="<%=RefundHandleStatus.VERIFY %>">退款审核</option>
					<%}if(RefundHandleStatus.VERIFY_ACCEPT.equals(refund.getHandleStatus())){ %>
					<option value="<%=RefundHandleStatus.REFUND %>">已退款</option>
					<%} %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="td1">处理意见:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="handleOpinion" value="" data-options="required:true,multiline:true,validType:'length[0,200]'" style="height: 60px" /></td>
		</tr>
		<tr name="refundImgTr" class="hide">
			<td class="td1">退款截图:</td>
			<td class="td2">
				<input type="file" name="refundImgName" style="width:200px;" value="">
			</td>
		</tr>
		<tr name="refundNoticeTr" class="hide">
			<td class="td1">用户公告内容:<br/>(退回、取消、退款时会通过公告发送给用户)</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="noticeContent" value="" data-options="multiline:true,validType:'length[0,200]'" style="height: 60px" /></td>
		</tr>
	</table>
    </form>
    </div>
</body>
</html>
