<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>身份验证开关</title>
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
	<div id="module_submit_container" align="center">
		<form id="module_submit_form" class="easyui-form" method="post">
			<table class="easyui-table">
				<tr>
					<td>身份验证开关:</td>
					<td><select class="easyui-combobox input_width_default"
						editable="false" name="map[config_identity_auth]">
							<option value="">[请选择]</option>
							<fns:getOptions category="global.switch_status" value="${config_identity_auth}" />
					</select>
				   </td>
				</tr>
				<tr>
					<td class="td2" colspan="2" align="center"><a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-ok-rf" plain="false" onclick="submitForm()">保存</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-undo" plain="false" onclick="resetForm()">重置</a></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
