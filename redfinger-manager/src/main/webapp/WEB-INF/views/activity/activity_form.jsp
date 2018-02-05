<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>新增/编辑活动</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>

	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'update', 
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
		
		function choiceActivity(){
			var width = '500px';
			var activityCode = $("#activityCode").combobox('getValue');
			$("tr[name=goodsTr],tr[name=rbcTr],tr[name=lotteryTr],tr[name=couponTr],tr[name=vipTr],tr[name=timeTr],tr[name=walletTr],tr[name=inviteTimeTr],tr[name=inviteTimeCouponTr],tr[name=inviteSingupBoxCouponTr],tr[name=inviteBuyBoxCouponTr],tr[name=limitUserFinishCountTr],tr[name=lotteryCountTr],tr[name=systemLotteryCountTr],tr[name=cumulativeDayTr],tr[name=activityCouponTypeIdTr],tr[name=awardTypeIdTr],tr[name=inviteBuyLlotteryTr]").addClass("hide");
			<%-- 只有为邀请购买设备时赠送时长次数为必填字段 --%>
			$("#limitUserFinishCount").numberbox({required:false});
			if(activityCode=='rbc'){
				$("tr[name=rbcTr]").removeClass("hide");
			}else if(activityCode=='goods'){
				$("tr[name=goodsTr]").removeClass("hide");
				width = '800px';
			}else if(activityCode=='wechartLottery'){
				$("tr[name=lotteryTr]").removeClass("hide");
			}else if(activityCode=='coupon'){
				$("tr[name=couponTr]").removeClass("hide");
			}else if(activityCode=='svip'){
				$("tr[name=vipTr]").removeClass("hide");
			}else if(activityCode=='time'){
				$("tr[name=timeTr]").removeClass("hide");
			}else if(activityCode=='wallet_recharge'){
				$("tr[name=walletTr]").removeClass("hide");
				width = '800px';
			}else if(activityCode=='invite_time'){
				$("tr[name=inviteTimeTr]").removeClass("hide");
				$("tr[name=limitUserFinishCountTr]").removeClass("hide");
				<%-- 设置赠送时长次数为必填字段 --%>
				$("#limitUserFinishCount").numberbox({required:true,editable:true,width:155});
			}else if(activityCode=='invite_time_coupon'){
				$("tr[name=inviteTimeCouponTr]").removeClass("hide");
				$("tr[name=limitUserFinishCountTr]").removeClass("hide");
				$("#limitUserFinishCount").numberbox({editable:true,width:155});
				width = '600px';
			}else if(activityCode=='invite_lottery'){
				$("tr[name=lotteryCountTr]").removeClass("hide");
				$("tr[name=systemLotteryCountTr]").removeClass("hide");
			}else if(activityCode=='invite_buy_lottery'){
				$("tr[name=inviteBuyLlotteryTr]").removeClass("hide");
				$("tr[name=limitUserFinishCountTr]").removeClass("hide");
				<%-- 设置赠送时长次数为必填字段 --%>
				$("#limitUserFinishCount").numberbox({required:true,editable:true,width:155});
			}else if(activityCode=='cumulative_login'){
				$("tr[name=cumulativeDayTr]").removeClass("hide");
				$("tr[name=lotteryCountTr]").removeClass("hide");
				$("tr[name=systemLotteryCountTr]").removeClass("hide");
			}else if(activityCode=='daily_login'){
				$("tr[name=lotteryCountTr]").removeClass("hide");
				$("tr[name=systemLotteryCountTr]").removeClass("hide");
			}else if(activityCode=='daily_sign_in'){
				$("tr[name=lotteryCountTr]").removeClass("hide");
				$("tr[name=systemLotteryCountTr]").removeClass("hide");
			}else if(activityCode=='daily_walletRecharge'){
				$("tr[name=lotteryCountTr]").removeClass("hide");
				$("tr[name=systemLotteryCountTr]").removeClass("hide");
			}else if(activityCode=='novice_coupon'){
				$("tr[name=activityCouponTypeIdTr]").removeClass("hide");
			}else if(activityCode=='big_lottery'){
				$("tr[name=awardTypeIdTr]").removeClass("hide");
			}else if(activityCode=='little_lottery'){
				$("tr[name=awardTypeIdTr]").removeClass("hide");
			}else if(activityCode=='invite_signup_box'){
				$("tr[name=inviteSingupBoxCouponTr]").removeClass("hide");
				$("tr[name=limitUserFinishCountTr]").removeClass("hide");
				$("#limitUserFinishCount").numberbox({editable:true,width:155});
				width = '600px';
			}else if(activityCode=='invite_buy_box'){
				$("tr[name=inviteBuyBoxCouponTr]").removeClass("hide");
				$("tr[name=limitUserFinishCountTr]").removeClass("hide");
				$("#limitUserFinishCount").numberbox({editable:true,width:155});
				width = '600px';
			}
			
			$(module_dialog).window('resize',{width:width});
		}
		
		function delActivityImg(divName, uploadKey, showKey){
			$("div [name="+divName+"]").remove();
			$("input[name="+showKey+"]").show();
			$("#"+uploadKey).val("");
		}
		
		$(document).ready(function(){
			<%-- 赠送时长次数被始为不是必填字段 --%>
			$("#limitUserFinishCount").numberbox({required:false});
			
			if('${bean.activityCode}'=='goods' || '${bean.activityCode}'=='wallet_recharge'){
				$(module_dialog).window('resize',{width:'800px'});
			}else if('${bean.activityCode}'=='invite_time_coupon' || '${bean.activityCode}'=='invite_signup_box'){
				$(module_dialog).window('resize',{width:'600px'});
			}else if('${bean.activityCode}'=='invite_buy_box'){
				$(module_dialog).window('resize',{width:'800px'});
			}else{
				$(module_dialog).window('resize',{width:'500px'});
			}
			
			$("div[name^='couponTypeDiv']").each(function(){
				var index = $(this).attr("index");
				
				$('#couponTypeId'+index).combo({
		            editable : true,
		            multiple : true
		        });
		        $('#couponTypeId'+index).combo('panel').find("div").remove();
				$(this).appendTo($('#couponTypeId'+index).combo('panel'));
			});
			
			// 邀请登录活动
			$("div[name^='logincouponTypeDiv']").each(function(){
				var index = $(this).attr("index");
				
				$('#logincouponTypeId'+index).combo({
		            editable : true,
		            multiple : true
		        });
		        $('#logincouponTypeId'+index).combo('panel').find("div").remove();
				$(this).appendTo($('#logincouponTypeId'+index).combo('panel'));
			});
			
			$("input[name^='logincouponType']").click(function(){
	       		 var index = $(this).attr("index");
	       		 var name = $(this).attr("name");
	       		 
                var _text = "";
                var _value = "";
				 $("input[name='"+name+"']:checked").each(function() {
                     _value += (_value==""?"":" | ")+ $(this).val();
                     _text += (_text==""?"":" | ")+$(this).next("span").text();
                });
                //设置下拉选中值
               $("#logincouponTypeId"+index).combo('setValue', _value).combo('setText', _text);
               $("#logincouponTypeIds"+index).val(_value);
           });
           
           $("input[name^='logincouponType']").each(function(){
	       		 var index = $(this).attr("index");
	       		 var name = $(this).attr("name");
	       		 
                var _text = "";
                var _value = "";
				 $("input[name='"+name+"']:checked").each(function() {
                     _value += (_value==""?"":" | ")+ $(this).val();
                     _text += (_text==""?"":" | ")+$(this).next("span").text();
                });
                //设置下拉选中值
               $("#logincouponTypeId"+index).combo('setValue', _value).combo('setText', _text);
           });

           
           
			// 邀请注册送平安礼盒
			$("div[name^='inviteSignupBoxcouponTypeDiv']").each(function(){
				var index = $(this).attr("index");
				
				$('#inviteSignupBoxcouponTypeId'+index).combo({
		            editable : true,
		            multiple : true
		        });
		        $('#inviteSignupBoxcouponTypeId'+index).combo('panel').find("div").remove();
				$(this).appendTo($('#inviteSignupBoxcouponTypeId'+index).combo('panel'));
			});
			
			$("input[name^='inviteSignupBoxcouponType']").click(function(){
	       		 var index = $(this).attr("index");
	       		 var name = $(this).attr("name");
	       		 
               var _text = "";
               var _value = "";
				 $("input[name='"+name+"']:checked").each(function() {
                    _value += (_value==""?"":" | ")+ $(this).val();
                    _text += (_text==""?"":" | ")+$(this).next("span").text();
               });
               //设置下拉选中值
              $("#inviteSignupBoxcouponTypeId"+index).combo('setValue', _value).combo('setText', _text);
              $("#inviteSignupBoxcouponTypeIds"+index).val(_value);
          });
          
          $("input[name^='inviteSignupBoxcouponType']").each(function(){
	       		 var index = $(this).attr("index");
	       		 var name = $(this).attr("name");
	       		 
               var _text = "";
               var _value = "";
				 $("input[name='"+name+"']:checked").each(function() {
                    _value += (_value==""?"":" | ")+ $(this).val();
                    _text += (_text==""?"":" | ")+$(this).next("span").text();
               });
               //设置下拉选中值
              $("#inviteSignupBoxcouponTypeId"+index).combo('setValue', _value).combo('setText', _text);
          });
               

          
			// 邀请注册送圣诞礼盒
			$("div[name^='inviteBuyBoxcouponTypeDiv']").each(function(){
				var index = $(this).attr("index");
				
				$('#inviteBuyBoxcouponTypeId'+index).combo({
		            editable : true,
		            multiple : true
		        });
		        $('#inviteBuyBoxcouponTypeId'+index).combo('panel').find("div").remove();
				$(this).appendTo($('#inviteBuyBoxcouponTypeId'+index).combo('panel'));
			});
			
			$("input[name^='inviteBuyBoxcouponType']").click(function(){
	       		 var index = $(this).attr("index");
	       		 var name = $(this).attr("name");
	       		 
             var _text = "";
             var _value = "";
				 $("input[name='"+name+"']:checked").each(function() {
                  _value += (_value==""?"":" | ")+ $(this).val();
                  _text += (_text==""?"":" | ")+$(this).next("span").text();
             });
             //设置下拉选中值
            $("#inviteBuyBoxcouponTypeId"+index).combo('setValue', _value).combo('setText', _text);
            $("#inviteBuyBoxcouponTypeIds"+index).val(_value);
        });
        
        $("input[name^='inviteBuyBoxcouponType']").each(function(){
	       		 var index = $(this).attr("index");
	       		 var name = $(this).attr("name");
	       		 
             var _text = "";
             var _value = "";
				 $("input[name='"+name+"']:checked").each(function() {
                  _value += (_value==""?"":" | ")+ $(this).val();
                  _text += (_text==""?"":" | ")+$(this).next("span").text();
             });
             //设置下拉选中值
            $("#inviteBuyBoxcouponTypeId"+index).combo('setValue', _value).combo('setText', _text);
        });
           
        
        
			
			//钱包充值活动
			$("div[name^='wrcouponTypeDiv']").each(function(){
				var index = $(this).attr("index");
				
				$('#wrcouponTypeId'+index).combo({
			          editable : true,
			          multiple : false
			      });
			    $('#wrcouponTypeId'+index).combo('panel').find("div").remove();
				$(this).appendTo($('#wrcouponTypeId'+index).combo('panel'));
			});
			
	       	$("input[name^='couponType']").click(function(){
	       		 var index = $(this).attr("index");
	       		 var name = $(this).attr("name");
	       		 
                 var _text = "";
                 var _value = "";
				 $("input[name='"+name+"']:checked").each(function() {
                      _value += (_value==""?"":" | ")+ $(this).val();
                      _text += (_text==""?"":" | ")+$(this).next("span").text();
                 });
                 //设置下拉选中值
                $("#couponTypeId"+index).combo('setValue', _value).combo('setText', _text);
                $("#couponTypeIds"+index).val(_value);
            });
            
            $("input[name^='couponType']").each(function(){
	       		 var index = $(this).attr("index");
	       		 var name = $(this).attr("name");
	       		 
                 var _text = "";
                 var _value = "";
				 $("input[name='"+name+"']:checked").each(function() {
                      _value += (_value==""?"":" | ")+ $(this).val();
                      _text += (_text==""?"":" | ")+$(this).next("span").text();
                 });
                 //设置下拉选中值
                $("#couponTypeId"+index).combo('setValue', _value).combo('setText', _text);
            });
           
            $("input[name^='wrcouponType']").each(function(){
	       		 var index = $(this).attr("index");
	       		 var name = $(this).attr("name");
	       		 
                 var _text = "";
                 var _value = "";
				 $("input[name='"+name+"']:checked").each(function() {
                      _value += (_value==""?"":" | ")+ $(this).val();
                      _text += (_text==""?"":" | ")+$(this).next("span").text();
                 });
                 //设置下拉选中值
                $("#wrcouponTypeId"+index).combo('setValue', _value).combo('setText', _text);
              
                if(_value == ""){
                	$("#wrcouponTypeId"+index).combo({disabled:true});
                	//$("#wrcouponTypeId"+index).addClass('textbox-disabled');
                }
               
                var rbcAmount = $("#redbean"+index).val();
                if(rbcAmount == ""){
                	$("#redbean"+index).textbox({disabled:true});
                	//$("#redbean"+index).addClass('textbox-disabled');
                }
                
                if(_value != ""){
                	$("#awardItem"+index)[0].selectedIndex = 1; 
                } else if(rbcAmount != ""){
                	$("#awardItem"+index)[0].selectedIndex = 2;
                } else {
                	$("#awardItem"+index)[0].selectedIndex = 0;
                }
                
            });
		});
		
		function selectedSingle(box ,index, text){
			if(box.checked){
				var item = $("#wrcouponTypeDiv"+index).attr("selectedItem");
				$("#wrcouponType"+index +"_"+ item).attr("checked", false);
				$("#wrcouponTypeId"+index).combo('setValue', box.value).combo('setText', text);
				$("#wrcouponTypeDiv"+index).attr("selectedItem", box.value);
			}else{
				$("#wrcouponTypeDiv"+index).attr("selectedItem", "");
				$("#wrcouponTypeId"+index).combo('setValue', "").combo('setText', "");
			}
		}
		
		function selectAwardItem(item, index){	
			if("coupon" == item.value){
				$("#wrcouponTypeId"+index).combo({disabled:false});
				$("#redbean"+index).textbox({disabled:true});
				$("#redbean"+index).textbox("setValue", "");;
			}else {
				$("#wrcouponTypeId"+index).combo({disabled: true});
				$("#redbean"+index).textbox({disabled:false});
			}
		}
	</script>
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post" enctype="multipart/form-data">
			<c:if test="${not empty bean.activityId}">
				<input type="hidden" name="activityId" value="${bean.activityId }">
			</c:if>
			
			<table id="module_submit_table">
			    <tr>
					<td class="td1">活动名称:</td>
					<td class="td2">
					<input class="easyui-textbox" type="text"name="activityName" value="${bean.activityName }"data-options="required:true,validType:'length[0,50]'" />
					</td>
				</tr>
				<tr>
					<td class="td1">活动:</td>
					<td class="td2">
						<select class="easyui-combobox input_width_short" id="activityCode" name="activityCode" validType="selectValueRequired['#activityCode']" data-options="required:true,editable:false,onSelect:choiceActivity" style="width:100px">
							<option value="">[全部]</option>
						    <fns:getOptions category="rf_activity.activity" value="${bean.activityCode}"></fns:getOptions>
					 	</select>
					</td>
				</tr>
				
				<tr>
					<td class="td1">标签:</td>
					<td class="td2">
						<select class="easyui-combobox input_width_short" id="labelId" name="labelId" data-options="required:false,editable:false">
							<option value="">[全部]</option>
							<c:forEach items="${labelList }" var="one">
							    <option value="${one.labelId }" <c:if test="${bean.labelId==one.labelId }">selected="selected"</c:if>>${one.labelName }[${one.labelCode }]</option>
							</c:forEach>
					 	</select>
					</td>
				</tr>
				
				<tr name="limitUserFinishCountTr" class="${bean.activityCode=='invite_time' || bean.activityCode=='invite_time_coupon' || bean.activityCode=='invite_buy_lottery' || bean.activityCode=='invite_signup_box' || bean.activityCode=='invite_buy_box'?'show':'hide'}">
					<td class="td1">赠送次数:</td>
					<td class="td2">
						<input class="easyui-alidatebox" min=0 type="text" id="limitUserFinishCount" name="limitUserFinishCount" value="${bean.limitUserFinishCount }"/>（次）
					</td>
				</tr>
				
				<tr name="vipTr" class="${bean.activityCode=='svip'?'show':'hide'}">
					<td class="td1">活动数量:</td>
					<td class="td2">
						<input class="easyui-numberbox" min=0 type="text" name="activityNum" value="${bean.activityNum }"data-options="validType:'length[0,50]',min:0" />
					</td>
				</tr>
				<%-- 累计登录天数 --%>
				<tr name="cumulativeDayTr" class="${ bean.activityCode=='cumulative_login' ?'show':'hide'}">
					<td class="td1">累计登录天数:</td>
					<td class="td2">
						<input class="easyui-numberbox" min=0  type="text" id="cumulativeDay" name="cumulativeDay" value="${bean.cumulativeDay }" data-options="editable:true,width:155"  />（天）
					</td>
				</tr>
				<%-- 大转盘的抽奖次数 --%>
				<tr name="lotteryCountTr" class="${bean.activityCode=='invite_lottery' || bean.activityCode=='cumulative_login' || bean.activityCode=='daily_sign_in'|| bean.activityCode=='daily_login'|| bean.activityCode=='daily_walletRecharge'?'show':'hide'}">
					<td class="td1">赠送大转盘次数:</td>
					<td class="td2">
						<input class="easyui-numberbox" min=0  type="text" id="lotteryCount" name="lotteryCount" value="${bean.lotteryCount }" data-options="editable:true,width:155" />（次）
					</td>
				</tr>
				<%-- 小转盘的抽奖次数 --%>
				<tr name="systemLotteryCountTr" class="${bean.activityCode=='invite_lottery' || bean.activityCode=='cumulative_login' || bean.activityCode=='daily_sign_in'|| bean.activityCode=='daily_login'|| bean.activityCode=='daily_walletRecharge'?'show':'hide'}">
					<td class="td1">赠送小转盘次数:</td>
					<td class="td2">
						<input class="easyui-numberbox" min=0  type="text" id="systemLotteryCount" name="systemLotteryCount" value="${bean.systemLotteryCount }" data-options="editable:true,width:155" />（次）
					</td>
				</tr>
				
				
				<tr name="activityCouponTypeIdTr" class="${bean.activityCode=='novice_coupon'?'show':'hide'}">
					<td class="td1">赠送优惠券:</td>
					<td class="td2">
                  		<select editable="false" id="activityCouponTypeId" name="activityCouponTypeId"  class="easyui-combobox" class="easyui-combobox" data-options="editable:false,width:150,required:true">
							<option value="">请选择</option>
							<c:forEach var="one" items="${couponTypeList}">
								<option value="${one.typeId}"  <c:if test="${one.typeId==bean.activityCouponTypeId}">selected="selected"</c:if> >${one.typeName}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				
				<%-- 抽奖池 --%>
				<tr name="awardTypeIdTr" class="${bean.activityCode=='big_lottery' || bean.activityCode=='little_lottery' ?'show':'hide'}">
					<td class="td1">赠送礼品:</td>
					<td class="td2">
						<select class="easyui-combobox input_width_short" editable="false"
							name="awardTypeId" data-options="required:false">
							<option value="">请选择</option>
							<fns:getOptions category="rf_award_batch.award_type"
								value="${bean.awardTypeId}"></fns:getOptions>
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="td1">开始时间:</td>
					<td class="td2"><input type="text" class="easyui-datetimebox input_width_default" editable="false" id="begin" name="beginTimeStr" value="<fmt:formatDate value="${bean.activityStartTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" data-options="required:true"/></td>
				</tr>
				<tr>
					<td class="td1">结束时间:</td>
					<td class="td2"><input type="text" class="easyui-datetimebox input_width_default" editable="false" id="end" name="endTimeStr" value="<fmt:formatDate value="${bean.activityEndTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" data-options="required:true"/></td>
				</tr>
				<tr>
					<td class="td1">排序:</td>
					<td class="td2">
					<input class="easyui-numberbox easyui-alidatebox" type="text" name="reorder" value="${bean.reorder }" data-options="required:true,min:0,max:999" />
					</td>
				</tr>
				<tr>
					<td class="td1" valign="top" style="border-bottom:0px">备注:</td>
					<td class="td2" style="border-bottom:0px">
					<input class="easyui-textbox" name="remark"value="${bean.remark }" data-options="multiline:true,validType:'length[0,200]'"style="height: 60px" />
					</td>
				</tr>
				<tr style="margin-top:25px" name="rbcTr" class="${bean.activityCode=='rbc'?'show':'hide'}">
					<td colspan=2 class="td2" style="border-bottom:1px dashed black;">活动赠送红豆</td>
				</tr>
				<c:forEach var="g" items="${goodsList}" varStatus="vs">
					<tr name="rbcTr" class="${bean.activityCode=='rbc'?'show':'hide'}">
						<td class="td1">${g.goodsCode}<br/>${g.goodsName}:</td>
						<td class="td2">
							<input type="hidden" name="actRbcList[${vs.index}].actGoodsId" value="${actGoodsMap[g.goodsId].actGoodsId }"/>
							<input type="hidden" name="actRbcList[${vs.index}].goodsId" value="${g.goodsId }"/>
							<input class="easyui-numberbox easyui-alidatebox" min=0 type="text" name="actRbcList[${vs.index}].rbcAmount" value="${actGoodsMap[g.goodsId].rbcAmount }" data-options="min:0" />
						</td>
					</tr>
				</c:forEach>
				
				<tr style="margin-top:25px" name="timeTr" class="${bean.activityCode=='time'?'show':'hide'}">
					<td colspan=2 class="td2" style="border-bottom:1px dashed black;">活动赠送设备时长</td>
				</tr>
				<c:forEach var="g" items="${goodsList}" varStatus="vs">
					<tr name="timeTr" class="${bean.activityCode=='time'?'show':'hide'}">
						<td class="td1">${g.goodsCode}<br/>${g.goodsName}:</td>
						<td class="td2">
							<input type="hidden" name="padTimeList[${vs.index}].actGoodsId" value="${actGoodsMap[g.goodsId].actGoodsId }"/>
							<input type="hidden" name="padTimeList[${vs.index}].goodsId" value="${g.goodsId }"/>
							<input class="easyui-numberbox easyui-alidatebox" min=0 type="text" name="padTimeList[${vs.index}].padTime" value="${actGoodsMap[g.goodsId].padTime}" data-options="editable:true,width:100" />（小时）
						</td>
					</tr>
				</c:forEach>
				
				<!-- 邀请购买设备 start-->
				<tr style="margin-top:25px" name="inviteTimeTr" class="${bean.activityCode=='invite_time'?'show':'hide'}">
					<td colspan=2 class="td2" style="border-bottom:1px dashed black;">邀请购买设备赠送时长(给邀请人赠送时长)</td>
				</tr>
				<c:forEach var="g" items="${goodsList}" varStatus="vs">
					<tr name="inviteTimeTr" class="${bean.activityCode=='invite_time'?'show':'hide'}">
						<td class="td1">${g.goodsCode}<br/>${g.goodsName}:</td>
						<td class="td2">
							<input type="hidden" name="invitePadTimeList[${vs.index}].actGoodsId" value="${actGoodsMap[g.goodsId].actGoodsId }"/>
							<input type="hidden" name="invitePadTimeList[${vs.index}].goodsId" value="${g.goodsId }"/>
							<input class="easyui-numberbox easyui-alidatebox" min=0 type="text" name="invitePadTimeList[${vs.index}].padTime" value="${actGoodsMap[g.goodsId].padTime}" data-options="editable:true,width:100" />（小时）
						</td>
					</tr>
				</c:forEach>
				<!-- 邀请购买设备 end-->
				
				<!-- 邀请购买设备赠送抽奖 start-->
				<tr style="margin-top:25px" name="inviteBuyLlotteryTr" class="${bean.activityCode=='invite_buy_lottery'?'show':'hide'}">
					<td colspan=2 class="td2" style="border-bottom:1px dashed black;">邀请购买设备赠送抽奖次数</td>
				</tr>
				<c:forEach var="g" items="${goodsList}" varStatus="vs">
					<tr name="inviteBuyLlotteryTr" class="${bean.activityCode=='invite_buy_lottery'?'show':'hide'}">
						<td class="td1">${g.goodsCode}<br/>${g.goodsName}:</td>
						<td class="td2">
							<input type="hidden" name="inviteLotteryCountList[${vs.index}].actGoodsId" value="${actGoodsMap[g.goodsId].actGoodsId }"/>
							<input type="hidden" name="inviteLotteryCountList[${vs.index}].goodsId" value="${g.goodsId }"/>
							<input class="easyui-numberbox easyui-alidatebox" min=0 type="text" name="inviteLotteryCountList[${vs.index}].lotteryCount" value="${actGoodsMap[g.goodsId].lotteryCount}" data-options="editable:true,width:100" />（次）
						</td>
					</tr>
				</c:forEach>
				<!-- 邀请购买设备赠送抽奖 end-->
				
				<!-- 邀请登录 start-->
				<tr style="margin-top:25px" name="inviteTimeCouponTr" class="${bean.activityCode=='invite_time_coupon'?'show':'hide'}">
					<td colspan=2 class="td2" style="border-bottom:1px dashed black;">邀请购买设备赠送时长(给邀请的用户和被邀请的用户同时赠送设备时长和优惠劵)</td>
				</tr>
					<tr name="inviteTimeCouponTr" class="${bean.activityCode=='invite_time_coupon'?'show':'hide'}">
						<td colspan=3>
						<table style="width:100%" border="0">
							<tr>
								<td class="td1">产品套餐</td>
								<td class="td2">赠送设备时长</td>
								<td class="td2">赠送优惠券</td>
							</tr>
							<c:forEach var="g" items="${inviteGoodsList}" varStatus="vs">
								<tr>
									<td class="td1" >${g.goodsCode}<br/>${g.goodsName}:</td>
									
									<td class="td2" style="width:150px;height:25px;">
										<input class="easyui-numberbox easyui-alidatebox" min=0 type="text" name="invitePadTimeCouponList[${vs.index}].padTime" value="${padTimesMap[g.goodsId]}" data-options="editable:true,width:90,height:25" />（小时）
									</td>
									<td class="td2" ">
										<input type="hidden" name="invitePadTimeCouponList[${vs.index}].actGoodsId"/>
										<input type="hidden" name="invitePadTimeCouponList[${vs.index}].goodsId" value="${g.goodsId }"/>
										<%-- <input type="hidden" id="couponTypeIds${vs.index}" name="invitePadTimeCouponList[${vs.index}].couponTypeIds" value="${actCouponGoodsMap[g.goodsId]}"/> --%>
										<select id="logincouponTypeId${vs.index }" name="invitePadTimeCouponList[${vs.index}].couponTypeIds" data-options="editable:true,width:200" style="width:170px;height:25px"></select>
						                       　　 		<div id="logincouponTypeDiv${vs.index }" name="logincouponTypeDiv${vs.index }" index="${vs.index }">
						                      <c:forEach items="${couponTypeList }" var="b">
						                          <c:set var="inviteGoodsCouponKey" value="${g.goodsId}_${b.typeId}"></c:set>
						                      	  <input type="checkbox" name="logincouponType${vs.index }" value="${b.typeId}" index="${vs.index }" <c:if test="${b.typeId==actGoodsMap[inviteGoodsCouponKey].couponTypeId}">checked="checked"</c:if>>
						                          <span>${b.typeName}</span>
						                          <br />
						                      </c:forEach>
						                </div>
									</td>
							</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
				<!-- 邀请登录 end-->

				<!-- 邀请注册送平安礼盒 start-->
				<tr style="margin-top:25px" name="inviteSingupBoxCouponTr" class="${bean.activityCode=='invite_signup_box'?'show':'hide'}">
					<td colspan=2 class="td2" style="border-bottom:1px dashed black;">邀请注册送平安礼盒</td>
				</tr>
				<tr name="inviteSingupBoxCouponTr" class="${bean.activityCode=='invite_signup_box'?'show':'hide'}">
					<td colspan=3>
					<table style="width:100%" border="0">
						<tr>
							<td class="td1">产品套餐</td>
							<td class="td2">被邀请人送时长(小时)</td>
							<td class="td2">第一次打开礼盒赠送时长(小时)</td>
							<td class="td2">第二次打开礼盒赠送优惠券</td>
						</tr>
						<c:forEach var="g" items="${inviteSignupBoxGoodsList}" varStatus="vs">
							<tr>
								<td class="td1" >${g.goodsCode}<br/>${g.goodsName}:</td>
								<td class="td2">
									<input class="easyui-numberbox easyui-alidatebox" min=0 type="text" name="inviteSignupBoxCouponList[${vs.index}].padTime" value="${padTimesMap[g.goodsId]}" data-options="editable:true,width:80,height:25" />
								</td>
								<td class="td2">
									<input class="easyui-numberbox easyui-alidatebox" min=0 type="text" name="inviteSignupBoxCouponList[${vs.index}].padTimeTwo" value="${padTimesTwoMap[g.goodsId]}" data-options="editable:true,width:80,height:25" />
								</td>
								<td class="td2" ">
									<input type="hidden" name="inviteSignupBoxCouponList[${vs.index}].actGoodsId"/>
									<input type="hidden" name="inviteSignupBoxCouponList[${vs.index}].goodsId" value="${g.goodsId }"/>
									<%-- <input type="hidden" id="couponTypeIds${vs.index}" name="inviteSignupBoxCouponList[${vs.index}].couponTypeIds" value="${actCouponGoodsMap[g.goodsId]}"/> --%>
									<select id="inviteSignupBoxcouponTypeId${vs.index }" name="inviteSignupBoxCouponList[${vs.index}].couponTypeIds" data-options="editable:true,width:170" style="width:180px;height:25px"></select>
					                       　　 		<div id="inviteSignupBoxcouponTypeDiv${vs.index }" name="inviteSignupBoxcouponTypeDiv${vs.index }" index="${vs.index }">
					                      <c:forEach items="${couponTypeList }" var="b">
					                          <c:set var="inviteSignupBoxGoodsCouponKey" value="${g.goodsId}_${b.typeId}"></c:set>
					                      	  <input type="checkbox" name="inviteSignupBoxcouponType${vs.index }" value="${b.typeId}" index="${vs.index }" <c:if test="${b.typeId==actGoodsMap[inviteSignupBoxGoodsCouponKey].couponTypeId}">checked="checked"</c:if>>
					                          <span>${b.typeName}</span>
					                          <br />
					                      </c:forEach>
					                </div>
								</td>
						</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
			<!-- 邀请注册送平安礼盒 end-->



			<!-- 邀请购买送圣诞礼盒 start-->
			<tr style="margin-top:25px" name="inviteBuyBoxCouponTr" class="${bean.activityCode=='invite_buy_box'?'show':'hide'}">
				<td colspan=2 class="td2" style="border-bottom:1px dashed black;">邀请注册送圣诞礼盒</td>
			</tr>
			<tr name="inviteBuyBoxCouponTr" class="${bean.activityCode=='invite_buy_box'?'show':'hide'}">
				<td colspan=3>
				<table style="width:100%" border="0">
					<tr>
						<td class="td1">产品套餐</td>
						<td class="td2">第一次打开礼盒赠送时长(小时)</td>
						<td class="td2">第二次打开礼盒赠送优惠券</td>
						<td class="td2">第三次打开礼盒赠送时长(小时)</td>
					</tr>
					<c:forEach var="g" items="${inviteSignupBoxGoodsList}" varStatus="vs">
						<tr>
							<td class="td1" >${g.goodsCode}<br/>${g.goodsName}:</td>
							<td class="td2">
								<input class="easyui-numberbox easyui-alidatebox" min=0 type="text" name="inviteBuyBoxCouponList[${vs.index}].padTime" value="${padTimesMap[g.goodsId]}" data-options="editable:true,width:80,height:25" />
							</td>
							<td class="td2" ">
								<input type="hidden" name="inviteBuyBoxCouponList[${vs.index}].actGoodsId"/>
								<input type="hidden" name="inviteBuyBoxCouponList[${vs.index}].goodsId" value="${g.goodsId }"/>
								<%-- <input type="hidden" id="couponTypeIds${vs.index}" name="inviteSignupBoxCouponList[${vs.index}].couponTypeIds" value="${actCouponGoodsMap[g.goodsId]}"/> --%>
								<select id="inviteBuyBoxcouponTypeId${vs.index }" name="inviteBuyBoxCouponList[${vs.index}].couponTypeIds" data-options="editable:true,width:170" style="width:180px;height:25px"></select>
				                       　　 		<div id="inviteBuyBoxcouponTypeDiv${vs.index }" name="inviteBuyBoxcouponTypeDiv${vs.index }" index="${vs.index }">
				                      <c:forEach items="${couponTypeList }" var="b">
				                          <c:set var="inviteBuyBoxGoodsCouponKey" value="${g.goodsId}_${b.typeId}"></c:set>
				                      	  <input type="checkbox" name="inviteBuyBoxcouponType${vs.index }" value="${b.typeId}" index="${vs.index }" <c:if test="${b.typeId==actGoodsMap[inviteBuyBoxGoodsCouponKey].couponTypeId}">checked="checked"</c:if>>
				                          <span>${b.typeName}</span>
				                          <br />
				                      </c:forEach>
				                </div>
							</td>
							<td class="td2">
								<input class="easyui-numberbox easyui-alidatebox" min=0 type="text" name="inviteBuyBoxCouponList[${vs.index}].padTimeTwo" value="${padTimesTwoMap[g.goodsId]}" data-options="editable:true,width:80,height:25" />
							</td>
					</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
		<!-- 邀请购买送圣诞礼盒 end-->
				
				
				<tr style="margin-top:25px" name="lotteryTr" class="${bean.activityCode=='wechartLottery'?'show':'hide'}">
					<td colspan=2 class="td2" style="border-bottom:1px dashed black;">活动赠送抽奖次数</td>
				</tr>
				<c:forEach var="g" items="${goodsList}" varStatus="vs">
					<tr name="lotteryTr" class="${bean.activityCode=='wechartLottery'?'show':'hide'}">
						<td class="td1">${g.goodsCode}<br/>${g.goodsName}:</td>
						<td class="td2">
							<input type="hidden" name="actLotteryList[${vs.index}].actGoodsId" value="${actGoodsMap[g.goodsId].actGoodsId }"/>
							<input type="hidden" name="actLotteryList[${vs.index}].goodsId" value="${g.goodsId }"/>
							<input class="easyui-numberbox easyui-alidatebox" min=0 type="text" name="actLotteryList[${vs.index}].lotteryCount" value="${actGoodsMap[g.goodsId].lotteryCount }" data-options="min:0" />
						</td>
					</tr>
				</c:forEach>
				
				<tr style="margin-top:25px" name="goodsTr" class="${bean.activityCode=='goods'?'show':'hide'}">
					<td colspan=2 class="td2" style="border-bottom:1px dashed black;">套餐活动</td>
				</tr>
				<tr name="goodsTr" class="${bean.activityCode=='goods'?'show':'hide'}">
					<td colspan=3>
						<table style="width:100%" border="0">
							<tr>
								<td class="td1">产品套餐</td>
								<td class="td2">原价</td>
								<td class="td2">展示价格</td>
								<td class="td2">活动价格/分</td>
								<td class="td2">赠送红豆</td>
								<td class="td2">抽奖次数</td>
								<td class="td2">活动图片</td>
							</tr>
							<c:forEach var="g" items="${goodsList}" varStatus="vs">
								<tr>
									<td class="td1">${g.goodsCode }<br/>${g.goodsName }:</td>
									<td class="td2" style="width:20px">
										${g.goodsPrice}
									</td>
									<td class="td2" style="width:50px">
										<input class="easyui-textbox" type="text" name="actGoodsList[${vs.index}].activityShowPrice" value="${actGoodsMap[g.goodsId].activityShowPrice }" data-options="validType:'length[0,12]'" style="width:80px"/>
									</td>
									<td class="td2" style="width:50px">
										<input type="hidden" name="actGoodsList[${vs.index}].actGoodsId" value="${actGoodsMap[g.goodsId].actGoodsId }"/>
										<input type="hidden" name="actGoodsList[${vs.index}].goodsId" value="${g.goodsId }"/>
										<input type="hidden" name="actGoodsList[${vs.index}].activityImg" id="activityImg${g.goodsId}" value="${actGoodsMap[g.goodsId].activityImg }"/>
										<input class="easyui-numberbox easyui-alidatebox" type="text" name="actGoodsList[${vs.index}].activityPrice" value="${actGoodsMap[g.goodsId].activityPrice }" data-options="min:0" style="width:80px"/>
									</td>
									<td class="td2" style="width:50px">
										<input class="easyui-numberbox easyui-alidatebox" type="text" name="actGoodsList[${vs.index}].rbcAmount" value="${actGoodsMap[g.goodsId].rbcAmount }" data-options="min:0" style="width:80px"/>
									</td>
									<td class="td2" style="width:50px">
										<input class="easyui-numberbox easyui-alidatebox" type="text" name="actGoodsList[${vs.index}].lotteryCount" value="${actGoodsMap[g.goodsId].lotteryCount }" data-options="min:0" style="width:80px"/>
									</td>
									<td class="td2" style="width:50px">
										<c:if test="${not empty actGoodsMap[g.goodsId].activityImg}">
											<div name="divGoodsImg_${g.goodsId }"><img src="${actGoodsMap[g.goodsId].activityImg}"/><a href="javascript:delActivityImg('divGoodsImg_${g.goodsId }','activityImg${g.goodsId}','uploadActivityImg_${g.goodsId}')"><img src="${ctx}/static/images/del.png" border=0/></a></div>
										</c:if>
										<input type="file" name="uploadActivityImg_${g.goodsId}" style="width:200px;${not empty actGoodsMap[g.goodsId].activityImg?'display:none;':''}" value="">
									</td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			
				<tr style="margin-top:25px" name="couponTr" class="${bean.activityCode=='coupon'?'show':'hide'}">
					<td colspan=2 class="td2" style="border-bottom:1px dashed black;">优惠劵活动</td>
				</tr>
				<c:forEach var="g" items="${goodsList}" varStatus="vs">
					<tr name="couponTr" class="${bean.activityCode=='coupon'?'show':'hide'}">
						<td class="td1">${g.goodsCode}<br/>${g.goodsName}:</td>
						<td class="td2">
							<input type="hidden" name="actCouponList[${vs.index}].actGoodsId"/>
							<input type="hidden" name="actCouponList[${vs.index}].goodsId" value="${g.goodsId }"/>
							<input type="hidden" id="couponTypeIds${vs.index}" name="actCouponList[${vs.index}].couponTypeIds" value="${actCouponGoodsMap[g.goodsId]}"/>
							<select id="couponTypeId${vs.index }" name="couponTypeId" data-options="editable:true,width:200" style="width:200px;height:30px"></select>
			                       　　 		<div id="couponTypeDiv${vs.index }" name="couponTypeDiv${vs.index }" index="${vs.index }">
			                      <c:forEach items="${couponTypeList }" var="b">
			                          <c:set var="goodsCouponKey" value="${g.goodsId}_${b.typeId}"></c:set>
			                      	  <input type="checkbox" name="couponType${vs.index }" value="${b.typeId}" index="${vs.index }" <c:if test="${b.typeId==actGoodsMap[goodsCouponKey].couponTypeId}">checked="checked"</c:if>>
			                          <span>${b.typeName}</span>
			                          <br />
			                      </c:forEach>
			                </div>
						</td>
					</tr>
				</c:forEach>
				
				<c:set var="goodsLen" value="${fn:length(goodsList)}"></c:set>
				<tr style="margin-top:25px" name="walletTr" class="${bean.activityCode=='wallet_recharge'?'show':'hide'}">
					<td colspan=2 class="td2" style="border-bottom:1px dashed black;">钱包充值活动</td>
				</tr>
				<tr name="walletTr" class="${bean.activityCode=='wallet_recharge'?'show':'hide'}">
					<td colspan=3>
						<table style="width:100%" border="0">
							<tr>
								<td class="td1">产品套餐</td>
								<td class="td2">奖励选项</td>
								<td class="td2">赠送优惠券</td>
								<td class="td2">赠送红豆</td>								
								<td class="td2">活动图片</td>
							</tr>
							<c:forEach var="g" items="${wrGoodsList}" varStatus="vs">
								<c:set var="couponTypeId" value="${actGoodsMap[g.goodsId].couponTypeId}"></c:set>
								<tr>
									<td class="td1">${g.goodsCode}<br/>${g.goodsName}:</td>
									<td class="td2" style="width:50px">
										<select class="easyui-combobox input_width_short" id="awardItem${vs.index}" index=${vs.index} data-options="editable:false,onSelect:function(item){selectAwardItem(item,${vs.index})}" style="width:100px">
											<option value=""></option>
										    <option value="coupon" ${not empty couponTypeId?'checked':''}>优惠券</option>
										    <option value="rbc" ${not empty actGoodsMap[g.goodsId].rbcAmount?'checked':''}>红豆</option>
									 	</select>
									</td>
									<td class="td2" style="width:50px">
										<input type="hidden" name="walletGoodsList[${vs.index}].actGoodsId" value="${actGoodsMap[g.goodsId].actGoodsId}"/>
										<input type="hidden" name="walletGoodsList[${vs.index}].goodsId" value="${g.goodsId}"/>
					                       　　 		<select id="wrcouponTypeId${vs.index}" name="walletGoodsList[${vs.index}].couponTypeId" data-options="editable:true,width:200" style="width:200px;height:30px"></select>
					                       　　 		<div id="wrcouponTypeDiv${vs.index}" name="wrcouponTypeDiv${vs.index}" index="${vs.index}" selectedItem="${couponTypeId}">
					                      <c:forEach items="${couponTypeList}" var="b">	
					                      	  <input type="checkbox" id="wrcouponType${vs.index}_${b.typeId}" name="wrcouponType${vs.index}" value="${b.typeId}" index="${vs.index}" <c:if test="${b.typeId==couponTypeId}">checked="checked"</c:if> onclick="selectedSingle(this, ${vs.index},'${b.typeName}');">
					                          <span>${b.typeName}</span>
					                          <br />
					                      </c:forEach>
						                </div>
									</td>
																	
									<td class="td2" style="width:50px">
										<input type="hidden" name="walletGoodsList[${vs.index}].activityImg" id="walletActivityImg${g.goodsId}" value="${actGoodsMap[g.goodsId].activityImg }"/>
										<input class="easyui-numberbox easyui-alidatebox" id="redbean${vs.index}" type="text" name="walletGoodsList[${vs.index}].rbcAmount" value="${actGoodsMap[g.goodsId].rbcAmount}" data-options="min:0" style="width:80px"/>
									</td>									
									<td class="td2" style="width:50px">
										<c:if test="${not empty actGoodsMap[g.goodsId].activityImg}">
											<div name="divWalletImg_${g.goodsId }"><img src="${actGoodsMap[g.goodsId].activityImg}"/><a href="javascript:delActivityImg('divWalletImg_${g.goodsId }','walletActivityImg${g.goodsId}','uploadWalletActivityImg_${g.goodsId}')"><img src="${ctx}/static/images/del.png" border=0/></a></div>
										</c:if>
										<input type="file" name="uploadWalletActivityImg_${g.goodsId}" style="width:200px;${not empty actGoodsMap[g.goodsId].activityImg?'display:none;':''}" value="">
									</td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
				
				<tr style="margin-top:25px" name="vipTr" class="${bean.activityCode=='svip'?'show':'hide'}">
					<td colspan=2 class="td2" style="border-bottom:1px dashed black;">超级VIP活动</td>
				</tr>
				<tr name="vipTr" class="${bean.activityCode=='svip'?'show':'hide'}">
						<td class="td1">选择产品:</td>
						<td class="td2">
							<select class="easyui-combobox input_width_short" data-options="editable:false" id="sVipGoodsList[0].goodsId" name="sVipGoodsList[0].goodsId">
							    <c:forEach items="${svipGoodsList }" var="vip">
							    	<option value="${vip.goodsId }" <c:if test="${actGoodsMap[vip.goodsId].goodsId == vip.goodsId }">selected="selected"</c:if>>${vip.goodsName }</option>
							    </c:forEach>
							</select>
						</td>
					</tr>
			</table>
		</form>
	</div>
</body>
</html>