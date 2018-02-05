<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>红手指后台系统</title>
<link rel="shortcut icon" href="${ctxStatic}/images/redfinger-${fns:getEnv()}.ico" />
<script src="${ctxStatic}/js/backstretch.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/jquery.nicescroll.js"></script>
<meta name="decorator" content="layout"/>
<script type="text/javascript">
	$(function(){
		$(".nav_button").bind("mouseover", function(){
			if($(this).hasClass('nav_button_active') || $(this).hasClass('nav_button_over')){
				return ;
			}else{
				$(this).addClass('nav_button_over');
			}
		}).bind('mouseout',function(){
			$(this).removeClass('nav_button_over');
		}).bind('click',function(){
			if($(this).hasClass('nav_button_active')){
				return;
			}else{
				$(".nav_button").each(function(){
					$(this).removeClass('nav_button_active');
				});
				$(this).removeClass('nav_button_over');
				$(this).addClass('nav_button_active');
				showMenu($(this).attr("navId"),$(this).attr("navName"));
			}
		});
		
		//调整导航宽度
		var nav_width=0;
		$(".nav_button").each(function(){
			nav_width+=$(this).width()+10;
		});
		$(".nav_scroll").css("width",nav_width);
		$(".nav_arrow_left").bind("click",function(){
			var navWidth=$(".nav").width();
			var navScrollWidth=$(".nav_scroll").width();
			var marginLeft=parseInt($(".nav_scroll").css("margin-left"));
			if(marginLeft==0){
				return ;
			}
			if(marginLeft>-96){
				$(".nav_scroll").animate({"margin-left":'0px'},'fast');
			}else if(marginLeft<0){
				$(".nav_scroll").animate({"margin-left":parseInt(marginLeft+96)+'px'},'fast');
			}
		});
		$(".nav_arrow_right").bind("click",function(){
			var navWidth=$(".nav").width();
			var navScrollWidth=$(".nav_scroll").width();
			var marginLeft=parseInt($(".nav_scroll").css("margin-left"));
			if(navWidth - marginLeft < navScrollWidth){
				$(".nav_scroll").animate({"margin-left":parseInt(marginLeft-96)+'px'},'fast');
			}
		});
		//保持会话
		keepsession();
	})
	var showMenu=function(id,name){
		$("#easyui-layout-west").panel({
			title:name,href:'menu?id='+id,loadingMessage:"加载中..."
		});
		$('#easyui-layout-west').niceScroll({ 
		    cursorcolor: "#E8F1FF",//#CC0071 光标颜色 
		    cursoropacitymax: 1, //改变不透明度非常光标处于活动状态（scrollabar“可见”状态），范围从1到0 
		    touchbehavior: false, //使光标拖动滚动像在台式电脑触摸设备 
		    cursorwidth: "10px", //像素光标的宽度 
		    cursorborder: "0", //     游标边框css定义 
		    cursorborderradius: "5px",//以像素为光标边界半径 
		    autohidemode: false //是否隐藏滚动条 
		});
	}
	function tabSelect(title,index){
		var tab = $("#easyui-tabs").tabs("getTab",index);
		var id=$(tab).find('iframe').attr('id');
		$("#"+id).contents().find(".easyui-linkbutton:first").focus();
	}
	var keepSession;
	function keepsession(){
		$.get("${ctx}/keepSession",function(data){
			if (jQuery.type(data) == 'string') {
				data = eval('(' + data + ')');
			}
			var code = parseInt(data.code);
			switch (code) {
			case 304:
				window.clearTimeout(keepSession);
// 				$.messager.confirm('重新登录','登录已失效,请重新登录', function(btnData){
// 					if(btnData){
// 						top.location.href = ctx;
// 					}
// 				});
				$("#loginDiv").dialog({top:100,closed:true,modal:true,collapsible:false,minimizable:false,maximizable:false,title:"重新登录",width:600,height:440});
				$("#loginDiv").dialog("open");
				break;
			}
		});
		keepSession = window.setTimeout("keepsession()",10000);//10秒
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
		alert($("#verificationCode").val());
		$.post("${ctx}/login/login",{login:loginName,passwd:loginPwd,verificationCode:verificationCode},function(data){
			if(jQuery.type(data)=='string'){
				data=eval('(' + data + ')'); 
			}
			var code=parseInt(data.code);
			switch(code){
				case 200:
					$("#loginDiv").dialog("close");
					keepsession();
					break;
				case 500:
					alert('登录失败，请联系管理员！');
					break;
				case 501:
					alert(data.msg);
					break;
			}
		});
	}
	
	var login_success=function(){
		$("#loginDiv").dialog("close");
		keepsession();
	}
	
	var compulsionAlterPwd=function(){
		window.location.href=ctx+"/compulsionAlterPwd.jsp";
	}
</script>
</head>
<body>
	<div id="easyui-layout" class="easyui-layout" >  
	    <div data-options="region:'north',split:false,border:false" class="easyui-layout-north">
	    	<div class="logo">
	    		<img src="${ctxStatic}/images/logo-${fns:getEnv()}.png">
	    	</div>
	    	<div class="systemName">
	    	</div>
	    	<div class="nav-top">
	    		<div style="width: 163px;display: block;float: left;height: 22px;margin-left: 15px;overflow: hidden;">
	    			<div style="float: left;width: 16px;margin-left: 5px;margin-right: 10px;"><img src="${ctxStatic}/images/user.png"></div>
	    			<div style="width: 127px;height: 22px;float: left;text-align: left;text-overflow:ellipsis; white-space:nowrap;">您好，${sessionScope.user.adminName}</div>
	    		</div>
	    		<div style="display: block;float: right;">
	    			<span class="span"><a href="javascript:void(0)" onclick="addTab2('${host}/sys/admin/adminCentre','个人中心')">[个人中心]</a></span>
	    			<span class="span" style="width: 3px;">|</span>
	    			<span class="span"><a href="${ctx}/login/logout">[退出系统]</a></span>
    			</div>
	    	</div>
	    	<div class="nav">
	    		<div class="nav_scroll clearfix">
		    		<c:forEach items="${navList }" var="one">
		    		<div class="nav_button" navId="${one.code }" navName="${one.name }">
		    			<span class="nav_button_left"></span>
		    			<span class="nav_button_center">${one.name }</span>
		    			<span class="nav_button_right"></span>
		    		</div>
		    		</c:forEach>
	    		</div>
	    	</div>
	    	<div class="nav_arrow">
	    		<div class="nav_arrow_left"></div>
	    		<div class="nav_arrow_right"></div>
	    	</div>
	    </div>  
	    <div data-options="region:'west',title:'常用菜单',split:false,collapsed:false,collapsible:false" style="width: 180px;overflow: hidden;" id="easyui-layout-west">
	    </div>  
	    <div data-options="region:'center'">
	    	<div style="margin: 0px;padding: 0px;position: absolute;
	    		left: 0px;bottom: 0px;top: 0px;right: 0px;" 
	    		class="easyui-tabs" fit="true"  border="false" 
	    		id="easyui-tabs"
	    		data-options="onSelect:tabSelect"
	    	>
	    	</div>
	    </div>  
	    
	    <div style="display:none">
		    <div id="loginDiv">
		    	<iframe scrolling="auto" id='openLoginIframe' frameborder="0" src="login_dialog.jsp" style="width:100%;height:100%;"></iframe>
		    </div>
	    </div>
	</div>
    
	<script type="text/javascript">
		var height=$(window).outerHeight();
		var width=$(window).outerWidth();
		$("#easyui-layout").css("height",height).css("width",width);
	</script>
	
</body>
</html>