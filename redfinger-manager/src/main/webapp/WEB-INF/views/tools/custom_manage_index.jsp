<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>客服管理</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="customId";
	var module_datagrid = "#module_datagrid";
	var module_dialog = "#module_dialog";
	var module_search_form = "#module_search_form";
	var module_submit_form = "#module_submit_form";
	var callback = defaultCallback;
	var dataGridParamObj = {
		url : host + "list",
		idField : pk,
		onCheck : function(index,row) {
		},
		columns : [[
			{width : 100,title : 'id',field : pk,checkbox : true},
			{width : 100,title : '客服名称',field : 'customName',sortable : true},
			{width : 100,title : '客服组别',field : 'customGroup',sortable : true,formatter : formatterCustomGroup},
			{width : 100,title : '是否为班长',field : 'isLeader',sortable : true,formatter : formatterLeader},
			{width : 100,title : '开始排班日期',field : 'enableTime',sortable:true,formatter:formatterDate},
			{width : 100,title : '结束排班日期',field : 'disableTime',sortable:true,formatter:formatterDate},
			{width : 100,title : '启用状态',field : 'enableStatus',sortable : true,formatter : formatterStop}
		]]
	};
	var dialogParamObj = {
	};
	$(function() {
		$(module_datagrid).datagrid($.extend({}, dataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
	});
	function formatterCustomGroup(value,row,index){
		if(value&&value==1){
			return '甲组(早中晚班)';
		}else if(value&&value==2){
			return '乙组(早中晚班)';
		}else if(value&&value==3){
			return '丙组(早中晚班)';
		}else if(value&&value==4){
			return '丁组(早中晚班)';
		}else if(value&&value==5){
			return '甲组(夜班)';
		}else if(value&&value==6){
			return '乙组(夜班)';
		}else if(value&&value==7){
			return '丙组(夜班)';
		}else if(value&&value==8){
			return '丁组(夜班)';
		}
	}
	function formatterLeader(value,row,index){
		if(value&&value==1){
			return '班长';
		}else if(value&&value==0){
			return '客服';
		}
	}
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">
				客服名称：<input type="text" name="customName" class="easyui-textbox input_width_default"/>
			</div>
			<div class="module_search_input">
				组别：
		        <select class="easyui-combobox input_width_default" editable="false" name="customGroup">
					<option value="">[全部]</option>
				    <c:forEach items="${RfCustomGroup}" var="one">
						<option value="${one.key}">${one.value}</option>
					</c:forEach>
				</select>
			</div>
			<div class="module_search_input">
				是否为班长：
				<select class="easyui-combobox input_width_default" editable="false" name="isLeader">
					<option value="">[全部]</option>
				    <c:forEach items="${yesOrNo}" var="one">
						<option value="${one.key}">${one.value}</option>
					</c:forEach>
			 	</select>
			</div>
			<div class="module_search_input">启用状态：
		        <select class="easyui-combobox input_width_default" editable="false" name="enableStatus">
					<option value="">[全部]</option>
					<fns:getOptions category="global.enable_status"/>
				</select>
			</div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="add()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a>
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