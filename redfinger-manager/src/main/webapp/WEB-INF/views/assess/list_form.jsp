 <%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html  lang="zh-cn">
<head>
<title>添加被考核人</title>
<meta name="decorator" content=default />
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
			<c:if test="${not empty bean.id}">
				<input type="hidden" name="id" value="${bean.id}" /><!-- 这里要注意:name属性的值要与对应实体类的属性名相同-->
			</c:if>
			<table id="module_submit_table" class="easyui-table">
				<tr>
					<td class="td1">被考核人的姓名:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="targetAdmin" value="${bean.targetAdmin }" data-options="required:true" /></td>
				</tr>
				<tr>
					<td class="td1">考核题目的编号:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="projectId" value="${bean.projectId }" data-options="required:true" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>



