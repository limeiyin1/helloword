<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>产品类型信息编辑</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script>
	<script type="text/javascript">
		$('.easyui-form').form({
			url : host + 'save',
			success : callback
		});
		
		function uploadImageFile(type,fileUpdateName,iconId){
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
				hostUrl = host+'uploadIconImageRequest';
			}
			
			
			$("#module_submit_form").attr("enctype","multipart/form-data");
			$("#module_submit_form").ajaxSubmit({
				type: "post",
				url: hostUrl,
				success: function (data) {
				    var obj = jQuery.parseJSON(jQuery(data).text());
					$('#'+iconId).textbox({value:''});
					$('#module_submit_form').attr("enctype","application/x-www-form-urlencoded");
					if(obj){
						if(obj.flag == 'true'){
							 $('#'+iconId).textbox("setValue",obj.filePath);
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
		
		
		function goodsTypeIdSelect(){
			goodsTypeId = $("#goodsTypeId").combobox('getValue');
			
			$("#useLimt").addClass('hide');
			
			if(goodsTypeId == '0'){
				$("#useLimt").removeClass("hide");
			}
		}
		
		
	</script>
	<div id="module_submit_container">
		
		<form id="module_submit_form" class="easyui-form" method="post">
			<input type="hidden" name="infoId" value="${bean.infoId}">
			<table id="module_submit_table">
				<tr>
					<td class="td1">信息名称:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="infoName" value="${bean.infoName}"
						data-options="required:true,validType:'length[0,12]'" /></td>
				</tr>
				
				<tr>
					<td class="td1">信息内容:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="infoContent" value="${bean.infoContent}"
						data-options="required:true,validType:'length[0,30]'" /></td>
				</tr>
				<tr>
					<td class="td1">信息颜色:</td>
					<td class="td2">
						<select class="easyui-combobox"  editable="false" name="infoColor" data-options="required:true">
								<fns:getOptions category="goods_type_info.info_color" value="${bean.infoColor}"></fns:getOptions>
						</select> 
					</td>
				</tr>
				<tr>
					<td class="td1">产品类型:</td>
					<td class="td2">
						<select class="easyui-combobox" id="goodsTypeId" editable="false" name="goodsTypeId" data-options="onSelect:goodsTypeIdSelect">
							<option value="">请选择</option>
							<c:forEach items="${list}" var="one">
								<option value="${one.typeId }" <c:if test="${one.typeId==bean.goodsTypeId}">selected="selected"</c:if>>${one.typeName }</option>
							</c:forEach>
						</select> 
					</td>
				</tr>
				<tr id="useLimt" class="${bean.goodsTypeId == '0' ?'show':'hide'}">
					<td class="td1">是否限时:</td>
					<td class="td2">
						<select class="easyui-combobox" editable="false" name="freeUseLimit">
							<option value="-1">请选择</option>
							<option value="0" <c:if test="${bean.freeUseLimit == 0}">selected="selected"</c:if>>否</option>
							<option value="1" <c:if test="${bean.freeUseLimit == 1}">selected="selected"</c:if>>是</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="td1">排序:</td>
					<td class="td2">
						<input class="easyui-textbox" type="text" name="reorder"
								value="${bean.reorder }"
								data-options="required:true,min:0,max:999" />
					</td>
				</tr>
				
				<tr>
					<td class="td1">信息图标:</td>
					<td class="td2"><input class="easyui-textbox" type="text" id="infoIcon" 
						name="infoIcon" value="${bean.infoIcon}"
						data-options="validType:'length[0,100]'" /></td>
				</tr>
				<tr>
					<td class="td1"><div id="p" class="easyui-progressbar" data-options="value:0" style="width:100px;display:none;"></div></td>
					<td class="td2">
						<input type="file" style="display:none" id="fileUpdateIcon" name="fileUpdateIcon" class="in_file disableClass" 
						onchange="if(this.value == '') return false; $('#fileLabelIcon').html('File:' + this.value); uploadImageFile('1','fileUpdateIcon','infoIcon');"/>
						<label id="fileLabelIcon" >请点击“上传”按钮</label>
						<input class="disableClass" type="button" value="上传" onclick="$('#fileUpdateIcon').click()" id="fileBtn"/>
					</td>
				</tr> 
				
				<tr>
					<td class="td1">备注:</td>
					<td class="td2"><input class="easyui-textbox" name="remark"
						value="${bean.remark}"
						data-options="multiline:true,validType:'length[0,1000]'"
						style="height: 120px" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>



