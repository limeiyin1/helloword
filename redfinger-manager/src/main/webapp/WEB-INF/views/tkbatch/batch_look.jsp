<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>批处理任务详情</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script>
	<script type="text/javascript">
	//下载原文件
	function downloadFile(filePath,fileName){
		var downloadUrl = host+'downloadBatchFile?filePath='+filePath+"&fileName="+fileName;
		window.open(downloadUrl);
	};
	</script>

	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
    <input type="hidden" name="batchId" value="${bean.batchId}">
	<table id="module_submit_table" class="easyui-table">
		<tr>
			<td class="td1">批次标题:</td>
			<td class="td2"> ${bean.batchTitle}</td>
		</tr>
		<tr>
			<td class="td1"> 操作类型:</td>
			<td class="td2">
				${fns:getLabel('tk_batch_task.operate_type',bean.operateType)}
			</td>
		</tr>
		<tr>
			<td class="td1"> 操作状态:</td>
			<td class="td2">
				${fns:getLabelStyle('tk_batch_task.operate_status',bean.operateStatus)}
			</td>
		</tr>
		<tr>
			<td class="td1"> 执行总数:</td>
			<td class="td2">
				${bean.totalNumber}
			</td>
		</tr>
		<tr>
			<td class="td1"> 成功数量:</td>
			<td class="td2">
				${bean.successNumber}
			</td>
		</tr>
		<tr>
			<td class="td1"> 失败数量:</td>
			<td class="td2">
				${bean.failNumber}
			</td>
		</tr>
		<tr>
			<td class="td1"> 状态:</td>
			<td class="td2">
				<c:if test="${bean.enableStatus==1}">正常</c:if>
				<c:if test="${bean.enableStatus==0}">停用</c:if>
			</td>
		</tr>
		
		<!-- 用户和设备公告 start-->
		<tr id="sendPasNoticeTypeTr" class="${bean.operateType == '5' ? 'show' : 'hide'}">
			<td class="td1"> 发送方式：</td>
			<td class="td2">
					<c:if test="${sendPasNoticeType == 2}">按设备编码excel发送</c:if>
					<c:if test="${sendPasNoticeType == 1}">按机房发送</c:if>
					<c:if test="${sendPasNoticeType == 3}">按编码段发送</c:if>
			</td>
		</tr>
		<tr id="padNoticeIdcIdTr" class="${bean.operateType=='5' && sendPasNoticeType == 1 ? 'show' : 'hide'}">
			<td class="td1"> 选择机房：</td>
			<td class="td2">
					<c:if test='${not empty idcList}'>
						<c:forEach var="item" items="${idcList}">
							<c:if test="${padNoticeIdcId == item.idcId}">${item.idcName}</c:if>
					    </c:forEach>
					</c:if>
			</td>
		</tr>
		<tr id='padNoticePadCodeTr' class="${bean.operateType=='5' && sendPasNoticeType == 3 ? 'show' : 'hide'}">
			<td class="td1">设备编码段:</td>
			<td class="td2">
				${padNoticePadCodeGt }至
				${padNoticePadCodeLt }
			</td>
		</tr>
		<tr id='padNoticeTitleTr' class="${bean.operateType=='5' ? 'show' : 'hide'}">
			<td class="td1" valign="top">设备公告标题:</td>
			<td class="td2">
			${padNoticeTitle }
			</td>
		</tr>
		<tr id='userNoticeTitleTr' class="${bean.operateType == '4' ? 'show' : 'hide'}">
			<td class="td1" valign="top">用户公告标题:</td>
			<td class="td2">
				${userNoticeTitle }
			</td>
		</tr>
		<tr id='padNoticeContentTr' class="${bean.operateType=='5' ? 'show' : 'hide'}">
			<td class="td1" valign="top">设备公告内容:</td>
			<td class="td2">
				${padNoticeContent}
			</td>
		</tr>
		<tr id='userNoticeContentTr' class="${bean.operateType == '4' ? 'show' : 'hide'}">
			<td class="td1" valign="top">用户公告内容:</td>
			<td class="td2">
				${userNoticeContent}
			</td>
		</tr>
		<tr id='oneNoticeStatusTr' class="${bean.operateType == '5' ? 'show' : 'hide'}">
			<td class="td1" valign="top">是否按用户合并发送:</td>
			<td class="td2">
				<c:if test="${oneNoticeStatus == 1}">是</c:if>
				<c:if test="${oneNoticeStatus == 0}">否</c:if>
			</td>
		</tr>		
		<tr id='popStatus' class="${bean.operateType == '4' || bean.operateType == '5' ? 'show' : 'hide'}">
			<td class="td1" valign="top">是否强制弹出:</td>
			<td class="td2">
				<c:if test="${noticePopstatus == 1}">强制弹出</c:if>
				<c:if test="${noticePopstatus == 0 || noticePopstatus == null}">不强行弹出</c:if>
			</td>
		</tr>
		<tr id='popExpiredTime' class="${bean.operateType == '4' ? 'show' : 'hide'}">
			<td class="td1">弹出有效时间:</td>
			<td class="td2">
				 ${noticePopexpired }
			</td>
		</tr>
		<!-- 用户和设备公告 end-->
		<!-- 发送设备消息-start -->
		<tr id="sendPasMsgTypeTr" class="${bean.operateType == '25' ? 'show' : 'hide'}">
			<td class="td1"> 发送方式：</td>
			<td class="td2">
					<c:if test="${sendPadMsgType == 2}">按设备编码excel发送</c:if>
					<c:if test="${sendPadMsgType == 1 }">按机房发送</c:if>
					<c:if test="${sendPadMsgType == 3 }">按设备编码段发送</c:if>
			</td>
		</tr>
		<tr id="padMsgIdcIdTr" class="${bean.operateType=='25' && sendPadMsgType == 1 ? 'show' : 'hide'}">
			<td class="td1"> 选择机房：</td>
			<td class="td2">
				<c:forEach var="item" items="${idcList}">
					<c:if test="${padMsgIdcId == item.idcId}">${item.idcName}</c:if>
			    </c:forEach>
			</td>
		</tr>
		<tr id='padMsgPadCodeTr' class="${bean.operateType=='25' && sendPadMsgType == 3 ? 'show' : 'hide'}">
			<td class="td1">设备编码段:</td>
			<td class="td2">
				${padMsgPadCodeGt }至
				${padMsgPadCodeLt }
			</td>
		</tr>
		<tr id='padMsgTitleTr' class="${bean.operateType == '25' ? 'show' : 'hide'}">
			<td class="td1" valign="top">设备通知标题:</td>
			<td class="td2">
				${padMsgTitle }
			</td>
		</tr>
		<tr id='padMsgContentTr' class="${bean.operateType == '25' ? 'show' : 'hide'}">
			<td class="td1" valign="top">设备通知内容:</td>
			<td class="td2">
				${padMsgContent}
			</td>
		</tr>
		<tr id='sendPadWchartTr' class="${bean.operateType == '25' ? 'show' : 'hide'}">
			<td class="td1" valign="top">微信内容:</td>
			<td class="td2">
				${padWeixinContent}
			</td>
		</tr>
		<tr id='oneMsgStatusTr' class="${bean.operateType == '25' ? 'show' : 'hide'}">
			<td class="td1" valign="top">是否按用户合并发送:</td>
			<td class="td2">
				<c:if test="${oneMsgStatus == 1}">是</c:if>
				<c:if test="${oneMsgStatus == 0}">否</c:if>
			</td>
		</tr>		
		<!-- 发送设备消息 end-->
		
		<!-- 更换设备 start-->
		<tr class="${bean.operateType == '3' ? 'show' : 'hide'}" id='isSendMessageChoice'>
			<td class="td1">是否发送消息:</td>
			<td class="td2">
				<c:if test="${isSendMessage == 0 }">否</c:if>
				<c:if test="${isSendMessage == 1 }">是</c:if>
			</td>
		</tr>
		<tr name="sendMessageTr" class="${bean.operateType == '3' && isSendMessage != 0 ? 'show' : 'hide'}">
			<td class="td2"></td>
			<td class="td2">
				${sendMessageTemplate }
			</td>
		</tr>
		<%-- <tr name="sendMessageTr" class="${bean.operateType == '3' && isSendMessage != 0 ? 'show' : 'hide'}">
			<td class="td2"></td>
			<td class="td2">
			XXX为需要替换成设备名称,最好保留,不然消息看不到设备名称
			</td>
		</tr> --%>
		<tr class="${bean.operateType == '3' ? 'show' : 'hide'}" id='isSendWechartChoice'>
			<td class="td1">是否发送微信:</td>
			<td class="td2">
				<c:if test="${isSendWechart == 0 }">否</c:if>
				<c:if test="${isSendWechart == 1 }">是</c:if>
			</td>
		</tr>
		<tr name="sendWchartTr" class="${bean.operateType == '3' && isSendWechart != 0 ? 'show' : 'hide'}">
			<td class="td2"></td>
			<td class="td2">
				${sendWechartTemplate }
			</td>
		</tr>
	    <tr id="reNewPadIdcIdTr" class="${bean.operateType=='3' ? 'show' : 'hide'}">
			<td class="td1"> 机房：</td>
			<td class="td2">
				<c:if test='${not empty idcList}'>
					<c:forEach var="item" items="${idcList}">
						<c:if test="${reNewPadIdcId == item.idcId}">${item.idcName}</c:if>
				    </c:forEach>
				</c:if>
			</td>
		</tr>
	    <tr id='controlTr' class="${bean.operateType=='3' ? 'show' : 'hide'}">
			<td class="td1">设备节点:</td>
			<td class="td2">
				<c:forEach var="item" items="${controlList}">
						<c:if test="${padControlId == item.controlId}">${item.controlName}</c:if>
				</c:forEach>
			 </td>
		</tr>
		<tr id='padCodeInTr' class="${bean.operateType=='3' ? 'show' : 'hide'}">
			<td class="td1">设备编码段:</td>
			<td class="td2">
				${padCodeGt } 至  ${padCodeLt }
			</td>
		</tr>
		<tr id='deviceIpTr' class="${bean.operateType=='3' ? 'show' : 'hide'}">
			<td class="td1">物理IP:</td>
			<td class="td2">
				${deviceIpGT }至 ${deviceIpLT }
			</td>
		</tr>
       <!-- 更换设备 end-->
       
       <!-- 增加设备时间 start -->
        <tr id='timeType' class="${bean.operateType == '8' ? 'show' : 'hide'}" >
			<td class="td1">时间类型:</td>
			<td class="td2">
				<c:if test="${timeType =='day' }">天</c:if>
				<c:if test="${timeType =='hour' }">小时</c:if>
			</td>
		</tr>
		<tr id='controltime' class="${bean.operateType=='8' ?'show':'hide'}" >
			<td class="td1">调整在线时间:</td>
			<td class="td2">${controltime }</td>
		</tr>
		<!-- 增加设备时间 end -->
		
		<!-- 按用户修改红豆 start -->
		<tr id="userRbcTypeTr" class="${bean.operateType == '6' ? 'show' : 'hide'}">
			<td class="td1"> 赠送方式：</td>
			<td class="td2">
					<c:if test="${userRbcType == 1 }">按用户账号</c:if>
					<c:if test="${userRbcType == 2 }">按用户ID</c:if>
			</td>
		</tr>
		<tr id='rbcGet' class="${bean.operateType == '6' ? 'show' : 'hide'}">
			<td class="td1">调整红豆:</td>
			<td class="td2">${rbcGet }</td>
		</tr>
		<!-- 按用户修改红豆 end -->
		
		<!-- 按设备赠送红豆 start-->
		<tr id="giveRbcType" class="${bean.operateType == '7' ? 'show' : 'hide'}">
			<td class="td1"> 赠送方式：</td>
			<td class="td2">
				<c:if test="${giveRbcType == 1 }">按机房赠送</c:if>
				<c:if test="${giveRbcType == 2 }">按设备编码excel赠送</c:if>
				<c:if test="${giveRbcType == 3 }">按编码段赠送</c:if>
			</td>
		</tr>
		<tr id='padRbcPadCodeTr' class="${bean.operateType=='7'  && giveRbcType == '3' ? 'show' : 'hide'}">
			<td class="td1">设备编码段(至少填一项):</td>
			<td class="td2">
				${padRbcPadCodeGt }至
				${padRbcPadCodeLt }
			</td>
		</tr>
		<tr id="idcId" class="${bean.operateType=='7' && giveRbcType == '1' ?'show':'hide'}">
			<td class="td1"> 选择机房：</td>
			<td class="td2">
				<c:if test='${not empty idcList}'>
					<c:forEach var="item" items="${idcList}">
						<c:if test="${idcId == item.idcId}">${item.idcName}</c:if>
				    </c:forEach>
				</c:if>
			</td>
		</tr>
		<tr id='commonRbc' class="${bean.operateType == '7' ? 'show' : 'hide'}">
			<td class="td1">普通设备:</td>
			<td class="td2">${commonRbc }</td>
		</tr>
		
		<tr id='vipRbc' class="${bean.operateType == '7' ? 'show' : 'hide'}">
			<td class="td1">VIP设备:</td>
			<td class="td2">${vipRbc }</td>
		</tr>
		<tr id='gvipRbc' class="${bean.operateType == '7' ? 'show' : 'hide'}">
			<td class="td1">GVIP设备:</td>
			<td class="td2">${gvipRbc }</td>
		</tr>
		<tr id='svipRbc' class="${bean.operateType == '7' ? 'show' : 'hide'}">
			<td class="td1">SVIP设备:</td>
			<td class="td2">${svipRbc }</td>
		</tr>
		<!-- 按设备赠送红豆 end-->
		
		<!-- 批量绑定设备 start-->
		<tr id="padClassifyTr" class="${bean.operateType == '22' ? 'show' : 'hide'}">
			<td class="td1">设备类型：</td>
			<td class="td2">
				${fns:getLabel('rf_pad.pad_classify',padClassify)}
		     </td>
		</tr>
		<tr id="goodsId" class="${bean.operateType == '22' && (padClassify != '3'&&padClassify != '2'&&padClassify != '5') ? 'show' : 'hide'}">
			<td class="td1">VIP套餐：</td>
			<td class="td2">
				<c:if test="${goodsId == -1}">[普通]</c:if>
				<c:forEach var="one" items="${goods}">
					<c:if test="${goodsId == one.goodsId}">${one.goodsName }</c:if>
				</c:forEach>
		     </td>
		</tr>
		<tr id="gvipGoodsIdTr" class="${bean.operateType == '22' && padClassify == '3' ? 'show' : 'hide'}">
			<td class="td1">GVIP套餐：</td>
			<td class="td2">
					<c:forEach var="one" items="${gvipGoods}">
						<c:if test="${gvipGoodsId == one.goodsId}">${one.goodsName }</c:if>
					</c:forEach>
		     </td>
		</tr>
		<tr id="gamePadTimeTr" class="${bean.operateType == '22' && padClassify == '2' ? 'show' : 'hide'}">
			<td class="td1">绑定设备时长(单位:小时)：</td>
			<td class="td2">${gamePadTime }</td>
		</tr>
		<tr id="cloudPadTimeTr" class="${bean.operateType == '22' && padClassify == '5' ? 'show' : 'hide'}">
			<td class="td1">绑定设备时长(单位:小时)：</td>
			<td class="td2">${cloudPadTime }</td>
		</tr>
		<!-- 批量绑定设备 end-->
		<tr id='fileUploade' class="${giveRbcType eq '1' ? 'hide' : 'show'}">
			<td class="td1" style="width:10px">上传文件:</td>
			<td class="td2">
	   			<a href="${filePath}" class="${filePath != null ? 'show' : 'hide'}" target="_blank">${fileName }.xlsx</a>
			</td>
		</tr>
		<!-- 启用或禁用设备 -->
		<tr id="enablePadTypeTr" class="${bean.operateType == '23' || bean.operateType == '24' ? 'show' : 'hide'}">
			<td class="td1">设备启用或禁用方式：</td>
			<td class="td2">
					<c:if test="${enablePadType == 1 }">按设备编码excel</c:if>
					<c:if test="${enablePadType == 2 }">按条件</c:if>
					<c:if test="${enablePadType == 3 }">全部设备</c:if>
				</select> 
			</td>
		</tr>
		<tr id="enablePadIdcIdTr" class="${(bean.operateType == '23' || bean.operateType == '24') && enablePadType == '2' ? 'show' : 'hide'}">
			<td class="td1">选择机房：</td>
			<td class="td2">
				<c:forEach var="item" items="${idcList}">
					<c:if test="${enablePadIdcId == item.idcId}">${item.idcName}</c:if>
			    </c:forEach>
			</td>
		</tr>
		
	    <tr id='enablePadControlTr' class="${(bean.operateType == '23' || bean.operateType == '24') && enablePadType == '2' ? 'show' : 'hide'}">
			<td class="td1">设备节点:</td>
			<td class="td2">
					<c:forEach var="item" items="${controlList}">
						<c:if test="${enablePadControlId == item.controlId}">${item.controlName}</c:if>
					</c:forEach>
			 </td>
		</tr>
		
		<tr id="enablePadStatusTr" class="${(bean.operateType == '23' || bean.operateType == '24') && enablePadType == '2' ? 'show' : 'hide'}">
			<td class="td1">启用：</td>
			<td class="td2">
				${fns:getLabel('rf_pad.enable_status',enablePadStatus)}
			</td>
		</tr>

		<tr id="enablePadBindStatusTr" class="${(bean.operateType == '23' || bean.operateType == '24') && enablePadType == '2' ? 'show' : 'hide'}">
			<td class="td1">绑定：</td>
			<td class="td2">
				${fns:getLabel('rf_pad.bind_status',enablePadBindStatus)}
			</td>
		</tr>			
		<tr id="enablePadFaultStatusTr" class="${(bean.operateType == '23' || bean.operateType == '24') && enablePadType == '2' ? 'show' : 'hide'}">
			<td class="td1">故障：</td>
			<td class="td2">
				${fns:getLabel('rf_pad.fault_status',enablePadFaultStatus)}
			</td>
		</tr>		
		
		<tr id='enablePadCodeInTr' class="${(bean.operateType == '23' || bean.operateType == '24') && enablePadType == '2' ? 'show' : 'hide'}">
			<td class="td1">选择设备编码段:</td>
			<td class="td2">
				${enablePadCodeGt }至
				${enablePadCodeLt }
			</td>
		</tr>
		<tr id='enablePadDeviceIpTr' class="${(bean.operateType == '23' || bean.operateType == '24')  && enablePadType == '2' ? 'show' : 'hide'}">
			<td class="td1">选择物理ip段:</td>
			<td class="td2">
				${enablePadDeviceIpGt }至
				${enablePadDeviceIpLt }
			</td>
		</tr>
		<tr>
			<td class="td1">备注:</td>
			<td class="td2">${bean.remark }</td>
		</tr>
		<tr>
			<td class="td1">优先级：</td>
			<td class="td2">
				${bean.priority}
			</td>
		</tr>
		<tr>
			<td class="td1" style="width:20px">失败是否继续:</td>
			<td class="td2">
				<c:if test="${bean.failContinue == 1 || bean.failContinue == null }">继续</c:if>
				<c:if test="${bean.failContinue == 0 }">停止</c:if>
			</td>
		</tr>
		<tr>
			<td class="td1"> 创建时间:</td>
			<td class="td2">
				<fmt:formatDate value="${bean.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>
		</tr>
		<tr>
			<td class="td1">执行开始时间:</td>
			<td class="td2">
				<fmt:formatDate value="${bean.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>
		</tr>
		<tr>
			<td class="td1">执行结束时间:</td>
			<td class="td2">
				<fmt:formatDate value="${bean.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>
		</tr>
		<tr>
			<td class="td1">执行记录下载:</td>
			<td class="td2">
				<c:if test="${bean.exportUrl != null }">
					<a href="${bean.exportUrl}" target="_blank">下载</a>
				</c:if>
				<c:if test="${bean.exportUrl == null }">暂未生成文件</c:if>
			</td>
		</tr>
		<tr>
			<td class="td1"> 备注:</td>
			<td class="td2">
				${bean.remark }
			</td>
		</tr>
	</table>
    </form>
    </div>
</body>
</html>