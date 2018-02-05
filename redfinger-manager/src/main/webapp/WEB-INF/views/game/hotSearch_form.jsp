<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>游戏管理</title>
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
	<input type="hidden" name="id" value="${bean.id}"/>
	<table id="module_submit_table">
		<tr>
			<td class="td1">搜索名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="searchName"value="${bean.searchName }"data-options="required:true" /></td>
		</tr>
		<tr>
			<td class="td1">排序:</td>
			<td class="td2">
		    <input  class="easyui-numberbox" type="text" name="reorder" value="${bean.reorder }" />
		    </td>
		<tr>
		<tr>
			<td class="td1">描述:</td>
			<td class="td2"><input class="easyui-textbox" name="remark" value="${bean.remark }" data-options="multiline:true,validType:'length[0,200]'" style="height:60px" /></td>
		</tr>
	</table>
    </form>
    </div>
</body>
</html>



