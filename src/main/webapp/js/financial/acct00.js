$(function() {
	
	//SuperTable
	$("#disList").toSuperTable({ width: "98%", fixedCols: 2 }); 
	
	if($("#nextSupportMonth").val() == 'N'){
		$("#nextMonth").attr("disabled","");
	}
	if($("#prevSupportMonth").val() == 'N'){
		$("#prevMonth").attr("disabled","");
	}

	/*下個月*/
	$("#nextMonth").on("click",function(){
		var date = chgMonth($("#supportMonth").val(),1,'next');
		$("#supportMonth").val(date);
		$("#lblsupportMonth").text(date);
		search();
	});
	
	/*上個月*/
	$("#prevMonth").on("click",function(){
		var date = chgMonth($("#supportMonth").val(),1,'prev');
		$("#supportMonth").val(date);
		$("#lblsupportMonth").text(date);
		search();
	});

	// 重填
	$("#resetBtn").on("click", function(){
		$("input[type='text']").val("");
		$("#supplierId").val("");
	});
	//查詢按鈕
	$("#btn_search").click(function(){
		search();
	}); 
	function search(){
		$("#thisForm").attr("action","/APCSM/financial/acct00/query");
		$("#thisForm").submit();
		viewLoading();
	}
});

