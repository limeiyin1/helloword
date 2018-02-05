<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>游戏下载任务审核</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk = "checkId";
	var module_datagrid = "#module_datagrid";
	var module_dialog = "#module_dialog";
	var module_search_form = "#module_search_form"
	var module_submit_form = "#module_submit_form";
	var callback = defaultCallback;

	var dataGridParamObj = {
		url : host + "list",
		idField : pk,
		onCheck : function(row) {

		},
		columns : [[ 
		              {width : 100,title : 'id',field : pk,checkbox : true},
		              {width : 100,title : '用户手机',field : 'userMobilePhone',sortable : true},
		              {width : 100,title : '任务名称',field : 'map.taskName',sortable : true},
		              {width : 100,title : '游戏帐号',field : 'checkGameAccount',sortable : true}, 
		              {width : 100,title : '审核状态',field : 'checkStatus',sortable : true,formatter:function(value){return getDatagridDict('task_gamedownload_check.check_status',value);}}, 
		              {width : 100,title : '提交时间',field : 'createTime',sortable : true,formatter:formatterTime}, 
		              {width : 100,title : '审核时间',field : 'checkTime',sortable : true,formatter:formatterTime}, 
		              {width : 100,title : '审核人',field : 'checkPerson',sortable : true},
		              {width : 100,title : '备注',field : 'remark',sortable : true}
		          ]]
		};
	var dialogParamObj = {};
	$(function() {
		$(module_datagrid).datagrid($.extend({}, dataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
	});
	//审核通过
	var checkPass = function() {
		var id = getGridId();
		if (!id) {
			return false;
		}
		 $.ajax({ 
		     url:host+"checkPass?id="+id, 
		     success:callback,
		 }); 
	}
	//批量审核通过
	var batchCheckPass = function() {
		var ids = getGridIds();
		if (!ids) {
			return false;
		}
		 $.ajax({ 
		     url:host+"batchCheckPass?ids="+ids, 
		     success:callback,
		 }); 
	}
	//手动输入帐号审核
	var accountCheck = function() {
		var title = $("title").html() + " - 帐号批量审核";
		var href = host + 'form';
		$("#button-save").unbind("click").click(upCheck);
		openDialogForm(title, href);
	}
	//提交审核帐号
	var upCheck = function(){
		if ($(module_submit_form).form("validate")) {
			$.messager.progress();
			$(module_submit_form).form({
				url : host + 'accountCheck'
			});
			$(module_submit_form).form("submit");
		}
	}
	//导出
	function statExport() {
		var pager=$(module_datagrid).datagrid("getPager");
		var total=$(pager).pagination('options').total;
		if(total){
			var cols=dataGridParamObj.columns[0];
			var exportHead="";
			var exportField="";
			for(var i in cols){
				if(i!=0){
					exportHead=exportHead+cols[i].title+",";
					exportField=exportField+cols[i].field+",";
				}
			}
		var where="";
		var params=$(module_datagrid).datagrid('options').queryParams;
		for(var i in params){
			where+=i+"="+params[i]+"&";
		}
		    $("#exportForm").attr("action",host+'export?'+where);
			$("#exportHead").val(exportHead);
			$("#exportField").val(exportField);
			$("#exportName").val('游戏下载任务审核');
			$("#exportForm").submit();
		}else{
			$.messager.alert('操作失败','无数据！',"warning");
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
					用户手机：<input type="text" name="userMobilePhone" class="easyui-numberbox input_width_default" /> 
					游戏帐号：<input type="text" name="checkGameAccount" class="easyui-textbox input_width_default" /> 
				</div>
				<div class="module_search_input">
					任务名称：<input  class="easyui-combobox" name = "taskId" data-options="valueField: 'id',textField: 'name',url: 'gameDownloadCheck/getTask.html',editable:false" /> 
					审核状态：
					<select class="easyui-combobox input_width_short"  editable="false" name="checkStatus" data-options="required:false">
							<option value="">[全部]</option>
						   <fns:getOptions category="task_gamedownload_check.check_status"  ></fns:getOptions>
					</select> 
				</div>
				<div class="module_search_input">
					审核人：<input type="text" name="checkPerson" class="easyui-textbox input_width_default" /> 
				</div>
				<div class="module_search_input">
					提交时间：
					<input type="text" class="easyui-datebox input_width_default" editable="false" name="begin" data-options=""/>
					至
					<input type="text" class="easyui-datebox input_width_default" editable="false" name="end" data-options=""/>
				</div>
				<div class="module_search_button">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search-rf',plain:false" onclick="gridSearch()">搜索</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reset-rf',plain:false"onclick="gridReset()">清空</a>
				</div>
			</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload-rf',plain:true" onclick="fresh()">刷新</a>
			 <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-excel-rf',plain:true" onclick="statExport()">导出</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok-rf',plain:true" onclick="checkPass(callback)">审核通过</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-unlink-rf',plain:true" onclick="batchCheckPass(callback)">批量审核</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-unlink-rf',plain:true" onclick="accountCheck()">帐号审核</a>

		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>

	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
	<div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save"class="easyui-linkbutton" data-options="iconCls:'icon-ok-rf'">保存</a> 
		<a href="javascript:void(0)" id="button-cancel"class="easyui-linkbutton" data-options="iconCls:'icon-no'" onclick="cancel()">关闭</a>
	</div>
	<div class="hide">
	<form action="" id="exportForm" method="post" target="_blank">
		<input type="hidden" name="exportDatas" id="exportDatas" value=""/>
		<input type="hidden" name="exportHead" id="exportHead" value=""/>
		<input type="hidden" name="exportField" id="exportField" value=""/>
		<input type="hidden" name="exportName" id="exportName" value=""/>
	</form>
	</div>
</body>
</html>



