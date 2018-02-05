/**
 * 
 */

// 获取周的第一天
function getWeekFirstDay(date) {
	var day = date.getDay();
	if (day == 0) {
		day = 6;
	} else {
		day = day - 1;
	}
	var cdt = new Date(date.getTime() - 1000 * 60 * 60 * 24 * day);
	return cdt.format("yyyy-MM-dd");
}

// 获取周的最后一天
function getWeekLastDay(date) {
	var day = date.getDay();
	if (day != 0) {
		day = 7 - day;
	}
	var cdt = new Date(date.getTime() + 1000 * 60 * 60 * 24 * day);
	return cdt.format("yyyy-MM-dd");
}

// 获取月的第一天
function getMonthFirstDay(date) {
	date.setDate(1);
	return date.format("yyyy-MM-dd");
}
// 获取月的最后一天
function getMonthLastDay(date) {
	date.setDate(1);
	date.setMonth(date.getMonth() + 1);
	var cdt = new Date(date.getTime() - 1000 * 60 * 60 * 24);
	return cdt.format("yyyy-MM-dd");
}

function typeChange() {
	$("#begin").datebox('clear');
	$("#end").datebox('clear');
}
function beginSelect(date) {
	var type = $("#type").combobox('getValue');
	var sdate = date.format("yyyy-MM-dd")
	if (type == 'month') {
		var sdate = getMonthFirstDay(date);
	} else if (type == 'week') {
		var sdate = getWeekFirstDay(date);
	}
	$("#begin").datebox('setValue', sdate);
}
function endSelect(date) {
	var type = $("#type").combobox('getValue');
	var sdate = date.format("yyyy-MM-dd")
	if (type == 'month') {
		var sdate = getMonthLastDay(date);
	} else if (type == 'week') {
		var sdate = getWeekLastDay(date);
	}
	$("#end").datebox('setValue', sdate);
}

function chartDefaultCallback(data, func) {
	$.messager.progress('close');
	if (jQuery.type(data) == 'string') {
		data = eval('(' + data + ')');
	}
	if (data.code && data.message) {
		var code = parseInt(data.code);
		switch (code) {
		case 200:
			// 调用回调函数
			func(data);
			break;
		case 500:
			$.messager.alert('操作失败', '操作失败，请联系管理员！', "error");
			break;
		case 501:
			$.messager.alert('操作失败', data.message, "warning");
			break;
		case 304:
			alert("操作失败，" + data.message);
			top.location.href = ctx+'/login.jsp';
			break;
		}
	} else {
		func(data);
	}
}

// 获取echart默认配置
function getChartOption(title, subTitle, legend, labelArray, dataArray) {
	var option = {
		title : {
			text : title,
			subtext : subTitle
		},
		tooltip : {
			trigger : 'item'
		},
		legend : {
			data : [ legend ]
		},
		toolbox : {
			show : true,
			feature : {
				magicType : {
					show : true,
					type : [ 'line', 'bar' ]
				},
				saveAsImage : {
					show : true
				}
			}
		},
		xAxis : [ {
			type : 'category',
			data : labelArray
		} ],
		yAxis : [ {
			type : 'value',
			splitArea : {
				show : true
			}
		} ],
		series : [ {
			name : legend,
			type : 'bar',
			itemStyle : {
				normal : {
					label : {
						show : true,
						position : 'top'
					}
				}
			},
			data : dataArray
		} ]
	};
	return option;
}
function getChartOptionMutiple(title, subTitle, legend, labelArray, dataArray) {
	var series=[];
	var legendArr=legend.split(",");
	var arrayNumber=dataArray.length/legendArr.length;
	for(var i=0;i<legendArr.length;i++){
		var obj={};
		var itemStyle={normal : {label : {show : true,position : 'top'}}};
		obj.itemStyle=itemStyle;
		obj.type='bar';
		obj.name=legendArr[i];
		obj.data=dataArray.slice(i*arrayNumber,(i+1)*arrayNumber);
		series.push(obj);
	}
	var option = {
		title : {
			text : title,
			subtext : subTitle
		},
		tooltip : {
			trigger : 'item'
		},
		legend : {
			data : legendArr
		},
		toolbox : {
			show : true,
			feature : {
				magicType : {
					show : true,
					type : [ 'line', 'bar' ]
				},
				saveAsImage : {
					show : true
				}
			}
		},
		xAxis : [ {
			type : 'category',
			data : labelArray
		} ],
		yAxis : [ {
			type : 'value',
			splitArea : {
				show : true
			}
		} ],
		series : series
	};
	return option;
}

function getPieOption(title, subTitle, legend, labelArray, dataArray) {
	option = {
		title : {
			text : title,
			subtext : subTitle,
			x : 'center'
		},
		tooltip : {
			trigger : 'item',
			formatter : "{a} <br/>{b} : {c} ({d}%)"
		},
		legend : {
			orient : 'vertical',
			x : 'left',
			data : labelArray
		},
		toolbox : {
			show : true,
			feature : {
				saveAsImage : {
					show : true
				}
			}
		},
		series : [ {
			name : legend,
			type : 'pie',
			radius : '55%',
			center : [ '50%', '60%' ],
			data :dataArray
		} ]
	};
	return option;
}

function getChartOptionStack(title, subTitle, legend, labelArray, dataArray,dataArray1) {

	var option = {
			title : {
				text : title,
				subtext : subTitle
			},
		    tooltip : {
		        trigger: 'item',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        },
		        formatter: function (params){
		            return params[0].name + '<br/>'
		                   + params[0].seriesName + ' : ' + params[0].value + '<br/>'
		                   + params[1].seriesName + ' : ' + (params[1].value + params[0].value);
		        }
		    },
		    legend: {
		        selectedMode:false,
		        data:['在线', '离线']
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    xAxis : [
		        {
		            type : 'category',
		            data :labelArray
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value',
		            boundaryGap: [0, 0.1]
		        }
		    ],
		    series : [
		        {
		            name:'在线',
		            type:'bar',
		            stack: 'sum',
		            barCategoryGap: '50%',
		            itemStyle: {
		                normal: {
		                    color: 'tomato',
		                    barBorderColor: 'tomato',
		                    barBorderWidth: 6,
		                    barBorderRadius:0,
		                    label : {
		                        show: true, position: 'insideTop'
		                    }
		                }
		            },
		            data:dataArray
		        },
		        {
		            name:'离线',
		            type:'bar',
		            stack: 'sum',
		            itemStyle: {
		                normal: {
		                    color: '#fff',
		                    barBorderColor: 'tomato',
		                    barBorderWidth: 6,
		                    barBorderRadius:0,              
		                    label : {
		                        show: true, 
		                        position: 'top',
		                        formatter: function (params) {
		                            for (var i = 0, l = option.xAxis[0].data.length; i < l; i++) {
		                                if (option.xAxis[0].data[i] == params.name) {
		                                    return option.series[0].data[i] + params.value;
		                                }
		                            }
		                        },
		                        textStyle: {
		                            color: 'tomato'
		                        }
		                    }
		                }
		            },
		            data:dataArray1
		        }
		    ]
		};
		                    
	return option;
}
