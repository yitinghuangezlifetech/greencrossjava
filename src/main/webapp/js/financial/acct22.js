$(function() {	
	
	// 重填
	$("#resetBtn").on("click", function(){
		$("input[type='text']").val("");
		$("input[type='hidden']").val("");
		$("select").val("");
	});
	
	// 查詢按鈕
	$("#btn_search").click(function(){
		
		if($("#actionDateStart").val() == "" && $("#actionDateEnd").val() != ""){
			dialogShortMesg("請選擇檔期(起)");
		}
		else if($("#actionDateStart").val() != "" && $("#actionDateEnd").val() == ""){
			dialogShortMesg("請選擇檔期(迄)");
		}
		else if($("#actionDateEnd").val() < $("#actionDateStart").val()){
			dialogShortMesg("檔期(迄)必須大於(起)");
		}
		
		else{
			$("#isConfirm").val("Y");
			$("#thisForm").attr("action","/APCSM/financial/acct22/query");
			$("#thisForm").submit();
			viewLoading();
		}		
	}); 
});
