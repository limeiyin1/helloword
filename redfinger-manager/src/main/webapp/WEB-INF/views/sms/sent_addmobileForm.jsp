<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>发送手机短信</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    success:callback
		});
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
	<table id="module_submit_table">
		<tr>
			<td class="td1">批次名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="batchName"value=""data-options="required:true" /></td>
		</tr>
		<tr>
			<td class="td1">手机号码:</td>
			<td class="td2"><input class="easyui-textbox" name="smsMobile" data-options="required:true,multiline:true" style="height:100px" /></td>
		</tr>
		<tr>
			<td class="td1">短信内容:</td>
			<td class="td2"><input class="easyui-textbox" name="smsContent" data-options="required:true,multiline:true,validType:'length[0,70]'" style="height:60px" /></td>
		</tr>
	</table>
    </form>
    </div>
</body>
</html>



