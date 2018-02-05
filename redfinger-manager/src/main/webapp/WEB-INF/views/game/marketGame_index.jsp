<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>游戏管理</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="relationId";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField : 'relationId',
		onCheck: function(row){
			
		},
		columns:[[
			{width:100,title:'relationId',field:pk,checkbox:true},
			{width:100,title:'游戏名称',field:'map.gameName',sortable:false},
			{width:100,title:'应用市场类型',field:'marketType',sortable:true,formatter:function(value){return getDatagridDict('rf_game.show_market_type',value);}},
			{width:100,title:'状态',field:'enableStatus',sortable:true,formatter:formatterStop}
		]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	
	
 	
	
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
		    <div class="module_search_input">
		              游戏：<select class="easyui-combobox"  editable="false" name="gameId" data-options="required:false" >
					  <option value="">[全部]</option>
					  <c:forEach items="${games }" var="one">
					  	<option value="${one.gameId }">${one.gameName }</option>
					  </c:forEach>
				  </select> 
		    </div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search-rf',plain:false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reset-rf',plain:false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove-rf',plain:true" onclick="del(callback)">删除</a>
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


