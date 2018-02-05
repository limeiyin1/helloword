<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>活动名字</title>
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
					<td class="td1">活动名字:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="name" value="${bean.name}"
						data-options="required:true,validType:'length[0,32]'" /></td>
				</tr>
				<tr>
					<td class="td1">开始时间:</td>
					<td class="td2"><input type="text" 
						class="easyui-datebox input_width_default" 
						editable="false"  name="begin" value="${bean.beginTime}"
						data-options="required:true"/></td>
				</tr>
				<tr>
					<td class="td1">结束时间:</td>
					<td class="td2"><input type="text" 
						class="easyui-datebox input_width_default" 
						editable="false"  name="end" value="${bean.endTime}"
						data-options="required:true"/></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>



