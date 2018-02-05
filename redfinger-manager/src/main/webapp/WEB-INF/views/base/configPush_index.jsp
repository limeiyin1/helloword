<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>

<html>
<head>
<title>推送配置</title>
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
				<fns:getConfigTr code="config_push_headline"/>
				<fns:getConfigTr code="config_push_details"/>
				  <tr>
	                <td class="td1">#设备编号#,<br>#时间# *注：</td>
	                <td class="td2">#设备编号# 需要填入参数是设备编号 <br>
	                                #时间# 需要填入参数是时间信息
	                </td>
	            </tr>
				<fns:getConfigTr code="config_push_interval"/>
				<tr>
	                <td class="td1"> 游戏离线提醒间隔时间 *注：</td>
	                <td class="td2"> 不填=只发送1次;5=发送2次;第2次间隔5分钟;<br>
	                     5,10=发送3次，第二次间隔5分钟，第三次间隔第二次10分钟;以此类推
	                </td>
	            </tr>
	        	<fns:getConfigTr code="config_push_details_package"/>
	        	<tr>
	                <td class="td1">#设备编号#,<br>#时间#，<br>#游戏名称# *注：</td>
	                <td class="td2">
	                #设备编号# 需要填入参数是设备编号 <br>
	                #时间# 需要填入参数是时间信息 <br>
	                #游戏名称# 表示命令中需要填入参数是游戏包名
	                </td>
	            </tr>
	        	<fns:getConfigTr code="config_push_headline_package"/>
	            <fns:getConfigTr code="config_push_on_off" number="true"/>
	            <tr>
	                <td class="td1">推送功能开关 *注：</td>
	                <td class="td2">
	                1    全部推送 <br>
	                2    只向VIP设备推送 <br>
	                0     关闭推送功能
	                </td>
	            </tr>
	            	<fns:getConfigTr code="config_push_pad_headline"/>
	            	<fns:getConfigTr code="config_push_pad_details"/>
				  <tr>
	                <td class="td1">#设备编号#,<br>#时间# *注：</td>
	                <td class="td2">#设备编号# 需要填入参数是设备编号 <br>
	                                #时间# 需要填入参数是时间信息
	                </td>
	            </tr>
	            <fns:getConfigTr code="config_push_pad_on_off"/>
	            <tr>
	                <td class="td1">推送功能开关 *注：</td>
	                <td class="td2">
	                1    全部推送 <br>
	                0     关闭推送功能
	                </td>
	            </tr>
		        <fns:getConfigTr code="config_expire_before_on_off"/>
		        <tr>
	                <td class="td1">到期推送功能开关 *注：</td>
	                <td class="td2">
	                1    全部推送 <br>
	                0     关闭推送功能
	                </td>
	            </tr>
	            <fns:getConfigTr code="config_expire_before"/>
		        <tr>
	                <td class="td1">设备到期前多久推送提醒消息 *注：</td>
	                <td class="td2">
	                                                                                        单位：天
	                </td>
	            </tr>
	           	 <fns:getConfigTr code="config_expire_title"/>
	             <fns:getConfigTr code="config_expire_desc"/>
				 <tr>
	                <td class="td1">#设备编号#,<br>#到期时间# *注：</td>
	                <td class="td2">#设备编号# 需要填入参数是设备编号 <br>
	                                #到期时间# 需要填入参数是时间信息
	                </td>
	            </tr>
	             <fns:getConfigTr code="config_expire_time"/>
	            <tr>
	                <td class="td1">设备到期提醒功能首次开启时间 *注：</td>
	                <td class="td2">
	                                                                     参考时间格式 ：2015-12-03 11:00:00
	                </td>
	            </tr>
	               <fns:getConfigTr code="config_pad_reconnect"/>
	            <tr>
	                <td class="td1">设备掉线重连开关 *注：</td>
	                <td class="td2">注： 1 全部重连 <br>2 只重连VIP设备 <br>0 关闭自动重连功能</td>
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