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
var pk="id";
var module_datagrid = "#module_datagrid";
var module_dialog = "#module_dialog";
var module_search_form = "#module_search_form"
var module_submit_form = "#module_submit_form";
var callback = defaultCallback ;
var dataGridParamObj = {
	url : host + "list",
	idField : 'id',
	onCheck : function(row) {

	},
	columns : [ [
	 {width : 100,title :'id',field:'id',checkbox : true}, 
	 {width : 100,title : '会员ID',field:'externalUserIdT2',sortable : true},
	 {width : 100,title : '会员名称',field:'userNameT2',sortable : true},
	 {width : 100,title : '国家',field:'map.nationality',},
	 {width : 100,title : '省份',field:'map.province',},
	 {width : 100,title : '城市',field:'map.city',},
	 {width : 100,title : '区县',field:'map.contry'},
	 {width : 100,title : '会员资料邮箱',field:'userEmail',sortable : true},
	 {width : 100,title : '会员邮箱',field:'userEmailT2',sortable : true},
	 {width : 100,title : '会员电话',field:'userMobilePhoneT2',sortable : true},
	 {width : 100,title : 'ChannelId',field :'channelId',sortable : true},
	 {width : 100,title : 'OldChannelId',field:'channelIdOld',sortable : true},
	 {width : 100,title : '红豆币',field :'rbcAmountT2',sortable : true}, 
	 {width : 100,title : '申请体验状态',field:'applyExperStatusT2',sortable : true},
     {width : 100,title : 'MAC地址',field :'macId',sortable : true},
     {width : 100,title : 'IMEI',field:'imei',sortable : true},
     {width : 100,title : '性别',field:'userGenderT2',sortable :true,formatter:function(value){return getDatagridDict('global.gender',value);}},
     {width : 100,title : 'qq',field:'qqT2',sortable : true},
     {width : 100,title : '登录时间',field:'loginTimeT2',sortable:true,formatter:formatterTime},
	 {width : 100,title : '启用状态',field : 'enableStatusT2',sortable : true,formatter : formatterStop}
	] ]
};
var dialogParamObj = {

};
$(function() {
	$(module_datagrid).datagrid($.extend({}, dataGridParam, dataGridParamObj));
	$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
});

</script>
</head>
<body >
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search">
			<form id="module_search_form" class="easyui-form" method="post">
		        <div class="module_search_input">会员ID:<input type="text" name="externalUserIdT2"class="easyui-numberbox input_width_short" /></div>  
		        <div class="module_search_input">会员邮箱:<input type="text" name="userEmailT2"class="easyui-textbox input_width_default" /></div>  
			    <div class="module_search_input">会员名称:<input type="text" name="userNameT2"class="easyui-textbox input_width_default" /></div>
			    <div class="module_search_input">会员手机:<input type="text" name="userMobilePhoneT2"class="easyui-numberbox input_width_default" /></div>
			    <div class="module_search_input">登录次数:<input type="text" name="loginCountT2"class="easyui-numberbox input_width_default" /></div>   
			    <div class="module_search_input">
				登录时间:
				<input type="text" class="easyui-datebox input_width_default" editable="false"  name="beginTimeStr" />
				至
				<input type="text" class="easyui-datebox input_width_default" editable="false"  name="endTimeStr"/>
		        </div>
		        <div class="module_search_input" >		
			                     性别:<select class="easyui-combobox input_width_short"  editable="false" name="userGenderT2" data-options="required:false">
					     <option value="">[全部]</option>
				         <fns:getOptions category="global.gender"></fns:getOptions>
			 	      </select> 
		        </div>	  
				<div class="module_search_button">
					<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
				</div>
			</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar" >
	<%--            <c:if test="${not empty sessionScope.permission.button_member_user_export}">
				 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel-rf" plain="true" onclick="statExport()">导出</a>
			  </c:if>
				<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
			  <c:if test="${not empty sessionScope.permission.button_member_user_relieve}">
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-unlink-rf" plain="true" onclick="relieve(callback)">解绑</a>
			  </c:if>
			  <c:if test="${not empty sessionScope.permission.button_member_user_binding}">
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-link-rf" plain="true" id="binding" onclick="binding(callback)">绑定</a>
			  </c:if>
			  <c:if test="${not empty sessionScope.permission.button_member_user_renewal}">
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-star-rf" plain="true" onclick="renewal(callback)">更换</a>
			  </c:if>
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
				</c:if> --%>
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar" ></table>
	</div>

	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button" ></div>
	<div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save"class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a>
		<a href="javascript:void(0)" id="button-cancel"class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
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



