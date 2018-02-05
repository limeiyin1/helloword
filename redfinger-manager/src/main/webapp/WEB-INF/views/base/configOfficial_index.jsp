<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>

<html>
<head>
<title>官网配置</title>
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
				<fns:getConfigTr code="config_website_on_off" number="true"/>
				 <tr>
	               <td class="td1">#公告开关# *注：</td>
	               <td class="td2">
	                1     公告开启 <br>
	                0     公告关闭
	                </td>
	            </tr>
				<fns:getConfigTr code="config_website_title"/>
				<fns:getConfigTr code="config_website_details" textarea="true"/>
				 <tr>
	                <td class="td1"> 公告内容 *注：</td>
	                <td class="td2"> 请在需要换行时输入&lt;br/&gt;<br>
	                     	输入字数请小于500个字符
	                </td>
	            </tr>
				<fns:getConfigTr code="config_sms_minute"  number="true"/>
				<tr>
	                <td class="td1"> 每分钟短信数 *注：</td>
	                 <td class="td2">
	                                                                                        单位：分钟/条
	                </td>
	            </tr>       
	        	<fns:getConfigTr code="config_sms_hour"  number="true"/>
				<tr>
	                <td class="td1"> 每小时短信数 *注：</td>
	                 <td class="td2">
	                                                                                        单位：小时/条
	                </td>
	            </tr>
	            <fns:getConfigTr code="config_sms_day"  number="true"/>
				<tr>
	                <td class="td1"> 每天短信数 *注：</td>
	                 <td class="td2">
	                                                                                        单位：天/条
	                </td>
	            </tr>
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