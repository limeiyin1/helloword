<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>发行商相关游戏</title>
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
		    url : host + "applist?id=${bean.id}",
			idField : 'id',
			onCheck : function(index,row) {
	
			},
			onDblClickRow:dblClickCallbackToo,
			columns : [ [   	
			         	{width : 100,title : 'Apk名称',field:'name',sortable : true},
			         	{width : 100,title : '真实下载次数',field:'countDownload',sortable:true},
			         	{width : 100,title : '虚拟下载次数',field:'countDownloadBase',sortable:true},
			          	{width : 100,title : 'Apk大小',field:'apkSize',sortable:true},
			          	{width : 100,title : '当前评论分数',field:'commentScore',sortable:true},
			          	{width : 100,title : '评论次数',field:'countComment',sortable:true},
			          	{width : 150,title : '下载地址',field:'downloadUrl',sortable:true},
			         	] ]
		};
		var dialogParamObj = {

		};
		$(function() {
			$(module_datagrid_renewal).datagrid($.extend({}, dataGridParam, dataGridParamObj));
		});
		
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


