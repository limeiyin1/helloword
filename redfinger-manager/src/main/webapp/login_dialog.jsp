<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>系统登录</title>
	<meta name="decorator" content="login"/>
	<script type="text/javascript">
	var count=0;
		$(document).ready(function() {
			$("#verificationCode_div").hide();
			if(count>=3){
					document.getElementById("verificationCode_img").src="${ctx}/login/getVerificationCode?date="+ new Date();
					$("#verificationCode_div").show();
					$(".control-group").css("height", "38px");
					}
			$("#loginName").keypress(function( event ) {
				if ( event.which == 13 ) {
					$("#loginPwd").focus();//聚焦的"passwd"元素上
				}
			});
			$("#loginPwd").keypress(function( event ) {
				if ( event.which == 13 ) {
					dialogLogin();
				}
			});
			$("#loginName").focus();
		});
	
		function dialogLogin(){
			var loginName = $("#loginName").val();
			var loginPwd = $("#loginPwd").val();
			var verificationCode = $("#verificationCode").val();
			login(loginName,loginPwd,verificationCode);
			//window.parent.login(loginName,loginPwd);
		}
		//
		function refresh(){
			document.getElementById("verificationCode_img").src="${ctx}/login/getVerificationCode?date="+ new Date();
		}
		
		var login=function (loginName,loginPwd,verificationCode){
		if(loginName==''){
			alert("账号不能为空！");
			return false;
		}
		if(loginPwd==''){
			alert("密码不能为空！");
			return false;
		}
		$.post("${ctx}/login/login",{login:loginName,passwd:loginPwd,verificationCode:verificationCode},function(data){
			if(jQuery.type(data)=='string'){
				data=eval('(' + data + ')'); 
			}
			//alert(data.code+"---"+data.msg);
			var code=parseInt(data.code);
			count=data.fail_count;
			switch(code){
				case 200:
					window.parent.login_success();
					break;
				case 500:
					alert('登录失败，请联系管理员！');
					break;
				case 501:
					alert(data.msg);
					if(count>=3){
					$("#verificationCode").val("");
					refresh();
					}
					break;
				case 999:
					window.parent.compulsionAlterPwd();
			}
			if(count>=3){
					$("#verificationCode_div").show();
					$(".control-group").css("height", "38px");
					}
		});
	}
		
		
	</script>
</head>
<body>
        <div id="login-wraper">
        		<form class="login-form">
                <legend><span style="color:#08c;">系统登录</span></legend>
                <div class="body">
					<div class="control-group">
						<div class="controls">
							<input type="text" id="loginName" name="loginName" class="required" value="" placeholder="账号" style="height: 30px;">
						</div>
					</div>
					<div class="control-group">
						<div class="controls">
							<input type="password" id="loginPwd" name="loginPwd" autocomplete="off" class="required" placeholder="密码" style="height: 30px;"/>
						</div>
					</div>
					
					<div class="control-group" id="verificationCode_div">
						<div class="controls">
							<input type="text" id="verificationCode" name="verificationCode"  placeholder="验证码" style="height: 30px;width: 80px"/>
							<img id="verificationCode_img"  onclick="refresh()">
							<!-- <a onclick="refresh()">刷新</a> -->
						</div>
					</div>
					
					<div class="footer">
	                    <input class="btn btn-primary" type="button" value="登 录" onclick="dialogLogin()"/>
                	</div>
                </div>
                </form>
        </div>
  </body>
</html>