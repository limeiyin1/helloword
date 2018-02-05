<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>

<html>
<head>
<title>短信发送方</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	$(function() {
		$('.easyui-form').form({
			url : host + 'update',
			success : configCallback
		});
	});
</script>
</head>
<body>
	<div id="module_submit_container"  align="center">
		<form id="module_submit_form" class="easyui-form" method="post">
			<table class="easyui-table" >
	           <tr>
					<td class="td1">文字短信:</td>
					<td class="td2">
						<select class="easyui-combobox"  editable="false" data-options="required:true" name="smsTextSendType">
							<option value="">请选择</option>
							<fns:getOptions category="sms_send_type_text_key" value="${smsTextSendType}"></fns:getOptions>
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="td1">语音短信:</td>
					<td class="td2">
						<select class="easyui-combobox"  editable="false" data-options="required:true" name="smsVoiceSendType">
							<option value="">请选择</option>
							<fns:getOptions category="sms_send_type_voice_key" value="${smsVoiceSendType}"></fns:getOptions>
						</select>
					</td>
				</tr>
				
				<tr>
			        <td class="td1"></td>
		         	<td class="td2"> 
		         	<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-ok-rf" plain="false" onclick="submitForm()">保存</a>
		         	<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-undo" plain="false" onclick="resetForm()">重置</a>
		         	</td>
		        </tr>
				
			</table>
		</form>
	</div>
</body>


</html>