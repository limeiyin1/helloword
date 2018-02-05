<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>推荐图片</title>
<meta name="decorator" content="default" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
			url : host + 'recommendImage',
			success : callback,
		});
	</script>
	<div id="module_submit_container">

		<form id="easyui-form" class="easyui-form" method="post">
			<input type="hidden" name="apkId" value="${bean.id}" />
			<table id="module_submit_table" class="easyui-table">
						<tr>
							<td class="td1">图片地址480:</td>
							<td class="td2"><input class="easyui-textbox" type="text" size="40"
								name="recommendImage" value="${recommendUrl.split(',')[0]}" data-options="required:true" /></td>
						</tr>
						<tr>
							<td class="td1">图片地址720:</td>
							<td class="td2"><input class="easyui-textbox" type="text" size="40"
								name="recommendImage" value="${recommendUrl.split(',')[1]}" data-options="required:true" /></td>
						</tr>
						<tr>
							<td class="td1">图片地址1080:</td>
							<td class="td2"><input class="easyui-textbox" type="text" size="40"
								name="recommendImage" value="${recommendUrl.split(',')[2]}" data-options="required:true" /></td>
						</tr>

			</table>
		</form>

	</div>
</body>
</html>



