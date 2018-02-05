<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>设备</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
	
		
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
	<table id="module_submit_table">
		<tr>
			<td class="td1">设备编码:</td>
			<td class="td2">${bean.padCode }</td>
		</tr>
		<tr>
			<td class="td1">设备IP:</td>
			<td class="td2">${bean.padIp}
	
			</td>
		</tr>
		<tr>
			<td class="td1">设备MAC:</td>
			<td class="td2">${bean.vmMac}
	
			</td>
		</tr>
		<tr>
			<td class="td1">设备SN:</td>
			<td class="td2">${bean.padSn}
	
			</td>
		</tr>
		<tr>
			<td class="td1">机房:</td>
			<td class="td2">
				
				${bean.map.idcName }
			
			</td>
		</tr>
		<tr>
			<td class="td1">用户控制节点:</td>
			<td class="td2">
					${bean.map.userControlName }
			</td>
		</tr>
				<tr>
					<td class="td1">设备控制节点:</td>
					<td class="td2">${bean.map.controlName }</td>
				</tr>
				<tr>
					<td class="td1">设备管理控制节点:</td>
					<td class="td2">${bean.map.manageControlName }</td>
				</tr>
				<tr>
					<td class="td1">用户视频:</td>
					<td class="td2">${bean.map.userVideo }</td>
				</tr>
				<tr>
					<td class="td1">设备视频:</td>
					<td class="td2">${bean.map.padVideo }</td>
				</tr>
				<tr>
					<td class="td1">设备名称:</td>
					<td class="td2">${bean.padName }</td>
				</tr>
                  <tr>
					<td class="td1">设备守护进程号:</td>
					<td class="td2">${bean.remoteVersion }</td>
				</tr>
				<tr>
					<td class="td1">设备端口:</td>
					<td class="td2">${bean.padControlPort }</td>
			    </tr>
			    <tr>
					<td class="td1">批次号:</td>
					<td class="td2">${bean.batchNumber }</td>
			    </tr>
				<tr>
					<td class="td1">设备管理端口:</td>
					<td class="td2">${bean.padManagePort }</td>
				</tr>
		<%-- <tr>
			<td class="td1">排序:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="reorder" value="${bean.reorder }" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr> --%>
		<tr>
			<td class="td1">描述:</td>
			<td class="td2">${bean.remark }</td>
		</tr>
	</table>
    </form>
    </div>
</body>
</html>



