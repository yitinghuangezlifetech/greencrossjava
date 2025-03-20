$(function() {

//	$("#disList").toSuperTable({ width: "98%", fixedCols: 1 });
	
	// 第二關初始
	$(".opinion").each(function(){
		if($(this).val() != ""){
			$(this).parents("tr").find(".opinionMemo").removeAttr("readonly");
			$(this).parents("tr").find(".supportAmt").removeAttr("readonly");
		}		
	});
	
	// 查詢按鈕
	$("#btn_data_search").click(function(){
		
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
			$("#thisForm").attr("action","/APCSM/financial/acct22Cos/query");
			$("#thisForm").submit();
			viewLoading();
		}		
	});
	
	// 重填
	$("#resetBtn").click(function(){
		$(".searchForm").val("");
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
	
	// 列印
	$("#btn_export_audit").click(function(){
		if($(".chkOne:checked").is(":checked")){
			var idList = "";
			$.each( $( ".chkOne:checked" ) , function(i, option) {
	    		var ID =  $(this).attr("value");
	    		idList +=ID+",";
			});
			window.location.href = "/APCSM/financial/acct22Cos/exportExcel?idList="+idList;
		}
		else{
			dialogMessage("請勾選要匯出的項目");
		}
	});
	
	// 匯出
	$("#btn_export").click(function(){
		$("#thisForm").attr({"action":"/APCSM/financial/acct22Cos/exportExcelAll"}).submit();
	});
	
	// 審核
	$("#btn_audit").click(function(){
		if(checkThisForm2()){
			$("#thisForm2").attr("action","/APCSM/financial/acct22Cos/merge");
			$("#thisForm2").submit();
			viewLoading();
		}		
	});
	
	// 「修改說明」、「調整收費金額」依選單所選變動
	$(document).on("change",".opinion",function () {
		var opinionMemo = $(this).parents("tr").find(".opinionMemo");  //修改說明
		var supportAmt = $(this).parents("tr").find(".supportAmt");  //調整收費金額
		var supportAmtOriginal = $(this).parents("tr").find(".supportAmtOriginal");  //原始收費金額
		var rebateAmt = $(this).parents("tr").find(".rebateAmt");  //原收費總金額
		var realAcceptAmt = $(this).parents("tr").find(".realAcceptAmt");  //新收費總金額
		var rebateAmtAverage = $(this).parents("tr").find(".rebateAmtAverage");  //原平均店收費
		var realAcceptAmtAverage = $(this).parents("tr").find(".realAcceptAmtAverage");  //新平均店收費
		var amtTotalDiff = $(this).parents("tr").find(".amtTotalDiff");  //差異金額
		var role=$("role").val();//角色
		if($(this).val() == "M"){  //修改
			if(role == "C12"){
				//採購
				realAcceptAmt.text("");  
				realAcceptAmtAverage.text(""); 
				amtTotalDiff.text("");
			}
			opinionMemo.removeAttr("readonly");
			supportAmt.removeAttr("readonly");
			
		}
		else if($(this).val() == "P" || $(this).val() == "C"){  //通過
			if(role == "C12"){
				//採購
				supportAmt.val(supportAmtOriginal.val());  //調整收費金額=原始收費金額
				realAcceptAmt.text(rebateAmt.val());  //新收費總額=原收費總額
				realAcceptAmtAverage.text(rebateAmtAverage.val());  //新平均店收費=原平均店收費
				opinionMemo.val("");
			}
			opinionMemo.prop("readonly",true);
			supportAmt.prop("readonly",true);
		}
		else if($(this).val() == ""){  //請選擇
			if(role == "C12"){
				//採購
				opinionMemo.val("");
				supportAmt.val(supportAmtOriginal.val());
				realAcceptAmt.text("");
				realAcceptAmtAverage.text("");
			}
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
		        url: "/APCSM/financial/acct22Cos/getCmByTeam",
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
	
	// 審核狀態「修改」，即時計算新收費總額、新平均店收費
	$(document).on("change",".supportAmt",function(){
		var supportAmt = $(this).parents("tr").find(".supportAmt").val();  //調整收費金額
		var acct22Id = $(this).parents("tr").find(".acct22Id").val();  //ID
		var realAcceptAmt = $(this).parents("tr").find(".realAcceptAmt");  //新收費總金額
		var amtTotalDiff = $(this).parents("tr").find(".amtTotalDiff");  //差異金額
		var realAcceptAmtAverage = $(this).parents("tr").find(".realAcceptAmtAverage");  //新平均店收費
		
		var formData = new FormData();
		formData.append("supportAmt", supportAmt);
		formData.append("acct22Id", acct22Id);
		
		if(supportAmt != ""){
			$.ajax({
				url: "/APCSM/financial/acct22Cos/calculate",
				data: formData,
			    cache: false,
			    contentType: false,
			    processData: false,
			    type: "POST",
				success: function(data) {
					realAcceptAmt.text(data.realAcceptAmtCal);
					amtTotalDiff.text(data.amtTotalDiffCal);
					realAcceptAmtAverage.text(data.realAcceptAmtAverageCal);
				}
			});	
		}			
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
					dialogMessage("審核狀態為「修改」，「調整收費金額」欄位必填，請輸入「調整收費金額」。");
					checkResult = false;
				}
				if(opinionMemo == ""){
					dialogMessage("審核狀態為「修改」，「修改說明」欄位必填，請輸入「修改說明」。");
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
	
	//批次選擇
//	$("select[name=flowStatusListTitle]").change(function () {
//		var titleVal = $(this).val().split("@");
//		$("select[name='acct22.acct22Status'] option").each(function () {
//			var listVal = $(this).val().split("@");
//			if(listVal[0] == titleVal[0]){
//				$(this).prop("selected",true);
//			}
//		});
//	});

	// 實罰金額
//	$(document).on("change",".txtsupportAmt",function () {
//		var index = $(this).parents("tr").index();
//		var state = $(this).parents("tr").find("select[name='acct22.acct22Status']").val();
//		var txtsupportAmt = parseInt($(this).val()); // 實罰金額
//		var supportAmtOri = parseInt($(this).parent().find(".supportAmtOri").val()); // 應罰金額
//		$(this).parent().find(".supportAmt").val(txtsupportAmt);
//		$(".sFData table tbody tr:eq("+index+") .supportAmt").val(txtsupportAmt);

//		if(state[0] == 'M'){
//			if(txtsupportAmt > supportAmtOri || txtsupportAmt < 1){
//				dialogMessage("部份罰款的實罰金額不可大於原始金額或小於1",false);
//				$(this).val(supportAmtOri);
//				$(this).parent().find(".supportAmt").val(supportAmtOri);
//				$(".sFData table tbody tr:eq("+index+") .supportAmt").val(supportAmtOri);
//			}
//		}
//	});
	//意見文字框更改後將值一併更改至被凍結的相同欄位
//	$(document).on("change",".opinionMemo",function () {
//		var index = $(this).parents("tr").index();
//		$(".sFData table tbody tr:eq("+index+") .opinionMemo").val($(this).val()); // 將未凍結的相同欄位一併修改，以利後端傳值使用
//	});

//	function chgflowStatus (element) {
//		var index = element.parents("tr").index();
//		var supportAmt = element.parents("tr").find(".supportAmt");
//		var supportAmtOti = element.parents("tr").find(".supportAmtOri");
//		var state = element.val().split("@");
//		var txtsupportAmt = $(".sData table tbody tr:eq("+index+") .txtsupportAmt");

//		if(state[0] == 'M'){
//			txtsupportAmt.removeAttr("disabled");
//		}else if(supportAmtOti.val() > 0){
//			txtsupportAmt.val(supportAmtOti.val());
//			supportAmt.val(supportAmtOti.val());
//			txtsupportAmt.prop("disabled",true);
//		}
//	}
});

