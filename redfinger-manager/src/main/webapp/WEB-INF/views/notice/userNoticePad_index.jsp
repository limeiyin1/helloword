<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>用户</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
 
	var pk="padId";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField : 'padId',
		//onRowContextMenu: onRowContextMenu, //右键。
		onCheck: function(row){
			
		},
		columns:[[
		        	{width : 100,title : 'id',field :'padId',checkbox : true}, 
		        	{width : 100,title : '会员ID',field :'map.externalUserId',sortable : false}, 
		         	{width : 100,title : '设备名称',field:'padName',sortable : true},	
		        	{width : 100,title : '用户设备名称',field:'userPadNameT2',sortable : true},	 
		         	{width : 100,title : '设备编码',field:'padCode',sortable:true},
		         	{width : 50,title : '设备等级',field:'padGradeT2',sortable:true,formatter:formatterPadGradeStatus},
		         	{width : 100,title : '会员手机号',field:'map.bindMobile'},
		         	{width : 100,title : '会员邮箱',field:'map.userEmail'},
		         	{width : 100,title : '绑定时间',field:'bindTimeT2',sortable:true,formatter:formatterTime},
		         	{width : 100,title : '最近受控时间',field:'padControlTimeT2',sortable:true,formatter:formatterTime},
		    /*      	{width : 60,title : '剩余天数',field:'map.controltime'}, */
		         	{width : 60,title : '控制时间',field:'map.onlinetime',/* sortable:true ,formatter:formatterOnlineTime */ },
		         	{width : 100,title : '设备过期时间',field:'expireTimeT2',sortable:true,formatter:formatterTime},
		         	{width : 100,title : '设备IP',field:'padIp',sortable:true},
		         	{width : 100,title : '机房',field:'map.idcName'},
		         	{width : 100,title : '用户控制节点',field:'map.controlName',},
		         	{width : 100,title : '设备控制节点',field:'map.padControlName',},
		         	{width : 100,title : '设备管理控制节点',field:'map.manageControlName',},
		         /* 	{width : 50,title : '设备控制端口',field:'padControlPort',sortable:true},
		         	{width : 50,title : '设备管理端口',field:'padManagePort',sortable:true}, */
		         	/* {width : 50,title : '批次号',field:'batchNumber',sortable:true}, */
		         	{width : 60,title : '设备守护进程版本',field:'remoteVersion',sortable:true}, 
		         	{width : 100,title : '设备SN',field:'padSn',sortable:true},
		         	{width : 60, title : '维护状态',field:'maintStatus',sortable:true,formatter:function(value){return getDatagridDict('rf_pad.maint_status',value);}},
		           	{width : 60, title : '授权状态',field:'grantOpenStatus',sortable:true ,formatter:function(value){return getDatagridDict('rf_pad.grant_open_status',value);}},
		         	{width : 60, title : '虚拟状态',field:'vmStatus',sortable:true ,formatter:function(value){return getDatagridDict('rf_pad.vm_status',value);}},
		         	/* {width : 100,title : '冻结状态',field : 'activeStatus',sortable : true,formatter :formatterActiveStatus}, */
		         	{width : 60,title : '绑定状态',field : 'bindStatus',sortable : true,formatter :formatterBindStatus},
		         	{width : 60,title : '启用状态',field : 'enableStatus',sortable : true,formatter :formatterEnableStatus},
		         	{width : 60,title : '受控状态',field : 'padStatus',sortable : true,formatter :formatterOnlineStatus},
		         	{width : 60,title : '故障状态',field : 'faultStatus',sortable : true,formatter :formatterFaultStatus}
		         	
		         	] ]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	
	function notice(){
		var ids=getGridIdsAll();
		var title=$("title").html()+" - 发布公告";
      //加载搜索条件
		var arr = $(module_search_form).serializeArray();
		var obj ="&";
		$.each(arr, function(i, field) {
		obj+=field.name+"="+field.value+"&";
		});
		
		var href=host+'noticeForm?ids='+ids+obj;
		$("#button-save").unbind("click").click(noticeSave);
		openDialogFormNotice(title,href);
	}
	function noticeSave(){
		if($('#easyui-form').form("validate")){
			$.messager.progress();
			$('#easyui-form').form("submit");
		}
	}
 function openDialogFormNotice(title, href) {
		$(module_dialog).dialog({title : title,href: href,width:720,height:550,maximizable:true});
		$(module_dialog).dialog("open");
	
	}
 function getGridIdsAll() {
		var row = $(module_datagrid).datagrid("getSelections");
		if (row == '') {
			return '';
		} else {
			var ids = [];
			for (var i = 0; i < row.length; i++) {
				ids[i] = row[i][pk]
			}
			return ids.join(",");
		}
 }
 
 function onRowContextMenu(e, rowIndex, rowData){
	    e.preventDefault();
	    var selected=$("#module_datagrid").datagrid('getRows'); //获取所有行集合对象
	     var idValue = selected[rowIndex].uid;
	     $(this).datagrid('selectRecord', idValue);  //通过获取到的id的值做参数选中一行
	    $('#mm').menu('show', {
	        left:e.pageX,
	        top:e.pageY
	    });       
	}
 
 var dataGridParam = {
			fitColumns : true,
			pagination : true,
			striped : true,
			rownumbers : true,  
			singleSelect : false,
			idField : 'id',
			pageSize : 20,
			pageList : [ 20, 100, 200,500 ],
			loadFilter : loadFilterForDataGrid,
			loadMsg : "处理中，请稍后..."
		};

//绑定状态格式化（禁用、可用）
	function formatterBindStatus(value, row, index) {
		return getDatagridDict('rf_pad.bind_status',value);
	}
	//状态格式化（禁用、可用）
	function formatterEnableStatus(value, row, index) {
		return getDatagridDict('rf_pad.enable_status',value);
	}
	//在线状态
	function formatterOnlineStatus(value, row, index) {
		return getDatagridDict('rf_pad.pad_status',value);
	}
	//故障状态
	function formatterFaultStatus(value, row, index) {
		return getDatagridDict('rf_pad.fault_status',value)
	}
	//设备等级
	function formatterPadGradeStatus(value, row, index) {
		return getDatagridDict('rf_user_pad.pad_grade',value)
	}
	
	var noticePadCode =function(){
		var title=$("title").html()+"-设备编号发布公告";
		var href=host+'padCodeForm';
		$("#button-save").unbind("click").click(noticeSave);
		openDialogFormNotice(title,href);
	}
	
	function statExport(){
		var pager=$(module_datagrid).datagrid("getPager");
		var total=$(pager).pagination('options').total;
		if(total){
			var cols=dataGridParamObj.columns[0];
			var exportHead="";
			var exportField="";
			for(var i in cols){
				if(i!=0){
					exportHead=exportHead+cols[i].title+",";
					exportField=exportField+cols[i].field+",";
				}
			}
			var where="";
			var params=$(module_datagrid).datagrid('options').queryParams;
			for(var i in params){
				where+=i+"="+params[i]+"&";
			}
			$("#exportForm").attr("action",host+'export?'+where);
			$("#exportHead").val(exportHead);
			$("#exportField").val(exportField);
			$("#exportName").val('设备公告');
			$("#exportForm").submit();
		}else{
			$.messager.alert('操作失败','无数据！',"warning");
		}
	}
	
	
	function noticeParam(){
 		var title=$("title").html()+" - 发布公告";
 		var href=host+'param';
 		$("#button-save").unbind("click").click(noticeSave);
 		
 		
		$(module_dialog).dialog({
		    title: title,
		    width: 600,
		    height: 400,
		    closed: false,
		    cache: false,
		    href: href,
		    modal: true
		});
		$(module_dialog).dialog("open");
	}
	
</script>
<style type="text/css">

</style>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
		         <div class="module_search_input">
						会员ID:<input type="text" name="externalUserId" class="easyui-numberbox input_width_short" />
				 </div>
		         <div class="module_search_input">
						设备名称:<input type="text" name="padName" class="easyui-textbox input_width_default" />
				 </div>
				 <div class="module_search_input">
						用户设备名称:<input type="text" name="userPadNameT2" class="easyui-textbox input_width_default" />
				 </div>
	             <div class="module_search_input">
					设备SN:<input type="text" class="easyui-numberbox" editable="true"  name="padSn" style="width: 60px" />
				 </div>  
		         <div class="module_search_input">
						编码段:<input type="text" name="padCode" class="easyui-textbox input_width_default" />至
						<input type="text" name="padCode2" class="easyui-textbox input_width_default" />
				 </div>
				 <div class="module_search_input">
						IP:<input type="text" name="padIp" class="easyui-textbox input_width_default" />
				 </div>
				 <div class="module_search_input">
					最后受控时间:<input type="text" class="easyui-datebox input_width_default" editable="false"  name="padControlTime" />
				 </div>  
				 <div class="module_search_input">
						绑定时段:
						<input type="text" class="easyui-datebox input_width_default" editable="false"  name="beginTimeStr" />
						至
						<input type="text" class="easyui-datebox input_width_default" editable="false"  name="endTimeStr"/>
				 </div>
				 <div class="module_search_input" >		
					       启用:<select class="easyui-combobox input_width_short"  editable="false" name="enableStatus" data-options="required:false">
							<option value="">[全部]</option>
						   <fns:getOptions category="rf_pad.enable_status"  ></fns:getOptions>
					 	</select> 
				 </div>	  
				 <div class="module_search_input">		
				绑定:<select class="easyui-combobox input_width_short"  editable="false" name="bindStatus" data-options="required:false" >
						<option value="">[全部]</option>
					   <fns:getOptions category="rf_pad.bind_status"  ></fns:getOptions>
						</select> 
				 </div>	  
				 <div class="module_search_input">		
				故障:<select class="easyui-combobox input_width_short"  editable="false" name="faultStatus" data-options="required:false" >
						<option value="">[全部]</option>
					   <fns:getOptions category="rf_pad.fault_status"  ></fns:getOptions>
						</select> 
				 </div>	  
				 <div class="module_search_input">		
					 授权状态:<select class="easyui-combobox input_width_short"  editable="false" name="grantOpenStatus" data-options="required:false">
							<option value="">[全部]</option>
						   <fns:getOptions category="rf_pad.grant_open_status"  ></fns:getOptions>
							</select> 
				 </div>	
				 <div class="module_search_input">		
					 维护状态:<select class="easyui-combobox input_width_short"  editable="false" name="maintStatus" data-options="required:false">
							<option value="">[全部]</option>
						   <fns:getOptions category="rf_pad.maint_status"  ></fns:getOptions>
							</select> 
				 </div>	
				  <div class="module_search_input">		
					 虚拟状态:<select class="easyui-combobox input_width_short"  editable="false" name="vmStatus" data-options="required:false">
							<option value="">[全部]</option>
						   <fns:getOptions category="rf_pad.vm_status"  ></fns:getOptions>
							</select> 
				 </div>	
				 <div class="module_search_input">		
					 受控状态:<select class="easyui-combobox input_width_short"  editable="false" name="padStatus" data-options="required:false">
							<option value="">[全部]</option>
						   <fns:getOptions category="rf_pad.pad_status"  ></fns:getOptions>
							</select> 
				 </div>	
				 <div class="module_search_input">
						设备节点:<select editable="false" name="padControlId" class="easyui-combobox input_width_default" >
							<option value="">[全部]</option>
							<c:forEach var="one" items="${controlList}">
								<option value="${one.controlId}">${one.controlName}</option>
							</c:forEach>
						</select>
				 </div>
					 <div class="module_search_input">
						机房:<select editable="false" name="idcId" class="easyui-combobox input_width_default" >
							<option value="">[全部]</option>
							<c:forEach var="one" items="${idcList}">
								<option value="${one.idcId}">${one.idcName}</option>
							</c:forEach>
						</select>
					 </div>
				 <div class="module_search_input">		
				   使用状态:<select class="easyui-combobox input_width_short"  editable="false" name="leftOnlineTimeT2" data-options="required:false">
						<option value="">[全部]</option>
					    <option value="0">正常使用期</option>
					    <option value="-3">已过使用期</option>
					   <!--  <option value="-4">已过期</option> -->
						</select> 
			     </div>	
			      <div class="module_search_input">		
				      设备过期时间:<input type="text" class="easyui-datebox input_width_default" editable="false"  name="expireTime"/>
			     </div>	
			    <div class="module_search_input" >		
					      设备等级:<select class="easyui-combobox input_width_short"  editable="false" name="padGradeT2" data-options="required:false">
							<option value="">[全部]</option>
						   <fns:getOptions category="rf_user_pad.pad_grade"  ></fns:getOptions>
					 	</select> 
				 </div>	
			<div class="module_search_input">
				用户邮箱：<input type="text" name="userEmail" class="easyui-textbox input_width_default" />
			</div>
			<div class="module_search_input">
				用户手机：<input type="text" name="bindMobile" class="easyui-textbox input_width_default" />
			</div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-excel-rf',plain:true"onclick="statExport()">导出</a>
<!-- 	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-star-rf" plain="true" onclick="notice(callback)">发布公告</a> -->
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-star-rf" plain="true" onclick="noticePadCode()">设备编号发布公告</a>
	        <!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-star-rf" plain="true" onclick="noticeParam()">根据条件发布公告</a> -->
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
	   
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"   ></table>
	</div>
	
	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
    <div id="module_dialog_button" >
		<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a>
		<a href="javascript:void(0)" id="button-cancel" class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
	
		
	<div id="mm" class="easyui-menu" style="width:120px;">
    <div onClick="fresh()" data-options="iconCls:'icon-reload'">刷新</div>
    <div onClick="notice(callback)" data-options="iconCls:'icon-add'">发布公告</div>
    </div>
    
    <div class="hide">
		<form action="" id="exportForm" method="post" target="_blank">
			<input type="hidden" name="exportDatas" id="exportDatas" value=""/>
			<input type="hidden" name="exportHead" id="exportHead" value=""/>
			<input type="hidden" name="exportField" id="exportField" value=""/>
			<input type="hidden" name="exportName" id="exportName" value=""/>
		</form>
	</div>
</body>
</html>



