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
			<input type="hidden" name="scriptId" value="${bean.scriptId}">
			<table id="module_submit_table">
				<tr>
					<td class="td1">脚本编码:</td>
					<td class="td2"><input type="text" name="scriptCode" class="easyui-textbox" value="${bean.scriptCode}" data-options="required:true"/></td>
				</tr>
				<tr>
					<td class="td1">路径:</td>
					<td class="td2"><input type="text" name="path" class="easyui-textbox" value="${bean.path}" data-options="required:true"/></td>
				</tr>
				<tr>
					<td class="td1">频率:</td>
					<td class="td2"><input type="text" name="frequency" class="easyui-numberbox" value="${bean.frequency}" data-options="required:true"/></td>
				</tr>
				<tr>
					<td class="td1">类型:</td>
					<td class="td2">
					<select class="easyui-combobox" editable="false" name="type" data-options="required:true,value:'${bean.type}'">
						<option value="pad">pad</option>
						<option value="device">device</option>
					</select>
					</td>
				</tr>
				<tr>
					<td class="td1">排序:</td>
					<td class="td2"><input type="text" name="reorder" class="easyui-numberbox" value="${bean.reorder}" data-options="required:true"/></td>
				</tr>
				<tr>
					<td class="td1">备注:</td>
					<td class="td2">
					<textarea name="remark" class="easyui-validatebox" style="height:100px;width:280px;" validType="text[1,500]" wrap="hard" >${bean.remark}</textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>



