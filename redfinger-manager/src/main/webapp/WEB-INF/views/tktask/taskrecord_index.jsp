<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>红手指任务日志</title>
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
		idField : 'taskId',
		singleSelect: false,
		checkOnSelect: true, 
		selectOnCheck: true,
		onCheck : function(row) {

		},
		columns : [[ 
              {width : 100,title : 'id',field : 'recordId',checkbox : true}, 
              {width : 90,title : '会员ID',field : 'map.externalUserId'},
              {width : 90,title : '用户账号',field : 'map.userMobilePhone'},
              {width : 100,title : '任务名',field : 'map.taskName'},  
              {width : 80,title : '任务类型',field : 'map.taskType',formatter:function(value){return getDatagridDict('task_system.category',value);}}, 
              {width : 100,title : '任务编码',field : 'map.taskCode',sortable : false},
              {width : 60,title : '奖励红豆',field : 'rbcAward',sortable : true}, 
              {width : 60,title : '奖励积分',field : 'scoreAward',sortable : true},
              {width : 80,title : '游戏',field : 'map.gameName',sortable : false},
              {width : 80,title : '问卷调查',field : 'map.surveyName',sortable : false},
              {width : 80,title : '邀请码',field : 'inviteCode',sortable : false},
              {width : 100,title : '备注',field : 'remark',sortable : false},
              {width : 60,title : '记录状态',field : 'recordStatus',sortable : true,formatter: function(value){return getDatagridDict('rf_task.task_record_status',value);}},
              {width : 60,title : '执行状态',field : 'enableStatus',sortable : true,formatter: formatterStop},
              {width : 100,title : '创建时间',field : 'createTime',sortable : true,formatter:formatterTime}
          ]]
		};
	var dialogParamObj = {
			
	};
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
				      会员ID：<input type="text" name="externalUserId" class="easyui-numberbox input_width_short" /> 
				</div>
				<div class="module_search_input">
				      用户手机：<input type="text" name="userMobilePhone" class="easyui-textbox input_width_default" /> 
				</div>
				<div class="module_search_input">
				      任务名称：<input type="text" name="taskName" class="easyui-textbox input_width_default" /> 
				</div>
				<div class="module_search_input">
				   任务类型：<select class="easyui-combobox input_width_short" editable="false" name="taskType" data-options="required:false">
							<option value="">[全部]</option>
						   <fns:getOptions category="task_system.category"></fns:getOptions>
					 	</select> 
				</div>
				<div class="module_search_input">
				      邀请码：<input type="text" name="inviteCode" class="easyui-textbox input_width_default" /> 
				</div>
				 <div class="module_search_input">
					完成时间:
						<input type="text" class="easyui-datebox input_width_default" editable="false"  name="beginTimeStr" /> 至 
						<input type="text" class="easyui-datebox input_width_default" editable="false"  name="endTimeStr"/>
				 </div>  
				<div class="module_search_button">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
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
		<a href="javascript:void(0)" id="button-save"
			class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a> <a
			href="javascript:void(0)" id="button-cancel"
			class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
</body>
</html>



