<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>优惠劵类型编辑</title>
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
			<input type="hidden" name="typeId" value="${bean.typeId}">
			<table id="module_submit_table">
				<tr>
					<td class="td1">类型名称:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="typeName" value="${bean.typeName}"
						data-options="required:true,validType:'length[0,100]'" /></td>
				</tr>
				<tr>
					<td class="td1">类型编码:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="typeCode" value="${bean.typeCode}"
						data-options="required:true,validType:'length[0,100]'" /></td>
				</tr>
				<tr>
					<td class="td1">备注:</td>
					<td class="td2"><input class="easyui-textbox" name="remark"
						value="${bean.remark}"
						data-options="multiline:true,validType:'length[0,1000]'"
						style="height: 120px" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>



