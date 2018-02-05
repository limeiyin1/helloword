<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>设备授权日志</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="grantLogId";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField :pk,
		onCheck: function(row){
			
		},
		columns:[[
			{width:100,title:'id',field:pk,checkbox:true},
			{width:70,title:'授权人ID',field:'map.grantorExternalUserId'},
			{width:70,title:'被授权人ID',field:'map.externalUserId'},
			{width:100,title:'授权用户',field:'map.grantorUserMobilePhone'},
			{width:100,title:'被授权用户',field:'map.userMobilePhone'},
			{width:80,title:'操作类型',field:'map.operateTypeCode'},
			{width:100,title:'操作时间',field:'operateTime',sortable:true,formatter:formatterTime},
			{width:60,title:'授权方式',field:'map.grantModeCode'},
			{width:50,title:'授权编码',field:'map.grantCode'},
			{width:50,title:'控制授权',field:'map.grantControlCode'},
			{width:50,title:'观看授权',field:'map.grantWatchCode'},
			{width:100,title:'授权时间',field:'grantTime',sortable:true,formatter:formatterTime},
			{width:100,title:'授权开始时间',field:'grantStartTime',sortable:true,formatter:formatterTime},
			{width:100,title:'授权结束时间',field:'grantEndTime',sortable:true,formatter:formatterTime}
		]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
		$.extend($.fn.validatebox.defaults.rules,{
			number:{// 验证数字
	          validator : function(value) {
	              return /^[0-9]{1,9}$/gi.test(value);
	          },
	          message : '只允许1-9位的正整数'
	        }
		});
	});
	//grid搜索
	var gridSearchValidate = function() {
		var arr = $(module_search_form).serializeArray();
		var obj = {};
		$.each(arr, function(i, field) {
			obj[field.name] = field.value;
		});
		if(!($('#module_search_form').form("validate"))){
			return false;
			}
		try{
			$(module_datagrid).treegrid("reload",obj);
		}catch(e){
			$(module_datagrid).datagrid("reload",obj);
		}
	}
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">
				授权人ID：<input type="text" name="grantorExternalUserId" data-options="validType:'number'"" class="easyui-numberbox input_width_short"/>
		    </div>
			<div class="module_search_input">
				被授权人ID：<input type="text" name="externalUserId" data-options="validType:'number'"" class="easyui-numberbox input_width_short"/>
		    </div>
			<div class="module_search_input">
			      授权人号码：<input type="text" name="grantorUserMobilePhone" class="easyui-textbox input_width_default" /> 
			</div>
			<div class="module_search_input">
			      被授权人号码：<input type="text" name="userMobilePhone" class="easyui-textbox input_width_default" /> 
			</div>
		    <div class="module_search_input">
		               操作类型  ：<select class="easyui-combobox input_width_short" editable="false" name="operateType" style="width:150px" data-options="required:false">
		                <option value="">[全部]</option>
		                <c:forEach items="${maps }" var="map">
		                	<option value="${map.key }">${map.value }</option>
		                </c:forEach>
		           </select>
		    </div>
			<div class="module_search_input">
				操作时间：
				<input type="text" class="easyui-datebox input_width_default" editable="false" id="begin" name="begin" data-options=""/>
				至
				<input type="text" class="easyui-datebox input_width_default" editable="false" id="end" name="end" data-options=""/>
			</div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search-rf" plain="false" onclick="gridSearchValidate()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset-rf" plain="false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload-rf" plain="true" onclick="fresh()">刷新</a>
		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>
	
	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
    <div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton" iconCls="icon-ok-rf">保存</a>
		<a href="javascript:void(0)" id="button-cancel" class="easyui-linkbutton" iconCls="icon-no" onclick="cancel()">关闭</a>
	</div>
</body>
</html>


