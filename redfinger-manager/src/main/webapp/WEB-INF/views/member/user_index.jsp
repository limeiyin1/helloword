<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>会员管理</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript" src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/jquery-jbox/2.3/i18n/jquery.jBox-zh-CN.js"></script>
<link type="text/css" rel="stylesheet" href="${ctxStatic}/jquery-jbox/2.3/Skins/Blue/jbox.css"/>
<script type="text/javascript">
var pk="userId";
var module_datagrid = "#module_datagrid";
var module_dialog = "#module_dialog";
var module_search_form = "#module_search_form"
var module_submit_form = "#module_submit_form";
var callback = defaultCallback ;
var dataGridParamObj = {
	url : host + "list",
	idField : 'userId',
	onCheck : function(row) {

	},
	columns : [ [
	 {width : 100,title : 'id',field : 'userId',checkbox : true},
	 {width : 100,title : '会员ID',field : "map.showUserId"}, 
	 {width : 100,title : '客户端来源',field:'client',sortable:true},
	 {width : 100,title : '来源',field:'userSource',sortable:true},
// 	 {width : 100,title : '会员名称',field : 'userName',sortable : true},
	 {width : 100,title : '会员邮箱',field : 'userEmail',sortable : true},
	 {width : 100,title : '会员电话',field : 'userMobilePhone',sortable : true},
	 {width : 100,title : '会员类型',field : 'userClassify',sortable : true,formatter:function(value){return getDatagridDict('rf_user.user_classify',value);}},
	 {width : 100,title : '积分等级',field : 'scoreGrade',sortable : true},
	 {width : 100,title : '积分数',field : 'scoreAmount',sortable : true},
	 {width : 100,title : '红豆币',field :  'rbcAmount',sortable : true}, 
	 //{width : 100,title : '普通设备',field :'firstApplyStatus',sortable : true},
	 //{width : 100,title : '体验设备',field :'firstProbationalStatus',sortable : true},
     //{width : 100,title : '绑定设备最大数',field :'bindPadMax',sortable : true},
     //{width : 100,title : 'vip设备数量',field :'map.vipPadCount',sortable : true},
     //{width : 100,title : '超级vip设备数量',field :'map.svipPadCount',sortable : true},
     {width : 100,title : '限制普通设备申请',field : 'limitUse',sortable : true,formatter:function(value){return getDatagridDict('global.yes_no',value);}},
     {width : 100,title : '性别',field : 'userGender',sortable :true,formatter:function(value){return getDatagridDict('global.gender',value);}},
     {width : 100,title : 'qq',field : 'qq',sortable : true},
     {width : 100,title : '普通设备剩余时间',field : 'leftOnlineTime',sortable : true},
     {width : 100,title : '登录时间',field:'loginTime',sortable:true,formatter:formatterTime},
     {width : 100,title : '注册时间',field:'createTime',sortable:true,formatter:formatterTime},
//   /*    {width : 100,title : '会员状态',field : 'userStatus',sortable : true/* ,formatter : formatterUser */}, */
	 {width : 100,title : '启用状态',field : 'enableStatus',sortable : true,formatter : formatterStop}
	] ]
};
var dialogParamObj = {

};
$(function() {
	$(module_datagrid).datagrid($.extend({}, dataGridParam, dataGridParamObj));
	$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
	$("#look").dialog($.extend({}, dialogParam, dialogParamObj));
});
//双击callback
var dblClickCallback=function(index,row){
	var title = $("title").html() + " - 查看";
	var href = host + 'look?'+pk+'=' + row[pk];
	$("#button-save").unbind("click").click(update);
	lookForm(title, href);
};

//查看	
function look() {
	var id = getGridId();
	if (!id){
		return false;
	}
	var title = $("title").html() + " - 查看";
	var href = host + 'look?'+pk+'=' + id;
	$("#button-save").unbind("click").click(update);
	lookForm(title, href);
}

//打开编辑对话框
function lookForm(title, href) {
	$("#look").dialog({title : title,href: href,width:699});
	$("#look").dialog("open");
}
function lookcancel() {
	$("#look").dialog("close");
}
//解绑
function relieve(callback) {
	var id=getGridId();
	if(!id)return false;
	var title=$("title").html()+" - 解绑设备";
	var href=host+'relieveForm?userId='+id;
	$("#button-save").unbind("click").click(userBindSave);
	openRelieveForm(title,href);
}
function openRelieveForm(title, href) {
	$(module_dialog).dialog({title : title,href: href,width:680});
	$(module_dialog).dialog("open");
}


//绑定
function binding(){
	var id=getGridId();
	if(!id)return false;
	var title=$("title").html()+" - 绑定设备";
	var href=host+'userForm?userId='+id;
	$("#button-save").unbind("click").click(userBindSave);
	openRelieveForm(title,href);
}
function userBindSave(){
	if($('#module_submit_form').form("validate")){
		$.messager.progress();
		$('#module_submit_form').form("submit");
	}
	
}
//更换renewal
function renewal(){
	var id=getGridId();
	if(!id)return false;
	var title=$("title").html()+" - 更换设备";
	var href=host+'renewalForm?userId='+id;
	$("#button-save").unbind("click").click(userBindSave);
	 openRelieveForm(title,href);
}
//异步加载
//短信发送
function add_checkbox(){
		var ids = getGridIds();
		if (!ids) {
			return false;
		}
		var title = $("title").html() + " - 当前发送（"+ids.split(",").length+"条）";
		var href = host + 'smsForm';
		$("#button-save").unbind("click").click(save_checkbox);
		openDialogForm(title, href);
	}
	function save_checkbox(){
		var ids = getGridIds();
		if ($(module_submit_form).form("validate")) {
			$.messager.progress();
			$(module_submit_form).form({
			    url:host+'sendCheckbox',
			    onSubmit: function(param){    
			    	param.ids = ids;    
			    }
			});
			$(module_submit_form).form("submit");
		}	
	}
	
	function add_search(){
		var total=$(module_datagrid).datagrid("getData").total;
		if(total<=0){
			$.messager.alert('提示', '无记录，可选择其他查询条件', "info");
			return false;
		}
		var title = $("title").html() + " - 当前发送（"+total+"条）";
		var href = host + 'smsForm';
		$("#button-save").unbind("click").click(save_search);
		openDialogForm(title, href);
	}
	function save_search(){
		if ($(module_submit_form).form("validate")) {
			$.messager.progress();
			$(module_submit_form).form({
			    url:host+'sendSearch',
			    onSubmit: function(param){    
			    	var params=$(module_datagrid).datagrid('options').queryParams;
			    	for(var i in params){
			    		param[i]=params[i];
			    	}
			    }
			});
			$(module_submit_form).form("submit");
		}	
	}
	function smsCallback(data) {
		$.messager.progress('close');
		cancel();
		if (jQuery.type(data) == 'string') {
			data = eval('(' + data + ')');
		}
		var info='<span class="jbox-content2">';
		info+='短信总数：'+data.total+'<br/>';
		info+='<span class="red">发送成功：'+data.success+'</span><br/>';
		info+='<span class="green">发送失败：'+data.fault+'</span><br/>';
		info+='详情请查看历史短信列表！';
		info+='</span>';
		$.jBox.messager(info, '短信发送结果!',5000);
	}
	function statExport() {
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
			$("#exportName").val('会员');
			$("#exportForm").submit();
		}else{
			$.messager.alert('操作失败','无数据！',"warning");
	      }
	}
	function rbcGet() {
		var id = getGridId();
		if (!id) {
			return false;
		}
		var title = "赠送红豆";
		var href = host + 'form?' + pk + '=' + id;
 		$("#button-save").unbind("click").click(userRbc);  
		openDialogForm(title, href); 
	}
	function rbcGets() {
		var title = "批量修改红豆";
		var href = host + 'formRbc';
 		$("#button-save").unbind("click").click(userRbc);  
		openDialogForm(title, href); 
	}
	
	function addTime(){
		var id = getGridId();
		if (!id) {
			return false;
		}
		var title = "增加时间";
		var href = host + 'addTimeForm?' + pk + '=' + id;
 		$("#button-save").unbind("click").click(userRbc);  
		openDialogForm(title, href); 
	}
	
	function batchUpdatetime(){
		var title = "批量修改时间";
		var href = host + 'batchUpdateTimeForm';
 		$("#button-save").unbind("click").click(userRbc);  
		openDialogForm(title, href); 
	}
	
	function rbcGive() {
		var title = "维护赠送红豆";
		var href = host + 'giveRbc';
 		$("#button-save").unbind("click").click(userRbc);  
		openDialogForm(title, href); 
	}
	
	function userRbc() {
		if ($('#module_submit_form').form("validate")) {
			$.messager.progress();
			$('#module_submit_form').form("submit");
		}
	}
	
	function maintAddTime(){
		var title = "维护赠送天数";
		var href = host + 'maintAddTimeForm';
 		$("#button-save").unbind("click").click(userRbc);  
		openDialogForm(title, href); 
	}
	
	function batchBindPad(){
		var title = "批量绑定设备";
		var href = host + 'batchBindPadForm';
 		$("#button-save").unbind("click").click(userRbc);  
		openDialogForm(title, href);
	}
	
	function batchBindLabel(){
		var title = "批量绑定标签";
		var href = host + 'batchBindLabelForm';
 		$("#button-save").unbind("click").click(userRbc);  
		openDialogForm(title, href);
	}
	
	function allowLoginIos(){
		var title = "允许登录IOS";
		var href = host + 'allowLoginIosForm';
 		$("#button-save").unbind("click").click(userRbc);  
		openDialogForm(title, href);
	}
	
	function bindLabel(){
		var id = getGridId();
		if (!id) {
			return false;
		}
		var title = "绑定标签";
		var href = host + 'bindLabelForm?' + pk + '=' + id;
 		$("#button-save").unbind("click").click(userRbc);  
		openDialogForm(title, href); 
	}
	
	function limitUse(){
		var title = "限制普通设备申请";
		var href = host + 'limitUseForm';
 		$("#button-save").unbind("click").click(userRbc);
		openDialogForm(title, href);
	}
	
	
	function midifyClassify(){
		// 判断是否选中一行
		var id = getGridId();
		if (!id) {
			return false;
		}
		// 获取行数据
		var row = $(module_datagrid).datagrid("getSelected");
		// 用户id, 用户邮箱, 用户手机, 用户类型(userId=2&userEmail=359371816@qq.com&userMobilePhone=13990108482&userClassify=2)
		var params = serializeRowData(row,"userId","userEmail","userMobilePhone","userClassify");
		// 设置标题
		var title = "修改会员类型";
		// 表单地址
		var href = host + 'classifyForm?' + params;
 		$("#button-save").unbind("click").click(updateClassify); 
		openDialogForm(title, href); 
	}
	
	// 将行数据转化为get提交参数
	function serializeRowData(row){
		
		 var params = "";
		 
		 for(var i = 1 ; i < arguments.length; i ++){
			 // 要获取的字段
		    var paramName = arguments[i];
		    // 根据指定字段得字段值
			var paramValue = eval("row."+paramName);
			if(paramValue){
				params = params + paramName + "=" + paramValue;
				if(i != arguments.length-1){
					params = params + "&";
				}
			}
			
		 }
		 
		 return params;
		
	}
	
    /* 更新会员类型*/
	var updateClassify = function() {
		if ($(module_submit_form).form("validate")) {
			$.messager.progress();
			$(module_submit_form).form("submit");
		}
	}
	
</script>
</head>
<body >
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search">
			<form id="module_search_form" class="easyui-form" method="post">
		        <div class="module_search_input">会员邮箱:<input type="text" name="userEmail" class="easyui-textbox input_width_default" /></div>  
			    <div class="module_search_input">会员名称:<input type="text" name="userName" class="easyui-textbox input_width_default" /></div>
			    <div class="module_search_input">会员手机:<input type="text" name="userMobilePhone" class="easyui-textbox input_width_default" /></div>
			    <div class="module_search_input">会员ID:<input type="text" name="externalUserId" class="easyui-numberbox input_width_default" /></div>  
			    <div class="module_search_input">昵称:<input type="text" name="nickName" class="easyui-textbox input_width_default" /></div> 
			    <div class="module_search_input" >		
			                   客户端来源:<select class="easyui-combobox input_width_short" style="width:80px;" editable="false" name="client" data-options="required:false">
					     <option value="">[全部]</option>
				         <c:forEach items="${clientSourceList}" var="client">
				         	<option value="${client}">${client}</option>
				         </c:forEach>
			 	      </select> 
		        </div>
			    <div class="module_search_input">
				创建时间:
				<input type="text" class="easyui-datebox input_width_default" editable="false"  name="createTimeBegin" />
				至
				<input type="text" class="easyui-datebox input_width_default" editable="false"  name="createTimeEnd"/>
		        </div>
			    <div class="module_search_input">
				登录时间:
				<input type="text" class="easyui-datebox input_width_default" editable="false"  name="beginTimeStr" />
				至
				<input type="text" class="easyui-datebox input_width_default" editable="false"  name="endTimeStr"/>
		        </div>
		        <div class="module_search_input" >		
			                     性别:<select class="easyui-combobox input_width_short"  editable="false" name="userGender" data-options="required:false">
					     <option value="">[全部]</option>
				         <fns:getOptions category="global.gender"></fns:getOptions>
			 	      </select> 
		        </div>	
		        <div class="module_search_input" >		
			                   用户来源:<select class="easyui-combobox input_width_short" style="width:210px;" editable="false" name="userSource" data-options="required:false">
					     <option value="">[全部]</option>
				         <c:forEach items="${userSourceList}" var="userSource">
				         	<option value="${userSource}">${userSource }</option>
				         </c:forEach>
			 	      </select> 
		        </div> 
		        <%-- 用户类型搜索 --%>
		        <div class="module_search_input" >		
			                  会员类型:<select class="easyui-combobox input_width_short" style="width:110px;"  editable="false" name="userClassify" data-options="required:false">
					     <option value="">[全部]</option>
				         <fns:getOptions category="rf_user.user_classify"></fns:getOptions>
			 	      </select> 
		        </div>
<%-- 		        <div class="module_search_input" >		
			                   用户活动来源:<select class="easyui-combobox input_width_short" style="width:210px;" editable="false" name="inviteType" data-options="required:false">
					     <option value="">[全部]</option>
				         <c:forEach items="${inviteTypeMap}" var="item">
				         	<option value="${item.key}">${item.value }</option>
				         </c:forEach>
			 	      </select> 
		        </div>  --%>
				<div class="module_search_button">
					<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
				</div>
			</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar" style="height: auto">
	           <c:if test="${not empty sessionScope.permission.button_member_user_export}">
				 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel-rf" plain="true" onclick="statExport()">导出</a>
			  </c:if>
				<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
			 <%--  <c:if test="${not empty sessionScope.permission.button_member_user_relieve}">
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-unlink-rf" plain="true" onclick="relieve(callback)">解绑</a>
			  </c:if>
			  <c:if test="${not empty sessionScope.permission.button_member_user_binding}">
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-link-rf" plain="true" id="binding" onclick="binding(callback)">绑定</a>
			  </c:if>
			  <c:if test="${not empty sessionScope.permission.button_member_user_renewal}">
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-star-rf" plain="true" onclick="renewal(callback)">更换</a>
			  </c:if> --%>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok-rf" plain="true" onclick="add_checkbox()">发送当前选中</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="add_search()">发送后台所有</a>
			  <!--   <a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a> -->
			    <a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-search" plain="true" onclick="look()">查看</a> 
				<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
				<c:if test="${not empty sessionScope.permission.button_member_user_rbc}">
					<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-rbc-rf" plain="true" onclick="rbcGet()">赠送红豆</a>
				</c:if>
 				<c:if test="${not empty sessionScope.permission.button_member_user_batch}">
					<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-rbc-rf" plain="true" onclick="rbcGets()">批量修改红豆</a>
				</c:if>
				<c:if test="${not empty sessionScope.permission.button_member_user_maintainBatch}">
					<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-rbc-rf" plain="true" onclick="rbcGive()">维护赠送红豆</a>
				</c:if>
				<c:if test="${not empty sessionScope.permission.button_member_user_addtime}">
					<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-add-rf" plain="true" onclick="addTime()">增加时间</a> 
				</c:if>
				<c:if test="${not empty sessionScope.permission.button_member_user_batchUpdate}">
					<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-add-rf" plain="true" onclick="batchUpdatetime()">批量修改时间</a> 
				</c:if>
				<%-- <c:if test="${not empty sessionScope.permission.button_member_user_miantAdd}">
					<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-add-rf" plain="true" onclick="maintAddTime()">维护赠送天数</a> 
				</c:if> --%>
				
				<%-- <c:if test="${not empty sessionScope.permission.button_member_user_batchBindPad}">
					<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-add-rf" plain="true" onclick="batchBindPad()">批量绑定设备</a> 
				</c:if> --%>
				<c:if test="${not empty sessionScope.permission.button_user_bindLabel}">
					<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-add-rf" plain="true" onclick="bindLabel()">绑定标签</a> 
				</c:if>
				<c:if test="${not empty sessionScope.permission.button_user_batchBindLabel}">
					<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-add-rf" plain="true" onclick="batchBindLabel()">批量绑定标签</a> 
				</c:if>
				
			<!-- 	<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-add-rf" plain="true" onclick="allowLoginIos()">允许登录ios</a>  -->
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="limitUse()">限制普通设备申请</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-edit-rf" plain="true" onclick="midifyClassify()">修改会员类型</a>
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar" ></table>
	</div>

	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button" ></div>
	<div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save"class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a>
		<a href="javascript:void(0)" id="button-cancel"class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
	<div id="look" buttons="#look_button"  ></div>
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



