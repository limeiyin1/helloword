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
		    url:host+'bindLabel',   
		    success:callback
		});
		
		
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
                      _value += (_value == "" ? "" : " | ")+ $(this).val();
                      _text += (_text == ""? "" : " | ")+$(this).next("span").text();
                 });
                 //设置下拉选中值
                $("#labelId").combo('setValue', _value).combo('setText', _text);
                $("#labelIds").val(_value);
            });
            
            
             $("input[name='labelId']").each(function(){
	       		 var index = $(this).attr("index");
	       		 var name = $(this).attr("name");
	       		 
                 var _text = "";
                 var _value = "";
				 $("input[name='"+name+"']:checked").each(function() {
                      _value += (_value == "" ? "" : " | ")+ $(this).val();
                      _text += (_text== "" ? "" : " | ")+$(this).next("span").text();
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
		<input type="hidden" name="padId" value="${bean.padId }">
		<tr>
			<td class="td1">设备名称:</td>
			<td class="td2"><input type="hidden" name="padName" value="${bean.padName }">${bean.padName }</td>
		</tr>
		<tr>
			<td class="td1">设备编码:</td>
			<td class="td2"><input type="hidden" name="padCode" value="${bean.padCode }">${bean.padCode }</td>
		</tr>
		<tr>
			<td class="td1">用户标签:</td>
			<td class="td2">
				<input type="hidden" id="labelIds" name="labelIds"/>
				<select id="labelId" name="labelId" data-options="editable:true,width:200" style="width:200px;height:30px"></select>
                       　　 		<div id="labelIdsDiv" name="labelIdsDiv">
                      <c:forEach items="${list }" var="label">
                      	  <c:set var="padLabelKey" value="${label.labelId}_${bean.padId }"></c:set>
                      	  <input type="checkbox" name="labelId" value="${label.labelId}" <c:if test="${label.labelId == padLabelMap[padLabelKey].labelId}">checked="checked"</c:if>>
                          <span>${label.labelName}</span>
                          <br />
                      </c:forEach>
                </div>
			</td>
		</tr>
	    
	</table>
    </form>
    </div>
</body>
</html>



