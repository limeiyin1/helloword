//自适应宽和高
var autoWidthHeight = function() {
	if (typeof module_datagrid!='undefined' && $(module_datagrid)) {
		var height = $("#module_container").outerHeight() - $("#module_search_form").height();
		var width = $("#module_container").outerWidth();
		$(module_datagrid).css("height", height).css("width", width);
	}
}
//打开编辑对话框
var openDialogForm = function(title, href) {
	$(module_dialog).dialog({title : title,href: href});
	$(module_dialog).dialog("open");	
}

//关闭对话框
var cancel = function() {
	if(typeof module_dialog!='undefined' && $(module_dialog)){
		$(module_dialog).dialog("close");
		$('.tooltip.tooltip-right').hide(); 
	}
}


//获取单条被选择数据，如果不符合弹出提示对话框
var getGridId = function() {
	var row = $(module_datagrid).datagrid("getSelections");
	if (row == '') {
		$.messager.alert('提示', '请选择记录！', "info");
		return null;
	} else if (row.length > 1) {
		$.messager.alert('提示', '请选择其中一条记录！', "info");
		return null;
	} else {
		return row[0][pk];
	}
}
//获取单条被选择数据，未选中数据不弹出提示框
var getId = function() {
	var row = $(module_datagrid).datagrid("getSelections");
	return row==''?null:row[0].id;
}
//获取多条被选择数据，数据用逗号隔开，如果不符合弹出提示对话框
var getGridIds = function() {
	var row = $(module_datagrid).datagrid("getSelections");
	if (row == '') {
		$.messager.alert('提示', '请选择记录！', "info");
		return null;
	} else {
		var ids = [];
		for (var i = 0; i < row.length; i++) {
			ids[i] = row[i][pk]
		}
		return ids.join(",");
	}
}

//默认修改数据后的回调函数
var defaultCallback = function(data) {
	innerCallback(data, fresh);
}
//配置文件编辑的回调函数
var configCallback= function(data){
	innerCallback(data, function(){
		$.messager.alert('系统提示','操作成功！',"info",function(){
			window.location.href=window.location.href;
		});
	});
}
// 回调函数
var innerCallback = function(data, fun) {
	$.messager.progress('close');
	if (jQuery.type(data) == 'string') {
		data = eval('(' + data + ')');
	}
	var code = parseInt(data.code);
	switch (code) {
	case 200:
		// $.messager.alert('系统提示','操作成功！',"info");
		cancel();
		// 调用回调函数
		fun.apply();
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

$.extend($.fn.validatebox.defaults.rules, {    
    code: {    
        validator: function(value){    
        	var regx=/^[a-zA-Z0-9_-]+$/;
            return regx.test(value);
        },    
        message: '只能输入字母数字-_'   
    }    
});


//打开自定义宽度编辑对话框
var openWidthDialogForm = function(title, href,width) {
	$(module_dialog).dialog({title : title,href: href,width:width});
	$(module_dialog).dialog("open");
}

