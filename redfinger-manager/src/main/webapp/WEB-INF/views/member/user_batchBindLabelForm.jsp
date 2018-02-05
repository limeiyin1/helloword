<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>批量绑定标签</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'batchBindLabel',   
		    success:callback
		});
		function countPhones(message,phones) {
			var phonelist;
			phonelist= message.value.split("\n").length;
			phones.value=phonelist;
		}
		
		$(document).ready(function(){
			$('#labelId').combo({
	            required : true,
	            editable : true,
	            multiple : true
	        });
	        $('#labelIdsDiv').appendTo($('#labelId').combo('panel'));
		
		
			$("input[name='labelId']").click(function(){
	       		 var index = $(this).attr("index");
	       		 var name = $(this).attr("name");
	       		 
                 var _text = "";
                 var _value = "";
				 $("input[name='"+name+"']:checked").each(function() {
                      _value += (_value==""?"":" | ")+ $(this).val();
                      _text += (_text==""?"":" | ")+$(this).next("span").text();
                 });
                 //设置下拉选中值
                $("#labelId").combo('setValue', _value).combo('setText', _text);
                $("#labelIds").val(_value);
            });
		});
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
	<table id="module_submit_table">
		<tr>
			<td class="td1">用户标签:</td>
			<td class="td2">
				<input type="hidden" id="labelIds" name="labelIds"/>
				<select id="labelId" name="labelId" data-options="editable:true,width:200" style="width:200px;height:30px"></select>
                       　　 		<div id="labelIdsDiv" name="labelIdsDiv">
                      <c:forEach items="${list }" var="label">
                      	  <input type="checkbox" name="labelId" value="${label.labelId}">
                          <span>${label.labelName}</span>
                          <br />
                      </c:forEach>
                </div>
                
               <%--  <select id="labelId" name="labelId" data-options="editable:true,width:200" style="width:200px;height:30px"></select>                           　　
                <div id="labelIdsDiv">                                  
	                <c:forEach items="${list }" var="label">                                      
		                <input type="checkbox" name="labelName" value="${label.labelId}"><span>${label.labelName}</span>                                     
		                <br />                                  
	                </c:forEach>                           
                </div>  --%>
			</td>
		</tr>
		
		<tr>
			<td class="td1">账号类型：</td>
			<td class="td2">
				<input type="radio" name="idType" value="1" checked="checked">手机号</input>
				<input type="radio" name="idType" value="2">会员ID</input>
			</td>
		</tr>
		
	     <tr>
			<td class="td1">会员清单:</td>
			<td class="td2"><fieldset>
						<legend>请输入内容</legend>
						<textarea name="userPhone" wrap=PHYSICAL style="width: 220px;height: 200px" onKeyDown="countPhones(this.form.userPhone,this.form.phons);"
						onKeyUp="countPhones(this.form.userPhone,this.form.phons);"></textarea>
		                </fieldset>
		    </td>
		 </tr>
		 <tr>
		    <td class="td1">已输入行数：</td>
		    <td class="td2"><input disabled maxlength="4" name="phons" size="3" value="0" class="inputtext"></td>
		 </tr>
	</table>
    </form>
    </div>
</body>
</html>



