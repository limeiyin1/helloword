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
					<td class="td1">设备ID :</td>
					<td class="td2">${bean.map.showPadId}</td>
				</tr>
				<tr>
					<td class="td1">申请来源 :</td>
					<td class="td2">${bean.padSourceT2}</td>
				</tr>
				<tr>
					<td class="td1">设备名称:</td>
					<td class="td2">${bean.padName}</td>
				</tr>
				<tr>
					<td class="td1">用户设备名称:</td>
					<td class="td2">${bean.userPadNameT2}</td>
				</tr>
				<tr>
					<td class="td1">设备编码:</td>
					<td class="td2">${bean.padCode}</td>
				</tr>
				<tr>
					<td class="td1">设备等级:</td>
					<td class="td2">
						${fns:getLabelStyle('rf_user_pad.pad_grade',bean.padGradeT2)}
					</td>
				</tr>
				<tr>
					<td class="td1">会员手机号:</td>
					<td class="td2">${bean.map.userMobilePhone}</td>
				</tr>
				<tr>
					<td class="td1">绑定时间:</td>
					<td class="td2">
						<fmt:formatDate value="${bean.bindTimeT2}" pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
				</tr>
				<tr>
					<td class="td1">更换时间:</td>
					<td class="td2">
						<fmt:formatDate value="${bean.renewalTimeT2}" pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
				</tr>
				<tr>
					<td class="td1">最近受控时间:</td>
					<td class="td2">
						<fmt:formatDate value="${bean.padControlTimeT2}" pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
				</tr>
				<tr>
					<td class="td1">物理设备:</td>
					<td class="td2">${bean.map.deviceName}</td>
				</tr>
				<tr>
					<td class="td1">控制时间:</td>
					<td class="td2">${bean.map.onlinetime}</td>
				</tr>
				<tr>
					<td class="td1">设备过期时间:</td>
					<td class="td2">
						<fmt:formatDate value="${bean.expireTimeT2}" pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
				</tr>
				<tr>
					<td class="td1">MAC:</td>
					<td class="td2">${bean.vmMac}</td>
				</tr>
				<tr>
					<td class="td1">设备IP:</td>
					<td class="td2">${bean.padIp}</td>
				</tr>
				<tr>
					<td class="td1">物理设备IP:</td>
					<td class="td2">${bean.map.deviceIp}</td>
				</tr>
				<tr>
					<td class="td1">机房:</td>
					<td class="td2">${bean.map.idcName}</td>
				</tr>
				<tr>
					<td class="td1">ROM版本:</td>
					<td class="td2">${bean.romVersion}</td>
				</tr>
				<tr>
					<td class="td1">控制协议:</td>
					<td class="td2">
						${fns:getLabelStyle('rf_pad.control_protocol',bean.controlProtocol)}
					</td>
				</tr>
				<tr>
					<td class="td1">用户控制节点:</td>
					<td class="td2">${bean.map.controlName}</td>
				</tr>
				<tr>
					<td class="td1">设备控制节点:</td>
					<td class="td2">${bean.map.padControlName}</td>
				</tr>
				<tr>
					<td class="td1">设备SN:</td>
					<td class="td2">${bean.padSn}</td>
				</tr>
				<tr>
					<td class="td1">设备来源:</td>
					<td class="td2">${bean.map.padSourceName}</td>
				</tr>
				<tr>
					<td class="td1">设备imei:</td>
					<td class="td2">${bean.imei}</td>
				</tr>
				<tr>
					<td class="td1">备注:</td>
					<td class="td2">${bean.remark}</td>
				</tr>
				<tr>
					<td class="td1">维护状态:</td>
					<td class="td2">
						${fns:getLabelStyle('rf_pad.maint_status',bean.maintStatus)}
					</td>
				</tr>
				<tr>
					<td class="td1">授权状态:</td>
					<td class="td2">
						${fns:getLabelStyle('rf_pad.grant_open_status',bean.grantOpenStatus)}
					</td>
				</tr>
				<tr>
					<td class="td1">虚拟状态:</td>
					<td class="td2">
						${fns:getLabelStyle('rf_pad.vm_status',bean.vmStatus)}
					</td>
				</tr>
				<tr>
					<td class="td1">绑定状态:</td>
					<td class="td2">
						${fns:getLabelStyle('rf_pad.bind_status',bean.bindStatus)}
					</td>
				</tr>
				<tr>
					<td class="td1">启用状态:</td>
					<td class="td2">
						${fns:getLabelStyle('rf_pad.enable_status',bean.enableStatus)}
					</td>
				</tr>
				<tr>
					<td class="td1">受控状态:</td>
					<td class="td2">
						${fns:getLabelStyle('rf_pad.pad_status',bean.padStatus)}
					</td>
				</tr>
				<tr>
					<td class="td1">故障状态:</td>
					<td class="td2">
						${fns:getLabelStyle('rf_pad.fault_status',bean.faultStatus)}
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
