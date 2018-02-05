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
			url : host + 'saveBatch',
			success : callback
		});
		function awardCodeSelect(record) {
			if (record.value == "shouxie") {
				$("#feedbackHandleTr6").removeClass("hide");
			} else {
				$("#feedbackHandleTr6").addClass("hide");
			}
		}
		function feedbackStatusSelect(record) {
			var objStr = record.value;

			if (objStr == "") {
				$("#feedbackHandleTr1").addClass("hide");
				$("#feedbackHandleTr2").addClass("hide");
				$("#feedbackHandleTr3").addClass("hide");
				$("#feedbackHandleTr4").addClass("hide");
				$("#feedbackHandleTr5").addClass("hide");
				$("#feedbackHandleTr7").addClass("hide");
			} else {
				var bean = eval("(" + objStr + ")");
				$("#awardGrade").html(formatter1(bean.awardGrade));
				$("#awardCategory").html(formatter2(bean.awardCategory));
				$("#winningRate").html(bean.winningRate + "/10000");
				$("#awardTotal").html(bean.awardTotal);
				$("#awardMargin").html(bean.awardMargin);
				$("#awardPoolType").html(formatter3(bean.awardType));
				$("#awardPoolType2").val(bean.awardType);
				$("#feedbackHandleTr1").removeClass("hide");
				$("#feedbackHandleTr2").removeClass("hide");
				$("#feedbackHandleTr3").removeClass("hide");
				$("#feedbackHandleTr4").removeClass("hide");
				$("#feedbackHandleTr5").removeClass("hide");
				$("#feedbackHandleTr7").removeClass("hide");
				$("#awardId").val(bean.awardId);
			}
		}
		var formatter1 = function(value) {
			return getDatagridDict('rf_award_batch.award_grade', value);
		}
		var formatter2 = function(value) {
			return getDatagridDict('rf_award_batch.award_category', value);
		}
		var formatter3 = function(value) {
			return getDatagridDict('rf_award_batch.award_type', value);
		}
	</script>
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post">
			<input type="hidden" name="awardId" id="awardId">
			<table id="module_submit_table">
				<tr>
					<td class="td1">选择奖品:</td>
					<td class="td2"><select class="easyui-combobox"
						data-options="editable:false,required:true,onSelect:feedbackStatusSelect">
							<option value="">--选择奖品--</option>
							<c:forEach items="${list}" var="one">
								<option
									value="{awardId:${one.awardId},awardGrade:${one.awardGrade},awardCategory:${one.awardCategory},winningRate:${one.winningRate},awardTotal:${one.awardTotal},awardMargin:${one.awardMargin},awardType:${one.awardType }}">${one.awardName}--抽奖池${one.awardType}</option>
							</c:forEach>
					</select>
				</tr>

				<tr id="feedbackHandleTr1" class="hide">
					<td class="td1">奖品级别:</td>
					<td class="td2" id="awardGrade">fsdf</td>
				</tr>
				<tr id="feedbackHandleTr2" class="hide">
					<td class="td1">奖品类型:</td>
					<td class="td2" id="awardCategory"></td>
				</tr>
				<tr id="feedbackHandleTr3" class="hide">
					<td class="td1">中奖率:</td>
					<td class="td2" id="winningRate"></td>
				</tr>
				<tr id="feedbackHandleTr4" class="hide">
					<td class="td1">奖品总量:</td>
					<td class="td2" id="awardTotal"></td>
				</tr>
				<tr id="feedbackHandleTr5" class="hide">
					<td class="td1">奖品余量:</td>
					<td class="td2" id="awardMargin"></td>
				</tr>
				<tr id="feedbackHandleTr7" class="hide">
					<td class="td1">奖品池类型:</td>
					<td class="td2" id="awardPoolType"><input type="hidden" id="awardPoolType2" name="awardPoolType"/></td>
				</tr>
				<tr>
					<td class="td1">添加奖品数量:</td>
					<td class="td2"><input class="easyui-numberbox" type="text"
						name="awardNumber"
						data-options="required:true,validType:'length[0,32]'" /> <br>
						<font size="2" color="red">注：添加数量必须小于等于奖品余量，否则添加失败。</font></td>
				</tr>
				<tr>
					<td class="td1">奖池编码获取方式:</td>
					<td class="td2"><select
						class="easyui-combobox input_width_short" editable="false"
						name="awardCodeMode"
						data-options="required:false,onSelect:awardCodeSelect">
							<option value="suiji">随机</option>
							<!-- <option value="shouxie">手写</option> -->
					</select></td>
				</tr>
				<tr id="feedbackHandleTr6" class="hide">
					<td class="td1">奖品编码:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="awardCode" data-options="validType:'length[0,32]'" /></td>
				</tr>
				<tr>
					<td class="td1">发放状态:</td>
					<td class="td2"><select
						class="easyui-combobox input_width_short" editable="false"
						name="giveStatus" data-options="required:false">
							<fns:getOptions category="rf_award_batch.award_give_status"></fns:getOptions>
					</select></td>
				</tr>
				<tr>
					<td class="td1">领取状态:</td>
					<td class="td2"><select
						class="easyui-combobox input_width_short" editable="false"
						name="receiveStatus" data-options="required:false">
							<fns:getOptions category="rf_award_batch.award_receive_status"></fns:getOptions>
					</select></td>
				</tr>
				<tr>
					<td class="td1">备注:</td>
					<td class="td2"><input class="easyui-textbox" name="remark"
						data-options="multiline:true,validType:'length[0,1000]'"
						style="height: 120px" /></td>
				</tr>

			</table>
		</form>
	</div>
</body>
</html>



