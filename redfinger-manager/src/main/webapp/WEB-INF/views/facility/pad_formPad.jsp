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
	$("#module_gameTask").html("");
	$("#info").html("");
	if($('#module_submit_form_execute').form("validate")){
		$.messager.progress();
		$('#module_submit_form_execute').form("submit",{url:host+"padTask", success:function(data){
			$.messager.progress('close'); 
		var	personJson = jQuery.parseJSON(data);
		if(personJson.code=='501'){
			alert(personJson.message);
			return;
		}
		
		if($('#type').combobox('getValue')=="device_get_info"){
		   showinfo(personJson.info);
		}else if($('#type').combobox('getValue')=="pad_package"){
			
			$("#info").html(
				"<form method='post' id='game_form_execute'>"
				+"<input type='hidden' name='taskId' id='taskId' value='"+personJson.taskId+"'></input>"
				+"</form>"
				+"<button onclick='getGameInfo()'>刷新</button>"
				);
		    showGameInfo(personJson.info);
		}
		
		var xdataGridParamObj = {
			    url : host + "padTaskList",
			    data:personJson.rows,
				idField : 'taskId',
				queryParams: { 'taskId': personJson.taskId },
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



function showGameInfo(info){
     if(info.gameList!=null&&info.gameList!=""){
		var str="<table border='1' bordercolor='#a0c6e5' style='border-collapse:collapse;'><tr><td width='150px'>设备IP</td><td width='280px'>游戏包名</td><td width='80px'>运行方式</td><td width='200px'>备注</td></tr>";
		for(var i=0;i<info.gameList.length;i++){
		   var game=info.gameList[i];
		   str=str+"<tr><td >"+game.padIp+"</td><td >"+game.packgeName+"</td><td>"+game.runName+"</td><td>"+game.descripble+"</td></tr>";
		}
		str=str+"</table>";
		
		$("#module_gameTask").html(str);
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

function showinfo(info){
	//alert("into show");
	if(info.MEM_TOTAL!=undefined){
		$("#info").html(
		"<a style='font-size:20px';>设备属性:</a><br/>"
		+"<a>设备ip："+info.IP+"</a><br/>"
		+"<a>设备属性："+info.MEM_TOTAL+"</a><br/>"
		+"<a>运行天数："+info.UPTIME+"</a><br/>"
		+"<a>CPU温度："+info.TEMP+"</a><br/>"
		+"<a>CPU频率："+info.CUP_FREQ+"</a><br/>"
		+"<a>运行内存剩余："+info.MEM_FREE+"</a><br/>"
		+"<a>系统已使用缓存："+info.BUFFERS_USED+"</a><br/>"
		+"<a>系统未使用缓存："+info.BUFFERS_FREE+"</a><br/>"
		+"<a>DATA总数："+info.DATA_DISK_TOTAL+" 已使用："+info.DATA_DISK_USED+" 剩余："+info.DATA_DISK_FREE+"</a><br/>"
		+"<a>SD总数："+info.SD_DISK_TOTAL+" SD使用："+info.SD_DISK_USED+" SD剩余："+info.SD_DISK_FREE+"</a>"
		);
	}else{
		$("#info").html(
		"<form method='post' id='module_submit_form_execute2'>"
		+"<input type='hidden' name='taskId' id='taskId' value='"+info.taskId+"'></input>"
		+"<input type='hidden' name='IP' id='IP' value='"+info.IP+"'></input></form>"
		+"<button onclick='execute2()'>刷新</button>"
		);
	}
}


function execute2(){
		$('#module_submit_form_execute2').form("submit",{url:host+"getDeviceInfo", success:function(data){
		var	info = jQuery.parseJSON(data);
		showinfo(info);
		var xdataGridParamObj = {};
		var xdialogParamObj = {};
			}});
}

function getGameInfo(){
    $("#module_gameTask").html("");
		 $('#game_form_execute').form("submit",{url:host+"getGameInfo", success:function(data){
		    var info = jQuery.parseJSON(data);
	     	  showGameInfo(info);
		
			}});
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
    <td class="td2">   命令:<select class="easyui-combobox input_width_short" id="type" name="type" data-options="required:false,editable:false">
						<option value="vm_screencap">截图</option>
						<option value="device_get_info">属性查询</option>
						<option value="pad_package">游戏查询</option>
						</select> </td>
    </tr>
    </table>
    </form>
    </div>
    <h3 align="center">命令结果</h3>
	<table id="module_padTask"  >
	</table>
	<div id="info">
	</div>
	<div id="module_gameTask">
	</div>
	
</div>
</body>
</html>