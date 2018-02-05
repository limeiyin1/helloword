<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>礼包</title>
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
		columns : [ [ {
			width : 100,
			title : 'id',
			field : 'id',
			checkbox : true
		}, {
			width : 100,
			title : '礼包名称',
			field : 'name',
			sortable : true
		}, {
			width : 100,
			title : '礼包类型',
			field : 'category',
			sortable:true,                                     
			formatter:function(value){return getDatagridDict('shop_package.category',value);}
		}, {
			width : 100,
			title : '礼包原价(元)',
			field : 'originalPrice',
			sortable : true
		}, {
			width : 100,
			title : '礼包现价(元)',
			field : 'price',
			sortable : true
		}, {
			width : 100,
			title : '礼包icon',
			field : 'icon',
			sortable : true
		},
		{
			width : 100,
			title : '礼包详情',
			field : 'content',
			sortable : true
		}, {
			width : 100,
			title : '礼包使用说明',
			field : 'remark',
			sortable : true
		}, {
			width : 100,
			title : '开始时间',
			field : 'beginTime',
			sortable : true,
			formatter : formatterTime
		}, {
			width : 100,
			title : '结束时间',
			field : 'endTime',
			sortable : true,
			formatter : formatterTime
		}, {
			width : 100,
			title : '礼包总数',
			field : 'num',
			sortable : true
		}, {
			width : 100,
			title : '礼包库存',
			field : 'stock',
			sortable : true
		},{
			width : 100,
			title : '礼包限量数',
			field : 'parameter',
			sortable : true
		}, {
			width : 100,
			title : '状态',
			field : 'enableStatus',
			sortable : true,
			formatter : formatterStop
		}, {
			width : 100,
			title : '排序',
			field : 'reorder',
			sortable : true
		}, ] ]
	};
	var dialogParamObj = {};
	$(function() {
		$(module_datagrid).datagrid(
				$.extend({}, dataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
	});
	function codeGets() {
		var id = getGridId();
		if (!id)
			return false;
		var title = $("title").html() + " - 批量添加礼包码";
		;
		var href = host + 'formCode?id=' + id;
		$("#button-save").unbind("click").click(codeSave);
		openDialogForm(title, href);
	}

	function codeSave() {
		if ($('#module_submit_form').form("validate")) {
			$.messager.progress();
			$('#module_submit_form').form("submit");
		}
	}
	//双击callback
	var dblClickCallback=function(index,row){
		var width=700;
		var title = $("title").html() + " - 查看";
		var href = host + 'look?'+pk+'=' + row[pk];
		$("#button-save").unbind("click").click(update);
		openDialogFormWidth(title, href, width);
	};
	
	var openDialogFormWidth= function(title, href,width) {
		$(module_dialog).dialog({title : title,href: href,width: width});
		$(module_dialog).dialog("open");
	}
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container">
		<div id="module_search">
			<form id="module_search_form" class="easyui-form" method="post">
				<div class="module_search_input">
					礼包名称：<input type="text" name="name"
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
			<c:if
				test="${not empty sessionScope.permission.button_shoppackage_save}">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-add-rf" plain="true" onclick="add()">新增</a>
			</c:if>
			<c:if
				test="${not empty sessionScope.permission.button_shoppackage_update}">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a>
			</c:if>
			<c:if
				test="${not empty sessionScope.permission.button_shoppackage_start}">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
			</c:if>
			<c:if
				test="${not empty sessionScope.permission.button_shoppackage_stop}">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
			</c:if>
			<c:if
				test="${not empty sessionScope.permission.button_shoppackage_delete}">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a>
			</c:if>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
			<c:if
				test="${not empty sessionScope.permission.button_shoppackage_batch}">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-addBatch-rf" plain="true" onclick="codeGets()">批量添加礼包码</a>
			</c:if>
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



