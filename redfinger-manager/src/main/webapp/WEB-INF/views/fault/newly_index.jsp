<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>故障咨询</title>
<meta name="decorator" content="moduleIndex" />
<style type="text/css">
	.td1{
	width:100px;
	border-bottom: 1px solid #ccc;
	text-align: right;
	padding: 10px;
	font-size: 12px;
}
	.td2{
		border-bottom: 1px solid #ccc;
		padding: 10px;
		font-size: 12px;
	}
</style>
<script type="text/javascript">
	var pk="faultFeedbackId";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var module_dialog="#module_dialog";
	var callback=function(data){
		innerCallback(data, function(){
			$.messager.alert('提示', '操作成功！', "info");
			var tab = $('#subtab').tabs('getSelected');
			var options=$(tab).panel('options',tab);
			fresh_tab(options.title);
		});
	};
	var dialogParamObj={
			
	};
	$(function(){
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	function gridSearch(){
		var padCode=$("#padCode").textbox('getValue');
		padCode = $.trim(padCode);
		console.log(padCode);
		if(padCode==''){
			return false;
		}
		var tb=$('#subtab').tabs('getTab',padCode);
		var height=$("#subtab").height();
		if(tb==null){
			$('#subtab').tabs('add',{
				title:padCode,
				closable:true,
				href:host+'getPad?padCode='+padCode
			});
		}else{
			$('#subtab').tabs('select',padCode);
		}
	}
	function fresh_tab(padCode){
		var panel=$("#subtab").tabs('getTab',padCode);
		$(panel).panel("refresh");
	}
	function fault_add(padCode){
		var title = "新增故障工单 - "+padCode;
		var href = host + 'form?padCode='+padCode;
		$("#button-save").unbind("click").click(save);
		openDialogForm(title, href);
	}
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">设备编号：
				<input type="text" value="" id="padCode" name="padCode" class="easyui-textbox input_width_long" data-options='' />
			</div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="subtab" class="easyui-tabs" style="position: absolute;top: 40px;left: 0px;right: 0px;bottom: 0px;" data-options ="tabPosition:'top'">   
		</div>  
	</div>
	
	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
    <div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton" iconCls="icon-ok-rf">确定</a>
		<a href="javascript:void(0)" id="button-cancel" class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
	
</body>
</html>



