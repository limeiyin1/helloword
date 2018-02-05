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
		    url:host+'renewal',   
		    success:callback
		}); 

		function doSearch(){
			$('#module_datagrid_renewal').datagrid('reload',{
				idcId: $("#idcId").combobox('getValue'),
				padClassify: $("#padClassify").combobox('getValue'),
				userControlId: $("#controlId").combobox('getValue'),
				padCode: $("#padCode").textbox('getValue'),
				padCode2: $("#padCode2").textbox('getValue')
			});
		}
		
	function addUserPadLog(padCodeSpan,newPadCode,oldPadClassify) {
		var row = $('#module_datagrid_renewal').datagrid('getSelected');
		
		/* alert(row.padClassify);
		alert(oldPadClassify);
		alert(row.padClassify!=oldPadClassify); */
		if(row==null){
			$.messager.alert('操作失败','无数据！请勾选要替换的新设备',"warning");
		}else{
			if(row.padClassify!=oldPadClassify){
				if(oldPadClassify==1){
					alert("主营设备不能更换为游戏设备");
				}else{
					alert("游戏设备不能更换为主营设备");
				}
				return;
			}
			$("#"+padCodeSpan).text(row.padCode);
			$("#"+newPadCode).val(row.padCode);
		}
	}	

	var module_datagrid_renewal = "#module_datagrid_renewal";
	var callback = defaultCallback ;
	var dataGridParamObj = {
			singleSelect : true,
		    url : host + "padlist",
			idField : 'padId',
			onLoadSuccess:function(){
				if($('#module_datagrid_renewal').datagrid('getData').size>0){
			 		$("#module_datagrid_renewal").datagrid('selectRow', 0);
				}
			},
			onCheck : function(index,row) {
	
			},
			columns : [ [
			         	{width : 100,title : 'id',field :'padId',checkbox : true}, 
			         	{width : 100,title : '设备名称',field:'padName',sortable : true},
			         	{width : 100,title : '设备编码',field:'padCode',sortable:true},
			         	{width:100,title:'设备控制端口',field:'padControlPort',sortable:true},
			          	{width:100,title:'设备IP',field:'padIp',sortable:true},
			        	{width:100,title:'设备SN',field:'padSn',sortable:true},
			          	{width:100,title:'MAC',field:'vmMac',sortable:true},
			          	{width:80,title:'设备来源',field:'map.facilityName'},
			        	{width : 60, title : '授权状态',field:'grantOpenStatus',sortable:true ,formatter:function(value){return getDatagridDict('rf_pad.grant_open_status',value);}},
			          	{width : 60, title : '虚拟状态',field:'vmStatus',sortable:true ,formatter:function(value){return getDatagridDict('rf_pad.vm_status',value);}},
			          	{width : 60,title : '绑定状态',field : 'bindStatus',sortable : true,formatter:function(value){return getDatagridDict('rf_pad.bind_status',value);}},
			         	{width : 60,title : '启用状态',field : 'enableStatus',sortable : true,formatter:function(value){return getDatagridDict('rf_pad.enable_status',value);}},
			         	{width : 60,title : '受控状态',field : 'padStatus',sortable : true,formatter:function(value){return getDatagridDict('rf_pad.pad_status',value);}},
			         	{width : 60,title : '故障状态',field : 'faultStatus',sortable : true,formatter:function(value){return getDatagridDict('rf_pad.fault_status',value);}},
			         	{width : 120,title : '设备类别',field : 'padClassify',sortable : true,formatter:function(value){return getDatagridDict('rf_pad.pad_classify',value);}},
			         	] ]
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
			
			if(''=='${msgTemplates_msg_last}'){
				$("tr[name=sendMessageTr]").addClass("hide");
			}else{
				$("tr[name=sendMessageTr]").removeClass("hide");
			}
			if(''=='${msgTemplates_weixin_last}'){
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
		 <table id="module_submit_table" >
				<tr>
					<td class="td1"> 用户邮箱:</td>
					<td class="td2">${user.userEmail}  <input type="hidden" name="userEmail" value="${user.userEmail}" /></td>
				</tr>
				<tr>
					<td class="td1"> 用户手机:</td>
					<td class="td2">${user.userMobilePhone}<input type="hidden" name="userMobilePhone"value="${user.userMobilePhone}" /></td>
				</tr>
				<c:forEach items="${padList}" var="pad">
					<tr >
						<td class="td1">绑定设备:</td>
						<td class="td2">
						<span id="padCodeSpan${pad.padId}">${pad.padCode}</span>
						<input type="hidden" id="newPadCode${pad.padId}" name="remark" value="${pad.padCode}" />
						<input type="hidden" name="ids" value="${pad.padId}" />
						<a href="#" class="easyui-linkbutton"iconCls="icon-addtop-rf" plain="false" onclick ="addUserPadLog('padCodeSpan${pad.padId}','newPadCode${pad.padId}','${pad.padClassify}')">更换</a>
						${fns:getLabelStyle('rf_pad.pad_classify',pad.padClassify)}
						</td>
					</tr>
				</c:forEach>
				
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
					<td class="td1">设备类别:</td>
					<td class="td2">
						<select class="easyui-combobox"  editable="false" id="padClassify" name="padClassify" data-options="required:true">
							<option value="">[全部]</option>
							<fns:getOptions category="rf_pad.pad_classify" value="${pad.padClassify}" keys="rf_pad.pad_classify@major,rf_pad.pad_classify@game"></fns:getOptions>
						</select> 
						<a href="#" iconCls="icon-search-rf" class="easyui-linkbutton" plain="false" onclick="doSearch()">查询</a>
					</td>
				</tr>
				
			</table>
	   	</form>
	</div>
  </div>
		<table id="module_datagrid_renewal" toolbar="#module_toolbar_renewal"></table>
</body>
</html>


