<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>

<html>
<head>
<title>渠道配置</title>
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
				<fns:getConfigTr code="config_channel_ransom" number="true"/>
				<fns:getConfigTr code="config_channel_common" number="true"/>
				<fns:getConfigTr code="config_channel_max_pad_count" number="true"/>
				<fns:getConfigTr code="config_channel_bind_pad_count" number="true" readonly="true"/>
				<fns:getConfigTr code="config_pad_expired_reminded" number="true"/>
				<%-- <fns:getConfigTr code="config_check_ios_user_on_off"/>
	            <tr>
	                <td class="td1">校验ios用户登录的开关 *注：</td>
	                <td class="td2">
	                1    开 <br>
	                0     关
	                </td>
	            </tr>
	            <fns:getConfigTr code="config_check_ios_user_time"/>
	            <tr>
	                <td class="td1">校验ios用户登录的截止时间 *注：</td>
	                <td class="td2">
	                                                                     参考时间格式 ：2015-12-03 11:00:00
	                </td>
	            </tr> --%>
	        	<tr>
			        <td class="td1"></td>
		         	<td class="td2"> 
		         	<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-ok-rf" plain="false" onclick="submitForm()">保存</a>
		         	<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-undo" plain="false" onclick="resetForm()">重置</a>
		         	</td>
		        </tr>
			</table>
		</form>
	</div>
</body>


</html>