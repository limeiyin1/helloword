<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>激活码编辑</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
			url : host + 'update',
			success : callback
		});
		
		
		$(document).ready(function(){
			
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
	        
	        
	        /* $('#discountGoodsId').combo({
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
			
			
	        /* //默认选择
	        var _text = "";
		    var _value = "";
			$("input[name='discountGoodsId']:checked").each(function() {
                  _value += (_value==""?"":" | ")+ $(this).val();
                  _text += (_text==""?"":" | ")+$(this).next("span").text();
            });
            //设置下拉选中值
            $("#discountGoodsId").combo('setValue', _value).combo('setText', _text); */
            
            var _text1 = "";
		    var _value1 = "";
            $("input[name='goodsId']:checked").each(function() {
                  _value1 += (_value1==""?"":" | ")+ $(this).val();
                  _text1 += (_text1==""?"":" | ")+$(this).next("span").text();
            });
            //设置下拉选中值
            $("#goodsId").combo('setValue', _value1).combo('setText', _text1);
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
			<input type="hidden" name="couponId" value="${bean.couponId}">
			<table id="module_submit_table">
				<tr>
					<td class="td1">优惠劵编码:</td>
					<td class="td2"><input type="hidden" name="couponCode" value="${bean.couponCode}">${bean.couponCode}</td>
				</tr>
				
				<tr>
					<td class="td1">优惠劵名称:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="couponName" value="${bean.couponName}"
						data-options="required:true,validType:'length[0,25]'" />
						<br/><span style="color:red">客户端展示优惠券名称，如：优惠券、折扣券</span>
					</td>
				</tr>
				<tr>
					<td class="td1">展示优惠金额:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="couponMoney" value="${bean.couponMoney}"
						data-options="required:true,validType:'length[0,25]'" />
						<br/><span style="color:red">客户端展示优惠金额，如：10元、八折</span>
					</td>
				</tr>
				
				<tr>
					<td class="td1">使用条件:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="couponCondition" value="${bean.couponCondition}"
						data-options="required:true,validType:'length[0,25]'" /></td>
				</tr>
				
				<tr>
					<td class="td1">是否使用:</td>
					<td class="td2">
						<select class="easyui-combobox"  editable="false" name="couponStatus" data-options="required:true">
							<c:forEach items="${map}" var="one">
								<option value="${one.key }" <c:if test="${one.key==bean.couponStatus}">selected="selected"</c:if>>${one.value }</option>
							</c:forEach>
						</select> 
					</td>
				</tr>
				
				<tr>
					<td class="td1">是否绑定:</td>
					<td class="td2">
						<select class="easyui-combobox"  editable="false" name="bindStatus" data-options="required:true">
							<c:forEach items="${map}" var="one">
								<option value="${one.key }" <c:if test="${one.key==bean.bindStatus}">selected="selected"</c:if>>${one.value }</option>
							</c:forEach>
						</select> 
					</td>
				</tr>
				
				
				<tr>
					<td class="td1">开始时间:</td>
					<td class="td2">
						<input type="text" class="easyui-datetimebox input_width_default" editable="false" 
						id="begin" name="begin" 
						value="<fmt:formatDate value="${bean.couponStartTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" 
						data-options="required:true"/>
					</td>
				</tr>
				
				<tr>
					<td class="td1">结束时间:</td>
					<td class="td2">
						<input type="text" class="easyui-datetimebox input_width_default" editable="false" 
						id="end" name="end" 
						value="<fmt:formatDate value="${bean.couponEndTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" 
						data-options="required:true"/>
					</td>
				</tr>
				
				<tr>
					<td class="td1">优惠劵类型:</td>
					<td class="td2">
						<select class="easyui-combobox"  editable="false" name="typeId" data-options="required:true">
							<c:forEach items="${typeList}" var="one">
								<option value="${one.typeId }" <c:if test="${one.typeId==bean.typeId}">selected="selected"</c:if>>${one.typeName }</option>
							</c:forEach>
						</select> 
					</td>
				</tr>
				
				<tr>
					<td class="td1">折扣类型:</td>
					<td class="td2">
					<select class="easyui-combobox input_width_short" editable="false" name="couponType" id="couponType" data-options="required:false,onSelect:checkCouponType">
						<fns:getOptions category="coupon.coupon_type" value="${bean.couponType }"></fns:getOptions>
					</select></td>
				</tr>
				
				<tr>
					<td class="td1">备注:</td>
					<td class="td2"><input class="easyui-textbox" name="remark"
						value="${bean.remark}"
						data-options="multiline:true,validType:'length[0,1000]'"
						style="height: 120px" /></td>
				</tr>
				
				<tr>
					<td class="td1">
						选择产品:
					</td>
					<td class="td2">
						
						<select id="goodsId" name="goodsId" data-options="editable:true,width:200" style="width:200px;height:30px"></select>
		                       　　 		<div id="goodsDiv" name="goodsDiv">
		                      <c:forEach var="one" items="${goods}"  varStatus="vs">
		                      	  <input type="checkbox" name="goodsId" value="${one.goodsId}"  <c:if test="${one.goodsId==couponGoodsMap[one.goodsId].goodsId}">checked="checked"</c:if>>
		                          <span>${one.goodsCode}-${one.goodsName}</span>
		                          <br />
		                      </c:forEach>
		                </div>
					</td>
					
				</tr>
				
				<tr name="discountAmount" class="${bean.couponType=='1'?'show':'hide'}">
					<td class="td1">
						优惠金额:
					</td>
					<td class="td2">
						<input class="easyui-numberbox" type="text" name="money" value="${money }" data-options="precision:2,min:1,max:248" />(元)
					
					</td>
				</tr>
				
				<%-- <tr name="discount" class="${bean.couponType=='2'?'show':'hide'}">
					<td class="td1">
						选择产品:
					</td>
					<td class="td2">
						<select id="discountGoodsId" name="discountGoodsId" data-options="editable:true,width:200" style="width:200px;height:30px"></select>
		                       　　 		<div id="discountGoodsIdDiv" name="discountGoodsIdDiv">
		                      <c:forEach var="one" items="${goods}"  varStatus="vs">
		                      	  <c:set var="goodsIdKey" value="${one.goodsId}"></c:set>
		                      	  <input type="checkbox" name="discountGoodsId" value="${one.goodsId}"  <c:if test="${one.goodsId==couponGoodsMap[goodsIdKey].goodsId}">checked="checked"</c:if>>
		                          <span>${one.goodsName}</span>
		                          <br />
		                      </c:forEach>
		                </div>
		            </td>
				</tr> --%>
				
				<tr name="discount" class="${bean.couponType=='2'?'show':'hide'}" >
					<td class="td1">
						请填写折扣：
					</td>
					<td class="td2">
						<input class="easyui-numberbox" type="text" name="discount"  value="${discount }" data-options="min:1,max:99" />%
		            </td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>



