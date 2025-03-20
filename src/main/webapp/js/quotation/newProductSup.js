$(document).ready(function() {
	//查詢
	$("#btn_search").on("click", function() {
		$("#thisForm").attr("action", "/APCSM/quotation/newProductSup/query");
		$("#thisForm").submit();
		viewLoading();
	});
	
	//新增
	$("#btn_add").on("click", function() {
		$("#dialog").dialog({
			title:"注意事項",
			modal:true,
			width: 500,
			height: 400,
			position:"middle",
			open:function(event, ui){
				$(this).load("/APCSM/pages/quotation/dialog_checkMessage.jsp?v="+getSystemTimeId());
			}		
		});
	});

	//列印新品協議書
	$("#btn_export_agree_market_report").on("click", function() {
//		if($("#queryBarcode1").val() == '' && $("#queryItemName").val() == '' 
//			&& $("#queryItemCode").val() == '' && $("#quertstrdate").val() == '' 
//			&& $("#queryenddate").val() == '' && $("#querySaleEffectiveDate").val() == '') {
//			dialogShortMesg("查詢條件請擇一填寫");
//			return false;
//		} else {
			$("#thisForm").attr("action", "/APCSM/quotation/newProductSup/exportNewProductAgreeMarket");
			$("#thisForm").attr("target","_blank");
			$("#thisForm").submit();
			$("#thisForm").attr("target","_self");
//		}
	});
	
	$(document).on("click", "Button[name='btn_export_report']", function() {
		var eshopCount = $("#eshopCount").val();
		if (eshopCount == "0") {
			dialogShortMesg("最後一筆不可刪除");
			return false;
		} else {
			$("#eshopCount").val(parseInt(eshopCount) - 1);
		}
		var id = $(this).attr("id");
		$("." + id).remove();
	});
	
	//暫存
	$("Button[name='btn_save_temp']").on("click", function(event) {
		$("#htmlDescription").val(CKEDITOR.instances.longDescription.getData());
		if (!validate()) 
			return false;
		$("#flowStatus").val("W");		
		mergeTemp("saveTemp");
	});
	
	//新增送出提報
	$("#btn_save").on("click", function() {
		var flag = validateAll();//檢核所有頁籤
		$("#htmlDescription").val(CKEDITOR.instances.longDescription.getData());
		if (!flag){
			changeTab($("#menuId").val().substring(4));
			dialogShortMesg("尚有商品資訊未填寫正確，請檢查！");
			return false;
		}
		
		$("#flowStatus").val("C");
		$("#mode").val("save");
		mergeTemp("save");
	});
	
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
	
	//10碼廠編 change
	$("#supplierId").change(function() {
		if ($("#supplierId").val() != "") {
			getCmUserAndFamilyCodeList();
		}
	});
	
	//10碼廠編 輸入框 autocomplete
	$("#supplierIdTags").autocomplete({
	    source: function(request, response) {
	        var formData = new FormData();
	        formData.append("cmUser", $("#cmUser").val());
	    	formData.append("supplierId", request.term.toLowerCase());
	    	$.ajax({
	    	    url: "/APCSM/quotation/newProductSup/getSupplierIdList",
	    	    data: formData,
	    	    cache: false,
	    	    contentType: false,
	    	    processData: false,
	    	    type: "POST",
	    	    success: function(data) {
	    	    	if (data.msg == "") {
	    	    		var choices = [];
	    	    		var items = data.supplierIdList;
	    		    	$.each(items, function (i, item) {
	    		    		choices.push({ 
	    		    			label : item.SUPPLIER_ID + " - " + item.SUPPLIER_NAME_BRIEF + "(" + item.SUPPLIER_ID_SUB + ")",
	    		    	        value: item.SUPPLIER_ID
	    		    	    });
	    		    	});
	    		        response(choices);
	    	    	} else {
	    	    		dialogMessage(data.msg);
	    	    	}
	    	    }
	    	});
	    },
	    select: function (event, ui) {
//	    	var array = ui.item.value.split("@");
//	    	$("#supplierIdTags").val(array[0] + " - " + ui.item.label + "(" + array[1] + ")");	    	
//	    	$("#supplierId").val(array[0]);
	    	$("#supplierIdTags").val(ui.item.label);
	    	$("#supplierId").val(ui.item.value);
	    	getCmUserAndFamilyCodeList();
	    	return false;
	    }
	});
	
	//商品經理 change
	$("#cmUser").change(function() {
		getClassCodeList();
	});

	//同系列商品
	$("#addSeries").click(function() {
		var count = $("#seriesCount").val();

		var barcode = $("#seriesList" + count + "0").val();
		var barcode_disabled = $("#seriesList" + count + "0").prop("disabled");
		var itemName = $("#seriesList" + count + "2").val();
		var ecItemName = $("#seriesList" + count + "3").val();
		var imageFile = $("#seriesList" + count + "8").val();
		var image = $("#seriesImage" + count + "8").val();
		
		//條碼反白，其餘欄位皆未填寫
		if(barcode_disabled && (itemName == "" || ecItemName == ""
			|| (imageFile == "" && image == ""))) {
			dialogMessage("請資料填寫完整後再新增下一筆");
		//條碼沒反白，所有欄位皆未填寫
		} else if(!barcode_disabled && (barcode == "" || itemName == "" 
			|| ecItemName == "" || (imageFile == "" && image == ""))) {
			dialogMessage("請資料填寫完整後再新增下一筆");
		} else {
			if(checkMenu6(count)) {
				count = parseInt(count) + 1;
				$("#seriesCount").val(count);
	
				var html = '<tr>';
				html += '<td><button type="button" class="btn btn-success btn-xs seriesDelete' + count + '" ';
				html += 'id="seriesDelete' + count + '" name="seriesDelete">刪除</button></td>';
				html += '<td><input type="text" id="seriesList' + count + '0" name="quotation.seriesList" value="" class="checkData" dataType="number" notNull="N" limit="13" data-toggle="tooltip" onblur="barcodeOnblur(\'seriesList' + count + '0\');">';
				html += '<span class="inputLimitBlock"><span class="inputLimit">13</span></span></td>';
				html += '<td><input type="text" id="seriesList' + count + '1" name="quotation.seriesList" value="" class="checkData" dataType="number" notNull="N" limit="13" data-toggle="tooltip" onblur="barcodeOnblur(\'seriesList' + count + '0\');">';
				html += '<span class="inputLimitBlock"><span class="inputLimit">13</span></span></td>';
				html += '<td><input type="text" id="seriesList' + count + '2" name="quotation.seriesList" value="" class="checkData" dataType="string" notNull="N" limit="25" onblur="seriesItemNameOnblur();">';
				html += '<span class="inputLimitBlock"><span class="inputLimit">25</span></span></td>';
				html += '<td><input type="text" id="seriesList' + count + '3" name="quotation.seriesList" value="" class="checkData" dataType="string" notNull="N" limit="30">';
				html += '<span class="inputLimitBlock"><span class="inputLimit">30</span></span></td>';
				html += '<td><input type="text" id="seriesList' + count + '4" name="quotation.seriesList" value="" class="checkData text-xs" dataType="number" data-toggle="tooltip" limit="5" canZero="Y" decimal="0" greatThen="0"></td>';
				html += '<td><input type="text" id="seriesList' + count + '5" name="quotation.seriesList" value="" class="checkData text-xs" dataType="number" data-toggle="tooltip" limit="5" canZero="Y" decimal="0" greatThen="0"></td>';
				html += '<td><input type="text" id="seriesList' + count + '6" name="quotation.seriesList" value="" class="checkData datepicker" dataType="date" notNull="N"></td>';
				html += '<td><input type="text" id="seriesList' + count + '7" name="quotation.seriesList" value="" class="checkData datepicker" dataType="date" notNull="N"></td>';
				html += '<td><input type="file" id="seriesList' + count + '8" name="seriesImage" class="checkData" dataType="string" notNull="N">';
				html += '<input type="hidden" id="seriesImage' + count + '8" name="quotation.seriesList" value=""></td>'
				html += '</tr>';
				
				$("#seriesBlock").append(html);
				getDatapicker();
				inputLimit();
				
				if(barcode_disabled) {
					for(var i=0; i<=1; i++) {
						$("#seriesList" + count + i).prop("disabled",true);
					}
				}
				
				//將新產出的檔案列綁定事件
				$("input[name='seriesImage']").change(function() {
					var imageId = $(this).attr("id");
					var laseNum = imageId.substring(imageId.length-2, imageId.length);
					var id = imageId.substring(0, 6) + "Image" + laseNum;
					var file = this.files[0], //定義file=發生改的file
						name = file.name, //name=檔案名稱
						size = file.size, //size=檔案大小
						type = file.type; //type=檔案型態
					
					if (size > 3145728) { //假如檔案大小超過3MB
						dialogMessage("檔案內容需小於3MB!!");
						$(this).val("");  //將檔案欄設為空白
					} else if (file.type != "image/jpeg") {//假如檔案格式不符合圖片格式
						dialogMessage("檔案格式不符合: JPG");
						$(this).val(""); //將檔案欄設為空
					} else {
						uploadFile(file, id, "menu6");
					}
				});
			}
		}
	});
	
	//要有查詢資料才顯示按鈕
	if($("#disList tbody tr:not(.empty)").length != 0){
		$("#btn_control").show();
	}
	//進入編輯
	if($("#mode").val() == "edit")
		initEditValue();//初始預設值
	
});

//編輯模式預設控制選項值
function initEditValue(){
	noBarcodeCtrl();
	featureCodeCtrl();
	orgnCityCtrl();
	kindCodeCtrl();
}

//條碼
function goModify(docId,flowStatus){
	$("#menuId").val("");
	if(flowStatus == "R" || flowStatus == "W"){
		$("#thisForm").attr("action","/APCSM/quotation/newProductSup/goModify?id=" + docId + "&" + getSystemTimeId());
	}else{
		$("#thisForm").attr("action","/APCSM/quotation/newProductCos/goModify?id=" + docId + "&roleNo=&sortIndex=&" + getSystemTimeId());
	}
	$("#thisForm").submit();
	viewLoading();
}

//刪除新品提報
function btn_remove(id){
	var dialog_buttons = {}; 
	dialog_buttons["確定"] = function(){
		$("#thisForm").attr("action","/APCSM/quotation/newProductSup/remove?delId=" + id);
		$("#thisForm").submit();
		viewLoading();	
	};
	dialog_buttons["取消"] = function(){
		$(this).dialog("close");
	};   
	dialogConfirm("確定要刪除嗎？", dialog_buttons);
}

//複製
function btn_copy(id){
	var dialog_buttons = {}; 
	dialog_buttons["確定"] = function(){
		$("#thisForm").attr("action","/APCSM/quotation/newProductSup/goCopy?id=" + id);
		$("#thisForm").submit();
		viewLoading();
	};
	dialog_buttons["取消"] = function(){
		$(this).dialog("close");
	};   
	dialogConfirm("確定要複製嗎？", dialog_buttons);
}

//暫存
function mergeTemp(method) {
	viewLoading();
	var url = "/APCSM/quotation/newProductSup/mergeTemp";
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
    			location.href = "/APCSM/quotation/newProductSup/merge?barcode1=" + $("#barcode1Temp").val();
    		setTimer();
			setMmss();
			//法規屬性update後，重新建立法規屬性html
			genProductRegulationsHtml($("#regulationsHtml"), $("#featureCode").val(), $("#docId").val(), false);
    	} else {
    		dialogMessage(data.msg);
    	}
    	viewLoadingClose();
    });
    
    return false;
}

//10碼廠編 change
function getCmUserAndFamilyCodeList() {
	var formData = new FormData();
	formData.append("supplierId", $("#supplierId").val());
	$.ajax({
	    url: "/APCSM/quotation/newProductSup/getCmUserAndFamilyCodeList",
	    data: formData,
	    cache: false,
	    contentType: false,
	    processData: false,
	    type: "POST",
	    success: function(data) {
	    	if (data.msg == "") {
	    		$("#cmUser").empty();
		    	var items = data.cmUserList;
		    	$("#cmUser").append($("<option>", { 
	    	        value: "",
	    	        text : "請選擇" 
	    	    }));
		    	$.each(items, function (i, item) {
		    	    $("#cmUser").append($("<option>", { 
		    	        value: item.CM_USER,
		    	        text : item.CM_USER_NAME 
		    	    }));
		    	});
		    	
		    	$("#familyCode").empty();
		    	items = data.familyCodeList;
		    	$("#familyCode").append($("<option>", { 
	    	        value: "",
	    	        text : "請選擇" 
	    	    }));
		    	$.each(items, function (i, item) {
		    	    $("#familyCode").append($("<option>", { 
		    	        value: item.CATE_ID_1,
		    	        text : item.CATE_NAME_1 
		    	    }));
		    	});
		    	
		    	$("#classCode").empty();
	    	} else {
	    		dialogMessage(data.msg);
	    	}		    	
	    }
	});
}

//提報大類別
function getFamilyCodeListByPma() {
	var formData = new FormData();
	formData.append("supplierId", $("#supplierId").val());
	$.ajax({
	    url: "/APCSM/quotation/newProductSup/getFamilyCodeListByPma",
	    data: formData,
	    cache: false,
	    contentType: false,
	    processData: false,
	    type: "POST",
	    success: function(data) {
	    	if (data.msg == "") {
	    		$("#familyCode").empty();
		    	var items = data.familyCodeList;
		    	$("#familyCode").append($("<option>", { 
	    	        value: "",
	    	        text : "請選擇" 
	    	    }));
		    	$.each(items, function (i, item) {
		    	    $("#familyCode").append($("<option>", { 
		    	        value: item.CATE_ID_1,
		    	        text : item.CATE_NAME_1 
		    	    }));
		    	});
	    	} else {
	    		dialogMessage(data.msg);
	    	}
	    }
	});
}

//移除ec商品分類
function removeRefEcCategory(quotationId, sourceType, xmsCategory) {
	var formData = new FormData();
    formData.append("quotationId", quotationId);
    formData.append("sourceType", sourceType);
	formData.append("xmsCategory", xmsCategory);
	$.ajax({
	    url: "/APCSM/quotation/newProductSup/removeRefEcCategory",
	    data: formData,
	    cache: false,
	    contentType: false,
	    processData: false,
	    type: "POST",
	    success: function(data) {
	    	if (data.msg == "") {
	    		
	    	} else {
	    		dialogShortMesg(data.msg);
	    	}
	    }
	});
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

