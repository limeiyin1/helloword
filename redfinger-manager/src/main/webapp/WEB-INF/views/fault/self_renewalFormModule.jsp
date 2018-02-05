<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
			<input type="hidden" name="oldPadId" id="oldPadId" value="${pad.padId}">
			<input type="hidden" name="userId" value="${user.userId}" />
			<input type="hidden" name="faultFeedbackId" value="${faultFeedbackId}" />
			<table style="width:100%;" id="change-pad-tb" >
				<tr>
					<td class="td1">发送消息:</td>
					<td class="td2">
						<select editable="false" id="isSendMessage" name="isSendMessage"  class="easyui-combobox input_width_default change-input" class="easyui-combobox input_width_default" data-options="editable:false,width:330,onSelect:choiceIsSendMessage">
							<option value="0">不发送</option>
							<c:forEach var="one" items="${msgTemplates_msg}">
								<option value="${one.templateText}"  <c:if test="${one.templateText==msgTemplates_msg_last}">selected="selected"</c:if> >${one.templateText}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				
				<tr name="sendMessageTr" class="remove">
					<td class="td2"></td>
					<td class="td2">
					<textarea id="sendMessageTemplate" name="sendMessageTemplate"  rows="3" cols="50">${msgTemplates_msg_last}</textarea>
					</td>
				</tr>
				<tr name="sendMessageTr" class="remove">
					<td class="td2"></td>
					<td class="td2">
					XXX为需要替换成设备名称,最好保留,不然消息看不到设备名称
					</td>
				</tr>	
				
				<tr>
					<td class="td1">发送微信:</td>
					<td class="td2">
						<select editable="false" id="isSendWechart" name="isSendWechart" class="easyui-combobox input_width_default" style="width:330px;" data-options="editable:false,onSelect:choiceIsSendWechart">
							<option value="0">不发送</option>
							<c:forEach var="one" items="${msgTemplates_weixin}">
								<option value="${one.templateText}"  <c:if test="${one.templateText==msgTemplates_weixin_last}">selected="selected"</c:if> >${one.templateText}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				
				<tr name="sendWchartTr" class="remove">
					<td class="td2"></td>
					<td class="td2">
					<textarea id="sendWechartTemplate" name="sendWechartTemplate"  rows="3" cols="50">${msgTemplates_weixin_last}</textarea>
					</td>
				</tr>
				<tr name="sendWchartTr" class="remove">
					<td class="td2"></td>
					<td class="td2">
					XXX为需要替换成设备名称,最好保留,不然公告看不到设备名称
					</td>
				</tr>
				
				
				<tr class="remove">
					<td class="td1">机房:</td>
					<td class="td2">
						<select editable="false" id="idcId" name="idcId" data-option="onChange:controlOnChange" class="easyui-combobox input_width_default" style="width:30%">
							<option value="" selected="selected">[全部]</option>
							<c:forEach var="one" items="${idcList}">
								<option value="${one.idcId}">${one.idcName}</option>
							</c:forEach>
						</select>
						
						控制节点:
						<select editable="false" id="controlId" name="userControlId" class="easyui-combobox input_width_default" style="width:30%">
							<option value="">[全部]</option>
						</select>
					</td>
				</tr>
				<tr class="remove">
					<td class="td1">设备编码段:</td>
					<td class="td2">
						<input type="text" id="padCode"  name="padCode"  class="easyui-textbox input_width_default change-input" style="width:150px"/>至
						<input type="text" id="padCode2" name="padCode2"  class="easyui-textbox input_width_default change-input" style="width:150px"/>
					</td>
				</tr>
				<tr class="remove">
					<td class="td1">物理设备ip:</td>
					<td class="td2">
						<input type="text" id="deviceIp" name="deviceIp"  class="easyui-textbox input_width_default change-input" style="width:150px"/>至
						<input type="text" id="deviceIp2" name="deviceIp2"  class="easyui-textbox input_width_default change-input" style="width:150px"/>
					</td>
				</tr>
		</table>
		<span id="controlLIst" style="display:none;">${controlList}</span>
