<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>优惠劵管理</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="couponId";
	var module_datagrid = "#module_datagrid";
	var module_dialog = "#module_dialog";
	var module_search_form = "#module_search_form";
	var module_submit_form = "#module_submit_form";
	var callback = defaultCallback;
	var dataGridParamObj = {
		url : host + "list",
		idField : pk,
		onCheck : function(index,row) {
			//是否使用
			var flag = row.couponStatus;
			var bindStatus = row.bindStatus;
			if(flag=='1' || bindStatus=='1'){
				$("#edit").linkbutton('disable');
			}else{
				$("#edit").linkbutton('enable');
			}
		},
		columns : [[
			{width : 100,title : 'id',field : pk,checkbox : true},
			{width : 100,title : '优惠劵编码',field : 'couponCode',sortable : true},
			{width : 100,title : '优惠劵名称',field : 'couponName',sortable : true},
			{width : 100,title : '展示优惠金额',field : 'couponMoney',sortable : true},
			{width : 100,title : '优惠劵类型名称',field : 'map.couponTypeName',sortable : true},
			{width : 100,title : '批次号',field : 'batchNo',sortable : true},
			{width : 100,title : '开始时间',field : 'couponStartTime',sortable : true,formatter : formatterTime},
			{width : 150,title : '结束时间',	field : 'couponEndTime',sortable : true,formatter : formatterTime}, 
			{width : 80,title : '是否使用',field : 'map.couponStatusName',sortable : true},
			{width : 60,title : '是否绑定',field : 'map.bindStatusName',sortable : true},
			{width : 100,title : '创建人',field : 'creater',sortable : true}, 
			{width : 150,title : '创建时间',field : 'createTime',sortable : true,formatter : formatterTime},
			{width : 60,title : '状态',field : 'status',sortable : true,formatter : formatterStop},
			{width : 60,title : '启用状态',	field : 'enableStatus',	sortable : true,formatter : formatterStop}
		]]
	};
	var dialogParamObj = {

	};
	$(function() {
		$(module_datagrid).datagrid($.extend({}, dataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
		$("#module_dialog_edit").dialog($.extend({}, dialogParam, dialogParamObj));
	});
	
	function addCoupon(){
		var title = $("title").html() + " - 新增";
		var href = host + 'addCoupon';
		$("#button-save").unbind("click").click(saveCoupon);
		$(module_dialog).dialog({title : title,href: href,width:550, left:screen.width/4});
		$(module_dialog).dialog("open");
	}
	
	function editCoupon(){
		var id = getGridId();
		if (!id) {
			return false;
		}
		var title = $("title").html() + " - 编辑";
		var href = host + 'form?' + pk + '=' + id;
		$("#button-save").unbind("click").click(update);
		$(module_dialog).dialog({title : title,href: href,width:550, left:screen.width/4});
		$(module_dialog).dialog("open");
	}
	
	function saveCoupon(){
		if ($(module_submit_form).form("validate")) {
			$.messager.progress();
			$(module_submit_form).form({
				url : host + 'saveCoupon'
			});
			$(module_submit_form).form("submit");
		}
	}
	
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container">
		<div id="module_search">
			<form id="module_search_form" class="easyui-form" method="post">
				<div class="module_search_input">
					优惠劵类型：<select class="easyui-combobox" editable="false" name="typeId">
					            <option value="">全部</option>
								<c:forEach items="${couponTypes}" var="one">
									<option value="${one.typeId }">${one.typeName }</option>
								</c:forEach>
							</select>

				</div>
				<div class="module_search_input">
					优惠劵编码：<input type="text" name="couponCode"
						class="easyui-textbox input_width_default" />

				</div>
				<div class="module_search_input">
					是否使用：
			        <select class="easyui-combobox" editable="false" name="couponStatus">
						<option value="">[全部]</option>
						<c:forEach items="${map}" var="one">
							<option value="${one.key }">${one.value }</option>
						</c:forEach>
					</select>
				</div>
				<div class="module_search_input">
					是否绑定：
			        <select class="easyui-combobox" editable="false" name="bindStatus">
						<option value="">[全部]</option>
						<c:forEach items="${map}" var="one">
							<option value="${one.key }">${one.value }</option>
						</c:forEach>
					</select>
				</div>
				
				<!-- 根据启用禁用状态查询 -->
				<div class="module_search_input">
					启用状态：
					<select class="easyui-combobox input_width_short" editable="false" name="enableStatus" data-options="required:false" style="width:150px">
						<option value="">[全部]</option>
						<fns:getOptions category="global.enable_status"/>
					</select>
				</div>
				
				<div class="module_search_button">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
				</div>
			</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add-rf" plain="true" onclick="addCoupon()">批量生成优惠劵</a> 
			<a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit-rf" id="edit" plain="true" onclick="editCoupon()">编辑</a> 
			<a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-start-rf" plain="true" onclick="start(callback)">启用
			</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-stop-rf" plain="true" onclick="stop(callback)">禁用</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-remove-rf" plain="true" onclick="del(callback)">删除</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>
	<!-- 新增框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
	<div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save"
			class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a> <a
			href="javascript:void(0)" id="button-cancel"
			class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
	
	<div style="display:none">
	<!-- 图片编辑框 -->
	<div id="activation_code_dialog" buttons="#activation_code_dialog_button" ></div>
	<div id="activation_code_dialog_button" >
		<a href="javascript:void(0)" id="button-save-pic" class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a>
		<a href="javascript:void(0)" id="button-cancel-pic"	class="easyui-linkbutton" iconCls="icon-no" onclick="pictureCancel()">关闭</a>
	</div>
	</div>
</body>
</html>