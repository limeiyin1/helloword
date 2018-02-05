<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
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
			url : host + 'noticeTotalPush',
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
						<textarea name="noticeContent" rows="10" wrap=virtual cols="85"
							onKeyDown="gbcount(this.form.noticeContent,this.form.total,this.form.used,this.form.remain);"
							onKeyUp="gbcount(this.form.noticeContent,this.form.total,this.form.used,this.form.remain);"></textarea>
						<p>
							最多字数： <input disabled maxLength="4" name="total" size="3"value="680" class="inputtext"> 
							已用字数： <input disabled maxLength="4" name="used" size="3" value="0" class="inputtext">
							剩余字数： <input disabled maxLength="4" name="remain" size="3"value="680" class="inputtext">
						</p>
					</fieldset>
					<p>
					是否弹出：<input type="radio" name="popStatus" value="1">强制弹出 <input type="radio" name="popStatus" value="0" checked="checked">不强制弹出
					<p>
					<%
						Calendar c = Calendar.getInstance();
						c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+15);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String expiredTime = sdf.format(c.getTime()) + " 23:59:59";
					%>
					弹出有效期：
					<input type="text" id="popExpiredTimeStr" name="popExpiredTimeStr" class="easyui-datetimebox" value="<%=expiredTime%>" data-options="editable:false,showSeconds:true" />
					<span style="display:none">
					优先级:<input class="easyui-textbox" type="text"name="reorder" value="1"  />
					</span>
					</br>
					<input type="hidden" name="isPush" value="0">
					<!-- 是否推送公告:<input type="radio" name="isPush" value="1">是 <input type="radio" name="isPush" value="0" checked="checked">否 -->
				</div>
			</form>
		</div>
	</div>
</body>
</html>
