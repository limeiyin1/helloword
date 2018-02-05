<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>游戏礼包</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
			url : host + 'save',
			success : callback
		});
		var chanage=function(newValue, oldValue){
			if (newValue == "game" || newValue == "redfinger") {
				$('#parameter').hide();
				$('#oldprice').hide();
				$('#price').find('.td1').html("礼包价格");
				$('#originalPrice').numberbox('setValue', -1);
				$('#myparameter').numberbox('setValue', -1);

			}
			if (newValue == "discount" ) {
				$('#parameter').hide();
				$('#oldprice').show();
				$('#price').find('.td1').html("礼包现价");
				$('#originalPrice').numberbox('setValue', "");
				$('#myparameter').numberbox('setValue', -1);

			}
		 	if (newValue == "limitedEdition" ) {
		 		$('#parameter').show();
				$('#oldprice').show();
				$('#price').find('.td1').html("礼包现价");
				$('#myparameter').numberbox('setValue', 0);
				$('#originalPrice').numberbox('setValue', "");
				
			} 
		
		};
		   var a=$('#sel').val();
		   if(a=="game"|| a=="redfinger"){
				$('#oldprice').hide();
				$('#originalPrice').val(-1);
				$('#price').find('.td1').html("礼包价格");
			} 
		  if(a!="limitedEdition"){
			  $('#parameter').hide();
			  $('#myparameter').val(-1);
		  }
	</script>
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post">
			<input type="hidden" name="id" value="${bean.id}">
			<table id="module_submit_table">

				<tr>
					<td class="td1">礼包类型:</td>
					<td class="td2"><select id="sel" class="easyui-combobox"
						name="category" data-options="required:true,onChange:chanage">
						<fns:getOptions category="shop_package.category" value="${bean.category}"></fns:getOptions>
						</select> 			
					</td>
				</tr>
				<tr>
					<td class="td1">礼包名称:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="name" value="${bean.name }"
						data-options="required:true,validType:'length[0,12]'" /></td>
				</tr>
				<tr>
					<td class="td1">礼包icon:</td>
					<td class="td2"><input class="easyui-textbox" name="icon"
						value="${bean.icon }" data-options="required:true" /></td>
				</tr>
				<tr>
					<td class="td1">注*</td>
					<td class="td2">图片地址错误将导致红豆商城崩溃<br>请慎重输入！</td>
				</tr>
				<tr>
					<td class="td1">礼包详情图片480p:</td>
					<td class="td2"><input class="easyui-textbox" name="image480"
						value="${bean.image480 }" data-options="required:true" /></td>
				</tr>
				<tr>
					<td class="td1">礼包详情图片720p:</td>
					<td class="td2"><input class="easyui-textbox" name="image720"
						value="${bean.image720 }" data-options="required:true" /></td>
				</tr>
				<tr>
					<td class="td1">礼包详情图片1080p:</td>
					<td class="td2"><input class="easyui-textbox" name="image1080"
						value="${bean.image1080 }" data-options="required:true" /></td>
				</tr>
				<tr id="price">
					<td class="td1">礼包现价:</td>
					<td class="td2"><input class="easyui-numberbox" type="number"
						name="price" value="${bean.price }"
						data-options="required:true,validType:'length[0,32]'" /></td>
				</tr>
				<tr id="oldprice">
					<td class="td1">礼包原价:</td>
					<td class="td2"><input class="easyui-numberbox" type="number"
						id="originalPrice" name="originalPrice"
						value="${bean.originalPrice }"
						data-options="required:true,validType:'length[0,32]'" /></td>
				</tr>
				<tr>
					<td class="td1">开始时间:</td>
					<td class="td2"><input
						class="easyui-datebox input_width_default" type="text"
						 name="beginTimeStr"
						value="<fmt:formatDate value="${bean.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						data-options=" required:true"/></td>
				</tr>
				<tr>
					<td class="td1">结束时间:</td>
					<td class="td2"><input
						class="easyui-datebox input_width_default" type="text"
						 name="endTimeStr"
						value="<fmt:formatDate value="${bean.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						data-options="required:true" /></td>
				</tr>
				<tr>
					<td class="td1">礼包内容:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="content" value="${bean.content }"
						data-options="required:true"  /></td>
				</tr>
				<tr id="parameter">
					<td class="td1">礼包限量数:</td>
					<td class="td2"><input class="easyui-numberbox" type="number" id="myparameter" name="parameter"
						value="${bean.parameter }"
						data-options="required:true,validType:'length[0,32]'" /></td>
				</tr>
				<%-- <tr>
					<td class="td1">礼包使用说明:</td>
					<td class="td2"><input class="easyui-textbox" type="text"
						name="remark" value="${bean.remark }" data-options="required:true,multiline:true" style="height:100px" /></td>
				</tr> --%>
				<tr>
					<td class="td1">礼包使用说明:</td>
					<td class="td2"><textarea name="remark" wrap="virtual" style="width: 280px;height: 100px" >${bean.remark }</textarea></td>
				</tr>
				<tr>
					<td class="td1">排序:</td>
					<td class="td2"><input class="easyui-numberbox" type="text"
						name="reorder" value="${bean.reorder }"
						data-options="required:true,validType:'length[0,32]'" /></td>
				</tr>

			</table>
		</form>
	</div>
</body>
</html>



