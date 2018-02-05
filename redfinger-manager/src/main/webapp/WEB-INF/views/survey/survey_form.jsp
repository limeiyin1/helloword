<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>产品</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'save',   
		    success:callback
		});
		function addPro(surveyId,type){
			var  myselect = document.getElementById(type);
			var index = myselect.selectedIndex ;            
			var problemId = myselect.options[index].value; 
			 $.ajax({ 
			     url:host+"addProblem?problemId="+problemId+"&surveyId="+surveyId, 
			     success:callback,
			    }); 
		}
		function delPro(surveyId,problemId){
			 $.ajax({ 
				url:host+"delProblem?problemId="+problemId+"&surveyId="+surveyId, 
			 	success:callback,
			}); 
		}
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
     <input type="hidden" name="surveyId" value="${bean.surveyId}">
	<table id="module_submit_table">
		<tr>
			<td class="td1">问卷名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="surveyName" value="${bean.surveyName }" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		
		<tr>
			<td class="td1">问卷简介:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="surveyContent" value="${bean.surveyContent}" data-options="multiline:true,validType:'length[0,500]'" style="height:60px" /></td>
		</tr>
		<tr>
		<td class="td1">排序:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="reorder" value="${bean.reorder }" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		<tr>
			<td class="td1">备注:</td>
			<td class="td2"><input class="easyui-textbox" name="remark" value="${bean.remark }" data-options="multiline:true,validType:'length[0,500]'" style="height:60px" /></td>
		</tr>
		<tr>
    	<c:if test="${not empty radio}">
		<td class="td1">添加单选题:</td>
			<td class="td2">
			
				<select  name="radio" id ="radio" style="width:260px">
				
				<c:forEach items="${radio}" var="radio" >
					<option value="${radio.problemId}">${radio.problemContent}</option>
				</c:forEach>	
			</select>

					<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton" iconCls="icon-ok-rf" onclick="addPro(${bean.surveyId},'radio')">添加</a>
			</td>
		</c:if>
		</tr>
		<tr>
		<c:if test="${not empty check}">
			<td class="td1">添加多选题:</td>
			<td class="td2">
				<select  name="check" id ="check" style="width:260px">
				<c:forEach items="${check}" var="check" >
					<option value="${check.problemId}">${check.problemContent}</option>
				</c:forEach>	
			</select>
					<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton" iconCls="icon-ok-rf" onclick="addPro(${bean.surveyId},'check')">添加</a>
			</td>
		</c:if>
		</tr>
		<tr>
		<c:if test="${not empty blank}">
			<td class="td1">添加问答题:</td>
			<td class="td2">
				<select  name="blank" id ="blank" style="width:260px" >
				<c:forEach items="${blank}" var="blank">
					<option value="${blank.problemId}">${blank.problemContent}</option>
				</c:forEach>	
			</select>
					<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton" iconCls="icon-ok-rf" onclick="addPro(${bean.surveyId},'blank')">添加</a>
			</td>
		</c:if>
		</tr>
	</table>
    </form>
    </div>
 
    <div>
		<c:forEach items="${bean.list_problemVO}" var="one" varStatus="status">
		<span><B>第 （${status.index+1}） 题 : </B> ${one.problemContent } ( id= ${one.problemId })</span><a href="javascript:void(0)" id="button-cancel" class="easyui-linkbutton" iconCls="icon-no" onclick="delPro(${bean.surveyId},${one.problemId })"></a>
		<ul type="upper-alpha">
		<c:forEach items="${one.list_rfProblemAnswer}" var="two" varStatus="status2" >
		<li >
			<span>${two.answerContent} ( id= ${two.answerId })</span>
		</li>
		</c:forEach>
		</ul>
		
		</c:forEach>	
	</div>
</body>
</html>



