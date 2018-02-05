<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>故障咨询</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
	
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
	<table id="module_submit_table">
			<tr>
				<td class="td1">${bean.creater}</td>
				<td class="td2">[<fmt:formatDate value="${bean.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>]&nbsp;&nbsp;提交故障</td>
			</tr>
		<c:forEach var="one" items="${list}">
			<tr>
				<td class="td1">${one.creater}</td>
				<td class="td2">[<fmt:formatDate value="${one.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>]&nbsp;&nbsp;受理故障</td>
			</tr>
			<c:if test="${not empty one.modifyTime }">
			<tr>
				<td class="td1">${one.modifier}</td>
				<td class="td2">
					<c:if test="${one.isSolve=='1'}">[<fmt:formatDate value="${one.modifyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>]&nbsp;&nbsp;解决故障，${one.remark }</c:if>
					<c:if test="${one.isSolve=='0'}">[<fmt:formatDate value="${one.modifyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>]&nbsp;&nbsp;${one.remark }</c:if>
				</td>
			</tr>
			</c:if>
		</c:forEach>
		<c:if test="${not empty bean.finishTime }">
		<tr>
			<td class="td1">${bean.creater}</td>
			<td class="td2">[<fmt:formatDate value="${bean.finishTime}" pattern="yyyy-MM-dd HH:mm:ss"/>]&nbsp;&nbsp;故障处理确认</td>
		</tr>	
		</c:if>
	</table>
    </form>
    </div>
</body>
</html>



