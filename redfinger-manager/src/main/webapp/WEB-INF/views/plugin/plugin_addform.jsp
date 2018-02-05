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
					$('#pluginUrl').textbox({value:''});
				    $('#module_submit_form').attr("enctype","application/x-www-form-urlencoded");
 					//去掉pre
					if(obj){
						if(obj.flag == 'true'){
							$('#pluginUrl').textbox("setValue", obj.filePath);
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
	<input type="hidden" name="pluginId" value="${bean.pluginId}">
	<table id="module_submit_table">
		<tr>
			<td class="td1">插件渠道编码:</td>
			<td class="td2"><input type="hidden" name="pluginChannelCode" value="com.redfinger.app" />
				com.redfinger.app
			</td>
		</tr>
		<tr>
			<td class="td1">插件版本号:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="pluginVersionCode" value="${bean.pluginVersionCode }" data-options="required:true,validType:'length[0,32]'"  /></td>
		</tr>
		<tr>
			<td class="td1">插件编码:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="pluginCode" value="${bean.pluginCode }" data-options="required:true,validType:'length[0,32]'"  /></td>
		</tr>
		<tr>
			<td class="td1">渠道客户端版本编码:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="channelVersionCode" value="${bean.channelVersionCode }" data-options="required:true,validType:'length[0,32]'"  /></td>
		</tr>
		<tr>
			<td class="td1">插件版本名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="pluginVersionName" value="${bean.pluginVersionName }" data-options="required:true,validType:'length[0,32]'"  /></td>
		</tr>
		
		<tr>
			<td class="td1">是否必须更新:</td>
			<td class="td2">
				<select class="easyui-combobox input_width_short" editable="false" id="pluginVersionMust" name="pluginVersionMust" validType="selectValueRequired['#versionMust']">
				   <fns:getOptions category="client_version.update" value="${bean.pluginVersionMust}"></fns:getOptions>
			 	</select>
			</td>
		</tr>
		
		<tr>
			<td class="td1">下载地址:</td>
			<td class="td2">
				<input  class="easyui-textbox" type="text" name="pluginUrl" id="pluginUrl" value="${bean.pluginUrl }"/>
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
			<td class="td1">插件详情:</td>
			<td class="td2">
			<textarea name="pluginDesc" class="easyui-validatebox" style="height:160px;width:280px;" data-options="validType:'length[0,500]',required:true,multiline:true">${bean.pluginDesc}</textarea>
			</td>
			
		</tr>
	</table>
    </form>
    </div>
</body>
</html>



