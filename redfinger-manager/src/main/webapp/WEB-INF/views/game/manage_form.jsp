<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>游戏管理</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'save',   
		    success:callback
		});
		
		/* function md5ShaSelect(){
			var md5ShaSum = $("#md5ShaSum").combobox('getValue');
			if(md5ShaSum == "1"){
				$("#md5_tr,#sha_tr").addClass('hide');
			}else{
				$("#md5_tr,#sha_tr").removeClass('hide');
			}
		} */
		
		$.extend($.fn.validatebox.defaults.rules, {  
			selectValueRequired: {
				validator: function(value,param){ 
					return $(param[0]).find("option:contains('"+value+"')").val() != '';  
				},  
				message: '该选项为必选项'  
			}  
		});
		
		function softTypeSelect(){
			var softType = $("#softType2").combobox('getValue');
			if(softType == "tool"){
				$("tr[name=disclaimerTr]").removeClass('hide');
			}else{
				$("tr[name=disclaimerTr]").addClass('hide');
			}
		}
		
		function checkFile(){
			var gameFile = $("#gameFile").textbox("getValue");
			if(gameFile == null || gameFile == ''){
				 $.messager.alert('提示', '渠道游戏地址不能为空！', "info");
                 return;
			}
			$.ajax({
				url : host+'updateMd5ShaVersionCode',
				data : {gameFile : gameFile},
				dataType : 'json',
				beforeSend: function () {
				    $.messager.progress({ 
				       title: '提示', 
				       msg: 'MD5、SHA校验中，请稍候……', 
				       text: '' 
				    });
			    },
			    complete: function () {
			         $.messager.progress('close');
			    },
				success:function(data){
				
					if(null != data.result){
						$.messager.alert('提示', data.result, "info");
                   	    return;
					}else{
						$("#md5").textbox("setValue",data.md5);
						$("#sha").textbox("setValue",data.sha);
						$("#versionCode").numberbox("setValue",data.versionCode);
					}
                },
                error:function(e){
                    $.messager.alert('提示', '系统异常！', "info");
                    return;
                }
			});
		
		}
		
		$('#gameAppName').keydown(function(e){
			if(e.keyCode==13){
			    var gameAppName = $('#gameAppName').textbox('getValue');
			    alert(gameAppName);
			}
		});
		
		function selectGameAppName(){
			 var gameAppName = $('#gameAppName').textbox('getValue');
			 $.ajax({
			 	type : 'post',
			 	url : host + 'selectGameApp',
			 	data : {gameAppName : gameAppName},
			 	dataType : 'json',
			 	success : function(data){
			 		if(null != data && null != data.list){
			            $('#gameAppId').combobox({
			            	data : data.list,
						    valueField:'gameAppId',
						    textField:'gameAppName'
						});
					}
			 	},
			 	error : function(){
			 		$.messager.alert('提示', '系统异常！', "info");
			 	}
			 });
		
		}
		
		function selectGameChannelName(){
			 var channelName = $('#channelName').textbox('getValue');
			 $.ajax({
			 	type : 'post',
			 	url : host + 'selectGameChannel',
			 	data : {channelName : channelName},
			 	dataType : 'json',
			 	success : function(data){
			 		if(null != data && null != data.list){
			 			$('#channelId').combobox({
			            	data : data.list,
						    valueField:'channelId',
						    textField:'channelName'
						});
					}
			 	},
			 	error : function(){
			 		$.messager.alert('提示', '系统异常！', "info");
			 	}
			 });
		
		}
		
		$(document).ready(function(){//加载页面完成后执行
			/* $("#gameAppId").combobox({editable:true});
			$("#channelId").combobox({editable:true});
			if($.fn.combobox){
			    //为了兼容火狐浏览器
			    $.fn.combobox.defaults.inputEvents.keyup=$.fn.combobox.defaults.inputEvents.keydown;
			    $.fn.combobox.defaults.inputEvents.keydown=function(){};
			} */
			
		});
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
	<input type="hidden" name="gameId" value="${bean.gameId}"/>
	<table id="module_submit_table">
		<tr>
			<td class="td1">游戏名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="gameName" value="${bean.gameName }"data-options="required:true" /></td>
		</tr>
		
		<tr>
			<td class="td1">4.4游戏类型:</td>
			<td class="td2">
				<select class="easyui-combobox"  editable="false" id="classId" name="classId" >
					<option value="">[全部]</option>
					<c:forEach items="${ClassList}" var="one">
						<option value="${one.classId }" <c:if test="${one.classId==bean.classId}">selected="selected"</c:if>>${one.className }</option>
					</c:forEach>
				</select>
				<%-- <input class="easyui-combotree" type="text" id="classId" name="classId" value="${bean.classId }" editable="true" data-options='data:${categoryTree}' /> --%>
			</td>
		</tr>
		<tr>
			<td class="td1">4.4游戏版本号:</td>
			<td class="td2"><input class="easyui-numberbox" id="gameVersion" type="text" name="gameVersion" value="${bean.gameVersion }" /></td>
		</tr>
		<tr>
			<td class="td1">游戏应用名:</td>
			<td class="td2">
				<select class="easyui-combobox"  id="gameAppId" name="gameAppId" data-options="editable:true">
					<option value="">[全部]</option>
					<c:forEach items="${gameAppList}" var="one">
						<option value="${one.gameAppId }" <c:if test="${one.gameAppId==bean.gameAppId}">selected="selected"</c:if>>${one.gameAppName }</option>
					</c:forEach>
				</select>
				<!-- </select>&nbsp;&nbsp;<input class="easyui-textbox" type="text" id="gameAppName" name="gameAppName" style="width:100px;" />
				<input type="button" onclick="selectGameAppName()" value="查询" /> -->
			</td>
		</tr>
		
		<tr>
			<td class="td1">游戏渠道:</td>
			<td class="td2">
				<select class="easyui-combobox"  id="channelId" name="channelId" data-options="editable:true">
					<option value="">[全部]</option>
					<c:forEach items="${gameChannelList}" var="one">
						<option value="${one.channelId }" <c:if test="${one.channelId==bean.channelId}">selected="selected"</c:if>>${one.channelName }</option>
					</c:forEach>
				</select>
				<!-- </select>&nbsp;&nbsp;<input class="easyui-textbox" type="text" id="channelName" name="channelName" style="width:100px;" />
				<input type="button" onclick="selectGameChannelName()" value="查询" /> -->
			</td>
		</tr>
		<tr>
			<td class="td1">游戏状态:</td>
			<td class="td2">
				<%-- <input class="easyui-combotree" type="text" name="gameStatus" value="${bean.gameStatus }" data-options='required:true,data:${status}' /> --%>
				<select class="easyui-combobox"  editable="false" name="gameStatus" data-options="required:true">
					<c:forEach items="${status}" var="one">
						<option value="${one.key }" <c:if test="${one.key==bean.gameStatus}">selected="selected"</c:if>>${one.value }</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		
		<%-- <tr>
			<td class="td1">游戏类型:</td>
			<td class="td2">
				<select class="easyui-combobox"  editable="false" name="classId" data-options="required:false">
					<option value="">[全部]</option>
					<c:forEach items="${classList}" var="one">
						<option value="${one.classId }" <c:if test="${one.classId==bean.classId}">selected="selected"</c:if>>${one.className }</option>
					</c:forEach>
				</select>
			</td>
		</tr> --%>
		
		<tr>
			<td class="td1">应用类型:</td>
			<td class="td2">
				<select class="easyui-combobox" id="softType2" name="softType" validType="selectValueRequired['softType']" data-options="required:true,editable:false, onSelect:softTypeSelect" style="width:100px">
					<option value="">[全部]</option>
				    <fns:getOptions category="rf_game.soft_type" value="${bean.softType}"></fns:getOptions>
			 	</select>
			</td>
		</tr>
		
		<tr>
			<td class="td1">MD5:</td>
			<td class="td2"><input  class="easyui-textbox" id="md5" type="text" name="md5New" value="${bean.md5New }" /></td>
		</tr>
		<tr>
			<td class="td1">SHA1:</td>
			<td class="td2"><input  class="easyui-textbox" id="sha" type="text" name="sha" value="${bean.sha }" /></td>
		</tr>
		<tr>
			<td class="td1">游戏版本号:</td>
			<td class="td2"><input class="easyui-numberbox" id="versionCode" type="text" name="versionCode" value="${bean.versionCode }" data-options="min:0" /></td>
		</tr>
		
		<tr>
			<td class="td1"></td>
			<td class="td2"><input type="button" onclick="checkFile()" title="自动生成MD5、SHA、游戏版本号" value="校验游戏文件值"> </td>
		</tr>
		<tr>
			<td class="td1">游戏版本名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" id="versionName" name="versionName"value="${bean.versionName }"data-options="required:false" /></td>
		</tr>
		<tr>
			<td class="td1">包名:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="gamePackageName"value="${bean.gamePackageName }"data-options="required:false" /></td>
		</tr>
		<tr >
			<td class="td1">游戏大小:</td>
			<td class="td2">
		    <input  class="easyui-numberbox" type="text" name="gameSize" value="${bean.gameSize }" data-options="min:0," style="width: 100px"/>(KB)
		    </td>
		</tr>
		<tr>
			<td class="td1">发行商:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="gameCompay"value="${bean.gameCompay }"data-options="required:false" /></td>
		</tr>
		<tr>
			<td class="td1">游戏描述:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="gameDesc" value="${bean.gameDesc }" data-options="multiline:true,required:false,validType:'length[0,70]'" style="height:60px"/></td>
		</tr>
		<tr>
			<td class="td1">下载地址:</td>
			<td class="td2"><input class="easyui-textbox" id="gameFile" type="text" name="gameFile" value="${bean.gameFile }" data-options="required:true" /></td>
		</tr>
		<!-- br名称已包含在url里 -->
		<%-- <tr>
			<td class="td1">bt名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="btName"value="${bean.btName }" data-options="required:false" /></td>
		</tr> --%>
		<tr>
			<td class="td1">bt下载url:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="btUrl"value="${bean.btUrl }" data-options="required:false" /></td>
		</tr>
		<tr style="display:none">
			<td class="td1">游戏标记:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="gameFlag"value=""data-options="required:false" /></td>
		</tr> 
		<tr style="display:none">
			<td class="td1">适配系统:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="gameOs"value="${bean.gameOs }"data-options="required:false" /></td>
		</tr>
		<tr>
			<td class="td1">图标:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="gameImage"value="${bean.gameImage }"data-options="required:false" /></td>
		</tr>
		<tr>
			<td class="td1">灰图标:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="gameGrayImage"value="${bean.gameGrayImage }"data-options="required:false" /></td>
		</tr>
		<tr  style="display:none">
			<td class="td1">黑名单:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="blackList"value="${bean.blackList }" data-options="required:false" /></td>
		</tr>
		<tr>
			<td class="td1">排序:</td>
			<td class="td2">
		    <input  class="easyui-numberbox" type="text" name="reorder" value="${bean.reorder }" data-options="min:0," />
		    </td>
		<tr>
		<tr name="disclaimerTr" class="${bean.softType=='tool'?'show':'hide'}">
			<td class="td1">免责声明:</td>
			<td class="td2">
			<textarea name="disclaimer" class="easyui-validatebox" style="height:160px;width:280px;" validType="text[1,500]">${bean.disclaimer }</textarea>
			</td>
		</tr>
		
		<tr name="disclaimerTr" class="${bean.softType=='tool'?'show':'hide'}">
			<td class="td1">是否显示免责声明:</td>
			<td class="td2">
				<select class="easyui-combobox" id="popStatus" name="popStatus" data-options="required:false,editable:false" style="width:100px">
					<c:forEach items="${yesOrNo}" var="one">
						<option value="${one.key }" <c:if test="${one.key==bean.popStatus}">selected="selected"</c:if>>${one.value }</option>
					</c:forEach>
			 	</select>
			</td>
		</tr>
		
		<tr>
			<td class="td1">是否是热门游戏:</td>
			<td class="td2">
				<select class="easyui-combobox" id="isHot" name="isHot" data-options="required:false,editable:false" style="width:100px">
					<option value="">[全部]</option>
				    <c:forEach items="${yesOrNo}" var="one">
						<option value="${one.key }" <c:if test="${one.key==bean.isHot}">selected="selected"</c:if>>${one.value }</option>
					</c:forEach>
			 	</select>
			</td>
		</tr>
		
		<tr>
			<td class="td1">更新方式:</td>
			<td class="td2">
				<select class="easyui-combobox" id="updateType" name="updateType" data-options="required:false,editable:false" style="width:100px">
					<option value="">[全部]</option>
				    <fns:getOptions category="rf_game.update_type" value="${bean.updateType}"></fns:getOptions>
			 	</select>
			</td>
		</tr>
		
		<tr>
			<td class="td1">设备版本:</td>
			<td class="td2">
				<select class="easyui-combobox" id="deviceVersion" name="deviceVersion" data-options="required:false,editable:false" style="width:100px">
					<option value="">[全部]</option>
				    <fns:getOptions category="rf_game.device_version" value="${bean.deviceVersion}"></fns:getOptions>
			 	</select>
			</td>
		</tr>
		
		<tr>
			<td class="td1">描述:</td>
			<td class="td2"><input class="easyui-textbox" name="remark" value="${bean.remark }" data-options="multiline:true,validType:'length[0,200]'" style="height:60px" /></td>
		</tr>
	</table>
    </form>
    </div>
</body>
</html>



