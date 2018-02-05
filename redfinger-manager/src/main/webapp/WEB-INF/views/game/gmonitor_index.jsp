<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>实时监控</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="padId";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField : 'padId',
		onCheck: function(row){
			
		},
		columns:[[
			{width:100,title:'id',field:pk,checkbox:true},
			{width:100,title:'会员ID',field:'map.externalUserId'},
			{width:100,title:'会员手机',field:'userMobilePhoneT4',sortable:true},
			{width:100,title:'会员注册时间',field:'createTimeT4',sortable:true,formatter:formatterTime},
			{width:100,title:'会员注册IP',field:'registerIpT4',sortable:true},
			{width:100,title:'会员登录IP',field:'loginIpT4',sortable:true},
			{width:100,title:'设备编号',field:'padCode',sortable:true}, 
			{width:100,title:'设备等级',field:'padGradeT3',sortable:true,formatter:function(value){return getDatagridDict('rf_user_pad.pad_grade',value);}}, 
			{width:100,title:'绑定手机',field:'bindMobileT3',sortable:true},
			{width:100,title:'游戏名称',field:'gameNameT2',sortable:true},
			{width:100,title:'游戏包名',field:'packageNameT2',sortable:true},
	/* 		{width:100,title:'排序',field:'reorder',sortable:true}, */
	        {width:100,title:'受控状态',field:'padStatus', formatter:function(value){return getDatagridDict('rf_pad.pad_status',value);},sortable:true },
			{width:100,title:'游戏状态',field:'gameStatusT2',sortable:true, formatter:function(value){return getDatagridDict('rf_gmonitor_game.game_status',value);}},
		/* 	{width:150,title:'创建时间',field:'createTime',formatter:formatterTime,sortable:true}, */
			{width:150,title:'监控时间',field:'modifyTimeT2',formatter:formatterTime,sortable:true},
			{width:150,title:'虚拟IP',field:'padIp',sortable:true},
			/* {width:100,title:'状态',field:'enableStatus',sortable:true,formatter:formatterStop} */
		]],
		onLoadSuccess:function(data){
			if(data){
				var total = data.total;
				if(total > 5000){
					$("#export-link").unbind("click").click(asyncExportForm);
				}else{
					$("#export-link").unbind("click").click(exportData);
				}
				
			}
		}
		
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
		$("#look").dialog($.extend({}, dialogParam, dialogParamObj));
		$(document).bind("keydown","+",function(){
			$.messager.progress();
			getFloatData();
			$('#floatData').window('open');
			return false;
		}).bind("keydown","pagedown",function(){
			$.messager.progress();
			getFloatData();
			$('#floatData').window('open');
			return false;
		}).bind("keydown","-",function(){
			$('#floatData').window('close');
			return false;
		}).bind("keydown","pageup",function(){
			$('#floatData').window('close');
			return false;
		});
	});
	
	function getFloatData(){
		var url = host + 'floatData';
		$.getJSON(url, function(json){
			for(var i in json){
				$("#"+i).html(json[i]);
			}
			$.messager.progress('close');
		});
	}
	
    function look() {
	
		var title = $("title").html() + " - 查看";
		var href = host + 'form?';
		$("#button-save").unbind("click").click(update);
		lookForm(title, href);
	}
	    
	  //打开编辑对话框
	function lookForm(title, href) {
		$("#look").dialog({title : title,href: href,width:666});
		$("#look").dialog("open");
	}
	function lookcancel() {
		$("#look").dialog("close");
	}
	function exportCancel() {
		$(module_dialog).dialog('resize',{width:500,height:500});
		$(module_dialog).dialog("close");
	}
	
	function asyncExportForm(){
		var module_submit_container=$('<div id="module_submit_container"></div>'); 
		var form=$('<form id="module_submit_form" class="easyui-form">');
		var easyui_table=$('<table class="easyui-table" id="module_submit_table"></table>');
		easyui_table.appendTo(form);
		form.appendTo(module_submit_container);
		var tr=$('<tr><td class="td1">导出任务名:</td><td class="td2"><input class="easyui-textbox" type="text" id="exportTaskName" name="taskName"  data-options="required:true,validType:\'length[1,32]\'" /></td></tr>');
		easyui_table.append(tr);
		tr=$('<tr><td class="td1" colspan="2">注：导出操作为异步队列操作，结果请于【批处理】下的【导出管理】下载查看</td></tr>');
		easyui_table.append(tr);
		var title = $("title").html() + " - 异步导出";
		$("#button-save").unbind("click").click(asyncExportData);
		$("#button-cancel").unbind("click").click(exportCancel);
		$(module_dialog).dialog({height:200,title : title,content:$(module_submit_container).prop("outerHTML"),href:''});
		// 键盘事件监听, 当用户输入非法字符, 清除输入的非法字符
		$("#exportTaskName").textbox({
			inputEvents: $.extend({},$.fn.textbox.defaults.inputEvents,{
				keyup: function(event){
					var text = $(this).val();
					$('#exportTaskName').textbox('setValue', text.replace(/[^a-zA-Z0-9\u4e00-\u9fa5\-_]/g,'')) ;
				}})
		});
		$(module_dialog).dialog("open");
	}

	function exportData(){
		var pager=$(module_datagrid).datagrid("getPager");
		var total=$(pager).pagination('options').total;
		if(total){
			var cols=dataGridParamObj.columns[0];
			var exportHead="";
			var exportField="";
			for(var i in cols){
				if(i!=0){
					exportHead=exportHead+cols[i].title+",";
					exportField=exportField+cols[i].field+",";
				}
			}
			var where="";
			var params=$(module_datagrid).datagrid('options').queryParams;
			console.log(params);
			// 清除历史搜索条件
			$(".exportParams").remove();
			for(var i in params){
				if(params[i]){
					$("#exportForm").append('<input type="hidden" name="'+i+'" class="exportParams"  value="'+params[i]+'"/>');
				}
			}
			$("#exportForm").attr("action",host+'export');
			$("#exportForm").attr("target",'_blank');
			$("#exportHead").val(exportHead);
			$("#exportField").val(exportField);
			$("#exportName").val('实时监控');
			$("#exportForm").submit();
		}else{
			$.messager.alert('操作失败','无数据！',"warning");
		}
	}
	
	function asyncExportData(){
		var exportTaskName =  $("#exportTaskName").val();
		if(exportTaskName == ''){
			$.messager.alert('操作失败','导出名称不能为空！',"warning");
			return;
		}
		
		var isValid = $(module_submit_form).form('validate');
		if(!isValid){
			$(module_submit_form).form('enableValidation').form('validate');
			return;
		}
		
		var resArr = new Array();
		
		var pager=$(module_datagrid).datagrid("getPager");
		var total=$(pager).pagination('options').total;
		if(total){
			var cols=dataGridParamObj.columns[0];
			var exportHead="";
			var exportField="";
			for(var i in cols){
				if(i!=0){
					exportHead=exportHead+cols[i].title+",";
					exportField=exportField+cols[i].field+",";
				}
			}
			var where="";
			var params=$(module_datagrid).datagrid('options').queryParams;
			console.log(params);
			// 清除历史搜索条件
			$(".exportParams").remove();
			for(var i in params){
				if(params[i]){
					resArr.push("\"" + i +"\":" + "\"" +params[i]+ "\"");
				}
			}
			//resArr.push("\"exportTaskName\":" + "\"" +exportTaskName+ "\"");
			var json = "{" + resArr.join(",") + "}";
			//console.log(json.toString());
			$("#exportForm").append('<input type="hidden" name="exportTaskName" class="exportParams"  value="'+exportTaskName+'"/>');
			$("#exportForm").append('<input type="hidden" name="queryParams" class="exportParams"  value="'+encodeURI(json.toString())+'"/>');
			$("#exportForm").attr("action",host+'asyncExport');
			$("#exportForm").attr("target",'_self');
			$("#exportHead").val(exportHead);
			$("#exportField").val(exportField);
			$("#exportName").val('实时监控');
			
			$('#exportForm').form('submit', {    
			    success: function(data){  
			    	$(module_dialog).dialog('resize',{width:500,height:500});
			    	innerCallback(data, function(){
						$.messager.alert('提示', '成功生成导出任务，请到批处理下的导出管理处下载!', "info");
					}); 
			    }    
			});  

			
			
		}else{
			$.messager.alert('操作失败','无数据！',"warning");
		}
		
	}
	
	$.extend($.fn.validatebox.defaults.rules,{
		exportTask:{
          validator : function(value) {
              return /^[a-zA-Z0-9\u4e00-\u9fa5\-_]{1,32}$/.test(value);
          },
          message : '请确认你输入导出任务名中是否包含非法字符'
        }
	});
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
		    <div class="module_search_input" >		
			       游戏在线状态:
			    <select class="easyui-combobox input_width_short"   name="gameStatusT2" data-options="required:false,editable:false">
					<option value="">[全部]</option>
				    <fns:getOptions category="rf_gmonitor_game.game_status"  ></fns:getOptions>
			 	</select>
			</div>	
			 <div class="module_search_input">会员ID：<input type="text" name="externalUserId" class="easyui-numberbox input_width_short"/></div>
		    <div class="module_search_input">用户号码：<input type="text" name="userMobilePhoneT4" class="easyui-textbox input_width_default"/></div>
		    <div class="module_search_input">设备编号：<input type="text" name="padCode" class="easyui-textbox input_width_default"/></div>
			<div class="module_search_input">游戏名称：<input type="text" name="gameNameT2" class="easyui-textbox input_width_default"/></div>
			<div class="module_search_input">用户创建时间：<input type="text" name="beginTimeStr" class="easyui-datebox input_width_default" data-options="editable:false" />
			至 <input type="text" name="endTimeStr" class="easyui-datebox input width_defaul" editable="false"/>
			 </div>
			 <div class="module_search_input">注册IP：<input type="text" name="registerIpT4" class="easyui-textbox input_width_default" /></div>
			 
			 <div class="module_search_input">		
					IP段:
					<input type="text" class="easyui-textbox input_width_default" editable="true" name="padStartIp"/>
					至
					<input type="text" class="easyui-textbox input_width_default" editable="true" name="padEndIp"/>
			</div>
			<div class="module_search_input">
					编码段:<input type="text" name="startPadCode" class="easyui-textbox input_width_default" />至
					<input type="text" name="endPadCode" class="easyui-textbox input_width_default" />
			</div>
				
			<%-- <div class="module_search_input">
			 游戏分类:<select data-options="editable:false" name="gameNameT2" class="easyui-combobox input_width_default" style="width: 160px">
							<option value="">[全部]</option>
							<c:forEach var="one" items="${monitorlist}">
								<option value="${one.gameName}">${one.gameName}</option>
							</c:forEach>
						</select>
			</div> --%>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search-rf',plain:false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reset-rf',plain:false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="look()">查看</a>
			<!-- <!-- <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add-rf',plain:true" onclick="add()">新增</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit-rf',plain:true" onclick="edit()">编辑</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-start-rf',plain:true" onclick="start(callback)">启用</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-stop-rf',plain:true" onclick="stop(callback)">禁用</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove-rf',plain:true" onclick="del(callback)">删除</a> -->
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload-rf',plain:true" onclick="fresh()">刷新</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" id="export-link" iconCls="icon-excel-rf" plain="true" >导出</a>
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>
	
	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
    <div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton"data-options="iconCls:'icon-ok-rf'">保存</a>
		<a href="javascript:void(0)" id="button-cancel" class="easyui-linkbutton" data-options="iconCls:'icon-no'" onclick="cancel()">关闭</a>
	</div>
	
	<div id="look" buttons="#look_button"  ></div>
	<div id="look_button">
     <a href="javascript:void(0)"  class="easyui-linkbutton" iconCls="icon-no" onclick="lookcancel()">关闭</a>
	</div>
	
	<!-- 浮动数据 -->
	<div id="floatData" class="easyui-window" title="设备数据汇总" data-options="iconCls:'icon-save',modal:false,minimizable:false,maximizable:false,resizable:true,closed:true" >
        <div>当前设备总数：&nbsp;&nbsp;总数(<span class='red' id="padTotal">-</span>)&nbsp;,&nbsp;在线(<span class='red' id="onlinePad">-</span>)&nbsp;,&nbsp;离线(<span class='red' id="offlinePad">-</span>)&nbsp;,&nbsp;</div>
        <div>当前监控总数：&nbsp;&nbsp;总数(<span class='red' id="gameTotal">-</span>)&nbsp;,&nbsp;在线(<span class='red' id="onlineGmae">-</span>)&nbsp;,&nbsp;离线(<span class='red' id="offlineGame">-</span>)&nbsp;,&nbsp;</div>
    </div>
    
    <!-- 导出表单 -->
    <div class="hide">
		<form action="" id="exportForm" method="post" target="_blank">
			<input type="hidden" name="exportDatas" id="exportDatas" value=""/>
			<input type="hidden" name="exportHead" id="exportHead" value=""/>
			<input type="hidden" name="exportField" id="exportField" value=""/>
			<input type="hidden" name="exportName" id="exportName" value=""/>
		</form>
	</div>
	
</body>
</html>



