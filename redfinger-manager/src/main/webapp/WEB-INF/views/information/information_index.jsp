<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>资讯信息</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="id";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form"; 
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField :pk,
		onCheck: function(row){
			
		},
		columns:[[
			{width:100,title:'id',field:pk,checkbox:true},
			{width:100,title:'资讯标题',field:'informationTitle',sortable:true},
			{width:100,title:'资讯类型',field:'informationType',sortable:true},
			{width:100,title:'客户端类型',field:'clientType',sortable:true},
			{width:50,title:'排序',field:'reorder',sortable:true},
			
			{width:100,title:'创建时间',field:'createTime',sortable:true,formatter:formatterTime},
			{width:100,title:'状态',field:'enableStatus',sortable:true,formatter:formatterStop}
			
		]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	
	
	var addInformation = function() {
		var title = $("title").html() + " - 新增";
		var href = host + 'form';
		$("#button-save").unbind("click").click(save);
		
		$("#module_dialog").dialog({title : title,href: href,width: 1000,height: 550,top:0,left:100});
		$("#module_dialog").dialog("open");
	}
	
	var detail = function() {
	    var gcUrl = $('#gcUrl').val();
		var id = getGridId();
		if (!id) {
			return false;
		}
		var title = $("title").html() + " - 预览";
		/* window.open(gcUrl+'?informationId='+id,"_blank","height=10px","width=10px"); */
		/* openwindow(gcUrl+'?informationId='+id,'预览',350,550); */
		
		$.ajax({
             type: "GET",
             url: host + 'getClientType',
             data: {id:id},
             dataType: "json",
             success: function(data){
                 if(data.clientType == 'android'){
                 	 openwindow(gcUrl+'?informationId='+id,'预览',350,550);
                 }else if(data.clientType == 'win'){
                 	openwindow(gcUrl+'?informationId='+id,'预览',800,550);
                 }
             }
        });
	}
	
	var editInformation = function() {
		var id = getGridId();
		if (!id) {
			return false;
		}
		var title = $("title").html() + " - 编辑";
		var href = host + 'form?' + pk + '=' + id;
		$("#button-save").unbind("click").click(update);
		$("#module_dialog").dialog({title : title,href: href,width: 1000,height: 550,top:0,left:100});
		$("#module_dialog").dialog("open");
	}
	
	function openwindow(url,name,iWidth,iHeight){
	    var url;                             //转向网页的地址;
	    var name;                            //网页名称，可为空;
	    var iWidth;                          //弹出窗口的宽度;
	    var iHeight;                         //弹出窗口的高度;
	    //获得窗口的垂直位置
	    var iTop = (window.screen.availHeight-30-iHeight)/2;        
	    //获得窗口的水平位置
	    var iLeft = (window.screen.availWidth-10-iWidth)/2;           
	    window.open(url,name,'height='+iHeight+',,innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no');
	}
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
		    <input type="hidden" id="gcUrl" name="gcUrl" value="${gcUrl }">
			<div class="module_search_input">
				资讯标题：
				<input type="text" name="informationTitle" class="easyui-textbox input_width_default" />
			</div>
			
			<div class="module_search_input">
				资讯类型：
				<input type="text" name="informationType" class="easyui-textbox input_width_default" />
			</div>
			
			<div class="module_search_input">
				客户端类型：
				<select class="easyui-combobox input_width_short" editable="false" name="clientType" data-options="required:false">
					<option value="">[全部]</option>
					<option value="win">win</option>
					<option value="android">android</option>
					<option value="ios">ios</option>
				</select>
			</div>
			
			<!-- 启用状态 -->
			<div class="module_search_input">
				启用状态：
				<select class="easyui-combobox input_width_short" editable="false" name="enableStatus" data-options="required:false">
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
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="addInformation()">新增</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="editInformation()">编辑</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">预览</a>
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



