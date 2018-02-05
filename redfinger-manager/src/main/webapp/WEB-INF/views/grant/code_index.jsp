<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>设备授权</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="grantCodeId";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField :pk,
		onCheck: function(row){
			
		},
		columns:[[
			{width:100,title:'id',field:pk,checkbox:true},
			{width:50,title:'授权编码',field:'grantCode',sortable:true},
			{width:50,title:'授权人',field:'map.grantorUserName',sortable:true},
			{width:50,title:'设备名称',field:'map.padName',sortable:true},
			{width:100,title:'控制授权',field:'map.grantControlCode',sortable:true},
			{width:50,title:'观看授权',field:'map.grantWatchCode',sortable:true},
			{width:150,title:'授权时间',field:'grantTime',sortable:true,formatter:formatterTime},
			{width:100,title:'授权开始时间',field:'grantStartTime',sortable:true,formatter:formatterTime},
			{width:50,title:'授权结束时间',field:'grantEndTime',sortable:true,formatter:formatterTime}
			
		]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	
	
	//取消设备
	var cancelGrant = function(callback) {
		var ids = getGridIds();
		if (!ids) {
			return false;
		}
		ajaxPost("cancelGrant", {
			ids : ids
		}, callback);
	}
	
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">
				授权码：
				<input type="text" name="grantCode" class="easyui-textbox input_width_default" />
			</div>
			<div class="module_search_input">
				设备名称：
				<input type="text" name="padName" class="easyui-textbox input_width_default" />
			</div>
			<div class="module_search_input">
				控制授权：
				<select class="easyui-combobox input_width_short" editable="false" name="grantControl" data-options="required:false">
						<option value="">[全部]</option>
						<c:forEach var="one" items="${grantContrls}">
							<option value="${one.key}">${one.value}</option>
						</c:forEach>
					</select>
			</div>
			<div class="module_search_input">
				观看授权：
				<select class="easyui-combobox input_width_short" editable="false" name="grantWatch" data-options="required:false">
						<option value="">[全部]</option>
						<c:forEach var="one" items="${grantWatchs}">
							<option value="${one.key}">${one.value}</option>
						</c:forEach>
					</select>
			</div>
		    <div class="module_search_input">
				授权时间：
				<input type="text" class="easyui-datebox input_width_default" editable="false" id="beginGrant" name="beginGrant" data-options=""/>
				至
				<input type="text" class="easyui-datebox input_width_default" editable="false" id="endGrant" name="endGrant" data-options=""/>
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
</body>
</html>



