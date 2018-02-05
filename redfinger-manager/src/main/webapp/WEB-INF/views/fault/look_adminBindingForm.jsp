<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>绑定</title>
<meta name="decorator" content="default" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'adminbinding',   
		    success:callback
		});
		$(document).ready(function(){
			/* var padClassify = ${padClassify};//注释代码原因：管理员绑定改回原来的，不根据设备类别区分情况
			if(padClassify=="2"){
				$("#padClassify_main").hide();
			}else if(padClassify=="1"){
				$("#padClassify_game").hide();
			} */
			$("#padClassify_game").hide();
		});
	</script>
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post">
		<input type="hidden" name="padClassify" value="${padClassify}" />
			<table id="module_submit_table">
				<tr>
					<td class="td1">绑定的帐号：</td>
					<td class="td2" colspan="3"><input type="text" name="mid" class="easyui-textbox" /></td>
				</tr>
				<tr id="padClassify_main">
					<td class="td1">VIP套餐：</td>
					<td class="td2" colspan="3"><select class="easyui-combobox input_width_default" editable="false" name="goodsId">
										<option  value="">[普通]</option>
										<c:forEach var="one" items="${goods}">
										<option value="${one.goodsId }">${one.goodsName }</option>
										</c:forEach>
			         	             </select>
				     </td>
				</tr>
				<tr id="padClassify_game">
					<td class="td1">${config.configLabel}</td>
					<td class="td2" colspan="3"><input type="text" name="gamePadOnlineTime" class="easyui-textbox" value="${config.configValue}"/></td>
				</tr>
				
				<c:forEach items="${padList}" var="pad"> 
					<tr>
						<input type="hidden" name="padId" value="${pad.padId}" />
						<td class="td1">设备编号：</td>
						<td class="td2">${pad.padCode}</td>
						<td class="td1">设备名称：</td>
						<td class="td2">${pad.padName}</td>
					</tr>
				</c:forEach>
			</table>
	
	
    </form>
    </div>
</body>
</html>


