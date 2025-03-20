$(function() {
	
	//SuperTable
	$("#disList").toSuperTable({ width: "98%", height: "600px", fixedCols: 1 }); 
	
	// 重填
	$("#resetBtn").on("click", function(){
		$("input[type='text']").val("");
		$("input[type='hidden']").val("");
		$("select").val("");
	});
	//查詢按鈕
	$("#btn_search").click(function(){
		$("#thisForm").attr("action","/APCSM/financial/acct39/query");
		$("#thisForm").submit();
		viewLoading();
	}); 
});