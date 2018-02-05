<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>中间表批量修改</title>
<meta name="decorator" content="default" />
</head>
<body>
	<script type="text/javascript">
		var module_datagrid_renewal = "#module_datagrid_renewal";
		var callback = defaultCallback;
		var dataGridParamObj = {
			url : host + "batchList?ids=${sId}",
			idField : 'id',
			onCheck : function(index, row) {

			},
			columns : [ [
			             {width : 100,title : 'id',field : 'id',checkbox : true}, 
			              {width : 80,title : '用户Id',field : 'userId',sortable : true}, 
			              {width : 120,title : '用户手机',field : 'map.userMobilePhone',sortable : true}, 
			              {width : 80,title : '任务ID',field : 'taskId',sortable : true}, 
			              {width : 80,title : '任务进度',field : 'taskNow',sortable : true}, 
			              {width : 80,title : '任务目标',field : 'taskReach',sortable : true}, 
			              {width : 80,title : '任务状态',field : 'awardStatus',sortable : true,formatter:function(value){return getDatagridDict('task_record.award_status',value);}}, 
			              {width : 100,title : '任务类型',field : 'category',sortable : true,formatter:function(value){return getDatagridDict('task_system.category',value);}}, 
			              {width : 100,title : '任务名称',field : 'taskName',sortable : true}
			          ]]
		};
		var dialogParamObj = {

		};
		$(function() {
			$(module_datagrid_renewal).datagrid(
					$.extend({}, dataGridParam, dataGridParamObj));
		});
		
		$('.easyui-form').form({
			url : host + 'saveBatch',
			success : callback
		});

	</script>
		<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post">
		<input type="hidden" name="ids" value="${sId}">
			<table id="module_submit_table">
				<tr>
					<td class="td1">任务进度:</td>
					<td class="td2"><input class="easyui-numberbox" type="text"
						name="taskNow" value="${bean.taskNow }"
						data-options="validType:'length[0,32]'" /></td>
				</tr>

				<tr>
					<td class="td1">任务状态:</td>
					<td class="td2"><select class="easyui-combobox"
						editable="false" name="awardStatus" data-options="required:true">
						<option value="">==不修改==</option>
							<fns:getOptions category="task_record.award_status"
								value="${bean.awardStatus}"></fns:getOptions>
					</select></td>
				</tr>

				<tr>
					<td class="td1">任务名称:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="taskName" value="${bean.taskName }"
						data-options="validType:'length[0,32]'" /></td>
				</tr>
				<tr>
					<td class="td1">任务详情:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="remark" value="${bean.remark }"
						data-options="validType:'length[0,32]'" /></td>
				</tr>
				<tr>
					<td class="td1">奖励红豆:</td>
					<td class="td2"><input class="easyui-numberbox" type="text"
						name="awardAmount" value="${bean.awardAmount }"
						data-options="validType:'length[0,32]'" /></td>
				</tr>

				<tr>
					<td class="td1">起始时间:</td>
					<td class="td2"><input
						class="easyui-datebox input_width_default" type="text"
						name="beginTime1"
						value="<fmt:formatDate value="${bean.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"></td>
				</tr>
				<tr>
					<td class="td1">终止时间:</td>
					<td class="td2"><input
						class="easyui-datebox input_width_default" type="text"
						name="endTime1"
						value="<fmt:formatDate value="${bean.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"></td>
				</tr>

				<tr>
					<td class="td1">任务阀值:</td>
					<td class="td2"><input class="easyui-numberbox" type="text"
						name="thresholds" value="${bean.thresholds}"
						data-options="validType:'length[0,255]'" /></td>
				</tr>

				<tr>
					<td class="td1">任务编码:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="taskSer" value="${bean.taskSer}"
						data-options="validType:'length[0,32]'" /></td>
				</tr>

			</table>
		</form>
	</div>

	<table id="module_datagrid_renewal" toolbar="#module_toolbar_renewal"></table>


</body>
</html>


