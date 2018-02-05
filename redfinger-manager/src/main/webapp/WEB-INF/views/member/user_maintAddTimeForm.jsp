<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>维护赠送天数</title>
<meta name="decorator" content="default" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({ 
			url : host + 'maintAddTime',
			success : callback
		});
		
		
	</script>
     <div id="module_submit_container">
	 <div id="module_search">
		<form id="module_submit_form" class="easyui-form" method="post">
		<table id="module_submit_table">
			<tr>
				<td class="td1">机房:</td>
				<td class="td2">
					<select class="easyui-combobox input_width_short"  editable="false" name="idcId" data-options="required:false">
					    <option value="">请选择</option>
					    <c:forEach items="${idcs }" var="idc">
					    	 <option value="${idc.idcId }">${idc.idcName }</option>
					    </c:forEach>
				 	</select> 
				</td>
			</tr>
			<tr>
				<td class="td1">单位:</td>
				<td class="td2">
					<select class="easyui-combobox input_width_short"  editable="false" name="timeType" data-options="required:false">
					    <fns:getOptions category="expire_time.time_type"  ></fns:getOptions>
				 	</select> 
				</td>
			</tr>
			<tr>
				<td class="td1">体验设备赠送数量:</td>
				<td class="td2"><input class="easyui-numberbox" type="text" name="tasteExpireTime"  data-options="validType:'length[0,4]'" /></td>
			</tr>
			<tr>
				<td class="td1">普通设备赠送数量:</td>
				<td class="td2"><input class="easyui-numberbox" type="text" name="normalExpireTime"  data-options="validType:'length[0,4]'" /></td>
			</tr>
			<tr>
				<td class="td1">vip设备赠送数量:</td>
				<td class="td2"><input class="easyui-numberbox" type="text" name="vipExpireTime"  data-options="validType:'length[0,4]'" /></td>
			</tr>
			<tr>
				<td class="td1">超级vip设备赠送数量:</td>
				<td class="td2"><input class="easyui-numberbox" type="text" name="svipExpireTime"  data-options="validType:'length[0,4]'" /></td>
			</tr>
			
		</table>
	</div>
</body>
</html>


