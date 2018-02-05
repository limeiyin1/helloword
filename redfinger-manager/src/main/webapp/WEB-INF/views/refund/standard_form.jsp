<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>超级VIP退款标准</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script>
	<script type="text/javascript">
		$('.easyui-form').form({url:host+'save',   success:callback});
		
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
	<input type="hidden" name="standardId" value="${bean.standardId}">
	<table id="module_submit_table">
		<tr>
			<td class="td1">产品名称:</td>
			<td class="td2">
				<select class="easyui-combobox" editable="false" name="goodsId">
					<c:forEach items="${goodsList}" var="one">
						<option value="${one.goodsId }" <c:if test="${one.goodsId == bean.goodsId }">selected="selected"</c:if>>${one.goodsName }</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td class="td1">开始天数:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="dayStart" value="${bean.dayStart }"  data-options="required:true,min:0,max:366"/></td>
		</tr>
		<tr>
			<td class="td1">结束天数:</td>
			<td class="td2">
				<input class="easyui-numberbox" type="text" name="dayEnd" value="${bean.dayEnd }"  data-options="required:true,min:0,max:9999"/>
				<span style="color:red"><br/>无结束日期时请填写：9999</span>
			</td>
		</tr>
		<tr>
			<td class="td1">收取费用(元):</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="charge" value="${bean.charge }"  data-options="required:true,validType:'length[0,5]',min:0" /></td>
		</tr>
		<tr>
			<td class="td1">排序:</td>
			<td class="td2">
				<input class="easyui-numberbox" type="text" name="reorder" value="${bean.reorder }" data-options="required:true,validType:'length[0,32]'" />
			</td>
		</tr>
		<tr>
			<td class="td1">备注:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="remark" value="${bean.remark }" data-options="validType:'length[0,100]'" /></td>
		</tr>
	</table>
    </form>
    </div>
</body>
</html>
