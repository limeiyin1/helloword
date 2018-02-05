<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>批量新增天数</title>
<meta name="decorator" content="default" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
			  onSubmit: function(){ 
				  var row = $('#module_datagrid_renewal').datagrid("getSelections");
				  if(row == ''){
				  	  /* $.messager.alert('提示', '请选择设备！', "info");
				  	  return false; */
				  }else if(row.length > 0){
				      var padIds = '';
				  	  for(var i=0;i<row.length;i++){
				  	  	  padIds = padIds + ',' +row[i].padId
				  	  }
				  	  $("#bindPadId").textbox('setValue',padIds);
				  }
				 
			  },  
			url : host + 'addTime',
			success : callback
		});
		
		
		var module_datagrid_renewal = "#module_datagrid_renewal";
		var userId='${bean.userId}';
        var dataGridParamObj = {
			idField : 'padId',
			pageSize : 10,
			url : "user/padList?userId="+userId,
			onCheck : function(index,row) {
	   
			},
			columns : [ [
		         	{width : 100,title : 'id',field : 'padId',checkbox : true},
					{width : 100,title : '设备名称',	field : 'map.padName',sortable : true},
					{width : 100,title : '设备编码',	field : 'padCode',sortable : true}
		    ] ]
      	};
      	
		var dialogParamObj = {

		};
		$(function() {
			$(module_datagrid_renewal).datagrid($.extend({}, dataGridParam, dataGridParamObj));
		});
		$("#divPadId" ).css("display", "none");
	</script>
     <div id="module_submit_container">
	 <div id="module_search">
		<form id="module_submit_form" class="easyui-form" method="post">
		<input type="hidden" name="userId" value="${bean.userId}" />
		<table id="module_submit_table">
			<tr>
				<td class="td1">会员名称:</td>
				<td class="td2">${bean.userName}</td>
			</tr>	
			<tr>
				<td class="td1">会员邮箱:</td>
				<td class="td2">${bean.userEmail}</td>
			</tr> 
			<tr>
				<td class="td1">会员电话:</td>
				<td class="td2">${bean.userMobilePhone}</td>
			</tr>
			<tr>
				<td class="td1">会员等级:</td>
				<td class="td2">${bean.userLevel}</td>
			</tr>
			<tr>
				<td class="td1">会员红豆币:</td>
				<td class="td2">${bean.rbcAmount}</td>
			</tr>
			
			<tr>
				<td class="td1">单位:</td>
				<td class="td2">
					<select class="easyui-combobox input_width_short"  editable="false" name="timeType" data-options="required:false">
					    <fns:getOptions category="expire_time.time_type"  ></fns:getOptions>
				 	</select> 
				</td>
			</tr>
			<tr>
				<td class="td1">赠送数量:</td>
				<td class="td2"><input class="easyui-numberbox" type="text" name="expireTime"  data-options="required:true,validType:'length[0,4]'" /></td>
			</tr>
		</table>
		<div id="divPadId" style="margin:10px 0;">
		<input type="hidden" name="padIds" class="easyui-textbox input_width_default"id="bindPadId"  />
		</div>
		</form>
		</div>
		<div class="module_search_button">
			<form id="user_search_form" method="post">
			</form>
		</div>
	</div>
	
	<table id="module_datagrid_renewal" toolbar="#module_toolbar_renewal" ></table>
	
</body>
</html>


