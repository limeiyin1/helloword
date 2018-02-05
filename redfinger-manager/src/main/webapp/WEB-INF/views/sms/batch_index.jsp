<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>短信批次列表</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var formatterResultStatus=function(value,row,index){
		return getDatagridDict('rf_sms.result_status',value);	
	}
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
			{width:20,title:'id',field:'id',checkbox:true},
			{width:100,title:'名称',field:'name',sortable:true},
			{width:100,title:'发送时间',field:'createTime',sortable:true,formatter:formatterTime}
		]]
	};
	var dialogParamObj={
		width:1000
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	
	function detail(){
		var id = getGridId();
		if (!id) {
			return false;
		}
		var title = $("title").html() + " - 短信列表";
		var href = host + 'detailForm?' + pk + '=' + id;
		$("#button-save").unbind("click").click(cancel);
		openDialogForm(title, href);
	}
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">批次名称：<input type="text" name="name" class="easyui-textbox input_width_default"/></div>
			<div class="module_search_input">短信时间：<input type="text" name="beginTimeStr" class="easyui-datebox input_width_default" style="width: 100px" editable="false"/>至<input type="text" name="endTimeStr" class="easyui-datebox input_width_default" style="width: 100px" editable="false"/></div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-detail-rf" plain="true" onclick="detail()">短信详情</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>
	
	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
    <div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton" iconCls="icon-ok-rf">确定</a>
		<a href="javascript:void(0)" id="button-cancel" class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
</body>
</html>



