<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>批次处理</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="executeId";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form"; 
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField :pk,
		singleSelect:true,
		onCheck: function(row){
			
		},
		columns:[[
			{width:100,title:'用户id',field:'userId',hidden:true},
			{width:70,title:'会员ID',field:'map.externalUserId'},
			{width:70,title:'用户账号',field:'userPhone'},
			{width:50,title:'活动类型',field:'activityCode',formatter:function(value){return getDatagridDict('rf_activity.activity',value);}},
			{width:50,title:'活动名称',field:'activityName'},
			{width:50,title:'是否领取',field:'awardStatus',formatter:function(value){if(value=='1' || value=='3'){return '是'}else if(value=='0'){return "否"}}},
			{width:40,title:'红豆数量',field:'rbcAmount'},
			{width:40,title:'抽奖次数',field:'lotteryCount'},
			{width:50,title:'优惠劵',field:'couponCode'},
			{width:50,title:'商品名称',field:'goodsName'},
			{width:40,title:'订单价格',field:'orderPrice'},
			{width:90,title:'订单号',field:'orderId'},
			{width:98,title:'设备编码',field:'padCode'},
			{width:50,title:'获赠时长(h)',field:'padAdd'},
			{width:70,title:'被邀请人',field:'inviteePhone'},
			{width:98,title:'被邀请人设备编码',field:'inviteePadCode'},
			{width:50,title:'被邀请人获赠时长(h)',field:'inviteePadAdd'},
			{width:90,title:'记录/注册时间',field:'recoedTime',formatter:formatterTime},
			{width:90,title:'获奖时间',field:'awardTime',formatter:formatterTime},
			{width:120,title:'备注',field:'remark'},
		]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	
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
			$("#exportName").val('活动日志');
			$("#exportForm").submit();
		}else{
			$.messager.alert('操作失败','无数据！',"warning");
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
			   会员ID：<input type="text" name="externalUserId" class="easyui-numberbox input_width_short" />
			</div>
			<div class="module_search_input">
			活动类型：
				<select class="easyui-combobox input_width_short" name="activityCode" data-options="editable:false" style="width:100px">
					<option value="">[全部]</option>
				    <fns:getOptions category="rf_activity.activity"></fns:getOptions>
			 	</select>
		    </div>
		    <div class="module_search_input">
			   活动名称：<input type="text" name="activityName" class="easyui-textbox input_width_default" />
			</div>
		    <div class="module_search_input">		
				是否领取：<select class="easyui-combobox input_width_short" editable="false" name="awardStatus" data-options="required:false">
					<option value="">[全部]</option>
				    <option value="1">是</option>
				    <option value="0">否</option>
				</select> 
			</div>
			<div class="module_search_input">
			   用户账号：<input type="text" name="userPhone" class="easyui-textbox input_width_default" />
			</div>
			<div class="module_search_input">
			    被邀请人：<input type="text" name="inviteePhone" class="easyui-textbox input_width_default" /> 
			</div>
		    <div class="module_search_input">		
				购买套餐:<select class="easyui-combobox input_width_default" editable="false" name="goodsId">
					<option value="">[全部]</option>
					<c:forEach var="one" items="${goods}">
						<option value="${one.goodsId }">${one.goodsName }</option>
					</c:forEach>
      	        </select> 
			</div>
			<div class="module_search_input">
			    订单号：<input type="text" name="orderId" class="easyui-textbox input_width_default" /> 
			</div>	
			<div class="module_search_input">
				活动记录时间/注册时间:
				<input type="text" class="easyui-datebox input_width_default" editable="false"  name="signTimeGt" />
				至
				<input type="text" class="easyui-datebox input_width_default" editable="false"  name="signTimeLt"/>
		     </div>
		     <div class="module_search_input">		
				是否购买:<select class="easyui-combobox input_width_short" editable="false" name="isBuy" data-options="required:false">
					<option value="">[全部]</option>
				    <option value="1">是</option>
				    <option value="0">否</option>
				</select> 
			</div>
		    <div class="module_search_input">
				获奖时间:
				<input type="text" class="easyui-datebox input_width_default" editable="false"  name="buyTimeGt" />
				至
				<input type="text" class="easyui-datebox input_width_default" editable="false"  name="buyTimeLt"/>
		     </div>
		     <div class="module_search_input">		
				是否获得赠送时长:
				<select class="easyui-combobox input_width_short" editable="false" name="padAdd" data-options="required:false">
					<option value="">[全部]</option>
				    <option value="1">是</option>
				    <option value="0">否</option>
				</select> 
			</div>
			<div class="module_search_input">
			    设备编码：<input type="text" name="padCode" class="easyui-textbox input_width_default" /> 
			</div>
			<div class="module_search_input">
			    获赠优惠劵编码：<input type="text" name="couponCode" class="easyui-textbox input_width_default" /> 
			</div>
			<div class="module_search_input">
			    被邀请人设备编码：<input type="text" name="inviteePadCode" class="easyui-textbox input_width_default" /> 
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
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel-rf" plain="true" onclick="statExport()">导出</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
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
</body>
</html>



