<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>APP应用市场</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="cfgId";  //主键,这个很重要,不能弄错
	var module_datagrid="#module_datagrid";    //#表示id
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",  //数据的来源
		idField : pk,
		onCheck: function(row){
			
		},
		
		//field中填写的是实体类中的属性名,不是数据库字段名
		columns:[[
			{width:100,title:'游戏监听配置ID',field:pk,checkbox:true},
			{width:100,title:'游戏名称',field:'gameName',sortable:true},
			{width:120,title:'游戏包名',field:'packageName',sortable:true},
			{width:100,title:'监听端口',field:'monitorPort',sortable:true},
			{width:110,title:'数据包临界值',field:'dataPacketLimit',sortable:true},
			{width:100,title:'采集频率(秒/次)',field:'gatherInterval',sortable:true},
			{width:100,title:'脚本路径',field:'gamePathPkg',sortable:true},
			{width:100,title:'启动游戏参数',field:'gameFlagCmp',sortable:true},
			{width:100,title:'创建人',field:'creater',sortable:true},
			{width:110,title:'创建时间',field:'createTime',formatter:formatterTime,sortable:true},
			{width:100,title:'修改人',field:'modifier',sortable:true},
			{width:150,title:'修改时间',field:'modifyTime',formatter:formatterTime,sortable:true},
			{width:100,title:'排序',field:'reorder',sortable:true},
			{width:100,title:'备注',field:'remark',sortable:true},
			{width:100,title:'启动状态',field:'enableStatus',sortable:true,formatter : formatterStop},
		
			]]
	};
	var dialogParamObj={};
	
	$( function(){
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
			<div class="module_search_input">游戏名称：<input type="text" name="gameName" class="easyui-textbox input_width_default"/></div>
			<div class="module_search_input">包名：<input type="text" name="packageName" class="easyui-textbox input_width_default"/></div>
			<div class="module_search_input">监控端口：<input type="text" name="monitorPort" class="easyui-textbox input_width_default"/></div>

			 <div class="module_search_input">
					创建时间:
						<input type="text" class="easyui-datebox input_width_default" editable="false"  name="beginTimeStr" />
						至
						<input type="text" class="easyui-datebox input_width_default" editable="false"  name="endTimeStr"/>
				 </div>  
			
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		
		
		<!--增删查改的biaoge  -->
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="add()">新增</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a>
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



