<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>激活码管理</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="codeId";
	var module_datagrid = "#module_datagrid";
	var module_dialog = "#module_dialog";
	var module_search_form = "#module_search_form";
	var module_submit_form = "#module_submit_form";
	var callback = defaultCallback;
	var dataGridParamObj = {
		url : host + "list",
		idField : pk,
		onCheck : function(index,row) {
			//是否是激活
			var flag=row.activationStatus;
			if(flag=='1'){
				$("#edit").linkbutton('disable');
			}else{
				$("#edit").linkbutton('enable');
			}
		},
		columns : [[
			{width : 100,title : 'id',field : pk,checkbox : true},
			{width : 100,title : '激活码',field : 'activationCode',sortable : true},
			{width : 100,title : '激活码类型名称',field : 'map.activationTypeName',sortable : true},
			{width : 100,title : '批次号',field : 'map.batchNumber',sortable : true},
			{width : 60,title : '设备类型',field : 'map.padTypeName',sortable : true},
			{width : 100,title : '开始时间',field : 'startTime',sortable : true,formatter : formatterTime},
			{width : 150,title : '结束时间',	field : 'endTime',sortable : true,formatter : formatterTime}, 
			{width : 80,title : '是否激活',field : 'map.activationStatusName',sortable : true},
			{width : 60,title : '绑定状态',field : 'bindStatus',sortable : true,formatter : function(value){return getDatagridDict('rf_activation.activation_bind',value);}},
			{width : 100,title : '设备时长(小时)',field : 'map.padUsetime',sortable : true},
			{width : 100,title : '创建人',field : 'creater',sortable : true}, 
			{width : 150,title : '创建时间',field : 'createTime',sortable : true,formatter : formatterTime},
			{width : 60,title : '状态',field : 'status',sortable : true,formatter : formatterStop},
			{width : 60,title : '启用状态',	field : 'enableStatus',	sortable : true,formatter : formatterStop}
		]]
	};
	var dialogParamObj = {

	};
	$(function() {
		$(module_datagrid).datagrid($.extend({}, dataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
		$("#module_dialog_edit").dialog($.extend({}, dialogParam, dialogParamObj));
	});
	
	function addActivation(){
		var title = $("title").html() + " - 新增";
		var href = host + 'addActivation';
		$("#button-save").unbind("click").click(saveActivation);
		$(module_dialog).dialog({title : title,href: href,width:550, left:screen.width/4});
		$(module_dialog).dialog("open");
	}
	
	function saveActivation(){
		if ($(module_submit_form).form("validate")) {
			$.messager.progress();
			$(module_submit_form).form({
				url : host + 'saveActivation'
			});
			$(module_submit_form).form("submit");
		}
	}
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
 				where+=i+"="+params[i]+"&";
 			}
 			$("#exportForm").attr("action",host+'export?'+where);
 			$("#exportHead").val(exportHead);
 			$("#exportField").val(exportField);
 			$("#exportName").val('激活码');
 			$("#exportForm").submit();
 		}else{
 			$.messager.alert('操作失败','无数据！',"warning");
 		}
 	}
 	
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container">
		<div id="module_search">
			<form id="module_search_form" class="easyui-form" method="post">
				<div class="module_search_input">
					激活码类型：<select class="easyui-combobox" editable="false" name="typeId">
					            <option value="">全部</option>
								<c:forEach items="${typeList}" var="one">
									<option value="${one.typeId }">${one.activationTypeName }</option>
								</c:forEach>
							</select>

				</div>
				<div class="module_search_input">
					激活码：<input type="text" name="activationCode"
						class="easyui-textbox input_width_default" />

				</div>
				<div class="module_search_input">
					是否激活：
			        <select class="easyui-combobox" editable="false" name="activationStatus">
						<option value="">[全部]</option>
						<c:forEach items="${map}" var="one">
							<option value="${one.key }">${one.value }</option>
						</c:forEach>
					</select>
				</div>
				
				<!-- 是否绑定 -->
				<div class="module_search_input">		
				绑定:<select class="easyui-combobox input_width_short" editable="false" name="bindStatus" data-options="required:false" >
					<option value="">[全部]</option>
					<fns:getOptions category="rf_pad.bind_status"></fns:getOptions>
				</select> 
				</div>
				
				<!-- 启用状态 -->
				<div class="module_search_input">
					启用状态：
					<select class="easyui-combobox input_width_short" editable="false" name="enableStatus" data-options="required:false">
						<option value="">[全部]</option>
						<fns:getOptions category="global.enable_status"/>
					</select>
				</div>
				
				<!-- 设备类型 -->
				<div class="module_search_input">
					设备类型:
					<select class="easyui-combobox input_width_short" editable="false" name="padType" id="padType" data-options="required:false">
						<option value="">[全部]</option>
						<c:forEach var="one" items="${padType}">
							<option value="${one.key}">${one.value}</option>
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
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="addActivation()">批量生成激活码</a> 
			<a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit-rf" id="edit" plain="true" onclick="edit()">编辑</a> 
			<a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用
			</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel-rf" plain="true" onclick="statExport()">导出</a>
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