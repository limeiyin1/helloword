<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>插件管理</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="pluginId";  //主键,这个很重要,不能弄错
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField : pk,
		onCheck: function(index,row){
			//只有启动才可以绑定设备
		},
		
		//这里field中填写的是实体类中的属性名,不是数据库字段名
		columns:[[
			{width:100,title:'pluginId',field:pk,checkbox:true},
			{width:120,title:'插件版本名称',field:'pluginVersionName',sortable:true},
			{width:120,title:'插件版本号',field:'pluginVersionCode',sortable:true},
			{width:120,title:'插件渠道编码',field:'pluginChannelCode',sortable:true},
			{width:120,title:'插件编码',field:'pluginCode',sortable:true},
			{width:150,title:'渠道客户端版本编码',field:'channelVersionCode',sortable:true},
			{width:200,title:'插件地址',field:'pluginUrl',sortable:true},
			{width:100,title:'是否必须更新',field:'pluginVersionMust',sortable:true,formatter:function(value){return getDatagridDict('client_version.update',value);}},
			{width:100,title:'创建人',field:'creater',sortable:true},
			{width:150,title:'创建时间',field:'createTime',formatter:formatterTime,sortable:true},
			{width:100,title:'修改人',field:'modifier',sortable:true},
			{width:100,title:'修改时间',field:'modifyTime',formatter:formatterTime,sortable:true},
			{width:100,title:'启用状态',field:'enableStatus',sortable:true,formatter : formatterStop}
			]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	
	function addPlugin(){
		var title=$("title").html()+" - 新增";
		var href=host+'addform';
		$("#button-save").unbind("click").click(save);
		openDialogForm(title,href);
	}
	
	
	//插件分包
	function subPluginPackageForm(){
		var title=$("title").html()+" - 插件分包操作";
 		var href=host+'subPluginPackageForm';
		$("#button-save").unbind("click").click(pluginSave);
		openDialogForm(title,href);
	}
	
	function pluginSave() {
		if ($('#module_submit_form').form("validate")) {
			$.messager.progress();
			$('#module_submit_form').form("submit");
		}
	}
</script>
</head>
<body>
	<!-- 综合查询条件的表格  -->
	   <div id="module_container" >
		<div id="module_search" >
		
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">插件版本号：<input type="text" name="pluginVersionCode" class="easyui-textbox input_width_default"/></div>
			<div class="module_search_input">插件渠道编码：<input type="text" name="pluginChannelCode" class="easyui-textbox input_width_default"/></div>
			<div class="module_search_input">插件编码：<input type="text" name="pluginCode" class="easyui-textbox input_width_default"/></div>
			<div class="module_search_input">渠道客户端版本编码：<input type="text" name="channelVersionCode" class="easyui-textbox input_width_default"/></div>

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
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="addPlugin()">新增</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a>
	       	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="subPluginPackageForm()">插件分包</a>
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



