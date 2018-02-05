<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>图片</title>
<meta name="decorator" content="default" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
			url : host + 'image',
			success : callback,
		});
	</script>
	<div id="module_submit_container">

		<form id="easyui-form" class="easyui-form" method="post">
			<input type="hidden" name="apkId" value="${bean.id}" />
			<table id="module_submit_table" class="easyui-table">
				<c:forEach items="${imageList}" var="image">
					<c:if test="${image.category=='icon'}">

						<tr>
							<td class="td1">图标:</td>
							<td class="td2"><input class="easyui-textbox" type="text" size="60"
								name="icon" value="${image.url }" data-options="required:false" /></td>
						</tr>
					</c:if>
					<c:if test="${image.category=='desc'}">
						<tr>
							<td class="td1">图片:</td>
							<td class="td2"><input class="easyui-textbox" type="text" size="60"
								name="ids" value="${image.url }" data-options="required:false" /></td>
						</tr>
					</c:if>
					<c:if test="${image.category==null}">
						<tr>
							<td class="td1">图片:</td>
							<td class="td2"><input class="easyui-textbox" type="text" size="60"
								name="ids" value="${image.url }" data-options="required:false" /></td>
						</tr>

					</c:if>

				</c:forEach>

			</table>
		</form>

	</div>
</body>
</html>



