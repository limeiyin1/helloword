<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>批量添加红豆</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'batch', 
		    success:callback
		});
		function countCodes(message,codes) {
			var codelist;
			codelist= message.value.split("\n").length;
			codes.value=codelist;
			}
		
		var change=function(newValue,oldValue){
			$('#checkbox').attr("disabled",true).attr("checked",false);
			$('#beginTimeStr').datetimebox('disable');
			$('#endTimeStr').datetimebox("disable").datetimebox("clear");
			if(newValue==0){
				$('#checkbox').attr("disabled",false);
				$('#beginTimeStr').datetimebox('enable');
				$('#endTimeStr').datetimebox("enable");
			}
		}
		
		var timechange=function(newValue,oldValue){
			$('#num').numberbox("disable");
			if(newValue==0){
				$('#num').numberbox("enable");
			}
		}
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
	<table id="module_submit_table">
		<tr>
			<td class="td1">礼包名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="name" value="${bean.name}" data-options="required:true,validType:'length[0,32]'" /></td>
			<td><input type="hidden" name="id" value="${bean.id}"/></td>
		</tr>
	     <tr>
			<td class="td1">礼包码清单:</td>
			<td class="td2"><fieldset>
						<legend>请输入内容</legend>
						<textarea name="codes" wrap=PHYSICAL style="width: 220px;height: 200px" onKeyDown="countCodes(this.form.codes,this.form.phons);"
						onKeyUp="countCodes(this.form.codes,this.form.phons);"></textarea>
		                </fieldset>
		    </td>
		 </tr>
		 <tr>
		    <td class="td1">已输入行数：</td>
		    <td class="td2"><input disabled maxlength="4" name="phons" size="3" value="0" class="inputtext"></td>
		 </tr>
		 <tr>
			<td class="td1">开始时间:</td>
			<td class="td2"><input class="easyui-datetimebox input_width_default" type="text" id="beginTimeStr" name="beginTimeStr" data-options="onChange:timechange"/></td>
		</tr>
		<tr>
			<td class="td1">结束时间:</td>
			<td class="td2"><input class="easyui-datetimebox input_width_default" type="text" id="endTimeStr" name="endTimeStr"  /></td>
		</tr>
		
		<tr>
			<td class="td1">是否按天数平分:</td>
			<td class="td2"><input name="check" id="checkbox" type="checkbox" /></td>
		</tr>
		<tr>
			<td class="td1">按数量平分:</td>
			<td class="td2"><input class="easyui-numberbox" name="num" id="num" data-options="onChange:change" style="width:50px;"/>个/天</td>
		</tr>
	</table>
    </form>
    </div>
</body>
</html>



