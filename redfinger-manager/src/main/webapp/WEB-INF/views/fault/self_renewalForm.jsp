<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>更换</title>
<meta name="decorator" content="default" />
</head>
<body>
<script type="text/javascript">
	$('.easyui-form').form({
	
		onSubmit:function(){
			/* alert("into onSubmit");
			alert(typeof($("#isSendMessage").combobox('getValue')) == "undefined");
			alert($("#isSendMessage").combobox('getText')); 
		
			if(typeof($("#sendMessageTemplate").combobox('getValue')) == "undefined"){
				//alert("sendMessageTemplate is null");
				$('#sendMessageTemplate').combobox('setValue',$('#sendMessageTemplate').combobox('getText')) 
			}
			if(typeof($("#sendWechartTemplate").combobox('getValue')) == "undefined"){
				//alert("sendWechartTemplate is null");
				$('#sendWechartTemplate').combobox('setValue',$('#sendWechartTemplate').combobox('getText')) 
			}
			 */
	    },
		
	    url:host+'renewalOne',   
	    success:callback
	}); 

	function doSearch(){
		//$("#module_datagrid_renewal").datagrid("unselectRow", rowIndex);
		$('#module_datagrid_renewal').datagrid('reload',{
			idcId: $("#idcId").combobox('getValue'),
			userControlId: $("#controlId").combobox('getValue'),
			padCode: $("#padCode").textbox('getValue'),
			padCode2: $("#padCode2").textbox('getValue'),
			deviceIp:$("#deviceIp").textbox('getValue'),
			deviceIp2:$("#deviceIp2").textbox('getValue')
		});
	}
		
	function addUserPadLog(OldpadId) {
		var row = $('#module_datagrid_renewal').datagrid('getSelected');
		/* var bindPhone=myform.NEWphone.value;
		
		var OldeName=document.getElementById(OldpadId).value; */
		if(!row || !row.padCode || row.padCode==null){
			$.messager.alert('操作失败','无数据！请勾选要替换的新设备',"warning");
			return;
		}
		$("#padCodeSpan").text(row.padCode);
		$("#newPadId").val(row.padId);
	}	
	
	var module_datagrid_renewal = "#module_datagrid_renewal";
	var dataGridParamObj = {
		url : host + "padlist?oldPadId="+$("#oldPadId").val(),
		idField : 'padId',
		singleSelect: true,
		onLoadSuccess:function(){
			if($('#module_datagrid_renewal').datagrid('getData').size>0){
			 $("#module_datagrid_renewal").datagrid('selectRow', 0);
			}
			//alert($('#module_datagrid_renewal').datagrid('getData').size);
			
		},
		onCheck : function(index,row) {

		},
		onDblClickRow :function(rowIndex,rowData){
		},
		columns : [[
         	{width:100,title:'id',field :'padId',checkbox : true}, 
         	{width:100,title:'设备名称',field:'padName',sortable : false},
         	{width:100,title:'设备编码',field:'padCode',sortable:false},
         	{width:180,title:'控制节点',field:'map.controlName',sortable:false},
         	{width:80,title:'设备来源',field:'map.facilityName',sortable:false},
          	{width:100,title:'设备IP',field:'padIp',sortable:false},
          	{width : 120,title : '设备类别',field : 'padClassify',sortable : false,formatter:function(value){return getDatagridDict('rf_pad.pad_classify',value);}}
       	]]
	};
	var dialogParamObj = {

	};
	$(function() {
		$(module_datagrid_renewal).datagrid($.extend({}, dataGridParam, dataGridParamObj));
	});
	
	var controlList = ${controlList};
	$(document).ready(function(){
		$('#idcId').combobox({
			onChange:function(newValue,oldValue){
				$("#controlId").combobox("clear");
				if(newValue != ''){
					var data = [];
					data.push({"value":"","text":"[全部]"});
					for(i=0;i<controlList.length;i++){
						if(controlList[i].idcId==newValue){
							data.push({"value":controlList[i].controlId,"text":controlList[i].controlName});
						}
					}
					$("#controlId").combobox("loadData", data);
				}
			 }
		});
		
		if(${empty msgTemplates_msg_last}){
			$("tr[name=sendMessageTr]").addClass("hide");
		}else{
			$("tr[name=sendMessageTr]").removeClass("hide");
		}
		if(${empty msgTemplates_weixin_last}){
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
	<div id="module_search" >
		<form id="module_submit_form" class="easyui-form" method="post">
			<input type="hidden" name="userId" value="${user.userId}" />
			 <table id="module_submit_table" >
				<tr>
					<td class="td1"> 用户邮箱:</td>
					<td class="td2">${user.userEmail}  <input type="hidden" name="userEmail" value="${user.userEmail}" /></td>
				</tr>
				<tr>
					<td class="td1"> 用户手机:</td>
					<td class="td2">${user.userMobilePhone}<input type="hidden" name="userMobilePhone"value="${user.userMobilePhone}" /></td>
				</tr>
				<tr >
					<td class="td1">绑定设备:</td>
					<td class="td2">
					<span id="padCodeSpan">${pad.padCode}</span>
					<input type="hidden" name="oldPadId" id="oldPadId" value="${pad.padId}">
					<input type="hidden" name="newPadId" id="newPadId" value="">
					<a href="#" class="easyui-linkbutton" iconCls="icon-addtop-rf" plain="false" onclick ="addUserPadLog(${pad.padId})">更换</a>
					${fns:getLabelStyle('rf_pad.pad_classify',pad.padClassify)}
					</td>
				</tr>
				
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
					<td class="td1">机房:</td>
					<td class="td2">
						<select editable="false" id="idcId" name="idcId" class="easyui-combobox input_width_default" style="width:30%">
							<option value="">[全部]</option>
							<c:forEach var="one" items="${idcList}">
								<option value="${one.idcId}">${one.idcName}</option>
							</c:forEach>
						</select>
						
						控制节点:
						<select editable="false" id="controlId" name="controlId" class="easyui-combobox input_width_default" style="width:35%">
							<option value="">[全部]</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="td1">设备编码段:</td>
					<td class="td2">
						<input type="text" id="padCode" name="padCode" class="easyui-textbox input_width_default" style="width:150px"/>至
						<input type="text" id="padCode2" name="padCode2" class="easyui-textbox input_width_default" style="width:150px"/>
					</td>
				</tr>
				<tr>
					<td class="td1">物理设备ip:</td>
					<td class="td2">
						<input type="text" id="deviceIp" name="deviceIp" class="easyui-textbox input_width_default" style="width:150px"/>至
						<input type="text" id="deviceIp2" name="deviceIp2" class="easyui-textbox input_width_default" style="width:150px"/>
						<a href="#" iconCls="icon-search-rf" class="easyui-linkbutton" plain="false" onclick="doSearch()">查询</a>
					</td>
				</tr>
			</form>
		</div> 
	</div>
	<table id="module_datagrid_renewal" toolbar="#module_toolbar_renewal" ></table>
</body>
</html>


