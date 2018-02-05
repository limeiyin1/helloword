<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>发行商管理</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript" src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/jquery-jbox/2.3/i18n/jquery.jBox-zh-CN.js"></script>
<link type="text/css" rel="stylesheet" href="${ctxStatic}/jquery-jbox/2.3/Skins/Blue/jbox.css"/>
<script type="text/javascript">
	var pk="id";
	var ip = "ip";
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
			columns : [ [ 
	    		 {width : 100,title : 'id',field : 'id',checkbox : true}, 
	    		 {width : 20,title : '发行商名称',field : 'name'},
	    		 {width : 50,title : '创建时间',field : 'createTime',formatter:formatterTime,sortable:true},
	    		 {width : 50,title : '修改时间',field : 'modifyTime',formatter:formatterTime,sortable:true},
	    		 {width : 20,title : '修改人',field : 'modifier'},
	    		 {width : 20,title : '状态',field : 'enableStatus',sortable:true,formatter:formatterStop},
	    		 {width : 60,title : '公司简介',field : 'remark'}
			    		 ] ]
		};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
		$("#look").dialog($.extend({}, dialogParam, dialogParamObj));
	});
	
	//双击callback
	var dblClickCallback=function(index,row){		
		var title = $("title").html() + " - 旗下apk";
		var href = host + 'look?'+pk+'=' + row[pk];
		$("#button-save").unbind("click").click(update);
		lookForm(title, href);
	};
	
	
	//打开编辑对话框
	function lookForm(title, href) {	
		$("#look").dialog({title : title,href: href,width:699,height: 500,left: 280,top:50,});
		$("#look").dialog("open");
	}
	function lookcancel() {
		$("#look").dialog("close");
	}
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">
				发行商名称：
			<input type="text" name="name" class="easyui-textbox input_width_default" />
			</div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<c:if test="${not empty sessionScope.permission.button_sensitive_save}">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="add()">新增</a>
			</c:if>
			<c:if test="${not empty sessionScope.permission.button_sensitive_update}">
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a>
	        </c:if>
	        <c:if test="${not empty sessionScope.permission.button_sensitive_start}">
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
	        </c:if>
	        <c:if test="${not empty sessionScope.permission.button_sensitive_stop}">
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
	        </c:if>
	        <c:if test="${not empty sessionScope.permission.button_sensitive_delete}">
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



