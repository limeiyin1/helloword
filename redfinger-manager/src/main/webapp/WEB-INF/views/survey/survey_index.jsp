<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>问卷调查管理</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="surveyId";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField : pk,
		onCheck: function(row){
		},
		columns:[[
			{width:100,title:'surveyId',field:'surveyId',checkbox:true},
			{width:100,title:'问卷调查ID',field:'surId',formatter:function(value, row) {return row.surveyId;}},
			{width:100,title:'问卷调查名称',field:'surveyName',sortable:true},
			{width:100,title:'问卷调查简介',field:'surveyContent',sortable:true},
			{width:100,title:'创建人',field:'creater',sortable:true},
			{width:150,title:'创建时间',field:'createTime',sortable:true,formatter:formatterTime},
			{width:100,title:'修改人',field:'modifier',sortable:true},
			{width:150,title:'修改时间',field:'modifyTime',sortable:true,formatter:formatterTime},
			{width:100,title:'排序',field:'reorder',sortable:true},
			{width:100,title:'启用状态',field:'enableStatus',sortable:true,formatter:formatterStop},
			{width:100,title:'备注',field:'remark',sortable:true}
		]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	//双击callback
	var dblClickCallback=function(index,row){
		var title = $("title").html() + " - 编辑";
		var href = host + 'form?' + pk + '=' + row[pk];
		$("#button-save").unbind("click").click(update);
		openSurveyForm(title, href);
	};
	 function openSurveyForm(title, href) {
			$(module_dialog).dialog({title : title,href: href,width:800});
			$(module_dialog).dialog("open");
	}

</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">问卷名称：<input type="text" name="surveyName" class="easyui-textbox input_width_default"/></div>
			<div class="module_search_input">
				创建时间：
				<input type="text" class="easyui-datebox input_width_default" editable="false" id="begin" name="begin" data-options=""/>
				至
				<input type="text" class="easyui-datebox input_width_default" editable="false" id="end" name="end" data-options=""/>
			</div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="add()">新增</a>
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
		<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a>
		<a href="javascript:void(0)" id="button-cancel" class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
</body>
</html>



