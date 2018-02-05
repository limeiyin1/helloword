<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>标签管理</title>
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
		
		function choiceLabelType(){
			var labelType = $("#labelType").combobox('getValue');
			$("tr[name=userLabelTr],tr[name=padLabelTr]").addClass("hide");
			if(labelType == '1'){
				$("tr[name=userLabelTr]").removeClass("hide");
			}else if(labelType == '2'){
				$("tr[name=padLabelTr]").removeClass("hide");
			}
		
		}
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
     <input type="hidden" name="labelId" value="${bean.labelId}">
	<table id="module_submit_table">
		<tr>
			<td class="td1">标签类型:</td>
			<td class="td2">
				<select class="easyui-combobox" id="labelType" editable="false" name="labelType" data-options="required:true,onSelect:choiceLabelType">
					<option value="">请选择</option>
					<fns:getOptions category="rf_label.label_type"  value="${bean.labelType }"></fns:getOptions>
				</select> 
			</td>
		</tr>
		<!-- 用户标签 -->
		<tr name="userLabelTr" class="${bean.labelType=='1'?'show':'hide'}">
			<td class="td1">用户标签编码:</td>
			<td class="td2">
				<select class="easyui-combobox" id="userLabelCode" editable="false" name="userLabelCode" data-options="required:false">
					<fns:getOptions category="system.user_label"  value="${bean.labelCode }"></fns:getOptions>
				</select> 
			</td>
		</tr>
		<!-- 设备标签 -->
		<tr name="padLabelTr" class="${bean.labelType=='2'?'show':'hide'}">
			<td class="td1">设备标签编码:</td>
			<td class="td2">
				<select class="easyui-combobox" id="padLabelCode" editable="false" name="padLabelCode" data-options="required:false">
					<fns:getOptions category="system.pad_label"  value="${bean.labelCode }"></fns:getOptions>
				</select> 
			</td>
		</tr>
		
		
		<tr>
			<td class="td1">标签名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="labelName" value="${bean.labelName }" data-options="required:true,validType:'length[0,25]'" /></td>
		</tr>
		
		
		<tr>
			<td class="td1">排序:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="reorder" value="${bean.reorder }" data-options="required:false,min:0,max:1000" /></td>
		</tr>
	</table>
    </form>
    </div>
</body>
</html>



