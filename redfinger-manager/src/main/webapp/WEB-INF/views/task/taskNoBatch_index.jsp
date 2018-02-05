<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>设备命令记录</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk = "taskId";
	var module_datagrid = "#module_datagrid";
	var module_dialog = "#module_dialog";
	var module_search_form = "#module_search_form"
	var module_submit_form = "#module_submit_form";
	var callback = defaultCallback;
	var dataGridParamObj = {
		url : host + "list",
		idField : 'taskId',
		onCheck : function(row) {

		},
		columns : [[ 
		              {width : 100,title : 'taskId',field : 'taskId',checkbox : true}, 
		              {width : 100,title : '命令',field : 'taskCommand',sortable : true}, 
		              {width : 100,title : '命令详细',field :'remark',sortable : true}, 
		              {width : 300,title : '任务结果信息',field : 'taskResultInfo',sortable : true}, 
		              {width : 100,title : '批次号',field : 'batchId',sortable : true}, 
		              {width : 100,title : '设备CODE',field : 'padCode',sortable : true,},
		              {width : 100,title : '命令类型',field : 'commandType',sortable : true,},
		              {width : 100,title : '任务结果状态',field : 'taskResultStatus',sortable : true,formatter:function(value){return getDatagridDict('rf_pad_task.task_result_status',value);}},
		              {width : 100,title : '任务状态',field : 'taskStatus',sortable : true,formatter:function(value){return getDatagridDict('rf_pad_task.task_status',value);}},
		              {width : 100,title : '创建时间',field : 'createTime',sortable : true,formatter:formatterTime}
		            
		          ]]
		};
	var dialogParamObj = {
			
	};
	$(function() {
		$(module_datagrid).datagrid($.extend({}, dataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
	
	});
	



</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container">
		<div id="module_search">
			<form id="module_search_form" class="easyui-form" method="post">
				<div class="module_search_input">
				      批次名：<input type="text" name="batchId" class="easyui-numberbox input_width_default" data-options="min:0,precision:0,max:2147483647" /> 
				</div>
				<div class="module_search_input">
				      创建人：<input type="text" name="creater" class="easyui-textbox input_width_default" /> 
				</div>
				<div class="module_search_input">
				    游戏名：<input type="text" name="gameName" class="easyui-textbox input_width_default" /> 
				</div>
				<%-- <div class="module_search_input">
				    命令类型：<select class="easyui-combobox input_width_short"  editable="false" name="commandType" data-options="required:false">
						    <option value="">[全部]</option>
							<fns:getOptions category="rf_padtask.batch"></fns:getOptions>		
					 	</select> 
				</div>  --%>
				<div class="module_search_input">
				    执行状态：<select class="easyui-combobox input_width_short"  editable="false" name="taskStatus" data-options="required:false">
						    <option value="">[全部]</option>
							<fns:getOptions category="rf_pad_task.task_status"></fns:getOptions>		
			
					 	</select> 
				</div> 
				 <div class="module_search_input">
					创建时间:
						<input type="text" class="easyui-datebox input_width_default" data-options="editable:false"  name="beginTimeStr" />
						至
						<input type="text" class="easyui-datebox input_width_default" data-options="editable:false" name="endTimeStr"/>
				 </div>  
				<div class="module_search_button">
					<a href="javascript:void(0)" class="easyui-linkbutton"data-options="iconCls:'icon-search-rf',plain:false" onclick="gridSearch()">搜索</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"data-options="iconCls:'icon-reset-rf',plain:false" onclick="gridReset()">清空</a>
				</div>
			</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
				<a href="javascript:void(0)" class="easyui-linkbutton"data-options="iconCls:'icon-reload-rf',plain:true" onclick="fresh()">刷新</a>
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>

	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
	<div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton" data-options="iconCls:'icon-ok-rf'">保存</a> 
		<a href="javascript:void(0)" id="button-cancel"class="easyui-linkbutton" data-options="iconCls:'icon-no'" onclick="cancel()">关闭</a>
	</div>
</body>
</html>



