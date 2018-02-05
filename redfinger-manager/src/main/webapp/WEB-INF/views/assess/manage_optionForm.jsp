<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title></title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
<script type="text/javascript">
 var module_datalist_renewal1 = "#dl1";
 var module_datalist_renewal2 = "#dl2";

 var dataListParamObj1 = {
		url : host + "optionList?id=${bean.id}",
		  
		    columns:[[
		  			{width:50,title:'id',field:'id',checkbox:true},
		  			{width:250,title:'考核题目',field:'name',sortable:true}
		  		]]
	};
var dataListParamObj2 = {
		url : host + "myOptionList?id=${bean.id}",
		
		    columns:[[
		  			{width:50,title:'id',field:'id',checkbox:true},
		  			{width:250,title:'考核题目',field:'name',sortable:true}
		  		]]
	};

	var dialogParamObj={
		
		};
	$(function() {
		$(module_datalist_renewal1).datagrid(
				$.extend({}, dataListParam, dataListParamObj1));
		$(module_datalist_renewal2).datagrid(
				$.extend({}, dataListParam, dataListParamObj2));
		
	});
	
	var dataListParam = {
			
			
		}; 
	var addOptions=function(){
		var ids=getGrid1()
		if (!ids) {
			return false;
		}
		 $.ajax({
			url:host+"addOption?id=${bean.id}&ids="+ids, 
			cache:false,
			success:function(){
				$(module_datalist_renewal1).datagrid(
						$.extend({}, dataListParam, dataListParamObj1));
				$(module_datalist_renewal2).datagrid(
						$.extend({}, dataListParam, dataListParamObj2));
			}
			
		} );
	}
	var removeOption=function(){
		var ids=getGrid2();
		if (!ids) {
			return false;
		}
		 $.ajax({
				url:host+"removeOption?id=${bean.id}&ids="+ids,
				cache:false,
			success:function(){
				$(module_datalist_renewal1).datagrid(
						$.extend({}, dataListParam, dataListParamObj1));
				$(module_datalist_renewal2).datagrid(
						$.extend({}, dataListParam, dataListParamObj2));
			}
		});
		
	}
	 //获取选中题目
	 var getGrid1 = function() {
			var row = $('#dl1').datagrid("getSelections");
			if (row == '') {
				$.messager.alert('提示', '请选择记录！', "info");
				return null;
			} else {
				var ids = [];
				for (var i = 0; i < row.length; i++) {
					ids[i] = row[i]['id']
				}
				return ids.join(",");
			}
		}
	 //获取选中已项目已有的题目
	 var getGrid2 = function() {
			var row = $('#dl2').datagrid("getSelections");
			if (row == '') {
				$.messager.alert('提示', '请选择记录！', "info");
				return null;
			} else {
				var ids = [];
				for (var i = 0; i < row.length; i++) {
					ids[i] = row[i]['id']
				}
				return ids.join(",");
			}
		}

	
	function doSearch() {
		$('#dl1').datagrid('load', {
			name : $('#name').val(),
			keyword : $('#keyword').val()
		});
	}	
	
	$(document).keydown(function(event){
		if(event.keyCode==13){
			doSearch();
		}
	});
	
	
       
</script>
	<div id="cc" class="easyui-layout" data-options="fit:true">   
    <div data-options="region:'east',collapsible:false,hideCollapsedContent:false" style="width:300px;">
    	 <div >
			<form id="form" class="easyui-form" method="post">
				<div><input type="text" id="name" name="name" class="easyui-textbox" style="width:200px"/><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="doSearch()">搜索</a>
				</div>
			</form>
		</div>  
    	<div id="dl1"></div>
    	 
    </div>   
    <div data-options="region:'west',collapsible:false" style="width:300px;">
    	<div id="dl2"></div>
    </div>   
    <div data-options="region:'center'" style="padding:5px;background:#eee;text-align:center;">
   	 <div style="margin-top:140px;">
    	<a id="add" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add-rf'" onclick="addOptions()">添加</a><br>
    </div>
    	<div style="margin-top:60px;">
    	<a id="remove" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove-rf'" onclick="removeOption()">移除</a>
    	</div>
    </div>   
</div>

</body>
</html>



