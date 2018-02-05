<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>渠道管理</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script>
	<script type="text/javascript">
		$('.easyui-form').form({url:host+'save',   success:callback});
		
		
		/////////////功能添加分割线//////////////////////////
		var getSecondGradeIdsCallback=function (json,fun){
			var data;//var data = new Array();
			data = $.parseJSON(json);
			if(data.length>0){
			$("#secondGradeId").combobox('loadData',data);//重新加载数据
			$("#secondGradeId").combobox('select', data[0].gradeId);//选择第一个
			}else{
				$("#secondGradeId").combobox('loadData',[]);
				$("#secondGradeId").combobox('setValue',null);
			}
			
		}
		var callback1=getSecondGradeIdsCallback;
 		var getSecondGradeIds = function (newValue, oldValue){
 		 	var firstGradeId = newValue;
			$.get("${ctx}/market/channel/getSecondGradeIds",{firstGradeId:firstGradeId},callback1);
		}
		
		/////////////功能添加分割线//////////////////////////
		
		
		
		
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
	<input type="hidden" name="channelId" value="${bean.channelId}">
	<table id="module_submit_table">
		<tr>
			<td class="td1">渠道编码:</td>
			<td class="td2">
				<c:if test="${not empty bean.channelCode }"><input type="hidden" name="channelCode" value="${bean.channelCode }">${bean.channelCode }</c:if>
				<c:if test="${empty bean.channelCode }"><input class="easyui-textbox" type="text" name="channelCode"  data-options="required:true,validType:'length[0,500]'" /></c:if>
			</td>
		</tr>	
	
	
		<tr>
			<td class="td1">渠道名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="channelName" value="${bean.channelName }" data-options="required:true,validType:'length[0,32]'"  /></td>
		</tr>
		
		
		<tr>
			<td class="td1">一级渠道:</td>
			<td class="td2" checkbox="true">
				<select class="easyui-combobox input_width_short" editable="false" id=firstGradeId name="firstGradeId" validType="selectValueRequired['#versionMust']" data-options="required:true,onChange:getSecondGradeIds">
				   <option value=""></option>
				   <c:forEach items="${firstGradeIds}" var="one">
				   	   <option  value="${one.gradeId }" <c:if test="${one.gradeId==bean.firstGradeId}">selected="selected"</c:if>>${one.gradeName }</option>
				   </c:forEach>
			 	</select>
			</td>
		</tr>
		
		<tr>
			<td class="td1">二级渠道:</td>
			<td class="td2" checkbox="true">
				<select class="easyui-combobox input_width_short" editable="false" id=secondGradeId name="secondGradeId" validType="selectValueRequired['#versionMust']" 
				data-options="required:true,valueField: 'gradeId',textField: 'gradeName'">
				   <c:forEach items="${secondGradeIds}" var="one">
				   	   <option  value="${one.gradeId }" <c:if test="${one.gradeId==bean.secondGradeId}">selected="selected"</c:if>>${one.gradeName }</option>
				   </c:forEach>
			 	</select>
			</td>
		</tr>
		
		
		<tr>
			<td class="td1">客户端类型:</td>
			<td class="td2" checkbox="true">
				<select class="easyui-combobox input_width_short" editable="false" id=clientType name="clientType" validType="selectValueRequired['#versionMust']">
				   	<option value="android" <c:if test="${'android'==bean.clientType}">selected="selected"</c:if>>android</option>
				  	<option value="win" <c:if test="${'win'==bean.clientType}">selected="selected"</c:if>>win</option>
				  	<option value="ios" <c:if test="${'ios'==bean.clientType}">selected="selected"</c:if>>ios</option>
				  	<option value="win_st" <c:if test="${'win_st'==bean.clientType}">selected="selected"</c:if>>win_st</option>
			 	</select>
			</td>
		</tr>
		
		<tr>
			<td class="td1">是否维护:</td>
			<td class="td2" checkbox="true">
				<select class="easyui-combobox input_width_short" editable="false" id=maintStatus name="maintStatus" validType="selectValueRequired['#versionMust']">
				   <c:forEach items="${maintOrMainCancel}" var="one">
				   	   <option  value="${one.key }" <c:if test="${one.key==bean.maintStatus}">selected="selected"</c:if>>${one.value }</option>
				   </c:forEach>
			 	</select>
			</td>
		</tr>
		
		<tr>
			<td class="td1">发现栏默认显示:</td>
			<td class="td2" checkbox="true">
				<select class="easyui-combobox input_width_short" editable="false" id="discoverLimit" name="discoverLimit" validType="selectValueRequired['#versionMust']">
				  <fns:getOptions category="global.yes_no" value="${bean.discoverLimit}"></fns:getOptions>
			 	</select>
			</td>
		</tr>
		
		<tr>
			<td class="td1">任务栏默认显示:</td>
			<td class="td2" checkbox="true">
				<select class="easyui-combobox input_width_short" editable="false" id="taskLimit" name="taskLimit" validType="selectValueRequired['#versionMust']">
				  <fns:getOptions category="global.yes_no" value="${bean.taskLimit}"></fns:getOptions>
			 	</select>
			</td>
		</tr>
		
		<tr>
			<td class="td1">渠道类型:</td>
			<td class="td2" checkbox="true">
				<select class="easyui-combobox input_width_short" editable="false"  name="channelType" validType="selectValueRequired['#versionMust']">
				  <fns:getOptions category="rf_channel_type" value="${bean.channelType}"></fns:getOptions>
			 	</select>
			</td>
		</tr>
		
		
		<tr>
			<td class="td1">备注:</td>
			<td class="td2">
			<%-- <input class="easyui-textbox" type="text" name="remark" value="${bean.remark }" data-options="validType:'length[0,100]'" /> --%>
			<textarea rows="13" cols="38" maxlength="500" name="remark">${bean.remark }</textarea>
			</td>
		</tr>
		
	</table>
    </form>
    </div>
</body>
</html>



