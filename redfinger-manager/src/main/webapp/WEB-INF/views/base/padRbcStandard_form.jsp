<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>批处理任务</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script>
	<script type="text/javascript">
		$('.easyui-form').form({
		   url:host+'save',   
		   success:defaultCallback	
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
	<form id="module_submit_form" class="easyui-form" name="batch_submit_form" method="post" enctype="multipart/form-data">
	<div style="float:left;">
    <input type="hidden" name="standardId" value="${bean.standardId}">
	<table id="batch_submit_table">
	
		<tr>
					<td class="td1">设备等级:</td>
					<td class="td2">
						<select class="easyui-combobox"  editable="false" name="padGrade" data-options="required:true">
								<fns:getOptions category="rf_user_pad.pad_grade" value="${bean.padGrade}"  keys="rf_user_pad.pad_grade@general,rf_user_pad.pad_grade@vip,rf_user_pad.pad_grade@gvip"></fns:getOptions>
						</select> 
					</td>
		</tr>
		
		<tr>
			<td class="td1">红豆数量:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="rbcAmount" value="${bean.rbcAmount}" data-options="required:true" /></td>
		</tr>
		<tr>
			<td class="td1">兑换天数:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="days" value="${bean.days}" data-options="required:true" /></td>
		</tr>
		<tr>
			<td class="td1">排序:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="reorder" value="${bean.reorder}" data-options="required:true" /></td>
		</tr>
		
		
	</table>
	</div>
    </form>
    </div>
</body>
</html>



