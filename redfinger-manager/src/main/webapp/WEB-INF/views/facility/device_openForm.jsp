<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>虚拟设备开放授权批量操作</title>
<meta name="decorator" content="default" />
</head>
<body>
	<script type="text/javascript">
	$('.easyui-form').form({
	    url:host+'openAll',   
	    success:callback
	});
	

	</script>
 
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post">
		 <table id="module_submit_table" >
				<tr>
					<td class="td1">设备编号:</td>
					<td class="td2">设备IP</td>
				</tr>
				<c:forEach  items="${list}" var="bean">
				<tr>
					<td class="td1">${bean.deviceCode} <input type="hidden" name="ids" value="${bean.deviceId}"></td>
					<td class="td2">${bean.deviceIp}</td>
				</tr>
				</c:forEach>
			
				<tr>
					<td class="td1"> 执行操作:</td>
					<td class="td2"><select  style="width: 100px" class="easyui-combobox" name="open" data-options="required:true,editable:false">
				<fns:getOptions category="rf_pad.grant_open_status" ></fns:getOptions>
				</select> </td>
				</tr>
			
				<tr>
					<td class="td1"> 设备SN： </td>
					<td class="td2">
					<select  style="width: 100px" class="easyui-combobox" name="sn" data-options="required:true,editable:false">
				<option value="all" >-全部-</option>
				<option value="0" >-0-</option>
				<option value="1" >-1-</option>
				<option value="2" >-2-</option>
				<option value="3" >-3-</option>
                <option value="4" >-4-</option>
				</select> 
					
					</td>
				</tr>
		</table>
	   </form>
		</div>

</body>
</html>


