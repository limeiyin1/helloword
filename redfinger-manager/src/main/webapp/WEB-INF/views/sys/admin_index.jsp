<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>用户</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="adminCode";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField :'adminCode',
		onCheck: function(row){
			
		},
		columns:[[
			{width:100,title:'id',field:'adminCode',checkbox:true},
			{width:100,title:'管理员用户名',field:'adminName',sortable:true},
			{width:100,title:'管理员帐号',field:'checkboxValue',formatter:function(value, row) {return row.adminCode;}},
			{width:100,title:'联系电话',field:'adminPhone',sortable:true},
			{width:100,title:'部门名称',field:'map.orgName'},
			{width:100,title:'QQ',field:'adminQq',sortable:true},
			{width:100,title:'描述',field:'remark'},
			{width:100,title:'角色',field:'map.roleNames'},
			{width:100,title:'状态',field:'enableStatus',sortable:true,formatter:formatterStop}
		]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	function role(){
		var id=getGridId();
		if(!id)return false;
		var title=$("title").html()+" - 赋予用户组";
		var href=host+'roleForm?adminCode='+id;	
		$("#button-save").unbind("click").click(roleSave);
		openDialogForm(title,href);
	}
	function roleSave(){
		if($('#easyui-form').form("validate")){
			$.messager.progress();
			$('#easyui-form').form("submit");
		}
	}
	//重置密码
	function resetPwd() {
		var ids = getGridIds();
		if (!ids) {
			return false;
		}
		$.messager.confirm('确认？', '确认重置密码?', function(confirm) {
				if (confirm) {
				ajaxPost("resetPwd", {
					ids : ids
				}, callback);
			 }
		});
	}
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">名称：<input type="text" name="adminName" class="easyui-textbox input_width_default"/></div>
			<div class="module_search_input">账号：<input type="text" name="adminCode" class="easyui-textbox input_width_default"/></div>
			<div class="module_search_input">部门：
		        <select class="easyui-combobox input_width_default" editable="false" name="orgCode" style="width: 135px">
					<option value="">[全部]</option>
					<c:forEach items="${sysOrgList}" var="one">
						<option value="${one.orgCode}">${one.orgName}</option>
					</c:forEach>
				</select>
			</div>
			<div class="module_search_input">角色：
		        <select class="easyui-combobox input_width_default" editable="false" name="roleCode" style="width: 120px">
					<option value="">[全部]</option>
					<c:forEach items="${roleList}" var="one">
						<option value="${one.roleCode}">${one.roleName}</option>
					</c:forEach>
				</select>
			</div>
			<div class="module_search_input">状态：
		        <select class="easyui-combobox input_width_default" editable="false" name="enableStatus">
					<option value="">[全部]</option>
					<fns:getOptions category="global.enable_status"/>
				</select>
			</div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="add()">新增</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-star-rf" plain="true" onclick="role()">赋予角色</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a>
	        <c:if test="${not empty sessionScope.permission.button_sys_admin_resetpwd}">
	        <a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-undo" plain="true" onclick="resetPwd()">重置密码</a>
	        </c:if>
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



