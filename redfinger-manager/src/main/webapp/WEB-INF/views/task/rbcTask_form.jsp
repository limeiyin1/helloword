<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>红豆任务</title>
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
     <input type="hidden" name="taskId" value="${bean.taskId}">
	<table id="module_submit_table">
		<tr>
			<td class="td1">任务编码:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="taskCode" value="${bean.taskCode }" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		
		<tr>
			<td class="td1">任务名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="taskName" value="${bean.taskName}" data-options="required:true,validType:'length[0,255]'" /></td>
		</tr>
		
		<tr>
			<td class="td1">赠送红豆:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="rbcAmount" value="${bean.rbcAmount}" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		
		<tr>
			<td class="td1">排序:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="reorder" value="${bean.reorder }" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		<tr>
			<td class="td1">任务描述:</td>
			<td class="td2"><input class="easyui-textbox" name="taskDesc" value="${bean.taskDesc }" data-options="multiline:true,validType:'length[0,255]'"style="height:60px" /></td>
		</tr>

	</table>
    </form>
    </div>
</body>
</html>



