<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>命令列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
</script>
</head>
<body>
	<script type="text/javascript">
	function doSearch() {
		$('#module_datagrid_renewal').datagrid('load', {
			padCode : $('#itemid').val(),
			taskResultStatus : $('#resultid').combobox('getValue'),
			});
	}
	var module_datagrid_renewal = "#module_datagrid_renewal";
	var callback = defaultCallback ;
	var dataGridParamObj = {
			url : host + "tasklist?batchId=${bean.id}",
			idField : 'taskId',
			onCheck : function(index,row) {
				
			},
		    onDblClickRow:dblClickCallbackToo,
			columns : [ [
			{width:100,title:'创建人',field:'creater',sortable:true},
			{width:100,title:'命令内容',field:'taskCommand',sortable:true},
			{width:200,title:'设备编号',field:'padCode',sortable:true},
			{width:200,title:'设备IP',field:'map.IP',},
			{width:150,title:'发送时间',field:'createTime',sortable:true,formatter:formatterTime},
			{width:60,title:'任务结果状态',field:'taskResultStatus',sortable:true,formatter:function(value){if(value==1){return "成功";}else if(value==-1){return "失败";}}},
			{width:60,title:'任务状态',field:'taskStatus',sortable:true,formatter:function(value){return getDatagridDict('rf_pad_task.task_status',value);}},
			{width:200,title:'任务信息',field:'taskResultInfo',sortable:true},
			{width:100,title:'命令类型',field:'commandType',sortable:true},
			{width:100,title:'备注',field:'remark',sortable:true,formatter:function(val, row){ return '<a target="_blank" style="color:red;" href="' + val + '">'+val+ '</a>';  }}
		]]
	};
	var dataGridParam500 = {
			fitColumns : true,
			pagination : true,
			striped : true,
			rownumbers : true,
			singleSelect : false,
			idField : 'id',
			pageSize : 20,
			pageList : [ 10, 15, 20, 50, 100, 200,500
			 ],
			loadFilter : loadFilterForDataGrid,
			onDblClickRow:specific,
			loadMsg : "处理中，请稍后..."
		};
	var dialogParamObj = {

	};
	$(function() {
		$(module_datagrid_renewal).datagrid($.extend({}, dataGridParam500, dataGridParamObj));
	});
	function doReset () {
		$(user_search_form).form("reset");
	}
	
	//刷新
	function dofresh  () {
		try{
			$(module_datagrid).treegrid("reload");
		}catch(e){
			$(module_datagrid).datagrid("reload");
		}
	}
  
	function dblClickCallbackToo(index, row){
		var view=$(module_datagrid_renewal).parent();
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
<div id="module_submit_container">
<div id="module_search">
</div>
<div class="module_search_button">
	<form id="user_search_form" method="post">
		设备编码:<input id="itemid" class="easyui-textbox input_width_default" value="">
		任务结果:<select class="easyui-combobox" id="resultid" name="resultid" >
			     <option value="">[全部]</option>
			     <option value="1">成功</option>
		         <option value="-1">失败</option>
		      </select>
		<a href="#" iconCls="icon-search-rf" class="easyui-linkbutton"plain="true"onclick="doSearch()">查询</a>
	</form>
</div> 

</div>
	<table id="module_datagrid_renewal" toolbar="#module_toolbar_renewal" ></table>


</body>
</html>

