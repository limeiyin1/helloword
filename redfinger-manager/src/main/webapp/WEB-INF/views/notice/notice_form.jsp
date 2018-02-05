<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>图片编辑</title>
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
			<input type="hidden" name="noticeId" value="${bean.noticeId}">
			<table id="module_submit_table">
				<tr>
					<td class="td1">标题:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="noticeTitle" value="${bean.noticeTitle}"
						data-options="required:true,validType:'length[0,100]'" /></td>
				</tr>
				
				<tr>
					<td class="td1">内容:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="noticeContent" value="${bean.noticeContent}"
						data-options="required:true,validType:'length[0,1000]'" /></td>
				</tr>
				
				<tr>
					<td class="td1">优先级:</td>
					<td class="td2">
						<input class="easyui-textbox" type="text" name="reorder"
								value="${bean.reorder }"
								data-options="required:true,min:0,max:999" />
					</td>
				</tr>
				
				<tr>
					<td class="td1">是否弹出:</td>
					<td class="td2">
						<select class="easyui-combobox"  editable="false" name="popStatus" data-options="required:true">
							<c:forEach items="${yesOrNo}" var="one">
								<option value="${one.key }" <c:if test="${one.key==bean.popStatus}">selected="selected"</c:if>>${one.value }</option>
							</c:forEach>
						</select> 
					</td>
				</tr>
				
				<tr>
					<td class="td1">备注:</td>
					<td class="td2"><input class="easyui-textbox" name="remark"
						value="${bean.remark}"
						data-options="multiline:true,validType:'length[0,1000]'"
						style="height: 120px" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>



