<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>我的考核表</title>
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
		idField : 'id',
		pagination : false,
		onCheck: function(row){
			
		},
		columns:[[
			{width:100,title:'id',field:pk,checkbox:true},
			{width:100,title:'考核名目',field:'map.proname'},
			{width:100,title:'被评价人',field:'targetAdmin',sortable:true},
			{width:150,title:'评价开始的时间',field:'map.probegintime',formatter:formatterDate},
			{width:150,title:'评价结束时间',field:'map.proendtime',formatter:formatterDate},
			{width:100,title:'评价提交人',field:'assessAdmin'},
		]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	
	function getScore() {
		var id = getGridId();
		if (!id)
			return false;
		var title = $("title").html() + " - 打分问卷";
		;
		var href = host + 'formScore?id=' + id;
		$("#button-save").unbind("click").click(count);
		openDialogForm(title, href);
	}
	
	var count = function() {
		if ($(module_submit_form).form("validate")) {
			$.messager.progress();
			$(module_submit_form).form({
				url : host + 'count'
			});
			$(module_submit_form).form("submit");
		}
	}

</script>
</head>
<body>

	<div id="module_container" >
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit-rf',plain:true" onclick="getScore()">打分</a>
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



