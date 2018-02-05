<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>设备</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
var pk="padId";
var module_datagrid = "#module_datagrid";
var module_dialog = "#module_dialog";
var module_search_form = "#module_search_form"
var module_submit_form = "#module_submit_form";
var callback = defaultCallback ;
var dataGridParamObj = {
	url : host + "list",
	idField : 'padId',
	onCheck : function(index,row) {
	//状态为绑定时不能绑定
		var flag=row.bindStatus;
		if(flag==1){
			$("#binding").linkbutton('disable');

		}else{
			$("#binding").linkbutton('enable');

		}
	},
	columns : [ [
	         	{width : 100,title : 'id',field :'padId',checkbox : true}, 
	         	{width : 100,title : '设备名称',field:'padName',sortable : true},
	         	{width : 100,title : '设备编码',field:'padCode',sortable:true},
	         	{width : 100,title : '设备等级',field:'padGradeT2',sortable:true,formatter:formatterPadGradeStatus},
	         	{width : 100,title : '会员手机号',field:'map.userMobilePhone',sortable:true},
	         	{width : 100,title : '绑定时间',field:'bindTimeT2',sortable:true,formatter:formatterTime},
	         	{width : 100,title : '最近受控时间',field:'padControlTimeT2',sortable:true,formatter:formatterTime},
	         	{width : 50,title : '剩余天数',field:'leftOnlineTimeT2',sortable:true},
	         	{width : 100,title : '剩余时间',field:'leftControlTimeT2',sortable:true/* ,formatter:formatterOnlineTime */},
	         	{width : 100,title : '设备IP',field:'padIp',sortable:true},
	         	{width : 100,title : '机房',field:'map.idcName',sortable:true},
	         	{width : 100,title : '用户控制节点',field:'map.controlName',},
	         	{width : 100,title : '设备控制节点',field:'map.padControlName',},
	         	{width : 100,title : '设备管理控制节点',field:'map.manageControlName',},
	         	{width : 50,title : '设备控制端口',field:'padControlPort',sortable:true},
	         	{width : 50,title : '设备管理端口',field:'padManagePort',sortable:true},
	         	{width : 50,title : '批次号',field:'batchNumber',sortable:true},
	         	{width : 60,title : '设备守护进程版本',field:'remoteVersion',sortable:true},
	         	{width : 100,title : '描述',field : 'remark'},
	         	/* {width : 100,title : '冻结状态',field : 'activeStatus',sortable : true,formatter :formatterActiveStatus}, */
	         	{width : 60,title : '绑定状态',field : 'bindStatus',sortable : true,formatter :formatterBindStatus},
	         	{width : 60,title : '启用状态',field : 'enableStatus',sortable : true,formatter :formatterEnableStatus},
	         	{width : 60,title : '受控状态',field : 'padStatus',sortable : true,formatter :formatterOnlineStatus},
	         	{width : 60,title : '故障状态',field : 'faultStatus',sortable : true,formatter :formatterFaultStatus}
	         	
	         	] ]
};
var dialogParamObj = {

};
$(function() {
	$(module_datagrid).datagrid($.extend({}, dataGridParam, dataGridParamObj));
	$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
	$("#look").dialog($.extend({}, dialogParam, dialogParamObj));
});
//绑定
function binding(){
	var id=getGridId();
	if(!id)return false;
	var title=$("title").html()+" - 绑定设备";
	var href=host+'userForm?padId='+id;
	$("#button-save").unbind("click").click(userBindSave);
	openDialogForm(title,href);
}
//绑定Admin
function bindAdmin(){
	var id=getGridId();
	if(!id)return false;
	var title=$("title").html()+" - 管理员绑定设备";
	var href=host+'adminForm?padId='+id;
	$("#button-save").unbind("click").click(userBindSave);
	openDialogForm(title,href);
}

 //解绑
	function relieve(callback) {
		var ids = getGridIds();
		if (!ids) {
			return false;
		}
		$.messager.confirm('确认？', '确认解绑该设备?', function(confirm) {
				if (confirm) {
			if (ids.indexOf(',') != -1) {
				ajaxPost("relieve", {
					ids : ids
				}, callback);
			} else {
				ajaxPost("relieve", {
					ids : ids
				}, callback);
			}}
		});
	}
	function userBindSave() {
		if ($('#module_submit_form').form("validate")) {
			$.messager.progress();
			$('#module_submit_form').form("submit");
		}
	}
	//设备等级
	function formatterPadGradeStatus(value, row, index) {
		return getDatagridDict('rf_user_pad.pad_grade',value)
	}
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
	//冻结状态
	function formatterActiveStatus(value, row, index) {
		return getDatagridDict('rf_pad.active_status',value)
	}
	//查看	
	function look() {
		var id = getGridId();
		if (!id) {
			return false;
		}
		var title = $("title").html() + " - 查看";
		var href = host + 'look?' + pk + '=' + id;
		$("#button-save").unbind("click").click(update);
		lookForm(title, href);
	}
	function lookcancel() {
		$("#look").dialog("close");
	}
	//打开编辑对话框
	function lookForm(title, href) {
		$("#look").dialog({title : title,href: href,width:555});
		$("#look").dialog("open");
	}
	function addOnly() {
		var title = $("title").html() + " - 新增";
		var href = host + 'formOnly';
		$("#button-save").unbind("click").click(save);
		openDialogForm(title, href);
	}
	//激活
	
	function unlock(callback) {
		var ids = getGridIds();
		if (!ids) {
			return false;
		}
		ajaxPost("active", {
			ids : ids
		}, callback);
	}
	//冻结
	function locked(callback) {
		var ids = getGridIds();
		if (!ids) {
			return false;
		}
		ajaxPost("freeze", {
			ids : ids
		}, callback);
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
 			$("#exportName").val('设备');
 			$("#exportForm").submit();
 		}else{
 			$.messager.alert('操作失败','无数据！',"warning");
 		}
 	}
 	
 	function upVIP() {
 		var id = getGridId();
 		if (!id) {
 			return false;
 		}
 		var title = $("title").html() + " - 编辑";
 		var href = host + 'upVIPForm?' + pk + '=' + id;
 		$("#button-save").unbind("click").click(userBindSave);
 		openDialogForm(title,href);
 	}
 	
 	//时间格式化
 	 function formatterOnlineTime (value,row,index){
 		if(value){
 			
 			return value/60;
 		}
 	}
</script>
</head>
<body >
	<!-- 表格  -->
  <div id="module_container" >
<!-- 	//查询条件 1.绑定的手机号码2.大于等于绑定时间3.小于等于绑定时间4.设备控制节点5.设备状态6.绑定状态7.启用状态8.故障状态9.设备IP10.机房11.设备编码12.绑定的用户帐号13.为控制天数 -->
	 <div id="module_search">
		<form id="module_search_form" class="easyui-form" method="post">
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
			<!-- <div class="module_search_input">会员:<input type="text" name="userIdT2"class="easyui-textbox input_width_default"/></div> -->
				<!-- <div class="module_search_input">手机号:<input type="text" name="bindMobileT2"class="easyui-textbox input_width_default" /></div> -->
			
				<div class="module_search_button">
					<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
				</div>
		</form>
	 </div>
	 <div id="module_toolbar" class="easyui-toolbar">
	 <%--       <c:if test="${not empty sessionScope.permission.button_facility_pad_export}">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel-rf" plain="true" onclick="statExport()">导出</a>
		   </c:if>
			<c:if test="${not empty sessionScope.permission.button_facility_pad_addfacility}">
				<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-add-rf" plain="true" onclick="addOnly()">新增</a> 
				<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-add-rf" plain="true" onclick="add()">批量新增</a> 
			</c:if>
			<c:if test="${not empty sessionScope.permission.button_facility_pad_update}">
				<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a>
			</c:if> 
			<c:if test="${not empty sessionScope.permission.button_facility_pad_start}">
				<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-start-rf" plain="true" id="start" onclick="start(callback)">启用</a>
			</c:if>
			<c:if test="${not empty sessionScope.permission.button_facility_pad_stop}">
				<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
			</c:if>
			<c:if test="${not empty sessionScope.permission.button_facility_pad_relieve}">
		    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-star-rf" plain="true" onclick="relieve(callback)">解绑</a>
		    </c:if>
		    <c:if test="${not empty sessionScope.permission.button_facility_pad_binding}">
		    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-star-rf" plain="true" id="binding" onclick="binding(callback)">绑定</a>
		    </c:if>
			<c:if test="${not empty sessionScope.permission.button_facility_pad_delete}">
				<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a>
            </c:if>	
            <c:if test="${not empty sessionScope.permission.button_facility_pad_bindAdmin}">
			 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-star-rf" plain="true" id="binding" onclick="bindAdmin(callback)">管理员绑定</a>
            </c:if>	 --%>
        <%--     <c:if test="${not empty sessionScope.permission.button_facility_pad_freeze}">
				<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-locked-rf" plain="true" onclick="locked(callback)">冻结</a>
			</c:if>
			<c:if test="${not empty sessionScope.permission.button_facility_pad_active}">
				<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-unlock-rf" plain="true" onclick="unlock(callback)">激活</a>
			</c:if> --%>
				
			<c:if test="${not empty sessionScope.permission.button_facility_pad_change}">
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-star-rf" plain="true" onclick="change(callback)">设备换新</a>
			</c:if>
		  	<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-search" plain="true" onclick="look()">查看</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
	 </div>
		<table id="module_datagrid" toolbar="#module_toolbar" ></table>
 </div>

	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
	<div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save"
			class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a> <a
			href="javascript:void(0)" id="button-cancel"
			class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
	<div id="look" buttons="#look_button"  data-options="maximizable:true"></div>
	<div id="look_button">
     <a href="javascript:void(0)"  class="easyui-linkbutton" iconCls="icon-no" onclick="lookcancel()">关闭</a>
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



