<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>支付交易日志</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="tradeLogId";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField :'tradeLogId',
		onCheck: function(row){
			
		},
		columns:[[
			{width:100,title:'tradeLogId',field:pk,checkbox:true},	
			{width:200,title:'来源',field:'userSource',sortable:true},
			{width:200,title:'订单号',field:'orderId',sortable:true},
			{width:200,title:'支付方式',field:'payModeCode',sortable:true,formatter:function(value){return getDatagridDict('rf_pay_trade_log.pay_mode_code',value);}},
			{width:200,title:'交易流水号',field:'tradeSerialNum',sortable:true},
			{width:200,title:'交易账号',field:'tradeAccount',sortable:true},
			{width:200,title:'交易时间',field:'tradeTime',sortable:true,formatter:formatterTime},
			{width:200,title:'收款账号',field:'gatherAccount',sortable:true},
			{width:200,title:'交易金额(元)',field:'tradeMoney',sortable:true,formatter:function(value){return value/100;}},
			{width:200,title:'交易状态',field:'tradeStatus',sortable:true},	
			{width:100,title:'状态',field:'enableStatus',sortable:true,formatter:formatterStop}
		]]
		,onLoadSuccess:function(data){
			var rows = $(module_datagrid).datagrid('getRows');
			var total = rows.length > 0 ? rows[0]['map.tradeMoneyCount'] : 0
			$("#tradeMoneyTotal").html("交易总金额:" + "<span style='color:red;'>" + (total/100) + "元</span>" );
		}
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	
	//导出数据
	function statExport() {
		var pager = $(module_datagrid).datagrid("getPager");
		var total = $(pager).pagination('options').total;
		if (total) {
			var cols = dataGridParamObj.columns[0];
			var exportHead = "";
			var exportField = "";
			for ( var i in cols) {
				if (i != 0) {
					exportHead = exportHead + cols[i].title + ",";
					exportField = exportField + cols[i].field + ",";
				}
			}
			var where = "";
			var params = $(module_datagrid).datagrid('options').queryParams;
			for ( var i in params) {
				where += i + "=" + params[i] + "&";
			}
			$("#exportForm").attr("action", host + 'export?' + where);
			$("#exportHead").val(exportHead);
			$("#exportField").val(exportField);
			$("#exportName").val('支付交易日志');
			$("#exportForm").submit();
		} else {
			$.messager.alert('操作失败', '无数据！', "warning");
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
				支付方式：
			 	<select class="easyui-combobox input_width_default" editable="false" name="payModeCode">
					<option value="">[全部]</option>
					<fns:getOptions category="rf_pay_trade_log.pay_mode_code"/>
				</select> 
			</div>
			<div class="module_search_input">
			交易状态：
		 	<select class="easyui-combobox input_width_default" editable="false" name="tradeStatus">
				<option value="">[全部]</option>
				<fns:getOptions category="rf_pay_trade_log.trade_status"/>
			</select> 
		   </div>
		   <div class="module_search_input">
			交易金额:
		 	<select class="easyui-combobox input_width_default" editable="false" name="tradeCondition" style="width:40px">
				<option value="=">=</option>
				<option value="&gt;">&gt;</option>
				<option value="&ge;">&ge;</option>
				<option value="&lt;">&lt;</option>
				<option value="&le;">&le;</option>
			</select> 
			<input type="text" class="easyui-numberbox input_width_default" name="tradeMoneyDouble" data-options="precision:2" style="width:100px"/>
		   </div>
			<div class="module_search_input">
				订单号：<input type="text" class="easyui-textbox input_width_default" name="orderId" data-options=""/>
			</div>
			<div class="module_search_input">
				收款账号：
				<select class="easyui-combobox input_width_long" editable="false" name="gatherAccount">
				<option value="">[全部]</option>
				<fns:getOptions category="rf_pay_trade_log.gather_account"/>
			</select> 
			</div>
			<div class="module_search_input">
				操作时间：
				<input type="text" class="easyui-datebox input_width_default" editable="false" id="begin" name="beginTimeStr" data-options=""/>
				至
				<input type="text" class="easyui-datebox input_width_default" editable="false" id="end" name="endTimeStr" data-options=""/>
			</div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
				<span id="tradeMoneyTotal">总金额:</span>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
		   <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-excel-rf',plain:true" onclick="statExport()">导出</a>
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
	<!--导出  -->
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



