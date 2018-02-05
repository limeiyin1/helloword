<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>添加母包</title>
<meta name="decorator" content="default" />
</head>
<body>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script>
	<script type="text/javascript">
		$('.easyui-form').form({
			url:host+'save',   
			success:callback
		});
		
		
		$(document).ready(function () {
		    var osType = $("#osType").val();
			$("#osType").combobox({
				onChange: function (n,o) {
					if(n == 'starter' || n=='startVM'){
						$('#updateUrlTr').hide(); 
               		    $('#downloadUrlTr').hide();
					}else{
						$('#updateUrlTr').show();
               		    $('#downloadUrlTr').show();
					}
				}
			});
			if(osType == 'starter' || osType=='startVM'){
				$('#updateUrlTr').hide(); 
               	$('#downloadUrlTr').hide();
			}else{
				$('#updateUrlTr').show();
               	$('#downloadUrlTr').show();
			}
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
					$('#parentVersionDownloadurl').textbox({value:''});
				    $('#module_submit_form').attr("enctype","application/x-www-form-urlencoded");
 					//去掉pre
					if(obj){
						if(obj.flag == 'true'){
							$('#parentVersionDownloadurl').textbox({value:obj.filePath});
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
				//dataType:"json",
				success: function (data) {
				    var obj = jQuery.parseJSON(jQuery(data).text());
					$('#parentVersionUpdateurl').textbox({value:''});
					$('#module_submit_form').attr("enctype","application/x-www-form-urlencoded");
 					//去掉pre
					if(obj){
						if(obj.flag == 'true'){
							 $('#parentVersionUpdateurl').textbox({value:obj.filePath});
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
			var updateFile = $('#parentVersionUpdateurl').val();
			$('#parentVersionDownloadurl').textbox({value:updateFile});
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
		<table id="module_submit_table">
		<tr>
			<td class="td1">渠道编码:</td>
			<td class="td2"><input type="hidden" name="parentChannelCode" value="com.redfinger.app" />com.redfinger.app</td>
		</tr>
		<tr>
			<td class="td1">版本名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="parentVersionName" data-options="required:true,validType:'length[0,32]'"  /></td>
		</tr>
		<tr>
			<td class="td1">版本编码:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="parentVersionCode"  data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		
		<tr>
			<td class="td1">系统类别:</td>
			<td class="td2">
				<select class="easyui-combobox"  editable="false" id="osType" name="osType" data-options="required:true">
					<c:forEach items="${map}" var="one">
						<option value="${one.key }">${one.value }</option>
					</c:forEach>
				</select>
			</td>
		</tr>
 		<tr>
			<td class="td1">版本时间:</td>
			<td class="td2">
				<input type="text" class="easyui-datetimebox input_width_default" editable="false" id="end" name="parentVersionTimeStr" data-options="required:true"/>
			</td>
		</tr>
		
		<tr>
			<td class="td1">是否必须更新:</td>
			<td class="td2">
				<select class="easyui-combobox input_width_short" editable="false" id="versionMust" name="versionMust" validType="selectValueRequired['#versionMust']">
				   <option value="">[全部]</option>
				   <fns:getOptions category="client_version.update" value="${bean.versionMust}"></fns:getOptions>
			 	</select>
			</td>
		</tr>
		
		<tr>
			<td class="td1">是否新版本:</td>
			<td class="td2">
				<select class="easyui-combobox input_width_short" editable="false" id="versionNew" name="versionNew" validType="selectValueRequired['#versionMust']">
				   <c:forEach items="${news}" var="one">
				   	   <option  value="${one.key }" <c:if test="${one.key==bean.versionNew}">selected="selected"</c:if>>${one.value }</option>
				   </c:forEach>
			 	</select>
			</td>
		</tr>
		
		<tr>
			<td class="td1">是否启用:</td>
			<td class="td2" checkbox="true">
				<select class="easyui-combobox input_width_short" editable="false" id="enableStatus" name="enableStatus" validType="selectValueRequired['#versionMust']">
				   <c:forEach items="${news}" var="one">
				   	   <option  value="${one.key }" <c:if test="${one.key==bean.versionNew}">selected="selected"</c:if>>${one.value }</option>
				   </c:forEach>
			 	</select>
			</td>
		</tr>
		
		<%-- <tr>
			<td class="td1">发现栏是否可用:</td>
			<td class="td2" checkbox="true">
				<select class="easyui-combobox input_width_short" editable="false" id="discoverLimit" name="discoverLimit" validType="selectValueRequired['#versionMust']">
				  <fns:getOptions category="global.yes_no"></fns:getOptions>
			 	</select>
			</td>
		</tr> --%>
		
		<tr>
			<td class="td1">母包类型:</td>
			<td class="td2" checkbox="true">
				<select class="easyui-combobox input_width_short" editable="false"  name="parentVersionType" validType="selectValueRequired['#versionMust']">
				  <fns:getOptions category="rf_channel_type"></fns:getOptions>
			 	</select>
			</td>
		</tr>
		
		<tr>
			<td class="td1">更新地址:</td>
			<td class="td2">
			   <input class="easyui-textbox" type="text" id="parentVersionUpdateurl" name="parentVersionUpdateurl"/>	
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
				<input  class="easyui-textbox" type="text" name="parentVersionDownloadurl" id="parentVersionDownloadurl"/><input class="disableClass" type="button" value="复制更新地址" onclick="copyUpdateFile();" id="copyBtn"/>
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
			<td class="td1">版本描述:</td>
			<td class="td2">
				<!-- <input style="height: 180px" class="easyui-textbox" type="text" name="parentVersionDesc"  data-options="required:true,validType:'length[0,500]',multiline:true" /> -->
				<!-- <textarea rows="13" cols="38" maxlength="500" name="parentVersionDesc"></textarea> -->
				<textarea name="parentVersionDesc" class="easyui-validatebox" style="height:160px;width:280px;" data-options="validType:'length[0,500]',required:true,multiline:true"></textarea>
			</td>
		</tr>
	</table>
    </form>
    <!-- <table id="module_submit_table">
   		<tr>
			<td class="td1">下载地址:</td>
			<td class="td2">
			<form id="fileForm" method="post" enctype="multipart/form-data">
				<input type="file" style="display:none" id="fileInput" name="fileInput" class="in_file disableClass" onchange="if(this.value == '') return false; $('#fileLabel').html('File:' + this.value); uploadFile();"/>
				<label id="fileLabel" >请点击“上传”按钮</label>
				<input class="disableClass" type="button" value="上传" onclick="$('#fileInput').click()" id="fileBtn"/>
			</form>
			
			</td>
		</tr>
    </table> -->
    </div>
</body>
</html>



