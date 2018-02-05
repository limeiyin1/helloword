<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>分期夺宝</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'save',   
		    success:callback
		});
		
		
		function uploadImageFile(fileUpdateName,pictureId){
			$.messager.progress({
                title:'请稍后',
                msg:'正在上传...'
            });
		    if(!$("#"+fileUpdateName).val()){
				$.dialog.alert("请选择上传文件！");
				return;
			}	
			var hostUrl = host+'uploadImageRequest';
			$("#module_submit_form").attr("enctype","multipart/form-data");
			$("#module_submit_form").ajaxSubmit({
				type: "post",
				url: hostUrl,
				success: function (data) {
				    var obj = jQuery.parseJSON(jQuery(data).text());
					$('#'+pictureId).textbox({value:''});
					$('#module_submit_form').attr("enctype","application/x-www-form-urlencoded");
					if(obj){
						if(obj.flag == 'true'){
							 $('#'+pictureId).textbox("setValue",obj.filePath);
							 $.messager.progress('close');
						}else{
						    $.messager.progress('close');
							if(obj.msg){
								alert(obj.msg);
							}else{
								alert('文件上传失败!');
							}
						}
					}
				},
				error : function(result){
					$('#module_submit_form').attr("enctype","application/x-www-form-urlencoded");
					alert('后台程序出错!');
				}
			});
			return false; 
		}
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
	<input type="hidden" name="periodId" value="${bean.periodId}">
	<table id="module_submit_table">
		<c:if test="${not empty treasureName }">
			<tr>
			<td class="td1">总夺宝:</td>
			<td class="td2">${treasureName }</td>
		</tr>
		</c:if> 
		<c:if test="${empty treasureName }">
		<tr>
			<td class="td1">总夺宝:</td>
			<td class="td2">
				<select id="sel" class="easyui-combobox" name="treasureId" data-options="required:true">
					<c:forEach items="${treasures }" var="surs">
						<option value="${surs.treasureId }" <c:if test="${bean.treasureId == surs.treasureId }">selected="selected"</c:if>>${surs.treasureName }</option>
					</c:forEach>
				</select> 			
			</td>
		</tr>
		</c:if>
		<tr>
			<td class="td1">夺宝名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="treasureName" value="${bean.treasureName }" data-options="required:true" /></td>
		</tr>
		<tr>
			<td class="td1">夺宝图片:</td>
			<td class="td2">
				<input class="easyui-textbox" type="text" id="treasureImg" name="treasureImg" value="${bean.treasureImg}" data-options="validType:'length[0,100]'" />			
			</td>
		</tr>
		<tr>
			<td class="td1"><div id="p3" class="easyui-progressbar" data-options="value:0" style="width:100px;display:none;"></div></td>
			<td class="td2">
				<input type="file" style="display:none" id="fileUpdate" name="fileUpdate" class="in_file disableClass" 
				onchange="if(this.value == '') return false; $('#fileLabel').html('File:' + this.value); uploadImageFile('fileUpdate','treasureImg');"/>
				<label id="fileLabel" >请点击“上传”按钮</label>
				<input class="disableClass" type="button" value="上传" onclick="$('#fileUpdate').click()" id="fileBtn"/>
			</td>
		</tr>
		<tr>
			<td class="td1">当前期数:</td>
			<td class="td2"><input class="easyui-numberbox" name="currentPeriod" value="${bean.currentPeriod }" data-options="min:1,required:true" /></td>
		</tr>
		
		<tr>
			<td class="td1">开始时间:</td>
			<td class="td2">
				<input type="text" class="easyui-datetimebox input_width_default" editable="false" 
				id="begin" name="beginTimeStr" 
				value="<fmt:formatDate value="${bean.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" 
				data-options="required:true"/>
			</td>
		</tr>
		
		<tr>
			<td class="td1">结束时间:</td>
			<td class="td2">
				<input type="text" class="easyui-datetimebox input_width_default" editable="false" 
				id="end" name="endTimeStr" 
				value="<fmt:formatDate value="${bean.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" 
				data-options="required:true"/>
			</td>
		</tr>
	</table>
    </form>
    </div>
</body>
</html>



