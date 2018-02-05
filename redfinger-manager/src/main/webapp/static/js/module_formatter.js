//状态格式化（禁用、启用）
var formatterStop=function(value,row,index){
	return getDatagridDict('global.enable_status',value);
}

//时间格式化
var formatterTime=function(value,row,index){
	if(value){
		var d=new Date();
		d.setTime(value);
		return d.format("yyyy-MM-dd HH:mm:ss");
	}
}
//日期格式化
var formatterDate=function(value,row,index){
	if(value){
		var d=new Date();
		d.setTime(value);
		return d.format("yyyy-MM-dd");
	}
}

function getDatagridDict(category,value){
	var dictGroup=dict[category];
	for(var i in dictGroup){
		if(dictGroup[i].dictValue==value){
			if(dictGroup[i].themes){
				return '<span class="label label-'+dictGroup[i].themes+'">'+dictGroup[i].dictName+'</span>'
			}else{
				return dictGroup[i].dictName;
			}
		}
	}
}

function getDictByKey(value){
	for(var i in dict){
		var dictGroup=dict[i];
		for(var j in dictGroup){
			if(dictGroup[j].dictCode==value){
				return dictGroup[j].dictValue;
			}
		}
	}
}