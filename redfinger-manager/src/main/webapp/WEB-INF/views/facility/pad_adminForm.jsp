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
		    url:host+'bindAdmin',   
		    success:callback
		}); 
		
		//扩展easyui表单的验证  
		$.extend($.fn.validatebox.defaults.rules, {  
		    //移动手机号码验证  
		    mobile: {//value值为文本框中的值  
		        validator: function (value) {  
		            var reg = /^1[3|4|5|8|9]\d{9}$/;  
		            return reg.test(value);  
		        },  
		        message: '输入手机号码格式不准确.'  
		    },  
		})  
	</script>
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post">
			<input type="hidden" name="padId" value="${pad.padId}" />
			<table id="module_submit_table">
				<tr>
					<td class="td1">设备编号：</td>
					<td class="td2"><input type="hidden" name="padCode" value="${pad.padCode}" />${pad.padCode}</td>
				</tr>
				<tr>
					<td class="td1">设备名称</td>
					<td class="td2"><input type="hidden" name="padName"value="${pad.padName}" />${pad.padCode}</td>
				</tr>
				<tr>
					<td class="td1">账号类型：</td>
					<td class="td2">
						<input type="radio" name="idType" value="1" checked="checked">手机号</input>
						<input type="radio" name="idType" value="2">会员ID</input>
					</td>
				</tr>
				<tr>
					<td class="td1">绑定的帐号：</td>
					<td class="td2"><input type="text" name="mid" class="easyui-textbox" data-options="required:true"/></td>
				</tr>
				<tr>
					<td class="td1">VIP套餐：</td>
					<td class="td2"><select class="easyui-combobox input_width_default" editable="false" name="goodsId">
										<option value="">[全部]</option>
										<c:forEach var="one" items="${goods}">
										<option value="${one.goodsId }">${one.goodsName }</option>
										</c:forEach>
			         	             </select>
				     </td>
				</tr>
				
			</table>
	
	
    </form>
    </div>
</body>
</html>


