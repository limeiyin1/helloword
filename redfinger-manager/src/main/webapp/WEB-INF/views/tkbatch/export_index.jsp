<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>导出下载</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="exportId";
	var module_datagrid = "#module_datagrid";
	var module_dialog = "#module_dialog";
	var module_search_form = "#module_search_form"
	var module_submit_form = "#module_submit_form";
	var callback = defaultCallback ;
	var dataGridParamObj = {
		url : host + "list",
		idField : pk,
		onCheck : function(row) {
			
		},
		columns : [[ {width : 10,title :'id', field:pk,checkbox : true},
			{width : 20,title : '导出任务名', field : 'taskName'},
			{width : 20,title : '导出来源',field : 'type',formatter :function(value){return getDatagridDict('tkbatch.export_type',value);}},
			{width : 30,title : '导出状态', field : 'exportStatus',formatter :function(value){return getDatagridDict('tkbatch.export_status',value);}},
			{width : 20,title : '导出数目', field : 'finishCount'},
			{width : 40,title : '创建时间', field : 'createTime',sortable : true,formatter : formatterTime},
			{width : 40,title : '结束时间', field : 'finishTime',sortable : true,formatter : formatterTime},
			{width : 20,title : '操作人', field : 'creater',sortable : true},
			{width : 20,title : '参数', field : 'parm'},
			{width:50,title:'执行记录下载',field:'url',formatter:function(value){if(value && value!=''){return '<a href="'+value+'" target="_blank">下载</a>'}else{return "暂未生成文件"}}}
		]]
	};
	var dialogParamObj = {
	
	};
	$(function() {
		$(module_datagrid).datagrid($.extend({}, dataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
	});
	
	//
	var del = function(callback) {
	var ids = getGridIds();
	if (!ids) {
		return false;
	}
	$.messager.confirm('确认？', '请谨慎操作，仅可删除本账户导出文件，确认删除该文件及记录?', function(confirm) {
		if (confirm) {
			ajaxPost("delete", {
				ids : ids
			}, callback);
		}
	});
}
	
	
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container">
		<div id="module_search">
			<form id="module_search_form" class="easyui-form" method="post">
				<div class="module_search_input">
					导出任务名：
					<input type="text" name="taskName" class="easyui-textbox input_width_default" />
					导出来源:
					<select class="easyui-combobox"  editable="false" name="type" data-options="required:true">
						<option value="">[全部]</option>
						<fns:getOptions category="tkbatch.export_type"/>
					</select> 
					导出状态:
					<select class="easyui-combobox"  editable="false" name="exportStatus" data-options="required:true">
						<option value="">[全部]</option>
						<fns:getOptions category="tkbatch.export_status"/>
					</select> 
					操作人：
					<input type="text" name="creater" class="easyui-textbox input_width_default" />
					操作时间:
					<input type="text" class="easyui-datebox input_width_default" editable="false" name="createTime2" />
				</div>
				<div class="module_search_button">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
				</div>
			</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<!-- <a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-add-rf" plain="true" onclick="add()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a> -->
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a>
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



