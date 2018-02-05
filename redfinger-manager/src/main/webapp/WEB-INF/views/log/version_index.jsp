<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>安装日志</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk = "id";
	var module_datagrid = "#module_datagrid";
	var module_dialog = "#module_dialog";
	var module_search_form = "#module_search_form";
	var module_submit_form = "#module_submit_form";
	var callback = defaultCallback;
	var dataGridParamObj = {
		url : host + "list",
		idField : 'id',
		onCheck : function(row) {

		},
		columns : [[
		{width : 100,title : 'id',field : pk,checkbox : true}, 
		{width : 100,title : 'IMEI',field : 'imei'},
		{width : 100,title : 'MAC',field : 'mac'}, 
		{width : 100,title : '系统名称',field : 'systemName'}, 
		{width : 100,title : '系统版本',field : 'systemVersion'}, 
		{width : 100,title : '客户端来源',field : 'clientSource',sortable:true}, 
		{width : 100,title : '客户端版本',field : 'clientVersion',sortable:true}, 
		{width : 100,title : '客户端类型',field : 'clientType'}, 
		{width : 100,title : '客户端ip',field : 'operateIp',sortable:true}, 
		{width : 100,title : 'cpu信息',field : 'cpuMessage',sortable:true}, 
		{width : 100,title : '状态',field : 'status',formatter : formatterStop,sortable:true}, 
		{width : 100,title : '创建时间',field : 'createTime',formatter : formatterTime,sortable:true},
		{width : 100,title : '启用状态',field : 'enableStatus',sortable : true,formatter : formatterStop,sortable:true}
		]]
	};
	var dialogParamObj = {

	};
	$(function() {
		$(module_datagrid).datagrid(
				$.extend({}, dataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
	});
	
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container">
		<div id="module_search">
			<form id="module_search_form" class="easyui-form" method="post">

				<div class="module_search_input">
					客户端类型：<select class="easyui-combobox input_width_default" editable="false" name="clientType">
								<option value="">[全部]</option>
								<option value="win">win</option>
								<option value="android">android</option>
							</select> 
					
					操作时间： <input type="text" class="easyui-datebox input_width_default"
						editable="false" id="begin" name="begin" data-options="" /> 至 <input
						type="text" class="easyui-datebox input_width_default"
						editable="false" id="end" name="end" data-options="" />
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
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
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



