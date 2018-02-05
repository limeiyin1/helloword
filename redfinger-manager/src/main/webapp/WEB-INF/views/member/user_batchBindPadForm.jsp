
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
			url : host + 'batchBindPad',
			success : callback
		});
		
		function countPads(message,phones) {
			var padlist;
			userMobilePhoneList = message.value.split("\n").length;
			phones.value = userMobilePhoneList;
		}
		
		function selectIdc(){
			$('#controlId').combobox('clear');
			var idcId = $('#idcId').combobox('getValue');
			if(null != idcId || "" != idcId){
				$.getJSON(host + 'getControlByIdcId?idcId='+idcId,function(json){
					$('#controlId').combobox({         
			            data:json.controlList, //此为重点
			            valueField:'controlId',
			            textField:'controlName',
			            multiple:false, //允许多选
			            editable:false//禁止编辑
			        });
			    })
			}
		}
	</script>
     <div id="module_submit_container">
	 <div id="module_search">
		<form id="module_submit_form" class="easyui-form" method="post">
		<table id="module_submit_table">
			<tr>
				<td class="td1">绑定设备类型:</td>
				<td class="td2">
					<select class="easyui-combobox input_width_short"  editable="false" name="padGrade" data-options="required:false">
					    <fns:getOptions category="rf_user_pad.pad_grade"  keys="rf_user_pad.pad_grade@vip,rf_user_pad.pad_grade@general,rf_user_pad.pad_grade@taste"></fns:getOptions>
					</select> 
				</td>
			</tr>
			<tr>
				<td class="td1">绑定时长单位:</td>
				<td class="td2">
					<select class="easyui-combobox input_width_short"  editable="false" name="timeType" data-options="required:false">
					    <fns:getOptions category="expire_time.time_type"  ></fns:getOptions>
				 	</select> 
				</td>
			</tr>
			<tr>
				<td class="td1">绑定时长:</td>
				<td class="td2"><input class="easyui-numberbox" type="text" name="bindTime"  data-options="validType:'length[0,4]'" /></td>
			</tr>
			
			<tr>
				<td class="td1">机房选择:</td>
				<td class="td2">
					<select class="easyui-combobox input_width_short"  editable="false" id="idcId" name="idcId" data-options="required:false,onSelect:selectIdc">
					    <option value="">请选择</option>
					    <c:forEach var="one" items="${idcList}">
							<option value="${one.idcId}">${one.idcName}</option>
						</c:forEach>
				 	</select> 
				</td>
			</tr>
			
			<tr>
				<td class="td1">控制节点选择:</td>
				<td class="td2">
					<select class="easyui-combobox input_width_short"  editable="false" id="controlId" name="controlId" data-options="required:false">
					    <option value="">请选择</option>
					    <%-- <c:forEach var="one" items="${controlList}">
							<option value="${one.controlId}">${one.controlName}</option>
						</c:forEach> --%>
				 	</select> 
				</td>
			</tr>
			
			<tr>
				<td class="td1">设备编码段:</td>
				<td class="td2">
					<input type="text" name="padCode" class="easyui-textbox input_width_default" />至
						<input type="text" name="padCode2" class="easyui-textbox input_width_default" />
				</td>
			</tr>
			
			
			<tr>
				<td class="td1">赠送账号:</td>
				<td class="td2"><fieldset>
							<legend>请输入内容</legend>
							<textarea name="userMobilePhones" wrap=PHYSICAL style="width: 220px;height: 200px" onKeyDown="countPads(this.form.userMobilePhones,this.form.phones);"
							onKeyUp="countPads(this.form.userMobilePhones,this.form.phones);"></textarea>
			                </fieldset>
			    </td>
			 </tr>
			 <tr>
			    <td class="td1">已输入行数：</td>
			    <td class="td2"><input disabled maxlength="4" name="phones" size="3" value="0" class="inputtext"></td>
			 </tr>
		</table>
	</div>
</body>
</html>


