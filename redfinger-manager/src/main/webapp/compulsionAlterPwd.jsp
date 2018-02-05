<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="WEB-INF/include/taglib.jsp"%>
<html>
<head>
	<title>修改密码</title>
	<meta name="decorator" content="login"/>
	<style type="text/css">
	body,html,ul,li,input{
	    margin:0;
	    padding:0;
	}
		.control-group{border-bottom:0px;}
		.controls>input{
		    margin:10px 5%;
		    width:90%;
		    height:40px;
		}
		.controls{
		    width:80%;
		    margin:0 auto;
		}
	</style>
	<%-- <link rel="shortcut icon" href="${ctxStatic}/images/redfinger-${fns:getEnv()}.ico" /> --%>
    <script src="${ctxStatic}/js/backstretch.min.js"></script>
	<script type="text/javascript">
	var callback=function(data,fun){
	 if(jQuery.type(data)=='string'){
				data=eval('(' + data + ')'); 
			}
			var code=parseInt(data.code);
			var str=data.msg;
			//alert(code+"---"+str+"---"+ctx);
			switch(code){
				case 200:
					//alert(ctx+"---"+genpath);
					window.location.href=ctx;
					break;
				case 500:
					alert('登录失败，请联系管理员！');
					break;
				case 501:
					alert(str);
					break;
			}
	}
	var changePwd = function(){
	var newPassword = $("#newPassword").val();
	var reNewPassword = $("#reNewPassword").val();
		if(newPassword==''||reNewPassword==''){
			alert("密码不能为空");
		}else if(newPassword!=reNewPassword){
			alert("两次输入的密码不一致!");
		}else{
			$.post("${ctx}/sys/admin/compulsionAlterPwd",{pwd:newPassword},callback);
		}
	}
	
	var notNow = function(){
		window.location.href=ctx;
	}
	</script>
</head>
<body>
        <div class="container" style="margin-top:150px;">
        <div >
        		<form class="login-form" id="login-form">
                <legend style="margin:30px auto;width:60%;padding:30px 0;text-align:center;">
                	<span style="color:#08c;font-size:30px;">当前密码过于简单,请及时修改</span>
                </legend>
                <div style="margin:auto;width:60%;">
					<div class="control-group" >
						<div class="controls">
							<input class="easyui-textbox" type="password" id="newPassword" required="true" name="pwd" placeholder="请输入新密码"/> 
						</div>
					</div>
					<div class="control-group">
						<div class="controls">
							<input class="easyui-textbox" type="password" id="reNewPassword" required="true"  name="reNewPassword" placeholder="再次输入密码"/>  
						</div>
					</div>
					<p style="width:100%;text-align:center;color: red">注：密码长度不能小于8位,必须由字母和数字组成,且至少一个大写字母或特殊字符</p>
					<div class="footer" style="width:180px;margin:30px auto;">
	                    <input class="btn btn-primary" type="button" value="提交" onclick="changePwd()" style="width:180px;height:40px;font-size:25px;margin:0;padding:0;"/>
	                    <!-- <input class="btn btn-primary" type="button" value="暂时不改" onclick="notNow()"/> -->
                	</div>
                </div>
                </form>
        </div>
    </div>
  </body>
</html>