<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>故障类型</title>
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
	<c:if test="${not empty bean.classId}"><input type="hidden" name="classId" value="${bean.classId}"/></c:if>
	<c:if test="${empty parent.className }"><input type="hidden" name="classPid" value="0" /></c:if>
	<c:if test="${not empty parent.className }"><input type="hidden" name="classPid" value="${parent.classId}" /></c:if>
	<table id="module_submit_table">
	    <tr>
			<td class="td1">上级类型:</td>
			<td class="td2">
				<c:if test="${not empty parent.className }">${parent.className}</c:if>
				<c:if test="${empty parent.className }">无</c:if>
			</td>
		</tr>
		<tr>
			<td class="td1">名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="className" value="${bean.className }" data-options="required:true,validType:'length[0,500]'" /></td>
		</tr>
		<c:if test="${empty parent.className && empty bean.classId}">
		<tr>
			<td class="td1">分类类型:</td>
			<td class="td2">
				<select class="easyui-combobox"  editable="false" name="classType" data-options="required:false">  
					<c:forEach items="${classList}" var="one">
						<option value="${one.dictValue }">${one.dictName }</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		</c:if>
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



