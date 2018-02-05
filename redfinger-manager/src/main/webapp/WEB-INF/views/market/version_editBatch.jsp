<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>版本控制</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script>
	<script type="text/javascript">
		$('.easyui-form').form({url:host+'editBatchVersion',   success:callback});
		
		
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
	<input type="hidden" name="ids" value="${ids}">
	<table id="module_submit_table">
		<tr>
			<td class="td1">版本编码:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="versionCode" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		<tr>
			<td class="td1">版本内容:</td>
			<td class="td2"><input style="height: 180px" class="easyui-textbox" type="text" name="versionDesc" data-options="required:true,validType:'length[0,500]',multiline:true" /></td>
		</tr>
		<tr>
			<td class="td1">系统类别:</td>
			<td class="td2">
				<select class="easyui-combobox"  editable="false" id="osType" name="osType" data-options="required:true">
					<c:forEach items="${map}" var="one">
						<option value="${one.key }" <c:if test="${one.key==android}">selected="selected"</c:if>>${one.value }</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td class="td1">版本名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="versionName" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		<tr>
			<td class="td1">更新日期:</td>
			<td class="td2">
			   <input type="text" class="easyui-datebox input_width_default" editable="true"  name="appTime" data-options="required:true" />	
			</td>
		</tr>
		<tr>
			<td class="td1">app版本名称:</td>
			<td class="td2">
			   <input class="easyui-textbox" type="text" id="appName" name="appName" data-options="required:true" />多个请用英文,隔开
			</td>
		</tr>
		
	</table>
    </form>
    </div>
</body>
</html>



