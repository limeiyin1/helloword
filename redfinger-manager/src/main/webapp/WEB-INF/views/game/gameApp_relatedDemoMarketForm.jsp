<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>游戏应用推荐</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'relatedDemoMarket',   
		    success:callback
		});
		
		
		function uploadImageFile(fileUpdateName,gameAppImage){
			$.messager.progress({
                title:'请稍后',
                msg:'正在上传...'
            });
		    if(!$("#"+fileUpdateName).val()){
				$.dialog.alert("请选择上传文件！");
				return;
			}	
			var hostUrl = host+'uploadOneImageRequest';
			
			
			$("#module_submit_form").attr("enctype","multipart/form-data");
			$("#module_submit_form").ajaxSubmit({
				type: "post",
				url: hostUrl,
				success: function (data) {
				    var obj = jQuery.parseJSON(jQuery(data).text());
					$('#'+gameAppImage).textbox({value:''});
					$('#module_submit_form').attr("enctype","application/x-www-form-urlencoded");
					if(obj){
						if(obj.flag == 'true'){
							 $('#'+gameAppImage).textbox("setValue",obj.filePath);
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
	<input type="hidden" name="gameAppId" value="${bean.gameAppId}"/>
	<table id="module_submit_table">
		<tr>
			<td class="td1">游戏名称:</td>
			<td class="td2">${bean.gameAppName }</td>
		</tr>
		
		<tr>
			<td colspan=3>
				<table style="width:100%" border="0">
					<tr>
						<td class="td1">展示图片:</td>
						<td class="td2"><input class="easyui-textbox" type="text" id="gameAppImage" 
							name="gameAppImage"
							data-options="validType:'length[0,100]'" /></td>
					</tr>
					<tr>
						<td class="td1"><div id="p" class="easyui-progressbar" data-options="value:0" style="width:100px;display:none;"></div></td>
						<td class="td2">
							<input type="file" style="display:none" id="fileUpdate" name="fileUpdate" class="in_file disableClass" 
							onchange="if(this.value == '') return false; $('#fileLabel').html('File:' + this.value); uploadImageFile('fileUpdate','gameAppImage');"/>
							<label id="fileLabel" >请点击“上传”按钮</label>
							<input class="disableClass" type="button" value="上传" onclick="$('#fileUpdate').click()" id="fileBtn"/>
						</td>
					</tr> 
				</table>
			</td>
		</tr>
		
	</table>
    </form>
    </div>
</body>
</html>



