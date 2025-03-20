var currentIndex = 0; // 當前的最大順序

$(function() {
	
	// 重填
	$("#btn_reset").on("click", function(){
		$("input[type='text']").val("");
	});
	
	// 查詢按鈕
	$("#btn_search").click(function(){
		$("#thisForm").attr("action","/APCSM/system/flow/query");
		$("#thisForm").submit();
		viewLoading();
	});
	
	// 設定審核流程畫面
	$("#btn_config").click(function(){
		$("#thisForm").attr("action","/APCSM/system/flow/goMergeFlowStage");
		$("#thisForm").submit();
		viewLoading();
	});
	
	// 回上一頁(查詢頁)
	$("#btn_backQuery").click(function(){
		$("#thisForm").attr("action","/APCSM/system/flow/query");
		$("#thisForm").submit();
		viewLoading();
	});
	
	// 回上一頁(流程關卡頁)
	$("#btn_backStage").click(function(){
		$("#thisForm").attr("action","/APCSM/system/flow/goMergeFlowStage");
		$("#thisForm").submit();
		viewLoading();
	});

	// 設定頁 審核流程下拉選單 事件
	$("#flowType").change(function(){
		$.ajax({
		    url: "/APCSM/system/flow/getFlowByType",
		    type: "POST",		    
		    dataType: "json",
		    data: {
		    	"flowType": $("#flowType").val()
		    },
		    async: false,
		    success: function(dataMap) {
		    	/*
		    	var disabled = $("#flowStageTable tbody").sortable("option", "disabled");
		    	if(disabled){
		    		$("#flowStageTable tbody").sortable("enable");
		    	}else {
		    		$("#flowStageTable tbody").sortable("disable");
		    	}*/
		    	$("#flowStageTable tbody").empty();
		    	currentIndex = 0;
		    	
		        $.each(dataMap.dataList, function(index, obj){
		        	var html = 
		        		'<tr class="' + (index%2 == 0 ? "odd" : "even") + '">'
		        		+ '<input type="hidden" name="flowProfStageList[' + index + '].id" value="' + obj.id + '"/>'
		        		+ '<input type="hidden" name="flowProfStageList[' + index + '].flowType" value="' + obj.flowType + '"/>'		        		
		        		+ '<input type="hidden" name="flowProfStageList[' + index + '].sortIndex" value="' + obj.sortIndex + '"/>'
		        		+ '<td class="center">'
		        		+ '<input type="text" name="flowProfStageList[' + index + '].aduitName" value="' + obj.aduitName + '"/>'
		        		+ '</td>'
		        		+ '<td class="center">' + obj.sortIndex + '</td>'
		        		+ 	'<td class="center">'
		        		+ 		'<button type="button" class="btn btn-success btn-sm" onclick="goMergeFlowRole(\'' + obj.id + '\');">設定</button>'
		        		+ 	'</td>'
		        		+ 	'<td class="center">'
		        		+ 		'<button type="button" class="btn btn-success btn-sm" onclick="goMergeFlowStatus(\'' + obj.id + '\');">設定</button>'
		        		+ 	'</td>'
		        		+ 	'<td class="center">'
		        		+ 		'<span class="glyphicon glyphicon-remove" aria-hidden="true" style="color:red;cursor:pointer;" onclick="removeFlowStage($(this),\'' + obj.id + '\');"></span>'
		        		+ 	'</td>'
		        		+ '</tr>';
		        	$("#flowStageTable tbody").append(html);
		        	currentIndex = index + 1;
		        });
		        
		        tableSlide($("#flowStageTable"));
		    }
		});
	});
	
	// 新增流程關卡
	$("#btn_addFlowStage").click(function(){
		var check = true;
		//validate
		$("#msg_bl").empty();
		if($("#flowType").val() == ""){
			$("#msg_bl").append("請選擇審核流程</br>");
			check = false;
		}
		if($.trim($("#aduitName").val()) == ""){
			$("#msg_bl").append("請輸入關卡名稱</br>");
			check = false;
		}		

		if(check){
			var html = 
	    		'<tr class="' + (currentIndex%2 == 0 ? "odd" : "even") + '">'
	    		+ '<input type="hidden" name="flowProfStageList[' + currentIndex + '].id" value=""/>'
	    		+ '<input type="hidden" name="flowProfStageList[' + currentIndex + '].flowType" value="' + $("#flowType").val() + '"/>'
	    		+ '<input type="hidden" name="flowProfStageList[' + currentIndex + '].sortIndex" value="' + (currentIndex + 1) + '"/>'
	    		+ '<td class="center">'
	    		+ '<input type="text" name="flowProfStageList[' + currentIndex + '].aduitName" value="' + $("#aduitName").val() + '"/>'
	    		+ '</td>'
	    		+ '<td class="center">' + (currentIndex + 1) + '</td>'
	    		+ 	'<td class="center">'
	    		+ 		'<div class="tooltip-wrapper" data-title="請先儲存後再進行設定">'
	    		+ 			'<button type="button" class="btn btn-success btn-sm" onclick="goMergeFlowRole(\'\');" disabled>設定</button>'
	    		+		'</div>'
	    		+ 	'</td>'
	    		+ 	'<td class="center">'
	    		+ 		'<div class="tooltip-wrapper" data-title="請先儲存後再進行設定">'
	    		+ 			'<button type="button" class="btn btn-success btn-sm" onclick="goMergeFlowStatus(\'\');" disabled>設定</button>'
	    		+ 		'</div>'
	    		+ 	'</td>'
	    		+ 	'<td class="center">'
	    		+ 		'<span class="glyphicon glyphicon-remove" aria-hidden="true" style="color:red;cursor:pointer;" onclick="removeFlowStage($(this));"></span>'
	    		+ 	'</td>'
	    		+ '</tr>';
			$("#flowStageTable tbody").append(html);
			currentIndex++;
			resetStageTable($("#flowStageTable tbody").children());
			$(".tooltip-wrapper").tooltip();
		} else{
			$("#dialogMsg").modal({
				backdrop: "static",
				keyboard: false,
				show: true
			});
		}		
	});

	// 新增流程角色
	$("#btn_addFlowRole").click(function(){
		var check = true;
		//validate
		$("#msg_bl").empty();
		if($("#roleType").val() == ""){
			$("#msg_bl").append("請選擇角色</br>");		
			check = false;
		}
		$("#flowRoleTable :hidden[name*='roleNo']").each(function(){
			if($(this).val() == $("#roleType").val()){
				$("#msg_bl").append("角色已重複</br>");		
				check = false;
				return false;
			}
		});
		
		if(check){
			var html = 
	    		'<tr class="' + (currentIndex%2 == 0 ? "odd" : "even") + '">'
	    		+ '<input type="hidden" name="flowProfRoleList[' + currentIndex + '].id" value=""/>'
	    		+ '<input type="hidden" name="flowProfRoleList[' + currentIndex + '].stageId" value="' + $("#stageId").val() + '"/>'
	    		+ '<input type="hidden" name="flowProfRoleList[' + currentIndex + '].roleType" value="C"/>'
	    		+ '<input type="hidden" name="flowProfRoleList[' + currentIndex + '].roleNo" value="' + $("#roleType").val() + '"/>'
	    		+ 	'<td class="center">'
	    		+ 		'<span class="glyphicon glyphicon-remove" aria-hidden="true" style="color:red;cursor:pointer;" onclick="removeFlowRole($(this));"></span>'
	    		+ 	'</td>'
	    		+ '<td class="center">' + $("#roleType option:selected").text() + '</td>'	
	    		+ '</tr>';
			$("#flowRoleTable tbody").append(html);
			currentIndex++;
			resetRoleTable($("#flowRoleTable tbody").children());
		} else{
			$("#dialogMsg").modal({
				backdrop: "static",
				keyboard: false,
				show: true
			});
		}		
	});
	
	// 新增流程狀態
	$("#btn_addFlowStatus").click(function(){
		var check = true;
		//validate
		$("#msg_bl").empty();
		if($("#approveStatus").val() == ""){
			$("#msg_bl").append("請選擇審核選項</br>");		
			check = false;
		}
		if($("#stageStatus").val() == ""){
			$("#msg_bl").append("請選擇審核動作</br>");		
			check = false;
		}
		$("#flowStatusTable :hidden[name*='approveStatus']").each(function(){
			if($(this).val() == $("#approveStatus").val()){
				$("#msg_bl").append("審核選項已重複</br>");		
				check = false;
				return false;
			}
		});
		
		if(check){
			var html = 
	    		'<tr class="' + (currentIndex%2 == 0 ? "odd" : "even") + '">'
	    		+ '<input type="hidden" name="flowProfStatusList[' + currentIndex + '].id" value=""/>'
	    		+ '<input type="hidden" name="flowProfStatusList[' + currentIndex + '].stageId" value="' + $("#stageId").val() + '"/>'
	    		+ '<input type="hidden" name="flowProfStatusList[' + currentIndex + '].approveStatus" value="' + $("#approveStatus").val() + '"/>'
	    		+ '<input type="hidden" name="flowProfStatusList[' + currentIndex + '].stageStatus" value="' + $("#stageStatus").val() + '"/>'
	    		+ 	'<td class="center">'
	    		+ 		'<span class="glyphicon glyphicon-remove" aria-hidden="true" style="color:red;cursor:pointer;" onclick="removeFlowStatus($(this));"></span>'
	    		+ 	'</td>'
	    		+ '<td class="center">' + $("#approveStatus option:selected").text() + '</td>'	
	    		+ '<td class="center">' + $("#stageStatus option:selected").text() + '</td>'
	    		+ '</tr>';
			$("#flowStatusTable tbody").append(html);
			currentIndex++;
			resetStatusTable($("#flowStatusTable tbody").children());
		} else{
			$("#dialogMsg").modal({
				backdrop: "static",
				keyboard: false,
				show: true
			});
		}		
	});
	
	// 設定流程關卡-儲存
	$("#btn_saveStage").click(function(){
		var check = true;
		$("#msg_bl").empty();
		if($("#flowStageTable tbody tr").length == 0){			
			$("#msg_bl").append("請新增關卡</br>");
			check = false;
		}
		if(checkFlowStatus($("#flowType").val())){
			$("#msg_bl").append("有審核中審件，無法修改</br>");
    		check = false;
		}
		
		if(check){
			$("#thisForm").attr("action","/APCSM/system/flow/mergeFlowStage");
			$("#thisForm").submit();
			viewLoading();			
		} else{
			$("#dialogMsg").modal({
				backdrop: "static",
				keyboard: false,
				show: true
			});			
		}
	});
	
	// 設定流程角色-儲存
	$("#btn_saveRole").click(function(){
		var check = true;
		$("#msg_bl").empty();
		if($("#flowRoleTable tbody tr").length == 0){			
			$("#msg_bl").append("請新增角色</br>");
			check = false;
		}
		if(checkFlowStatus($("#flowType").val())){
			$("#msg_bl").append("有審核中審件，無法修改</br>");
    		check = false;
		}
		
		if(check){
			$("#thisForm").attr("action","/APCSM/system/flow/mergeFlowRole");
			$("#thisForm").submit();
			viewLoading();			
		} else{
			$("#dialogMsg").modal({
				backdrop: "static",
				keyboard: false,
				show: true
			});			
		}
	});
	
	// 設定流程狀態-儲存
	$("#btn_saveStatus").click(function(){
		var check = true;
		$("#msg_bl").empty();
		if($("#flowStatusTable tbody tr").length == 0){			
			$("#msg_bl").append("請新增選單</br>");
			check = false;
		}
		if(checkFlowStatus($("#flowType").val())){
			$("#msg_bl").append("有審核中審件，無法修改</br>");
    		check = false;
		}
		
		if(check){
			$("#thisForm").attr("action","/APCSM/system/flow/mergeFlowStatus");
			$("#thisForm").submit();
			viewLoading();			
		} else{
			$("#dialogMsg").modal({
				backdrop: "static",
				keyboard: false,
				show: true
			});			
		}
	});

	//初始化
	tableSlide($("#flowStageTable"));
	$("#flowType").change();
	
});


//開啟拖曳效果
function tableSlide($table){		
	$table.find("tbody").sortable({
		update: function(event, ui) {
			resetStageTable($(this).children());
		}
	});
}

//刪除流程關卡
function removeFlowStage($e, id){		
	var dialog_buttons = {}; 
	dialog_buttons["確定"] = function(){
		$e.parents("tr:first").remove();
		resetStageTable($("#flowStageTable tbody").children());
		
		//刪除的IDs
		if(id != null){
			if($("#deleteIds").val() == ""){
				$("#deleteIds").val(id);
			} else{
				$("#deleteIds").val($("#deleteIds").val() + "," + id);
			}	
		}
		
		$(this).dialog("close");
	};
	dialog_buttons["取消"] = function(){
		$(this).dialog("close");
	};   
	
	dialogConfirm("確定要刪除嗎？", dialog_buttons);
}

//刪除流程角色
function removeFlowRole($e){		
	var dialog_buttons = {}; 
	dialog_buttons["確定"] = function(){
		$e.parents("tr:first").remove();
		resetRoleTable($("#flowRoleTable tbody").children());
		$(this).dialog("close");
	};
	dialog_buttons["取消"] = function(){
		$(this).dialog("close");
	};   
	
	dialogConfirm("確定要刪除嗎？", dialog_buttons);
}

//刪除流程狀態
function removeFlowStatus($e){		
	var dialog_buttons = {}; 
	dialog_buttons["確定"] = function(){
		$e.parents("tr:first").remove();
		resetStatusTable($("#flowStatusTable tbody").children());
		$(this).dialog("close");
	};
	dialog_buttons["取消"] = function(){
		$(this).dialog("close");
	};   
	
	dialogConfirm("確定要刪除嗎？", dialog_buttons);
}

//進入流程角色頁
function goMergeFlowRole(id){		
	$("#myForm").attr("action","/APCSM/system/flow/goMergeFlowRole");
	$("#myForm #stageId").val(id);
	$("#myForm").submit();
	viewLoading();
}

//進入流程狀態頁
function goMergeFlowStatus(id){		
	$("#myForm").attr("action","/APCSM/system/flow/goMergeFlowStatus");
	$("#myForm #stageId").val(id);
	$("#myForm").submit();
	viewLoading();
}

//重設Stage table資訊
function resetStageTable($trs){
	$trs.each(function(index) {
		$(this).find(':hidden:eq(2)').val(index + 1);
		$(this).find('td:eq(1)').html(index + 1);
		$(this).find(':hidden:eq(0)').attr("name", 'flowProfStageList[' + index + '].id');
		$(this).find(':hidden:eq(1)').attr("name", 'flowProfStageList[' + index + '].flowType');
		$(this).find(':hidden:eq(2)').attr("name", 'flowProfStageList[' + index + '].sortIndex');
		$(this).find(':text').attr("name", 'flowProfStageList[' + index + '].aduitName');
		
		if(index%2 == 0){
			$(this).removeClass("odd even").addClass("odd");
		} else{
			$(this).removeClass("odd even").addClass("even");
		}
	});
	currentIndex = $trs.length - 1;
}

//重設Role table資訊
function resetRoleTable($trs){
	$trs.each(function(index) {
		$(this).find(':hidden:eq(0)').attr("name", 'flowProfRoleList[' + index + '].id');
		$(this).find(':hidden:eq(1)').attr("name", 'flowProfRoleList[' + index + '].stageId');
		$(this).find(':hidden:eq(2)').attr("name", 'flowProfRoleList[' + index + '].roleType');
		$(this).find(':hidden:eq(3)').attr("name", 'flowProfRoleList[' + index + '].roleNo');
		
		if(index%2 == 0){
			$(this).removeClass("odd even").addClass("odd");
		} else{
			$(this).removeClass("odd even").addClass("even");
		}
	});
	currentIndex = $trs.length - 1;
}

//重設Status table資訊
function resetStatusTable($trs){
	$trs.each(function(index) {
		$(this).find(':hidden:eq(0)').attr("name", 'flowProfStatusList[' + index + '].id');
		$(this).find(':hidden:eq(1)').attr("name", 'flowProfStatusList[' + index + '].stageId');
		$(this).find(':hidden:eq(2)').attr("name", 'flowProfStatusList[' + index + '].approveStatus');
		$(this).find(':hidden:eq(3)').attr("name", 'flowProfStatusList[' + index + '].stageStatus');
		
		if(index%2 == 0){
			$(this).removeClass("odd even").addClass("odd");
		} else{
			$(this).removeClass("odd even").addClass("even");
		}
	});
	currentIndex = $trs.length - 1;
}

//檢查是否有審核中審件
function checkFlowStatus(flowType){
	var result = false;
	$.ajax({
	    url: "/APCSM/system/flow/checkFlowStatus",
	    type: "POST",		    
	    dataType: "json",
	    data: {
	    	"flowType": flowType
	    },
	    async: false,
	    success: function(dataMap) {
	    	result =  dataMap.result;
	    }	    
	});
	return result;
}
