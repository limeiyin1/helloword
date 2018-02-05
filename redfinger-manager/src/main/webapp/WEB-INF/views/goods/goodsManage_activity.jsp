<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>活动赠送</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	
</script>
</head>
<body>
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post">
			<table id="module_submit_table">
				<tr style="margin-top:25px">
					<td class="td1" style="border-bottom:1px dashed black;width:30%">活动赠送红豆</td>
					<td class="td1" style="border-bottom:1px dashed black"></td>
				</tr>
				<c:forEach var="a" items="${activitieList}" varStatus="vs">
					<tr>
						<td class="td1">${a.activityName }:</td>
						<td class="td2">
							<input class="easyui-numberbox easyui-alidatebox" disabled type="text" name="rbcAmount" value="${actGoodsMap[a.activityId].rbcAmount}" data-options="required:true,min:0" />
						</td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</div>
</body>
</html>



