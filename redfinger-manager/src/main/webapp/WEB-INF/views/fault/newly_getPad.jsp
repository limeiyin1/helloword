<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>故障咨询</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body id="bd">
   <style>
   
        /*commons*/
       /*火狐浏览器去除焦点事件的小黑框*/
       button,a,input[type=button],input[type=submit]::-moz-focus-inner{outline:0;}
       input,textarea:focus {
            outline: none;
       }
       
       .td1{min-width:100px;}
       .td2{max-width:200px;word-break: break-all;}
       
	    .action-button {
	    width:150px;
		background-color: #5cb85c;
	    border-color: #4cae4c;
		font-weight: bold;
		color: white;
		border: 0 none;
		border-radius: 5px;
		cursor: pointer;
		padding: 10px 5px;
		margin: 10px 5px;
	}
	
	.action-button:hover,  .action-button:focus {
		box-shadow: 0 0 0 2px white, 0 0 0 3px #27AE60;
		background-color: #449d44;
	}
	
	#history-fault .panel-title .history-fault-title{
		white-space:nowrap;
		text-overflow:ellipsis;
		width:390px;
		overflow:hidden;
		cursor:default;
	}
	
	
	</style>
	<script type="text/javascript">
	
	
	
	    var self_host = '${pageContext.request.contextPath}/fault/self/';
	
	    var faultback_id;
	    
	    var controlLIst;
	
	   /* 加载*/
	   $(function(){
		   
		   
		   /* 添加工单和处理工单回调函数*/
		   var save_callback=function(data){
				innerCallback(data, function(){
					// 关闭进度条
					$.messager.progress('close');
					// 故障类型
					inputToSpan("classId","#subtab_content_${padCode} .td2",$("#subtab_content_${padCode} #classId").combotree("getText"));
					// 故障描述
					inputToSpan("feedbackContent","#subtab_content_${padCode} .td2");
					// 联系电话
					inputToSpan("feedbackContact","#subtab_content_${padCode} .td2");
					// QQ
					inputToSpan("feedbackQq","#subtab_content_${padCode} .td2");
					// 来源
					inputToSpan("feedbackSource","#subtab_content_${padCode} .td2",$('#subtab_content_${padCode} #feedbackSource').combobox('getText'));
					// 备注
					inputToSpan("remark","#subtab_content_${padCode} .td2");
					// 受理工单
					inputToSpan("isAccept","#subtab_content_${padCode} .td2",$$("input[name='isAccept']").val() == 1 ? "已受理" : "未受理");
					
					// 设置面板标题为'处理工单'
					$("#fault-panel-${padCode}").panel("setTitle","处理工单");
					
					// 更换按钮文字
					$("#fault-btn-${padCode}").unbind("click").removeClass("newly-${padCode}").addClass("chanage-pad-${padCode}").val("下一步,移交工单");
					
				 	$(".chanage-pad-${padCode}").click(function(){
					   $(".change-form-${padCode}").submit();
					   $.messager.progress({
			                title:'请稍后',
			                msg:'正在处理工单...'
			           });
				 	 });
					
					if (jQuery.type(data) == 'string') {
						data = eval('(' + data + ')');
					}
					  
					 faultback_id = data.sysDate;
					  
					$(".change-form-${padCode}").load(self_host + "renewalFormModule?faultFeedbackId="+faultback_id,function(){
						
						// 重新渲染重新, 避免easyui组件没有样式问题
						 $.parser.parse($("#fault-panel-${padCode}"));
						
						controlList = $("#controlLIst").text();
						
						if (jQuery.type(controlList) == 'string') {
							controlList = eval('(' + controlList + ')');
						}
						

						 $('#fault-panel-${padCode} #idcId').combobox({
							onChange:function(newValue,oldValue){
								 $("#fault-panel-${padCode} #controlId").combobox("clear");
								if(newValue != ''){
									var data = [];
									data.push({"value":"","text":"[全部]"});
									for(i=0;i<controlList.length;i++){
										if(controlList[i].idcId==newValue){
											data.push({"value":controlList[i].controlId,"text":controlList[i].controlName});
										}
									}
									$("#fault-panel-${padCode} #controlId").combobox("loadData", data);
								} 
							 }
						}); 
						
						$('#fault-panel-${padCode} #idcId').combobox('setValue',"");
						/*
						if(''=='${msgTemplates_msg_last}'){
							$("tr[name=sendMessageTr]").addClass("hide");
						}else{
							$("tr[name=sendMessageTr]").removeClass("hide");
						}
						if(''=='${msgTemplates_weixin_last}'){
							$("tr[name=sendWchartTr]").addClass("hide");
						}else{
							$("tr[name=sendWchartTr]").removeClass("hide");
						}*/
					});
				});
			}
		   
		   
		   
		   /* 更换设备*/
		   var renewa_callback=function(data){
				innerCallback(data, function(){
					
					// 设置面板标题为'处理工单'
					$("#fault-panel-${padCode}").panel("setTitle","移交工单");
					
					// 更换按钮文字
					$("#fault-btn-${padCode}").unbind("click").removeClass("chanage-pad-{padCode}").addClass("fix-faultback").val("确认移交工单");
					
					
				 	$(".fix-faultback").click(function(){
					   $(".fix-form-${padCode}").submit();
					   $.messager.progress({
			                title:'请稍后',
			                msg:'正在更换设备...'
			           });
				 	 });
				 	
				 	$(".fix-form-${padCode}").load(self_host + "fixFormModule?ids="+faultback_id,function(){
				 		
				 		var fixList = $("#fixList").text();
						
						if (jQuery.type(fixList) == 'string') {
							fixList = eval('(' + fixList + ')');
						}
						// 重新渲染重新, 避免easyui组件没有样式问题
						$('#reply-content').textbox({width:274,height:70});
						
						$('#fault-panel-${padCode} #fault-status').combobox({
							onChange:function(newValue,oldValue){
								
								if(newValue==getDictByKey('rf_fault_feedback.feedback_status@handle')||newValue==getDictByKey('rf_fault_feedback.feedback_status@moveyunwei')){
									
									var data = [];
									 var str='';
									 for(i=0;i<fixList.length;i++){
											var classId=fixList[i].classId;
											var calssName=fixList[i].className;
											 data.push({ "classId": classId, "className": calssName });
									}
							        $$("#rwlb").combobox("loadData", data); 
									$$("#smallClassIdTr").removeClass("hide");
									$$("#feedbackHandleTr").removeClass("hide");
								
								}else{	
									$$("#smallClassIdTr").addClass("hide");
									$$("#feedbackHandleTr").addClass("hide");
								}
								
							 }
						});
						
						
						
						$("#fault-panel-${padCode} #fault-status").combobox({  
						       onSelect: function () {  
						           var newPtion = $("#fault-panel-${padCode} #fault-status").combobox('getText')  
						          $("#fault-panel-${padCode} #status-sp").text(newPtion);
						       }  
						   })  
						   
						 $("#fault-panel-${padCode} #rwlb").combobox({  
						       onSelect: function () {  
						           var newPtion = $("#fault-panel-${padCode} #rwlb").combobox('getText')  
						           $("#fault-panel-${padCode} #rwlb-sp").text(newPtion);
						       }  
						   }) 
						
						
						$('#fault-panel-${padCode} #fault-status').combobox('setValue',"3");
						$("#fault-panel-${padCode} #status-sp").text("移交测试");
						
						$("#fault-panel-${padCode} .remove").hide();
						inputToSpan("isSendMessage","#subtab_content_${padCode} .td2",$$("#sendMessageTemplate").val() == 0 ? "不发送" : $$("#sendMessageTemplate").val().replace(/\n/ig,"<br/>"));
						inputToSpan("isSendWechart","#subtab_content_${padCode} .td2",$$("#sendWechartTemplate").val() == 0 ? "不发送" : $$("#sendWechartTemplate").val().replace(/\n/ig,"<br/>"));
				 		
				 	});
					
				 // 关闭进度条
					$.messager.progress('close'); 
				});
			}
		   
		   
		   /* 处理*/
		   var fix_callback=function(data){
			   innerCallback(data, function(){
			   // 关闭进度条
			   $.messager.progress('close');
			   inputToSpan("feedbackStatus","#subtab_content_${padCode} .td2",$("#fault-panel-${padCode} #status-sp").text());
			   inputToSpan("smallClassId","#subtab_content_${padCode} .td2",$("#fault-panel-${padCode} #rwlb-sp").text());
			   inputToSpan("feedbackHandle","#subtab_content_${padCode} .td2");
			   // 隐藏按钮
			   $("#fault-btn-${padCode}").hide();
			   $("#fault-panel-${padCode}").panel("setTitle","完成工单");
			   $.messager.alert('我的消息','您的工单已经成功' + $("#fault-panel-${padCode} #status-sp").text(),'info');
			   });

		   }
		   
		   
		   /* 新建工单提交*/
		   $(".newly-${padCode}").click(function(){
			   $(".newly-form-${padCode}").submit();
			   $.messager.progress({
	                title:'请稍后',
	                msg:'正在新建工单...'
	           });
			  
			   if(typeof($("input[name='classId']").val()) == "undefined" || $("input[name='classId']").val()== ""){
					// 关闭进度条
					$.messager.progress('close');
					$.messager.alert('错误信息','故障类型不能为空!','info');
			   }
		   });
		   
		   
			/* 提交表单*/
		 	$('.newly-form-${padCode}').form({
			    url:host+'saveAndAccept',   
			    success:save_callback,
			    onSubmit:function(){
                    return $('.newly-form-${padCode}').form('validate');
                }
			}); 
			
			$('.change-form-${padCode}').form({
			    url:self_host+'renewalRandom',   
			    success:renewa_callback
			}); 
		 	
		 	$('.fix-form-${padCode}').form({
			    url:'${pageContext.request.contextPath}/fault/follow/fixAndReply',   
			    success:fix_callback
			});  
			
		   
	   });
	   
	   
	   
	  
	   
	   
	   function choiceIsSendMessage(){
			var isSendMessage = $$("#isSendMessage").combobox('getValue');
			$$("tr[name=sendMessageTr]").addClass("hide");
			if(isSendMessage!='0'){
				$$("tr[name=sendMessageTr]").removeClass("hide");
				$$("#sendMessageTemplate").html(isSendMessage);
			}else{
				$$("#sendMessageTemplate").html("");
			}
		}

		function choiceIsSendWechart(){
			var isSendWechart = $$("#isSendWechart").combobox('getValue');
			$$("tr[name=sendWchartTr]").addClass("hide");
			if(isSendWechart!='0'){
				$$("tr[name=sendWchartTr]").removeClass("hide");
				$$("#sendWechartTemplate").html(isSendWechart);
			}else{
				$$("#sendWechartTemplate").html("");
			}
		}
	
	
		function screencap(padCode) {
			var title = "截图";
			$.messager.confirm('确认？', '截图，是否执行该操作?', function(confirm) {
					if (confirm) {
					ajaxPost("pad_screencap", {
						padCode : padCode
					}, callback);
				}}
			);
		}
		
		
		/* 把输入框的内容变成不可编辑*/
		function inputToSpan(inputName,parentSelected, value){
			/* 获得input标签*/
			var input = $(".tabs-panels>.panel:visible").find("input[name='"+inputName+"']");
			
			if(input.size() > 0 ){
				/* 添加文本*/
				input.parents(parentSelected).empty().append("<span>"+(value ? value : ($.trim(input.val()) == '' ? '无':input.val()))+"</span>");
			}
			
		}
		
		/*选择当前选中选项卡下的元素*/
		function $$(selected){
			return $(".tabs-panels>.panel:visible").find(selected);
		}
		
	</script>
	<div id="subtab_content_${padCode}" style="padding: 20px;" class="clearfix">
		<c:if test="${empty pad }">设备编号[${padCode}]无记录</c:if>
		<c:if test="${not empty pad }">
			<div style="margin-bottom: 10px;">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload-rf" plain="false" id="arrange" onclick="fresh_tab('${pad.padCode}')">刷新</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"data-options="iconCls:'icon-star-rf',plain:true" onclick="screencap('${pad.padCode}')">截图</a> 
			</div>
			
			<!-- content warp -->
			<div style="overflow: hidden;">
			
			    <!-- content left -->
				<div style="width: 450px; float: left;">
					<!-- 设备详情  start-->
					<div class="easyui-panel" title="设备详情" style="padding: 10px;margin-bottom: 10px;">
						<table  style="width: 100%;border-collapse:collapse;">
							<tr>
								<td class="td1">设备编号:</td>
								<td class="td2">${pad.padCode}</td>
							</tr>
							<tr>
								<td class="td1">设备等级:</td>
								<td class="td2">${fns:getLabelStyle('rf_user_pad.pad_grade',pad.map.padGrade)}</td>
							</tr>
							<tr>
								<td class="td1">设备名称:</td>
								<td class="td2">${pad.padName}</td>
							</tr>
							<tr>
								<td class="td1">设备类型:</td>
								<td class="td2">${pad.map.classifyName}</td>
							</tr>
							<tr>
								<td class="td1">设备IP:</td>
								<td class="td2">${pad.padIp}</td>
							</tr>
							<tr>
								<td class="td1">机房:</td>
								<td class="td2">${pad.map.idc.idcName}</td>
							</tr>
							<tr>
								<td class="td1">机房地址:</td>
								<td class="td2">${pad.map.idc.idcAddres}</td>
							</tr>
							<tr>
								<td class="td1">设备状态:</td>
								<td class="td2">${fns:getLabelStyle('rf_pad.pad_status',pad.padStatus)}</td>
							</tr>
							<tr>
								<td class="td1">是否可用:</td>
								<td class="td2">${fns:getLabelStyle('global.enable_status',pad.enableStatus)}</td>
							</tr>
							<tr>
								<td class="td1">是否在线:</td>
								<td class="td2">${fns:getLabelStyle('rf_pad.pad_status',pad.userPadStatus)}</td>
							</tr>
							<tr>
								<td class="td1">是否绑定:</td>
								<td class="td2">${fns:getLabelStyle('rf_pad.bind_status',pad.bindStatus)}</td>
							</tr>
							<tr>
								<td class="td1">故障状态:</td>
								<td class="td2">${fns:getLabelStyle('rf_pad.fault_status',pad.faultStatus)}</td>
							</tr>
							<tr>
								<td class="td1">备注:</td>
								<td class="td2">${pad.remark}</td>
							</tr>
						</table>
					</div>
					<!-- 设备详情  end-->
					
					<!-- 绑定用户 start-->
					<div class="easyui-panel" title="绑定用户" style="padding: 10px;">
						<c:if test="${pad.bindStatus=='0' || empty requestScope.user}">此设备尚未绑定！</c:if>
						<c:if test="${pad.bindStatus=='1' && not empty requestScope.user}">
							<table  style="width: 100%;border-collapse:collapse;">
								<tr>
									<td class="td1">绑定电话:</td>
									<td class="td2">${user.userMobilePhone}</td>
								</tr>
								<tr>
									<td class="td1">绑定时间:</td>
									<td class="td2"><fmt:formatDate value="${userPad.bindTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								</tr>
								<tr>
									<td class="td1">用户帐号:</td>
									<td class="td2">${user.userId}</td>
								</tr>
								<tr>
									<td class="td1">会员名称:</td>
									<td class="td2">${user.userName}</td>
								</tr>
								<tr>
									<td class="td1">用户电话:</td>
									<td class="td2">${user.userMobilePhone}</td>
								</tr>
								<tr>
									<td class="td1">最后登录时间:</td>
									<td class="td2"><fmt:formatDate value="${user.loginTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								</tr>
								<tr>
									<td class="td1">登录次数:</td>
									<td class="td2">${user.loginCount}</td>
								</tr>
								<tr>
									<td class="td1">用户设备绑定数:</td>
									<td class="td2">${user.map.bindCount}</td>
								</tr>
								<tr>
									<td class="td1">设备绑定总次数：</td>
									<td class="td2">${user.map.bindAllCount}</td>
								</tr>
							</table>
						</c:if>
					</div>
					<!-- 绑定用户  end-->
					
					<!-- 历史故障 start-->
					<div class="easyui-panel" title="历史故障" style="padding: 10px;"  id="history-fault">
						<c:if test="${fn:length(feedbackList)==0}">此设备暂无历史故障！</c:if>
						<c:if test="${fn:length(feedbackList)>0}">
							此设备历史故障次数：<span class="red">${fn:length(feedbackList)}</span>
							<c:forEach var="one" items="${feedbackList }" varStatus="index">
								<div style="margin-top: 10px;">
								<c:set var="collapsed" value="true"/>
								<c:if test="${index.count==1 }">
									<c:set var="collapsed" value="false"/>
								</c:if>
								<div class="easyui-panel" style="padding: 10px;white-space:nowrap;" title="<div class='history-fault-title' title='[<fmt:formatDate value="${one.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>]&nbsp;&nbsp;&nbsp;&nbsp;${one.map.title }'>[<fmt:formatDate value="${one.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>]&nbsp;&nbsp;&nbsp;&nbsp;${one.map.title }</div>" collapsible="true" collapsed="${collapsed}">
									<table  style="width: 100%;border-collapse:collapse;">
										<tr>
											<td class="td1">故障时间:</td>
											<td class="td2"><fmt:formatDate value="${one.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										</tr>
										<tr>
											<td class="td1">完成时间:</td>
											<td class="td2"><fmt:formatDate value="${one.finishTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										</tr>
										<tr>
											<td class="td1">故障类型:</td>
											<td class="td2">${one.map.className }</td>
										</tr>
										<tr>
											<td class="td1">故障描述:</td>
											<td class="td2">${one.feedbackContent }</td>
										</tr>
										<tr>
											<td class="td1">联系电话:</td>
											<td class="td2">${one.feedbackContact }</td>
										</tr>
										<tr>
											<td class="td1">联系QQ:</td>
											<td class="td2">${one.feedbackQq }</td>
										</tr>
										<tr>
											<td class="td1">故障来源:</td>
											<td class="td2">${fns:getLabel('rf_fault_feedback.feedback_source',one.feedbackSource)}</td>
										</tr>
										<tr>
											<td class="td1">故障创建人:</td>
											<td class="td2">${one.map.promoter }</td>
										</tr>
										<tr>
											<td class="td1">状态:</td>
											<td class="td2">${fns:getLabelStyle('rf_fault_feedback.feedback_status',one.feedbackStatus)}</td>
										</tr>
										<tr>
											<td class="td1">处理类型:</td>
											<td class="td2">${one.map.fixName}</td>
										</tr>
										<tr>
											<td class="td1">处理方式:</td>
											<td class="td2">${one.feedbackHandle }</td>
										</tr>
									</table>
								</div>
								</div>
							</c:forEach>
						</c:if>
					</div>
				    <!-- 历史故障  end-->
				
				</div>
				<!-- content left end-->
				
				<!-- content right start-->
				<div style="width: 650px; float: left;margin-left: 20px;">
					<div id="fault-panel-${padCode}" class="easyui-panel" title="创建工单" style="padding: 10px;">
					
					<c:if test="${pad.bindStatus=='0' || empty requestScope.user}">此设备尚未绑定！</c:if>
						<c:if test="${pad.bindStatus=='1' && not empty requestScope.user}">
						 <!-- 创建工单页面 start -->	
						<form id="module_submit_form" class="easyui-form newly-form-${padCode}" method="post">
						<input type="hidden" name="padCode" value="${padCode}"/>
						<table id="module_submit_table">
						    <%-- 设备故障编号 --%>
							<tr>
								<td class="td1">故障设备编号:</td>
								<td class="td2">${padCode}</td>
							</tr>
							<%-- 故障类型 --%>
							<tr>
								<td class="td1">故障类型:</td>
								<td class="td2"> <input class="easyui-combotree" type="text" name="classId" id="classId" value="${bean.classId }" data-options='required:true,data:${categoryTree},missingMessage:"故障类型不能为空"' ></td>
							</tr>
							<%-- 故障描述 --%>
							<tr>
								<td class="td1">故障描述:</td>
								<td class="td2"><input class="easyui-textbox" name="feedbackContent" value="${bean.feedbackContent }" data-options="required:false,multiline:true,validType:'length[0,200]'" style="height:60px" /></td>
							</tr>
							<%-- 联系电话, 默认为当前用户的联系方式 --%>
							<tr>
								<td class="td1">联系电话:</td>
								<td class="td2"><input class="easyui-textbox" type="text" id="feedbackContact" name="feedbackContact" value="${user.userMobilePhone}"  /></td>
							</tr>
							<%-- 联系QQ--%>
							<tr>
								<td class="td1">联系QQ:</td>
								<td class="td2"><input class="easyui-numberbox" type="text" id="feedbackQq" name="feedbackQq" value="${bean.feedbackQq }"  /></td>
							</tr>
							<%-- 故障来源--%>
							<tr>
								<td class="td1">故障来源:</td>
								<td class="td2">
									<select class="easyui-combobox" id="feedbackSource"  editable="false" data-options="required:true" name="feedbackSource">
										<fns:getOptions category="rf_fault_feedback.feedback_source" keys="rf_fault_feedback.feedback_source@phone,rf_fault_feedback.feedback_source@qq,rf_fault_feedback.feedback_source@weixin"></fns:getOptions>
									</select>
								</td>
							</tr>
							<%-- 备注--%>
							<tr>
								<td class="td1">备注:</td>
								<td class="td2"><input class="easyui-textbox" name="remark" value="${bean.remark }" data-options="multiline:true,validType:'length[0,200]'" style="height:60px" /></td>
							</tr>
							<%-- 是否受理工单, 默认受理--%>
							<tr>
								<td class="td1">受理工单:</td>
								<td class="td2"><div class="checkbox"><input type="checkbox" name="isAccept" checked="checked" value="1"  onclick="this.value=(this.value==0)?1:0" style="margin: 4px 0 0;"></div></td>
							</tr>
							<!-- <tr>
							     <td colspan="2" style="text-align: center;padding:10px 0;"><button type="submit" class="action-button">下一步,更换设备</button></td>
							</tr> -->
							
							
						</table>
		  				</form>
		  				
		  				 <!-- 创建工单页面   end -->	
		  				 
		  				 
		  				 <form class="easyui-form change-form-${padCode}" method="post" >
		  				 </form>
		  				 
		  				 <form class="easyui-form fix-form-${padCode}" method="post" >
		  				 </form>
		  				 <span style="display:none;" id="status-sp"></span>
		  				 <span style="display:none;" id="rwlb-sp"></span>
		  				 
		  				 <div class="btn" style="text-align:center;padding:10px 0;"><input type="button" id="fault-btn-${padCode}" class="action-button newly-${padCode}" value="下一步,更换设备"/></div>
		  				 </c:if>
		   			</div>
						
					
				</div>
			    <!-- content right end-->
			    
			</div>
			<!-- content warp end-->
		</c:if>
	</div>
	
	
	   
	<script type="text/javascript">
		var height = $("#subtab").outerHeight()-72;
		var width = $("#subtab").outerWidth()-62;
		$("#subtab_content_${padCode}").css("height", height).css("width", width);
	</script>
</body>
</html>



