<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>用户订单统计</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript" src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/jquery-jbox/2.3/i18n/jquery.jBox-zh-CN.js"></script>
<link type="text/css" rel="stylesheet" href="${ctxStatic}/jquery-jbox/2.3/Skins/Blue/jbox.css"/>
<script type="text/javascript">
var pk="userId";
var parentKey="";
var module_datagrid = "#module_datagrid";
var module_dialog = "#module_dialog";
var module_search_form = "#module_search_form"
var module_submit_form = "#module_submit_form";
var callback = defaultCallback ;
var treeGridParamObj={
	url : host + "list",
	idField : 'userId',
	onCheck : function(row) {

	},
	columns : [ [
	 {width:100,title:'id',field:"userId",checkbox:true},
	 {width : 100,title : '会员邮箱',field : 'userEmail',sortable : true},
	 {width : 100,title : '会员电话',field : 'userMobilePhone',sortable : true},
	 {width : 100,title : '总订单(：/单)',field : 'map.ordernumber',},
	 {width : 100,title : '总金额(：/元)',field : 'map.ordermoney',},
	 {width : 100,title : '平均消费',field : 'map.orderaverage',},
	] ]
};
var dialogParamObj = {

};
$(function(){
	$(module_datagrid).treegrid($.extend({},treeGridParam,treeGridParamObj));
	$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
});
//导出
 function statExport() {
		var total=$(module_datagrid).datagrid("getRows");
		if(total){
			var cols=treeGridParamObj.columns[0];
			var exportHead="";
			var exportField="";
			for(var i in cols){
				if(i!=0){
					exportHead=exportHead+cols[i].title+",";
					exportField=exportField+cols[i].field+",";
				}
			}
		}
	var arr = $(module_search_form).serializeArray();
	var where ="";
	$.each(arr, function(i, field) {
		where+=field.name+"="+field.value+"&";
	});
    $("#exportForm").attr("action",host+'export?'+where);
	$("#exportHead").val(exportHead);
	$("#exportField").val(exportField);
	$("#exportName").val('用户订单');
	$("#exportForm").submit();
} 

</script>
</head>
<body >
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search">
		<form id="module_search_form" class="easyui-form" method="post">
				<div class="module_search_input">
					排序：<select class="easyui-combobox input_width_default" editable="false" name="orderSequence">
					<option value="">[全部]</option>
					<option value="1">总订单升序</option>
					<option value="2">总订单降序</option>
					<option value="3">总金额升序</option>
					<option value="4">总金额降序</option>
				</select>
				</div>
				<div class="module_search_input">
				会员邮箱：<input type="text" id = "userE" name="userE" class="easyui-textbox input_width_default" /> 
				会员手机：<input type="text" id = "userPhone" name="userPhone" class="easyui-textbox input_width_default" /> 
				</div>
			    <div class="module_search_input">
				 查询时间段:
				<input type="text" class="easyui-datebox input_width_default" editable="false" id="begin" name="begin" />
				至
				<input type="text" class="easyui-datebox input_width_default" editable="false" id="end" name="end"/>
		        </div>
				<div class="module_search_button">
					<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
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
	<div id="module_dialog" buttons="#module_dialog_button" ></div>
	<div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save"class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a>
		<a href="javascript:void(0)" id="button-cancel"class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
	<div id="look" buttons="#look_button"  ></div>
	
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



