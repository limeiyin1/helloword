<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>虚拟管理任务</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
var pk="vmTaskId";
var module_datagrid = "#module_datagrid";
var module_dialog = "#module_dialog";
var module_search_form = "#module_search_form"
var module_submit_form = "#module_submit_form";
var callback = defaultCallback ;
var dataGridParamObj = {
	url : host + "list",
	idField : 'vmTaskId',
	onCheck : function(row) {

	},
	columns : [ [   {width : 100,title : 'id',field : 'vmTaskId',checkbox : true}, 
	                {width : 100,title : '任务来源',field : 'taskSource',sortable:true},
					{width : 100,title : '物理设备编号',field : 'map.deviceCode'},
					{width : 100,title : '设备编号',field : 'map.padCode'},
					{width : 100,title : '节点', field : 'map.manageControlName'} ,
					{width : 100,title : '任务类型',field : 'taskType',sortable:true,formatter:function(value){return getDatagridDict('rf_vm_task.task_type',value);}},
					{width : 100,title : '任务状态',field : 'taskStatus',sortable:true,formatter:function(value){return getDatagridDict('rf_vm_task.task_status',value);}},
					{width : 100,title : '任务结果状态',field : 'taskResultStatus',sortable:true,formatter:function(value){return getDatagridDict('rf_vm_task.task_result_status',value);}},
					{width : 100,title : '任务结果信息',field : 'taskResultInfo',sortable:true},
					{width : 100,title : '重试次数',field : 'retryTimes',sortable:true},
					{width : 100,title : '间隔时间(秒)',field : 'retryInterval',sortable:true}, 
					{width : 100,title : '创建人',field : 'creater',sortable:true},
					{width : 100,title : '创建时间',field : 'createTime',sortable:true,formatter :formatterTime},
					{width : 100,title : '修改人',field : 'modifier',sortable:true},
					{width : 100,title : '修改时间',field : 'modifyTime',sortable:true,formatter :formatterTime}
	] ]
};
var dialogParamObj = {

};
$(function() {
	$(module_datagrid).datagrid(
			$.extend({}, dataGridParam, dataGridParamObj));
	$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
});


//启用
var vmTask = function(callback) {
	var ids = getGridIds();
	if (!ids) {
		return false;
	}
	ajaxPost("vmTask", {
		ids : ids
	}, callback);
}

	
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container">
		<div id="module_search">
			<form id="module_search_form" class="easyui-form" method="post">
				<div class="module_search_input">
					设备编号：<input type="text" name="padCode"
						class="easyui-textbox input_width_default" />
				</div>
				<div class="module_search_button">
					<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
				</div>
			</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
		<!-- 	<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-add-rf" plain="true" onclick="add()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a> -->
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-start-rf" plain="true" onclick="vmTask(callback)">执行任务</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>

	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
	<div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save"class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a> 
		<a href="javascript:void(0)" id="button-cancel"class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
</body>
</html>



