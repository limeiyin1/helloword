<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>发布公告</title>
<meta name="decorator" content="default" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
			url : host + 'umeng',
			success : callback,
		});

		function checktext(text) {
			allValid = true;
			for (i = 0; i < text.length; i++) {
				if (text.charAt(i) != " ") {
					allValid = false;
					break;
				}
			}
			return allValid;
		}

		function gbcount(message, total, used, remain) {
			var max;
			max = total.value;
			if (message.value.length > max) {
				message.value = message.value.substring(0, max);
				used.value = max;
				remain.value = 0;
				alert("内容不能超过 680 个字!");
			} else {
				used.value = message.value.length;
				remain.value = max - used.value;
			}
		}
	</script>
	<div id="module_submit_container">
		<div>
			<form id="easyui-form" class="easyui-form" method="post"onSubmit="return datacheck();">
				<h3 align="center">标题：<input type="text" name="noticeTitle" value="公告" /></h3>
				<div>
					<fieldset>
						<legend>请输入内容</legend>
						<textarea name="noticeContent" rows="10" wrap=virtual cols="75"
							onKeyDown="gbcount(this.form.noticeContent,this.form.total,this.form.used,this.form.remain);"
							onKeyUp="gbcount(this.form.noticeContent,this.form.total,this.form.used,this.form.remain);"></textarea>
						<p>
							最多字数： <input disabled maxLength="4" name="total" size="3"value="680" class="inputtext"> 
							已用字数： <input disabled maxLength="4" name="used" size="3" value="0" class="inputtext">
							剩余字数： <input disabled maxLength="4" name="remain" size="3"value="680" class="inputtext">
						</p>
					</fieldset>
					<!-- <input type="radio" name="popStatus" value="1">强制弹出 <input type="radio" name="popStatus" value="0" checked="checked">不强行弹出
						<br>
						优先级:<input class="easyui-textbox" type="text"name="reorder" value="1"  /> -->
				</div>
				<%-- <c:if test="${empty userList}">全部</c:if>
				<c:if test="${not empty userList}">
					<p>人数:${userList.size()}</p>
					<table>
						<tr>
							<td>用户名</td>
							<td>邮箱</td>
						</tr>
						<c:forEach items="${userList}" var="one">
							<tr>
								<td><input type="hidden" name="uidS" value="${one.userId}"> ${one.userMobilePhone}</td>
								<td>${one.userEmail}</td>
							</tr>
						</c:forEach>
					</table>
				</c:if> --%>
				<input type="hidden" id="isTotal" name="isTotal" value="0">
				<input type="hidden" id="userId" name="userId" value="${userId }">
				<input type="hidden" id="userEmail" name="userEmail" value="${userEmail }">
				<input type="hidden" id="userMobilePhone" name="userMobilePhone" value="${userMobilePhone }">
				<input type="hidden" id="beginTimeStr" name="beginTimeStr" value="${beginTimeStr }">
				<input type="hidden" id="endTimeStr" name="endTimeStr" value="${endTimeStr }">
			</form>
		</div>
	</div>
</body>
</html>
