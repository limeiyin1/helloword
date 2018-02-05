<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>

<html>
<head>
<title>计费配置</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	$(function(){
		$('.easyui-form').form({
		    url:host+'update',   
		    success:configCallback
		});
	});
</script>
</head>
<body>
	<div id="module_submit_container"  align="center">
		<form id="module_submit_form" class="easyui-form" method="post">
			<table class="easyui-table" >
			    <fns:getConfigTr code="config_fee_tariff30" number="true"/>
			    <fns:getConfigTr code="config_fee_tariff90" number="true"/>
				<fns:getConfigTr code="config_fee_tariff180" number="true"/>
				<fns:getConfigTr code="config_fee_tariff365" number="true"/>
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