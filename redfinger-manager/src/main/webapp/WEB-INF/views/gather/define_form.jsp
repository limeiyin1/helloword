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
			
			<table id="module_submit_table">
				<tr>
					<td class="td1">字段:</td>
					<td class="td2"><input type="text" name="fieldCode" class="easyui-textbox" data-options="required:true" value="${bean.fieldCode}"/></td>
				</tr>
				<tr>
					<td class="td1">字段名:</td>
					<td class="td2"><input type="text" name="lable" class="easyui-textbox" data-options="required:true" value="${bean.lable}"/></td>
				</tr>
				<tr>
					<td class="td1">数据类型:</td>
					<td class="td2">
					<select class="easyui-combobox" editable="false" name="dataType" data-options="required:true">
						
						<fns:getOptions category="gather.data_type" value="${bean.dataType}"></fns:getOptions>
					</select>
					</td>
				</tr>
				<tr>
					<td class="td1">隶属表:</td>
					<td class="td2">
					<select class="easyui-combobox" editable="false" name="tableId" data-options="required:true">
						
						<fns:getOptions category="gather.from_table" value="${bean.tableId}"></fns:getOptions>
					</select>
					</td>
				</tr>
				<tr>
					<td class="td1">排序:</td>
					<td class="td2"><input type="text" name="reorder" class="easyui-numberbox" data-options="min:0,precision:0" value="${bean.reorder}"/></td>
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



