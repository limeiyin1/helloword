<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>虚拟化批量操作</title>
<meta name="decorator" content="default" />
</head>
<body>
	<script type="text/javascript">
	$('.easyui-form').form({
	    url:host+'task',   
	    success:callback
	});
		$("#divPadIds" ).css("display", "none");
	</script>
 
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post">
		 <table id="module_submit_table" >
			
				<tr>
					<td class="td1">设备编号:</td>
					<td class="td2">设备IP</td>
				</tr>
				<c:forEach  items="${list}" var="bean">
				<tr>
					<td class="td1">${bean.deviceCode} <input type="hidden" name="ids" value="${bean.deviceId}"></td>
					<td class="td2">${bean.deviceIp}</td>
				</tr>
				</c:forEach>
			<!-- 	<tr>
					<td class="td1"> 间隔时间：</td>
					<td class="td2"><input style="width: 80px" class="easyui-numberbox" type="text"name="retryInterval" data-options="required:false," /></td>
				</tr> --> 
				<tr>
					<td class="td1"> 执行操作:</td>
					<td class="td2"><select style="width: 100px" class="easyui-combobox" name="taskType" data-options="required:true,editable:false">
				<fns:getOptions category="rf_vm_task.task_type" keys="rf_vm_task.task_type@start,rf_vm_task.task_type@stop,rf_vm_task.task_type@restart,rf_vm_task.task_type@resetVM,rf_vm_task.task_type@restartRemotePlay,rf_vm_task.task_type@recovery"></fns:getOptions>
		     	<option value="gamemanage" >kill->gamemanage</option>
				</select> </td>
				</tr>
				
				<tr>
					<td class="td1">执行范围:</td>
					<td class="td2"><select style="width: 100px" class="easyui-combobox" name="accredit" data-options="required:true,editable:false">
					<option value="" >-全部-</option>
				<fns:getOptions category="rf_pad.grant_open_status"></fns:getOptions>
				</select> </td>
				</tr>
		</table>
	   </form>
		</div>

</body>
</html>


