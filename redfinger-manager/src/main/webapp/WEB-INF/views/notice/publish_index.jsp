<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>用户</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
 
	var pk="userId";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField : 'userId',
		onRowContextMenu: onRowContextMenu, //右键。
		onCheck : function(index,row) {
			//状态为绑定时不能绑定
		
			},
		columns:[[
			{width:100,title:'id',field:'userId',checkbox:true},
			{width:100,title:'会员ID',field:'externalUserId'},
			{width:100,title:'登录帐号',field:'checkboxValue',formatter:function(value, row) {return row.userId;}},
			{width:100,title:'用户名',field:'userName',sortable:true},
			{width:100,title:'用户手机',field:'userMobilePhone',sortable:true},
			{width:100,title:'邮箱',field:'userEmail',sortable:true},
			{width:100,title:'申请时间',field:'createTime',sortable:true,formatter:formatterTime},
			{width:100,title:'最近登录时间',field:'loginTime',sortable:true,formatter:formatterTime},
			{width:100,title:'状态',field:'status',sortable:true,formatter:formatterStop}
		]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	
	function notice(){

		var ids=getGridIdsAll();
		if(!ids)return false;
		var title=$("title").html()+" - 发布公告";
		var href=host+'noticeForm?ids='+ids;
		$("#button-save").unbind("click").click(noticeSave);
		openDialogFormNotice(title,href);
	}
	
	function noticeUmeng(){
		var userId = $('#userId').val();
		var userMobilePhone = $('#userMobilePhone').val();
		var userEmail = $('#userEmail').val();
		var beginTimeStr = $('#beginTimeStr').val();
		var endTimeStr = $('#endTimeStr').val();
		
		if(''==userId && ''==userMobilePhone && ''==userEmail && ''==beginTimeStr && ''==endTimeStr){
			$.messager.alert('提示', '请填写查询条件！', "info");
			return null;
		}
		/* var ids=getGridIdsAll();
		if(!ids)return false; */
		
		var params = "&userId="+userId+"&userMobilePhone="+userMobilePhone+"&userEmail="+userEmail+"&beginTimeStr="+beginTimeStr+"&endTimeStr="+endTimeStr;
		var title=$("title").html()+" - 推送公告";
		var href=host+'noticeUmeng?'+params;
		$("#button-save").unbind("click").click(noticeSave);
		openDialogFormNotice(title,href);
	}
	
	function noticeSave(){
		if($('#easyui-form').form("validate")){
			$.messager.progress();
			$('#easyui-form').form("submit");
		}
	}
 function openDialogFormNotice(title, href) {
		$(module_dialog).dialog({title : title,href: href,width:720,height:500,maximizable:true,left:screen.width/6});
		$(module_dialog).dialog("open");
	
	}
 function getGridIdsAll() {
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
 
 function onRowContextMenu(e, rowIndex, rowData){
	    e.preventDefault();
	    var selected=$("#module_datagrid").datagrid('getRows'); //获取所有行集合对象
	     var idValue = selected[rowIndex].uid;
	     $(this).datagrid('selectRecord', idValue);  //通过获取到的id的值做参数选中一行
	    $('#mm').menu('show', {
	        left:e.pageX,
	        top:e.pageY
	    });       
	}
 
 var dataGridParam = {
			fitColumns : true,
			pagination : true,
			striped : true,
			rownumbers : true,  
			singleSelect : false,
			idField : 'id',
			pageSize : 20,
			pageList : [ 20, 100, 200,500 ],
			loadFilter : loadFilterForDataGrid,
			loadMsg : "处理中，请稍后..."
		};
 	function formatterPop(value,row,index){
	 	return getDatagridDict('rf_notice.pop_status',value);
	}
 	//发布后台部分
  	function add_search(){
		
		var title = $("title").html() + " - 发送部分用户";
		
		var href = host + 'sendPartsForm';
		$("#button-save").unbind("click").click(noticeSave);
		openDialogFormNotice(title, href);
	} 
	
	 //发布后台所有
  	function add_search_total(){
  		$.messager.confirm('确认？', '确定对所有用户发送公告?', function(confirm) {
			if (confirm) {
				var total=$(module_datagrid).datagrid("getData").total;
				var title = $("title").html() + " - 发送所有用户";
				var arr = $(module_search_form).serializeArray();
				
				var href = host + 'noticeTotalForm';
				
				$("#button-save").unbind("click").click(noticeSave);
				openDialogFormNotice(title, href);
			}
		});
  	} 
  	
  	 //推送后台所有
  	function umeng_search(){
		var total=$(module_datagrid).datagrid("getData").total;
		if(total<=0){
			$.messager.alert('提示', '无记录，可选择其他查询条件', "info");
			return false;
		}
		var title = $("title").html() + " - 发送所有用户";
		var arr = $(module_search_form).serializeArray();
		
		var obj ="?";
		$.each(arr, function(i, field) {
		obj+=field.name+"="+field.value+"&";
		});
		var href = host + 'houUmeng'+obj;
		
		$("#button-save").unbind("click").click(noticeSave);
		openDialogFormNotice(title, href);
	}
	
	function noticeGroup(){
		var title = $("title").html() + " - 按组推送公告";
		
		var href = host + 'umengGroup';
		$("#button-save").unbind("click").click(noticeSave);
		openDialogFormNotice(title, href);
	}
	
	 

</script>
<style type="text/css">

</style>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
		    <div class="module_search_input">   会员ID:<input type="text" id="externalUserId" name="externalUserId"  class="easyui-numberbox input_width_short" />&nbsp;</div>
		    <div class="module_search_input">   登录帐号:<input type="text" id="userId" name="userId" maxlength="9" class="easyui-validatebox input_width_short" onkeyup="this.value=this.value.replace(/\D/g,'')"/>&nbsp;</div>
		     <div class="module_search_input">  手机号:<input type="text" id="userMobilePhone" name="userMobilePhone" class="easyui-textbox input_width_default"/>&nbsp;</div>
		    <div class="module_search_input"> Email:<input type="text" id="userEmail" name="userEmail" class="easyui-textbox input_width_default"/>&nbsp;</div>
			<div class="module_search_input">
				登录时间：<input type="text" id="beginTimeStr" name="beginTimeStr" class="easyui-datetimebox" data-options="editable:false,showSeconds:true"/>
				至<input type="text" id="endTimeStr" name="endTimeStr" class="easyui-datetimebox"data-options="editable:false,showSeconds:true" />
			</div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-star-rf" plain="true" onclick="notice(callback)">发布当前选中</a>
			<a href="javascript:void(0)" id="add_search" class="easyui-linkbutton" iconCls="icon-star-rf" plain="true" onclick="add_search()">发布部分用户</a>
	        <a href="javascript:void(0)" id="add_search" class="easyui-linkbutton" iconCls="icon-star-rf" plain="true" onclick="add_search_total()">发布所有用户</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
	       
	        <!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-star-rf" plain="true" onclick="noticeUmeng(callback)">推送部分用户</a>
	        <a href="javascript:void(0)" id="add_search" class="easyui-linkbutton" iconCls="icon-star-rf" plain="true" onclick="umeng_search()">推送所有用户</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-star-rf" plain="true" onclick="noticeGroup(callback)">按组推送公告</a> -->
	        
	       <!--  	<a href="/redfinger-manager/notice/notice" class="easyui-linkbutton" iconCls="icon-search-rf" plain="true" >查看公告</a>
	        	<a href="/redfinger-manager/notice/userNotice" class="easyui-linkbutton" iconCls="icon-search-rf" plain="true" >查看公告记录</a> -->
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"   ></table>
	</div>
	
	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
    <div id="module_dialog_button" >
		<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a>
		<a href="javascript:void(0)" id="button-cancel" class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
	
		
	<div id="mm" class="easyui-menu" style="width:120px;">
    <div onClick="fresh()" data-options="iconCls:'icon-reload'">刷新</div>
    <div onClick="notice(callback)" data-options="iconCls:'icon-add'">发布公告</div>
    </div>
</body>
</html>



