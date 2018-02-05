<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>渠道管理</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script>
	<script type="text/javascript">
		$('.easyui-form').form({url:host+'subOnePackage',   success:callback});
		
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
	<table id="module_submit_table">
		<tr>
			<td class="td1">母包:</td>
			<td class="td2">${channelCode}</td>
		</tr>
		
		<tr>
			<td class="td1">渠道编码:</td>
			<td class="td2">
			<input type="hidden" name="channelCode" value="${bean.channelCode }">
			<input type="hidden" name="channelName" value="${bean.channelName }">
			<input type="hidden" name="channelOsType" value="${bean.clientType }">
			${bean.channelCode }
			</td>
		</tr>
		
		<tr>
			<td class="td1">系统类别:</td>
			<td class="td2">
				<%-- <select class="easyui-combobox input_width_short" editable="false" id="channelOsType" name="channelOsType" data-options="required:true">
				   <fns:getOptions category="version.os_type" keys="version.os_type@android,version.os_type@win" value="${bean.clientType}"></fns:getOptions>
			 	</select> --%>
			 	${bean.clientType}
			</td>
		</tr>
		
		<tr>
			<td class="td1">版本编码:</td>
			<td class="td2">
				<select class="easyui-combobox input_width_short" id="channelVersionCode" name="channelVersionCode" data-options="required:true,editable:false">
					<option value="">-请选择-</option>
					 <c:forEach var="one" items="${list}">
					 	<option value="${one.parentVersionCode }" >${one.parentVersionCode }[${one.osType }]</option>
					 </c:forEach>
				</select>
			</td>
		</tr>
		
		<tr>
			<td class="td1">是否必须更新:</td>
			<td class="td2"><select class="easyui-combobox input_width_short" editable="false" id="channelVersionMust" name="channelVersionMust">
				   <fns:getOptions category="client_version.update"></fns:getOptions>
			 	</select>
			</td>
		</tr>
		
		<tr>
			<td class="td1">是否是新版本:</td>
			<td class="td2"><select class="easyui-combobox input_width_short" editable="false" id="channelVersionNew" name="channelVersionNew">
				   <fns:getOptions category="client_version.update"></fns:getOptions>
			 	</select>
			</td>
		</tr>
		
		
		<tr>
			<td class="td1">版本名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="channelVersionName" data-options="required:true,validType:'length[0,32]'"  /></td>
		</tr>
		
		<tr>
			<td class="td1">版本时间:</td>
			<td class="td2">
				<input type="text" class="easyui-datetimebox input_width_default" editable="false" id="end" name="channelVersionTimeStr" data-options="required:true"/>
			</td>
		</tr>
		
		
		<tr>
			<td class="td1">版本描述:</td>
			<td class="td2">
			<!-- <input style="height: 180px" class="easyui-textbox" type="text" name="channelVersionDesc"data-options="required:true,validType:'length[0,500]',multiline:true" /> -->
			<textarea name="channelVersionDesc" class="easyui-validatebox" style="height:160px;width:280px;" data-options="validType:'length[0,500]'" wrap="hard" required="true"></textarea>
			</td>
		</tr>
	</table>
    </form>
    </div>
</body>
</html>



