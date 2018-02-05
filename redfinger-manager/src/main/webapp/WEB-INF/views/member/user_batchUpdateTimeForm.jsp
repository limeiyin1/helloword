<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>批量新增天数</title>
<meta name="decorator" content="default" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({ 
			url : host + 'batchUpdateTime',
			success : callback
		});
		
		function countPads(message,pads) {
			var padlist;
			padlist= message.value.split("\n").length;
			pads.value=padlist;
		}
		
	</script>
     <div id="module_submit_container">
	 <div id="module_search">
		<form id="module_submit_form" class="easyui-form" method="post">
		<table id="module_submit_table">
			<tr>
				<td class="td1">单位:</td>
				<td class="td2">
					<select class="easyui-combobox input_width_short"  editable="false" name="timeType" data-options="required:false">
					    <fns:getOptions category="expire_time.time_type"  ></fns:getOptions>
				 	</select> 
				</td>
			</tr>
			<tr>
				<td class="td1">赠送数量:</td>
				<td class="td2"><input class="easyui-numberbox" type="text" name="expireTime"  data-options="required:true,validType:'length[0,4]'" /></td>
			</tr>
			
			<tr>
				<td class="td1">设备编码:</td>
				<td class="td2"><fieldset>
							<legend>请输入内容</legend>
							<textarea name="padCodes" wrap=PHYSICAL style="width: 220px;height: 200px" onKeyDown="countPads(this.form.padCodes,this.form.pads);"
							onKeyUp="countPads(this.form.padCodes,this.form.pads);"></textarea>
			                </fieldset>
			    </td>
			 </tr>
			 <tr>
			    <td class="td1">已输入行数：</td>
			    <td class="td2"><input disabled maxlength="4" name="pads" size="3" value="0" class="inputtext" style="width:20px;"></td>
			 </tr>
		</table>
	</div>
</body>
</html>


