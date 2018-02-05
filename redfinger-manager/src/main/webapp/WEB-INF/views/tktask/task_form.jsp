<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>红手指任务</title>
<script type="text/javascript" src="${ctxStatic}/js/jquery-1.7.2.min.js"></script>
<!-- EASYUI begin -->
<link rel="stylesheet" type="text/css" href="${ctxStatic}/jquery-easyui-1.4.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/jquery-easyui-1.4.1/themes/icon.css">
<script type="text/javascript" src="${ctxStatic}/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
<!-- EASYUI end -->

<!-- HOTKEYS begin  -->
<script type="text/javascript" src="${ctxStatic}/js/jquery.hotkeys.js"></script>
<!-- HOTKEYS end  -->
<!-- customer begin -->
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/module.css">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/icons.css">
<script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/module_util.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/module_setting.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/module_ajax.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/module_query.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/module_formatter.js"></script>
<script type="text/javascript">var host='${ctx}/${model}/';</script>
<script type="text/javascript">var dict=${dictCategoryMap}</script>
<!-- customer end -->
</head>
<body>
	<script type="text/javascript">
		var pk = "subTaskId";	//等于taskId时，编辑子任务后保存父任务会有问题
		var module_datagrid = "#module_datagrid";
		var module_dialog = "#module_dialog";
		var module_search_form = "#module_search_form"
		var module_submit_form = "#module_submit_form";
		var callback = defaultCallback;
		
		var dataGridParamObj = {
			url : host + "subTaskListEdit?parentTaskId=${task.taskId}",
			idField : 'subTaskId',
			singleSelect: false,
			checkOnSelect: true, 
			selectOnCheck: true,
			onCheck : function(row) {
	
			},
			columns : [[ 
	              {width : 100,title : 'id',field : 'subTaskId',checkbox : true}, 
	              {width : 100,title : '子任务名',field : 'taskName',sortable : true}, 
	              {width : 300,title : '子任务描述',field : 'remark',sortable : true}, 
	              {width : 100,title : '奖励红豆',field : 'map.rbcAward',sortable : false}, 
	              {width : 100,title : '奖励积分',field : 'map.scoreAward',sortable : false},
	              {width : 100,title : '激活码类型',field : 'map.activationCodeTypeName',sortable : false},
	              {width : 100,title : '优惠券类型',field : 'map.couponTypeName',sortable : false},
	              {width : 100,title : '已完成次数',field : 'finishCount',sortable : true},
	              {width : 100,title : '已奖励次数',field : 'awardCount',sortable : true},
	              {width : 100,title : '结束时间',field : 'taskEndTime',sortable : true, formatter:formatterTime},
	              {width : 100,title : '创建人',field : 'creater',sortable : true},
	              {width : 50,title : '排序',field : 'reorder',sortable : true},
	              {width : 100,title : '执行状态',field : 'enableStatus',sortable : true,formatter: formatterStop}
	          	]]
			};
		var dialogParamObj = {
				
		};
		
		// 普通表格默认参数
		var lookDataGridParam = {
			fitColumns : true,
			pagination : true,
			striped : true,
			rownumbers : true,
			singleSelect : false,
			idField : 'id',
			pageSize : 20,
			pageList : [ 10, 15, 20, 50, 100, 200,500 ],
			loadFilter : loadFilterForDataGrid,
// 			onDblClickRow:lookSpecific,
			loadMsg : "处理中，请稍后..."
		};
		$(function() {
			$(module_datagrid).datagrid($.extend({}, lookDataGridParam, dataGridParamObj));
			$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
		});
	
		//保存任务
		var saveTaskCallback = function(data){
			innerCallback(data, function(){
				var taskId= "${task.taskId}";
				if(taskId==''){//新增时
					$.messager.confirm('操作提示','新增成功，继续新增任务?', function(btnData){
						if(btnData){
							location.reload();
						}else{
							close();
						}
					});
				}else{	//修改时
					$.messager.alert('操作提示','修改成功', "info", function(btnData){
						refreshTask();
					});
				}
			});
		}
		
		//保存子任务
		var saveSubTaskCallback = function(data){
			innerCallback(data, function(){
				$.messager.alert('操作提示','保存成功', "info", function(btnData){
					if($("#subSaveType").val()=='next'){	//保存任务并新增子任务的，刷新页面
						refreshTask();
					}else{
						fresh();	//新增、编辑子任务后刷新列表
					}
				});
			});
		}
		
		//保存任务并新增子任务
		var saveTaskAndNextCallback = function(data){
			innerCallback(data, function(){
				if (jQuery.type(data) == 'string') {
					data = eval('(' + data + ')');
				}
				var taskId = data.taskId;
				$("#taskId").val(taskId);
				
				var parentTaskId = $("#taskId").val();
				if(parentTaskId==''){
					$.messager.alert('提示', '父任务ID为空，请联系管理员', "error");
					return;
				}
				
				addSubTask();
				$("#button-cancel").unbind("click").click(refreshTask);	//关闭按钮，刷新页面
				$("#subSaveType").val("next");
			});
		}
		
		function editSubTask(){
			var subTaskId = getGridId();
			if (!subTaskId) {
				return false;
			}
			
			var title = $("title").html() + " - 新增";
			var href = host + 'subform?taskId=' + subTaskId;
			$("#button-save").unbind("click").click(saveSubTask);
			$("#button-cancel").unbind("click").click(cancel);
			openDialogForm(title, href);
			
			$("#subSaveType").val("current");
		}
		
		function addSubTask(){
			var parentTaskId = $("#taskId").val();
			if(parentTaskId==''){
				$.messager.alert('提示', '父任务ID为空，请联系管理员', "error");
				return;
			}
			var title = $("title").html() + " - 新增";
			var href = host + 'subform?parentTaskId=' + parentTaskId;
			$("#button-save").unbind("click").click(saveSubTask);
			$("#button-cancel").unbind("click").click(cancel);
			openDialogForm(title, href);
			
			$("#subSaveType").val("current");
		}
		
		function refreshTask(){
			var taskId= $("#taskId").val();
			location.href = host + "form?taskId="+taskId;
		}
		
		var close = function(){
			var topJQ = top.jQuery;
			var tab=topJQ('#easyui-tabs').tabs('getSelected');//获取当前选中tabs
		    var index = topJQ('#easyui-tabs').tabs('getTabIndex',tab);//获取当前选中tabs的index
		    topJQ('#easyui-tabs').tabs('close',index);//关闭对应index的tabs  
		}
		
		//保存子任务
		var saveSubTask = function() {
			if ($("[name=subtask_submit_form]").form("validate")) {
				$.messager.progress();
				$("[name=subtask_submit_form]").form({
					url : host + 'saveSub',
					success : saveSubTaskCallback
				});
				$("[name=subtask_submit_form]").form("submit");
			}
		}
		
		//保存任务
		var saveTask = function() {
			if ($("[name=task_submit_form]").form("validate")) {
				if(saveTaskCheck()){
					$.messager.progress();
					$("[name=task_submit_form]").form({
						url : host + 'save',
						success : saveTaskCallback
					});
					$("[name=task_submit_form]").form("submit");
				}
			}
		}
		
		//保存任务并新增子任务
		var saveTaskAndNext = function() {
			if ($("[name=task_submit_form]").form("validate")) {
				if(saveTaskCheck()){
					$.messager.progress();
					$("[name=task_submit_form]").form({
						url : host + 'save',
						success : saveTaskAndNextCallback
					});
					$("[name=task_submit_form]").form("submit");
				}
			}
		}
		
		//taskModule、taskType切换
		function feedbackStatusSelect(record) {
			var taskModule = $("#taskModule").combobox('getValue'); 
			var taskType = $("#taskType").combobox('getValue'); 
		
			$("#gameTr,#surveyTr,[name=gameTaskTr],#subTaskSaveBtn").hide();
			if(taskModule == "2"){	//签到
				$("#subTaskSaveBtn").show();
			}else if(taskType=="wjdc"){	//问卷调查
				$("#surveyTr").show("hide");
			}else if (taskType == "gamedownload") {	//游戏下载
				$("#gameTr").show("hide");
			}else if (taskType == "gametask") {	//游戏任务
				$("#subTaskSaveBtn,[name=gameTaskTr]").show();
			}
		}
		
		function choiceDownloadGame(){
			$("#gameId").val($("#downloadGameId").combobox('getValue'));
			$("#gameName").val($("#downloadGameId").combobox('getText'));
		}
		
		var gameJson = ${not empty gameJson?gameJson:"null"};
		function choiceTaskGame(){
			var gameId = $("#taskGameId").combobox('getValue');
			$("#gameId").val(gameId);
			$("#gameName").val($("#taskGameId").combobox('getText'));
			
			//切换游戏时更好icon
			if(gameJson != null){
				eval("game=gameJson.game"+gameId);
				
				$("#gameIconDiv").show();
				$("input[name=gameIconFile]").hide();
				$("#gameIcon").val(game.icon);
				$("#gameIconImg").attr("src", game.icon);
				
				$("#gameDesc").textbox("setValue", game.oneword);
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

		function saveTaskCheck(){
			var taskType = $('#taskType').combobox('getValue');
			if(taskType == 'wjdc' && $('#surveyId').combobox('getValue')==''){
				alert("请选择问卷调查");
				return false;
			}
			if(taskType == 'gametask' && $('#taskGameId').combobox('getValue')==''){
				alert("请选择游戏");
				return false;
			}
			return true;
		}
		
		function delGameIcon(){
			$("#gameIconDiv").hide();
			$("input[name=gameIconFile").show();
			$("#gameIcon").val("");
		}
		
	</script>
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post" name="task_submit_form" enctype="multipart/form-data">
			<input type="hidden" id="taskId" name="taskId" value="${task.taskId }">
			<input type="hidden" id="subSaveType" value="">
			<input type="hidden" id="gameId" name="gameId" value="${task.gameId}"/>
			<input type="hidden" id="gameName" name="gameName" value="${task.gameName}"/>
			<table class="easyui-table">
				<tr>
					<td class="td1">任务名称:</td>
					<td class="td2">
						<input class="easyui-textbox" type="text" name="taskName" value="${task.taskName }" data-options="required:true,validType:'length[0,32]'" />
					</td>
				</tr>
				<tr>
					<td class="td1">任务状态:</td>
					<td class="td2">
						<span>
			                <label><input type="radio" name="enableStatus" value="1" ${task.enableStatus=="1"?"checked=true":"" }>启用</label>
			                <label><input type="radio" name="enableStatus" value="0" ${task.enableStatus!="1"?"checked=true":"" }>禁用</label>
			            </span>
					</td>
				</tr>
				<tr>
					<td class="td1">标签:</td>
					<td class="td2">
						<select class="easyui-combobox input_width_short" editable="false" id="labelId" name="labelId" >
							<option value="">[无]</option>
						    <c:forEach var="label" items="${labelList }">
						    	<option value="${label.labelId}" ${label.labelId==task.labelId?"selected":""}>${label.labelName}</option>
						    </c:forEach>
					 	</select>
					 	&nbsp;<font color='red'>注:选择标签后只有关联该标签的用户才能看到此任务</font>
					</td>
				</tr>
				<tr>
					<td class="td1">任务编码:</td>
					<td class="td2">
						<select class="easyui-combobox input_width_short" editable="false" id="taskCode" name="taskCode" >
							<option value="">[无]</option>
						    <c:forEach var="dict" items="${taskCodeList }">
						    	<option value="${dict.dictValue}" ${dict.dictValue==task.taskCode?"selected":""}>${dict.dictName}[${dict.dictValue}]</option>
						    </c:forEach>
					 	</select>
					</td>
				</tr>
				<tr>
					<td class="td1">任务模块:</td>
					<td class="td2">
						<select class="easyui-combobox input_width_short" editable="false" id="taskModule" name="taskModule" data-options="onSelect:feedbackStatusSelect" validType="selectValueRequired['#taskModule']">
							<option value="">[全部]</option>
						   <fns:getOptions category="tk_task.task_module" value="${task.taskModule}"></fns:getOptions>
					 	</select>
					</td>
				</tr>
				<tr>
					<td class="td1">任务类型:</td>
					<td class="td2">
						<select class="easyui-combobox input_width_short" editable="false" id="taskType" name="taskType" data-options="onSelect:feedbackStatusSelect" validType="selectValueRequired['#taskType']">
							<option value="">[全部]</option>
						   <fns:getOptions category="task_system.category" value="${task.taskType}"></fns:getOptions>
					 	</select>
					</td>
				</tr>
				<tr id="gameTr" class="${task.taskType=='gamedownload'?'show':'hide'}">
					<td class="td1">请选择游戏:</td>
					<td class="td2">
						<select	class="easyui-combobox input_width_short" id="downloadGameId" name="downloadGameId" data-options="editable:true,required:true,onSelect:choiceDownloadGame">
							<option value="">[全部]</option>
							<c:forEach var="apk" items="${apkList}">
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
				<tr name="gameTaskTr" class="${task.taskType=='gametask'?'show':'hide'}">
					<td class="td1">选择游戏:</td>
					<td class="td2">
						<select	class="easyui-combobox input_width_short" id="taskGameId" name="taskGameId" data-options="editable:true,onSelect:choiceTaskGame">
							<option value="">[全部]</option>
							<c:forEach var="apk" items="${apkList}">
								<option value="${apk.id}" icon="${apk.icon}" ${task.gameId==apk.id?"selected":""}>${apk.name}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr name="gameTaskTr" class="${task.taskType=='gametask'?'show':'hide'}">
					<td class="td1">游戏icon:</td>
					<td class="td2">
						<input type="hidden" id="gameIcon" name="gameIcon" value="${task.gameIcon }" />
						<div id="gameIconDiv" class="${not empty task.gameIcon?'show':'hide'}">
							<img src="${task.gameIcon}" id="gameIconImg" style="width:150px;height:100px"/><a href="javascript:delGameIcon()"><img src="${ctx}/static/images/del.png" border=0/></a>
						</div>
						<input type="file" name="gameIconFile" style="width:200px;${not empty task.gameIcon?'display:none;':''}" value="">
					</td>
				</tr>
				<tr name="gameTaskTr" class="${task.taskType=='gametask'?'show':'hide'}">
					<td class="td1">游戏描述:</td>
					<td class="td2">
						<input class="easyui-textbox" type="text" id="gameDesc" name="gameDesc" value="${task.gameDesc }" data-options="validType:'length[0,100]'" />
					</td>
				</tr>
				<tr name="gameTaskTr" class="${task.taskType=='gametask'?'show':'hide'}">
					<td class="td1">按钮文字:</td>
					<td class="td2">
						<input class="easyui-textbox" type="text"	id="taskBtn" name="taskBtn" value="${task.taskBtn }" data-options="validType:'length[0,4]'" style="width:100px"/>
						&nbsp;&nbsp;&nbsp;
						奖励描述:
						<input class="easyui-textbox" type="text" id="awardDesc" name="awardDesc" value="${task.awardDesc }" data-options="validType:'length[0,50]'" style="width:300px"/>
					</td>
				</tr>
				<tr name="gameTaskTr" class="${task.taskType=='gametask'?'show':'hide'}">
					<td class="td1">游戏任务子名称:</td>
					<td class="td2">
						<input class="easyui-textbox" type="text" name="subName" value="${task.subName }" data-options="validType:'length[0,32]'" />
					</td>
				</tr>
				<tr name="gameTaskTr" class="${task.taskType=='gametask'?'show':'hide'}">
					<td class="td1" valign="top">游戏任务子描述:</td>
					<td class="td2">
						<textarea rows="5" cols="40" id="subRemark" name="subRemark" maxlength="500">${task.subRemark}</textarea>
					</td>
				</tr>
				<tr>
					<td class="td1">自动领取任务:</td>
					<td class="td2">
						<span>
			                <label><input type="radio" name="autoReceiveTask" value="1" ${task.autoReceiveTask!="0"?"checked=true":"" }>是</label>
			                <label><input type="radio" name="autoReceiveTask" value="0" ${task.autoReceiveTask=="0"?"checked=true":"" }>否</label>
			            </span>
			            &nbsp;&nbsp;&nbsp;
			          	 自动领取奖励:	
						<span>
			                <label><input type="radio" name="autoReceiveAward" value="1" ${task.autoReceiveAward!="0"?"checked=true":"" }>是</label>
			                <label><input type="radio" name="autoReceiveAward" value="0" ${task.autoReceiveAward=="0"?"checked=true":"" }>否</label>
			            </span>
					</td>
				</tr>
				<tr style="display:none">
					<td class="td1">生成广播:</td>
					<td class="td2">
						<span>
			                <label><input type="radio" name="generateBroadcast" value="1" ${task.generateBroadcast=="1"?"checked=true":"" }>是</label>
			                <label><input type="radio" name="generateBroadcast" value="0" ${task.generateBroadcast!="1"?"checked=true":"" }>否</label>
			            </span>
			            &nbsp;&nbsp;&nbsp;
			                        广播模板:
						<select class="easyui-combobox input_width_short" editable="false" id="broadcastTempCode" name="broadcastTempCode" data-options="editable:false"  style="width:200px">
							<option value="">[请选择]</option>
							
					 	</select>
					</td>
				</tr>
				<tr>
					<td class="td1">红豆奖励:</td>
					<td class="td2">
						<input type="hidden" name="taskAwardList[0].awardType" value="1"/>
						<input class="easyui-numberbox" type="text"	id="rbcAward" name="taskAwardList[0].awardNum" value="${rbcAward.awardNum }" data-options="validType:'length[0,32]'" style="width:100px"/>
						&nbsp;&nbsp;&nbsp;
						奖励激活码类型:
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
					<td class="td1">积分奖励:</td>
					<td class="td2">
						<input type="hidden" name="taskAwardList[1].awardType" value="2"/>
						<input class="easyui-numberbox" type="text" id="scoreAward" name="taskAwardList[1].awardNum" value="${scoreAward.awardNum }" data-options="validType:'length[0,32]'" style="width:100px"/>
						&nbsp;&nbsp;&nbsp;
						奖励优惠券类型:
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
					<td class="td1">任务开始时间:</td>
					<td class="td2">
						<input type="text" class="easyui-datetimebox input_width_default" name="beginTimeStr" value="<fmt:formatDate value="${task.taskStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"	data-options="required:true,editable:false" />
						&nbsp;&nbsp;&nbsp;
						任务结束时间:
						<input type="text" class="easyui-datetimebox input_width_default" name="endTimeStr"	value="<fmt:formatDate value="${task.taskEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" data-options="required:true,editable:false" />
					</td>
				</tr>
				<tr>
					<td class="td1">阀值(0为不限制完成次数):</td>
					<td class="td2">
						<input class="easyui-numberbox" type="text" name="taskThreshold" value="${task.taskThreshold }" data-options="required:true,validType:'length[0,32]'" />
					&nbsp;&nbsp;&nbsp;排序:
						<input class="easyui-numberbox" type="text" name="reorder" value="${task.reorder }" data-options="required:true,validType:'length[0,32]'" />
					</td>
				</tr>
				<tr>
					<td class="td1" valign="top">任务描述:</td>
					<td class="td2">
						<textarea rows="10" cols="80" id="remark" name="remark" maxlength="500">${task.remark}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan=2 style="text-align:center;padding-top:20px">
						<a href="javascript:void(0)" id="taskSaveBtn" class="easyui-linkbutton" iconCls="icon-ok-rf" onclick="saveTask()">保存</a>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0)" id="subTaskSaveBtn" style="${task.taskType=='gametask' or task.taskModule=='2'?'':'display:none'}" class="easyui-linkbutton" iconCls="icon-edit" onclick="saveTaskAndNext()">保存并新增子任务</a>
					</td>
				</tr>
				
				<tr name="subTaskTr" class="${task.taskType=='gametask' or task.taskModule=='2'?'':'hide'}" style="margin-top:25px">
					<td colspan=2 class="td2" style="border-bottom:1px dashed black;">子任务列表</td>
				</tr>
				<tr name="subTaskTr" class="${task.taskType=='gametask' or task.taskModule=='2'?'':'hide'}">
					<td colspan=2>
						<div id="module_toolbar" class="easyui-toolbar">
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="addSubTask()">新增</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="editSubTask()">编辑</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
						</div>
						<table id="module_datagrid" toolbar="#module_toolbar"></table>
					</td>
				</tr>
			</table>
		</form>
		
	</div>

	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
	<div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a>
		<a href="javascript:void(0)" id="button-cancel"	class="easyui-linkbutton" iconCls="icon-no">关闭</a>
	</div>
	
</body>
</html>