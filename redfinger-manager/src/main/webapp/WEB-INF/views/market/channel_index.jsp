<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>渠道管理</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="channelId";  //主键,这个很重要,不能弄错
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField : pk,
		onCheck: function(index,row){
			
		},
		
		//这里field中填写的是实体类中的属性名,不是数据库字段名
		columns:[[
			{width:100,title:'channelId',field:pk,checkbox:true},
			{width:100,title:'渠道名称',field:'channelName',sortable:true},
			{width:100,title:'客户端类型',field:'clientType',sortable:true},
			{width:120,title:'渠道编码',field:'channelCode',sortable:true},
			{width:120,title:'一级渠道',field:'firstGradeName',sortable:true},
			{width:120,title:'二级渠道',field:'secondGradeName',sortable:true},
			{width:100,title:'创建人',field:'creater',sortable:true},
			{width:150,title:'创建时间',field:'createTime',formatter:formatterTime,sortable:true},
			{width:100,title:'修改人',field:'modifier',sortable:true},
			{width:100,title:'修改时间',field:'modifyTime',formatter:formatterTime,sortable:true},
			{width:100,title:'备注',field:'remark',sortable:true},
			{width:100,title:'发现栏默认显示',field:'discoverLimit',sortable:true,formatter:function(value){return getDatagridDict('global.yes_no',value);}},
			{width:100,title:'任务栏默认显示',field:'taskLimit',sortable:true,formatter:function(value){return getDatagridDict('global.yes_no',value);}},
			{width:100,title:'渠道类型',field:'channelType',sortable:true,formatter:function(value){return getDatagridDict('rf_channel_type',value);}},
			{width:100,title:'维护状态',field:'maintStatus',sortable:true,formatter:function(value){return getDatagridDict('rf_channel_version.maint_status',value);}},
			{width:100,title:'状态',field:'status',sortable:true,formatter:function(value){return getDatagridDict('global.enable_status',value);}},
			{width:100,title:'启用状态',field:'enableStatus',sortable:true,formatter : formatterStop},
			]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	
	function subPackage(){
		/* var ids=getGridIds();
 		if(!ids)return false; */
 		var href=host+'subPackageForm';
		var title=$("title").html()+" - 分包操作";
		$("#button-save").unbind("click").click(channelSave);
		openDialogForm(title,href);
	}
	
	function channelSave() {
		if ($('#module_submit_form').form("validate")) {
			$.messager.progress();
			$('#module_submit_form').form("submit");
		}
	}
	
	function subOnePackage(){
		var id=getGridId();
 		if(!id)return false;
 		var href=host+'subOnePackageForm?channelId='+id;
		var title=$("title").html()+" - 安卓单个分包操作";
		$("#button-save").unbind("click").click(channelSave);
		openDialogForm(title,href);
	}
	
	/* function subOneMendPackage(){
		var id=getGridId();
 		if(!id)return false;
 		var href=host+'subOneMendPackageForm?channelId='+id;
		var title=$("title").html()+" - 安卓单个补丁分包操作";
		$("#button-save").unbind("click").click(channelSave);
		openDialogForm(title,href);
	} */
	
	function subMendPackage(){
 		var href=host+'subMendPackageForm';
		var title=$("title").html()+" - 补丁分包操作";
		$("#button-save").unbind("click").click(channelSave);
		openDialogForm(title,href);
	}
	
	/////////////功能添加分割线//////////////////////////
		var getsecondGradeId2sCallback=function (json,fun){
			var themecombo2 =[{'gradeName':'请选择','gradeId':''}]; 
			
			var data;
			data = $.parseJSON(json);
			//data = themecombo2.join(data);
			if(data.length>0){
			$("#secondGradeId2").combobox('loadData',data);//重新加载数据
			$("#secondGradeId2").combobox('select', data[0].gradeId);//选择第一个
			}else{
				$("#secondGradeId2").combobox('loadData',[]);
				$("#secondGradeId2").combobox('setValue',null);
			}
			
		}
		var callback2=getsecondGradeId2sCallback;
 		var getsecondGradeId2s = function (newValue, oldValue){
 		 	var firstGradeId = newValue;
			$.get("${ctx}/market/channel/getSecondGradeIdsHaveAll",{firstGradeId:firstGradeId},callback2);
		}
		
		/////////////功能添加分割线//////////////////////////
	
	
</script>
</head>
<body>
	<!-- 综合查询条件的表格  -->
	   <div id="module_container" >
		<div id="module_search" >
		
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">
			渠道名称：<input type="text" name="channelName" class="easyui-textbox input_width_default"/>
			</div>
			<div class="module_search_input">
			渠道编码：<input type="text" name="channelCode" class="easyui-textbox input_width_default"/>
			</div>
			<div class="module_search_input">
				维护状态：
				<select class="easyui-combobox input_width_short" name="maintStatus" data-options="editable:false" style="width:100px">
					<option value="">[全部]</option>
				    <c:forEach items="${maintStatuss }" var="one">
				    	<option value="${one.key }">${one.value }</option>
				    </c:forEach>
			 	</select>
		  	</div>
		  	<div class="module_search_input">
				客户端类型：
				<select class="easyui-combobox input_width_short" name="clientType" data-options="editable:false" style="width:100px">
					<option value="">[全部]</option>
					<option value="android">android</option>
				  	<option value="win">win</option>
				  	<option value="ios">ios</option>
				  	<option value="win_st">win_st</option>
			 	</select>
		  	</div>
		  	
		  	<div class="module_search_input">
				发现栏是否可用：
				<select class="easyui-combobox input_width_short" editable="false" id="discoverLimit" name="discoverLimit" validType="selectValueRequired['#versionMust']">
					<option value="">[全部]</option>
				  <fns:getOptions category="global.yes_no"></fns:getOptions>
			 	</select>
		  	</div>
		  	
		  	<div class="module_search_input">
				渠道类型：
				<select class="easyui-combobox input_width_short" editable="false" name="channelType" validType="selectValueRequired['#versionMust']">
					<option value="">[全部]</option>
				  <fns:getOptions category="rf_channel_type"></fns:getOptions>
			 	</select>
		  	</div>
		  	
		  	<div class="module_search_input">
				一级渠道：
				<select class="easyui-combobox input_width_short" name="firstGradeId" id="firstGradeId" data-options="editable:false,onChange:getsecondGradeId2s" style="width:100px">
					<option value="">[全部]</option>
					 <c:forEach items="${firstGradeIds}" var="one">
				   	   <option  value="${one.gradeId }">${one.gradeName }</option>
				   </c:forEach>
			 	</select>
		  	</div>
		  	
		  	<div class="module_search_input">
				二级渠道：
				<select class="easyui-combobox input_width_short" name="secondGradeId" id="secondGradeId2" 
				data-options="editable:false,valueField: 'gradeId',textField: 'gradeName'" style="width:100px">
					<option value="">[全部]</option>
			 	</select>
		  	</div>
		  	
		  	
			
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		
		
		<!--增删查改的biaoge  -->
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="add()">新增</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a>
	        <!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="subPackage()">分包</a> -->
	        <!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="subOnePackage()">单个分包</a> --> 
	       <!--  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="subMendPackage()">补丁分包</a>  -->
	        <!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="subAllMendPackage()">全部渠道补丁插件分包</a>  -->
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



