<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html  lang="zh-cn">
<head>
<title>打分</title>
<meta name="decorator" content=default />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'score',  
		    success:callback
		});
		
		
	</script>
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post">
			<c:if test="${not empty bean.id}">
				<input type="hidden" name="id" value="${bean.id}" />
			</c:if>
			<table id="module_submit_table" class="easyui-table">
				
				<c:forEach items="${optionlist}" var="option" varStatus="status">
				
				<tr>
					<td class="td1">题目${status.index+1}:</td>
					<td class="td2">${option.name}
						<input type="hidden" name="option" value="${option.id}" />
					</td>
					
				</tr>
				<tr>
					<td class="td1">分值:</td>
					<td class="td2">${option.point}</td>
				</tr>
				<tr>
					<td class="td1">评分:</td>
					<td class="td2"><input class="easyui-numberbox" type="number" 
						name="markscope" id="markscope" value=""
						data-options="required:true,min:${option.point<0?option.point:0},max:${option.point>0?option.point:0},precision:0"/></td>
				</tr>
				</c:forEach>
			</table>
		</form>
	</div>
</body>
</html>



