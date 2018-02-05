<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>考核额外加分</title>
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
		idField : pk,
		onCheck: function(row){
		},
		columns:[[
			{width:100,title:'id',field:pk,checkbox:true},
			{width:100,title:'考核名称',field:'map.pName',sortable:true},
			{width:100,title:'考核员工',field:'targetAdmin',sortable:true},
			{width:100,title:'额外加分',field:'otherPoint',sortable:true,formatter:function(value){if(value==null){return "未加分";}else if(value!=null){return "已加";}}}
		]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	
	// 加分
	var addPoints = function() {
		var id = getGridId();
		if (!id) {
			return false;
		}
		var title = $("title").html() + " - 加（改）分";
		var href = host + 'form?' + pk + '=' + id;
		$("#button-save").unbind("click").click(update);
		openDialogForm(title, href);
	}
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">考核名称：<input type="text" name="name" class="easyui-textbox input_width_default"/></div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="addPoints()">加(改)分</a>
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



