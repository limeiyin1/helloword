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
		
		function md5ShaSelect(){
			var md5ShaSum = $("#md5ShaSum").combobox('getValue');
			if(md5ShaSum == "1"){
				$("#md5_tr,#sha_tr").addClass('hide');
			}else{
				$("#md5_tr,#sha_tr").removeClass('hide');
			}
		}
		
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
	<input type="hidden" name="gameAppId" value="${bean.gameAppId}"/>
	<table id="module_submit_table">
		<tr>
			<td class="td1">游戏名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="gameAppName"value="${bean.gameAppName }"data-options="required:true" /></td>
		</tr>
		<tr>
			<td class="td1">发行商:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="gameCompay"value="${bean.gameCompay }"data-options="required:false" /></td>
		</tr>
		
		<tr>
			<td class="td1">游戏描述:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="gameDesc" value="${bean.gameDesc }" data-options="multiline:true,required:false,validType:'length[0,70]'" style="height:60px"/></td>
		</tr>
		<tr>
			<td class="td1">图标:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="gameImage"value="${bean.gameImage }"data-options="required:false" /></td>
		</tr>
		
		<tr>
			<td class="td1">应用类别:</td>
			<td class="td2">
				<select class="easyui-combobox input_width_short" id="softType" name="softType" validType="selectValueRequired['softType']" data-options="required:true,editable:false" style="width:100px">
					<option value="">[全部]</option>
				    <fns:getOptions category="rf_game.soft_type" value="${bean.softType}"></fns:getOptions>
			 	</select>
			</td>
		</tr>
		
		<tr>
			<td class="td1">排序:</td>
			<td class="td2">
		    <input  class="easyui-numberbox" type="text" name="reorder" value="${bean.reorder }" data-options="min:0," />
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



