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
			url : host + 'noticePadCode',
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
		
		
		function countPads(message,pads) {
		var padlist;
		padlist= message.value.split("\n").length;
		pads.value=padlist;
		}
	</script>
	<div id="module_submit_container">
		<div>
			<form id="easyui-form" class="easyui-form" method="post">
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
						name="reorder" value="1" />
				</div>
				   <table >
				      <tr>
						<td class="td1">设备清单:</td>
						<td class="td2"><fieldset>
									<legend>请输入内容</legend>
									<textarea   name="padCodes" wrap=PHYSICAL style="width: 220px;height: 200px" onKeyDown="countPads(this.form.padCodes,this.form.pads);"
									onKeyUp="countPads(this.form.padCodes,this.form.pads);"></textarea>
					                </fieldset>
					    </td>
					</tr>
					 <tr>
					    <td class="td1">已输入行数：</td>
					    <td class="td2"><input disabled maxlength="4" name="pads" size="3" value="0" class="inputtext"></td>
					 </tr>
				 </table>
			</form>
		</div>


	</div>
</body>
</html>
