<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>应用市场扫描</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="id";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj = {
			url : host + "list",
			idField : 'id',
			onCheck : function(row) {

			},
			columns:[[
						{width:100,title:'id',field:pk,checkbox:true},
						{width:100,title:'名称',field:'name',sortable:true},
						{width:100,title:'apk地址',field:'apkUrl',sortable:true},
						{width:100,title:'应用市场名称',field:'marketName',sortable:true},
						{width:100,title:'应用市场页面地址',field:'marketUrl',sortable:true},
						{width:100,title:'是否需要更新',field:'result',sortable:true,formatter : function(value) {return getDatagridDict('tools_market_update.result', value);}},
						{width:100,title:'信息',field:'remark'},
						{width:100,title:'apk下载地址',field:'apkDownloadUrl',sortable:true},
						{width:100,title:'apk大小',field:'apkSize',sortable:true},
						{width:100,title:'应用市场apk大小',field:'marketSize',sortable:true},
						{width:100,title:'描述',field:'remark2'},
						{width:100,title:'扫描时间',field:'scanTime',sortable:true,formatter:formatterTime},
						{width:100,title:'状态',field:'enableStatus',sortable:true,formatter:formatterStop}
					]]
		};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	
	var scan = function() {
		var ids = getGridIds();
		if (!ids) {
			return false;
		}
		ajaxPost("scan", {
			ids : ids
		}, callback);
	}
	
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">
				是否需要更新：
		        <select class="easyui-combobox input_width_default" editable="false" name="result">
					<option value="">[全部]</option>
					<fns:getOptions category="tools_market_update.result"/>
				</select>
			</div>
			<div class="module_search_input">
				应用市场：
		        <select class="easyui-combobox input_width_default" editable="false" name="marketName">
					<option value="">[全部]</option>
					<fns:getOptions category="tools_market_update.market_name"/>
				</select>
			</div>
			<div class="module_search_input">
				状态：
		        <select class="easyui-combobox input_width_default" editable="false" name="enableStatus">
					<option value="">[全部]</option>
					<fns:getOptions category="global.enable_status"/>
				</select>
			</div>
			<div class="module_search_input">名称：<input type="text" name="name" class="easyui-textbox input_width_default"/></div>
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
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-star-rf" plain="true" onclick="scan()">扫描</a>
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



