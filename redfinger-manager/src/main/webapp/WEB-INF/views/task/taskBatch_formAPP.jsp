<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>修改在线时间</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'${bean.remark}',   
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
	<input type="hidden" name="remark" value="${bean.remark}">
	<table id="module_submit_table">
		<tr>
			<td class="td1">批次名：</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="name"  data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		<tr>
			<td class="td1">操作名：</td>
			<td class="td2"><fns:getOptions category=""></fns:getOptions>
			${fns:getLabel('rf_padtask.batch',bean.remark)}
            </td>
		</tr>
		<tr>
			<td class="td1">游戏名：</td>
			<td class="td2">
			    <select class="easyui-combobox"  editable="false" name="gameId" data-options="required:true">
			     <option value="com.redfinger.gamemanage">启动器</option>
						<c:forEach items="${gameList}" var="one">
						   <option value="${one.gameId }">${one.gameName }</option>
					    </c:forEach>
				</select> 
			</td>
		</tr>
	    <tr>
			<td class="td1">设备清单:</td>
			<td class="td2"><fieldset>
						<legend>请输入内容</legend>
						<textarea   name="padCodes" wrap=PHYSICAL style="width: 220px;height: 200px" onKeyDown="countPads(this.form.padCodes,this.form.pads);"
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



