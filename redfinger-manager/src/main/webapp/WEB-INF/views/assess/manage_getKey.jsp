<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>私钥</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post">
			<table id="module_submit_table">

				<tr>
					<td class="td2"><font size="3">${name}：</font></td>
				</tr>
				<tr>
					<td class="td2">${privateKey}</td>
				</tr>
				<tr>
					<td class="td1"><font size="2" color="red"> p s :
							私钥请妥善保存！</font></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>