<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>分类</title>
<meta name="decorator" content="default" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'category',   
		    success:callback,
		}); 
		function showSpan(obj){
			var id="reorder_"+$(obj).val();
			if($(obj).is(':checked')){
				$("#"+id).show();
				$("#"+id).find("input").attr("disabled",false);
			}else{
				$("#"+id).hide();
				$("#"+id).find("input").attr("disabled",true);
			}
		}
		$(function(){
			$(".categoryApkId").each(function(){
				showSpan(this);
			});
		});
	</script>
	<div id="module_submit_container">
	<form id="easyui-form" class="easyui-form" method="post">
		<input type="hidden" name="id" value="${bean.id}"/>
		<c:forEach items="${categoryList}" var="one">
			<c:set value="" var="checked"/>
			<c:set value="0" var="reorder"></c:set>
			<c:forEach items="${categoryApkList}" var="subOne">
				<c:if test="${one.id==subOne.categoryId}">
					<c:set value="checked" var="checked"></c:set>
					<c:set value="${subOne.reorder}" var="reorder"></c:set>
				</c:if>
			</c:forEach>
			<label><input class="categoryApkId" name="categoryApkId" type="checkbox" value="${one.id}"  onclick="showSpan(this)" ${checked}> ${one.name}</label>
			<span id="reorder_${one.id}" style="display:inline;"><input name="categoryreorder" size="1" value="${reorder}" /></span><br/>
		</c:forEach>
    </form>
    </div>
</body>
</html>



