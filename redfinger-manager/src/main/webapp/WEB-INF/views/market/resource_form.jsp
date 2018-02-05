<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>APP应用市场</title>
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
	<input type="hidden" name="id" value="${bean.id}">
	<table id="module_submit_table">
		<tr>
			<td class="td1">名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="name" value="${bean.name }" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		<tr>
			<td class="td1">排序:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="reorder" value="${bean.reorder }"  data-options="required:true,min:0,max:999" /></td>
		</tr>
		<tr>
			<td class="td1">抓取地址:</td>
			<td class="td2"><input class="easyui-textbox" name="url" value="${bean.url }" data-options="required:true,multiline:true,validType:'length[0,1000]'" style="height:120px" /></td>
		</tr>
		<tr>
			<td class="td1">程序抓取类:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="programService" value="${bean.programService }" data-options="required:true,validType:'length[0,32]'" /></td>
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



