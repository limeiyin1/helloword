<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>奖品编辑</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
			url : host + 'save',
			success : callback
		});
		
		function selectCategory(){
			var awardCategory = $("#awardCategory").combobox('getValue');
			$("tr[name=couponTypeTr],tr[name=activationTypeTr]").addClass("hide");
			if(awardCategory == '1' || awardCategory == '2' || awardCategory=='3' || awardCategory=='6'){//激活码
				$("tr[name=activationTypeTr]").removeClass("hide");
			}else if(awardCategory == '7'){//优惠劵
				$("tr[name=couponTypeTr]").removeClass("hide");
			}
		}
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
			<input type="hidden" name="awardId" value="${bean.awardId}">
			<table id="module_submit_table">
				<tr>
					<td class="td1">奖品池:</td>
					<td class="td2">
						<select
							class="easyui-combobox input_width_short" editable="false"
							name="awardType" data-options="required:false">
							<fns:getOptions category="rf_award_batch.award_type"
								value="${bean.awardType}"></fns:getOptions>
						</select>
					</td>
				</tr>
				<tr>
					<td class="td1">奖品级别:</td>
					<td class="td2"><select
						class="easyui-combobox input_width_short" editable="false"
						name="awardGrade" data-options="required:false">
							<fns:getOptions category="rf_award_batch.award_grade"
								value="${bean.awardGrade}"></fns:getOptions>
					</select></td>
				</tr>
				<tr>
					<td class="td1">奖品类型:</td>
					<td class="td2"><select
						class="easyui-combobox input_width_short" editable="false" id="awardCategory"
						name="awardCategory" data-options="required:true,onSelect:selectCategory" validType="selectValueRequired['#awardCategory']">
							<option value="">请选择</option>
							<fns:getOptions category="rf_award_batch.award_category"
								value="${bean.awardCategory}"></fns:getOptions>
					</select></td>
				</tr>
				<tr name="couponTypeTr" class="${bean.awardCategory=='7' ? 'show' : 'hide' }">
					<td class="td1">优惠劵类型:</td>
					<td class="td2">
						<select class="easyui-combobox input_width_short" editable="false"
							name="couponTypeId" data-options="required:false">
							<option value="">请选择</option>
							<c:forEach items="${couponTypeList }" var="one">
								<option value="${one.typeId }" <c:if test="${bean.couponTypeId ==  one.typeId}">selected="selected"</c:if>>${one.typeName }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr name="activationTypeTr" class="${isActivation=='1' ? 'show' : 'hide' }">
					<td class="td1">激活码类型:</td>
					<td class="td2">
						<select class="easyui-combobox input_width_short" editable="false"
							name="activationTypeId" data-options="required:false">
							<option value="">请选择</option>
							<c:forEach items="${codeTypeList }" var="one">
								<option value="${one.typeId }" <c:if test="${bean.activationTypeId ==  one.typeId}">selected="selected"</c:if>>${one.activationTypeName }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="td1"></td>
					<td class="td2"><span color="red">奖品类型是激活码或者优惠劵时请选择激活码或者优惠劵的类型，不然用户分配不到奖品</span></td>
				</tr>
				<tr>
					<td class="td1">中奖率（/10000）:</td>
					<td class="td2"><input class="easyui-numberbox" type="text"
						name="winningRate" value="${bean.winningRate}"
						data-options="required:true,validType:'length[0,32]'" /></td>
				</tr>
				<tr>
					<td class="td1">奖品名称:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="awardName" value="${bean.awardName}"
						data-options="required:true,validType:'length[0,16]'" /></td>
				</tr>
				<tr>
					<td class="td1">奖品总量:</td>
					<td class="td2"><input class="easyui-numberbox" type="text"
						name="awardTotal" value="${bean.awardTotal}"
						data-options="required:true,validType:'length[0,32]'" /></td>
				</tr>
				<tr>
					<td class="td1"></td>
					<td class="td2"><span color="red">数量大于99999时表示该奖品无数量限制</span></td>
				</tr>
				<tr>
					<td class="td1">奖品余量:</td>
					<td class="td2"><input class="easyui-numberbox" type="text"
						name="awardMargin" value="${bean.awardMargin}"
						data-options="required:true,validType:'length[0,32]'" /></td>
				</tr>
				<tr>
					<td class="td1">奖励红豆数:</td>
					<td class="td2"><input class="easyui-numberbox" type="text"
						name="rbcAmount" value="${bean.rbcAmount}"
						data-options="validType:'length[0,32]'" /></td>
				</tr>
				<tr>
					<td class="td1">奖励钱包(元):</td>
					<td class="td2"><input class="easyui-numberbox" type="text"
						name="walletAmount" value="${bean.walletAmount}"
						data-options="validType:'length[0,32]'" /></td>
				</tr>
				<tr>
					<td class="td1">备注:</td>
					<td class="td2"><input class="easyui-textbox" name="remark"
						value="${bean.remark}"
						data-options="multiline:true,validType:'length[0,1000]'"
						style="height: 120px" /></td>
				</tr>

			</table>
		</form>
	</div>
</body>
</html>



