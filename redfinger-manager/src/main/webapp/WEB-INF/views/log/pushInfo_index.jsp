<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>推送信息记录</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
var pk="id";
var module_datagrid = "#module_datagrid";
var module_dialog = "#module_dialog";
var module_search_form = "#module_search_form"
var module_submit_form = "#module_submit_form";
var callback = defaultCallback ;
var dataGridParamObj = {
	url : host + "list",
	idField : 'id',
		onCheck: function(row){
			
		},
		columns:[[
			{width:100,title:'id',field:pk,checkbox:true},
			{width:100,title:'推送批次',field:'batchId',sortable:true},
			{width:100,title:'会员手机',field:'map.userPhone',},
			{width:100,title:'channelId',field:'channelId',sortable:true},
			{width:100,title:'会员设备',field:'map.padCode',},
			{width:150,title:'实际发送时间',field:'realSendTime',sortable:true,formatter:formatterTime},
			{width:150,title:'计划发送时间',field:'planSendTime',sortable:true,formatter:formatterTime},
			{width:100,title:'通知标题',field:'title',sortable:true},
			{width:100,title:'通知内容',field:'description',sortable:true},
			{width:100,title:'创建人',field:'creater',sortable:true},
			{width:150,title:'创建时间',field:'createTime',sortable:true,formatter:formatterTime},
			{width:100,title:'消息状态',field:'msgStatus',sortable:true},
			{width:100,title:'消息类型',field:'msgType',sortable:true},
			{width:100,title:'错误编号',field:'errorCode',sortable:true},
			{width:100,title:'错误消息',field:'errorMsg',sortable:true},
			{width:100,title:'推送状态',field:'pushStatus',sortable:true},
			{width:100,title:'状态编号',field:'statusCode',sortable:true},
		]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	
	function statExport(){
 		var pager=$(module_datagrid).datagrid("getPager");
 		var total=$(pager).pagination('options').total;
 		if(total){
 			var cols=dataGridParamObj.columns[0];
 			var exportHead="";
 			var exportField="";
 			for(var i in cols){
 				if(i!=0){
 					exportHead=exportHead+cols[i].title+",";
 					exportField=exportField+cols[i].field+",";
 				}
 			}
 			var where="";
 			var params=$(module_datagrid).datagrid('options').queryParams;
 			for(var i in params){
 				where+=i+"="+params[i]+"&";
 			}
 			$("#exportForm").attr("action",host+'export?'+where);
 			$("#exportHead").val(exportHead);
 			$("#exportField").val(exportField);
 			$("#exportName").val('推送消息日志');
 			$("#exportForm").submit();
 		}else{
 			$.messager.alert('操作失败','无数据！',"warning");
 		}
 	}
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
		    <div class="module_search_input">
				channel_id：<input type="text" class="easyui-textbox input_width_default"  name="channelId"/>
			</div>
			<div class="module_search_input">
				推送批次号：<input type="text" class="easyui-numberbox input_width_default"  name="batchId"/>
			</div>
			<div class="module_search_input">	
				起始时间：
				<input type="text" class="easyui-datebox input_width_default"  id="begin" name="beginTimeStr" data-options="editable:false"/>
				至
				<input type="text" class="easyui-datebox input_width_default"  id="end" name="endTimeStr" data-options="editable:false"/>
			</div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search-rf',plain:false"  onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reset-rf',plain:false"   onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel-rf" plain="true" onclick="statExport()">导出</a>
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



