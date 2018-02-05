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
		    url:host+'addNewVM',
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
			//alert("into changeControlList");
			var userControlId = newValue;
			$.get("${ctx}/facility/relation/getByUserControlId",{userControlId:userControlId},callback2);
		}
		
		$(document).ready(function(){//加载页面完成后执行
			/* alert(${lastOne!=null});
			if(${lastOne!=null}){
			alert("${lastOne.idcId}"+"---${lastOne.userControlId}"+"---${lastOne.padControlId}"+"---${lastOne.padManageControlId}"+"---${lastOne.padVideoId}"+"---${lastOne.userVideoId}");
				$("#showId").combobox('select', ${lastOne.idcId});
				$("#userControl").combobox('select', ${lastOne.userControlId});
				$("#padControl").combobox('select', ${lastOne.padControlId});
				$("#padManageControl").combobox('select', ${lastOne.padManageControlId});
				$("#padVideo").combobox('select', ${lastOne.padVideoId});
				$("#userVideo").combobox('select', ${lastOne.userVideoId});
			}*/
			if(${lastOne==null}){
				//alert("no lastone");
				var idcId = $('#showId').val();
				getControlList(idcId,null);
			}
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
					${bean.deviceName }
					</td>
				</tr>
			
				<tr>
					<td class="td1">设备编号:</td>
					<td class="td2">${bean.deviceCode }</td>
				</tr>
				
				<tr>
					<td class="td1">设备来源:</td>
					<td class="td2">
						<%-- <select editable="false" name="deviceSource" id="deviceSource" class="easyui-combobox input_width_default" validType="selectValueRequired['#deviceSource']">
							<option value="">[请选择]</option>
							<c:forEach var="one" items="${facilityList}">
								<option value="${one.facilityCode}" ${bean.deviceSource==one.facilityCode?"selected":"" }>${one.facilityName}</option>
							</c:forEach>
						</select> --%>
						${bean.deviceSource}
					</td>
				</tr>
				
				<tr>
					<td class="td1">设备类型:</td>
					<td class="td2">
						<%-- <select class="easyui-combobox" name="deviceType" data-options="required:false,editable:false">
							<option value="ANDROID" <c:if test="${'ANDROID'==bean.deviceType}">selected="selected"</c:if>>ANDROID</option>
							<option value="IOS" <c:if test="${'IOS'==bean.deviceType}">selected="selected"</c:if>>IOS</option>
						</select> --%>
						${bean.deviceType}
					</td>
				</tr>
				
				<tr>
					<td class="td1">设备rom版本号:</td>
					<td class="td2">
						<%-- <select class="easyui-combobox" name="romVersion" data-options="required:true,editable:false">
							 <c:forEach items="${romVersions}" var="one">
							   	   <option  value="${one.key }" <c:if test="${one.key==bean.romVersion}">selected="selected"</c:if>>${one.value }</option>
							 </c:forEach>
						</select> --%>
						${bean.romVersion}
					</td>
				</tr>
				
				<tr>
					<td class="td1">设备管理节点:</td>
					<td class="td2">
					<%-- <select class="easyui-combobox" name="deviceManageControlId" data-options="required:false,editable:false">
							<c:forEach items="${deviceControlList}" var="one">
								<option value="${one.controlId }"
									<c:if test="${one.controlId==bean.deviceManageControlId}">selected="selected"</c:if>>${one.controlName }</option>
							</c:forEach>
					</select> --%>
					${deviceManageControlName}
					</td>
				</tr>

				<tr id="beginIp">
					<td class="td1">物理设备IP:</td>
					<td class="td2">  
				   <%-- <c:choose>  
  
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
					</c:choose>   --%>
					${bean.deviceIp}
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
		
		
				<tr>
					<td class="td1">虚拟设备IP:</td>
					<td class="td2">
					<input style="width:255px" class="easyui-textbox" type="text" name="vmIp"  data-options="required:true,validType:['checkIp']" missingMessage="必须填写正确的IP格式" />
					</td>
			    </tr>
			
			<tr>
				<td class="td1">机房:</td>
				<td class="td2">
					<select id="showId" class="easyui-combobox"  editable="false" name="idcId" data-options="required:true,onChange:getControlList">
						<c:forEach items="${idcList}" var="one">
							<option value="${one.idcId }" 
							<c:if test="${one.idcId==lastOne.idcId}">selected="selected"</c:if>
							 >${one.idcName }</option>
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
							<option value="${one.controlId }" 
							<c:if test="${one.controlId==lastOne.userControlId}">selected="selected"</c:if> 
							>${one.controlName }</option>
						</c:forEach>
					</select> 
				</td>
			</tr>
			<tr>
				<td class="td1">设备控制节点:</td>
				<td class="td2">
					<select class="easyui-combobox"  editable="false" id="padControl" name="padControlId" data-options="required:true">
						<c:forEach items="${padControlList}" var="one">
							<option value="${one.controlId }"
							<c:if test="${one.controlId==lastOne.padControlId}">selected="selected"</c:if> 
							 >${one.controlName }</option>
						</c:forEach>
					</select> 
				</td>
			</tr>
			<tr>
				<td class="td1">设备管理控制节点:</td>
				<td class="td2">
					<select class="easyui-combobox"  editable="false" id="padManageControl" name="padManageControlId" data-options="required:true">
						<c:forEach items="${manageControlList}" var="one">
							<option value="${one.controlId }" 
							 <c:if test="${one.controlId==lastOne.padManageControlId}">selected="selected"</c:if> 
							 >${one.controlName }</option>
						</c:forEach>
					</select> 
				</td>
			</tr>
			<tr>
				<td class="td1">设备视频:</td>
				<td class="td2">
					<select class="easyui-combobox"  editable="false" id="padVideo" name="padVideoId" data-options="required:true">
						<c:forEach items="${padVideoList}" var="one">
							<option value="${one.videoId }" 
							<c:if test="${one.videoId==lastOne.padVideoId}">selected="selected"</c:if> 
							 >${one.videoName }</option>
						</c:forEach>
					</select> 
				</td>
			</tr>
			<tr>
				<td class="td1">用户视频:</td>
				<td class="td2">
					<select class="easyui-combobox"  editable="false" id="userVideo" name="userVideoId" data-options="required:true">
						<c:forEach items="${userVideoList}" var="one">
							<option value="${one.videoId }" 
							 <c:if test="${one.videoId==lastOne.userVideoId}">selected="selected"</c:if> 
							 >${one.videoName }</option>
						</c:forEach>
					</select> 
				</td>
			</tr>
	   
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





