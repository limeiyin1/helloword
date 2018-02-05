<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>渠道管理</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script>
	<script type="text/javascript">
		$('.easyui-form').form({url:host+'subPackage',   success:callback});
		
		$(document).ready(function(){
			$('#channelCodes').combo({
	            editable : true,
	            multiple : true
	        });
	        $('#channelCodeDiv').appendTo($('#channelCodes').combo('panel'));
	
	        $("#channelCodeDiv input").click(function() {
	                var _value = "";
	                var _text = "";
	                $("[name=channelCodes]:input:checked").each(function() {
	                    _value += (_value==""?"":" | ")+ $(this).val() + "";
	                    _text += (_text==""?"":" | ")+ $(this).next("span").text();
	                });
	                //设置下拉选中值
	                $("#channelCodes").combo('setValue', _value).combo(
	                        'setText', _text);
	        });
	        
	        
		});
		
		
		function isYes(){
			var isYes = $("#isYes").combobox('getValue');
			if(isYes == "0"){
				$("tr[name=channelTr]").removeClass("hide");
			}else{
				$("tr[name=channelTr]").addClass("hide");
			}
		}
		
		/* function isWin(){
			var isWin = $("#parentOsType").combobox('getValue');
			if(isWin != "win"){
				$("#qudao").removeClass("hide");
				isYes();
			}else{
				$("#qudao").addClass("hide");
				$("tr[name=channelTr]").addClass("hide");
			}
		} */
//=====================功能分割线==========================
		var callback1=function (json,fun){
			var data;
			data = $.parseJSON(json);
			var str = "";
			//alert(data.channels.length);
			for(var i=0;i<data.channels.length;i++){
				str = str+"<input type='checkbox' name='channelCodes' value='"+data.channels[i].channelCode+"'><span>"+data.channels[i].channelName+"</span><br/>";
			}
			$("#channelCodeDiv").html(str);
			
			$("#parentVersionCode").combobox('loadData',data.versions);//重新加载数据
			$("#parentVersionCode").combobox('select', data.versions[0].parentVersionCode);//选择第一个
		}
 		var getChannelAndVersionByClentType = function (newValue, oldValue){
 		 	var parentOsType = newValue;
			$.get("${ctx}/parent/version/getChannelAndVersionByClentType",{parentOsType:parentOsType},callback1);
		}

		
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
	<input type="hidden" name="parentVersionId" value="${rfParentVersion.parentVersionId } ">
	<table id="module_submit_table">
		<tr>
			<td class="td1">母包:</td>
			<td class="td2"><input type="hidden" name="parentChannelCode" value="${rfParentVersion.parentChannelCode } ">${rfParentVersion.parentChannelCode }</td>
		</tr>
		
		<tr>
			<td class="td1">系统类别:</td>
			<td class="td2"><input type="hidden" name="parentOsType" value="${rfParentVersion.osType}">${rfParentVersion.osType}</td>
			<%-- <td class="td2">
				<select class="easyui-combobox input_width_short" id="parentOsType" name="parentOsType" data-options="required:true,editable:false,onChange:getChannelAndVersionByClentType"> <!-- ,onSelect:isWin -->
					 <option value="">请选择</option>
					 <fns:getOptions category="version.os_type" keys="version.os_type@android,version.os_type@win"></fns:getOptions>
				</select>
			</td> --%>
		</tr>
		
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
				<select id="channelCodes" name="channelCodes" data-options="editable:true,width:200" style="height:30px"></select>
                       　　 		<div id="channelCodeDiv" name="channelCodeDiv">
                      <c:forEach var="one" items="${channels}"  varStatus="vs">
                      	  <input type="checkbox" name="channelCodes" value="${one.channelCode}">
                          <span>${one.channelName}</span>
                          <br />
                      </c:forEach> 
                </div>
			</td>
		</tr>
		
		<tr>
			<td class="td1">版本名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="parentVersionName" value="${rfParentVersion.parentVersionName }" data-options="required:true,validType:'length[0,32]'"  /></td>
		</tr>
		<tr>
			<td class="td1">版本编码:</td>
			<td class="td2"><input type="hidden" name="parentVersionCode" value="${rfParentVersion.parentVersionCode } ">${rfParentVersion.parentVersionCode }</td>
			<%-- <td class="td2">
				<select class="easyui-combobox input_width_short" id="parentVersionCode" name="parentVersionCode" data-options="required:true,editable:false
				,valueField:'parentVersionCode',textField:'parentVersionCode'">
					 <c:forEach var="one" items="${versions}"  varStatus="vs">
					 	<option value="${one.parentVersionCode }" >${one.parentVersionCode }[${one.osType }]</option>
					 </c:forEach>
				</select>
			</td> --%>
		</tr>
		
		<tr>
			<td class="td1">是否必须更新:</td>
			<td class="td2"><select class="easyui-combobox input_width_short" editable="false" id="versionMust" name="versionMust" data-options="required:true,editable:false,onSelect:isYes">
				<option value=""></option>
				   <fns:getOptions category="client_version.update" ></fns:getOptions>
			 	</select>
			</td>
		</tr>
		<tr>
			<td class="td1">是否是新版本:</td>
			<td class="td2">
				<select class="easyui-combobox input_width_short" editable="false" id="versionNew" name="versionNew" data-options="required:true,editable:false,onSelect:isYes">
				<option value=""></option>
					 <c:forEach var="one" items="${yesOrNos}"  varStatus="vs">
					 	<option value="${one.key }">${one.value }</option>
					 </c:forEach>
			 	</select>
			</td>
		</tr>
		<tr>
			<td class="td1">发现栏默认显示:</td>
			<td class="td2" checkbox="true">
				<select class="easyui-combobox input_width_short" editable="false" id="discoverLimit" name="discoverLimit" data-options="required:true,editable:false,onSelect:isYes">
				<option value=""></option>
				  <fns:getOptions category="global.yes_no"></fns:getOptions>
			 	</select>
			</td>
		</tr>
		<tr>
			<td class="td1">任务栏默认显示:</td>
			<td class="td2" checkbox="true">
				<select class="easyui-combobox input_width_short" editable="false" id="taskLimit" name="taskLimit" data-options="required:true,editable:false,onSelect:isYes">
				<option value=""></option>
				  <fns:getOptions category="global.yes_no"></fns:getOptions>
			 	</select>
			</td>
		</tr>
		
		<tr>
			<td class="td1">版本时间:</td>
			<td class="td2">
					<input type="text" class="easyui-datetimebox input_width_default" editable="false" 
					id="end" name="parentVersionTimeStr" 
					value="<fmt:formatDate value="${rfParentVersion.parentVersionTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" 
					data-options="required:true"/>
			</td>
		</tr>
		<tr>
			<td class="td1">版本详情:</td>
			<td class="td2">
<!-- 			<input style="height: 180px" class="easyui-textbox" type="text" name="parentVersionDesc" value="${versions[0].parentVersionDesc }" data-options="required:true,validType:'length[0,500]'"  /> -->
			<textarea rows="13" cols="38" maxlength="500" name="parentVersionDesc">${rfParentVersion.parentVersionDesc }</textarea>
			</td>
		</tr>
	</table>
    </form>
    </div>
</body>
</html>



