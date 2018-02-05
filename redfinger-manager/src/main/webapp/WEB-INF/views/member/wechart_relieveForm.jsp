<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>微信解绑</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'unbundling',   
		    success:callback
		});
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
    <input type="hidden" name="id" value="${bean.id}">
    <input type="hidden" name="userId" value="${bean.userId}">
    <input type="hidden" name="openid" value="${bean.openid}">
	<table id="module_submit_table">
		<tr>
			<td class="td1">微信昵称:</td>
			<td class="td2">${bean.nickname}</td>
		</tr>	
		<tr>
			<td class="td1">会员邮箱:</td>
			<td class="td2">${bean.userEmail}</td>
		</tr> 
		<tr>
			<td class="td1">会员电话:</td>
			<td class="td2">${bean.userMobilePhone}</td>
		</tr>
	</table>
    </form>
    </div>
</body>
</html>



