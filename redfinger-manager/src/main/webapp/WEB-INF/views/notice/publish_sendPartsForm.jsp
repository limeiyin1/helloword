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
		 url:host+'sendPartNotice', 
			success : callback
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
		
		function countMobiles(message,phones) {
			var padlist;
			userMobilePhoneList = message.value.split("\n").length;
			phones.value = userMobilePhoneList;
		}
	</script>
	<div id="module_submit_container">
		<div>
			<form id="easyui-form" class="easyui-form" method="post"onSubmit="return datacheck();">
                <h3 align="center">标题：<input type="text" name="noticeTitle" value="公告" /></h3>
				<div>
					<fieldset>
						<legend>公告内容</legend>
						<textarea name="noticeContent" rows="10" wrap="virtual"  cols="75"
							onKeyDown="gbcount(this.form.noticeContent,this.form.total,this.form.used,this.form.remain);"
							onKeyUp="gbcount(this.form.noticeContent,this.form.total,this.form.used,this.form.remain);"></textarea>
						<p>
							最多字数： <input disabled maxLength="4" name="total" size="3"value="680" class="inputtext"> 
							已用字数： <input disabled maxLength="4" name="used" size="3" value="0" class="inputtext">
							剩余字数： <input disabled maxLength="4" name="remain" size="3"value="680" class="inputtext">
							</td>
						</p>
					</fieldset>
                          <input  type="radio" name="popStatus" value="1">强制弹出  <input  type="radio" name="popStatus" value="0" checked="checked" >不强行弹出
                          <br>
                       <span style="display:none">
						优先级：<input class="easyui-textbox" type="text"name="reorder" value="1"  /></br>
					</span>
					<%
					Calendar c = Calendar.getInstance();
					c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+15);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String expiredTime = sdf.format(c.getTime()) + " 23:59:59";
					%>
					弹出有效期：
					<input type="text" id="popExpiredTimeStr" name="popExpiredTimeStr" class="easyui-datetimebox" value="<%=expiredTime%>" data-options="editable:false,showSeconds:true" />
				
					<input type="hidden" name="isPush" value="0">
					<!-- 是否推送公告:<input type="radio" name="isPush" value="1">是 <input type="radio" name="isPush" value="0" checked="checked">否 -->
				</div> 
				
				<div>
					<fieldset>
						<legend>号码</legend>
						<textarea name="userMobilePhones" wrap=PHYSICAL style="width: 220px;height: 200px" onKeyDown="countMobiles(this.form.userMobilePhones,this.form.phones);"
						onKeyUp="countMobiles(this.form.userMobilePhones,this.form.phones);"></textarea>
		            </fieldset>
		            </br>
		            <input disabled maxlength="4" name="phones" size="3" value="0" class="inputtext">
				</div> 
				<%-- <p>人数:${userSize}</p> --%>
			</form>
		</div>
	</div>
</body>
</html>
