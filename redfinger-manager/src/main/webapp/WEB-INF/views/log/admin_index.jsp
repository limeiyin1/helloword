<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>系统操作日志</title>
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
			{width:100,title:'id',field:pk,checkbox:true},
			{width:200,title:'操作时间',field:'createTime',sortable:true,formatter:formatterTime},
			{width:100,title:'操作内容',field:'name',sortable:true},
			{width:100,title:'管理员',field:'map.creater'},
			{width:100,title:'操作类别',field:'category',sortable:true},
			{width:100,title:'操作IP',field:'operIp'},
			{width:800,title:'入参',field:'paramIn'},
			{width:100,title:'出参',field:'paramOut'},
			{width:100,title:'操作结果',field:'resultStatus',sortable:true,formatter:function(value){return getDatagridDict('log_admin.result_status',value);}},
			{width:200,title:'异常信息',field:'exceptionMsg',},
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
		                管理员帐号：<input type="text" name="creater" class="easyui-textbox input_width_default" />
		    </div>
		    <div class="module_search_input">
		                操作内容：<input type="text" name="name" class="easyui-textbox input_width_default" />
		    </div>
		    <div class="module_search_input">
		                操作结果：<select class="easyui-combobox input_width_short" editable="false" name="resultStatus" data-options="required:false">
		                <option value="">[全部]</option>
		                <fns:getOptions category="log_admin.result_status"></fns:getOptions> 
		           </select>
		    </div>
			<div class="module_search_input">
				操作时间：
				<input type="text" class="easyui-datebox input_width_default" editable="false" id="begin" name="begin" data-options=""/>
				至
				<input type="text" class="easyui-datebox input_width_default" editable="false" id="end" name="end" data-options=""/>
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



