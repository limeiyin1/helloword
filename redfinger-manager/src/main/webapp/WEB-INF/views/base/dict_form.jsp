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
	<c:if test="${not empty bean.dictCode}"><input type="hidden" name="dictCode" value="${bean.dictCode}"> </c:if>

	<table class="easyui-table" >
		<tr>
			<td class="td1">key:</td>
			<td class="td2">
				<c:if test="${empty bean.dictCode}"><input class="easyui-textbox" type="text" name="dictCode" value="${bean.dictCode }" data-options="required:true,validType:'length[0,100]'" /></c:if>
				<c:if test="${not empty bean.dictCode}">${bean.dictCode }</c:if>
			</td>
		</tr>
		<tr>
			<td class="td1">类型:</td>
			<td class="td2">
				<c:if test="${empty bean.dictCode}">
				<input class="easyui-textbox" type="text" name="dictCategory" value="${bean.dictCategory }" data-options="required:true,validType:'length[0,100]'" />
				</c:if>
				<c:if test="${not empty bean.dictCode}">${bean.dictCategory}</c:if>
			</td>
		</tr>
		<tr>
			<td class="td1">label:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="dictName" value="${bean.dictName }" data-options="required:true,validType:'length[0,100]'" /></td>
		</tr>
		<tr>
			<td class="td1">value:</td>
			<td class="td2">
				<c:if test="${empty bean.dictCode}"><input class="easyui-textbox" type="text" name="dictValue" value="${bean.dictValue }" data-options="required:true,validType:'length[0,100]'" /></c:if>
				<c:if test="${not empty bean.dictCode}">${bean.dictValue }</c:if>
			</td>
		</tr>
		<tr>
			<td class="td1">样式:</td>
			<td class="td2">
				<select class="easyui-combobox" name="themes" data-options="required:true,editable:false">
					<option value="" <c:if test="${bean.themes=='' }">selected</c:if>>nothing</option>
					<option value="default" <c:if test="${bean.themes=='default' }">selected</c:if>>default</option>
					<option value="success" <c:if test="${bean.themes=='success' }">selected</c:if>>success</option>
					<option value="primary" <c:if test="${bean.themes=='primary' }">selected</c:if>>primary</option>
					<option value="info" <c:if test="${bean.themes=='info' }">selected</c:if>>info</option>
					<option value="warning" <c:if test="${bean.themes=='warning' }">selected</c:if>>warning</option>
					<option value="danger" <c:if test="${bean.themes=='danger' }">selected</c:if>>danger</option>
				</select>
			</td>
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



