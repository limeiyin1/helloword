<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>

<html>
<head>
<title>设备配置</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	$(function() {
		$('.easyui-form').form({
			url : host + 'update',
			success : configCallback
		});
	});
</script>
</head>
<body>
	<div id="module_submit_container"  align="center">
		<form id="module_submit_form" class="easyui-form" method="post">
			<table class="easyui-table" >
				<tr >
					<td class="td1"><input name="sysConfigs[0].configCode" type="hidden" value="${firstLimit.configCode}"/></td>
					<td class="td1">${firstLimit.configLabel }:</td>
					<td class="td2"><input name="sysConfigs[0].configValue" class="easyui-numberbox" type="text" value="${firstLimit.configValue }"/></td>
				</tr>
				<tr >
					<td class="td1"><input name="sysConfigs[1].configCode" type="hidden" value="${shareTimes.configCode}"/></td>
					<td class="td1">${shareTimes.configLabel }:</td>
					<td class="td2"><input name="sysConfigs[1].configValue" class="easyui-numberbox" type="text" value="${shareTimes.configValue }"/></td>
				</tr>
					<tr >
					<td class="td1"><input name="sysConfigs[2].configCode" type="hidden" value="${shareDay.configCode}"/></td>
					<td class="td1">${shareDay.configLabel}:</td>
					<td class="td2"><input name="sysConfigs[2].configValue" class="easyui-numberbox" type="text" value="${shareDay.configValue }"/></td>
				</tr>
				<%-- <tr >
					<td class="td1"></td>
					<td class="td1">普通设备截图预览限时是否启用:</td>
					<td class="td2">
						<select name="sysConfigs[3].enableStatus">
							<option value="1" <c:if test="${1 == screenLimit.enableStatus }">selected="selected"</c:if>>是</option>
							<option value="0" <c:if test="${0 == screenLimit.enableStatus }">selected="selected"</c:if>>否</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="td1"><input name="sysConfigs[3].configCode" type="hidden" value="${screenLimit.configCode}"/></td>
					<td class="td1">${screenLimit.configLabel }:</td>
					<td class="td2">
						<select name="sysConfigs[3].configValue">
							<option value="1" <c:if test="${1 == screenLimit.configValue }">selected="selected"</c:if>>星期一</option>
							<option value="2" <c:if test="${2 == screenLimit.configValue }">selected="selected"</c:if>>星期二</option>
							<option value="3" <c:if test="${3 == screenLimit.configValue }">selected="selected"</c:if>>星期三</option>
							<option value="4" <c:if test="${4 == screenLimit.configValue }">selected="selected"</c:if>>星期四</option>
							<option value="5" <c:if test="${5 == screenLimit.configValue }">selected="selected"</c:if>>星期五</option>
							<option value="6" <c:if test="${6 == screenLimit.configValue }">selected="selected"</c:if>>星期六</option>
							<option value="7" <c:if test="${7 == screenLimit.configValue }">selected="selected"</c:if>>星期天</option>
							<option value="0" <c:if test="${0 == screenLimit.configValue }">selected="selected"</c:if>>全周</option>
						</select>
					</td>
				</tr> --%>
				<tr>
					<td class="td1"></td>
					<td class="td1">普通设备上传功能限时是否启用:</td>
					<td class="td2">
						<select name="sysConfigs[4].enableStatus">
							<option value="1" <c:if test="${1 == uoloadLimit.enableStatus }">selected="selected"</c:if>>是</option>
							<option value="0" <c:if test="${0 == uoloadLimit.enableStatus }">selected="selected"</c:if>>否</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="td1"><input name="sysConfigs[4].configCode" type="hidden" value="${uoloadLimit.configCode}"/></td>
					<td class="td1">${uoloadLimit.configLabel }:</td>
					<td class="td2">
						<select name="sysConfigs[4].configValue">
							<option value="1" <c:if test="${1 == uoloadLimit.configValue }">selected="selected"</c:if>>星期一</option>
							<option value="2" <c:if test="${2 == uoloadLimit.configValue }">selected="selected"</c:if>>星期二</option>
							<option value="3" <c:if test="${3 == uoloadLimit.configValue }">selected="selected"</c:if>>星期三</option>
							<option value="4" <c:if test="${4 == uoloadLimit.configValue }">selected="selected"</c:if>>星期四</option>
							<option value="5" <c:if test="${5 == uoloadLimit.configValue }">selected="selected"</c:if>>星期五</option>
							<option value="6" <c:if test="${6 == uoloadLimit.configValue }">selected="selected"</c:if>>星期六</option>
							<option value="7" <c:if test="${7 == uoloadLimit.configValue }">selected="selected"</c:if>>星期天</option>
							<option value="0" <c:if test="${0 == uoloadLimit.configValue }">selected="selected"</c:if>>全周</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="td1"></td>
					<td class="td1">普通设备微信功能限时是否启用:</td>
					<td class="td2">
						<select name="sysConfigs[5].enableStatus">
							<option value="1" <c:if test="${1 == weixinLImit.enableStatus }">selected="selected"</c:if>>是</option>
							<option value="0" <c:if test="${0 == weixinLImit.enableStatus }">selected="selected"</c:if>>否</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="td1"><input name="sysConfigs[5].configCode" type="hidden" value="${weixinLImit.configCode}"/></td>
					<td class="td1">${weixinLImit.configLabel }:</td>
					<td class="td2">
						<select name="sysConfigs[5].configValue">
							<option value="1" <c:if test="${1 == weixinLImit.configValue }">selected="selected"</c:if>>星期一</option>
							<option value="2" <c:if test="${2 == weixinLImit.configValue }">selected="selected"</c:if>>星期二</option>
							<option value="3" <c:if test="${3 == weixinLImit.configValue }">selected="selected"</c:if>>星期三</option>
							<option value="4" <c:if test="${4 == weixinLImit.configValue }">selected="selected"</c:if>>星期四</option>
							<option value="5" <c:if test="${5 == weixinLImit.configValue }">selected="selected"</c:if>>星期五</option>
							<option value="6" <c:if test="${6 == weixinLImit.configValue }">selected="selected"</c:if>>星期六</option>
							<option value="7" <c:if test="${7 == weixinLImit.configValue }">selected="selected"</c:if>>星期天</option>
							<option value="0" <c:if test="${0 == weixinLImit.configValue }">selected="selected"</c:if>>全周</option>
						</select>
					</td>
				</tr>
	        	<tr>
			        <td class="td1"></td>
		         	<td class="td2"> 
		         	<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-ok-rf" plain="false" onclick="submitForm()">保存</a>
		         	</td>
		        </tr>
			</table>
		</form>
	</div>
</body>


</html>