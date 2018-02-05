<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>系统登录</title>
	<meta name="decorator" content="login"/>
	<style type="text/css">
		.control-group{border-bottom:0px;}
	</style>
	<link rel="shortcut icon" href="${ctxStatic}/images/redfinger-${fns:getEnv()}.ico" />
    <script src="${ctxStatic}/js/backstretch.min.js"></script>
	<script type="text/javascript">
	var count=0;
		$(document).ready(function() {
			$.backstretch([
 		      "${ctxStatic}/images/bg1.jpg", 
 		      "${ctxStatic}/images/bg2.jpg",
 		      "${ctxStatic}/images/bg3.jpg"
 		  	], {duration: 10000, fade: 2000});
			$("#login").keypress(function( event ) {
				if ( event.which == 13 ) {
					$("#passwd").focus();//聚焦的"passwd"元素上
				}
			});
			$("#passwd").keypress(function( event ) {
				if ( event.which == 13 ) {
					login1();
				}
			});
			
			$("#verificationCode_div").hide();
			if(${fail_count>=3}){
				document.getElementById("verificationCode_img").src="${ctx}/login/getVerificationCode?date="+ new Date();
				$("#verificationCode_div").show();
				$(".control-group").css("height", "38px");
			}
			
		});
		var loginCallback=function (data,fun){
			 if(jQuery.type(data)=='string'){
				data=eval('(' + data + ')'); 
			}
			var code=parseInt(data.code);
			var str=data.msg;
			//alert(code+"---"+str+"---"+data.fail_count);
			switch(code){
				case 200:
					window.location.href=ctx;//当前页面打开URL页面
					break;
				case 500:
					alert('登录失败，请联系管理员！');
					break;
				case 501:
					count=data.fail_count;
					
					if(count>=3){
					$("#verificationCode").val("");
					refresh();
					}
					
					alert(str);
					break;
				case 999:
					window.location.href=ctx+"/compulsionAlterPwd.jsp";//当前页面打开URL页面
					break;
			}
			 if(count>=3){
				$("#verificationCode_div").show();
				$(".control-group").css("height", "38px");
			}
		}
 		var callback1=loginCallback;
		var login1=function (){
			var login=$("#login").val();
			var passwd=$("#passwd").val();
			var verificationCode=$("#verificationCode").val();
			if(login==''){
				alert("账号不能为空！");
				return false;
			}
			if(passwd==''){
				alert("密码不能为空！");
				return false;
			}
			$.post("${ctx}/login/login",{login:login,passwd:passwd,verificationCode:verificationCode},callback1);
		} 

function refresh(){
	document.getElementById("verificationCode_img").src="${ctx}/login/getVerificationCode?date="+ new Date();
}
	</script>
</head>
<body>
	<script type="text/javascript">
		// 如果在框架中，则跳转刷新上级页面
		if(self.frameElement && self.frameElement.tagName=="IFRAME"){
			parent.location.reload();
		}
	</script>
    <div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container" style="margin-top: 12px;width: 1200px;">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <div style="margin-top: 15px;"><img src="${ctxStatic}/images/login-${fns:getEnv()}.png"></div>
        </div>
      </div>
    </div>

    <div class="container">
        <div id="login-wraper">
        		<form class="login-form" id="login-form">
                <legend><span style="color:#08c;">系统登录</span></legend>
                <div class="body">
					<div class="control-group">
						<div class="controls">
							<input type="text" id="login" name="login" class="required" value="${username}" placeholder="账号" style="height: 30px;"/>
						</div>
					</div>
					<div class="control-group">
						<div class="controls">
							<input type="password" id="passwd" name="passwd" class="required" placeholder="密码" style="height: 30px;"/>
						</div>
					</div>
					<div class="control-group" id="verificationCode_div"  >
						<div class="controls">
							<input type="text" id="verificationCode" name="verificationCode"  placeholder="验证码" style="height: 30px;width: 80px"/>
							<img id="verificationCode_img" onclick="refresh()">
							<!-- <a onclick="refresh()">刷新</a> -->
						</div>
					</div>
					<div class="footer">
	                    <input class="btn btn-primary" type="button" value="登 录" onclick="login1()"/>
                	</div>
                </div>
                </form>
        </div>
    </div>
  </body>
</html>