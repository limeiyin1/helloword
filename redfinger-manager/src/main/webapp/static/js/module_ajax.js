// 异步post提交
var ajaxPost = function(method, pram, callback) {
	$.messager.progress();
	callback = callback ? callback : defaultCallback;
	$.post(host + method, pram, callback);
}
// 普通新增
var add = function() {
	var title = $("title").html() + " - 新增";
	var href = host + 'form';
	$("#button-save").unbind("click").click(save);
	openDialogForm(title, href);
}
// 树形新增
var addTree = function() {
	var id = getGridId();
	if (!id)
		return false;
	var title = $("title").html() + " - 新增";
	var href = host + 'form?' + parentKey + '=' + id;
	$("#button-save").unbind("click").click(save);
	openDialogForm(title, href);
}
// 编辑
var edit = function() {
	var id = getGridId();
	if (!id) {
		return false;
	}
	var title = $("title").html() + " - 编辑";
	var href = host + 'form?' + pk + '=' + id;
	$("#button-save").unbind("click").click(update);
	openDialogForm(title, href);
}
// 详情
var detail = function() {
	var id = getGridId();
	if (!id) {
		return false;
	}
	var title = $("title").html() + " - 详情";
	var href = host + 'detail?' + pk + '=' + id;
	$("#button-save").unbind("click").click(cancel);
	openDialogForm(title, href);
}
// 保存
var save = function() {
	if ($(module_submit_form).form("validate")) {
		$.messager.progress();
		$(module_submit_form).form({
			url : host + 'save'
		});
		$(module_submit_form).form("submit");
	}
}
var update = function() {
	if ($(module_submit_form).form("validate")) {
		$.messager.progress();
		$(module_submit_form).form({
			url : host + 'update'
		});
		$(module_submit_form).form("submit");
	}
}
// 启用
var start = function(callback) {
	var ids = getGridIds();
	if (!ids) {
		return false;
	}
	ajaxPost("start", {
		ids : ids
	}, callback);
}
// 禁用
var stop = function(callback) {
	var ids = getGridIds();
	if (!ids) {
		return false;
	}
	ajaxPost("stop", {
		ids : ids
	}, callback);
}
// 删除
var del = function(callback) {
	var ids = getGridIds();
	if (!ids) {
		return false;
	}
	$.messager.confirm('确认？', '确认删除此数据?', function(confirm) {
		if (confirm) {
			ajaxPost("delete", {
				ids : ids
			}, callback);
		}
	});
}
//重置表单
var resetForm=function(){
	$(module_submit_form).form("reset");
}
//提交表单
var submitForm=function(){
	if ($(module_submit_form).form("validate")) {
		$.messager.progress();
		$(module_submit_form).form({
			url : host + 'update'
		});
		$(module_submit_form).form("submit");
	}
}
