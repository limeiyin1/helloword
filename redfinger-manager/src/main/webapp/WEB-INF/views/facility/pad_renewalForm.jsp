<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>设备批量更换操作</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'renewal',   
		    success:callback
		});
		function countPads(message,pads) {
			var padlist;
			padlist= message.value.split("\n").length;
			pads.value=padlist;
		}
		
		$(document).ready(function(){
			if(${msgTemplates_msg_last==''}){
			$("tr[name=sendMessageTr]").addClass("hide");
		}else{
			$("tr[name=sendMessageTr]").removeClass("hide");
		}
		if(${msgTemplates_weixin_last==''}){
			$("tr[name=sendWchartTr]").addClass("hide");
		}else{
			$("tr[name=sendWchartTr]").removeClass("hide");
		}
		});
		
		function choiceIsSendMessage(){
		var isSendMessage = $("#isSendMessage").combobox('getValue');
		$("tr[name=sendMessageTr]").addClass("hide");
		if(isSendMessage!='0'){
			$("tr[name=sendMessageTr]").removeClass("hide");
			$("#sendMessageTemplate").html(isSendMessage);
		}else{
			$("#sendMessageTemplate").html("");
		}
	}
	
	function choiceIsSendWechart(){
		var isSendWechart = $("#isSendWechart").combobox('getValue');
		$("tr[name=sendWchartTr]").addClass("hide");
		if(isSendWechart!='0'){
			$("tr[name=sendWchartTr]").removeClass("hide");
			$("#sendWechartTemplate").html(isSendWechart);
		}else{
			$("#sendWechartTemplate").html("");
		}
	}
		
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
    
	<table id="module_submit_table">
		<tr>
					<td class="td1">发送消息:</td>
					<td class="td2">
						<select editable="false" id="isSendMessage" name="isSendMessage" class="easyui-combobox input_width_default" data-options="editable:false,onSelect:choiceIsSendMessage">
							<option value="0">不发送</option>
							<c:forEach var="one" items="${msgTemplates_msg}">
								<option value="${one.templateText}"  <c:if test="${one.templateText==msgTemplates_msg_last}">selected="selected"</c:if> >${one.templateText}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				
				<tr name="sendMessageTr">
					<td class="td2"></td>
					<td class="td2">
					<textarea id="sendMessageTemplate" name="sendMessageTemplate" rows="2" cols="38">${msgTemplates_msg_last}</textarea>
					</td>
				</tr>
		<tr name="sendMessageTr">
			<td class="td2"></td>
			<td class="td2">
			XXX为需要替换成设备名称,最好保留,不然消息看不到设备名称
			</td>
		</tr>
				
		<tr>
					<td class="td1">发送微信:</td>
					<td class="td2">
						<select editable="false" id="isSendWechart" name="isSendWechart" class="easyui-combobox input_width_default" data-options="editable:false,onSelect:choiceIsSendWechart">
							<option value="0">不发送</option>
							<c:forEach var="one" items="${msgTemplates_weixin}">
								<option value="${one.templateText}"  <c:if test="${one.templateText==msgTemplates_weixin_last}">selected="selected"</c:if> >${one.templateText}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				
				<tr name="sendWchartTr">
					<td class="td2"></td>
					<td class="td2">
					<textarea id="sendWechartTemplate" name="sendWechartTemplate" rows="2" cols="38">${msgTemplates_weixin_last}</textarea>
					</td>
				</tr>
		<tr name="sendWchartTr">
			<td class="td2"></td>
			<td class="td2">
			XXX为需要替换成设备名称,最好保留,不然公告看不到设备名称
			</td>
		</tr>
		
	    <tr>
			<td class="td1">换新设备名称前缀:</td>
			<td class="td2">
				<input style="width: 50px"  class="easyui-textbox" type="text" name="suffix"  data-options="required:false," />
			 </td>
			</tr>
       <tr>
	     <tr>
			<td class="td1">设备清单:</td>
			<td class="td2"><fieldset>
						<legend>请输入内容</legend>
						<textarea name="code" wrap=PHYSICAL style="width: 220px;height: 200px" onKeyDown="countPads(this.form.code,this.form.pads);"
						onKeyUp="countPads(this.form.code,this.form.pads);"></textarea>
		                </fieldset>
		    </td>
		 </tr>
		 <tr>
		    <td class="td1">已输入行数：</td>
		    <td class="td2"><input disabled maxlength="4" name="pads" size="3" value="0" class="inputtext"></td>
		 </tr>
	</table>
    </form>
    </div>
</body>
</html>



