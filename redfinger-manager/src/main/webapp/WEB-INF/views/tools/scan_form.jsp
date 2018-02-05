<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>应用市场扫描</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'save',   
		    success:callback
		});
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
	<input type="hidden" name="id" value="${bean.id}">
	<table id="module_submit_table">
		<tr>
			<td class="td1">名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="name" value="${bean.name }" data-options="required:true" /></td>
		</tr>
		<tr>
			<td class="td1">应用市场:</td>
			<td class="td2"><select id="sel" class="easyui-combobox"
				name="marketName" data-options="required:true">
				<fns:getOptions category="tools_market_update.market_name" value="${bean.marketName}"></fns:getOptions>
				</select> 			
			</td>
		</tr>
		<tr>
			<td class="td1">apk地址:</td>
			<td class="td2"><input class="easyui-textbox" name="apkUrl" value="${bean.apkUrl }" data-options="required:true" /></td>
		</tr>
		<tr>
			<td class="td1">应用市场页面地址:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="marketUrl" value="${bean.marketUrl }" data-options="required:true" /></td>
		</tr>
		<tr>
			<td class="td1">描述:</td>
			<td class="td2"><input class="easyui-textbox" name="remark2" value="${bean.remark2 }" data-options="multiline:true,validType:'length[0,200]'" style="height:60px" /></td>
		</tr>
	</table>
    </form>
    </div>
</body>
</html>



