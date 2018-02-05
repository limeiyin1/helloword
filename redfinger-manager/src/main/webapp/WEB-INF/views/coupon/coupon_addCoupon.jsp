<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>批量生成优惠劵</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
			url : host + 'saveCoupon',
			success : callback
		});
		
		$(document).ready(function(){
			$("tr[name=discount]").addClass("hide");
			
			
			$('#goodsId').combo({
	            editable : true,
	            multiple : true
	        });
	        $('#goodsDiv').appendTo($('#goodsId').combo('panel'));
	
	        $("#goodsDiv input").click(function() {
	                var _value = "";
	                var _text = "";
	                $("[name=goodsId]:input:checked").each(function() {
	                    _value += (_value==""?"":" | ")+ $(this).val() + "";
	                    _text += (_text==""?"":" | ")+ $(this).next("span").text();
	                });
	                //设置下拉选中值
	                $('#goodsId').combo('setValue', _value).combo(
	                        'setText', _text);
	        });
	        
	        
	       /*  $('#discountGoodsId').combo({
	            editable : true,
	            multiple : true
	        });
	        $('#discountGoodsIdDiv').appendTo($('#discountGoodsId').combo('panel'));
	
	        $("#discountGoodsIdDiv input").click(function() {
	                var _value = "";
	                var _text = "";
	                $("[name=discountGoodsId]:input:checked").each(function() {
	                    _value += (_value==""?"":" | ")+ $(this).val() + "";
	                    _text += (_text==""?"":" | ")+ $(this).next("span").text();
	                });
	                //设置下拉选中值
	                $('#discountGoodsId').combo('setValue', _value).combo(
	                        'setText', _text);
	        }); */
	        
	        
			
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
	</script>
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post">
			<table id="module_submit_table">
				<tr>
					<td class="td1">优惠劵前缀:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="couponCodePrefix"
						data-options="required:true,validType:'length[0,3]'" /></td>
				</tr>
				<tr>
					<td class="td1">优惠劵名称:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="couponName"
						data-options="required:true,validType:'length[0,25]'" />
						<br/><span style="color:red">客户端展示优惠券名称，如：优惠券、折扣券</span>
					</td>
				</tr>
				<tr>
					<td class="td1">展示优惠金额:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="couponMoney"
						data-options="required:true,validType:'length[0,25]'" />
						<br/><span style="color:red">客户端展示优惠金额，如：10元、八折</span>
					</td>
				</tr>
				<tr>
					<td class="td1">优惠劵个数:</td>
					<td class="td2"><input class="easyui-numberbox" type="text"
						name="couponNumber" precision="0" min="1" max="1000"
						data-options="required:true,validType:'length[0,4]'" /></td>
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
					<td class="td1">优惠劵类型:</td>
					<td class="td2">
					<select class="easyui-combobox input_width_short" editable="false" name="typeId" data-options="required:false">
						<c:forEach var="one" items="${couponTypes}">
							<option value="${one.typeId}">${one.typeName}</option>
						</c:forEach>
					</select></td>
				</tr>
				
				<tr>
					<td class="td1">折扣类型:</td>
					<td class="td2">
					<select class="easyui-combobox input_width_short" editable="false" name="couponType" id="couponType" data-options="required:false,onSelect:checkCouponType">
						<fns:getOptions category="coupon.coupon_type"></fns:getOptions>
					</select></td>
				</tr>
				
				
				<tr>
					<td class="td1">
						选择产品:
					</td>
					<td class="td2">
						
						<select id="goodsId" name="goodsId" data-options="editable:true,width:200" style="width:200px;height:30px"></select>
		                       　　 		<div id="goodsDiv" name="goodsDiv">
		                      <c:forEach var="one" items="${goods}"  varStatus="vs">
		                      	  <input type="checkbox" name="goodsId" value="${one.goodsId}">
		                          <span>${one.goodsCode}-${one.goodsName}</span>
		                          <br />
		                      </c:forEach>
		                </div>
					</td>
					
				</tr>
				
				<tr name="discountAmount">
					<td class="td1">
						优惠金额:
					</td>
					<td class="td2">
						<input class="easyui-numberbox" type="text" name="money" data-options="precision:2,min:1,max:248" />(元)
					
					</td>
				</tr>
				
				<%-- <tr name="discount">
					<td class="td1">
						选择产品:
					</td>
					<td class="td2">
						<select id="discountGoodsId" name="discountGoodsId" data-options="editable:true,width:200" style="width:200px;height:30px"></select>
		                       　　 		<div id="discountGoodsIdDiv" name="discountGoodsIdDiv">
		                      <c:forEach var="one" items="${goods}"  varStatus="vs">
		                      	  <input type="checkbox" name="discountGoodsId" value="${one.goodsId}">
		                          <span>${one.goodsName}</span>
		                          <br />
		                      </c:forEach>
		                </div>
		            </td>
				</tr> --%>
				
				<tr name="discount">
					<td class="td1">
						请填写折扣：
					</td>
					<td class="td2">
						<input class="easyui-numberbox" type="text" name="discount" data-options="min:1,max:99" />%
		            </td>
				</tr>
				
				<tr>
					<td class="td1">备注:</td>
					<td class="td2"><input class="easyui-textbox" name="remark" 
						data-options="multiline:true,validType:'length[0,500]'"
						style="height: 120px" /></td>
				</tr>
				
				
			</table>
		</form>
	</div>
</body>
</html>



