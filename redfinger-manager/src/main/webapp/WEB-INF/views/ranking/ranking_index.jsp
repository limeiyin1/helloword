<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>超级VIP退款标准</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="rankingId";  //主键,这个很重要,不能弄错
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField : pk,
		onCheck: function(index,row){
			
		},
		//这里field中填写的是实体类中的属性名,不是数据库字段名
		columns:[[
			{width:100,title:'id',field:pk,checkbox:true},
			{width:100,title:'用户号码',field:'map.userMobilePhone',sortable:true},
			{width:120,title:'用户昵称',field:'map.nickName',sortable:true},
			{width:120,title:'名次',field:'ranking',sortable:true},
			{width:120,title:'红豆收入数量',field:'num',sortable:true},
			{width:150,title:'开始时间',field:'startTime',formatter:formatterTime,sortable:true},
			{width:150,title:'结束时间',field:'endTime',formatter:formatterTime,sortable:true},
			{width:100,title:'状态',field:'status',sortable:true,formatter:function(value){return getDatagridDict('global.enable_status',value);}},
			{width:100,title:'启用状态',field:'enableStatus',sortable:true,formatter : formatterStop},
			]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	
	
</script>
</head>
<body>
	<!-- 综合查询条件的表格  -->
	   <div id="module_container" >
		<div id="module_search" >
		
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">排行时间：
				<input type="text" class="easyui-datetimebox input_width_default" editable="false" 
					id="statTime" name="statTimeStr"/>
			</div>
			<div class="module_search_input">名次:
			<input  class="easyui-numberbox" type="text" name="begin" data-options="min:0," style="width:40px;"/>-
			<input  class="easyui-numberbox" type="text" name="end" data-options="min:0," style="width:40px;"/>
			</div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		
		
		<!--增删查改的biaoge  -->
		<div id="module_toolbar" class="easyui-toolbar">
			<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="add()">新增</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a> -->
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
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



