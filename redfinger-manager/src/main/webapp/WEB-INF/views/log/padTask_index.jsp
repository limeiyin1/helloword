<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>设备任务日志</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="taskId";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField :'taskId',
		onCheck: function(row){
			
		},
		columns:[[
			{width:100,title:'taskId',field:pk,checkbox:true},
			{width:100,title:'批次名称',field:'nameT3',sortable:true},
			{width:100,title:'设备编码',field:'padCodeT2',sortable:true},
			{width:100,title:'任务状态',field:'taskStatus',sortable:true,formatter:function(value){return getDatagridDict('rf_pad_task.task_status',value);}},
			{width:100,title:'创建人',field:'creater',sortable:true},
			{width:150,title:'创建时间',field:'createTime',sortable:true,formatter:formatterTime},
			{width:100,title:'命令类型',field:'commandType',sortable:true},
			{width:200,title:'命令',field:'taskCommand',sortable:true},
			{width:100,title:'任务结果状态',field:'taskResultStatus',sortable:true,formatter:function(value){return getDatagridDict('log.task_result',value);}},
			{width:100,title:'任务结果信息',field:'taskResultInfo',sortable:true},
			{width:100,title:'启用状态',field:'enableStatus',sortable:true,formatter:formatterStop},
			{width:100,title:'备注',field:'remark',sortable:true}
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
 			$("#exportName").val('设备任务日志');
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
				批次名称：<input type="text" class="easyui-textbox input_width_default" id="nameT3" name="nameT3"/>
				设备编码：<input type="text" class="easyui-textbox input_width_default" id="padCodeT2" name="padCodeT2"/>
			</div>
			<div class="module_search_input">
		           任务状态：<select class="easyui-combobox input_width_short" editable="false" name="taskStatus" data-options="required:false">
		                <option value="">[全部]</option>
		                <fns:getOptions category="rf_pad_task.task_status"></fns:getOptions> 
		           </select>
		     </div>
		     
		     <div class="module_search_input">
			  任务结果状态：<select class="easyui-combobox input_width_short" editable="false" name="taskResultStatus" data-options="required:false">
		                <option value="">[全部]</option>
		               <!--   <option value="成功">成功</option>
		                  <option value="失败">失败</option> -->
		                  <fns:getOptions category="log.task_result"></fns:getOptions>
		           </select>
			</div>
					<div class="module_search_input">
		           命令类型：<select class="easyui-combobox input_width_short" editable="false" name="commandType" data-options="required:false" style="width:120px">
		                <option value="">[全部]</option>
		                <fns:getOptions category="rf_padtask.batch"></fns:getOptions> 
		           </select>
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
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel-rf" plain="true" onclick="statExport()">导出</a>
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



