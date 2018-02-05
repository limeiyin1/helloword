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
					{width:60,title:'状态',field:'feedbackStatus',sortable:true,formatter:formatterFlag},
					{width:100,title:'设备编码',field:'padCode',sortable:true},
					{width:100,title:'设备IP',field:'map.padIp'},
					{width:100,title:'当前绑定状态',field:'map.bindStatus',formatter :function(value){return getDatagridDict('rf_pad.bind_status',value);}},
					{width:100,title:'故障时间',field:'createTime',sortable:true,formatter:formatterTime},
					{width:100,title:'修复时间',field:'finishTime',sortable:true,formatter:formatterTime},
					{width:100,title:'故障类型',field:'map.className'},
					{width:100,title:'故障描述',field:'feedbackContent',sortable:true},
					{width:100,title:'修复类型',field:'map.fixName'},
					{width:100,title:'修复内容',field:'feedbackHandle',sortable:true},
					{width:100,title:'来源',field:'feedbackSource',sortable:true,formatter:function(value){return getDatagridDict('rf_fault_feedback.feedback_source',value);}},
					{width:100,title:'联系电话',field:'feedbackContact',sortable:true},
					{width:100,title:'联系QQ',field:'feedbackQq',sortable:true},
					{width:100,title:'咨询',field:'map.promoter'},
					{width:100,title:'当前处理',field:'map.lastHandler'},
					{width:100,title:'操作来源',field:'map.clientSourceName'},
					{width:100,title:'备注',field:'remark'}
		]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	//双击callback
	var dblClickCallback=function(index,row){
		var title =	"故障详情";
		var href = host + 'detail?faultFeedbackId='+row[pk];
		$("#button-save").unbind("click").click(cancel);
		openDialogForm(title, href);
	};
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">
				设备编码:<input type="text" name="padCode" class="easyui-textbox input_width_default" />
		 	</div>
		 	<div class="module_search_input">
					编码段:<input type="text" name="padCodeStart" class="easyui-textbox input_width_default" />至
					<input type="text" name="padCodeEnd" class="easyui-textbox input_width_default" />
			 </div>
		 	<div class="module_search_input">
				故障时间:
				<input type="text" class="easyui-datebox input_width_default" editable="false"  name="beginTimeStr" />
				至
				<input type="text" class="easyui-datebox input_width_default" editable="false"  name="endTimeStr"/>
		 	</div>
			<div class="module_search_input">故障类型：
				<input type="text" id="classId" value="0" name="classId" class="easyui-combotree input_width_long3" data-options='data:${categoryTree}' />
			</div>
			<div class="module_search_input">故障状态：
				<select class="easyui-combobox input_width_default" editable="false" name="feedbackStatus">  
					<option value="">[全部]</option>
			        <fns:getOptions category="rf_fault_feedback.feedback_status" value=""
			        	keys="rf_fault_feedback.feedback_status@new,rf_fault_feedback.feedback_status@processing,rf_fault_feedback.feedback_status@movekefu,rf_fault_feedback.feedback_status@moveceshi,rf_fault_feedback.feedback_status@moveyunwei,rf_fault_feedback.feedback_status@handle"  
			        ></fns:getOptions>
				</select>
			</div>
			<div class="module_search_input">故障来源：
				<select class="easyui-combobox input_width_default" editable="false" name="feedbackSource">
					<option value="">[全部]</option>
					<fns:getOptions category="rf_fault_feedback.feedback_source"/>
				</select>
			</div>
			<div class="module_search_input">当前处理：
				<select class="easyui-combobox input_width_default" editable="false" name="lastHandler">
					<option value="">[全部]</option>
					<c:forEach var="one" items="${handerList }">
						<option value="${one.adminCode }">${one.adminName }</option>
					</c:forEach>
				</select>
			</div>
			<div class="module_search_input">操作类型：
				<select class="easyui-combobox input_width_default" editable="false" name="operateType">
					<option value="">[全部]</option>
					<fns:getOptions category="rf_fault_feekback.operate_type"/>
				</select>
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



