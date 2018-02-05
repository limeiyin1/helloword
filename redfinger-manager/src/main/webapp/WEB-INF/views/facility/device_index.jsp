<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>物理设备</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript" src="${ctxStatic}/jquery-easyui-1.4.1/datagrid-deviceview.js"></script>
<script type="text/javascript">
	var pk="deviceId";
	var module_datagrid = "#module_datagrid";
	var module_dialog = "#module_dialog";
	var module_search_form = "#module_search_form"
	var module_submit_form = "#module_submit_form";
	var callback = defaultCallback ;
	var dataGridParamObj = {
		url:host+"list",
		idField:"deviceId",
		onCheck:function(){
			
		},
	columns:[[
	          {width : 100,title : 'id',field :'deviceId',checkbox : true}, 
	          {width : 100,title : '设备名称',field:'deviceName',sortable : true},	
	          {width : 100,title : '设备编码',field:'deviceCode',sortable : true},	
	          {width : 100,title : '设备类型',field:'deviceType',sortable : true},	 
	          {width : 100,title : '设备管理节点',field:'map.manageControlName',},
	          {width : 100,title : '设备IP',field:'deviceIp',sortable : true},
	          {width : 100,title : 'ROM版本',field:'romVersion',sortable : true},
	          {width : 100,title : '运存(G)',field:'ramSize',sortable : true},
	          {width : 100,title : '项目使用',field:'deviceUse',sortable : true,formatter:function(value){return getDatagridDict('rf_device.device_use',value);}},
	          {width : 100,title : '创建时间',field:'createTime',sortable : true,formatter :formatterTime},
	          {width : 60, title : '设备来源',field:'map.deviceSourceName'},	
	          {width : 60, title : '设备状态',field:'deviceStatus',sortable:true,formatter:function(value){return getDatagridDict('rf_device.device_status',value);}},
	        //  {width : 60, title : '状态',field : 'status',sortable : true,formatter :formatterStop},
	          ]],
	        view: deviceview,
	      	detailFormatter:function(index,row){
				return '<div style="padding:2px;"><table id="ddv-' + index + '"></table></div>';
			},
			onExpandRow: function(index,row){
				getDetail(index,row);
			},
	};
	var dialogParamObj = {
			
	};
	//双击显示明细
	var specific =function(index, row){
		if(typeof dblClickCallback!='undefined' && dblClickCallback){
			dblClickCallback.apply(null, [index,row]);
		}else{
			var view=$(module_datagrid).parent();
			if($(view).hasClass("datagrid-view")){
				//获取头
				var heads=["设备名称","设备编码","设备类型","设备管理节点","设备IP","ROM版本","运存(G)","创建时间","设备来源","设备状态"];
				var cellIndex = 0;
				//获取内容
				var bodys=[];
				cellIndex = 0;
				$(view).find(".datagrid-body").find(".datagrid-row[datagrid-row-index="+index+"]").find(".datagrid-cell").each(function(){
					if((row.walletAccountCount && row.walletAccountCount == 0) || cellIndex > 0){
						bodys.push($(this).html());
					}
					cellIndex++;
				});
				var module_submit_container=$('<div id="module_submit_container"></div>'); 
				var form=$('<form id="module_submit_form" class="easyui-form">');
				var easyui_table=$('<table class="easyui-table" id="module_submit_table"></table>');
				easyui_table.appendTo(form);
				form.appendTo(module_submit_container);
				for(var i in heads){
					var tr=$('<tr><td class="td1">'+heads[i]+':</td><td class="td2">'+bodys[i]+'</td></tr>');
					easyui_table.append(tr);
				}
				var title = $("title").html() + " - 明细";
				$("#button-save").unbind("click").click(cancel);
				$(module_dialog).dialog({title : title,content:$(module_submit_container).prop("outerHTML"),href:''});
				$(module_dialog).dialog("open");
			}
		}
	}
	
		// 普通表格默认参数
	var lookDataGridParam = {
		fitColumns : true,
		pagination : true,
		striped : true,
		rownumbers : true,
		singleSelect : false,
		idField : 'id',
		pageSize : 20,
		pageList : [ 10, 15, 20, 50, 100, 200,500 ],
		loadFilter : loadFilterForDataGrid,
		onDblClickRow:specific,
		loadMsg : "处理中，请稍后..."
	};

	$(function() {
		$(module_datagrid).datagrid($.extend({}, lookDataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
		$("#look").dialog($.extend({}, dialogParam, dialogParamObj));
	});

	function getDetail(index,row){
		  $('#ddv-'+index).datagrid({
			  url:host+'padList?deviceId='+row.deviceId,
			  fitColumns:true,  
              height:'auto',  
              loadFilter : loadFilterForDataGridNoPage,
				columns:[[
                  
						{width : 100,title : '设备名称',field:'padName',sortable : true},
						{width : 100,title : '设备编码',field:'padCode',sortable:true},
						{width : 100,title : '虚拟节点序号',field:'padSn',sortable:true},
						{width : 100,title : '物理设备编号',field:'map.deviceCode'},
						{width:100,title:'设备控制端口',field:'padControlPort',sortable:true},
						{width:100,title:'设备IP',field:'padIp',sortable:true},
						{width:100,title:'MAC',field:'vmMac',sortable:true},
						{width:100,title:'imei',field:'imei',sortable:true},
						{width : 60, title : '授权状态',field:'grantOpenStatus',sortable:true ,formatter:function(value){return getDatagridDict('rf_pad.grant_open_status',value);}},
						{width : 60, title : '虚拟状态',field:'vmStatus',sortable:true ,formatter:function(value){return getDatagridDict('rf_pad.vm_status',value);}},
						{width : 60,title : '绑定状态',field : 'bindStatus',sortable : true,formatter:function(value){return getDatagridDict('rf_pad.bind_status',value);}},
						{width : 60,title : '启用状态',field : 'enableStatus',sortable : true,formatter:function(value){return getDatagridDict('rf_pad.enable_status',value);}},
						{width : 60,title : '受控状态',field : 'padStatus',sortable : true,formatter:function(value){return getDatagridDict('rf_pad.pad_status',value);}},
						{width : 60,title : '故障状态',field : 'faultStatus',sortable : true,formatter:function(value){return getDatagridDict('rf_pad.fault_status',value);}}
		            	]],
				onResize:function(){
					$('#module_datagrid').datagrid('fixDetailRowHeight',index);
				},
				onLoadSuccess:function(){
					setTimeout(function(){
						$('#module_datagrid').datagrid('fixDetailRowHeight',index);
					},0);
				}
			});
			$('#module_datagrid').datagrid('fixDetailRowHeight',index);
	}
	
	//虚拟管理
	 /*   function taskOnly(){
		   	var id=getGridId();
		   	if(!id)return false;
		   	var title=$("title").html()+" - 虚拟管理";
		   	var href=host+'vmTaskForm?deviceId='+id;
		   	$("#button-save").unbind("click").click(addVmDefined);
		   	openWidthDialogForm(title,href,800);
		   } */
		//无保存按钮 虚拟管理
		function manage() {
			var id = getGridId();
			if (!id) {
				return false;
			}
		   	var title=$("title").html()+" - 虚拟管理";
		   	var href=host+'vmTaskForm?deviceId='+id;
			lookForm(title, href);
		}
		function lookcancel() {
			if(typeof module_dialog!='undefined' && $(look)){
				$(look).dialog("close");
			}
		//	$("#look").dialog("close");
		}
		//打开编辑对话框
		function lookForm(title, href) {
			$("#look").dialog({title : title,href: href,width:700});
			$("#look").dialog("open");
		}

		
	//虚拟化批量操作
	   function task(){
		   	var ids=getGridIds();
		   	if(!ids)return false;
		   	var title=$("title").html()+" - 虚拟化批量操作";
		   	var href=host+'vmTasksForm?ids='+ids;
			$("#button-save").unbind("click").click(taskSave);
			openDialogForm(title, href);
		   }

	   var taskSave = function() {
			if ($('#module_submit_form').form("validate")) {
				$.messager.progress();
				$('#module_submit_form').form("submit");
			
			}
		}
		
		//虚拟设备编辑
		   function updateVm(){
			   	var id=getGridId();
			   	if(!id)return false;
			   	var title=$("title").html()+" - 虚拟设备编辑";
			 	var href=host+'addForm?deviceId='+id;
				$("#button-save").unbind("click").click(taskSave);
				openDialogForm(title, href);
			   }
	   
	 //添加虚拟设备
	   function addVM(){
	   	var id=getGridId();
	   	if(!id)return false;
	   	var title=$("title").html()+" - 添加虚拟设备";
	   	var href=host+'vmForm?deviceId='+id;
	   	$("#button-save").unbind("click").click(addVmDefined);
	   	openWidthDialogForm(title,href,700);
	   }
	 
	   //移除虚拟设备
	   function rmVM(){
	   	var id=getGridId();
	   	if(!id)return false;
	   	var title=$("title").html()+" - 移除虚拟设备";
	   	var href=host+'rmForm?deviceId='+id;
	   	$("#button-save").unbind("click").click(addVmDefined);
	   	addVmDefinedForm(title,href);
	   }
	   
/* 	   function rmVmDefined(){
			  var row = $(module_datagrid_renewal).datagrid("getSelections");
			  if (row == '') {
					$.messager.alert('提示', '请选择记录！', "error");
					return false;
				}	
				if($('#module_submit_form').form("validate")){
					$.messager.progress();
					$('#module_submit_form').form("submit");
				}else{
					alert("操作错误");
				}
				
			}
	    */
	  var addVmDefined = function() {
		  var row = $(module_datagrid_renewal).datagrid("getSelections");
		 /*通过后台判断配置信息确认可以绑定的台数，不再用前段控制 
		 if( row.length>4){
			  $.messager.alert('提示', '不能选择大于4台设备！', "error");
				return false;
		  } */
		  if (row == '') {
				$.messager.alert('提示', '请选择记录！', "error");
				return false;
			}	
			if($('#module_submit_form').form("validate")){
				$.messager.progress();
				$('#module_submit_form').form("submit");
			}else{
				alert("操作错误");
			}
			
		}
	   function addVmDefinedForm(title, href) {
			$(module_dialog).dialog({title : title,href: href,width:700});
			$(module_dialog).dialog("open");
		}
	   
	// 批量操作物理设备
	   var updateDevice = function(callback) {
	   	var ids = getGridIds();
	   	if (!ids) {
	   		return false;
	   	}
	   	var title=$("title").html()+" - 批量操作物理设备";
	   	var href=host+'vmOpenForm?ids='+ids;
	   	$("#button-save").unbind("click").click(taskSave);
	   	openDialogForm(title, href);
	   }
	
		//批量操作设备
		function batchPad() {
				var title = "批量操作";
				var href = host + 'batchForm';
		 		$("#button-save").unbind("click").click(taskSave);  
				openDialogForm(title, href); 
			}
		
		// 虚拟设备授权
		   var openAll = function(callback) {
		   	var ids = getGridIds();
		   	if (!ids) {
		   		return false;
		   	}
		   	var title=$("title").html()+" - 虚拟设备开放授权批量操作";
		   	var href=host+'openForm?ids='+ids;
		   	$("#button-save").unbind("click").click(taskSave);
		   	openDialogForm(title, href);
		   }
		
		
		
				//导出数据
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
					$("#exportName").val('物理设备');
					$("#exportForm").submit();
				}else{
					$.messager.alert('操作失败','无数据！',"warning");
				}
			}
			
			
		//ping物理机
		function devicePing() {
			var ids = getGridIds();
			if (!ids) {
				return false;
			}
			$.messager.confirm('确认？', '确认ping该物理设备?', function(confirm) {
				if (confirm) {
					ajaxPost("device_ping", {
						ids : ids
					}, callback);
				}
			});
		}
		
		//////////新功能//////////////
		//添加新的虚拟设备
		 var addNewVM_before = function() {
		
			if($('#module_submit_form').form("validate")){
				$.messager.progress();
				$('#module_submit_form').form("submit");
			}else{
				alert("操作错误");
			}
			
		}
	   function addNewVMForm(){
	   	var id=getGridId();
	   	if(!id)return false;
	   	var title=$("title").html()+" - 添加虚拟设备";
	   	var href=host+'addNewVMForm?deviceId='+id;
	   	$("#button-save").unbind("click").click(addNewVM_before);
	   	openWidthDialogForm(title,href,700);
	   }
	   
	   function deviceUseEdit(){
		   var row = $(module_datagrid).datagrid("getSelections");
		   	var module_submit_container=$('<div id="module_submit_container"></div>'); 
			var form=$('<form id="module_submit_form" class="easyui-form">');
			var easyui_table=$('<table class="easyui-table" id="module_submit_table"></table>');
			easyui_table.appendTo(form);
			form.appendTo(module_submit_container);
			
			if (row == '') {
				 var tr=$('<tr><td class="td1">物理IP段起始:</td><td class="td2"><input type="text" class="easyui-textbox input_width_default" editable="true" name="deviceStartIp" data-options="required:true,validType:\'length[1,32]\'"/></td></tr>');
				 easyui_table.append(tr);
				 var tr=$('<tr><td class="td1">物理IP段结束:</td><td class="td2"><input type="text" class="easyui-textbox input_width_default" editable="true" name="deviceEndIp" data-options="required:true,validType:\'length[1,32]\'"/></td></tr>');
				 easyui_table.append(tr);
			} else {
				// 设备编码
				var codes = [];
				var ids = [];
			   	for (var i = 0; i < row.length; i++) {
			   		codes[i] = row[i]['deviceCode'];
			   		ids[i] = row[i][pk];
				}
			   var input = '<input type="hidden" name="ids" value="'+ids.join(",")+'"/>';
			   easyui_table.append(input);
			   var tr=$('<tr><td class="td1">设备编码:</td><td class="td2">'+codes.join("<br>")+'</td></tr>');
			   easyui_table.append(tr);
			}
		    tr=$('<tr><td class="td1">使用的项目:</td><td class="td2"><select class="easyui-combobox" id="editDU" validType="selectValueRequired[\'#editDU\']"  editable="false" name="deviceUse" data-options="required:true">'+$("#deviceUse-select").html()+'</select></td></tr>');
		    easyui_table.append(tr);
			var title = $("title").html() + " - 使用的项目编辑";
			$("#button-save").unbind("click").click(updateDeviceUse);
			$(module_dialog).dialog({title : title,content:$(module_submit_container).prop("outerHTML"),href:''});
			$(module_dialog).dialog("open");
	   }
	   
	   $.extend($.fn.validatebox.defaults.rules, {  
			selectValueRequired: {
				validator: function(value,param){ 
					return $(param[0]).find("option:contains('"+value+"')").val() != '';  
				},  
				message: '该选项为必选项'  
			}  
		});
	   
	   function updateDeviceUse(){
		   var device_use_form = "#device_use_module_submit_form";
		   $(module_submit_form).form('submit', {
				url:host + 'updateDeviceUse', 
				onSubmit: function(){
					var isValid = $(this).form('validate');
					if (isValid){
						$.messager.progress();	
					}
					return isValid;	
				},
			    success: callback    
			}); 
	   }
		
				 
</script>
</head>
<body>
<!--搜索条件  -->
		<div id="module_container">
				<div id="module_search">
					<form id="module_search_form" class="easyui-form" method="post">
						<div class="module_search_input">
						     会员ID：<input type="text" name="externalUserId" class="easyui-numberbox input_width_short" /> 
						</div>
						<div class="module_search_input">
						     手机号：<input type="text" name="userMobilePhone" class="easyui-textbox input_width_default" /> 
						</div>
						<div class="module_search_input">
						     设备IP:
						     <select class="easyui-combobox input_width_short" data-options="editable:false,required:false,width:'80px'" name="ipSearchType">
								<option value="1">模糊</option>
								<option value="2">精准</option>
							</select>
							<input type="text" name="deviceIp" class="easyui-textbox input_width_default" />
						</div>
						<div class="module_search_input">
						     设备编号：<input type="text" name="deviceCode" class="easyui-textbox input_width_default" /> 
						</div>
						<div class="module_search_input">
						    设备名称：<input type="text" name="deviceName" class="easyui-textbox input_width_default" /> 
						</div>
						<div class="module_search_input">
						 	 设备状态:<select class="easyui-combobox input_width_short" data-options="editable:false,required:false" name="deviceStatus" >
									<option value="">[全部]</option>
								    <fns:getOptions category="rf_device.device_status"  ></fns:getOptions>
								    <option value="null">空白</option>
								</select> 
						</div>
						<div class="module_search_input">
						设备来源:
						<select editable="false" name="deviceSource" class="easyui-combobox input_width_default" >
								<option value="">[全部]</option>
								<c:forEach var="one" items="${facilityList}">
									<option value="${one.facilityCode}">${one.facilityName}</option>
								</c:forEach>
							</select>
					 </div>
					 <div class="module_search_input">
						 	 项目使用:<select id="deviceUse-select" class="easyui-combobox input_width_short" data-options="editable:false,required:false" name="deviceUse" >
									<option value="">[全部]</option>
								    <fns:getOptions category="rf_device.device_use"  ></fns:getOptions>
								</select> 
						</div>
					 <div class="module_search_input input_width_default">
					 ROM版本:
					 	<select class="easyui-combobox" name="romVersion" data-options="editable:false,width:80">
							 <option value="">[全部]</option>
							 <option  value="4.4">4.4</option>
							 <option  value="6.0">6.0</option>
						</select>
					 </div>
					  <div class="module_search_input input_width_default">
						机房:<select editable="false" name="idcId" class="easyui-combobox input_width_default" >
							<option value="">[全部]</option>
							<c:forEach var="one" items="${idcList}">
								<option value="${one.idcId}">${one.idcName}</option>
							</c:forEach>
						</select>
					 </div>
					 <div class="module_search_input">
						物理IP段:
						<input type="text" class="easyui-textbox input_width_default" editable="true" name="deviceStartIp" />
						至
						<input type="text" class="easyui-textbox input_width_default" editable="true" name="deviceEndIp" />
					</div>
					<div class="module_search_button">
						<a href="javascript:void(0)" class="easyui-linkbutton"data-options="iconCls:'icon-search-rf',plain:false" onclick="gridSearch()">搜索</a>
						<a href="javascript:void(0)" class="easyui-linkbutton"data-options="iconCls:'icon-reset-rf',plain:false" onclick="gridReset()">清空</a>
					</div>
				</form>
			</div>
		<!--数据操作项  -->
		     <div id="module_toolbar" class="easyui-toolbar" style="height: auto">
		     	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-excel-rf',plain:true"onclick="statExport()">导出</a>
		     	<c:if test="${not empty sessionScope.permission.button_facility_device_addVM}">
		      	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-link-rf',plain:true" onclick="addVM(callback)">添加虚拟设备</a>
		      	</c:if>
		      	<c:if test="${not empty sessionScope.permission.button_facility_device_rmVM}">
		      	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-link-rf',plain:true" onclick="rmVM(callback)">移除虚拟设备</a>
		      	</c:if>
		      	<c:if test="${not empty sessionScope.permission.button_facility_device_add}">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add-rf',plain:true" onclick="add()">新增</a>
				</c:if>
				<c:if test="${not empty sessionScope.permission.button_facility_device_edit}">
			    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit-rf',plain:true" onclick="edit()">编辑</a>
			    </c:if>
			    <c:if test="${not empty sessionScope.permission.button_facility_device_start}">
		        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-start-rf',plain:true" onclick="start(callback)">启用</a>
		        </c:if>
		        <c:if test="${not empty sessionScope.permission.button_facility_device_stop}">
		        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-stop-rf',plain:true" onclick="stop(callback)">禁用</a>
		        </c:if>
		        <c:if test="${not empty sessionScope.permission.button_facility_device_del}">
		        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove-rf',plain:true" onclick="del(callback)">删除</a>
		        </c:if>
		        <c:if test="${not empty sessionScope.permission.button_facility_device_manage}"> 
		        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit-rf',plain:true" onclick="manage()">虚拟管理</a>
		        </c:if>
		        <c:if test="${not empty sessionScope.permission.button_facility_device_updateVm}">
		        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit-rf',plain:true" onclick="updateVm()">SN编辑</a>
		        </c:if>
		        <c:if test="${not empty sessionScope.permission.button_facility_device_task}">
		        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit-rf',plain:true" onclick="task()">批量虚拟管理</a>
		        </c:if>
		        <c:if test="${not empty sessionScope.permission.button_facility_device_batchPad}">
		        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit-rf',plain:true" onclick="batchPad()">批处理</a>
		        </c:if>
		        <c:if test="${not empty sessionScope.permission.button_facility_device_upDevice}">
		        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-stop-rf',plain:true" onclick="updateDevice(callback)">批量操作物理设备</a>
				</c:if>
				<c:if test="${not empty sessionScope.permission.button_facility_device_openAll}">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-stop-rf',plain:true" onclick="openAll(callback)">虚拟设备授权</a>
				</c:if>
				
				<c:if test="${not empty sessionScope.permission.button_facility_device_addVM}">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add-rf',plain:true" onclick="addNewVMForm(callback)">添加虚拟设备</a>
				</c:if>
				
		        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload-rf',plain:true" onclick="fresh()">刷新</a>
		        <c:if test="${not empty sessionScope.permission.button_facility_device_updateDu}">
		         <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit-rf',plain:true" onclick="deviceUseEdit()">使用的项目</a>
		         </c:if>
		        <!-- <a href="javascript:void(0)" class="easyui-linkbutton"data-options="iconCls:'icon-star-rf',plain:true" onclick="devicePing()">ping物理机</a> -->
			    </div>
			     <table id="module_datagrid"  toolbar="#module_toolbar" ></table>
		</div>
		
	     	<!-- 编辑框 -->
		<div id="module_dialog" buttons="#module_dialog_button"></div>
		<div id="module_dialog_button">
			<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton" data-options="iconCls:'icon-ok-rf'" >保存</a> 
			<a href="javascript:void(0)" id="button-cancel" class="easyui-linkbutton" data-options="iconCls:'icon-no'" onclick="cancel()">关闭</a>
		</div>
		
	  	<!-- 无保存按钮编辑框 -->
		<div id="look" buttons="#look_button"  data-options="maximizable:true"></div>
		<div id="look_button">
		    <a href="javascript:void(0)"  class="easyui-linkbutton" iconCls="icon-no" onclick="lookcancel()">关闭</a>
		</div>
			
			<!--导出  -->
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