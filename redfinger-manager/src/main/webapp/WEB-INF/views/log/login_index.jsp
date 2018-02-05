<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>会员登录日志</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="id";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField :'id',
		onCheck: function(row){
			
		},
		columns:[[
//			{width:100,title:'会员名称',field:'map.userName',sortable:true},
			{width:100,title:'手机号码',field:'map.userMobilePhone',sortable:true},
			{width:120,title:'邮箱',field:'map.userEmail',sortable:true},
			{width:120,title:'IP地址',field:'ipAddress'},
			{width:120,title:'MAC地址',field:'macId'},
			{width:120,title:'IMEI号',field:'imei'},
			{width:70,title:'手机型号',field:'mobileModel',sortable:true},
			{width:80,title:'客户端类型',field:'clientType',sortable:true},
			{width:100,title:'客户端编号',field:'userSource',sortable:true},
			{width:60,title:'版本编号',field:'version',sortable:true},
			{width:130,title:'登录时间',field:'loginTime',sortable:true,formatter:formatterTime}
			
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
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
		    <div class="module_search_input">
		               手机号码：<input type="text" name="phone" class="easyui-textbox input_width_default" />
		    </div>
		    
			<div class="module_search_input">
				登录时间：
				<input type="text" class="easyui-datebox input_width_default" editable="false"  name="beginTimeStr" data-options=""/>
				至
				<input type="text" class="easyui-datebox input_width_default" editable="false"  name="endTimeStr" data-options=""/>
			</div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
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


