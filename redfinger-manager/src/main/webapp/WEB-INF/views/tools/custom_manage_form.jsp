<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>客服</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'update',
		    success:callback
		});
		$.extend($.fn.validatebox.defaults.rules, {
		     selectValueRequired: {
	          	validator: function(value,param){
	               	return $(param[0]).find("option:contains('"+value+"')").val() != '';
	          	},
	     	  	message: '该输入项为必输项'
		     }
		});
		$.extend($.fn.validatebox.defaults.rules,{
			space:{// 验证是否有空格
	          validator : function(value) {
	              return /^[^\s]+$/gi.test(value);
	          },
	          message : '该输入项不得有空格'
	        }
		});
		function customGroupSelect(){
			var customGroup = $("#customGroup").combobox('getValue');
			if(customGroup == "5"||customGroup == "6"||customGroup == "7"||customGroup == "8"){
		        $("#isLeader").combobox("disable");
				$("#isLeader").combobox('select','0');
			}else{
				$("#isLeader").combobox("enable");
				$("#isLeader").combobox('select','');
			}
		}
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
  		<input type="hidden" name="customId" value="${bean.customId}">
	<table id="module_submit_table">
		<tr>
			<td class="td1">客服名称:</td>
			<td class="td2">
				<input class="easyui-textbox" type="text" name="customName" value="${bean.customName }" data-options="required:true,validType:['space','length[1,6]']" />
			</td>
		</tr>
		<tr>
			<td class="td1">组别:</td>
			<td class="td2">
				<select class="easyui-combobox"  editable="false" id="customGroup" name="customGroup" value="${bean.customGroup}" required="true" validType="selectValueRequired['#customGroup']" data-options="onSelect:customGroupSelect">
					<option value=''>请选择</option>
					<c:forEach items="${RfCustomGroup}" var="one">
						<option value="${one.key}"<c:if test="${one.key==bean.customGroup}">selected="selected"</c:if>>${one.value}</option>
					</c:forEach>
				</select> 
			</td>
		</tr>
		<tr>
			<td class="td1">是否为班长:</td>
			<td class="td2">
				<select class="easyui-combobox"  editable="false" id="isLeader" name="isLeader" value="${bean.isLeader}" required="true" validType="selectValueRequired['#isLeader']">
					<option value=''>请选择</option>
					<c:forEach items="${yesOrNo}" var="one">
						<option value="${one.key}"<c:if test="${one.key==bean.isLeader}">selected="selected"</c:if>>${one.value}</option>
					</c:forEach>
				</select> 
			</td>
		</tr>
	    <tr>
			<td class="td1">开始排班日期:</td>
			<td class="td2">
				<input class="easyui-datebox input_width_default" type="text" name="enableTimeStr" data-options="required:true" value="<fmt:formatDate value="${bean.enableTime}" pattern="yyyy-MM-dd"/>">
			</td>
		</tr>
	</table>
    </form>
    </div>
</body>
</html>