<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>网络故障</title>
<meta name="decorator" content="moduleIndex" />
<style type="text/css">
	.datagrid-cell, .datagrid-cell-group{text-overflow: ellipsis;}
	#module_submit_form .td1{width:126px}
</style>
<script type="text/javascript">
var pk="netinfoId";
var module_datagrid = "#module_datagrid";
var module_dialog = "#module_dialog";
var module_search_form = "#module_search_form"
var module_submit_form = "#module_submit_form";
var callback = defaultCallback ;
var dataGridParamObj = {
	url : host + "list",
	idField : 'netinfoId',
	onCheck : function(row) {

	},
	columns:[[
		{width:100,title:'id',field:pk,checkbox:true},
		{width:55,title:'cuid',field:'cuid',sortable:true,align:'center', formatter:formatterHover},
		{width:75,title:'手机号码',field:'userMobilePhone',sortable:false,align:'center', formatter:formatterHover},
		{width:80,title:'公网IP',field:'clientIp',sortable:true,align:'center', formatter:formatterHover},
		{width:100,title:'设备编码',field:'padCode',sortable:true,align:'center', formatter:formatterHover},
		{width:70,title:'TCPING是否成功',field:'tcpingResult',align:'center',formatter:formatterResult},
		{width:80,title:'TCPING信息',field:'tcpingContent',align:'center',formatter:formatterHover},
		{width:60,title:'PING是否成功',field:'pingResult',align:'center',formatter:formatterResult},
		{width:80,title:'PING信息',field:'pingContent',align:'center', formatter:formatterHover},
		{width:70,title:'TRACE是否成功',field:'traceResult',align:'center',sortable:true,formatter:formatterResult},
		{width:80,title:'TRACE信息',field:'traceContent',align:'center', formatter:formatterHover},
		{width:120,title:'失败类型',field:'gatherType',align:'center', formatter:formatterGatherType},
		{width:125,title:'上报时间',field:'serverTime',align:'center',sortable:true,formatter:formatterTime},
		{width:125,title:'客户端执行时间',field:'clientTime',align:'center',sortable:true,formatter:formatterTime},
		{width:125,title:'云服务器发送时间',field:'cloudReceiveTime',align:'center',sortable:true,formatter:formatterTime},
		{width:125,title:'云服务器执行时间',field:'cloudSendTime',align:'center',sortable:true,formatter:formatterTime}
	]],
	onLoadSuccess : function () {  
        $(this).datagrid("fixRownumber");  
    }  
	
};
var dialogParamObj = {
	width : 700,
	height:600

};

// 格式化datagrid的数字序列在数值过大时显示不全 
$.extend($.fn.datagrid.methods, {  
    fixRownumber : function (jq) {  
        return jq.each(function () { 
            var panel = $(this).datagrid("getPanel");  
            var clone = $(".datagrid-cell-rownumber", panel).last().clone();  
            clone.css({  
                "position" : "absolute",  
                left : -1000  
            }).appendTo("body");  
            var width = clone.width("auto").width();  
            if (width > 25) {  
                //多加10个像素,保持一点边距  
                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 10);  
                $(this).datagrid("resize");  
                //一些清理工作  
                clone.remove();  
                clone = null;  
            } else {  
                //还原成默认状态  
                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).removeAttr("style");  
            }  
        });  
    }  
});  

//双击显示明细, 将\n输出为<br>
var specific =function(index, row){
	if(typeof dblClickCallback!='undefined' && dblClickCallback){
		dblClickCallback.apply(null, [index,row]);
	}else{
		var view=$(module_datagrid).parent();
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
				var bodyValue = bodys[i];
				if(bodyValue){
					bodyValue = bodyValue.replace(/\n/g,'<br>');
				}
				var tr=$('<tr><td class="td1">'+heads[i]+':</td><td class="td2">'+bodyValue+'</td></tr>');
				easyui_table.append(tr);
			}
			var title = $("title").html() + " - 明细";
			$("#button-save").unbind("click").click(cancel);
			$(module_dialog).dialog({title : title,content:$(module_submit_container).prop("outerHTML"),href:''});
			$(module_dialog).dialog("open");
		}
	}
	
}

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
});

function formatterResult(value,row,index){
	if("0" ==value){
		return '<span class="label label-danger">失败</span>'
	}else if("1" ==value){
		return '<span class="label label-success">成功</span>'
	}else if("-1" ==value){
		return '<span class="label label-danger">解析出错</span>'
	}else{
		return value;
	}
}

function formatterGatherType(value,row,index){
	if("control" ==value){
		return '<span class="label label-danger">控制设备失败</span>'
	}else if("request" ==value){
		return '<span class="label label-danger">请求服务失败</span>'
	}else{
		return value;
	}
}

function formatterHover(value, row, index){
	if(value){
		return '<span title="'+value+'" >'+value+'</span>';   
	}
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
					var field = cols[i].field;
					if(field == 'tcpingResult'){
						field = 'map.tcpingResult';
					}else if(field == 'traceResult'){
						field = 'map.traceResult';
					}else if(field == 'pingResult'){
						field = 'map.pingResult';
					}
					exportField=exportField+field+",";
				}
			}
			var where="";
			var params=$(module_datagrid).datagrid('options').queryParams;
			console.log(params);
			// 清除历史搜索条件
			$(".exportParams").remove();
			for(var i in params){
				if(params[i]){
					$("#exportForm").append('<input type="hidden" name="'+i+'" class="exportParams"  value="'+params[i]+'"/>');
				}
			}
			$("#exportForm").attr("action",host+'export');
			$("#exportHead").val(exportHead);
			$("#exportField").val(exportField);
			$("#exportName").val('网络故障');
			$("#exportForm").submit();
		}else{
			$.messager.alert('操作失败','无数据！',"warning");
		}
}
	
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container">
		<div id="module_search">
			<form id="module_search_form" class="easyui-form" method="post">
				<div class="module_search_input">
					用户ID：&nbsp;<input type="text" name="externalUserId" class="easyui-numberbox input_width_short" />
				</div>
				<div class="module_search_input">
					手机号：&nbsp;<input type="text" name="userMobilePhone" class="easyui-textbox input_width_default" />
				</div>
				<div class="module_search_input">
					设备编码:&nbsp;<input type="text" name="padCode" class="easyui-textbox input_width_long" />
				</div>
				<div class="module_search_input">
					上报开始时间：
					<input type="text" class="easyui-datetimebox input_width_long" editable="false" id="begin" name="serverStartTime" data-options=""/>
				</div>
				<div class="module_search_input">
					上报结束时间：
					<input type="text" class="easyui-datetimebox input_width_long" editable="false" id="end" name="serverEndTime" data-options=""/>
				</div>
				<div class="module_search_button">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
				</div>
			</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel-rf" plain="true" onclick="statExport()">导出</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>

	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
	<div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save"
			class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a> <a
			href="javascript:void(0)" id="button-cancel"
			class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
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



