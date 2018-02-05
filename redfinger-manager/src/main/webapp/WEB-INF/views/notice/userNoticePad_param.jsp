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
			url : host + 'pushNotice',
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
			<form id="easyui-form" class="easyui-form" method="post" >
				<h3 align="center">
					标题：<input type="text" name="noticeTitle" value="公告" />
				</h3>
				<div>
					<fieldset>
						<legend>请输入内容</legend>
						<textarea name="noticeContent" rows="10" wrap=virtual cols="75"
							onKeyDown="gbcount(this.form.noticeContent,this.form.total,this.form.used,this.form.remain);"
							onKeyUp="gbcount(this.form.noticeContent,this.form.total,this.form.used,this.form.remain);"></textarea>
						<p>
							最多字数： <input disabled maxLength="4" name="total" size="3"
								value="680" class="inputtext"> 已用字数： <input disabled
								maxLength="4" name="used" size="3" value="0" class="inputtext">
							剩余字数： <input disabled maxLength="4" name="remain" size="3"
								value="680" class="inputtext">
							</td>
						</p>
					</fieldset>
					<input type="radio" name="popStatus" value="1">强制弹出 <input
						type="radio" name="popStatus" value="0" checked="checked">不强行弹出
					<br> 优先级:<input class="easyui-textbox" type="text"
						name="reorder" value="1" /></br>
						<input type="hidden" name="isPush" value="0">
						<!-- 是否推送公告:<input type="radio" name="isPush" value="1">是 <input type="radio" name="isPush" value="0" checked="checked">否 -->
				</div>
				
				
				<table id="module_submit_table">
					<tr>
						<td class="td1">设备名称:</td>
						<td class="td2"><input class="easyui-textbox" type="text" name="padName" data-options="validType:'length[0,32]'"  />以设备名称开头</td>
					</tr>
					<tr>
						<td class="td1">设备编号:</td>
						<td class="td2"><input class="easyui-textbox" type="text" name="padCode" data-options="validType:'length[0,32]'"  />以设备编号开头</td>
					</tr>
					<tr>
						<td class="td1">设备IP:</td>
						<td class="td2"><input class="easyui-textbox" type="text" name="padIp"  data-options="validType:'length[0,32]'" />以设备IP开头</td>
					</tr>
					
				</table>

			</form>
		</div>


	</div>
</body>
</html>
