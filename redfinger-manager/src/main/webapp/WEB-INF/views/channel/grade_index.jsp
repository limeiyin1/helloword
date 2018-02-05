<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>渠道分类</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="gradeId";
	var module_datagrid = "#module_datagrid";
	var module_dialog = "#module_dialog";
	var module_search_form = "#module_search_form"
	var module_submit_form = "#module_submit_form";
	var callback = defaultCallback ;
	var dataGridParamObj = {
		url : host + "list",
		idField : pk,
		singleSelect: false,
		checkOnSelect: true, 
		selectOnCheck: true,
		onCheck: function(index,row){
			
		},
		
		onLoadSuccess: function(index,row){
			var isAllowAdd = true;
			var row = $(module_datagrid).datagrid("getSelections");
			//alert(row.length);
			for(var i=0;i<row.length;i++){
				if(row[i].channelGrade!='1'){
					isAllowAdd=false;
					break;
				}
			}
			if(row.length>1){
				isAllowAdd = false;
			}
			if(isAllowAdd){
				$("#addById").linkbutton('enable');
			}else{
				$("#addById").linkbutton('disable');
			}
		},
		
		onSelect: function(index,row){
			var isAllowAdd = true;
			var row = $(module_datagrid).datagrid("getSelections");
			//alert(row.length);
			for(var i=0;i<row.length;i++){
				if(row[i].channelGrade!='1'){
					isAllowAdd=false;
					break;
				}
			}
			if(row.length>1){
				isAllowAdd = false;
			}
			if(isAllowAdd){
				$("#addById").linkbutton('enable');
			}else{
				$("#addById").linkbutton('disable');
			}
		},
		
		onUnselect: function(index,row){
			var isAllowAdd = true;
			var row = $(module_datagrid).datagrid("getSelections");
			//alert(row.length);
			for(var i=0;i<row.length;i++){
				if(row[i].channelGrade!='1'){
					isAllowAdd=false;
					break;
				}
			}
			if(row.length>1){
				isAllowAdd = false;
			}
			if(isAllowAdd){
				$("#addById").linkbutton('enable');
			}else{
				$("#addById").linkbutton('disable');
			}
		},
		
		
		columns : [[ {width : 100,title :'id', field:pk,checkbox : true},
			
			{width : 100,title : '渠道归类名称', field : 'gradeName'},
			
			{width : 100,title : '渠道级别',field : 'channelGrade',formatter :function(value){return getDatagridDict('rf_channel_grade',value);}},
			{width : 100,title : '上级渠道', field : 'parentName'},
			
			{width : 100,title : '排序', field : 'reorder'},
			{width : 100,title : '状态', field : 'enableStatus',sortable : true,formatter : formatterStop},
			{width : 100,title : '创建时间', field : 'createTime',sortable : true,formatter : formatterTime},
			{width : 100,title : '创建人', field : 'creater',sortable : true},
			{width : 100,title : '修改时间', field : 'modifyTime',sortable : true,formatter : formatterTime},
			{width : 100,title : '修改人', field : 'modifier',sortable : true}
		]]
	};
	var dialogParamObj = {
	
	};
	$(function() {
		$(module_datagrid).datagrid($.extend({}, dataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
	});
	
	var getGradeId = function() {
		var row = $(module_datagrid).datagrid("getSelections");
		
		if(row.length>1){
			alert("最多可选中一行");
			return;
		}
		return row==''?null:row[0].gradeId;
	}
	
	var addById = function() {
		var row = $(module_datagrid).datagrid("getSelections");
		if(row.length>1){
			alert("最多可选中一行");
			return;
		}
		var id = row==''?null:row[0].gradeId;
		var title = $("title").html() + " - 新增";
		var href;
		if(!id){
		 	href = host + 'form';
		}else{
			href = host + 'form?pk=' + id;
		}
		$("#button-save").unbind("click").click(save);
		$("#module_dialog").dialog({title : title,href: href,width: 600,height: 480,top:0,left:200});
		$("#module_dialog").dialog("open");
	}
	
	var del2 = function(callback) {
		var ids = getGridIds();
		if (!ids) {
			return false;
		}
		$.messager.confirm('确认？', '确认删除此数据?删除一级将把它的子级删除!', function(confirm) {
			if (confirm) {
				ajaxPost("delete", {
					ids : ids
				}, callback);
			}
		});
	}
	
	
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container">
		<div id="module_search">
			<form id="module_search_form" class="easyui-form" method="post">
				<div class="module_search_input">
					渠道级别:
					<select class="easyui-combobox"  editable="false" name="channelGrade" data-options="required:true">
						<option value="">[全部]</option>
						<fns:getOptions category="rf_channel_grade"/>
					</select> 
					<!-- 消息内容：
					<input type="text" name="templateText" class="easyui-textbox input_width_default" />   -->
				</div>
				<div class="module_search_input">
				上级渠道：
				<select class="easyui-combobox input_width_short" name="firstGradeId" id="firstGradeId" data-options="editable:false" style="width:100px">
					<option value="">[全部]</option>
					 <c:forEach items="${firstGradeIds}" var="one">
				   	   <option  value="${one.gradeId }">${one.gradeName }</option>
				   </c:forEach>
			 	</select>
				
				</div>
				
				
				
				
				<div class="module_search_button">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
				</div>
			</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-add-rf" plain="true" id="addById" onclick="addById()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-remove-rf" plain="true" onclick="del2(callback)">删除</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
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



