<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>虚拟管理操作</title>
<meta name="decorator" content="default" />
</head>
<body>
	<script type="text/javascript">

	$('.easyui-form').form({
		 onSubmit: function(){ 
				  var row = $(module_datagrid_renewal).datagrid("getSelections");
						var ids = [];
						for (var i = 0; i < row.length; i++) {
							ids[i] = row[i]["padId"]
						}
						var padIds=ids.join(",");
					$("#padIds").val(padIds);
			    }, 
		    url:host+'taskOnly',   
		    success:callback
		}); 

		function doSearch(){
			$('#module_datagrid_renewal').datagrid('load',{
				padCode: $('#itemid').val(),
				padIp: $('#twoid').val(),
				deviceId:$('#deviceid').val()
			});
		}	

	var module_datagrid_renewal = "#module_datagrid_renewal";
	var callback = defaultCallback ;
	var deviceId=${bean.deviceId};
	var dataGridParamObj = {
		    url : host + "padlist",
			idField : 'padId',
			queryParams: { 'deviceId': deviceId },
			onCheck : function(index,row) {
	
			},
			columns : [ [
			         	{width : 50,title : 'id',field :'padId',checkbox : true}, 
			         	{width : 150,title : '设备名称',field:'padName',sortable : true},
			         	{width : 150,title : '设备编码',field:'padCode',sortable:true},
			         	{width : 40,title : '虚拟SN',field:'padSn',sortable:true},
			        	{width : 100,title : '物理设备编号',field:'map.deviceCode'},
			         /* 	{width : 100,title : '设备控制端口',field:'padControlPort',sortable:true}, */
			          	{width : 100,title : '设备IP',field:'padIp',sortable:true},
			          	{width : 60,title : 'MAC',field:'vmMac',sortable:true},
			        	{width : 60, title : '授权状态',field:'grantOpenStatus',sortable:true ,formatter:function(value){return getDatagridDict('rf_pad.grant_open_status',value);}},
			          	{width : 60, title : '虚拟状态',field:'vmStatus',sortable:true ,formatter:function(value){return getDatagridDict('rf_pad.vm_status',value);}},
			          	{width : 60,title : '绑定状态',field : 'bindStatus',sortable : true,formatter:function(value){return getDatagridDict('rf_pad.bind_status',value);}},
			         	{width : 60,title : '启用状态',field : 'enableStatus',sortable : true,formatter:function(value){return getDatagridDict('rf_pad.enable_status',value);}},
			         	{width : 60,title : '受控状态',field : 'padStatus',sortable : true,formatter:function(value){return getDatagridDict('rf_pad.pad_status',value);}},
			         	{width : 60,title : '故障状态',field : 'faultStatus',sortable : true,formatter:function(value){return getDatagridDict('rf_pad.fault_status',value);}}
			          ] ]
		};
		var dialogParamObj = {

		};
		$(function() {
			$(module_datagrid_renewal).datagrid($.extend({}, dataGridParam, dataGridParamObj));
		});

		$("#xbutton-save").unbind("click").click(execute);
		   function execute(){
			  var row = $(module_datagrid_renewal).datagrid("getSelections");
			 /*通过后台判断配置信息确认可以绑定的台数，不再用前段控制 
			 if( row.length>4){
				  $.messager.alert('提示', '不能选择大于4台设备！', "error");
					return false;
			  } */
			  if (row == '') {
					$.messager.alert('提示', '请选择记录！', "error");
					$('#module_datagrid_renewal').datagrid("clearSelections");
					return false;
				}	
				if($('#module_submit_form_execute').form("validate")){
					$.messager.progress();
					$('#module_submit_form_execute').form("submit");
				}else{
					alert("操作错误");
				}
		$('#module_datagrid_renewal').datagrid("clearSelections");
			//分别加载虚拟任务列表和虚拟任务清单列表
				var xdataGridParamObj = {
					    url : host + "vmlist",
						idField : 'vmTaskId',
						queryParams: { 'deviceId': deviceId },
						onCheck : function(index,row) {
				
						},
						columns : [ [
									{width : 100,title : 'id',field : 'vmTaskId',checkbox : true},
							        {width : 100,title : '任务来源',field : 'taskSource',sortable:true},
									{width : 100,title : '物理设备编号',field : 'map.deviceCode'},
									{width : 100,title : '设备编号',field : 'map.padCode'},
									{width : 100,title : '节点', field : 'map.manageControlName'} ,
									{width : 100,title : '任务类型',field : 'taskType',sortable:true,formatter:function(value){return getDatagridDict('rf_vm_task.task_type',value);}},
									{width : 100,title : '任务状态',field : 'taskStatus',sortable:true},
									{width : 100,title : '任务结果状态',field : 'taskResultStatus',sortable:true},
									{width : 100,title : '任务结果信息',field : 'taskResultInfo',sortable:true},
									{width : 100,title : '创建时间',field : 'createTime',sortable:true,formatter :formatterTime},
						          ] ]
					};
					var xdialogParamObj = {

					};

					var ydataGridParamObj = {
						    url : host + "vmHislist",
							idField : 'vmTaskId',
							queryParams: { 'deviceId': deviceId },
							onCheck : function(index,row) {
					
							},
							columns : [ [
										{width : 100,title : 'id',field : 'vmTaskId',checkbox : true}, 
										{width : 100,title : '任务来源',field : 'taskSource',sortable:true},
										{width : 100,title : '物理设备编号',field : 'map.deviceCode'},
										{width : 100,title : '设备编号',field : 'map.padCode'},
										{width : 100,title : '节点', field : 'map.manageControlName'} ,
										{width : 100,title : '任务类型',field : 'taskType',sortable:true,formatter:function(value){return getDatagridDict('rf_vm_task.task_type',value);}},
										{width : 100,title : '任务状态',field : 'taskStatus',sortable:true},
										{width : 100,title : '任务结果状态',field : 'taskResultStatus',sortable:true},
										{width : 100,title : '任务结果信息',field : 'taskResultInfo',sortable:true},
										{width : 100,title : '创建时间',field : 'createTime',sortable:true,formatter :formatterTime},
							          ] ]
						};
						var xdialogParamObj = {

						};
					
					$(function() {
						$(xmodule_datagrid_renewal).datagrid($.extend({}, dataGridParam, xdataGridParamObj));
						$(ymodule_datagrid_renewal).datagrid($.extend({}, dataGridParam, ydataGridParamObj));
					});
			}
	</script>
 
	<div id="module_submit_container">
	<div id="module_search" >
		<form id="module_submit_form_execute" class="easyui-form" method="post">
		<input type="hidden" name=deviceId value="${bean.deviceId}" />
		<input type="hidden" name="ids" id="padIds"  />
		 <table id="module_submit_table" >
				<tr>
					<td class="td1"> 设备编号:</td>
					<td class="td2">${bean.deviceName}</td>
				</tr>
				<tr>
					<td class="td1"> 设备IP:</td>
					<td class="td2">${bean.deviceIp}</td>
				</tr>
				<tr>
					<td class="td1"> 执行操作:</td>
					<td class="td2"><select  style="width: 100px" class="easyui-combobox" name="taskType" data-options="required:true,editable:false">
				<fns:getOptions category="rf_vm_task.task_type" keys="rf_vm_task.task_type@start,rf_vm_task.task_type@stop,rf_vm_task.task_type@restart,rf_vm_task.task_type@resetVM,rf_vm_task.task_type@restartRemotePlay,rf_vm_task.task_type@recovery,rf_vm_task.task_type@pad_screencap"></fns:getOptions>
				</select> 
				<a href="javascript:void(0)" id="xbutton-save" class="easyui-linkbutton" data-options="iconCls:'icon-ok-rf'" >执行</a> </td>
				</tr>
		</table>
	   </form>
		</div>
		<div class="module_search_button">
			<form id="user_search_form" method="post">
			<input type="hidden" id="deviceid" value="${bean.deviceId}" />
				设备编码:<input id="itemid" class="easyui-textbox input_width_default">
				设备IP:<input id="twoid" class="easyui-textbox input_width_default">
				<a href="#"  data-options="iconCls:'icon-search-rf',plain:true" class="easyui-linkbutton"onclick="doSearch()">查询</a>
			</form>
		</div> 
	  </div>
	
			<table id="module_datagrid_renewal" toolbar="#module_toolbar_renewal"   ></table>
			 <h3 align="center">虚拟任务</h3>
	     	<table id="xmodule_datagrid_renewal" toolbar="#xmodule_toolbar_renewal"   ></table>
	     	 <h3 align="center">虚拟任务清单</h3>
	     	<table id="ymodule_datagrid_renewal" toolbar="#ymodule_toolbar_renewal"   ></table>

</body>
</html>


