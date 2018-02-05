<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<html>
<head>
<title>游戏管理</title>
<meta name="decorator" content="moduleIndex" />
</head>
<body>
	<script type="text/javascript">
	  
	    var gameIdValue1 = '${gameList[0].gameId}';
	    var gameIdValue2 = '${gameList[1].gameId}';
	    var gameIdValue3 = '${gameList[2].gameId}';
	
		$('.easyui-form').form({
		    url:host+'recommand',   
		    success:callback
		});
		
		function doSearch(){
			$('#module_datagrid_game').datagrid('reload',{
				gameName: $("#gameName4").textbox('getValue')
			});
		}
		
		
		var module_datagrid_game = "#module_datagrid_game";
		var callback = defaultCallback ;
		var dataGridParamObj = {
			singleSelect : true,
		    url : host + "gameList",
			idField : 'gameId',
			onDblClickRow:dblClickRow,
			onCheck : function(index,row) {
				fillText(row.gameId,row.gameName);
			},
			columns : [ [
			         	{width:100,title:'gameId',field:pk,checkbox:true},
						{width:100,title:'游戏名称',field:'gameName',sortable:true},
						{width:100,title:'游戏应用名',field:'map.gameAppName'},
						{width:100,title:'游戏渠道',field:'map.gameChannelName',sortable:true},
						{width:100,title:'应用类型',field:'map.softType'},
						{width:100,title:'游戏状态',field:'map.gameStatusName',sortable:true}                                   
			         	] ]
		};
		var dialogParamObj = {

		};
		$(function() {
			$(module_datagrid_game).datagrid($.extend({}, dataGridParam, dataGridParamObj));
		});
		
		function dblClickRow(){}
		
		function fillText(rowGameId,rowGameName){
			// gameId1
			var gameId=$('#gameId').val();
			gameId = (gameId == -1 ? '' : gameId) +gameIdValue1;
			var gameName=$("#gameName").textbox('getValue');
			
			// gameId2
			var gameId2=$('#gameId2').val();
			gameId2 = (gameId2 == -1 ? '' : gameId2) +gameIdValue2;
			var gameName2=$("#gameName2").textbox('getValue');
			
			// gameId3
			var gameId3=$('#gameId3').val();
			gameId3 = (gameId3 == -1 ? '' : gameId3) +gameIdValue3;
			var gameName3=$("#gameName3").textbox('getValue');
			
			if((rowGameId == gameId) || (rowGameId == gameId2) || (rowGameId == gameId3)){
				return false;
			}
			
			if(gameId==null || gameId=='' || gameName=='' || gameName==null){
				$('#gameId').val(rowGameId);
				$("#gameName").textbox('setValue',rowGameName);
			}else{
				if(gameId2==null || gameId2=='' || gameName2=='' || gameName2==null){
					$('#gameId2').val(rowGameId);
					$("#gameName2").textbox('setValue',rowGameName);
				}else{
					if(gameId3==null || gameId3=='' || gameName3=='' || gameName3==null){
						$('#gameId3').val(rowGameId);
						$("#gameName3").textbox('setValue',rowGameName);
					}
				}
			}
		}
		
		
		function cleanGame(){
			$('#gameId').val('');
			gameIdValue1 = '';
			$("#gameName").textbox('setValue','');
		}
		
		function cleanGame2(){
			$('#gameId2').val('');
			gameIdValue2 = '';
			$("#gameName2").textbox('setValue','');
		}
		
		function cleanGame3(){
			$('#gameId3').val('');
			gameIdValue3 = '';
			$("#gameName3").textbox('setValue','');
		}
		
	</script>
	<div id="module_submit_container">
	<form id="module_submit_form" class="easyui-form" method="post">
	<input type="hidden" name="gameAppId" value="${gameAppId}"/>
	<div style="text-align: center;">${gameAppName}</div>
	<!-- font-size: 18px; -->
	<table id="module_submit_table">
		
		<tr>
			<td class="td1">关联推荐游戏1:</td>
			<td class="td2">
				<input type="hidden" id="gameId" name="gameId" value="${gameAppRelationList[0].relationId != null ? -1 : ''}">
				<input type="hidden" id="relationId1" name="relationIds" value="${gameAppRelationList[0].relationId}">
				<input class="easyui-textbox" type="text" id="gameName" name="gameName" value="${gameList[0].gameName}"/>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset" style="text-align:center" plain="false" onclick="cleanGame()">清空</a>
			</td>
		</tr>
		<tr>
			<td class="td1">关联推荐游戏2:</td>
			<td class="td2">
				<input type="hidden" id="gameId2" name="gameId" value="${gameAppRelationList[1].relationId != null ? -1 : ''}">
				<input type="hidden" id="relationId2" name="relationIds" value="${gameAppRelationList[1].relationId}">
				<input class="easyui-textbox" type="text" id="gameName2" name="gameName" value="${gameList[1].gameName}"/>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset" plain="false" onclick="cleanGame2()">清空</a>
			</td>
		</tr>
		<tr>
			<td class="td1">关联推荐游戏3:</td>
			<td class="td2">
				<input type="hidden" id="gameId3" name="gameId" value="${gameAppRelationList[2].relationId != null ? -1 : ''}">
				<input type="hidden" id="relationId3" name="relationIds" value="${gameAppRelationList[2].relationId}">
				<input class="easyui-textbox" type="text" id="gameName3" name="gameName" value="${gameList[2].gameName}"/>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset" plain="false" onclick="cleanGame3()">清空</a>
			</td>
		</tr>
		
		<tr>
			<td class="td1">游戏名称:</td>
			<td class="td2">
				<input type="text" id="gameName4" name="gameName4" class="easyui-textbox input_width_default" style="width:150px"/>
				<a href="#" iconCls="icon-search-rf" class="easyui-linkbutton" plain="false" onclick="doSearch()">查询</a>
			</td>
		</tr>
		
	</table>
    </form>
    </div>
    
    <table id="module_datagrid_game" toolbar="#module_toolbar_game"></table>
</body>
</html>



