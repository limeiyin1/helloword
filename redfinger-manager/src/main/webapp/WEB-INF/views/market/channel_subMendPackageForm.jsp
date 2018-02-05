<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>渠道管理</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({url:host+'subMend',   success:callback});
		
		$.extend($.fn.validatebox.defaults.rules, {  
			selectValueRequired: {  
				validator: function(value,param){ 
					return $(param[0]).find("option:contains('"+value+"')").val() != '';  
				},  
				message: '该选项为必选项'  
			}  
		});

		$("#mendVersionCode").combobox({
			onChange: function (n,o) {
				$.ajax({
					url:host+'selectMend?parentVersionCode='+n,
					type: "POST",
					success:function(data){
						data = eval('(' + data + ')');
						if(data != null){
							$('#mendVersionName').textbox({value:data.mend.parentVersionName});//补丁版本名称
							$('#mendVersionTimeStr').datetimebox({value:data.parentVersionTime});//补丁版本时间
							$('#mendUpdateurl').textbox({value:data.mend.parentVersionUpdateurl});//更新地址
							$('#mendDownloadurl').textbox({value:data.mend.parentVersionDownloadurl});//下载地址
							$('#mendVersionDesc').textbox({value:data.mend.parentVersionDesc});//版本描述
							$('#mendOsType').val(data.mend.osType);//补丁类别
						} else {
							$('#mendVersionName').textbox({value:""});//补丁版本名称
							$('#mendVersionTimeStr').datetimebox({value:""});//补丁版本时间
							$('#mendUpdateurl').textbox({value:""});//更新地址
							$('#mendDownloadurl').textbox({value:""});//下载地址
							$('#mendVersionDesc').textbox({value:""});//版本描述
						}
					}
				});
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
					$('#mendDownloadurl').textbox({value:''});
				    $('#module_submit_form').attr("enctype","application/x-www-form-urlencoded");
 					//去掉pre
					if(obj){
						if(obj.flag == 'true'){
							$('#mendDownloadurl').textbox({value:obj.filePath});
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
					$('#mendUpdateurl').textbox({value:''});
					$('#module_submit_form').attr("enctype","application/x-www-form-urlencoded");
 					//去掉pre
					if(obj){
						if(obj.flag == 'true'){
							 $('#mendUpdateurl').textbox({value:obj.filePath});
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
			var updateFile = $('#mendUpdateurl').val();
			$('#mendDownloadurl').textbox({value:updateFile});
		}
		
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
	</script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
	<table id="module_submit_table">
		<tr>
			<td class="td1">母包:</td>
			<td class="td2">${channelCode}</td>
		</tr>
		
		<%-- <tr>
			<td class="td1">渠道编码:</td>
			<td class="td2">
			<input type="hidden" name="channelCode" value="${bean.channelCode }">
			<input type="hidden" name="channelName" value="${bean.channelName }">
			<input type="hidden" name="channelOsType" value="${bean.clientType }">
			<input type="hidden" name="ids" value="${ids}">
			<c:forEach var="channel" items="${channelList}">
				${channel.channelCode } <br/>
			</c:forEach>
			</td>
		</tr> --%>
		
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
			<td class="td1">补丁版本编码:</td>
			<td class="td2">
				<select class="easyui-combobox input_width_short" id="mendVersionCode" name="mendVersionCode" data-options="required:true,editable:false">
					 <option value="">-请选择-</option>				 	
					 <c:forEach var="one" items="${list}">
						 <option value="${one.parentVersionCode }" >${one.parentVersionCode }[${one.osType }]</option>					 	
					 </c:forEach>
				</select>
				<input type="hidden" name="mendOsType" id="mendOsType">
			</td>
		</tr> 
		
		<tr>
			<td class="td1">版本名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" id="mendVersionName" name="mendVersionName" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		
		<tr>
			<td class="td1">版本时间:</td>
			<td class="td2">
				<input type="text" id="mendVersionTimeStr" class="easyui-datetimebox input_width_default" editable="false" id="end" name="mendVersionTimeStr" data-options="required:true"/>
			</td>
		</tr>
		
		<tr>
			<td class="td1">更新地址:</td>
			<td class="td2">
			   <input class="easyui-textbox" type="text" id="mendUpdateurl" name="mendUpdateUrl"/>	
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
				<input  class="easyui-textbox" type="text" name=mendDownloadUrl" id="mendDownloadurl"/>
				<input class="disableClass" type="button" value="复制更新地址" onclick="copyUpdateFile();" id="copyBtn"/>
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
			<!-- <input style="height: 180px" class="easyui-textbox" type="text" name="channelVersionDesc"data-options="required:true,validType:'length[0,500]',multiline:true" /> -->
			<textarea name="mendVersionDesc" id="mendVersionDesc" class="easyui-validatebox" style="height:160px;width:280px;" data-options="validType:'length[0,500]'" wrap="hard" required="true"></textarea>
			</td>
		</tr>
		
	</table>
    </form>
    </div>
</body>
</html>



