<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>节点控制关系</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
			url : host + 'save',
			success : callback
		});
	</script>
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post">
			<input type="hidden" name="matchId" value="${bean.matchId}">
			<table id="module_submit_table">
				<tr>
					<td class="td1">字段:</td>
					<td class="td2"><input type="text" name="fieldName" class="easyui-textbox" value="${bean.fieldName}"/></td>
				</tr>
				<tr>
					<td class="td1">字段名:</td>
					<td class="td2"><input type="text" name="lable" class="easyui-textbox" value="${bean.lable}"/></td>
				</tr>
				<tr>
					<td class="td1">数据类型:</td>
					<td class="td2">
					<select class="easyui-combobox" editable="false" name="dataType" data-options="required:true,value:'${bean.dataType}'">
						<option value="string">string</option>
						<option value="number">number</option>
					</select>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>



