<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>绑定设备</title>
<meta name="decorator" content="default" />
</head>
<body>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script>
	<script type="text/javascript">
		$('.easyui-form').form({
			url:host+'bindPad',   
			success:callback
		});
		
		
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
	<input type="hidden" id="versionId" name="versionId" value="${versionId }"/>
	<table id="module_submit_table">
		<tr>
			<td class="td1">设备名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="padName" data-options="validType:'length[0,32]'"  />以设备名称开头</td>
		</tr>
		<tr>
			<td class="td1">设备编号:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="padCode" data-options="validType:'length[0,32]'"  />以设备编号开头</td>
		</tr>
		<tr>
			<td class="td1">设备IP:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="padIp"  data-options="validType:'length[0,32]'" />以设备IP开头</td>
		</tr>
		
		<tr>
			<td class="td1">物理设备名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="deviceName"  data-options="validType:'length[0,32]'" />以物理设备名称开头</td>
		</tr>
		
		<tr>
			<td class="td1">物理设备IP:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="deviceIp"  data-options="validType:'length[0,32]'" />以物理设备IP开头</td>
		</tr>
		<!-- <tr>
			<td class="td1"></td>
			<td class="td2">取所有条件的交集</td>
		</tr> -->
	</table>
    </form>
    </div>
</body>
</html>



