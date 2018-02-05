<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>会员详情</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
	
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">	
	<div id="tt" class="easyui-tabs">   
	    <div title="个人详情">   
	        <table id="module_submit_table">
				<tr>
					<td class="td1">会员头像:</td>
					<td class="td2">${bean.userImageUrl }</td>
				</tr>
				<tr>
					<td class="td1">会员名称:</td>
					<td class="td2">${bean.userName}</td>
				</tr>
				<tr>
					<td class="td1">会员性别:</td>
					<td class="td2">${bean.userGender}</td>
				</tr>
				<tr>
					<td class="td1">会员电话:</td>
					<td class="td2">${bean.userMobilePhone}</td>
				</tr>
				<tr>
					<td class="td1">邮箱:</td>
					<td class="td2">${bean.userEmail }</td>
				</tr>
				<tr>
					<td class="td1">红豆币:</td>
					<td class="td2">${bean.rbcAmount}</td>
				</tr>
				<tr>
					<td class="td1">会员等级:</td>
					<td class="td2">${bean.userLevel }</td>
				</tr>
				<tr>
					<td class="td1">会员状态:</td>
					<td class="td2">${bean.userStatus }</td>
				</tr>
				<tr>
					<td class="td1">登录方式:</td>
					<td class="td2">${bean.loginType}</td>
				</tr>
				<tr>
					<td class="td1">登录IP:</td>
					<td class="td2">${bean.loginIp }</td>
				</tr>
				<tr>
					<td class="td1">登录次数:</td>
					<td class="td2">${bean.loginCount }</td>
				</tr>
				<tr>
					<td class="td1">登录时间:</td>
					<td class="td2"><fmt:formatDate value="${bean.loginTime}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
				</tr>
				<tr>
					<td class="td1">职业:</td>
					<td class="td2">${bean.occupation}</td>
				</tr>
				<tr>
					<td class="td1">昵称:</td>
					<td class="td2">${bean.nickName}</td>
				</tr>
				<tr>
					<td class="td1">地址:</td>
					<td class="td2">${bean.address}</td>
				</tr>
				<tr>
					<td class="td1">QQ:</td>
					<td class="td2">${bean.qq}</td>
				</tr>
				<tr>
					<td class="td1">微信:</td>
					<td class="td2">${bean.wechat}</td>
				</tr>
				<tr>
					<td class="td1">手机号码绑定状态:</td>
					<td class="td2">${bean.mobileBindStatus}</td>
				</tr>
				<tr>
					<td class="td1">出生日:</td>
					<td class="td2">${bean.userBirth}</td>
				</tr>
				<tr>
					<td class="td1">身份证:</td>
					<td class="td2">${bean.cardId }</td>
				</tr>
				<tr>
					<td class="td1">注册IP:</td>
					<td class="td2">${bean.registerIp }</td>
				</tr>
				<tr>
					<td class="td1">可绑定设备数量:</td>
					<td class="td2">${bean.bindPadMax  }</td>
				</tr>
				<tr>
					<td class="td1">备注:</td>
					<td class="td2">${bean.remark  }</td>
				</tr>
			</table>
	    </div>   
	    <div title="绑定设备">   
	        <table id="module_submit_table">
				<c:forEach items="${padList}" var="one">
				<tr>
					<td class="td1">${one.padCode}</td>
					<td class="td2">绑定:<fmt:formatDate value="${one.bindTimeT2 }" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;到期:<fmt:formatDate value="${one.expireTimeT2 }" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;<br> ${fns:getLabelStyle('rf_user_pad.pad_grade',one.padGradeT2)}&nbsp;${fns:getLabelStyle('rf_pad.pad_status',one.padStatus)}&nbsp;${fns:getLabelStyle('rf_pad.fault_status',one.faultStatus)}&nbsp;</td>
				</tr>
				</c:forEach>
			</table>
	    </div>   
	</div> 
	</form>
    </div>
</body>
</html>



