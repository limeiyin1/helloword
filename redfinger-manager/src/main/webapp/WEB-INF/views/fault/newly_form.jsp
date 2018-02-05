<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>故障咨询</title>
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
	<input type="hidden" name="padCode" value="${bean.padCode}"/>
	<table id="module_submit_table">
		<tr>
			<td class="td1">故障设备编号:</td>
			<td class="td2">${bean.padCode}</td>
		</tr>
		<tr>
			<td class="td1">故障类型:</td>
			<td class="td2"><input class="easyui-combotree" type="text" name="classId" value="${bean.classId }" data-options='required:true,data:${categoryTree}' /></td>
		</tr>
		<tr>
			<td class="td1">故障描述:</td>
			<td class="td2"><input class="easyui-textbox" name="feedbackContent" value="${bean.feedbackContent }" data-options="required:false,multiline:true,validType:'length[0,200]'" style="height:60px" /></td>
		</tr>
		<tr>
			<td class="td1">联系电话:</td>
			<td class="td2"><input class="easyui-textbox" type="text" id="feedbackContact" name="feedbackContact" value="${bean.feedbackContact }"  /></td>
		</tr>
		<tr>
			<td class="td1">联系QQ:</td>
			<td class="td2"><input class="easyui-textbox" type="text" id="feedbackQq" name="feedbackQq" value="${bean.feedbackQq }"  /></td>
		</tr>
		<tr>
			<td class="td1">故障来源:</td>
			<td class="td2">
				<select class="easyui-combobox"  editable="false" data-options="required:true" name="feedbackSource">
					<fns:getOptions category="rf_fault_feedback.feedback_source" keys="rf_fault_feedback.feedback_source@phone,rf_fault_feedback.feedback_source@qq,rf_fault_feedback.feedback_source@weixin"></fns:getOptions>
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



