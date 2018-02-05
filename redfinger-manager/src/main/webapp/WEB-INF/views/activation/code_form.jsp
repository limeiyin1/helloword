<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>激活码编辑</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
			url : host + 'update',
			success : callback
		});
	</script>
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post">
			<input type="hidden" name="codeId" value="${bean.codeId}">
			<table id="module_submit_table">
				<tr>
					<td class="td1">激活码:</td>
					<td class="td2">${bean.activationCode}</td>
				</tr>
				
				<tr>
					<td class="td1">激活码类型:</td>
					<td class="td2">
						<select class="easyui-combobox"  editable="false" name="typeId" data-options="required:true">
							<c:forEach items="${typeList}" var="one">
								<option value="${one.typeId }" <c:if test="${one.typeId==bean.typeId}">selected="selected"</c:if>>${one.activationTypeName }</option>
							</c:forEach>
						</select> 
					</td>
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
					<td class="td1">设备时间(小时):</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="padTime" value="${bean.padTime}"
						data-options="required:true,min:0,max:999" /></td>
				</tr>
				
				<tr>
					<td class="td1">开始时间:</td>
					<td class="td2">
						<input type="text" class="easyui-datetimebox input_width_default" editable="false" 
						id="begin" name="begin" 
						value="<fmt:formatDate value="${bean.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" 
						data-options="required:true"/>
					</td>
				</tr>
				
				<tr>
					<td class="td1">结束时间:</td>
					<td class="td2">
						<input type="text" class="easyui-datetimebox input_width_default" editable="false" 
						id="end" name="end" 
						value="<fmt:formatDate value="${bean.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" 
						data-options="required:true"/>
					</td>
				</tr>
				
				<tr>
					<td class="td1">用户注册时间:</td>
					<td class="td2">
						<input type="text" class="easyui-datetimebox input_width_default" editable="false" 
						id="userRegisterTimeStr" name="userRegisterTimeStr" 
						value="<fmt:formatDate value="${bean.userRegisterTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" 
						data-options="required:false"/>
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



