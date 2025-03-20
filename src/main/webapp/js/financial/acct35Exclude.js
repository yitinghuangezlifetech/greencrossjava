$(document).ready(function(){
	
	// 設定按鈕
	$("#btn_data_save").on("click", function(){
		var type = $("#type").val();
		if(checkForm()){
			var thisForm = document.getElementById("thisForm");
			var formData = new FormData(thisForm);
			$.ajax({
				url: "/APCSM/financial/outStockSupCode/checkSupCode",
				data: formData,
			    cache: false,
			    contentType: false,
			    processData: false,
			    type: "POST",
				success: function(data) {				
					if(type =="supCode"){
						if(data.msg == "supCodeNotFound"){
							dialogShortMesg("無此廠編，請重新輸入");
							$("#supplierIdTags").val("");
							$("#supplierId").val("");
						}
						else{
							if(data.msg != "supCodeNotFound" && data.msg != "supCodeNotSet"){
								$("#profExcludeId").val(data.msg);
							}
							$("#thisForm").attr("action","/APCSM/financial/setOutStockSupCode");
							$("#thisForm").submit();
							$("#supplierIdTags").val("");
							$("#supplierId").val("");
							viewLoading();
						}
					}
					else if(type == "category"){
						if(data.msg != "categoryCodeNotSet"){
							$("#profExcludeId").val(data.msg);
						}
						$("#thisForm").attr("action","/APCSM/financial/setOutStockCategory");
						$("#thisForm").submit();
						viewLoading();
					}	
				}
			});
		}
	});
});

// 檢查廠編
function checkSupCode(){
	var thisForm = document.getElementById("thisForm");
	var formData = new FormData(thisForm);
	$.ajax({
		url: "/APCSM/financial/outStockSupCode/checkSupCode",
		data: formData,
	    cache: false,
	    contentType: false,
	    processData: false,
	    type: "POST",
		success: function(data) {
			if(data.msg == "supCodeNotFound"){
				dialogShortMesg("無此廠編，請重新輸入");
				$("#supplierIdTags").val("");
				$("#supplierId").val("");
			}
		}
	});
}

// 檢查日期
function checkForm(){
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var supplierIdTags = $("#supplierIdTags").val();
	var categoryCode = $("#categoryCode").val();
	var type = $("#type").val();
	
	if(type == "supCode" && supplierIdTags == ""){
		dialogShortMesg("請輸入廠編");
		return false;
	}
	else if(type == "category" && categoryCode == ""){
		dialogShortMesg("請選擇大類");
		return false;
	}
	else if(startDate != "" && endDate ==""){
		dialogShortMesg("請輸入訖日");
		return false;
	}
	else if(startDate == "" && endDate != ""){
		dialogShortMesg("請輸入起日");
		return false;
	}
	else if(startDate != "" && endDate != "" && endDate < startDate){
		dialogShortMesg("訖日("+endDate+")必須大於起日("+startDate+")");
		return false;
	}
	else{
		return true;
	}
}

// 修改按鈕
function editOutStock(type, code, startDate, endDate, id){
	$("#startDate").val(startDate);
	$("#endDate").val(endDate);
	$("#profExcludeId").val(id);
	if(type == "category"){
		if($("#categoryCode").val() != code){
			$("#categoryCode option[value="+code+"]").removeAttr("selected").attr("selected", "selected");
		}
	}
	else{
		$("#supplierIdTags").val(code);
	}
}

// 刪除按鈕
function deleteOutStock(type, code, id){
	var dialog_buttons = {}; 
	
	dialog_buttons["確定"] = function(){
		$(this).dialog( "close" );
		goDelete(type, code, id);
	};
	dialog_buttons["取消"] = function(){
		$(this).dialog( "close" );
	};
	dialogConfirm("是否確認刪除",dialog_buttons);
}

// 刪除
function goDelete(type, code, id){
	$("#profExcludeId").val(id);
	if(type == "supCode"){
		$("#supplierId").val(code);		
	}
	else if(type == "category"){
		if($("#categoryCode").val() != code){
			$("#categoryCode option[value="+code+"]").removeAttr("selected").attr("selected", "selected");
		}
	}
	$("#thisForm").attr("action","/APCSM/financial/deleteOutStock");
	$("#thisForm").submit();
	$("#supplierIdTags").val("");
	$("#supplierId").val("");
	viewLoading();	
}
