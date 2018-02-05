<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>绩效考核明细</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="id";
	var module_shiyao="#module_shiyao";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField : 'id',
		onCheck: function(row){
			
		},
		columns:[[
			{width:100,title:'id',field:pk,checkbox:true},
			{width:100,title:'考核名称',field:'name',sortable:true},
			{width:100,title:'考核开始时间',field:'beginTime',sortable:true,formatter:formatterTime},
			{width:100,title:'考核结束时间',field:'endTime',sortable:true,formatter:formatterTime},
			{width:100,title:'备注',field:'remark',sortable:true}
		]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
		$(module_shiyao).dialog($.extend({},dialogParam,dialogParamObj));
	});
	
	function getOption(){
		var id = getGridId();
		if (!id) {
			return false;
		}
		$(module_shiyao).dialog({title : "请输入私钥：",width:520,height:300});
		$(module_shiyao).dialog("open");
		$("#shiyao-save").unbind("click").click(sySave);
	}
	function sySave(){
		cancelShiyao();
		var id = getGridId();
		var sy =  $("#shiyao").val();
		if (!sy || !id) {
			return false;
		}
		var title =$("title").html() + " - 查看明细";
		var href = host + 'formOption?id=' + id+"&sy="+sy;
		$("#button-save").unbind('click').hide();
		openDialogLook(title, href);
	}
	var openDialogLook = function(title, href) {
		$(module_dialog).dialog({title : title,href: href,width:600});
		$(module_dialog).dialog("open");
	}

	var cancelShiyao = function() {
		if(typeof module_shiyao!='undefined' && $(module_shiyao)){
			$(module_shiyao).dialog("close");
		}
	}
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">考核名称：<input type="text" name="name" class="easyui-textbox input_width_default"/></div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search-rf',plain:false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reset-rf',plain:false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload-rf',plain:true" onclick="fresh()">刷新</a>
		 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-detail-rf" plain="true" onclick="getOption()">考卷得分详情</a>
	 		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>
	<!-- 私钥输入框 -->
	<div id="module_shiyao" buttons = "#module_shiyao_button">
		<input class="easyui-textbox" id="shiyao" data-options="required:true,multiline:true,validType:'length[0,1000]'"
		style="width:500px;height: 200px" />
	</div>
	 <div id="module_shiyao_button">
		<a href="javascript:void(0)"  id="shiyao-save" class="easyui-linkbutton"data-options="iconCls:'icon-ok-rf'">确定</a>
		<a href="javascript:void(0)"  class="easyui-linkbutton" data-options="iconCls:'icon-no'" onclick="cancelShiyao()">关闭</a>
	</div>
	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
    <div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton"data-options="iconCls:'icon-ok-rf'">保存</a>
		<a href="javascript:void(0)" id="button-cancel" class="easyui-linkbutton" data-options="iconCls:'icon-no'" onclick="cancel()">关闭</a>
	</div>
</body>
</html>



