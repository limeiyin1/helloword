<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>批量生成激活码</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
			url : host + 'saveActivation',
			success : callback
		});
	</script>
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post">
			<table id="module_submit_table">
				<tr>
					<td class="td1">激活码前缀:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="activationCodePrefix"
						data-options="required:true,validType:'length[0,2]'" /></td>
				</tr>
				<tr>
					<td class="td1">激活码个数:</td>
					<td class="td2"><input class="easyui-numberbox" type="text"
						name="activationNumber" precision="0" min="1" max="1000"
						data-options="required:true,validType:'length[0,4]'" /></td>
				</tr>
			
				<tr>
					<td class="td1">激活码类型:</td>
					<td class="td2">
					<select class="easyui-combobox input_width_short" editable="false" name="typeId" data-options="required:false">
						<c:forEach var="one" items="${typeList}">
							<option value="${one.typeId}">${one.activationTypeName}</option>
						</c:forEach>
					</select></td>
				</tr>
				
				<tr>
					<td class="td1">开始时间:</td>
					<td class="td2"><input type="text" class="easyui-datetimebox input_width_default" editable="false" 
						id="begin" name="begin" 
						data-options="required:true"/></td>
				</tr>
				
				<tr>
					<td class="td1">结束时间:</td>
					<td class="td2"><input type="text" class="easyui-datetimebox input_width_default" editable="false" 
						id="end" name="end" 
						data-options="required:true"/></td>
				</tr>
				
				<tr>
					<td class="td1">用户注册时间:</td>
					<td class="td2"><input type="text" class="easyui-datetimebox input_width_default" editable="false" 
						id="userRegisterTimeStr" name="userRegisterTimeStr" 
						data-options="required:false"/></td>
				</tr>
				
				
				<tr>
					<td class="td1">备注:</td>
					<td class="td2"><input class="easyui-textbox" name="remark" 
						data-options="multiline:true,validType:'length[0,500]'"
						style="height: 120px" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>



