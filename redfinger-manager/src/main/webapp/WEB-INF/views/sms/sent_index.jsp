<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>短信列表</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var formatterResultStatus=function(value,row,index){
		return getDatagridDict('rf_sms.result_status',value);	
	}
	
	var formatterSmsSource=function(value,row,index){
		return getDatagridDict('sms.sms_source',value);	
	}
	
	var formatterSmsSendType=function(value,row,index){
		return getDatagridDict('sms.sms_send_type',value);	
	}
	var pk="smsId";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField : pk,
		onCheck: function(row){
			
		},
		columns:[[
			{width:100,title:'id',field:'smsId',checkbox:true},
			{width:100,title:'会员ID',field:'map.externalUserId'},
			{width:100,title:'手机号码',field:'smsMobile',sortable:true},
			{width:600,title:'短信内容',field:'smsContent',sortable:true},
			{width:100,title:'用户帐号',field:'userId',sortable:true},
			{width:100,title:'发送时间',field:'createTime',sortable:true,formatter:formatterTime},
			{width:60,title:'发送方式',field:'smsStatus',sortable:true,formatter:function(value){return getDatagridDict('rf_sms.sms_status',value);}},
			{width:60,title:'状态',field:'resultStatus',sortable:true,formatter:formatterResultStatus},
			/* {width:60,title:'回执状态编码',field:'resultStatusCode',sortable:true}, */
			{width:60,title:'回执状态',field:'map.resultStatusName',sortable:false},
			{width:60,title:'发送方',field:'smsSendType',sortable:true,formatter:formatterSmsSendType},
			{width:60,title:'短信来源',field:'smsSource',sortable:true,formatter:formatterSmsSource},
			{width:60,title:'客户端来源',field:'map.clientSourceName',sortable:false},
			{width:100,title:'短信类型',field:'map.smsTypeName',sortable:false},
			{width:100,title:'备注',field:'remark'}
		]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	function addmobileForm(){
		var title = $("title").html() + " - 发送手机短信";
		var href = host + 'addmobileForm';
		$("#button-save").unbind("click").click(addmobile);
		openDialogForm(title, href);
	}
	function addmobile(){
		if ($(module_submit_form).form("validate")) {
			$.messager.progress();
			$(module_submit_form).form({
			    url:host+'addmobile'   
			});
			$(module_submit_form).form("submit");
		}
	}
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">会员ID：<input type="text" name="externalUserId" class="easyui-numberbox input_width_short"/></div>
			<div class="module_search_input">手机号码：<input type="text" name="smsMobile" class="easyui-textbox input_width_default"/></div>
			<div class="module_search_input">用户姓名：<input type="text" name="userName" class="easyui-textbox input_width_short"/></div>
			<div class="module_search_input">设备编号：<input type="text" name="padCode" class="easyui-textbox input_width_short"/></div>
			<div class="module_search_input">发送时间：<input style="width: 100px" editable="false" type="text" name="beginTimeStr" class="easyui-datebox input_width_short"/>至<input style="width: 100px" editable="false" type="text" name="endTimeStr" class="easyui-datebox input_width_short"/></div>
			
			<div class="module_search_input">
				短信类型：<select class="easyui-combobox" editable="false" name="smsType">
							<option value="">全部</option>
							<c:forEach items="${smsType}" var="one">
								<option value="${one.key }">${one.value }</option>
							</c:forEach>
						</select>
			</div>
			<div class="module_search_input">
				短信来源：<select class="easyui-combobox" editable="false" name="smsSource">
							<option value="">全部</option>
							<fns:getOptions category="sms.sms_source"></fns:getOptions>
						</select>
			</div>
			<div class="module_search_input">
				客户端来源：<select class="easyui-combobox" editable="false" name="clientSource">
							<option value="">全部</option>
							<c:forEach items="${clientType}" var="one">
								<option value="${one.key }">${one.value }</option>
							</c:forEach>
						</select>

			</div>
			<div class="module_search_input">
				发送来源：<select class="easyui-combobox" editable="false" name="smsSendType">
							<option value="">全部</option>
							<fns:getOptions category="sms.sms_send_type"></fns:getOptions>
						</select>
			</div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<c:if test="${not empty sessionScope.permission.button_sms_sent_addmobile}">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="addmobileForm()">发送手机短信</a>
			</c:if>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a>
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



