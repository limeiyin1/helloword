<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>敏感词</title>
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
					<td class="td1">敏感词:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="sensitiveName" value="${bean.sensitiveName }"
						data-options="required:true,validType:'length[0,32]'" /></td>
				</tr>
				<tr>
					<td class="td1">排序:</td>
					<td class="td2"><input class="easyui-numberbox" type="text"
						name="reorder" value="${bean.reorder }"
						data-options="min:0,max:999" /></td>
				</tr>
				<tr>
					<td class="td1">长度:</td>
					<td class="td2"><input class="easyui-numberbox" type="text"
						name="sensitiveLength" value="${bean.sensitiveLength }"
						data-options="min:0,max:999" /></td>
				</tr>
				<tr>
					<td class="td1">敏感词首字母:</td>
					<td class="td2"><input class="easyui-textbox" name="sensitivePrimacy"
						value="${bean.sensitivePrimacy }"
						data-options="multiline:true,validType:'length[0,200]'"
						 /></td>
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



