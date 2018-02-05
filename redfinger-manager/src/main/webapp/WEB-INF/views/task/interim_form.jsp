<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>任务中间表管理</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
			url : host + 'save',
			success : callback
		});
	</script>
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post">
			<input type="hidden" name="id" value="${bean.id}">
			<table id="module_submit_table">
				<tr>
					<td class="td1">用户ID:</td>
					<td class="td2">${bean.userId }</td>
				</tr>
				<tr>
					<td class="td1">用户手机:</td>
					<td class="td2">${userMobilePhone }</td>
				</tr>
				<tr>
					<td class="td1">任务ID:</td>
					<td class="td2">${bean.taskId }</td>
				</tr>
				<tr>
					<td class="td1">任务进度:</td>
					<td class="td2"><input class="easyui-numberbox" type="text"
						name="taskNow" value="${bean.taskNow }"
						data-options="required:true,validType:'length[0,32]'" /></td>
				</tr>
				<tr>
					<td class="td1">任务目标:</td>
					<td class="td2"><input class="easyui-numberbox" type="text"
						name="taskCode" value="${bean.taskReach }"
						data-options="required:true,validType:'length[0,32]'" /></td>
				</tr>

				<tr>
					<td class="td1">任务状态:</td>
					<td class="td2"><select class="easyui-combobox"
						editable="false" name="awardStatus" data-options="required:true">
							<fns:getOptions category="task_record.award_status"
								value="${bean.awardStatus}"></fns:getOptions>
					</select></td>
				</tr>
				<tr>
					<td class="td1">任务类型:</td>
					<td class="td2">${fns:getLabelStyle('task_system.category',bean.category)}</td>
				</tr>
				<tr>
					<td class="td1">任务名称:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="taskName" value="${bean.taskName }"
						data-options="required:true,validType:'length[0,32]'" /></td>
				</tr>
				<tr>
					<td class="td1">任务详情:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="remark" value="${bean.remark }"
						data-options="required:true,validType:'length[0,32]'" /></td>
				</tr>
				<tr>
					<td class="td1">奖励红豆:</td>
					<td class="td2"><input class="easyui-numberbox" type="text"
						name="awardAmount" value="${bean.awardAmount }"
						data-options="required:true,validType:'length[0,32]'" /></td>
				</tr>
				<tr>
					<td class="td1">接取时间:</td>
					<td class="td2"><input
						class="easyui-datebox input_width_default" type="text"
						name="takeTime1"
						value="<fmt:formatDate value="${bean.takeTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"></td>
				</tr>
				<tr>
					<td class="td1">记录时间:</td>
					<td class="td2"><input
						class="easyui-datebox input_width_default" type="text"
						name="recordTime1"
						value="<fmt:formatDate value="${bean.recordTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"></td>
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
						data-options="required:true,validType:'length[0,255]'" /></td>
				</tr>

				<tr>
					<td class="td1">任务编码:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="taskSer" value="${bean.taskSer}"
						data-options="required:true,validType:'length[0,32]'" /></td>
				</tr>

			</table>
		</form>
	</div>
</body>
</html>



