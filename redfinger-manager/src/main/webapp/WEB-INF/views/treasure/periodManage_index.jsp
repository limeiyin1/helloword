<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>分期夺宝</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="periodId";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj = {
			url : host + "list",
			idField : 'periodId',
			onCheck : function(row) {

			},
			columns:[[
						{width:100,title:'periodId',field:pk,checkbox:true},
						{width:100,title:'夺宝名称',field:'treasureName',sortable:true},
						{width:100,title:'总夺宝名称',field:'map.treasureName',sortable:true},
						{width:100,title:'当前期数',field:'currentPeriod',sortable:true},
						{width:100,title:'可购买次数',field:'treasureFrequency',sortable:true},
						{width:100,title:'剩余购买次数',field:'remainderFrequency',sortable:true},
						{width:100,title:'购买金额(分)',field:'treasureMoney',sortable:true},
						{width:100,title:'激活码类型',field:'map.codeType',sortable:false},
						{width:100,title:'激活码',field:'activationCode',sortable:true},
						{width:100,title:'未中奖赠送红币数',field:'redCoin',sortable:true},
						{width:100,title:'开奖状态',field:'periodStatus',sortable:false,formatter : function(value) {return getDatagridDict('treasure_period.period_status', value);}},
						{width:100,title:'备注',field:'remark',sortable:false},
						{width:100,title:'开始时间',field:'startTime',sortable:true,formatter:formatterTime},
						{width:100,title:'结束时间',field:'endTime',sortable:true,formatter:formatterTime},
						{width:100,title:'开奖时间',field:'lotteryTime',sortable:true,formatter:formatterTime},
						{width:100,title:'中奖号码',field:'map.lotteryNumber',sortable:false},
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
			<div class="module_search_input">夺宝名称：<input type="text" name="treasureName" class="easyui-textbox input_width_default"/></div>
			
			<div class="module_search_input">夺宝：
				<select id="sel" class="easyui-combobox" name="treasureId" editable="false">
						<option value="">全部</option>
					<c:forEach items="${treasures }" var="surs" varStatus="status">
						<option value="${surs.treasureId }">${surs.treasureName }</option>
					</c:forEach>
				</select> 
			</div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="add()">新增</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a> -->
	        <!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
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



