<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>查看二维码</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/qrcode.js"></script> 
    <%-- <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/qrcode.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script> --%>
	
	<script type="text/javascript">
		$('.easyui-form').form({url:host+'save',   success:callback});
		
		$(document).ready(function () {
		    var qrcode = new QRCode('qrcode', {
			  text: '',
			  width: 256,
			  height: 256,
			  colorDark : '#000000',
			  colorLight : '#ffffff',
			  correctLevel : QRCode.CorrectLevel.H
			});
			 qrcode.makeCode($('#downloadUrl').val());
		});
		
		
	</script>
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post">
		<input type="hidden" id="downloadUrl" value="${downloadUrl }">
		<div id="qrcode"></div>
	    </form>
    </div>
</body>
</html>



