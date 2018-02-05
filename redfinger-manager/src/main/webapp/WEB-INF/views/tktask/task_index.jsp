<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>红手指任务</title>
<meta name="decorator" content="moduleIndex" />
<style type="text/css">
.tableBorder {
	position: relative;
	border: 1px solid #95B8E7;
	background: white;
	vertical-align: middle;
	display: inline-block;
	margin: 0;
	padding: 0;
	-moz-border-radius: 5px 5px 5px 5px;
	-webkit-border-radius: 5px 5px 5px 5px;
	border-radius: 5px 5px 5px 5px;
	font-size: 13px;
}
</style>
<script type="text/javascript"
	src="${ctxStatic}/jquery-easyui-1.4.1/datagrid-tktaskview.js"></script>
<script type="text/javascript">
	var pk = "taskId";
	var module_datagrid = "#module_datagrid";
	var module_dialog = "#module_dialog";
	var module_search_form = "#module_search_form"
	var module_submit_form = "#module_submit_form";
	var callback = defaultCallback;

	var dataGridParamObj = {
		url : host + "list",
		idField : 'taskId',
		singleSelect: false,
		checkOnSelect: true, 
		selectOnCheck: true,
		onCheck : function(row) {

		},
		columns : [[ 
              {width : 100,title : 'id',field : 'taskId',checkbox : true}, 
              {width : 100,title : 'subTaskCount',field : 'subTaskCount'}, 
              {width : 100,title : '任务名',field : 'taskName',sortable : true}, 
              {width : 100,title : '任务模块',field : 'taskModule',sortable : true,formatter:function(value){return getDatagridDict('tk_task.task_module',value);}}, 
              {width : 100,title : '任务类型',field : 'taskType',sortable : true,formatter:function(value){return getDatagridDict('task_system.category',value);}}, 
              {width : 100,title : '任务编码',field : 'taskCode',sortable : true}, 
              {width : 300,title : '任务描述',field : 'remark',sortable : true}, 
              {width : 100,title : '奖励红豆',field : 'map.rbcAward',sortable : false}, 
              {width : 100,title : '奖励积分',field : 'map.scoreAward',sortable : false},
              {width : 100,title : '激活码类型',field : 'map.activationCodeTypeName',sortable : false},
              {width : 100,title : '优惠券类型',field : 'map.couponTypeName',sortable : false},
              {width : 100,title : '游戏',field : 'gameName',sortable : true},
//               {width : 100,title : '问卷调查',field : 'map.surveyName',sortable : true},
              {width : 100,title : '阀值(0为不限制完成次数)',field : 'taskThreshold',sortable : true},
              {width : 100,title : '已完成次数',field : 'finishCount',sortable : true},
              {width : 100,title : '已奖励次数',field : 'awardCount',sortable : true},
              {width : 100,title : '标签',field : 'map.labelName',sortable : false},
              {width : 100,title : '创建人',field : 'creater',sortable : true},
              {width : 50,title : '排序',field : 'reorder',sortable : true},
              {width : 100,title : '执行状态',field : 'enableStatus',sortable : true,formatter: formatterStop},
              {width : 100,title : '起始时间',field : 'taskStartTime',sortable : true,formatter:formatterTime},
              {width : 100,title : '终止时间',field : 'taskEndTime',sortable : true,formatter:formatterTime}
          	]],
          	view: subtaskview,
         	detailFormatter:function(index,row){
			  	return '<div style="padding:2px;"><table id="subtask-' + index + '"></table></div>';
		  	},
		  	onExpandRow: function(index,row){
			  	getSubTaskList(index,row);
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
				var heads=["任务名","任务模块","任务类型","任务编码","任务描述","奖励红豆","奖励积分","激活码类型","优惠券类型","游戏","问卷调查","阀值(0为不限制完成次数)","已完成次数",
				"已奖励次数","创建人","排序","执行状态","起始时间","终止时间"];
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
					if((row.subTaskCount && row.subTaskCount == 0) || cellIndex > 1){
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
		$(module_datagrid).datagrid('hideColumn', 'subTaskCount');
	});
	
	function getSubTaskList(index,row){
	    $('#subtask-'+index).datagrid({
		  	url:host+'subTaskList?parentTaskId='+row.taskId,
		  	fitColumns:true,  
            height:'auto',
            loadFilter : loadFilterForDataGridNoPage,
	 		columns:[[
	              {width : 100,title : '任务名',field : 'taskName',sortable : true}, 
	              {width : 300,title : '任务描述',field : 'remark',sortable : false}, 
	              {width : 100,title : '奖励红豆',field : 'map.rbcAward',sortable : false}, 
	              {width : 100,title : '奖励积分',field : 'map.scoreAward',sortable : false},
	              {width : 100,title : '激活码类型',field : 'map.activationCodeTypeName',sortable : false},
	              {width : 100,title : '优惠券类型',field : 'map.couponTypeName',sortable : false},
	              {width : 100,title : '已完成次数',field : 'finishCount',sortable : true},
	              {width : 100,title : '已奖励次数',field : 'awardCount',sortable : true},
	              {width : 50,title : '排序',field : 'reorder',sortable : true}
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
	
	function addTaskTab(url,title){
		var topJQ = top.jQuery;
		var tb=topJQ('#easyui-tabs').tabs('getTab',title);
		var height=topJQ("#easyui-tabs").height()-30;
		if(tb!=null){
			var index = topJQ('#easyui-tabs').tabs('getTabIndex',tb);//获取当前选中tabs的index
		    topJQ('#easyui-tabs').tabs('close',index);//关闭对应index的tabs  
		}
		
		topJQ('#easyui-tabs').tabs('add',{
			title:title,
			iconCls:'icon-menu-rf',
			closable:true,
			content:'<iframe id="'+url.replaceAll("/","_")+'" name="page" width="100%" height="'+height+'" frameborder="0" src="'+url+'"></iframe>'
		});
		
// 		topJQ('#easyui-tabs').tabs({
// 			onClose:function(title,index){
// 				$("#searchBtn").trigger("");
// 			}
// 		});
	}
	
	var addTask = function() {
		var title = $("title").html() + " - 新增";
		var href = host + 'form';
		
		addTaskTab(href, title);
	}
	
	var editTask = function() {
		var id = getGridId();
		if (!id) {
			return false;
		}
		var title = $("title").html() + " - 编辑";
		var href = host + 'form?' + pk + '=' + id;
		
		addTaskTab(href, title);
	}
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container">
		<div id="module_search">
			<form id="module_search_form" class="easyui-form" method="post">
				<div class="module_search_input">
					任务名称：<input type="text" name="taskName" class="easyui-textbox input_width_default" />
				</div>
				<div class="module_search_input">
					任务类型：
					<select class="easyui-combobox input_width_short" editable="false" name="taskType" data-options="required:false">
						<option value="">[全部]</option>
						<fns:getOptions category="task_system.category"></fns:getOptions>
					</select>
				</div>
				<div class="module_search_input">
					任务状态：
					<select class="easyui-combobox input_width_short" editable="false" name="enableStatus" data-options="required:false">
						<option value="">[全部]</option>
						<fns:getOptions category="global.enable_status"></fns:getOptions>
					</select>
				</div>
				
				<div class="module_search_input">
					标签：
					<select class="easyui-combobox input_width_short" editable="false" id="labelId" name="labelId" style="width:120px">
						<option value="">[无]</option>
					    <c:forEach var="label" items="${labelList }">
					    	<option value="${label.labelId}" ${label.labelId==task.labelId?"selected":""}>${label.labelName}</option>
					    </c:forEach>
				 	</select>
				</div>
				<div class="module_search_input">
					任务开始时间: <input type="text"	class="easyui-datebox input_width_default" editable="false" name="beginTimeStr" />
					任务结束时间: <input type="text" class="easyui-datebox input_width_default" editable="false"	name="endTimeStr" />
				</div>
				<div class="module_search_button">
					<a href="javascript:void(0)" id="searchBtn" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
				</div>
			</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="addTask()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="editTask()">编辑</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
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
