<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>设备</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'stop',   
		    success:callback
		});
		 
	</script>
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post">
		  	<input type="hidden" name="padId" value="${bean.padId}">
			<table id="module_submit_table">
				<tr>
					<td class="td1">描述:</td>
					<td class="td2"><input class="easyui-textbox" name="remark" data-options="multiline:true,validType:'length[0,200]'" style="height:160px" /></td>
				</tr>
			</table>
	    </form>
    </div>
</body>
</html>



