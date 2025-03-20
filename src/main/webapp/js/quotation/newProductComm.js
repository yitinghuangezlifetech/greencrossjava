//新品提報共用
$(document).ready(function() {
	//上一頁
	$("Button[name='btn_back']").on("click", function(event) {
		btnBackClick();
	});
	
	//下一頁
	$("Button[name='btn_forward']").on("click", function(event) {
		btnForwardClick();
	});
	
	//產生頁面各EC下拉式選單
	/*var ecNameArray = ['yahoo','eshop','shopee','comsed'];
	for (var i = 0; i < ecNameArray.length; i++) {
		var name = ecNameArray[i];
		item = ecAuto(name, 0, 5, true);
		console.log("ecAuto="+item);
	}*/
	
	//商品屬性 checkbox
	$("input[name='quotation.chidList']").on("click", function() {
		if ($(this).is(":checked")) {
	    	dialogMessage("品名前方請記得加入符號，例： (乙)白花油");
	    }
	    //如果法規屬性選「乙類成藥」那麼商品屬性要選「乙類成藥」與「藥品」，且不能取消
	    if (($(this).val() == "9" || $(this).val() == "1") && $("#featureCode").val() == "01") {
	    	$(this).prop("checked", true);
	    }
	    //如果法規屬性選「藥品」那麼商品屬性也要選「藥品」且不能取消
	    if ($(this).val() == "1" && $("#featureCode").val() == "08") {
	    	$(this).prop("checked", true);
	    }	   
	    
	    //R藥品與乙類只能選一個
//	    if($("input[name='quotation.chidList'][value='1']").is(":checked") && $("input[name='newproduct.chidList'][value='9']").is(":checked")){
//	    	dialogMessage("(R)藥品 與 (乙)乙類成藥僅可二選一");
//	    	$("input[name='quotation.chidList'][value='1']").prop("checked",false);
//	    	$("input[name='quotation.chidList'][value='9']").prop("checked",false);
//	    }
	    kindCodeCtrl();//判斷商品種類
	});
	
	//提報大類別 change
	$("#familyCode").change(function() {
		getClassCodeList();
		kindCodeCtrl();
	});
	
	//提報中類別 change
	$("#classCode").change(function() {
		getBrickCodeList();
	});
	
	//稅別
	$("#taxType").change(function() {
		if ($("#taxType").val() == "3") {
			dialogMessage("請確認此商品為【免稅】");
		}
	});	
	
	//產地連動
	$("#isImport").change(function() {
		//進口國
		if($(this).val() == "0")
			$(".ref_originCountry").show();
		else{
			$(".ref_originCountry").hide();
			$("#orgnCountry").val("");
			$("#orgnCity").val("");
		}
	});
	
	//是否超取
	$("#storageEnv").on("change", function(event) {
		superTake();
	});
	
	$("#brandCodeTags").autocomplete({
	    source: function(request, response) {
	        var formData = new FormData();
	        formData.append("chtype", "104");
	        formData.append("chid", "");
	    	formData.append("chnm", request.term.toLowerCase());
	    	$.ajax({
	    	    url: "/APCSM/quotation/newProductSup/getCharacterList",
	    	    data: formData,
	    	    cache: false,
	    	    contentType: false,
	    	    processData: false,
	    	    type: "POST",
	    	    success: function(data) {
	    	    	if (data.msg == "") {
	    	    		var choices = [];
	    	    		var items = data.characterList;
	    		    	$.each(items, function (i, item) {
	    		    		choices.push({ 
	    		    			label : item.CHNM,
	    		    	        value: item.CHID
	    		    	    });
	    		    	});
	    		        response(choices);
	    	    	} else {
	    	    		dialogMessage(data.msg);
	    	    		$("#brandCodeTags").val("");
	    	    		$("#brandCode").val("");
	    	    	}
	    	    }
	    	});
	    },
	    select: function (event, ui) {
	    	$("#brandCodeTags").val(ui.item.label);
	    	$("#brandCode").val(ui.item.value);
	    	return false;
	    }
	});
	
	//商品資訊file
	$("input[name='menu3File']").change(function() {
		var imageId = $(this).attr("id").substring(0, 10);
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
			uploadFile(file, imageId, "menu3");
		}
	});	
	
	//刪除圖檔XX(正面主圖)
	$(".ajaxDelMenu3File").click(function() {
		var attachId = $(this).attr("attachId");
		var dialog_buttons = {}; 
		dialog_buttons["確定"] = function() {
			$(this).dialog("close");
			doDelMenu3File(attachId);
		};
		dialog_buttons["取消"] = function() {
			$(this).dialog("close");
		};   
		dialogConfirm("確定要刪除嗎？", dialog_buttons);
	});
	
	//同國內負責廠商 checkbox
	$("input[name='principalCheck']").on("click", function() {
		if ($(this).is(":checked")) {
			insertProfCompany($(this).attr("id"));
		}
	});
	
	//衛福部法規屬性連動
	$("#featureCode").change(function() {
		//產生法規屬性頁的html內容
		if ($("#featureCode").val() != "") {
			$("#regulationsHtml").empty();
			genProductRegulationsHtml($("#regulationsHtml"), $("#featureCode").val(), $("#docId").val(), false);
		} else {
			$("#regulationsHtml").empty();
		}
		
		//清空商品屬性
		$("input[name='quotation.chidList']").each(function() {
			$(this).prop("checked", false);
		});
		
		/*如果是選擇「藥品」，要將商品屬性的藥品預設勾選
		 * 如果是選擇「乙類成藥」，要將商品屬性的乙類成藥預設勾選
		 * */
		if ($("#featureCode").val() == "01" || $("#featureCode").val() == "08") {
			$("input[name='quotation.chidList']").each(function() {
				//乙類成藥
				if ($("#featureCode").val() == "01" && ($(this).val() == "9" || $(this).val() == "1")) {
					$(this).prop("checked", true);
				}
				//藥品
				if ($("#featureCode").val() == "08" && $(this).val() == "1") {
					$(this).prop("checked", true);
				}
			});
		}
		featureCodeCtrl();//連動控制
		ajaxCalAcceptableDate();//立即計算允收
	});	
	
	//法規屬性file
	$(document).on("change", "input[name='menu4File']", function() {
		var imageId = $(this).attr("id") + "Menu4";
		var file = this.files[0], //定義file=發生改的file
		name = file.name, //name=檔案名稱
		size = file.size, //size=檔案大小
		type = file.type; //type=檔案型態
		if (size > 3145728) { //假如檔案大小超過3MB
			dialogMessage("檔案內容需小於3MB!!");
			$(this).val("");  //將檔案欄設為空白
		} else if (file.type != "image/jpeg" && file.type != "image/png") {//假如檔案格式不符合圖片格式
			dialogMessage("檔案格式不符合: JPG,PNG");
			$(this).val(""); //將檔案欄設為空
		} else {
			uploadFile(file, imageId, "menu4");
		}
	});
	
	//法規屬性刪除圖檔
	$(".ajaxDelMenu4File").click(function() {
		var attachId = $(this).attr("attachId");
		var featureCode = $(this).attr("featureCode");
		var dialog_buttons = {}; 
		dialog_buttons["確定"] = function() {
			$(this).dialog("close");
			doDelMenu4File(attachId,featureCode);
		};
		dialog_buttons["取消"] = function() {
			$(this).dialog("close");
		};   
		dialogConfirm("確定要刪除嗎？", dialog_buttons);
	});

	//set法規屬性
	setMenu4Attribute();
	
	//yahoo商城分類
	$(document).on("change", "select[name='yahooSelect']", function() {
		var id = $(this).attr("id").substring(0, 6);
		var num = $(this).attr("id").substring(6, 7);
		var length = $('.yahooDelete').length ;
		getProfEcCategoryList("yahoo", $(this).val(), id + (parseInt(num) + 1));
		setEcSelect(id, num, length);
	});
	
	//yahoo商城自定分類
	$(document).on("change", "select[name='yahooShopSelect']", function() {
		var id = $(this).attr("id").substring(0, 10);
		var num = $(this).attr("id").substring(10, 11);
		var length = $('.yahooShopDelete').length ;
		getProfEcCategoryList("yahooShop", $(this).val(), id + (parseInt(num) + 1));
		setEcSelect(id, num, length);
	});
	
	//91app商城分類
	$(document).on("change", "select[name='eshopSelect']", function() {
		var id = $(this).attr("id").substring(0, 6);
		var num = $(this).attr("id").substring(6, 7);
		var length = $('.eshopDelete').length ;
		getProfEcCategoryList("eshop", $(this).val(), id + (parseInt(num) + 1));
		setEcSelect(id, num, length);
	});
	//91app商城自定分類
	$(document).on("change", "select[name='eshopShopSelect']", function() {
		var id = $(this).attr("id").substring(0, 10);
		var num = $(this).attr("id").substring(10, 11);
		var length = $('.eshopShopDelete').length ;
		getProfEcCategoryList("eshopShop", $(this).val(), id + (parseInt(num) + 1));
		setEcSelect(id, num, length);
	});

	//蝦皮商城分類
	$(document).on("change", "select[name='shopeeSelect']", function() {
		var id = $(this).attr("id").substring(0, 7);
		var num = $(this).attr("id").substring(7, 8);
		var length = $('.shopeeDelete').length ;
		getProfEcCategoryList("shopee", $(this).val(), id + (parseInt(num) + 1));
		setEcSelect(id, num, length);
	});
	
	//蝦皮自定分類
	$(document).on("change", "select[name='shopeeShopSelect']", function() {
		var id = $(this).attr("id").substring(0, 11);
		var num = $(this).attr("id").substring(11, 12);
		var length = $('.shopeeShopDelete').length ;
		getProfEcCategoryList("shopeeShop", $(this).val(), id + (parseInt(num) + 1));
		setEcSelect(id, num, length);
	});
	
	//所有商城的刪除共用
	$(document).on("click", "Button[name='deleteItem']", function() {
		 $(this).parent().parent().remove();
	    return false;
	});
	
	//系列商品圖檔file
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
	
	//系列商品刪除按鈕
	$(document).on("click", "Button[name='seriesDelete']", function() {
		var seriesCount = $("#seriesCount").val();
		//欲移除按鈕id
		var id = $(this).attr("id");
		
		var dialog_buttons = {}; 
		dialog_buttons["確定"] = function(){
			$(this).dialog("close");
			$("#seriesCount").val(parseInt(seriesCount) - 1);
			//抓取該列位置
			var row = id.substring(id.length-1);
			//移除刪除按鈕整列<tr>
			$("#" + id).parents("tr").remove();
			//移除該列下拉式選單，並重新排列 剩餘下拉式選單、刪除按鈕 id 與 class
			rearrangeRowIndex("series", row);
		};
		dialog_buttons["取消"] = function(){
			$(this).dialog("close");
		};   
		dialogConfirm("確定要刪除嗎？", dialog_buttons);
	});
	
	//保存期限onclick事件，立即計算允收期限
	$("input[name='expirationRadio']").click(function() {
		ajaxCalAcceptableDate();
	});
	
	//保存期限onChange事件，立即計算允收期限
	$("#expirationDate").change(function() {
		ajaxCalAcceptableDate();
	});
	
	
	//新增公司資料，清空原本欄位值
	$("#btn_comp_add").on("click", function() {
		$("#thisCompForm .checkData").each(function() {
			var id = $(this).attr("id");
			$("#" + id).val("");
		});
		$("#compId").val("");
	});
	
	//例句維護
	$("#btn_sentence").on("click", function() {
		openSentence();
	});
	
	//例句下拉選單 change
	$(document).on("change", "select[name='quotation.sentenceId']", function() {
		if ($("#sentenceId").val() != "") {
			var array = $("#sentenceId").val().split("@");
			$("#auditSentence").val(array[0]);
			$("#auditOpinion").val(array[1]);
		}
	});
	//ckedit上傳圖片
	if ($("#longDescription").length > 0) {
		CKEDITOR.replace("longDescription",{
			filebrowserUploadUrl : '/APCSM/quotation/newProductSup/ckeditorUpload?docId=' + $("#docId").val()
		});
	}
	
});

//新增網購分類選項
function addItem( sourceType ){
	var mergeText =' '; 
	var lastVal = '';
	var mapText = new Map();
	var mapVal = new Map();
	var itemCount = 0 ;
	var checkSelectVal = "N";
	
	$( "." + sourceType +"Delete" ).each(function( index ) {
		var selectText  = $("option:selected", this).text();
		var selectVal  = $(this).val();

		if(selectText =="請選擇"){
			checkSelectVal = "Y";
		}
		itemCount = index ;
		mapVal.set(index, selectVal);
		mapText.set(index, selectText);
	});
	

	if(checkSelectVal == "Y"){
		dialogMessage("商品分類重複，請重新選擇");
		return ;
	}else {
		mapText.forEach(function (item, index) {
			if(index < itemCount){
				item = item + " ＞ ";
			}else {
				item = item + "　";
			}
			mergeText += item ;
		});
		
		mapVal.forEach(function (item) {
			lastVal = item ;
		});

		if(itemReCheck(true, lastVal ,sourceType ) == true ){
			html = addHtmlText( mergeText, lastVal, sourceType);
			$("#" +sourceType +"Add").find(".errorMsg").parent().parent().remove();
			$("#" +sourceType +"Add").append(html);
		}
	}

}

//檢核新增資料內容
function itemReCheck( check , val ,sourceType ){
	//預設數量
	var count=1;
	var categoryId = "check"+sourceType; 
	//自定分類數量
	if(categoryId.indexOf("Shop") > 1){
		count = 3;
	}
	if (check) {
		//官網最多3個分類
		if ($(":hidden[id="+categoryId+"]").length >= count) {
			check=false;
			dialogMessage("官網分類最多"+count+"個");
		}
	}
	if(check){
		//檢查商品分類是否有重複
		$(":hidden[id="+categoryId+"]").each(function(){
			if($(this).val() == val){
				check=false;
				dialogMessage("商品分類重複，請重新選擇");
			}
		});
	}
	return check ;
}

//開啟查詢商品分類
function openSearch(categoryType){
	$("#dialog").dialog({		
		//autoOpen: false,
		title:"查詢商品分類",
		modal:true,
		width: 900,
		height: 600,
		open:function() {
			$(this).load("/APCSM/quotation/newProductSup/dialogCategory?categoryType="+categoryType);
		}
	});	
}


//產生法規屬性HTML
function genProductRegulationsHtml($e, productType, quotationId, isDisable){
	$.ajax({
	    url: "/APCSM/genProductRegulationsHtml",
	    type: "POST",		    
	    dataType: "json",
	    data: {
	    	"productType": productType,
	    	"quotationId": quotationId,
	    	"isDisable": isDisable
	    },
	    async: false,
	    success: function(dataMap) {
	    	$e.html(dataMap.result);
	    	inputLimit();//提示剩餘字數
	    	appendRedStar();//必填欄位加上紅色星號			    	
	    	$("[data-toggle='tooltip']").tooltip();//提示
	    	getDatapicker();//有套datepicker的欄位產生日期選項
	    	setMenu4Attribute();//set法規屬性
	    	$e.find("textarea").css("overflow", "hidden");
	    }	    
	});
}

//網購資訊html
function selectEcHtml(sourceType) {
	var count = $("#" + sourceType + "Count").val();
	count = parseInt(count) + 1;
	$("#" + sourceType + "Count").val(count);
	var html = '<select id="' + sourceType + count + '1" name="' + sourceType + 'Select" class="checkData ' + sourceType;
	html += 'Delete' + count + '" dataType="select" notNull="Y">';
	$("#" + sourceType + (count-1) + "1 > option").each(function() {
		html += '<option value="' + $(this).val() + '">' + $(this).text() + '</option>';
	});
	html += '</select>';
	
	html += '<span id="' + sourceType + 'Span' + count + '3"> </span>';
	html += '<button type="button" class="btn btn-warning btn-xs ' + sourceType + 'Delete' + count + '" ';
	html += 'id="' + sourceType + 'Delete' + count + '" name="' + sourceType + 'Delete">刪除</button></br>';
	
	return html;
}

//網購資訊 html
function addHtmlText(mergeText ,lastVal ,sourceType) {
	
	var 
	html =  '<li id='+sourceType+'>';
	html += '<label></label>';
	html += 	'<div id="quotation.yahooSelectList">';
	html += 	'<input type="hidden" id=check'+sourceType+' name="quotation.'+sourceType+'SelectList" value="'+lastVal+'"/>';
	html += 		 mergeText+'<button type="button" class="btn btn-warning btn-xs"';
	html += 		'name="deleteItem">刪除</button>';
	html += 	'</div>';
	html += '</li>';
	return html ;
}


//網購資訊下拉
function getProfEcCategoryList(sourceType, categoryId, id) {
	var formData = new FormData();
	formData.append("sourceType", sourceType);
	formData.append("categoryId", categoryId);
	$.ajax({
	    url: "/APCSM/quotation/newProductSup/getProfEcCategoryList",
	    data: formData,
	    cache: false,
	    contentType: false,
	    processData: false,
	    type: "POST",
	    success: function(data) {
	    	if (data.msg == "") {
		    	//EC名
	    		var ecName = id.substring(0, id.length-2);
		    	//EC名後方編碼1
	    		var num1 = id.substring(id.length-2, id.length-1);
	    		//EC名後方編碼2
	    		var num2 = id.substring(id.length-1, id.length);

	    		var html = '';
	    		
	    		if(data.categoryIdList.length != 0) {
	    			//判斷下拉式選單是否已存在
	    			if($("#" + id).length > 0) {
	    	    		$("#" + id).empty();
	    	    		$("#" + id).append($("<option>", { 
	    	    	        value: "",
	    	    	        text : "請選擇" 
	    	    	    }));
	    	    		$.each(data.categoryIdList, function (i, item) {
	    	    			$("#" + id).append($("<option>", { 
	    	    		        value: item.CATEGORY_ID,
	    	    		        text : item.CATEGORY_NAME 
	    	    		    }));
	    	    		});
	    			} else {
	    				html += '<span id="' + ecName + 'Span' + num1 + (parseInt(num2)-1) + '"> </span>';
		    			if(num2 == 5) {
		    				html += '<select id="' + id + '" name="quotation.' + ecName + 'SelectList" ';
		    			} else {
		    				html += '<select id="' + id + '" name="' + ecName + 'Select" ';
		    			}
		    			html += 'class="checkData ' + ecName + 'Delete dataType="select" notNull="Y">'
		    			html += '<option value="">請選擇</option>';

				    	$.each(data.categoryIdList, function (i, item) {
				    		var categoryId = $("#" + ecName + "CategoryId" + (i+2)).val();
				    		html += '<option value="' + item.CATEGORY_ID + '" ' + (categoryId == item.CATEGORY_ID ? "selected" : "") +'>' + item.CATEGORY_NAME + '</option> ';
				    	});

			    		html += '</select>';
			    		
			    		$("#" + ecName + num1 + (parseInt(num2)-1)).after(html);
	    			}
	    		}
	    	} else {
	    		dialogShortMesg(data.msg);
	    	}
	    }
	});
}


function setEcSelect(id, num, length) {
	if (parseInt(num) <= length-1) {
		for (var i = parseInt(num) + 1; i <= 5; i++) {
			$("#" + id + i).remove();
			/*$("#" + id + i).append($("<option>", { 
		        value: "",
		        text : "請選擇" 
		    }));*/
		}			
	}
}

//檢核條碼
function barcodeOnblur(id) {
	var msg = "";
	var barcode = $("#" + id).val();
	if (barcode != "") {
		if(isNaN(barcode)){
			msg = "需為數字";
		}else if (isAllZero(barcode)) {
			msg = "條碼不可以都為零";
		} else if (!(barcode.length == 8 || barcode.length == 13)) {
			msg = "條碼只接受長度為8、13";
		} else if (barcode.substring(0, 2) == "21") {
			msg = "條碼開頭21，不可建立";
		} else if (barcode.substring(0, 5) == "00000") {
			msg = "條碼開頭00000，不可建立";
		} else {
			if ($("#" + id).val() != $("#" + id + "Temp").val())//如果與原本自己的條碼重複就不檢查
				checkBarcode(id);
		}
		if (msg != "") {
			dialogMessage(msg);
			$("#" + id).focus().val("");
		}
	}
}
//檢核條碼不可以都為零
function isAllZero(barcode) {
	var flag = true;
	for (var i = 0 ; i < barcode.length; i++) {
		var char = barcode.substring(i , i + 1);
		if (char != "0") {
			flag = false;
			return false;
		}
	}
	return flag;
}

//條碼不可重複
function checkBarcode(id) {
	var formData = new FormData();
	formData.append("barcode", $("#" + id).val());
	formData.append("quotationId",$("docId").val());
	$.ajax({
		url: "/APCSM/quotation/newProductSup/checkBarcode",
		data: formData,
	    cache: false,
	    contentType: false,
	    processData: false,
	    type: "POST",
		success: function(data) {
			if (data.msg == "") {				
	    	} else {
	    		dialogMessage(data.msg);
	    		$("#" + id).focus();
	    	}
		}
	});
}

//檢核名稱不能有特殊字元
function itemNameOnblur(id) {
	$("#" + id).val(replaceStr(id, ""));
	
	//如果輸入品名，需將名稱複製到網購名稱且去除屬性字
	if(id == "itemName" && $("#ecName").val() == ""){
		$("#ecName").val($("#itemName").val().replace("(R)","").replace("(S)","").replace("(買)","").replace("(預)","").replace("(宅)","").replace("(轉)","").replace("(區)","").replace("(乙)","").replace("(EC)","").replace("(試)",""));
	}
}

//檢核系列商品名稱不能有特殊字元
function seriesItemNameOnblur() {
	var seriesCount = $("#seriesCount").val();
	for(var i = 0; i <= seriesCount; i++) {
		var id = "seriesList" + i + "2";
		$("#" + id).val(replaceStr(id, ""));
		//如果輸入品名，需將名稱複製到網購名稱且去除屬性字
		if($("#" + id).val() != "" && $("#" + "seriesList" + i + "3").val() == ""){
			$("#" + "seriesList" + i + "3").val($("#" + id).val().replace("(R)","").replace("(S)","").replace("(買)","").replace("(預)","").replace("(宅)","").replace("(轉)","").replace("(區)","").replace("(乙)","").replace("(EC)","").replace("(試)",""));
		}
	}
}

//置換特殊字元
function replaceStr(id, repStr) {
	var ret = "";
	var inStr = ",'<>{}";
	var str = $("#" + id).val();
	str.replace(/\r/g, repStr);
	str.replace(/\n/g, repStr);
	str.replace(/\t/g, repStr);
	for (var i = 0 ; i < str.length; i++) {
		if (inStr.indexOf(str.charAt(i)) < 0) {
			ret += str.charAt(i);
		}
	}
	return ret;
}

//檢視長寬高範例圖片
function showDimension(){
	$("#dialog").dialog({
		title:"範例圖片",
		modal:true,
		width: $(window).width()-50,
		height: 600,
		position:"top",
		open:function(){
			$(this).load("/APCSM/quotation/newProductSup/dialogDimension?v="+getSystemTimeId());
		}		
	});
}

//是否超取：任一邊長超過42cm]、冷藏都不能門市/超商取貨
function superTake(){
	//冷藏或冷凍
	if($("#storageEnv").val() == "2" || $("#storageEnv").val() == "3" 
		|| $("#unitDepth").val() > 42 || $("#unitWidth").val() > 42 || $("#unitHigh").val() > 42 
		|| $("boxDepth").val() > 42 || $("#boxWidth").val() > 42 || $("#boxHigh").val() > 42 ){
		$("#superTakeHtml").html("否");
		$("#superTake").val("Y");
	}else{
		$("#superTakeHtml").html("是");
		$("#superTake").val("N");
	}
}

//計算允收期
function ajaxCalAcceptableDate() {
	var formData = new FormData();
	var expirationRadioValue = $("input[name=expirationRadio]:checked").val();
	
	if("Y" == expirationRadioValue) {
		formData.append("itemReserve", 4015);
		$("#expirationDate").val("");
	} else if("N" == expirationRadioValue) {
		formData.append("itemReserve", $("#expirationDate").val());
	} else {
		formData.append("itemReserve", "");
	}
	
	formData.append("strOriginClass", $("#isImport").val());
	formData.append("featureCode", $("#featureCode").val());
	$.ajax({
		url: "/APCSM/quotation/newProductSup/ajaxCalAcceptableDate",
		data: formData,
	    cache: false,
	    contentType: false,
	    processData: false,
	    type: "POST",
		success: function(data) {
			if (data.msg == "") {
				//計算允收日，需判斷是否已填寫"是否可退"與"商品產地"，若此兩項未選擇，則不回傳允收計算結果
				if($("#isReturn").val() != "" && $("#isImport").val() != "") {
					$("#acceptableDateTag").html($ESAPI.encoder().encodeForHTML(data.acceptableDate));
				}
	    	} else {
	    		dialogMessage(data.msg);
	    		$("#" + id).focus();
	    	}
		}
	});
}

//商品屬性
function getCharacterList(chtype, chid, htmlId) {
	var formData = new FormData();
    formData.append("chtype", chtype);
    formData.append("chid", chid);
	formData.append("chnm", "");
	$.ajax({
	    url: "/APCSM/quotation/newProductSup/getCharacterList",
	    data: formData,
	    cache: false,
	    contentType: false,
	    processData: false,
	    type: "POST",
	    success: function(data) {
	    	if (data.msg == "") {
	    		var items = data.characterList;
		    	$.each(items, function (i, item) {
		    		$("#" + htmlId).html(item.CHID + "-" + item.CHNM);
		    	});
	    	} else {
	    		dialogMessage(data.msg);
	    	}
	    }
	});
}

//檢視範例圖片
function showSamplePic(){
	$("#dialog").dialog({
		title:"範例圖片",
		modal:true,
		width: $(window).width()-50,
		height: 600,
		position:"top",
		open:function(){
			$(this).load("/APCSM/quotation/newProductSup/dialogSamplePic?v="+getSystemTimeId());
		}		
	});
}

//檢視商品禁用字
function showBanWord(){
	$("#dialog").dialog({
		title:"商品禁用字",
		modal:true,
		width: $(window).width()-50,
		height: 800,
		position:"top",
		open:function(){
			$(this).load("/APCSM/quotation/newProductSup/showBanWord?v="+getSystemTimeId());
		}		
	});
}


//檔案上傳
function uploadFile(file, imageId, menu) {
	var formData = new FormData();
	formData.append("file", file);
	formData.append("docId", $("#docId").val());
	formData.append("attachId", $("#" + imageId).val());
	formData.append("menu", menu);
	formData.append("imageId", imageId);
	$.ajax({
		url: "/APCSM/quotation/newProductSup/uploadFile",
		data: formData,
	    cache: false,
	    contentType: false,
	    processData: false,
	    type: "POST",
		success: function(data) {
			if (data.msg == "") {
				$("#" + imageId).val(data.attachId);
	    	} else {
	    		dialogMessage(data.msg);
	    	}
		}
	});
}

//刪除圖檔XX(正面主圖)
function doDelMenu3File(attachId) {
	var formData = new FormData();
	formData.append("attachId", attachId);
	$.ajax({
	    url: "/APCSM/quotation/newProductSup/attachDelete",
	    data: formData,
	    cache: false,
	    contentType: false,
	    processData: false,
	    type: "POST",
	    success: function(data) {
	    	if (data.msg != "") {
	    		//$(".attachId_" + attachId).parent().remove();
	    		$(".attachId_" + attachId).remove();
	    		if ($("#itemImage1").val() == attachId) $("#itemImage1").val("");
	    		if ($("#itemImage2").val() == attachId) $("#itemImage2").val("");
	    		if ($("#itemImage3").val() == attachId) $("#itemImage3").val("");
	    		if ($("#itemImage4").val() == attachId) $("#itemImage4").val("");
	    		//dialogMessage(data.msg);
	    	} else {
	    		dialogMessage(data.msg);
	    	}
	    }
	});
}

//刪除法規屬性則
function doDelMenu4File(attachId,featureCode) {
	var formData = new FormData();
	formData.append("attachId", attachId);
	$.ajax({
	    url: "/APCSM/quotation/newProductSup/attachDelete",
	    data: formData,
	    cache: false,
	    contentType: false,
	    processData: false,
	    type: "POST",
	    success: function(data) {
	    	if (data.msg != "") {
	    		$(".attachBlock_" + attachId).remove();
	    		$(".attachHidden_" + attachId).val("");
	    		$("#"+featureCode).attr("notNull","Y");
	    		//dialogMessage(data.msg);
	    	} else {
	    		dialogMessage(data.msg);
	    	}
	    }
	});
}
//廠商資料維護
function openManufacturer(profCompanyList) {
	$("#dialog").dialog({		
		//autoOpen: false,
		title:"廠商資料維護",
		modal:true,
		width: $(window).width()-50,
		height: 600,
		position:"top",
		open:function() {
			$(this).load("/APCSM/quotation/newProductSup/dialogManufacturerEdit?companyType="+$("#profCompanyType").val()+"&"+getSystemTimeId());
		}
	});	
	
	if (profCompanyList != undefined) {
		var id = $("#profCompanyType").val() + "Company";
		setCompanyList(profCompanyList, id);
	}
}

//設定廠商資料下拉選單
function setCompanyList(profCompanyList, id) {	
	$("#" + id).empty();
	$("#" + id).append($("<option>", { 
        value: "",
        text : "請選擇" 
    }));
	$.each(profCompanyList, function (i, item) {
		$("#" + id).append($("<option>", { 
	        value: item.VAT,
	        text : item.APPELLATION 
	    }));
	});
}


//編輯廠商資料
function manufacturerEdit(companyType) {
	$("#profCompanyType").val(companyType);
	openManufacturer();
	//$("#dialog").dialog("open");
}

//編輯廠商資料
function editManufacturer(compId) {
	$("#compId").val(compId);
	var formData = new FormData();
	formData.append("compId", compId);
	$.ajax({
	    url: "/APCSM/quotation/newProductSup/editManufacturer",
	    data: formData,
	    cache: false,
	    contentType: false,
	    processData: false,
	    type: "POST",
	    success: function(data) {
	    	if (data.msg == "") {
	    		$("#owner").val(data.profCompany.owner);
	    		$("#appellation").val(data.profCompany.appellation);
	    		$("#vat").val(data.profCompany.vat);
	    		$("#tel").val(data.profCompany.tel);
	    		$("#address").val(data.profCompany.address);
	    		$("#serviceLine").val(data.profCompany.serviceLine);
	    		$("#serviceTime").val(data.profCompany.serviceTime);
	    		$("#website").val(data.profCompany.website);
	    		$("#drugLicenseNo").val(data.profCompany.drugLicenseNo);
	    		$("#stronghold").val(data.profCompany.stronghold);
	    		if (data.profCompany.delegate == "Y")
	    			$("#delegate").prop("checked", true);
	    		else
	    			$("#delegate").prop("checked", false);
	    		$("#compCreateUser").val(data.profCompany.createUser);
	    		$("#compCreateTime").val(data.profCompany.createTime);
	    	} else {
	    		dialogMessage(data.msg);
	    	}
	    }
	});
}

//新增廠商資訊
function insertProfCompany(companyType) {
	var formData = new FormData();
	if($("#principalCompany").val() == ""){
		dialogMessage("請選擇國內負責廠商");
		$("#manufacture").prop("checked",false);
	}else{
		formData.append("companyType", companyType);
		formData.append("principalCompany", $("#principalCompany").val());
		$.ajax({
		    url: "/APCSM/quotation/newProductSup/insertProfCompany",
		    data: formData,
		    cache: false,
		    contentType: false,
		    processData: false,
		    type: "POST",
		    success: function(data) {
		    	if (data.msg == "") {
		    		var id = companyType + "Company";
		    		setCompanyList(data.profCompanyList, id);
		    		if ($("#principalCompany").val() != "")
		    			$("#" + id).val($("#principalCompany").val());
		    	} else {
		    		dialogMessage(data.msg);
		    	}
		    }
		});
	}
	
}
//取得製造商資訊
function getManufacturerJsonData() {
	var object = {};
	$("#thisCompForm .checkData").each(function() {
		var id = $(this).attr("id");
		var value = $(this).val() == null ? "" : $(this).val();
		object[id] = value;
	});
	return object;
}

//set法規屬性
function setMenu4Attribute() {
	$("#menu4 .checkData").each(function() {
		if ($(this).attr("type") == "file") {
			$(this).attr("name", "menu4File");
		} else {
			$(this).attr("name", "quotation.attributeValueList");
		}
	});
}

//無條碼控制
function noBarcodeCtrl(){
	if($("#noBarcode").is(":checked")){
		$("#barcode1").val("").prop("disabled",true);
		$("#barcode2").val("").prop("disabled",true);

		//系列商品數條碼欄位disabled
		var seriesCount = $("#seriesCount").val();
		for(var i=0; i<=seriesCount; i++) {
			for(var j=0; j<=1; j++) {
				$("#seriesList" + i + j).val("").prop("disabled",true);
			}
		}
	}else{
		$("#barcode1").prop("disabled",false);
		$("#barcode2").prop("disabled",false);
		
		//系列商品數條碼欄位顯示
		var seriesCount = $("#seriesCount").val();
		for(var i=0; i<=seriesCount; i++) {
			for(var j=0; j<=1; j++) {
				$("#seriesList" + i + j).prop("disabled",false);
			}
		}
	}
}
//產地別控制
function orgnCityCtrl(){
	if ($("#isImport").val() == "0")
		$(".ref_originCountry").show();
	else
		$(".ref_originCountry").hide();
}

//法規屬性控制
function featureCodeCtrl(){
	//如果選擇「食品類、小綠人」，才需要顯示「製造廠商資訊已通報轄區主管機關」選項，且製造廠商改不必填
	if($("#featureCode").val() == "02" || $("#featureCode").val() == "03"){
		$(".manufactureCompanyNotice").show();
		$("#manufactureCompany").attr("notNull","N");
	//如果選擇「藥品、醫療器材-法規網路不可售」時，網購資訊頁就可以不必填
	}else if($("#featureCode").val() == "08" || $("#featureCode").val() == "09"){
		$("#yahoo01").add("#eshop01").add("#shopee01").add("#comsed01")
		.add("#yahoo02").add("#eshop02").add("#shopee02").add("#comsed02")
		.add("#yahoo03").add("#eshop03").add("#shopee03").add("#comsed03")
		.add("#yahoo04").add("#eshop04").add("#shopee04").add("#comsed04")
		.add("#yahoo05").add("#eshop05").add("#shopee05").add("#comsed05")
		.attr("notNull","N");
		$("#yahooBlock").parent().find("font").text("");
		$("#eshopBlock").parent().find("font").text("");
		$("#shopeeBlock").parent().find("font").text("");
		$("#comsedBlock").parent().find("font").text("");
	}else{
		$(".manufactureCompanyNotice").hide();
		$("#manufactureCompanyNotice").prop("checked",false);
		$("#manufactureCompany").attr("notNull","Y");
		$("#yahoo01").add("#eshop01").add("#shopee01").add("#comsed01")
		.add("#yahoo02").add("#eshop02").add("#shopee02").add("#comsed02")
		.add("#yahoo03").add("#eshop03").add("#shopee03").add("#comsed03")
		.add("#yahoo04").add("#eshop04").add("#shopee04").add("#comsed04")
		.add("#yahoo05").add("#eshop05").add("#shopee05").add("#comsed05")
		.attr("notNull","Y");
	}
}
//商品種類控制
function kindCodeCtrl(){
	var isSelfPrd=false;//是否為自用商品
	//如果是商品屬性選擇試用品
	$("input[name='quotation.chidList']").each(function() {
		if ($(this).is(":checked")) {
			//如果是試用品
	    	 if ($(this).val() == "11") {	 	    	
	    		 isSelfPrd=true;
	 	    }
	    }
	});
	//大類是97、98類
	if($("#familyCode").val().substring(4, 6) == "97" || $("#familyCode").val().substring(4, 6) == "98"){
		isSelfPrd=true;
	}
	//如果大類是20、24、26類，網購資訊頁就可以不必填
	if($("#familyCode").val().substring(4, 6) == "20"
		|| $("#familyCode").val().substring(4, 6) == "24"
		|| $("#familyCode").val().substring(4, 6) == "26") {
		$("#yahoo01").add("#eshop01").add("#shopee01").add("#comsed01")
		.add("#yahoo02").add("#eshop02").add("#shopee02").add("#comsed02")
		.add("#yahoo03").add("#eshop03").add("#shopee03").add("#comsed03")
		.attr("notNull","N");
	}
	//判斷結果是否為自用品
	if(isSelfPrd){
		getCharacterList("101", "1", "kindCodeTag");
		$("#kindCode").val("1");
	}else{
		getCharacterList("101", "0", "kindCodeTag");
		$("#kindCode").val("0");
	}
}

function isInteger(obj) {
    return (obj | 0) === obj;
}

/*function ecAuto(ecName, row, index, run) {
	if(run) {
		var categoryIdList = [];
		for (var i = 0; i < index ; i++) {
			var categoryId = $('#' + ecName +"CategoryId"+ row + i).val();
			console.log("CategoryId="+categoryId);
			if( categoryId != "" &&  typeof categoryId != 'undefined'){
				categoryIdList.push(categoryId);
			}
		}

		/*var formData = new FormData();
		formData.append("sourceType", ecName);
		formData.append("categoryId", categoryIdList);
		$.ajax({
		    url: "/APCSM/quotation/newProductSup/getProfEcAllCategoryList",
		    data: formData,
		    cache: false,
		    contentType: false,
		    processData: false,
		    type: "POST",
		    success: function(data) {
		    	console.log(data);
		    	if (data.msg == "") {
		    		var html = '';
		    		var next = parseInt(index)+1;
		    		
		    		if(data.categoryIdList.length != 0) {
		    			console.log(data.categoryIdList);
				    	$.each(data.categoryIdList, function (i, item) {
				    		var categoryId = $("#" + ecName + "CategoryId" + row + next).val();
				    		console.log("for each categoryId="+ categoryId);
				    		html += '<option value="' + item.CATEGORY_ID + '" ' + (categoryId == item.CATEGORY_ID ? "selected" : "") +'>' + item.CATEGORY_NAME + '</option> ';
				    		
				    	});
			    		$("#"+ecName+"Add").append(html);
			    		
		    		} else {
		    			return;
		    		}
		    		ecAuto(ecName, row, next, run);
			    } else {
		    		dialogShortMesg(data.msg);
		    	}
		    }
		});
	}
}*/

/* 重新排列下拉式選單row編號，因刪除後新增有row index之問題
 * ex.原先為二下拉式選單 第一列id為 yahoo01、yahoo02、yahoo03
 *                第二列id為 yahoo11、yahoo12、yahoo13
 *                
 *    此時若先刪除 第一列，則再點選新增時，新產出的列數 id 仍為 yahoo11、yahoo12、yahoo13
 *    因刪除時會將 yahooList.length-1，而新增時會將yahooList.length+1
 *    所以導致此種情形發生，因此需刪除後將 id 編碼重新排序
 */
function rearrangeRowIndex(sourceType, row) {
	//EC網購資訊用
	if(sourceType != "series") {
		//移除刪除列的各空<span>
		$("span[id^='" + sourceType + 'Span' + row + "']").each(function(){
			$(this).remove();
		});
		
		//移除刪除列的下拉式選單
		$("select[id^='" + sourceType + row + "']").each(function(){
			$(this).remove();
		});
	}
	
	var count = $("#" + sourceType + "Count").val();
	
	//取得刪除該列之後的row位置
	afterDeleteRow = parseInt(row)+1;
	beforeDeleteCount = parseInt(count)+1;

	for(var i = afterDeleteRow ; i <= beforeDeleteCount; i++) {
		//更改刪除button class 與 id
		$("#" + sourceType + "Delete" + i).attr("class", "btn btn-success btn-xs " +  sourceType + "Delete" + (parseInt(i)-1));
		$("#" + sourceType + "Delete" + i).attr("id", sourceType + "Delete" + (parseInt(i)-1));
		//系列商品用
		if(sourceType == "series") {
			for(var j = 0; j <= 8; j++) {
				$("#" + sourceType + "List" + i + j).attr("id", sourceType + "List" + (parseInt(i)-1) + j);
				if(j == 8) {
					$("#" + sourceType + "Image" + i + j).attr("id", sourceType + "Image" + (parseInt(i)-1) + j);
				}
			}
			
		//EC網購資訊用
		} else {
			for(var j = 1; j <= 3; j++) {
				//下拉式選單重新編製id
				if($("#" + sourceType + i + j).length > 0) {
					$("#" + sourceType + i + j).attr("id", sourceType + (parseInt(i)-1) + j);
				}
				//下拉式選單後方<span>重新編製id
				if($("#" + sourceType + "Span" + i + j).length > 0) {
					$("#" + sourceType + "Span" + i + j).attr("id", sourceType + "Span" + (parseInt(i)-1) + j);
				}
			}
		}
	}
}

//按上方的切換頁籤
function changeTab(menuId){
	$(".menuList li").removeClass("active");
	$(".menuList li").eq(menuId-1).addClass("active");
	$(".tab-pane").removeClass("in active");
	$("#menu" + (menuId)).addClass("in active");
	$("#menuId").val("menu"+menuId);//記下來目前位置，為了讓按上、下頁使用
}

//按上一頁的頁籤顏色反應
function btnBackTab(){
	//判斷目前是點哪一個頁籤，找出上一頁的編號
	var num = $("#menuId").val().replace("menu", "");
	$(".menuList li").removeClass("active");
	$(".menuList li").eq(parseInt(num-2)).addClass("active");
	$(".tab-pane").removeClass("in active");
	$("#menu" + (parseInt(num)-1)).addClass("in active");
	$("#menuId").val("menu"+(parseInt(num)-1));//記下來目前位置，為了讓按上、下頁使用
}

//按下一頁的頁籤顏色反應
function btnForwardTab(){
	//判斷目前是點哪一個頁籤，找出下一頁的編號
	var num = $("#menuId").val().replace("menu", "");
	$(".menuList li").removeClass("active");
	$(".menuList li").eq(num).addClass("active");
	$(".tab-pane").removeClass("in active");
	$("#menu" + (parseInt(num)+1)).addClass("in active");
	$("#menuId").val("menu"+(parseInt(num)+1));//記下來目前位置，為了讓按上、下頁使用
}

//例句維護
function openSentence(sentenceList) {
	$("#dialog").dialog({		
		//autoOpen: false,
		title:"例句維護",
		modal:true,
		width: $(window).width() - 700,
		height: 500,
		position:"top",
		open:function() {
			$(this).load("/APCSM/quotation/newProductCos/dialogSentenceEdit");
		}
	});
	
	if (sentenceList != undefined) {
		$("#sentenceId").empty();
		$("#sentenceId").append($("<option>", { 
	        value: "",
	        text : "請選擇" 
	    }));
		$.each(sentenceList, function (i, item) {
			$("#sentenceId").append($("<option>", { 
		        value: item.ID + "@" + item.SENTENCE,
		        text : item.SENTENCE 
		    }));
		});
	}
}

//編輯例句
function editSentence(sentId) {
	$("#sentId").val(sentId);
	var formData = new FormData();
	formData.append("sentId", sentId);
	$.ajax({
	    url: "/APCSM/quotation/newProductCos/editSentence",
	    data: formData,
	    cache: false,
	    contentType: false,
	    processData: false,
	    type: "POST",
	    success: function(data) {
	    	if (data.msg == "") {
	    		$("#sentence").val(data.flowProfSentence.sentence);
	    		$("#sentCreateUser").val(data.flowProfSentence.createUser);
	    		$("#sentCreateTime").val(data.flowProfSentence.createTime);
	    	} else {
	    		dialogShortMesg(data.msg);
	    	}
	    }
	});
}

//基本資料檢查
function checkMenu1() {
	//異動提報
	//新商品名稱
	if($("#newItemName").val() != ""){
		if($("#agreeName").val() == ""){
			addHtmlNew($("#agreeName"),"必填");
			return false;
		}else{
			if($("#agreeName").val() != "01" && $("#agreeNameSub").val() ==""){
				addHtmlNew($("#agreeNameSub"),"必填");
				return false;
			}
		}
	}
	//新條碼
	if($("#newBarcode").val() != "" && $("#agreeBarcode").val() == ""){
		addHtmlNew($("#agreeBarcode"),"必填");
		return false;
	}
	
	return true;
}

//入倉資料檢查
function checkMenu2() {
	//國產
	if ($("#isImport").val() == "1") {
		if (parseInt($("#qtryCtn").val()) > (parseInt($("#packQty").val()) * 60)) {
			dialogMessage("外箱入數需小於等於「收縮數」的60倍");
			$("#qtryCtn").focus();
			return false;
		}
	}
	//進口
	if ($("#isImport").val() == "0") {
		if ($("#orgnCountry").val() == "") {
			dialogMessage("請輸入進口國別");
			$("#orgnCountry").focus();
			return false;
		}
		if($("#orgnCountry").val() == "JP" && $("#orgnCity").val() == ""){
			dialogMessage("進口城市需必填");
			return false();
		}
		if (!isInteger(parseFloat($("#qtryCtn").val()) / parseFloat($("#packQty").val()))) {
			dialogMessage("「外箱入數」需為「收縮數」的倍數");
			$("#qtryCtn").focus();
			return false;
		}
	}
	//價格
	if(parseFloat($("#marketPriceTax").val()) < parseFloat($("#priceTax").val())){
		dialogMessage("市價需大於等於零售價");
		return false;
	}
	//限量商品
	if ($("#limitQty").val() != "") {
		if ($("#limitStartDate").val() == "") {
			dialogMessage("請輸入限量期間(起)");
			$("#limitStartDate").focus();
			return false;
		} else {
			
		}
		if ($("#limitEndDate").val() == "") {
			dialogMessage("請輸入限量期間(迄)");
			$("#limitEndDate").focus();
			return false;
		} else {
			
		}
		if ($("#packQty").val() != "") {
			if (!isInteger(parseFloat($("#limitQty").val()) / parseFloat($("#packQty").val()))) {
				dialogMessage("限量商品數需為[收縮數]的倍數");
				$("#limitQty").focus();
				return false;
			}
		}
	}
	//EC限量商品
	if ($("#limitEcQty").val() != "") {
		if ($("#packQty").val() != "") {
			if (!isInteger(parseFloat($("#limitEcQty").val()) / parseFloat($("#packQty").val()))) {
				dialogMessage("限量商品數需為[收縮數]的倍數");
				$("#limitEcQty").focus();
				return false;
			}
		}
		if ($("#limitQty").val() != "") {
			if (parseFloat($("#limitEcQty").val()) > parseFloat($("#limitQty").val())) {
				dialogMessage("EC限量數需小於限量總數");
				$("#limitQty").focus();
				return false;
			}
		}
	}
	
	//單品尺吋
	if(parseFloat($("#unitDepth").val()) + parseFloat($("#unitWidth").val()) + parseFloat($("#unitHigh").val()) > 1350){
		dialogMessage("單品尺吋三邊加總不可超過1350mm");
		return false;
	}
	//單品重量
	if(parseFloat($("#unitWeight").val())> 15000){
		dialogMessage("單品重量不可大於15000g");
		return false;
	}
	//外箱尺吋
	if(parseFloat($("#boxDepth").val()) + parseFloat($("#boxWidth").val()) + parseFloat($("#boxHigh").val()) > 1350){
		dialogMessage("外箱尺吋三邊加總不可超過1350mm");
		return false;
	}
	//外箱重量
	if(parseFloat($("#boxWeight").val())> 15000){
		dialogMessage("外箱重量不可大於15000g");
		return false;
	}
	//保存期限
	if ($("input[name=expirationRadio]:checked").val() == "Y") {//無保存期
		$("#expirationDate").val("4015");
	} else if ($("input[name=expirationRadio]:checked").val() == "N") {
		//有保存期
		if ($("#expirationDate").val() == "") {
			dialogMessage("請輸入保存期限(日)");
			$("#expirationDate").focus();
			return false;
		} else {
			if (parseInt($("#expirationDate").val()) > 4015) {
				dialogMessage("保存期限(日)不可大於4015");
				$("#expirationDate").focus();
				return false;
			}
		}
	}
	
	//異動提報
	//新價格
	
	if(($("#newCostTax").val() != "" || $("#newPriceTax").val() != "") && $("#agreePrice").val() == ""){
		addHtmlNew($("#agreePrice"),"必填");
		return false;
	}
	//新收縮數、外箱數
	if($("#newPackQty").val() == "" && $("#newQtryCtn").val() != ""){
		addHtmlNew($("#newPackQty"),"必填");
		return false;
	}
	if($("#newPackQty").val() != "" && $("#newQtryCtn").val() == ""){
		addHtmlNew($("#newQtryCtn"),"必填");
		return false;
	}
	if(($("#newPackQty").val() != "" || $("#newQtryCtn").val() != "") && $("#agreeSpec").val() == ""){
		addHtmlNew($("#agreeSpec"),"必填");
		return false;
	}
	return true;
}

//商品資訊
function checkMenu3() {
	if ($("#brandCodeTags").val() == "") {
		$("#brandCode").val("");
		dialogMessage("請輸入品牌");
		$("#brandCodeTags").focus();
		return false;
	}
	if ($("#itemImage1").val() == "") {
		dialogMessage("圖檔一(正面主圖)必填");
		$("#itemImage1Menu3").focus();
		return false;
	}
	if ($("#itemImage2").val() == "") {
		dialogMessage("圖檔二(背面圖)必填");
		$("#itemImage2Menu3").focus();
		return false;
	}
	if ($("#brandCode").val() == "") {
		dialogMessage("品牌必填");
		$("#brandCodeTags").focus();
		return false;
	}
	return true;
}

//法規屬性
function checkMenu4() {
	return true;
}

//網購資訊
function checkMenu5() {
	//檢查是否有輸入至少一筆資料
	var check_result = true;
	var ecNameArray = ['yahooAdd','eshopAdd','shopeeAdd','yahooShopAdd','eshopShopAdd','shopeeShopAdd'];
		//如果選擇「藥品、醫療器材-法規網路不可售」，或大類是20、24、26類時，網購資訊頁就可以不必填
		if($("#featureCode").val() != "08" && $("#featureCode").val() != "09" && 
		   $("#familyCode").val().substring(4, 6) != "20" && $("#familyCode").val().substring(4, 6) != "24" && 
		   $("#familyCode").val().substring(4, 6) != "26"){
			for (var i = 0; i < ecNameArray.length; i++) {
				var ecName = ecNameArray[i];
				$("#"+ecName +" .child").remove();
				if($("#"+ecName).html().trim() == '' ||  $("#"+ecName).html().trim() == undefined ){
					$('#'+ecName).html("<li class='child'><label></label><div><span class='errorMsg'>請至少新增一筆資料</span><div></li>");
					check_result = false;
				}
			}
		}		
	return check_result;
}

//系列商品
function checkMenu6(row) {
	var checkOk = "";
	var method = $("#uploadImageMethod").val();
	//新品提報
	if("new" == method) {
		var barcode = $("#seriesList" + row + "0").val();
		var barcode_disabled = $("#seriesList" + row + "0").prop("disabled");
		var barcode2 = $("#seriesList" + row + "1").val();
		var barcode2_disabled = $("#seriesList" + row + "0").prop("disabled");
		var itemName = $("#seriesList" + row + "2").val();
		var ecItemName = $("#seriesList" + row + "3").val();
		var limitQty = $("#seriesList" + row + "4").val();
		var ecLimitQty = $("#seriesList" + row + "5").val();
		var limitStartDate = $("#seriesList" + row + "6").val();
		var limitEndDate = $("#seriesList" + row + "7").val();
		var imageFile = $("#seriesList" + row + "8").val();
		var image = $("#seriesImage" + row + "8").val();
		
		//條碼反白，其餘欄位皆未填寫
		if(barcode_disabled && barcode2_disabled && itemName == "" && ecItemName == ""
			&& limitQty == "" && limitQty == "" && ecLimitQty == "" && limitStartDate == ""
			&& limitEndDate == "" && imageFile == "") {
			checkOk = true;
			return checkOk;
		//條碼沒反白，所有欄位皆未填寫
		} else if(!barcode_disabled && !barcode2_disabled && barcode == "" && barcode2 == ""
			&& itemName == "" && ecItemName == "" && limitQty == "" && limitQty == "" 
			&& ecLimitQty == "" && limitStartDate == "" && limitEndDate == "" && imageFile == "") {
			checkOk = true;
			return checkOk;
		} else {
			//條碼欄位若沒有disabled則必填
			if(!barcode_disabled && barcode == "") {
				dialogMessage("請輸入條碼");
				checkOk = false;
				return checkOk;
			}
				
			//商品名稱必填
			if(itemName == "") {
				dialogMessage("請輸入商品名稱");
				checkOk = false;
				return checkOk;
			}
				
			//網購商品名稱必填
			if(ecItemName == "") {
				dialogMessage("請輸入網購商品名稱");
				checkOk = false;
				return checkOk;
			}
				
			//正面圖必填
			if(imageFile == "" && image == "") {
				dialogMessage("請上傳正面圖");
				checkOk = false;
				return checkOk;
			}
			
			//限量總數須為收縮數的倍數、限量商品須輸入限量期間
			if(limitQty != "") {
				var packQty = $("#packQty").val();
				if (packQty == "") {
					dialogMessage("收縮數尚未填寫，請先填寫收縮數");
					$("#seriesList" + row + "4").val("");
					checkOk = false;
					return checkOk;
				} else {
					if (!isInteger(parseFloat(limitQty) / parseFloat(packQty))) {
						dialogMessage("限量總數需為[收縮數]的倍數");
						checkOk = false;
						return checkOk;
					} else {
						if (limitStartDate == "") {
							dialogMessage("請輸入限量起日");
							checkOk = false;
							return checkOk;
						}
						if (limitEndDate == "") {
							dialogMessage("請輸入限量迄日");
							checkOk = false;
							return checkOk;
						}
					}
					
				}
			}
			
			//EC限量數不得大於限量總數
			if (ecLimitQty != "") {
				if (limitQty != "") {
					if (parseFloat(ecLimitQty) > parseFloat(limitQty)) {
						dialogMessage("EC限量數需小於限量總數");
						checkOk = false;
						return checkOk;
					}
				} else {
					dialogMessage("請填寫限量總數");
					checkOk = false;
					return checkOk;
				}
			}
			
			//限量迄日不得大於限量起日
			if(limitStartDate != "" && limitEndDate != "") {
				if (limitQty != "") {
					var startDate = limitStartDate.substring(0, 4) + "/" + limitStartDate.substring(4, 6) + "/" + limitStartDate.substring(6, 8);
					var endDate = limitEndDate.substring(0, 4) + "/" + limitEndDate.substring(4, 6) + "/" + limitEndDate.substring(6, 8);
					if(Date.parse(startDate).valueOf() > Date.parse(endDate).valueOf()) {
						dialogMessage("限量起日需小於限量迄日");
						checkOk = false;
						return checkOk;
					}
				} else {
					dialogMessage("請填寫限量總數");
					checkOk = false;
					return checkOk;
				}
			}
		}

		if(checkOk == "") {
			checkOk = true;
			return checkOk;
		}
	//異動提報	
	} else if("modify" == method) {
		var newBarcode = $("#seriesList" + row + "1").val();
		var newItemName = $("#seriesList" + row + "2").val();
		var limitQty = $("#seriesList" + row + "3").val();
		var ecLimitQty = $("#seriesList" + row + "4").val();
		var limitStartDate = $("#seriesList" + row + "5").val();
		var limitEndDate = $("#seriesList" + row + "6").val();
		var imageFile = $("#seriesList" + row + "7").val();
		var image = $("#seriesImage" + row + "7").val();
		
		//新商品名稱、新條碼、限量總數、EC限量數、限量起日、限量迄日為空
		if(newBarcode == "" && newItemName == "" && limitQty == "" 
			&& ecLimitQty == "" && limitStartDate == "" && limitEndDate == "" 
			&& (imageFile != "" || imageFile != "")) {
			checkOk = true;
			return checkOk;
		} else {
			//正面圖必填
			if(imageFile == "" && image == "") {
				dialogMessage("請上傳正面圖");
				checkOk = false;
				return checkOk;
			}
			
			//限量總數須為收縮數的倍數、限量商品須輸入限量期間
			if(limitQty != "") {
				//新收縮數
				var newPackQty = $("#newPackQty").val();
				//舊收縮數
				var packQty = $("#packQty").val();
				
				if (newPackQty != "") {
					if (!isInteger(parseFloat(limitQty) / parseFloat(newPackQty))) {
						dialogMessage("限量總數需為[新收縮數]的倍數");
						checkOk = false;
						return checkOk;
					} else {
						if (limitStartDate == "") {
							dialogMessage("請輸入限量起日");
							checkOk = false;
							return checkOk;
						}
						if (limitEndDate == "") {
							dialogMessage("請輸入限量迄日");
							checkOk = false;
							return checkOk;
						}
					}
				} else {
					if (!isInteger(parseFloat(limitQty) / parseFloat(packQty))) {
						dialogMessage("限量總數需為[收縮數]的倍數");
						checkOk = false;
						return checkOk;
					} else {
						if (limitStartDate == "") {
							dialogMessage("請輸入限量起日");
							checkOk = false;
							return checkOk;
						}
						if (limitEndDate == "") {
							dialogMessage("請輸入限量迄日");
							checkOk = false;
							return checkOk;
						}
					}
				}		
			}
			
			//EC限量數不得大於限量總數
			if (ecLimitQty != "") {
				if (limitQty != "") {
					if (parseFloat(ecLimitQty) > parseFloat(limitQty)) {
						dialogMessage("EC限量數需小於限量總數");
						checkOk = false;
						return checkOk;
					}
				} else {
					dialogMessage("請填寫限量總數");
					checkOk = false;
					return checkOk;
				}
			}
			
			//限量迄日不得大於限量起日
			if(limitStartDate != "" && limitEndDate != "") {
				if (limitQty != "") {
					var startDate = limitStartDate.substring(0, 4) + "/" + limitStartDate.substring(4, 6) + "/" + limitStartDate.substring(6, 8)
					var endDate = limitEndDate.substring(0, 4) + "/" + limitEndDate.substring(4, 6) + "/" + limitEndDate.substring(6, 8)
					if(Date.parse(startDate).valueOf() > Date.parse(endDate).valueOf()) {
						dialogMessage("限量起日需小於限量迄日");
						checkOk = false;
						return checkOk;
					}
				} else {
					dialogMessage("請填寫限量總數");
					checkOk = false;
					return checkOk;
				}
			}
		}
		
		//新條碼
		if(newBarcode != "" && $("#series" + row + "AgreeBarcode").val() == "") {
			addHtmlNew($("#series" + row + "AgreeBarcode"), "必填");
			checkOk = false;
			return checkOk;
		}
		
		//新商品名稱
		if(newItemName != ""){
			if($("#series" + row + "AgreeName").val() == "") {
				addHtmlNew($("#series" + row + "AgreeName") ,"必填");
				checkOk = false;
				return checkOk;
			} else {
				if($("#series" + row + "AgreeName").val() != "01" && $("#series" + row + "AgreeNameSub").val() =="") {
					addHtmlNew($("#series" + row + "AgreeNameSub"), "必填");
					checkOk = false;
					return checkOk;
				}
			}
		}

		if(checkOk == "") {
			checkOk = true;
			return checkOk;
		}
	} else {
		checkOk = true;
		return checkOk;
	}
}

//提報大類別 change
function getClassCodeList() {
	var formData = new FormData();
	formData.append("familyCode", $("#familyCode").val());
	$.ajax({
	    url: "/APCSM/quotation/newProductSup/getClassCodeList",
	    data: formData,
	    cache: false,
	    contentType: false,
	    processData: false,
	    type: "POST",
	    success: function(data) {
	    	if (data.msg == "") {
	    		$("#classCode").empty();
		    	var items = data.classCodeList;
		    	$("#classCode").append($("<option>", { 
	    	        value: "",
	    	        text : "請選擇" 
	    	    }));
		    	$.each(items, function (i, item) {
		    	    $("#classCode").append($("<option>", { 
		    	        value: item.CATE_ID_2,
		    	        text : item.CATE_NAME_2 
		    	    }));
		    	});
	    	} else {
	    		dialogMessage(data.msg);
	    	}
	    }
	});
}

//提報次類別 change
function getBrickCodeList() {
	var formData = new FormData();
	formData.append("classCode", $("#classCode").val());
	$.ajax({
	    url: "/APCSM/quotation/newProductSup/getBrickCodeList",
	    data: formData,
	    cache: false,
	    contentType: false,
	    processData: false,
	    type: "POST",
	    success: function(data) {
	    	if (data.msg == "") {
	    		$("#brickCode").empty();
		    	var items = data.brickCodeList;
		    	$("#brickCode").append($("<option>", { 
	    	        value: "",
	    	        text : "請選擇" 
	    	    }));
		    	$.each(items, function (i, item) {
		    	    $("#brickCode").append($("<option>", { 
		    	        value: item.CATE_ID_3,
		    	        text : item.CATE_NAME_3 
		    	    }));
		    	});
	    	} else {
	    		dialogShortMesg(data.msg);
	    	}
	    }
	});
}

