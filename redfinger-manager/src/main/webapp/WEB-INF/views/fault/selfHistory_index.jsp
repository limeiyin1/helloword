<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>故障工单</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="faultFeedbackId";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var formatterFlag=function(value,row,index){
		return getDatagridDict('rf_fault_feedback.feedback_status',value);
	}
	var dataGridParamObj={
		url:host+"list",
		idField : pk,
		onCheck: function(index,row){
			
		},
		singleSelect:true,
		columns:[[
					{width:100,title:'id',field:pk,checkbox:true},
					{width:100,title:'故障当前状态',field:'map.feedbackStatus',formatter:formatterFlag},
					{width:100,title:'设备编码',field:'map.padCode'},
					{width:100,title:'设备IP',field:'map.padIp'},
					{width:100,title:'故障时间',field:'map.createTime',formatter:formatterTime},
					{width:100,title:'故障类型',field:'map.className'},
					{width:100,title:'故障描述',field:'map.feedbackContent'},
					{width:100,title:'来源',field:'map.feedbackSource',formatter:function(value){return getDatagridDict('rf_fault_feedback.feedback_source',value);}},
					{width:100,title:'联系电话',field:'map.feedbackContact'},
					{width:100,title:'联系QQ',field:'map.feedbackQq'},
					{width:100,title:'咨询人',field:'map.creater'},
					{width:100,title:'受理时间',field:'createTime',sortable:true,formatter:formatterTime},
					{width:100,title:'处理时间',field:'modifyTime',sortable:true,formatter:formatterTime},
					{width:100,title:'是否解决',field:'isSolve',formatter:function(value){return getDatagridDict('rf_fault_feedback.is_solve',value);}}
		]]
	};
	var dialogParamObj={
		
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
		var title =	"故障详情";
		var href = host + 'detail?faultFeedbackId='+id;
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
		 	<div class="module_search_input">
				处理时间:
				<input type="text" class="easyui-datebox input_width_default" editable="false"  name="beginTimeStr" />
				至
				<input type="text" class="easyui-datebox input_width_default" editable="false"  name="endTimeStr"/>
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
		<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton" iconCls="icon-ok-rf">确定</a>
		<a href="javascript:void(0)" id="button-cancel" class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
</body>
</html>



