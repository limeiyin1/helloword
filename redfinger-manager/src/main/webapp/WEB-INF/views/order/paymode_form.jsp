<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>添加考核题目</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
			url : host + 'save',
			success : callback
		});
	</script>
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post">
			<input type="hidden" name="payModeCode" value="${bean.payModeCode}">
			<table id="module_submit_table">
				<tr>
					<td class="td1">图片地址480:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="payModePhoto" value="${bean.payModePhoto.split(',')[0] }"
						data-options="required:true,validType:'length[0,200]'" /></td>
				</tr>
				<tr>
					<td class="td1">图片地址720:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="payModePhoto" value="${bean.payModePhoto.split(',')[1]  }"
						data-options="required:true,validType:'length[0,200]'" /></td>
				</tr>
				<tr>
					<td class="td1">图片地址1080:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="payModePhoto" value="${bean.payModePhoto.split(',')[2]  }"
						data-options="required:true,validType:'length[0,200]'" /></td>
				</tr>
				<tr>
					<td class="td1">排序:</td>
					<td class="td2"><input class="easyui-textbox" type="text" name="reorder" value="${bean.reorder }"
						data-options="required:true" /></td>
				</tr>
				<tr>
					<td class="td1">是否默认:</td>
					<td class="td2">
						<select editable="false" name="isDefault" class="easyui-combobox input_width_default">
							<option value="">[全部]</option>
							<c:forEach var="one" items="${yesOrNo}">
								<option value="${one.key}" <c:if test="${bean.isDefault == one.key }">selected="selected"</c:if>>${one.value}</option>
							</c:forEach>
						</select>	
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>



