<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>报名管理</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript" src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/jquery-jbox/2.3/i18n/jquery.jBox-zh-CN.js"></script>
<link type="text/css" rel="stylesheet" href="${ctxStatic}/jquery-jbox/2.3/Skins/Blue/jbox.css"/>
<script type="text/javascript">
var pk="enrollId";
var module_datagrid = "#module_datagrid";
var module_dialog = "#module_dialog";
var module_search_form = "#module_search_form"
var module_submit_form = "#module_submit_form";
var callback = defaultCallback ;
var dataGridParamObj = {
	url : host + "list",
	idField : 'enrollId',
	onCheck : function(row) {

	},
	columns : [ [
	 {width : 100,title : 'id',field : 'enrollId',checkbox : true}, 
	 {width : 100,title : '报名用户',field:'userName',sortable:true},
	 {width : 100,title : '报名QQ',field : 'qq',sortable : true},
	 {width : 100,title : '报名号码',field : 'mobile',sortable : true},
     {width : 100,title : '报名时间',field:'createTime',sortable:true,formatter:formatterTime}
	] ]
};
var dialogParamObj = {

};
$(function() {
	$(module_datagrid).datagrid($.extend({}, dataGridParam, dataGridParamObj));
	$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
});

function statExport() {
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
			$("#exportName").val('报名用户');
			$("#exportForm").submit();
		}else{
			$.messager.alert('操作失败','无数据！',"warning");
	      }
	}
</script>
</head>
<body >
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search">
			<form id="module_search_form" class="easyui-form" method="post">
		        <div class="module_search_input">报名qq:<input type="text" name="qq" class="easyui-textbox input_width_default" /></div>  
			    <div class="module_search_input">报名姓名:<input type="text" name="userName" class="easyui-textbox input_width_default" /></div>
			    <div class="module_search_input">报名手机:<input type="text" name="mobile" class="easyui-textbox input_width_default" /></div>  
			    <div class="module_search_input">
				创建时间:
				<input type="text" class="easyui-datebox input_width_default" editable="false"  name="createTimeBegin" />
				至
				<input type="text" class="easyui-datebox input_width_default" editable="false"  name="createTimeEnd"/>
		        </div>
			    
				<div class="module_search_button">
					<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
				</div>
			</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar" >
	           <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel-rf" plain="true" onclick="statExport()">导出</a>
			   <a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar" ></table>
	</div>

	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button" ></div>
	<div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save"class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a>
		<a href="javascript:void(0)" id="button-cancel"class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
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


