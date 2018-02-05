<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>邀请码日志</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="recordId";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField :'generalizeId',
		onCheck: function(row){	
		},
		columns:[[
			{width:100,title:'recordId',field:pk,checkbox:true},
			{width:70,title:'被邀请ID',field:'map.inviteeExternalUserId'},
			{width:70,title:'邀请人ID',field:'map.externalUserId'},
			{width:100,title:'被邀请用户',field:'map.inviteeMobilePhone'},
			{width:100,title:'邀请人',field:'map.userMobilePhone'},
			{width:100,title:'邀请状态',field:'recordStatus',sortable:true,formatter:function(val, row) {if(val=='1'){
				return "已邀请";}else if(val=='3'){return "已奖励";}}},
			{width:100,title:'红豆奖励',field:'rbcAward',sortable:true},
			{width:100,title:'邀请码',field:'inviteCode',sortable:true},
			{width:150,title:'创建时间',field:'createTime',sortable:true,formatter:formatterTime},
			{width:150,title:'邀请码到期时间',field:'map.taskEndTime',formatter:formatterTime},
			{width:100,title:'状态',field:'enableStatus',sortable:true,formatter:formatterStop}
		]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	
	function statExport(){
 		var pager=$(module_datagrid).datagrid("getPager");
 		var total=$(pager).pagination('options').total;
 		if(total){
 			var cols=dataGridParamObj.columns[0];
 			var exportHead="";
 			var exportField="";
 			for(var i in cols){
 				if(i!=0){
 					exportHead=exportHead+cols[i].title+",";
 					exportField=exportField+cols[i].field+",";
 				}
 			}
 			var where="";
 			var params=$(module_datagrid).datagrid('options').queryParams;
 			for(var i in params){
 				where+=i+"="+params[i]+"&";
 			}
 			$("#exportForm").attr("action",host+'export?'+where);
 			$("#exportHead").val(exportHead);
 			$("#exportField").val(exportField);
 			$("#exportName").val('邀请码日志');
 			$("#exportForm").submit();
 		}else{
 			$.messager.alert('操作失败','无数据！',"warning");
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
				被邀请人ID：<input type="text" class="easyui-numberbox input_width_short" id="inviteeExternalUserId" name="inviteeExternalUserId"/>
			</div>
			<div class="module_search_input">
				邀请人ID：<input type="text" class="easyui-numberbox input_width_short" id="externalUserId" name="externalUserId"/>
			</div>
			<div class="module_search_input">
				被邀请人号码：<input type="text" class="easyui-textbox input_width_default" id="inviteeMobilePhone" name="inviteeMobilePhone"/>
			</div>
			<div class="module_search_input">
				邀请人号码：<input type="text" class="easyui-textbox input_width_default" id="userMobilePhone" name="userMobilePhone"/>
			</div>
			<div class="module_search_input">
				邀请码：<input type="text" class="easyui-textbox input_width_default" id="inviteCode" name="inviteCode"/>
			</div>
			<div class="module_search_input">
				邀请状态：
				<select class="easyui-combobox input_width_short" editable="false" id="recordStatus" name="recordStatus" >
					<option value="">[全部]</option>
					<option value="1">已邀请</option>
					<option value="3">已奖励</option>
			 	</select>
			</div>
			<div class="module_search_input">
				创建时间：
				<input type="text" class="easyui-datebox input_width_default" editable="false" id="beginTimeStr" name="beginTimeStr" data-options=""/>
				至
				<input type="text" class="easyui-datebox input_width_default" editable="false" id="endTimeStr" name="endTimeStr" data-options=""/>
			</div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel-rf" plain="true" onclick="statExport()">导出</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>
	
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



