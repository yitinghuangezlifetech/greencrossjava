$(function() {

//	$("#disList").toSuperTable({ width: "98%", fixedCols: 1 });
	
	// 第二關初始
	$(".opinion").each(function(){
		if($(this).val() != ""){
			$(this).parents("tr").find(".opinionMemo").removeAttr("readonly");
			$(this).parents("tr").find(".supportAmt").removeAttr("readonly");
		}		
	});
	
	// 重填
	$("#resetBtn").click(function(){
		$(".searchForm").val("");
	});
	
	// 查詢
	$("#btn_data_search").click(function(){
		$("#thisForm").attr("action","/APCSM/financial/acct06Cos/query");
		$("#thisForm").submit();
		viewLoading();
	});
	
	// 列印全選
	$(document).on("click",".chkAll",function () {
		$(".chkOne").prop("checked",$(this).prop("checked"));
	});
	$(document).on("click",".chkOne",function () {
		if(!$(this).prop("checked")){
			$(".chkAll").prop("checked",$(this).prop("checked"));
		}
	});
	
	// 匯出(查詢)
	$("#btn_export").click(function(){
		$("#thisForm").attr({"action":"/APCSM/financial/acct06Cos/exportExcelAll"}).submit();
	});
	
	// 列印(審核)
	$("#btn_export_audit").click(function(){
		if($(".chkOne:checked").is(":checked")){
			var idList = "";
			$.each( $( ".chkOne:checked" ) , function(i, option) {
	    		var ID =  $(this).attr("value");
	    		idList +=ID+",";
			});
			window.location.href = "/APCSM/financial/acct06Cos/exportExcel?idList="+idList;
		}
		else{
			dialogMessage("請勾選要匯出的項目");
		}
	});
	
	// 審核
	$("#btn_audit").click(function(){
		if(checkThisForm2()){
			$("#thisForm2").attr("action","/APCSM/financial/acct06Cos/merge");
			$("#thisForm2").submit();
			viewLoading();
		}
	});
	
	// 「意見」、「收費金額」依審核結果選單所選變動
	$(document).on("change",".opinion",function () {
		var opinionMemo = $(this).parents("tr").find(".opinionMemo");  //意見
		var supportAmt = $(this).parents("tr").find(".supportAmt");  //收費金額
		var supportAmtOriginal = $(this).parents("tr").find(".supportAmtOriginal");  //原始收費金額
		var amtTotalDiff = $(this).parents("tr").find(".amtTotalDiff");  //差異金額
		
		if($(this).val() == "M"){  //修改
			opinionMemo.removeAttr("readonly");
			supportAmt.removeAttr("readonly");
			//realAcceptAmt.text("");  
			//realAcceptAmtAverage.text(""); 
			amtTotalDiff.text("");
		}
		else if($(this).val() == "P" || $(this).val() == "C"){  //通過
			supportAmt.val(supportAmtOriginal.val());  //調整收費金額=原始收費金額
			opinionMemo.val("");
			opinionMemo.prop("readonly",true);
			supportAmt.prop("readonly",true);
		}
		else if($(this).val() == ""){  //請選擇
			opinionMemo.val("");
			supportAmt.val("");
			opinionMemo.prop("readonly",true);
			supportAmt.prop("readonly",true);
		}
	});
	
	// 檢查調整收費金額不可小於0
	$(document).on("change",".supportAmt",function(){
		if($(this).val() < 0){
			$(this).val("");
			dialogMessage("金額不可小於0");
		}
	});
	
	// 選TEAM帶出CM
	$(document).on("change","#teamId",function(){
		 $("option:selected",this).each(function(){
		    $("#cmId option").remove();
		    $.ajax({
		        url: "/APCSM/financial/acct06Cos/getCmByTeam",
		        data: {"teamId" :this.value},
		        type: "post",
		        async: false, //啟用同步請求
		        success: function (data) {
		        	$("#cmId").append('<option value="">請選擇</option>');
			        
		        	for(var i = 0 ; i < data.length ; i++){
		        		$("#cmId").append('<option value='+ data[i].CM_USER +'>'+data[i].CM_USER_NAME+'</option>');
		        	}
		        }
		    });
		 });
	});
	
	// 審核狀態「修改」，即時計算差異金額
	$(document).on("change",".supportAmt",function(){
		var supportAmt = $(this).parents("tr").find(".supportAmt").val();  //新收費金額
		var supportAmtOriginal = $(this).parents("tr").find(".supportAmtOriginal").val();  //原收費金額
		var amtTotalDiff = $(this).parents("tr").find(".amtTotalDiff");  //差異金額
		console.log("supportAmt:   "+supportAmt);
		console.log("supportAmtOriginal:   "+supportAmtOriginal);
		amtTotalDiff.text(supportAmt-supportAmtOriginal);
		console.log("amtTotalDiff:   "+amtTotalDiff.val());
	});
	
	// 檢查必填欄位
	function checkThisForm2(){
		var checkResult = true;
		var opinionCount = 0;
		var opinionNullCount = 0;
		$(".opinion").each(function(){
			if($(this).val() == "M"){
				var opinionMemo = $(this).parents("tr").find(".opinionMemo").val();
				var supportAmt = $(this).parents("tr").find(".supportAmt").val();
				if(supportAmt == ""){
					dialogMessage("審核狀態為「修改」，「收費金額」欄位必填，請輸入「收費金額」。");
					checkResult = false;
				}
				if(opinionMemo == ""){
					dialogMessage("審核狀態為「修改」，「意見」欄位必填，請輸入「意見」。");
					checkResult = false;
				}
			}
			if($(this).val() == ""){
				opinionNullCount++;
			}
			opinionCount++;
		});
		if(opinionNullCount == opinionCount){
			dialogMessage("請至少選擇一個審核項目的「審核狀態」進行審核。");
			checkResult = false;
		}
		return checkResult;
	}
	
});

