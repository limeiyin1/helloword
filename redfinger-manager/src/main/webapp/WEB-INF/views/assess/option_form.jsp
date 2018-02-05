<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>添加考核题目</title>
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
			<input type="hidden" name="id" value="${bean.id}">
			<table id="module_submit_table">

				<tr>
					<td class="td1">考核内容:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="name" value="${bean.name }"
						data-options="required:true" /></td>
				</tr>
				<tr>
					<td class="td1">关键字:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="keyword" value="${bean.keyword }"
						data-options="validType:'length[0,32]'" /></td>
				</tr>
				<tr>
					<td class="td1">分数:</td>
					<td class="td2"><input class="easyui-numberbox" type="text"
						name="point" value="${bean.point }"
						data-options="validType:'length[0,32]'"<c:if test="${not empty bean.point}">disabled="true"</c:if> /></td>
				</tr>
				<tr>
					<td class="td1">排序:</td>
					<td class="td2"><input class="easyui-numberbox" type="text"
						name="reorder" value="${bean.reorder }"
						data-options="validType:'length[0,32]'" /></td>
				</tr>

				<tr>
					<td class="td1">描述:</td>
					<td class="td2"><input class="easyui-textbox" name="remark"
						value="${bean.remark }"
						data-options="multiline:true,validType:'length[0,200]'"
						style="height: 60px" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>



