<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>新用户按钮操作统计</title>
<meta name="decorator" content="stat" />
<script type="text/javascript">

var myChart = null;
var datas=null;
var echartsPath = "${ctxStatic}/echarts-2.2.1";
var title='新用户按钮操作统计';
var legend;
function statSearch(){
	legend= $("#where").datebox('getText');
	var where=$("#where").datebox('getValue');
	var type=$("#type").val();
	var begin=$("#begin").datebox('getValue');
	var end=$("#end").datebox('getValue');
	$.messager.progress();
	$.getJSON(host+'getChart',{type:type,begin:begin,end:end,where:where},function(data){
		chartDefaultCallback(data,function(data){
			datas=data;
			myChart.hideLoading();
			myChart.clear();
			var All =data.numAll;
			var subTitle=data.subTitle;
			var labelArray=data.label;
			var dataArray=[];
			for(var i=0;i<labelArray.length;i++){
				var obj={};
				obj.value=data.number[i];
				obj.name=data.label[i];
				dataArray.push(obj);
			}
			 $("#floatExpire").html("&nbsp;&nbsp;&nbsp;总次数 : " + All);
			myChart.setOption(getPieOption(title+"_"+legend,subTitle,legend,labelArray,dataArray));
		});
	})
}
function statExport(){
	if(datas && datas.label && datas.label.length>0){
		$("#exportForm").attr("action",host+'export');
		$("#exportHead").val(legend+"按钮,点击次数");
		$("#exportField").val("label,number");
		$("#exportName").val(title+"_"+legend);
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
					操作步骤：<select class="easyui-combobox input_width_default" editable="false" name="where" id="where">
					<option value="">[全部]</option>
					<option value="1">第 1 步</option>
					<option value="2">第 2 步</option>
					<option value="3">第 3 步</option>
					<option value="4">第 4 步</option>
					<option value="5">第 5 步</option>
					<option value="6">第 6 步</option>
					<option value="7">第 7 步</option>
					<option value="8">第 8 步</option>
					<option value="9">第 9 步</option>
					<option value="10">第10步</option>
					<option value="11">第11步</option>
					<option value="12">第12步</option>
					<option value="13">第13步</option>
					<option value="14">第14步</option>
					<option value="15">第15步</option>
					<option value="16">第16步</option>
					<option value="17">第17步</option>
					<option value="18">第18步</option>
					<option value="19">第19步</option>
					<option value="20">第20步</option>
				</select>
				</div>
			<div class="module_search_input hide" >
				统计周期：
				<select class="easyui-combobox input_width_short" editable="false" id="type" data-options="">
						<option value="day">天</option>
				</select>
			</div>
			<div class="module_search_input">
				统计时段：
				<input type="text" class="easyui-datebox input_width_default" editable="false" id="begin" name="begin" data-options=""/>
				至
				<input type="text" class="easyui-datebox input_width_default" editable="false" id="end" name="end" data-options=""/>
			</div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="statSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel-rf" plain="false" onclick="statExport()">导出</a>
				<a href="javascript:void(0)" id="floatExpire" >&nbsp;&nbsp;&nbsp;总次数 :</a>
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
		require([ 'echarts', 'echarts/chart/bar', 'echarts/chart/line','echarts/chart/pie',
				'echarts/chart/map' ], function(ec) {
			myChart = ec.init(document.getElementById('statistics_chart'));
		});
	</script>
</body>
</html>



