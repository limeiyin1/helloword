<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>绑定</title>
<meta name="decorator" content="default" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'bindGame',   
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
					<td class="td2"><input type="hidden" name="padCode" value="${pad.padCode}" />${pad.padCode}</td>
				</tr>
				<tr>
					<td class="td1">设备名称:</td>
					<td class="td2"><input type="hidden" name="padName"value="${pad.padName}" />${pad.padName}</td>
				</tr>
				<tr>
					<td class="td1">绑定的帐号：</td>
					<td class="td2"><input type="text" name="mid" class="easyui-textbox" data-options="required:true"/></td>
				</tr>
				<tr>
					<td class="td1" name="gamePadTime">游戏设备绑定后在线时间(单位：小时):</td>
					<td class="td2"><input class="easyui-numberbox" type="text" name="gamePadTime"  data-options="required:true,validType:'length[0,32]'" value="${gamePadTime }"/></td>
				</tr>
			</table>
    </form>
    </div>
</body>
</html>


