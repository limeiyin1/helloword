<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>监控数据</title>
<meta name="decorator" content="default" />
</head>
<body>
	<script type="text/javascript">
	
	function doSearch(){
		$.post(host + "monitorList",{id: $('#itemid').combobox('getValue'),beginTimeStr:$('#twoid').datetimebox('getValue')},function(data){
			  $('#module_datagrid_monitor').treegrid('loadData',data);
			},'json');
		
		/* $('#module_datagrid_monitor').treegrid('load',{
			id: $('#itemid').val(),
			beginTimeStr: $('#twoid').val()
		}); */
	}

	var parentKey = "";
	var module_datagrid_monitor = "#module_datagrid_monitor";
	var callback = defaultCallback ;
	var treeGridParamObj = {
		    url : host + "monitorList",
	
			onCheck : function(index,row) {
			},
			columns : [ [
                       
			         	{width : 100,title : '游戏名称',field:'name',sortable : true},
			         	{width : 100,title : '在线',field:'online',sortable:true},
			         	{width : 100,title:'离线',field:'offline',sortable:true},
			
			         	] ]
		};
		var dialogParamObj = {

		};
		$(function() {
			$(module_datagrid_monitor).treegrid($.extend({}, treeGridParam, treeGridParamObj));
		
		});
		
		// grid搜索条件清除
		 function gridReset() {
			$(user_search_form).form("reset");
		}
		
	</script>	
	
	<div id="module_submit_container">
	<div class="module_search_button">
			<form id="user_search_form" method="post">
				游戏名称: <select id="itemid" data-options="editable:false"  class="easyui-combobox input_width_default" style="width: 160px">
							<option value="">[全部]</option>
							<c:forEach var="one" items="${monitorlist}">
								<option value="${one.cfgId}">${one.gameName}</option>
							</c:forEach>
						</select>
			       
				时间:<input id="twoid" type="text" name="beginTimeStr" class="easyui-datetimebox" data-options="editable:false,showSeconds:true"/>
				<a href="#" data-options="iconCls:'icon-search-rf',plain:false" class="easyui-linkbutton" onclick="doSearch()">查询</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reset-rf',plain:false" onclick="gridReset()">清空</a>
			</form>
	</div> 

    </div>
    <table id="module_datagrid_monitor" toolbar="module_datagrid_monitor"></table>
</body>
</html>



