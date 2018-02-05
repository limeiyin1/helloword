<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>设备</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
	
		
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
  
	<table id="module_submit_table">
		<tr>
			<td class="td1">公告标题:</td>
			<td class="td2">${bean.noticeTitle }</td>
		</tr>
		<tr>
			<td class="td1">创建人:</td>
			<td class="td2">${bean.creater }</td>
		</tr>
		<tr>
		    <td class="td1">创建时间:</td>
			<td class="td2" ><fmt:formatDate value="${bean.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr>
			<td class="td1">弹出状态:</td>
			<td class="td2">${fns:getLabelStyle('rf_notice.pop_status',bean.popStatus)}</td>
		</tr>
		<tr>
			<td class="td1">公告内容:</td>
			<td class="td2">${bean.noticeContent }</td>
		</tr>
	</table>
    </form>
    </div>
</body>
</html>



