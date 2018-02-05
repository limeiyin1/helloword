<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>广播</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'save',   
		    success:callback
		});
		
		function countContents(message,contents) {
			var padlist;
			padlist= message.value;
			
			var len = 0;  
		    for (var i=0; i<padlist.length; i++) {  
		        if (padlist.charCodeAt(i)>127 || padlist.charCodeAt(i)==94) {  
		        	len += 2;  
		        } else {  
		        	len ++;  
		        }  
		    }

			var contentLength = $('#contentLength').val();
			if(len > contentLength){
				$.messager.alert('提示', '您输入的字符长度超过了规定的字符长度', 'info');
			}
			contents.value=len;
		}
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
	<input type="hidden" id="contentLength" name="contentLength" value="${contentLength }">
    <input type="hidden" name="broadcastId" value="${bean.broadcastId}">
	<table id="module_submit_table">
		<tr>
			<td class="td1">广播内容:</td>
			<td class="td2">
				<fieldset>
					<legend>请输入内容</legend>
					<textarea name="broadcastContent" wrap=PHYSICAL style="width: 220px;height: 200px" onKeyDown="countContents(this.form.broadcastContent,this.form.contents);"
					onKeyUp="countContents(this.form.broadcastContent,this.form.contents);">${bean.broadcastContent }</textarea>
                </fieldset>
			</td>
		</tr>
		 <tr>
		    <td class="td1">已输入字数：</td>
		    <td class="td2"><input disabled maxlength="4" name="contents" size="3" value="0" class="inputtext"></td>
		 </tr>
		<tr>
			<td class="td1">播放时间(秒):</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="broadcastPlaytime" value="${bean.broadcastPlaytime }" data-options="required:true,min:0,max:999" /></td>
		</tr>
		<tr>
			<td class="td1">开始时间:</td>
			<td class="td2">
				<input type="text" class="easyui-datetimebox input_width_default" editable="false"  id="begin" name="braodcastStartTimeStr" value="<fmt:formatDate value="${bean.broadcastStartTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" data-options="required:true"/>
			</td>
		</tr>
		<tr>
			<td class="td1">结束时间:</td>
			<td class="td2">
				<input type="text" class="easyui-datetimebox input_width_default" editable="false"  id="end" name="braodcastEndTimeStr" value="<fmt:formatDate value="${bean.broadcastEndTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" data-options="required:true"/>
			</td>
		</tr>
		<tr>
			<td class="td1">链接类型:</td>
			<td class="td2">
				<select class="easyui-combobox"  editable="false" name="linkType">
					<option value="">全部</option>
					<c:forEach items="${linkTypes}" var="one">
						<option value="${one.key }" <c:if test="${one.key==bean.linkType}">selected="selected"</c:if>>${one.value }</option>
					</c:forEach>
				</select> 
			</td>
		</tr>
		<tr>
			<td class="td1"></td>
			<td class="td2">
				<span>页面链接请填写页面链接地址，界面链接请填写界面的链接apk</span>
			</td>
		</tr>
		<tr>
			<td class="td1">链接详情:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="linkDetail" value="${bean.linkDetail}" data-options="validType:'length[0,100]'" /></td>
		</tr>
		
		<tr>
			<td class="td1">排序:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="reorder" value="${bean.reorder }" data-options="required:true,min:0,max:999" /></td>
		</tr>
		<tr>
			<td class="td1">描述:</td>
			<td class="td2"><input class="easyui-textbox" name="remark" value="${bean.remark }" data-options="multiline:true,validType:'length[0,200]'" style="height:60px" /></td>
		</tr>
	</table>
    </form>
    </div>
</body>
</html>



