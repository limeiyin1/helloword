<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>设备</title>
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
     <input type="hidden" name="videoId" value="${bean.videoId}">
	<table id="module_submit_table">
		<tr>
			<td class="td1">视频编号:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="videoCode" value="${bean.videoCode }" data-options="required:true,validType:'length[0,255]'" /></td>
		</tr>
		<tr>
			<td class="td1">视频名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="videoName" value="${bean.videoName }" data-options="required:true,validType:'length[0,255]'" /></td>
		</tr>
		<tr>
			<td class="td1">视频地址:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="videoUrl" value="${bean.videoUrl}" data-options="required:true,validType:'length[0,255]'" /></td>
		</tr>
		<tr>
			<td class="td1">视频协议:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="videoProtocol" value="${bean.videoProtocol}" data-options="required:true,validType:'length[0,255]'" /></td>
		</tr>
		<tr>
			<td class="td1">视频域:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="videoDomain" value="${bean.videoDomain}" data-options="required:true,validType:'length[0,255]'" /></td>
		</tr>
		<tr>
			<td class="td1">视频端口:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="videoPort" value="${bean.videoPort}" data-options="required:true,validType:'length[0,10]'" /></td>
		</tr>
		<tr>
			<td class="td1">视频上下文:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="videoContext" value="${bean.videoContext}" data-options="required:true,validType:'length[0,255]'" /></td>
		</tr>
		<tr>
			<td class="td1">备用视频地址:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="bakVideoUrl" value="${bean.bakVideoUrl}" data-options="validType:'length[0,255]'" /></td>
		</tr>
		<tr>
			<td class="td1">备用视频协议:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="bakVideoProtocol" value="${bean.bakVideoProtocol}" data-options="validType:'length[0,255]'" /></td>
		</tr>
		<tr>
			<td class="td1">备用视频域:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="bakVideoDomain" value="${bean.bakVideoDomain}" data-options="validType:'length[0,255]'" /></td>
		</tr>
		<tr>
			<td class="td1">备用视频端口:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="bakVideoPort" value="${bean.bakVideoPort}" data-options="validType:'length[0,10]'" /></td>
		</tr>
		<tr>
			<td class="td1">备用视频上下文:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="bakVideoContext" value="${bean.bakVideoContext}" data-options="validType:'length[0,255]'" /></td>
		</tr>
		<tr>
			<td class="td1">机房:</td>
			<td class="td2">
				<select class="easyui-combobox"  editable="false" name="idcId" data-options="required:true">
					<c:forEach items="${idcList}" var="one">
						<option value="${one.idcId }" <c:if test="${one.idcId==bean.idcId}">selected="selected"</c:if>>${one.idcName }</option>
					</c:forEach>
				</select> 
			</td>
		</tr>
		<tr>
			<td class="td1">视频类型:</td>
			<td class="td2">
				<select class="easyui-combobox"  editable="false" name="videoType" data-options="required:true">
					<fns:getOptions category="rf_video.video_type" value="${bean.videoType}" keys="rf_video.video_type@device,rf_video.video_type@user" ></fns:getOptions>
			
				</select> 
			</td>
		</tr>
		<tr>
			<td class="td1">排序:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="reorder" value="${bean.reorder }" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
         <%--   <tr>
			<td class="td1">是否正常:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="isGood" value="${bean.isGood }" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>  --%>
		<tr>
			<td class="td1">描述:</td>
			<td class="td2"><input class="easyui-textbox" name="remark" value="${bean.remark }" data-options="multiline:true,validType:'length[0,200]'" style="height:60px" /></td>
		</tr>
	</table>
    </form>
    </div>
</body>
</html>



