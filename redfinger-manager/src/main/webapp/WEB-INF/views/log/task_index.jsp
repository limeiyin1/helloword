<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>任务日志</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk = "logId";
	var module_datagrid = "#module_datagrid";
	var module_dialog = "#module_dialog";
	var module_search_form = "#module_search_form";
	var module_submit_form = "#module_submit_form";
	var callback = defaultCallback;
	var dataGridParamObj = {
		url : host + "list",
		idField : 'logId',
		onCheck : function(row) {

		},
		columns : [[
		{width : 100,title : 'logId',field : pk,checkbox : true}, 
		{width : 100,title : '会员ID',field : 'map.externalUserId'},
		{width : 100,title : '创建时间',field : 'createTime',formatter : formatterTime,sortable:true},
		{width : 100,title : '会员名称',field : 'map.userName'},
		{width : 100,title : '会员手机',field : 'map.userMobilePhone'}, 
		{width : 100,title : '设备名称',field : 'map.padName'}, 
		{width : 100,title : '任务名称',field : 'map.taskName'}, 
		{width : 100,title : '任务编码',field : 'taskCode',sortable:true}, {width : 100,title : '设备编码',field : 'padCode',sortable:true}, 
		{width : 100,title : '礼包名称',field : 'map.commodity'}, 
		{width : 100,title : '礼包编码',field : 'packageCode',sortable:true}, 
		{width : 100,title : '操作类型',field : 'logType',formatter : formatterLogType,sortable:true}, 
		{width : 100,title : '红豆数量',field : 'rbcAmount',sortable:true}, 
		{width : 100,title : '状态',field : 'status',formatter : formatterStop,sortable:true}, 
		{width : 100,title : '启用状态',field : 'enableStatus',sortable : true,formatter : formatterStop,sortable:true}
		]]
	};
	var dialogParamObj = {

	};
	$(function() {
		$(module_datagrid).datagrid(
				$.extend({}, dataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
		$.extend($.fn.validatebox.defaults.rules,{
			number:{// 验证数字
	          validator : function(value) {
	              return /^[0-9]{1,9}$/gi.test(value);
	          },
	          message : '只允许1-9位的正整数'
	        }
		});
	});
	function formatterLogType(value, row, index) {
		return getDatagridDict('rf_rbc_log.log_type',value)
	}
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

				<div class="module_search_input">
					会员ID：<input type="text" name="externalUserId" data-options="validType:'number'"" class="easyui-numberbox input_width_short"/>
			    </div>
				<div class="module_search_input">
					会员手机：<input type="text" name="userMobilePhone" class="easyui-textbox input_width_default" /> 
					任务编码：<input type="text" name="taskCode" class="easyui-textbox input_width_default" /> 
					设备编码：<input type="text" name="padCode" class="easyui-textbox input_width_default" /> 
					操作类型：<select class="easyui-combobox input_width_default" editable="false" name="logType">
								<option value="">[全部]</option>
								<fns:getOptions category="rf_rbc_log.log_type"/>
							</select> 
					
					操作时间： <input type="text" class="easyui-datebox input_width_default"
						editable="false" id="begin" name="begin" data-options="" /> 至 <input
						type="text" class="easyui-datebox input_width_default"
						editable="false" id="end" name="end" data-options="" />
				</div>
				
				<div class="module_search_button">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-search-rf" plain="false" onclick="gridSearchValidate()">搜索</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
				</div>
			</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
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



