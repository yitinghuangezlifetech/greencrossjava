$(function() {
	// 第二關初始
	$(".outStockStatus").each(function(){
		var outStockStatus = $(this).val();
		if(outStockStatus == 'Y' || outStockStatus == 'S' || outStockStatus == 'R'){
			$(this).parents("tr").find(".opinion").removeAttr("disabled");  //CM申訴原因
			$(this).parents("tr").find(".opinionMemo").removeAttr("disabled");  //CM申訴說明
			$(this).parents("tr").find(".txtrealAcceptAmt").removeAttr("disabled");  //調整罰款金額
		}
	});
	
	//「調整罰款金額」需依「預計罰款項目」而變動
	$(document).on("change",".outStockStatus",function () {
		var txtrealAcceptAmt = $(this).parents("tr").find(".txtrealAcceptAmt");  //調整罰款金額(輸入框)
		var realAcceptAmt = $(this).parents("tr").find(".realAcceptAmt");  
		var rebateAmt = $(this).parents("tr").find(".rebateAmt");  //應罰金額(hidden)
		var opinion = $(this).parents("tr").find(".opinion");  //CM申訴原因
		var opinionMemo = $(this).parents("tr").find(".opinionMemo");  //CM申訴說明
		
		if($(this).val() == 'Y'){  //不罰款
			txtrealAcceptAmt.val("0");
			realAcceptAmt.val("0");
			txtrealAcceptAmt.prop("readonly",true);
			opinion.removeAttr("disabled");
			opinionMemo.removeAttr("disabled");
		}else if($(this).val() == 'S' || $(this).val() == 'R'){  //部分罰款(S)、資源交換(R)			
			txtrealAcceptAmt.val("");
			realAcceptAmt.val("");
			txtrealAcceptAmt.removeAttr("readonly");
			opinion.removeAttr("disabled");
			opinionMemo.removeAttr("disabled");
		}else if($(this).val() == 'N'){  //全部罰
			txtrealAcceptAmt.val(rebateAmt.val());
			realAcceptAmt.val(rebateAmt.val());			
			opinion.val("");
			opinionMemo.val("");
			txtrealAcceptAmt.prop("readonly",true);
			opinion.prop("disabled",true);
			opinionMemo.prop("disabled",true);
		}
		else{  //請選擇
			txtrealAcceptAmt.val("");
			realAcceptAmt.val("");			
			opinion.val("");
			opinionMemo.val("");
			txtrealAcceptAmt.prop("readonly",true);
			opinion.prop("disabled",true);
			opinionMemo.prop("disabled",true);
		}
	});
	
	// 實罰金額
	$(document).on("change",".txtrealAcceptAmt",function () {
		var txtrealAcceptAmt = parseInt($(this).val()); // 實罰金額
		var rebateAmt = parseInt($(this).parent().find(".rebateAmt").val()); // 應罰金額
		$(this).parent().find(".realAcceptAmt").val(txtrealAcceptAmt);
		if(txtrealAcceptAmt > rebateAmt || txtrealAcceptAmt < 1){
			dialogMessage("實罰金額不可大於應罰金額或小於1",false);
			$(this).val(rebateAmt);
			$(this).parent().find(".realAcceptAmt").val(rebateAmt);
		}
	});
	
	// 審核全選
	$(document).on("click",".chkAll",function () {
		$(".chkOne").prop("checked",$(this).prop("checked"));
	});
	$(document).on("click",".chkOne",function () {
		if(!$(this).prop("checked")){
			$(".chkAll").prop("checked",$(this).prop("checked"));
		}
	});	
	
	//重填
	$("#resetBtn").on("click", function(){
		$(".searchForm").val("");
	});
	//查詢按鈕
	$("#btn_search").click(function(){
		$("#thisForm").attr("action","/APCSM/financial/acct35Cos/query");
		thisFormSubmit();
	}); 

	//匯出
	$("#btn_exportAll").click(function(){
		$("#thisForm").attr({"action":"/APCSM/financial/acct35Cos/exportExcelAll"}).submit();
	});
	
	//列印按鈕
	$("#btn_export").click(function(){
		if($(".chkOne:checked").is(":checked")){
			var idList = "";
			$.each( $( ".chkOne:checked" ) , function(i, option) {
	    		var ID =  $(this).attr("value");
	    		idList +=ID+",";
			});
			window.location.href = "/APCSM/financial/acct35Cos/exportExcel?idList="+idList;
		}
		else{
			dialogMessage("請勾選要匯出的項目");
		}
	});
	
	//審核按鈕
	$("#btn_audit").click(function(){
		if(checkThisForm2()){
			$(".opinion").removeAttr("disabled");
			$(".opinionMemo").removeAttr("disabled");
			$("#thisForm2").attr("action","/APCSM/financial/acct35Cos/goAudit");
			$("#thisForm2").submit();
			viewLoading();
		}
	});
	
	function thisFormSubmit(){
		$("#thisForm").submit();
		viewLoading();
	}
	
	//檢查必填欄位
	function checkThisForm2(){
		var checkResult = true;
		var outStockStatusCount = 0;
		var outStockStatusNullCount = 0;
		$(".outStockStatus").each(function(){
			var outStockStatus = $(this).val();
			if(outStockStatus == "S" || outStockStatus == "R"){  //部分罰款、資源交換
				if(txtrealAcceptAmt = $(this).parents("tr").find(".txtrealAcceptAmt").val() == ""){
					dialogMessage("請輸入「調整罰款金額」。");
					checkResult = false;
				}
			}
			if(outStockStatus == "S" || outStockStatus == "Y" || outStockStatus == "R"){  //部分罰款、不罰款、資源交換
				var opinion = $(this).parents("tr").find(".opinion").val();  //CM申訴原因
				var opinionMemo = $(this).parents("tr").find(".opinionMemo").val();  //CM申訴說明				
				if(opinionMemo == ""){
					dialogMessage("請輸入「CM申訴說明」。");
					checkResult = false;
				}
				if(opinion == "" ){
					dialogMessage("請輸入「CM申訴原因」。");
					checkResult = false;
				}
			}
			if($(this).val() == ""){
				outStockStatusNullCount++;
			}
			outStockStatusCount++;
		});	
		if(outStockStatusNullCount == outStockStatusCount){
			dialogMessage("請至少選擇一個項目審核的「預計罰款項目」進行審核。");
			checkResult = false;
		}
		return checkResult;
	}
	
	//送出審核
//	$("#btn_submit").click(function(){
//		if(checkFormData("thisForm")){
//			$("#thisForm").attr("action","/APCSM/financial/acct35Cos/merge");
//			thisFormSubmit();
//		}
//	});
});

