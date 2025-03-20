$(document).ready(function() {
	//SuperTable 有查到資料時才需要凍結視窗，不然會有script錯誤
	if($("#disList").length > 0 && $("#disList .empty").length == 0)
		$("#disList").toSuperTable({ width: "98%", fixedCols: 4,height:"500"}); 
	
	//查詢
	$("#btn_search").on("click", function() {
		$("#thisForm").attr("action", "/APCSM/quotation/modifyProductSup/query");
		$("#thisForm").submit();
		viewLoading();
	});
	
	//新增商品異動
	$("#btn_pick").on("click", function() {
		$("#thisForm").attr("action", "/APCSM/quotation/modifyProductSup/goPick");
		$("#thisForm").submit();
		viewLoading();
	});
	
	//新增商品異動頁 回上頁
	$("#btn_backQuery").on("click", function() {
		$("#thisForm").attr("action", "/APCSM/quotation/modifyProductSup");
		$("#thisForm").submit();
		viewLoading();
	});
	
	//查詢要異動的商品主檔
	$("#btn_pick_search").on("click", function() {
		$("#thisForm").attr("action", "/APCSM/quotation/modifyProductSup/queryPick");
		$("#thisForm").submit();
		viewLoading();
	});
	
	//暫存
	$("Button[name='btn_save_temp']").on("click", function(event) {
		$("#htmlDescription").val(CKEDITOR.instances.longDescription.getData());
		if (!validate()) return false;
		$("#flowStatus").val("W");		
		mergeTemp("saveTemp");
	});
	
	//新增送出
	$("#btn_save").on("click", function() {
		var flag = validateAll();
		$("#htmlDescription").val(CKEDITOR.instances.longDescription.getData());
		if (!flag) {
			changeTab($("#menuId").val().substring(4));
			dialogShortMesg("尚有商品資訊未填寫正確，請檢查！");
			return false;
		}
		
		$("#flowStatus").val("C");
		$("#mode").val("save");
		mergeTemp("save");
	});
	
	//進入編輯
	if($("#mode").val() == "edit")
		initEditValue();//初始預設值
	
	//大類為000020、000024、000026或法規為08、09，uploadEc為N
	$("#featureCode").change(function(){
		if($(this).val() == '08' || $(this).val() == '09'){
			$("#uploadEc").val("N");
		}
		else{
			if($("#familyCode").val() != '000020' && $("#familyCode").val() != '000024' && $("#familyCode").val() != '000026'){
				$("#uploadEc").val("Y");
			}			
		}
		console.log("uploadEc:   "+$("#uploadEc").val());
	});
	$("#familyCode").change(function(){		
		var familyCode = $(this).val();
		if(familyCode == '000020' || familyCode == '000024' || familyCode == '000026'){
			$("#uploadEc").val("N");
		}
		else{
			if($("#featureCode").val() != '08' && $("#featureCode").val() != '09'){
				$("#uploadEc").val("Y");
			}			
		}		
	});
});

//編輯模式預設控制選項值
function initEditValue(){
	newNameCtrl();
	agreeNameCtrl();
	newBarcodeCtrl();
	newPriceCtrl();
	newQtyCtrl();
	featureCodeCtrl();
	orgnCityCtrl();
	seriesNewNameCtrl();
	seriesAgreeNameCtrl();
	seriesNewBarcodeCtrl();
	initSuitdateSug();
}

//新商品名稱異動時
function newNameCtrl(){
	if($("#newItemName").val() != ""){
		$("#agreeName").show();
	}else{
		$("#agreeName,#agreeNameSub").hide();
		$("#agreeName,#agreeNameSub").val("");
	}
}
//新商品名稱同意選項控制
function agreeNameCtrl(){
	if($("#agreeName").val()=="01" || $("#agreeName").val() == ""){
		$("#agreeNameSub").val("");
		$("#agreeNameSub").hide();
	}else{
		$("#agreeNameSub").show();
	}
}
//新條碼異動時
function newBarcodeCtrl(){
	if($("#newBarcode").val() != ""){
		$("#agreeBarcode").show();
	}else{
		$("#agreeBarcode").hide();
		$("#agreeBarcode").val("");
	}
}
//新價格異動時
function newPriceCtrl(){
	if($("#newCostTax").val() != "" || $("#newPriceTax").val() != "") {
		$("#agreePrice").show();
		$("#chgPriceSuitdateSug").parents("li").show();
		$("#chgPriceSuitdateSug").attr("notNull", "Y");
		
		var newCostTax = $("#newCostTax").val();
		var oldCostTax = $("#oldCostTax").val();
		var newPriceTax = $("#newPriceTax").val();
		var oldPriceTax = $("#oldPriceTax").val();
		var oldGp = $("#oldGp").val();
		//成本變價調整幅度
		if(newCostTax != "" && oldCostTax != "") {
			var costIncrease = ((newCostTax / oldCostTax)-1)*100;
			//因Math.round()方法無法設定小數後幾位四捨五入，因此透過設定*100取小數第三位做四捨五入
			costIncrease = (Math.round(costIncrease*100)/100);
			$("#costIncrease").html(costIncrease + "%");
			$("#costIncrease").parents("li").show();
		}
		//零售變價調整幅度
		if(newPriceTax != "" && oldPriceTax != "") {
			var priceIncrease = ((newPriceTax / oldPriceTax)-1)*100;
			//因Math.round()方法無法設定小數後幾位四捨五入，因此透過設定*100取小數第三位做四捨五入
			priceIncrease = (Math.round(priceIncrease*100)/100);
			$("#priceIncrease").html(priceIncrease + "%");
			$("#priceIncrease").parents("li").show();
		}
		//新毛利率：(售價-成本)/售價
		var tmpPrice = $("#newPriceTax").val() == "" ? $("#oldPriceTax").val() : $("#newPriceTax").val();
		var tmpCost = $("#newCostTax").val() == "" ? $("#oldCostTax").val() : $("#newCostTax").val();
		var newGp = Math.round((tmpPrice-tmpCost)/tmpPrice*10000)/100;
		if(oldGp > newGp) {
			$("#newGp").html('<font color="red">' + newGp + "%</font>");
		} else {
			$("#newGp").html(newGp + "%");
		}
		$("#newGp").show();
		$("#newGp").prev("label").show();
	} else {
		$("#agreePrice").hide();
		$("#agreePrice").val("");
		$("#chgPriceSuitdateSug").parents("li").hide();
		$("#chgPriceSuitdateSug").val("");
		$("#chgPriceSuitdateSug").attr("notNull", "N");
		$("#costIncrease").parents("li").hide();
		$("#priceIncrease").parents("li").hide();
		$("#newGp").hide();
		$("#newGp").prev("label").hide();
	}
}
//新收縮數、外箱數異動時
function newQtyCtrl(){
	if($("#newPackQty").val() != "" || $("#newQtryCtn").val() != "" ){
		$("#agreeSpec").show();
	}else{
		$("#agreeSpec").hide();
		$("#agreeSpec").val("");
	}
}

//刪除異動提報
function remove(quotationId) {
	var dialog_buttons = {}; 
	dialog_buttons["確定"] = function() {
		$("#thisForm").attr("action", "/APCSM/quotation/modifyProductSup/remove?quotationId=" + quotationId);
		$("#thisForm").submit();
		viewLoading();	
	};
	dialog_buttons["取消"] = function() {
		$(this).dialog("close");
	};   
	dialogConfirm("確定要刪除嗎？", dialog_buttons);
}

//點擊條碼進入編輯畫面
function goModify(docId,flowStatus){
	$("#menuId").val("");
	if(flowStatus == "R" || flowStatus == "W"){
		$("#thisForm").attr("action","/APCSM/quotation/modifyProductSup/goModify?id=" + docId + "&" + getSystemTimeId());
	}else{
		$("#thisForm").attr("action","/APCSM/quotation/modifyProductCos/goModify?id=" + docId + "&roleNo=&sortIndex=&" + getSystemTimeId());
	}
	$("#thisForm").submit();
	viewLoading();
}

//選好要異動哪一筆主檔商品後按提報按鈕
function copyProductInfoToQuotation(quotationId, itemCode) {
	$("#menuId").val("");
	$("#thisForm").attr("action","/APCSM/quotation/modifyProductSup/copyProductInfoToQuotation?quotationId=" + quotationId + "&itemCode=" + itemCode + "&" + getSystemTimeId());
	$("#thisForm").submit();
	viewLoading();
}

//系列商品新條碼異動時
function seriesNewBarcodeCtrl(){
	var row = $("#seriesCount").val();
	for(var i=0; i<=row; i++) {
		if($("#seriesList" + i + "1").val() != "") {
			$("#series" + i + "AgreeBarcode").show();
		} else {
			$("#series" + i + "AgreeBarcode").hide();
			$("#series" + i + "AgreeBarcode").val("");
		}
	}
}

//系列商品新商品名稱異動時
function seriesNewNameCtrl(){
	var row = $("#seriesCount").val();
	for(var i=0; i<=row; i++) {
		if($("#seriesList" + i + "2").val() != "") {
			$("#series" + i + "AgreeName").show();
		} else {
			$("#series" + i + "AgreeName, #series" + i + "AgreeNameSub").hide();
			$("#series" + i + "AgreeName, #series" + i + "AgreeNameSub").val("");
		}
	}
}

//系列商品新商品名稱同意選項控制
function seriesAgreeNameCtrl(){
	var row = $("#seriesCount").val();
	for(var i=0; i<=row; i++) {
		if($("#series" + i + "AgreeName").val()=="01" || $("#series" + i + "AgreeName").val() == "") {
			$("#series" + i + "AgreeNameSub").val("");
			$("#series" + i + "AgreeNameSub").hide();
		} else {
			$("#series" + i + "AgreeNameSub").show();
		}
	}
}

//異動生效日、變動生效日起始時間需大於系統日7天後
function initSuitdateSug() {
	var date = new Date();
	date.setDate(date.getDate() + 7);
    $("#chgDataSuitdateSug").datepicker("option", "minDate", date);
    $("#chgPriceSuitdateSug").datepicker("option", "minDate", date);
}

//脫離系列
function btn_separateSeries(itemCode, flagMod) {
	if(flagMod != "Y") {
		dialogMessage("此商品異動中不可脫離");
	} else {
		var dialog_buttons = {}; 
		dialog_buttons["確定"] = function(){
			$("#thisForm").attr("action","/APCSM/quotation/modifyProductSup/separateSeries?itemCode=" + itemCode);
			$("#thisForm").submit();
			viewLoading();	
		};
		dialog_buttons["取消"] = function(){
			$(this).dialog("close");
		};   
		dialogConfirm("確定要脫離系列嗎？", dialog_buttons);
	}
}

//點擊menuXX
function changeMenu(n) {
	if ($("#mode").val() == "add") {
		dialogMessage("新增模式不能點擊頁籤");
		return false;
	}
	if($("#mode").val() != "show"){
		if (!validate()) {
			return false;
		}else{
			mergeTemp("");
		}	
	}
	$("#menuId").val("menu" + n);
	changeTab(n);	
}

//上一頁
function btnBackClick() {
	if($("#mode").val() != "show"){
		var dialog_buttons = {};
		dialog_buttons["確定"] = function() {
			$(this).dialog( "close" );
			var flag = validate();
			if (!flag) return false;
			
			$("#htmlDescription").val(CKEDITOR.instances.longDescription.getData());
			$("#flowStatus").val("W");
			mergeTemp("");//暫存
			btnBackTab();
			
		};
		dialog_buttons["取消"] = function(){
			$(this).dialog( "close" );
			btnBackTab();
		};   
		dialogConfirm("是否要儲存本頁資料？", dialog_buttons);
	}
	else{
		//純顯示模式
		btnBackTab();
	}
}

//下一頁
function btnForwardClick() {
	//判斷是編輯模式還是顯示模式
	if($("#mode").val() != "show"){
		var flag = validate();
		if (!flag) return false;	
		
		$("#htmlDescription").val(CKEDITOR.instances.longDescription.getData());
		$("#flowStatus").val("W");	
		mergeTemp("");//儲存資料
	}
	
	btnForwardTab();
	if($("#mode").val() == "show" && $("#menuId").val() == "menu6"){
		$("#btn_forward_6").hide();
	}
}

//頁面檢核
function validateAll() {	
	var flag = true;
	for (var i = 1; i <= 6; i++) {
		$("#menuId").val("menu" + i);
		flag = validate();
		if (!flag) return false;
	}
	return flag;
}

//暫存
function mergeTemp(method) {
	viewLoading();
	var url = "/APCSM/quotation/modifyProductSup/mergeTemp";
	$("#thisForm").ajaxForm({url: url, type: "post"});
	//$("#thisForm").ajaxSubmit({url: url, type: "post"});//解決了ajaxForm不能submit問題，可是會重複ajax2次
    $.post(url, $("#thisForm").serialize(), function(data) {    	
    	if (data.msg == "") {
    		$("#docId").val(data.docId);
    		$("#createUser").val(data.createUser);
    		$("#createTime").val(data.createTime);
    		if (method == "saveTemp")
    			dialogShortMesg("暫存成功");
    		if (method == "save")
    			location.href = "/APCSM/quotation/modifyProductSup/merge";
    		setTimer();
			setMmss();
    	} else {
    		dialogMessage(data.msg);
    	}
    	viewLoadingClose();
    });
    
    return false;
}
//頁籤檢核(按暫存或按上下頁時檢核當下頁籤)
function validate() {	
	var menuId = $("#menuId").val();
	var flag = checkFormData(menuId);
	if (menuId == "menu1" && flag) {
		flag = checkMenu1();
	}	
	if (menuId == "menu2" && flag) {
		flag = checkMenu2();
	}
	if (menuId == "menu3" && flag) {
		flag = checkMenu3();
	}
	if (menuId == "menu4" && flag) {
		flag = checkMenu4();
	}
	if (menuId == "menu5" && flag) {
		flag = checkMenu5();
	}
	if (menuId == "menu6" && flag) {
		var seriesCount = $("#seriesCount").val();
		for(var i=0; i<=seriesCount; i++) {
			if(!checkMenu6(i)) {
				flag = false;
				break;
			}
		}
	}
	return flag;
}

