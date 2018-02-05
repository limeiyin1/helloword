<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>用户任务管理</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript"
	src="${ctxStatic}/jquery-easyui-1.4.1/datagrid-tkusertaskview.js"></script>
<script type="text/javascript">
	var pk = "userTaskId";
	var module_datagrid = "#module_datagrid";
	var module_dialog = "#module_dialog";
	var module_search_form = "#module_search_form"
	var module_submit_form = "#module_submit_form";
	var callback = defaultCallback;

	var dataGridParamObj = {
		url : host + "list",
		idField : 'userTaskId',
		onCheck : function(row) {

		},
		columns : [[ 
              {width : 100,title : 'id',field : 'userTaskId',checkbox : true}, 
              {width : 100,title : 'userId',field : 'userId'}, 
              {width : 100,title : 'subUserTaskCount',field : 'subUserTaskCount'}, 
              {width : 120,title : '会员ID',field : 'map.externalUserId',sortable : true}, 
              {width : 120,title : '用户手机',field : 'map.userMobilePhone',sortable : true}, 
              {width : 80,title : '已完成次数',field : 'finishCount',sortable : true},
              {width : 80,title : '剩余奖励次数',field : 'remainderAwardCount',sortable : true},
              {width : 100,title : '任务名称',field : 'map.taskName',sortable : true}, 
              {width : 80,title : '任务类型',field : 'map.taskType',sortable : true,formatter:function(value){return getDatagridDict('task_system.category',value);}}, 
              {width : 150,title : '任务描述',field : 'map.taskRemark',sortable : true},
              {width : 60,title : '邀请码',field : 'inviteCode',sortable : true},
              {width : 80,title : '奖励红豆',field : 'rbcAward',sortable : true}, 
              {width : 80,title : '奖励积分',field : 'scoreAward',sortable : true},
              {width : 100,title : '创建时间',field : 'createTime',sortable : true,formatter:formatterTime},
              {width : 100,title : '完成时间',field : 'finishTime',sortable : true,formatter:formatterTime},  
              {width : 50,title : '阀值',field : 'map.taskThreshold',sortable : true,},
              {width : 80,title : '任务状态',field : 'taskStatus',sortable : true,formatter:function(value){return getDatagridDict('rf_task.task_status',value);}},
              {width : 80,title : '执行状态',field : 'enableStatus',sortable : true,formatter: formatterStop}
            ]],
          	view: subusertaskview,
         	detailFormatter:function(index,row){
			  	return '<div style="padding:2px;"><table id="subusertask-' + index + '"></table></div>';
		  	},
		  	onExpandRow: function(index,row){
			  	getSubUserTaskList(index,row);
			},
		};
	var dialogParamObj = {
			
	};
	//双击显示明细
	var lookSpecific =function(index, row){
		if(typeof dblClickCallback!='undefined' && dblClickCallback){
			dblClickCallback.apply(null, [index,row]);
		}else{
			var view=$(module_datagrid).parent();
			if($(view).hasClass("datagrid-view")){
				//获取头
				var heads=["用户手机","已完成次数","剩余奖励次数","任务名称","任务类型","任务描述","邀请码","奖励红豆","奖励积分","创建时间","完成时间","阀值","任务状态","执行状态"];
				var cellIndex = 0;
// 				$(view).find(".datagrid-header-row").find(".datagrid-cell").each(function(){
// 					if((row.subTaskCount && row.subTaskCount == 0) || cellIndex > 1){
// 						heads.push($(this).text());
// 					}
// 					cellIndex++;
// 				});
				//获取内容
				var bodys=[];
				cellIndex = 0;
				$(view).find(".datagrid-body").find(".datagrid-row[datagrid-row-index="+index+"]").find(".datagrid-cell").each(function(){
					if((row.subUserTaskCount && row.subUserTaskCount == 0) || cellIndex > 2){
						bodys.push($(this).html());
					}
					cellIndex++;
				});
				var module_submit_container=$('<div id="module_submit_container"></div>'); 
				var form=$('<form id="module_submit_form" class="easyui-form">');
				var easyui_table=$('<table class="easyui-table" id="module_submit_table"></table>');
				easyui_table.appendTo(form);
				form.appendTo(module_submit_container);
				for(var i in heads){
					var tr=$('<tr><td class="td1">'+heads[i]+':</td><td class="td2">'+bodys[i]+'</td></tr>');
					easyui_table.append(tr);
				}
				var title = $("title").html() + " - 明细";
				$("#button-save").unbind("click").click(cancel);
				$(module_dialog).dialog({title : title,content:$(module_submit_container).prop("outerHTML"),href:''});
				$(module_dialog).dialog("open");
			}
		}
	}
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
		onDblClickRow:lookSpecific,
		loadMsg : "处理中，请稍后..."
	};
	$(function() {
		$(module_datagrid).datagrid($.extend({}, lookDataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
		$(module_datagrid).datagrid('hideColumn', 'userId');
		$(module_datagrid).datagrid('hideColumn', 'subUserTaskCount');
	});
	
	function getSubUserTaskList(index,row){
	    $('#subusertask-'+index).datagrid({
		  	url:host+'subUserTaskList?userId='+row.userId+'&taskId='+row.taskId,
		  	fitColumns:true, 
            height:'auto',
            loadFilter : loadFilterForDataGridNoPage,
	 		columns:[[
	              {width : 100,title : '任务名称',field : 'map.taskName',sortable : true}, 
	              {width : 80,title : '已完成次数',field : 'finishCount',sortable : true},
	              {width : 80,title : '剩余奖励次数',field : 'remainderAwardCount',sortable : true},
	              {width : 150,title : '任务详情',field : 'remark',sortable : true},
	              {width : 80,title : '奖励红豆',field : 'rbcAward',sortable : true}, 
	              {width : 80,title : '奖励积分',field : 'scoreAward',sortable : true},
	              {width : 100,title : '创建时间',field : 'createTime',sortable : true,formatter:formatterTime},
	              {width : 100,title : '完成时间',field : 'finishTime',sortable : true,formatter:formatterTime},  
	              {width : 80,title : '任务状态',field : 'taskStatus',sortable : true,formatter:function(value){return getDatagridDict('rf_task.task_status',value);}},
	              {width : 80,title : '执行状态',field : 'enableStatus',sortable : true,formatter: formatterStop}
            ]],
			onResize:function(){
				$("#module_datagrid").datagrid('fixDetailRowHeight',index);
			},
			onLoadSuccess:function(){
				setTimeout(function(){
					$("#module_datagrid").datagrid('fixDetailRowHeight',index);
				},0);
			}
		});
		$("#module_datagrid").datagrid('fixDetailRowHeight',index);
	}
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container">
		<div id="module_search">
			<form id="module_search_form" class="easyui-form" method="post">
				<div class="module_search_input"> 
				     会员ID：<input type="text" name="externalUserId" class="easyui-numberbox input_width_short" /> 
				</div>
				<div class="module_search_input"> 
				      用户手机：<input type="text" name="userMobilePhone" class="easyui-numberbox input_width_default" /> 
				</div>
				<div class="module_search_input">
				      任务名称：<input type="text" name="taskName" class="easyui-textbox input_width_default" /> 
				</div>
				<div class="module_search_input">
				      邀请码：<input type="text" name="inviteCode" class="easyui-textbox input_width_default" /> 
				</div>
				<div class="module_search_input">
				   任务类型：<select class="easyui-combobox input_width_short"  editable="false" name="taskType" data-options="required:false">
							<option value="">[全部]</option>
						   <fns:getOptions category="task_system.category"  ></fns:getOptions>
					 	</select> 
				</div>
				<div class="module_search_input">
				  任务状态：<select class="easyui-combobox input_width_short"  editable="false" name="taskStatus" data-options="required:false">
							<option value="">[全部]</option>
						   <fns:getOptions category="rf_task.task_status"  ></fns:getOptions>
					 	</select> 
				</div>
				 <div class="module_search_input">
					创建时间:
						<input type="text" class="easyui-datebox input_width_default" editable="false"  name="beginTimeStr" />
						至
						<input type="text" class="easyui-datebox input_width_default" editable="false"  name="endTimeStr"/>
				 </div>  
				<div class="module_search_button">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
				</div>
			</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>

	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
	<div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a>
		<a href="javascript:void(0)" id="button-cancel" class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
</body>
</html>

