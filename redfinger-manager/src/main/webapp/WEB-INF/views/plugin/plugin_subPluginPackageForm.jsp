<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>插件分包</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script>
	<script type="text/javascript">
		$('.easyui-form').form({url:host+'subPlugin',   success:callback});
		
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
		
		function isYes(){
			var isYes = $("#isYes").combobox('getValue');
			if(isYes == "0"){
				$("tr[name=channelTr]").removeClass("hide");
			}else{
				$("tr[name=channelTr]").addClass("hide");
			}
		}
		
		$(document).ready(function(){
			$('#channelIds').combo({
	            editable : true,
	            multiple : true
	        });
	        $('#channelIdDiv').appendTo($('#channelIds').combo('panel'));
	
	        $("#channelIdDiv input").click(function() {
	                var _value = "";
	                var _text = "";
	                $("[name=channelIds]:input:checked").each(function() {
	                    _value += (_value==""?"":" | ")+ $(this).val() + "";
	                    _text += (_text==""?"":" | ")+ $(this).next("span").text();
	                });
	                //设置下拉选中值
	                $("#channelIds").combo('setValue', _value).combo(
	                        'setText', _text);
	        });
	        
	        
		});
		
		$("#pluginId").combobox({
			onChange: function (n,o) {
				if(n!=null && n!=""){
					$.ajax({
						url:host+'selectPlugin?pluginId='+n,
						type: "POST",
						success:function(data){
							data = eval('(' + data + ')');
							if(data != null){
								$('#pluginCode').textbox({value:data.pluginCode});
								$('#channelVersionCode').textbox({value:data.channelVersionCode});
								$('#pluginVersionCode').textbox({value:data.pluginVersionCode});
								$('#pluginVersionName').textbox({value:data.pluginVersionName});
								$('#pluginUrl').textbox({value:data.pluginUrl});
								$('#pluginDesc').textbox({value:data.pluginDesc});
							}
						}
					});
				} else {
					$('#pluginCode').textbox({value:""});
					$('#channelVersionCode').textbox({value:""});
					$('#pluginVersionName').textbox({value:""});
					$('#pluginVersionCode').textbox({value:""});
					$('#pluginUrl').textbox({value:""});
					$('#pluginDesc').textbox({value:""});
				}
				
			}
		});
		
		function isBlank(n,o){
			alert(n,o);
			if(n.length>0 && n.length<30){
				$(this).validatebox('remove');
			} else if(n.length<=0) {
				$(this).validatebox('reduce');
			}
		}
		
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
	<table id="module_submit_table">
		<tr id="qudao" class="qudao">
			<td class="td1">是否是所有渠道:</td>
			<td class="td2">
				<select class="easyui-combobox input_width_short" id="isYes" name="isYes" data-options="required:true,editable:false,onSelect:isYes">
					 <option value=""></option>
					 <c:forEach var="one" items="${yesOrNos}"  varStatus="vs">
					 	<option value="${one.key }" >${one.value }</option>
					 </c:forEach>
				</select>
			</td>
		</tr>
		
		<tr name="channelTr" class="hide">
			<td class="td1">选择渠道:</td>
			<td class="td2">
				<select id="channelIds" name="channelIds" data-options="editable:true,width:200" style="height:30px"></select>
                       　　 		<div id="channelIdDiv" name="channelIdDiv">
                      <c:forEach var="one" items="${channelList}"  varStatus="vs">
                      	  <input type="checkbox" name="channelIds" value="${one.channelId}">
                          <span>${one.channelName}<%-- _${one.clientType } --%></span>
                          <br />
                      </c:forEach> 
                </div>
			</td>
		</tr>
		
		<tr>
			<td class="td1">选择插件:</td>
			<td class="td2">
				<select class="easyui-combobox input_width_short" id="pluginId" name="pluginId" data-options="required:true,editable:false">
					 <option value="">-请选择-</option>				 	
					 <c:forEach var="one" items="${pluginList}">
						 <option value="${one.pluginId }" >${one.pluginVersionName } [${one.pluginCode}]</option>					 	
					 </c:forEach>
				</select>
			</td>
		</tr> 
		
		<tr>
			<td class="td1">插件编码:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="pluginCode" id="pluginCode" value="${bean.pluginCode }" onChange='isBlank()' data-options="required:true,validType:'length[0,32]'"  /></td>
		</tr>
		<tr>
			<td class="td1">插件版本号:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="pluginVersionCode" id="pluginVersionCode" value="${bean.pluginVersionCode }" data-options="required:true,validType:'length[0,32]'"  /></td>
		</tr>
		<tr>
			<td class="td1">渠道客户端版本编码:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="channelVersionCode" id="channelVersionCode" value="${bean.channelVersionCode }" data-options="required:true,validType:'length[0,32]'"  /></td>
		</tr>
		<tr>
			<td class="td1">插件版本名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="pluginVersionName" id="pluginVersionName" value="${bean.pluginVersionName }" data-options="required:true,validType:'length[0,32]'"  /></td>
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
			<td class="td1">补丁版本详情:</td>
			<td class="td2">
			<textarea name="pluginDesc" id="pluginDesc" class="easyui-validatebox" style="height:160px;width:280px;" data-options="validType:'length[0,500]',required:true,multiline:true">${bean.pluginDesc}</textarea>
			</td>
			
		</tr>
	</table>
    </form>
    </div>
</body>
</html>



