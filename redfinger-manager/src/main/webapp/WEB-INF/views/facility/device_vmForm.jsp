<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>添加虚拟设备</title>
<meta name="decorator" content="default" />
</head>
<body>
	<script type="text/javascript">
	$('.easyui-form').form({
		 onSubmit: function(){ 
				  var row = $(module_datagrid_renewal).datagrid("getSelections");
						var ids = [];
						for (var i = 0; i < row.length; i++) {
							ids[i] = row[i]["padId"]
						}
						var padIds=ids.join(",");
					$("#padIds").textbox('setValue',padIds);
			    }, 
		    url:host+'addVM',   
		    success:callback
		}); 

		function doSearch(){
			$('#module_datagrid_renewal').datagrid('load',{
				padCode: $('#itemid').val(),
				padIp: $('#twoid').val()
			});
		}	

	var module_datagrid_renewal = "#module_datagrid_renewal";
	var callback = defaultCallback ;
	var dataGridParamObj = {
		    url : host + "padlistNotDevice?padSource=${bean.deviceSource}",
			idField : 'padId',
			onCheck : function(index,row) {
		
			},
			columns : [ [
			         	{width : 100,title : 'id',field :'padId',checkbox : true,}, 
			         	{width : 100,title : '设备名称',field:'padName',sortable : true},
			         	{width : 100,title : '设备编码',field:'padCode',sortable:true},
			         	{width : 100,title : '虚拟节点序号',field:'padSn',sortable:true},
			         	{width : 100,title : '物理设备编号',field:'map.deviceCode'},
			         	{width:100,title:'设备控制端口',field:'padControlPort',sortable:true},
			          	{width:100,title:'设备IP',field:'padIp',sortable:true},
			          	{width:100,title:'MAC',field:'vmMac',sortable:true},
			        	{width : 60, title : '授权状态',field:'grantOpenStatus',sortable:true ,formatter:function(value){return getDatagridDict('rf_pad.grant_open_status',value);}},
			          	{width : 60, title : '虚拟状态',field:'vmStatus',sortable:true ,formatter:function(value){return getDatagridDict('rf_pad.vm_status',value);}},
			          	{width : 60,title : '绑定状态',field : 'bindStatus',sortable : true,formatter:function(value){return getDatagridDict('rf_pad.bind_status',value);}},
			         	{width : 60,title : '启用状态',field : 'enableStatus',sortable : true,formatter:function(value){return getDatagridDict('rf_pad.enable_status',value);}},
			         	{width : 60,title : '受控状态',field : 'padStatus',sortable : true,formatter:function(value){return getDatagridDict('rf_pad.pad_status',value);}},
			         	{width : 60,title : '故障状态',field : 'faultStatus',sortable : true,formatter:function(value){return getDatagridDict('rf_pad.fault_status',value);}}                                                                                                                                                      
			         	] ]  
		};
		var dialogParamObj = {

		};
		$(function() {
			$(module_datagrid_renewal).datagrid($.extend({}, dataGridParam, dataGridParamObj));
		});
		$("#divPadIds" ).css("display", "none");
	</script>
 
	<div id="module_submit_container">
	<div id="module_search" >
		<form id="module_submit_form" class="easyui-form" method="post">
		<input type="hidden" name=deviceId value="${bean.deviceId}" />
		<input type="hidden" name="deviceType" value="${bean.deviceType}"  />
		<div id="divPadIds" style="margin:10px 0;">
		 <input type="hidden" name="ids" class="easyui-textbox input_width_default" id=padIds  />
		 <input type="hidden" name="type" class="easyui-textbox input_width_default" value="add"  />
		 

		</div>
		 <table id="module_submit_table" >
				<tr>
					<td class="td1"> 设备编号:</td>
					<td class="td2">${bean.deviceName}</td>
				</tr>
				<tr>
					<td class="td1"> 设备IP:</td>
					<td class="td2">${bean.deviceIp}</td>
				</tr>
		</table>
	   </form>
		</div>
		
		<div class="module_search_button">
			<form id="user_search_form" method="post">
				设备编码:<input id="itemid" class="easyui-textbox input_width_default">
				设备IP:<input id="twoid" class="easyui-textbox input_width_default">
				<a href="#" data-options="iconCls:'icon-search-rf',plain:true" class="easyui-linkbutton" onclick="doSearch()">查询</a>
			</form>
		</div> 
	  </div>
	
			<table id="module_datagrid_renewal" toolbar="#module_toolbar_renewal"  ></table>
		

</body>
</html>


