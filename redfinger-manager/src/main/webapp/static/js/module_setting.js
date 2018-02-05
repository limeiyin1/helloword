/**** 私有 ****/

//数据验证并转换为JSON格式
var validateGridData=function(data){
	if (jQuery.type(data) == 'string') {
		data = eval('(' + data + ')');
	}
	if (data.code && data.code == 304) {
		alert("操作失败," + data.message);
		top.location.href = ctx;
		return false;
	}else{
		return true;
	}
}
// 普通表格数据过滤
var loadFilterForDataGrid = function(data) {
	if(validateGridData(data)){
		data.rows = data.list;
		// 转换map
		for (var i = 0; i < data.rows.length; i++) {
			if (!data.rows[i].map) {
				break;
			}
			var map = data.rows[i].map;
			for ( var key in map) {
				data.rows[i]["map." + key] = map[key];
			}
		}
		$(module_datagrid).datagrid("clearSelections");
		return data;
	}
	return null;
}

//普通表格数据过滤(不分页)
var loadFilterForDataGridNoPage = function(data) {
	if(validateGridData(data)){
		data.rows = data;
		// 转换map
		for (var i = 0; i < data.rows.length; i++) {
			if (!data.rows[i].map) {
				break;
			}
			var map = data.rows[i].map;
			for ( var key in map) {
				data.rows[i]["map." + key] = map[key];
			}
		}
		$(module_datagrid).datagrid("clearSelections");
		return data;
	}
	return null;
}



// 树形表格数据过滤
var loadFilterForTreeGrid = function(data,parentId) {
	if(validateGridData(data)){
		var object = {};
		object.rows = data;
		object.total = data.length;
		var rows = data.rows;
		for (var i = 0; i < data.length; i++) {
			var arr = data[i];
			arr["_parentId"] = arr[parentKey];
		}
		// 转换map
		for (var i = 0; i < object.rows.length; i++) {
			if (!object.rows[i].map) {
				break;
			}
			var map = object.rows[i].map;
			for ( var key in map) {
				object.rows[i]["map." + key] = map[key];
			}
		}
		$(module_datagrid).datagrid("clearSelections");
		return object;
	}else{
		return null;
	}
	
}
//双击显示明细
var specific =function(index, row){
	if(typeof dblClickCallback!='undefined' && dblClickCallback){
		dblClickCallback.apply(null, [index,row]);
	}else{
		var view=$(module_datagrid).parent();
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
			$("#button-save").unbind("click").click(cancel);
			$(module_dialog).dialog({title : title,content:$(module_submit_container).prop("outerHTML"),href:''});
			$(module_dialog).dialog("open");
		}
	}
	
}
/**** 公有 ****/

// 普通表格默认参数
var dataGridParam = {
	fitColumns : true,
	pagination : true,
	striped : true,
	rownumbers : true,
	singleSelect : false,
	idField : 'id',
	pageSize : 20,
	pageList : [ 10, 15, 20, 50, 100, 200,500 ],
	loadFilter : loadFilterForDataGrid,
	onDblClickRow:specific,
	loadMsg : "处理中，请稍后..."
};

// 树形表格默认参数
var treeGridParam = {
	fitColumns : true,
	pagination : false,
	striped : false,
	rownumbers : true,
	singleSelect : true,
	idField : 'id',
	treeField : 'name',
	parentField : "parentId",
	loadFilter : loadFilterForTreeGrid,
	loadMsg : "处理中，请稍后..."
};

// 对话框默认参数
var dialogParam = {
	title : "编辑",
	closed : true,
	resizable : true,
	modal : true,
	width : 500,
	height : 500,
	loadingMessage : "处理中，请稍后..."
};
