<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>

<html>
<head>
<title>更换设备配置</title>
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
    <c:set var="hasPpermission" value="${not empty sessionScope.permission.button_update_renewal_config}" scope="page"/>
	<div id="module_submit_container"  align="center">
		<form id="module_submit_form" class="easyui-form" method="post">
			<table class="easyui-table" >
				<%-- 自行更换开关 --%>
				<fns:getConfigDictTr code="config_pad_renewal_on_off" category="global.switch_status" disabled="false" readonly="${!hasPpermission}" />
				
	            <%-- 每日最多可更换设备数量 --%>
	            <c:choose>
				    <c:when test="${hasPpermission}">
				    	<fns:getConfigTr code="config_pad_renewal_max_count" number="true" dataOptions="min:0"/>
				    </c:when>
				    <c:otherwise>
				    	<fns:getConfigTr code="config_pad_renewal_max_count" number="true" readonly=""/>
				    </c:otherwise>
				</c:choose>
	            
	            
	            <%-- 当前离线设备数 --%>
				<tr>
	                <td class="td1"> 受控离线设备数：</td>
	                <td class="td2"> ${offlinePadCount }</td>
	            </tr>
	            
	            <%-- 当日已自行更换离线设备数 --%>
	            <tr>
	                <td class="td1"> 当日已自行更换离线设备数：</td>
	                <td class="td2"> ${renewalOfflinePadCount}</td>
	            </tr>
	            
	            <%-- 设备离线数量阈值 --%>
				<c:choose>
				    <c:when test="${hasPpermission}">
				    	<fns:getConfigTr code="config_pad_renewal_offline_count" number="true" dataOptions="min:0" />
				    </c:when>
				    <c:otherwise>
				    	<fns:getConfigTr code="config_pad_renewal_offline_count" number="true" readonly=""/>
				    </c:otherwise>
				</c:choose>
				
				 <%-- 设备离线更换时间 --%>
				<c:choose>
				    <c:when test="${hasPpermission}">
				    	<fns:getConfigTr code="config_pad_renewal_time" number="true" dataOptions="min:0"/>
				    </c:when>
				    <c:otherwise>
				    	<fns:getConfigTr code="config_pad_renewal_time" number="true" readonly=""/>
				    </c:otherwise>
				</c:choose>
				<tr>
	                <td class="td1"> 受控离线时长 *注：</td>
	                <td class="td2"> 单位: 秒</td>
	            </tr>
				
				<%-- 是否有修改的权限 --%>
				<c:if test="${hasPpermission}">
		        	<tr>
				        <td class="td1"></td>
			         	<td class="td2"> 
			         	<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-ok-rf" plain="false" onclick="submitForm()">保存</a>
			         	<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-undo" plain="false" onclick="resetForm()">重置</a>
			         	<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-reload-rf" plain="false" onclick="javascript:window.location.href=window.location.href;">刷新</a>
			         	</td>
			        </tr>
		        </c:if>
			</table>
		</form>
	</div>
</body>


</html>