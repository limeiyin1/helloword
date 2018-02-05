<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>节点控制关系</title>
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
	<input type="hidden" name=templateId value="${bean.templateId}">
	<table id="module_submit_table">
			
			<tr>
				<td class="td1">消息模板类型:</td>
				<td class="td2">
					<select class="easyui-combobox"  editable="false" name="templateType" data-options="required:true,value:'${bean.templateType}'">
						<fns:getOptions category="fault.msg_template"/>
					</select> 
				</td>
			</tr>
			<tr>
				<td class="td1">模板内容:</td>
				<td class="td2">
					<textarea rows="8" cols="40" name="templateText" >${bean.templateText}</textarea>
				</td>
			</tr>
			<tr>
				<td class="td1">排序:</td>
				<td class="td2">
					<input type="text" name="reorder" class="easyui-textbox input_width_default" value="${bean.reorder}"/>
				</td>
			</tr>
			
	</table>
    </form>
    </div>
</body>
</html>



