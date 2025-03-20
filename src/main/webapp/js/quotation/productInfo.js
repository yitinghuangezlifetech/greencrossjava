$(document).ready(function() {
	
	//SuperTable 有查到資料時才需要凍結視窗，不然會有script錯誤
	if($("#disList .empty").length == 0)
		$("#disList").toSuperTable({ width: "98%", fixedCols: 4 }); 
	
	//查詢
	$("#btn_search").on("click", function() {
		var suspendFromDate = $("#suspendFromDate").val();
		var suspendEndDate = $("#suspendEndDate").val();
		var checkOk = true;
		/*if(suspendFromDate != "" && suspendEndDate != "") {
			var startDate = suspendFromDate.substring(0, 4) + "/" + suspendFromDate.substring(4, 6) + "/" + suspendFromDate.substring(6, 8);
			var endDate = suspendEndDate.substring(0, 4) + "/" + suspendEndDate.substring(4, 6) + "/" + suspendEndDate.substring(6, 8);
			if(Date.parse(startDate).valueOf() > Date.parse(endDate).valueOf()) {
				dialogMessage("訂貨生效起日不得大於訂貨生效迄日");
				checkOk = false;
			}
		}*/
		if(checkOk) {
			$("#thisForm").attr("action", "/APCSM/quotation/productInfo/query");
			$("#thisForm").submit();
			viewLoading();
		}
	});

	//重填
	$("#resetBtn").on("click", function(){
		$("input").val("");
	});
	
	//上一頁
	$("Button[name='btn_back']").on("click", function(event) {
		btnBackClick();
	});
	
	//下一頁
	$("Button[name='btn_forward']").on("click", function(event) {
		btnForwardClick();
	});
	
	//進入明細頁
	$(".goDetail").click(function () {
		$("#qId").val($(this).attr("qId"));
		$("#thisForm").attr("action","/APCSM/quotation/productInfo/goDetail");
		$("#thisForm").submit();
		viewLoading();
		
	});
	
	//大分類 change
	$("#familyCode").change(function() {
		getClassCodeList();
	});
	
	//提報中類別 change
	$("#classCode").change(function() {
		getBrickCodeList();
	});

});

//點擊menuXX
function changeMenu(n) {
	$("#menuId").val("menu" + n);
}

//上一頁
function btnBackClick() {
	var menuId = $("#menuId").val();
	menuId = "menu" + (parseInt(menuId.replace("menu", "")) - 1);
	$("#menuId").val(menuId);
	setMenuClass();
}

//下一頁
function btnForwardClick() {
	var menuId = $("#menuId").val();
	menuId = "menu" + (parseInt(menuId.replace("menu", "")) + 1);
	$("#menuId").val(menuId);
	setMenuClass();
}

function setMenuClass() {
	var menuId = $("#menuId").val();
	for (i = 1; i <= 7; i++) {
		if (i == parseInt(menuId.replace("menu", ""))) {
			$("#menu" + i).removeAttr("class").addClass("tab-pane fade in active");
			$("#li" + i).removeAttr("class").addClass("active");			
		} else {
			$("#menu" + i).removeAttr("class").addClass("tab-pane fade");
			$("#li" + i).removeAttr("class").addClass("");
		}		
	}
}

//脫離系列
function btn_separateSeries(itemCode, flagMod){
	if(flagMod != "Y") {
		dialogMessage("此商品異動中不可脫離");
	} else {
		var dialog_buttons = {};
		dialog_buttons["確定"] = function(){
			$("#thisForm").attr("action","/APCSM/quotation/productInfo/separateSeries?itemCode=" + itemCode);
			$("#thisForm").submit();
			viewLoading();	
		};
		dialog_buttons["取消"] = function(){
			$(this).dialog("close");
		};   
		dialogConfirm("確定要脫離系列嗎？", dialog_buttons);
	}
}

//大分類 change
function getClassCodeList() {
	var formData = new FormData();
	formData.append("familyCode", $("#familyCode").val());
	$.ajax({
	    url: "/APCSM/quotation/productInfo/getClassCodeList",
	    data: formData,
	    cache: false,
	    contentType: false,
	    processData: false,
	    type: "POST",
	    success: function(data) {
	    	if (data.msg == "") {
	    		$("#classCode").empty();
		    	var items = data.classCodeList;
		    	$("#classCode").append($("<option>", { 
	    	        value: "",
	    	        text : "請選擇" 
	    	    }));
		    	$.each(items, function (i, item) {
		    	    $("#classCode").append($("<option>", { 
		    	        value: item.CATE_ID_2,
		    	        text : item.CATE_NAME_2 
		    	    }));
		    	});
	    	} else {
	    		dialogMessage(data.msg);
	    	}
	    }
	});
}


//提報次類別 change
function getBrickCodeList() {
	var formData = new FormData();
	formData.append("classCode", $("#classCode").val());
	$.ajax({
	    url: "/APCSM/quotation/newProductSup/getBrickCodeList",
	    data: formData,
	    cache: false,
	    contentType: false,
	    processData: false,
	    type: "POST",
	    success: function(data) {
	    	if (data.msg == "") {
	    		$("#brickCode").empty();
		    	var items = data.brickCodeList;
		    	$("#brickCode").append($("<option>", { 
	    	        value: "",
	    	        text : "請選擇" 
	    	    }));
		    	$.each(items, function (i, item) {
		    	    $("#brickCode").append($("<option>", { 
		    	        value: item.CATE_ID_3,
		    	        text : item.CATE_NAME_3 
		    	    }));
		    	});
	    	} else {
	    		dialogShortMesg(data.msg);
	    	}
	    }
	});
}