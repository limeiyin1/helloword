<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>产品类型</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk = "userLabelId";
	var module_datagrid = "#module_datagrid";
	var module_dialog = "#module_dialog";
	var module_search_form = "#module_search_form"
	var module_submit_form = "#module_submit_form";
	var callback = defaultCallback;

	var dataGridParamObj = {
		url : host + "list",
		idField : 'userLabelId',
		onCheck : function(row) {

		},
		columns : [[ 
		              {width : 100,title : 'id',field : 'userLabelId',checkbox : true}, 
		              {width : 100,title : '手机号码',field : 'map.userMobilePhone',sortable : false}, 
		              {width : 100,title : '标签编码',field : 'map.labelCode',sortable : false}, 
		              {width : 100,title : '标签名称',field : 'map.labelName',sortable : false}, 
		              {width : 100,title : '创建人',field : 'creater',sortable : true}, 
					  {width : 150,title : '创建时间',field : 'createTime',sortable : true,formatter : formatterTime},
		              {width : 100,title : '启用状态',field : 'enableStatus',sortable : true,formatter : formatterStop} 
		          ]]
		};
	var dialogParamObj = {};
	$(function() {
		$(module_datagrid).datagrid($.extend({}, dataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
	});

	function formatterLabelType(value, row, index) {
		return getDatagridDict('rf_label.label_type',value)
	}

	
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container">
		<div id="module_search">
			<form id="module_search_form" class="easyui-form" method="post">
				<div class="module_search_input">		
					标签名称:<select class="easyui-combobox"  editable="false" name="labelId" data-options="required:false" >
							  <option value="">[全部]</option>
							  <c:forEach items="${labels }" var="label">
							  		<option value="${label.labelId }">${label.labelName }</option>
							  </c:forEach>
						  </select> 
				 </div>	
				<div class="module_search_input">
					用户号码：<input type="text" name="userMobilePhone"
						class="easyui-textbox input_width_default" /> 
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
			<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="add()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a> -->
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
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



