<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>新增/编辑设备</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body onload="changeControlList()">

	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'update',   
		    success:callback

		});
		
	    $.extend($.fn.validatebox.defaults.rules, {            
            checkIp : {// 验证IP地址  
                validator : function(value) {  
                    var reg = /^((1?\d?\d|(2([0-4]\d|5[0-5])))\.){3}(1?\d?\d|(2([0-4]\d|5[0-5])))$/ ;  
                    return reg.test(value);  
                },  
                message : 'IP地址格式不正确'  
        } });
	    
		$('#batchIp').change(function(){
			  var trs =document.getElementById("endadrs");  
			if($('#batchIp').is(':checked')){
				//$("#beginIp").after('<tr><td class="td1">'+'<input id="ip1" name="ip1" style="width: 50px" class="easyui-numberbox" data-options="required:true,validType:\'length[0,3]\',max:255"/>'+'.</td><td class="td2"></td></tr>');
				$("#endadrs").removeClass("hide");	
			//	$("#vmAdders").removeClass("hide");
				$('#eip1').numberbox({required:true});
				$('#eip2').numberbox({required:true});
				$('#eip3').numberbox({required:true});
				$('#eip4').numberbox({required:true});
				//$("#endadrs").find("input").attr("disabled",false);
			}else{	
				$("#endadrs").addClass("hide");
				//$("#vmAdders").addClass("hide");
				$('#eip1').numberbox({required:false});
				$('#eip2').numberbox({required:false});
				$('#eip3').numberbox({required:false});
				$('#eip4').numberbox({required:false});
			//	$("#endadrs").find("input").attr("disabled",true);
			}
		});
		
		var deviceIpVal=function(newValue, oldValue){
			if (newValue >=0 || newValue<=255) {
		     var ip1=$('#ip1').numberbox('getValue');
		     var ip2=$('#ip2').numberbox('getValue');
		     var ip3=$('#ip3').numberbox('getValue');
		     var ip4=$('#ip4').numberbox('getValue');
		     var ip=ip1+"."+ip2+"."+ip3+"."+ip4;
				$('#ip').val(ip);
			}
			}
		
		var endIp=function(newValue, oldValue){
			if (newValue >=0 || newValue<=255) {
		     var ip1=$('#eip1').numberbox('getValue');
		     var ip2=$('#eip2').numberbox('getValue');
		     var ip3=$('#eip3').numberbox('getValue');
		     var ip4=$('#eip4').numberbox('getValue');
		     var ip=ip1+"."+ip2+"."+ip3+"."+ip4;
				$('#endIp').val(ip);
			}
			}
		if($('#batchIp').is(':checked')){
			//$("#beginIp").after('<tr><td class="td1">'+'<input id="ip1" name="ip1" style="width: 50px" class="easyui-numberbox" data-options="required:true,validType:\'length[0,3]\',max:255"/>'+'.</td><td class="td2"></td></tr>');
			$("#endadrs").removeClass("hide");
		//	$("#vmAdders").removeClass("hide");
		//	$("#endadrs").find("input").attr("disabled",false);
		}else{	
			$("#endadrs").addClass("hide");
			//$("#vmAdders").addClass("hide");
			$('#eip1').numberbox({required:false});
			$('#eip2').numberbox({required:false});
			$('#eip3').numberbox({required:false});
			$('#eip4').numberbox({required:false});
		//	$("#endadrs").find("input").attr("disabled",true);
		}
		
		$.extend($.fn.validatebox.defaults.rules, {  
			selectValueRequired: {
				validator: function(value,param){ 
					return $(param[0]).find("option:contains('"+value+"')").val() != '';  
				},  
				message: '该选项为必选项'  
			}  
		});
		
		/////////////功能添加分割线//////////////////////////
		var getControlListCallback=function (json,fun){
			var data;//var data = new Array();
			data = $.parseJSON(json);
			data = $.parseJSON(data);
			//alert(data[0].controlId+"--"+data[1].controlId+"--"+data.length);
			//$("#userControl").find("option").remove();
			//$("#userControl").combobox('clear');//清空input
			//data.unshift({'controlId':'','controlName':'请选择'});
			$("#userControl").combobox('loadData',data);//重新加载数据
			$("#userControl").combobox('select', data[0].controlId);//选择第一个
			var userControlId = data[0].controlId;
			changeControlList(userControlId,null);
		}
		var callback1=getControlListCallback;
 		var getControlList = function (newValue, oldValue){
 		 	var idcId = newValue;
			//alert("${ctx}/facility/device/getControls");
			$.get("${ctx}/facility/device/getControls",{idcId:idcId},callback1);
		}
		
		/////////////功能添加分割线//////////////////////////
		var changeControlListCallback=function (data,fun){
			data = $.parseJSON(data);
			//alert(data==null);
			if(data!=null){
			$("#padControl").combobox('select', data.padControlId);
			$("#padManageControl").combobox('select', data.manageControlId);
			$("#padVideo").combobox('select', data.padVideoId);
			$("#userVideo").combobox('select', data.userVideoId);
			}
		}
		var callback2=changeControlListCallback;
		var changeControlList = function(newValue, oldValue){
			//alert("into");
			var userControlId = newValue;
			$.get("${ctx}/facility/relation/getByUserControlId",{userControlId:userControlId},callback2);
		}
		
		$(document).ready(function(){//加载页面完成后执行
			var idcId = $('#showId').val();
			getControlList(idcId,null);
			if(''!='${bean.deviceId}'){
				$("#padClassify_tr").hide();
			}
			
			/* var userControlId = $('#userControl').val();
			changeControlList(userControlId,null); */
		});
		
	</script>
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post">
		<c:if test="${not empty bean.deviceId}">
		<input type="hidden" name="deviceId" value="${bean.deviceId }">
		</c:if>
			
			<table id="module_submit_table">
			
			    <tr>
					<td class="td1">设备名称:</td>
					<td class="td2">
					<input class="easyui-textbox" type="text"name="deviceName" value="${bean.deviceName }"data-options="required:true,validType:'length[0,32]'" />
					</td>
				</tr>
			
				<tr>
					<td class="td1">设备编号:</td>
					<td class="td2"><input class="easyui-textbox" type="text"name="deviceCode" value="${bean.deviceCode }"data-options="required:true,validType:'length[0,32]'" /></td>
				</tr>
				
				<tr>
					<td class="td1">设备来源:</td>
					<td class="td2">
						<select editable="false" name="deviceSource" id="deviceSource" class="easyui-combobox input_width_default" validType="selectValueRequired['#deviceSource']">
							<option value="">[请选择]</option>
							<c:forEach var="one" items="${facilityList}">
								<option value="${one.facilityCode}" ${bean.deviceSource==one.facilityCode?"selected":"" }>${one.facilityName}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="td1">设备类型:</td>
					<td class="td2">
						<select class="easyui-combobox" name="deviceType" data-options="required:true,editable:false" validType="selectValueRequired['#deviceSource']">
							<option value="">[请选择]</option>
							<option value="ANDROID" <c:if test="${'ANDROID'==bean.deviceType}">selected="selected"</c:if>>ANDROID</option>
							<option value="IOS" <c:if test="${'IOS'==bean.deviceType}">selected="selected"</c:if>>IOS</option>
						</select>
					</td>
				</tr>
				
				<tr id="padClassify_tr">
					<td class="td1">设备类别:</td>
					<td class="td2">
						<select class="easyui-combobox"  editable="false" name="padClassify" data-options="required:true">
								<fns:getOptions category="rf_pad.pad_classify" ></fns:getOptions>
						</select> 
					</td>
				</tr>
				
				<tr>
					<td class="td1">设备rom版本号:</td>
					<td class="td2">
						<select class="easyui-combobox" name="romVersion" data-options="required:true,editable:false" validType="selectValueRequired['#deviceSource']">
							 <%-- <c:forEach items="${romVersions}" var="one">
							   	   <option  value="${one.key }" <c:if test="${one.key==bean.romVersion}">selected="selected"</c:if>>${one.value }</option>
							 </c:forEach> --%>
							 <option value="">[请选择]</option>
							 <option  value="4.4" <c:if test="${'4.4'==bean.romVersion}">selected="selected"</c:if>>4.4</option>
							 <option  value="6.0" <c:if test="${'6.0'==bean.romVersion}">selected="selected"</c:if>>6.0</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="td1">物理机运存:</td>
					<td class="td2">
						<select class="easyui-combobox" name="ramSize" data-options="required:true,editable:false" validType="selectValueRequired['#deviceSource']">
							<option value="">[请选择]</option>
							 <option  value=2 <c:if test="${'2'==bean.ramSize}">selected="selected"</c:if>>2G</option>
							 <option  value=3 <c:if test="${'3'==bean.ramSize}">selected="selected"</c:if>>3G</option>
							 <option  value=4 <c:if test="${'4'==bean.ramSize}">selected="selected"</c:if>>4G</option>
							 <option  value=8 <c:if test="${'8'==bean.ramSize}">selected="selected"</c:if>>8G</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="td1">设备管理节点:</td>
					<td class="td2"><select class="easyui-combobox" name="deviceManageControlId" data-options="required:false,editable:false">
							<c:forEach items="${deviceControlList}" var="one">
								<option value="${one.controlId }"
									<c:if test="${one.controlId==bean.deviceManageControlId}">selected="selected"</c:if>>${one.controlName }</option>
							</c:forEach>
					</select></td>
				</tr>

				<tr id="beginIp">
					<td class="td1">设备IP:</td>
					<td class="td2">  
				   <c:choose>  
  
                      <c:when test="${not empty bean.deviceIp}">  
                      <input style="width:255px" class="easyui-textbox" type="text" name="deviceIp" value="${bean.deviceIp}" data-options="required:true,validType:['checkIp']" missingMessage="必须填写正确的IP格式" />
                      </c:when>  
                           <c:otherwise>  
                      <input id="ip1" name="ip1" style="width: 50px" class="easyui-numberbox" data-options="required:true,validType:'length[0,3]',max:255"/>.
				      <input id="ip2" name="ip2" style="width: 50px" class="easyui-numberbox" data-options="required:true,validType:'length[0,3]',max:255"/>.
				      <input id="ip3" name="ip3" style="width: 50px" class="easyui-numberbox" data-options="required:true,validType:'length[0,3]',max:255"/>.
				      <input id="ip4" name="ip4" style="width: 50px"  class="easyui-numberbox" data-options="required:true,validType:'length[0,3]',max:255,onChange:deviceIpVal" />
                      <input id="ip"  name="deviceIp" type="hidden"/> 
                      <input id="batchIp" name="batchIp" type="checkbox"  />批量新增
					  </c:otherwise>  
					</c:choose>  
                      </td>
				</tr>  
				<!--run-in：正常显示 ，style="display:none;"none：元素不会显示  -->
				<tr id="endadrs" >
					<td class="td1">结束IP:</td>
					<td class="td2">   
					  <input id="eip1" name="ip1" style="width: 50px" class="easyui-numberbox" data-options="required:true,validType:'length[0,3]',max:255"/>.
				      <input id="eip2" name="ip2" style="width: 50px" class="easyui-numberbox" data-options="required:true,validType:'length[0,3]',max:255"/>.
				      <input id="eip3" name="ip3" style="width: 50px" class="easyui-numberbox" data-options="required:true,validType:'length[0,3]',max:255"/>.
				      <input id="eip4" name="ip4" style="width: 50px"  class="easyui-numberbox" data-options="required:true,validType:'length[0,3]',max:255,onChange:endIp" />
                      <input id="endIp"  name="endIp" type="hidden"/>
                      </td>
				</tr>  
				
				
				<%-- <tr>
					<td class="td1">设备状态:</td>
					<td class="td2">	
					<select class="easyui-combobox"  editable="false" name="deviceStatus" data-options="required:true">
						<fns:getOptions category="rf_device.device_status" value="${bean.deviceStatus}" keys="rf_device.device_status@offline,rf_device.device_status@online," ></fns:getOptions>
				</select> 
					</td>
				</tr> --%>
				
				<tr>
					<td class="td1">排序:</td>
					<td class="td2"><input class="easyui-numberbox" type="text"name="reorder" value="${bean.reorder }"data-options="required:true,validType:'length[0,32]'" />
					</td>
				</tr>
				<tr>
					<td class="td1">备注:</td>
					<td class="td2">
					<%-- <input class="easyui-textbox" name="remark"value="${bean.remark }"data-options="multiline:true,validType:'length[0,200]'"style="height: 60px" /> --%>
					<textarea name="remark" class="easyui-validatebox" style="height:100px;width:280px;" validType="text[1,500]" wrap="hard" >${bean.remark}</textarea>
					</td>
				</tr>
				<!--虚拟机配置信息  -->
		<c:if test="${empty bean.deviceId}">
		<!--  id="vmAdders" -->
				<tr>
					<td class="td1">虚拟设备开始IP:</td>
					<td class="td2">
					<input style="width:255px" class="easyui-textbox" type="text" name="vmIp"  data-options="required:true,validType:['checkIp']" missingMessage="必须填写正确的IP格式" />
				
					</td>
			    </tr>
			    
			<tr>
				<td class="td1">控制协议:</td>
				<td class="td2">
					<select class="easyui-combobox"  editable="false" name="controlProtocol" data-options="required:true,editable:false" validType="selectValueRequired['#deviceSource']">
						<option value="">[请选择]</option>
						<fns:getOptions category="rf_pad.control_protocol" value="${bean.controlProtocol}" keys="rf_pad.control_protocol@one,rf_pad.control_protocol@two"></fns:getOptions>
					</select>
				</td>
			</tr>
			
			<tr>
				<td class="td1">机房:</td>
				<td class="td2">
					<select id="showId" class="easyui-combobox"  editable="false" name="idcId" data-options="required:true,onChange:getControlList">
						<c:forEach items="${idcList}" var="one">
							<option value="${one.idcId }" >${one.idcName }</option>
						</c:forEach>
					</select> 
				</td>
			</tr>
			<tr>
				<td class="td1">用户控制节点:</td>
				<td class="td2">
					<select class="easyui-combobox"  editable="false" id="userControl" onload="" name="userControlId" data-options="required:true,onChange:changeControlList,
					valueField: 'controlId',
					textField: 'controlName'
					">
						<c:forEach items="${controlList}" var="one">
							<option value="${one.controlId }">${one.controlName }</option>
						</c:forEach>
					</select> 
				</td>
			</tr>
			<tr>
				<td class="td1">设备控制节点:</td>
				<td class="td2">
					<select class="easyui-combobox"  editable="false" id="padControl" name="padControlId" data-options="required:true">
						<c:forEach items="${padControlList}" var="one">
							<option value="${one.controlId }" >${one.controlName }</option>
						</c:forEach>
					</select> 
				</td>
			</tr>
			<tr>
				<td class="td1">设备管理控制节点:</td>
				<td class="td2">
					<select class="easyui-combobox"  editable="false" id="padManageControl" name="padManageControlId" data-options="required:true">
						<c:forEach items="${manageControlList}" var="one">
							<option value="${one.controlId }">${one.controlName }</option>
						</c:forEach>
					</select> 
				</td>
			</tr>
			<tr>
				<td class="td1">设备视频:</td>
				<td class="td2">
					<select class="easyui-combobox"  editable="false" id="padVideo" name="padVideoId" data-options="required:true">
						<c:forEach items="${padVideoList}" var="one">
							<option value="${one.videoId }">${one.videoName }</option>
						</c:forEach>
					</select> 
				</td>
			</tr>
			<tr>
				<td class="td1">用户视频:</td>
				<td class="td2">
					<select class="easyui-combobox"  editable="false" id="userVideo" name="userVideoId" data-options="required:true">
						<c:forEach items="${userVideoList}" var="one">
							<option value="${one.videoId }" >${one.videoName }</option>
						</c:forEach>
					</select> 
				</td>
			</tr>
	   </c:if>
		<!-- 虚拟设备配置结束 -->
		
			</table>
		</form>
	</div>
</body>
<script type="text/javascript">
window.onload = function() { 

alert($("#userControl").val);
}; 
</script>

</html>





