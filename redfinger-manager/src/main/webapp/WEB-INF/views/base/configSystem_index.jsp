<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>

<html>
<head>
<title>系统配置</title>
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
				<fns:getConfigTr code="config_role_kefu"/>
				<fns:getConfigTr code="config_role_yunwei"/>
				<fns:getConfigTr code="config_role_ceshi"/>
				<fns:getConfigTr code="config_role_zixun" />
				<fns:getConfigTr code="config_fault_client_creater"/>
				<fns:getConfigTr code="config_pictureDownloadLocal"/>
				<fns:getConfigTr code="config_pictureDownloadLocal_sz"/>
				<fns:getConfigTr code="config_begin_time"/>
				<fns:getConfigTr code="config_end_time"/>
				<%-- <fns:getConfigTr code="config_broadcast_content_length"/>
				<fns:getConfigTr code="config_broadcast_page_size"/> --%>
			<%-- 	<fns:getConfigTr code="config_rbc_start_time"/>
				 <tr>
	                <td class="td1">红豆赠送活动开启时间 *注：</td>
	                <td class="td2">
	                                                                     参考时间格式 ：2015-12-03 11:00:00
	                </td>
	            </tr>
				<fns:getConfigTr code="config_rbc_end_time"/>
				<tr>
	                <td class="td1">红豆赠送活动结束时间 *注：</td>
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