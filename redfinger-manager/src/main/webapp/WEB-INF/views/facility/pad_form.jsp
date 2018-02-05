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
	       }  
	      });   
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
  	<input type="hidden" name="padId" value="${bean.padId}">
	<table id="module_submit_table">
		<tr>
			<td class="td1">设备编码:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="padCode" value="${bean.padCode }"  missingMessage="至少输入6位设备编码前缀"  data-options="required:true,validType:'length[6,32]'" /></td>
		</tr>
		<tr>
			<td class="td1">设备IP:</td>
			<td class="td2">IP:<input style="width:120px" class="easyui-textbox" type="text" name="padIp" value="${bean.padIp}" data-options="required:true,validType:['checkIp']" missingMessage="必须填写正确的IP格式" />
		<c:if test="${ empty bean.padId}" >终止IP:<input style="width: 50px" type="text" name="ids" class="easyui-numberbox" min="0" max="255" data-options="required:true" missingMessage="结束IP号不能大于255"  /></c:if>
			</td>
		</tr>
		<tr>
			<td class="td1">机房:</td>
			<td class="td2">
				<select class="easyui-combobox"  editable="false" name="idcId" data-options="required:true">
					<c:forEach items="${idcList}" var="one">
						<option value="${one.idcId }" <c:if test="${one.idcId==bean.idcId}">selected="selected"</c:if>>${one.idcName }</option>
					</c:forEach>
				</select> 
			</td>
		</tr>
		<tr>
			<td class="td1">控制协议:</td>
			<td class="td2">
				<select class="easyui-combobox"  editable="false" name="controlProtocol" data-options="required:true">
						<fns:getOptions category="rf_pad.control_protocol" value="${bean.controlProtocol}" keys="rf_pad.control_protocol@one,rf_pad.control_protocol@two"></fns:getOptions>
				</select> 
			</td>
		</tr>
		<tr>
			<td class="td1">控制节点:</td>
			<td class="td2">
				<select class="easyui-combobox"  editable="false" name="userControlId" data-options="required:true">
					<c:forEach items="${controlList}" var="one">
						<option value="${one.controlId }" <c:if test="${one.controlId==bean.userControlId}">selected="selected"</c:if>>${one.controlName }</option>
					</c:forEach>
				</select> 
			</td>
		</tr>
		<tr>
			<td class="td1">设备控制节点:</td>
			<td class="td2">
				<select class="easyui-combobox"  editable="false" name="padControlId" data-options="required:true">
					<c:forEach items="${padControlList}" var="one">
						<option value="${one.controlId }" <c:if test="${one.controlId==bean.padControlId}">selected="selected"</c:if>>${one.controlName }</option>
					</c:forEach>
				</select> 
			</td>
		</tr>
		<tr>
			<td class="td1">设备管理控制节点:</td>
			<td class="td2">
				<select class="easyui-combobox"  editable="false" name="padManageControlId" data-options="required:true">
					<c:forEach items="${manageControlList}" var="one">
						<option value="${one.controlId }" <c:if test="${one.controlId==bean.padManageControlId}">selected="selected"</c:if>>${one.controlName }</option>
					</c:forEach>
				</select> 
			</td>
		</tr>
		<tr>
			<td class="td1">虚拟设备状态：</td>
			<td class="td2"><input style="width: 50px"  class="easyui-numberbox" type="text" name="vmStatus" value="${bean.vmStatus }" data-options="required:false,validType:'length[0,9]',min:0,max:1" /></td>
		</tr>
		<tr>
			<td class="td1">注*</td>
			<td class="td2">0表示虚拟设备离线，1表示虚拟设备在线，不填 表示增加的是老的实体设备</td>
		</tr>
		<tr>
			<td class="td1">MAC:</td>
			<td class="td2"><input style="width: 200px"  class="easyui-textbox" type="text" name="vmMac" value="${bean.vmMac }" data-options="required:false,validType:'length[0,50]'" /></td>
		</tr>
		<tr>
			<td class="td1">注*</td>
			<td class="td2">该数据针对虚拟设备才有</td>
		</tr>
		<tr>
			<td class="td1">虚拟节点序号:</td>
			<td class="td2"><input style="width: 50px"  class="easyui-numberbox" type="text" name="padSn" value="${bean.padSn }" data-options="required:false,validType:'length[0,9]'" /></td>
		</tr>
		<tr>
			<td class="td1">注*</td>
			<td class="td2">该数据针对虚拟设备才有</td>
		</tr>
		<tr>
			<td class="td1">设备名称:</td>
				<td class="td2">
				<input class="easyui-textbox" type="text" name="padName" value="${bean.padName }" data-options="required:true,validType:'length[0,66]'" />
				</td>
		</tr>

		<%-- <tr>
			<td class="td1">设备端口:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="padControlPort" value="${bean.padControlPort }" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		<tr>
			<td class="td1">设备管理端口:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="padManagePort" value="${bean.padManagePort }" data-options="required:true,validType:'length[0,5]'" /></td>
		</tr> --%>
		<tr>
			<td class="td1">设备视频:</td>
			<td class="td2">
				<select class="easyui-combobox"  editable="false" name="padVideoId" data-options="required:true">
					<c:forEach items="${padVideoList}" var="one">
						<option value="${one.videoId }" <c:if test="${one.videoId==bean.padVideoId}">selected="selected"</c:if>>${one.videoName }</option>
					</c:forEach>
				</select> 
			</td>
		</tr>
		<tr>
			<td class="td1">用户视频:</td>
			<td class="td2">
				<select class="easyui-combobox"  editable="false" name="userVideoId" data-options="required:true">
					<c:forEach items="${userVideoList}" var="one">
						<option value="${one.videoId }" <c:if test="${one.videoId==bean.userVideoId}">selected="selected"</c:if>>${one.videoName }</option>
					</c:forEach>
				</select> 
			</td>
		</tr>
		<tr>
			<td class="td1">是否启用:</td>
			<td class="td2">
				<select class="easyui-combobox"  editable="false" name="enableStatus" data-options="required:true">
						<fns:getOptions category="rf_pad.enable_status" value="${bean.enableStatus}" keys="rf_pad.enable_status@start,rf_pad.enable_status@forbidden" ></fns:getOptions>
				</select> 
			</td>
		</tr>
		<tr>
			<td class="td1">设备类别:</td>
			<td class="td2">
				<select class="easyui-combobox"  editable="false" name="padClassify" data-options="required:true">
						<fns:getOptions category="rf_pad.pad_classify" value="${bean.padClassify}" keys="rf_pad.pad_classify@major,rf_pad.pad_classify@game,rf_pad.pad_classify@gvip,rf_pad.pad_classify@cloud,rf_pad.pad_classify@svip"></fns:getOptions>
				</select> 
			</td>
		</tr>
		<tr>
			<td class="td1">设备守护进程号:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="remoteVersion" value="${bean.remoteVersion }" data-options="required:false,validType:'length[0,32]'" /></td>
		</tr>
		<tr>
			<td class="td1">批次号:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="batchNumber" value="${bean.batchNumber }" data-options="required:false,validType:'length[0,32]'" /></td>
		</tr>
		 <tr>
			<td class="td1">排序:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="reorder" value="${bean.reorder }" data-options="required:false,validType:'length[0,32]'" /></td>
		</tr>
		<tr>
			<td class="td1">描述:</td>
			<td class="td2"><input class="easyui-textbox" name="remark" value="${bean.remark }" data-options="multiline:true,validType:'length[0,200]'" style="height:60px" /></td>
		</tr>
	</table>
    </form>
    </div>
</body>
</html>



