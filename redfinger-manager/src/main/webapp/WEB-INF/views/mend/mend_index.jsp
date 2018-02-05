<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>补丁管理</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk = "mendId";  //主键,这个很重要,不能弄错
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField : pk,
		onCheck: function(index,row){
			//只有启动才可以绑定设备
		},
		
		//这里field中填写的是实体类中的属性名,不是数据库字段名
		columns:[[
			{width:100,title:'mendId',field:pk,checkbox:true},
			{width:100,title:'版本名称',field:'mendVersionName',sortable:true},
			{width:100,title:'版本编码',field:'mendVersionCode',sortable:true},
			{width:100,title:'渠道编码',field:'mendChannelCode',sortable:true},
			{width:150,title:'版本时间',field:'mendVersionTime',sortable:true,formatter:formatterTime},
			{width:400,title:'更新地址',field:'mendUpdateUrl',sortable:true},
			{width:400,title:'下载地址',field:'mendDownloadUrl',sortable:true},
			{width:100,title:'系统类别',field:'mendOsType',sortable:true},
			{width:100,title:'创建人',field:'creater',sortable:true},
			{width:150,title:'创建时间',field:'createTime',formatter:formatterTime,sortable:true},
			{width:100,title:'修改人',field:'modifier',sortable:true},
			{width:100,title:'修改时间',field:'modifyTime',formatter:formatterTime,sortable:true},
			{width:100,title:'启用状态',field:'enableStatus',sortable:true,formatter : formatterStop}
			]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	
	function addMend(){
		var title=$("title").html()+" - 新增";
		var href=host+'addform';
		$("#button-save").unbind("click").click(save);
		openDialogForm(title,href);
	}
	
	
	
	
	
	function mend() {
		if ($('#module_submit_form').form("validate")) {
			$.messager.progress();
			$('#module_submit_form').form("submit");
		}
	}
</script>
</head>
<body>
	<!-- 综合查询条件的表格  -->
	   <div id="module_container" >
		<div id="module_search" >
		
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">版本名称：<input type="text" name="mendVersionName" class="easyui-textbox input_width_default"/></div>
			<div class="module_search_input">版本编码：<input type="text" name="mendVersionCode" class="easyui-textbox input_width_default"/></div>

			<%-- <div class="module_search_input">
				系统类别：
				<select class="easyui-combobox input_width_short" name="mendOsType" data-options="editable:false" style="width:100px">
					<option value="">[全部]</option>
				    <c:forEach items="${osTypes}" var="one">
				    	<option value="${one.key }">${one.value }</option>
				    </c:forEach>
			 	</select>
		  	</div> --%>

			<div class="module_search_input">
				版本时间:
				<input type="text" class="easyui-datebox input_width_default" editable="false"  name="beginTimeStr" />
				至
				<input type="text" class="easyui-datebox input_width_default" editable="false"  name="endTimeStr"/>
			</div>  
			
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		
		
		<!--增删查改的biaoge  -->
		<div id="module_toolbar" class="easyui-toolbar">
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a>
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
</body>
</html>



