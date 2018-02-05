<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>电子钱包交易记录</title>
<meta name="decorator" content="moduleIndex" />
<style type="text/css">
.tableBorder {
	position: relative;
	border: 1px solid #95B8E7;
	background: white;
	vertical-align: middle;
	display: inline-block;
	margin: 0;
	padding: 0;
	-moz-border-radius: 5px 5px 5px 5px;
	-webkit-border-radius: 5px 5px 5px 5px;
	border-radius: 5px 5px 5px 5px;
	font-size: 13px;
}
</style>
<script type="text/javascript"
	src="${ctxStatic}/jquery-easyui-1.4.1/datagrid-recordview.js"></script>
<script type="text/javascript">
	var pk = "walletRecordId";
	var module_datagrid = "#module_datagrid";
	var module_dialog = "#module_dialog";
	var module_search_form = "#module_search_form"
	var module_submit_form = "#module_submit_form";
	var callback = defaultCallback;

	var dataGridParamObj = {
		url : host + "list",
		idField : 'walletRecordId',
		singleSelect: false,
		checkOnSelect: true, 
		selectOnCheck: true,
		onCheck : function(row) {

		},
		columns : [[ 
              {width : 100,title : 'id',field : 'walletRecordId',checkbox : true}, 
              {width : 100,title : 'recordNumberCount',field : 'recordNumberCount'},
              {width : 100,title : '会员ID',field : 'map.externalUserId',sortable : false},
              {width : 100,title : '用户号码',field : 'map.userMobilePhone',sortable : false},
              {width : 100,title : '钱包交易类型',field : 'recordType',sortable:false,formatter : function(value) {return getDatagridDict('rf_wallet_record.record_type', value);}}, 
              {width : 100,title : '总变更金额(元)',field : 'changeAmount',sortable : true, formatter:function(value){var money = 0;if(value){money=value/100;} return money.toFixed(2);}},
              {width : 100,title : '现金账户变更金额(元)',field : 'map.cashChangeAmount', formatter:function(value){var money = 0;if(value){money=value/100;} return money.toFixed(2);}},
              {width : 100,title : '赠款账户变更金额(元)',field : 'map.giveChangeAmount', formatter:function(value){var money = 0;if(value){money=value/100;} return money.toFixed(2);}},
              {width : 100,title : '创建时间',field : 'createTime',sortable : true,formatter:formatterTime},
              {width : 100,title : '状态',field : 'enableStatus',sortable : true,formatter: formatterStop},
          	]],
		};
	var dialogParamObj = {
			
	};
	
	$(function() {
		$(module_datagrid).datagrid($.extend({}, dataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
		$(module_datagrid).datagrid('hideColumn', 'recordNumberCount');
		$.extend($.fn.validatebox.defaults.rules,{
			number:{// 验证数字
	          validator : function(value) {
	              return /^[0-9]{1,9}$/gi.test(value);
	          },
	          message : '只允许1-9位的正整数'
	        }
		});
	});
	//grid搜索
	var gridSearchValidate = function() {
		var arr = $(module_search_form).serializeArray();
		var obj = {};
		$.each(arr, function(i, field) {
			obj[field.name] = field.value;
		});
		if(!($('#module_search_form').form("validate"))){
			return false;
			}
		try{
			$(module_datagrid).treegrid("reload",obj);
		}catch(e){
			$(module_datagrid).datagrid("reload",obj);
		}
	}
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container">
		<div id="module_search">
			<form id="module_search_form" class="easyui-form" method="post">
				<div class="module_search_input">用户号码：<input type="text" name="userMobilePhone" class="easyui-textbox input_width_default"/></div>
				<div class="module_search_input">
					交易类型：
					<select class="easyui-combobox input_width_default" editable="false" name="recordType">
						<option value="">[全部]</option>
						<fns:getOptions category="rf_wallet_record.record_type"/>
					</select>
				</div>
				<div class="module_search_input">
					会员ID：<input type="text" name="externalUserId" data-options="validType:'number'"" class="easyui-textbox input_width_default"/>
			    </div>
				<div class="module_search_button">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearchValidate()">搜索</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
				</div>
			</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="addTask()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="editTask()">编辑</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a> -->
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
