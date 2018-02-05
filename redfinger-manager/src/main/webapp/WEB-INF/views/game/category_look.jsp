<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>分类游戏</title>
<meta name="decorator" content="default" />
</head>
<body>
	<script type="text/javascript">
	function doSearch() {
		$('#module_datagrid_renewal').datagrid('load', {
			name : $('#apkname').val()
			});
	} 
	
	var module_datagrid_renewal = "#module_datagrid_renewal";
	var callback = defaultCallback ;
	var dataGridParamObj = {
			url : host + "apklist?id=${bean.id}",
			idField : 'id',
			onCheck : function(index,row) {
	
			},
			onDblClickRow:dblClickCallbackToo,
			columns : [[   	
			         	{width:100,title:'游戏名',field:'name'},
						{width:100,title:'排序',field:'reorder'},
						{width :100,title:'状态',field:'enableStatus',formatter:formatterStop},
			  ]]
		};
		var dialogParamObj = {

		};
		$(function() {
			$(module_datagrid_renewal).datagrid($.extend({}, dataGridParam, dataGridParamObj));
		});
		
		//获取详情
		function dblClickCallbackToo(index, row){
			var view=$(module_datagrid_renewal).parent();
			if($(view).hasClass("datagrid-view")){
				//获取头
				var heads=[];
				$(view).find(".datagrid-header-row").find(".datagrid-cell").each(function(){
					heads.push($(this).text());
				});
				//获取内容
				var bodys=[];
				$(view).find(".datagrid-body").find(".datagrid-row[datagrid-row-index="+index+"]").find(".datagrid-cell").each(function(){
					bodys.push($(this).html());
				});
				var module_submit_container=$('<div id="module_submit_container"></div>'); 
				var form=$('<form id="module_submit_form" class="easyui-form">');
				var easyui_table=$('<table class="easyui-table" id="module_submit_table"></table>');
				easyui_table.appendTo(form);
				form.appendTo(module_submit_container);
				for(var i in heads){
					var tr=$('<tr><td class="td1">'+heads[i]+':</td><td class="td2">'+bodys[i]+'</td></tr>');
					easyui_table.append(tr);
				}
				var title = $("title").html() + " - 明细";
				$("#button-save").unbind("click").click(cancel);
				$(module_dialog).dialog({title : title,content:$(module_submit_container).prop("outerHTML"),href:''});
				$(module_dialog).dialog("open");
			}
		}
		
	</script>
	
	<div id="module_submit_container">
	<div id="module_search" >
		<form id="module_submit_form" class="easyui-form" method="post">
		 <table id="module_submit_table" >
				<tr>
					<td class="td1"> 游戏类型:</td>
					<td class="td2">${bean.name}  <input type="hidden" name="name" value="${bean.name}" /></td>
				</tr>
				<tr>
					<td class="td1"> 排序:</td>
					<td class="td2">${bean.reorder}  <input type="hidden" name="name" value="${bean.reorder}" /></td>
				</tr>
 		</table>
	   	</form>
	   	</div>
	   	</div>
	  <div id="module_submit_container">		
		<div class="module_search_button">
			<form id="user_search_form" method="post">
				游戏名称:<input id="apkname" name ="apkname" class="easyui-textbox input_width_default">
				<a href="#" iconCls="icon-search-rf" class="easyui-linkbutton"plain="true"onclick="doSearch()">查询</a>
				
			</form>
		</div> 
	  </div> 
	
			<table id="module_datagrid_renewal" toolbar="#module_toolbar_renewal"></table>
		

</body>
</html>


