<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>产品类型</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'save',   
		    success:callback
		});
		
		$.extend($.fn.validatebox.defaults.rules, {  
			selectValueRequired: {
				validator: function(value,param){ 
					return $(param[0]).find("option:contains('"+value+"')").val() != '';  
				},  
				message: '该选项为必选项'  
			}  
		});
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
     <input type="hidden" name="typeId" value="${bean.typeId}">
	<table id="module_submit_table">
		<tr>
			<td class="td1">类型名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="typeName" value="${bean.typeName }" data-options="required:true,validType:'length[0,25]'" /></td>
		</tr>
		
		<tr>
			<td class="td1">按钮:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="btnTitle" value="${bean.btnTitle }" data-options="required:true,validType:'length[0,12]'" /></td>
		</tr>
		
		<tr>
			<td class="td1">添加云手机是否展示:</td>
			<td class="td2">
				<select class="easyui-combobox" id="isCloudShow" editable="false" name="isCloudShow" data-options="required:true">
					<option value="">请选择</option>
					<c:forEach items="${yesOrNo}" var="one">
						<option value="${one.key }" <c:if test="${one.key==bean.isCloudShow}">selected="selected"</c:if>>${one.value }</option>
					</c:forEach>
				</select> 
			</td>
		</tr>
		
		<tr>
			<td class="td1">排序:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="reorder" value="${bean.reorder }" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		<tr>
			<td class="td1">类型描述:</td>
			<td class="td2">
			<textarea name="typeDesc" class="easyui-validatebox" style="height:160px;width:280px;" validType="text[1,500]" required="true">${bean.typeDesc }</textarea>
			</td>
		</tr>
	</table>
    </form>
    </div>
</body>
</html>



