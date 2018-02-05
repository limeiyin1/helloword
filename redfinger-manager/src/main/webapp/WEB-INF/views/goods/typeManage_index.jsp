<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>产品类型</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk = "typeId";
	var module_datagrid = "#module_datagrid";
	var module_dialog = "#module_dialog";
	var module_search_form = "#module_search_form"
	var module_submit_form = "#module_submit_form";
	var callback = defaultCallback;

	var dataGridParamObj = {
		url : host + "list",
		idField : 'typeId',
		onCheck : function(row) {

		},
		columns : [[ 
		              {width : 100,title : 'id',field : 'typeId',checkbox : true}, 
		              {width : 100,title : '类型名称',field : 'typeName',sortable : true}, 
		              {width : 100,title : '按钮',field : 'btnTitle',sortable : true}, 
		              {width : 60,title : '排序',field : 'reorder',sortable : true}, 
		              {width : 100,title : '创建人',field : 'creater',sortable : true}, 
					  {width : 150,title : '创建时间',field : 'createTime',sortable : true,formatter : formatterTime},
		              {width : 100,title : '启用状态',field : 'enableStatus',sortable : true,formatter : formatterStop} 
		          ]]
		};
	var dialogParamObj = {};
	$(function() {
		$(module_datagrid).datagrid($.extend({}, dataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
	});

	var editType = function() {
		var id;
		var row = $(module_datagrid).datagrid("getSelections");
		
		if (row == '') {
			$.messager.alert('提示', '请选择记录！', "info");
			return ;
		} else if (row.length > 1) {
			$.messager.alert('提示', '请选择其中一条记录！', "info");
			return;
		} else {
			id = row[0][pk];
		}
		
		var title = $("title").html() + " - 编辑";
		var href = host + 'form?' + pk + '=' + id;
		$("#button-save").unbind("click").click(update);
		openDialogForm(title, href);
	}
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container">
		<div id="module_search">
			<form id="module_search_form" class="easyui-form" method="post">
				<div class="module_search_input">
					商品编码：<input type="text" name="typeName"
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
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="add()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="editType()">编辑</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a>
<!-- 			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-link-rf" plain="true" onclick="activity(callback)">活动赠送</a> -->
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
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



