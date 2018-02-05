<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>红手指任务</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$.extend($.fn.validatebox.defaults.rules, {
			selectValueRequired: {
				validator: function(value,param){ 
					return $(param[0]).find("option:contains('"+value+"')").val() != '';  
				},  
				message: '该选项为必选项'  
			}  
		});
		
		function rechargeTip(){
			var conditionType = $("#conditionType").combobox('getValue');
			if(conditionType=='2' || conditionType=='3'){
				$("#rechargeTip").show();
			}else{
				$("#rechargeTip").hide();
			}
		}
		
		$(document).ready(function(){
		 var dt_buttons = $.extend([], $.fn.datetimebox.defaults.buttons);
        	dt_buttons.splice(2, 1, {
            text: '清空',
            handler: function(target){
                $(target).datetimebox('setValue','');
            }
        });
        $('.easyui-datetimebox').datetimebox({
            buttons: dt_buttons,
            editable:false,
            width:'150px'
        });
        
		
		});
		
	</script>
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post" name="subtask_submit_form">
			<input type="hidden" name="taskId" value="${subTask.taskId }">
			<input type="hidden" name="parentTaskId" value="${parentTask.taskId }">
			<table class="easyui-table">
				<tr>
					<td class="td1">子任务名称:</td>
					<td class="td2">
						<input class="easyui-textbox" type="text" name="taskName" value="${subTask.taskName }" data-options="required:true,validType:'length[0,32]'" />
					</td>
				</tr>
				
				<c:if test="${parentTask.taskType=='gametask' }">
				<tr>
					<td class="td1">任务结束时间:</td>
					<td class="td2">
						<input type="text" class="easyui-datetimebox input_width_default" name="endTimeStr"	value="<fmt:formatDate value="${subTask.taskEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" data-options="editable:false" />
					</td>
				</tr>
				
				<tr>
				<td class="td1">区服判断:</td>
				<td class="td2">
					<select class="easyui-combobox input_width_short" editable="false" id="conditionCalc" name="conditionCalc" data-options="editable:false" style="width:90px">
						<option value=">=" ${subTask.conditionCalc==">="?"selected":""}>大于等于</option>
						<option value=">" ${subTask.conditionCalc==">"?"selected":""}>大于</option>
						<option value="=" ${subTask.conditionCalc=="="?"selected":""}>等于</option>
				 	</select>
				 	&nbsp;
				 	游戏区服:
				 	<input class="easyui-textbox" type="text" id ="serviceArea" name="serviceArea" value="${subTask.serviceArea}" data-options="required:false,validType:'length[0,20]'" style="width:80px"/>
				</td>
				</tr>
				</c:if>
				
				<tr>
					<td class="td1">完成条件:</td>
					<td class="td2">
						<c:choose>
						 	<c:when test="${parentTask.taskType=='gametask' }">
						 		<select class="easyui-combobox input_width_short" editable="false" id="conditionType" name="conditionType" data-options="onSelect:rechargeTip" validType="selectValueRequired['#conditionType']" style="width:110px">
									<option value="">[全部]</option>
								    <fns:getOptions category="rf_task.game_condition" value="${subTask.conditionType}"></fns:getOptions>
							 	</select>
						 	</c:when>
					 		
						 	<c:otherwise>
						 		<select class="easyui-combobox input_width_short" editable="false" id="conditionType" name="conditionType" data-options="editable:false" style="width:110px">
									<option value="1">累计签到天数</option>
							 	</select>
						 	</c:otherwise>
						</c:choose>
					 	&nbsp;
					 	条件值：
					 	<input class="easyui-numberbox" type="text" id ="conditionValueStart" name="conditionValueStart" value="${subTask.conditionValueStart}" data-options="required:false,validType:'length[0,15]'" style="width:70px"/>
					 	<span id="rechargeTip" style="color:red;${subTask.conditionType=='2' or subTask.conditionType=='3'?'':'display:none'}">单位:分</span>
					</td>
				</tr>
								
				<tr>
					<td class="td1">红豆奖励:</td>
					<td class="td2">
						<input type="hidden" name="taskAwardList[0].awardId" value="${rbcAward.awardId}"/>
						<input type="hidden" name="taskAwardList[0].awardType" value="1"/>
						<input class="easyui-numberbox" type="text"	id="rbcAward" name="taskAwardList[0].awardNum" value="${rbcAward.awardNum }" data-options="validType:'length[0,32]'" style="width:90px"/>
						&nbsp;积分奖励:
						<input type="hidden" name="taskAwardList[1].awardId" value="${scoreAward.awardId}"/>
						<input type="hidden" name="taskAwardList[1].awardType" value="2"/>
						<input class="easyui-numberbox" type="text" id="scoreAward" name="taskAwardList[1].awardNum" value="${scoreAward.awardNum }" data-options="validType:'length[0,32]'" style="width:90px"/>
					</td>
				</tr>
				<tr>
					<td class="td1" valign="top">奖励激活码类型:</td>
					<td class="td2">
						<input type="hidden" name="taskAwardList[2].awardId" value="${activationCodeAward.awardId}"/>
						<input type="hidden" name="taskAwardList[2].awardType" value="3"/>
						<select class="easyui-combobox input_width_short" editable="false" id="activationCodeTypeId" name="taskAwardList[2].foreignKey" data-options="editable:false" style="width:200px">
							<option value="">[请选择]</option>
							<c:forEach var="dto" items="${activationCodeTypes }">
								<option value="${dto.typeId}" ${dto.typeId==activationCodeAward.foreignKey?"selected":""}>${dto.codeTypeName}</option>
							</c:forEach>
					 	</select>
					</td>
				</tr>
				<tr>
					<td class="td1" valign="top">奖励优惠券类型:</td>
					<td class="td2">
						<input type="hidden" name="taskAwardList[3].awardId" value="${couponAward.awardId}"/>
						<input type="hidden" name="taskAwardList[3].awardType" value="4"/>
						<select class="easyui-combobox input_width_short" editable="false" id="couponTypeId" name="taskAwardList[3].foreignKey" data-options="editable:false" style="width:200px">
							<option value="">[请选择]</option>
							<c:forEach var="couponType" items="${couponTypes }">
								<option value="${couponType.typeId}" ${couponType.typeId==couponAward.foreignKey?"selected":""}>[${couponType.typeName}]</option>
							</c:forEach>
					 	</select>
					</td>
				</tr>
				<tr>
					<td class="td1" valign="top">任务描述:</td>
					<td class="td2">
						<textarea rows="10" cols="40" id="remark" name="remark" maxlength="500">${subTask.remark}</textarea>
					</td>
				</tr>
				<tr>
					<td class="td1">排序:</td>
					<td class="td2">
						<input class="easyui-numberbox" type="text" name="reorder" value="${subTask.reorder }" data-options="required:true,validType:'length[0,32]'" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>