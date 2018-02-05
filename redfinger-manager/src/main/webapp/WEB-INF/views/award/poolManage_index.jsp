<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>抽奖池管理</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk = "id";
	var module_datagrid = "#module_datagrid";
	var module_dialog = "#module_dialog";
	var module_search_form = "#module_search_form";
	var module_submit_form = "#module_submit_form";
	var callback = defaultCallback;
	var dataGridParamObj = {
		url : host + "list",
		idField : pk,
		onCheck : function(row) {
		},
		columns : [ [
				{
					width : 100,
					title : 'id',
					field : pk,
					checkbox : true
				},
				{
					width : 100,
					title : '抽奖池类型',
					field : 'map.awardType',
					sortable : true,
					formatter : function(value) {
						return getDatagridDict('rf_award_batch.award_type',
								value);
					}
				},
				{
					width : 100,
					title : '奖品级别',
					field : 'map.awardGrade',
					sortable : true,
					formatter : function(value) {
						return getDatagridDict('rf_award_batch.award_grade',
								value);
					}
				},
				{
					width : 100,
					title : '奖品名称',
					field : 'map.awardName',
					sortable : true
				},
				{
					width : 80,
					title : '奖品总量',
					field : 'awardTotal',
					sortable : true
				},
				{
					width : 80,
					title : '奖品余量',
					field : 'awardMargin',
					sortable : true
				},
				{
					width : 120,
					title : '中奖率（/10000）',
					field : 'winningRate',
					sortable : true
				},
				{
					width : 100,
					title : '奖池编码',
					field : 'awardCode',
					sortable : true
				},
				{
					width : 80,
					title : '发放状态',
					field : 'giveStatus',
					sortable : true,
					formatter : function(value) {
						return getDatagridDict(
								'rf_award_batch.award_give_status', value);
					}
				},
				/* {
					width : 80,
					title : '领取状态',
					field : 'receiveStatus',
					sortable : true,
					formatter : function(value) {
						return getDatagridDict(
								'rf_award_batch.award_receive_status', value);
					}
				},  */{
					width : 150,
					title : '更新时间',
					field : 'replaceTime',
					sortable : true,
					formatter : formatterTime
				}, {
					width : 50,
					title : '创建人',
					field : 'creater',
					sortable : true
				}, {
					width : 150,
					title : '创建时间',
					field : 'createTime',
					sortable : true,
					formatter : formatterTime
				}, {
					width : 50,
					title : '修改人',
					field : 'modifier',
					sortable : true
				}, {
					width : 150,
					title : '修改时间',
					field : 'modifyTime',
					sortable : true,
					formatter : formatterTime
				}, {
					width : 80,
					title : '状态',
					field : 'status',
					sortable : true,
					formatter : formatterStop
				}, {
					width : 80,
					title : '启用状态',
					field : 'enableStatus',
					sortable : true,
					formatter : formatterStop
				}, {
					width : 100,
					title : '备注',
					field : 'remark',
					sortable : true
				} ] ]
	};
	var dialogParamObj = {

	};
	$(function() {
		$(module_datagrid).datagrid(
				$.extend({}, dataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
/* 		$(document).bind("keydown","+",function(){
			$.messager.progress();
			getFloatData();
			$('#floatData').window('open');
			return false;
		}).bind("keydown","pagedown",function(){
			$.messager.progress();
			getFloatData();
			$('#floatData').window('open');
			return false;
		}).bind("keydown","-",function(){
			$('#floatData').window('close');
			return false;
		}).bind("keydown","pageup",function(){
			$('#floatData').window('close');
			return false;
		}); */
	});
/* 	function getFloatData(){
		var url = host + 'floatData';
		$.getJSON(url, function(json){
			for(var i in json){
				$("#"+i).html(json[i]);
			}
			$.messager.progress('close');
		});
	} */
	// 批量注奖
	var addBatch = function() {
		var title = "奖池 - 批量注奖";
		var href = host + 'addBatch';
		$("#button-save").unbind("click").click(saveBatch);
		openBatchForm(title, href);
	}
	//打开编辑对话框
	var openBatchForm = function(title, href) {
		$(module_dialog).dialog({
			title : title,
			href : href,
			width : 600
		});
		$(module_dialog).dialog("open");
	}
	var saveBatch = function() {
		if ($(module_submit_form).form("validate")) {
			$.messager.progress();
			$(module_submit_form).form({
				url : host + 'saveBatch'
			});
			$(module_submit_form).form("submit");
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
				奖品池:<select
							class="easyui-combobox input_width_short" editable="false"
							name="awardType" data-options="required:false">
							<option value="">[全部]</option>
							<fns:getOptions category="rf_award_batch.award_type"
								></fns:getOptions>
						</select>
				</div>
				<div class="module_search_input">
					奖品级别：<select class="easyui-combobox input_width_short"
						editable="false" name="awardGrade" data-options="required:false">
						<option value="">[全部]</option>
						<fns:getOptions category="rf_award_batch.award_grade"></fns:getOptions>
					</select>
				</div>
				<div class="module_search_input">
					发放状态：<select class="easyui-combobox input_width_short"
						editable="false" name="giveStatus" data-options="required:false">
						<option value="">[全部]</option>
						<fns:getOptions category="rf_award_batch.award_give_status"></fns:getOptions>
					</select>
				</div>
			    <div class="module_search_input">
			                奖池编码：<input type="text" name="awardCode" class="easyui-textbox input_width_default" />
			    </div>
<%-- 				<div class="module_search_input">
					领取状态：<select class="easyui-combobox input_width_short"
						editable="false" name="receiveStatus"
						data-options="required:false">
						<option value="">[全部]</option>
						<fns:getOptions category="rf_award_batch.award_receive_status"></fns:getOptions>
					</select>
				</div> --%>
				<div class="module_search_input">
					创建时间： <input type="text" class="easyui-datebox input_width_default"
						editable="false" id="begin" name="begin" data-options="" /> 至 <input
						type="text" class="easyui-datebox input_width_default"
						editable="false" id="end" name="end" data-options="" />
				</div>
				<div class="module_search_button">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
						<a href="#" title="搜索：enter<br/>清空：delete<br/>打开/刷新汇总：+/pagedown<br/>关闭汇总：-/pageup" class="easyui-tooltip">快捷键</a>
				</div>
			</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-addBatch-rf" plain="true" onclick="addBatch()">批量注奖</a>
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>
	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
	<div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save"
			class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a> <a
			href="javascript:void(0)" id="button-cancel"
			class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
		<div id="floatData" class="easyui-window" title="订单数据汇总" data-options="iconCls:'icon-save',modal:false,minimizable:false,maximizable:false,resizable:false,closed:true">
        <div>当日成功订单：&nbsp;&nbsp;<span class='red' id="floatExpire">笔-</span>(总数)&nbsp;,&nbsp;<span class='red' id="floatExpire2">-</span>(当日总金额)&nbsp;,&nbsp;</div>   
        <div>充值成功订单：&nbsp;&nbsp;<span class='red' id="floatTotal">笔-</span>(总数)&nbsp;,&nbsp;<span class='red' id="floatTotal2">-</span>(总金额)&nbsp;,&nbsp;</div>  
        <div>购买成功订单：&nbsp;&nbsp;<span class='red' id="floatFault">笔-</span>(总数)&nbsp;,&nbsp;<span class='red' id="floatFault2">-</span>(总金额)&nbsp;,&nbsp;</div>  
        <div>当前成功订单：&nbsp;&nbsp;<span class='red' id="floatEnable">笔-</span>(总数)&nbsp;,&nbsp;<span class='red' id="floatEnable2">-</span>(总金额)&nbsp;,&nbsp;</div>
	</div>
</body>
</html>