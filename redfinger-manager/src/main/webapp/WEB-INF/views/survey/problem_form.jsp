<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>问题管理</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'save',   
		    success:callback

		});
		function openAdd(){
			$("#add_submit_form").css("display", ""); 
		}
		function closeAdd() {
			$("#add_submit_form").css("display", "none"); 
		}
		function delAnswer(id){
			 $.ajax({ 
			     url:host+"delAnswer?answerId="+id, 
			     success:callback,
			    }); 
		}
		 $('#sub').click(function(){
             $.ajax({
            	 url:host+"addAnswer",
            	 data:$(".easyui-form-add").serialize(),
            	 type:"post",
            	 success:callback,
             });     
		 });
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
     <input type="hidden" name="problemId" value="${bean.problemId}">
	<table id="module_submit_table">
		<tr>
			<td class="td1">问题题目:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="problemContent" value="${bean.problemContent }" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		<tr><td class="td1">问题类型:</td><td class="td2">
		<select class="easyui-combobox input_width_default"  name="problemType">
					<option value="1" <c:if test="${1==bean.problemType}">selected="selected"</c:if>>单选题</option>
					<option value="2" <c:if test="${2==bean.problemType}">selected="selected"</c:if>>多选题</option>
					<option value="-1" <c:if test="${-1==bean.problemType}">selected="selected"</c:if>>问答题</option>
		</select></td>
		</tr>
		<tr>
			<td class="td1">是否必答:</td>
			<td class="td2">
					<select class="easyui-combobox input_width_default"  name="isMust">
					<option value="-1" <c:if test="${-1==bean.isMust}">selected="selected"</c:if>>选答题</option>
					<option value="1" <c:if test="${1==bean.isMust}">selected="selected"</c:if>>* 必答题</option>
		</select>
		</td>
		</tr>
		<tr>
			<td class="td1">排序:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="reorder" value="${bean.reorder }" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		<c:if test="${not empty bean.problemId}">
			<tr><td>答案</td><td><input type="button"   value="新增答案" onclick="openAdd()"></td></tr>
		</c:if>
		<c:forEach items="${bean.list_rfProblemAnswer}" var="one" varStatus="status">
		<tr>
		<td >答案：<input type="hidden" name="list_rfProblemAnswer[${status.index}].answerId" value="${one.answerId}"></td>
		<td>
		<input class="easyui-textbox" type="text" name="list_rfProblemAnswer[${status.index}].answerContent" value="${one.answerContent}" data-options="required:true,validType:'length[0,32]'" />
		<input type="button"  value="-" onclick="delAnswer(${one.answerId})">
		</td>
		</tr>
		</c:forEach>
	</table>
    </form>
    </div>
    <!-- 添加问题答案DIV -->
    <div id = "add_submit_form" style="display: none">
    <form id="module_submit_form" class="easyui-form-add" method="post">
     	<input type="hidden" name="problemId" value="${bean.problemId}">
		<table id="add_submit_table" >
		<tr>
			<td class="td1">新增答案：</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="list_rfProblemAnswer[0].answerContent"  data-options="required:true,validType:'length[0,32]'" />
			</td>
		</tr>
		<tr>
			<td></td><td><input type="button" id ="sub" value="保存答案"/>
			<input type="button" onclick="closeAdd()" value="取消" /> </td>
		</tr>
		</table>
    </form>
    </div>
</body>
</html>



