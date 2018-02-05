<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>角色</title>
<meta name="decorator" content="default" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'role',   
		    success:callback,
		}); 
	</script>
	<div id="module_submit_container">
	<form id="easyui-form" class="easyui-form" method="post">
		<input type="hidden" name="adminCode" value="${bean.adminCode}"/>
		<c:forEach items="${roleList}" var="one">
			<c:set value="" var="checked"/>
			<c:forEach items="${adminRoleList}" var="subOne">
				<c:if test="${one.roleCode==subOne.roleCode}">
					<c:set value="checked" var="checked"></c:set>
				</c:if>
			</c:forEach>
			<label><input name="roleIds" type="checkbox" value="${one.roleCode}" ${checked}> ${one.roleName}</label><br/>
		</c:forEach>
    </form>
    </div>
</body>
</html>



