<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<div id="content">
<input type="hidden" name="ids" id="fix_faultback_id" value="${bean.ids}"/>
	<table id="module_submit_table" style="width:100%;">
		<tr>
			<td class="td1">更换设备编号:</td>
			<td class="td2">
				${list[0].renewalPadCode}
			</td>
		</tr>
		<tr>
			<td class="td1">故障设备编号:</td>
			<td class="td2">
				<c:forEach var="one" items="${list}">
					${one.padCode }<br/>
				</c:forEach>
			</td>
		</tr>
		<tr>
			<td class="td1">故障处理:</td>
			<td class="td2">
				<select class="easyui-combobox" style="width:275px;" id="fault-status" editable="false" name="feedbackStatus" data-options="required:true">
					<fns:getOptions category="rf_fault_feedback.feedback_status" value="2" keys="rf_fault_feedback.feedback_status@movekefu,rf_fault_feedback.feedback_status@moveceshi,rf_fault_feedback.feedback_status@moveyunwei,rf_fault_feedback.feedback_status@handle"/>  
				</select>
			</td>
		</tr>
		<tr id="smallClassIdTr" class="hide">
			<td class="td1">修复类型:</td>
			<td class="td2">
			<input class="easyui-combobox" id="rwlb" style="width:275px;"  name="smallClassId" id="smallClassId"  data-options="editable:false,required:false,valueField:'classId', textField:'className'," >  
			</td>
		</tr>
		<tr id="feedbackHandleTr" class="hide">
			<td class="td1">故障修复内容:</td>
			<td class="td2"><input class="easyui-textboxt" id="reply-content" style="width:275px;height:65px;" name="feedbackHandle" value="${bean.feedbackHandle }" data-options="required:false,multiline:true,validType:'length[0,200]'" style="height:60px" /></td>
		</tr>
	</table>
	<span id="fixList" style="display:none;">${fixListJson}</span>
</div>