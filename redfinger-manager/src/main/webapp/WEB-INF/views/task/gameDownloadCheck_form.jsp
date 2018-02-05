<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>帐号批量审核通过</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
			success : callback
		});
		function countaccounts(message, accounts) {
			var codelist;
			codelist = message.value.split("\n").length;
			accounts.value = codelist;
		}
	</script>
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post">
			<table id="module_submit_table">
				<tr>
					<td class="td1">选择任务:</td>
					<td class="td2"><input class="easyui-combobox" name="taskId"
						data-options="valueField: 'id',textField: 'name',url: 'gameDownloadCheck/getTask.html'" /></td>
				</tr>
				<tr>
					<td class="td1">审核帐号输入:</td>
					<td class="td2"><fieldset>
							<legend>请输入内容</legend>
							<textarea name="accounts" wrap=PHYSICAL
								style="width: 220px; height: 200px"
								onKeyDown="countaccounts(this.form.accounts,this.form.phons);"
								onKeyUp="countaccounts(this.form.accounts,this.form.phons);"></textarea>
						</fieldset></td>
				</tr>
				<tr>
					<td class="td1">审核帐号个数：</td>
					<td class="td2"><input disabled maxlength="4" name="phons"
						size="3" value="0" class="inputtext"></td>
				</tr>

			</table>
		</form>
	</div>
</body>
</html>



