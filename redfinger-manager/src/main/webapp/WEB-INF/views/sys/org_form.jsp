<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>部门组织</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    success:callback
		});
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
	
	<table id="module_submit_table">
	    <tr>
			<td class="td1">上级部门:</td>
			<td class="td2">
				<c:if test="${not empty parent.orgName }">${parent.orgName}<input type="hidden" name="parentOrgCode" value="${parent.orgCode}"></c:if>
				<c:if test="${empty parent.orgName }">无<input type="hidden" name="parentOrgCode" value="0"></c:if>
			</td>
		</tr>
		<tr>
			<td class="td1">编码:</td>
			<td class="td2">
				<c:if test="${not empty bean.orgCode }">${bean.orgCode}<input type="hidden" name="orgCode" value="${bean.orgCode}"></c:if>
				<c:if test="${empty bean.orgCode }"><input class="easyui-textbox" type="text" name="orgCode" value="${bean.orgCode }" data-options="required:true,validType:'code'" /></c:if>
			</td>
		</tr>
		<tr>
			<td class="td1">名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="orgName" value="${bean.orgName }" data-options="required:true,validType:'length[0,32]'" /></td>
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



