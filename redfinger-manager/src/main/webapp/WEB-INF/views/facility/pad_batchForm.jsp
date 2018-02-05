<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>设备批量操作</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'batchPad',   
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
			<td class="td1">执行批量操作:</td>
			<td class="td2"><select class="easyui-combobox input_width_short"  editable="false" name="actionType" data-options="required:false">
						   <fns:getOptions category="rf_pad.enable_status" keys="rf_pad.enable_status@start,rf_pad.enable_status@forbidden" ></fns:getOptions>
					 	    </select>
		     </td>
		</tr>
	     <tr>
			<td class="td1">需要替换的设备:</td>
			<td class="td2"><fieldset>
						<legend>请输入内容</legend>
						<textarea name="padIp" wrap=PHYSICAL style="width: 220px;height: 200px" onKeyDown="countPads(this.form.padIp,this.form.pads);"
						onKeyUp="countPads(this.form.padIp,this.form.pads);"></textarea>
		                </fieldset>
		    </td>
		 </tr>
		 <tr>
			<td class="td1">描述:</td>
			<td class="td2">
			<textarea name="remark" wrap=PHYSICAL style="width: 220px;height: 200px" onKeyDown="countPads(this.form.padIp,this.form.pads);"
						onKeyUp="countPads(this.form.padIp,this.form.pads);"></textarea></td>
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



