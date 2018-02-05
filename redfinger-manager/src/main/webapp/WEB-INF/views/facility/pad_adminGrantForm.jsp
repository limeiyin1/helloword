<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>管理员授权 </title>
<meta name="decorator" content="default" />
</head>
<body>
	<script type="text/javascript">
		$(function(){
			var expireTime = "${expireTime}";
			expireTime = expireTime.replace(/-/g,"/");
			var expireDate = new Date(expireTime);
		 			$("#dt").datetimebox({
				onChange : function(newValue, oldValue) {
					var date = new Date(newValue);
					// 如果设备授权到期日期大于设备到期日期,需要重新选择
					if (date > expireDate) {
						$.messager.alert('警告','您选择的日期大于设备到期时间,请重新选择','warning');
					}else if (date < new Date()) {
						$.messager.alert('警告','您选择的日期无效,请重新选择','warning');
					}					
				}
			}); 
		 			$('#module_submit_form').form("submit",{
						onSubmit:function() {
							debugger;
							var boxDate = $("#dt").datetimebox("getValue");
							boxDate = boxDate.replace(/-/g,"/");
							var date = new Date(boxDate);
							if (date > expireDate) {
								$.messager.progress('close');
								$.messager.alert('警告','您选择的日期大于设备到期时间,请重新选择','warning');
								return false;
							}
							if (date != "" && date < new Date()) {
								$.messager.progress('close');
								$.messager.alert('警告','您选择的日期无效,请重新选择','warning');
								return false;
							}
							var isValid = $(this).form('validate');
							if (!isValid){
								$.messager.progress('close');	// 如果表单是无效的则隐藏进度条
								return false;
							}
							return true;
						}
		 			});
		 			
		 	
		});
	
	</script>
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post" action="${ctx}/facility/pad/adminGrant.html" >
			 <input type="hidden" name="padId" value="${pad.padId}" /> 
			<table id="module_submit_table">
				<tr>
					<td class="td1">设备名称：</td>
					<td class="td2" colspan="3">${pad.padName}</td>
				</tr>
				<tr>
					<td class="td1">设备剩余天数：</td>
					<td class="td2" colspan="3">${leftControlTime}</td>
				</tr>
				<tr>
					<td class="td1">设备到期时间：</td>
					<td class="td2" colspan="3">${expireTime}</td>
				</tr>
				<tr>
					<td class="td1">设备编码：</td>
					<td class="td2" colspan="3">${pad.padCode}<input type="hidden" name="padCode" value="${pad.padCode}" /></td>
					
				</tr>
				<tr>
					<td class="td1">授权账号：</td>
					<td class="td2" colspan="3"><input type="text" name="granteeAccount" class="easyui-validatebox" data-options="required:true"  /></td>
				</tr>
				<tr>
					<td class="td1">授权到期时间：</td>
					<td class="td2" colspan="3">
						<input name="grantDays" class="easyui-datetimebox" data-options="required:true,editable:false,showSeconds:true" style="width:175px"  id="dt"/>  
					</td>
				</tr>
				<%-- 默认有观看权限,这里的0是指没有操作权限, 1 是有操作权限   --%>
				<tr id="padClassify_main">
					<td class="td1">授权权限：</td>
					<td class="td2" colspan="3">
					<select class="easyui-combobox" style="width: 175px;" editable="false" name="grantOperate">
					<option value="1">可操作</option>
					<option  value="0">可观看</option>
			        </select>
			        
				     </td>
				</tr>
			</table>
    </form>
    </div>
</body>
</html>


