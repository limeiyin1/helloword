<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>游戏渠道管理</title>
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
	<input type="hidden" name="channelPlugId" value="${bean.channelPlugId}"/>
	<table id="module_submit_table">
		<tr>
			<td class="td1">渠道名称:</td>
			<td class="td2">
				<select class="easyui-combobox"  editable="false" id="gameChannelId" name="gameChannelId" >
					<option value="">[全部]</option>
					<c:forEach items="${gameChannels}" var="one">
						<option value="${one.channelId }" <c:if test="${one.channelId==bean.gameChannelId}">selected="selected"</c:if>>${one.channelName }</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td class="td1">插件名称:</td>
			<td class="td2">
				<select class="easyui-combobox"  editable="false" id="gameId" name="gameId" >
					<option value="">[全部]</option>
					<c:forEach items="${games}" var="one">
						<option value="${one.gameId }" <c:if test="${one.gameId==bean.gameId}">selected="selected"</c:if>>${one.gameName }</option>
					</c:forEach>
				</select>
			</td>
		</tr>
	</table>
    </form>
    </div>
</body>
</html>
