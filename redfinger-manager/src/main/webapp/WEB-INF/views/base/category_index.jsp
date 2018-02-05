<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>故障类型</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="classId";
	var parentKey="classPid";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var treeGridParamObj={
		url:host+"list",
		idField : 'classId',
		treeField : 'className',
		parentField : "classPid",
		onCheck: function(row){
			
		},
		columns:[[
			{width:100,title:'id',field:'classId',checkbox:true},
			{width:100,title:'名称',field:'className'},
			{width:100,title:'类型',field:'classType',formatter:function(value){return getDatagridDict('rf_class.class_type',value)}},
			{width:100,title:'描述',field:'remark'},
			{width:100,title:'排序',field:'reorder'},
			{width:100,title:'状态',field:'enableStatus',formatter:formatterStop}
		]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).treegrid($.extend({},treeGridParam,treeGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	
	
        function  addType() {
        var id ="";
        var row = $(module_datagrid).datagrid("getSelections");
        if(row!=''){
         id=row[0][pk];
        }
		var title = $("title").html() + " - 新增";
		var href = host + 'form?' + parentKey + '=' + id;
		$("#button-save").unbind("click").click(save);
		openDialogForm(title, href);
	}
        

</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">

			<div class="module_search_input">模版名称：<input type="text" name="className" class="easyui-textbox input_width_default"/></div>
			
			<div class="module_search_input">模版名称:
			<select class="easyui-combobox"  editable="false" name="classType" data-options="required:false">  
					<c:forEach items="${classList}" var="one">
						<option value="${one.dictValue }">${one.dictName }</option>
					</c:forEach>
				</select>
			</div>
			
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search-rf',plain:false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reset-rf',plain:false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add-rf',plain:true" onclick="addType()">新增</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit-rf',plain:true" onclick="edit()">编辑</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-start-rf',plain:true" onclick="start(callback)">启用</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-stop-rf',plain:true" onclick="stop(callback)">禁用</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove-rf',plain:true" onclick="del(callback)">删除</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload-rf',plain:true" onclick="fresh()">刷新</a>
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



