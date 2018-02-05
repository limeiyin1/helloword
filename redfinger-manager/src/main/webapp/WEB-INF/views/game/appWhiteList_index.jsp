<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head> 
<title>虚拟机安装应用白名单</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript" src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/jquery-jbox/2.3/i18n/jquery.jBox-zh-CN.js"></script>
<link type="text/css" rel="stylesheet" href="${ctxStatic}/jquery-jbox/2.3/Skins/Blue/jbox.css"/>
<script type="text/javascript">
		var pk="appWhiteId";
		var module_datagrid="#module_datagrid";
		var module_dialog="#module_dialog";
		var module_search_form="#module_search_form";
		var module_submit_form="#module_submit_form";
		var callback=defaultCallback;
		var dataGridParamObj = {
					url : host + "list",
					idField : 'id',
					onCheck: function(row){
						
					},
				columns : [[ 
				{width :100,title:'id',field:'appWhiteId',checkbox : true}, 
				{width :100,title:'应用名称',field :'appName',sortable:true},
				{width: 100,title:'应用包',field:'packageName',sortable:true},
				{width: 100,title:'应用版本号',field:'versionCode',sortable:true},
				{width: 100,title:'应用版本名称',field:'versionName',sortable:true},
				{width: 100,title:'应用路径',field:'packageUri',sortable:true},
				{width: 100,title:'ManifestDigest',field:'manifestDigest',sortable:true},
				{width: 100,title:'签名',field:'sign',sortable:true},
				{width: 100,title:'创建时间',field:'createTime',sortable:true,formatter:formatterTime},
				{width :100,title:'审核状态',field:'auditStatus',sortable:true,formatter:function(value){return getDatagridDict('rf_app_white_list.audit_status',value);}},
				{width :100,title:'设备类别',field:'padClassify',sortable:true,formatter:function(value){return getDatagridDict('rf_pad.pad_classify',value);}},
				{width :100,title:'状态',field:'status',sortable:true,formatter:formatterStop}
				]]
		};
		var dialogParamObj = {
		
		};
		$(function(){
			$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
			$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
		});

</script>
</head>
<body>
	<div id="module_container" >
			<div id="module_search">
		    <form id="module_search_form" class="easyui-form" method="post">
			 <div class="module_search_input">审核状态：		
				<select class="easyui-combobox"  editable="false" name="auditStatus" data-options="required:true">
					    <option value="">[全部]</option>
						<fns:getOptions category="rf_app_white_list.audit_status" ></fns:getOptions> 
				</select> 
			</div>
			
			<div class="module_search_input">
				应用包 :<input type="text" name="packageName" class="easyui-textbox input_width_default" />
			</div>
			
			<div class="module_search_input">设备类别：		
				<select class="easyui-combobox"  editable="false" name="padClassify" data-options="required:true">
					    <option value="">[全部]</option>
						<fns:getOptions category="rf_pad.pad_classify" ></fns:getOptions> 
				</select> 
			</div>
				 
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
			
			</form>
			</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-add-rf" plain="true" onclick="add()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
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