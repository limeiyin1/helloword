<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>设备</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="padId";
	var module_datagrid = "#module_datagrid";
	var module_dialog = "#module_dialog";
	var module_search_form = "#module_search_form"
	var module_submit_form = "#module_submit_form";
	var flag = "rf_pad_index";
	var currentUserId = "${currentUserId}";
	var initColumnMap = {
	    'map.externalUserId':60, 
		'padId':10,
		'map.userMobilePhone':70,
		'padGradeT2':50,
		'padCode':100,
		'padName':70,
		'userPadNameT2':70,
		'bindTimeT2':80,
		'expireTimeT2':80,
		'padIp':70,
		'map.deviceIp':70,
		'map.idcName':50,
		'map.padSourceName':50,
		'padClassify':50,
		'controlProtocol':50,
		'remark':40,
		'maintStatus':50,
		'grantOpenStatus':50,
		'vmStatus':50,
		'bindStatus':50,
		'enableStatus':50,
		'padStatus':50,
		'faultStatus':50
	};
	var callback = defaultCallback;
	var dataGridParamObj = {
		url : host + "list",
		idField : 'padId',
		onCheck : function(index,row) {
			//状态为绑定时不能绑定
			var flag=row.bindStatus;
			if(flag==1){
				$("#binding").linkbutton('disable');
			}else{
				$("#binding").linkbutton('enable');
			}
			//判断为主营设备不可绑定游戏设备
			var padClassify=row.padClassify;
			if(padClassify==1){
				$("#bindGame").linkbutton('disable');
			}else{
				$("#bindGame").linkbutton('enable');
			}
		},
		onSelect: linkButtonChange,
		onUnselect: linkButtonChange,
		onCheckAll:	linkButtonChange,
		onUncheckAll: linkButtonChange,
		columns : [ [
	         	{width : 10,title : 'id',field :'padId',checkbox : true},
	         	/* {width : 100,title : '申请来源',field:'padSourceT2',sortable : true}, */
	         	{width : 70,title : '会员ID',field:'map.externalUserId'},
	         	{width : 70,title : '会员手机',field:'map.userMobilePhone'},
	         	{width : 50,title : '设备等级',field:'padGradeT2',sortable:true,formatter:formatterPadGradeStatus},
	         	{width : 100,title : '设备编码',field:'padCode',sortable:true},
	         	{width : 70,title : '设备名称',field:'padName',sortable : true},
	         	{width : 70,title : '用户设备名称',field:'userPadNameT2',sortable : true},	 
	         	{width : 80,title : '绑定时间',field:'bindTimeT2',sortable:true,formatter:formatterTime},
	         	{width : 80,title : '设备过期时间',field:'expireTimeT2',sortable:true,formatter:formatterTime},
	         	{width : 70,title : '设备IP',field:'padIp',sortable:true},
	         	{width : 70,title : '物理设备IP',field:'map.deviceIp',sortable:false},
	         	{width : 50,title : '机房',field:'map.idcName'},
	         	{width : 50,title : 'ROM版本',field:'romVersion'},
	         	{width : 50,title : '设备来源',field:'map.padSourceName'},
	         	{width : 50, title : '设备类别',field:'padClassify',sortable:true,formatter:function(value){return getDatagridDict('rf_pad.pad_classify',value);}},
	         	{width : 50, title : '控制协议',field:'controlProtocol',sortable:true,formatter:function(value){return getDatagridDict('rf_pad.control_protocol',value);}},
	         	{width : 40,title : '备注',field:'remark',sortable:true},
	         	{width : 50, title : '维护状态',field:'maintStatus',sortable:true,formatter:function(value){return getDatagridDict('rf_pad.maint_status',value);}},
	           	{width : 50, title : '授权状态',field:'grantOpenStatus',sortable:true ,formatter:function(value){return getDatagridDict('rf_pad.grant_open_status',value);}},
	           	{width : 50, title : '虚拟状态',field:'vmStatus',sortable:true ,formatter:function(value){return getDatagridDict('rf_pad.vm_status',value);}},
	         	{width : 50,title : '绑定状态',field : 'bindStatus',sortable : true,formatter :formatterBindStatus},
	         	{width : 50,title : '启用状态',field : 'enableStatus',sortable : true,formatter :formatterEnableStatus},
	         	{width : 50,title : '受控状态',field : 'padStatus',sortable : true,formatter :formatterOnlineStatus},
	         	{width : 50,title : '故障状态',field : 'faultStatus',sortable : true,formatter :formatterFaultStatus},
	         	
	         	/* {width : 100,title : '更换时间',field:'renewalTimeT2',sortable:true,formatter:formatterTime}, */
	         	/* {width : 100,title : '最近受控时间',field:'padControlTimeT2',sortable:true,formatter:formatterTime}, */
	         	/* {width : 60,title : '剩余天数',field:'map.controltime'}, */
	         	/* {width : 100,title : '物理设备',field:'map.deviceName'}, */
	         	/* {width : 60,title : '控制时间',field:'map.onlinetime',sortable:true ,formatter:formatterOnlineTime}, */
	        	/* {width : 100,title : 'MAC',field:'vmMac',sortable:true},
	         	{width : 100,title : '用户控制节点',field:'map.controlName',},
	         	{width : 100,title : '设备控制节点',field:'map.padControlName',}, */
	         	/*{width : 100,title : '设备管理控制节点',field:'map.manageControlName',}, */
	         	/*{width : 50,title : '设备控制端口',field:'padControlPort',sortable:true},
	         	{width : 50,title : '设备管理端口',field:'padManagePort',sortable:true},
	         	{width : 50,title : '批次号',field:'batchNumber',sortable:true},
	         	{width : 60,title : '设备守护进程版本',field:'remoteVersion',sortable:true},
	         	{width : 100,title : '设备内启动器版本号',field : 'versionCode',sortable:true}, */
	         	/* {width : 100,title : '冻结状态',field : 'activeStatus',sortable : true,formatter :formatterActiveStatus}, */
	         	/* {width : 100,title : '设备SN',field:'padSn',sortable:true}, */
	         	/* {width : 100,title : '设备imei',field:'imei'}, */
	     ] ],
	     onLoadSuccess:function(data){
			if(data){
				var total = data.total;
				if(total > 5000){
					$("#export-link").unbind("click").click(statExportForm);
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
			width:600
	};
	
	//双击显示明细
	var specific =function(index, row){
		if(typeof dblClickCallback!='undefined' && dblClickCallback){
			dblClickCallback.apply(null, [index,row]);
		}else{
			var view=$(module_datagrid).parent();
			if($(view).hasClass("datagrid-view")){
				//获取头
				var heads=["设备id","申请来源","设备名称","用户设备名称","设备编码","设备等级","会员手机号",
				           "绑定时间","更换时间","最近受控时间","物理设备","控制时间","设备过期时间","MAC","设备IP","物理设备IP",
				           "机房","ROM版本","控制协议","用户控制节点","设备控制节点","设备SN","设备来源","设备imei","备注","维护状态","授权状态","虚拟状态",
				           "绑定状态","启用状态","受控状态","故障状态"];
				var cellIndex = 0;
				//获取内容
				var bodys=[];
				cellIndex = 0;
				$(view).find(".datagrid-body").find(".datagrid-row[datagrid-row-index="+index+"]").find(".datagrid-cell").each(function(){
					bodys.push($(this).html());
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
		$("#module_column_defined_dialog").dialog($.extend({}, dialogParam, dialogParamObj));
		$(module_datagrid).datagrid($.extend({}, lookDataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
		$("#look").dialog($.extend({}, dialogParam, dialogParamObj));
		loadColumnDefined(currentUserId,initColumnMap,pk,flag);
		//快捷键
		$(document).bind("keydown","+",function(){
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
		});
		$.extend($.fn.validatebox.defaults.rules,{
			number:{// 验证数字
	          validator : function(value) {
	              return /^[0-9]{1,9}$/gi.test(value);
	          },
	          message : '只允许1-9位的正整数'
	        }
		});
	});
	
	//双击callback
	var dblClickCallback=function(index,row){
		var title = $("title").html() + " - 明细";
		var href = host + 'detail?'+pk+'=' + row[pk];
		$("#look").dialog({title : title,href: href,width:500});
		$("#look").dialog("open");
	};
	
	function getFloatData(){
	   var padClassify=$("#padClassify_collect").combobox("getValue");
	   var idcId=$("#idcId_collect").combobox("getValue");
	   
		var url = host + 'floatData?'+'padClassify='+padClassify+'&idcId='+idcId;
		$.getJSON(url, function(json){
			for(var i in json){
				$("#"+i).html(json[i]);
			}
			$.messager.progress('close');
		});
	}
	//绑定
	function binding(){
		var id=getGridId();
		if(!id)return false;
		var title=$("title").html()+" - 绑定设备";
		var href=host+'userForm?padId='+id;
		$("#button-save").unbind("click").click(userBindSave);
		$(module_dialog).window('resize',{width:600,height:600});
		openDialogForm(title,href);
	}
	//绑定VIP
	function bindAdmin(){
		var id=getGridId();
		if(!id)return false;
		var title=$("title").html()+" - 管理员绑定设备";
		var href=host+'adminForm?padId='+id;
		$("#button-save").unbind("click").click(userBindSave);
		$(module_dialog).window('resize',{width:600,height:600});
		openDialogForm(title,href);
	}
	
	//绑定GVIP
	function bindGvipAdmin(){
		var id=getGridId();
		if(!id)return false;
		var title=$("title").html()+" - 管理员绑定Gvip设备";
		var href=host+'adminGvipForm?padId='+id;
		$("#button-save").unbind("click").click(userBindSave);
		$(module_dialog).window('resize',{width:600,height:600});
		openDialogForm(title,href);
	}
	
	//批量绑定VIP
	function batchBindVip(){
		/* var ids=getGridIds();
		if(!id)return false; */
		var title=$("title").html()+" - 批量绑定设备";
		var href=host+'batchBindVipForm';
		$("#button-save").unbind("click").click(userBindSave);
		$(module_dialog).window('resize',{width:600,height:600});
		openDialogForm(title,href);
	}
	
	//绑定游戏设备
	function bindGame(callback){
		var id=getGridId();
		if(!id)return false;
		var title=$("title").html()+" - 绑定游戏设备";
		var href=host+'adminGame?padId='+id;
		$("#button-save").unbind("click").click(userBindSave);
		$(module_dialog).window('resize',{width:600,height:600});
		openDialogForm(title,href);
	}
	
 	//解绑
	function relieve(callback) {
		var ids = getGridIds();
		if (!ids) {
			return false;
		}
		$.messager.confirm('确认？', '确认解绑该设备?', function(confirm) {
				if (confirm) {
			if (ids.indexOf(',') != -1) {
				ajaxPost("relieve", {
					ids : ids
				}, callback);
			} else {
				ajaxPost("relieve", {
					ids : ids
				}, callback);
			}}
		});
	}
	function userBindSave() {
		if ($('#module_submit_form').form("validate")) {
			$.messager.progress();
			$('#module_submit_form').form("submit");
		}
	}
	//设备等级
	function formatterPadGradeStatus(value, row, index) {
		return getDatagridDict('rf_user_pad.pad_grade',value)
	}
	//绑定状态格式化（禁用、可用）
	function formatterBindStatus(value, row, index) {
		return getDatagridDict('rf_pad.bind_status',value);
	}
	//状态格式化（禁用、可用）
	function formatterEnableStatus(value, row, index) {
		return getDatagridDict('rf_pad.enable_status',value);
	}
	//在线状态
	function formatterOnlineStatus(value, row, index) {
		return getDatagridDict('rf_pad.pad_status',value);
	}
	//故障状态
	function formatterFaultStatus(value, row, index) {
		return getDatagridDict('rf_pad.fault_status',value)
	}
	//冻结状态
	function formatterActiveStatus(value, row, index) {
		return getDatagridDict('rf_pad.active_status',value)
	}
	//查看	
	function look() {
		var id = getGridId();
		if (!id) {
			return false;
		}
		var title = $("title").html() + " - 查看";
		var href = host + 'look?' + pk + '=' + id;
		$("#button-save").unbind("click").click(update);
		$(module_dialog).window('resize',{width:600,height:600});
		lookForm(title, href);
	}
	function lookcancel() {
		$("#look").dialog("close");
	}
	//打开编辑对话框
	function lookForm(title, href) {
		$("#look").dialog({title : title,href: href,width:555});
		$("#look").dialog("open");
	}
	function addOnly() {
		var title = $("title").html() + " - 新增";
		var href = host + 'formOnly';
		$("#button-save").unbind("click").click(save);
		$(module_dialog).window('resize',{width:600,height:600});
		openDialogForm(title, href);
	}
	
	function stopOnly() {
 		var id = getGridId();
 		if (!id) {
 			return false;
 		}
 		var title = $("title").html() + " - 禁用";
 		var href = host + 'stopOnlyForm?' + pk + '=' + id;
 		$("#button-save").unbind("click").click(userBindSave);
 		$(module_dialog).window('resize',{width:600,height:600});
 		openDialogForm(title,href);
	}
	
	//激活
	function unlock(callback) {
		var ids = getGridIds();
		if (!ids) {
			return false;
		}
		ajaxPost("active", {
			ids : ids
		}, callback);
	}
	
	//冻结
	function locked(callback) {
		var ids = getGridIds();
		if (!ids) {
			return false;
		}
		ajaxPost("freeze", {
			ids : ids
		}, callback);
	}
	
	function exportCancel() {
		$(module_dialog).dialog('resize',{width:500,height:500});
		$(module_dialog).dialog("close");
	}
	
	function searchFormSubmit() {
		var exportTaskName =  $("#exportTaskName").val();
		if(exportTaskName==''){
			return;
		}
		var pager=$(module_datagrid).datagrid("getPager");
 		var total=$(pager).pagination('options').total;
 		if(total){
 			var where="";
 			var params=$(module_datagrid).datagrid('options').queryParams;
 			for(var i in params){
 				if(i=="remark"||i=="userPadNameT2"||i=="padName"){
 			        where+=i+"="+encodeURI(encodeURI(params[i]))+"&";
 			    }else{
 				   where+=i+"="+params[i]+"&";
 			    }
 			}
 			where+="taskName="+encodeURI(encodeURI(exportTaskName));
 			$('#exportForm').form({
			    url:host+'asyncExport?'+where,
			    success:function(data){
					//alert("成功生成导出任务，请到批处理下的导出管理处下载");
					innerCallback(data, function(){
						$.messager.alert('提示', '成功生成导出任务，请到批处理下的导出管理处下载!', "info");
					});
			    }
			});
			$('#exportForm').submit();
 		}else{
 			$.messager.alert('操作失败','无数据！',"warning");
 		}
		//$(module_dialog).window('resize',{width:600,height:600});
		$("#button-save").unbind("click").click(userBindSave);
	}
	//导出form，输入导出任务名
	function statExportForm() {
 				var title = "导出任务";
 				var href = host + 'exportForm';
 		 		$("#button-save").unbind("click").click(searchFormSubmit);
 		 		$(module_dialog).window('resize',{width:500,height:160});
 				openDialogForm(title, href);
 	}
 	
	
 	
 	function statExport(){
 		var pager=$(module_datagrid).datagrid("getPager");
 		var total=$(pager).pagination('options').total;
 		if(total){
 			
 			var where="";
 			var params=$(module_datagrid).datagrid('options').queryParams;
 			for(var i in params){
 				where+=i+"="+params[i]+"&";
 			}
 			
 			$('#exportForm').form({
			    url:host+'export?'+where,
			    success:function(data){
					//alert("成功生成导出任务，请到批处理下的导出管理处下载");
					innerCallback(data, function(){
						//$.messager.alert('提示', '成功生成导出任务，请到批处理下的导出管理处下载!', "info");
					});
			    }
			});
 			
 			//$("#exportForm").attr("action",host+'export?'+where);
 			/* $("#exportHead").val(exportHead);
 			$("#exportField").val(exportField); */
 			$("#exportName").val('设备');
 			$("#exportForm").submit();
 		}else{
 			$.messager.alert('操作失败','无数据！',"warning");
 		}
 	}
 	
 	
 	function statExportTxt(){
 		var pager=$(module_datagrid).datagrid("getPager");
 		var total=$(pager).pagination('options').total;
 		if(total){
 			
 			var where="";
 			var params=$(module_datagrid).datagrid('options').queryParams;
 			for(var i in params){
 			    if(i=="remark"||i=="userPadNameT2"||i=="padName"){
 			        where+=i+"="+encodeURI(encodeURI(params[i]))+"&";
 			    }else{
 				   where+=i+"="+params[i]+"&";
 			    }
 			}
 			//$("#exportForm").attr("action",host+'exportTxt?'+where);
 			$('#exportForm').form({
			    url:host+'exportTxt?'+where,
			});
 			
 			
 			$("#exportName").val('设备');
 			$("#exportForm").submit();
 		}else{
 			$.messager.alert('操作失败','无数据！',"warning");
 		}
 	}
 	function upVIP() {
 		var id = getGridId();
 		if (!id) {
 			return false;
 		}
 		var title = $("title").html() + " - 编辑";
 		var href = host + 'upVIPForm?' + pk + '=' + id;
 		$("#button-save").unbind("click").click(userBindSave);
 		$(module_dialog).window('resize',{width:600,height:600});
 		openDialogForm(title,href);
 	}
 	
 	//时间格式化
 	 function formatterOnlineTime (value,row,index){
 		if(value){
 			var hour=value/(60*60*1000);
 			var  min =((value/(60*1000))-hour*60);
 			var time=hour+'小时'+min+'分钟';
 			return time;
 		}
 	}
 	
 	 //强制解绑
 	function adminrelieve(callback) {
 		var ids = getGridIds();
 		if (!ids) {
 			return false;
 		}
 		$.messager.confirm('确认？', '强制解绑该设备，设备绑定记录将被清除，是否执行该操作?', function(confirm) {
 				if (confirm) {
 				ajaxPost("adminrelieve", {
 					ids : ids
 				}, callback);
 			}}
 		);
 	}
	
	 //VIP强制解绑
	function relievevip(callback) {
		var ids = getGridIds();
		if (!ids) {
			return false;
		}
		$.messager.confirm('确认？', '强制解绑该VIP设备，设备绑定记录将被清除，是否执行该操作?', function(confirm) {
				if (confirm) {
				ajaxPost("relievevip", {
					ids : ids
				}, callback);
			}}
		);
	}
	 
	 //GVIP强制解绑
	function relieveGvip(callback) {
		var ids = getGridIds();
		if (!ids) {
			return false;
		}
		$.messager.confirm('确认？', '强制解绑该GVIP设备，设备绑定记录将被清除，是否执行该操作?', function(confirm) {
				if (confirm) {
				ajaxPost("relieveGvip", {
					ids : ids
				}, callback);
			}}
		);
	}
 	 
	//管理员绑定设备
 	function adminbinding(){
 		var id=getGridIds();
 		if(!id)return false;
 		var title=$("title").html()+" - 管理员绑定设备";
 		var href=host+'adminBindingForm?padIds='+id;
 		$("#button-save").unbind("click").click(userBindSave);
 		$(module_dialog).window('resize',{width:600,height:600});
 		openDialogForm(title,href);
 	}

	//授权给管理员
 	function grantAdmin(callback){
 		var id=getGridIds();
 		if(!id){return false};
 		if(id.indexOf(",") != -1){
 			$.messager.alert("提示","请只选择一条记录","warning");
 			return false;
 		}
 		var title=$("title").html()+" - 授权给管理员";
 		var href=host+'adminGrantForm?padIds='+id;
 		$("#button-save").unbind("click").click(userBindGrant);
 		$(module_dialog).window('resize',{width:600,height:600});
 		openDialogForm(title,href);
 	}
	
 	function userBindGrant() {
		if ($('#module_submit_form').form("validate")) {
			$.messager.progress();
			$('#module_submit_form').form("submit",{
				  success:callback,
			});
		}
	} 
	
 	
 	//管理员绑定按钮根据选择的设备是否已经绑定来启用或者禁用
	function linkButtonChange(){
 		// 管理员绑定
		var flag = true;
 		// GVIP绑定
		var bindGvipAdminFlag = true;
 		// 升级VIP
		var upVIPFlag = true;
 		// VIP绑定
		var bindAdminFlag = true;
 		// 游戏设备绑定
		var bindGameFlag = true;
 		// 绑定
		var bindingFlag = true;
 		// 解绑VIP
		var relieveVIPFlag = true;
 		// 解绑
		var relieveFlag = true;
 		// 解绑GVIP
		var relieveGVIPFlag = true;
		
		$.each($(module_datagrid).datagrid('getSelections'),function(index,row){
			if(row.bindStatus == '1'){
				flag = false;
			}
			// 绑定状态为已绑定, 故障状态为故障, 设备状态不为在线, 设备类型不为GVIP, 禁用GVIP绑定按钮
			if(row.bindStatus == '1' || row.padStatus != '1' || row.faultStatus == '1' || row.padClassify != '3'){
				bindGvipAdminFlag = false;
			}
			
			// 绑定/VIP绑定
			if(row.bindStatus == '1' || row.padStatus != '1' || row.faultStatus == '1' || row.padClassify != '1'){
				bindingFlag = false;
				bindAdminFlag = false;
			}
			
			// 游戏设备绑定
			if(row.bindStatus == '1' || row.padStatus != '1' || row.faultStatus == '1' || row.padClassify != '2'){
				bindGameFlag = false;
			}
			
			// 升级VIP 
			if(row.bindStatus != '1' || row.padClassify != '1'){
				upVIPFlag = false;
			}
			
			// 解绑/VIP解绑
			if(row.bindStatus != '1' || row.padGradeT2 != '1'){
				//relieveFlag = false;
				relieveVIPFlag = false;
			}
			// GVIP解绑
			if(row.bindStatus != '1' || row.padGradeT2 != '5'){
				relieveGVIPFlag = false;
			}
			
			
			if (!(flag || bindGvipAdminFlag || upVIPFlag || bindingFlag
					|| bindAdminFlag || bindGameFlag || relieveFlag
					|| relieveVIPFlag || relieveGVIPFlag)) {
				return false;
			}

		});
		flag ? $("#adminbinding").linkbutton('enable') : $("#adminbinding").linkbutton('disable');
		bindGvipAdminFlag ? $("#bindGvipAdmin").linkbutton('enable') : $("#bindGvipAdmin").linkbutton('disable');
		upVIPFlag ? $("#upVIP").linkbutton('enable') : $("#upVIP").linkbutton('disable');
		bindingFlag ? $("#binding").linkbutton('enable') : $("#binding").linkbutton('disable');
		bindAdminFlag ? $("#bindAdmin").linkbutton('enable') : $("#bindAdmin").linkbutton('disable');
		bindGameFlag ? $("#bindGame").linkbutton('enable') : $("#bindGame").linkbutton('disable');
		relieveFlag ? $("#relieve").linkbutton('enable') : $("#relieve").linkbutton('disable');
		relieveVIPFlag ? $("#relieveVIP").linkbutton('enable') : $("#relieveVIP").linkbutton('disable');
		relieveGVIPFlag ? $("#relieveGVIP").linkbutton('enable') : $("#relieveGVIP").linkbutton('disable');
	}
 	
 	//查看设备
 	function lookGrantCode(){
 		var id=getGridId();
 		if(!id)return false;
 		var title=$("title").html()+" - 查看授权编码";
 		var href=host+'lookGrantCode?' + pk + '=' + id;
 		$("#button-save").unbind("click").click(userBindSave);
 		$(module_dialog).dialog({title : title,href: href,width:800});
 		$(module_dialog).window('resize',{width:800,height:600});
		$(module_dialog).dialog("open");
 	}
 	
 	//修改在线时间
 	function onlinetime() {
		var id = getGridId();
		if (!id) {
			return false;
		}
		var title = "修改在线时间";
		var href = host + 'onlinetimeForm?' + pk + '=' + id;
 		$("#button-save").unbind("click").click(userBindSave);  
 		$(module_dialog).window('resize',{width:600,height:600});
		openDialogForm(title, href); 
	}
	
	//修改设备状态
	function updatePadStatus(){
		var id = getGridId();
		if (!id) {
			return false;
		}
		var title = "重新获取设备状态";
		var href = host + 'updatePadStatus?' + pk + '=' + id;
		
		$.messager.confirm('确认？', '重新获取设备状态，是否执行该操作?', function(confirm) {
				if (confirm) {
				ajaxPost("updatePadStatus", {
					padId : id
				}, callback);
			}}
		);
	}
 	
 	//修改控制天数
 		function controltime() {
 				var id = getGridId();
 				if (!id) {
 					return false;
 				}
 				var title = "修改控制天数";
 				var href = host + 'controltimeForm?' + pk + '=' + id;
 		 		$("#button-save").unbind("click").click(userBindSave);  
 		 		$(module_dialog).window('resize',{width:600,height:600});
 				openDialogForm(title, href); 
 			}
 		//批量修改控制天数
 		function controls() {
 				var title = "批量修改控制天数";
 				var href = host + 'controlsForm';
 		 		$("#button-save").unbind("click").click(userBindSave);  
 		 		$(module_dialog).window('resize',{width:600,height:600});
 				openDialogForm(title, href); 
 			}
 	 	//批量修改在线时间
 		function onlines() {
		var title = "批量修改在线时间";
		var href = host + 'onlinesForm';
 		$("#button-save").unbind("click").click(userBindSave);  
 		$(module_dialog).window('resize',{width:600,height:600});
		openDialogForm(title, href); 
	}
 		//批量操作设备
 		function batchPad() {
 				var title = "批量操作设备";
 				var href = host + 'batchForm';
 		 		$("#button-save").unbind("click").click(userBindSave);  
 		 		$(module_dialog).window('resize',{width:600,height:600});
 				openDialogForm(title, href); 
 			}
 	//虚拟设备开放	
 		var openOn = function(callback) {
 			var ids = getGridIds();
 			if (!ids) {
 				return false;
 			}
 			ajaxPost("openOn", {
 				ids : ids
 			}, callback);
 		}
 	//虚拟设备部开放
 		var openOff = function(callback) {
 			var ids = getGridIds();
 			if (!ids) {
 				return false;
 			}
 			ajaxPost("openOff", {
 				ids : ids
 			}, callback);
 		}

	//打开数据汇总
	function openData() {
		getFloatData();
		$('#floatData').window('open');
		return false;
	}
	//关闭数据汇总
	function closeData() {
		$('#floatData').window('close');
		return false;
	}

	//批量操作更换设备
	function renewalPads() {
		var title = "批量操作设备";
		var href = host + 'renewalForm';
		$("#button-save").unbind("click").click(userBindSave);
		$(module_dialog).window('resize',{width:600,height:600});
		openDialogForm(title, href);
	}

	//设备维护
	var maintainOn = function(callback) {
		var maintStatus=getDictByKey("rf_pad.maint_status@on");
		var ids = getGridIds();
		if (!ids) {
			return false;
		}
		ajaxPost("maintainOn", {
			ids : ids,
			maintStatus:maintStatus
		}, callback);
	}
	//取消维护
	var maintainOff = function(callback) {
		var maintStatus=getDictByKey("rf_pad.maint_status@off");
		var ids = getGridIds();
		if (!ids) {
			return false;
		}
		ajaxPost("maintainOff", {
			ids : ids,
			maintStatus:maintStatus
			
		}, callback);
	}
	
	//批量维护
	function batchMaintain(){
		var title = "批量维护";
		var href = host + 'batchMaintainForm';
 		$("#button-save").unbind("click").click(userBindSave);  
 		$(module_dialog).window('resize',{width:600,height:600});
		openDialogForm(title, href); 
	}
	
	var countPads=function (newValue, oldValue) {	
		var padlist;
		padlist= newValue.split(",").length;
		$('#padRow').numberbox('setValue',padlist);
		}


	var countDevices = function(newValue,oldValue) {
         var padlist;
         padlist=newValue.split(",").length;
         $('#deviceRow').numberbox('setValue',padlist);
	}
	
	//设备操作
	var padTask = function() {
	
		var id = getGridId();
		if (!id) {
			return false;
		}
		var title = $("title").html() + " -设备操作";
		var href = host + 'formPad?padId='+id;
		$(module_dialog).window('resize',{width:600,height:600});
		noDialogForm(title, href);
	}
   
    var noDialogForm=function(title,href){
	   $("#module_dialog").dialog({title:title,href:href,width:700});
	   $(module_dialog).window('resize',{width:700,height:600});
	   $("#module_dialog").dialog("open");
    }
    
    //绑定标签
	function bindLabel(){
		var id=getGridId();
		if(!id)return false;
		var title=$("title").html()+" - 绑定标签";
		var href=host+'bindLabelForm?padId='+id;
		$("#button-save").unbind("click").click(userBindSave);
		$(module_dialog).window('resize',{width:600,height:600});
		openDialogForm(title,href);
	}
	
	//批量绑定标签
	function batchBindLabel(){
		var title = "批量绑定标签";
		var href = host + 'batchBindLabelForm';
 		$("#button-save").unbind("click").click(userBindSave); 
 		$(module_dialog).window('resize',{width:600,height:600}); 
		openDialogForm(title, href); 
	}
	
	// 显示隐藏更多筛选条件
	function moreSearch(obj){
		var title = obj.innerHTML;
		if(title == "更多筛选条件"){
			obj.innerHTML = "精简筛选条件";
		}
		if(title == "精简筛选条件"){
			obj.innerHTML = "更多筛选条件";
		}
		$(".more_search").toggle();
	}
	
	//grid搜索
	var gridSearchValidate = function() {
		var arr = $(module_search_form).serializeArray();
		var obj = {};
		$.each(arr, function(i, field) {
			obj[field.name] = field.value;
		});
		if(!($('#module_search_form').form("validate"))){
			return false;
			}
		try{
			$(module_datagrid).treegrid("reload",obj);
		}catch(e){
			$(module_datagrid).datagrid("reload",obj);
		}
	}
</script>
<style>
	.more_search{display: none;}
	body{overflow-x:hidden}
</style>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >

		<div id="module_search">
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">
					会员ID：<input type="text" name="externalUserId"  class="easyui-numberbox input_width_short" />
			</div>
			<div class="module_search_input">
				用户手机号码:<input type="text" name="userMobilePhone" class="easyui-textbox input_width_default" />
			</div>
			<div class="module_search_input">
				<!-- <textarea name="padCodes" wrap=PHYSICAL style="height: 20px;" onKeyDown="countPads(this.form.padCodes,this.form.padRow);"onKeyUp="countPads(this.form.padCodes,this.form.padRow);"></textarea> -->
				导入编号(虚拟):<input name="padCodes" class="easyui-textbox" data-options="onChange:countPads," />
			 	<input type="text" id="padRow" class="easyui-numberbox" style="width: 20px" data-options="editable:false," />
			</div>
			<div class="module_search_input">		
				导入编号(物理):<input name="deviceCodes" class="easyui-textbox" data-options="onChange:countDevices," />
			 	<input type="text" id="deviceRow" class="easyui-numberbox" style="width: 20px"  data-options="editable:false,"/>
			</div>
			<div class="module_search_input">
				机房:<select editable="false" name="idcId" class="easyui-combobox input_width_default" >
					<option value="">[全部]</option>
					<c:forEach var="one" items="${idcList}">
						<option value="${one.idcId}">${one.idcName}</option>
					</c:forEach>
				</select>
			</div>
			<div class="module_search_input">
				ROM版本:<select class="easyui-combobox input_width_short" data-options="editable:false,required:false" name="romVersion" >
					<option value="">[全部]</option>
				    <fns:getOptions category="rf_game.device_version"></fns:getOptions>
				</select> 
			</div>
			<div class="module_search_input">
				设备节点:<select editable="false" name="padControlId" class="easyui-combobox input_width_default"  style="width:150px">
					<option value="">[全部]</option>
					<c:forEach var="one" items="${controlList}">
						<option value="${one.controlId}">${one.controlName}</option>
					</c:forEach>
				</select>
			</div>
			<div class="module_search_input" >		
				 启用:<select class="easyui-combobox input_width_short" editable="false" name="enableStatus" data-options="required:false">
					<option value="">[全部]</option>
					<fns:getOptions category="rf_pad.enable_status"></fns:getOptions>
				</select> 
			</div>	  
			<div class="module_search_input">		
				绑定:<select class="easyui-combobox input_width_short" editable="false" name="bindStatus" data-options="required:false" >
					<option value="">[全部]</option>
					<fns:getOptions category="rf_pad.bind_status"></fns:getOptions>
				</select> 
			</div>	  
			<div class="module_search_input">		
				故障:<select class="easyui-combobox input_width_short" editable="false" name="faultStatus" data-options="required:false" >
					<option value="">[全部]</option>
					<fns:getOptions category="rf_pad.fault_status"></fns:getOptions>
				</select> 
			</div>
			<div class="module_search_input">		
				 授权状态:<select class="easyui-combobox input_width_short" editable="false" name="grantOpenStatus" data-options="required:false">
					<option value="">[全部]</option>
					<fns:getOptions category="rf_pad.grant_open_status"></fns:getOptions>
				</select> 
			</div>
			<div class="module_search_input">		
				 维护状态:<select class="easyui-combobox input_width_short" editable="false" name="maintStatus" data-options="required:false">
					<option value="">[全部]</option>
					<fns:getOptions category="rf_pad.maint_status"></fns:getOptions>
				</select> 
			</div>	
			<div class="module_search_input">		
				 虚拟状态:<select class="easyui-combobox input_width_short" editable="false" name="vmStatus" data-options="required:false">
					<option value="">[全部]</option>
					<fns:getOptions category="rf_pad.vm_status"></fns:getOptions>
				</select> 
			</div>	
			<div class="module_search_input">		
				 受控状态:<select class="easyui-combobox input_width_short" editable="false" name="padStatus" data-options="required:false">
					<option value="">[全部]</option>
					<fns:getOptions category="rf_pad.pad_status"></fns:getOptions>
				</select> 
			</div>	
			<div class="module_search_input">		
				使用状态:<select class="easyui-combobox input_width_short" editable="false" name="leftOnlineTimeT2" data-options="required:false">
					<option value="">[全部]</option>
				    <option value="0">正常使用期</option>
				    <option value="-3">已过使用期</option>
					<!--  <option value="-4">已过期</option> -->
				</select> 
			</div>
			<div class="module_search_input" >		
				设备等级:<select class="easyui-combobox input_width_short" editable="false" name="padGradeT2" data-options="required:false">
					<option value="">[全部]</option>
					<fns:getOptions category="rf_user_pad.pad_grade"></fns:getOptions>
				</select> 
			</div>	
			<div class="module_search_input">
				设备来源:<select editable="false" name="padSource" class="easyui-combobox input_width_default" style="width:100px">
					<option value="">[全部]</option>
					<c:forEach var="one" items="${facilityList}">
						<option value="${one.facilityCode}">${one.facilityName}</option>
					</c:forEach>
				</select>
			</div>
			
			<div class="module_search_input">
				设备类别:
				<select class="easyui-combobox"  editable="false" name="padClassify" data-options="required:true">
					<option value="">[全部]</option>
						<fns:getOptions category="rf_pad.pad_classify" value="${bean.padClassify}" keys="rf_pad.pad_classify@major,rf_pad.pad_classify@game,rf_pad.pad_classify@gvip,rf_pad.pad_classify@svip,rf_pad.pad_classify@cloud"></fns:getOptions>
				</select> 
			</div>
		<div  class="module_search_input">
				控制协议<select class="easyui-combobox"  editable="false" name="controlProtocol" data-options="required:true">
					<option value="">[全部]</option>
						<fns:getOptions category="rf_pad.control_protocol" value="${bean.controlProtocol}" keys="rf_pad.control_protocol@one,rf_pad.control_protocol@two"></fns:getOptions>
						 <option value="null">空白</option>
				</select> 
			</div>
			<!-- <div class="module_search_input">会员:<input type="text" name="userIdT2"class="easyui-textbox input_width_default"/></div> -->
			<!-- <div class="module_search_input">手机号:<input type="text" name="bindMobileT2"class="easyui-textbox input_width_default" /></div> -->
			    
			<!--  
			<div class="module_search_input">		
				设备类型:<select class="easyui-combobox input_width_short" editable="false" name="type" data-options="required:false">
					<option value="">[全部]</option>
				    <option value="0">老设备</option>
				    <option value="1">虚拟设备</option>
				</select> 
		    </div> -->
				 
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-search-rf" plain="false" onclick="gridSearchValidate()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
				<a href="#" title="搜索：Enter<br/>清空：Delete<br/>打开/刷新汇总：+/pagedown<br/>关闭汇总：-/pageup<br/>关闭弹出窗：Esc" class="easyui-tooltip">快捷键</a>
				<a href="javascript:void(0)" onclick="moreSearch(this)">更多筛选条件</a>
			</div>
			
			<div class="more_search" style="float:left;">
				<div class="module_search_input">
					编码段:<input type="text" name="padCode" class="easyui-textbox input_width_default" />至
					<input type="text" name="padCode2" class="easyui-textbox input_width_default" />
				</div>
				<div class="module_search_input">		
					 设备SN:<input type="text" name="padSn" class="easyui-numberbox" style="width: 20px" />
				</div>	
				<div class="module_search_input">
					虚拟机IP:<input type="text" name="padIp" class="easyui-textbox input_width_default" />
				</div>
				<div class="module_search_input">
					设备名称:<input type="text" name="padName" class="easyui-textbox input_width_default" />
				</div>
				<div class="module_search_input">
					用户设备名称:<input type="text" name="userPadNameT2" class="easyui-textbox input_width_default" />
				</div>
				<div class="module_search_input">
					最后受控时间:
					<input type="text" class="easyui-datebox input_width_default" editable="false" name="padControlTimeBegin" />
					至
					<input type="text" class="easyui-datebox input_width_default" editable="false" name="padControlTimeEnd"/>
				</div>  
				<div class="module_search_input">
					绑定时段:
					<input type="text" class="easyui-datebox input_width_default" editable="false" name="beginTimeStr" />
					至
					<input type="text" class="easyui-datebox input_width_default" editable="false" name="endTimeStr"/>
				</div>
				<div class="module_search_input">
					更换时段:
					<input type="text" class="easyui-datebox input_width_default" editable="false" name="renewalBeginTimeStr" />
					至
					<input type="text" class="easyui-datebox input_width_default" editable="false" name="renewalEndTimeStr"/>
				</div>
				<div class="module_search_input">		
					设备过期时间:
					<input type="text" class="easyui-datetimebox input_width_default" editable="false" name="expireTimeBegin" />
					至
					<input type="text" class="easyui-datetimebox input_width_default" editable="false" name="expireTimeEnd"/>
				</div>
				<div class="module_search_input">		
					物理IP段:
					<input type="text" class="easyui-textbox input_width_default" editable="true" name="deviceStartIp" value="" />
					至
					<input type="text" class="easyui-textbox input_width_default" editable="true" name="deviceEndIp" value=""/>
				</div>
				<div class="module_search_input">
					备注:<input type="text" name="remark" class="easyui-textbox input_width_default" />
				</div>
			</div>
		</form>
		</div>

<!--    <div id="mm" class="easyui-menu" style="width:120px;">
          <div>New</div>
          <div>
             <span>Open</span>
              <div style="width:150px;">
                    <div><b>Word</b></div>
                    <div>Excel</div>
                    <div>PowerPoint</div>
              </div>
          </div>
          <div iconCls="icon-save">Save</div>
          <div class="menu-sep"></div>
        <div>Exit</div>
    </div> -->
    
	 <div id="module_toolbar" class="easyui-toolbar" style="height: auto">

	       <c:if test="${not empty sessionScope.permission.button_facility_pad_export}">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel-rf" plain="true" id="export-link">导出</a>
		   </c:if>
			<c:if test="${not empty sessionScope.permission.button_facility_pad_addfacility}">
<!-- 				<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-add-rf" plain="true" onclick="addOnly()">新增</a>  -->
<!-- 				<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-addBatch-rf" plain="true" onclick="add()">批量新增</a>  -->
			</c:if>
			<c:if test="${not empty sessionScope.permission.button_facility_pad_update}">
				<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a>
			</c:if> 
			<c:if test="${not empty sessionScope.permission.button_facility_pad_start}">
				<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-start-rf" plain="true" id="start" onclick="start(callback)">启用</a>
			</c:if>
			<c:if test="${not empty sessionScope.permission.button_facility_pad_stop}">
				<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-stop-rf" plain="true" onclick="stopOnly()">禁用</a>
			</c:if>
			<c:if test="${not empty sessionScope.permission.button_facility_pad_relieve}">
		    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-unlink-rf" plain="true" id="relieve" onclick="relieve(callback)">解绑</a>
		    </c:if>
		    <c:if test="${not empty sessionScope.permission.button_facility_pad_binding}">
		    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-link-rf" plain="true" id="binding" onclick="binding(callback)">绑定</a>
		    </c:if>
			<c:if test="${not empty sessionScope.permission.button_facility_pad_delete}">
				<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a>
            </c:if>	
            <c:if test="${not empty sessionScope.permission.button_facility_pad_bindAdmin}">
			 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-vip-rf" plain="true" id="bindAdmin" onclick="bindAdmin(callback)">绑定VIP</a>
            </c:if>	
            <%--绑定GVIP按钮 --%>
            <c:if test="${not empty sessionScope.permission.button_facility_pad_bindAdmin}">
			 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-gvip-rf" plain="true" id="bindGvipAdmin" onclick="bindGvipAdmin(callback)">绑定GVIP</a>
            </c:if>	
            <c:if test="${not empty sessionScope.permission.button_facility_pad_upVIP}">
			 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-vip-rf" plain="true" id="upVIP" onclick="upVIP(callback)">升级VIP</a>
            </c:if>	
            <c:if test="${not empty sessionScope.permission.button_facility_pad_adminbinding}">
			 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-link-rf" plain="true" id="adminbinding" onclick="adminbinding(callback)">管理员绑定</a>
            </c:if>	
            <c:if test="${not empty sessionScope.permission.button_facility_pad_adminrelieve}">
			 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-unlink-rf" plain="true" id="adminrelieve" onclick="adminrelieve(callback)">管理员解绑</a>
            </c:if>	
            <c:if test="${not empty sessionScope.permission.button_facility_pad_relievevip}">
			 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-unlink-rf" plain="true" id="relieveVIP" onclick="relievevip(callback)">VIP解绑</a>
            </c:if>	
            <%--GVIP解绑 --%>
            <c:if test="${not empty sessionScope.permission.button_facility_pad_relievevip}">
			 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-unlink-rf" plain="true" id="relieveGVIP" onclick="relieveGvip(callback)">GVIP解绑</a>
            </c:if>	
            <c:if test="${not empty sessionScope.permission.button_facility_pad_onlinetime}">
				<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-add-rf" plain="true" onclick="onlinetime()">增加时间</a> 
			</c:if>
			<c:if test="${not empty sessionScope.permission.button_facility_pad_controltime}">
				<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-add-rf" plain="true" onclick="controltime()">增加天数</a> 
			</c:if>
			<c:if test="${not empty sessionScope.permission.button_facility_pad_onlines}">
				<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-add-rf" plain="true" onclick="onlines()">批量增加时间</a> 
			</c:if>
            <c:if test="${not empty sessionScope.permission.button_facility_pad_controls}"> 
				<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-add-rf" plain="true" onclick="controls()">批量增加天数</a> 
		    </c:if> 
		    <c:if test="${not empty sessionScope.permission.button_facility_pad_batchPad}"> 
		    <a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-add-rf" plain="true" onclick="batchPad()">批量操作设备</a> 
		    </c:if>
		  <a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-start-rf" plain="true" id="start" onclick="openOn(callback)">授权虚拟设备开放</a>
		  <a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-start-rf" plain="true" id="start" onclick="openOff(callback)">取消虚拟设备授权</a>
		  <a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-start-rf" plain="true" id="start" onclick="maintainOn(callback)">设备维护</a>
		  <a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-start-rf" plain="true" id="start" onclick="maintainOff(callback)">取消维护</a>
		  <a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-start-rf" plain="true" id="start" onclick="batchMaintain(callback)">批量维护</a>
	      <a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-search" plain="true" onclick="look()">查看</a> 
		  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload-rf',plain:true" onclick="fresh()">刷新</a>
          <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload-rf',plain:true" onclick="openData()">打开数据汇总</a>
          <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload-rf',plain:true" onclick="closeData()">关闭数据汇总</a>
          <c:if test="${not empty sessionScope.permission.button_facility_pad_renewal}"> 
          <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload-rf',plain:true" onclick="renewalPads()">更换设备</a>
          </c:if>
          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="lookGrantCode(callback)">查看授权编码</a>
          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload-rf" plain="true" onclick="updatePadStatus(callback)">重新获取设备状态</a>
          <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-star-rf',plain:true" onclick="padTask()" >设备操作</a>
          <c:if test="${not empty sessionScope.permission.button_facility_pad_bindGame}">
			 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-link-rf" plain="true" id="bindGame" onclick="bindGame(callback)">绑定游戏设备</a>
          </c:if>	
          <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit-rf',plain:true" onclick="columnDefined(currentUserId,initColumnMap,pk,flag)">列表自定义</a>
          <c:if test="${not empty sessionScope.permission.button_facility_pad_batchBindPad}">
		 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-vip-rf" plain="true" id="batchBindVip" onclick="batchBindVip(callback)">批量绑定VIP</a>
          </c:if>	
          <c:if test="${not empty sessionScope.permission.button_facility_pad_bindLabel}">
		    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-link-rf" plain="true" id="bindLabel" onclick="bindLabel(callback)">绑定标签</a>
		  </c:if>
		  <c:if test="${not empty sessionScope.permission.button_facility_pad_BatchLabel}">
		    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-link-rf" plain="true" id="bindLabel" onclick="batchBindLabel(callback)">批量绑定标签</a>
		  </c:if>
		  <c:if test="${not empty sessionScope.permission.button_facility_pad_grantAdmin}">
		    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-link-rf" plain="true" id="grantAdmin" onclick="grantAdmin(callback)">授权给管理员</a>
		  </c:if>
        	<!--   <c:if test="${not empty sessionScope.permission.button_facility_pad_freeze}">
				<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-locked-rf" plain="true" onclick="locked(callback)">冻结</a>
			</c:if>
			<c:if test="${not empty sessionScope.permission.button_facility_pad_active}">
				<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-unlock-rf" plain="true" onclick="unlock(callback)">激活</a>
			</c:if> -->
		  	
	     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="statExportTxt()">IP-MAC导出</a>

	  </div>
		<table id="module_datagrid" toolbar="#module_toolbar" ></table>
	</div>

	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
	<div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save"
			class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a> <a
			href="javascript:void(0)" id="button-cancel"
			class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
	<!-- 列表自定义编辑框 -->
	<div id="module_column_defined_dialog" buttons="#module_column_defined_dialog_button"></div>
	<div style="text-align:center" id="module_column_defined_dialog_button">
		<a href="javascript:void(0)" id="buttone-column-defined-save-select-all"class="easyui-linkbutton" iconCls="icon-edit-rf" onclick="selectAllColumn()">全选</a>
	    <a href="javascript:void(0)" id="buttone-column-defined-save"class="easyui-linkbutton" iconCls="icon-ok-rf">确定</a>
	    <a href="javascript:void(0)" id="button-column-defined-cancel"class="easyui-linkbutton" iconCls="icon-reload-rf" onclick="resetColumn(currentUserId,initColumnMap,pk,flag)">重置</a>
	</div>
	<!-- 列表自定义编辑框  end-->
	<div id="look" buttons="#look_button"  data-options="maximizable:true"></div>
	<div id="look_button">
    	<a href="javascript:void(0)"  class="easyui-linkbutton" iconCls="icon-no" onclick="lookcancel()">关闭</a>
	</div>
	<div class="hide">
	<form action="" id="exportForm" method="post" target="_blank">
		<input type="hidden" name="exportDatas" id="exportDatas" value=""/>
		<!-- 优化了列表，隐藏了部分字段，所以把表头写死,无需动态获取表头 - 2017年6月2日17:33:25
		<input type="hidden" name="exportHead" id="exportHead" value=""/>
		<input type="hidden" name="exportField" id="exportField" value=""/> -->
		<input type="hidden" name="exportName" id="exportName" value=""/>
	</form>
	</div>
	<!-- 浮动数据 -->
	<div id="floatData" class="easyui-window" title="设备数据汇总" data-options="width:'580px',iconCls:'icon-save',modal:false,minimizable:false,maximizable:false,resizable:true,closed:true" >
        <div class="module_search_input">
				设备类别:
				<select  editable="false" id="padClassify_collect" class="easyui-combobox" >
					<option value="">全部</option>
						<fns:getOptions category="rf_pad.pad_classify" value="${bean.padClassify}" keys="rf_pad.pad_classify@major,rf_pad.pad_classify@game,rf_pad.pad_classify@gvip,rf_pad.pad_classify@svip"></fns:getOptions>
				</select> 
				&nbsp;&nbsp;
				机房:<select editable="false" id="idcId_collect" class="easyui-combobox" >
					<option value="">全部</option>
					<c:forEach var="one" items="${idcList}">
						<option value="${one.idcId}">${one.idcName}</option>
					</c:forEach>
				</select>&nbsp;&nbsp;
				<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-search-rf" plain="false" onclick="getFloatData()">搜索</a>
			</div>
        
        <div>当前设备总数：&nbsp;&nbsp;总数(<span class='red' id="floatTotal">-</span>)&nbsp;,&nbsp;在线(<span class='red' id="floatTotal2">-</span>)&nbsp;,&nbsp;离线(<span class='red' id="floatTotal3">-</span>)&nbsp;&nbsp;</div>
        <div>当前可用设备：&nbsp;&nbsp;总数(<span class='red' id="floatEnable">-</span>)&nbsp;,&nbsp;在线(<span class='red' id="floatEnable2">-</span>)&nbsp;,&nbsp;离线(<span class='red' id="floatEnable3">-</span>)&nbsp;&nbsp;</div>
        <div>当前禁用设备：&nbsp;&nbsp;总数(<span class='red' id="floatUnable">-</span>)&nbsp;,&nbsp;在线(<span class='red' id="floatUnable2">-</span>)&nbsp;,&nbsp;离线(<span class='red' id="floatUnable3">-</span>)&nbsp;&nbsp;</div>
        <div>绑定设备总数：&nbsp;&nbsp;总数(<span class='red' id="bindingPad4">-</span>)&nbsp;,&nbsp;
	         VIP(<span class='red' id="bindingPad" >-</span>/<span class='red' id="bindingPad1" >-</span>)&nbsp;,&nbsp;
	                         普通(<span class='red' id="bindingPad2" >-</span>/<span class='red' id="bindingPad3" >-</span>)&nbsp;,&nbsp;
	        SVIP(<span class='red' id="bindingPad5" >-</span>/<span class='red' id="bindingPad6" >-</span>)&nbsp;,&nbsp;
	        GVIP(<span class='red' id="bindingPad7" >-</span>/<span class='red' id="bindingPad8" >-</span>)&nbsp;,&nbsp;
	                       体验(<span class='red' id="bindingPad9" >-</span>/<span class='red' id="bindingPad10" >-</span>)&nbsp;&nbsp;
        </div>
        <div>未到期设备总数：&nbsp;&nbsp;总数(<span class='red' id="noExpire">-</span>)&nbsp;,&nbsp;
       	    VIP(<span class='red' id="noExpire1" >-</span>/<span class='red' id="noExpire2" >-</span>)&nbsp;,&nbsp;
        	普通(<span class='red' id="noExpire3" >-</span>/<span class='red' id="noExpire4" >-</span>)&nbsp;,&nbsp; 
            SVIP(<span class='red' id="noExpire5" >-</span>/<span class='red' id="noExpire6" >-</span>)&nbsp;,&nbsp; 
        	GVIP(<span class='red' id="noExpire7" >-</span>/<span class='red' id="noExpire8" >-</span>)&nbsp;,&nbsp; 
        	体验(<span class='red' id="noExpire9" >-</span>/<span class='red' id="noExpire10" >-</span>)&nbsp;&nbsp; 
        </div>
        <div>当前到期设备：&nbsp;&nbsp;总数(<span class='red' id="floatExpire">-</span>)&nbsp;,&nbsp;
	        VIP(<span class='red' id="floatExpire2">-</span>/<span class='red' id="floatExpire5">-</span>)&nbsp;,&nbsp;
	                       普通(<span class='red' id="floatExpire3">-</span>/<span class='red' id="floatExpire4">-</span>)&nbsp;,&nbsp;
	        SVIP(<span class='red' id="floatExpire6">-</span>/<span class='red' id="floatExpire7">-</span>)&nbsp;,&nbsp;
	        GVIP(<span class='red' id="floatExpire8">-</span>/<span class='red' id="floatExpire9">-</span>)&nbsp;,&nbsp;
	                       体验(<span class='red' id="floatExpire10">-</span>/<span class='red' id="floatExpire11">-</span>)&nbsp;&nbsp;
        </div>   
        <div>当日到期设备：&nbsp;&nbsp;总数(<span class='red' id="todayExpire">-</span>)&nbsp;,&nbsp;
           VIP(<span class='red' id="todayExpire2">-</span>)&nbsp;,&nbsp;
                                普通(<span class='red' id="todayExpire1">-</span>)&nbsp;,&nbsp;
           SVIP(<span class='red' id="todayExpire3">-</span>)&nbsp;,&nbsp;
           GVIP(<span class='red' id="todayExpire4">-</span>)&nbsp;&nbsp;
                               体验(<span class='red' id="todayExpire5">-</span>)&nbsp;&nbsp;
        </div>   
        <div>当前故障设备：&nbsp;&nbsp;总数(<span class='red' id="floatFault">-</span>)&nbsp;,&nbsp;
           VIP(<span class='red' id="floatFault2">-</span>/<span class='red' id="floatFault3">-</span>)&nbsp;,&nbsp;
                                普通(<span class='red' id="floatFault5">-</span>/<span class='red' id="floatFault4">-</span>) &nbsp;,&nbsp;
           SVIP(<span class='red' id="floatFault6">-</span>/<span class='red' id="floatFault7">-</span>) &nbsp;,&nbsp;
           GVIP(<span class='red' id="floatFault8">-</span>/<span class='red' id="floatFault9">-</span>) &nbsp;,&nbsp;
                                 体验(<span class='red' id="floatFault10">-</span>/<span class='red' id="floatFault11">-</span>) &nbsp;&nbsp;
        </div>
	</div>
</body>
</html>



