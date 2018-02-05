<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>激活码日志管理</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="logId";
	var module_datagrid = "#module_datagrid";
	var module_dialog = "#module_dialog";
	var module_search_form = "#module_search_form";
	var module_submit_form = "#module_submit_form";
	var callback = defaultCallback;
	var dataGridParamObj = {
		url : host + "list",
		idField : pk,
		onCheck : function(index,row) {
			
		},
		columns : [[
			{width : 100,title : 'id',field : pk,checkbox : true},
			{width : 100,title : '优惠劵编码',field : 'couponCode',sortable : true},
			{width : 100,title : '优惠劵名称',field : 'couponName',sortable : true},
			{width : 100,title : '优惠劵类型名称',field : 'map.couponTypeName',sortable : true},
			{width : 100,title : '操作类型',field : 'map.logTypeName',sortable : true},
			{width : 100,title : '创建人',field : 'creater',sortable : true}, 
			{width : 150,title : '创建时间',field : 'createTime',sortable : true,formatter : formatterTime},
			{width : 100,title : '状态',field : 'status',sortable : true,formatter : formatterStop},
			{width : 100,title : '启用状态',	field : 'enableStatus',	sortable : true,formatter : formatterStop},
			{width : 150,title : '描述',	field : 'remark',	sortable : true}
			
		]]
	};
	var dialogParamObj = {

	};
	$(function() {
		$(module_datagrid).datagrid($.extend({}, dataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
	});
	
	
	
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container">
		<div id="module_search">
			<form id="module_search_form" class="easyui-form" method="post">
				<div class="module_search_input">
					优惠劵类型：<select class="easyui-combobox" editable="false" name="typeId">
								<option value="">全部</option>
								<c:forEach items="${typeList}" var="one">
									<option value="${one.typeId }">${one.typeName }</option>
								</c:forEach>
							</select>

				</div>
				<div class="module_search_input">
					优惠劵编码：<input type="text" name="couponCode"
						class="easyui-textbox input_width_default" />

				</div>
				<div class="module_search_input">
					操作类型：
			        <select class="easyui-combobox" editable="false" name="logType">
						<option value="">[全部]</option>
						<c:forEach items="${map}" var="one">
							<option value="${one.key }">${one.value }</option>
						</c:forEach>
					</select>
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
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>
	<!-- 新增框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
	<div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save"
			class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a> <a
			href="javascript:void(0)" id="button-cancel"
			class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
	
	<div style="display:none">
	<!-- 图片编辑框 -->
	<div id="activation_code_dialog" buttons="#activation_code_dialog_button" ></div>
	<div id="activation_code_dialog_button" >
		<a href="javascript:void(0)" id="button-save-pic" class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a>
		<a href="javascript:void(0)" id="button-cancel-pic"	class="easyui-linkbutton" iconCls="icon-no" onclick="pictureCancel()">关闭</a>
	</div>
	</div>
</body>
</html>