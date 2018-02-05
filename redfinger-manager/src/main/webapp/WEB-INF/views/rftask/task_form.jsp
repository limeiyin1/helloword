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
		
		//taskType任务类型切换
		function feedbackStatusSelect(record) {
			$("#gameTr,#surveyTr,#gameListTr,#gameConditionTr").addClass("hide");
			if(record.value=="wjdc"){
				$("#surveyTr").removeClass("hide");
			}else if (record.value == "gamedownload") {
				$("#gameTr").removeClass("hide");
			}else if (record.value == "gametask" && $("#taskClassify").combobox('getValue')=='1') {
				$("#gameListTr,#gameConditionTr").removeClass("hide");	//游戏任务显示条件区
			}
		}
		//taskClassify任务分类切换
		function choiceTaskClassify(){
			$("#rbcAwardTr,#scoreAwardTr,#autoReceiveTaskTr,#autoReceiveAwardTr,#activationCodeTypeTr,#autoAwardTypeTr").addClass('hide');
			if($("#taskClassify").combobox('getValue')=='1'){//红豆任务
				$("#autoReceiveTaskTr,#autoReceiveAwardTr,#autoAwardTypeTr").removeClass('hide');
				$(":radio[name='awardType']:checked").trigger("click");//显示赠送红豆或激活码
				if($("#taskType").combobox('getValue')=='gametask'){
					$("#gameListTr,#gameConditionTr").removeClass("hide");
				}
			}else if($("#taskClassify").combobox('getValue')=='2'){//积分任务
				$("#scoreAwardTr").removeClass('hide');
				$("#gameListTr,#gameConditionTr").addClass("hide");	//积分任务没有游戏任务
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
			var taskClassify = $("#taskClassify").combobox('getValue');
			if(taskClassify!='1' && taskType=='gametask'){
				alert("游戏任务不支持积分奖励");
				return false;
			}
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
			/* $.post("${ctx}//rftask/task/taskCount",$("#module_submit_form").serialize(),function(data){
				if(jQuery.type(data)=='string'){
					data=eval('(' + data + ')'); 
				}
				if(data.code == '200'){
					$.messager.confirm('提示','任务时间范围内存在相同的任务，确定保存?', function(btnData){
						alert(btnData);
						if(btnData){
							return true;
						}
					});
					return false;
				}else{
					alert("数据校验失败，请联系管理员");
					return false;
				}
			}); */
		}
		
		$(document).ready(function(){
			$(":radio[name='awardType']").click(function(){
		        var type = $(":radio[name='awardType']:checked").val();
		        if(type == '3'){//奖励激活码
		       		$("#rbcAwardTr").addClass("hide");
		       		$("#activationCodeTypeTr").removeClass("hide");
		        }else if(type == '1'){	//奖励红豆
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
						<select class="easyui-combobox input_width_short" editable="false" id="taskClassify" name="taskClassify" data-options="onSelect:choiceTaskClassify" validType="selectValueRequired['#taskClassify']">
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
								<option value="${s.surveyId}" ${task.surveyId==s.surveyId?"selected=true":""}>${s.surveyName}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr id="gameListTr" class="${task.taskClassify=='1' and task.taskType=='gametask'?'show':'hide'}">
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
					 	<input class="easyui-textbox" type="text" id ="serviceArea" name="serviceArea" value="${task.serviceArea}" data-options="required:false,validType:'length[0,20]'" style="width:90px"/>
					</td>
				</tr>
				<tr id="gameConditionTr" class="${task.taskClassify=='1' and task.taskType=='gametask'?'show':'hide'}">
					<td class="td1">完成条件:</td>
					<td class="td2">
						<select class="easyui-combobox input_width_short" editable="false" id="condition" name="condition" data-options="editable:false" style="width:100px">
							<option value="">[全部]</option>
						   <fns:getOptions category="rf_task.game_condition" value="${task.condition}"></fns:getOptions>
					 	</select>
					 	&nbsp;
					 	数值：
					 	<input class="easyui-numberbox" type="text" id ="conditionValue" name="conditionValue" value="${task.conditionValue}" data-options="required:false,validType:'length[0,15]'" style="width:110px"/>
					</td>
				</tr>
				<tr>
					<td class="td1">任务编码:</td>
					<td class="td2">
						<input class="easyui-textbox" type="text" id ="taskCode" name="taskCode" value="${task.taskCode }" data-options="required:false,validType:'length[0,32]'" />
					</td>
				</tr>
				<tr>
					<td class="td1" valign="top">红手指任务描述:</td>
					<td class="td2">
						<textarea name="remark"	class="easyui-validatebox" style="height:120px;width:280px;" validType="text[1,700]" required="true">${task.remark }</textarea>
					</td>
				</tr>
				<!-- 
				<tr class="${task.taskClassify=='1'?'show':'hide'}" id="autoReceiveTaskTr">
					<td class="td1">需要领取任务:</td>
					<td class="td2">
						<span class="radioSpan">
			                <label><input type="radio" name="autoReceiveTask" value="1" ${task.autoReceiveAward=="1"?"checked=true":"" }>否</label>
			                <label><input type="radio" name="autoReceiveTask" value="0" ${task.autoReceiveAward!="1"?"checked=true":"" }>是</label>
			            </span>
					</td>
				</tr>
				<tr class="${task.taskClassify=='1'?'show':'hide'}" id="autoReceiveAwardTr">
					<td class="td1">自动领取奖励:</td>
					<td class="td2">
						<span class="radioSpan">
			                <label><input type="radio" name="autoReceiveAward" value="0" ${task.autoReceiveAward=="1"?"checked=true":"" }>否</label>
			                <label><input type="radio" name="autoReceiveAward" value="1" ${task.autoReceiveAward!="1"?"checked=true":"" }>是</label>
			            </span>
					</td>
				</tr>
				 -->
				<tr id="autoAwardTypeTr" class="${task.taskClassify=='1'?'show':'hide'}">
					<td class="td1">奖励类型:</td>
					<td class="td2">
						<span class="radioSpan">
			                <label><input type="radio" name="awardType" value="1" ${task.awardType=="1"||task.awardType==null?"checked=true":"" }>红豆</label>
			                <label><input type="radio" name="awardType" value="3" ${task.awardType=="3"?"checked=true":"" }>激活码</label>
			            </span>
					</td>
				</tr>
				
				<tr class="${task.taskClassify=='1' && task.awardType=='1'?'show':'hide'}" id="rbcAwardTr">
					<td class="td1">红豆奖励数量:</td>
					<td class="td2">
						<input class="easyui-numberbox" type="text"	id="rbcAward" name="rbcAward" value="${task.rbcAward }" data-options="validType:'length[0,32]'" />
					</td>
				</tr>
				<tr class="${task.taskClassify=='2'?'show':'hide'}" id="scoreAwardTr">
					<td class="td1">积分奖励数量:</td>
					<td class="td2">
						<input class="easyui-numberbox" type="text" id="scoreAward" name="scoreAward" value="${task.scoreAward }" data-options="validType:'length[0,32]'" />
					</td>
				</tr>
				
				<tr id="activationCodeTypeTr" class="<c:if test="${task.taskClassify!='1' or task.awardType!='3'}">hide</c:if>">
					<td class="td1">激活码类型:</td>
					<td class="td2">
						<select class="easyui-combobox input_width_short" editable="false" id="typeId" name="typeId" data-options="editable:true">
							<c:forEach var="dto" items="${codeTypes }">
								<option value="${dto.typeId}" ${dto.typeId==activationCodeTypeId?"selected":""}>${dto.codeTypeName}</option>
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