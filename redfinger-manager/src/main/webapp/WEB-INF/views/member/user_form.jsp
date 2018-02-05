<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>赠送红豆</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'rbc',   
		    success:callback
		});
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
     <input type="hidden" name="userId" value="${bean.userId}">
     <input type="hidden" name="rbcAmount" value="${bean.rbcAmount}">
	<table id="module_submit_table">
		<tr>
			<td class="td1">会员名称:</td>
			<td class="td2">${bean.userName}</td>
		</tr>	
		<tr>
			<td class="td1">会员邮箱:</td>
			<td class="td2">${bean.userEmail}</td>
		</tr> 
		<tr>
			<td class="td1">会员电话:</td>
			<td class="td2">${bean.userMobilePhone}</td>
		</tr>
		<tr>
			<td class="td1">会员等级:</td>
			<td class="td2">${bean.userLevel}</td>
		</tr>
		<tr>
			<td class="td1">拥有红豆币:</td>
			<td class="td2">${bean.rbcAmount}</td>
		</tr>
		<tr>
			<td class="td1">赠送红豆币:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="rbcGet"  data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
	
	</table>
    </form>
    </div>
</body>
</html>



