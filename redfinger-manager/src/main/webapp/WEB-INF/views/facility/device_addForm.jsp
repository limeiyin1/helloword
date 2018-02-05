<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>虚拟设备编辑</title>
<meta name="decorator" content="default" />
</head>
<body>
	<script type="text/javascript">
	$('.easyui-form').form({
	    url:host+'updateVM',   
	    success:callback
	});
	

/*  	$('.easyui-numberbox').numberbox({  
		 onChange:function(newValue,oldValue){  
	
		        var rq = $('.easyui-numberbox').val();
		        for(var i=0;i<rq.length;i++){
		        	   if ($('.easyui-numberbox')[i].value == newValue) {
		        	
		        		   $("#"+$('.easyui-numberbox')[i].value).numberbox("setValue", "");
		        			
	                    }
		        }
		    } 
            });  */
	
 	
/*    $.extend($.fn.validatebox.defaults.rules, {   
        padSN: {
    		validator: function(value){	
    		 	var rq = $('.easyui-numberbox').val();
		        for(var i=0;i<rq.length;i++){
		        	   if ($('.easyui-numberbox')[i].value == value && $('.easyui-numberbox')[i].value != "") {   
	                        return false;
		        }
		    }   
    			return true;
    		},
    		message: '有相同SN！'
        }
    });  */


	</script>
 
	<div id="module_submit_container">
		<form id="module_submit_form" class="easyui-form" method="post">
		 <table id="module_submit_table" >
				<tr>
					<td >设备编号:<input type="hidden" name="devicePadId" value="${bean.deviceId}"></td>
					<td >设备IP</td>
					<td >设备SN</td>
	            </tr>
				<c:forEach  items="${list}" var="one" varStatus="status" >
				<tr>
					<td >${one.padCode} <input type="hidden" name="pads[${status.index}].padId" value="${one.padId}"></td>
					<td >${one.padIp} <input type="hidden" name="pads[${status.index}].deviceId" value="${bean.deviceId}"></td>
					<td ><input style="width: 80px" class="easyui-numberbox" type="text"name="pads[${status.index}].padSn" value="${one.padSn}"data-options="required:true," /></td>
				</tr>
				</c:forEach> 
			
		</table>
	   </form>
		</div>

</body>
</html>


