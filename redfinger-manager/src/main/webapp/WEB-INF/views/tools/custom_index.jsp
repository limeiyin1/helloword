<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>客服智能排班</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var module_datagrid="#module_datagrid";
	var module_search_form="#module_search_form";
	var callback=defaultCallback;
	var dataGridParamObj = {
		url : host + "list",
		onCheck : function(row) {
		},
		fitColumns : true,
		pagination:false,
		columns:[[
			{width:50,title:'姓名',field:'customName',fixed:true},
			{width:100,title:'1',field:'map.1',	styler:function (value,row,index){
				if(value=='休'){
					return 'background-color:#FF00FF;color:#000000;text-align:center';
				}else if(value=='早'){
					return 'background-color:#99CC00;color:#000000;text-align:center';
				}else if(value=='中'){
					return 'background-color:#FFFF99;color:#000000;text-align:center';
				}else if(value=='晚'){
					return 'background-color:#00CCFF;color:#000000;text-align:center';
				}else if(value=='夜'){
					return 'background-color:#FF9900;color:#000000;text-align:center';
				}
			}},
			{width:100,title:'2',field:'map.2',styler:function (value,row,index){
				if(value=='休'){
					return 'background-color:#FF00FF;color:#000000;text-align:center';
				}else if(value=='早'){
					return 'background-color:#99CC00;color:#000000;text-align:center';
				}else if(value=='中'){
					return 'background-color:#FFFF99;color:#000000;text-align:center';
				}else if(value=='晚'){
					return 'background-color:#00CCFF;color:#000000;text-align:center';
				}else if(value=='夜'){
					return 'background-color:#FF9900;color:#000000;text-align:center';
				}
			}},
			{width:100,title:'3',field:'map.3',styler:function (value,row,index){
				if(value=='休'){
					return 'background-color:#FF00FF;color:#000000;text-align:center';
				}else if(value=='早'){
					return 'background-color:#99CC00;color:#000000;text-align:center';
				}else if(value=='中'){
					return 'background-color:#FFFF99;color:#000000;text-align:center';
				}else if(value=='晚'){
					return 'background-color:#00CCFF;color:#000000;text-align:center';
				}else if(value=='夜'){
					return 'background-color:#FF9900;color:#000000;text-align:center';
				}
			}},
			{width:100,title:'4',field:'map.4',styler:function (value,row,index){
				if(value=='休'){
					return 'background-color:#FF00FF;color:#000000;text-align:center';
				}else if(value=='早'){
					return 'background-color:#99CC00;color:#000000;text-align:center';
				}else if(value=='中'){
					return 'background-color:#FFFF99;color:#000000;text-align:center';
				}else if(value=='晚'){
					return 'background-color:#00CCFF;color:#000000;text-align:center';
				}else if(value=='夜'){
					return 'background-color:#FF9900;color:#000000;text-align:center';
				}
			}},
			{width:100,title:'5',field:'map.5',styler:function (value,row,index){
				if(value=='休'){
					return 'background-color:#FF00FF;color:#000000;text-align:center';
				}else if(value=='早'){
					return 'background-color:#99CC00;color:#000000;text-align:center';
				}else if(value=='中'){
					return 'background-color:#FFFF99;color:#000000;text-align:center';
				}else if(value=='晚'){
					return 'background-color:#00CCFF;color:#000000;text-align:center';
				}else if(value=='夜'){
					return 'background-color:#FF9900;color:#000000;text-align:center';
				}
			}},
			{width:100,title:'6',field:'map.6',styler:function (value,row,index){
				if(value=='休'){
					return 'background-color:#FF00FF;color:#000000;text-align:center';
				}else if(value=='早'){
					return 'background-color:#99CC00;color:#000000;text-align:center';
				}else if(value=='中'){
					return 'background-color:#FFFF99;color:#000000;text-align:center';
				}else if(value=='晚'){
					return 'background-color:#00CCFF;color:#000000;text-align:center';
				}else if(value=='夜'){
					return 'background-color:#FF9900;color:#000000;text-align:center';
				}
			}},
			{width:100,title:'7',field:'map.7',styler:function (value,row,index){
				if(value=='休'){
					return 'background-color:#FF00FF;color:#000000;text-align:center';
				}else if(value=='早'){
					return 'background-color:#99CC00;color:#000000;text-align:center';
				}else if(value=='中'){
					return 'background-color:#FFFF99;color:#000000;text-align:center';
				}else if(value=='晚'){
					return 'background-color:#00CCFF;color:#000000;text-align:center';
				}else if(value=='夜'){
					return 'background-color:#FF9900;color:#000000;text-align:center';
				}
			}},
			{width:100,title:'8',field:'map.8',styler:function (value,row,index){
				if(value=='休'){
					return 'background-color:#FF00FF;color:#000000;text-align:center';
				}else if(value=='早'){
					return 'background-color:#99CC00;color:#000000;text-align:center';
				}else if(value=='中'){
					return 'background-color:#FFFF99;color:#000000;text-align:center';
				}else if(value=='晚'){
					return 'background-color:#00CCFF;color:#000000;text-align:center';
				}else if(value=='夜'){
					return 'background-color:#FF9900;color:#000000;text-align:center';
				}
			}},
			{width:100,title:'9',field:'map.9',styler:function (value,row,index){
				if(value=='休'){
					return 'background-color:#FF00FF;color:#000000;text-align:center';
				}else if(value=='早'){
					return 'background-color:#99CC00;color:#000000;text-align:center';
				}else if(value=='中'){
					return 'background-color:#FFFF99;color:#000000;text-align:center';
				}else if(value=='晚'){
					return 'background-color:#00CCFF;color:#000000;text-align:center';
				}else if(value=='夜'){
					return 'background-color:#FF9900;color:#000000;text-align:center';
				}
			}},
			{width:100,title:'10',field:'map.10',styler:function (value,row,index){
				if(value=='休'){
					return 'background-color:#FF00FF;color:#000000;text-align:center';
				}else if(value=='早'){
					return 'background-color:#99CC00;color:#000000;text-align:center';
				}else if(value=='中'){
					return 'background-color:#FFFF99;color:#000000;text-align:center';
				}else if(value=='晚'){
					return 'background-color:#00CCFF;color:#000000;text-align:center';
				}else if(value=='夜'){
					return 'background-color:#FF9900;color:#000000;text-align:center';
				}
			}},
			{width:100,title:'11',field:'map.11',styler:function (value,row,index){
				if(value=='休'){
					return 'background-color:#FF00FF;color:#000000;text-align:center';
				}else if(value=='早'){
					return 'background-color:#99CC00;color:#000000;text-align:center';
				}else if(value=='中'){
					return 'background-color:#FFFF99;color:#000000;text-align:center';
				}else if(value=='晚'){
					return 'background-color:#00CCFF;color:#000000;text-align:center';
				}else if(value=='夜'){
					return 'background-color:#FF9900;color:#000000;text-align:center';
				}
			}},
			{width:100,title:'12',field:'map.12',styler:function (value,row,index){
				if(value=='休'){
					return 'background-color:#FF00FF;color:#000000;text-align:center';
				}else if(value=='早'){
					return 'background-color:#99CC00;color:#000000;text-align:center';
				}else if(value=='中'){
					return 'background-color:#FFFF99;color:#000000;text-align:center';
				}else if(value=='晚'){
					return 'background-color:#00CCFF;color:#000000;text-align:center';
				}else if(value=='夜'){
					return 'background-color:#FF9900;color:#000000;text-align:center';
				}
			}},
			{width:100,title:'13',field:'map.13',styler:function (value,row,index){
				if(value=='休'){
					return 'background-color:#FF00FF;color:#000000;text-align:center';
				}else if(value=='早'){
					return 'background-color:#99CC00;color:#000000;text-align:center';
				}else if(value=='中'){
					return 'background-color:#FFFF99;color:#000000;text-align:center';
				}else if(value=='晚'){
					return 'background-color:#00CCFF;color:#000000;text-align:center';
				}else if(value=='夜'){
					return 'background-color:#FF9900;color:#000000;text-align:center';
				}
			}},
			{width:100,title:'14',field:'map.14',styler:function (value,row,index){
				if(value=='休'){
					return 'background-color:#FF00FF;color:#000000;text-align:center';
				}else if(value=='早'){
					return 'background-color:#99CC00;color:#000000;text-align:center';
				}else if(value=='中'){
					return 'background-color:#FFFF99;color:#000000;text-align:center';
				}else if(value=='晚'){
					return 'background-color:#00CCFF;color:#000000;text-align:center';
				}else if(value=='夜'){
					return 'background-color:#FF9900;color:#000000;text-align:center';
				}
			}},
			{width:100,title:'15',field:'map.15',styler:function (value,row,index){
				if(value=='休'){
					return 'background-color:#FF00FF;color:#000000;text-align:center';
				}else if(value=='早'){
					return 'background-color:#99CC00;color:#000000;text-align:center';
				}else if(value=='中'){
					return 'background-color:#FFFF99;color:#000000;text-align:center';
				}else if(value=='晚'){
					return 'background-color:#00CCFF;color:#000000;text-align:center';
				}else if(value=='夜'){
					return 'background-color:#FF9900;color:#000000;text-align:center';
				}
			}},
			{width:100,title:'16',field:'map.16',styler:function (value,row,index){
				if(value=='休'){
					return 'background-color:#FF00FF;color:#000000;text-align:center';
				}else if(value=='早'){
					return 'background-color:#99CC00;color:#000000;text-align:center';
				}else if(value=='中'){
					return 'background-color:#FFFF99;color:#000000;text-align:center';
				}else if(value=='晚'){
					return 'background-color:#00CCFF;color:#000000;text-align:center';
				}else if(value=='夜'){
					return 'background-color:#FF9900;color:#000000;text-align:center';
				}
			}},
			{width:100,title:'17',field:'map.17',styler:function (value,row,index){
				if(value=='休'){
					return 'background-color:#FF00FF;color:#000000;text-align:center';
				}else if(value=='早'){
					return 'background-color:#99CC00;color:#000000;text-align:center';
				}else if(value=='中'){
					return 'background-color:#FFFF99;color:#000000;text-align:center';
				}else if(value=='晚'){
					return 'background-color:#00CCFF;color:#000000;text-align:center';
				}else if(value=='夜'){
					return 'background-color:#FF9900;color:#000000;text-align:center';
				}
			}},
			{width:100,title:'18',field:'map.18',styler:function (value,row,index){
				if(value=='休'){
					return 'background-color:#FF00FF;color:#000000;text-align:center';
				}else if(value=='早'){
					return 'background-color:#99CC00;color:#000000;text-align:center';
				}else if(value=='中'){
					return 'background-color:#FFFF99;color:#000000;text-align:center';
				}else if(value=='晚'){
					return 'background-color:#00CCFF;color:#000000;text-align:center';
				}else if(value=='夜'){
					return 'background-color:#FF9900;color:#000000;text-align:center';
				}
			}},
			{width:100,title:'19',field:'map.19',styler:function (value,row,index){
				if(value=='休'){
					return 'background-color:#FF00FF;color:#000000;text-align:center';
				}else if(value=='早'){
					return 'background-color:#99CC00;color:#000000;text-align:center';
				}else if(value=='中'){
					return 'background-color:#FFFF99;color:#000000;text-align:center';
				}else if(value=='晚'){
					return 'background-color:#00CCFF;color:#000000;text-align:center';
				}else if(value=='夜'){
					return 'background-color:#FF9900;color:#000000;text-align:center';
				}
			}},
			{width:100,title:'20',field:'map.20',styler:function (value,row,index){
				if(value=='休'){
					return 'background-color:#FF00FF;color:#000000;text-align:center';
				}else if(value=='早'){
					return 'background-color:#99CC00;color:#000000;text-align:center';
				}else if(value=='中'){
					return 'background-color:#FFFF99;color:#000000;text-align:center';
				}else if(value=='晚'){
					return 'background-color:#00CCFF;color:#000000;text-align:center';
				}else if(value=='夜'){
					return 'background-color:#FF9900;color:#000000;text-align:center';
				}
			}},
			{width:100,title:'21',field:'map.21',styler:function (value,row,index){
				if(value=='休'){
					return 'background-color:#FF00FF;color:#000000;text-align:center';
				}else if(value=='早'){
					return 'background-color:#99CC00;color:#000000;text-align:center';
				}else if(value=='中'){
					return 'background-color:#FFFF99;color:#000000;text-align:center';
				}else if(value=='晚'){
					return 'background-color:#00CCFF;color:#000000;text-align:center';
				}else if(value=='夜'){
					return 'background-color:#FF9900;color:#000000;text-align:center';
				}
			}},
			{width:100,title:'22',field:'map.22',styler:function (value,row,index){
				if(value=='休'){
					return 'background-color:#FF00FF;color:#000000;text-align:center';
				}else if(value=='早'){
					return 'background-color:#99CC00;color:#000000;text-align:center';
				}else if(value=='中'){
					return 'background-color:#FFFF99;color:#000000;text-align:center';
				}else if(value=='晚'){
					return 'background-color:#00CCFF;color:#000000;text-align:center';
				}else if(value=='夜'){
					return 'background-color:#FF9900;color:#000000;text-align:center';
				}
			}},
			{width:100,title:'23',field:'map.23',styler:function (value,row,index){
				if(value=='休'){
					return 'background-color:#FF00FF;color:#000000;text-align:center';
				}else if(value=='早'){
					return 'background-color:#99CC00;color:#000000;text-align:center';
				}else if(value=='中'){
					return 'background-color:#FFFF99;color:#000000;text-align:center';
				}else if(value=='晚'){
					return 'background-color:#00CCFF;color:#000000;text-align:center';
				}else if(value=='夜'){
					return 'background-color:#FF9900;color:#000000;text-align:center';
				}
			}},
			{width:100,title:'24',field:'map.24',styler:function (value,row,index){
				if(value=='休'){
					return 'background-color:#FF00FF;color:#000000;text-align:center';
				}else if(value=='早'){
					return 'background-color:#99CC00;color:#000000;text-align:center';
				}else if(value=='中'){
					return 'background-color:#FFFF99;color:#000000;text-align:center';
				}else if(value=='晚'){
					return 'background-color:#00CCFF;color:#000000;text-align:center';
				}else if(value=='夜'){
					return 'background-color:#FF9900;color:#000000;text-align:center';
				}
			}},
			{width:100,title:'25',field:'map.25',styler:function (value,row,index){
				if(value=='休'){
					return 'background-color:#FF00FF;color:#000000;text-align:center';
				}else if(value=='早'){
					return 'background-color:#99CC00;color:#000000;text-align:center';
				}else if(value=='中'){
					return 'background-color:#FFFF99;color:#000000;text-align:center';
				}else if(value=='晚'){
					return 'background-color:#00CCFF;color:#000000;text-align:center';
				}else if(value=='夜'){
					return 'background-color:#FF9900;color:#000000;text-align:center';
				}
			}},
			{width:100,title:'26',field:'map.26',styler:function (value,row,index){
				if(value=='休'){
					return 'background-color:#FF00FF;color:#000000;text-align:center';
				}else if(value=='早'){
					return 'background-color:#99CC00;color:#000000;text-align:center';
				}else if(value=='中'){
					return 'background-color:#FFFF99;color:#000000;text-align:center';
				}else if(value=='晚'){
					return 'background-color:#00CCFF;color:#000000;text-align:center';
				}else if(value=='夜'){
					return 'background-color:#FF9900;color:#000000;text-align:center';
				}
			}},
			{width:100,title:'27',field:'map.27',styler:function (value,row,index){
				if(value=='休'){
					return 'background-color:#FF00FF;color:#000000;text-align:center';
				}else if(value=='早'){
					return 'background-color:#99CC00;color:#000000;text-align:center';
				}else if(value=='中'){
					return 'background-color:#FFFF99;color:#000000;text-align:center';
				}else if(value=='晚'){
					return 'background-color:#00CCFF;color:#000000;text-align:center';
				}else if(value=='夜'){
					return 'background-color:#FF9900;color:#000000;text-align:center';
				}
			}},
			{width:100,title:'28',field:'map.28',styler:function (value,row,index){
				if(value=='休'){
					return 'background-color:#FF00FF;color:#000000;text-align:center';
				}else if(value=='早'){
					return 'background-color:#99CC00;color:#000000;text-align:center';
				}else if(value=='中'){
					return 'background-color:#FFFF99;color:#000000;text-align:center';
				}else if(value=='晚'){
					return 'background-color:#00CCFF;color:#000000;text-align:center';
				}else if(value=='夜'){
					return 'background-color:#FF9900;color:#000000;text-align:center';
				}
			}},
			{width:40,title:'29',field:'map.29',fixed:true,styler:function (value,row,index){
				if(value=='休'){
					return 'background-color:#FF00FF;color:#000000;text-align:center';
				}else if(value=='早'){
					return 'background-color:#99CC00;color:#000000;text-align:center';
				}else if(value=='中'){
					return 'background-color:#FFFF99;color:#000000;text-align:center';
				}else if(value=='晚'){
					return 'background-color:#00CCFF;color:#000000;text-align:center';
				}else if(value=='夜'){
					return 'background-color:#FF9900;color:#000000;text-align:center';
				}
			}},
			{width:40,title:'30',field:'map.30',fixed:true,styler:function (value,row,index){
				if(value=='休'){
					return 'background-color:#FF00FF;color:#000000;text-align:center';
				}else if(value=='早'){
					return 'background-color:#99CC00;color:#000000;text-align:center';
				}else if(value=='中'){
					return 'background-color:#FFFF99;color:#000000;text-align:center';
				}else if(value=='晚'){
					return 'background-color:#00CCFF;color:#000000;text-align:center';
				}else if(value=='夜'){
					return 'background-color:#FF9900;color:#000000;text-align:center';
				}
			}},
			{width:40,title:'31',field:'map.31',fixed:true,styler:function (value,row,index){
				if(value=='休'){
					return 'background-color:#FF00FF;color:#000000;text-align:center';
				}else if(value=='早'){
					return 'background-color:#99CC00;color:#000000;text-align:center';
				}else if(value=='中'){
					return 'background-color:#FFFF99;color:#000000;text-align:center';
				}else if(value=='晚'){
					return 'background-color:#00CCFF;color:#000000;text-align:center';
				}else if(value=='夜'){
					return 'background-color:#FF9900;color:#000000;text-align:center';
				}
			}}
		]]
	};
	var dialogParamObj={
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$.extend($.fn.validatebox.defaults.rules,{
			year:{// 验证年份
	          validator : function(value) {
	              return /^[0-9]{4}$/gi.test(value);
	          },
	          message : '只允许4位的年份'
	        }
		});
	});
	function statExport(){
 		if($(module_datagrid).datagrid("getRows").length>0){
 			var fields=$('#module_datagrid').datagrid('getColumnFields');
 			var exportHead="";
 			var exportField="";
 			var exportName="";
 			for(var i in fields){
 				if(!$(module_datagrid).datagrid('getColumnOption',fields[i]).hidden){
 					exportHead=exportHead+$('#module_datagrid').datagrid( "getColumnOption" ,fields[i]).title+",";
 					exportField=exportField+fields[i]+",";
 				}
 		    }
 			var where="";
 			var params=$(module_datagrid).datagrid('options').queryParams;
 			for(var i in params){
 				if(i&&i=='year'){
 					exportName+=params[i];
 					exportName+='年';
 				}
 				if(i&&i=='month'){
 					exportName+=params[i];
 					exportName+='月';
 				}
 				where+=i+"="+params[i]+"&";
 			}
 			exportName+='客服智能排班';
 			$("#exportForm").attr("action",host+'export?'+where);
 			$("#exportHead").val(exportHead);
 			$("#exportField").val(exportField);
 			$("#exportName").val(exportName);
 			$("#exportForm").submit(); 
 		}else{
 			$.messager.alert('操作失败','无数据！',"warning");
 		}
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
		if(obj.year&&obj.month&&obj.month!='0'){
			var date = new Date;
			if(obj.year>date.getFullYear()){
			}else if(obj.year==date.getFullYear()){
				if(obj.month>date.getMonth()){
				}else if(obj.month==date.getMonth()+1){
				}else {//抛出
					$.messager.alert('操作失败', '填写的年月必须大于或等于当前的年月！', "error");
					return;
				}
			}else {//抛出
				$.messager.alert('操作失败', '填写的年月必须大于或等于当前的年月！', "error");
				return;
			}
			var lastday=getDaysByYearMonth(obj.year,obj.month);
			if(lastday){
				if(lastday==28){
					$("#module_datagrid").datagrid('hideColumn','map.29');
					$("#module_datagrid").datagrid('hideColumn','map.30');
					$("#module_datagrid").datagrid('hideColumn','map.31');
				}else if(lastday==29){
					$("#module_datagrid").datagrid('showColumn','map.29');
					$("#module_datagrid").datagrid('hideColumn','map.30');
					$("#module_datagrid").datagrid('hideColumn','map.31');
				}else if(lastday==30){
					$("#module_datagrid").datagrid('showColumn','map.29');
					$("#module_datagrid").datagrid('showColumn','map.30');
					$("#module_datagrid").datagrid('hideColumn','map.31');
				}else if(lastday==31){
					$("#module_datagrid").datagrid('showColumn','map.29');
					$("#module_datagrid").datagrid('showColumn','map.30');
					$("#module_datagrid").datagrid('showColumn','map.31');
				}
			}
		}else{
			$.messager.alert('操作失败', '操作失败，请填年月！', "error");
			return;
		}
		try{
			$(module_datagrid).treegrid("reload",obj);
		}catch(e){
			$(module_datagrid).datagrid("reload",obj);
		}
	}
	function getDaysByYearMonth(year,month){
		var d= new Date(year,month,0);
		return d.getDate();
	}
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">
				年份：
				<input type="text" name="year" data-options="validType:'year'"" class="easyui-textbox input_width_default"/>
			</div>
			<div class="module_search_input">
				月份：
		        <select class="easyui-combobox input_width_default" editable="false" name="month">
					<option value=0>[选择月份]</option>
					<option value=1>1月</option>
					<option value=2>2月</option>
					<option value=3>3月</option>
					<option value=4>4月</option>
					<option value=5>5月</option>
					<option value=6>6月</option>
					<option value=7>7月</option>
					<option value=8>8月</option>
					<option value=9>9月</option>
					<option value=10>10月</option>
					<option value=11>11月</option>
					<option value=12>12月</option>
				</select>
			</div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearchValidate()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel-rf" plain="true" onclick="statExport()">导出</a>
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>
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