<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>红手指任务</title>
<meta name="decorator" content="moduleIndex" />
<style type="text/css">
.radioSpan {
  position: relative;
  border: 1px solid #95B8E7;
  background-color: #fff;
  vertical-align: middle;
  display: inline-block;
  overflow: hidden;
  white-space: nowrap;
  margin: 0;
  padding: 0;
  -moz-border-radius: 5px 5px 5px 5px;
  -webkit-border-radius: 5px 5px 5px 5px;
  border-radius: 5px 5px 5px 5px;
  display:block;
}
</style>
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
			success : callback
		});
		function feedbackStatusSelect(record) {
			$("#gameTr,#surveyTr,#gameListTr,#gameServiceAreaTr,#gameConditionTr").addClass("hide");
			if(record.value=="wjdc"){
				$("#surveyTr").removeClass("hide");
			}else if (record.value == "gamedownload") {
				$("#gameTr").removeClass("hide");
			}else if (record.value == "game") {
				$("#gameListTr,#gameServiceAreaTr,#gameConditionTr").removeClass("hide");
			}
		}
		
		function choiceTaskkClassify(){
			$("#rbcAwardTr,#scoreAwardTr,#autoReceiveTaskTr,#autoReceiveAwardTr,#activationCodeTypeTr,#autoAwardTypeTr").addClass('hide');
			if($("#taskClassify").combobox('getValue')=='1'){
				$("#rbcAwardTr,#autoReceiveTaskTr,#autoReceiveAwardTr,#autoAwardTypeTr").removeClass('hide');
			}else if($("#taskClassify").combobox('getValue')=='2'){
				$("#scoreAwardTr").removeClass('hide');
			}
		}
		
		function choiceDownloadGame(){
			$("#gameId").val($("#downloadGameId").combobox('getValue'));
			$("#gameName").val($("#downloadGameId").combobox('getText'));
		}
		
		function choiceTaskGame(){
			$("#gameId").val($("#taskGameId").combobox('getValue'));
			$("#gameName").val($("#taskGameId").combobox('getText'));
		}
				
		$.extend($.fn.validatebox.defaults.rules, {  
			selectValueRequired: {  
				validator: function(value,param){ 
					return $(param[0]).find("option:contains('"+value+"')").val() != '';  
				},  
				message: '该选项为必选项'  
			}  
		});
		
		function saveTaskCheck(){
			var taskType = $('#taskType').combobox('getValue');
			if(taskType == 'wjdc' && $('#surveyId').combobox('getValue')==''){
				alert("请选择问卷调查");
				return false;
			}
			var taskClassify = $('#taskClassify').combobox('getValue');
			var awardType = $(":radio[name='awardType']:checked").val();
			if(taskClassify == '1' && awardType=='1' && $('#rbcAward').numberbox('getValue')==''){
				alert("请输入红豆奖励数量");
				return false;
			}
			if(taskClassify == '2' && $('#scoreAward').numberbox('getValue')==''){
				alert("请输入积分奖励数量");
				return false;
			}
			return true;
		}
		
		$(document).ready(function(){
			$("radio[name=autoReceiveTask][value=${task.autoReceiveTask}]").attr("checked",true);
			$("radio[name=autoReceiveAward][value=${task.autoReceiveAward}]").attr("checked",true);
			
			$(":radio[name='awardType']").click(function(){
		        var type = $(":radio[name='awardType']:checked").val();
		        if(type == '3'){
		       		$("#rbcAwardTr").addClass("hide");
		       		$("#activationCodeTypeTr").removeClass("hide");
		        	$("#activationCodeTypeTr").addClass("show");
		        }else if(type == '1'){
		        	$("#activationCodeTypeTr").addClass("hide");
		        	$("#rbcAwardTr").removeClass("hide");
		        }
		    });
			
		});
	</script>
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post" >
			<c:if test="${not empty task.taskId}">
				<input type="hidden" name="taskId" value="${task.taskId }">
			</c:if>
			<table class="easyui-table">
				<tr>
					<td class="td1">任务名称:</td>
					<td class="td2">
						<input class="easyui-textbox" type="text" name="taskName" value="${task.taskName }" data-options="required:true,validType:'length[0,32]'" />
					</td>
				</tr>
				<tr>
					<td class="td1">任务分类:</td>
					<td class="td2">
						<select class="easyui-combobox input_width_short" editable="false" id="taskClassify" name="taskClassify" data-options="onSelect:choiceTaskkClassify" validType="selectValueRequired['#taskClassify']">
							<option value="">[全部]</option>
						   <fns:getOptions category="rf_task.task_classify" value="${task.taskClassify}"></fns:getOptions>
					 	</select>
					</td>
				</tr>
				<tr>
					<td class="td1">任务类型:</td>
					<td class="td2">
						<select class="easyui-combobox input_width_short"  editable="false" id="taskType" name="taskType" data-options="onSelect:feedbackStatusSelect" validType="selectValueRequired['#taskType']">
							<option value="">[全部]</option>
						   <fns:getOptions category="task_system.category" value="${task.taskType}"></fns:getOptions>
					 	</select>
					</td>
				</tr>
				<tr id="gameTr" class="${task.taskType=='gamedownload'?'show':'hide'}">
					<td class="td1">请选择游戏:</td>
					<td class="td2">
						<input type="hidden" id="gameId" name="gameId" value="${task.gameId}"/>
						<input type="hidden" id="gameName" name="gameName" value="${task.gameName}"/>
						<select	class="easyui-combobox input_width_short" id="downloadGameId" name="downloadGameId" data-options="editable:true,required:true,onSelect:choiceDownloadGame">
							<option value="">[全部]</option>
							<c:forEach var="apk" items="${appApkList}">
								<option value="${apk.id}" ${task.gameId==apk.id?"selected":""}>${apk.name}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr id="surveyTr" class="${task.taskType=='wjdc'?'show':'hide'}">
					<td class="td1">问卷调查:</td>
					<td class="td2">
						<select	class="easyui-combobox input_width_short" id="surveyId" name="surveyId" data-options="editable:false,required:true">
							<option value="">[全部]</option>
							<c:forEach var="s" items="${surveyList }">
								<option value="${s.surveyId}"  <c:if test="${task.surveyId==s.surveyId}">selected="selected"</c:if>>${s.surveyName}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr id="gameListTr" class="${task.taskType=='game'?'show':'hide'}">
					<td class="td1">选择游戏:</td>
					<td class="td2">
						<select	class="easyui-combobox input_width_short" id="taskGameId" name="taskGameId" data-options="editable:false,onSelect:choiceTaskGame" style="width:100px">
							<option value="">[全部]</option>
							<c:forEach var="apk" items="${appApkList}">
								<option value="${apk.id}" ${task.gameId==apk.id?"selected":""}>${apk.name}</option>
							</c:forEach>
						</select>
						&nbsp;
						游戏区服：
					 	<input class="easyui-numberbox" type="text" id ="serviceArea" name="serviceArea" value="${task.serviceArea }" data-options="required:false,validType:'length[0,20]'" style="width:90px"/>
					</td>
				</tr>
				<tr id="gameConditionTr" class="${task.taskType=='game'?'show':'hide'}">
					<td class="td1">完成条件:</td>
					<td class="td2">
						<select class="easyui-combobox input_width_short" editable="false" id="condition" name="condition" data-options="editable:false" style="width:100px">
							<option value="">[全部]</option>
						    <c:forEach var="cs" items="${conditions }">
								<option value="${cs.key}" <c:if test="${cs.key==task.condition}">selected="selected"</c:if>>${cs.value}</option>
							</c:forEach>
					 	</select>
					 	&nbsp;
					 	数值：
					 	<input class="easyui-numberbox" type="text" id ="conditionValue" name="conditionValue" value="${task.conditionValue }" data-options="required:false,validType:'length[0,15]'" style="width:110px"/>
					</td>
				</tr>
				<tr>
					<td class="td1">任务编码:</td>
					<td class="td2">
						<input class="easyui-textbox" type="text" id ="taskCode" name="taskCode" value="${task.taskCode }" data-options="required:false,validType:'length[0,32]'" />
					</td>
				</tr>
				<tr>
					<td class="td1">红手指任务描述:</td>
					<td class="td2">
						<input class="easyui-textbox" name="remark"	value="${task.remark }"	data-options="required:true,multiline:true,validType:'length[0,700]'" style="height: 60px" />
					</td>
				</tr>
				<tr class="${task.taskClassify=='1'?'show':'hide'}" id="autoReceiveTaskTr">
					<td class="td1">需要领取任务:</td>
					<td class="td2">
						<span class="radioSpan">
			                <label><input type="radio" name="autoReceiveTask" value="1" <c:if test="${'1'==task.autoReceiveTask}">checked="checked"</c:if>>否</label>
			                <label><input type="radio" name="autoReceiveTask" value="0" <c:if test="${'0'==task.autoReceiveTask}">checked="checked"</c:if>>是</label>
			            </span>
					</td>
				</tr>
				<tr class="${task.taskClassify=='1'?'show':'hide'}" id="autoReceiveAwardTr">
					<td class="td1">自动领取奖励:</td>
					<td class="td2">
						<span class="radioSpan">
			                <label><input type="radio" name="autoReceiveAward" value="0" <c:if test="${'0'==task.autoReceiveAward}">checked="checked"</c:if>>否</label>
			                <label><input type="radio" name="autoReceiveAward" value="1" <c:if test="${'1'==task.autoReceiveAward}">checked="checked"</c:if>>是</label>
			            </span>
					</td>
				</tr>
				<tr id="autoAwardTypeTr" class="${task.taskClassify=='1'?'show':'hide'}">
					<td class="td1">奖励类型:</td>
					<td class="td2">
						<span class="radioSpan">
			                <label><input type="radio" name="awardType" value="1" <c:if test="${'1'==task.awardType}">checked="checked"</c:if>>红豆</label>
			                <label><input type="radio" name="awardType" value="3" <c:if test="${'3'==task.awardType}">checked="checked"</c:if>>激活码</label>
			            </span>
					</td>
				</tr>
				
				<tr class="${task.awardType=='1'?'show':'hide'}" id="rbcAwardTr">
					<td class="td1">红豆奖励数量:</td>
					<td class="td2">
						<input class="easyui-numberbox" type="text"	id="rbcAward" name="rbcAward" value="${task.rbcAward }" data-options="validType:'length[0,32]'" />
					</td>
				</tr>
				<tr class="${task.awardType=='2'?'show':'hide'}" id="scoreAwardTr">
					<td class="td1">积分奖励数量:</td>
					<td class="td2">
						<input class="easyui-numberbox" type="text" id="scoreAward" name="scoreAward" value="${task.scoreAward }" data-options="validType:'length[0,32]'" />
					</td>
				</tr>
				
				<tr id="activationCodeTypeTr" class="${task.awardType=='3'?'show':'hide'}">
					<td class="td1">激活码类型:</td>
					<td class="td2">
						<select class="easyui-combobox input_width_short" editable="false" id="typeId" name="typeId" data-options="editable:false">
							<c:forEach var="dto" items="${codeTypes }">
								<option value="${dto.typeId}" <c:if test="${dto.typeId==activationCodeTypeId}">selected="selected"</c:if>>${dto.codeTypeName}</option>
							</c:forEach>
					 	</select>
					</td>
				</tr>
				
				<tr>
					<td class="td1">阀值(0为不限制完成次数):</td>
					<td class="td2">
						<input class="easyui-numberbox" type="text" name="taskThreshold" value="${task.taskThreshold }" data-options="required:true,validType:'length[0,32]'" />
					</td>
				</tr>
				<tr>
					<td class="td1">任务开始时间:</td>
					<td class="td2">
						<input type="text" class="easyui-datetimebox input_width_default" name="beginTimeStr" value="<fmt:formatDate value="${task.taskStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"	data-options="required:true,editable:false" />
					</td>
				</tr>
				<tr>
					<td class="td1">任务结束时间:</td>
					<td class="td2">
						<input type="text" class="easyui-datetimebox input_width_default" name="endTimeStr"	value="<fmt:formatDate value="${task.taskEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" data-options="required:true,editable:false" />
					</td>
				</tr>
				<tr>
					<td class="td1">排序:</td>
					<td class="td2">
						<input class="easyui-numberbox" type="text" name="reorder" value="${task.reorder }" data-options="required:true,validType:'length[0,32]'" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
<script type="text/javascript">
choiceTaskkClassify();
</script>
</html>