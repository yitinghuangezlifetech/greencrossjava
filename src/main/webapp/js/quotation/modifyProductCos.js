$(document).ready(function() {
	//SuperTable 有查到資料時才需要凍結視窗，不然會有script錯誤
	//alert($(this).width());//1280-220=1080
	if($("#disList .empty").length == 0)
		$("#disList").toSuperTable({ width: "98%", fixedCols: 5 }); //1047-1017=30
	
	//查詢
	$("#btn_search").on("click", function() {
		$("#thisForm").attr("action", "/APCSM/quotation/modifyProductCos/query");
		$("#thisForm").submit();
		viewLoading();
	});

	//送出
	$("#btn_save").on("click", function() {
		var flag = validateAll();
		if (!flag) return false;
		$("#mode").val("save");
		mergeTemp("save");
	});
	
	//disabled 審核頁籤
	if ($("#mode").val() == "false") {
		$("#menu7 .checkData").each(function() {
			var id = $(this).attr("id");
			$("#" + id).prop("disabled", true);
		});
	}
	
	//檢核 buttonsTax
	$("#approveStatus").on("change", function(event) {
		buttonsTax();		
	});
	
	//檢核 buttonsTax
	$("#chgPriceSuitdate , #chgPriceSuitdate").on("change", function(event) {
		buttonsSuitdate();
	});
	
	//審核狀態，控制生效日選單顯示
	$("#approveStatus").change(function(){
		var sortIndex = $("#sortIndex").val()
		if($(this).val() == "Y" && sortIndex == "1"){
			//生效日選單
			$("#chgDataSuitdate").parents("li").show();
			$("#chgDataSuitdate").attr("notNull","Y");
			if($("#newCostTax").val() != "") {
				$("#chgCostSuitdate").parents("li").show();
				$("#chgCostSuitdate").attr("notNull", "Y");
			}
			if($("#newPriceTax").val() != "") {
				$("#chgPriceSuitdate").parents("li").show();
				$("#chgPriceSuitdate").attr("notNull", "Y");
			}
			
			//收縮數變更需顯示最小訂量，最大訂量供填寫
			var packQty = $("#packQty").val();
			var newPackQty = $("#newPackQty").val();
			if(packQty != newPackQty) {
				$("#minOrderQty").parents("li").show();
				$("#minOrderQty").attr("notNull", "Y");
				$("#maxOrderQty").parents("li").show();
				$("#maxOrderQty").attr("notNull", "Y");
			} else {
				$("#minOrderQty").parents("li").hide();
				$("#minOrderQty").attr("notNull", "N");
				$("#maxOrderQty").parents("li").hide();
				$("#maxOrderQty").attr("notNull", "N");
			}
		} else {
			$("#chgDataSuitdate").parents("li").hide();
			$("#chgDataSuitdate").attr("notNull","N");
			$("#chgDataSuitdate").val("");
			$("#chgCostSuitdate").parents("li").hide();
			$("#chgCostSuitdate").attr("notNull","N");
			$("#chgCostSuitdate").val("");
			$("#chgPriceSuitdate").parents("li").hide();
			$("#chgPriceSuitdate").attr("notNull","N");
			$("#chgPriceSuitdate").val("");
			$("#minOrderQty").parents("li").hide();
			$("#minOrderQty").attr("notNull", "N");
			$("#maxOrderQty").parents("li").hide();
			$("#maxOrderQty").attr("notNull", "N");
		}
	});
	
	//列印零售變價簽呈
	$("#btn_export_chgPricePetition_report").on("click", function() {
		if($("input[name=printArray]:checked").length == 0){
			dialogMessage("尚未勾選欲列印簽呈之商品");
			return false;
		} else {
			var printArray = new Array();
			$("input[name=printArray]:checked").each(function(i, item) {
				printArray.push($(item).val());
			});
			$("#thisForm").attr("action", "/APCSM/quotation/modifyProductCos/exportChangePricePetition?printArray=" + printArray);
			$("#thisForm").attr("target","_blank");
			$("#thisForm").submit();
			$("#thisForm").attr("target","_self");
		}
	});
});

//條碼
function goModify(docId, roleNo, sortIndex){
	$("#menuId").val("");
	$("#thisForm").attr("action","/APCSM/quotation/modifyProductCos/goModify?id=" + docId + "&roleNo=" + roleNo + "&sortIndex=" + 
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
		//mergeTemp("");
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

//審核頁檢核
function validateMenu7() {	
	//如果選擇不通過，審核意見需必填
	if($("#approveStatus").val() == "N" && $("#auditOpinion").val() == ""){
		addHtmlNew($("#auditOpinion"),"必填");
		return false;
	}
	//最小訂量，最大訂量需為 收縮數 倍數
	var packQty = $("#packQty").val();
	var newPackQty = $("#newPackQty").val();
	var minOrderQty = $("#minOrderQty").val();
	var maxOrderQty = $("#maxOrderQty").val();
	var checkQty = newPackQty;
	if(checkQty == "") {
		checkQty = packQty;
	}
	if((maxOrderQty % checkQty != 0) || (minOrderQty % checkQty != 0)) {
		dialogMessage("最小訂量及最大訂量都需為收縮數的倍數");
		return false;
	}
	
	return true;
}

//狀況一：成本漲價生效日早於零售價生效日
function buttonsSuitdate(){
	var chgCostSuitdate = parseInt($("#chgCostSuitdate").val());
	var chgPriceSuitdate = parseInt($("#chgPriceSuitdate").val());
	if(chgCostSuitdate < chgPriceSuitdate){
		$("#qSuitdate").html("成本漲價生效日早於零售價生效日，將造成毛利折損，請再次確認");
	}else if(chgCostSuitdate >= chgPriceSuitdate){
		$("#qSuitdate").html("");
	}
}

//狀況二：成本漲價但零售價不變
function buttonsTax() {
	var costTax = parseInt($("#costTax").val());
	var newCostTax = parseInt($("#newCostTax").val());
	var priceTax = parseInt($("#priceTax").val());
	var newPriceTax = parseInt($("#newPriceTax").val());
	if( costTax < newCostTax && ( newPriceTax ="" || priceTax == newPriceTax )){
		$("#qPriceTax").html("成本漲價但零售價不變，將造成毛利折損，請再次確認");
	}
}

//暫存
function mergeTemp(method) {
	viewLoading();
	var url = "/APCSM/quotation/modifyProductCos/mergeTemp";
	$("#thisForm").ajaxForm({url: url, type: "post"});
	//$("#thisForm").ajaxSubmit({url: url, type: "post"});//解決了ajaxForm不能submit問題，可是會重複ajax2次
    $.post(url, $("#thisForm").serialize(), function(data) {    	
    	if (data.msg == "") {
    		if (method == "saveTemp")
    			dialogShortMesg("暫存成功");
    		if (method == "save")
    			location.href = "/APCSM/quotation/modifyProductCos/merge";
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



