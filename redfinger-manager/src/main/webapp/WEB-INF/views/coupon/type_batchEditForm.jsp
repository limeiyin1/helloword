<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>优惠劵批量修改</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
			url : host + 'batchEditCoupon',
			success : callback
		});
		
		$(document).ready(function(){
			$("tr[name=discount]").addClass("hide");
		});
		
		function checkCouponType(){
		    var couponType = $("#couponType").combobox('getValue');
		    if(couponType == '1'){
		    	$("tr[name=discountAmount]").removeClass("hide");
		    	$("tr[name=discount]").addClass("hide");
		    }else if(couponType == '2'){
		    	$("tr[name=discount]").removeClass("hide");
		    	$("tr[name=discountAmount]").addClass("hide");
		    }
		}
		
		function doSearch() {
			$('#module_datagrid_coupon').datagrid('load', {
				couponCode : $('#couponCode').val(),
				couponStatus : $('#couponStatus').combobox('getValue'),
				bindStatus : $('#bindStatus').combobox('getValue')
			});
		}
		
		var module_datagrid_coupon = "#module_datagrid_coupon";
		var callback = defaultCallback ;
		var dataGridParamObj = {
			idField : 'couponId',
			onCheck : function(index,row) {
				
			},
			columns : [ [
			         	{width : 100,title : 'id',field : 'couponId',checkbox : true},
						{width : 100,title : '优惠劵编码',field : 'couponCode',sortable : true},
						{width : 100,title : '优惠劵名称',field : 'couponName',sortable : true},
						{width : 100,title : '优惠劵类型名称',field : 'map.couponTypeName',sortable : true},
						{width : 100,title : '批次号',field : 'batchNo',sortable : true},
						{width : 100,title : '开始时间',field : 'couponStartTime',sortable : true,formatter : formatterTime},
						{width : 150,title : '结束时间',	field : 'couponEndTime',sortable : true,formatter : formatterTime}, 
						{width : 80,title : '是否使用',field : 'map.couponStatusName',sortable : true},
						{width : 60,title : '是否绑定',field : 'map.bindStatusName',sortable : true},
						{width : 150,title : '创建时间',field : 'createTime',sortable : true,formatter : formatterTime},
						{width : 100,title : '创建人',field : 'creater',sortable : true}, 
						{width : 60,title : '状态',field : 'status',sortable : true,formatter : formatterStop},
						{width : 60,title : '启用状态',	field : 'enableStatus',	sortable : true,formatter : formatterStop}
			    
			         	] ]
		};
		var dialogParamObj = {

		};
		$(function() {
			$(module_datagrid_coupon).datagrid($.extend({}, dataGridParam, dataGridParamObj));
		});
	</script>
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post">
			<input type="hidden" name="typeId" value="${bean.typeId}">
			<table id="module_submit_table">
				<tr>
					<td class="td1">批次号:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="batchNo"
						data-options="required:true,validType:'length[0,8]'" /></td>
				</tr>
				
				<tr>
					<td class="td1">使用条件:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="couponCondition"
						data-options="required:true,validType:'length[0,25]'" /></td>
				</tr>
				
				
				<tr>
					<td class="td1">开始时间:</td>
					<td class="td2"><input type="text" class="easyui-datetimebox input_width_default" editable="false" 
						id="begin" name="begin" 
						data-options="required:true"/></td>
				</tr>
				
				<tr>
					<td class="td1">结束时间:</td>
					<td class="td2"><input type="text" class="easyui-datetimebox input_width_default" editable="false" 
						id="end" name="end" 
						data-options="required:true"/></td>
				</tr>
				
				<tr>
					<td class="td1">折扣类型:</td>
					<td class="td2">
					<select class="easyui-combobox input_width_short" editable="false" name="couponType" id="couponType" data-options="required:false,onSelect:checkCouponType">
						<fns:getOptions category="coupon.coupon_type"></fns:getOptions>
					</select></td>
				</tr>
				
				<c:forEach var="one" items="${goods}"  varStatus="vs">
					<tr name="discountAmount">
						<td class="td1">
							<input name="couponGoodsList[${vs.index}].goodsId" type="hidden" value="${one.goodsId }" />
							<input name="couponGoodsList[${vs.index}].goodsPrice" type="hidden" value="${one.goodsPrice }" />
							${one.goodsName }
						</td>
						<td class="td2"><input class="easyui-textbox" type="text" name="couponGoodsList[${vs.index}].couponMoney" data-options="validType:'length[0,10]'" />(元)</td>
					</tr>
				</c:forEach>
				
				<c:forEach var="one" items="${goods}"  varStatus="vs">
					<tr name="discount">
						<td class="td1">
						<input name="discountGoodsList[${vs.index}].goodsId" type="hidden" value="${one.goodsId }" />
						<input name="discountGoodsList[${vs.index}].goodsPrice" type="hidden" value="${one.goodsPrice }" />
						${one.goodsName }
						</td>
						<td class="td2"><input class="easyui-textbox" type="text" name="discountGoodsList[${vs.index}].discount" data-options="validType:'length[0,2]'" />%</td>
					</tr>
				</c:forEach>
			</table>
		</form>
		<div class="module_search_button">
			<form id="user_search_form" method="post">
				优惠劵编码：<input type="text" name="couponCode" id="couponCode"
					class="easyui-textbox input_width_default" />

				是否使用：
		        <select class="easyui-combobox" editable="false" name="couponStatus" id="couponStatus">
					<option value="">[全部]</option>
					<c:forEach items="${map}" var="one">
						<option value="${one.key }">${one.value }</option>
					</c:forEach>
				</select>
				是否绑定：
		        <select class="easyui-combobox" editable="false" name="bindStatus" id="bindStatus">
					<option value="">[全部]</option>
					<c:forEach items="${map}" var="one">
						<option value="${one.key }">${one.value }</option>
					</c:forEach>
				</select>
				<a href="#" iconCls="icon-search-rf" class="easyui-linkbutton"plain="true"onclick="doSearch()">查询</a>
			</form>
		</div> 
	</div>
	
	<table id="module_datagrid_coupon" toolbar="#module_toolbar_coupon" url="${host}type/getCodeList?typeId=${bean.typeId}" ></table>
</body>
</html>



