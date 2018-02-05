<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>故障咨询</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		 $(function(){
	        $('input[name="replyType"]').each(function(){
	            $(this).click(function(){
	                if($(this).attr('checked')){
	                    $('input[name="replyType"]').removeAttr('checked');
	                    $(this).attr('checked','checked');
	                }
	            });
	        });
	    }); 
	
		$('.easyui-form').form({
		    success:callback
		});
		function feedbackStatusSelect(record){
			if(record.value==getDictByKey('rf_fault_feedback.feedback_status@handle')||record.value==getDictByKey('rf_fault_feedback.feedback_status@moveyunwei')){
				var data = [];
				 var str='';
				<c:forEach items="${fixList}" var="one">
				var classId="${one.classId}";
				var calssName="${one.className}";
			    data.push({ "classId": classId, "className": calssName });
		        </c:forEach>
		        $("#rwlb").combobox("loadData", data); 
				$("#smallClassIdTr").removeClass("hide");
				$("#feedbackHandleTr").removeClass("hide");
				if(record.value==getDictByKey('rf_fault_feedback.feedback_status@handle')){
					$("#replyTr").removeClass("hide");
				}
			}else
			/*  if(record.value==getDictByKey('rf_fault_feedback.feedback_status@moveyunwei')){
				 var data = [];
				 var str='';
					<c:forEach items="${suggestList}" var="one">
					var classId="${one.classId}";
					var calssName="${one.className}";
					 data.push({ "classId": classId, "className": calssName });
			        </c:forEach>
			     $("#rwlb").combobox("loadData", data); 
				 $("#smallClassIdTr").removeClass("hide");
				 $("#feedbackHandleTr").removeClass("hide");
			
			 }else */{	
				$("#smallClassIdTr").addClass("hide");
				$("#feedbackHandleTr").addClass("hide");
				$("#replyTr").addClass("hide");
			}
		}
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
	<input type="hidden" name="ids" value="${bean.ids}"/>
	<table id="module_submit_table">
		<tr>
			<td class="td1">故障设备编号:</td>
			<td class="td2">
				<c:forEach var="one" items="${list}">
					${one.padCode }<br/>
				</c:forEach>
			</td>
		</tr>
		<tr>
			<td class="td1">故障处理:</td>
			<td class="td2">
				<select class="easyui-combobox"  editable="false" name="feedbackStatus" data-options="required:true,onSelect:feedbackStatusSelect">
					<fns:getOptions category="rf_fault_feedback.feedback_status" value="" keys="rf_fault_feedback.feedback_status@movekefu,rf_fault_feedback.feedback_status@moveceshi,rf_fault_feedback.feedback_status@moveyunwei,rf_fault_feedback.feedback_status@handle"/>  
				</select>
			</td>
		</tr>
		<tr id="smallClassIdTr" class="hide">
			<td class="td1">修复类型:</td>
			<td class="td2">
			<input class="easyui-combobox" id="rwlb"  name="smallClassId"  data-options="editable:false,required:false,valueField:'classId', textField:'className'," >  
			</td>
		</tr>
		<tr id="feedbackHandleTr" class="hide">
			<td class="td1">故障修复内容:</td>
			<td class="td2"><input class="easyui-textbox" name="feedbackHandle" value="${bean.feedbackHandle }" data-options="required:false,multiline:true,validType:'length[0,200]'" style="height:60px" /></td>
		</tr>
		<tr id="replyTr" class="hide">
			<td class="td1">处理完毕确认:</td>
			<td class="td2">
				<input class="" name="replyType" value="0" type="checkbox" />无需
				<input class="" name="replyType" value="1" type="checkbox" />咨询客服
				<input class="" name="replyType" value="2" type="checkbox" checked="checked" />所有客服
			</td>
		</tr>
	</table>
    </form>
    </div>
</body>
</html>



