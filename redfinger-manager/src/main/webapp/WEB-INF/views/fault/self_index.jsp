<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>我的故障工单</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="faultFeedbackId";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var formatterFlag=function(value,row,index){
		return getDatagridDict('rf_fault_feedback.feedback_status',value);
	}
	var dataGridParamObj={
		url:host+"list",
		idField : pk,
		onCheck: function(index,row){
			
		},
		singleSelect:false,
		columns:[[
					{width:100,title:'id',field:pk,checkbox:true},
					{width:60,title:'状态',field:'feedbackStatus',sortable:true,formatter:formatterFlag},
					{width:100,title:'设备编码',field:'padCode',sortable:true},
					{width:100,title:'设备等级',field:'map.padGrade',formatter :function(value){return getDatagridDict('rf_user_pad.pad_grade',value);}},
					{width:100,title:'设备IP',field:'map.padIp'},
					{width:100,title:'当前绑定状态',field:'map.bindStatus',formatter :function(value){return getDatagridDict('rf_pad.bind_status',value);}},
					{width:100,title:'故障时间',field:'createTime',sortable:true,formatter:formatterTime},
					{width:100,title:'故障类型',field:'map.className'},
					{width:100,title:'故障描述',field:'feedbackContent',sortable:true},
					{width:100,title:'修复类型',field:'map.fixName'},
					{width:100,title:'修复内容',field:'feedbackHandle',sortable:true},
					{width:100,title:'来源',field:'feedbackSource',sortable:true,formatter:function(value){return getDatagridDict('rf_fault_feedback.feedback_source',value);}},
					{width:100,title:'联系电话',field:'feedbackContact',sortable:true},
					{width:100,title:'联系QQ',field:'feedbackQq',sortable:true},
					{width:100,title:'咨询',field:'map.promoter'},
					{width:100,title:'操作来源',field:'map.clientSourceName'},
					{width:100,title:'备注',field:'remark'},
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
		}
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
		$("#noSave_dialog").dialog($.extend({},dialogParam,dialogParamObj));
	});
	// 处理
	var fixForm = function() {
		var ids = getGridIds();
		if (!ids) {
			return false;
		}
		if(ids.split(",").length>2000){
			$.messager.alert('提示', '批量处理不能超过2000！', "info");
			return false;
		}
		var title =	"故障处理";
		var href = host + 'fixForm?ids='+ids;
		$("#button-save").unbind("click").click(fix);
		openDialogForm(title, href);
	}
	
	//在线状态
	function formatterOnlineStatus(value, row, index) {
		return getDatagridDict('rf_pad.pad_status',value);
	}
	
	var fix = function(){
		if ($(module_submit_form).form("validate")) {
			$.messager.progress();
			$(module_submit_form).form({
				url : host + 'fix'
			});
			$(module_submit_form).form("submit");
		}
	}
	function detail(){
		var id = getGridId();
		if (!id) {
			return false;
		}
		var title =	"故障详情";
		var href = host + 'detail?faultFeedbackId='+id;
		$("#button-save").unbind("click").click(cancel);
		openDialogForm(title, href);
	}
	

	function userBindSave(){
		if($('#module_submit_form').form("validate")){
			if($("#newPadId").val()==''){
				alert("请先更换设备再保存");
				return;
			}
			$.messager.progress();
			$('#module_submit_form').form("submit");
		}
	}
	//更换renewal
	function renewal(){
		var id=getGridId();
		if(!id)return false;
		var title=$("title").html()+" - 更换设备";
		var href=host+'renewalForm?faultFeedbackId='+id;
		$("#button-save").unbind("click").click(userBindSave);
		 openRelieveForm(title,href);
	}
	 //解绑
	function relieve(callback) {
		var ids= getGridId();
		if (!ids) {
			return false;
		}
		$.messager.confirm('确认？', '确认解绑该设备?', function(confirm) {
				if (confirm) {
				ajaxPost("relieve", {
					ids : ids
				}, configCallback/* callback */);
		}
		});
	}
	function openRelieveForm(title, href) {
		$(module_dialog).dialog({title : title,href: href,width:600});
		$(module_dialog).dialog("open");
	
	}
   //设备操作
	var padTask = function() {
		var id = getGridId();
		if (!id) {
			return false;
		}
		var title = $("title").html() + " -设备操作";
		var href = host + 'formPad?faultFeedbackId='+id;
		noSaveDialogForm(title, href);
	}
   
   var noSaveDialogForm=function(title,href){
	  $("#noSave_dialog").dialog({title:title,href:href,width:700});
	  $("#noSave_dialog").dialog("open");
   }
   
   //关闭没有保存按钮的对话框
   var noSaveCancel = function() {
	   if(typeof noSave_dialog!='undefined' && $(noSave_dialog)){
	   		$(noSave_dialog).dialog("close");
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
 			$("#exportName").val('我的故障工单');
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
			//加入lastHandler搜索条件
			var lastHandler = '${user.adminCode}';
			resArr.push("\"" + "lastHandler" +"\":" + "\"" +lastHandler+ "\"");
			
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
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" id="export-link" iconCls="icon-excel-rf" plain="true">导出</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-tools-rf" plain="true" onclick="fixForm()">处理</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" id="edit" iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a>
	        <c:if test="${not empty sessionScope.permission.button_fault_pad_relieve}">
		    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-unlink-rf" plain="true" onclick="relieve(callback)">解绑</a>
		    </c:if>
		     <c:if test="${not empty sessionScope.permission.button_fault_pad_renewal}">
		      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-star-rf" plain="true" onclick="renewal(callback)">更换</a>
		      </c:if>
		    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-star-rf',plain:true" onclick="padTask()" >设备操作</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar" ></table>
	</div>
		
	
	
	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
    <div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton" iconCls="icon-ok-rf">确定</a>
		<a href="javascript:void(0)" id="button-cancel" class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
	<!--无保存按钮编辑框 -->
    <div id="noSave_dialog" buttons="#noSave_button"></div>
    <div id="noSave_button">
    	<a href="javascript:void(0)"  class="easyui-linkbutton" data-option="icon-no" onclick="noSaveCancel()">关闭</a>
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



