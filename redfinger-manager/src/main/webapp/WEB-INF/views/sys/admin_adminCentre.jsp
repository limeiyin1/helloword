<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>个人中心</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="adminCode";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj = {
			idField : 'adminCode',
			onCheck : function(row) {
			},	
	};
	var dialogParamObj={
		
	};
	$(function(){
		
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});

	//查看	
	function pwd() {
		var id =myform.adminCode.value;
		var title = $("title").html() + " - 修改密码";
		var href = host + 'formPwd';
		$("#button-save").unbind("click").click(pwdSave);
		openDialogForm(title, href);
	}
	function pwdSave(){
		if($('.easyui-form').form("validate")){
			$.messager.progress();
			$('.easyui-form').form("submit");
		}
	}

</script>
</head>
<body>
	<!-- 表格  -->
		<div id="module_submit_container" >
	<form id="module_submit_form" name="myform" method="post">
      <input  type="hidden" name="adminCode" value="${bean.adminCode}">
     <div align="center">
	<table  class="easyui-table" >
		<tr>
			<td class="td1">名称:</td>
			<td class="td2" >${bean.adminName }</td>
		</tr>
		<tr>
			<td class="td1">账号:</td>
			<td class="td2">${bean.adminCode }</td>
		</tr>
		<tr>
			<td class="td1">部门:</td>
			<td class="td2">${bean.orgCode }</td>
		</tr>
		<tr>
			<td class="td1">QQ:</td>
			<td class="td2">${bean.adminQq }</td>
		</tr>
		<tr>
			<td class="td1">联系电话:</td>
			<td class="td2">${bean.adminPhone }</td>
		</tr>
		<tr>
			<td class="td1">描述:</td>
			<td class="td2"><input class="easyui-textbox" readonly="readonly" name="remarks" value="${bean.remark }" data-options="multiline:true,validType:'length[0,200]'" style="height:60px" /></td>
		</tr>
		<tr>
			<td class="td1"></td>
			<td class="td2"><a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-edit-rf" onclick="pwd()">修改密码</a> </td>
		</tr>
	</table>
	</div>
    </form>
    </div>
	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
    <div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:void(0)" id="button-cancel" class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
</body>
</html>



