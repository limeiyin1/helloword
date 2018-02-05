<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>游戏实时统计</title>
<meta name="decorator" content="stat" />
<script type="text/javascript">

var myChart = null;
var datas=null;
var echartsPath = "${ctxStatic}/echarts-2.2.1";
var title='游戏监控';
var legend=null;

function statSearch(){
/* 	var type=$("#type").combobox('getValue');
	var begin=$("#begin").datebox('getValue');
	var end=$("#end").datebox('getValue'); */
	$.messager.progress();
	$.getJSON(host+'getChart',{/* type:type,begin:begin,end:end */},function(data){
		chartDefaultCallback(data,function(data){
			datas=data;
			myChart.hideLoading();
			myChart.clear();
			var subTitle=data.subTitle;
			var labelArray=data.label;
			var dataArray=data.number;
			var dataArray1=data.number1;
		    legend=data.names
		    goodsIds=data.goodsIds;
			myChart.setOption(getChartOptionStack(title,subTitle,legend,labelArray,dataArray,dataArray1));
		});
	})
}
function statExport(){
	if(datas && datas.label && datas.label.length>0){
		$("#exportForm").attr("action",host+'export');
		$("#exportHead").val("日期,"+legend);
		$("#exportField").val("label,"+goodsIds);
		$("#exportName").val(title+datas.subTitle);
		$("#exportDatas").val(JSON.stringify(datas));
		$("#exportForm").submit();
	}else{
		$.messager.alert('操作失败','无数据！',"warning");
	}
	
}
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="statSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel-rf" plain="false" onclick="statExport()">导出</a>
			
			</div>
		</form>
		</div>
		
		<div class="statistics_chartStack" id="statistics_chart"></div>
	</div>
	<script type="text/javascript">
		require.config({
			paths : {
				echarts : echartsPath
			}
		});
		require([ 'echarts', 'echarts/chart/bar', 'echarts/chart/line',
				'echarts/chart/map' ], function(ec) {
			myChart = ec.init(document.getElementById('statistics_chart'));
		});
	</script>
</body>
</html>



