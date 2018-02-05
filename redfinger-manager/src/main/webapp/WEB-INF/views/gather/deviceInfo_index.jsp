<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>信息采集</title>
<script type="text/javascript" src="${ctxStatic}/jquery-easyui-1.4.1/datagrid-deviceview.js"></script>
<meta name="decorator" content="moduleIndex" />

<script type="text/javascript">
	var pk="deviceInfoId";
	var module_datagrid = "#module_datagrid";
	var module_dialog = "#module_dialog";
	var module_search_form = "#module_search_form";
	var module_submit_form = "#module_submit_form";
	var callback = defaultCallback ;
	var dataGridParamObj = {
		url : host + "list",
		idField : pk,
		singleSelect: true,
		onCheck : function(row) {
			
		},
		onLoadSuccess:function(){
		/* 		$(".datagrid-row").mouseenter(function(){//这个是鼠标移上去展示的代码，暂时注释
				var infoId = $(this).find("input").val();
				var jqs = $(this);
				$.ajax({
			             type: "GET",
			             url: host + 'getInfo',
			             data: {infoId:infoId},
			             dataType: "json",
			             success: function(data){
			                var infos = data;
			                var content = "<table>";
							for(i in infos){
								var con = "<tr><td>"+infos[i].lable+":</td><td>"+infos[i].value+"</td></tr>";
								content = content+con;
							}
							content=content+"</table>";
							
							jqs.tooltip({
								content : content,
								trackMouse : true,
								deltaX: 15,
								deltaY: -5,
							});
							jqs.tooltip('show');
						}
					});
			}); */
			
			
		},  
		columns : [[ {width : 100,title : 'id', field : pk,checkbox : true},
			{width : 100,title : '物理机编码', field : 'deviceCode'},
			{width : 100,title : 'ip', field : 'ip'},
			{width : 100,title : '状态', field : 'enableStatus',sortable : true,formatter : formatterStop},
			{width : 100,title : '创建时间', field : 'createTime',sortable : true,formatter : formatterTime},
			/* {width : 100,title : '创建人', field : 'creater',sortable : true}, */
			{width : 100,title : '修改时间', field : 'modifyTime',sortable : true,formatter : formatterTime}
			/* {width : 100,title : '修改人', field : 'modifier',sortable : true} */
		]],
		view: deviceview,
		detailFormatter:function(index,row){
				return '<div style="overflow:auto;"><table style="width: 100%;border-left:1px #ccc dotted;" id="ddv-' + index + '"></table><div id="dddv-' + index + '"></div></div>';
		},
		onExpandRow: function(index,row){
			getDetail(index,row);
		}
	};
	
	var getDetail = function(index,row){
			var ip = row.ip;
			var content = "";
			$.ajax({
		             type: "GET",
		             url: host + 'getInfo',
		             data: {ip:ip},
		             dataType: "json",
		             success: function(data){
		             //===============================================普通属性展示========================================================//
		                var infos = data.list;
		                var con = "";
		                var ros = "";
		                var j=1;
		                $('#ddv-'+index).html("");
						for(i in infos){
						
							con = con+"<td style='width: 12.5%;text-align:center;padding:5px 0;font-weight:600;background:#F0F0F0'>"+infos[i].lable+":</td><td style='width: 12.5%;text-align:center;padding:5px 0;'>"+infos[i].value+"</td>";
							if(j==4){
								ros = "<tr>"+con+"</tr>";
								$('#ddv-'+index).append(ros);
								con = "";
								j=1;
								continue;
							}
							if(i==infos.length-1){
								ros = "<tr>"+con+"</tr>";
								$('#ddv-'+index).append(ros);
								continue;
							}
							j=j+1;
						}
						//=========================================拓展属性展示==============================================================//
						$('#dddv-'+index).html("");
						var all_table = "";
						var tables = data.tables;
						
						for(i in tables){
							
							var title = tables[i].title;
							var head = tables[i].head;
							var body = tables[i].body;
							var table = "";
							table = table+title+":<br/>";
							table = table+"<table style='width: 100%;border-left:1px #ccc dotted;'><tr>";
							for(j in head){
								table = table+"<th style='text-align:center;padding:5px 0;font-weight:600;background:#F0F0F0'>"+head[j]+"</th>";
							}
							table = table+"</tr>";
							for(k in body){
								table = table+"<tr>";
								for(l in body[k]){
									table = table+"<td style='text-align:center;padding:5px 0;'>"+body[k][l]+"</td>";
								}
								table = table+"</tr>";
							}
							table = table+"</table>";
							table = table+"<bt/>";
							all_table = all_table+table;
						}
						$('#dddv-'+index).html(all_table);
						
						
						
						$('#module_datagrid').datagrid('fixDetailRowHeight',index);
					}
				});
				
				
				
	};
	
	
	
	
	
	
	
	var dialogParamObj = {
	};
	$(function() {
		$(module_datagrid).datagrid($.extend({}, dataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
		//$("#look").dialog($.extend({}, dialogParam, dialogParamObj));
	});

	
	/* //双击callback
	var dblClickCallback=function(index,row){
		var title = $("title").html() + " - 查看";
		var href = host + 'look?'+pk+'=' + row[pk];
		$("#button-save").unbind("click").click(update);
		lookForm(title, href);
	}; */
	//打开编辑对话框
	/* function lookForm(title, href) {
		$("#look").dialog({title : title,href: href,width:699});
		$("#look").dialog("open");
	} */
	//展开所有
	function expandAll(){
		var rowsLenght = $('#module_datagrid').datagrid('getRows').length;
		for(var i=0;i<rowsLenght;i++){
		$('#module_datagrid').datagrid('expandRow',i);
		}
	}
	//收起所有
	function collapseAll(){
		var rowsLenght = $('#module_datagrid').datagrid('getRows').length;
		for(var i=0;i<rowsLenght;i++){
		$('#module_datagrid').datagrid('collapseRow',i);
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
					
					物理机ip：
					<input type="text" name="ip" class="easyui-textbox input_width_default" />
					物理机编码：
					<input type="text" name="deviceCode" class="easyui-textbox input_width_default" />
				</div>
				<div class="module_search_button">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
				</div>
			</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="expandAll()">展开所有</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove-rf" plain="true" onclick="collapseAll()">收起所有</a>
			<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="add()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a> -->
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>

	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
	<div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a> 
		<a href="javascript:void(0)" id="button-cancel" class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
	<div id="look" buttons="#look_button"  ></div>
</body>
</html>



