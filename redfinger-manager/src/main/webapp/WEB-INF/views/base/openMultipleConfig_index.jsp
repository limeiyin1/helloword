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
	<div class="easyui-panel" title="多开客服配置" style="width:600px" align="center">
		<form id="module_submit_form" class="easyui-form" method="post">
			<table class="easyui-table" >
				<tr>
				 <td class="td1">${tip.configLabel}:</td>
		         	<td class="td2">
		         	<textarea name="map['${tip.configCode}']" rows="5" cols="38">${tip.configValue}</textarea>
		         	</td>
				</tr>
				
				<tr>
				 <td class="td1">${url.configLabel}:</td>
		         	<td class="td2">
		         	<textarea name="map['${url.configCode}']" rows="5" cols="38">${url.configValue}</textarea>
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
	</div>
</body>


</html>