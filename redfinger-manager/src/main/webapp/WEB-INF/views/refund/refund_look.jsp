<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>退款详情</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		var logPk="refundLogId";  //主键,这个很重要,不能弄错
		var log_datagrid="#log_datagrid";
		var log_dialog="#module_dialog";
		var module_log_datagrid = "#module_log_datagrid";
		var module_look_dialog = "#look_dialog";
		var module_submit_form="form[name=module_submit_form_log]";
		var callback=defaultCallback;
		var url = host+"refundLogList?refundId=${refund.refundId}"; 
		var log_dataGridParamObj={
			url:url,
			idField : logPk,
			onCheck: function(index,row){
				
			},
			//这里field中填写的是实体类中的属性名,不是数据库字段名
			columns:[[
				{width:100,title:'refundLogId',field:logPk,checkbox:true,hidden:true},
				{width:80,title:'处理人',field:'handleAdmin',sortable:false},
				{width:100,title:'操作类型',field:'logType',sortable:false,formatter:function(value){return getDatagridDict('rf_refund_log.log_type',value);}},
				{width:100,title:'内部退款状态',field:'handleStatus',sortable:false,formatter:function(value){return getDatagridDict('rf_refund.handle_status',value);}},
				{width:100,title:'外部退款状态',field:'refundStatus',sortable:false,formatter:function(value){return getDatagridDict('rf_refund.refund_status',value);}},
				{width:100,title:'上一流程状态',field:'map.parentHandleStatus',sortable:false,formatter:function(value){return getDatagridDict('rf_refund.handle_status',value);}},
				{width:100,title:'处理时间',field:'createTime',sortable:false,formatter:formatterTime},
				{width:150,title:'处理意见',field:'handleOpinion',sortable:false},
				{width:100,title:'备注',field:'remark',sortable:false}
				]]
		};
		var dialogParamObj={
			
		};
		
		//双击显示明细
		var lookSpecific =function(index, row){
			if(typeof dblClickCallback!='undefined' && dblClickCallback){
				dblClickCallback.apply(null, [index,row]);
			}else{
				var view=$(module_log_datagrid).parent();
				if($(view).hasClass("datagrid-view")){
					//获取头
					var heads=[];
					$(view).find(".datagrid-header-row").find(".datagrid-cell").each(function(){
						heads.push($(this).text());
					});
					//获取内容
					var bodys=[];
					$(view).find(".datagrid-body").find(".datagrid-row[datagrid-row-index="+index+"]").find(".datagrid-cell").each(function(){
						bodys.push($(this).html());
					});
					var module_submit_container=$('<div id="module_submit_container"></div>'); 
					var form=$('<form id="module_submit_form" class="easyui-form">');
					var easyui_table=$('<table class="easyui-table" id="module_submit_table"></table>');
					easyui_table.appendTo(form);
					form.appendTo(module_submit_container);
					for(var i in heads){
						var tr=$('<tr><td class="td1">'+heads[i]+':</td><td class="td2">'+bodys[i]+'</td></tr>');
						easyui_table.append(tr);
					}
					var title = $("title").html() + " - 明细";
					$("#button-save").unbind("click").click(cancel);
					$(module_look_dialog).dialog({title : title,content:$(module_submit_container).prop("outerHTML"),href:''});
					$(module_look_dialog).dialog("open");
				}
			}
		}

		// 普通表格默认参数
		var lookDataGridParam = {
			fitColumns : true,
			pagination : true,
			striped : true,
			rownumbers : true,
			singleSelect : false,
			idField : 'id',
			pageSize : 20,
			pageList : [ 10, 15, 20, 50, 100, 200,500 ],
			loadFilter : loadFilterForDataGrid,
			onDblClickRow:lookSpecific,
			loadMsg : "处理中，请稍后..."
		};
		$(function(){
			$(module_log_datagrid).datagrid($.extend({},lookDataGridParam,log_dataGridParamObj));
			$(module_look_dialog).dialog($.extend({},dialogParam,dialogParamObj));
		});
	
		var checkSecurePwd = function(userId) {
			var securePwd = $("#securePwd").val();
			if(securePwd == ''){
				$.messager.alert("操作提示", "安全口令不能为空", "error");
				return;
			}
			ajaxPost("checkSecurePwd", {userId:userId, securePwd:securePwd}, function(data){
				$.messager.progress('close');
				if (jQuery.type(data) == 'string') {
					data = eval('(' + data + ')');
				}
				var code = parseInt(data.code);
				switch (code) {
				case 0:
					$.messager.alert('操作失败', data.message, "warning");
					break;
				case 1:
					$.messager.alert('操作成功', data.message, "info");
					break;
				case 200:
					
					break;
				case 500:
					$.messager.alert('操作失败', '操作失败，请联系管理员！', "error");
					break;
				case 501:
					$.messager.alert('操作失败', data.message, "warning");
					break;
				case 304:
					alert("操作失败，" + data.message);
					top.location.href = ctx;
					break;
				}
			});
		}
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post" name="module_submit_form_log">	
	<div id="tt" class="easyui-tabs">   
	    <div title="用户信息">
	        <table id="module_submit_table">
				<tr>
					<td class="td1">用户账号:</td>
					<td class="td2">${user.userMobilePhone }</td>
				</tr>
				<tr>
					<td class="td1">设备编码:</td>
					<td class="td2">${refund.padCode}</td>
				</tr>
				<tr>
					<td class="td1">订单号:</td>
					<td class="td2">${refund.orderId }</td>
				</tr>
				<tr>
					<td class="td1">内部退款状态:</td>
					<td class="td2">${fns:getLabel("rf_refund.handle_status",refund.handleStatus)}</td>
				</tr>
				<tr>
					<td class="td1">外部退款状态:</td>
					<td class="td2">${fns:getLabel("rf_refund.refund_status",refund.refundStatus)}</td>
				</tr>
				<tr>
					<td class="td1">申请退款时间:</td>
					<td class="td2"><fmt:formatDate value="${refund.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /> </td>
				</tr>
				<tr>
					<td class="td1">设备绑定时间:</td>
					<td class="td2"><fmt:formatDate value="${userPad.bindTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
				<tr>
					<td class="td1">联系电话:</td>
					<td class="td2">${userData.contactPhone }</td>
				</tr>
			</table>
	    </div>
	    
	    <div title="银行信息">
	        <table id="module_submit_table">
				<tr>
					<td class="td1">用户开户姓名:</td>
					<td class="td2">${userData.bankUsername }</td>
				</tr>
				<tr>
					<td class="td1">用户收款银行:</td>
					<td class="td2">${userData.bankName }</td>
				</tr>
				<tr>
					<td class="td1">用户银行卡号:</td>
					<td class="td2">${userData.bankCard }</td>
				</tr>
				<tr>
					<td class="td1">订单金额(元):</td>
					<td class="td2">${refund.orderPrice/100 }</td>
				</tr>
				<tr>
					<td class="td1">应收手续费(元):</td>
					<td class="td2">${refund.realFee/100 }</td>
				</tr>
				<tr>
					<td class="td1">应退金额(元):</td>
					<td class="td2">${refund.returnFee/100 }</td>
				</tr>
				<tr>
					<td class="td1">安全口令校验:</td>
					<td class="td2">
					<input class="easyui-textbox" type="text" id="securePwd" value="" data-options="validType:'length[0,50]'" style="width:150px"/>
					<a href="javascript:void(0)" id="button-secure" class="easyui-linkbutton" iconCls="icon-ok-rf" onclick="checkSecurePwd(${user.userId},callback)">校验</a>
					</td>
				</tr>
			</table>
	    </div>
	    
	    <c:if test="${not empty refund.refundImg}">
	    <div title="退款截图">
			<img src="${refund.refundImg}"/>
		</div>
		</c:if>
		
		<div title="退款流程日志">
			<table id="module_log_datagrid" ></table>
		</div>
	</div> 
	
	</form>
    </div>
</body>
</html>
