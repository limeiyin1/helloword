<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>客户年龄统计</title>
<meta name="decorator" content="stat" />
<script type="text/javascript">

var myChart = null;
var datas=null;
var echartsPath = "${ctxStatic}/echarts-2.2.1";
var title='客户年龄统计';
var legend="客户数量";
function statSearch(){
	$.messager.progress();
	$.getJSON(host+'getChart',{},function(data){
		chartDefaultCallback(data,function(data){
			datas=data;
			myChart.hideLoading();
			myChart.clear();
			var subTitle=data.subTitle;
			var labelArray=data.label;
			var dataArray=data.number;
			myChart.setOption(getChartOption(title,subTitle,legend,labelArray,dataArray));
		});
	})
}
function statExport(){
	if(datas && datas.label && datas.label.length>0){
		$("#exportForm").attr("action",host+'export');
		$("#exportHead").val("客户年龄段,客户数量");
		$("#exportField").val("label,number");
		$("#exportName").val(title+datas.subTitle);
		$("#exportDatas").val(JSON.stringify(datas));
		$("#exportForm").submit();
	}else{
		$.messager.alert('操作失败','无数据！',"warning");
	}
}
$(function(){
	statSearch();
});
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
		
		<div class="statistics_chart" id="statistics_chart"></div>
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



