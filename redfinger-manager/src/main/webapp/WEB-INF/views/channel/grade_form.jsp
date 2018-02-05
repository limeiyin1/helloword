<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>渠道分类</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'save',   
		    success:callback
		});
		
		
		/* $(document).ready(function () {
			$("#channelGrade").combobox({
	             onChange:function(newValue,oldValue){
					if("1"==newValue){
						$("#parent_id").hide();
						
						$("#parent_id").attr({"disabled":"disabled"});
						//$("#parentId").attr("data-options", "required:false");
						//$('#parentId').validatebox({required:false});
					}else{
						$("#parent_id").show();
						$("#parent_id").removeAttr("disabled");
						//$("#parentId").attr("data-options", "required:true");
						//$('#parentId').validatebox({required:true});
					}
	             }, 
			});

		$('#channelGrade').combobox('select','${bean.channelGrade}');
		
		}); */
		
		/* function isYes(){
			var channelGrade = $("#channelGrade").combobox('getValue');
			if(channelGrade == "1"){
				$('#parentId').combobox('clear');
				$("tr[name=channelTr]").addClass("hide");
				//$('#parentId').combobox({required:false});
				
			}else{
				$("tr[name=channelTr]").removeClass("hide");
				//$('#parentId').combobox({required:true});
			}
		} */
		
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
	<input type="hidden" name=gradeId value="${bean.gradeId}">
	<table id="module_submit_table">
			
			
			<tr>
				<td class="td1">渠道名称:</td>
				<td class="td2">
					<input class="easyui-textbox" type="text" name="gradeName" value="${bean.gradeName }" data-options="required:true,validType:'length[0,32]'"  />
				</td>
			</tr>
			
			
			<tr>
				<td class="td1">渠道级别:</td>
				<td class="td2">
					<input type="hidden" name="channelGrade" value="${channelGrade}"></input>${channelGradeName}
					<%-- <select class="easyui-combobox"  editable="false" name="channelGrade" id="channelGrade" data-options="required:true,onSelect:isYes">
						<fns:getOptions category="rf_channel_grade" value="${bean.channelGrade}"/>
				</select>  --%>
				</td>
			</tr>
			
			<tr name="channelTr" class="${isHide}">
				<td class="td1">上级渠道:</td>
				<td class="td2">
					<input type="hidden" name="parentId" value="${parentId}"></input>${parentName}
					<%-- <select class="easyui-combobox"  editable="false" name="parentId" id="parentId" >
						<c:forEach items="${list}" var="one">
							<option value="${one.gradeId }" <c:if test="${one.gradeId==bean.parentId}">selected="selected"</c:if>>${one.gradeName }</option>
						</c:forEach>
					</select>  --%>
				</td>
			</tr>
			
			<tr>
				<td class="td1">排序:</td>
				<td class="td2">
					<%-- <input type="text" name="reorder" class="easyui-textbox input_width_default" value="${bean.reorder}"/> --%>
					<input type="text" id="reorder" name="reorder" maxlength="9" class="easyui-validatebox" value="${bean.reorder}" onkeyup="this.value=this.value.replace(/\D/g,'')"/>
				</td>
			</tr>
			
			<tr>
				<td class="td1">备注:</td>
				<td class="td2">
					<textarea rows="8" cols="40" name="remark" >${bean.remark}</textarea>
				</td>
			</tr>
			
	</table>
    </form>
    </div>
    
    <script type="text/javascript">
		
    </script>
</body>
</html>



