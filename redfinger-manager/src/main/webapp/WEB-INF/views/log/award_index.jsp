<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>抽奖日志</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="id";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField :'id',
		onCheck: function(row){
			
		},
		columns:[[
			{width:100,title:'id',field:pk,checkbox:true},
			{width:100,title:'会员ID',field:'map.externalUserId'},
			{width:100,title:'用户号码',field:'map.userPhone'},
			{width:50,title:'奖品级别',field:'map.awardGrade',formatter:function(value){return getDatagridDict('rf_award_batch.award_grade',value);}},
			{width:100,title:'奖品名称',field:'map.awardName'},
			{width:50,title:'奖池',field:'map.awardType',formatter:function(value){return getDatagridDict('rf_award_batch.award_type',value);}},
			{width:100,title:'奖池编码',field:'map.awardCode'},
			{width:100,title:'红豆数量',field:'rbcAmount'},
			{width:100,title:'钱包金额',field:'walletAmount'},
			{width:100,title:'激活码编码',field:'activationCode'},
			{width:100,title:'优惠劵编码',field:'counpCode'},
			{width:150,title:'创建时间',field:'createTime',formatter:formatterTime}
		]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	
</script>
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
		                用户号码:<input type="text" name="userPhone" class="easyui-textbox input_width_default" />
		    </div>
		    <div class="module_search_input">
		                奖品级别:<select class="easyui-combobox input_width_short" editable="false" name="awardGrade" data-options="required:false">
		                <option value="">[全部]</option>
		                <fns:getOptions category="rf_award_batch.award_grade"></fns:getOptions> 
		           </select>
		    </div>
			<div class="module_search_input">
				奖品池:<select
							class="easyui-combobox input_width_short" editable="false"
							name="awardType" data-options="required:false">
							<option value="">[全部]</option>
							<fns:getOptions category="rf_award_batch.award_type"></fns:getOptions>
						</select>
				</div>
		    <div class="module_search_input">
		                奖品名称：<input type="text" name="awardName" class="easyui-textbox input_width_default" />
		    </div>
		    <div class="module_search_input">
		                奖池编码：<input type="text" name="awardCode" class="easyui-textbox input_width_default" />
		    </div>
		    <div class="module_search_input">
		                优惠劵编码：<input type="text" name="counpCode" class="easyui-textbox input_width_default" />
		    </div>
		    <div class="module_search_input">
		                激活码编码：<input type="text" name="activationCode" class="easyui-textbox input_width_default" />
		    </div>
			<div class="module_search_input">
				中奖时间：
				<input type="text" class="easyui-datebox input_width_default" editable="false" id="begin" name="begin" data-options=""/>
				至
				<input type="text" class="easyui-datebox input_width_default" editable="false" id="end" name="end" data-options=""/>
			</div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
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
</body>
</html>



