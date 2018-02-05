<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>地市</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk = "id";
	var parentKey = "parentId";
	var module_datagrid = "#module_datagrid";
	var module_dialog = "#module_dialog";
	var module_search_form = "#module_search_form"
	var module_submit_form = "#module_submit_form";
	var callback = defaultCallback;
	var formatterCategory = function(value, row, index){
	
	}
	

	var treeGridParamObj = {
		url : host + "list",
		idField : 'id',
		treeField : 'name',
		parentField : "parentId",
		onLoadSuccess: function () {$('#module_datagrid').treegrid('collapseAll')},
		onCheck : function(row) {
			
		},
		columns : [ [ 
		{width:100,title:'id',field:'id',checkbox : true}, 
		{width:100,title:'名称',field:'name',sortable:true}, 
		{width:100,title:'排序',field:'reorder',sortable:true},
		{width:100,title:'描述',field:'remark',sortable:true},
		{width:100,title:'状态',field:'enableStatus',sortable:true,formatter:formatterStop}
		] ]
	};
	var dialogParamObj = {

	};
	$(function() {
		$(module_datagrid).treegrid($.extend({}, treeGridParam, treeGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
	});
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container">
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">名称：<input type="text" name="areaName" class="easyui-textbox input_width_default"/></div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-addtop-rf" plain="true" onclick="add()" id="add_button">新增顶级</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="addTree()" id="addTree_button">新增下级</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="edit()" id="edit_button">编辑</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start-rf" plain="true" onclick="start(callback)" id="start_button">启用</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop-rf" plain="true" onclick="stop(callback)" id="stop_button">禁用</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove-rf" plain="true" onclick="del(callback)" id="del_button">删除</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>

	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
	<div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save"
			class="easyui-linkbutton" iconCls="icon-ok">保存</a> 
		<a href="javascript:void(0)" id="button-cancel"
			class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
</body>
</html>


