/**
 * 
 */
function addTab(url,obj){
	var title=$(obj).text();
	var tb=$('#easyui-tabs').tabs('getTab',title);
	var height=$("#easyui-tabs").height()-30;
	if(tb==null){
		$('#easyui-tabs').tabs('add',{
			title:title,
			iconCls:'icon-menu-rf',
			closable:true,
			content:'<iframe id="'+url.replaceAll("/","_")+'" name="page" width="100%" height="'+height+'" frameborder="0" src="'+ctx+url+'"></iframe>'
		});
	}else{
		$('#easyui-tabs').tabs('select',title);
	}
}
function addTab2(url,title){
	var tb=$('#easyui-tabs').tabs('getTab',title);
	var height=$("#easyui-tabs").height()-33;
	if(tb==null){
		$('#easyui-tabs').tabs('add',{
			title:title,
			iconCls:'icon-menu-rf',
			closable:true,
			content:'<iframe name="page" width="100%" height="'+height+'" frameborder="0" src="'+ctx+url+'"></iframe>'
		});
	}else{
		$('#easyui-tabs').tabs('select',title);
	}
}


$.jBox.messagerDefaults = {
    content: '', /* 信息的内容，不支持前缀标识 */
    title: 'jBox', /* 信息的标题 */
    icon: 'none', /* 信息图标，值为'none'时为不显示图标，可选值有'none'、'info'、'question'、'success'、'warning'、'error' */
    width: 350, /* 信息的高度，值为'auto'或表示像素的整数 */
    height: 'auto', /* 信息的高度，值为'auto'或表示像素的整数 */
    timeout: 3000, /* 信息显示多少毫秒后自动关闭,如果设置为0,则不自动关闭 */
    showType: 'slide', /* 信息显示的类型,可选值有:show、fade、slide */
    showSpeed: 600, /* 信息显示的速度,可选值有:'slow'、'fast'、表示毫秒的整数 */
    border: 0, /* 信息的外边框像素大小,必须是0以上的整数 */
    buttons: {}, /* 信息的按钮 */
    buttonsFocus: 0, /* 表示第几个按钮为默认按钮，索引从0开始 */
    loaded: function (h) { }, /* 窗口加载完成后执行的函数，参数h表示窗口内容的jQuery对象 */
    submit: function (v, h, f) { return true; },
    /* 点击信息按钮后的回调函数，返回true时表示关闭窗口，
    参数有三个，v表示所点的按钮的返回值，h表示窗口内容的jQuery对象，f表示窗口内容里的form表单键值 */
    closed: function () { } /* 信息关闭后执行的函数 */
};
function showMessage(title,content){
	$.jBox.messager(content, title,0);
}