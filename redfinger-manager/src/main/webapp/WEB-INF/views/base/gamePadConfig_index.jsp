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
				<fns:getConfigTr code="config_left_game_pad_count_hour" number="true"/>
				<fns:getConfigTr code="config_left_game_pad_count" number="true" readonly="true"/>
				<fns:getConfigTr code="config_gamePad_online_time" number="true"/>
				<fns:getConfigTr code="config_gamePad_share_times" number="true"/>
				<fns:getConfigTr code="config_gamePad_share_add_day" number="true"/>
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