<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>打分卷详情</title>
<meta name="decorator" content=default />
</head>
<body>
	<script type="text/javascript">
		function getAssess() {
			var id = $('#targetId').combobox('getValue');
			var dg1 = "#dg1";
			var dataGridParamObj1 = {
				url : host + "getContentList?id=" + id,
				columns : [ [ {
					width : 50,
					title : 'id',
					field : 'id',
					checkbox : true
				}, {
					width : 100,
					title : '被考核人',
					field : 'targetAdmin'
				} , {
					width : 100,
					title : '考核人',
					field : 'assessAdmin'
				} , {
					width : 100,
					title : '总分',
					field : 'point'
				} , {
					width : 100,
					title : '考核状态',
					field : 'assessStatus',formatter:function(value){if(value==1){return "已评";}else if(value==0){return "未评";}}
				} 
				
				] ]
			};
			var dialogParamObj1 = {

			};
			$(function() {
				$(dg1).datagrid($.extend({}, dialogParamObj, dataGridParamObj1));});
		}
		
		function look() {
			var ids = getGrid();
			if (!ids) {
				return false;
			}
			var dataGridParamObj2 = {
					url : host + "getContentDetail?ids=" + ids,
					columns : [ [{
						width : 100,
						title : '考核人',
						field : 'assessAdmin'
					} , {
						width : 300,
						title : '题目',
						field : 'optionName'
					} , {
						width : 50,
						title : '总分',
						field : 'optionPoint'
					} ,{
						width : 50,
						title : '得分',
						field : 'point'
					}
					] ]
				};
				var dialogParamObj2 = {

				};
				$(function() {
					$(dg2).datagrid($.extend({}, dialogParamObj, dataGridParamObj2));});
			}
		
		
		 //获取选中适用员工
		 var getGrid = function() {
				var row = $('#dg1').datagrid("getSelections");
				if (row == '') {
					$.messager.alert('提示', '请选择记录！', "info");
					return null;
				} else {
					var ids = [];
					for (var i = 0; i < row.length; i++) {
						ids[i] = row[i]['id']
					}
					return ids.join(",");
				}
			}
	</script>

	<div id="module_submit_container">
		<select class="easyui-combobox" id="targetId" style="width: 260px"
			data-options="onSelect:getAssess">
			<option value="">-- 请选择考核人 --</option>
			<c:forEach items="${acList}" var="one">
				<option value="${one.id}">${one.targetAdmin}</option>
			</c:forEach>
		</select>
		<div><font size="2" color="red">* 括号里面的数字表示额外的加分 *</font></div>
	</div>
	<div id="module_con" >
		<div id="module_tool" class="easyui-toolbar" style="display:none">
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="look()">查看</a>
		</div>
		<table id="dg1" toolbar="#module_tool"></table>
	</div>

	<div  id="module_c">
		<br>
		<table id="dg2"></table>
	</div>

</body>
</html>



