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
		    url:host+'batchBindVip',   
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
			<table id="module_submit_table">
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
					<td class="td1">机房：</td>
					<td class="td2">
						<select class="easyui-combobox input_width_default" editable="false" name="idcId">
							<option value="">[全部]</option>
							<c:forEach var="one" items="${idcs}">
							<option value="${one.idcId }">${one.idcName }</option>
							</c:forEach>
				        </select>
				     </td>
				</tr>
				<tr>
					<td class="td1">产品名称：</td>
					<td class="td2">绑定设备的数量</td>
				</tr>
				<c:forEach var="g" items="${goods}" varStatus="vs">
					<tr name="goodsTr">
						<td class="td1"><input type="hidden" name="padGoods[${vs.index}].goodsId" value="${g.goodsId }"/>${g.goodsName }：</td>
						<td class="td2">
							<input class="easyui-numberbox easyui-alidatebox" type="text" name="padGoods[${vs.index}].padCount" data-options="min:0" />
						</td>
					</tr>
				</c:forEach>
				
			</table>
	</form>
    </div>
</body>
</html>


