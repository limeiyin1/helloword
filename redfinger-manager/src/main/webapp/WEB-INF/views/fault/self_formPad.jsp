<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>设备操作</title>
<meta name="decorator" content="default" />
</head>
<body><script type="text/javascript">

$("#button-execute").unbind("click").click(execute);
function execute(){
	if($('#module_submit_form_execute').form("validate")){
		$.messager.progress();
		$('#module_submit_form_execute').form("submit",{url:host+"padTask", success:function(data){
			$.messager.progress('close'); 
		var	personJson = jQuery.parseJSON(data);
		var xdataGridParamObj = {
			    //url : host + "padtasklist",
			    data:personJson,
				idField : 'taskId',
			//	queryParams: { 'taskId': personJson.taskId },
				onCheck : function(index,row) {
		
				},
				   onDblClickRow:dblClickCallbackToo,
				columns : [ [
							{width:100,title:'创建人',field:'creater',sortable:true},
							{width:100,title:'命令内容',field:'taskCommand',sortable:true},
							{width:200,title:'设备编号',field:'padCode',sortable:true},
							{width:150,title:'发送时间',field:'createTime',sortable:true,formatter:formatterTime},
							{width:60,title:'任务结果状态',field:'taskResultStatus',sortable:true,formatter:function(value){if(value==1){return "成功";}else if(value==-1){return "失败";}}},
							{width:60,title:'任务状态',field:'taskStatus',sortable:true,formatter:function(value){return getDatagridDict('rf_pad_task.task_status',value);}},
							{width:200,title:'任务信息',field:'taskResultInfo',sortable:true},
							{width:100,title:'命令类型',field:'commandType',sortable:true},
							{width:100,title:'备注',field:'remark',sortable:true,formatter:function(val, row){ return '<a target="_blank" style="color:red;" href="' + val + '">'+val+ '</a>';  }}
				          ] ]
			};
			var xdialogParamObj = {

			};
			$(function() {
				$(module_padTask).datagrid($.extend({}, dataGridParam, xdataGridParamObj));
				
			});
		}});
	}else{
		alert("操作错误");
	}

}
function dblClickCallbackToo(index, row){
	var view=$(module_padTask).parent();
	if($(view).hasClass("datagrid-view")){
		//获取头
		var heads=[];
		$(view).find(".datagrid-header-row").find(".datagrid-cell").each(function(){
			heads.push($(this).text());
		});
		//获取内容
		var bodys=[];
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

</script>
<div>
    <div id="module_submit_container">
    <form method="post" class="easyui-form" id="module_submit_form_execute">
    <table>
    <tr>
    <td class="td1">用户手机号：</td>                
    <td class="td2">${user.userMobilePhone}</td>
    </tr>
    <tr>
    <td class="td1">设备编号：${pad.padCode}<input type="hidden" name="padId" id="padId" value="${pad.padId }"></td>
    <td class="td2">${fns:getLabelStyle('rf_pad.maint_status',pad.maintStatus)},${fns:getLabelStyle('rf_pad.grant_open_status',pad.grantOpenStatus)},${fns:getLabelStyle('rf_pad.vm_status',pad.vmStatus)},${fns:getLabelStyle('rf_pad.bind_status',pad.bindStatus)},${fns:getLabelStyle('rf_pad.enable_status',pad.enableStatus)},${fns:getLabelStyle('rf_pad.pad_status',pad.padStatus)},${fns:getLabelStyle('rf_pad.fault_status',pad.faultStatus)}</td>
    </tr>
    <tr>
    <td class="td1"><a href="javascript:void(0)" id="button-execute" class="easyui-linkbutton" data-options="iconCls:'icon-ok-rf'" >执行</a></td>                
    <td class="td2">   命令:<select class="easyui-combobox input_width_short" name="type" data-options="required:false,editable:false">
						<option value="screencap">截图</option>
						</select> </td>
    </tr>
    </table>
    </form>
    </div>
    <h3 align="center">命令结果</h3>
	<table id="module_padTask"  >
	</table>
</div>
</body>
</html>