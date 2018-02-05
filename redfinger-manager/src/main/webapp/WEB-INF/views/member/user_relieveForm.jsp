<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>解绑</title>
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
					$("#relievePadId").textbox('setValue',padIds);
			    },  
			url : host + 'relieve',
			success : callback
		});

		function doSearch() {
			$('#module_datagrid_renewal').datagrid('load', {
				padCode : $('#itemid').val(),
				padName : $('#productid').val()
			});
		}
		
		var module_datagrid_renewal = "#module_datagrid_renewal";
		var callback = defaultCallback ;
		var dataGridParamObj = {
				idField : 'padId',
				onCheck : function(index,row) {
					
				},
				columns : [ [
				         	{width : 100,title : 'id',field :'padId',checkbox : true}, 
				         	{width : 100,title : '设备名称',field:'padName',sortable : true},
				         	{width : 100,title : '设备编码',field:'padCode',sortable:true},
				         	{width:100,title:'设备控制端口',field:'padControlPort',sortable:true},
				         	{width:100,title:'设备IP',field:'padIp',sortable:true}
				    
				         	] ]
			};
			var dialogParamObj = {

			};
			$(function() {
				$(module_datagrid_renewal).datagrid($.extend({}, dataGridParam, dataGridParamObj));
			});
			$("#divPadId" ).css("display", "none");
	</script>
     <div id="module_submit_container">
	 <div id="module_search">
		<form id="module_submit_form" class="easyui-form" method="post">
		<input type="hidden" name="userId" value="${user.userId}" />
		<table id="module_submit_table">
		<tr>
			<td class="td1"> 用户邮箱：</td>
			<td class="td2">${user.userEmail}  <input type="hidden" name="userEmail" value="${user.userEmail}" /></td>
		</tr>
		<tr>
			<td class="td1"> 用户手机：</td>
			<td class="td2">${user.userMobilePhone}<input type="hidden" name="userMobilePhone"value="${user.userMobilePhone}" /></td>
		</tr>
		</table>
		<div id="divPadId" style="margin:10px 0;">
		 <input type="hidden" name="padIds" class="easyui-textbox input_width_default" id="relievePadId"  />
		</div>
		</form>
		</div>
		<div class="module_search_button">
			<form id="user_search_form" method="post">
				设备编码:<input id="itemid" class="easyui-textbox input_width_default">
				设备名称:<input id="productid" class="easyui-textbox input_width_default">
				<a href="#" iconCls="icon-search-rf" class="easyui-linkbutton"plain="true"onclick="doSearch()">查询</a>
			</form>
		</div> 
	</div>
			<table id="module_datagrid_renewal" toolbar="#module_toolbar_renewal" url="${host}user/padlist?userId=${user.userId}" ></table>
   

</body>
</html>


