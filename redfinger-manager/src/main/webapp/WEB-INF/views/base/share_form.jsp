<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>图片编辑</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script>
	<script type="text/javascript">
		$('.easyui-form').form({
			url : host + 'save',
			success : callback
		});
		
		function uploadImageFile(fileUpdateName,shareId){
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
					$('#'+shareId).textbox({value:''});
					$('#module_submit_form').attr("enctype","application/x-www-form-urlencoded");
					if(obj){
						if(obj.flag == 'true'){
							 $('#'+shareId).textbox("setValue",obj.filePath);
							 $.messager.progress('close');
						}else{
						    $.messager.progress('close');
							if(obj.msg){
								$.messager.alert('上传失败',obj.msg);
							}else{
								$.messager.alert('上传失败','文件上传失败!');
							}
						}
					}
				},
				error : function(result){
					$('#module_submit_form').attr("enctype","application/x-www-form-urlencoded");
					$.messager.alert('上传失败','后台程序出错!');
				}
			});
			return false; 
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
			<input type="hidden" name="shareId" value="${bean.shareId}">
			<table id="module_submit_table">
				
				<tr>
					<td class="td1">客户端:</td>
					<td class="td2">
						<select class="easyui-combobox" editable="false" name="client" id="client" data-options="required:true" validType="selectValueRequired['#client']">
							<option value="">请选择</option>
							<fns:getOptions category="global.client_category" value="${bean.client}"></fns:getOptions>
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="td1">分享类型:</td>
					<td class="td2">
						<select class="easyui-combobox" editable="false" name="shareType" id="shareType" data-options="required:true" validType="selectValueRequired['#shareType']">
							<option value="">请选择</option>
							<fns:getOptions category="rf_share.type" value="${bean.shareType}"></fns:getOptions>
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="td1">分享标题:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="shareTitle" value="${bean.shareTitle}"
						data-options="required:true,min:0,max:999" /></td>
				</tr>
				<tr>
					<td class="td1">分享内容:</td>
					<td class="td2">
					<textarea required="required" rows="10" cols="30" name="shareText">${bean.shareText}</textarea>
					</td>
				</tr>
				<tr>
					<td class="td1">分享链接:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="shareLinkUrl" value="${bean.shareLinkUrl}"
						data-options="required:true,min:0,max:999" />
					</td>
				</tr>
			
				<tr>
					<td colspan=3>
						<table style="width:100%" border="0">
							<tr>
								<td class="td1">展示图片:</td>
								<td class="td2"><input class="easyui-textbox" type="text" id="shareImageUrl" 
									name="shareImageUrl" value="${bean.shareImageUrl}"
									data-options="validType:'length[0,100]',required:true" /></td>
							</tr>
							<tr>
								<td class="td1"><div id="p" class="easyui-progressbar" data-options="value:0" style="width:100px;display:none;"></div></td>
								<td class="td2">
									<input type="file" style="display:none" id="fileUpdate" name="fileUpdate" class="in_file disableClass" 
									onchange="if(this.value == '') return false; $('#fileLabel').html('File:' + this.value); uploadImageFile('fileUpdate','shareImageUrl');"/>
									<label id="fileLabel" >请点击“上传”按钮</label>
									<input class="disableClass" type="button" value="上传" onclick="$('#fileUpdate').click()" id="fileBtn"/>
								</td>
							</tr> 
						</table>
					</td>
				</tr>
				
				<tr>
					<td class="td1">备注:</td>
					<td class="td2">
					<textarea rows="10" cols="30" name="remark">${bean.remark}</textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>



