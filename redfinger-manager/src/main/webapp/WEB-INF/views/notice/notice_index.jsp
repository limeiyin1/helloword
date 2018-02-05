<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>公告</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="noticeId";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var formatterPop=function(value,row,index){
		return getDatagridDict('rf_notice.pop_status',value);
	}
	var dataGridParamObj={
		url:host+"list",
		idField : 'noticeId',
		 onRowContextMenu: onRowContextMenu, //右键。
		onCheck: function(row){
			
		},
		columns:[[
			{width:100,title:'id',field:'noticeId',checkbox:true},
			{width:100,title:'标题',field:'noticeTitle',sortable:true},
			{width:100,title:'内容',field:'noticeContent',sortable:true},
			{width:100,title:'发送类型',field:'map.noticeTypeName',sortable:true},
			{width:100,title:'创建人',field:'creater',sortable:true},
			{width:100,title:'优先级',field:'reorder',sortable:true},
			{width:100,title:'创建时间',field:'createTime',sortable:true,formatter:formatterTime},
			{width:100,title:'是否弹出',field:'popStatus',sortable:true,formatter:formatterPop},
			{width:100,title:'弹出状态',field:'popExpired',sortable:true,formatter:function(value){return getDatagridDict('rf_notice.pop_expired',value);}},
			{width:100,title:'弹出有效期',field:'popExpiredTime',sortable:true,formatter:formatterTime},
			{width:100,title:'启用状态',field:'enableStatus',sortable:true,formatter:formatterStop}
		]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
		$("#look").dialog($.extend({}, dialogParam, dialogParamObj));
	});
	
	function notice(){
		var ids=getGridIdAll();
		var title=$("title").html()+" - 发布公告";
		var href=host+'noticeForm?ids='+ids;
		$("#button-save").unbind("click").click(noticeSave);
		openDialogFormNotice(title,href);
	}
	function noticeSave(){
		if($('#easyui-form').form("validate")){
			$.messager.progress();
			$('#easyui-form').form("submit");
		}
	}
 function openDialogFormNotice(title, href) {
		$(module_dialog).dialog({title : title,href: href,width:800,height:550,maximizable:true,});
		$(module_dialog).dialog("open");
	
	}

 function getGridIdAll() {
	 var row = $(module_datagrid).datagrid("getSelections");
		if (row == '') {
			return 'all';
		} else if (row.length > 1) {
			$.messager.alert('提示', '请选择其中一条记录！', "info");
			return null;
		} else {
			return row[0][pk];
		}
	}
 function addNotice() {
		var title = $("title").html() + " - 新增";
		var href = host + 'form';
		$("#button-save").unbind("click").click(save);
		openDialogFormNotice(title, href);
	}
 function editNotice() {
		var id = getGridId();
		if (!id){
			return false;
		}
		var title = $("title").html() + " - 编辑";
		var href = host + 'form?'+pk+'=' + id;
		$("#button-save").unbind("click").click(update);
		openDialogFormNotice(title, href);
	}
 function onRowContextMenu(e, rowIndex, rowData){
	    e.preventDefault();
	    var selected=$("#module_datagrid").datagrid('getRows'); //获取所有行集合对象
	     var idValue = selected[rowIndex].noticeId;
	     $(this).datagrid('selectRecord', idValue);  //通过获取到的id的值做参数选中一行
	    $('#mm').menu('show', {
	        left:e.pageX,
	        top:e.pageY
	    });       
	}
 var dataGridParam = {
			fitColumns : true,
			pagination : true,
			striped : true,
			rownumbers : true,  
			singleSelect : false,
			idField : 'id',
			pageSize : 100,
			pageList : [ 100, 50, 200,500 ],
			loadFilter : loadFilterForDataGrid,
			loadMsg : "处理中，请稍后..."
		};
	//查看	
	function lookNotice() {
		var id = getGridId();
		if (!id) {
			return false;
		}
		var title = $("title").html() + " - 查看";
		var href = host + 'look?' + pk + '=' + id;
		$("#button-save").unbind("click").click(update);
		lookForm(title, href);
	}
	//打开编辑对话框
	function lookForm(title, href) {
		$("#look").dialog({title : title,href: href,width:555});
		$("#look").dialog("open");
	}
	
	function cancelPop(){
		var ids = getGridIds();
		if (!ids) {
			return false;
		}
		$.post("${ctx}/notice/notice/cancelPop",{ids:ids},callback);
	}
	
	function lookcancel(){
		$("#look").dialog("close");
	}
</script>
<style type="text/css">



</style>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">
			标题:<input type="text" name="noticeTitle" class="easyui-textbox input_width_default"/>&nbsp;
			内容:<input type="text" name="noticeContent" class="easyui-textbox input_width_default"/>&nbsp;        
          	弹出类型：
          	<select class="easyui-combobox" name="popStatus" id="state" editable="false" style="width:100px;">
           		<option value="">[全部]</option> 
		        <fns:getOptions category="rf_notice.pop_status"></fns:getOptions>
			</select> &nbsp;
			弹出状态:
			<select class="easyui-combobox" name="popExpired" editable="false" style="width:100px;">
           		<option value="">[全部]</option> 
		        <fns:getOptions category="rf_notice.pop_expired"></fns:getOptions>
			</select> &nbsp;
			</div>
			
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="lookNotice()">查看</a>
		<!-- 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="addNotice()">新增</a>-->
	    <!--     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="editNotice()">编辑</a> -->
	   <!--      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a> --> 
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop-rf" plain="true" onclick="cancelPop()">取消弹出</a>
	   <!--      	<a href="/redfinger-manager/notice/publish" class="easyui-linkbutton" iconCls="icon-search-rf" plain="true" >发布公告</a>
	        	<a href="/redfinger-manager/notice/userNotice" class="easyui-linkbutton" iconCls="icon-search-rf" plain="true" >查看公告记录</a> -->
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"   ></table>
	</div>
	
	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
    <div id="module_dialog_button" >
		<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a>
		<a href="javascript:void(0)" id="button-cancel" class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
	<div id="mm" class="easyui-menu" style="width:120px;">
    <div onClick="editNotice()" data-options="iconCls:'icon-search'">查看</div>
    <div onClick="addNotice()" data-options="iconCls:'icon-add'">新增</div>
    <div onClick="editNotice()" data-options="iconCls:'icon-edit'">编辑</div>
    <div onClick="del(callback)" data-options="iconCls:'icon-remove'">删除</div>
    <div class="menu-sep"></div>
    <div onClick="fresh()" data-options="iconCls:'icon-reload'">刷新</div>
</div>
<div id="look" buttons="#look_button"  data-options="maximizable:true"></div>
	<div id="look_button">
     <a href="javascript:void(0)"  class="easyui-linkbutton" iconCls="icon-no" onclick="lookcancel()">关闭</a>
	</div>
</body>
</html>



