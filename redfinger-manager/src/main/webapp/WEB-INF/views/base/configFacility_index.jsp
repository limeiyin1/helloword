<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>

<html>
<head>
<title>设备配置</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	$(function() {
		$('.easyui-form').form({
			url : host + 'update',
			success : configCallback
		});
	});
</script>
</head>
<body>
	<div id="module_submit_container"  align="center">
		<form id="module_submit_form" class="easyui-form" method="post">
			<table class="easyui-table" >
				<fns:getConfigTr code="config_facility_advertisementtime" number="true"/>
				<fns:getConfigTr code="config_facility_advertisementurl"/>
				<fns:getConfigTr code="config_facility_common" number="true"/>
				<fns:getConfigTr code="config_facility_commoncontrol" number="true"/>
				<fns:getConfigTr code="config_facility_vipcontrol" number="true"/>
				<fns:getConfigTr code="config_facility_svipcontrol" number="true"/>
				<fns:getConfigTr code="config_facility_reapply" number="true"/>
				<fns:getConfigTr code="config_facility_refresh" number="true"/>
				<fns:getConfigTr code="config_facility_ransom" number="true"/>
				<fns:getConfigTr code="config_task_timeout" number="true"/>
				<fns:getConfigTr code="config_device_vm" number="true"/>
				<fns:getConfigTr code="config_device_vm_max" number="true"/>
				
	        	<tr>
			        <td class="td1"></td>
		         	<td class="td2"> 
		         	<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-ok-rf" plain="false" onclick="submitForm()">保存</a>
		         	<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-undo" plain="false" onclick="resetForm()">重置</a>
		         	</td>
		        </tr>
			</table>
		</form>
	</div>
</body>


</html>