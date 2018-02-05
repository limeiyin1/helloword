<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>考核编辑</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'save',   
		    success:callback
		});
		
		$('#endTimeStr').datebox({
		    onSelect: function(date){
		    	var begin=$('#beginTimeStr').datetimebox('getValue');
		    	if(begin==''){
		    		$.messager.alert('提示', '开始时间不能为空！', "info");
					$('#endTimeStr').datetimebox('clear');
					return null;
		    	}
				var end=$('#endTimeStr').datetimebox('getValue');
				var currentTime=new Date();
				var endDate=Date.parse(end);
				if(begin>=end){
					$.messager.alert('提示', '结束时间不能小于等于开始时间！', "info");
					$('#endTimeStr').datetimebox('clear');
					return null;
				}
				if(endDate<=currentTime){
					$.messager.alert('提示', '结束时间不能小于等于当前时间！', "info");
					$('#endTimeStr').datetimebox('clear');
					return null;
				}
		    }
		});
		
		$('#beginTimeStr').datebox({
		    onSelect: function(date){
		    	var begin=$('#beginTimeStr').datetimebox('getValue');
				var currentTime=new Date();
				var yesterday=currentTime-86400000;
				var beginDate=Date.parse(begin);
				if(beginDate<yesterday){
					$.messager.alert('提示', '开始时间不能小于今天！', "info");
					$('#beginTimeStr').datetimebox('clear');
					return null;
				}
		    }
		});
		


	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
     <input type="hidden" name="id" value="${bean.id}">
	<table id="module_submit_table">
		<tr>
			<td class="td1">考核名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="name" value="${bean.name }" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		
		<tr>
			<td class="td1">考核开始时间:</td>
			<td class="td2"><input
				class="easyui-datebox input_width_default" type="text" id="beginTimeStr"
				name="beginTimeStr" data-options="required:true"
				value="<fmt:formatDate value="${bean.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"></td>
		</tr>
		
		<tr>
			<td class="td1">考核结束时间:</td>
			<td class="td2"><input
				class="easyui-datebox input_width_default" type="text" id="endTimeStr"
				name="endTimeStr" data-options="required:true"
				value="<fmt:formatDate value="${bean.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"></td>
		</tr>
		
		<tr>
			<td class="td1">备注:</td>
			<td class="td2"><input class="easyui-textbox" name="remark" value="${bean.remark}" data-options="multiline:true,validType:'length[0,255]'"style="height:60px" /></td>
		</tr>
	</table>
    </form>
    </div>
</body>
</html>