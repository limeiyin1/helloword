<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>充值记录</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="rechargeId";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField :'rechargeId',
		onCheck: function(row){
			
		},
		columns:[[
			{width:100,title:'rechargeId',field:pk,checkbox:true},
			{width:200,title:'会员帐号',field:' userId',sortable:true},
			{width:200,title:'充值金额',field:'rechargeMoney',sortable:true},
			{width:200,title:'交易流水号',field:'tradeNo',sortable:true},
			{width:200,title:'交易时间',field:'tradeTime',sortable:true,formatter:formatterTime},
			{width:100,title:'创建人',field:'map.creater'},
			{width:200,title:'交易类型',field:'rechargeType',sortable:true,formatter:function(value){return getDatagridDict('rf_recharge.recharge_type',value);}},
			{width:200,title:'交易状态',field:'tradeStatus',sortable:true,formatter:function(value){return getDatagridDict('rf_pay_trade_log.trade_status',value);}},
			{width:100,title:'状态',field:'enableStatus',sortable:true,formatter:formatterStop}
		]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">
				充值类型：
			 	<select class="easyui-combobox input_width_default" editable="false" name="category">
					<option value="">[全部]</option>
					<fns:getOptions category="rf_recharge.recharge_type"/>
				</select> 
			</div>
			<div class="module_search_input">
				交易流水号：<input type="text" class="easyui-textbox input_width_default" name="tradeNo" data-options=""/>
			</div>
			<div class="module_search_input">
				操作时间：
				<input type="text" class="easyui-datebox input_width_default" editable="false" id="begin" name="begin" data-options=""/>
				至
				<input type="text" class="easyui-datebox input_width_default" editable="false" id="end" name="end" data-options=""/>
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
		<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a>
		<a href="javascript:void(0)" id="button-cancel" class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
</body>
</html>



