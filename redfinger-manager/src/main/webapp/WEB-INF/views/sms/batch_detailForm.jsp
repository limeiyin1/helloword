<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>短信列表</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
</script>
</head>
<body>
	<!-- 表格  -->
	<div>
		<table id="module_datagrid2" style="height: 425px;"></table>
	</div>
	<script type="text/javascript">
	var formatterResultStatus=function(value,row,index){
		return getDatagridDict('rf_sms.result_status',value);	
	}
	var dataGridParamObj2={
		url:host+"detailList?id=${bean.id}",
		idField : "smsId",
		onCheck: function(row){
			
		},
		columns:[[
			{width:100,title:'手机号码',field:'smsMobile',sortable:true},
			{width:400,title:'短信内容',field:'smsContent',sortable:true},
			{width:100,title:'用户帐号',field:'userId',sortable:true},
			{width:100,title:'用户姓名',field:'userName',sortable:true},
			{width:100,title:'设备编号',field:'padCode',sortable:true},
			{width:100,title:'发送时间',field:'createTime',sortable:true,formatter:formatterTime},
			{width:60,title:'发送方式',field:'smsStatus',sortable:true,formatter:function(value){return getDatagridDict('rf_sms.sms_status',value);}},
			{width:60,title:'状态',field:'resultStatus',sortable:true,formatter:formatterResultStatus},
			{width:100,title:'备注',field:'remark'}
		]]
	};
	$("#module_datagrid2").datagrid($.extend({},dataGridParam,dataGridParamObj2));
	</script>
</body>
</html>



