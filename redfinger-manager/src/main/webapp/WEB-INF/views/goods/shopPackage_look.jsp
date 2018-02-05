<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>礼包码</title>
<meta name="decorator" content="default" />
</head>
<body>
	<script type="text/javascript">
		function doSearch() {
			$('#module_datagrid_renewal').datagrid('load', {
				code : $('#code').val(),
				userphone : $('#userphone').val(),
				buyFlag:$('#buyFlag').combobox('getValue')
			});
		}
		var module_datagrid_renewal = "#module_datagrid_renewal";
		var callback = defaultCallback;
		var dataGridParamObj = {
			url : host + "codelist?id=${bean.id}",
			idField : 'id',
			onCheck : function(index, row) {

			},
			columns : [ [
					{
						width : 100,
						title : 'id',
						field : 'id',
						checkbox : true,
					},
					{
						width : 100,
						title : '礼包码',
						field : 'code',
						sortable : true
					},
					{
						width : 100,
						title : '开始时间',
						field : 'beginTime',
						formatter : formatterTime,
						sortable : true
					},{
						width : 100,
						title : '结束时间',
						field : 'endTime',
						formatter : formatterTime,
						sortable : true
					},
					{
						width : 100,
						title : '购买时间',
						field : 'buyTime',
						formatter : formatterTime,
						sortable : true
					},
					{
						width : 100,
						title : '用户手机号',
						field : 'map.userPhone',
					},
					{
						width : 100,
						title : '是否被购买',
						field : 'buyFlag',
						formatter : function(value) {
							return getDatagridDict(
									'shop_package_code.buy_flag', value);
						},
					sortable : true
					}, ] ]
		};
		var dialogParamObj = {

		};
		$(function() {
			$(module_datagrid_renewal).datagrid(
					$.extend({}, dataGridParam, dataGridParamObj));
		});
		
		$("#modify").click(function() {
			var begin=$('#beginTimeStr').datetimebox('getValue');
			var end=$('#endTimeStr').datetimebox('getValue');
			if(begin>end){
				$.messager.alert('提示', '结束时间不能小于开始时间！', "info");
				return null;
			}
			var b=begin.length;
			var e=end.length;
			if(b==0 || e==0){
				$.messager.alert('提示', '修改时间不能为空！', "info");
				return null;
			}
			var ids = myGetGridIds();
			var id = ${bean.id};
			if (!ids) {
				return false;
			}
			$.messager.confirm('确认？', '确认修改礼包码时间?', function(confirm) {
				if (confirm) {
					ajaxPost("modify", {
						ids : ids,
						id : id,
						begin:begin,
						end:end
					}, callback);
				}
			});
		});
		
		$("#remove").click(function() {
			var ids = myGetGridIds();
			var id = ${bean.id};
			if (!ids) {
				return false;
			}
			$.messager.confirm('确认？', '确认删除此数据?', function(confirm) {
				if (confirm) {
					ajaxPost("remove", {
						ids : ids,
						id : id
					}, callback);
				}
			});
		});
		var myGetGridIds = function() {
			var row = $(module_datagrid_renewal).datagrid("getSelections");
			if (row == '') {
				$.messager.alert('提示', '请选择记录！', "info");
				return null;
			} else {
				var ids = [];
				for (var i = 0; i < row.length; i++) {
					ids[i] = row[i][pk];
			}
			return ids.join(",");
			}
		}
	</script>
	<div id="module_submit_container">
		<div id="module_search">
			<form id="module_submit_form" class="easyui-form" method="post">
				<table id="module_submit_table">
					<tr>
						<td class="td1">礼包名字:</td>
						<td class="td2">${bean.name}</td>
					</tr>
					<tr>
						<td class="td1">礼包类型:</td>
						<td class="td2">${fns:getLabelStyle('shop_package.category',bean.category)}</td>
					</tr>
					<tr>
						<td class="td1">开始时间:</td>
						<td class="td2"><fmt:formatDate value="${bean.beginTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					</tr>
					<tr>
						<td class="td1">结束时间:</td>
						<td class="td2"><fmt:formatDate value="${bean.endTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					</tr>
				</table>
			</form>
		</div>
		<br>
		<div class="module_search_button">
			<form id="user_search_form" class="easyui-form" method="post">
				<input type="hidden" id="packageid" value="${bean.id}" /> 
					礼包码:<input id="code" name="code" class="easyui-textbox input_width_default">  
					手机号:<input id="userphone" name="userphone" class="easyui-textbox input_width_default">
					状态：
		        <select class="easyui-combobox input_width_default" editable="false" name="buyFlag" id="buyFlag">
					<option value="">[全部]</option>
					<fns:getOptions category="shop_package_code.buy_flag"/>
				</select>
					 <a href="#" iconCls="icon-search-rf" class="easyui-linkbutton" plain="true" onclick="doSearch()">查询</a>

			</form>
		</div>
		<table id="module_submit_table">
			<tr>
				<td class="td1">开始时间:</td>
				<td class="td2"><input class="easyui-datetimebox input_width_default" type="text" id="beginTimeStr" /></td>
			</tr>
			<tr>
				<td class="td1">结束时间:</td>
				<td class="td2"><input class="easyui-datetimebox input_width_default" type="text" id="endTimeStr"  /></td>
			</tr>
			
		</table>
		<c:if test="${not empty sessionScope.permission.button_shoppackage_remove}">
			<a href="javascript:void(0)" id="modify" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true">修改礼包码时间</a>
		</c:if>
	</div>
	<div id="module_toolbar" class="easyui-toolbar">
		<c:if test="${not empty sessionScope.permission.button_shoppackage_remove}">
			<a href="javascript:void(0)" id="remove" class="easyui-linkbutton" iconCls="icon-remove-rf" plain="true">删除</a>
		</c:if>
	</div>
	<table id="module_datagrid_renewal" toolbar="#module_toolbar_renewal"></table>


</body>
</html>


