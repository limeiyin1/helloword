<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>节点</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
var pk="controlId";
var module_datagrid = "#module_datagrid";
var module_dialog = "#module_dialog";
var module_search_form = "#module_search_form"
var module_submit_form = "#module_submit_form";
var callback = defaultCallback ;
var dataGridParamObj = {
	url : host + "list",
	idField : 'controlId',
	onCheck : function(row) {

	},
	columns : [ [ {
		width : 100,
		title : 'id',
		field : 'controlId',
		checkbox : true
	}, {
		width : 100,
		title : '名称',
		field : 'controlName',
		sortable : true
	},

	{
		width : 100,
		title : '编号',
		field : 'controlCode',
	},
	{
		width : 100,
		title : '节点类型',
		field : 'controlType',
		formatter : formatterControlType
	},
	{
		width : 100,
		title : '控制节点端口',
		field : 'controlPort',
	},
	{
		width : 100,
		title : '节点IP',
		field : 'controlIp',
	},
	{
		width : 100,
		title : '备用控制节点端口',
		field : 'bakControlPort',
	},
	{
		width : 100,
		title : '备用节点IP',
		field : 'bakControlIp',
	},
	{
		width : 100,
		title : '消息队列名称',
		field : 'controlQueueName',
		sortable : true
	},
	{
		width : 100,
		title : '描述',
		field : 'remark'
	},
	{width:100,title:'排序',field:'reorder',sortable:true},{
		width : 100,
		title : '状态',
		field : 'enableStatus',
		sortable : true,
		formatter : formatterStop
	} ] ]
};
var dialogParamObj = {

};
$(function() {
	$(module_datagrid).datagrid($.extend({}, dataGridParam, dataGridParamObj));
	$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
});

//formatterControlType
function formatterControlType(value, row, index) {
	return getDatagridDict('rf_control.control_type',value)
}
	
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container">
		<div id="module_search">
			<form id="module_search_form" class="easyui-form" method="post">
				<div class="module_search_input">
					名称：<input type="text" name="controlName"
						class="easyui-textbox input_width_default" />
				</div>
				<div class="module_search_input">
					节点编码：<input type="text" name="controlCode"
						class="easyui-textbox input_width_default" />
				</div>
				<div class="module_search_input">		
					节点类型:<select class="easyui-combobox input_width_short"  editable="false" name="controlType" data-options="required:false" style="width:130px">
							<option value="">[全部]</option>
						   <fns:getOptions category="rf_control.control_type"  ></fns:getOptions>
							</select> 
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
				iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton"
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



