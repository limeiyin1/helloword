<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>图片模块编辑</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('form[name=module_submit_form_module]').form({
			url : host + 'save',
			success : callback
		});
		
		var module_picture_datagrid = "#module_picture_datagrid";
		var module_picture_dialog = "#module_picture_dialog";
		var module_picture_search_form = "#module_search_form";
		var module_submit_form = "#module_submit_form";
		var module_submit_form_picture = "form[name=module_submit_form_picture]";
		var moduleId='${moduleBean.moduleId}';
		var url = moduleId==''?null:"picture/list?moduleId="+moduleId;
		var _pictureDataGridParamObj = {
			url : url,
			idField : "pictureId",
			onCheck : function(row) {
			},
			columns : [[
				{width : 100,title : 'id',field : 'pictureId',checkbox : true},
				{width : 100,title : '图片名称',	field : 'pictureName',sortable : true},
				{width : 100,title : '播放时间',	field : 'playTime',sortable : true},
				{width : 100,title : '排序',	field : 'reorder',sortable : true},
				{width : 150,title : '模块',	field : 'map.moduleName',sortable : true},
				{width : 150,title : 'ANDROID端展示图片地址',field : 'map.androidPictureUrl',sortable : true},
				{width : 150,title : 'PC端展示图片地址',field : 'map.pcPictureUrl',sortable : true},
				{width : 150,title : '开始时间',field : 'startTime',	sortable : true,formatter : formatterTime},
				{width : 150,title : '结束时间',field : 'endTime',sortable : true,formatter : formatterTime},
				{width : 100,title : '状态',	field : 'status',sortable : true,formatter : formatterStop},
				{width : 100,title : '启用状态',	field : 'enableStatus',sortable : true,formatter : formatterStop}
			]]
		};
		var _pictureDialogParamObj = {
	
		};
		
		// 普通表格默认参数
		var _pictureDataGridParam = {
			fitColumns : true,
			pagination : true,
			striped : true,
			rownumbers : true,
			singleSelect : false,
			idField : 'id',
			pageSize : 20,
			pageList : [ 10, 15, 20, 50, 100, 200 ],
			loadFilter : loadFilterForDataGrid,
			onDblClickRow:_pictureDblClickCallback,
			loadMsg : "处理中，请稍后..."
		};
		
		$(function() {
			$(module_picture_datagrid).datagrid($.extend({}, _pictureDataGridParam, _pictureDataGridParamObj));
			$(module_picture_dialog).dialog($.extend({}, dialogParam, _pictureDialogParamObj));
		});
		
		function addPicture(){
			var title = $("title").html() + " - 新增图片";
			var href = host+'pictureform?picture.moduleId=${moduleBean.moduleId}';
			$("#button-save-pic").show().unbind("click").click(savePicture);
			$(module_picture_dialog).dialog({title : title, href: href});
			$(module_picture_dialog).dialog("open");
		}
		
		function savePicture(){
			if ($(module_submit_form_picture).form("validate")) {
				$.messager.progress();
				$(module_submit_form_picture).form({
					url : host+'picturesave',
					success : pictureCallback
				});
				$(module_submit_form_picture).form("submit");
			}
		}
		
		function editPicture(){
			var id = getPictureGridId();
			if (!id) {
				return false;
			}
			var title = $("title").html() + " - 编辑图片";
			var href = host+'pictureform?picture.pictureId='+id;
			$("#button-save-pic").show().unbind("click").click(savePicture);
			$(module_picture_dialog).dialog({title : title, href: href});
			$(module_picture_dialog).dialog("open");
		}
		
		function startPicture(){
			var ids = getPictureGridIds();
			if (!ids) {
				return false;
			}
			ajaxPost("picturestart", {ids : ids}, pictureCallback);
		}
		
		function stopPicture(){
			var ids = getPictureGridIds();
			if (!ids) {
				return false;
			}
			ajaxPost("picturestop", {ids : ids}, pictureCallback);
		}
		
		function delPicture(){
			var ids = getPictureGridIds();
			if (!ids) {
				return false;
			}
			$.messager.confirm('确认？', '确认删除此数据?', function(confirm) {
				if (confirm) {
					ajaxPost("picturedelete", {ids : ids}, pictureCallback);
				}
			});
		}
		
		function pictureCancel(){
			$(module_picture_dialog).dialog("close");
		}
		
		function freshPicture(){
			$("#module_picture_datagrid").datagrid("reload");
		}
		
		function pictureCallback(data){
			$.messager.progress('close');
			if (jQuery.type(data) == 'string') {
				data = eval('(' + data + ')');
			}
			var code = parseInt(data.code);
			switch (code) {
			case 200:
				pictureCancel();
				// 调用回调函数
				freshPicture();
				break;
			case 500:
				$.messager.alert('操作失败', '操作失败，请联系管理员！', "error");
				break;
			case 501:
				$.messager.alert('操作失败', data.message, "warning");
				break;
			case 304:
				alert("操作失败，" + data.message);
				top.location.href = ctx;
				break;
			}
		}
		
		//获取单条被选择数据，如果不符合弹出提示对话框
		var getPictureGridId = function() {
			var row = $(module_picture_datagrid).datagrid("getSelections");
			if (row == '') {
				$.messager.alert('提示', '请选择记录！', "info");
				return null;
			} else if (row.length > 1) {
				$.messager.alert('提示', '请选择其中一条记录！', "info");
				return null;
			} else {
				return row[0]['pictureId'];
			}
		}
		//获取多条被选择数据，数据用逗号隔开，如果不符合弹出提示对话框
		var getPictureGridIds = function() {
			var row = $(module_picture_datagrid).datagrid("getSelections");
			if (row == '') {
				$.messager.alert('提示', '请选择记录！', "info");
				return null;
			} else {
				var ids = [];
				for (var i = 0; i < row.length; i++) {
					ids[i] = row[i]['pictureId']
				}
				return ids.join(",");
			}
		}
		
		function _pictureDblClickCallback(index, row){
			var view=$(module_picture_datagrid).parent();
			if($(view).hasClass("datagrid-view")){
				//获取头
				var heads=[];
				$(view).find(".datagrid-header-row").find(".datagrid-cell").each(function(){
					heads.push($(this).text());
				});
				//获取内容
				var bodys=[];
				$(view).find(".datagrid-body").find(".datagrid-row[datagrid-row-index="+index+"]").find(".datagrid-cell").each(function(){
					bodys.push($(this).html());
				});
				var module_submit_container=$('<div id="module_submit_container"></div>'); 
				var form=$('<form id="module_submit_form" class="easyui-form">');
				var easyui_table=$('<table class="easyui-table" id="module_submit_table"></table>');
				easyui_table.appendTo(form);
				form.appendTo(module_submit_container);
				for(var i in heads){
					var tr=$('<tr><td class="td1">'+heads[i]+':</td><td class="td2">'+bodys[i]+'</td></tr>');
					easyui_table.append(tr);
				}
				var title = $("title").html() + " - 明细";
				$("#button-save-pic").hide();
				$("#module_picture_dialog").dialog({title : title,content:$(module_submit_container).prop("outerHTML"),href:''});
				$("#module_picture_dialog").dialog("open");
			}
		}
	</script>
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post" name="module_submit_form_module">
			<c:if test="${not empty moduleBean.moduleId}">
				<input type="hidden" name="moduleId" value="${moduleBean.moduleId}">
			</c:if>
			<table id="module_submit_table">
				<tr>
					<td class="td1">模块名称:</td>
					<td class="td2"><input class="easyui-textbox" type="text" name="moduleName" value="${moduleBean.moduleName}" data-options="required:true,validType:'length[0,100]'" /></td>
				</tr>
				<tr>
					<td class="td1">模块编码:</td>
					<td class="td2">
						<c:if test="${not empty bean.moduleCode }">
							${bean.moduleCode}<input type="hidden" name="moduleCode" value="${moduleBean.moduleCode}">
						</c:if>
						<c:if test="${empty bean.moduleCode }">
							<input class="easyui-textbox" type="text" name="moduleCode"	value="${moduleBean.moduleCode }" data-options="required:true,validType:'code'" />
						</c:if>
					</td>
				</tr>
				<tr>
					<td class="td1">备注:</td>
					<td class="td2"><input class="easyui-textbox" name="remark" value="${moduleBean.remark}" data-options="multiline:true,validType:'length[0,1000]'"	style="height: 100px" /></td>
				</tr>
				<c:if test="${moduleBean != null}">
				<tr>
					<td colspan=2>
						<div id="#module_picture_toolbar" class="easyui-toolbar">
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="addPicture()">新增</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="editPicture()">编辑</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start-rf" plain="true" onclick="startPicture()">启用</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop-rf" plain="true" onclick="stopPicture()">禁用</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove-rf" plain="true" onclick="delPicture()">删除</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload-rf" plain="true" onclick="freshPicture()">刷新</a>
						</div>
						<table id="module_picture_datagrid" toolbar="#module_picture_toolbar" style="height:140px"></table>
					</td>
				</tr>
				</c:if>
			</table>
		</form>
	</div>
	
</body>
</html>



