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
     <input type="hidden" name="labelId" value="${bean.labelId}">
	<table id="module_submit_table">
		<tr>
			<td class="td1">标签编码:</td>
			<td class="td2">
				<select class="easyui-combobox" id="labelCode" editable="false" name="labelCode" data-options="required:true">
					<option value="">请选择</option>
					<fns:getOptions category="rf_label.label_code"  value="${bean.labelCode }"></fns:getOptions>
				</select> 
			</td>
		</tr>
		<tr>
			<td class="td1">标签名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="labelName" value="${bean.labelName }" data-options="required:true,validType:'length[0,25]'" /></td>
		</tr>
		<tr>
			<td class="td1">标签类型:</td>
			<td class="td2">
				<select class="easyui-combobox" id="labelType" editable="false" name="labelType" data-options="required:true">
					<option value="">请选择</option>
					<fns:getOptions category="rf_label.label_type"  value="${bean.labelType }"></fns:getOptions>
				</select> 
			</td>
		</tr>
		
		<tr>
			<td class="td1">排序:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="reorder" value="${bean.reorder }" data-options="required:false,validType:'length[0,32]'" /></td>
		</tr>
	</table>
    </form>
    </div>
</body>
</html>



