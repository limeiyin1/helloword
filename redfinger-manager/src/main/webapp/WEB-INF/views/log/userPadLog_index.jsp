<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>用户设备日志</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="userPadLogId";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField :'userPadLogId',
		onCheck: function(row){
			
		},
		columns:[[
			{width:100,title:'id',field:pk,checkbox:true},
			{width:100,title:'会员ID',field:'map.externalUserId'},
			{width:100,title:'来源',field:'padSource',sortable:true},
			{width:200,title:'绑定时间',field:'bindTime',sortable:true,formatter:formatterTime},
			{width:200,title:'过期时间',field:'expireTime',sortable:true,formatter:formatterTime},
		   	{width:50,title:'设备等级',field:'padGrade',sortable:true,formatter:function(value){return getDatagridDict('rf_user_pad.pad_grade',value);}},
		   	{width:100,title:'用户手机',field:'map.phone',},
			{width:100,title:'创建人',field:'creater',sortable:true},
			{width:200,title:'创建时间',field:'createTime',sortable:true,formatter:formatterTime},
			{width:100,title:'修改人',field:'modifier',sortable:true},
			{width:200,title:'修改时间',field:'modifyTime',sortable:true,formatter:formatterTime},
			{width:100,title:'备注',field:'remark',sortable:true,},
			{width:100,title:'绑定手机号',field:'bindMobile',sortable:true,},
			{width:200,title:'剩余时间',field:'leftOnlineTime',sortable:true,},
			{width:200,title:'新编码',field:'map.newPadId',},
			{width:200,title:'旧编码',field:'map.oldPadId',},
			{width:200,title:'IMEI',field:'imei',}

		]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
		$.extend($.fn.validatebox.defaults.rules,{
			number:{// 验证数字
	          validator : function(value) {
	              return /^[0-9]{1,9}$/gi.test(value);
	          },
	          message : '只允许1-9位的正整数'
	        }
		});
	});
	//grid搜索
	var gridSearchValidate = function() {
		var arr = $(module_search_form).serializeArray();
		var obj = {};
		$.each(arr, function(i, field) {
			obj[field.name] = field.value;
		});
		if(!($('#module_search_form').form("validate"))){
			return false;
			}
		try{
			$(module_datagrid).treegrid("reload",obj);
		}catch(e){
			$(module_datagrid).datagrid("reload",obj);
		}
	}
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">
				会员ID：<input type="text" name="externalUserId" data-options="validType:'number'"" class="easyui-numberbox input_width_default"/>
		    </div>
			<div class="module_search_input">
			   使用状态:<select class="easyui-combobox input_width_short"  editable="false" name="field" data-options="required:false">
						<option value="">[全部]</option>
					    <option value="newPadId">绑定</option>
					    <option value="oldPadId">解绑</option>
					   <!--  <option value="-4">已过期</option> -->
						</select> 
			</div>
			<div class="module_search_input">
				用户手机：<input type="text" class="easyui-textbox input_width_default" name="userPhone" data-options=""/>
			</div>
			<div class="module_search_input">
				绑定手机：<input type="text" class="easyui-textbox input_width_default" name="phone" data-options=""/>
			</div>
			<div class="module_search_input">
				备注：<input type="text" class="easyui-textbox input_width_default" name="remark" data-options=""/>
			</div>
			<div class="module_search_input">
				操作时间：
				<input type="text" class="easyui-datebox input_width_default" editable="false" id="begin" name="beginTimeStr" data-options=""/>
				至
				<input type="text" class="easyui-datebox input_width_default" editable="false" id="end" name="endTimeStr" data-options=""/>
			</div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearchValidate()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>
	
	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
    <div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a>
		<a href="javascript:void(0)" id="button-cancel" class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
</body>
</html>



