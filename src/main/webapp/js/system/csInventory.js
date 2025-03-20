$(document).ready(function(){
		
	// 查詢按鈕   
	$("#btn_data_search").on("click", function(){
		$("#thisForm").attr("action","/APCSM/system/getCsInventoryList");
	  	$("#thisForm").submit();
	  	viewLoading();
	});
	
	// 重填按鈕
	$("#resetBtn").on("click", function(){
		$("input").val("");
	});
	
	// 新增上傳盤點檔按鈕
	$("#uploadBtn").on("click",function(){
		$("#thisForm").attr("action","/APCSM/system/csInventory/goUpload");
		$("#thisForm").submit();
		viewLoading();		
	});
	
	// 檢查上傳檔案
	$("#uploadFile").change(function(){
		if($("#storeNo").val() != ""){
			checkFileName();
			checkFileName2();
		}
		else{
			checkFileName();
		}
	});	
	$("#storeNo").change(function(){
		if($("#uploadFile").val() != ""){
			checkFileName2();
		}		
	});	
	
	// 上傳盤點檔按鈕
	$("#uploadFileBtn").on("click",function(){
		var checkFormRes = checkFormData("thisForm",false);
		
		var hourz = (new Date()).getHours();
		var minutez = (new Date()).getMinutes();
		
		if((hourz==18 && minutez>=50) || (hourz==19 && minutez<=5)) {
			dialogShortMesg('現在正在將盤點檔案傳送給NEC，請於19:10後在做上傳，謝謝！');
			return;
		}else if(checkFormRes){
//			$("#thisForm").attr("action","/APCSM/system/csInventory/uploadCsInventory");
//			$("#thisForm").submit();
//			viewLoading();	
			
			var fullPath = $("#uploadFile").val();
			var startIndex = (fullPath.indexOf("\\") >= 0 ? fullPath.lastIndexOf("\\") : fullPath.lastIndexOf("/"));
			var fileName = fullPath.substring(startIndex);
			fileName = fileName.substring(1);
			var fileSplitName = fileName.split("_");
			var thisForm = document.getElementById("thisForm");
			var formData = new FormData(thisForm);
			var dialog_buttons = {}; 
			
			$.ajax({
				url: "/APCSM/system/csInventory/checkStoreFile",
				data: formData,
			    cache: false,
			    contentType: false,
			    processData: false,
			    type: "POST",
				success: function(data) {
					if(data.msg != "fileNotFound"){
						dialog_buttons["確定"] = function(){
							$(this).dialog( "close" );
							uploadFile();
						};
						dialog_buttons["取消"] = function(){
							$(this).dialog( "close" );
						};
						dialogConfirm("店號："+fileSplitName[0]+"<br>日期："+fileSplitName[1]+"<br>盤點檔已上傳過，若再上傳則會刪除原檔以本次上傳的為主，是否確認上傳？",dialog_buttons);
					}
					else{
						uploadFile();
					}
				}
			});
		}
	});
	
	//門市 輸入框 清空
	$("#allStoreTags").change(function () {
		if($(this).val().trim() == ""){
			$("#storeNo").val("");
		}
	});
	
	// 門市下拉 autocomplete
	$("#allStoreTags").autocomplete({
	    source: function(request, response) {
	    	var thisForm = document.getElementById("thisForm");
	    	var formData = new FormData(thisForm);
	    	formData.append("storeTag", request.term.toLowerCase());
	    	$.ajax({
	    	    url: "/APCSM/system/csInventory/getStoreList",
	    	    data: formData,
	    	    cache: false,
	    	    contentType: false,
	    	    processData: false,
	    	    type: "POST",
	    	    success: function(data) {
	    	    	if (data.msg == "") {
	    	    		var choices = [];
	    	    		var items = data.allStore;
	    		    	$.each(items, function (i, item) {
	    		    		choices.push({ 
	    		    			label : item.STOREID + "-" + item.STORENM,
	    		    	        value: item.STOREID
	    		    	    });
	    		    	});
	    		        response(choices);
	    	    	} else {
	    	    		dialogShortMesg(data.msg);
	    	    	}
	    	    }
	    	});
	    },
	    select: function (event, ui) {
	    	$("#allStoreTags").val(ui.item.label);	    	
	    	$("#storeNo").val(ui.item.value);
	    	if($("#uploadFile").val() != ""){
	    		checkFileName2();
	    	}
	    	return false;
	    }
	});
});

// 檢查檔案名稱(店號除外)
function checkFileName(){
	var fullPath = $("#uploadFile").val();
	var startIndex = (fullPath.indexOf("\\") >= 0 ? fullPath.lastIndexOf("\\") : fullPath.lastIndexOf("/"));
	var fileName = fullPath.substring(startIndex);
	fileName = fileName.substring(1);
	$("#csInventoryFileName").val(fileName);
	
	if(fullPath != ""){
		var fileLowerName = fileName.toLowerCase();
		if(!fileLowerName.endsWith(".txt")){
			dialogShortMesg("盤點檔只接受格式為.txt的檔案");
			$("#uploadFile").val("");
		}
		else if(fileName.toLowerCase().split("_").length != 3){
			dialogShortMesg("正確盤點檔名稱為：<br>八碼的門市代碼_盤點日期_資料筆數.txt");
			$("#uploadFile").val("");
		}
		else if(fileName.toLowerCase().split(".").length != 2){
			dialogShortMesg("正確盤點檔名稱為：<br>八碼的門市代碼_盤點日期_資料筆數.txt");
			$("#uploadFile").val("");
		}
		else {
			var fileSplitName = fileName.split("_");
			$("#csInventoryIvtDate").val(fileSplitName[1]);
			$("#csInventoryItemsNum").val(fileSplitName[2]);
			
			var nowDay = new Date();
			var month = (nowDay.getMonth()+1);
			month = ((month<10) ?('0'+month):(''+month));
		    var date = nowDay.getDate();
		    date = ((date<10) ?('0'+date):(''+date));
			var today = (nowDay.getFullYear()+''+month+''+date);
			
			var previouz = new Date();
			previouz.setDate((previouz.getDate())-3);
			var previousMonth = (previouz.getMonth()+1);
			previousMonth = ((previousMonth<10) ?('0'+previousMonth):(''+previousMonth));
		    var previousDate = previouz.getDate();
		    previousDate = ((previousDate<10) ?('0'+previousDate):(''+previousDate));
			var previousDay = (previouz.getFullYear()+''+previousMonth+''+previousDate);

			if(fileSplitName.length != 3){
				dialogShortMesg("正確盤點檔名稱為：<br>八碼的門市代碼_盤點日期_資料筆數.txt");
				$("#uploadFile").val("");
			}
			else if(fileSplitName[1].length != 8){
				dialogShortMesg("正確盤點檔名稱日期部分為：yyyyMMdd");
				$("#uploadFile").val("");
			}
			else if(fileSplitName[1] > today){
				dialogShortMesg("無法上傳本日("+today+")後的盤點檔，請重新確認後再傳");
				$("#uploadFile").val("");
			}
			else if(fileSplitName[1] <= previousDay){
				dialogShortMesg("無法上傳三天("+previousDay+")前的盤點檔，請重新確認後再傳");
				$("#uploadFile").val("");
			}
			else{
				checkItemsNum();	
			}
		}
	}
}

//檢查檔案名稱(店號是否一致)
function checkFileName2(){
	var fullPath = $("#uploadFile").val();
	var startIndex = (fullPath.indexOf("\\") >= 0 ? fullPath.lastIndexOf("\\") : fullPath.lastIndexOf("/"));
	var fileName = fullPath.substring(startIndex);
	fileName = fileName.substring(1);
	
	if(fullPath != ""){
		var fileSplitName = fileName.split("_");
		var storeId = $("#storeNo").val();
		$("#csInventoryIvtDate").val(fileSplitName[1]);
		$("#csInventoryItemsNum").val(fileSplitName[2]);

		if(fileSplitName[0] != storeId){
			dialogShortMesg("所選門市("+storeId+")與上傳檔案店號("+fileSplitName[0]+")不符，請重新確認後再上傳");
			$("select").prop('selectedIndex', 0);
			$("#uploadFile").val("");
		}
	}	
	
}

// 檢查筆數是否一致
function checkItemsNum(){
	var count = $("#csInventoryItemsNum").val().split(".")[0];  //檔名筆數
	var thisForm = document.getElementById("thisForm");
	var formData = new FormData(thisForm);
	$.ajax({
		url: "/APCSM/system/csInventory/checkItemsNum",
		data: formData,
	    cache: false,
	    contentType: false,
	    processData: false,
	    type: "POST",
		success: function(data) {
			if(data.msg <= 0){
				dialogShortMesg("檔案內容無資料，請重新確認後再傳");
		    	$("#uploadFile").val("");
			}
			else if(data.msg != count){
				dialogShortMesg("檔名筆數("+count+")與實際筆數("+data.msg+")不一致，請重新確認後再傳");
		    	$("#uploadFile").val("");
			}	    			    	
		}
	});
}

// 上傳檔案
function uploadFile(){
	var thisForm = document.getElementById("thisForm");
	var formData = new FormData(thisForm);
	$.ajax({
		url: "/APCSM/system/csInventory/uploadCsInventory",
		data: formData,
	    cache: false,
	    contentType: false,
	    processData: false,
	    type: "POST",
		success: function(data) {				
			$("input").val("");			
			dialogShortMesg(data.msg);
		}
	});
}
