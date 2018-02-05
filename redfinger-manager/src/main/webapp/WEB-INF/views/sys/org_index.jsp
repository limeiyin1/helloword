<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>部门组织</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="orgCode";
	var parentKey="parentOrgCode";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var treeGridParamObj={
		url:host+"list",
		idField : 'orgCode',
		treeField : 'orgName',
		parentField : "parentOrgCode",
		onCheck: function(row){
			
		},
		columns:[[
			{width:100,title:'id',field:'orgCode',checkbox:true},
			{width:100,title:'名称',field:'orgName',sortable:true},
			{width:100,title:'编码',field:'checkboxValue',formatter:function(value,row){return row.orgCode;}},
			{width:100,title:'描述',field:'remark'},
			{width:100,title:'排序',field:'reorder',sortable:true},
			{width:100,title:'状态',field:'enableStatus',formatter:formatterStop}
		]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).treegrid($.extend({},treeGridParam,treeGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
		
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-addtop-rf" plain="true" onclick="add()">新增顶级</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="addTree()">新增下级</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a>
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
		<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:void(0)" id="button-cancel" class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
</body>
</html>



