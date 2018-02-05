<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>积分日志</title>
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
		              {width : 100,title : 'id',field : 'logId',checkbox : true}, 
		              {width : 70,title : '会员ID',field : 'map.externalUserId'},
		              {width : 100,title : '用户账号',field : 'map.userMobilePhone'},
		              {width : 100,title : '任务名',field : 'map.taskName'},  
		              {width : 100,title : '任务编码',field : 'map.taskCode'},
		              {width : 100,title : '操作类型',field : 'logType',sortable : true,formatter:function(value){return getDatagridDict('rf_score_log.log_type',value);}},  
		              {width : 100,title : '积分',field : 'score',sortable : true}, 
		              {width : 100,title : '创建时间',field : 'createTime',sortable : true,formatter:formatterTime},
		              {width : 100,title : '状态',field : 'enableStatus',sortable : true,formatter: formatterStop}
		          ]]
		};
	var dialogParamObj = {
			
	};
	$(function() {
		$(module_datagrid).datagrid($.extend({}, dataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
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
	<div id="module_container">
		<div id="module_search">
			<form id="module_search_form" class="easyui-form" method="post">
				<div class="module_search_input">
					会员ID：<input type="text" name="externalUserId" data-options="validType:'number'"" class="easyui-numberbox input_width_short"/>
			    </div>
				<div class="module_search_input">
				      用户手机：<input type="text" name="userMobilePhone" class="easyui-textbox input_width_default" /> 
				</div>
				<div class="module_search_input">
					创建时间:
						<input type="text" class="easyui-datebox input_width_default" editable="false"  name="beginTimeStr" /> 至 
						<input type="text" class="easyui-datebox input_width_default" editable="false"  name="endTimeStr"/>
				</div> 
				<div class="module_search_button">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-search-rf" plain="false" onclick="gridSearchValidate()">搜索</a>
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
</body>
</html>
