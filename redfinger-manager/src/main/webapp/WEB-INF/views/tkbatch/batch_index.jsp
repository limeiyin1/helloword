<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>批处理任务</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="batchId";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form"; 
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField :pk,
		singleSelect:false,
		onSelect: function(rowIndex,rowData){
			linkButtonChange();
		},
		onUnselect : function(rowIndex,rowData){
			linkButtonChange();
		},
		columns:[[
			{width:100,title:'batchId',field:pk,checkbox:true},
			{width:90,title:'批次标题',field:'batchTitle',sortable:true},
			{width:60,title:'操作类型',field:'operateType',sortable:true,formatter:function(value){return getDatagridDict('tk_batch_task.operate_type',value);}},  
			{width:50,title:'操作状态',field:'operateStatus',sortable:true,formatter:function(value){return getDatagridDict('tk_batch_task.operate_status',value);}},
			{width:80,title:'开始时间',field:'startTime',sortable:true,formatter:formatterTime},
			{width:80,title:'结束时间',field:'endTime',sortable:true,formatter:formatterTime}, 
            {width:40,title:'执行总数',field:'totalNumber',sortable:true},  
            {width:40,title:'成功数量',field:'successNumber',sortable:true}, 
            {width:40,title:'失败数量',field:'failNumber',sortable:true},
            {width:30,title:'状态',field : 'enableStatus',sortable:true,formatter:formatterStop},
            {width:80,title:'创建时间',field : 'createTime',sortable:true,formatter:formatterTime},
            {width:40,title:'备注',field:'remark',sortable:true},
            {width:50,title:'执行记录下载',field:'exportUrl',formatter:function(value){if(value && value!=''){return '<a href="'+value+'" target="_blank">下载</a>'}else{return "暂未生成文件"}}}
			
		]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
		$("#look").dialog($.extend({}, dialogParam, dialogParamObj));
	});
	
	//双击callback
	var dblClickCallback=function(index,row){
		var title = $("title").html() + " - 查看";
		var href = host + 'look?'+pk+'=' + row[pk];
		$("#look").dialog({title : title,href: href,width:599});
		$("#look").dialog("open");
	};
	
	function linkButtonChange(){
		var flag = true;
		$.each($(module_datagrid).datagrid('getSelections'),function(index,row){
			if(row.operateStatus != '1'){
				flag = false;
				return false;
			}
		});
		if(flag){
			$("#startBatchTask").linkbutton('enable');
			$("#editBatchTask").linkbutton('enable');
		}else{
			$("#startBatchTask").linkbutton('disable');
			$("#editBatchTask").linkbutton('disable');
		}
	}
	
	//启用批次任务
	function startBatchTask(callback) {
		var ids = getGridIds();
		if (!ids) {
			return false;
		}
		$.messager.confirm('确认？', '确认启用选择的任务批次?', function(confirm) {
			if (confirm) {
				ajaxPost("startBatchTask", {
					ids : ids
				}, callback);
			}
		});
	}
	
	var addBatch = function() {
		var title = $("title").html() + " - 新增";
		var href = host + 'form';
		$("#button-save").unbind("click").click(save);
		$("#module_dialog").dialog({title : title,href: href,width: 600,height: 480,top:0,left:200});
		$("#module_dialog").dialog("open");
	}
	
	var editBatch = function() {
		var id = getGridId();
		if (!id) {
			return false;
		}
		var title = $("title").html() + " - 编辑";
		var href = host + 'form?' + pk + '=' + id;
		
		$("#button-save").unbind("click").click(save);
		$("#module_dialog").dialog({title : title,href: href,width: 600,height: 480,top:0,left:200});
		$("#module_dialog").dialog("open");
	}
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">
			    批处理任务：<input type="text" name="batchTitle" class="easyui-textbox input_width_default" /> 
			</div>
			<div class="module_search_input">
			   操作类型：
			   	<select class="easyui-combobox input_width_short" editable="false" name="operateType" data-options="required:false" style="width:120px;">
					<option value="">[全部]</option>
					<fns:getOptions category="tk_batch_task.operate_type"></fns:getOptions>
			 	</select> 
			</div>
			<div class="module_search_input">
			   操作状态：<select class="easyui-combobox input_width_short" editable="false" name="operateStatus" data-options="required:false" style="width:120px;">
						<option value="">[全部]</option>
					   <fns:getOptions category="tk_batch_task.operate_status"></fns:getOptions>
				 	</select> 
			</div>
			
			<!-- 启用状态 -->
				<div class="module_search_input">
					启用状态：
					<select class="easyui-combobox input_width_short" editable="false" name="enableStatus" data-options="required:false">
						<option value="">[全部]</option>
						<fns:getOptions category="global.enable_status"/>
					</select>
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
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="addBatch()">新增</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="editBatch()" id="editBatchTask">编辑</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" id="startBatchTask" onclick="startBatchTask(callback)">启用任务批次</a> 
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>
	
	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
    <div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a>
		<a href="javascript:void(0)" id="button-cancel" class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
	<!-- 查看框 -->
	<div id="look" buttons="#look_button"></div>
	<div id="look_button">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-no" onclick=" $('#look').dialog('close');">关闭</a>
	</div>
</body>
</html>
