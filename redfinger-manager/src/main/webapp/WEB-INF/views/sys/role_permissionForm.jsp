<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html lang="zh-cn">
<head>
<title>角色</title>
<meta name="decorator" content="default" />
</head>
<body>
	<script type="text/javascript">
		$('.easyui-form').form({
		    url:host+'permission',   
		    success:callback,
		    onSubmit:function(){
		    	getTreeIds();
		    }
		    
		}); 
		$("#permissionIds").tree({
			data:${treeJson},
			checkbox:true,
			cascadeCheck:false
		});
		
		
		function getTreeIds(){
			var nodes=$('#permissionIds').tree('getChecked');
			var ids=",";
	    	if(nodes){
	    		$.each(nodes,function(n,value) {   
	    			ids+=value.id+","
	    		});
	    	}
	    	if(ids!=","){
	    		$("#easyui-form").find("input[name='permissionIds']").val(ids);
	    	}
		}
		function checkAll(check){
			var roots = $('#permissionIds').tree('getRoots');
			for(var i in roots){
				checkHelp(roots[i],check);
			}
		}
		function checkHelp(node,check){
			$('#permissionIds').tree(check?'check':'uncheck',node.target);
			var children =$('#permissionIds').tree('getChildren',node.target);
			for(var i in children){
				checkHelp(children[i],check);
			}
		}
	</script>
	<div id="module_submit_container">
	<a href="javascript:void(0)" class="easyui-linkbutton" plain="false" onclick="checkAll(true)">全选中</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" plain="false" onclick="checkAll(false)">全取消</a>
	<form id="easyui-form" class="easyui-form" method="post">
		<input type="hidden" name="roleCode" value="${bean.roleCode}"/>
		<input type="hidden" name="permissionIds" value=""/>
		<div id="permissionIds" style="margin-top: 10px;"></div>
    </form>
    </div>
</body>
</html>



