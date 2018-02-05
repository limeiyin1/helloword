<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>设备</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'save',   
		    success:callback

		});
		
		function choiceControlType(){
		    // 获取选择的设备节点类型
			var controlType = $("#controlType").combobox('getValue');
		    // 设备管理节点类型, 消息队列名称是必填的
			if(controlType=='MANAGE'){
				$("#controlQueueName").textbox({required:true});
			}else{
				$("#controlQueueName").textbox({required:false});
			}
		}
	</script>
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post">
			<input type="hidden" name="controlId" value="${bean.controlId}">
			<table id="module_submit_table">
				<tr>
					<td class="td1">节点编号:</td>
					<td class="td2"><input class="easyui-textbox" type="text"name="controlCode" value="${bean.controlCode }"data-options="required:true,validType:'length[0,32]'" /></td>
				</tr>
				<tr>
					<td class="td1">机房:</td>
					<td class="td2"><select class="easyui-combobox" name="idcId" data-options="required:false,editable:false">
							<c:forEach items="${idcList}" var="one">
								<option value="${one.idcId }"
									<c:if test="${one.idcId==bean.idcId}">selected="selected"</c:if>>${one.idcName }</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td class="td1">节点名称:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="controlName" value="${bean.controlName }"
						data-options="required:true,validType:'length[0,32]'" /></td>
				</tr>
				<tr>
					<td class="td1">节点IP:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="controlIp" value="${bean.controlIp }"
						data-options="required:true,validType:'length[0,255]'" /></td>
				</tr>
				<tr>
					<td class="td1">节点类型:</td>
					<td class="td2"><select class="easyui-combobox" id="controlType"
					  name="controlType" data-options="required:false,editable:false,onSelect:choiceControlType">
							<fns:getOptions category="rf_control.control_type"
								value="${bean.controlType}"></fns:getOptions>
					</select></td>
				</tr>
				<tr>
					<td class="td1">控制节点端口:</td>
					<td class="td2"><input class="easyui-numberbox" type="text"
						name="controlPort" value="${bean.controlPort }"
						data-options="required:true,validType:'length[0,32]'" /></td>
				</tr>
				<tr>
					<td class="td1">备用节点IP:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="bakControlIp" value="${bean.bakControlIp }"
						data-options="validType:'length[0,255]'" /></td>
				</tr>
				<tr>
					<td class="td1">备用控制节点端口:</td>
					<td class="td2"><input class="easyui-numberbox" type="text"
						name="bakControlPort" value="${bean.bakControlPort }"
						data-options="validType:'length[0,32]'" /></td>
				</tr>
				<tr>
					<td class="td1">消息队列名称:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="controlQueueName" id="controlQueueName" value="${bean.controlQueueName }"
						data-options="required:${bean.controlType == 'MANAGE' ? true : false},validType:'length[0,50]'" /></td>
				</tr>
				<tr>
					<td class="td1">排序:</td>
					<td class="td2"><input class="easyui-numberbox" type="text"
						name="reorder" value="${bean.reorder }"
						data-options="required:true,validType:'length[0,32]'" /></td>
				</tr>

				<tr>
					<td class="td1">描述:</td>
					<td class="td2"><input class="easyui-textbox" name="remark"
						value="${bean.remark }"
						data-options="multiline:true,validType:'length[0,200]'"
						style="height: 60px" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>



