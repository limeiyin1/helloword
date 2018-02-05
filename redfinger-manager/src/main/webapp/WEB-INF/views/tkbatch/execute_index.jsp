<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>批次处理</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="executeId";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form"; 
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField :pk,
		singleSelect:true,
		onCheck: function(row){
			
		},
		columns:[[
			/*{width:100,title:'executeId',field:pk ,checkbox:true}, */
			{width:90,title:'批处理标题',field:'map.batchTitle',sortable:false},
			{width:90,title:'处理类型',field:'operateType',sortable:true,formatter:function(value){return getDatagridDict('tk_batch_task.operate_type',value);}},
		    {width:100,title:'用户号码',field:'map.userMobilePhone',sortable:false},
		    {width:100,title:'设备编码',field:'padCode',sortable:true},
			{width:100,title:'开始时间',field:'startTime',sortable:true,formatter:formatterTime},
			{width:80,title:'结束时间',field:'endTime',sortable:true,formatter:formatterTime}, 
			{width:80,title:'执行状态',field:'executeStatus',sortable:true,formatter:function(value){return getDatagridDict('tk_batch_execute.execute_status',value);}},
            {width:100,title:'执行时间',field : 'executeTime',sortable:true,formatter:formatterTime},
			{width:50,title:'状态',field : 'enableStatus',sortable:true,formatter:formatterStop},
            {width:100,title:'创建时间',field : 'createTime',sortable:true,formatter:formatterTime},
            {width:100,title:'执行结果',field : 'executeResult',sortable:true},
            {width:100,title:'备注',field : 'remark'},
		]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	
	function statExport() {
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
			$("#exportName").val('任务批次列表');
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
			   批处理任务：<input type="text" name="batchTitle" class="easyui-textbox input_width_default" />
			</div>
			<div class="module_search_input">
			   操作类型：
			   	<select class="easyui-combobox input_width_short" editable="false" name="operateType" data-options="required:false" style="width:120px;">
					<option value="">[全部]</option>
					<fns:getOptions category="tk_batch_task.operate_type"></fns:getOptions>
			 	</select> 
			</div>
			<div class="module_search_input">
			   执行状态：<select class="easyui-combobox input_width_short" editable="false" name="executeStatus" data-options="required:false">
						<option value="">[全部]</option>
					   <fns:getOptions category="tk_batch_execute.execute_status"  ></fns:getOptions>
				 	</select> 
			</div>
			<div class="module_search_input">
			    用户号码：<input type="text" name="userMobilePhone" class="easyui-textbox input_width_default" /> 
			</div>
			<div class="module_search_input">
			    设备编码：<input type="text" name="padCode" class="easyui-textbox input_width_default" /> 
			</div>
			
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel-rf" plain="true" onclick="statExport()">导出</a> -->
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



