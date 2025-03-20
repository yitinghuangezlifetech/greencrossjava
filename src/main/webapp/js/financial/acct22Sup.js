$(function() {
	
	var dialog_buttons = {}; 
	dialog_buttons["確定"] = function(){
//		$("#thisForm").attr("action","/APCSM/quotation/newProductSup/remove?delId=" + id);
//		$("#thisForm").submit();
//		viewLoading();
		$(this).dialog("close");
	};
	dialog_buttons["取消"] = function(){
		
	};   
	dialogConfirm("確定要刪除嗎？", dialog_buttons);

	// 申訴button是否顯示
	if($("input[type=checkbox]").length == 0){
		$("#btn_appeal").css("display","none");
	}else{
		$("#btn_appeal").css("display","block");
	}
	// 申訴全選
	$(document).on("click",".chkAll",function () {
		$(".sFData .chkOne").prop("checked",$(this).prop("checked"));
	});
	$(document).on("click",".chkOne",function () {
		if(!$(this).prop("checked")){
			$(".chkAll").prop("checked",$(this).prop("checked"));
		}
	});
	
	//SuperTable
	if($("#disList").length > 0){
		$("#disList").toSuperTable({ width: "98%", fixedCols: 1 });
	}
	
	//重填
	$("#resetBtn").on("click", function(){console.log("123456789");
		$("input[type='text']").val("");
		$("select").val("");
	});
	//查詢按鈕
//	$("#btn_search").click(function(){
//		$("#thisForm").attr("action","/APCSM/financial/acct35Sup/query");
//		thisFormSubmit();
//	}); 
	//申訴
	$("#btn_appeal").click(function(){
		if($("input[type=checkbox]").is(":checked")){
			$("#thisForm").attr("action","/APCSM/financial/acct35Sup/goAppeal");
			thisFormSubmit();
		}else{
			dialogMessage("請勾選至少一項欲申訴的項目");
		}
	});
	//申訴內容
	$(".appeal").click(function(){
		$("#docId").val($(this).attr("docId"));
		$("#thisForm").attr("action","/APCSM/financial/acct35Sup/goAppeal");
		thisFormSubmit();
	});
	//儲存
	$("#btn_submit").click(function(){
		if(checkFormData("thisForm")){
			$("#thisForm").attr("action","/APCSM/financial/acct35Sup/merge");
			thisFormSubmit();
		}
	});
	
	function thisFormSubmit(){
		$("#thisForm").submit();
		viewLoading();
	}
});

