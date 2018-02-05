<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>图片管理</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk = "pictureId";
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
			title : '图片名称',
			field : 'pictureName',
			sortable : true
		}, {
			width : 100,
			title : '客户端',
			field : 'client',
			sortable : true
		}, {
			width : 100,
			title : '播放时间(秒)',
			field : 'playTime',
			sortable : true
		}, {
			width : 50,
			title : '排序',
			field : 'reorder',
			sortable : true
		},{
			width : 150,
			title : '模块',
			field : 'map.moduleName',
			sortable : true
		}, {
			width : 250,
			title : '展示图片地址',
			field : 'sevenPictureUrl',
			sortable : true
		}, {
			width : 150,
			title : '开始时间',
			field : 'startTime',
			sortable : true,
			formatter : formatterTime
		}, {
			width : 150,
			title : '结束时间',
			field : 'endTime',
			sortable : true,
			formatter : formatterTime
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
					客户端：
					<select class="easyui-combobox input_width_short" editable="false" name="client" data-options="required:false" style="width:150px">
						<option value="">[全部]</option>
						<option value="win">win</option>
						<option value="android">android</option>
						<option value="ios">ios</option>
					</select>
				</div>
				<div class="module_search_input">
					模块：
					<select class="easyui-combobox input_width_short" editable="false" name="moduleId" data-options="required:false" style="width:150px">
						<option value="">[全部]</option>
						<c:forEach var="one" items="${pmList}">
							<option value="${one.moduleId}">${one.moduleName}</option>
						</c:forEach>
					</select>
				</div>
				
				<!-- 根据启用禁用状态查询 -->
				<div class="module_search_input">
					启用状态：
					<select class="easyui-combobox input_width_short" editable="false" name="enableStatus" data-options="required:false" >
						<option value="">[全部]</option>
						<fns:getOptions category="global.enable_status"/>
					</select>
				</div>
				
				<div class="module_search_input">
					图片名称：<input type="text" name="pictureName"
						class="easyui-textbox input_width_default" />
				</div>
				<div class="module_search_input">
					创建时间： <input type="text" class="easyui-datebox input_width_default"
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