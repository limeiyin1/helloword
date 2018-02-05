<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>激活码类型管理</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="typeId";
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
		columns : [[
			{width : 100,title : 'id',field : pk,checkbox : true},
			{width : 100,title : '激活码类型名称',field : 'activationTypeName',sortable : true}, 
			{width : 100,title : '激活码类型编码',field : 'activationTypeCode',sortable : true}, 
			{width : 100,title : '设备时长（小时）',field : 'padTime',sortable : true},
			{width : 100,title : '设备类型',field : 'map.padType',sortable : true},
			{width : 100,title : '创建人',field : 'creater',sortable : true}, 
			{width : 150,title : '创建时间',field : 'createTime',sortable : true,formatter : formatterTime},
			{width : 100,title : '状态',field : 'status',sortable : true,formatter : formatterStop},
			{width : 100,title : '启用状态',	field : 'enableStatus',	sortable : true,formatter : formatterStop} 
		]]
	};
	var dialogParamObj = {

	};
	$(function() {
		$(module_datagrid).datagrid($.extend({}, dataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
		$("#module_dialog_edit").dialog($.extend({}, dialogParam, dialogParamObj));
	});
	
	function addModule(){
		var title = $("title").html() + " - 新增";
		var href = host + 'form';
		$("#button-save").unbind("click").click(save);
		$(module_dialog).dialog({title : title,href: href,width:550, left:screen.width/4});
		$(module_dialog).dialog("open");
	}
	
	function editModule(){
		var id = getGridId();
		if (!id) {
			return false;
		}
		var title = $("title").html() + " - 编辑";
		var href = host + 'form?' + pk + '=' + id;
		$("#button-save").unbind("click").click(update);
		$(module_dialog).dialog({title : title,href: href,width:750, left:screen.width/7});
		$(module_dialog).dialog("open");
	}
	
	function batchEditModule(){
		var id = getGridId();
		if (!id) {
			return false;
		}
		var title = $("title").html() + " - 批量修改";
		var href = host + 'batchEditForm?' + pk + '=' + id;
		$("#button-save").unbind("click").click(batchEdit);
		$(module_dialog).dialog({title : title,href: href,width:750, left:screen.width/7});
		$(module_dialog).dialog("open");
	}
	
	function batchEdit(){
		if ($('#module_submit_form').form("validate")) {
			$.messager.progress();
			$('#module_submit_form').form({
				url : host+'batchEditActivation',
				success : callback
			});
			$('#module_submit_form').form("submit");
		}
	}
	
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container">
		<div id="module_search">
			<form id="module_search_form" class="easyui-form" method="post">
				<div class="module_search_input">
					类型名称：<input type="text" name="activationTypeName"
						class="easyui-textbox input_width_default" />

				</div>
				<div class="module_search_input">
					状态：
			        <select class="easyui-combobox input_width_default" editable="false" name="enableStatus">
						<option value="">[全部]</option>
						<fns:getOptions category="global.enable_status"/>
					</select>
		      	</div>
		      	<!-- 设备类型 -->
				<div class="module_search_input">
					设备类型:
					<select class="easyui-combobox input_width_short" editable="false" name="padType" id="padType" data-options="required:false">
						<option value="">[全部]</option>
						<c:forEach var="one" items="${padType}">
							<option value="${one.key}">${one.value}</option>
						</c:forEach>
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
				iconCls="icon-add-rf" plain="true" onclick="addModule()">新增</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit-rf" plain="true" onclick="editModule()">编辑</a> 
			<a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit-rf" plain="true" onclick="batchEditModule()">批量修改</a>
			<a
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
	<!-- 新增框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
	<div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save"
			class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a> <a
			href="javascript:void(0)" id="button-cancel"
			class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
	
	<div style="display:none">
	<!-- 图片编辑框 -->
	<div id="activation_type_dialog" buttons="#activation_type_dialog_button" ></div>
	<div id="activation_type_dialog_button" >
		<a href="javascript:void(0)" id="button-save-pic" class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a>
		<a href="javascript:void(0)" id="button-cancel-pic"	class="easyui-linkbutton" iconCls="icon-no" onclick="pictureCancel()">关闭</a>
	</div>
	</div>
</body>
</html>