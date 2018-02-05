<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>虚拟机安装应用白名单</title>
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
<input type="hidden" name="appWhiteId" value="${bean.appWhiteId}">
	<table id="module_submit_table">
		<tr>
			<td class="td1">应用包:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="packageName" value="${bean.packageName }" data-options="required:true,validType:'length[0,200]'" /></td>
		</tr>
		<tr>
			<td class="td1">设备类别:</td>
			<td class="td2"><c:if test="${bean.padClassify!=null}"><select class="easyui-combobox" disabled="disabled"  editable="false" name="auditStatus" data-options="required:true">
				<fns:getOptions category="rf_pad.pad_classify" value="${bean.padClassify}"></fns:getOptions>
			</select> </c:if>
			<c:if test="${bean.padClassify==null}"><select class="easyui-combobox"  editable="false" name="auditStatus" data-options="required:true">
				<fns:getOptions category="rf_pad.pad_classify"></fns:getOptions>
			</select> </c:if>
			</td>
		</tr>
		<tr>
			<td class="td1">应用名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="appName" value="${bean.appName }" data-options="required:true,validType:'length[0,200]'" /></td>
		</tr>
		<tr>
			<td class="td1">签名:</td>
			<td class="td2"><input class="easyui-textbox" style="height: 100px" type="text" name="manifestDigest" value="${bean.manifestDigest }" data-options="required:true,validType:'length[0,500]',multiline:true" /></td>
		</tr>
		<tr>
			<td class="td1">应用路径:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="packageUri" value="${bean.packageUri }" data-options="required:true,validType:'length[0,300]'" /></td>
		</tr>
		<tr>
			<td class="td1">排序:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="reorder" value="${bean.reorder }" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
        <tr>
			<td class="td1">审核状态:</td>
			<td class="td2">
				<select class="easyui-combobox"  editable="false" name="auditStatus" data-options="required:true">
					<fns:getOptions category="rf_app_white_list.audit_status" value="${bean.auditStatus}" ></fns:getOptions> 
				</select> 
			</td>
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



