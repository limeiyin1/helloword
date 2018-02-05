<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>母包版本控制</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script>
	<script type="text/javascript">
		$('.easyui-form').form({url:host+'update',   success:callback});
		
		$(document).ready(function () {
			$('#updateUrlTr').show();
            $('#downloadUrlTr').show();
		});
		
		function uploadFile(){
			if(!$("#fileInput").val()){
				$.dialog.alert("请选择上传文件！");
				return;
			}	
			
			$("#module_submit_form").attr("enctype","multipart/form-data");
			$("#module_submit_form").ajaxSubmit({
				type: "post",
				url: host+'uploadFileRequest',
				success: function (data) {
					var obj = jQuery.parseJSON(jQuery(data).text());
					$('#mendDownloadUrl').textbox({value:''});
				    $('#module_submit_form').attr("enctype","application/x-www-form-urlencoded");
 					//去掉pre
							alert(obj);
					if(obj){
							alert(obj.flag);
						if(obj.flag == 'true'){
							alert(obj.filePath);
							$('#mendDownloadUrl').textbox("setValue", obj.filePath);
						}else{
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
		
		
		
		 function uploadUpdateFile(){
		    if(!$("#fileUpdate").val()){
				$.dialog.alert("请选择上传文件！");
				return;
			}	
			
			$("#module_submit_form").attr("enctype","multipart/form-data");
			$("#module_submit_form").ajaxSubmit({
				type: "post",
				url: host+'uploadUpdateFileRequest',
				success: function (data) {
				    var obj = jQuery.parseJSON(jQuery(data).text());
					$('#mendUpdateUrl').textbox({value:''});
					$('#module_submit_form').attr("enctype","application/x-www-form-urlencoded");
 					//去掉pre
					if(obj){
						if(obj.flag == 'true'){
							 $('#mendUpdateUrl').textbox("setValue",obj.filePath);
						}else{
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
		
		function copyUpdateFile(){
			var updateFile = $('#mendUpdateUrl').val();
			$('#mendDownloadUrl').textbox("setValue", updateFile);
		}
		
		$.extend($.fn.validatebox.defaults.rules, {  
			selectValueRequired: {  
				validator: function(value,param){ 
					return $(param[0]).find("option:contains('"+value+"')").val() != '';  
				},  
				message: '该选项为必选项'  
			}  
		});
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
	<input type="hidden" name="mendId" value="${bean.mendId}">
	<table id="module_submit_table">
		<tr>
			<td class="td1">补丁渠道编码:</td>
			<td class="td2"><input type="hidden" name="mendChannelCode" value="com.redfinger.app" />com.redfinger.app</td>
		</tr>
		<tr>
			<td class="td1">补丁版本编码:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="mendVersionCode" value="${bean.mendVersionCode }" data-options="required:true,validType:'length[0,32]'"  /></td>
		</tr>
		<tr>
			<td class="td1">补丁版本名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="mendVersionName" value="${bean.mendVersionName }" data-options="required:true,validType:'length[0,32]'"  /></td>
		</tr>
		<tr>
			<td class="td1">系统类别:</td>
			<td class="td2">
				<input type="hidden" name="mendOsType" value="mend" />mend
				<%-- <select class="easyui-combobox"  editable="false" id="osType" name="mendOsType" data-options="required:true">
					<c:forEach items="${osTypes}" var="one">
						<option value="${one.key }" <c:if test="${one.key==bean.mendOsType}">selected="selected"</c:if>>${one.value }</option>
					</c:forEach>
				</select> --%>
			</td>
		</tr>
 		<tr>
			<td class="td1">补丁版本时间:</td>
			<td class="td2">
					<input type="text" class="easyui-datetimebox input_width_default" editable="false" 
					id="end" name="mendVersionTimeStr" 
					value="<fmt:formatDate value="${bean.mendVersionTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" 
					data-options="required:true"/>
			</td>
		</tr>
		<tr>
			<td class="td1">更新地址:</td>
			<td class="td2">
			   <input class="easyui-textbox" type="text" id="mendUpdateUrl" name="mendUpdateUrl" value="${bean.mendUpdateUrl }"/>	
			</td>
		</tr>
		<tr id="updateUrlTr">
			<td class="td1"></td>
			<td class="td2">
				<input type="file" style="display:none" id="fileUpdate" name="fileUpdate" class="in_file disableClass" onchange="if(this.value == '') return false; $('#fileLabel2').html('File:' + this.value); uploadUpdateFile();"/>
				<label id="fileLabel2" >请点击“上传”按钮</label>
				<input class="disableClass" type="button" value="上传" onclick="$('#fileUpdate').click()" id="fileBtn"/>
			</td>
		</tr> 
		<tr>
			<td class="td1">下载地址:</td>
			<td class="td2">
				<input  class="easyui-textbox" type="text" name="mendDownloadUrl" id="mendDownloadUrl" value="${bean.mendDownloadUrl }"/><input class="disableClass" type="button" value="复制更新地址" onclick="copyUpdateFile();" id="copyBtn"/>
			</td>
		</tr>
		
		<tr id="downloadUrlTr">
			<td class="td1"></td>
			<td class="td2">
				<input type="file" style="display:none" id="fileInput" name="fileInput" class="in_file disableClass" onchange="if(this.value == '') return false; $('#fileLabel').html('File:' + this.value); uploadFile();"/>
				<label id="fileLabel" >请点击“上传”按钮</label>
				<input class="disableClass" type="button" value="上传" onclick="$('#fileInput').click()" id="fileBtn"/>
			</td>
		</tr>
		<tr>
			<td class="td1">补丁版本详情:</td>
			<td class="td2">
			<textarea rows="13" cols="38" maxlength="500" name="mendVersionDesc" class="easyui-validatebox" data-options="validType:'length[0,500]',required:true,multiline:true">${bean.mendVersionDesc}</textarea>
			</td>
			
		</tr>
	</table>
    </form>
    </div>
</body>
</html>



