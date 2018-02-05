<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>设备来源</title>
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
	<input type="hidden" name="facilityId" value="${facility.facilityId}">
	<table id="module_submit_table">
		<tr>
			<td class="td1">设备池编号:</td>
			<td class="td2">
				<c:choose>
					<c:when test="${facility.facilityCode==null}">
						<input class="easyui-textbox" type="text" name="facilityCode" value="${facility.facilityCode }" data-options="required:true,validType:'length[0,32]'" />
					</c:when>
					<c:otherwise>
						<input type="hidden" name="facilityCode" value="${facility.facilityCode }" />
						${facility.facilityCode}
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="td1">设备分类名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="facilityName" value="${facility.facilityName }" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		<tr>
			<td class="td1">排序:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="reorder" value="${facility.reorder }" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		<tr>
			<td class="td1">备注:</td>
			<td class="td2"><input class="easyui-textbox" name="remark" value="${facility.remark }" data-options="multiline:true,validType:'length[0,200]'" style="height:60px" /></td>
		</tr>
	</table>
    </form>
    </div>
</body>
</html>



