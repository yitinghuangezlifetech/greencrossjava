$(function(){
	
	// 設定按鈕
	$("#btn_data_save").on("click", function(){
		if(checkForm()){
			var thisForm = document.getElementById("thisForm");
			var formData = new FormData(thisForm);
			$.ajax({
				url: "/APCSM/financial/acct22Exclude/checkStore",
				data: formData,
			    cache: false,
			    contentType: false,
			    processData: false,
			    type: "POST",
				success: function(data) {				
					if(data.msg == "storeNotFound"){
						dialogShortMesg("無此門市，請重新輸入");
						$("#storeId").val("");
					}
					else{
						if(data.msg != "storeNotSet"){
							$("#profExcludeId").val(data.msg);
						}
						$("#thisForm").attr("action","/APCSM/financial/acct22Exclude/setExclude");
						$("#thisForm").submit();
						$("#storeId").val("");
						viewLoading();
					}
				}
			});
		}
	});
});

// 檢查日期
function checkForm(){
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var storeid = $("#storeid").val();
	
	if(storeid == ""){
		dialogShortMesg("請選擇門市");
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
function editExclude(type, code, startDate, endDate, id){
	$("#startDate").val(startDate);
	$("#endDate").val(endDate);
	$("#profExcludeId").val(id);
	if($("#storeid").val() != code){
		$("#storeid option[value="+code+"]").removeAttr("selected").attr("selected", "selected");
	}
}

// 刪除按鈕
function deleteExclude(type, code, id){
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
	if($("#storeid").val() != code){
		$("#storeid option[value="+code+"]").removeAttr("selected").attr("selected", "selected");
	}
	$("#thisForm").attr("action","/APCSM/financial/acct22Exclude/deleteExclude");
	$("#thisForm").submit();
	$("#storeid").val("");
	viewLoading();	
}
