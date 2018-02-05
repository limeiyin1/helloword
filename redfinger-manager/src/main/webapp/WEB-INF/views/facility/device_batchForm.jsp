<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>设备批量操作</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'batchPad',   
		    success:callback
		});
		function countPads(message,pads) {
			var padlist;
			padlist= message.value.split("\n").length;
			pads.value=padlist;
			}
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
    
	<table id="module_submit_table">
		   <tr>
				<td class="td1"> 执行操作:</td>
				<td class="td2">
					<select  style="width: 100px" class="easyui-combobox" name="actionType" data-options="required:true,editable:false">
					<fns:getOptions category="rf_vm_task.task_type" keys="rf_vm_task.task_type@start,rf_vm_task.task_type@stop,rf_vm_task.task_type@restart,rf_vm_task.task_type@resetVM,rf_vm_task.task_type@restartRemotePlay,rf_vm_task.task_type@recovery,rf_vm_task.task_type@update,rf_vm_task.task_type@rebootDevice"></fns:getOptions>
					</select>
				 </td>
				</tr>
	       <tr>
			<td class="td1">设备清单:</td>
			<td class="td2"><fieldset>
						<legend>请输入内容</legend>
						<textarea name="padCode" wrap=PHYSICAL style="width: 220px;height: 200px" onKeyDown="countPads(this.form.padCode,this.form.pads);"
						onKeyUp="countPads(this.form.padCode,this.form.pads);"></textarea>
		                </fieldset>
		    </td>
		 </tr>
		 <tr>
		    <td class="td1">已输入行数：</td>
		    <td class="td2"><input disabled maxlength="4" name="pads" size="3" value="0" class="inputtext"></td>
		 </tr>
	</table>
    </form>
    </div>
</body>
</html>



