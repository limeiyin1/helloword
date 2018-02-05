<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>游戏apk管理</title>
<meta name="decorator" content="moduleIndex" />
<script type="text/javascript">
	var pk="id";
	var module_datagrid="#module_datagrid";
	var module_dialog="#module_dialog";
	var module_search_form="#module_search_form";
	var module_submit_form="#module_submit_form";
	var callback=defaultCallback;
	var dataGridParamObj={
		url:host+"list",
		idField : 'id',
		onCheck: function(row){
			
		},
		columns:[[
			{width:100,title:'apkId',field:pk,checkbox:true},
			{width:100,title:'游戏名称',field:'name',sortable:true},
			{width:150,title:'游戏包',field:'packageName',sortable:true},
			{width:150,title:'游戏详情',field:'content',sortable:true},
			{width:100,title:'游戏大小',field:'apkSize',sortable:true},
			{width:100,title:'游戏版本',field:'apkVersion',sortable:true},
			{width:100,title:'浏览次数',field:'map.countBrowse'},
			{width:100,title:'搜索次数',field:'map.countSearch'},
			{width:100,title:'下载次数',field:'map.countDownload'},
			{width:100,title:'评论次数',field:'countComment',sortable:true},
			{width:100,title:'评论分数',field:'commentScore',sortable:true},
			{width:100,title:'开发商',field:'map.developer'},
			{width:100,title:'关键字',field:'keywords',sortable:true},
			{width:100,title:'拼音首字母',field:'pinyin',sortable:true},
			{width:100,title:'全拼',field:'pinyinFull',sortable:true},
			{width:100,title:'游戏下载路径',field:'downloadUrl',sortable:true},
			{width:150,title:'发布更新时间',field:'updateTime',sortable:true,formatter:formatterDate},
			{width:150,title:'重新排序',field:'reorder',sortable:true},
			{width:100,title:'状态',field:'enableStatus',sortable:true,formatter:formatterStop},
		]]
	};
	var dialogParamObj={
		
	};
	$(function(){
		$(module_datagrid).datagrid($.extend({},dataGridParam,dataGridParamObj));
		$(module_dialog).dialog($.extend({},dialogParam,dialogParamObj));
	});
	
	
	//游戏推荐
	function recommend(){
		var id=getGridId();
		if(!id)return false;
		var title=$("title").html()+" - 推荐";
		var href=host+'recommendForm?id='+id;	
		$("#button-save").unbind("click").click(appApkSave);
		openDialogForm(title,href);
	}
	//游戏分类
	function category(){
		var id=getGridId();
		if(!id)return false;
		var title=$("title").html()+" - 分类";
		var href=host+'categoryForm?id='+id;	
		$("#button-save").unbind("click").click(appApkSave);
		openDialogForm(title,href);
	}
	//游戏图片
	function image(){
		var id=getGridId();
		if(!id)return false;
		var title=$("title").html()+" - 图片";
		var href=host+'imageForm?id='+id;	
		$("#button-save").unbind("click").click(appApkSave);
		openDialogForm(title,href);
	}
	
	function recommendImage(){
		var id=getGridId();
		if(!id)return false;
		var title=$("title").html()+" - 推荐图片";
		var href=host+'recommendImageForm?id='+id;	
		$("#button-save").unbind("click").click(appApkSave);
		openDialogForm(title,href);
	}
	
	function appApkSave(){
		if($('#easyui-form').form("validate")){
			$.messager.progress();
			$('#easyui-form').form("submit");
		}
	}
	
</script>
</head>
<body>
	<!-- 表格  -->
	<div id="module_container" >
		<div id="module_search" >
		<form id="module_search_form" class="easyui-form" method="post">
			<div class="module_search_input">游戏名称：<input type="text" name="name" class="easyui-textbox input_width_default"/></div>
			<div class="module_search_button">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search-rf',plain:false" onclick="gridSearch()">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reset-rf',plain:false" onclick="gridReset()">清空</a>
			</div>
		</form>
		</div>
		<div id="module_toolbar" class="easyui-toolbar">
		  <c:if test="${not empty sessionScope.permission.button_game_apkmanage_save}">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add-rf',plain:true" onclick="add()">新增</a>
	      </c:if>
	      <c:if test="${not empty sessionScope.permission.button_game_apkmanage_update}">
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit-rf',plain:true" onclick="edit()">编辑</a>
	      </c:if>
	      <c:if test="${not empty sessionScope.permission.button_game_apkmanage_start}">
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-start-rf',plain:true" onclick="start(callback)">启用</a>
	      </c:if>
	      <c:if test="${not empty sessionScope.permission.button_game_apkmanage_stop}">
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-stop-rf',plain:true" onclick="stop(callback)">禁用</a>
	      </c:if>
	      <c:if test="${not empty sessionScope.permission.button_game_apkmanage_delete}">
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove-rf',plain:true" onclick="del(callback)">删除</a>
	      </c:if>
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload-rf',plain:true" onclick="fresh()">刷新</a>
		  <c:if test="${not empty sessionScope.permission.button_game_apkmanage_category}">
		    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-star-rf',plain:true" onclick="category()">分类</a>
		  </c:if>
		  <c:if test="${not empty sessionScope.permission.button_game_apkmanage_recommend}">
		    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-star-rf',plain:true" onclick="recommend()">推荐</a>
   	      </c:if>
   	      <c:if test="${not empty sessionScope.permission.button_game_apkmanage_image}">
   	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-star-rf',plain:true" onclick="image()">图片</a>
	 	  </c:if>
	 	  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-star-rf',plain:true" onclick="recommendImage()">推荐图片</a>
	 		</div>
		<table id="module_datagrid" toolbar="#module_toolbar"></table>
	</div>
	
	<!-- 编辑框 -->
	<div id="module_dialog" buttons="#module_dialog_button"></div>
    <div id="module_dialog_button">
		<a href="javascript:void(0)" id="button-save" class="easyui-linkbutton"data-options="iconCls:'icon-ok-rf'">保存</a>
		<a href="javascript:void(0)" id="button-cancel" class="easyui-linkbutton" data-options="iconCls:'icon-no'" onclick="cancel()">关闭</a>
	</div>
</body>
</html>



