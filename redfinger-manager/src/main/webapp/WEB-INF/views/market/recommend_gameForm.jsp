<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<html lang="zh-cn">
<head>
<title>游戏</title>
<meta name="decorator" content="default" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'apk',   
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
			$(".appApkId").each(function(){
				showSpan(this);
			});
		});
	</script>
	
	<div id="module_submit_container">
	<form id="easyui-form" class="easyui-form" method="post">
	<div id="tt" class="easyui-tabs">  
	<input type="hidden" name="id" value="${bean.id}"/>
	<div title="  A  ~  E  ">
		<div style="padding: 10px;">
		<c:forEach items="${appApkList}" var="one">
		
			<c:if test="${(fn:substring(one.pinyin,0,1)>='A'  && fn:substring(one.pinyin,0,1)<='E') || (fn:substring(one.pinyin,0,1)>='a'  && fn:substring(one.pinyin,0,1)<='e')}">
				<c:set value="" var="checked"/>
				<c:set value="0" var="reorder"></c:set>
				<c:forEach items="${appRecommendApkList}" var="subOne">
					<c:if test="${one.id==subOne.apkId}">
						<c:set value="checked" var="checked"></c:set>
						<c:set value="${subOne.reorder==null ? '0':subOne.reorder}" var="reorder"></c:set>
					</c:if> 

				</c:forEach>
				
				<label><input class="appApkId" name="appApkId" type="checkbox" value="${one.id}" ${checked} onclick="showSpan(this)"> ${one.name} </label> 
				<span id="reorder_${one.id}" style="display:inline;"><input  name="recommendreorder" size="1" value="${reorder}" /></span>
				<br/>
			
			</c:if>
			
		</c:forEach>
		</div>
	</div>
		<div title=" F ~ J ">
			<div style="padding: 10px;">
				<c:forEach items="${appApkList}" var="one">
					<c:if test="${(fn:substring(one.pinyin,0,1)>='F'  && fn:substring(one.pinyin,0,1)<='J') || (fn:substring(one.pinyin,0,1)>='f'  && fn:substring(one.pinyin,0,1)<='j')}">
						<c:set value="" var="checked"/>
						<c:set value="0" var="reorder"></c:set>
						<c:forEach items="${appRecommendApkList}" var="subOne">
							<c:if test="${one.id==subOne.apkId}">
								<c:set value="checked" var="checked"></c:set>
								<c:set value="${subOne.reorder==null ? '0':subOne.reorder}" var="reorder"></c:set>
							</c:if> 
						</c:forEach>
						<label><input class="appApkId" name="appApkId" type="checkbox" value="${one.id}" ${checked} onclick="showSpan(this)"> ${one.name}</label>
						<span id="reorder_${one.id}" style="display:inline;"><input  name="recommendreorder" size="1" value="${reorder}" /></span>
						<br/>
					</c:if>
					
				</c:forEach>
			</div>
		</div>
		<div title=" K ~ O ">
			<div style="padding: 10px;">
				<c:forEach items="${appApkList}" var="one">
					<c:if test="${(fn:substring(one.pinyin,0,1)>='K'  && fn:substring(one.pinyin,0,1)<='O') || (fn:substring(one.pinyin,0,1)>='k'  && fn:substring(one.pinyin,0,1)<='o')}">
						<c:set value="" var="checked"/>
						<c:set value="0" var="reorder"></c:set>
						<c:forEach items="${appRecommendApkList}" var="subOne">
							<c:if test="${one.id==subOne.apkId}">
								<c:set value="checked" var="checked"></c:set>
								<c:set value="${subOne.reorder==null ? '0':subOne.reorder}" var="reorder"></c:set>
							</c:if> 
						</c:forEach>
						<label><input class="appApkId" name="appApkId" type="checkbox" value="${one.id}" ${checked} onclick="showSpan(this)"> ${one.name} </label> 
				<span id="reorder_${one.id}" style="display:inline;"><input  name="recommendreorder" size="1" value="${reorder}" /></span>
						<br/>
					</c:if>
				</c:forEach>
			</div>
		</div>
		<div title=" P ~ T ">
			<div style="padding: 10px;">
			<c:forEach items="${appApkList}" var="one">
				<c:if test="${(fn:substring(one.pinyin,0,1)>='P'  && fn:substring(one.pinyin,0,1)<='T') || (fn:substring(one.pinyin,0,1)>='p'  && fn:substring(one.pinyin,0,1)<='t')}">
					<c:set value="" var="checked"/>
					<c:set value="0" var="reorder"></c:set>
					<c:forEach items="${appRecommendApkList}" var="subOne">
						<c:if test="${one.id==subOne.apkId}">
							<c:set value="checked" var="checked"></c:set>
							<% %>
							<c:set value="${subOne.reorder==null ? '0':subOne.reorder}" var="reorder"></c:set>
						</c:if> 
					</c:forEach>
					<label><input class="appApkId" name="appApkId" type="checkbox" value="${one.id}" ${checked} onclick="showSpan(this)"> ${one.name} </label> 
				<span id="reorder_${one.id}" style="display:inline;"><input  name="recommendreorder" size="1" value="${reorder}" /></span>
					<br/>
				</c:if>
			</c:forEach>
			</div>
		</div>
		<div title="U~Z">
			<div style="padding: 10px;">
				<c:forEach items="${appApkList}" var="one">
					<c:if test="${(fn:substring(one.pinyin,0,1)>='U'  && fn:substring(one.pinyin,0,1)<='Z') || (fn:substring(one.pinyin,0,1)>='u'  && fn:substring(one.pinyin,0,1)<='z')}">
						<c:set value="" var="checked"/>
						<c:set value="0" var="reorder"></c:set>
						<c:forEach items="${appRecommendApkList}" var="subOne">
							<c:if test="${one.id==subOne.apkId}">
								<c:set value="checked" var="checked"></c:set>
								<c:set value="${subOne.reorder==null ? '0':subOne.reorder}" var="reorder"></c:set>
							</c:if> 
						</c:forEach>
						<label><input class="appApkId" name="appApkId" type="checkbox" value="${one.id}" ${checked} onclick="showSpan(this)"> ${one.name} </label> 
				<span id="reorder_${one.id}" style="display:inline;"><input  name="recommendreorder" size="1" value="${reorder}" /></span>
						<br/>
					</c:if>
				</c:forEach>
			</div>
		</div>
   </div>
    </form>
    </div>
</body>
</html>



