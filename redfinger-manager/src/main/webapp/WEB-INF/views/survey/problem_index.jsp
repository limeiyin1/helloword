<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>问卷问题管理</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="problemId";
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
			{width:100,title:'problemId',field:pk,checkbox:true},
			{width:100,title:'问题内容',field:'problemContent',sortable:true},
			{width:100,title:'问题类型',field:'problemType',sortable:true,formatter:function(val, row) {if(val=='-1'){
				return "问-答";}else if(val=='1'){return "单-选";}else if(val=='2'){return "多-选";}}},
			{width:100,title:'是否必答',field:'isMust',sortable:true,formatter:function(val, row) {if(val=='-1'){
				return "选答";}else if(val=='1'){return "* 必答";}}},
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
		problemForm(title, href);
	};
	 function problemForm(title, href) {
		$(module_dialog).dialog({title : title,href: href,width:580});
		$(module_dialog).dialog("open");
	}
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
				<div class="module_search_input">问题题目：
					<input type="text" name="problemContent" class="easyui-textbox input_width_default"/>
				</div>
				<div class="module_search_input">
				是否必答：<select class="easyui-combobox input_width_default" editable="false" name="isMust">
					<option value="">[全部]</option>
					<option value="1">必答题</option>
					<option value="-1">选答题</option>
				</select>
				</div>
				<div class="module_search_input">
				问题类型：<select class="easyui-combobox input_width_default" editable="false" name="problemType">
					<option value="">[全部]</option>
					<option value="1">单选题</option>
					<option value="2">多选题</option>
					<option value="-1">问答题</option>
				</select>
				</div>
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



