<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>批量受理</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'batchAccept',   
		    success:batchAcceptCallback
		});
		
		function countPads(message,pads) {
			var padlist;
			padlist= message.value.split("\n").length;
			pads.value=padlist;
		}
		
		function batchAcceptCallback(data){
			var servetlData;
			if (jQuery.type(data) == 'string') {
				servetlData = eval('(' + data + ')');
			}
			if(!servetlData.code){
				var length = Object.keys(servetlData).length;
				
				if(length > 0){
					$.messager.progress('close');
					$(".errormsg").empty();
					var i = 1;
					for ( var key in servetlData) {  
			            //通过遍历对象属性的方法，遍历键值对，获得key，然后通过 对象[key]获得对应的值  
			            name = key;  
			            value = servetlData[key];  
			            $(".errorTr").removeClass("hide");
			            $(".errormsg").append("<span style='font-size:13px;color:red'>" + i + ".&nbsp;" + name + ":" + value + "</span><br/>")
			            i++;
					} 	
					$("#button-save").unbind("click").click(cancel);
				}else{
					defaultCallback({"code":"200"})
				}
				
			}else{
				defaultCallback(data);
			}
			
			
		}
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">

	<table id="module_submit_table">
		
	    <tr>
			<td class="td1">设备清单:</td>
			<td class="td2"><fieldset>
						<legend>请输入内容</legend>
						<textarea   name="padCodes" wrap=PHYSICAL style="width: 220px;height: 200px" onKeyDown="countPads(this.form.padCodes,this.form.pads);"
						onKeyUp="countPads(this.form.padCodes,this.form.pads);"></textarea>
		                </fieldset>
		    </td>
		</tr>
		 <tr>
		    <td class="td1">已输入行数：</td>
		    <td class="td2"><input disabled maxlength="4" name="pads" size="3" value="0" class="inputtext"></td>
		 </tr>
		 <tr class="errorTr hide" ><td colspan="2" style="padding:10px;font-size:12px;font-weight:bold;color:red">部分受理失败,错误信息如下:</td></tr>
		  <tr class="errorTr hide">
		    <td class="td1"><span style='color:red'>错误信息：</span></td>
		    <td class="td2">
                 <div id="p" class="easyui-panel errormsg" title="错误信息列表"     
				        style="width:400px;height:150px;padding:10px;"   
				        data-options="noheader:true">   
				</div>  
                    
			</td>
<!-- 		    <td class="td2"><textarea class="errormsg" style="width: 100%;height: 200px"></textarea></td> -->
		 </tr>
	</table>
    </form>
    </div>
</body>
</html>



