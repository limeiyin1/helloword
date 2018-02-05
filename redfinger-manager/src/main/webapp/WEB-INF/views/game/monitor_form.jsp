<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>APP应用市场</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({url:host+'save',   success:callback});
		
		  $.extend($.fn.validatebox.defaults.rules, {
	            isBlank: {
	                validator: function (value, param) { return $.trim(value) != '' },
	                message: '不能为空，全空格也不行'
	            }
	        });
		
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
	<input type="hidden" name="cfgId" value="${bean.cfgId}">
	<table id="module_submit_table">
		<tr>
			<td class="td1">游戏名称</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="gameName" value="${bean.gameName }" data-options="required:true,validType:'length[0,250]'"  /></td>
		</tr>
		<tr>
			<td class="td1">游戏包名:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="packageName" value="${bean.packageName }"  data-options="required:true,validType:['isBlank']" /></td>
		</tr>
		<tr>
			<td class="td1">监听端口:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="monitorPort" value="${bean.monitorPort }"  data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		<tr>
			<td class="td1">数据包临界值</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="dataPacketLimit" value="${bean.dataPacketLimit }" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		<tr>
			<td class="td1">采集频率:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="gatherInterval" value="${bean.gatherInterval }" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		<tr>
		<tr>
			<td class="td1">脚本路径:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="gamePathPkg" value="${bean.gamePathPkg }" data-options="required:true,validType:'length[0,255]'" /></td>
		</tr>
		<tr>
		<tr>
			<td class="td1">启动游戏参数:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="gameFlagCmp" value="${bean.gameFlagCmp }" data-options="required:true,validType:'length[0,500]'" /></td>
		</tr>
		<tr>

	</table>
    </form>
    </div>
</body>
</html>



