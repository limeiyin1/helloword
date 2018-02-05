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
			$('#channelCode').combo({
	            editable : true,
	            multiple : true
	        });
	        $('#channelCodeDiv').appendTo($('#channelCode').combo('panel'));
	
	        $("#channelCodeDiv input").click(function() {
	                var _value = "";
	                var _text = "";
	                $("[name=channelCode]:input:checked").each(function() {
	                    _value += (_value==""?"":" | ")+ $(this).val() + "";
	                    _text += (_text==""?"":" | ")+ $(this).next("span").text();
	                });
	                //设置下拉选中值
	                $("#channelCode").combo('setValue', _value).combo(
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
		
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
	<%-- <input type="hidden" name="versionId" value="${version.versionId}">
	<input type="hidden" name="osType" value="${version.osType }"/> --%>
	<table id="module_submit_table">
		<tr>
			<td class="td1">母包:</td>
			<td class="td2">${channelCode }</td>
		</tr>
		
		<tr>
			<td class="td1">是否是所有渠道:</td>
			<td class="td2">
				<select class="easyui-combobox input_width_short" id="isYes" name="isYes" data-options="required:true,editable:false,onSelect:isYes">
					<option value="">[全部]</option>
					 <c:forEach var="one" items="${yesOrNos}"  varStatus="vs">
					 	<option value="${one.key }" >${one.value }</option>
					 </c:forEach>
				</select>
			</td>
		</tr>
		
		<tr name="channelTr" class="hide">
			<td class="td1">选择渠道:</td>
			<td class="td2">
				<select id="channelCode" name="channelCode" data-options="editable:true,width:200" style="width:200px;height:30px"></select>
                       　　 		<div id="channelCodeDiv" name="channelCodeDiv">
                      <c:forEach var="one" items="${channels}"  varStatus="vs">
                      	  <input type="checkbox" name="channelCode" value="${one.channelCode}">
                          <span>${one.channelName}</span>
                          <br />
                      </c:forEach>
                </div>
				
			</td>
		</tr>
		
		<tr>
			<td class="td1">系统类别:</td>
			<td class="td2"><select class="easyui-combobox input_width_short" editable="false" id="osType" name="osType" data-options="required:true">
				   <fns:getOptions category="version.os_type" keys="version.os_type@android,version.os_type@win"></fns:getOptions>
			 	</select>
			</td>
		</tr>
		
		<tr>
			<td class="td1">版本编码:</td>
			<td class="td2">
				<select class="easyui-combobox input_width_short" id="versionCode" name="versionCode" data-options="required:true,editable:false">
					<option value="">[全部]</option>
					 <c:forEach var="one" items="${list}">
					 	<option value="${one.parentVersionCode }" >${one.parentVersionCode }[${one.osType }]</option>
					 </c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td class="td1">是否必须更新:</td>
			<td class="td2"><select class="easyui-combobox input_width_short" editable="false" id="versionMust" name="versionMust">
				   <fns:getOptions category="client_version.update"></fns:getOptions>
			 	</select>
			</td>
		</tr>
		<tr>
			<td class="td1">是否是新版本:</td>
			<td class="td2"><select class="easyui-combobox input_width_short" editable="false" id="VersionNew" name="VersionNew">
				   <fns:getOptions category="client_version.update"></fns:getOptions>
			 	</select>
			</td>
		</tr>
		
		<tr>
			<td class="td1">版本名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="versionName" data-options="required:true,validType:'length[0,32]'"  /></td>
		</tr>
		
		<tr>
			<td class="td1">版本时间:</td>
			<td class="td2">
				<input type="text" class="easyui-datetimebox input_width_default" editable="false" id="end" name="channelVersionTimeStr" data-options="required:true"/>
			</td>
		</tr>
		<tr>
			<td class="td1">版本描述:</td>
			<td class="td2"><input style="height: 180px" class="easyui-textbox" type="text" name="versionDesc" data-options="required:true,validType:'length[0,500]',multiline:true" /></td>
		</tr>
		
	</table>
    </form>
    </div>
</body>
</html>



