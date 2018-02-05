<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>短信发送设备列表</title>
<meta name="decorator" content="moduleIndex" />

<script type="text/javascript" src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/jquery-jbox/2.3/i18n/jquery.jBox-zh-CN.js"></script>
<link type="text/css" rel="stylesheet" href="${ctxStatic}/jquery-jbox/2.3/Skins/Blue/jbox.css"/>

<style type="text/css">
	span.jbox-content2{
		font-size: 12px;
		line-height: 16px;
	}
	span.red{
		color: red
	}
	span.green{
		color: green;
	}
</style>
<script type="text/javascript">
	var pk="padId";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;

	var formatterPadStatus=function(value,row,index){
		return getDatagridDict('rf_pad.pad_status',value);
	}

	var dataGridParamObj={
		url:host+"list",
		idField:pk,
		columns:[[
			{width:20,title:'id',field:'padId',checkbox:true},
			{width:100,title:'设备编码',field:'padCode',sortable:true},
			{width:100,title:'设备IP',field:'padIp',sortable:true},
			{width:100,title:'机房名称',field:'map.idcId'},
			{width:100,title:'控制节点',field:'controlCode',sortable:true},
			{width:100,title:'用户账号',field:'userIdT2'},
			{width:100,title:'绑定手机',field:'map.userMobilePhone'},
			{width:100,title:'绑定日期',field:'bindTimeT2',formatter:formatterTime},
			{width:100,title:'是否可用',field:'enableStatus',sortable:true,formatter:formatterEnableStatus},
			{width:100,title:'是否绑定',field:'bindStatus',sortable:true,formatter:formatterBindStatus},
			{width:100,title:'受控状态',field:'padStatus',sortable:true,formatter:formatterPadStatus},
			{width:100,title:'备注',field:'remark'},
			{width : 100,title : '设备来源',field:'map.padSourceName'}
		]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	//绑定状态格式化（禁用、可用）
	function formatterBindStatus(value, row, index) {
		return getDatagridDict('rf_pad.bind_status',value);
	}
	//状态格式化（禁用、可用）
	function formatterEnableStatus(value, row, index) {
		return getDatagridDict('rf_pad.enable_status',value);
	}
	//故障状态
	function formatterFaultStatus(value, row, index) {
		return getDatagridDict('rf_pad.fault_status',value)
	}
	function add_checkbox(){
		var ids = getGridIds();
		if (!ids) {
			return false;
		}
		var title = $("title").html() + " - 当前发送（"+ids.split(",").length+"条）";
		var href = host + 'form';
		$("#button-save").unbind("click").click(save_checkbox);
		openDialogForm(title, href);
	}
	function save_checkbox(){
		var ids = getGridIds();
		if ($(module_submit_form).form("validate")) {
			$.messager.progress();
			$(module_submit_form).form({
			    url:host+'sendCheckbox',
			    onSubmit: function(param){    
			    	param.ids = ids;    
			    }
			});
			$(module_submit_form).form("submit");
		}	
	}
	
	function add_search(){
		var total=$(module_datagrid).datagrid("getData").total;
		if(total<=0){
			$.messager.alert('提示', '无记录，可选择其他查询条件', "info");
			return false;
		}
		var title = $("title").html() + " - 当前发送（"+total+"条）";
		var href = host + 'form';
		$("#button-save").unbind("click").click(save_search);
		openDialogForm(title, href);
	}
	function save_search(){
		if ($(module_submit_form).form("validate")) {
			$.messager.progress();
			$(module_submit_form).form({
			    url:host+'sendSearch',
			    onSubmit: function(param){    
			    	var params=$(module_datagrid).datagrid('options').queryParams;
			    	for(var i in params){
			    		param[i]=params[i];
			    	}
			    }
			});
			$(module_submit_form).form("submit");
		}	
	}
	function smsCallback(data) {
		$.messager.progress('close');
		cancel();
		if (jQuery.type(data) == 'string') {
			data = eval('(' + data + ')');
		}
		var info='<span class="jbox-content2">';
		info+='短信总数：'+data.total+'<br/>';
		info+='<span class="red">发送成功：'+data.success+'</span><br/>';
		info+='<span class="green">发送失败：'+data.fault+'</span><br/>';
		info+='详情请查看历史短信列表！';
		info+='</span>';
		$.jBox.messager(info, '短信发送结果!',5000);
	}
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">
				编码段：<input type="text" name="padCode" class="easyui-textbox input_width_default" />至
				<input type="text" name="padCode2" class="easyui-textbox input_width_default" />
			</div>
			<div class="module_search_input">
				IP：<input type="text" name="padIp" class="easyui-textbox input_width_default" />
			</div>
			<div class="module_search_input">
				机房：<select editable="false" name="idcId" class="easyui-combobox input_width_default" >
					<option value="">[全部]</option>
					<c:forEach var="one" items="${idcList}">
						<option value="${one.idcId}">${one.idcName}</option>
					</c:forEach>
				</select>
			</div>
			<div class="module_search_input">
				受控状态：<select editable="false" name="padStatus" class="easyui-combobox input_width_default" >
					<option value="">[全部]</option>
			        <fns:getOptions category="rf_pad.pad_status"  ></fns:getOptions>
				</select>
			</div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok-rf" plain="true" onclick="add_checkbox()">发送当前选中</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="add_search()">发送后台所有</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" plain="true">|</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>
	
	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
    <div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton" iconCls="icon-ok-rf">确定</a>
		<a href="javascript:void(0)" id="button-cancel" class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">取消</a>
	</div>
	
</body>
</html>



