<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>用户</title>
<meta name="decorator" content="default" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'alterPwd',   
		    success:callback
		}); 
		
		$.extend($.fn.validatebox.defaults.rules, {         
			equalTo : {  
	            validator : function(value, param) {  
	                return $("#" + param[0]).val() == value;  
	            },  
	            message : '两次输入的密码不一致!'  
	        },  
	/*         checkPassword :{  
	            validator : function(value,param){  
	                var sysAdmin= {};  
	                var flag ;  
	                sysAdmin.adminPwd = value; 
	                $.ajax({  
	                    url : host+'passWord',  
	                    type : 'POST',                    
	                    timeout : 60000,  
	                    data:sysAdmin,  
	                    async: false,    
	                    success : function(data, textStatus, jqXHR) {     
	                        if (data =="\"1\"") {  
	                            flag = true;      
	                        }else{  
	                            flag = false; 
	                        }  
	                    }  
	                });  
	                if(flag){  
	                    $("#adminPwd").removeClass('validatebox-invalid');  
	                }  
	                return flag;  
	            },  
	            message: '原始密码输入错误！'  
	        }   */
		})   
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form"  class="easyui-form" method="post">
	<input type="hidden" name="adminCode" value="${bean.adminCode}"> 
	<table class="easyui-table" >
		<tr>
			<td class="td1">旧密码:</td>
			<td class="td2"><input  class="easyui-textbox" type="password" id="adminPwd" name="adminPwd" data-options="required:true" validType="checkPassword"/>  
			</td>
		</tr>
		<tr>  
        <td class="td1">请输入新密码：</td>  
        <td class="td2"><input  class="easyui-textbox" type="password" id="newPassword"  name="pwd" data-options="required:true" />  
        </td>  
       </tr>  
     <tr>  
        <td class="td1">请确认输入新密码：</td>  
        <td class="td2"><input  class="easyui-textbox" type="password" id="reNewPassword" name="reNewPassword"  
          data-options="required:true"  validType="equalTo['newPassword']" />  
        </td>  
    </tr>  
	</table>
    </form>
    </div>
</body>
</html>



