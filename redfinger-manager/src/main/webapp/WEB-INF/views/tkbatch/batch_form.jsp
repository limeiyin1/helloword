<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>批处理任务</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script>
	<script type="text/javascript">
		$('.easyui-form').form({
		   url:host+'save',   
		    success:defaultCallback	
		});
		
		function uploadFile(fileUpdateName,batchExcuteDataFile){
			$.messager.progress({
                title:'请稍后',
                msg:'正在上传...'
            });
		    if(!$("#"+fileUpdateName).val()){
				$.dialog.alert("请选择上传文件！");
				return;
			}	
			var hostUrl = host+'uploadFileRequest';
			
			$("#module_submit_form").attr("enctype","multipart/form-data");
			$("#module_submit_form").ajaxSubmit({
				type: "post",
				url: hostUrl,
				success: function (data) {
				    var obj = jQuery.parseJSON(jQuery(data).text());
					$('#'+batchExcuteDataFile).textbox({value:''});
					$('#module_submit_form').attr("enctype","application/x-www-form-urlencoded");
					if(obj){
						if(obj.flag == 'true'){
							 $('#'+batchExcuteDataFile).textbox("setValue",obj.fileBookPath);
							 $.messager.progress('close');
						}else{
						    $.messager.progress('close');
							if(obj.msg){
								alert(obj.msg);
							}else{
								alert('文件上传失败!');
						    }
						}
					}
				},
				error : function(result){
					$('#module_submit_form').attr("enctype","application/x-www-form-urlencoded");
					alert('后台程序出错!');
				}
			});
		return false; 
	}
	
	$.extend($.fn.validatebox.defaults.rules, {  
		selectValueRequired: {
			validator: function(value,param){ 
				return $(param[0]).find("option:contains('"+value+"')").val() != '';  
			},  
			message: '该选项为必选项'  
		}  
	});
	
	//下载原文件
	function downloadFile(filePath,fileName){
		var downloadUrl = host+'downloadBatchFile?filePath='+filePath+"&fileName="+fileName;
		window.open(downloadUrl);
	};
	
	var controlList = eval('${controlListJson}');//更换设备时的控制节点，选择机房时需控制表单联动
	//控制表单联动
	function operateTypeSelect(){
		var operateType = $("#operateType").combobox('getValue');
		var giveType = $("#giveType").combobox('getValue');//赠送红豆方式
		var sendPasNoticeType = $("#sendPasNoticeTypeTrSelect").combobox('getValue');
		var isSendMessage = $("#isSendMessage").combobox('getValue');//更换设备是否发送消息
		var isSendWechart = $("#isSendWechart").combobox('getValue');//更换设备是否发送微信
		var padClassify = $("#choicePadClassify").combobox('getValue');//绑定设备的类型
		var enablePadType = $("#enablePadType").combobox('getValue');//启用或禁用方式
		var userRbcType = $("#userRbcType").combobox('getValue');//按用户赠送红豆的方式
		var sendPadMsgType = $("#sendPadMsgTypeSelect").combobox('getValue');//发送设备通知的方式

		
		$("#fileUploadTr").addClass('hide');
		
		$("#padMaintExcelUrl").addClass('hide');
		$("#renewPadExcelUrl").addClass('hide');
		$("#userNoticeExcelUrl").addClass('hide');
		$("#padNoticeExcelUrl").addClass('hide');
		$("#userRbcByAccountExcelUrl").addClass('hide');
		$("#userRbcByUserIdExcelUrl").addClass('hide');
		$("#padRbcExcelUrl").addClass('hide');
		$("#padTimeExcelUrl").addClass('hide');
		$("#padNoticeExcelUrl").addClass('hide');
		$("#vmPadGrantExcelUrl").addClass('hide');
		$("#vmPadRestartExcelUrl").addClass('hide');
		$("#deviceRebootExcelUrl").addClass('hide');
		$("#bindPadExcelUrl").addClass('hide');
		$("#enablePadExcelUrl").addClass('hide');
		$("#padMsgExcelUrl").addClass('hide');

		
		$("#padNoticeTitleTr").addClass('hide');//发送用户、设备公告
		$("#padNoticeContentTr").addClass('hide');
		$("#userNoticeTitleTr").addClass('hide');
		$("#userNoticeContentTr").addClass('hide');
		$("#popStatusTr").addClass('hide');
		$("#popExpiredTimeTr").addClass('hide');
		$("#sendPasNoticeTypeTr").addClass('hide');
		$("#padNoticeIdcIdTr").addClass('hide');
		$("#padNoticePadCodeTr").addClass('hide');
		$("#oneNoticeStatusTr").addClass('hide');
		$("#sendPadNoticeTemplateTr").addClass('hide');
		$("#sendPadnoticeTipTr").addClass('hide');
		
		
		
		$("#isSendMessageChoice").addClass('hide');//更换设备
		$("#sendMessageTemplateTr").addClass('hide');
		$("#sendMessageTr").addClass('hide');
		$("#sendMessageTipTr").addClass('hide');
		$("#isSendWechartChoice").addClass('hide');
		$("#sendWeixinTemplateTr").addClass('hide');
		$("#sendWchartTr").addClass('hide');
		$("#sendWchartTipTr").addClass('hide');
		$("#controlTr").addClass('hide');
		$("#padCodeInTr").addClass('hide');
		$("#deviceIpTr").addClass('hide');
		$("#reNewPadIdcIdTr").addClass('hide');
		
		
		
		$("#timeTypeTr").addClass('hide');//增加设备时间
		$("#controltimeTr").addClass('hide');
		
		
		
		$("#rbcGetTr").addClass('hide');//按用户赠送红豆
		$("#userRbcTypeTr").addClass('hide');
		
		
		
		//giveRbcType idcId commonRbc vipRbc svipRbc svipRbc 
		$("#giveRbcTypeTr").addClass('hide');//按设备赠送红豆
		$("#idcIdTr").addClass('hide');
		$("#commonRbcTr").addClass('hide');
		$("#vipRbcTr").addClass('hide');
		$("#svipRbcTr").addClass('hide');
		$("#gvipRbcTr").addClass('hide');
		$("#padRbcPadCodeTr").addClass('hide');
		
		
		
		$("#padClassifyTr").addClass('hide');//批量绑定设备
		$("#gamePadTimeTr").addClass('hide');//批量绑定设备
		$("#cloudPadTimeTr").addClass('hide');//批量绑定设备
		$("#goodsIdTr").addClass('hide');//批量绑定设备
		$("#gvipGoodsIdTr").addClass('hide');//批量绑定设备
		
		
		
		$("#enablePadDeviceIpTr").addClass('hide');//启用或禁用设备
		$("#enablePadCodeInTr").addClass('hide');//启用或禁用设备
		$("#enablePadIdcIdTr").addClass('hide');//启用或禁用设备
		$("#enablePadTypeTr").addClass('hide');//启用或禁用设备
		
		
		
		$("#sendPasMsgTypeTr").addClass('hide');//发送设备消息
		$("#padMsgIdcIdTr").addClass('hide');//发送设备消息
		$("#padMsgPadCodeTr").addClass('hide');//发送设备消息
		$("#padMsgTitleTr").addClass('hide');//发送设备消息
		$("#padMsgContentTr").addClass('hide');//发送设备消息
		$("#oneMsgStatusTr").addClass('hide');//发送设备消息
		$("#sendPadMsgTemplateTr").addClass('hide');//发送设备消息
		$("#padMsgContentTipTr").addClass('hide');//发送设备消息
		$("#sendPadWeixinTemplateTr").addClass('hide');//发送设备消息
		$("#sendPadWchartTr").addClass('hide');//发送设备消息
		$("#sendPadWchartTipTr").addClass('hide');//发送设备消息
		
		
		//发送设备消息
		if(operateType == '25'){
			$("#padMsgTitleTr").removeClass("hide");
			$("#padMsgContentTr").removeClass("hide");
			$("#oneMsgStatusTr").removeClass("hide");
			$("#sendPasMsgTypeTr").removeClass('hide');
			$("#sendPadMsgTemplateTr").removeClass('hide');
			$("#padMsgContentTipTr").removeClass('hide');
			$("#sendPadWeixinTemplateTr").removeClass('hide');
			$("#sendPadWchartTr").removeClass('hide');
			$("#sendPadWchartTipTr").removeClass('hide');
			  
			if(sendPadMsgType == '2'){
				//excel发送
				$("#padMsgExcelUrl").removeClass("hide");
				$("#fileUploadTr").removeClass('hide');
			}
			if(sendPadMsgType == '1'){
				//机房发送
				$("#padMsgIdcIdTr").removeClass("hide");
			}
			if(sendPadMsgType == '3'){
				//设备编码段发送
				$("#padMsgPadCodeTr").removeClass("hide");
			}
		}
		
		
		
		//设备维护
		if(operateType == '1' || operateType == '2'){
			$("#padMaintExcelUrl").removeClass('hide');
			$("#fileUploadTr").removeClass('hide');
		}
		
		
		//设备授权开放与取消
		if(operateType == '11' || operateType == '12'){
			$("#vmPadGrantExcelUrl").removeClass('hide');
			$("#fileUploadTr").removeClass('hide');
		}
		
		
		//虚拟设备重启
		if(operateType == '13'){
			$("#vmPadRestartExcelUrl").removeClass('hide');
			$("#fileUploadTr").removeClass('hide');
		}
		
		
		//物理设备重启
		if(operateType == '19'){
			$("#deviceRebootExcelUrl").removeClass('hide');
			$("#fileUploadTr").removeClass('hide');
		}
		
		
		//更换设备
		if(operateType == '3'){
			$("#isSendMessageChoice").removeClass("hide");
			$("#isSendWechartChoice").removeClass("hide");
			$("#controlTr").removeClass("hide");
			$("#renewPadExcelUrl").removeClass('hide');
			$("#padCodeInTr").removeClass('hide');
			$("#deviceIpTr").removeClass('hide');
			$("#reNewPadIdcIdTr").removeClass('hide');
			$("#fileUploadTr").removeClass('hide');
			 
			if(isSendMessage == '1'){
				$("#sendMessageTr").removeClass('hide');
				$("#sendMessageTipTr").removeClass('hide');
				$("#sendMessageTemplateTr").removeClass("hide");
			}
			if(isSendWechart == '1'){
				$("#sendWchartTr").removeClass('hide');
				$("#sendWchartTipTr").removeClass('hide');
				$("#sendWeixinTemplateTr").removeClass("hide");
			}
		}
		
		
		//用户公告
		if(operateType == '4'){
			$("#noticeTitleTr").removeClass("hide");
			$("#noticeContentTr").removeClass("hide");
			$("#popStatusTr").removeClass("hide");
			$("#popExpiredTimeTr").removeClass("hide");
			$("#userNoticeExcelUrl").removeClass('hide');
			$("#fileUploadTr").removeClass('hide');
			$("#userNoticeTitleTr").removeClass('hide');
			$("#userNoticeContentTr").removeClass('hide');
		}
		
		
		//设备公告 
		if(operateType == '5'){
			$("#noticeTitleTr").removeClass("hide");
			$("#noticeContentTr").removeClass("hide");
			$("#popStatusTr").removeClass("hide");
			$("#sendPasNoticeTypeTr").removeClass('hide');
			$("#oneNoticeStatusTr").removeClass('hide');
			$("#sendPadNoticeTemplateTr").removeClass('hide');
			$("#sendPadnoticeTipTr").removeClass('hide');
			$("#padNoticeTitleTr").removeClass('hide');//发送用户、设备公告
			$("#padNoticeContentTr").removeClass('hide');
			
			if(sendPasNoticeType == '2'){
				//excel发送
				$("#padNoticeExcelUrl").removeClass("hide");
				$("#fileUploadTr").removeClass('hide');
			}
			if(sendPasNoticeType == '1'){
				//机房发送
				$("#padNoticeIdcIdTr").removeClass("hide");
			}
			if(sendPasNoticeType == '3'){
				//设备编码段发送
				$("#padNoticePadCodeTr").removeClass("hide");
			}
		}
		
		
		//增加设备时间
		if(operateType == '8'){
			$("#timeTypeTr").removeClass("hide");
			$("#controltimeTr").removeClass("hide");
			$("#padTimeExcelUrl").removeClass("hide");
			$("#fileUploadTr").removeClass('hide');
		}
		
		
		//按用户赠送红豆
		if(operateType == '6'){
			$("#rbcGetTr").removeClass("hide");
			$("#userRbcTypeTr").removeClass("hide");
			$("#userRbcExcelUrl").removeClass("hide");
			$("#fileUploadTr").removeClass('hide');
			if(userRbcType == '1'){
				$("#userRbcByAccountExcelUrl").removeClass('hide');
			}
			if(userRbcType == '2'){
				$("#userRbcByUserIdExcelUrl").removeClass('hide');
			}
		}
		//按设备赠送红豆
		if(operateType == '7'){
			if(giveType == '1'){
				$("#idcIdTr").removeClass('hide');
				$("#fileUploadTr").addClass('hide');
				$("#padRbcExcelUrl").addClass('hide');
			}
			if(giveType == '2'){
				$("#fileUploadTr").removeClass('hide');
				$("#fileTempletTr").removeClass('hide');
				$("#padRbcExcelUrl").removeClass('hide');
			}
			if(giveType == '3'){
				$("#padRbcPadCodeTr").removeClass('hide');
				$("#fileUploadTr").addClass('hide');
				$("#padRbcExcelUrl").addClass('hide');
			}
			
			$("#giveRbcTypeTr").removeClass("hide");
			$("#commonRbcTr").removeClass('hide');
			$("#vipRbcTr").removeClass('hide');
			$("#svipRbcTr").removeClass('hide');
			$("#gvipRbcTr").removeClass('hide');
			
		}
		
		
		//批量绑定设备
		if(operateType == '22'){
			$("#bindPadExcelUrl").removeClass("hide");
			$("#padClassifyTr").removeClass('hide');
			$("#fileUploadTr").removeClass('hide');
			if(padClassify == '1'){
				$("#goodsIdTr").removeClass('hide');//批量绑定设备
			}else if(padClassify == '2'){
				$("#gamePadTimeTr").removeClass('hide');//批量绑定设备
			}else if(padClassify == '3'){
				$("#gvipGoodsIdTr").removeClass('hide');//批量绑定设备
			}else if(padClassify == '5'){
				$("#cloudPadTimeTr").removeClass('hide');//批量绑定设备
			}else{
				$("#goodsIdTr").removeClass('hide');//批量绑定设备
			}
		}
		 
		
		//启用或禁用
		if(operateType == '23' || operateType == '24'){
			$("#enablePadTypeTr").removeClass('hide');
			if(enablePadType == '1'){
				$("#enablePadExcelUrl").removeClass("hide");
				$("#fileUploadTr").removeClass('hide');
			}
			if(enablePadType == '2'){
				$("#enablePadIdcIdTr").removeClass("hide");
			}
			if(enablePadType == '3'){
				$("#enablePadCodeInTr").removeClass("hide");
			}
			if(enablePadType == '4'){
				$("#enablePadDeviceIpTr").removeClass("hide");
			}
		}
	}
	
	
	//按用户增送红豆的方式(账号、ID)
	function userRbcTypeSelect(){
		var userRbcType = $("#userRbcType").combobox('getValue');//按用户赠送红豆的方式
		$("#userRbcByAccountExcelUrl").addClass('hide');
		$("#userRbcByUserIdExcelUrl").addClass('hide');
		if(userRbcType == '1'){
			$("#userRbcByAccountExcelUrl").removeClass('hide');
		}
		if(userRbcType == '2'){
			$("#userRbcByUserIdExcelUrl").removeClass('hide');
		}
	}
	
	
	//按设备赠送红豆选项
	function giveRbcTypeSelect(){
		var giveType = $("#giveType").combobox('getValue');
		
		$("#fileTempletTr").addClass('hide');
		$("#fileUploadTr").addClass('hide');
		$("#idcIdTr").addClass('hide');
		$("#padRbcPadCodeTr").addClass('hide');
		$("#padRbcExcelUrl").addClass('hide');
		
		if(giveType == '1'){
			$("#idcIdTr").removeClass('hide');
		}
		if(giveType == '2'){
			$("#fileUploadTr").removeClass('hide');
			$("#fileTempletTr").removeClass('hide');
			$("#padRbcExcelUrl").removeClass('hide');
		}
		if(giveType == '3'){
			$("#padRbcPadCodeTr").removeClass('hide');
		}
	}
	 
	
	//发送设备公告方式选择
	function sendPasNoticeTypeSelect(){
		var sendPasNoticeType = $("#sendPasNoticeTypeTrSelect").combobox('getValue');
		
		$("#fileTempletTr").addClass('hide');
		$("#fileUploadTr").addClass('hide');
		$("#padNoticeIdcIdTr").addClass('hide');
		$("#padNoticePadCodeTr").addClass('hide');
		$("#padNoticeExcelUrl").addClass('hide');
		
		if(sendPasNoticeType == '1'){
			$("#padNoticeIdcIdTr").removeClass('hide');
		}
		if(sendPasNoticeType == '2'){
			$("#fileUploadTr").removeClass('hide');
			$("#fileTempletTr").removeClass('hide');
			$("#padNoticeExcelUrl").removeClass('hide');
		}
		if(sendPasNoticeType == '3'){
			$("#padNoticePadCodeTr").removeClass('hide');
		}
	}
	
	
	//显示输入框字数
	function gbcount(message, total, used, remain) {
		var max;
		max = total.value;
		if (message.value.length > max) {
			message.value = message.value.substring(0, max);
			used.value = max;
			remain.value = 0;
			alert("内容不能超过 680 个字!");
		} else {
			used.value = message.value.length;
			remain.value = max - used.value;
		}
	}
	
	
	//是否发送信息
	function choiceIsSendMessage(){
		var isSendMessage = $("#isSendMessage").combobox('getValue');
		$("#sendMessageTemplateTr").addClass("hide");
		$("#sendMessageTr").addClass("hide");
		$("#sendMessageTipTr").addClass("hide");
		if(isSendMessage=='1'){
			$("#sendMessageTemplateTr").removeClass("hide");
			$("#sendMessageTr").removeClass("hide");
			$("#sendMessageTipTr").removeClass("hide");
		}
	}
	
	
	//选择消息模板
	function choicesendMessageTemplate(){
		$('#showMessageTemplate').text("")
		var messageTemplate = $("#sendMessageTemplate").combobox('getValue');
		$('#showMessageTemplate').text(messageTemplate)
	}
	
	
	//选择微信模板
	function choicesendWeixinTemplate(){
		$('#sendWechartTemplate').text("")
		var weixinTemplate = $("#sendWeixinTemplate").combobox('getValue');
		$('#sendWechartTemplate').text(weixinTemplate);
	}
	
	
	//是否发送微信
	function choiceIsSendWechart(){
		var isSendWechart = $("#isSendWechart").combobox('getValue');
		$("#sendWeixinTemplateTr").addClass("hide");
		$("#sendWchartTr").addClass("hide");
		$("#sendWchartTipTr").addClass("hide");
		if(isSendWechart=='1'){
			$("#sendWeixinTemplateTr").removeClass("hide");
			$("#sendWchartTr").removeClass("hide");
			$("#sendWchartTipTr").removeClass("hide");
		}
	}
	
	
	//选择设备公告模板
	function choicesendPadNoticeTemplate(){
		$('#padNoticeContentTex').val("")
		var noticeContent = $("#PadNoticeTemplateTrSel").combobox('getValue');
		$('#padNoticeContentTex').val(noticeContent);
		var len = noticeContent.length;
		$("#padNoticeUsed").val(len);
		$("#padNoticeRemain").val(680-len);
	}
	
	
	//发送设备消息
	//发送设备消息方式选择
	function sendPadMsgTypeSelect(){
		var sendPasNoticeType = $("#sendPadMsgTypeSelect").combobox('getValue');
		 
		$("#fileTempletTr").addClass('hide');
		$("#fileUploadTr").addClass('hide');
		$("#padMsgIdcIdTr").addClass('hide');
		$("#padMsgPadCodeTr").addClass('hide');
		$("#padMsgExcelUrl").addClass('hide');
		
		if(sendPasNoticeType == '1'){
			$("#padMsgIdcIdTr").removeClass('hide');
		}
		if(sendPasNoticeType == '2'){
			$("#fileUploadTr").removeClass('hide');
			$("#fileTempletTr").removeClass('hide');
			$("#padMsgExcelUrl").removeClass('hide');
		}
		if(sendPasNoticeType == '3'){
			$("#padMsgPadCodeTr").removeClass('hide');
		}
	}
	
	
	//选择发送设备消息模板
	function choiceSendPadMsgTemplate(){
		$('#padMsgContentTex').val("")
		var padMsgContent = $("#PadMsgTemplateTrSel").combobox('getValue');
		$('#padMsgContentTex').val(padMsgContent);
		var len = padMsgContent.length;
		$("#padMsgUsed").val(len);
		$("#padMsgRemain").val(680-len);
	}
	
	
	//选择发送设备微信模板
	function choicesendPadWeixinTemplate(){
		$('#padWeixinContentTex').val("")
		var weixinContent = $("#sendPadWeixinTemplate").combobox('getValue');
		$('#padWeixinContentTex').val(weixinContent);
		var len = weixinContent.length;
		$("#padWeixinUsed").val(len);
		$("#padWeixinRemain").val(680-len);
	}
	 
	
	//更换设备选择机房 联动 设备节点选项
	var padControlId = '${padControlId}';
	function reNewPadIdcIdSelect(){
		var optionHtml = '<select id="padControlIdSelect" editable="false" class="easyui-combobox input_width_short" name="padControlId">'
		optionHtml += '<option value="">[全部]</option>';
		var idcId = $("#reNewPadIdcIdSelectTr").combobox('getValue'); 
		if(idcId != null && idcId != ""){
			for (var i = 0; i < controlList.length; i++) {
				if(controlList[i].idcId == idcId){
					optionHtml += '<option value='+controlList[i].controlId+'>'+controlList[i].controlName+'</option>'
				}
			}
		}else{
			for (var i = 0; i < controlList.length; i++) {
				optionHtml += '<option value='+controlList[i].controlId+'>'+controlList[i].controlName+'</option>'
			}
		}
		optionHtml += '</select>'
		$("#padControlIdSelect").remove();
		$("#padControlIdSelectTr").html(optionHtml);
	} 
	
	
	//选择绑定设备的类型
	function choicePadClassify(){
		var padClassify = $("#choicePadClassify").combobox('getValue');
		$("#goodsIdTr").addClass("hide");
		$("#gamePadTimeTr").addClass("hide");
		$("#gvipGoodsIdTr").addClass("hide");
		$("#cloudPadTimeTr").addClass("hide");
		if(padClassify=='1'){
			$("#goodsIdTr").removeClass("hide");
		}else if (padClassify=='2'){
			$("#gamePadTimeTr").removeClass("hide");
		}else if(padClassify == '3'){
			$("#gvipGoodsIdTr").removeClass('hide');//批量绑定设备
		}else if(padClassify == '5'){
			$("#cloudPadTimeTr").removeClass('hide');//批量绑定设备
		}else{
			$("#goodsIdTr").removeClass("hide");
		}
	}
	
	
	//启用或禁用设备方式
	function enablePadTypeSelect(){
		var enablePadType = $("#enablePadType").combobox('getValue');
		
		$("#fileTempletTr").addClass('hide');//启用或禁用设备
		$("#fileUploadTr").addClass('hide');
		$("#enablePadExcelUrl").addClass('hide');
		$("#enablePadDeviceIpTr").addClass('hide');
		$("#enablePadCodeInTr").addClass('hide');
		$("#enablePadIdcIdTr").addClass('hide');
		$("#enablePadControlTr").addClass('hide');
		$("#enablePadStatusTr").addClass('hide');
		$("#enablePadBindStatusTr").addClass('hide');
		$("#enablePadFaultStatusTr").addClass('hide');
		       
		if(enablePadType == '1'){
			$("#fileUploadTr").removeClass('hide');
			$("#fileTempletTr").removeClass('hide');
			$("#enablePadExcelUrl").removeClass('hide');
		}
		if(enablePadType == '2'){
			$("#enablePadIdcIdTr").removeClass('hide');
			$("#enablePadCodeInTr").removeClass('hide');
			$("#enablePadIdcIdTr").removeClass('hide');
			$("#enablePadDeviceIpTr").removeClass('hide');
			$("#enablePadControlTr").removeClass('hide');
			$("#enablePadStatusTr").removeClass('hide');
			$("#enablePadBindStatusTr").removeClass('hide');
			$("#enablePadFaultStatusTr").removeClass('hide');
		}
	}
	
	
	//启用或禁用设备 选择机房 联动 设备节点选项
	var enablePadControlId = '${enablePadControlId}';
	function enablePadIdcIdSelect(){
		var optionHtml = '<select id="enablePadControlIdSelect" editable="false" class="easyui-combobox input_width_short" name="enablePadControlId">'
		optionHtml += '<option value="">[全部]</option>';
		var idcId = $("#enablePadIdcId").combobox('getValue'); 
		if(idcId != null && idcId != ""){
			for (var i = 0; i < controlList.length; i++) {
				if(controlList[i].idcId == idcId){
					optionHtml += '<option value='+controlList[i].controlId+'>'+controlList[i].controlName+'</option>'
				}
			}
		}else{
			for (var i = 0; i < controlList.length; i++) {
				optionHtml += '<option value='+controlList[i].controlId+'>'+controlList[i].controlName+'</option>'
			}
		}
		optionHtml += '</select>'
		$("#enablePadIdcIdSelect").remove();
		$("#enablePadControlTd").html(optionHtml);
	} 
	</script>

	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" name="batch_submit_form" method="post" enctype="multipart/form-data">
	<div style="float:left;">
    <input type="hidden" name="batchId" value="${bean.batchId}">
	<table id="batch_submit_table">
		<tr id='fileTempletTr' class="${(giveRbcType eq '1' || giveRbcType eq '3' || sendPasNoticeType eq '1' || sendPasNoticeType eq '3' || enablePadType eq '2' || enablePadType eq '3' || enablePadType eq '4' || sendPadMsgType eq '1' || sendPadMsgType eq '3') ? 'hide' : 'show'}">
			<td class="td1" style="width:20px">下载Excel模板</td>
			<td class="td2">
				<c:if test="${bean.operateType == '1' || bean.operateType == 2 }"><a href='http://file.gc.com.cn/batchTask/templet/padMaint.xlsx' target="_blank">下载模板</a></c:if>
				<c:if test="${bean.operateType == '3'}"><a href='http://file.gc.com.cn/batchTask/templet/renewPad.xlsx' target="_blank">下载模板</a></c:if>
				<c:if test="${bean.operateType == '4'}"><a href='http://file.gc.com.cn/batchTask/templet/userNotice.xlsx' target="_blank">下载模板</a></c:if>
				<c:if test="${bean.operateType == '5'}"><a href='http://file.gc.com.cn/batchTask/templet/padNotice.xlsx' target="_blank">下载模板</a></c:if>
				<c:if test="${bean.operateType == '6' && userRbcType == '1'}"><a href='http://file.gc.com.cn/batchTask/templet/userRbc.xlsx' target="_blank">下载模板</a></c:if>
				<c:if test="${bean.operateType == '6' && userRbcType == '2'}"><a href='http://file.gc.com.cn/batchTask/templet/userRbcByUserId.xlsx' target="_blank">下载模板</a></c:if>
				<c:if test="${bean.operateType == '7'}"><a href='http://file.gc.com.cn/batchTask/templet/padRbc.xlsx' target="_blank">下载模板</a></c:if>
				<c:if test="${bean.operateType == '8'}"><a href='http://file.gc.com.cn/batchTask/templet/padTime.xlsx'>下载模板</a></c:if>
				<c:if test="${bean.operateType == '9'}"><a href='http://file.gc.com.cn/batchTask/templet/padNotice.xlsx'>下载模板</a></c:if>
				<c:if test="${bean.operateType == '11' || bean.operateType == '12'}"><a href='http://file.gc.com.cn/batchTask/templet/vmPadGrant.xlsx'>下载模板</a></c:if>
				<c:if test="${bean.operateType == '13'}"><a href='http://file.gc.com.cn/batchTask/templet/vmPadRestart.xlsx'>下载模板</a></c:if>
				<c:if test="${bean.operateType == '19'}"><a href='http://file.gc.com.cn/batchTask/templet/deviceReboot.xlsx'>下载模板</a></c:if>
				<c:if test="${bean.operateType == '22'}"><a href='http://file.gc.com.cn/batchTask/templet/bindPad.xlsx'>下载模板</a></c:if>
				<c:if test="${bean.operateType == '23' || bean.operateType == '24'}"><a href='http://file.gc.com.cn/batchTask/templet/enablePad.xlsx'>下载模板</a></c:if>
				<c:if test="${bean.operateType == '25'}"><a href='http://file.gc.com.cn/batchTask/templet/padMessage.xlsx'>下载模板</a></c:if>

				
				<c:if test="${bean.operateType == null}">
					<a id='padMaintExcelUrl' href='http://file.gc.com.cn/batchTask/templet/padMaint.xlsx' class='hide' target="_blank">下载模板</a>
					<a id='renewPadExcelUrl' href='http://file.gc.com.cn/batchTask/templet/renewPad.xlsx' class='hide' target="_blank">下载模板</a>
					<a id='userNoticeExcelUrl' href='http://file.gc.com.cn/batchTask/templet/userNotice.xlsx' class='hide' target="_blank">下载模板</a>
					<a id='padNoticeExcelUrl' href='http://file.gc.com.cn/batchTask/templet/padNotice.xlsx' class='hide' target="_blank">下载模板</a>
					<a id='userRbcByAccountExcelUrl' href='http://file.gc.com.cn/batchTask/templet/userRbc.xlsx' class='hide' target="_blank">下载模板</a>
					<a id='userRbcByUserIdExcelUrl' href='http://file.gc.com.cn/batchTask/templet/userRbcByUserId.xlsx' class='hide' target="_blank">下载模板</a>
					<a id='padRbcExcelUrl' href='http://file.gc.com.cn/batchTask/templet/padRbc.xlsx' class='hide' target="_blank">下载模板</a>
					<a id='padTimeExcelUrl' href='http://file.gc.com.cn/batchTask/templet/padTime.xlsx' class='hide' target="_blank">下载模板</a>
					<a id='padNoticeExcelUrl' href='http://file.gc.com.cn/batchTask/templet/padNotice.xlsx' class='hide' target="_blank">下载模板</a>
					<a id='vmPadGrantExcelUrl' href='http://file.gc.com.cn/batchTask/templet/vmPadGrant.xlsx' class='hide' target="_blank">下载模板</a>
					<a id='vmPadRestartExcelUrl' href='http://file.gc.com.cn/batchTask/templet/vmPadRestart.xlsx' class='hide' target="_blank">下载模板</a>
					<a id='deviceRebootExcelUrl' href='http://file.gc.com.cn/batchTask/templet/deviceReboot.xlsx' class='hide' target="_blank">下载模板</a>
					<a id='bindPadExcelUrl' href='http://file.gc.com.cn/batchTask/templet/bindPad.xlsx' class='hide' target="_blank">下载模板</a>
					<a id='enablePadExcelUrl' href='http://file.gc.com.cn/batchTask/templet/enablePad.xlsx' class='hide' target="_blank">下载模板</a>
					<a id='padMsgExcelUrl' href='http://file.gc.com.cn/batchTask/templet/padMessage.xlsx' class='hide' target="_blank">下载模板</a>

				</c:if>
				
			</td>
		</tr>
		<tr>
			<td class="td1">标题:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="batchTitle" value="${bean.batchTitle}" data-options="required:true,validType:'length[0,100]'" /></td>
		</tr>
		<tr>
			<td class="td1"> 操作类型：</td>
			<td class="td2">
				<c:if test="${bean.batchId != null }">
					<input type="hidden" value="${bean.operateType}" name="operateType"/>
				</c:if>
				<select class="easyui-combobox input_width_short" <c:if test="${bean.batchId != null }">disabled="disabled"</c:if> editable="false" id="operateType" data-options="onSelect:operateTypeSelect" name="operateType" validType="selectValueRequired['#operateType']">
					<option value="">[全部]</option>
					<fns:getOptions category="tk_batch_task.operate_type" value="${bean.operateType}"></fns:getOptions>
				</select> 
			</td>
		</tr>
		
		<!-- 用户和设备公告 start-->
		<tr id='userNoticeTitleTr' class="${bean.operateType == '4' ? 'show' : 'hide'}">
			<td class="td1" valign="top">用户公告标题:</td>
			<td class="td2">
				<input class="easyui-textbox" name="userNoticeTitle" value="${userNoticeTitle }"></input>
			</td>
		</tr>
		<tr id='userNoticeContentTr' class="${bean.operateType == '4' ? 'show' : 'hide'}">
			<td class="td1" valign="top">用户公告内容:</td>
			<td class="td2">
				<textarea name="userNoticeContent" rows="10" cols="50" id="userNoticeContentTex"
					onKeyDown="gbcount(this.form.userNoticeContent,this.form.userNoticeTotal,this.form.userNoticeUsed,this.form.userNoticeRemain);"
					onKeyUp="gbcount(this.form.userNoticeContent,this.form.userNoticeTotal,this.form.userNoticeUsed,this.form.userNoticeRemain);">${userNoticeContent}</textarea>
				<p>
					最多字数： <input disabled maxLength="4" name="userNoticeTotal" id="userNoticeTotal" size="3" value="680" class="inputtext"/> 
					已用字数： <input disabled maxLength="4" name="userNoticeUsed" id="userNoticeUsed" size="3" value="${userNoticeContent==null?0:userNoticeContent.length()}" class="inputtext"/>
					剩余字数： <input disabled maxLength="4" name="userNoticeRemain" id="userNoticeRemain" size="3" value="${680-(userNoticeContent==null?0:userNoticeContent.length())}" class="inputtext"/>
				</p>
			</td>
		</tr>
		<tr id="sendPasNoticeTypeTr" class="${bean.operateType == '5' ? 'show' : 'hide'}">
			<td class="td1"> 发送方式：</td>
			<td class="td2">
				<select class="easyui-combobox input_width_short" editable="false" id='sendPasNoticeTypeTrSelect' data-options="onSelect:sendPasNoticeTypeSelect" name="sendPasNoticeType" >
					<option value="2" <c:if test="${sendPasNoticeType == 2 || sendPasNoticeType == null}">selected="selected"</c:if>>按设备编码excel发送</option>
					<option value="1" <c:if test="${sendPasNoticeType == 1 }">selected="selected"</c:if>>按机房发送</option>
					<option value="3" <c:if test="${sendPasNoticeType == 3 }">selected="selected"</c:if>>按设备编码段发送</option>
				</select> 
			</td>
		</tr>
		<tr id="padNoticeIdcIdTr" class="${bean.operateType=='5' && sendPasNoticeType == 1 ? 'show' : 'hide'}">
			<td class="td1"> 选择机房：</td>
			<td class="td2">
				<select class="easyui-combobox input_width_short" editable="false" name="padNoticeIdcId" >
					<option value="">请选择</option>
					<c:if test='${not empty idcList}'>
						<c:forEach var="item" items="${idcList}">
							<option value="${item.idcId}" <c:if test="${padNoticeIdcId == item.idcId}">selected="selected"</c:if>>${item.idcName}</option>
					    </c:forEach>
					</c:if>
				</select> 
			</td>
		</tr>
		<tr id='padNoticePadCodeTr' class="${bean.operateType=='5' && sendPasNoticeType == 3 ? 'show' : 'hide'}">
			<td class="td1">设备编码段(至少填一项):</td>
			<td class="td2">
				<input class="easyui-textbox" style="width: 150px" type="text" name="padNoticePadCodeGt" value="${padNoticePadCodeGt }"/>至
				<input class="easyui-textbox" style="width: 150px" type="text" name="padNoticePadCodeLt" value="${padNoticePadCodeLt }"/>
			</td>
		</tr>
		<tr id='padNoticeTitleTr' class="${bean.operateType=='5' ? 'show' : 'hide'}">
			<td class="td1" valign="top">设备公告标题:</td>
			<td class="td2">
				<input class="easyui-textbox" name="padNoticeTitle" value="${padNoticeTitle }"></input>
			</td>
		</tr>
		<tr class="${bean.operateType == '5'? 'show' : 'hide'}" id='sendPadNoticeTemplateTr'>
			<td class="td1">选择公告模板:</td>
			<td class="td2">
				<select editable="false" id="PadNoticeTemplateTrSel" class="easyui-combobox input_width_default" data-options="onSelect:choicesendPadNoticeTemplate">
					<c:if test='${not empty padNoticeTemplates}'>
						<c:forEach var="item" items="${padNoticeTemplates}">
							<option value="${item.templateText}">${item.templateText}</option>
					    </c:forEach>
					</c:if>
				</select>
			</td>
		</tr>
		<tr id='padNoticeContentTr' class="${bean.operateType=='5' ? 'show' : 'hide'}">
			<td class="td1" valign="top">设备公告内容:</td>
			<td class="td2">
				<textarea name="padNoticeContent" rows="10" cols="50" id="padNoticeContentTex"
					onKeyDown="gbcount(this.form.padNoticeContent,this.form.padNoticeTotal,this.form.padNoticeUsed,this.form.padNoticeRemain);"
					onKeyUp="gbcount(this.form.padNoticeContent,this.form.padNoticeTotal,this.form.padNoticeUsed,this.form.padNoticeRemain);">${padNoticeContent==null ? padNoticeTemplates[0].templateText : padNoticeContent}</textarea>
				<p>
					最多字数： <input disabled maxLength="4" name="padNoticeTotal" id="padNoticeTotal" size="3" value="680" class="inputtext"/> 
					已用字数： <input disabled maxLength="4" name="padNoticeUsed" id="padNoticeUsed" size="3" value="${padNoticeContent==null ? padNoticeTemplates[0].templateText.length() : padNoticeContent.length()}" class="inputtext"/>
					剩余字数： <input disabled maxLength="4" name="padNoticeRemain" id="padNoticeRemain" size="3" value="${padNoticeContent==null ? 680-padNoticeTemplates[0].templateText.length() : 680-padNoticeContent.length()}" class="inputtext"/>
				</p>
			</td>
		</tr>
		<tr id="sendPadnoticeTipTr" class="${bean.operateType == '5' ? 'show' : 'hide'}">
			<td class="td1"></td>
			<td class="td2">
			#设备名称#会自动替换成用户设备名称或设备名称
			</td>
		</tr> 
		<tr id='oneNoticeStatusTr' class="${bean.operateType == '5' ? 'show' : 'hide'}">
			<td class="td1" valign="top">是否按用户合并发送:</td>
			<td class="td2">
				<input  type="radio" name="oneNoticeStatus" value="1" <c:if test="${oneNoticeStatus == 1 || oneNoticeStatus == null}">checked="checked"</c:if>>是
				<input  type="radio" name="oneNoticeStatus" value="0" <c:if test="${oneNoticeStatus == 0}">checked="checked"</c:if>>否
			</td>
		</tr>
		<tr id='popStatusTr' class="${bean.operateType == '4' || bean.operateType == '5' ? 'show' : 'hide'}">
			<td class="td1" valign="top">是否强制弹出:</td>
			<td class="td2">
				<input  type="radio" name="popStatus" value="1" <c:if test="${noticePopstatus == 1}">checked="checked"</c:if>>强制弹出 
				<input  type="radio" name="popStatus" value="0" <c:if test="${noticePopstatus == 0 || noticePopstatus == null}">checked="checked"</c:if>>不强行弹出
			</td>
		</tr>
		<tr id='popExpiredTimeTr'  class="${bean.operateType == '4' ? 'show' : 'hide'}">
			<td class="td1">弹出有效时间:</td>
			<td class="td2">
				<input type="text" style="width:180px; height: 20px;" class="easyui-datetimebox input_width_default" editable="false" name="popExpiredTime" value="${noticePopexpired }"/>
			</td>
		</tr>
		<!-- 用户和设备公告 end-->
		
		
		
		
		
		<!-- 发送设备消息-start -->
		<tr id="sendPasMsgTypeTr" class="${bean.operateType == '25' ? 'show' : 'hide'}">
			<td class="td1"> 发送方式：</td>
			<td class="td2">
				<select class="easyui-combobox input_width_short" editable="false" id='sendPadMsgTypeSelect' data-options="onSelect:sendPadMsgTypeSelect" name="sendPadMsgType" >
					<option value="2" <c:if test="${sendPadMsgType == 2 || sendPadMsgType == null}">selected="selected"</c:if>>按设备编码excel发送</option>
					<option value="1" <c:if test="${sendPadMsgType == 1 }">selected="selected"</c:if>>按机房发送</option>
					<option value="3" <c:if test="${sendPadMsgType == 3 }">selected="selected"</c:if>>按设备编码段发送</option>
				</select> 
			</td>
		</tr>
		<tr id="padMsgIdcIdTr" class="${bean.operateType=='25' && sendPadMsgType == 1 ? 'show' : 'hide'}">
			<td class="td1"> 选择机房：</td>
			<td class="td2">
				<select class="easyui-combobox input_width_short" editable="false" name="padMsgIdcId" >
					<option value="">请选择</option>
					<c:if test='${not empty idcList}'>
						<c:forEach var="item" items="${idcList}">
							<option value="${item.idcId}" <c:if test="${padMsgIdcId == item.idcId}">selected="selected"</c:if>>${item.idcName}</option>
					    </c:forEach>
					</c:if>
				</select> 
			</td>
		</tr>
		<tr id='padMsgPadCodeTr' class="${bean.operateType=='25' && sendPadMsgType == 3 ? 'show' : 'hide'}">
			<td class="td1">设备编码段(至少填一项):</td>
			<td class="td2">
				<input class="easyui-textbox" style="width: 150px" type="text" name="padMsgPadCodeGt" value="${padMsgPadCodeGt }"/>至
				<input class="easyui-textbox" style="width: 150px" type="text" name="padMsgPadCodeLt" value="${padMsgPadCodeLt }"/>
			</td>
		</tr>
		<tr id='padMsgTitleTr' class="${bean.operateType == '25' ? 'show' : 'hide'}">
			<td class="td1" valign="top">通知标题:</td>
			<td class="td2">
				<input class="easyui-textbox" name="padMsgTitle" value="${padMsgTitle }"></input>
			</td>
		</tr>
		<tr  id='sendPadMsgTemplateTr' class="${bean.operateType == '25'? 'show' : 'hide'}">
			<td class="td1">选择设备通知内容模板:</td>
			<td class="td2">
				<select editable="false" id="PadMsgTemplateTrSel" class="easyui-combobox input_width_default" data-options="onSelect:choiceSendPadMsgTemplate">
					<c:if test='${not empty padMsgTemplates}'>
						<c:forEach var="item" items="${padMsgTemplates}">
							<option value="${item.templateText}">${item.templateText}</option>
					    </c:forEach>
					</c:if>
				</select>
			</td>
		</tr>
		<tr id='padMsgContentTr' class="${bean.operateType == '25' ? 'show' : 'hide'}">
			<td class="td1" valign="top">通知内容:</td>
			<td class="td2">
				<textarea name="padMsgContent" rows="10" cols="50" id="padMsgContentTex"
					onKeyDown="gbcount(this.form.padMsgContent,this.form.padMsgTotal,this.form.padMsgUsed,this.form.padMsgRemain);"
					onKeyUp="gbcount(this.form.padMsgContent,this.form.padMsgTotal,this.form.padMsgUsed,this.form.padMsgRemain);">${padMsgContent==null ? padMsgTemplates[0].templateText : padMsgContent}</textarea>
				<p>
					最多字数： <input disabled maxLength="4" name="padMsgTotal" id="padMsgTotal" size="3" value="680" class="inputtext"/> 
					已用字数： <input disabled maxLength="4" name="padMsgUsed" id="padMsgUsed" size="3" value="${padMsgContent==null ? padMsgTemplates[0].templateText.length() : padMsgContent.length()}" class="inputtext"/>
					剩余字数： <input disabled maxLength="4" name="padMsgRemain" id="padMsgRemain" size="3" value="${padMsgContent==null ? 680-padMsgTemplates[0].templateText.length() : 680-padMsgContent.length()}" class="inputtext"/>
				</p>
			</td>
		</tr>
		<tr id="padMsgContentTipTr" class="${bean.operateType == '25' ? 'show' : 'hide'}">
			<td class="td1"></td>
			<td class="td2">
			#设备名称#会自动替换成用户设备名称或设备名称
			</td>
		</tr>
		<tr class="${bean.operateType == '25' ? 'show' : 'hide'}" id='sendPadWeixinTemplateTr'>
			<td class="td1">选择微信模板:</td>
			<td class="td2">
				<select editable="false" id="sendPadWeixinTemplate" class="easyui-combobox input_width_default" data-options="onSelect:choicesendPadWeixinTemplate">
					<c:if test='${not empty padWeixinTemplates}'>
						<c:forEach var="item" items="${padWeixinTemplates}">
							<option value="${item.templateText}">${item.templateText}</option>
					    </c:forEach>
					</c:if>
				</select>
			</td>
		</tr> 
		<tr id="sendPadWchartTr" class="${bean.operateType == '25' ? 'show' : 'hide'}">
			<td class="td1">微信内容：</td>
			<td class="td2">
				<textarea name="padWeixinContent" rows="10" cols="50" id="padWeixinContentTex"
					onKeyDown="gbcount(this.form.padWeixinContent,this.form.padWeixinTotal,this.form.padWeixinUsed,this.form.padWeixinRemain);"
					onKeyUp="gbcount(this.form.padWeixinContent,this.form.padWeixinTotal,this.form.padWeixinUsed,this.form.padWeixinRemain);">${padWeixinContent==null ? padWeixinTemplates[0].templateText : padWeixinContent}</textarea>
				<p>
					最多字数： <input disabled maxLength="4" name="padWeixinTotal" id="padWeixinTotal" size="3" value="680" class="inputtext"/> 
					已用字数： <input disabled maxLength="4" name="padWeixinUsed" id="padWeixinUsed" size="3" value="${padWeixinContent==null ? padWeixinTemplates[0].templateText.length() : padWeixinContent.length()}" class="inputtext"/>
					剩余字数： <input disabled maxLength="4" name="padWeixinRemain" id="padWeixinRemain" size="3" value="${padWeixinContent==null ? 680-padWeixinTemplates[0].templateText.length() : 680-padWeixinContent.length()}" class="inputtext"/>
				</p>
			</td>
		</tr>
		<tr id="sendPadWchartTipTr" class="${bean.operateType == '25'? 'show' : 'hide'}">
			<td class="td2"></td>
			<td class="td2">
			XXX 和  #设备名称# 都会自动替换成设备名称
			</td>
		</tr>
		<tr id='oneMsgStatusTr' class="${bean.operateType == '25' ? 'show' : 'hide'}">
			<td class="td1" valign="top">是否按用户合并发送:</td>
			<td class="td2">
				<input  type="radio" name="oneMsgStatus" value="1" <c:if test="${oneMsgStatus == 1 || oneMsgStatus == null}">checked="checked"</c:if>>是
				<input  type="radio" name="oneMsgStatus" value="0" <c:if test="${oneMsgStatus == 0}">checked="checked"</c:if>>否
			</td>
		</tr>		
		<!-- 发送设备消息 end-->
		
		
		
		
		
		
		<!-- 更换设备 start-->
		<tr class="${bean.operateType == '3' ? 'show' : 'hide'}" id='isSendMessageChoice'>
			<td class="td1">是否发送消息:</td>
			<td class="td2">
				<select editable="false" id="isSendMessage" name="isSendMessage" class="easyui-combobox input_width_default" data-options="onSelect:choiceIsSendMessage">
					<option value="1" <c:if test="${isSendMessage == '1' }">selected="selected"</c:if>>是</option>
					<option value="0" <c:if test="${isSendMessage == '0' }">selected="selected"</c:if>>否</option>
				</select>
			</td>
		</tr>
		<tr class="${bean.operateType == '3' && isSendMessage == '1' ? 'show' : 'hide'}" id='sendMessageTemplateTr'>
			<td class="td1">选择消息模板:</td>
			<td class="td2">
				<select editable="false" id="sendMessageTemplate" class="easyui-combobox input_width_default" data-options="onSelect:choicesendMessageTemplate">
					<c:if test='${not empty msgTemplates}'>
						<c:forEach var="item" items="${msgTemplates}">
							<option value="${item.templateText}">${item.templateText}</option>
					    </c:forEach>
					</c:if>
				</select>
			</td>
		</tr>
		<tr id="sendMessageTr" class="${bean.operateType == '3' && isSendMessage == '1' ? 'show' : 'hide'}">
			<td class="td1">消息模板：</td>
			<td class="td2">
				<c:if test="${sendMessageTemplate == null}">
					<textarea id="showMessageTemplate" name="sendMessageTemplate" rows="2" cols="42">${msgTemplates[0].templateText}</textarea>
				</c:if>
				<c:if test="${sendMessageTemplate != null }">
					<textarea id="showMessageTemplate" name="sendMessageTemplate" rows="2" cols="42">${sendMessageTemplate }</textarea>
				</c:if>
			</td>
		</tr>
		<tr id="sendMessageTipTr" class="${bean.operateType == '3' && isSendMessage == '1' ? 'show' : 'hide'}">
			<td class="td1"></td>
			<td class="td2">
			XXX会自动替换成设备名称,最好保留,不然消息看不到设备名称
			</td>
		</tr> 
		<tr class="${bean.operateType == '3' ? 'show' : 'hide'}" id='isSendWechartChoice'>
			<td class="td1">是否发送微信:</td>
			<td class="td2">
				<select editable="false" id="isSendWechart" name="isSendWechart" class="easyui-combobox input_width_default" data-options="onSelect:choiceIsSendWechart" >
					<option value="1" <c:if test="${isSendWechart == '1' }">selected="selected"</c:if>>是</option>
					<option value="0" <c:if test="${isSendWechart == '0' }">selected="selected"</c:if>>否</option>
				</select>
			</td>
		</tr>
		<tr class="${bean.operateType == '3' && isSendWechart == '1' ? 'show' : 'hide'}" id='sendWeixinTemplateTr'>
			<td class="td1">选择微信模板:</td>
			<td class="td2">
				<select editable="false" id="sendWeixinTemplate" class="easyui-combobox input_width_default" data-options="onSelect:choicesendWeixinTemplate">
					<c:if test='${not empty weixinTemplates}'>
						<c:forEach var="item" items="${weixinTemplates}">
							<option value="${item.templateText}">${item.templateText}</option>
					    </c:forEach>
					</c:if>
				</select>
			</td>
		</tr>
		<tr id="sendWchartTr" class="${bean.operateType == '3'  && isSendWechart == '1' ? 'show' : 'hide'}">
			<td class="td1">微信模板：</td>
			<td class="td2">
				<c:if test="${sendWechartTemplate == null}">
					<textarea id="sendWechartTemplate" name="sendWechartTemplate" rows="2" cols="42">${weixinTemplates[0].templateText}</textarea>
				</c:if>
				<c:if test="${sendWechartTemplate != null }">
					<textarea id="sendWechartTemplate" name="sendWechartTemplate" rows="2" cols="42">${sendWechartTemplate }</textarea>
				</c:if>
			</td>
		</tr>
		<tr id="sendWchartTipTr" class="${bean.operateType == '3'  && isSendWechart == '1' ? 'show' : 'hide'}">
			<td class="td2"></td>
			<td class="td2">
			XXX会自动替换成设备名称,最好保留,不然公告看不到设备名称
			</td>
		</tr>
		<tr id="reNewPadIdcIdTr" class="${bean.operateType=='3' ? 'show' : 'hide'}">
			<td class="td1">新设备所在机房(可选)：</td>
			<td class="td2">
				<select id="reNewPadIdcIdSelectTr" class="easyui-combobox input_width_short" data-options="onSelect:reNewPadIdcIdSelect" editable="false" name="reNewPadIdcId" >
					<option value="">[全部]</option>
					<c:if test='${not empty idcList}'>
						<c:forEach var="item" items="${idcList}">
							<option value="${item.idcId}" <c:if test="${reNewPadIdcId == item.idcId}">selected="selected"</c:if>>${item.idcName}</option>
					    </c:forEach>
					</c:if>
				</select> 
			</td>
		</tr>
	    <tr id='controlTr' class="${bean.operateType=='3' ? 'show' : 'hide'}">
			<td class="td1">新设备的设备节点(可选):</td>
			<td class="td2" id='padControlIdSelectTr'>
				<select id="padControlIdSelect" editable="false" name="padControlId" class="easyui-combobox input_width_default">
					<option value="">[全部]</option>
					<c:choose>
						<c:when test="${bean.operateType == '3' && (not empty padControls)}">			
							<c:forEach var="item" items="${padControls}">
								<option class="reNewOption" value="${item.controlId}" <c:if test="${padControlId == item.controlId}">selected="selected"</c:if>>${item.controlName}</option>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<c:forEach var="item" items="${controlList}">
								<option class="reNewOption" value="${item.controlId}">${item.controlName}</option>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</select>
			 </td>
		</tr>
		<tr id='padCodeInTr' class="${bean.operateType=='3' ? 'show' : 'hide'}">
			<td class="td1">新设备的设备编码段(可选):</td>
			<td class="td2">
				<input class="easyui-textbox" style="width: 150px" type="text" name="padCodeGt" value="${padCodeGt }"/>至
				<input class="easyui-textbox" style="width: 150px" type="text" name="padCodeLt" value="${padCodeLt }"/>
			</td>
		</tr>
		<tr id='deviceIpTr' class="${bean.operateType=='3' ? 'show' : 'hide'}">
			<td class="td1">新设备的物理IP(可选):</td>
			<td class="td2">
				<input class="easyui-textbox" style="width: 150px" type="text" name="deviceIpGt" value="${deviceIpGt }"/>至
				<input class="easyui-textbox" style="width: 150px" type="text" name="deviceIpLt" value="${deviceIpLt }"/>
			</td>
		</tr>
       <!-- 更换设备 end-->
       
       
       
       
       
       
       <!-- 增加设备时间 start -->
        <tr id='timeTypeTr' class="${bean.operateType == '8' ? 'show' : 'hide'}" >
			<td class="td1">时间类型:</td>
			<td class="td2">
				<select class="easyui-combobox input_width_short"  editable="false" name="timeType" data-options="required:false">
				    <fns:getOptions category="time.time_type" value="${timeType}"></fns:getOptions>
			 	</select> 
			</td>
		</tr>
		<tr id='controltimeTr' class="${bean.operateType=='8' ?'show':'hide'}" >
			<td class="td1">调整在线时间:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="controltime" value="${controltime }"/></td>
		</tr>
		<!-- 增加设备时间 end -->
		
		
		
		
		
		
		<!-- 按用户修改红豆 start -->
		<tr id="userRbcTypeTr" class="${bean.operateType == '6' ? 'show' : 'hide'}">
			<td class="td1"> 赠送方式：</td>
			<td class="td2">
				<select class="easyui-combobox input_width_short" editable="false" id='userRbcType' data-options="onSelect:userRbcTypeSelect" name="userRbcType" >
					<option value="1" <c:if test="${userRbcType == 1 }">selected="selected"</c:if>>按用户账号</option>
					<option value="2" <c:if test="${userRbcType == 2 }">selected="selected"</c:if>>按用户ID</option>
				</select> 
			</td>
		</tr>
		<tr id='rbcGetTr' class="${bean.operateType == '6' ? 'show' : 'hide'}">
			<td class="td1">调整红豆:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="rbcGet" value="${rbcGet }"/></td>
		</tr>
		<!-- 按用户修改红豆 end -->
		
		
		
		
		
		<!-- 按设备赠送红豆 start-->
		<tr id="giveRbcTypeTr" class="${bean.operateType == '7' ? 'show' : 'hide'}">
			<td class="td1"> 赠送方式：</td>
			<td class="td2">
				<select class="easyui-combobox input_width_short" editable="false" id='giveType' data-options="onSelect:giveRbcTypeSelect" name="giveRbcType" >
					<option value="2" <c:if test="${giveRbcType == 2 || giveRbcType == null}">selected="selected"</c:if>>按设备编码excel赠送</option>
					<option value="1" <c:if test="${giveRbcType == 1 }">selected="selected"</c:if>>按机房赠送</option>
					<option value="3" <c:if test="${giveRbcType == 3 }">selected="selected"</c:if>>按编码段赠送</option>
				</select> 
			</td>
		</tr>
		<tr id='padRbcPadCodeTr' class="${bean.operateType=='7'  && giveRbcType == '3' ? 'show' : 'hide'}">
			<td class="td1">设备编码段(至少填一项):</td>
			<td class="td2">
				<input class="easyui-textbox" style="width: 150px"  type="text" name="padRbcPadCodeGt" value="${padRbcPadCodeGt }"/>至
				<input class="easyui-textbox" style="width: 150px"  type="text" name="padRbcPadCodeLt" value="${padRbcPadCodeLt }"/>
			</td>
		</tr>
		<tr id="idcIdTr" class="${bean.operateType=='7' && giveRbcType == '1' ?'show':'hide'}">
			<td class="td1"> 选择机房：</td>
			<td class="td2">
				<select class="easyui-combobox input_width_short" editable="false" name="idcId" >
					<option value="">请选择</option>
					<c:if test='${not empty idcList}'>
						<c:forEach var="item" items="${idcList}">
							<option value="${item.idcId}" <c:if test="${idcId == item.idcId}">selected="selected"</c:if>>${item.idcName}</option>
					    </c:forEach>
					</c:if>
				</select> 
			</td>
		</tr>
		<tr id='commonRbcTr' class="${bean.operateType == '7' ? 'show' : 'hide'}">
			<td class="td1">普通设备:</td>
			<td class="td2"><input class="easyui-numberbox" min=0 type="text" name="commonRbc" value="${commonRbc }"/></td>
		</tr>
		
		<tr id='vipRbcTr' class="${bean.operateType == '7' ? 'show' : 'hide'}">
			<td class="td1">VIP设备:</td>
			<td class="td2"><input class="easyui-numberbox" min=0 type="text" name="vipRbc" value="${vipRbc }"/></td>
		</tr>
		<tr id='gvipRbcTr' class="${bean.operateType == '7' ? 'show' : 'hide'}">
			<td class="td1">GVIP设备:</td>
			<td class="td2"><input class="easyui-numberbox" min=0 type="text" name="gvipRbc" value="${gvipRbc }"/></td>
		</tr>
		<tr id='svipRbcTr' class="${bean.operateType == '7' ? 'show' : 'hide'}">
			<td class="td1">SVIP设备:</td>
			<td class="td2"><input class="easyui-numberbox" min=0 type="text" name="svipRbc" value="${svipRbc }"/></td>
		</tr>
		<!-- 按设备赠送红豆 end-->
		
		
		
		
		
		
		<!-- 批量绑定设备 start-->
		<tr id="padClassifyTr" class="${bean.operateType == '22' ? 'show' : 'hide'}">
			<td class="td1">设备类型：</td>
			<td class="td2">
			  <select class="easyui-combobox input_width_short" editable="false" data-options="onSelect:operateTypeSelect" name="padClassify" id="choicePadClassify" data-options="onSelect:choicePadClassify">
					<fns:getOptions category="rf_pad.pad_classify" value="${padClassify}" keys="rf_pad.pad_classify@major,rf_pad.pad_classify@game,rf_pad.pad_classify@gvip,rf_pad.pad_classify@cloud"></fns:getOptions>
		      </select> 
		    </td>
		</tr>
		<tr id="goodsIdTr" class="${bean.operateType == '22' && (padClassify != '3'&&padClassify != '2'&&padClassify != '5') ? 'show' : 'hide'}">
			<td class="td1">VIP套餐：</td>
			<td class="td2">
				<select class="easyui-combobox input_width_default" editable="false" name="goodsId">
					<option  value="-1" <c:if test="${goodsId == -1}">selected="selected"</c:if>>[普通]</option>
					<c:forEach var="one" items="${goods}">
						<option value="${one.goodsId }" <c:if test="${goodsId == one.goodsId}">selected="selected"</c:if>>${one.goodsName }</option>
					</c:forEach>
      	        </select>
		     </td>
		</tr>
		<tr id="gvipGoodsIdTr" class="${bean.operateType == '22' && padClassify == '3' ? 'show' : 'hide'}">
			<td class="td1">GVIP套餐：</td>
			<td class="td2">
				<select class="easyui-combobox input_width_default" editable="false" name="gvipGoodsId">
					<c:forEach var="one" items="${gvipGoods}">
						<option value="${one.goodsId }" <c:if test="${gvipGoodsId == one.goodsId}">selected="selected"</c:if>>${one.goodsName }</option>
					</c:forEach>
      	        </select>
		     </td>
		</tr>
		<tr id="gamePadTimeTr" class="${bean.operateType == '22' && padClassify == '2' ? 'show' : 'hide'}">
			<td class="td1">绑定设备时长(单位:小时)：</td>
			<td class="td2"><input class="easyui-numberbox" min=1 type="text" name="gamePadTime" value="${gamePadTimeValue == null ? gamePadTime : gamePadTimeValue }"/></td>
		</tr>
		<tr id="cloudPadTimeTr" class="${bean.operateType == '22' && padClassify == '5' ? 'show' : 'hide'}">
			<td class="td1">绑定设备时长(单位:小时)：</td>
			<td class="td2"><input class="easyui-numberbox" min=1 type="text" name="cloudPadTime" value="${cloudPadTimeValue}"/></td>
		</tr>
		<!-- 批量绑定设备 end-->
		
		
		
		
		
		
		<!-- 启用或禁用设备 -->
		<tr id="enablePadTypeTr" class="${bean.operateType == '23' || bean.operateType == '24' ? 'show' : 'hide'}">
			<td class="td1">设备启用或禁用方式：</td>
			<td class="td2">
				<select class="easyui-combobox input_width_short" editable="false" id='enablePadType' data-options="onSelect:enablePadTypeSelect" name="enablePadType" >
					<option value="1" <c:if test="${enablePadType == 1 || enablePadType == null}">selected="selected"</c:if>>按设备编码excel</option>
					<option value="2" <c:if test="${enablePadType == 2 }">selected="selected"</c:if>>按条件</option>
					<option value="3" <c:if test="${enablePadType == 3 }">selected="selected"</c:if>>全部设备</option>
				</select> 
			</td>
		</tr>
		<tr id="enablePadIdcIdTr" class="${(bean.operateType == '23' || bean.operateType == '24') && enablePadType == '2' ? 'show' : 'hide'}">
			<td class="td1">选择机房：</td>
			<td class="td2">
				<select id="enablePadIdcId" class="easyui-combobox input_width_short" editable="false" name="enablePadIdcId" data-options="onSelect:enablePadIdcIdSelect" editable="false">
					<option value="">[请选择]</option>
					<c:if test='${not empty idcList}'>
						<c:forEach var="item" items="${idcList}">
							<option value="${item.idcId}" <c:if test="${enablePadIdcId == item.idcId}">selected="selected"</c:if>>${item.idcName}</option>
					    </c:forEach>
					</c:if>
				</select> 
			</td>
		</tr>
	    <tr id='enablePadControlTr' class="${(bean.operateType == '23' || bean.operateType == '24') && enablePadType == '2' ? 'show' : 'hide'}">
			<td class="td1">设备节点:</td>
			<td class="td2" id='enablePadControlTd'>
				<select id="enablePadControlIdSelect" editable="false" name="enablePadControlId" class="easyui-combobox input_width_default">
					<option value="">[全部]</option>
					<c:choose>
						<c:when test="${(bean.operateType == '23' || bean.operateType == '24') && (not empty enablePadControls)}">			
							<c:forEach var="item" items="${enablePadControls}">
								<option class="reNewOption" value="${item.controlId}" <c:if test="${enablePadControlId == item.controlId}">selected="selected"</c:if>>${item.controlName}</option>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<c:forEach var="item" items="${controlList}">
								<option class="reNewOption" value="${item.controlId}">${item.controlName}</option>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</select>
			 </td>
		</tr>
		<tr id="enablePadStatusTr" class="${(bean.operateType == '23' || bean.operateType == '24') && enablePadType == '2' ? 'show' : 'hide'}">
			<td class="td1">启用：</td>
			<td class="td2">
				<select id="enablePadIdcId" class="easyui-combobox input_width_short" editable="false" name="enablePadStatus" >
					<option value="">[全部]</option>
					<fns:getOptions category="rf_pad.enable_status" value="${enablePadStatus }"></fns:getOptions>
				</select> 
			</td>
		</tr>
		<tr id="enablePadBindStatusTr" class="${(bean.operateType == '23' || bean.operateType == '24') && enablePadType == '2' ? 'show' : 'hide'}">
			<td class="td1">绑定：</td>
			<td class="td2">
				<select id="enablePadIdcId" class="easyui-combobox input_width_short" editable="false" name="enablePadBindStatus" >
					<option value="">[全部]</option>
					<fns:getOptions category="rf_pad.bind_status" value="${enablePadBindStatus }"></fns:getOptions>
				</select> 
			</td>
		</tr>			
		<tr id="enablePadFaultStatusTr" class="${(bean.operateType == '23' || bean.operateType == '24') && enablePadType == '2' ? 'show' : 'hide'}">
			<td class="td1">故障：</td>
			<td class="td2">
				<select id="enablePadIdcId" class="easyui-combobox input_width_short" editable="false" name="enablePadFaultStatus" >
					<option value="">[全部]</option>
					<fns:getOptions category="rf_pad.fault_status"  value="${enablePadFaultStatus }"></fns:getOptions>
				</select> 
			</td>
		</tr>		
		<tr id='enablePadCodeInTr' class="${(bean.operateType == '23' || bean.operateType == '24') && enablePadType == '2' ? 'show' : 'hide'}">
			<td class="td1">设备编码段:</td>
			<td class="td2">
				<input class="easyui-textbox" style="width: 150px" type="text" name="enablePadCodeGt" value="${enablePadCodeGt }"/>至
				<input class="easyui-textbox" style="width: 150px" type="text" name="enablePadCodeLt" value="${enablePadCodeLt }"/>
			</td>
		</tr>
		<tr id='enablePadDeviceIpTr' class="${(bean.operateType == '23' || bean.operateType == '24') && enablePadType == '2' ? 'show' : 'hide'}">
			<td class="td1">物理ip段:</td>
			<td class="td2">
				<input class="easyui-textbox" style="width: 150px" type="text" name="enablePadDeviceIpGt" value="${enablePadDeviceIpGt }"/>至
				<input class="easyui-textbox" style="width: 150px" type="text" name="enablePadDeviceIpLt" value="${enablePadDeviceIpLt }"/>
			</td>
		</tr>
		<tr id='fileUploadTr' class="${(giveRbcType eq '1' || giveRbcType eq '3' || sendPasNoticeType eq '1' || sendPasNoticeType eq '3' || enablePadType eq '2' || enablePadType eq '3' || sendPadMsgType eq '1' || sendPadMsgType eq '3')? 'hide' : 'show'}">
			<td class="td1" style="width:10px">上传文件:</td>
			<td class="td2">
	   			<input type="file" name="fileUpdate" id="">
	   			<input type="hidden" name="excelPath" value="${excelPath}">
				<a class="${filePath != null ? 'show' : 'hide'}" href="${filePath}" target="_blank">${fileName }.xlsx</a>
			</td>
		</tr>
		<!-- 启用或禁用设备end -->
		
		
		
		
		
		<tr>
			<td class="td1">备注:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="remark" value="${bean.remark }"/></td>
		</tr>
		<tr>
			<td class="td1">优先级：</td>
			<td class="td2">
				<select class="easyui-combobox input_width_short" editable="false" name="priority" data-options="required:false" style="width:80px;" value="${bean.priority}">
					<option value="1" <c:if test="${bean.priority == 1 }">selected="selected"</c:if>>1</option>
					<option value="2" <c:if test="${bean.priority == 2 }">selected="selected"</c:if>>2</option>
					<option value="3" <c:if test="${bean.priority == 3 }">selected="selected"</c:if>>3</option>
					<option value="4" <c:if test="${bean.priority == 4 }">selected="selected"</c:if>>4</option>
					<option value="5" <c:if test="${bean.priority == 5 }">selected="selected"</c:if> <c:if test="${bean.batchId == null }">selected="selected"</c:if>>5</option>
					<option value="6" <c:if test="${bean.priority == 6 }">selected="selected"</c:if>>6</option>
					<option value="7" <c:if test="${bean.priority == 7 }">selected="selected"</c:if>>7</option>
					<option value="8" <c:if test="${bean.priority == 8 }">selected="selected"</c:if>>8</option>
					<option value="9" <c:if test="${bean.priority == 9 }">selected="selected"</c:if>>9</option>
				</select> 
			</td>
		</tr>
		<tr>
			<td class="td1" style="width:20px">失败是否继续:</td>
			<td class="td2">
				<input type="radio" name="failContinue" value="1" <c:if test="${bean.failContinue == 1 || bean.failContinue == null }">checked="checked"</c:if>/>继续
				<input type="radio" name="failContinue" value="0" <c:if test="${bean.failContinue == 0 }">checked="checked"</c:if>/>停止
			</td>
		</tr>
		<tr>
			<td class="td1">执行开始时间:</td>
			<td class="td2">
				<input type="text" class="easyui-datetimebox input_width_default" editable="false"  id="begin" name="startTime" value="<fmt:formatDate value="${bean.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" data-options="required:true"/>
			</td>
		</tr>
		<tr>
			<td class="td1">执行结束时间:</td>
			<td class="td2">
				<input type="text" class="easyui-datetimebox input_width_default" editable="false"  id="end" name="endTime" value="<fmt:formatDate value="${bean.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" data-options="required:true"/>
			</td>
		</tr>
	</table>
	</div>
    </form>
    </div>
</body>
</html>
