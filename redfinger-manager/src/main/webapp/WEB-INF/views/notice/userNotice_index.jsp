<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>公告记录</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
var pk="userNoticeId";
var module_datagrid = "#module_datagrid";
var module_dialog = "#module_dialog";
var module_search_form = "#module_search_form"
var module_submit_form = "#module_submit_form";
var callback = defaultCallback ;
var formatterStatus=function(value,row,index){
	return getDatagridDict('rf_user_notice.user_notice_status',value);
}

var dataGridParamObj = {
	url : host + "list",
	idField : 'userNoticeId',
	//onRowContextMenu: onRowContextMenu, //右键。
	onCheck : function(row) {
	},
	columns : [[ {width : 100,title : 'id',field : 'userNoticeId',checkbox : true},
	             {width:100,title:'会员ID',field:'map.externalUserId'},
	             {width:100,title:'用户手机',field:'map.userMobilePhone'},
	             {width:100,title:'用户邮箱',field:'map.userEmail'},
	             {width:100,title:'公告标题',field:'map.noticeTitle'},
	             {width:100,title:'是否弹出',field:'map.popStatus',formatter:function(value){return getDatagridDict('rf_notice.pop_status',value);}},
	             {width:100,title:'内容',field:'map.noticeContent'},
	             {width:100,title:'状态',field:'enableStatus',sortable:true,formatter:formatterStop},
	             {width:100,title:'创建人',field:'map.noticeCreater'},
	             {width:100,title:'创建时间',field:'noticeCreateTime',sortable:true,formatter:formatterTime},
                 {width:100,title:'发布状态',field:'userNoticeStatus',sortable:true,formatter:formatterStatus},
	 ]]
};
var dialogParamObj = {

};
$(function() {
	$(module_datagrid).datagrid(
			$.extend({}, dataGridParam, dataGridParamObj));
	$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
	$.extend($.fn.validatebox.defaults.rules,{
		number:{// 验证数字
          validator : function(value) {
              return /^[0-9]{1,9}$/gi.test(value);
          },
          message : '只允许1-9位的正整数'
        }
	});
});

function user(){
	var ids=getGridIds();
	if(!id)return false;
	var title=$("title").html()+" - 发布公告";
	var href=host+'userForm1?ids='+ids;
	$("#button-save").unbind("click").click(userNoticeSave);
	openDialogForm(title,href);
}
function userPhoneSave(){
	if($('#easyui-form').form("validate")){
		$.messager.progress();
		$('#easyui-form').form("submit");
	}
}

function getGridIdsAll() {
	var row = $(module_datagrid).datagrid("getSelections");
	if (row == '') {
		return 'all';
	} else {
		var ids = [];
		for (var i = 0; i < row.length; i++) {
			ids[i] = row[i][pk]
		}
		return ids.join(",");
	}
}
function onRowContextMenu(e, rowIndex, rowData){
    e.preventDefault();
    var selected=$("#module_datagrid").datagrid('getRows'); //获取所有行集合对象
     var idValue = selected[rowIndex].userNoticeId;
     $(this).datagrid('selectRecord', idValue);  //通过获取到的id的值做参数选中一行
    $('#mm').menu('show', {
        left:e.pageX,
        top:e.pageY
    });       
}

//grid搜索
var gridSearchValidate = function() {
	var arr = $(module_search_form).serializeArray();
	var obj = {};
	$.each(arr, function(i, field) {
		obj[field.name] = field.value;
	});
	if(!($('#module_search_form').form("validate"))){
		return false;
		}
	try{
		$(module_datagrid).treegrid("reload",obj);
	}catch(e){
		$(module_datagrid).datagrid("reload",obj);
	}
}

</script>
</head>
<body >
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
			<form id="module_search_form" class="easyui-form" method="post">
				 <div class="module_search_input">
				    手机号码:<input class="easyui-textbox" type="text" name="phone" data-options="" />
               </div>
			  <div class="module_search_input">
			                弹出状态:<select  class="easyui-combobox" name="pop" style="width:70px;" data-options="editable:false">  
	                		<option value="">[全部]</option> 
		                      <fns:getOptions category="rf_notice.pop_status"></fns:getOptions>
                    </select>
               </div>
			   <div class="module_search_input">
			                发布状态:<select  class="easyui-combobox" name="userNoticeStatus" style="width:70px;" data-options="editable:false">  
	                		<option value="">[全部]</option> 
		                      <fns:getOptions category="rf_user_notice.user_notice_status"></fns:getOptions>
                    </select>
               </div>
                <div class="module_search_input">
               			标题：<input type="text" name="title"class="easyui-textbox input_width_default" />
				</div>
				 <div class="module_search_input">
               			内容：<input type="text" name="content"class="easyui-textbox input_width_default" />
				</div>
				
				<div class="module_search_input">
				创建时间：<input type="text" name="beginTimeStr" class="easyui-datetimebox" data-options="editable:false,showSeconds:true"/>
				至<input type="text"  name="endTimeStr" class="easyui-datetimebox"data-options="editable:false,showSeconds:true" />
			    </div>
			    <div class="module_search_input">
					会员ID：<input type="text" name="externalUserId" data-options="validType:'number'"" class="easyui-textbox input_width_default"/>
			    </div>
				<div class="module_search_button">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-search-rf" plain="false" onclick="gridSearchValidate()">搜索</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
				</div>
			</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
			
		<!-- 	<a href="/redfinger-manager/notice/publish" class="easyui-linkbutton" iconCls="icon-search-rf" plain="true" >发布公告</a>
				<a href="/redfinger-manager/notice/notice" class="easyui-linkbutton" iconCls="icon-search-rf" plain="true" >查看公告</a> -->
	        	
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar" ></table>
	</div>

	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
	<div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save"
			class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a> <a
			href="javascript:void(0)" id="button-cancel"
			class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
	
	<div id="mm" class="easyui-menu" style="width:120px;">

</body>
</html>



