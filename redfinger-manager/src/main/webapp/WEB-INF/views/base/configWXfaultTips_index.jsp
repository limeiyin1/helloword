<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>

<html>
<head>
<title>微信自动回复配置</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	$(function() {
		$('.easyui-form').form({
			url : host + 'update',
			success : configCallback
		});
	});
	var resetForm=function(){
		$("#t1").val(''); 
	}
</script>
</head>
<body>
	<%
		String val = com.redfinger.manager.common.constants.ConfigConstantsDb.weixinFaultTips();
	%>
	<div id="module_submit_container" align="center">
		<form id="module_submit_form" class="easyui-form" method="post">
			<table class="easyui-table">
			<tr>
					<td class="td1">微信自动回复:</td>
					<td class="td2"><fieldset>
							<legend>请输入内容</legend>
							<textarea id="t1" name="configValue"
								style="width: 450px; height: 200px"><%=val%></textarea>
						</fieldset></td>
				</tr>
				<tr>
					<td class="td1"></td>
					<td class="td2"><a href="javascript:void(0)"
						class="easyui-linkbutton" iconCls="icon-ok-rf" plain="false"
						onclick="submitForm()">保存</a> <a href="javascript:void(0)"
						class="easyui-linkbutton" iconCls="icon-undo" plain="false"
						onclick="resetForm()">重置</a></td>
				</tr>
			</table>
		</form>
	</div>
</body>


</html>