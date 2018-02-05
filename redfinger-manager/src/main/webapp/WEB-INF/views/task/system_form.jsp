<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>红手指任务</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
			success : callback
		});
		function feedbackStatusSelect(record) {
			if (record.value == "gamedownload") {
				$("#taskSer").textbox('setValue',"GameDownload");

				$("#smallClassIdTr").removeClass("hide");
				$("#feedbackHandleTr").removeClass("hide");

			} else {
				$("#smallClassIdTr").addClass("hide");
				$("#feedbackHandleTr").addClass("hide");
			}
		}
		function getGameApk(record) {
			var url = 'system/gameApk?zm=' + record.value;
			$('#cc2').combobox('reload', url);
			$('#cc2').combobox('clear');
		}
	</script>
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post">
			<c:if test="${not empty bean.id}">
				<input type="hidden" name="id" value="${bean.id }">
			</c:if>
			<table class="easyui-table">
				<tr>
					<td class="td1">红手指任务名:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="name" value="${bean.name }"
						data-options="required:true,validType:'length[0,32]'" /></td>
				</tr>
				<tr>
					<td class="td1">任务类型:</td>
					<td class="td2"><select
						class="easyui-combobox input_width_short" name="category"
						data-options="editable:false,required:true,onSelect:feedbackStatusSelect">
							<fns:getOptions category="task_system.category"
								value="${bean.category}"></fns:getOptions>
					</select></td>
				</tr>
				<tr id="feedbackHandleTr" class="hide">
					<td class="td1"></td>
					<td class="td2"><font size="2" color="red">注：游戏下载类型的SERVICE不能以数字结尾。</font></td>
				</tr>
				<tr>
					<td class="td1">SERVICE:</td>
					<td class="td2"><input class="easyui-textbox" type="text" id ="taskSer"
						name="taskSer" value="${bean.taskSer }"
						data-options="required:true,validType:'length[0,32]'" /></td>
				</tr>
				<tr>
					<td class="td1">红手指任务描述:</td>
					<td class="td2"><input class="easyui-textbox" name="remark"
						value="${bean.remark }"
						data-options="required:true,multiline:true,validType:'length[0,200]'"
						style="height: 60px" /></td>
				</tr>
				<tr id="smallClassIdTr" class="hide">
					<td class="td1">请选择游戏:</td>
					<td class="td2"><select id="cc" class="easyui-combobox"
						style="width: 100px;"
						data-options="  
     				   	onSelect:getGameApk">
							<option value="">--首字母--</option>
							<option value="a">A</option>
							<option value="b">B</option>
							<option value="c">C</option>
							<option value="d">D</option>
							<option value="e">E</option>
							<option value="f">F</option>
							<option value="g">G</option>
							<option value="h">H</option>
							<option value="i">I</option>
							<option value="j">J</option>
							<option value="k">K</option>
							<option value="l">L</option>
							<option value="m">M</option>
							<option value="n">N</option>
							<option value="o">O</option>
							<option value="p">P</option>
							<option value="q">Q</option>
							<option value="r">R</option>
							<option value="s">S</option>
							<option value="t">T</option>
							<option value="u">U</option>
							<option value="v">V</option>
							<option value="w">W</option>
							<option value="x">X</option>
							<option value="y">Y</option>
							<option value="z">Z</option>
					</select> <input id="cc2" class="easyui-combobox" name ="game"
						data-options="valueField:'id',textField:'name'" /></td>
				</tr>

				<tr>
					<td class="td1">阀值:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="thresholds" value="${bean.thresholds }"
						data-options="required:true,validType:'length[0,32]'" /> <br>(如果是问卷类的任务请填入问卷的ID,问卷ID在问卷调查管理中查询)
					</td>
				</tr>
				<tr>
					<td class="td1">红豆奖励数量:</td>
					<td class="td2"><input class="easyui-numberbox" type="text"
						name="awardAmount" value="${bean.awardAmount }"
						data-options="required:true,validType:'length[0,32]'" /></td>
				</tr>
				<tr>
					<td class="td1">起始时间:</td>
					<td class="td2"><input type="text"
						class="easyui-datebox input_width_default" name="beginTimeStr"
						value="<fmt:formatDate value="${bean.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						data-options="editable:true" /></td>
				</tr>
				<tr>
					<td class="td1">终止时间:</td>
					<td class="td2"><input type="text"
						class="easyui-datebox input_width_default" name="endTimeStr"
						value="<fmt:formatDate value="${bean.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						data-options="editable:true" /></td>
				</tr>
				<tr>
					<td class="td1">启用状态:</td>
					<td class="td2"><select
						class="easyui-combobox input_width_short" name="enableStatus"
						data-options="editable:false,required:false">
							<fns:getOptions category="global.enable_status"
								value="${bean.enableStatus}"></fns:getOptions>
					</select></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>