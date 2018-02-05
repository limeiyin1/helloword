<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>字段匹配</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="fieldCode";
	var module_datagrid = "#module_datagrid";
	var module_dialog = "#module_dialog";
	var module_search_form = "#module_search_form";
	var module_submit_form = "#module_submit_form";
	var callback = defaultCallback ;
	var dataGridParamObj = {
		url : host + "list",
		idField : pk,
		onCheck : function(row) {
			
		},
		columns : [[ {width : 100,title : 'id', field : pk,checkbox : true},
			{width : 100,title : '字段', field : 'fieldName'},
			{width : 100,title : '字段名', field : 'lable'},
			{width : 100,title : '隶属表', field : 'tableId',formatter:function(value){return getDatagridDict('gather.from_table',value);}},
			{width : 100,title : '数据类型', field : 'dataType',formatter:function(value){return getDatagridDict('gather.data_type',value);}},
			{width : 100,title : '状态', field : 'enableStatus',sortable : true,formatter : formatterStop},
			{width : 100,title : '创建时间', field : 'createTime',sortable : true,formatter : formatterTime},
			{width : 100,title : '创建人', field : 'creater',sortable : true},
			{width : 100,title : '修改时间', field : 'modifyTime',sortable : true,formatter : formatterTime},
			{width : 100,title : '修改人', field : 'modifier',sortable : true},
			{width : 100,title : '排序', field : 'reorder',sortable : true},
			{width : 100,title : '备注', field : 'remark',sortable : true}
		]]
	};
	var dialogParamObj = {
	
	};
	$(function() {
		$(module_datagrid).datagrid($.extend({}, dataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
	});
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container">
		<!-- <div id="module_search">
			<form id="module_search_form" class="easyui-form" method="post">
				<div class="module_search_input">
					用户控制节点：
					<input type="text" name="userControlName" class="easyui-textbox input_width_default" />
					设备控制节点：
					<input type="text" name="padControlName" class="easyui-textbox input_width_default" />  
				</div>
				<div class="module_search_button">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
				</div>
			</form>
		</div> -->
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="add()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a> 
			<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a> -->
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



