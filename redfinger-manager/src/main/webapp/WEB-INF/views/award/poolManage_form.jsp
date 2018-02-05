<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>抽奖池编辑</title>
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
					<td class="td1">奖品级别:</td>
					<td class="td2">
						${fns:getLabelStyle('rf_award_batch.award_grade',bean.map.awardGrade)}
					</td>
				</tr>
				<tr>
					<td class="td1">奖励名称:</td>
					<td class="td2">${bean.map.awardName}</td>
				</tr>
				<tr>
					<td class="td1">中奖率（/10000）:</td>
					<td class="td2">${bean.winningRate}</td>
				</tr>
				<tr>
					<td class="td1">奖品池:</td>
					<td class="td2">
					${fns:getLabelStyle('rf_award_batch.award_type',bean.awardPoolType)}
					</td>
				</tr>
				<tr>
					<td class="td1">奖品总量:</td>
					<td class="td2">${bean.awardTotal}</td>
				</tr>
				<tr>
					<td class="td1">奖品余量:</td>
					<td class="td2">${bean.awardMargin}</td>
				</tr>
				<tr>
					<td class="td1">奖品编码:</td>
					<td class="td2">${bean.awardCode}</td>
				</tr>
				<tr>
					<td class="td1">发放状态:</td>
					<td class="td2"><select
						class="easyui-combobox input_width_short" editable="false"
						name="giveStatus" data-options="required:false">
							<fns:getOptions category="rf_award_batch.award_give_status"
								value="${bean.giveStatus}"></fns:getOptions>
					</select></td>
				</tr>
				<tr>
					<td class="td1">领取状态:</td>
					<td class="td2"><select
						class="easyui-combobox input_width_short" editable="false"
						name="receiveStatus" data-options="required:false">
							<fns:getOptions category="rf_award_batch.award_receive_status"
								value="${bean.receiveStatus}"></fns:getOptions>
					</select></td>
				</tr>
				<tr>
					<td class="td1">备注:</td>
					<td class="td2"><input class="easyui-textbox" name="remark"
						value="${bean.remark}"
						data-options="multiline:true,validType:'length[0,1000]'"
						style="height: 120px" /></td>
				</tr>

			</table>
		</form>
	</div>
</body>
</html>



