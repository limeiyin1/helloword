<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>图片管理</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	//普通设备是否限时
	var freeUse = function(value,row,index){
		if("1" == value){
			return "是";
		}
		if("0" == value){
			return "否"
		}
	}
	var pk = "infoId";
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
			title : '信息名称',
			field : 'infoName',
			sortable : true
		},{
			width : 150,
			title : '信息类型',
			field : 'map.typeName',
			sortable : true
		}, {
			width : 100,
			title : '信息图标',
			field : 'infoIcon',
			sortable : true
		}, {
			width : 100,
			title : '颜色',
			field : 'infoColor',
			sortable : true,
			formatter:function(value){return getDatagridDict('goods_type_info.info_color',value);}
		}, {
			width : 100,
			title : '排序',
			field : 'reorder',
			sortable : true
		}, {
			width : 100,
			title : '状态',
			field : 'status',
			sortable : true,
			formatter : formatterStop
		}, {
			width : 100,
			title : '启用状态',
			field : 'enableStatus',
			sortable : true,
			formatter : formatterStop
		}, {
			width : 100,
			title : '普通设备是否限时',
			field : 'freeUseLimit',
			sortable : true,
			formatter : freeUse
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
					产品类型：<select class="easyui-combobox input_width_short" editable="false" name="goodsTypeId" data-options="required:false">
						<option value="">[全部]</option>
						<c:forEach var="one" items="${list}">
							<option value="${one.typeId}">${one.typeName}</option>
						</c:forEach>
					</select>

				</div>
				<div class="module_search_input">
					信息名称：<input type="text" name="infoName"
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