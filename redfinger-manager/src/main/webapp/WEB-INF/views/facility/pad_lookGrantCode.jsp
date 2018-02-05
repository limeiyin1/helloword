<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>查看设备授权码</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
			url : host + 'list',
			success : callback
		});
		
		
		function grantSearch() {
			$('#module_grant_datagrid').datagrid('load', {
				grantCode : $('#grantCode').val()
			});
		}
		
		var module_grant_datagrid = "#module_grant_datagrid";
		var callback = defaultCallback ;
		var dataGridParamObj = {
				idField : 'padId',
				onCheck : function(index,row) {
					
				},
				columns : [ [
				         	{width : 100,title : '设备名称',field : 'map.padName',sortable : true},
							{width : 100,title : '授权码',field : 'grantCode',sortable : true},
							{width : 100,title : '授权码失效时间',field : 'expireTime',sortable : true,formatter : formatterTime},
							{width : 100,title : '控制授权',field : 'map.grantControlName',sortable : true},
							{width : 100,title : '观看授权',field : 'map.grantWatchName',sortable : true},
							{width : 100,title : '授权码状态',field : 'map.grantCodeStatusName',sortable : true},
							{width : 100,title : '用户授权时间',field : 'grantTime',sortable : true,formatter : formatterTime},
							{width : 100,title : '开始时间',field : 'grantStartTime',sortable : true,formatter : formatterTime},
							{width : 100,title : '结束时间',	field : 'grantEndTime',sortable : true,formatter : formatterTime}
				    
				         	] ]
			};
			var dialogParamObj = {

			};
			$(function() {
				$(module_grant_datagrid).datagrid($.extend({}, dataGridParam, dataGridParamObj));
			});
	</script>
	<div id="module_submit_container">
		<form id="module_search_form" class="easyui-form" method="post" name="module_submit_form_module">
			<input type="hidden" name="padId" id="padId" value ="${padId }">
			<input type="hidden" name="enableStatus" id="enableStatus" value ="${enableStatus }">
			<div class="module_search_input">
				授权码：<input type="text" id="grantCode" name="grantCode"
					class="easyui-textbox input_width_default" />
					<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-search-rf" plain="false" onclick="grantSearch()">搜索</a>
			</div>
			<div id="#module_grant_toolbar" class="easyui-toolbar">
			</div>
			<table style="width: 700px" id="module_grant_datagrid" url="${host}pad/getGrantCode?padId=${padId}&enableStatus=${enableStatus}" toolbar="#module_grant_toolbar"></table>
		</form>
	</div>
	
</body>
</html>



