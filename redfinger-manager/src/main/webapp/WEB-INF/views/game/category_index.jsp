<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head> 
<title>游戏类型</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript" src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/jquery-jbox/2.3/i18n/jquery.jBox-zh-CN.js"></script>
<link type="text/css" rel="stylesheet" href="${ctxStatic}/jquery-jbox/2.3/Skins/Blue/jbox.css"/>
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
		//		treeField : 'name',
				onCheck: function(row){
					
				},
			columns : [[ 
			{width :100,title:'id',field:'id',checkbox : true}, 
			{width :100,title:'栏目',field :'name',sortable:true},
			{width: 100,title:'排序',field:'reorder',sortable:true},
			{width: 100,title:'描述',field:'remark',sortable:true},
			{width :100,title:'状态',field:'enableStatus',sortable:true,formatter:formatterStop},
			]]
	};
	var dialogParamObj = {

	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
		$("#look").dialog($.extend({}, dialogParam, dialogParamObj));
	});
	
	//添加游戏
	function AppApk(){
		var id=getGridId();
		if(!id)return false;
		var title=$("title").html()+" - 添加游戏";
		var href=host+'gameForm?id='+id;	
		$("#button-save").unbind("click").click(appApkSave);
		openDialogForm(title,href);
	}
	//保存
	function appApkSave(){
		if($('#easyui-form').form("validate")){
			$.messager.progress();
			$('#easyui-form').form("submit");
		}
	}
	
	//双击callback
	var dblClickCallback=function(index,row){
		var title = $("title").html() + " - 查看";
		var href = host + 'look?'+pk+'=' + row[pk];
		$("#button-save").unbind("click").click(update);
		apkList(title,href);
	};

	//打开编辑对话框
	function apkList(title,href){
		$("#look").dialog({title:title,href:href,width:699,left:280,top:50,});
		$("#look").dialog("open");
	}
	function lookcancel(){
		$("#look").dialog("close");
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
			<c:if test="${not empty sessionScope.permission.button_game_category_save}">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="add()">新增</a>
	        </c:if>
	        <c:if test="${not empty sessionScope.permission.button_game_category_update}">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a>
	        </c:if>
	        <c:if test="${not empty sessionScope.permission.button_game_category_apk}">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-star-rf" plain="true" onclick="AppApk()">添加游戏</a>
	        </c:if>
	        <c:if test="${not empty sessionScope.permission.button_game_category_start}">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
	        </c:if>
	        <c:if test="${not empty sessionScope.permission.button_game_category_stop}">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
	        </c:if>
	        <c:if test="${not empty sessionScope.permission.button_game_category_delete}">
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
	<div id="look" buttons="#look_button"  ></div>
	<div id="look_button">
		<a href="javascript:void(0)"  class="easyui-linkbutton" iconCls="icon-no" onclick="lookcancel()">关闭</a>
	</div>
	<div class="hide">
	<form action="" id="exportForm" method="post" target="_blank">
		<input type="hidden" name="exportDatas" id="exportDatas" value=""/>
		<input type="hidden" name="exportHead" id="exportHead" value=""/>
		<input type="hidden" name="exportField" id="exportField" value=""/>
		<input type="hidden" name="exportName" id="exportName" value=""/>
	</form>
	</div>
</body>
</html>

