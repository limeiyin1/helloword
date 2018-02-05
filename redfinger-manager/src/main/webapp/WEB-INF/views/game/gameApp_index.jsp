<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>游戏管理</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="gameAppId";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField : 'gameAppId',
		onCheck: function(row){
			
		},
		columns:[[
			{width:100,title:'id',field:pk,checkbox:true},
			{width:100,title:'游戏名称',field:'gameAppName',sortable:true},
			{width:100,title:'游戏发行商',field:'gameCompay'},
			{width:100,title:'下载次数',field:'downcount',sortable:true},
			{width:100,title:'打开次数',field:'openFrequency',sortable:true},
			{width:100,title:'图标',field:'gameImage',sortable:true},
			{width:100,title:'应用类别',field:'softType',sortable:true,formatter:function(value){return getDatagridDict('rf_game.soft_type',value);}},
			{width:100,title:'状态',field:'enableStatus',sortable:true,formatter:formatterStop}
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
 				where+=i+"="+encodeURI(encodeURI(params[i]))+"&";
 			}
 			$("#exportForm").attr("action",host+'export?'+where);
 			$("#exportHead").val(exportHead);
 			$("#exportField").val(exportField);
 			$("#exportName").val('游戏管理列表');
 			$("#exportForm").submit();
 		}else{
 			$.messager.alert('操作失败','无数据！',"warning");
 		}
 	}
	
	function md5ShaSum(){
		var id = getGridId();
		if (!id) {
			return false;
		}
		$.messager.progress();
		$.post(host + "md5ShaSum", {gameId: id}, function(data){
			$.messager.progress('close');
			if(jQuery.type(data)=='string'){
				data=eval('(' + data + ')'); 
			}
			if(data.code == '200'){
				gridSearch();
				return false;
			}else{
				$.messager.alert("操作提示", data.message, "info");
				return false;
			}
		},'json');
	}
	
	
	//应用类别
	function formatterSoftType(value, row, index) {
		return getDatagridDict('rf_game.soft_type',value);
	}
	
	function recommand(){
		var id=getGridId();
		if(!id)return false;
		var title=$("title").html()+" - 游戏关联推荐";
		var href=host+'recommandForm?gameAppId='+id;
		$("#button-save").unbind("click").click(gameAppSave);
		openRecommandForm(title,href);
	}
	
	function openRecommandForm(title, href) {
		$(module_dialog).dialog({title : title,href: href,width:680});
		$(module_dialog).dialog("open");
	}
	
	
	
	function gameAppSave() {
		if ($('#module_submit_form').form("validate")) {
			$.messager.progress();
			$('#module_submit_form').form("submit");
		}
	}
	
	function relatedDemoMarket(){
		var id = getGridId();
        if(!id)return false;
		var title=$("title").html()+" - 关联试玩应用市场";
		var href=host+'relatedDemoMarketForm?gameAppId='+id;
		$("#button-save").unbind("click").click(gameAppSave);
		$(module_dialog).dialog({title : title,href: href,width:500});
		$(module_dialog).dialog("open");
	}
	
</script>
</head>
<body>
	<!-- 表格  -->
	<div id ="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
		    <div class="module_search_input">游戏应用名称：<input type="text" name="gameAppName" class="easyui-textbox input_width_default"/></div>
		    <div class="module_search_input">
		              应用类别：<select class="easyui-combobox input_width_short"  editable="false" name="softType" data-options="required:false" >
						<option value="">[全部]</option>
					   <fns:getOptions category="rf_game.soft_type"></fns:getOptions>
				  </select> 
		    </div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search-rf',plain:false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reset-rf',plain:false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<c:if test="${not empty sessionScope.permission.button_manage_export}"> 
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-excel-rf',plain:true" onclick="statExport()">导出</a>
			</c:if>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add-rf',plain:true" onclick="add()">新增</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit-rf',plain:true" onclick="edit()">编辑</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-start-rf',plain:true" onclick="start(callback)">启用</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-stop-rf',plain:true" onclick="stop(callback)">禁用</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove-rf',plain:true" onclick="del(callback)">删除</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload-rf',plain:true" onclick="fresh()">刷新</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add-rf',plain:true" onclick="recommand(callback)">游戏关联推荐</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add-rf',plain:true" onclick="relatedDemoMarket(callback)">关联试玩应用市场</a>
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>
	
	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
    <div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton"data-options="iconCls:'icon-ok-rf'">保存</a>
		<a href="javascript:void(0)" id="button-cancel" class="easyui-linkbutton" data-options="iconCls:'icon-no'" onclick="cancel()">关闭</a>
	</div>
	<!--导出  -->
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



