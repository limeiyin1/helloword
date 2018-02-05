<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>游戏管理</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="gameId";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var flag = "rf_manage_index";
	var currentUserId = "${currentUserId}";
	var initColumnMap = {
		'gameId':80,
		'gameName':60,
		'map.gameAppName':70,
		'map.gameChannelName':60,
		'map.softType':60,
		'gamePackageName':60,
		'versionCode':80,
		'gameVersion':80,
		'gameCompay':60,
		'gameDesc':60,
		'gameFile':60,
		'btUrl':70,
		'downcount':60,
		'openFrequency':60,
		'gameImage':40,
		'gameGrayImage':50,
		'remark':40,
		'reorder':40,
		'enableStatus':40,
		'md5New':40,
		'sha':40,
		'deviceVersion':60,
		'map.gameStatusName':60,
	};
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField : 'gameId',
		onCheck: function(row){
			
		},
		columns:[[
			{width:80,title:'id',field:pk,checkbox:true},
			{width:60,title:'游戏名称',field:'gameName',sortable:true},
			{width:70,title:'游戏应用名',field:'map.gameAppName'},
			{width:60,title:'游戏渠道',field:'map.gameChannelName',sortable:true},
			{width:60,title:'应用类型',field:'map.softType'},
			{width:60,title:'包名',field:'gamePackageName',sortable:true},
			{width:80,title:'游戏版本号',field:'versionCode',sortable:true},
			{width:80,title:'游戏版本名称',field:'gameVersion',sortable:true},
			{width:60,title:'发行商',field:'gameCompay',sortable:true},
			{width:60,title:'游戏描述',field:'gameDesc',sortable:true},
			{width:60,title:'下载地址',field:'gameFile',sortable:true},
			/* {width:100,title:'bt下载名称',field:'btName',sortable:true}, */
			{width:70,title:'bt下载url',field:'btUrl',sortable:true},
			/* {width:100,title:'适配系统',field:'gameOs',sortable:true},
			{width:100,title:'游戏标记',field:'gameFlag',sortable:true},
			{width:100,title:'大小',field:'gameSize',sortable:true}, */
			{width:60,title:'下载次数',field:'downcount',sortable:true},
			{width:60,title:'打开频率(次)',field:'openFrequency',sortable:true},
			{width:40,title:'图标',field:'gameImage',sortable:true},
			{width:50,title:'灰图标',field:'gameGrayImage',sortable:true},
			{width:40,title:'描述',field:'remark'},
			{width:40,title:'排序',field:'reorder',sortable:true},
			{width:40,title:'状态',field:'enableStatus',sortable:true,formatter:formatterStop},
			{width:40,title:'MD5',field:'md5New',sortable:true},
			{width:40,title:'SHA',field:'sha',sortable:true},
			{width:60,title:'设备版本',field:'deviceVersion',sortable:true},
			{width:60,title:'游戏状态',field:'map.gameStatusName',sortable:true}
		]],
		onResizeColumn:function(field, width){
			resizeColumn(currentUserId,flag);
		},
	};
	var dialogParamObj={
		
	};
	$(function(){
		$("#module_column_defined_dialog").dialog($.extend({}, dialogParam, dialogParamObj));
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
		loadColumnDefined(currentUserId,initColumnMap,pk,flag);
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
	
	
	function relatedDemoMarket(){
		var ids = getGridIds();
        if (ids.length == 0 || !ids) {  
            return false;  
        }
		$.messager.confirm('确认？', '确认关联试玩应用市场?', function(confirm) {
			if (confirm) {
				ajaxPost("relatedDemoMarket", {
					ids : ids
				}, callback);
			}
		});
	}
	
	$(document).ready(function(){//加载页面完成后执行
			/* $("#gameAppId").combobox({editable:true});
			$("#channelId").combobox({editable:true});
			if($.fn.combobox){
			    //为了兼容火狐浏览器
			    $.fn.combobox.defaults.inputEvents.keyup=$.fn.combobox.defaults.inputEvents.keydown;
			    $.fn.combobox.defaults.inputEvents.keydown=function(){};
			} */
			
		});
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
		    <div class="module_search_input">游戏包名：<input type="text" name="gamePackageName" class="easyui-textbox input_width_default"/></div>
		    <div class="module_search_input">游戏版本号：<input type="text" name="versionCode" class="easyui-validatebox" onkeyup="this.value=this.value.replace(/\D/g,'')" /></div>
			<div class="module_search_input">游戏名称：<input type="text" name="gameName" class="easyui-textbox input_width_default"/></div>
			<div class="module_search_input">
			应用类型：<select class="easyui-combobox" id="softType" name="softType" style="width:100px" editable="false">
					<option value="">[全部]</option>
				    <fns:getOptions category="rf_game.soft_type"></fns:getOptions>
			 	</select>
			</div>
			<div class="module_search_input">
			是否是热门游戏：
			    <select class="easyui-combobox" id="isHot" name="isHot" style="width:100px" editable="false">
					<option value="">[全部]</option>
				    <c:forEach items="${yesOrNo}" var="one">
						<option value="${one.key }">${one.value }</option>
					</c:forEach>
			 	</select>
			</div>
			<div class="module_search_input">
			游戏应用：
			    <select class="easyui-combobox" id="gameAppId" name="gameAppId" style="width:100px" data-options="editable:true">
					<option value="">[全部]</option>
				    <c:forEach items="${gameApps}" var="one">
						<option value="${one.gameAppId }">${one.gameAppName }</option>
					</c:forEach>
			 	</select>
			</div>
			
			<div class="module_search_input">
			设备版本：
			    <select class="easyui-combobox" id="deviceVersion" name="deviceVersion" editable="false">
					<option value="">[全部]</option>
				    <fns:getOptions category="rf_game.device_version"></fns:getOptions>
			 	</select>
			</div>
			<div class="module_search_input">状态：
		        <select class="easyui-combobox input_width_default" editable="false" name="enableStatus">
					<option value="">[全部]</option>
					<fns:getOptions category="global.enable_status"/>
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
<!-- 	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-tools-rf',plain:true" onclick="md5ShaSum()">计算MD5和SHA</a> -->
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add-rf',plain:true" onclick="relatedDemoMarket(callback)">关联试玩应用市场</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit-rf',plain:true" onclick="columnDefined(currentUserId,initColumnMap,pk,flag)">列表自定义</a>
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>
	
	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
    <div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton"data-options="iconCls:'icon-ok-rf'">保存</a>
		<a href="javascript:void(0)" id="button-cancel" class="easyui-linkbutton" data-options="iconCls:'icon-no'" onclick="cancel()">关闭</a>
	</div>
	<!-- 列表自定义编辑框 -->
	<div id="module_column_defined_dialog" buttons="#module_column_defined_dialog_button"></div>
	<div style="text-align:center" id="module_column_defined_dialog_button">
		<a href="javascript:void(0)" id="buttone-column-defined-save-select-all"class="easyui-linkbutton" iconCls="icon-edit-rf" onclick="selectAllColumn()">全选</a>
	    <a href="javascript:void(0)" id="buttone-column-defined-save"class="easyui-linkbutton" iconCls="icon-ok-rf">确定</a>
	    <a href="javascript:void(0)" id="button-column-defined-cancel"class="easyui-linkbutton" iconCls="icon-reload-rf" onclick="resetColumn(currentUserId,initColumnMap,pk,flag)">重置</a>
	</div>
	<!-- 列表自定义编辑框  end-->
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



