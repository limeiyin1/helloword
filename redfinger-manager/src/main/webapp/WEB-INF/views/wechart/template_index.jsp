<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>标签管理</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk = "templateId";
	var module_datagrid = "#module_datagrid";
	var module_dialog = "#module_dialog";
	var module_search_form = "#module_search_form"
	var module_submit_form = "#module_submit_form";
	var callback = defaultCallback;

	var dataGridParamObj = {
		url : host + "list",
		idField : 'templateId',
		onCheck : function(row) {

		},
		columns : [[ 
		              {width : 100,title : 'id',field : 'templateId',checkbox : true}, 
		              {width : 100,title : '用户号码',field : 'map.userMobilePhone',sortable : false}, 
		              {width : 100,title : '设备编码',field : 'padCode',sortable : true}, 
		              {width : 100,title : '微信类型',field : 'map.templateTypeName',sortable : false}, 
		              {width : 100,title : '微信内容',field : 'map.title',sortable : false}, 
		              {width : 50,title : '发送状态',field:'map.templateStatusName',sortable:false},
					  {width : 150,title : '创建时间',field : 'createTime',sortable : true,formatter : formatterTime},
		              {width : 100,title : '启用状态',field : 'enableStatus',sortable : true,formatter : formatterStop}, 
		              {width : 100,title : '备注',field : 'remark',sortable : false}
		          ]]
		};
	var dialogParamObj = {};
	$(function() {
		$(module_datagrid).datagrid($.extend({}, dataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
	});

	function formatterLabelType(value, row, index) {
		return getDatagridDict('rf_label.label_type',value)
	}


	var sentMessage = function(callback) {
		var id=getGridId();
		if(!id)return false;
		$.messager.confirm('确认', '确定要发送此微信消息?', function(confirm) {
			if (confirm) {
				ajaxPost("sentMessage", {
					templateId : id
				}, callback);
			}
		});
	}
	
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container">
		<div id="module_search">
			<form id="module_search_form" class="easyui-form" method="post">
				<div class="module_search_input">
					用户号码：<input type="text" name="userMobilePhone"
						class="easyui-textbox input_width_default" /> 
				</div>
				<div class="module_search_input">		
					设备编码:<input type="text" name="padCode" class="easyui-textbox input_width_default" /> 
				 </div>	
				 <div class="module_search_input">
					消息类型:<select editable="false" name="templateType" class="easyui-combobox input_width_default" >
						<option value="">[全部]</option>
						<c:forEach var="one" items="${types}">
							<option value="${one.key}">${one.value}</option>
						</c:forEach>
					</select>
				 </div>
				 <div class="module_search_input">
					发送状态:<select editable="false" name="templateStatus" class="easyui-combobox input_width_default" >
						<option value="">[全部]</option>
						<c:forEach var="one" items="${statuss}">
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
			<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="add()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a> -->
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start-rf" plain="true" onclick="sentMessage(callback)">发送消息</a>
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



