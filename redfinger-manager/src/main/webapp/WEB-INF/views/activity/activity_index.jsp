<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>活动管理</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="activityId";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField :'activityId',
		singleSelect: false,
		checkOnSelect: true, 
		selectOnCheck: true,
		onCheck : function(row) {
			
		},
		columns:[[
			{width:100,title:'id',field:"activityId",checkbox:true},
			{width:200,title:'活动名称',field:'activityName',sortable:true},
			{width:100,title:'活动',field:'activityCode',sortable:true,formatter:function(value){return getDatagridDict('rf_activity.activity',value);}},
			{width:200,title:'活动数量',field:'activityNum',sortable:true},
			{width:200,title:'完成数量',field:'doneNum',sortable:true},
			{width:200,title:'标签名称',field:'map.labelName',sortable:false},
			{width:200,title:'备注',field:'remark',sortable:true},
			{width:200,title:'排序',field:'reorder',sortable:true},
			{width:200,title:'状态',field:'enableStatus',sortable:true,formatter:function(value){return getDatagridDict('global.enable_status',value);}},
			{width:200,title:'开始时间',field:'activityStartTime',sortable:true,formatter:formatterTime},
			{width:200,title:'结束时间',field:'activityEndTime',sortable:true,formatter:formatterTime},
		]]
	};
	var dialogParamObj={
		
	};
	$(function() {
		$(module_datagrid).datagrid($.extend({}, dataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
	});
	
	var addActivity = function() {
		action = "save";
		var title = $("title").html() + " - 编辑";
		var href = host + 'form';
		$("#button-save").unbind("click").click(saveActivity);
		openDialogForm(title, href);
	}
	
	var action = "save";
	var saveActivity = function() {
		if ($(module_submit_form).form("validate")) {
			$.messager.progress();
			$(module_submit_form).form({
				url : host + action
			});
			$(module_submit_form).form("submit");
		}
	}

	var editActivity = function() {
		var id = getGridId();
		if (!id) {
			return false;
		}
		action = "update";
		var title = $("title").html() + " - 编辑";
		var href = host + 'form?' + pk + '=' + id;
		$("#button-save").unbind("click").click(saveActivity);
		openDialogForm(title, href);
	}
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
		  <div class="module_search_input">
				活动名称：<input type="text" class="easyui-textbox input_width_default" name="activityName" data-options=""/>
		  </div>
		  <div class="module_search_input">
				活动：
				<select class="easyui-combobox input_width_short" name="activityCode" data-options="editable:false" style="width:100px">
					<option value="">[全部]</option>
				    <fns:getOptions category="rf_activity.activity"></fns:getOptions>
			 	</select>
		  </div>
		    <div class="module_search_input">
				状态：
		        <select class="easyui-combobox input_width_default" editable="false" name="enableStatus">
					<option value="">[全部]</option>
					<fns:getOptions category="global.enable_status"/>
				</select>
			</div>
			<div class="module_search_input">
				开始时间：
				<input type="text" class="easyui-datebox input_width_default" editable="false" id="begin" name="beginTimeStr" data-options=""/>
			</div>
			<div class="module_search_input">
				结束时间：
				<input type="text" class="easyui-datebox input_width_default" editable="false" id="end" name="endTimeStr" data-options=""/>
			</div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
	     	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="addActivity()">新增</a>
	     	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="editActivity()">编辑</a>
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
