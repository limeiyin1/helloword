<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>微信管理</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript"
	src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript"
	src="${ctxStatic}/jquery-jbox/2.3/i18n/jquery.jBox-zh-CN.js"></script>
<link type="text/css" rel="stylesheet"
	href="${ctxStatic}/jquery-jbox/2.3/Skins/Blue/jbox.css" />
<script type="text/javascript">
	var pk = "id";
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
			title : 'id',
			field : pk,
			checkbox : true
		}, {
			width : 70,
			title : '会员ID',
			field : 'map.externalUserId'
		}, {
			width : 100,
			title : '会员邮箱',
			field : 'userEmail',
			sortable : true
		}, {
			width : 100,
			title : '会员电话',
			field : 'userMobilePhone',
			sortable : true
		}, {
			width : 150,
			title : '微信OpenId',
			field : 'openid',
			sortable : true
		}, {
			width : 100,
			title : '微信分组',
			field : 'groupid',
			sortable : true
		}, {
			width : 100,
			title : '微信昵称',
			field : 'nickname',
			sortable : true
		}, {
			width : 50,
			title : '性别',
			field : 'sex',
			sortable : true
		}, {
			width : 50,
			title : '国家',
			field : 'country',
			sortable : true
		}, {
			width : 50,
			title : '省份',
			field : 'province',
			sortable : true
		}, {
			width : 50,
			title : '城市',
			field : 'city',
			sortable : true
		}, {
			width : 150,
			title : '微信头像地址',
			field : 'headimgurl',
			sortable : true
		}, {
			width : 100,
			title : '关注时间',
			field : 'subscribeTime',
			sortable : true,
			formatter : formatterTime
		}, {
			width : 100,
			title : '创建人',
			field : 'creater',
			sortable : true
		}, {
			width : 100,
			title : '创建时间',
			field : 'createTime',
			sortable : true,
			formatter : formatterTime
		}, {
			width : 100,
			title : '修改人',
			field : 'modifier',
			sortable : true
		}, {
			width : 100,
			title : '修改时间',
			field : 'modifyTime',
			sortable : true,
			formatter : formatterTime
		}, {
			width : 100,
			title : '状态',
			field : 'status',
			sortable : true,
			formatter : formatterStop
		}, {
			width : 100,
			title : '启用状态',
			field : 'enableStatus',
			sortable : true,
			formatter : formatterStop
		}, {
			width : 100,
			title : '备注',
			field : 'remark',
			sortable : true
		}, ] ]
	};
	var dialogParamObj = {

	};
	$(function() {
		$(module_datagrid).datagrid(
				$.extend({}, dataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
		$(look).dialog($.extend({}, dialogParam, dialogParamObj));
	});
	//解绑
	function relieve(callback) {
		var id = getGridId();
		if (!id)
			return false;
		var title = $("title").html() + " - 微信解绑";
		var href = host + 'relieveForm?id=' + id;
		$("#button-save").unbind("click").click(userBindSave);
		openRelieveForm(title, href);
	}
	function userBindSave() {
		if ($('#module_submit_form').form("validate")) {
			$.messager.progress();
			$('#module_submit_form').form("submit");
		}

	}
	function openRelieveForm(title, href) {
		$(look).dialog({
			title : title,
			href : href,
			width : 400,
			height : 400
		});
		$(look).dialog("open");
	}
	var cancel = function() {
		if (typeof module_dialog != 'undefined' && $(module_dialog)) {
			$(look).dialog("close");
		}
	}
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container">
		<div id="module_search">
			<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">会员ID:<input type="text" name="externalUserId"class="easyui-numberbox input_width_short" /></div>
				<div class="module_search_input">
					微信昵称:<input type="text" name="nickname"
						class="easyui-textbox input_width_default" /> 微信分组:<input
						type="text" name="groupid"
						class="easyui-numberbox input_width_default" />
				</div>

				<div class="module_search_input">
					会员邮箱:<input type="text" name="userEmail"
						class="easyui-textbox input_width_default" /> 会员手机:<input
						type="text" name="userMobilePhone"
						class="easyui-numberbox input_width_default" />
				</div>
				<div class="module_search_input">
					绑定状态:<select class="easyui-combobox input_width_short"
						editable="false" name="status" data-options="required:false">
						<option value="">[全部]</option>
						<fns:getOptions category="global.enable_status"></fns:getOptions>
					</select>
				</div>
				<div class="module_search_input">
					绑定时间: <input type="text" class="easyui-datebox input_width_default"
						editable="false" name="beginTimeStr" /> 至 <input type="text"
						class="easyui-datebox input_width_default" editable="false"
						name="endTimeStr" />
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
			<c:if
				test="${not empty sessionScope.permission.button_member_wechart_unbundling}">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-unlink-rf" plain="true" onclick="relieve(callback)">解绑</a>
			</c:if>
			 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>

	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>

	<div id="look" buttons="#look_button"></div>
	<div id="look_button">

		<a href="javascript:void(0)" id="button-save"
			class="easyui-linkbutton" iconCls="icon-ok-rf">解绑</a> <a
			href="javascript:void(0)" id="button-cancel"
			class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
	<div class="hide">
		<form action="" id="exportForm" method="post" target="_blank">
			<input type="hidden" name="exportDatas" id="exportDatas" value="" />
			<input type="hidden" name="exportHead" id="exportHead" value="" /> <input
				type="hidden" name="exportField" id="exportField" value="" /> <input
				type="hidden" name="exportName" id="exportName" value="" />
		</form>
	</div>
</body>
</html>



