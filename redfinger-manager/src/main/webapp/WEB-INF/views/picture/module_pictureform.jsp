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
				hostUrl = host+'uploadOneImageRequest';
			}else if(type=='2'){
				hostUrl = host+'uploadTwoImageRequest';
			}else if(type=='3'){
				hostUrl = host+'uploadThreeImageRequest';
			}else if(type=='4'){
				hostUrl = host+'uploadFourImageRequest';
			}else if(type=='5'){
				hostUrl = host+'uploadFiveImageRequest';
			}else if(type=='6'){
				hostUrl = host+'uploadSixImageRequest';
			}else if(type=='7'){
				hostUrl = host+'uploadSevenImageRequest';
			}else if(type=='8'){
				hostUrl = host+'uploadEightImageRequest';
			}else if(type=='9'){
				hostUrl = host+'uploadNineImageRequest';
			}
			
			
			$("form[name='module_submit_form_picture']").attr("enctype","multipart/form-data");
			$("form[name='module_submit_form_picture']").ajaxSubmit({
				type: "post",
				url: hostUrl,
				success: function (data) {
				    var obj = jQuery.parseJSON(jQuery(data).text());
					$('#'+pictureId).textbox({value:''});
					$("form[name='module_submit_form_picture']").attr("enctype","application/x-www-form-urlencoded");
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
					$("form[name='module_submit_form_picture']").attr("enctype","application/x-www-form-urlencoded");
					alert('后台程序出错!');
				}
			});
			return false; 
		}
		
		
		function linkTypeSelect(){
			if($("#linkType").combobox('getValue')=='1'){
				$("#apkIdTr,#apkNameTr").removeClass('hide');
				$("#webLinkTr").addClass('hide');
			}else if($("#linkType").combobox('getValue')=='2'){
				$("#apkIdTr,#apkNameTr").addClass('hide');
				$("#webLinkTr").removeClass('hide');
			}
		}
		
	</script>

	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post" name="module_submit_form_picture">
			<input type="hidden" name="picture.pictureId" value="${pictureBean.pictureId}">
			<input type="hidden" name="picture.moduleId" value="${pictureBean.moduleId}">
			<input type="hidden" name="moduleCode" value="${moduleCode}">
			<table id="module_submit_table">
				<tr>
					<td class="td1">图片名称:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="picture.pictureName" value="${pictureBean.pictureName}"
						data-options="required:true,validType:'length[0,100]'" /></td>
				</tr>
				
				<tr>
					<td class="td1">播放时间:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="picture.playTime" value="${pictureBean.playTime}"
						data-options="required:true,min:0,max:999" /></td>
				</tr>
				
				<tr>
					<td class="td1">排序:</td>
					<td class="td2">
						<input class="easyui-textbox" type="text" name="picture.reorder"
								value="${pictureBean.reorder }"
								data-options="required:true,min:0,max:999" />
					</td>
				</tr>
				
				<tr>
					<td class="td1">开始时间:</td>
					<td class="td2">
						<input type="text" class="easyui-datetimebox input_width_default" editable="false" 
						id="begin" name="picture.beginTimeStr" 
						value="<fmt:formatDate value="${pictureBean.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" 
						data-options="required:true"/>
					</td>
				</tr>
				
				<tr>
					<td class="td1">结束时间:</td>
					<td class="td2">
						<input type="text" class="easyui-datetimebox input_width_default" editable="false" 
						id="end" name="picture.endTimeStr" 
						value="<fmt:formatDate value="${pictureBean.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" 
						data-options="required:true"/>
					</td>
				</tr>
				
				<%-- <tr>
					<td class="td1">480*216图片地址:</td>
					<td class="td2"><input class="easyui-textbox" type="text" id="fourPictureUrl"
						name="picture.fourPictureUrl" value="${pictureBean.fourPictureUrl}"
						data-options="validType:'length[0,100]'" /></td>
				</tr>
				
				<tr>
					<td class="td1"></td>
					<td class="td2">
						<input type="file" style="display:none" id="fileUpdate" name="fileUpdate" class="in_file disableClass" 
						onchange="if(this.value == '') return false; $('#fileLabel').html('File:' + this.value); uploadImageFile('1','fileUpdate','fourPictureUrl');"/>
						<label id="fileLabel" >请点击“上传”按钮</label>
						<input class="disableClass" type="button" value="上传" onclick="$('#fileUpdate').click()" id="fileBtn"/>
					</td>
				</tr>
				
				<tr>
					<td class="td1">720*324图片地址:</td>
					<td class="td2"><input class="easyui-textbox" type="text" id="sevenPictureUrl"
						name="picture.sevenPictureUrl" value="${pictureBean.sevenPictureUrl}"
						data-options="validType:'length[0,100]'" /></td>
				</tr>
				
				<tr>
					<td class="td1"></td>
					<td class="td2">
						<input type="file" style="display:none" id="fileUpdate2" name="fileUpdate2" class="in_file disableClass" 
						onchange="if(this.value == '') return false; $('#fileLabel2').html('File:' + this.value);  uploadImageFile('2','fileUpdate2','sevenPictureUrl');"/>
						<label id="fileLabel2" >请点击“上传”按钮</label>
						<input class="disableClass" type="button" value="上传" onclick="$('#fileUpdate2').click()" id="fileBtn"/>
					</td>
				</tr>  --%>
				
				<tr name="oneTr" class="${moduleCode=='SY' || moduleCode=='AD' ?'show':'hide'}">
					<td colspan=3>
						<table style="width:100%" border="0">
							<tr>
								<td class="td1">
									<span name="adAndroid" class="${moduleCode=='SY'?'show':'hide'}">ANDROID端展示图片地址:</span>
									<span name="adPc"  class="${moduleCode=='AD' ?'show':'hide'}">PC端展示图片地址: </span>
								</td>
								<td class="td2"><input class="easyui-textbox" type="text" id="onePictureUrl"
									name="onePictureUrl" value="${bean.onePictureUrl}"
									data-options="validType:'length[0,100]'" /></td>
							</tr>
							<tr>
								<td class="td1"><div id="p3" class="easyui-progressbar" data-options="value:0" style="width:100px;display:none;"></div></td>
								<td class="td2">
									<input type="file" style="display:none" id="fileUpdate3" name="fileUpdate3" class="in_file disableClass" 
									onchange="if(this.value == '') return false; $('#fileLabel3').html('File:' + this.value); uploadImageFile('3','fileUpdate3','onePictureUrl');"/>
									<label id="fileLabel3" >请点击“上传”按钮</label>
									<input class="disableClass" type="button" value="上传" onclick="$('#fileUpdate3').click()" id="fileBtn"/>
								</td>
							</tr>
							<tr name="adTr"  class="${moduleCode=='AD' ?'show':'hide'}">
								<td class="td1">ANDROID端展示图片地址:</td>
								<td class="td2"><input class="easyui-textbox" type="text" id="sevenPictureUrl"
									name="sevenPictureUrl" value="${bean.sevenPictureUrl}"
									data-options="validType:'length[0,100]'" /></td>
							</tr>
							<tr name="adTr"  class="${moduleCode=='AD' ?'show':'hide'}">
								<td class="td1"><div id="p2" class="easyui-progressbar" data-options="value:0" style="width:100px;display:none;"></div></td>
								<td class="td2">
									<input type="file" style="display:none" id="fileUpdate2" name="fileUpdate2" class="in_file disableClass" 
									onchange="if(this.value == '') return false; $('#fileLabel2').html('File:' + this.value); uploadImageFile('2','fileUpdate2','sevenPictureUrl');"/>
									<label id="fileLabel2" >请点击“上传”按钮</label>
									<input class="disableClass" type="button" value="上传" onclick="$('#fileUpdate2').click()" id="fileBtn"/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr name="sixTr" class="${moduleCode=='YD' ?'show':'hide'}">
					<td colspan=3>
						<table style="width:100%" border="0">
							<tr>
								<td class="td1">ANDROID端展示图片地址:</td>
								<td class="td2"><input class="easyui-textbox" type="text" id="sixPictureUrl" 
									name="sixPictureUrl" value="${bean.sixPictureUrl}"
									data-options="validType:'length[0,100]'" /></td>
							</tr>
							<tr>
								<td class="td1"><div id="p" class="easyui-progressbar" data-options="value:0" style="width:100px;display:none;"></div></td>
								<td class="td2">
									<input type="file" style="display:none" id="fileUpdate4" name="fileUpdate4" class="in_file disableClass" 
									onchange="if(this.value == '') return false; $('#fileLabel4').html('File:' + this.value); uploadImageFile('4','fileUpdate4','sixPictureUrl');"/>
									<label id="fileLabel4" >请点击“上传”按钮</label>
									<input class="disableClass" type="button" value="上传" onclick="$('#fileUpdate4').click()" id="fileBtn"/>
								</td>
							</tr> 
						</table>
					</td>
				</tr>
				
				<%-- <tr>
					<td class="td1">240*1080图片地址:</td>
					<td class="td2"><input class="easyui-textbox" type="text" id="twoPictureUrl"
						name="picture.twoPictureUrl" value="${pictureBean.twoPictureUrl}"
						data-options="validType:'length[0,100]'" /></td>
				</tr>
				<tr>
					<td class="td1"><div id="p2" class="easyui-progressbar" data-options="value:0" style="width:100px;display:none;"></div></td>
					<td class="td2">
						<input type="file" style="display:none" id="fileUpdate5" name="fileUpdate5" class="in_file disableClass" 
						onchange="if(this.value == '') return false; $('#fileLabel5').html('File:' + this.value); uploadImageFile('5','fileUpdate5','twoPictureUrl');"/>
						<label id="fileLabel5" >请点击“上传”按钮</label>
						<input class="disableClass" type="button" value="上传" onclick="$('#fileUpdate5').click()" id="fileBtn"/>
					</td>
				</tr> 
				
				<tr>
					<td class="td1">1120*760图片地址:</td>
					<td class="td2"><input class="easyui-textbox" type="text" id="oneSevenPrictureUrl"
						name="picture.oneSevenPrictureUrl" value="${pictureBean.oneSevenPrictureUrl}"
						data-options="validType:'length[0,100]'" /></td>
				</tr>
				<tr>
					<td class="td1"><div id="p3" class="easyui-progressbar" data-options="value:0" style="width:100px;display:none;"></div></td>
					<td class="td2">
						<input type="file" style="display:none" id="fileUpdate6" name="fileUpdate6" class="in_file disableClass" 
						onchange="if(this.value == '') return false; $('#fileLabel6').html('File:' + this.value); uploadImageFile('6','fileUpdate6','oneSevenPrictureUrl');"/>
						<label id="fileLabel6" >请点击“上传”按钮</label>
						<input class="disableClass" type="button" value="上传" onclick="$('#fileUpdate6').click()" id="fileBtn"/>
					</td>
				</tr> 
				
				<tr>
					<td class="td1">160*760图片地址:</td>
					<td class="td2"><input class="easyui-textbox" type="text" id="sixSixPictureUrl" 
						name="picture.sixSixPictureUrl" value="${pictureBean.sixSixPictureUrl}"
						data-options="validType:'length[0,100]'" /></td>
				</tr>
				<tr>
					<td class="td1"><div id="p" class="easyui-progressbar" data-options="value:0" style="width:100px;display:none;"></div></td>
					<td class="td2">
						<input type="file" style="display:none" id="fileUpdate7" name="fileUpdate7" class="in_file disableClass" 
						onchange="if(this.value == '') return false; $('#fileLabel7').html('File:' + this.value); uploadImageFile('7','fileUpdate7','sixSixPictureUrl');"/>
						<label id="fileLabel7" >请点击“上传”按钮</label>
						<input class="disableClass" type="button" value="上传" onclick="$('#fileUpdate7').click()" id="fileBtn"/>
					</td>
				</tr> 
				
				<tr>
					<td class="td1">100*480图片地址:</td>
					<td class="td2"><input class="easyui-textbox" type="text" id="oneFourPictureUrl"
						name="picture.oneFourPictureUrl" value="${pictureBean.oneFourPictureUrl}"
						data-options="validType:'length[0,100]'" /></td>
				</tr>
				<tr>
					<td class="td1"><div id="p2" class="easyui-progressbar" data-options="value:0" style="width:100px;display:none;"></div></td>
					<td class="td2">
						<input type="file" style="display:none" id="fileUpdate8" name="fileUpdate8" class="in_file disableClass" 
						onchange="if(this.value == '') return false; $('#fileLabel8').html('File:' + this.value); uploadImageFile('8','fileUpdate8','oneFourPictureUrl');"/>
						<label id="fileLabel8" >请点击“上传”按钮</label>
						<input class="disableClass" type="button" value="上传" onclick="$('#fileUpdate8').click()" id="fileBtn"/>
					</td>
				</tr> 
				<tr>
					<td class="td1">700*480图片地址:</td>
					<td class="td2"><input class="easyui-textbox" type="text" id="sevenFourPictureUrl"
						name="picture.sevenFourPictureUrl" value="${pictureBean.sevenFourPictureUrl}"
						data-options="validType:'length[0,100]'" /></td>
				</tr>
				<tr>
					<td class="td1"><div id="p3" class="easyui-progressbar" data-options="value:0" style="width:100px;display:none;"></div></td>
					<td class="td2">
						<input type="file" style="display:none" id="fileUpdate9" name="fileUpdate9" class="in_file disableClass" 
						onchange="if(this.value == '') return false; $('#fileLabel9').html('File:' + this.value); uploadImageFile('9','fileUpdate9','sevenFourPictureUrl');"/>
						<label id="fileLabel9" >请点击“上传”按钮</label>
						<input class="disableClass" type="button" value="上传" onclick="$('#fileUpdate9').click()" id="fileBtn"/>
					</td>
				</tr>  --%>
				
				<tr>
					<td class="td1">链接类型:</td>
					<td class="td2">
						<select class="easyui-combobox"  editable="false" id="linkType" name="linkType" data-options="onSelect:linkTypeSelect">
						    <option value="">请选择</option>
							<c:forEach items="${links}" var="one">
								<option value="${one.key }" <c:if test="${one.key==linkType}">selected="selected"</c:if>>${one.value }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				
				<tr  id="apkIdTr" class="${linkType=='1'?'show':'hide'}">
					<td class="td1">apkId:</td>
					<td class="td2">
						<input class="easyui-textbox" type="text" id="apkId" name="apkId" value="${apkId}" data-options="validType:'length[0,100]'"/>
					</td>
				</tr>
				<tr  id="apkNameTr" class="${linkType=='1'?'show':'hide'}">
					<td class="td1">apk全名:</td>
					<td class="td2">
						<input class="easyui-textbox" type="text" id="apkName" name="apkName" value="${apkName}" data-options="validType:'length[0,100]'"/>
					</td>
				</tr>
				
				<tr  id="webLinkTr" class="${linkType=='2'?'show':'hide'}">
					<td class="td1">网页链接地址:</td>
					<td class="td2">
						<input class="easyui-textbox" type="text" id="webLink" name="webLink" value="${webLink}" data-options="validType:'length[0,100]'"/>
					</td>
				</tr>
				
				<tr>
					<td class="td1">备注:</td>
					<td class="td2"><input class="easyui-textbox" name="picture.remark"
						value="${pictureBean.remark}"
						data-options="multiline:true,validType:'length[0,1000]'"
						style="height: 120px" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>



