<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>友盟公告</title>
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
		idField : 'id',
		onRowContextMenu: onRowContextMenu, //右键。
		onCheck : function(index,row) {
			//状态为发送失败或者没有发送才能重新发送
			var flag=row.pushStatus;
			if(flag=='1' || flag=='3'){
				$("#rePush").linkbutton('enable');
			}else{
				$("#rePush").linkbutton('disable');
			}
		},
		columns:[[
			{width:100,title:'id',field:'id',checkbox:true},
			{width:100,title:'标题',field:'title',sortable:true},
			{width:100,title:'内容',field:'noticeContent',sortable:true},
			{width:100,title:'发送类型',field:'map.pushTypeName',sortable:true},
			{width:100,title:'发送状态',field:'map.pushStatusName',sortable:true},
			{width:100,title:'发送时间',field:'createTime',sortable:true,formatter:formatterTime},
			{width:100,title:'状态',field:'status',sortable:true,formatter:formatterStop},
			{width:100,title:'条件',field:'pushExist',sortable:true}
		]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	
	
	
 	function openDialogFormNotice(title, href) {
		$(module_dialog).dialog({title : title,href: href,width:720,height:500,maximizable:true,left:screen.width/6});
		$(module_dialog).dialog("open");
	
	}
	
	function getGridIdsAll() {
		var row = $(module_datagrid).datagrid("getSelections");
		if (row == '') {
			$.messager.alert('提示', '请选择记录！', "info");
			return null;
		} else {
			var ids = [];
			for (var i = 0; i < row.length; i++) {
				ids[i] = row[i][pk]
			}
			return ids.join(",");
		}
 	}
 
 	function onRowContextMenu(e, rowIndex, rowData){
	    e.preventDefault();
	    var selected=$("#module_datagrid").datagrid('getRows'); //获取所有行集合对象
	     var idValue = selected[rowIndex].uid;
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
		pageSize : 20,
		pageList : [ 20, 100, 200,500 ],
		loadFilter : loadFilterForDataGrid,
		loadMsg : "处理中，请稍后..."
	};
	
 	function formatterPop(value,row,index){
	 	return getDatagridDict('rf_notice.pop_status',value);
	}
	
	function rePush(){
		var id=getGridId();
		if(!id)return false;
		$.messager.confirm('确认？', '是否重新推送公告?', function(confirm) {
				if (confirm) {
				ajaxPost("rePush", {
					id : id
				}, callback);
			}
		});
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
		    <div class="module_search_input">  标题:<input type="text" id="title" name="title" class="easyui-textbox input_width_default"/>&nbsp;</div>
		    <div class="module_search_input">  内容:<input type="text" id="noticeContent" name="noticeContent" class="easyui-textbox input_width_default"/>&nbsp;</div>
		    <div class="module_search_input">		
				发送状态:<select class="easyui-combobox input_width_short"  editable="false" name="pushStatus" data-options="required:false" >
						<option value="">[全部]</option>
					    <c:forEach var="s"  items="${pushStatus }">
					    	<option value="${s.key }">${s.value }</option>
					    </c:forEach>
						</select> 
			</div>
			<div class="module_search_input">		
				发送类型:<select class="easyui-combobox input_width_short"  editable="false" name="pushType" data-options="required:false" >
						<option value="">[全部]</option>
					    <c:forEach var="s"  items="${pushType }">
					    	<option value="${s.key }">${s.value }</option>
					    </c:forEach>
						</select> 
			</div>
			<div class="module_search_input">
				登录时间：<input type="text" id="beginTimeStr" name="beginTimeStr" class="easyui-datetimebox" data-options="editable:false,showSeconds:true"/>
				至<input type="text" id="endTimeStr" name="endTimeStr" class="easyui-datetimebox"data-options="editable:false,showSeconds:true" />
			</div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-star-rf" plain="true" id="rePush" onclick="rePush(callback)">重新推送</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
	        
	       <!--  	<a href="/redfinger-manager/notice/notice" class="easyui-linkbutton" iconCls="icon-search-rf" plain="true" >查看公告</a>
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
    <div onClick="fresh()" data-options="iconCls:'icon-reload'">刷新</div>
    <div onClick="notice(callback)" data-options="iconCls:'icon-add'">发布公告</div>
    </div>
</body>
</html>



