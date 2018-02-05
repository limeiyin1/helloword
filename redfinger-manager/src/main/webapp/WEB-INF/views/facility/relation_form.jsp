<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>节点控制关系</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'save',   
		    success:callback
		});
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
	<input type="hidden" name="relationId" value="${bean.relationId}">
	<table id="module_submit_table">
	
	<%-- <c:choose>//关系编号暂时去掉
		<c:when test="${bean.relationId!=null}">
		<tr>
				<td class="td1">关系编号:</td>
				<td class="td2">
					${bean.relationId}
				</td>
			</tr>
		</c:when>
	</c:choose> --%>
		
		<tr>
				<td class="td1">控制节点:</td>
				<td class="td2">
					<select class="easyui-combobox"  editable="false" id="userControl" name="userControlId" data-options="required:true,value:'${bean.userControlId}'">
						<c:forEach items="${controlList}" var="one">
							<option value="${one.controlId }">${one.controlName }</option>
						</c:forEach>
					</select> 
				</td>
			</tr>
			<tr>
				<td class="td1">设备控制节点:</td>
				<td class="td2">
					<select class="easyui-combobox"  editable="false" name="padControlId" data-options="required:true,value:'${bean.padControlId}'">
						<c:forEach items="${padControlList}" var="one">
							<option value="${one.controlId }" >${one.controlName }</option>
						</c:forEach>
					</select> 
				</td>
			</tr>
			<tr>
				<td class="td1">设备管理控制节点:</td>
				<td class="td2">
					<select class="easyui-combobox"  editable="false" name="ManageControlId" data-options="required:true,value:'${bean.manageControlId}'">
						<c:forEach items="${manageControlList}" var="one">
							<option value="${one.controlId }">${one.controlName }</option>
						</c:forEach>
					</select> 
				</td>
			</tr>
			<tr>
				<td class="td1">设备视频:</td>
				<td class="td2">
					<select class="easyui-combobox"  editable="false" name="padVideoId" data-options="required:true,value:'${bean.padVideoId}'">
						<c:forEach items="${padVideoList}" var="one">
							<option value="${one.videoId }">${one.videoName }</option>
						</c:forEach>
					</select> 
				</td>
			</tr>
			<tr>
				<td class="td1">用户视频:</td>
				<td class="td2">
					<select class="easyui-combobox"  editable="false" name="userVideoId" data-options="required:true,value:'${bean.userVideoId}'">
						<c:forEach items="${userVideoList}" var="one">
							<option value="${one.videoId }" >${one.videoName }</option>
						</c:forEach>
					</select> 
				</td>
			</tr>
			<tr>
				<td class="td1">备注:</td>
				<td class="td2">
					<textarea rows="10" cols="30" name="remark">${bean.remark} </textarea>
				</td>
			</tr>
	</table>
    </form>
    </div>
</body>
</html>



