<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>激活码批量修改</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
			
			url : host + 'batchEditActivation',
			success : callback
		});
		/* $(document).ready(function(){
			var activationCode = $('#activationCode').val();
			alert(activationCode);
			 var padType = $('#codeTypePadType').combobox('getValue');
			 alert(padType);
			$('.easyui-form').form('submit', {
				url: host + 'batchEditActivation?activationCode='+activationCode+'&padType='+padType,
				onSubmit: function(){
					
				},
				success : callback
			}); 
		}); */
		
		
		
		
		function doSearch() {
			$('#module_datagrid_activation').datagrid('load', {
				activationCode : $('#activationCode').val(),
				padType : $('#padType').combobox('getValue')
			});
		}
		
		var module_datagrid_activation = "#module_datagrid_activation";
		var callback = defaultCallback ;
		var dataGridParamObj = {
			idField : 'codeId',
			onCheck : function(index,row) {
				
			},
			columns : [ [
			         	{width : 100,title : 'id',field :'codeId',checkbox : true}, 
			         	{width : 100,title : '激活码',field : 'activationCode',sortable : true},
						{width : 100,title : '激活码类型名称',field : 'map.activationTypeName',sortable : true},
						{width : 100,title : '批次号',field : 'map.batchNumber',sortable : true},
						{width : 100,title : '设备类型',field : 'map.padTypeName',sortable : true},
						{width : 100,title : '开始时间',field : 'startTime',sortable : true,formatter : formatterTime},
						{width : 150,title : '结束时间',	field : 'endTime',sortable : true,formatter : formatterTime}, 
						{width : 100,title : '是否激活',field : 'map.activationStatusName',sortable : true},
						{width : 100,title : '设备时长(小时)',field : 'padTime',sortable : true},
						{width : 100,title : '创建人',field : 'creater',sortable : true}, 
						{width : 150,title : '创建时间',field : 'createTime',sortable : true,formatter : formatterTime},
						{width : 100,title : '状态',field : 'status',sortable : true,formatter : formatterStop},
						{width : 100,title : '启用状态',	field : 'enableStatus',	sortable : true,formatter : formatterStop}
			    
			         	] ]
		};
		var dialogParamObj = {

		};
		$(function() {
			$(module_datagrid_activation).datagrid($.extend({}, dataGridParam, dataGridParamObj));
		});
	</script>
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post">
			<input type="hidden" name="typeId" value="${bean.typeId}">
			<table id="module_submit_table">
				<tr>
					<td class="td1">类型名称:</td>
					<td class="td2">${bean.activationTypeName}</td>
				</tr>
				<tr>
					<td class="td1">类型编码:</td>
					<td class="td2">${bean.activationTypeCode}</td>
				</tr>
				<tr>
					<td class="td1">设备类型:</td>
					<td class="td2">
						<select class="easyui-combobox input_width_short" editable="false" id="codeTypePadType" name="padType" data-options="required:false">
							<c:forEach var="one" items="${padType}">
								<option <c:if test="${one.key==bean.padType}">selected="selected"</c:if> value="${one.key}">${one.value}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="td1">设备时间（小时）:</td>
					<td class="td2"><input class="easyui-textbox" type="text" id="padTime"
						name="padTime" value="${bean.padTime}"
						data-options="required:true,min:0,max:999" /></td>
				</tr>
			</table>
		</form>
		<div class="module_search_button">
			<form id="user_search_form" method="post">
				激活码编码:<input id="activationCode" class="easyui-textbox input_width_default">
				设备类型:<select class="easyui-combobox input_width_short" editable="false" id="padType" data-options="required:false">
							<option value="">-全部-</option>
							<c:forEach var="one" items="${padType}">
								<option value="${one.key}">${one.value}</option>
							</c:forEach>
						</select>
				<a href="#" iconCls="icon-search-rf" class="easyui-linkbutton"plain="true"onclick="doSearch()">查询</a>
			</form>
		</div> 
	</div>
	
	<table id="module_datagrid_activation" toolbar="#module_toolbar_activation" url="${host}type/getCodeList?typeId=${bean.typeId}" ></table>
</body>
</html>



