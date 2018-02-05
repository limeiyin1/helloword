<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>导出任务</title>
<meta name="decorator" content="moduleIndex" />
</head>
<script type="text/javascript">
</script>
<body>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
	<table id="module_submit_table">
		<tr>
			<td class="td1">导出任务名:</td>
			<td class="td2"><input class="easyui-validatebox" type="text" id="exportTaskName" name="taskName" onkeyup="this.value=this.value.replace(/[^a-zA-Z0-9\u4e00-\u9fa5\-_]/g,'')"  data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
	</table>
		注：导出操作为异步队列操作，结果请于【批处理】下的【导出管理】下载查看
    </form>
    </div>
</body>
</html>



