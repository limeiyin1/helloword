<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>物理设备批量操作</title>
<meta name="decorator" content="default" />
</head>
<body>
	<script type="text/javascript">
	$('.easyui-form').form({
	    url:host+'taskDevice',   
	    success:callback
	});
		$("#divPadIds" ).css("display", "none");
		
		$("#sn").combobox({
			onChange: function(){
				  var myOptValue = $("#sn").combobox("getValue") ;
				  if(myOptValue=="openSn"){
					  $("#vmSn").removeClass("hide");
				  }else{
					  $("#vmSn").addClass("hide");
					  
				  }
			  }
		});
		
		if($("#sn").combobox("getValue")!="openSn"){
			 $("#vmSn").addClass("hide");
		}

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
				<tr>
					<td class="td1"> 执行操作:</td>
					<td class="td2"><select id="sn"  style="width: 100px" class="easyui-combobox" name="taskType" data-options="required:true,editable:false">
				<option value=""></option>
				<fns:getOptions category="rf_vm_task.task_type" keys="rf_vm_task.task_type@open,rf_vm_task.task_type@openSn,rf_vm_task.task_type@update,rf_vm_task.task_type@rebootDevice"></fns:getOptions>
				</select> </td>
				</tr>
				
				<tr id="vmSn">
					<td class="td1"> 设备SN：</td>
					<td class="td2"><input style="width: 80px" class="easyui-numberbox" type="text"name="padSn" data-options="required:false," /></td>
				</tr>
		</table>
	   </form>
		</div>

</body>
</html>


