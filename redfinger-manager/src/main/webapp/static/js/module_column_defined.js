//列表自定义
function columnDefined(userId,initColumnMap,pk,flag) {
    $("#module_column_defined_dialog").dialog("clear");
    var html='<div style="margin-left:30px;margin-top:20px;">';
    var allColumn = $('#module_datagrid').datagrid('getColumnFields');
    for(var i in allColumn){
        if(i!=0){
            var value =allColumn[i];
            var text = $('#module_datagrid').datagrid( "getColumnOption" ,value).title;
            html+='<label style="float:left;width:25%;height:30px"><input name="column_defined" type="checkbox" value="'+value+'">'+text+'</label>';
            if(i%4==0){
                html+='<br/>';
            }
        }
    }
    html+='</div>';
    //解绑上一次点击事件并触发点击事件
    $("#buttone-column-defined-save").unbind('click').click(function(){
        var columnStr = "";
        var boolMap = {};
        $('input[name="column_defined"]:checked').each(function(){
            columnStr+=$(this).val();
            columnStr+=",";
            boolMap[$(this).val()] = true;
        });
        if($.isEmptyObject(boolMap)){
            $.messager.alert('操作失败','请至少选择一个！', "warning");
        }else{
            //设置Cookie
        	var cookieValueStr = getCookie(userId,flag);
        	if(cookieValueStr!=null){
        		var map = eval('(' + cookieValueStr + ')');
        		if(map['resizeColumn']){
        			$('#module_datagrid').datagrid('options').fitColumns=false;
        		}
        		map['field'] =columnStr.substring(0,columnStr.length-1);
    			setCookie(userId,JSON.stringify(map),flag);
        	}else{
        		var newStr = '{'+'field'+':'+'\''+columnStr.substring(0,columnStr.length-1)+'\''+'}';
                setCookie(userId,newStr,flag);
        	}
            //更新列状态（隐藏或显示）
            updateColumn(boolMap,allColumn,pk);
            //关闭对话框
            colsDefinedCancel();
        }
    });
    $("#module_column_defined_dialog").dialog({
        title : $("title").html()+" - 列表自定义",
        content : html,
        href : '',
        onBeforeOpen : function (){
            var cookieValueStr = getCookie(userId,flag);
            var columnArr = [];
            if(cookieValueStr!=null){
            	var map = eval('(' + cookieValueStr + ')');
            	if(map['field']){
            		columnArr = map['field'].split(",");
            	}else{
            		checkedColumn(initColumnMap);
            	}
            	//复选框勾选
                if(columnArr.length>0){
                	var boolMap = {};
                    for (var i=0;i<columnArr.length;i++){
                    	boolMap[columnArr[i]] = true;
                    }
                    checkedColumn(boolMap);
                }
            }else{
            	checkedColumn(initColumnMap);
            }
        }
    });
    $("#module_column_defined_dialog").dialog("open");
}

//加载列表自定义
function loadColumnDefined(userId,initColumnMap,pk,flag) {
	var columnArr = [];
    var cookieValueStr = getCookie(userId,flag);
    if(cookieValueStr!=null){
    	var map = eval('(' + cookieValueStr + ')');
    	if(map['field']){
    		columnArr = map['field'].split(",");
    	}
    	if(map['resizeColumn']){
    		$('#module_datagrid').datagrid('options').fitColumns=false;
    		for(var key in map['resizeColumn']){ 
				$('#module_datagrid').datagrid('getColumnOption',key).width = map['resizeColumn'][key];
			}
    		$('#module_datagrid').datagrid();
    	}
    }
    var boolMap = {};
    if(columnArr.length>0){
        for(var i=0;i<columnArr.length;i++){
            var key = columnArr[i];
            boolMap[key] = true;
        }
        //更新列状态（隐藏或显示）
        updateColumn(boolMap,$('#module_datagrid').datagrid('getColumnFields'),pk);
    }else{
    	//更新列状态（隐藏或显示）
        updateColumn(initColumnMap,$('#module_datagrid').datagrid('getColumnFields'),pk);
    }
}

//更新列状态（隐藏或显示）
function updateColumn(boolMap,arr,pk) {
    for (var i = 0; i < arr.length; i++) {
        var key = arr[i];
        if(boolMap[key]){//展示
            $("#module_datagrid").datagrid('showColumn', key);
        }else{//隐藏
        	if(key != pk){
        		$("#module_datagrid").datagrid('hideColumn', key);
        	}
        }
    }
}

//全选
function selectAllColumn() {
    var allColumn = $('#module_datagrid').datagrid('getColumnFields');
	var boolMap = {};
    for (var i=0;i<allColumn.length;i++){
    	boolMap[allColumn[i]] = true;
    }
    //复选框勾选
    checkedColumn(boolMap);
}

//反选
function deselectAllColumn() {
	var boolMap = {};
    //复选框勾选
    checkedColumn(boolMap);
}

//设置Cookie
function setCookie(userId,value,flag){
    var Days = 36135;
    var exp = new Date();
    exp.setTime(exp.getTime() + Days*24*60*60*1000);
    document.cookie = userId +flag + "="+ escape (value) + ";expires=" + exp.toGMTString();
}

//获取Cookie
function getCookie(userId,flag){
    var arr,reg=new RegExp("(^| )"+userId+flag+"=([^;]*)(;|$)");
    if(arr=document.cookie.match(reg))
        return unescape(arr[2]);    
    else
        return null;
}

//删除Cookie
function delCookie(userId,flag){
	var exp = new Date();
	exp.setTime(exp.getTime()-1);
	var value=getCookie(userId,flag);
	if(value!=null)
		document.cookie = userId + flag+ "="+ escape (value) + ";expires=" + exp.toGMTString();
}

//关闭对话框
function colsDefinedCancel() {
	$("#module_column_defined_dialog").dialog("close");
}

//复选框勾选
function checkedColumn(boolMap){
    $("input[name='column_defined']").each(function(){
        if(boolMap[$(this).val()]){
            $(this).attr("checked",true);  
        }else{
        	$(this).attr("checked",false);
        }
    });
}

//重置
function resetColumn(userId,initColumnMap,pk,flag){
	$.messager.confirm('确认？', '重置会将勾选项恢复到初始状态，是否执行该操作?', function(confirm) {
		if (confirm) {
			$('#module_datagrid').datagrid('options').fitColumns=true;
			colsDefinedCancel();
			delCookie(userId,flag);
			$('#module_datagrid').datagrid($.extend({},dataGridParam,dataGridParamObj));
			loadColumnDefined(currentUserId,initColumnMap,pk,flag);
		}
	});
}

//调整列宽度
function resizeColumn(userId,flag){
	$('#module_datagrid').datagrid('options').fitColumns=false;
	var allColumn = $('#module_datagrid').datagrid('getColumnFields');
	var resizeColumnMap ={};
	for(var i in allColumn){
		var key = allColumn[i];
		resizeColumnMap[key] = $('#module_datagrid').datagrid( "getColumnOption" ,key).width;
	}
	var cookieValueStr = getCookie(userId,flag);
	var map ={};
	if(cookieValueStr!=null){
		map = eval('(' + cookieValueStr + ')');
	}
	map['resizeColumn'] = resizeColumnMap;
	setCookie(userId,JSON.stringify(map),flag);
}

/*增强easyui-numberbox较验*/
$(function() {
	$.extend($.fn.validatebox.defaults.rules,{
		number:{// 扩展numberbox, 输入的数不是数字,负数,小数会有相应提示
          validator : function(value) {
              return /^\-\d+\.{1}\d+$|^\-\d+$|\d+\.{1}\d+$|\d+$/gi.test(value);
          },
          message : '只允许输入数字'
        }
	});
	
	$('.easyui-numberbox').numberbox({    
		validType:'number',
	}); 
	
});

 
