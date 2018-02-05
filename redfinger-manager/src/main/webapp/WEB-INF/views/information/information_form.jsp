<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>资讯编辑</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script>
	
	
	<script type="text/javascript">
		$('.easyui-form').form({
			url : host + 'save',
			success : callback
		});
		
		
		function uploadImageFile(type,fileUpdateName,pictureId){
			$.messager.progress({
                title:'请稍后',
                msg:'正在上传...'
            });
		    if(!$("#"+fileUpdateName).val()){
				$.dialog.alert("请选择上传文件！");
				return;
			}	
			var hostUrl = "";
			if(type=='1'){
				hostUrl = host+'uploadBigImageRequest';
			}else if(type=='2'){
				hostUrl = host+'uploadSmallImageRequest';
			}
			
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
		
		var editorDetail = KindEditor.create('textarea[name="informationDetail"]', {
			cssPath : '${pageContext.request.contextPath}/kindeditor/plugins/code/prettify.css',
			uploadJson : host+'uploadJson',
			fileManagerJson : host+'fileManagerJson',
			allowFileManager : true,
			resizeType: 1,
            allowPreviewEmoticons: false,
			afterCreate : function() {
				var self = this;
				this.sync('informationDetail');
			},
			afterBlur : function(){
				this.sync();
			}
	    });
		
	</script>
	<div id="module_submit_container">
		
		<form id="module_submit_form" class="easyui-form" method="post">
			<input type="hidden" name="id" value="${bean.id}">
			<table id="module_submit_table">
				<tr>
					<td class="td1">资讯标题:</td>
					<td class="td2">
						<input class="easyui-textbox" type="text"
							name="informationTitle" value="${bean.informationTitle}"
							data-options="required:true,validType:'length[0,100]'" />
					</td>
				</tr>
				
				<tr>
					<td class="td1">资讯类型:</td>
					<td class="td2">
						<input class="easyui-textbox" type="text"
							name="informationType" value="${bean.informationType}"
							data-options="required:true,validType:'length[0,100]'" />
					</td>
				</tr>
				
				<tr>
					<td class="td1">客户端类型:</td>
					<td class="td2">
						<select class="easyui-combobox"  editable="false" name="clientType" data-options="required:true">
							<option value="win" <c:if test="${bean.clientType=='win'}">selected="selected"</c:if>>win</option>
							<option value="android" <c:if test="${bean.clientType=='android'}">selected="selected"</c:if>>android</option>
							<option value="ios" <c:if test="${bean.clientType=='ios'}">selected="selected"</c:if>>ios</option>
						</select> 
					</td>
				</tr>
				
				<tr>
					<td class="td1">是否是全文字:</td>
					<td class="td2">
						<select class="easyui-combobox"  editable="false" name="isTotalWords" data-options="required:true">
							<c:forEach items="${maps}" var="one">
								<option value="${one.key }" <c:if test="${bean.isTotalWords==one.key}">selected="selected"</c:if>>${one.value }</option>
							</c:forEach>
						</select> 
					</td>
				</tr>
				
				<tr>
					<td class="td1">排序:</td>
					<td class="td2">
						<input class="easyui-numberbox" type="text" name="reorder"
								value="${bean.reorder }"
								data-options="required:true,min:0,max:999" />
					</td>
				</tr>
				
				<tr>
					<td class="td1">资讯大图:</td>
					<td class="td2"><input class="easyui-textbox" type="text" id="informationBigPicture" 
						name="informationBigPicture" value="${bean.informationBigPicture}"
						data-options="validType:'length[0,100]'" /></td>
				</tr>
				<tr>
					<td class="td1"><div id="p" class="easyui-progressbar" data-options="value:0" style="width:100px;display:none;"></div></td>
					<td class="td2">
						<input type="file" style="display:none" id="fileUpdate" name="fileUpdate" class="in_file disableClass" 
						onchange="if(this.value == '') return false; $('#fileLabel').html('File:' + this.value); uploadImageFile('1','fileUpdate','informationBigPicture');"/>
						<label id="fileLabel" >请点击“上传”按钮</label>
						<input class="disableClass" type="button" value="上传" onclick="$('#fileUpdate').click()" id="fileBtn"/>
					</td>
				</tr> 
				
				<tr>
					<td class="td1">资讯小图:</td>
					<td class="td2"><input class="easyui-textbox" type="text" id="informationSmallPicture"
						name="informationSmallPicture" value="${bean.informationSmallPicture}"
						data-options="validType:'length[0,100]'" /></td>
				</tr>
				<tr>
					<td class="td1"><div id="p2" class="easyui-progressbar" data-options="value:0" style="width:100px;display:none;"></div></td>
					<td class="td2">
						<input type="file" style="display:none" id="fileUpdate2" name="fileUpdate2" class="in_file disableClass" 
						onchange="if(this.value == '') return false; $('#fileLabel2').html('File:' + this.value); uploadImageFile('2','fileUpdate2','informationSmallPicture');"/>
						<label id="fileLabel2" >请点击“上传”按钮</label>
						<input class="disableClass" type="button" value="上传" onclick="$('#fileUpdate2').click()" id="fileBtn"/>
					</td>
				</tr> 
				
				<tr>
					<td class="td1">资讯内容:</td>
					<td class="td2">
					<input class="easyui-textbox" type="text"
						name="informationContent" value="${bean.informationContent}"
						data-options="required:true,validType:'length[0,500]'" />
					</td>
				</tr>
				
				<tr>
					<td class="td1">资讯详情:</td>
					<td class="td2"><textarea id="informationDetail" name="informationDetail" cols="100" rows="8" style="width:700px;height:200px;visibility:hidden;">${bean.informationDetail }</textarea></td>
				</tr>
				
			</table>
		</form>
	</div>
	
</body>
</html>



