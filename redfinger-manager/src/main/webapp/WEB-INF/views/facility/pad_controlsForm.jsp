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
		    url:host+'controls',   
		    success:callback
		});
		function countPads(message,pads) {
			var padlist;
			padlist= message.value.split("\n").length;
			pads.value=padlist;
			}
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
    
	<table id="module_submit_table">
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
	     <tr>
			<td class="td1">设备清单:</td>
			<td class="td2"><fieldset>
						<legend>请输入内容</legend>
						<textarea name="padCodes" wrap=PHYSICAL style="width: 220px;height: 200px" onKeyDown="countPads(this.form.padCodes,this.form.pads);"
						onKeyUp="countPads(this.form.padCodes,this.form.pads);"></textarea>
		                </fieldset>
		    </td>
		 </tr>
		 <tr>
		    <td class="td1">已输入行数：</td>
		    <td class="td2"><input disabled maxlength="4" name="pads" size="3" value="0" class="inputtext"></td>
		 </tr>
	</table>
    </form>
    </div>
</body>
</html>



