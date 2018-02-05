<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>用户</title>
<meta name="decorator" content="default" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'save',   
		    success:callback
		}); 
	
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
	<c:if test="${not empty bean.adminUserId}"><input type="hidden" name="adminUserId" value="${bean.adminUserId}"> </c:if>

	<table class="easyui-table" >
		<tr>
			<td class="td1">名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="adminUserName" value="${bean.adminUserName }" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		<tr>
			<td class="td1">账号:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="adminUserId" value="${bean.adminUserId }" data-options="required:true,validType:'length[0,32]'" <c:if test="${not empty bean.adminUserId}">disabled="true"</c:if>/></td>
		</tr>
		<tr>
			<td class="td1">部门:</td>
			<td class="td2"><input class="easyui-combotree" type="text" name="orgCode" value="${bean.orgCode }" data-options='required:true,data:${orgTree }' /></td>
		</tr>
		<tr>
			<td class="td1">排序:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="reorder" value="${bean.reorder }" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		<tr>
			<td class="td1">描述:</td>
			<td class="td2"><input class="easyui-textbox" name="remarks" value="${bean.remark }" data-options="multiline:true,validType:'length[0,200]'" style="height:60px" /></td>
		</tr>
	</table>
    </form>
    </div>
</body>
</html>



