<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>设备操作</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk = "id";
	var module_datagrid = "#module_datagrid";
	var module_dialog = "#module_dialog";
	var module_search_form = "#module_search_form"
	var module_submit_form = "#module_submit_form";
	var callback = defaultCallback;

	var dataGridParamObj = {
		url : host + "list",
		idField : 'id',
		onCheck : function(row) {

		},
		columns : [[ 
		              {width : 100,title : 'id',field : 'id',checkbox : true}, 
		              {width : 100,title : '批次名',field : 'name',sortable : true}, 
		              {width : 100,title : '创建人',field : 'creater',sortable : true}, 
		              {width : 300,title : '操作名',field : 'remark',sortable : true}, 
		              {width : 100,title : '游戏名',field : 'gameName',sortable : true}, 
		              {width : 100,title : '安装包名',field : 'packageName',sortable : true}, 
		              {width : 100,title : '执行设备数',field : 'padCount',sortable : true,},
		           /*    {width : 100,title : '执行状态',field : 'taskStatus',sortable : true,formatter:function(value){return getDatagridDict('rf_pad_task.task_status',value);}}, */
		              {width : 100,title : '创建时间',field : 'createTime',sortable : true,formatter:formatterTime}
		            
		          ]]
		};
	var dialogParamObj = {
			
	};
	$(function() {
		$(module_datagrid).datagrid($.extend({}, dataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
		$("#look").dialog($.extend($.fn.dblClickCallback,{}, dialogParam, dialogParamObj));
	});
	
	function checkappinstall() {
		var title = $("title").html() + " - 检查APP安装";
		var href = host + 'formAPP?remark='+'pad_check_app_install';
		$("#button-save").unbind("click").click(pad_check_app_install);
		openDialogForm(title, href);
	}
	var pad_check_app_install = function() {
		if ($(module_submit_form).form("validate")) {
			$.messager.progress();
			$(module_submit_form).form({
				url : host + 'pad_check_app_install'
			});
			$(module_submit_form).form("submit");
		}
	}
	function checkapprun() {
		var title = $("title").html() + " - 检查APP运行";
		var href = host + 'formAPP?remark='+'pad_check_app_run';
		$("#button-save").unbind("click").click(pad_check_app_run);
		openDialogForm(title, href);
	}
	var pad_check_app_run = function() {
		if ($(module_submit_form).form("validate")) {
			$.messager.progress();
			$(module_submit_form).form({
				url : host + 'pad_check_app_run'
			});
			$(module_submit_form).form("submit");
		}
	}
	function checkappversion() {
		var title = $("title").html() + " - 检查APP版本号";
		var href = host + 'formAPP?remark='+'pad_check_app_version';
		$("#button-save").unbind("click").click(pad_check_app_version);
		openDialogForm(title, href);
	}
	var pad_check_app_version = function() {
		if ($(module_submit_form).form("validate")) {
			$.messager.progress();
			$(module_submit_form).form({
				url : host + 'pad_check_app_version'
			});
			$(module_submit_form).form("submit");
		}
	}
	function install() {
		var title = $("title").html() + " - APP安装";
		var href = host + 'formAPP?remark='+'pad_install';
		$("#button-save").unbind("click").click(pad_install);
		openDialogForm(title, href);
	}
	var pad_install = function() {
		if ($(module_submit_form).form("validate")) {
			$.messager.progress();
			$(module_submit_form).form({
				url : host + 'pad_install'
			});
			$(module_submit_form).form("submit");
		}
	}
	function uninstall() {
		var title = $("title").html() + " - APP卸载";
		var href = host + 'formAPP?remark='+'pad_uninstall';
		$("#button-save").unbind("click").click(pad_uninstall);
		openDialogForm(title, href);
	}
	var pad_uninstall = function() {
		if ($(module_submit_form).form("validate")) {
			$.messager.progress();
			$(module_submit_form).form({
				url : host + 'pad_uninstall'
			});
			$(module_submit_form).form("submit");
		}
	}
	function reboot() {
		var title = $("title").html() + " - 重启";
		var href = host + 'form?remark='+'pad_reboot';
		$("#button-save").unbind("click").click(pad_reboot);
		openDialogForm(title, href);
	}
	var pad_reboot = function() {
		if ($(module_submit_form).form("validate")) {
			$.messager.progress();
			$(module_submit_form).form({
				url : host + 'pad_reboot'
			});
			$(module_submit_form).form("submit");
		}
	}
	function screencap() {
		var title = $("title").html() + " - 截图";
		var href = host + 'form?remark='+'pad_screencap';
		$("#button-save").unbind("click").click(pad_screencap);
		openDialogForm(title, href);
	}
	var pad_screencap = function() {
		if ($(module_submit_form).form("validate")) {
			$.messager.progress();
			$(module_submit_form).form({
				url : host + 'pad_screencap'
			});
			$(module_submit_form).form("submit");
		}
	}
	function settime() {
		var title = $("title").html() + " - 设置时间";
		var href = host + 'form?remark='+'pad_set_time';
		$("#button-save").unbind("click").click(pad_set_time);
		openDialogForm(title, href);
	}
	var pad_set_time = function() {
		if ($(module_submit_form).form("validate")) {
			$.messager.progress();
			$(module_submit_form).form({
				url : host + 'pad_set_time'
			});
			$(module_submit_form).form("submit");
		}
	}
	function reset() {
		var title = $("title").html() + " - 设备重置";
		var href = host + 'form?remark='+'pad_reset';
		$("#button-save").unbind("click").click(pad_reset);
		openDialogForm(title, href);
	}
	var pad_reset = function() {
		if ($(module_submit_form).form("validate")) {
			$.messager.progress();
			$(module_submit_form).form({
				url : host + 'pad_reset'
			});
			$(module_submit_form).form("submit");
		}
	}
	function pingpad(){
		var title = $("title").html() + " - PING";
		var href = host + 'form?remark='+'pad_ping';
		$("#button-save").unbind("click").click(pad_ping);
		openDialogForm(title, href);
	}
	var pad_ping = function() {
		if ($(module_submit_form).form("validate")) {
			$.messager.progress();
			$(module_submit_form).form({
				url : host + 'pad_ping'
			});
			$(module_submit_form).form("submit");
		}
	}
	
	function restart(){
		var title = $("title").html() + " - 新设备重启";
		var href = host + 'form?remark='+'pad_restart';
		$("#button-save").unbind("click").click(pad_restart);
		openDialogForm(title, href);
	}
	var pad_restart = function() {
		if ($(module_submit_form).form("validate")) {
			$.messager.progress();
			$(module_submit_form).form({
				url : host + 'pad_restart'
			});
			$(module_submit_form).form("submit");
		}
	}
	
	//双击callback
	var dblClickCallback=function(index,row){
		var title = $("title").html() + " - 查看";
		var href = host + 'look?'+pk+'=' + row[pk];
		$("#button-save").unbind("click").click(update);
		lookForm(title, href);
	};
	function lookForm(title, href) {
		$("#look").dialog({title : title,href: href,width:699});
		$("#look").dialog("open");
	}
	function lookcancel() {
		$("#look").dialog("close");
	}
//卸载小米插件
	function uninstallXM() {
		var title = $("title").html() + " - APP卸载";
		var href = host + 'form?remark='+'pad_uninstall';
		$("#button-save").unbind("click").click(pad_uninstallXM);
		openDialogForm(title, href);
	}
	var pad_uninstallXM = function() {
		if ($(module_submit_form).form("validate")) {
			$.messager.progress();
			$(module_submit_form).form({
				url : host + 'pad_uninstallXM'
			});
			$(module_submit_form).form("submit");
		}
	}
	
	function remote() {
		var title = $("title").html() + " - KILL命令";
		var href = host + 'form?remark='+'remote-play';
		$("#button-save").unbind("click").click(kill_remote_play);
		openDialogForm(title, href);
	}
	var kill_remote_play = function() {
		if ($(module_submit_form).form("validate")) {
			$.messager.progress();
			$(module_submit_form).form({
				url : host + 'pad_kill_pid'
			});
			$(module_submit_form).form("submit");
		}
	}
	
	function gamemanage() {
		var title = $("title").html() + " - KILL命令";
		var href = host + 'form?remark='+'gamemanage';
		$("#button-save").unbind("click").click(kill_gamemanage);
		openDialogForm(title, href);
	}
	var kill_gamemanage = function() {
		if ($(module_submit_form).form("validate")) {
			$.messager.progress();
			$(module_submit_form).form({
				url : host + 'pad_kill_pid'
			});
			$(module_submit_form).form("submit");
		}
	}
//vmScreencap 虚拟设备截图
function vmScreencap() {
		var title = $("title").html() + " - 虚拟截图";
		var href = host + 'form?remark='+'vm_screencap';
		$("#button-save").unbind("click").click(vm_screencap);
		openDialogForm(title, href);
	}
	var vm_screencap = function() {
		if ($(module_submit_form).form("validate")) {
			$.messager.progress();
			$(module_submit_form).form({
				url : host + 'vm_screencap'
			});
			$(module_submit_form).form("submit");
		}
	}
	//ping物理机
	function devicePing() {
			var title = $("title").html() + " - ping物理机";
			var href = host + 'form?remark='+'device_ping';
			$("#button-save").unbind("click").click(device_ping);
			openDialogForm(title, href);
		}
		var device_ping = function() {
			if ($(module_submit_form).form("validate")) {
				$.messager.progress();
				$(module_submit_form).form({
					url : host + 'device_ping'
				});
				$(module_submit_form).form("submit");
			}
		}
	//kill agent
		function agent() {
			var title = $("title").html() + " - KILL命令";
			var href = host + 'form?remark='+'manage-agent';
			$("#button-save").unbind("click").click(kill_agent);
			openDialogForm(title, href);
		}
		var kill_agent = function() {
			if ($(module_submit_form).form("validate")) {
				$.messager.progress();
				$(module_submit_form).form({
					url : host + 'pad_kill_agent'
				});
				$(module_submit_form).form("submit");
			}
		}

</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container">
		<div id="module_search">
			<form id="module_search_form" class="easyui-form" method="post">
				<div class="module_search_input">
				      批次名：<input type="text" name="name" class="easyui-textbox input_width_default" /> 
				</div>
				<div class="module_search_input">
				      创建人：<input type="text" name="creater" class="easyui-textbox input_width_default" /> 
				</div>
				<div class="module_search_input">
				     操作名：<input type="text" name="remark" class="easyui-textbox input_width_default" /> 
				</div>
				<div class="module_search_input">
				    游戏名：<input type="text" name="gameName" class="easyui-textbox input_width_default" /> 
				</div>
				<div class="module_search_input">
				    安装包名：<input type="text" name="packageName" class="easyui-textbox input_width_default" /> 
				</div>
				<%-- <div class="module_search_input">
				    执行状态：<select class="easyui-combobox input_width_short"  editable="false" name="taskStatus" data-options="required:false">
							<option value="">[全部]</option>
						   <fns:getOptions category="rf_pad_task.task_status"  ></fns:getOptions>
					 	</select> 
				</div> --%>
				 <div class="module_search_input">
					创建时间:
						<input type="text" class="easyui-datebox input_width_default" data-options="editable:false"  name="beginTimeStr" />
						至
						<input type="text" class="easyui-datebox input_width_default" data-options="editable:false" name="endTimeStr"/>
				 </div>  
				<div class="module_search_button">
					<a href="javascript:void(0)" class="easyui-linkbutton"data-options="iconCls:'icon-search-rf',plain:false" onclick="gridSearch()">搜索</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"data-options="iconCls:'icon-reset-rf',plain:false" onclick="gridReset()">清空</a>
				</div>
			</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar" style="height: auto">
			<c:if test="${not empty sessionScope.permission.button_taskbatch_checkappinstall}">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-star-rf',plain:true" onclick="checkappinstall()">检查APP安装</a> 
			</c:if>
			<c:if test="${not empty sessionScope.permission.button_taskbatch_checkapprun}">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-star-rf',plain:true" onclick="checkapprun()">检查APP运行</a> 
			</c:if>
			<c:if test="${not empty sessionScope.permission.button_taskbatch_checkappversion}">
				<a href="javascript:void(0)" class="easyui-linkbutton"data-options="iconCls:'icon-star-rf',plain:true" onclick="checkappversion()">检查APP版本号</a> 
			</c:if>
			<c:if test="${not empty sessionScope.permission.button_taskbatch_install}">
				<a href="javascript:void(0)" class="easyui-linkbutton"data-options="iconCls:'icon-star-rf',plain:true" onclick="install()">安装APP</a> 
			</c:if>
			<c:if test="${not empty sessionScope.permission.button_taskbatch_uninstall}">
				<a href="javascript:void(0)" class="easyui-linkbutton"data-options="iconCls:'icon-star-rf',plain:true" onclick="uninstall()">卸载APP</a> 
			</c:if>
			<%-- <c:if test="${not empty sessionScope.permission.button_taskbatch_reboot}">
				<a href="javascript:void(0)" class="easyui-linkbutton"data-options="iconCls:'icon-star-rf',plain:true" onclick="reboot()">重启设备</a>
			</c:if> 
			<c:if test="${not empty sessionScope.permission.button_taskbatch_screencap}">
				<a href="javascript:void(0)" class="easyui-linkbutton"data-options="iconCls:'icon-star-rf',plain:true" onclick="screencap()">截图</a> 
			</c:if> --%>
			<%-- <c:if test="${not empty sessionScope.permission.button_taskbatch_settime}">
				<a href="javascript:void(0)" class="easyui-linkbutton"data-options="iconCls:'icon-star-rf',plain:true" onclick="settime()">设置时间</a> 
			</c:if>
			<c:if test="${not empty sessionScope.permission.button_taskbatch_restart}">
				<a href="javascript:void(0)" class="easyui-linkbutton"data-options="iconCls:'icon-star-rf',plain:true" onclick="restart()">新设备重启</a> 
			</c:if> --%>
			<c:if test="${not empty sessionScope.permission.button_taskbatch_reset}">
				<a href="javascript:void(0)" class="easyui-linkbutton"data-options="iconCls:'icon-star-rf',plain:true" onclick="reset()">重置设备</a> 
			</c:if> 
			<c:if test="${not empty sessionScope.permission.button_taskbatch_ping}">
				<a href="javascript:void(0)" class="easyui-linkbutton"data-options="iconCls:'icon-star-rf',plain:true" onclick="pingpad()">ping命令</a> 
			</c:if>
		    <%-- <c:if test="${not empty sessionScope.permission.button_taskbatch_uninstallXM}">
				<a href="javascript:void(0)" class="easyui-linkbutton"data-options="iconCls:'icon-star-rf',plain:true" onclick="uninstallXM()">卸载小米插件</a> 
		    </c:if> --%>
		   <%--  <c:if test="${not empty sessionScope.permission.button_taskbatch_kremote}"> --%>
		    <a href="javascript:void(0)" class="easyui-linkbutton"data-options="iconCls:'icon-star-rf',plain:true" onclick="remote()">KILL-remote</a>
		  <%--   </c:if> --%>
		<%--     <c:if test="${not empty sessionScope.permission.button_taskbatch_kgamemanage}"> --%>
		    <a href="javascript:void(0)" class="easyui-linkbutton"data-options="iconCls:'icon-star-rf',plain:true" onclick="gamemanage()">KILL-gamemanage</a>
		   <%--    </c:if> --%>
		 <%--    <c:if test="${not empty sessionScope.permission.button_taskbatch_kagent}"> --%>
		    <a href="javascript:void(0)" class="easyui-linkbutton"data-options="iconCls:'icon-star-rf',plain:true" onclick="agent()">KILL-agent</a>
		   <%--    </c:if> --%>
		  <%--   <c:if test="${not empty sessionScope.permission.button_taskbatch_vmScreencap}"> --%>
		    <a href="javascript:void(0)" class="easyui-linkbutton"data-options="iconCls:'icon-star-rf',plain:true" onclick="vmScreencap()">虚拟截图</a> 
		    <%--  </c:if> --%>
		    <c:if test="${not empty sessionScope.permission.button_taskbatch_deviceping}"> 
		    <a href="javascript:void(0)" class="easyui-linkbutton"data-options="iconCls:'icon-star-rf',plain:true" onclick="devicePing()">ping物理机</a>  
		    </c:if>
			<a href="javascript:void(0)" class="easyui-linkbutton"data-options="iconCls:'icon-reload-rf',plain:true" onclick="fresh()">刷新</a>
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>

	<!-- 编辑框 -->
	<div id="look" buttons="#look_button"  ></div>
	<div id="look_button">
     <a href="javascript:void(0)"  class="easyui-linkbutton" iconCls="icon-no" onclick="lookcancel()">关闭</a>
	</div>
	<div id="module_dialog" buttons="#module_dialog_button"></div>
	<div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton" data-options="iconCls:'icon-ok-rf'">保存</a> 
		<a href="javascript:void(0)" id="button-cancel"class="easyui-linkbutton" data-options="iconCls:'icon-no'" onclick="cancel()">关闭</a>
	</div>
</body>
</html>



