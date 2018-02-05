<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>问卷调查日志</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="id";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField :'id',
		onCheck: function(row){
			
		},
		columns:[[
			{width:100,title:'id',field:pk,checkbox:true},
			{width:100,title:'用户手机',field:'userMobilePhone',sortable:true},
			{width:100,title:'设备编码',field:'padCode',sortable:true},
			{width:100,title:'标题',field:'title',sortable:true},
			{width:100,title:'内容',field:'noticeContent',sortable:true},
			{width:100,title:'设备编码',field:'deviceToken',sortable:true},
			{width:200,title:'推送类型',field:'map.pushTypeName',sortable:true},
			{width:200,title:'推送状态',field:'map.pushStatusName',sortable:true},
			{width:100,title:'创建人',field:'creater',sortable:true},
			{width:150,title:'创建时间',field:'createTime',sortable:true,formatter:formatterTime}
		]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	
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
 			$("#exportName").val('问卷调查日志');
 			$("#exportForm").submit();
 		}else{
 			$.messager.alert('操作失败','无数据！',"warning");
 		}
 	}
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">
				用户手机：<input type="text" class="easyui-textbox input_width_default" id="userMobilePhone" name="userMobilePhone"/>
				设备编码：<input type="text" class="easyui-textbox input_width_default" id="padCode" name="padCode"/>
				<!-- 内容：<input type="text" class="easyui-textbox input_width_default" id="noticeContent" name="noticeContent"/> -->
				推送类型：<select class="easyui-combobox input_width_short" editable="false" name="pushType" data-options="required:false">
						<option value="">[全部]</option>
						<c:forEach var="one" items="${pushTypes}">
							<option value="${one.key}">${one.value}</option>
						</c:forEach>
					</select>
				推送状态：<select class="easyui-combobox input_width_short" editable="false" name="pushStatus" data-options="required:false">
						<option value="">[全部]</option>
						<c:forEach var="one" items="${pushStatus}">
							<option value="${one.key}">${one.value}</option>
						</c:forEach>
					</select>
				
			</div>
			<div class="module_search_input">
				
				创建时间：
				<input type="text" class="easyui-datebox input_width_default" editable="false" id="begin" name="begin" data-options=""/>
				至
				<input type="text" class="easyui-datebox input_width_default" editable="false" id="end" name="end" data-options=""/>
			</div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>
	
	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
    <div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a>
		<a href="javascript:void(0)" id="button-cancel" class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
	<div class="hide">
	</div>
	
</body>
</html>



