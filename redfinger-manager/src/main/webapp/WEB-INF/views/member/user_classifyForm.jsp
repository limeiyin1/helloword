<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>故障咨询</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
			url : host + 'updateClassify',   
		    success:callback
		});
	</script>
    <div id="module_submit_container">
	 <div id="module_search">
		<form id="module_submit_form" class="easyui-form" method="post">
		<input type="hidden" name="userId" value="${userInfo.userId}" />
		<table id="module_submit_table">
		<tr>
			<td class="td1"> 用户邮箱：</td>
			<td class="td2">${userInfo.userEmail}</td>
		</tr>
		<tr>
			<td class="td1"> 用户手机：</td>
			<td class="td2">${userInfo.userMobilePhone}</td>
		</tr>
		<tr>
			<td class="td1">会员类型:</td>
			<td class="td2">
				<select class="easyui-combobox"  editable="false" name="userClassify" data-options="required:true,width:240">
					<fns:getOptions category="rf_user.user_classify" value="${userInfo.userClassify}"/>  
				</select>
			</td>
		</tr>
		</table>
		</form>
		</div>
	</div>
	

		
</body>
</html>