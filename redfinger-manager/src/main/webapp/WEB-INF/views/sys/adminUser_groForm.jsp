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
		    url:host+'gro',   
		    success:callback,
		}); 
	</script>
	<div id="module_submit_container">
	<form id="easyui-form" class="easyui-form" method="post">
		<input type="hidden" name="adminUserId" value="${bean.adminUserId}"/>
		<c:forEach items="${groList}" var="one">
			<c:set value="" var="checked"/>
			<c:forEach items="${operGroList}" var="subOne">
				<c:if test="${one.groCode==subOne.groCode}">
					<c:set value="checked" var="checked"></c:set>
				</c:if>
			</c:forEach>
			<label><input name="groIds" type="checkbox" value="${one.groCode}" ${checked}> ${one.groName}</label><br/>
		</c:forEach>
    </form>
    </div>
</body>
</html>



