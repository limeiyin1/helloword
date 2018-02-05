<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>用户反馈</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript"
	src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript"
	src="${ctxStatic}/jquery-jbox/2.3/i18n/jquery.jBox-zh-CN.js"></script>
<link type="text/css" rel="stylesheet"
	href="${ctxStatic}/jquery-jbox/2.3/Skins/Blue/jbox.css" />
<script type="text/javascript">
	var pk = "feedbackId";
	var module_datagrid = "#module_datagrid";
	var module_dialog = "#module_dialog";
	var module_search_form = "#module_search_form"
	var module_submit_form = "#module_submit_form";
	var callback = defaultCallback;
	var dataGridParamObj = {
		url : host + "list",
		idField : pk,
		onCheck : function(row) {

		},
		columns : [ [ {
			width : 100,
			title : 'feedbackId',
			field : pk,
			checkbox : true
		}, {
			width : 100,
			title : '用户手机',
			field : 'userMobilePhone',
			sortable : true
		}, {
			width : 150,
			title : '反馈类型',
			field : 'feedbackType',
			sortable : true
		}, {
			width : 100,
			title : '反馈内容',
			field : 'feedbackContent',
			sortable : true
		}, {
			width : 100,
			title : '处理状态',
			field : 'handleStatus',
			sortable : true,
			formatter:function(val, row) {if(val=='0'){return "未处理";}else if(val=='1'){return "已处理";}}
		}, {
			width : 100,
			title : '处理人',
			field : 'handlePerson',
			sortable : true
		}, {
			width : 100,
			title : '反馈时间',
			field : 'createTime',
			sortable : true,
			formatter : formatterTime
		}, {
			width : 100,
			title : '处理时间',
			field : 'handleTime',
			sortable : true,
			formatter : formatterTime
		} ] ]
	};
	var dialogParamObj = {};
	$(function() {
		$(module_datagrid).datagrid(
				$.extend({}, dataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
	});

	var hanOk = function() {
		var id=getGridId();
		if(!id)return false;
		$.ajax({
			url:host + "handle?" + pk + '=' + id,
			success:callback,
		});
	}
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container">
		<div id="module_search">
			<form id="module_search_form" class="easyui-form" method="post">
				<div class="module_search_input">
					商品编码：<input type="text" name="goodsCode"
						class="easyui-textbox input_width_default" /> 商品名称：<input
						type="text" name="goodsName"
						class="easyui-textbox input_width_default" /> 商品类型：<input
						type="text" name="goodsType"
						class="easyui-textbox input_width_default" />
				</div>
				<div class="module_search_input">
					反馈时间：<input type="text" class="easyui-datebox input_width_default"
						editable="false" id="begin" name="begin" data-options="" /> 至<input
						type="text" class="easyui-datebox input_width_default"
						editable="false" id="end" name="end" data-options="" />
				</div>
				<div class="module_search_button">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
				</div>
			</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-ok-rf" plain="true" onclick="hanOk()">处理完成</a> 

		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>

	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
	<div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save"
			class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a> <a
			href="javascript:void(0)" id="button-cancel"
			class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
</body>
</html>




