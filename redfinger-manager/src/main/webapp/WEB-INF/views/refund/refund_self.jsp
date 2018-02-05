<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>超级VIP退款</title>
<meta name="decorator" content="moduleIndex" />
<style type="text/css">
.my_td30{
	min-width:30px;
	width:30px;
	border-bottom: 1px solid #ccc;
	text-align: right;
	padding: 10px;
	font-size: 12px;
}
.my_td50{
	min-width:50px;
	width:50px;
	border-bottom: 1px solid #ccc;
	text-align: right;
	padding: 10px;
	font-size: 12px;
}
</style>
<script type="text/javascript">
	var pk="refundId";  //主键,这个很重要,不能弄错
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField : pk,
		onCheck: function(index,row){
			var handleStatus = row.handleStatus;
			
			if(handleStatus == '7'){
				$("#handleBtn,#finishBtn").linkbutton('disable');
			}else{
				if(handleStatus == '-1' || handleStatus == '0' || handleStatus == '5'){
					$("#finishBtn").linkbutton('enable');
					$("#handleBtn").linkbutton('disable');
				}else{
					$("#finishBtn").linkbutton('disable');
					$("#handleBtn").linkbutton('enable');
				}
			}
		},
		//这里field中填写的是实体类中的属性名,不是数据库字段名
		columns:[[
			{width:100,title:'refundId',field:pk,checkbox:true},
			{width:80,title:'会员ID',field:'map.externalUserId'},
			{width:80,title:'用户账号',field:'map.userMobilePhone',sortable:true},
			{width:120,title:'订单号',field:'orderId',sortable:false},
			{width:70,title:'订单金额(元)',field:'orderPrice',sortable:false,formatter:function(value){return value/100;}},
			{width:70,title:'退款金额(元)',field:'returnFee',sortable:false,formatter:function(value){return value/100;}},
			{width:70,title:'应收金额(元)',field:'realFee',sortable:false,formatter:function(value){return value/100;}},
			{width:100,title:'设备编码',field:'padCode',sortable:true},
			{width:100,title:'内部退款状态',field:'handleStatus',sortable:true,formatter:function(value){return getDatagridDict('rf_refund.handle_status',value);}},
			{width:100,title:'外部退款状态',field:'refundStatus',sortable:true,formatter:function(value){return getDatagridDict('rf_refund.refund_status',value);}},
			{width:150,title:'处理意见',field:'handleOpinion',sortable:true},
			{width:120,title:'申请退款时间',field:'createTime',sortable:true,formatter:formatterTime},
			{width:100,title:'设备绑定时间',field:'map.bindTime',sortable:true,formatter:formatterTime},
			{width:100,title:'备注',field:'remark',sortable:true},
			{width:100,title:'启用状态',field:'enableStatus',sortable:true,formatter : formatterStop}
			]]
	};
	var dialogParamObj={
		
	};
	//双击callback
	var lookDblClickCallback=function(index,row){
		var title = $("title").html() + " - 查看";
		var href = host + 'look?'+pk+'=' + row[pk];
		$("#button-close").unbind("click").click(lookCancel);
		
		$("#look").dialog({title : title,href: href, width:800, height:460, left:screen.width/6});
		$("#look").dialog("open");
	};
	// 普通表格默认参数
	var lookDataGridParam = {
		fitColumns : true,
		pagination : true,
		striped : true,
		rownumbers : true,
		singleSelect : false,
		idField : 'id',
		pageSize : 20,
		pageList : [ 10, 15, 20, 50, 100, 200,500 ],
		loadFilter : loadFilterForDataGrid,
		onDblClickRow:lookDblClickCallback,
		loadMsg : "处理中，请稍后..."
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},lookDataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	
	function lookCancel() {
		$("#look").dialog("close");
	}
	
	var refundForm = function() {
		var id = getGridId();
		if (!id) {
			return false;
		}
		var title = $("title").html() + " - 编辑";
		var href = host + 'refundForm?' + pk + '=' + id;
		$("#button-save").unbind("click").click(saveRefund);
		openDialogForm(title, href);
	}
	
	var saveRefund = function() {
		if ($("form[name=refund_submit_form]").form("validate")) {
			$.messager.progress();
			$("form[name=refund_submit_form]").form({
				url : host + 'refundHandle'
			});
			$("form[name=refund_submit_form]").form("submit");
		}
	}
	
	//归档	
	var finishRefund = function(callback) {
		var ids = getGridIds();
		if (!ids) {
			return false;
		}
		ajaxPost("finishRefund", {ids : ids}, callback);
	}
</script>
</head>
<body>
	<!-- 综合查询条件的表格  -->
	   <div id="module_container" >
		<div id="module_search" >
		
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">
				会员ID：<input type="text" name="externalUserId" class="easyui-numberbox input_width_short" />
			</div>
			<div class="module_search_input">
				用户账号：<input type="text" name="userMobilePhone" class="easyui-textbox input_width_default" />
			</div>
			<div class="module_search_input">
			  内部退款状态：<select class="easyui-combobox input_width_short"  editable="false" name="handleStatus" data-options="required:false" style="width:135px">
						<option value="">[全部]</option>
					   <fns:getOptions category="rf_refund.handle_status"  ></fns:getOptions>
				 	</select> 
			</div>
			<div class="module_search_input">
			  外部退款状态：<select class="easyui-combobox input_width_short"  editable="false" name="refundStatus" data-options="required:false" style="width:110px">
						<option value="">[全部]</option>
					   <fns:getOptions category="rf_refund.refund_status"  ></fns:getOptions>
				 	</select> 
			</div>
			<div class="module_search_input">
				申请开始时间:
					<input type="text" class="easyui-datebox input_width_default" editable="false"  name="beginTimeStr" />
				申请结束时间:
					<input type="text" class="easyui-datebox input_width_default" editable="false"  name="endTimeStr"/>
			</div>  
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		
		
		<!--增删查改的biaoge  -->
		<div id="module_toolbar" class="easyui-toolbar">
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-tools-rf" plain="true" onclick="refundForm()" id="handleBtn">处理</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-tools-rf" plain="true" onclick="finishRefund()" id="finishBtn">归档</a>
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
	
	<div style="display:none">
	<div id="look_dialog" buttons="#look_dialog_button"></div>
	<div id="look_dialog_button">
     <a href="javascript:void(0)" id="button-log-close" class="easyui-linkbutton" iconCls="icon-no" onclick="$('#look_dialog').dialog('close')">关闭</a>
	</div>
	<div id="look" buttons="#look_button"  ></div>
	<div id="look_button">
     <a href="javascript:void(0)" id="button-close" class="easyui-linkbutton" iconCls="icon-no" onclick="lookCancel()">关闭</a>
	</div>
	</div>
</body>
</html>
