<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>版本控制</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="versionId";  //主键,这个很重要,不能弄错
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
			var osType=row.osType;
			if(!(osType=='win' || osType=='android')){
				$("#bindPartPad").linkbutton('enable');
				$("#bindAllPad").linkbutton('enable');
			}else{
				$("#bindPartPad").linkbutton('disable');
				$("#bindAllPad").linkbutton('disable');
			}
			
			if(osType=='android'){
				$("#editBatch").linkbutton('enable');
			}else{
				$("#editBatch").linkbutton('disable');
			}
		},
		
		//这里field中填写的是实体类中的属性名,不是数据库字段名
		columns:[[
			{width:100,title:'versionId',field:pk,checkbox:true},
			{width:100,title:'版本名称',field:'versionName',sortable:true},
			{width:120,title:'版本描述',field:'versionDesc',sortable:true},
			{width:100,title:'版本编码',field:'versionCode',sortable:true},
			{width:150,title:'版本时间',field:'versionTime',sortable:true,formatter:formatterTime},
			{width:400,title:'更新地址',field:'updateUrl',sortable:true},
			{width:100,title:'系统类别',field:'osType',sortable:true},
// 			{width:100,title:'是否新版本',field:'versionNew',sortable:true},
			{width:100,title:'是否必须更新',field:'versionMust',sortable:true,formatter:function(value){return getDatagridDict('client_version.update',value);}},
			{width:100,title:'创建人',field:'creater',sortable:true},
			{width:150,title:'创建时间',field:'createTime',formatter:formatterTime,sortable:true},
			{width:100,title:'修改人',field:'modifier',sortable:true},
			{width:100,title:'修改时间',field:'modifyTime',formatter:formatterTime,sortable:true},
// 			{width:100,title:'排序',field:'reorder',sortable:true},
			{width:100,title:'备注',field:'remark',sortable:true},
			{width:100,title:'状态',field:'status',sortable:true,formatter:function(value){return getDatagridDict('global.enable_status',value);}},
			{width:100,title:'启用状态',field:'enableStatus',sortable:true,formatter : formatterStop},
			]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	
	function addVersion(){
		var title=$("title").html()+" - 新增";
		var href=host+'addform';
		$("#button-save").unbind("click").click(save);
		openDialogForm(title,href);
	}
	
	function editBatch(){
		var ids=getGridIds();
 		if(!ids)return false;
 		var href=host+'editBatch?ids=' + ids;
		var title=$("title").html()+" - 批量修改";
		$("#button-save").unbind("click").click(versionSave);
		openDialogForm(title,href);
	}
	
	function lookCord(){
		var id=getGridId();
 		if(!id)return false;
 		var title=$("title").html()+" - 查看二维码";
 		var href=host+'lookCord?' + pk + '=' + id;
 		$("#button-save").unbind("click").click(save);
 		$(module_dialog).dialog({title : title,href: href});
		$(module_dialog).dialog("open");
		
	}
	
	
	function bindPartPad(){
		var id=getGridId();
 		if(!id)return false;
 		var title=$("title").html()+" - 绑定部分设备";
 		var href=host+'bindPartPad?' + pk + '=' + id;
 		$("#button-save").unbind("click").click(versionSave);
 		/* $(module_dialog).dialog({title : title,href: href});
		$(module_dialog).dialog("open");
 		openDialogForm(title,href); */
 		
 		
		$(module_dialog).dialog({
		    title: title,
		    width: 600,
		    height: 400,
		    closed: false,
		    cache: false,
		    href: href,
		    modal: true
		});
		$(module_dialog).dialog("open");
	
	
	}
	
	function versionSave() {
		if ($('#module_submit_form').form("validate")) {
			$.messager.progress();
			$('#module_submit_form').form("submit");
		}
	}
	
	// 绑定所有设备
	var bindAllPad = function(callback) {
		var id = getGridId();
		if (!id) {
			return false;
		}
		$.messager.confirm('确认？', '确认绑定所有设备?', function(confirm) {
			if (confirm) {
				ajaxPost("bindAllPad", {
					versionId : id
				}, callback);
			}
		});
	}
</script>
</head>
<body>
	<!-- 综合查询条件的表格  -->
	   <div id="module_container" >
		<div id="module_search" >
		
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">版本名称：<input type="text" name="versionName" class="easyui-textbox input_width_default"/></div>
			<div class="module_search_input">版本描述：<input type="text" name="versionDesc" class="easyui-textbox input_width_default"/></div>
			<div class="module_search_input">版本编码：<input type="text" name="versionCode" class="easyui-textbox input_width_default"/></div>

			<div class="module_search_input">
				客户端类型：
				<select class="easyui-combobox input_width_short" name="osType" data-options="editable:false" style="width:100px" >
					<option value="">[全部]</option>
				    <c:forEach items="${osTypes }" var="one">
				    	<option value="${one.key }">${one.value }</option>
				    </c:forEach>
			 	</select>
		  	</div>
		  	
		  	<div class="module_search_input">
				渠道编码：<input type="text" name="remark" class="easyui-textbox input_width_default"/>
		  	</div>
			
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
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="addVersion()">新增</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="lookCord()">查看二维码</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-link-rf" plain="true" id="bindPartPad" onclick="bindPartPad()">绑定部分设备</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-link-rf" plain="true" id="bindAllPad" onclick="bindAllPad(callback)">绑定所有设备</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" id="editBatch" onclick="editBatch()">批量修改</a>
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



