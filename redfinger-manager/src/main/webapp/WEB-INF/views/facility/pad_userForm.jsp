<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>绑定</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'binding',   
		    success:callback
		}); 
		
		//扩展easyui表单的验证  
		$.extend($.fn.validatebox.defaults.rules, {  
		    //移动手机号码验证  
		    mobile: {//value值为文本框中的值  
		        validator: function (value) {  
		            var reg = /^1[3|4|5|8|9]\d{9}$/;  
		            return reg.test(value);  
		        },  
		        message: '输入手机号码格式不准确.'  
		    },  
		})  
		
	</script>

	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post">
			<input type="hidden" name="padId" value="${pad.padId}" />
			<table id="module_submit_table">
				<tr>
					<td class="td1">设备编号：</td>
					<td class="td2"><input type="text" readonly="readonly"
						name="padCode" class="easyui-textbox " value="${pad.padCode}" /></td>
				</tr>
				<tr>
					<td class="td1">设备名称：</td>
					<td class="td2"><input type="text" readonly="readonly"
						readonly="readonly" name="padName" class="easyui-textbox"
						value="${pad.padName}" /></td>
				</tr>
				
				<tr>
					<td class="td1">账号类型：</td>
					<td class="td2">
						<input type="radio" name="idType" value="1" checked="checked">手机号</input>
						<input type="radio" name="idType" value="2">会员ID</input>
					</td>
				</tr>
				
				
				<tr>
					<td class="td1">用户帐号：</td>
					<td class="td2"><input type="text" name="mid"
						class="easyui-textbox" /></td>
				</tr>
				<!-- <tr>
					<td class="td1">用户手机：</td>
					<td class="td2"><input type="text" name="bindMobile"
						class="easyui-textbox " validtype="mobile" /></td>
				</tr> -->
			</table>
		</form>
	</div>
</body>
</html>


