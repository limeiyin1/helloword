<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>虚拟管理任务清单</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
var pk="vmTaskId";
var module_datagrid = "#module_datagrid";
var module_dialog = "#module_dialog";
var module_search_form = "#module_search_form"
var module_submit_form = "#module_submit_form";
var flag = "rf_vmTaskHis_index";
var totalCount = 0;
var currentUserId = "${currentUserId}";
var initColumnMap = {
	'vmTaskId':20,
	'taskSource':60,
	'map.deviceCode':95,
	'map.padCode':95,
	'map.manageControlName':60,
	'taskType':60,
	'taskStatus':60,
	'taskResultStatus':80,
	'taskResultInfo':80,
	'retryTimes':60,
	'retryInterval':60,
	'creater':60,
	'createTime':80,
	'modifier':60,
	'modifyTime':80,
};
var callback = defaultCallback ;
var dataGridParamObj = {
	url : host + "list",
	idField : 'vmTaskId',
	onCheck : function(row) {

	},
	columns : [ [   {width : 20,title : 'id',field : 'vmTaskId',checkbox : true}, 
	                {width : 60,title : '任务来源',field : 'taskSource',sortable:true},
					{width : 95,title : '物理设备编号',field : 'map.deviceCode'},
					{width : 95,title : '设备编号',field : 'map.padCode'},
					{width : 90,title : '设备IP',field : 'map.padIp'},
					{width : 90,title : '物理IP',field : 'map.deviceIp'},
					{width : 80,title : 'rom版本号',field : 'map.romVersion'},
					{width : 60,title : '节点', field : 'map.manageControlName'} ,
					{width : 60,title : '任务类型',field : 'taskType',sortable:true,formatter:function(value){return getDatagridDict('rf_vm_task.task_type',value);}},
					{width : 60,title : '任务状态',field : 'taskStatus',sortable:true,formatter:function(value){return getDatagridDict('rf_vm_task.task_status',value);}},
					{width : 80,title : '任务结果状态',field : 'taskResultStatus',sortable:true,formatter:function(value){return getDatagridDict('rf_vm_task.task_result_status',value);}},
					{width : 80,title : '任务结果信息',field : 'taskResultInfo',sortable:true},
			    	{width : 60,title : '重试次数',field : 'retryTimes',sortable:true},
					{width : 60,title : '间隔时间',field : 'retryInterval',sortable:true}, 
					{width : 60,title : '创建人',field : 'creater',sortable:true},
					{width : 80,title : '创建时间',field : 'createTime',sortable:true,formatter :formatterTime},
					{width : 60,title : '修改人',field : 'modifier',sortable:true},
					{width : 80,title : '修改时间',field : 'modifyTime',sortable:true,formatter :formatterTime}
	] ],
	onLoadSuccess:function(data){
			if(data){
				var total = data.total;
				if(total > 5000){
					totalCount = total;
					$("#export-link").unbind("click").click(asyncExportForm);
				}else{
					$("#export-link").unbind("click").click(statExport);
				}
				
			}
		},
	onResizeColumn:function(field, width){
		resizeColumn(currentUserId,flag);
	},
	};
	var dialogParamObj = {

	};
	
	$(function() {
		$("#module_column_defined_dialog").dialog($.extend({}, dialogParam, dialogParamObj));
		$(module_datagrid).datagrid($.extend({}, dataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
		loadColumnDefined(currentUserId,initColumnMap,pk,flag);
	});
	
	function exportCancel() {
		$(module_dialog).dialog('resize',{width:500,height:500});
		$(module_dialog).dialog("close");
	}
	
	function asyncExportForm(){
		var module_submit_container=$('<div id="module_submit_container"></div>'); 
		var form=$('<form id="module_submit_form" class="easyui-form">');
		var easyui_table=$('<table class="easyui-table" id="module_submit_table"></table>');
		easyui_table.appendTo(form);
		form.appendTo(module_submit_container);
		var tr=$('<tr><td class="td1">导出任务名:</td><td class="td2"><input class="easyui-textbox" type="text" id="exportTaskName" name="taskName"  data-options="required:true,validType:\'length[1,32]\'" /></td></tr>');
		easyui_table.append(tr);
		tr=$('<tr><td class="td1" colspan="2">注：导出操作为异步队列操作，结果请于【批处理】下的【导出管理】下载查看</td></tr>');
		easyui_table.append(tr);
		var title = $("title").html() + " - 异步导出";
		$("#button-save").unbind("click").click(asyncExportData);
		$("#button-cancel").unbind("click").click(exportCancel);
		$(module_dialog).dialog({height:200,title : title,content:$(module_submit_container).prop("outerHTML"),href:''});
		// 键盘事件监听, 当用户输入非法字符, 清除输入的非法字符
		$("#exportTaskName").textbox({
			inputEvents: $.extend({},$.fn.textbox.defaults.inputEvents,{
				keyup: function(event){
					var text = $(this).val();
					$('#exportTaskName').textbox('setValue', text.replace(/[^a-zA-Z0-9\u4e00-\u9fa5\-_]/g,'')) ;
				}})
		});
		$(module_dialog).dialog("open");
	}

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
			$("#exportName").val('虚拟任务清单');
			$("#exportForm").submit();
		} else {
			$.messager.alert('操作失败', '无数据！', "warning");
		}
	}
	
	function asyncExportData(){
		var exportTaskName =  $("#exportTaskName").val();
		if(exportTaskName == ''){
			$.messager.alert('操作失败','导出名称不能为空！',"warning");
			return;
		}
		
		var isValid = $(module_submit_form).form('validate');
		if(!isValid){
			$(module_submit_form).form('enableValidation').form('validate');
			return;
		}
		
		var resArr = new Array();
		
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
			console.log(params);
			// 清除历史搜索条件
			$(".exportParams").remove();
			for(var i in params){
				if(params[i]){
					resArr.push("\"" + i +"\":" + "\"" +params[i]+ "\"");
				}
			}
			//resArr.push("\"exportTaskName\":" + "\"" +exportTaskName+ "\"");
			var json = "{" + resArr.join(",") + "}";
			//console.log(json.toString());
			$("#exportForm").append('<input type="hidden" name="exportTaskName" class="exportParams"  value="'+exportTaskName+'"/>');
			$("#exportForm").append('<input type="hidden" name="queryParams" class="exportParams"  value="'+encodeURI(json.toString())+'"/>');
			$("#exportForm").attr("action",host+'asyncExport');
			$("#exportForm").attr("target",'_self');
			$("#exportHead").val(exportHead);
			$("#exportField").val(exportField);
			$("#totalCount").val(totalCount);
			$("#exportName").val('虚拟任务清单');
			
			$('#exportForm').form('submit', {    
			    success: function(data){  
			    	$(module_dialog).dialog('resize',{width:500,height:500});
			    	innerCallback(data, function(){
						$.messager.alert('提示', '成功生成导出任务，请到批处理下的导出管理处下载!', "info");
					}); 
			    }    
			});  

			
			
		}else{
			$.messager.alert('操作失败','无数据！',"warning");
		}
		
	}
	
	$.extend($.fn.validatebox.defaults.rules,{
		exportTask:{
          validator : function(value) {
              return /^[a-zA-Z0-9\u4e00-\u9fa5\-_]{1,32}$/.test(value);
          },
          message : '请确认你输入导出任务名中是否包含非法字符'
        }
	});
	
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container">
		<div id="module_search">
			<form id="module_search_form" class="easyui-form" method="post">
				 <div class="module_search_input">
					创建时间：<input type="text" name="beginTimeStr" class="easyui-datetimebox" data-options="editable:false,showSeconds:true"/>
					至<input type="text"  name="endTimeStr" class="easyui-datetimebox"data-options="editable:false,showSeconds:true" />
				 </div>
			     <div class="module_search_input">创建人：<input type="text" name="creater"class="easyui-textbox input_width_default" /></div>
			     <div class="module_search_input">物理设备编号：<input type="text" name="deviceCode" class="easyui-textbox input_width_default"/></div>
			     <div class="module_search_input">设备编号：<input type="text" name="padCode" class="easyui-textbox input_width_default"/></div>
			     <div class="module_search_input">任务来源：<input type="text" name="taskSource" class="easyui-textbox input_width_default"/></div>
			     <div class="module_search_input">任务类型：		
					<select class="easyui-combobox"  editable="false" name="taskType" data-options="required:true">
						    <option value="">[全部]</option>
							<fns:getOptions category="rf_vm_task.task_type" ></fns:getOptions> 
					</select> 
				 </div>
				 <div class="module_search_input">结果状态：		
					<select class="easyui-combobox"  editable="false" name="taskResultStatus" data-options="required:true">
						    <option value="">[全部]</option>
							<fns:getOptions category="rf_vm_task.task_result_status" ></fns:getOptions> 
					</select> 
				</div>
				 <div class="module_search_input">任务状态：		
					<select class="easyui-combobox"  editable="false" name="taskStatus" data-options="required:true">
						    <option value="">[全部]</option>
							<fns:getOptions category="rf_vm_task.task_status" ></fns:getOptions> 
					</select> 
				</div>
				<div class="module_search_input">
					任务结果信息：<input type="text" name="taskResultInfo"class="easyui-textbox input_width_default" />
				</div>
				<div class="module_search_button">
					<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
				</div>
			</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" id="export-link" data-options="iconCls:'icon-excel-rf',plain:true">导出</a>
		<!-- <a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-add-rf" plain="true" onclick="add()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a> -->
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit-rf',plain:true" onclick="columnDefined(currentUserId,initColumnMap,pk,flag)">列表自定义</a>
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>

	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
	<div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save"class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a> 
		<a href="javascript:void(0)" id="button-cancel"class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
	<!-- 列表自定义编辑框 -->
	<div id="module_column_defined_dialog" buttons="#module_column_defined_dialog_button"></div>
	<div style="text-align:center" id="module_column_defined_dialog_button">
		<a href="javascript:void(0)" id="buttone-column-defined-save-select-all"class="easyui-linkbutton" iconCls="icon-edit-rf" onclick="selectAllColumn()">全选</a>
	    <a href="javascript:void(0)" id="buttone-column-defined-save"class="easyui-linkbutton" iconCls="icon-ok-rf">确定</a>
	    <a href="javascript:void(0)" id="button-column-defined-cancel"class="easyui-linkbutton" iconCls="icon-reload-rf" onclick="resetColumn(currentUserId,initColumnMap,pk,flag)">重置</a>
	</div>
	<!-- 列表自定义编辑框  end-->
	<!--导出  -->
	<div class="hide">
	<form action="" id="exportForm" method="post" target="_blank">
		<input type="hidden" name="exportDatas" id="exportDatas" value=""/>
		<input type="hidden" name="exportHead" id="exportHead" value=""/>
		<input type="hidden" name="exportField" id="exportField" value=""/>
		<input type="hidden" name="exportName" id="exportName" value=""/>
		<input type="hidden" name="totalCount" id="totalCount" value=""/>
	</form>
	</div>
</body>
</html>



