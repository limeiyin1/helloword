<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>发送短信</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    success:smsCallback
		});
		var templateListJson=${templateListJson};
		function templateSelect(record) {
			for(var i=0;i<templateListJson.length;i++){
				if(templateListJson[i].smsTemplateId==record.value){
					$("#smsContent").textbox('setValue', templateListJson[i].smsTemplateContent);
				}
			}
		}
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
	<table id="module_submit_table">
		<tr>
			<td class="td1">批次名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="batchName"value=""data-options="required:true" /></td>
		</tr>
		<tr>
			<td class="td1">短信模版:</td>
			<td class="td2">
				<select class="easyui-combobox" name="templateId" editable="false" data-options="onSelect:templateSelect">
					<c:forEach items="${templateList}" var="one">
						<option val="${one.smsTemplateContent }" value="${one.smsTemplateId }">${one.smsTemplateName }</option>
					</c:forEach>  
				</select>
			</td>
		</tr>
		<tr>
			<td class="td1">短信内容:</td>
			<td class="td2"><input class="easyui-textbox" id="smsContent" name="smsContent" value="${defaultContent}" data-options="required:true,multiline:true,validType:'length[0,70]'" style="height:80px" /></td>
		</tr>
	</table>
    </form>
    </div>
</body>
</html>



