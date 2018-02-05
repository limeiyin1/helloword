<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>任务中间表管理</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk = "id";
	var module_datagrid = "#module_datagrid";
	var module_dialog = "#module_dialog";
	var module_search_form = "#module_search_form"
	var module_submit_form = "#module_submit_form";
	var callback = defaultCallback;

	var dataGridParamObj = {
		url : host + "list",
		idField : 'id',
		onCheck : function(row) {

		},
		columns : [[ 
		              {width : 100,title : 'id',field : 'id',checkbox : true}, 
		              {width : 80,title : '用户Id',field : 'userId',sortable : true}, 
		              {width : 120,title : '用户手机',field : 'map.userMobilePhone',sortable : true}, 
		              {width : 80,title : '任务ID',field : 'taskId',sortable : true}, 
		              {width : 80,title : '任务进度',field : 'taskNow',sortable : true}, 
		              {width : 80,title : '任务目标',field : 'taskReach',sortable : true}, 
		              {width : 80,title : '任务状态',field : 'awardStatus',sortable : true,formatter:function(value){return getDatagridDict('task_record.award_status',value);}}, 
		              {width : 100,title : '任务类型',field : 'category',sortable : true,formatter:function(value){return getDatagridDict('task_system.category',value);}}, 
		              {width : 100,title : '任务名称',field : 'taskName',sortable : true}, 
		              {width : 150,title : '任务详情',field : 'remark',sortable : true}, 
		              {width : 80,title : '任务红豆',field : 'awardAmount',sortable : true}, 
		              {width : 100,title : '接取时间',field : 'takeTime', sortable: true,formatter:formatterTime}, 
		              {width : 100,title : '记录时间',field : 'recordTime', sortable: true,formatter:formatterTime}, 
		              {width : 100,title : '创建时间',field : 'createTime',sortable : true,formatter:formatterTime}, 
		              {width : 100,title : '修改时间',field : 'modifyTime',sortable : true,formatter:formatterTime}, 
		              {width : 100,title : '起始时间',field : 'beginTime',sortable : true,formatter:formatterTime},
		              {width : 100,title : '终止时间',field : 'endTime',sortable : true,formatter:formatterTime},
		              {width : 50,title : '阀值',field : 'thresholds',sortable : true,},
		              {width : 100,title : '任务编码',field : 'taskSer',sortable : true},
		              {width : 80,title : '状态',field : 'status',sortable : true,formatter: formatterStop},
		              {width : 80,title : '执行状态',field : 'enableStatus',sortable : true,formatter: formatterStop}
		          ]]
		};
	var dialogParamObj = {
			
	};
	$(function() {
		$(module_datagrid).datagrid($.extend({}, dataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
	
	});
	//删除中间表（物理删除）
	var removes = function(callback) {
		var ids = getGridIds();
		if (!ids) {
			return false;
		}
		$.messager.confirm('确认？', '确认删除此数据?', function(confirm) {
			if (confirm) {
				ajaxPost("remove", {
					ids : ids
				}, callback);
			}
		});
	}
	//批量修改数据
	var batchUpdate = function(callback) {
		var ids = getGridIds();
		if (!ids) {
			return false;
		}
		var title = $("title").html() + " - 编辑";
		var href = host + 'batchUpdate?ids=' + ids;
		$("#button-save").unbind("click").click(saveBatch);
		problemForm(title, href);
	}
	var saveBatch = function() {
		if ($(module_submit_form).form("validate")) {
			$.messager.progress();
			$(module_submit_form).form({
				url : host + 'saveBatch'
			});
			$(module_submit_form).form("submit");
		}
	}
	 function problemForm(title, href) {
			$(module_dialog).dialog({title : title,href: href,width:600});
			$(module_dialog).dialog("open");
	}

</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container">
		<div id="module_search">
			<form id="module_search_form" class="easyui-form" method="post">
				<div class="module_search_input"> 
				      用户手机：<input type="text" name="userMobilePhone" class="easyui-numberbox input_width_default" /> 
				</div>
				<div class="module_search_input">
				      任务名称：<input type="text" name="taskName" class="easyui-textbox input_width_default" /> 
				      任务进度：<input type="text" name="taskNow" class="easyui-numberbox input_width_default" />

				</div>
				<div class="module_search_input">
				   任务类型：<select class="easyui-combobox input_width_short"  editable="false" name="category" data-options="required:false">
							<option value="">[全部]</option>
						   <fns:getOptions category="task_system.category"  ></fns:getOptions>
					 	</select> 
				</div>
				<div class="module_search_input">
				  任务状态：<select class="easyui-combobox input_width_short"  editable="false" name="awardStatus" data-options="required:false">
							<option value="">[全部]</option>
						   <fns:getOptions category="task_record.award_status"  ></fns:getOptions>
					 	</select> 
				</div>
				 <div class="module_search_input">
					创建时间:
						<input type="text" class="easyui-datebox input_width_default" editable="false"  name="beginTimeStr" />
						至
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
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="add()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove-rf" plain="true" onclick="removes(callback)">删除</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-addBatch-rf" plain="true" onclick="batchUpdate()">批量修改</a>
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



