<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>产品</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'save',   
		    success:callback
		});
		
		$.extend($.fn.validatebox.defaults.rules, {  
			selectValueRequired: {
				validator: function(value,param){ 
					return $(param[0]).find("option:contains('"+value+"')").val() != '';  
				},  
				message: '该选项为必选项'  
			}  
		});
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
     <input type="hidden" name="goodsId" value="${bean.goodsId}">
	<table id="module_submit_table">
		<tr>
			<td class="td1">商品编码:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="goodsCode" value="${bean.goodsCode }" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		
		<tr>
			<td class="td1">商品类型:</td>
			<td class="td2">
				<select class="easyui-combobox input_width_short"  editable="false" id="goodsType" name="goodsType" validType="selectValueRequired['#goodsType']">
					<option value="">[全部]</option>
				    <fns:getOptions category="rf_goods.goods_type" value="${bean.goodsType}"></fns:getOptions>
			 	</select>
			</td>
		</tr>
		
		<tr>
			<td class="td1">商品名称:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="goodsName" value="${bean.goodsName }" data-options="required:true,validType:'length[0,255]'" /></td>
		</tr>
		
		<tr>
			<td class="td1">商品价格:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="goodsPrice" value="${bean.goodsPrice}" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		
		<tr>
			<td class="td1">商品展示价格:</td>
			<td class="td2"><input class="easyui-textbox" type="text" name="goodsPriceShow" value="${bean.goodsPriceShow }" data-options="required:true,validType:'length[0,255]'" /></td>
		</tr>
		
		<tr>
			<td class="td1">排序:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="reorder" value="${bean.reorder }" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		<tr>
			<td class="td1">在线天数:</td>
			<td class="td2"><input class="easyui-numberbox" type="text" name="onlineTime" value="${bean.onlineTime }" data-options="required:true,validType:'length[0,32]'" /></td>
		</tr>
		<tr>
			<td class="td1">商品描述:</td>
			<td class="td2"><input class="easyui-textbox" name="goodsDesc" value="${bean.goodsDesc }" data-options="multiline:true,validType:'length[0,500]'" style="height:60px" /></td>
		</tr>
		<tr>
			<td class="td1" valign="top">商品详情介绍:</td>
			<td class="td2">
			<textarea name="goodsDetail" class="easyui-validatebox" style="height:160px;width:280px;" validType="text[1,500]" wrap="hard" required="true">${bean.goodsDetail }</textarea>
			</td>
		</tr>
	</table>
    </form>
    </div>
</body>
</html>



