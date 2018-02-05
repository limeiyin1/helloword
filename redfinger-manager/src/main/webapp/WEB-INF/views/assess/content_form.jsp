<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>考核额外加（改）分</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
			url : host + 'addPoint',
			success : callback
		});
	</script>
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post">
			<input type="hidden" name="id" value="${bean.id}">
			<table id="module_submit_table">
				<tr>
					<td class="td1">考核员工:</td>
					<td class="td2">${bean.targetAdmin}</td>
				</tr>

				<tr>
					<td class="td1">分数:</td>
					<td class="td2"><input class="easyui-numberbox" type="text"
						name="point" data-options="validType:'length[0,32]'" /></td>
				</tr>

			</table>
		</form>
	</div>
</body>
</html>



