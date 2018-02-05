<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>修改控制时间</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'controltime',   
		    success:callback
		});
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
     <input type="hidden" name="userPadId" value="${bean.userPadIdT2}">
	<table id="module_submit_table">
		<tr>
			<td class="td1">会员手机:</td>
			<td class="td2">${bean.map.userPhone }</td>
		</tr>	
		<tr>
			<td class="td1">会员邮箱:</td>
			<td class="td2">${bean.map.userEmail}</td>
		</tr>	
		<tr>
			<td class="td1">设备编码:</td>
			<td class="td2">${bean.padCode}</td>
		</tr> 
		<tr>
			<td class="td1">设备等级:</td>
			<td class="td2">${fns:getLabelStyle('rf_user_pad.pad_grade',bean.padGradeT2)}</td>
		</tr>
		<tr>
			<td class="td1">到期时间:</td>
			<td class="td2"><fmt:formatDate value="${bean.expireTimeT2}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr>
			<td class="td1">剩余在线天数:</td>
			<td class="td2">${controltime}</td>
		</tr>
		<tr>
			<td class="td1">时间类型:</td>
			<td class="td2">
				<select class="easyui-combobox input_width_short"  editable="false" name="timeType" data-options="required:false">
					<option value="">[全部]</option>
				    <fns:getOptions category="time.time_type"  ></fns:getOptions>
			 	</select> 
			</td>
		</tr>
		<tr>
			<td class="td1">调整在线时间:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="controltime"  data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
	
	</table>
    </form>
    </div>
</body>
</html>



