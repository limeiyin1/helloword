<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>角色</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'save',   
		    success:callback
		});
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
	<input type="hidden" name="smsTemplateId" value="${bean.smsTemplateId}"/>
	<table id="module_submit_table">
		<tr>
			<td class="td1">模版名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="smsTemplateName"value="${bean.smsTemplateName }"data-options="required:true" /></td>
		</tr>
		<tr>
			<td class="td1">模版内容:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="smsTemplateContent" value="${bean.smsTemplateContent }" data-options="multiline:true,required:true,validType:'length[0,70]'" style="height:80px"/></td>
		</tr>
		<tr>
			<td class="td1">排序:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="reorder" value="${bean.reorder }"  data-options="required:true,min:0,max:999" /></td>
		</tr>
		<tr>
			<td class="td1">描述:</td>
			<td class="td2"><input class="easyui-textbox" name="remark" value="${bean.remark }" data-options="multiline:true,validType:'length[0,200]'" style="height:60px" /></td>
		</tr>
	</table>
    </form>
    </div>
</body>
</html>



