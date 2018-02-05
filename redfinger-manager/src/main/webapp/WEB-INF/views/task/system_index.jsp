<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>任务系统</title>
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
		              {width : 100,title : '任务名',field : 'name',sortable : true}, 
		              {width : 100,title : '创建人',field : 'creater',sortable : true}, 
		              {width : 300,title : 'SERVICE',field : 'taskSer',sortable : true}, 
		              {width : 300,title : '任务描述',field : 'remark',sortable : true}, 
		              {width : 100,title : '任务类型',field : 'category',sortable : true,formatter:function(value){return getDatagridDict('task_system.category',value);}}, 
		              {width : 100,title : '奖励红豆',field : 'awardAmount',sortable : true}, 
		              {width : 100,title : '阀值',field : 'thresholds',sortable : true,},
		            /*   {width : 100,title : 'SERVICE',field : 'taskSer',sortable : true,}, */
		              {width : 100,title : '执行状态',field : 'enableStatus',sortable : true,formatter: formatterStop},
		              {width : 100,title : '起始时间',field : 'beginTime',sortable : true,formatter:formatterTime},
		              {width : 100,title : '终止时间',field : 'endTime',sortable : true,formatter:formatterTime}
		            
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
				      任务名：<input type="text" name="name" class="easyui-textbox input_width_default" /> 
				</div>
				<div class="module_search_input">
				      创建人：<input type="text" name="creater" class="easyui-textbox input_width_default" /> 
				</div>
				<div class="module_search_input">
				     任务描述：<input type="text" name="remark" class="easyui-textbox input_width_default" /> 
				</div>
				<div class="module_search_input">
				   任务类型：<select class="easyui-combobox input_width_short"  editable="false" name="category" data-options="required:false">
							<option value="">[全部]</option>
						   <fns:getOptions category="task_system.category"  ></fns:getOptions>
					 	</select> 
				</div>
				<div class="module_search_input">
				  任务状态：<select class="easyui-combobox input_width_short"  editable="false" name="enableStatus" data-options="required:false">
							<option value="">[全部]</option>
						   <fns:getOptions category="global.enable_status"  ></fns:getOptions>
					 	</select> 
				</div>
				 <div class="module_search_input">
					起始时间:
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
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
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



