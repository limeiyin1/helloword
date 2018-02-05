<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>红手指任务记录</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk = "recordId";
	var module_datagrid = "#module_datagrid";
	var module_dialog = "#module_dialog";
	var module_search_form = "#module_search_form"
	var module_submit_form = "#module_submit_form";
	var callback = defaultCallback;

	var dataGridParamObj = {
		url : host + "list",
		idField : 'recordId',
		onCheck : function(row) {

		},
		columns : [[ 
		              {width : 100,title : 'id',field : 'recordId',checkbox : true}, 
		              {width : 100,title : '会员手机',field : 'userMobilePhoneT2',sortable : true}, 
		              {width : 100,title : '任务名称',field : 'nameT3',sortable : true}, 
		              {width : 50,title : '奖励红豆',field : 'awardAmount',sortable : true}, 
		              {width : 100,title : '领取状态',field : 'awardStatus',sortable : true,formatter:function(value){return getDatagridDict('task_record.award_status',value);}}, 
		              {width : 100,title : '领取有效期',field : 'awardTime',sortable : true,formatter:formatterTime}, 
		              {width : 100,title : '领取时间',field : 'takeTime',sortable : true,formatter:formatterTime}, 
		              {width : 100,title : '记录时间',field : 'recordTime',sortable : true,formatter:formatterTime},  	
		              {width : 100,title : '启用状态',field : 'enableStatus',sortable : true,formatter : formatterStop}	            
		          ]]
		};
	var dialogParamObj = {};
	$(function() {
		$(module_datagrid).datagrid($.extend({}, dataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
	});
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container">
		<div id="module_search">
			<form id="module_search_form" class="easyui-form" method="post">
				<div class="module_search_input">
					会员手机：<input type="text" name="userMobilePhoneT2"class="easyui-textbox input_width_default" /> 
				</div>
				<div class="module_search_input">
					任务名称： <select data-options="editable:true" name="taskId" class="easyui-combobox input_width_default" >
							<option value="">[全部]</option>
							<c:forEach var="one" items="${taskSystemList}">
								<option value="${one.id}">${one.name}</option>
							</c:forEach>
						  </select>
				</div>
				<div class="module_search_input">
					领取时间:
						<input type="text" class="easyui-datebox input_width_default" data-options="editable:true"  name="beginTimeStr" />
						至
						<input type="text" class="easyui-datebox input_width_default" data-options="editable:true" name="endTimeStr"/>
				 </div>  
				<div class="module_search_button">
					<a href="javascript:void(0)" class="easyui-linkbutton"data-options="iconCls:'icon-search-rf',plain:true" onclick="gridSearch()">搜索</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reset-rf',plain:true" onclick="gridReset()">清空</a>
				</div>
			</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add-rf',plain:true"  onclick="add()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit-rf',plain:true" onclick="edit()">编辑</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-start-rf',plain:true" onclick="start(callback)">启用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-stop-rf',plain:true" onclick="stop(callback)">禁用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove-rf',plain:true" onclick="del(callback)">删除</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload-rf',plain:true" onclick="fresh()">刷新</a>
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>

	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
	<div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save"class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a>
		 <a href="javascript:void(0)" id="button-cancel"class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
</body>
</html>



