$(document).ready(function() {
	//查詢
	$("#btn_search").on("click", function() {
		$("#thisForm").attr("action", "/APCSM/quotation/newProductCos/query");
		$("#thisForm").submit();
		viewLoading();
	});
		
	//審核
	$("#btn_audit").on("click", function() {
		$("#thisForm").attr("action", "/APCSM/quotation/newProductCos/audit");
		$("#thisForm").submit();
		viewLoading();
	});
	
	
	//掃條碼
	$("#btn_barcode").on("click", function() {
		$("#thisForm").attr("action", "/APCSM/quotation/newProductCos/barcode");
		$("#thisForm").submit();
		viewLoading();
	});
	
	//暫存
	$("Button[name='btn_save_temp']").on("click", function(event) {
		if (!validate()) return false;	
		mergeTemp("saveTemp");
	});
	
	//列印新品協議書
	$("#btn_export_agree_market_report").on("click", function() {
//		if($("#querySupplierId").val() == '' && $("#queryBarcode1").val() == '' 
//			&& $("#queryItemName").val() == '' && $("#queryItemCode").val() == '' 
//			&& $("#quertstrdate").val() == '' && $("#queryenddate").val() == '' 
//			&& $("#queryInputDate").val() == '' && $("#querySaleEffectiveDate").val() == '') {
//			dialogShortMesg("查詢條件請擇一填寫");
//			return false;
//		} else {
			$("#thisForm").attr("action", "/APCSM/quotation/newProductCos/exportNewProductAgreeMarket");
			$("#thisForm").attr("target","_blank");
			$("#thisForm").submit();
			$("#thisForm").attr("target","_self");
//		}
	});
	
	//列印新品評審表
	$("#btn_export_appraise_report").on("click", function() {
//		if($("#querySupplierId").val() == '' && $("#queryBarcode1").val() == '' 
//			&& $("#queryItemName").val() == '' && $("#queryItemCode").val() == '' 
//			&& $("#quertstrdate").val() == '' && $("#queryenddate").val() == '' 
//			&& $("#queryInputDate").val() == '' && $("#querySaleEffectiveDate").val() == '') {
//			dialogShortMesg("查詢條件請擇一填寫");
//			return false;
//		} else {
			$("#thisForm").attr("action", "/APCSM/quotation/newProductCos/exportNewProductAppraise");
			$("#thisForm").attr("target","_blank");
			$("#thisForm").submit();
			$("#thisForm").attr("target","_self");
//		}
	});
	
	//批次選擇
	$("select[name=flowStatusListTitle]").change(function () {
		var titleVal = $(this).val().split("@");
		$("select[name='quotation.flowStatusList'] option").each(function () {
			var listVal = $(this).val().split("@");
			if(listVal[0] == titleVal[0]){
				$(this).prop("selected",true);
			}
		});
	});
	
	//送出
	$("#btn_save").on("click", function() {
		var flag = validateAll();	
		if (!flag) return false;
		$("#mode").val("save");
		mergeTemp("save");
	});
	
	//推廣費用->不收費 change
	$("input[name='quotation.newSupport']").change(function() {
//		if ($(this).val() == "4") {
//			$(".newSupportOtherMemoSpan").show();
//		} else {
//			$(".newSupportOtherMemoSpan").hide();
//			$("#newSupportReason").val("");
//			$("#newSupportOtherMemo").val("");
//		}
	});

	$(".radio1").change(function () {
		if ($("#newSupportReason").val() == "7" && $(this).val() == "4") {
			$(".newSupportOtherMemoSpan").show();
		}else{
			$(".newSupportOtherMemoSpan").hide();
			$("#newSupportReason").val("");
			$("#newSupportOtherMemo").val("");
		}
	});

	$("#newSupportReason").change(function() {
		$("input[name='quotation.newSupport']").each(function () {
			if ($(this).val() == "4")
				$(this).prop("checked", true);
			else
				$(this).prop("checked", false);
		});
		if ($("#newSupportReason").val() == "7") {
			$(".newSupportOtherMemoSpan").show();
		} else {
			$(".newSupportOtherMemoSpan").hide();
			$("#newSupportOtherMemo").val("");
		}	
	});
	
	

	//是否緊急 click
	$("input[name='quotation.isEmergency']").change(function() {
		if ($(this).val() == "N") {
			$("#saleEffectiveDateY").val("");
			$("#saleEffectiveDateN").prop("disabled", false);
			$("#saleEffectiveDateY").prop("disabled", true);
		} else {
			$("#saleEffectiveDateN").val("");
			$("#saleEffectiveDateN").prop("disabled", true);
			$("#saleEffectiveDateY").prop("disabled", false);
		}
	});
	
	//條碼掃描
	$("#barcodeScan").focus();
	$("#barcodeScan").keypress(function (e) {
	  	if (e.which == 13) {
			if ($(".its td:contains('"+$(this).val()+"')").length > 0) {
				dialogShortMesg("條碼已確認");
				$(".its td:contains('"+$(this).val()+"')").parent().remove();
				$(this).val("");
			} else {
				dialogShortMesg("查無此商品");
			}
	  	}
	});
	
	//disabled 審核頁籤
	if ($("#mode").val() == "false") {
		$("#menu7 .checkData").each(function() {
			var id = $(this).attr("id");
			$("#" + id).prop("disabled", true);
		});
	}	
	//審核結果
	$("#approveStatus").change(function(){
		//要提報商採會，顯示商採會日期欄位
		if($(this).val() == "03") {
			$(".li_inputDate").show();
			$("#inputDate").attr("notNull","Y");
		} else {
			$(".li_inputDate").hide();
			$("#inputDate").attr("notNull","N");
		}
	});
});



//條碼
function goModify(docId, roleNo, sortIndex){
	$("#menuId").val("");
	$("#thisForm").attr("action","/APCSM/quotation/newProductCos/goModify?id=" + docId + "&roleNo=" + roleNo + "&sortIndex=" + 
			sortIndex + "&" + getSystemTimeId());
	$("#thisForm").submit();
	viewLoading();
}

//點擊menuXX
function changeMenu(n) {
	var menuId = $("#menuId").val();
	if (menuId == "menu7") {
		if (!validate()) {
			return false;
		}
	}	
	$("#menuId").val("menu" + n);
}

//頁面檢核
function validateAll() {	
	var flag = true;
	$("#menuId").val("menu7");
	flag = validate();
	if (!flag) return false;
	return flag;
}

function validate() {
	var menuId = $("#menuId").val();
	var flag = checkFormData(menuId);
	if (menuId == "menu7" && flag) {
		flag = validateMenu7();
	}
	return flag;
}

function validateMenu7() {
	var isEmergency = $("input[name='quotation.isEmergency']:checked").val();
	var inputDate = $("input[name='quotation.inputDate']").val();
	var newSupport = $("input[name='quotation.newSupport']:checked").val();
	var approveStatus = $("#approveStatus").find("option:selected").attr("audit");

	if (newSupport == "4" && $("#newSupportReason").val() == "") {
		dialogShortMesg("請選擇不收費");
		$("#newSupportReason").focus();
		return false;
	}
	if ($("#newSupportReason").val() == "7" && $("#newSupportOtherMemo").val() == "" && newSupport == "4") {
		dialogShortMesg("請輸入原因");
		$("#newSupportOtherMemo").focus();
		return false;
	}
	if (newSupport == "" || newSupport == "4") {
		$("#contractYear").val("");
		$("#contractAmt").val("");
	} else {
		$("#contractYear").val($("#year" + newSupport).val());
		$("#contractAmt").val($("#objectiveamt" + newSupport).val());
	}
	if(approveStatus < 0){
		if($("#auditOpinion").val() == ""){
			dialogShortMesg("若審核結果的動作為退回上一關，則審核意見為必填");
			return false;
		}
	} else {
		if(inputDate < getSystemDateToEight()){
			dialogShortMesg("提報商採會日期需大於等於今日");
			return false;
		}
	}
	if(isEmergency == 'Y'){
		if($("#auditOpinion").val() == ""){
			dialogShortMesg("若為緊急，則審核意見為必填");
			return false;
		}
	}
	if(($("#minOrderQty").val() % $("#packQty").val()) > 0){
		dialogShortMesg("最小訂量需為收縮數的倍數");
		return false;		
	}
	if(($("#maxOrderQty").val() % $("#packQty").val()) > 0){
		dialogShortMesg("最大訂量需為收縮數的倍數");
		return false;		
	}
	if(parseInt($("#limitEcQty").val()) > parseInt($("#limitQty").val())){
		dialogMessage("EC分配量必需小於等於限量商品總數");
		return false;
	}

	return true;
}

//暫存
function mergeTemp(method) {
	viewLoading();
	var url = "/APCSM/quotation/newProductCos/mergeTemp";
	$("#thisForm").ajaxForm({url: url, type: "post"});
	//$("#thisForm").ajaxSubmit({url: url, type: "post"});//解決了ajaxForm不能submit問題，可是會重複ajax2次
    $.post(url, $("#thisForm").serialize(), function(data) {    	
    	if (data.msg == "") {
    		if (method == "saveTemp")
    			dialogShortMesg("暫存成功");
    		if (method == "save")
    			location.href = "/APCSM/quotation/newProductCos/merge?barcode1=" + $("#barcode1").val();
    		setTimer();
			setMmss();
    	} else {
    		dialogShortMesg(data.msg);
    	}
    });
    return false;
}

//上一頁
function btnBackClick() {
	btnBackTab();
}

//下一頁
function btnForwardClick() {
	btnForwardTab();
}

//系統生效日
function getSaleEffectiveDateList(deliveryType) {
	var formData = new FormData();
	formData.append("deliveryType", deliveryType);
	$.ajax({
	    url: "/APCSM/quotation/newProductCos/getSaleEffectiveDateList",
	    data: formData,
	    cache: false,
	    contentType: false,
	    processData: false,
	    type: "POST",
	    success: function(data) {
	    	if (data.msg == "") {
	    		$("#saleEffectiveDate").empty();
	    		$("#saleEffectiveDate").append($("<option>", { 
	    	        value: "",
	    	        text : "請選擇" 
	    	    }));
	    		$.each(data.saleEffectiveDateList, function (i, item) {
	    			$("#saleEffectiveDate").append($("<option>", { 
	    		        value: item.saleEffectiveDate,
	    		        text : item.saleEffectiveDate 
	    		    }));
	    		});
	    	} else {
	    		dialogShortMesg(data.msg);
	    	}
	    }
	});
}
