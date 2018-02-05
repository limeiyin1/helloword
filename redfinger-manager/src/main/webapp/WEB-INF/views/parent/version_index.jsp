<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>版本控制</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="parentVersionId";  //主键,这个很重要,不能弄错
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
			var osType=row.osType;
			/* if(!(osType=='win' || osType=='android')){
				$("#bindPartPad").linkbutton('enable');
				$("#bindAllPad").linkbutton('enable');
			}else{
				$("#bindPartPad").linkbutton('disable');
				$("#bindAllPad").linkbutton('disable');
			} */
			
			if(osType=='android'){
				$("#editBatch").linkbutton('enable');
			}else{
				$("#editBatch").linkbutton('disable');
			}
		},
		
		//这里field中填写的是实体类中的属性名,不是数据库字段名
		columns:[[
			{width:100,title:'parentVersionId',field:pk,checkbox:true},
			{width:100,title:'版本名称',field:'parentVersionName',sortable:true},
			{width:100,title:'版本编码',field:'parentVersionCode',sortable:true},
			{width:150,title:'版本时间',field:'parentVersionTime',sortable:true,formatter:formatterTime},
			{width:400,title:'更新地址',field:'parentVersionUpdateurl',sortable:true},
			{width:400,title:'下载地址',field:'parentVersionDownloadurl',sortable:true},
			{width:100,title:'系统类别',field:'osType',sortable:true},
			{width:100,title:'是否必须更新',field:'versionMust',sortable:true,formatter:function(value){return getDatagridDict('client_version.update',value);}},
			/* {width:100,title:'发现栏是否可用',field:'discoverLimit',sortable:true,formatter:function(value){return getDatagridDict('global.yes_no',value);}}, */
			{width:100,title:'母包类型',field:'parentVersionType',sortable:true,formatter:function(value){return getDatagridDict('rf_channel_type',value);}},
			{width:100,title:'文件大小',field:'fileSize',sortable:true},
			{width:100,title:'创建人',field:'creater',sortable:true},
			{width:150,title:'创建时间',field:'createTime',formatter:formatterTime,sortable:true},
			{width:100,title:'修改人',field:'modifier',sortable:true},
			{width:100,title:'修改时间',field:'modifyTime',formatter:formatterTime,sortable:true},
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
	
	function addVersion(){
		var title=$("title").html()+" - 新增";
		var href=host+'addform';
		$("#button-save").unbind("click").click(save);
		openDialogForm(title,href);
	}
	
	
	
	function parentVersion() {
		if ($('#module_submit_form').form("validate")) {
			$.messager.progress();
			$('#module_submit_form').form("submit");
		}
	}
	
	//同步母包到渠道版本控制
	var synsPacket = function(callback) {
		var id = getGridId();
		if (!id) {
			return false;
		}
		$.messager.confirm('确认？', '是否同步此母包到渠道版本控制中', function(confirm) {
			if (confirm) {
				ajaxPost("synsPacket", {
					parentVersionId : id
				}, callback);
			}
		});
	}
	
	//同步补丁
	var synsMend = function(callback) {
		var id = getGridId();
		if (!id) {
			return false;
		}
		$.messager.confirm('确认？', '是否同步此补丁', function(confirm) {
			if (confirm) {
				ajaxPost("synsMend", {
					parentVersionId : id
				}, callback);
			}
		});
	}
	
	//同步插件
	var synsPlugin = function(callback) {
		var id = getGridId();
		if (!id) {
			return false;
		}
 		var href=host+'synsPluginForm?parentVersionId='+id;
		var title=$("title").html()+" - 同步插件操作";
		$("#button-save").unbind("click").click(parentVersion);
		openDialogForm(title,href);
	}
	
	function subPackage(){
		var id = getGridId();
		if (!id) {
			return false;
		}
		//alert(id);
 		var href=host+'subPackageForm?id='+id;
		var title=$("title").html()+" - 分包操作";
		$("#button-save").unbind("click").click(parentVersion);
		openDialogForm(title,href);
	}
	
	function parentVersion() {
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
			<div class="module_search_input">母包版本名称：<input type="text" name="parentVersionName" class="easyui-textbox input_width_default"/></div>
			<div class="module_search_input">母包版本编码：<input type="text" name="parentVersionCode" class="easyui-textbox input_width_default"/></div>
			<div class="module_search_input">渠道编码：<input type="text" name="parentChannelCode" class="easyui-textbox input_width_default"/></div>

			<div class="module_search_input">
				客户端类型：
				<select class="easyui-combobox input_width_short" name="osType" data-options="editable:false" style="width:100px">
					<option value="">[全部]</option>
				    <c:forEach items="${osTypes }" var="one">
				    	<option value="${one.key }">${one.value }</option>
				    </c:forEach>
			 	</select>
		  	</div>
		  	<div class="module_search_input">
				发现栏是否可用：
				<select class="easyui-combobox input_width_short" editable="false" id="discoverLimit" name="discoverLimit" validType="selectValueRequired['#versionMust']">
				<option value="">[全部]</option>
				  <fns:getOptions category="global.yes_no"></fns:getOptions>
			 	</select>
		  	</div>
		  	<div class="module_search_input">
				母包类型：
				<select class="easyui-combobox input_width_short" editable="false"  name="parentVersionType" validType="selectValueRequired['#versionMust']">
				<option value="">[全部]</option>
				  <fns:getOptions category="rf_channel_type"></fns:getOptions>
			 	</select>
		  	</div>
			
			 <div class="module_search_input">
					版本时间:
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
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="addVersion()">新增</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="subPackage()">渠道分包</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="synsPacket()">同步母包</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="synsMend()">同步补丁</a>
	        <!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="synsPlugin()">同步插件</a> -->
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



