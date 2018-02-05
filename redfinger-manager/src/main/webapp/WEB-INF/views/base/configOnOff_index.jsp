<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>

<html>
<head>
<title>消息开关</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	$(function() {
		$('.easyui-form').form({
			url : host + 'update',
			success : configCallback
		});
	});
</script>

<style type="text/css">
   #module_submit_form .td1,#module_submit_form .td2{padding:3px 10px;}
</style>
</head>
<body>
	<div id="module_submit_container"  align="center">
		<form id="module_submit_form" class="easyui-form" method="post">
			<table class="easyui-table" style="width:auto; white-space: nowrap;" >
				<%-- <tr>
					<td>发送微信公告开关:</td>
					<td><select class="easyui-combobox input_width_default"
						editable="false" name="map[send_wechart_on_off]">
							<option value="">[请选择]</option>
							<fns:getOptions category="global.switch_status" value="${send_wechart_on_off}"/>
					</select>
				   </td>
				</tr>
				<tr>
					<td>设备到期微信开关:</td>
					<td><select class="easyui-combobox input_width_default"
						editable="false" name="map[weixin_pad_expire_on_off]">
							<option value="">[请选择]</option>
							<fns:getOptions category="global.switch_status"  value="${weixin_pad_expire_on_off}"/>
					</select>
				   </td>
				</tr> --%>
				<%-- <tr>
					<td>游戏掉线微信开关:</td>
					<td><select class="easyui-combobox input_width_default"
						editable="false" name="map[weixin_game_drop_on_off]">
							<option value="">[请选择]</option>
							<fns:getOptions category="global.switch_status"  value="${weixin_game_drop_on_off}"/>
					</select>
				   </td>
				</tr> --%>
				<%-- <tr>
					<td>激活码到期的消息开关:</td>
					<td><select class="easyui-combobox input_width_default"
						editable="false" name="map[message_activation_expire_on_off]">
							<option value="">[请选择]</option>
							<fns:getOptions category="global.switch_status"  value="${message_activation_expire_on_off}"/>
					</select>
				   </td>
				</tr>
				<tr>
					<td>优惠劵到期 的消息开关:</td>
					<td><select class="easyui-combobox input_width_default"
						editable="false" name="map[message_coupon_expire_on_off]">
							<option value="">[请选择]</option>
							<fns:getOptions category="global.switch_status"  value="${message_coupon_expire_on_off}"/>
					</select>
				   </td>
				</tr>
				<tr>
					<td>游戏礼包到期 的消息开关:</td>
					<td><select class="easyui-combobox input_width_default"
						editable="false" name="map[message_gift_expire_on_off]">
							<option value="">[请选择]</option>
							<fns:getOptions category="global.switch_status"  value="${message_gift_expire_on_off}"/>
					</select>
				   </td>
				</tr>
				<tr>
					<td>设备授权到期的消息开关:</td>
					<td><select class="easyui-combobox input_width_default"
						editable="false" name="map[message_pad_grant_expire_on_off]">
							<option value="">[请选择]</option>
							<fns:getOptions category="global.switch_status"  value="${message_pad_grant_expire_on_off}"/>
					</select>
				   </td>
				</tr>
				<tr>
					<td>取消设备授权 的消息开关:</td>
					<td><select class="easyui-combobox input_width_default"
						editable="false" name="map[message_pad_grant_cancel_on_off]">
							<option value="">[请选择]</option>
							<fns:getOptions category="global.switch_status"  value="${message_pad_grant_cancel_on_off}"/>
					</select>
				   </td>
				</tr>
				<tr>
					<td>购买或者充值的消息开关:</td>
					<td><select class="easyui-combobox input_width_default"
						editable="false" name="map[message_buy_excharge_cancel_on_off]">
							<option value="">[请选择]</option>
							<fns:getOptions category="global.switch_status"  value="${message_buy_excharge_cancel_on_off}"/>
					</select>
				   </td>
				</tr>
				<tr>
					<td>设备过期的消息开关:</td>
					<td><select class="easyui-combobox input_width_default"
						editable="false" name="map[message_pad_expire_on_off]">
							<option value="">[请选择]</option>
							<fns:getOptions category="global.switch_status"  value="${message_pad_expire_on_off}"/>
					</select>
				   </td>
				</tr>
				
				<tr>
					<td>应用崩溃入库开关:</td>
					<td><select class="easyui-combobox input_width_default"
						editable="false" name="map[config_appcrash_weixinremind_touser]">
							<option value="">[请选择]</option>
							<fns:getOptions category="global.switch_status"  value="${config_appcrash_weixinremind_touser}"/>
					</select>
				   </td>
				</tr>
				
				<tr>
					<td>应用崩溃发送微信开关:</td>
					<td><select class="easyui-combobox input_width_default"
						editable="false" name="map[config_appcrash_weixinremind_todb]">
							<option value="">[请选择]</option>
							<fns:getOptions category="global.switch_status"  value="${config_appcrash_weixinremind_todb}"/>
					</select>
				   </td>
				</tr>
				
				<tr>
					<td>游戏离线入库开关:</td>
					<td><select class="easyui-combobox input_width_default"
						editable="false" name="map[config_offline_weixinremind_todb]">
							<option value="">[请选择]</option>
							<fns:getOptions category="global.switch_status"  value="${config_offline_weixinremind_todb}"/>
					</select>
				   </td>
				</tr>
				
				<tr>
					<td>游戏离线发送微信开关:</td>
					<td><select class="easyui-combobox input_width_default"
						editable="false" name="map[config_offline_weixinremind_touser]">
							<option value="">[请选择]</option>
							<fns:getOptions category="global.switch_status"  value="${config_offline_weixinremind_touser}"/>
					</select>
				   </td>
				</tr> --%>
				<fns:getConfigDictTr code="send_wechart_on_off" category="global.switch_status" width="284" disabled="false" />
				<fns:getConfigDictTr code="weixin_pad_expire_on_off" category="global.switch_status" width="284"  disabled="false" />
				<fns:getConfigDictTr code="message_activation_expire_on_off" category="global.switch_status" width="284"  disabled="false" />
				<fns:getConfigDictTr code="message_coupon_expire_on_off" category="global.switch_status" width="284"  disabled="false" />
				<fns:getConfigDictTr code="message_gift_expire_on_off" category="global.switch_status" width="284"  disabled="false" />
				<fns:getConfigDictTr code="message_pad_grant_expire_on_off" category="global.switch_status" width="284"  disabled="false" />
				<fns:getConfigDictTr code="message_pad_grant_cancel_on_off" category="global.switch_status" width="284"  disabled="false" />
				<fns:getConfigDictTr code="message_buy_excharge_cancel_on_off" category="global.switch_status" width="284"  disabled="false" />
				<fns:getConfigDictTr code="message_pad_expire_on_off" category="global.switch_status" width="284"  disabled="false" />
				
				<fns:getConfigDictTr code="config_appcrash_weixinremind_todb" category="global.switch_status" codeName="应用崩溃入库开关" width="284"  disabled="false" />
				<fns:getConfigDictTr code="config_appcrash_weixinremind_touser" category="global.switch_status" codeName="应用崩溃发送微信开关" width="284"  disabled="false" />
				<fns:getConfigDictTr code="config_offline_weixinremind_todb" category="global.switch_status" codeName="游戏离线入库开关" width="284"  disabled="false" />
				<fns:getConfigDictTr code="config_offline_weixinremind_touser" category="global.switch_status" codeName="游戏离线发送微信开关"  width="284"  disabled="false" /> 
				<fns:getConfigTr code="config_appcrash_weixinremind_touser_timegt" number="true" dataOptions="min:0,max:9999"/>
				<fns:getConfigTr code="config_appcrash_weixinremind_touser_timelt" number="true" dataOptions="min:0,max:9999"/>
				<fns:getConfigTr code="config_weixinremind_fail_resend_time_out" number="true" dataOptions="min:0,max:9999"/>
				<%-- <fns:getConfigTr code="send_wechart_on_off"/><!-- 发送微信公告开关 -->
				<fns:getConfigTr code="weixin_pad_expire_on_off"/><!-- 设备到期微信开关 -->
				<fns:getTwoInOne code="weixin_pad_exchange_on_off"/>
				<fns:getConfigTr code="weixin_game_drop_on_off"/><!-- 游戏掉线微信开关-->
				<fns:getConfigTr code="message_activation_expire_on_off"/><!-- 激活码到期的消息开关 -->
				<fns:getConfigTr code="message_coupon_expire_on_off"/><!-- 优惠劵到期 的消息开关-->
				<fns:getConfigTr code="message_gift_expire_on_off"/><!-- 游戏礼包到期 的消息开关-->
				<fns:getConfigTr code="message_pad_grant_expire_on_off"/><!-- 设备授权到期的消息开关 -->
				<fns:getConfigTr code="message_pad_grant_cancel_on_off"/><!-- 取消设备授权 的消息开关-->
				<fns:getConfigTr code="message_buy_excharge_cancel_on_off"/><!-- 购买或者充值的消息开关 -->
				<fns:getConfigTr code="message_pad_expire_on_off"/><!-- 设备过期的消息开关 --> --%>
	        	<tr>
		         	<td class="td2" colspan="2" align="center"> 
		         	<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-ok-rf" plain="false" onclick="submitForm()">保存</a>
		         	<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-undo" plain="false" onclick="resetForm()">重置</a>
		         	</td>
		        </tr>
			</table>
		</form>
	</div>
</body>


</html>