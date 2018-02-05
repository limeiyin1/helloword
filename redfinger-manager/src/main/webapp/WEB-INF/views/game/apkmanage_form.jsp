 <%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html  lang="zh-cn">
<head>
<title>游戏apk管理</title>
<meta name="decorator" content=default />
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
			<c:if test="${not empty bean.id}">
				<input type="hidden" name="id" value="${bean.id}" />
			</c:if>
			<table id="module_submit_table" class="easyui-table">
				<tr>
					<td class="td1">游戏名称:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="name" value="${bean.name }" data-options="required:true" /></td>
				</tr>
				<tr>
					<td class="td1">游戏版本号:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="apkVersion" value="${bean.apkVersion }"
						data-options="required:true" /></td>
				</tr>
				<tr>
					<td class="td1">游戏大小:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="apkSize" value="${bean.apkSize }"
						data-options="required:true" /></td>
				</tr>
				<tr>
					<td class="td1">开发商:</td>
					<td class="td2"><select class="easyui-combobox"
						name="developer" data-options="required:true">
						<option></option>
							<c:forEach items="${developerList}" var="one">
								<option value="${one.id }"
									<c:if test="${one.id==bean.developer}">selected="selected"</c:if>>${one.name }</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td class="td1">游戏详情:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="content" value="${bean.content }"
						data-options="multiline:true,required:false,validType:'length[0,500]'"
						style="height: 60px" /></td>
				</tr>
				<tr>
					<td class="td1">下载地址:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="downloadUrl" value="${bean.downloadUrl }"
						data-options="required:true" /></td>
				</tr>
				<tr>
					<td class="td1">游戏包:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="packageName" value="${bean.packageName }"
						data-options="required:true" /></td>
				</tr>
				<tr>
					<td class="td1">更新时间:</td>
					<td class="td2">			
					<input
						class="easyui-datebox input_width_default" type="text"
						id="updateTime" name="beginTimeStr" value="<fmt:formatDate value="${bean.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						data-options="required:true" /></td>
				</tr>
				<tr>
					<td class="td1">关键字:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="keywords" value="${bean.keywords }"
						data-options="required:false" /></td>
				</tr>
				<tr>
					<td class="td1">重新排序:</td>
					<td class="td2"><input class="easyui-textbox" type="number"
						name="reorder" value="${bean.reorder }"
						data-options="required:true" /></td>
				</tr>
				<tr>
					<td class="td1">下载虚拟次数:</td>
					<td class="td2"><input class="easyui-textbox" type="number"
						name="countDownloadBase" value="${bean.countDownloadBase }"
						data-options="required:true" /></td>
				</tr>
				<tr>
					<td class="td1">浏览虚拟次数:</td>
					<td class="td2"><input class="easyui-textbox" type="number"
						name="countBrowseBase" value="${bean.countBrowseBase }"
						data-options="required:true" /></td>
				</tr>
				<tr>
					<td class="td1">搜索虚拟次数:</td>
					<td class="td2"><input class="easyui-textbox" type="number"
						name="countSearchBase" value="${bean.countSearchBase }"
						data-options="required:true" /></td>
				</tr>
				<tr>
					<td class="td1">描述:</td>
					<td class="td2"><input class="easyui-textbox" name="remark"
						value="${bean.remark }"
						data-options="multiline:true,validType:'length[0,200]'"
						style="height: 60px" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>



