<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>故障工单</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="faultFeedbackId";
	var padCode = "padCode";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var flag = "rf_pool_index";
	var currentUserId = "${currentUserId}";
	var initColumnMap = {
		'faultFeedbackId':20,
		'feedbackStatus':60,
		'padCode':120,
		'map.padGrade':60,
		'map.padIp':80,
		'map.deviceIp':80,
		'map.bindStatus':70,
		'createTime':80,
		'map.className':60,
		'feedbackContent':60,
		'feedbackSource':80,
		'feedbackContact':60,
		'feedbackQq':80,
		'map.promoter':60,
		'map.lastHandler':60,
		'map.clientSourceName':60,
		'remark':50,
		'vmStatus':60,
		'padStatus':60,
	};
	var callback=defaultCallback;
	var formatterFlag=function(value,row,index){
		return getDatagridDict('rf_fault_feedback.feedback_status',value);
	}
	var dataGridParamObj={
		url:host+"list",
		idField : pk,
		singleSelect:false,
		onSelect: linkButtonChange,
		onUnselect: linkButtonChange,
		onCheckAll:	linkButtonChange,
		onUncheckAll: linkButtonChange,
		columns:[[   
					{width:20,title:'id',field:pk,checkbox:true},
					{width:60,title:'状态',field:'feedbackStatus',sortable:true,formatter:formatterFlag},
					{width:120,title:'设备编码',field:'padCode',sortable:true},
					{width:60,title:'设备等级',field:'map.padGrade',formatter :function(value){return getDatagridDict('rf_user_pad.pad_grade',value);}},
					{width:80,title:'设备IP',field:'map.padIp'},
					{width:80,title:'物理设备IP',field:'map.deviceIp'},
					{width:70,title:'当前绑定状态',field:'map.bindStatus',formatter :function(value){return getDatagridDict('rf_pad.bind_status',value);}},
					{width:80,title:'故障时间',field:'createTime',sortable:true,formatter:formatterTime},
					{width:60,title:'故障类型',field:'map.className'},
					{width:60,title:'故障描述',field:'feedbackContent',sortable:true},
					{width:80,title:'来源',field:'feedbackSource',sortable:true,formatter:function(value){return getDatagridDict('rf_fault_feedback.feedback_source',value);}},
					{width:60,title:'联系电话',field:'feedbackContact',sortable:true},
					{width:80,title:'联系QQ',field:'feedbackQq',sortable:true},
					{width:60,title:'咨询',field:'map.promoter'},
					{width:60,title:'当前处理',field:'map.lastHandler'},
					{width:60,title:'操作来源',field:'map.clientSourceName'},
					{width:50,title:'备注',field:'remark'},
					{width : 60, title : '虚拟状态',field:'vmStatus',sortable:true ,formatter:function(value){return getDatagridDict('rf_pad.vm_status',value);}},
					{width : 60,title : '受控状态',field : 'padStatus',sortable : true,formatter :formatterOnlineStatus}
		]],
		onLoadSuccess:function(data){
			if(data){
				var total = data.total;
				if(total > 5000){
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
	var dialogParamObj={
			width:600
	};
	
	//管理员绑定按钮根据选择的设备是否已经绑定来启用或者禁用
	function linkButtonChange(){
		var flag = true;
		$.each($(module_datagrid).datagrid('getSelections'),function(index,row){
			if(row.map.bindStatus == '1'){
				flag = false;
				return false;
			}
		});
		flag ? $("#adminbinding").linkbutton('enable') : $("#adminbinding").linkbutton('disable');
	}
	
	//管理员绑定设备
 	function adminbinding(){
 		var padCodes=getpadIds();
 		if(!padCodes)return false;
 		var title=$("title").html()+" - 管理员绑定设备";
 		var href=host+'adminBindingForm?padCodes='+padCodes;
 		$("#button-save").unbind("click").click(userBindSave);
 		openDialogForm(title,href);
 	}
	//表单提交
 	function userBindSave() {
		if ($('#module_submit_form').form("validate")) {
			$.messager.progress();
			$('#module_submit_form').form("submit");
		}
	}
	
	function getpadIds(){
		var row = $(module_datagrid).datagrid("getSelections");
		if (row == '') {
			$.messager.alert('提示', '请选择记录！', "info");
			return null;
		} else {
			var ids = [];
			for (var i = 0; i < row.length; i++) {
				ids[i] = row[i]["padCode"]
			}
			return ids.join(",");
		}
	}
	
	$(function(){
		$("#module_column_defined_dialog").dialog($.extend({}, dialogParam, dialogParamObj));
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
		loadColumnDefined(currentUserId,initColumnMap,pk,flag);
	});
	// 启用
	var accept = function(callback) {
		var ids = getGridIds();
		if (!ids) {
			return false;
		}
		ajaxPost("accept", {ids : ids}, callback);
	}
	
		//在线状态
	function formatterOnlineStatus(value, row, index) {
		return getDatagridDict('rf_pad.pad_status',value);
	}
	
	function batchAccept(){
		var title=$("title").html()+" - 批量受理";
		var href=host+'batchAcceptForm';
		$("#button-save").unbind("click").click(poolSave);
		openDialogForm(title,href);
	}
	
	function poolSave() {
		if ($('#module_submit_form').form("validate")) {
			$.messager.progress();
			$('#module_submit_form').form("submit");
		}
	}
	
	function screencap() {
		var padCodes = getGridPadCodes();
		if (!padCodes) {
			return false;
		}
		var title = "截图";
		$.messager.confirm('确认？', '截图，是否执行该操作?', function(confirm) {
				if (confirm) {
				ajaxPost("pad_screencap", {
					padCodes : padCodes
				}, callback);
			}}
		);
	}
	
	//获取多条被选择数据，数据用逗号隔开，如果不符合弹出提示对话框
	var getGridPadCodes = function() {
		var row = $(module_datagrid).datagrid("getSelections");
		if (row == '') {
			$.messager.alert('提示', '请选择记录！', "info");
			return null;
		} else {
			var ids = [];
			for (var i = 0; i < row.length; i++) {
				ids[i] = row[i][padCode]
			}
			return ids.join(",");
		}
	}
	
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
 			$("#exportName").val('待处理故障');
 			$("#exportForm").submit();
 		}else{
 			$.messager.alert('操作失败','无数据！',"warning");
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
			$("#exportName").val('待处理故障');
			
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
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">
				设备编码:<input type="text" name="padCode" class="easyui-textbox input_width_default" />
		 	</div>
		 	<div class="module_search_input">
					编码段:<input type="text" name="padCodeStart" class="easyui-textbox input_width_default" />至
					<input type="text" name="padCodeEnd" class="easyui-textbox input_width_default" />
			 </div>
		 	<div class="module_search_input">
				故障时间:
				<input type="text" class="easyui-datebox input_width_default" editable="false"  name="beginTimeStr" />
				至
				<input type="text" class="easyui-datebox input_width_default" editable="false"  name="endTimeStr"/>
		 	</div>
			<div class="module_search_input">故障类型：
				<input type="text" id="classId" value="0" name="classId" class="easyui-combotree input_width_long3" data-options='data:${categoryTree}' />
			</div>
			<div class="module_search_input">故障状态：
				<select class="easyui-combobox input_width_default" editable="false" name="feedbackStatus">  
					<option value="">[全部]</option>
					<fns:getOptions category="rf_fault_feedback.feedback_status" value="" keys="rf_fault_feedback.feedback_status@new,rf_fault_feedback.feedback_status@movekefu,rf_fault_feedback.feedback_status@moveceshi,rf_fault_feedback.feedback_status@moveyunwei"></fns:getOptions>
				</select>
			</div>
			<div class="module_search_input">故障来源：
				<select class="easyui-combobox input_width_default" editable="false" name="feedbackSource">
					<option value="">[全部]</option>
					<fns:getOptions category="rf_fault_feedback.feedback_source"/>
				</select>
			</div>
			<div class="module_search_input">咨询：
				<select class="easyui-combobox input_width_default" editable="false" name="promoter">
					<option value="">[全部]</option>
					<c:forEach var="one" items="${zixunList }">
						<option value="${one.adminCode }">${one.adminName }</option>
					</c:forEach>
				</select>
			</div>
			<div class="module_search_input">当前处理：
				<select class="easyui-combobox input_width_default" editable="false" name="lastHandler">
					<option value="">[全部]</option>
					<c:forEach var="one" items="${handerList }">
						<option value="${one.adminCode }">${one.adminName }</option>
					</c:forEach>
				</select>
			</div>
			<div class="module_search_input">操作类型：
				<select class="easyui-combobox input_width_default" editable="false" name="operateType">
					<option value="">[全部]</option>
					<fns:getOptions category="rf_fault_feekback.operate_type"/>
				</select>
			</div>
			<div class="module_search_input">		
				 虚拟状态:<select class="easyui-combobox input_width_short"  editable="false" name="vmStatus" data-options="required:false">
						<option value="">[全部]</option>
					   <fns:getOptions category="rf_pad.vm_status"  ></fns:getOptions>
						</select> 
			 </div>	
			 <div class="module_search_input">		
				 受控状态:<select class="easyui-combobox input_width_short"  editable="false" name="padStatus" data-options="required:false">
						<option value="">[全部]</option>
					   <fns:getOptions category="rf_pad.pad_status"  ></fns:getOptions>
						</select> 
			 </div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search-rf',plain:false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reset-rf',plain:false"  onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" id="export-link" iconCls="icon-excel-rf" plain="true">导出</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-heart-rf" plain="true" onclick="accept()">受理</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton"data-options="iconCls:'icon-star-rf',plain:true" onclick="screencap()">截图</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-heart-rf" plain="true" onclick="batchAccept()">批量受理</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-link-rf" plain="true" id="adminbinding" onclick="adminbinding(callback)">管理员绑定</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit-rf',plain:true" onclick="columnDefined(currentUserId,initColumnMap,pk,flag)">列表自定义</a>
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>
	
	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
    <div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton" iconCls="icon-ok-rf">确定</a>
		<a href="javascript:void(0)" id="button-cancel" class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
	<!-- 列表自定义编辑框 -->
	<div id="module_column_defined_dialog" buttons="#module_column_defined_dialog_button"></div>
	<div style="text-align:center" id="module_column_defined_dialog_button">
		<a href="javascript:void(0)" id="buttone-column-defined-save-select-all"class="easyui-linkbutton" iconCls="icon-edit-rf" onclick="selectAllColumn()">全选</a>
	    <a href="javascript:void(0)" id="buttone-column-defined-save"class="easyui-linkbutton" iconCls="icon-ok-rf">确定</a>
	    <a href="javascript:void(0)" id="button-column-defined-cancel"class="easyui-linkbutton" iconCls="icon-reload-rf" onclick="resetColumn(currentUserId,initColumnMap,pk,flag)">重置</a>
	</div>
	<!-- 列表自定义编辑框  end-->
	
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



