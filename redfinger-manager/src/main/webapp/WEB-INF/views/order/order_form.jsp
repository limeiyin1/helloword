<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>订单</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'save',   
		    success:callback
		}); 
    
    	$.extend($.fn.validatebox.defaults.rules, {  
			selectValueRequired: {
				validator: function(value,param){ 
					return $(param[0]).find("option:contains('"+value+"')").val() != '';  
				},  
				message: '该选项为必选项'  
			}  
		});
	</script>
<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
  	<input type="hidden" name="orderId" value="${bean.orderId}">
	<table id="module_submit_table">
			    <tr>
					<td class="td1">订单号:</td>
					<td class="td2">${bean.orderId }</td>
			    </tr>
			    <tr>
					<td class="td1">会员号码:</td>
					<td class="td2">${bean.map.userMobilePhone }</td>
			    </tr>
			    <tr>
					<td class="td1">设备编号:</td>
					<td class="td2">${bean.padCode}</td>
			   </tr>
		        <tr>
					<td class="td1">商品名称:</td>
					<td class="td2">${bean.map.goodsName}</td>
			   </tr>
			   <tr>
					<td class="td1">获赠红豆:</td>
					<td class="td2">${bean.rbcAmount}</td>
			   </tr>
			   <tr>
					<td class="td1">创建时间 :</td>
					<td class="td2"><fmt:formatDate value="${bean.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			   </tr>
		        <tr>
					<td class="td1">订单金额:</td>
					<td class="td2">${bean.orderPrice}</td>
			   </tr>
		        <tr>
					<td class="td1">应付金额:</td>
					<td class="td2">${bean.realFee}</td>
			   </tr>
		       <tr>
					<td class="td1">状态:</td>
					<td class="td2">
						<select class="easyui-combobox" editable="false" id="orderStatus" name="orderStatus" data-options="required:true" validType="selectValueRequired['#orderStatus']">
							<option value="">[全部]</option>
							<fns:getOptions category="rf_order.order_status" value="${bean.orderStatus}" keys="rf_order.order_status@nopayment,rf_order.order_status@payment,rf_order.order_status@abolish,${bean.orderStatus=='7'?'rf_order.order_status@refunds':''}" ></fns:getOptions>
						</select> 
				    </td>
			   </tr>
		       <tr>
					<td class="td1">订单业务类型:</td>
					<td class="td2">${fns:getLabelStyle('rf_order.order_biz_type',bean.orderBizType)}</td>
			   </tr>
			   <tr>
					<td class="td1">订单类型:</td>
					<td class="td2">${fns:getLabelStyle('rf_order.order_type',bean.orderType)}  </td>
			   </tr>
			   <tr>
					<td class="td1">支付方式:</td>
					<td class="td2">${fns:getLabelStyle('rf_order.pay_mode_code',bean.payModeCode)} </td>
			   </tr>
			   <tr>
					<td class="td1">状态 :</td>
					<td class="td2">${fns:getLabelStyle('global.enable_status',bean.enableStatus)} </td>
			   </tr>
			   <tr>
					<td class="td1">备注:</td>
					<td class="td2"><input class="easyui-textbox" name="remark" value="${bean.remark}" data-options="required:true,multiline:true,validType:'length[0,500]'" style="height:60px" /></td>
				</tr>
			</table>
    </form>
    </div>
</body>
</html>