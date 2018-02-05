<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>配置考核人</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
<script type="text/javascript">
	 function open(rec){
		var id = $('#cc2').combobox('getValue');
	    var url = 'manage/getAdmin?aid='+id+'&orgCode='+rec.orgCode;    
	    $('#dg1').datagrid({    
	        url:url,    
	        columns:[[    
	        	{width : 50,title : 'adminCode',field : 'adminCode',checkbox : true}, 
		        {width : 200,title : '可选考核人',field : 'adminName'}
	        ]]    
	    });

	}
	 function getAssess(rec){
		var dg1 = "#dg1";
		var dg2 = "#dg2";
		var dataGridParamObj1 = {
				url : host + "getAdmin?aid="+rec.id+"&orgCode=",
		        columns:[[    
		  	        	{width : 50,title : 'adminCode',field : 'adminCode',checkbox : true}, 
		  		        {width : 200,title : '可选考核人',field : 'adminName'}
		  	      ]] 
			};
    	var dataGridParamObj2 = {
    		url : host + "getAssess?id="+rec.id,
            columns:[[    
      	        	{width : 50,title : 'id',field : 'id',checkbox : true}, 
      		        {width : 200,title : '已选考核人',field : 'assessAdmin'}
      	      ]] 
    	};

    	var dialogParamObj = {
    	
    	};
    	$(function() {
    		$(dg1).datagrid($.extend({}, dialogParamObj, dataGridParamObj1));
    		$(dg2).datagrid($.extend({}, dialogParamObj, dataGridParamObj2));
    	});
    	
	    var url = 'manage/getOrg';
	    $('#cc1').combobox('clear'); 
        $('#cc1').combobox('reload', url);  
	}


	 //获取可选考核人
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
	 //获取已选考核人
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
	 
	 function adds(){
		 	var id = $('#cc2').combobox('getValue');
			var ids = getGrid1();
			if (!ids) {
				return false;
			}
			 $.ajax({ 
			     url:host+"addAssess?id="+id+"&ids="+ids, 
			   	 cache:false,
			     success:function(){
			    	 $('#dg1').datagrid("clearSelections");
			    	 $('#dg2').datagrid("clearSelections");
			    	 $('#dg1').datagrid('reload'); 
			    	 $('#dg2').datagrid('reload'); 
			     },
			    }); 
	 }
	 function rem(){
			var ids = getGrid2();
			if (!ids) {
				return false;
			}
			 $.ajax({ 
			     url:host+"removeAssess?ids="+ids, 
			     cache:false,
			     success:function(){
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
 		 部门：<input id="cc1" class="easyui-combobox" data-options="valueField:'orgCode',textField:'orgName' ,onSelect:open" />  
		<table id="dg1" ></table>
    </div>   
    <div data-options="region:'west',collapsible:false" style="width:250px;">
   	 <font size="2" color="red">被考核人：</font>
   	 <input id="cc2" class="easyui-combobox" data-options="valueField: 'id',textField: 'targetAdmin',url: 'manage/getAssessAdmin?id=${bean.id}',	onSelect:getAssess" />  
   	 <table id="dg2"></table>
    </div>   
    <div data-options="region:'center'" style="padding:5px;background:#eee;text-align:center;">
   		<div style="margin-top:140px;">
   			<a id="add" href="#" class="easyui-linkbutton"  data-options="iconCls:'icon-add-rf'" onclick="adds()">添加</a>
   		</div>
   		<div style="margin-top:60px;">
   			<a id="remove" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove-rf'" onclick="rem()">删除</a>
   		</div>
   		<div style="margin-top:60px;"><font size="3" color="red">*请先选择被考核人*</font></div>
   </div>   
</div>
</body>
</html>>