<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>分享配置</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk = "shareId";
	var module_datagrid = "#module_datagrid";
	var module_dialog = "#module_dialog";
	var module_search_form = "#module_search_form";
	var module_submit_form = "#module_submit_form";
	var callback = defaultCallback;
	var dataGridParamObj = {
		url : host + "list",
		idField : pk,
		onCheck : function(row) {
		},
		columns : [ [ {
			width : 100,
			title : 'id',
			field : pk,
			checkbox : true
		}, {
			width : 100,
			title : '客户端',
			field : 'client',sortable : true,formatter:function(value){return getDatagridDict('global.client_category',value);},
			sortable : true
		}, {
			width : 100,
			title : '分享类型',
			field : 'shareType',sortable : true,formatter:function(value){return getDatagridDict('rf_share.type',value);},
			sortable : true
		}, {
			width : 100,
			title : '分享标题',
			field : 'shareTitle',
			sortable : true
		}, {
			width : 100,
			title : '分享内容',
			field : 'shareText',
			sortable : true
		}, {
			width : 100,
			title : '分享链接',
			field : 'shareLinkUrl',
			sortable : true
		}, {
			width : 250,
			title : '图片地址',
			field : 'shareImageUrl',
			sortable : true
		}, {
			width : 50,
			title : '状态',
			field : 'status',
			sortable : true,
			formatter : formatterStop
		}, {
			width : 50,
			title : '启用状态',
			field : 'enableStatus',
			sortable : true,
			formatter : formatterStop
		} ] ]
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
					客户端： <select class="easyui-combobox input_width_short"
						editable="false" name="client" data-options="required:false"
						style="width: 150px">
						<option value="">[全部]</option>
						<fns:getOptions category="global.client_category"></fns:getOptions>
					</select>
				</div>

				<div class="module_search_input">
					分享类型： <select class="easyui-combobox input_width_short"
						editable="false" name="shareType" data-options="required:false"
						style="width: 150px">
						<option value="">[全部]</option>
						<fns:getOptions category="rf_share.type"></fns:getOptions>
					</select>
				</div>

				<div class="module_search_input">
					分享标题：<input type="text" name="shareTitle"
						class="easyui-textbox input_width_default" />
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
				iconCls="icon-add-rf" plain="true" onclick="add()">新增</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>
	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
	<div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save"
			class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a> <a
			href="javascript:void(0)" id="button-cancel"
			class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
</body>
</html>