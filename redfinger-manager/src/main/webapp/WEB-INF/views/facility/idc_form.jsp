<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>机房</title>
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
<input type="hidden" name="idcId" value="${bean.idcId}">
	<table id="module_submit_table">
		<tr>
			<td class="td1">机房编号:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="idcCode" value="${bean.idcCode }" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		<tr>
			<td class="td1">机房名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="idcName" value="${bean.idcName }" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		<tr>
			<td class="td1">机房地址:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="idcAddres" value="${bean.idcAddres }" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		<tr>
			<td class="td1">文件服务地址:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="fileServer" value="${bean.fileServer }" data-options="required:false,validType:'length[0,32]'" /></td>
		</tr>
		<tr>
			<td class="td1">内部命令服务地址:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="commandServer" value="${bean.commandServer }" data-options="required:false,validType:'length[0,100]'" /></td>
		</tr>
		<tr>
			<td class="td1">用户截图地址:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="userScreencapServer" value="${bean.userScreencapServer }" data-options="required:false,validType:'length[0,100]'" /></td>
		</tr>
		<tr>
			<td class="td1">脚本上传地址:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="uploadServer" value="${bean.uploadServer }" data-options="required:false,validType:'length[0,100]'" /></td>
		</tr>
		<tr>
			<td class="td1">排序:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="reorder" value="${bean.reorder }" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>

		<tr>
			<td class="td1">备注:</td>
			<td class="td2"><input class="easyui-textbox" name="remark" value="${bean.remark }" data-options="multiline:true,validType:'length[0,200]'" style="height:60px" /></td>
		</tr>
	</table>
    </form>
    </div>
</body>
</html>



