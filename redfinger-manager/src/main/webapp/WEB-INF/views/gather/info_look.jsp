<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>信息采集详情</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
	
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">	
	
	    <div title="信息采集详情">   
	        <table id="module_submit_table">
		        <c:forEach var="one" items="${list}">
					<tr>
						<td class="td1">${one.lable}:</td>
						<td class="td2">${one.value}</td>
					</tr>
				</c:forEach>
			</table>
	</div> 
	</form>
    </div>
</body>
</html>



