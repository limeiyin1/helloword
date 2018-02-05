<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>批量修改红豆</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'batch',   
		    success:callback
		});
		function countPhones(message,phones) {
			var phonelist;
			phonelist= message.value.split("\n").length;
			phones.value=phonelist;
			}
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
	<table id="module_submit_table">
		<tr>
			<td class="td1">调整红豆:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="rbcGet"  data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		
		<tr>
			<td class="td1">账号类型：</td>
			<td class="td2">
				<input type="radio" name="idType" value="1" checked="checked">手机号</input>
				<input type="radio" name="idType" value="2">会员ID</input>
			</td>
		</tr>
	     <tr>
			<td class="td1">会员清单:</td>
			<td class="td2"><fieldset>
						<legend>请输入内容</legend>
						<textarea name="userPhone" wrap=PHYSICAL style="width: 220px;height: 200px" onKeyDown="countPhones(this.form.userPhone,this.form.phons);"
						onKeyUp="countPhones(this.form.userPhone,this.form.phons);"></textarea>
		                </fieldset>
		    </td>
		 </tr>
		 <tr>
		    <td class="td1">已输入行数：</td>
		    <td class="td2"><input disabled maxlength="4" name="phons" size="3" value="0" class="inputtext"></td>
		 </tr>
	</table>
    </form>
    </div>
</body>
</html>



