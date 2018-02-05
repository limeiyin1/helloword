<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>栏目</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="id";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj = {
			url : host + "list",
			idField : 'id',
			onCheck : function(row) {

			},
			columns : [ [ {
				width : 100,
				title : 'id',
				field : 'id',
				checkbox : true
			}, {
				width : 100,
				title : '栏目',
				field : 'name',
				sortable : true
			},
			
			{
				width : 100,
				title : '描述',
				field : 'remark'
			},
			{width:100,title:'排序',field:'reorder',sortable:true},{
				width : 100,
				title : '状态',
				field : 'enableStatus',
				sortable : true,
				formatter : formatterStop
			} ] ]
		};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	function AppApk(){
		var id=getGridId();
		if(!id)return false;
		var title=$("title").html()+" - 添加游戏";
		var href=host+'gameForm?id='+id;	
		$("#button-save").unbind("click").click(appApkSave);
		openDialogForm(title,href);
	}
	function appApkSave(){
		if($('#easyui-form').form("validate")){
			$.messager.progress();
			$('#easyui-form').form("submit");
		}
	}
	
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">名称：<input type="text" name="name" class="easyui-textbox input_width_default"/></div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
		<c:if test="${not empty sessionScope.permission.button_recommend_save}">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="add()">新增</a>
			</c:if>
			<c:if test="${not empty sessionScope.permission.button_recommend_update}">
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a>
	        </c:if>
	        <c:if test="${not empty sessionScope.permission.button_recommend_apk}">
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-star-rf" plain="true" onclick="AppApk()">添加游戏</a>
	        </c:if>
	        <c:if test="${not empty sessionScope.permission.button_recommend_start}">
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
	        </c:if>
	        <c:if test="${not empty sessionScope.permission.button_recommend_stop}">
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
	        </c:if>
	        <c:if test="${not empty sessionScope.permission.button_recommend_delete}">
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a>
	        </c:if>
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



