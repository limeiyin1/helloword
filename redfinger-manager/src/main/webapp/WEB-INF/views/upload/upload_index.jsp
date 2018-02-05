<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>上传记录</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="uploadId";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField :'uploadId',
		singleSelect: false,
		checkOnSelect: true, 
		selectOnCheck: true,
		onCheck : function(row) {
			
		},
		columns:[[
			{width:100,title:'id',field:"uploadId",checkbox:true},
			{width:70,title:'会员ID',field:'map.externalUserId'},
			{width:150,title:'用户手机',field:'userMobilePhone'},
			{width:150,title:'用户邮箱',field:'userEmail',sortable:true},
			{width:150,title:'设备编码',field:'padCode',sortable:true},
			{width:300,title:'文件名称',field:'fileName',sortable:true},
			{width:200,title:'文件大小',field:'fileSize',sortable:true,formatter:function(value){var mb=(parseInt(value)/1024/1024).toFixed(2); return mb!=0?mb+" MB":(parseInt(value)/1024).toFixed(2)!=0?(parseInt(value)/1024).toFixed(2)+" KB":value+" Byte";}},
			{width:100,title:'状态',field:'enableStatus',sortable:true,formatter:function(value){return getDatagridDict('global.enable_status',value);}},
			{width:150,title:'上传状态',field:'uploadStatus',sortable:true,formatter:function(value){return getDatagridDict('global.upload_status',value);}},
			{width:150,title:'上传结果',field:'uploadResult',sortable:true},
			{width:200,title:'创建时间',field:'createTime',sortable:true,formatter:formatterTime},
			{width:200,title:'上传时间',field:'modifyTime',sortable:true,formatter:formatterTime},
		]]
	};
	var dialogParamObj={
		
	};
	$(function() {
		$(module_datagrid).datagrid($.extend({}, dataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
		$.extend($.fn.validatebox.defaults.rules,{
			number:{// 验证数字
	          validator : function(value) {
	              return /^[0-9]{1,9}$/gi.test(value);
	          },
	          message : '只允许1-9位的正整数'
	        }
		});
	});

	function transmission(callback){
		var ids = getGridIds();
		if (!ids)
			return false;
		
		var rows = $('#module_datagrid').datagrid('getSelections');
		for(var i=0; i<rows.length; i++){
			if(rows[i].uploadStatus == 1){
				$.messager.alert("操作提示", "只能选择非上传成功状态的文件", "info");
				return;
			}
			if(rows[i].enableStatus != 1){
				$.messager.alert("操作提示", "只能选择正常状态的文件", "info");
				return;
			}
		}
		ajaxPost("transmission", {ids : ids}, callback);
	}
	//grid搜索
	var gridSearchValidate = function() {
		var arr = $(module_search_form).serializeArray();
		var obj = {};
		$.each(arr, function(i, field) {
			obj[field.name] = field.value;
		});
		if(!($('#module_search_form').form("validate"))){
			return false;
			}
		try{
			$(module_datagrid).treegrid("reload",obj);
		}catch(e){
			$(module_datagrid).datagrid("reload",obj);
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
				会员ID：<input type="text" name="externalUserId" data-options="validType:'number'"" class="easyui-numberbox input_width_short"/>
		    </div>
		  	<div class="module_search_input">
				用户手机：<input type="text" class="easyui-textbox input_width_default" name="userMobilePhone" data-options=""/>
		  	</div>
		  	<div class="module_search_input">
				设备编码：<input type="text" class="easyui-textbox input_width_default" name="padCode" data-options=""/>
		  	</div>
		    <div class="module_search_input">
				上传状态：
		        <select class="easyui-combobox input_width_default" editable="false" name="uploadStatus">
					<option value="">[全部]</option>
					<fns:getOptions category="global.upload_status"/>
				</select>
			</div>
			<div class="module_search_input">
				上传时间：
				<input type="text" class="easyui-datebox input_width_default" editable="false" id="begin" name="beginTimeStr" data-options=""/>
			</div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearchValidate()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-addtop-rf" plain="true" onclick="transmission(callback)">上传</a>
	     	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
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
	<form action="" id="exportForm" method="post" target="_blank">
		<input type="hidden" name="exportDatas" id="exportDatas" value=""/>
		<input type="hidden" name="exportHead" id="exportHead" value=""/>
		<input type="hidden" name="exportField" id="exportField" value=""/>
		<input type="hidden" name="exportName" id="exportName" value=""/>
	</form>
	</div>
	
</body>
</html>



