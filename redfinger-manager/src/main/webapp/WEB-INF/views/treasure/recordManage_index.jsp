<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>红手指任务</title>
<meta name="decorator" content="moduleIndex" />
<style type="text/css">
.tableBorder {
	position: relative;
	border: 1px solid #95B8E7;
	background: white;
	vertical-align: middle;
	display: inline-block;
	margin: 0;
	padding: 0;
	-moz-border-radius: 5px 5px 5px 5px;
	-webkit-border-radius: 5px 5px 5px 5px;
	border-radius: 5px 5px 5px 5px;
	font-size: 13px;
}
</style>
<script type="text/javascript"
	src="${ctxStatic}/jquery-easyui-1.4.1/datagrid-recordview.js"></script>
<script type="text/javascript">
	var pk = "recordId";
	var module_datagrid = "#module_datagrid";
	var module_dialog = "#module_dialog";
	var module_search_form = "#module_search_form"
	var module_submit_form = "#module_submit_form";
	var callback = defaultCallback;

	var dataGridParamObj = {
		url : host + "list",
		idField : 'recordId',
		singleSelect: false,
		checkOnSelect: true, 
		selectOnCheck: true,
		onCheck : function(row) {

		},
		columns : [[ 
              {width : 100,title : 'id',field : 'recordId',checkbox : true}, 
              {width : 100,title : 'recordNumberCount',field : 'recordNumberCount'},
              {width : 100,title : '总期夺宝名称',field : 'map.treasureName',sortable : false}, 
              {width : 100,title : '分期夺宝名称',field : 'map.periodName',sortable : false},
              {width : 60,title : '当前期数',field : 'map.currentPeriod',sortable : true},
              {width : 100,title : '开奖状态',field : 'map.periodStatus',sortable : false, formatter:function(value){return getDatagridDict('treasure_period.period_status',value);}},
              {width : 100,title : '用户号码',field : 'map.userMobilePhone',sortable : false},
              {width : 50,title : '购买次数',field : 'treasureCount',sortable : true},
              {width : 60,title : '是否中奖',field : 'map.isWinning',sortable : false},
              {width : 60,title : '奖励是否发放',field : 'isAward',sortable : true, formatter:function(value){return getDatagridDict('treasure_period.redcoin_award',value);}},
              {width : 80,title : '创建人',field : 'creater',sortable : true},
              {width : 100,title : '创建时间',field : 'createTime',sortable : true,formatter:formatterTime},
              {width : 50,title : '状态',field : 'enableStatus',sortable : true,formatter: formatterStop},
              {width : 100,title : '备注',field : 'remark',sortable : false}
          	]],
          	view: subtaskview,
         	detailFormatter:function(index,row){
			  	return '<div style="padding:2px;"><table id="subtask-' + index + '"></table></div>';
		  	},
		  	onExpandRow: function(index,row){
			  	getSubTaskList(index,row);
			},
		};
	var dialogParamObj = {
			
	};
	//双击显示明细
	var lookSpecific =function(index, row){
		if(typeof dblClickCallback!='undefined' && dblClickCallback){
			dblClickCallback.apply(null, [index,row]);
		}else{
			var view=$(module_datagrid).parent();
			if($(view).hasClass("datagrid-view")){
				//获取头
				var heads=["总期夺宝名称","分期夺宝名称","用户号码","购买次数","是否中奖","奖励是否发放","创建人","创建时间","状态"];
				var cellIndex = 0;
				//获取内容
				var bodys=[];
				cellIndex = 0;
				$(view).find(".datagrid-body").find(".datagrid-row[datagrid-row-index="+index+"]").find(".datagrid-cell").each(function(){
					if((row.recordNumberCount && row.recordNumberCount == 0) || cellIndex > 1){
						bodys.push($(this).html());
					}
					cellIndex++;
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
	}
	// 普通表格默认参数
	var lookDataGridParam = {
		fitColumns : true,
		pagination : true,
		striped : true,
		rownumbers : true,
		singleSelect : false,
		idField : 'id',
		pageSize : 20,
		pageList : [ 10, 15, 20, 50, 100, 200,500 ],
		loadFilter : loadFilterForDataGrid,
		onDblClickRow:lookSpecific,
		loadMsg : "处理中，请稍后..."
	};
	$(function() {
		$(module_datagrid).datagrid($.extend({}, lookDataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
		$(module_datagrid).datagrid('hideColumn', 'recordNumberCount');
	});
	
	function getSubTaskList(index,row){
	    $('#subtask-'+index).datagrid({
		  	url:host+'subNumberList?recordId='+row.recordId,
		  	fitColumns:true,  
            height:'auto',
            loadFilter : loadFilterForDataGridNoPage,
	 		columns:[[
	              {width : 150,title : '夺宝号码',field : 'treasureNumber',sortable : false}, 
	              {width : 100,title : '是否中奖',field : 'map.isWinning',sortable : false}, 
	              {width : 100,title : '奖励是否发放',field : 'isAward',sortable : true, formatter:function(value){return getDatagridDict('treasure_period.redcoin_award',value);}}, 
	              {width : 100,title : '发放时间',field : 'awardTime',sortable : false,formatter:formatterTime}, 
	              {width : 100,title : '奖励类型',field : 'awardType',sortable : false,formatter : function(value) {return getDatagridDict('treasure.award_type', value);}},
	              {width : 100,title : '激活码',field : 'activationCode',sortable : false},
	              {width : 100,title : '红币',field : 'redCoin',sortable : false},
	              {width : 100,title : '备注',field : 'remark',sortable : false}
            ]],
			onResize:function(){
				$("#module_datagrid").datagrid('fixDetailRowHeight',index);
			},
			onLoadSuccess:function(){
				setTimeout(function(){
					$("#module_datagrid").datagrid('fixDetailRowHeight',index);
				},0);
			}
		});
		$("#module_datagrid").datagrid('fixDetailRowHeight',index);
	}
	
	function addTaskTab(url,title){
		var topJQ = top.jQuery;
		var tb=topJQ('#easyui-tabs').tabs('getTab',title);
		var height=topJQ("#easyui-tabs").height()-30;
		if(tb!=null){
			var index = topJQ('#easyui-tabs').tabs('getTabIndex',tb);//获取当前选中tabs的index
		    topJQ('#easyui-tabs').tabs('close',index);//关闭对应index的tabs  
		}
		
		topJQ('#easyui-tabs').tabs('add',{
			title:title,
			iconCls:'icon-menu-rf',
			closable:true,
			content:'<iframe id="'+url.replaceAll("/","_")+'" name="page" width="100%" height="'+height+'" frameborder="0" src="'+url+'"></iframe>'
		});
		
	}
	
	var addTask = function() {
		var title = $("title").html() + " - 新增";
		var href = host + 'form';
		
		addTaskTab(href, title);
	}
	
	var editTask = function() {
		var id = getGridId();
		if (!id) {
			return false;
		}
		var title = $("title").html() + " - 编辑";
		var href = host + 'form?' + pk + '=' + id;
		
		addTaskTab(href, title);
	}
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container">
		<div id="module_search">
			<form id="module_search_form" class="easyui-form" method="post">
				<div class="module_search_input">
					分期夺宝名称：
					<select id="sel" class="easyui-combobox" name="periodId" data-options="required:true,editable:false">
						<option value="">[全部]</option>
						<c:forEach items="${list }" var="period">
							<option value="${period.periodId }">${period.treasureName }</option>
						</c:forEach>
					</select>
				</div>
				<div class="module_search_input">
					是否中奖：
					<select id="sel" class="easyui-combobox" name="isWinning" data-options="required:true,editable:false">
						<option value="">[全部]</option>
						<c:forEach items="${yesOrNos }" var="yn">
							<option value="${yn.key }">${yn.value }</option>
						</c:forEach>
					</select>
				</div>
				<div class="module_search_input">
					是否发放奖励：
					<select id="sel" class="easyui-combobox" name="isAward" data-options="required:true,editable:false">
						<option value="">[全部]</option>
						<c:forEach items="${yesOrNos }" var="yn">
							<option value="${yn.key }">${yn.value }</option>
						</c:forEach>
					</select>
				</div>
				<div class="module_search_button">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
				</div>
			</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="addTask()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit-rf" plain="true" onclick="editTask()">编辑</a>
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
</body>
</html>
