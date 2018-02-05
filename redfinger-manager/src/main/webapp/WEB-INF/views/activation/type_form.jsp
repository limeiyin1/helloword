<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>激活码类型编辑</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
			url : host + 'save',
			success : callback
		});
		
		$.extend($.fn.validatebox.defaults.rules, {  
			selectValueRequired: {
				validator: function(value,param){ 
					return $(param[0]).find("option:contains('"+value+"')").val() != '';  
				},  
				message: '该选项为必选项'  
			}  
		});
	</script>
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post">
			<input type="hidden" name="typeId" value="${bean.typeId}">
			<table id="module_submit_table">
				<tr>
					<td class="td1">类型名称:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="activationTypeName" value="${bean.activationTypeName}"
						data-options="required:true,validType:'length[0,100]'" /></td>
				</tr>
				<tr>
					<td class="td1">类型编码:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="activationTypeCode" value="${bean.activationTypeCode}"
						data-options="required:true,validType:'length[0,50]'" /></td>
				</tr>
				<tr>
					<td class="td1">设备类型:</td>
					<td class="td2">
						<select class="easyui-combobox input_width_short" editable="false" name="padType" data-options="required:false">
							<c:forEach var="one" items="${padType}">
								<option <c:if test="${one.key==bean.padType}">selected="selected"</c:if> value="${one.key}">${one.value}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="td1">设备时间（小时）:</td>
					<td class="td2"><input class="easyui-numberbox" type="text"
						name="padTime" value="${bean.padTime}"
						data-options="required:true,min:0,max:10000" /></td>
				</tr>
				<tr>
					<td class="td1"> 同类型激活限制：</td>
					<td class="td2">
						<select class="easyui-combobox input_width_short" editable="false" id="useLimit" name="useLimit" validType="selectValueRequired['#useLimit']">
							<option value="">请选择</option>
							<option value="1" <c:if test="${1 == bean.useLimit}">selected="selected"</c:if>>一个</option>
							<option value="0" <c:if test="${0 == bean.useLimit}">selected="selected"</c:if>>不限制</option>
						</select> 
					</td>
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



