<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>

<html>
<head>
<title>体验配置</title>
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
				<fns:getConfigTr code="config_probation_pad_time" number="true"/>
				<tr>
	                <td class="td1"> 体验设备时间 *注：</td>
	                <td class="td2"> 单位（分钟）
	                </td>
	            </tr>
	            <fns:getConfigTr code="config_probation_control_time" number="true"/>
				<tr>
	                <td class="td1"> 体验设备可控时间 *注：</td>
	                <td class="td2"> 单位（秒）
	                </td>
	            </tr>

				<fns:getConfigTr code="config_probation_pad" number="true"/>
				<fns:getConfigTr code="config_usable_probation_pad" number="true" readonly="true"/>
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