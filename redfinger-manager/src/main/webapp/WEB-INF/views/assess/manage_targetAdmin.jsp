<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>配置适用员工</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
<script type="text/javascript">
	var dg1 = "#dg1";
	var dg2 = "#dg2";
	var dataGridParamObj1 = {
		url : host + "getAdmin?id=${bean.id}&orgCode=",
        columns:[[    
  	        	{width : 50,title : 'adminCode',field : 'adminCode',checkbox : true}, 
  		        {width : 200,title : '可选适用员工',field : 'adminName'}
  	      ]] 
	};
	var dataGridParamObj2 = {
		url : host + "getTarget?id=${bean.id}",
		idField : 'id',
		columns:[[
			{width:50,title:'id',field:'id',checkbox:true},
			{width:200,title:'已选适用员工',field:'targetAdmin',sortable:true}
		]]
	};
	var dialogParamObj = {
	
	};
	$(function() {
		$(dg1).datagrid($.extend({}, dialogParamObj, dataGridParamObj1));
	});
	$(function() {
		$(dg2).datagrid($.extend({}, dialogParamObj, dataGridParamObj2));
	});

	 function open(rec){
	    var url = 'manage/getAdmin?id=${bean.id}&orgCode='+rec.orgCode;    
	    $('#dg1').datagrid({    
	        url:url,    
	        columns:[[    
	        	{width : 150,title : 'adminCode',field : 'adminCode',checkbox : true}, 
		        {width : 200,title : '部门员工',field : 'adminName'}
	        ]]    
	    });

	}

	 //获取选中的部门员工
	 var getGrid1 = function() {
			var row = $('#dg1').datagrid("getSelections");
			if (row == '') {
				$.messager.alert('提示', '请选择记录！', "info");
				return null;
			} else {
				var ids = [];
				for (var i = 0; i < row.length; i++) {
					ids[i] = row[i]['adminCode']
				}
				return ids.join(",");
			}
		}
	 //获取选中适用员工
	 var getGrid2 = function() {
			var row = $('#dg2').datagrid("getSelections");
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
	 //添加适用员工
	 function adds(){
			var ids = getGrid1();
			if (!ids) {
				return false;
			}
			 $.ajax({ 
			     url:host+"addTarget?id=${bean.id}&ids="+ids, 
			   	 cache:false,
			     success:function(data){
			    	 $('#dg1').datagrid("clearSelections");
			    	 $('#dg2').datagrid("clearSelections");
			    	 $('#dg1').datagrid('reload'); 
			    	 $('#dg2').datagrid('reload'); 
			     },
			    }); 
	 }
	 //删除适用员工
	 function rem(){
			var ids = getGrid2();
			if (!ids) {
				return false;
			}
			 $.ajax({ 
			     url:host+"removeTarget?id=${bean.id}&ids="+ids, 
			     cache:false,
			     success:function(data){
			    	 $('#dg1').datagrid("clearSelections");
			    	 $('#dg2').datagrid("clearSelections");
			    	 $('#dg1').datagrid('reload'); 
			    	 $('#dg2').datagrid('reload'); 
			     },
			 }); 
	 }
	 
</script>
	<div id="cc" class="easyui-layout" data-options="fit:true">   
    <div data-options="region:'east',collapsible:false,hideCollapsedContent:false" style="width:250px;">
   		部门：<input id="cc1" class="easyui-combobox" data-options="valueField: 'orgCode',textField: 'orgName',url: 'manage/getOrg',	onSelect:open" />   
		<table id="dg1" ></table>
    </div>   
    <div data-options="region:'west',collapsible:false" style="width:250px;">
    	<table id="dg2"></table>
    </div>   
    <div data-options="region:'center'" style="padding:5px;background:#eee;text-align:center;">
   		<div style="margin-top:140px;">
   			<a id="add" href="#" class="easyui-linkbutton"  data-options="iconCls:'icon-add-rf'" onclick="adds()">添加</a>
   		</div>
   		<div style="margin-top:60px;">
   			<a id="remove" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove-rf'" onclick="rem()">删除</a>
   		</div>
	</div>   
</div>
</body>
</html>>