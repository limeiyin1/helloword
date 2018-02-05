<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>角色</title>
<meta name="decorator" content="moduleIndex" />
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

	<table id="module_submit_table">
		<tr>
			<td class="td1">编号:</td>
			<td class="td2"><c:if test="${not empty bean.roleCode }">${bean.roleCode}
			<input type="hidden" name="roleCode" value="${bean.roleCode}">
						</c:if> 
						<c:if test="${empty bean.roleCode }">
						<input class="easyui-textbox" type="text" name="roleCode"value="${bean.roleCode }"data-options="required:true,validType:'code'" />
						</c:if></td>
		</tr>
		<tr>
			<td class="td1">名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="roleName" value="${bean.roleName }" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		<tr>
			<td class="td1">排序:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="reorder" value="${bean.reorder }"  data-options="required:true,min:0,max:999" /></td>
		</tr>
		<tr>
			<td class="td1">描述:</td>
			<td class="td2"><input class="easyui-textbox" name="remark" value="${bean.remark }" data-options="multiline:true,validType:'length[0,200]'" style="height:60px" /></td>
		</tr>
	</table>
    </form>
    </div>
</body>
</html>



