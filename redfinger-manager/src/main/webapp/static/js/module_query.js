//刷新
var fresh = function() {
	try{
		$(module_datagrid).treegrid("reload");
	}catch(e){
		$(module_datagrid).datagrid("reload");
	}
}
//grid搜索
var gridSearch = function() {
	var arr = $(module_search_form).serializeArray();
	var obj = {};
	$.each(arr, function(i, field) {
		obj[field.name] = field.value;
	});
	try{
		$(module_datagrid).treegrid("reload",obj);
	}catch(e){
		$(module_datagrid).datagrid("reload",obj);
	}
}
// grid搜索条件清除
var gridReset = function() {
	$(module_search_form).form("reset");
}

$(function(){
	$(document).bind("keydown","return",function(){
		gridSearch();
		return false;
	}).bind("keydown","esc",function(){
		cancel();
		return false;
	}).bind("keydown","del",function(){
		gridReset();
		return false;
	});
	$("input").bind("keydown","return",function(){
		gridSearch();
		return false;
	}).bind("keydown","esc",function(){
		cancel();
		return false;
	}).bind("keydown","del",function(){
		gridReset();
		return false;
	});
});


