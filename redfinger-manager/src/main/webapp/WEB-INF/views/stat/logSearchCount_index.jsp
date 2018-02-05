<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>日志搜索次数统计</title>
<meta name="decorator" content="stat" />
<script type="text/javascript">

var myChart = null;
var datas=null;
var echartsPath = "${ctxStatic}/echarts-2.2.1";
var title='搜索次数统计';
var legend="搜索";
var All=0;
function statSearch(){
	var type=$("#type").combobox('getValue');
	var begin=$("#begin").datebox('getValue');
	var end=$("#end").datebox('getValue');
	$.messager.progress();
	$.getJSON(host+'getChart',{type:type,begin:begin,end:end},function(data){
		chartDefaultCallback(data,function(data){
			datas=data;
			myChart.hideLoading();
			myChart.clear();
			  All = data.all;
			var subTitle=data.subTitle;
			var labelArray=data.label;
			var dataArray=data.number;
			  $("#floatExpire").html("&nbsp;&nbsp;&nbsp;总搜索次数 : " + All);
			myChart.setOption(getChartOptionMutiple(title,subTitle,legend,labelArray,dataArray));
		});
	})
}
function statExport(){
	if(datas && datas.label && datas.label.length>0){
		$("#exportForm").attr("action",host+'export');
		$("#exportHead").val("日期,搜索次数");
		$("#exportField").val("label,number");
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
			<div class="module_search_input">
				统计周期：
				<select class="easyui-combobox input_width_short" editable="false" id="type" data-options="onChange:typeChange">
					<option value="month">月</option>
					<option value="week">周</option>
					<option value="day">天</option>
				</select>
			</div>
			<div class="module_search_input">
				统计时段：
				<input type="text" class="easyui-datebox input_width_default" editable="false" id="begin" name="begin" data-options="onSelect:beginSelect"/>
				至
				<input type="text" class="easyui-datebox input_width_default" editable="false" id="end" name="end" data-options="onSelect:endSelect"/>
			</div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="statSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel-rf" plain="false" onclick="statExport()">导出</a>
				<a href="javascript:void(0)" id="floatExpire" >&nbsp;&nbsp;&nbsp;总搜索次数 :</a>
			</div>
		</form>
		</div>
			<div class="statistics_chart" id="statistics_chart" ></div>
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
