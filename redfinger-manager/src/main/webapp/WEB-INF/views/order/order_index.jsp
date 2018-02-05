<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>订单管理</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="orderId";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField :'orderId',
		onCheck: function(row){
			
		},
		columns:[[
			{width:100,title:'id',field:"orderId",checkbox:true},
			{width:200,title:'会员ID',field:'map.externalUserId'},
			{width:200,title:'来源',field:'userSource',sortable:true},
			{width:100,title:'订单号',field:'checkboxValue',formatter:function(value, row) {return row.orderId;}},
			{width:200,title:'会员号码',field:'map.userMobilePhone',},
			{width:200,title:'设备编号',field:'padCode',sortable:true},
			{width:100,title :'设备等级',field:'padGrade',sortable:true,formatter:formatterPadGradeStatus},
			{width:200,title:'商品名称',field:'map.goodsName',},
			{width:100,title:'获赠红豆',field:'rbcAmount',sortable:true},
			{width:200,title:'创建时间',field:'createTime',sortable:true,formatter:formatterTime},
			{width:200,title:'下单时间',field:'orderCreateTime',sortable:true,formatter:formatterTime},
			{width:200,title:'交易完成时间',field:'finishTime',sortable:true,formatter:formatterTime},
			{width:200,title:'订单金额',field:'orderPrice',sortable:true,formatter:function(value){return value/100;}},
			{width:200,title:'应付金额',field:'realFee',sortable:true,formatter:function(value){if(value){return value/100;}}},
			{width:200,title:'状态',field:'orderStatus',sortable:true,formatter:function(value){return getDatagridDict('rf_order.order_status',value);}},
			{width:200,title:'订单业务类型',field:'orderBizType',sortable:true,formatter:function(value){return getDatagridDict('rf_order.order_biz_type',value);}},
			{width:200,title:'订单类型',field:'orderType',sortable:true,formatter:function(value){return getDatagridDict('rf_order.order_type',value);}},
			{width:200,title:'支付方式',field:'payModeCode',sortable:true,formatter:function(value){return getDatagridDict('rf_order.pay_mode_code',value);}},
			{width:200,title:'订单归属',field:'owner',sortable:true,formatter:function(value){return getDatagridDict('rf_order.order_owner',value);}},
			{width:200,title:'备注',field:'remark',sortable:true},
			{width:100,title:'状态',field:'enableStatus',sortable:true,formatter:formatterStop}
		]]
	};
	var dialogParamObj={
		
	};
	$(function() {
		$(module_datagrid).datagrid($.extend({}, dataGridParam, dataGridParamObj));
		$(module_dialog).dialog($.extend({}, dialogParam, dialogParamObj));
		$("#look").dialog($.extend({}, dialogParam, dialogParamObj));
		$(document).bind("keydown","+",function(){
			$.messager.progress();
			getFloatData();
			$('#floatData').window('open');
			return false;
		}).bind("keydown","pagedown",function(){
			$.messager.progress();
			getFloatData();
			$('#floatData').window('open');
			return false;
		}).bind("keydown","-",function(){
			$('#floatData').window('close');
			return false;
		}).bind("keydown","pageup",function(){
			$('#floatData').window('close');
			return false;
		});
		$.extend($.fn.validatebox.defaults.rules,{
			number:{// 验证数字
	          validator : function(value) {
	              return /^[0-9]{1,9}$/gi.test(value);
	          },
	          message : '只允许1-9位的正整数'
	        }
		});
	});

	function getFloatData(){
		var url = host + 'floatData';
		$.getJSON(url, function(json){
			for(var i in json){
				$("#"+i).html(json[i]);
			}
			$.messager.progress('close');
		});
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
 			$("#exportName").val('订单');
 			$("#exportForm").submit();
 		}else{
 			$.messager.alert('操作失败','无数据！',"warning");
 		}
 	}
 	
 	//设备等级
	function formatterPadGradeStatus(value, row, index) {
		return getDatagridDict('rf_user_pad.pad_grade',value)
	}
 	
	//打开数据汇总
	function openData() {
		getFloatData();
		$('#floatData').window('open');
		return false;
	}
	//关闭数据汇总
	function closeData() {
		$('#floatData').window('close');
		return false;
	}
	//grid搜索
	var gridSearchValidate = function() {
		var arr = $(module_search_form).serializeArray();
		var obj = {};
		$.each(arr, function(i, field) {
			obj[field.name] = field.value;
		});
		if(!($('#module_search_form').form("validate"))){
			return false;
			}
		try{
			$(module_datagrid).treegrid("reload",obj);
		}catch(e){
			$(module_datagrid).datagrid("reload",obj);
		}
	}
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
		 <div class="module_search_input">
				会员ID：<input type="text" name="externalUserId" data-options="validType:'number'"" class="easyui-numberbox input_width_default"/>
		 </div>
		 
		  <div class="module_search_input">
				手机号：<input type="text" class="easyui-textbox input_width_default" name="userPhone" data-options=""/>
		  </div>
		  <div class="module_search_input">
				业务类型：
		        <select class="easyui-combobox input_width_default" editable="false" name="orderBizType">
					<option value="">[全部]</option>
					<fns:getOptions category="rf_order.order_biz_type"/>
				</select>
			</div>
		    <div class="module_search_input">
				状态：
		        <select class="easyui-combobox input_width_default" editable="false" name="orderStatus">
					<option value="">[全部]</option>
					<fns:getOptions category="rf_order.order_status"/>
				</select>
			</div>
			<div class="module_search_input">
				商品名称：
				<select class="easyui-combobox input_width_short"  editable="false" id="goodsId" name="goodsId" style="width:120px">
					<option value="">[全部]</option>
			    	<c:forEach items="${goods }" var="one">
			    		<option value="${one.goodsId }">${one.goodsName }</option>
			    	</c:forEach>
		 		</select>
			</div>
			
			<!-- 根据支付方式查询 -->
			<div class="module_search_input">
				支付方式：
		        <select class="easyui-combobox input_width_default" editable="false" name="payModeCode">
					<option value="">[全部]</option>
					<fns:getOptions category="rf_order.pay_mode_code"/>
				</select>
			</div>
			<div class="module_search_input">
				订单号：<input type="text" class="easyui-textbox input_width_default" name="orderId" data-options=""/>
			</div>
			<!-- 根据订单来源查询 -->
			<div class="module_search_input">
				订单来源：<input type="text" class="easyui-textbox input_width_default" name="userSource" data-options=""/>
			</div>
			<div class="module_search_input">
				订单归属：
				<select class="easyui-combobox input_width_default" editable="false" name="owner">
					<option value="">[全部]</option>
					<fns:getOptions category="rf_order.order_owner"/>
				</select>
			</div>
			<div class="module_search_input">
				下单时间：
				<input type="text" class="easyui-datebox input_width_default" editable="false" id="begin" name="beginTimeStr" data-options=""/>
				至
				<input type="text" class="easyui-datebox input_width_default" editable="false" id="end" name="endTimeStr" data-options=""/>
			</div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearchValidate()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
				<a href="#" title="搜索：enter<br/>清空：delete<br/>打开/刷新汇总：+/pagedown<br/>关闭汇总：-/pageup" class="easyui-tooltip">快捷键</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
		    <a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-edit-rf" plain="true" onclick="edit()">编辑</a>
	     	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel-rf" plain="true" onclick="statExport()">导出</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
	        <!-- a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload-rf',plain:true" onclick="openData()">打开数据汇总</a>
          <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload-rf',plain:true" onclick="closeData()">关闭数据汇总</a> -->
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>
	
	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
    <div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a>
		<a href="javascript:void(0)" id="button-cancel" class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
	<div class="hide">
	<form action="" id="exportForm" method="post" target="_blank">
		<input type="hidden" name="exportDatas" id="exportDatas" value=""/>
		<input type="hidden" name="exportHead" id="exportHead" value=""/>
		<input type="hidden" name="exportField" id="exportField" value=""/>
		<input type="hidden" name="exportName" id="exportName" value=""/>
	</form>
	</div>
	<div id="floatData" class="easyui-window" title="订单数据汇总" data-options="width:'350px',iconCls:'icon-save',modal:false,minimizable:false,maximizable:false,resizable:false,closed:true">
        <div>当日成功订单：&nbsp;&nbsp;<span class='red' id="floatExpire">笔-</span>(总数)&nbsp;,&nbsp;<span class='red' id="floatExpire2">-</span>(当日总金额)&nbsp;&nbsp;</div>
        <br/>   
        <div>充值成功订单：&nbsp;&nbsp;<span class='red' id="floatTotal">笔-</span>(总数)&nbsp;,&nbsp;<span class='red' id="floatTotal2">-</span>(总金额)&nbsp;&nbsp;</div>  
        <br/>
        <div>购买成功订单：&nbsp;&nbsp;<span class='red' id="floatFault">笔-</span>(总数)&nbsp;,&nbsp;<span class='red' id="floatFault2">-</span>(总金额)&nbsp;&nbsp;</div>  
        <br/>
        <div>当前成功订单：&nbsp;&nbsp;<span class='red' id="floatEnable">笔-</span>(总数)&nbsp;,&nbsp;<span class='red' id="floatEnable2">-</span>(总金额)&nbsp;&nbsp;</div>
	</div>
</body>
</html>



