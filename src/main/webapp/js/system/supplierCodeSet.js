$(document).ready(function(){

	// 選帳號帶出場編
	$("#supplierIdList").change(function(){
		$("#supplierCodeNotSet option").remove();
		$("#supplierCodeIsSet option").remove();
		$("#email").val("");
		$("#newPwd").val("");
		$("#newPwd2").val("");
		getSupplierCodeIsSet();
		getSupplierCodeNotSet();
		getUserEmail();
	});
	
	// 新增按鈕
	$("#new_btn").on("click", function(){
		var notSetCode =  $("#supplierCodeNotSet").val();
		if(notSetCode != null){
			for(var i = 0 ; i < notSetCode.length ; i++){
				$("#supplierCodeIsSet").append('<option value='+ notSetCode[i] +'>'+notSetCode[i]+'</option>');
				$("#supplierCodeNotSet option[value='"+notSetCode[i]+"']").remove();
			}
		}
	});
	
	// 刪除按鈕
	$("#del_btn").on("click", function(){
		var isSetCode =  $("#supplierCodeIsSet").val();
		if(isSetCode != null){
			for(var i = 0 ; i < isSetCode.length ; i++){
				$("#supplierCodeNotSet").append('<option value='+ isSetCode[i] +'>'+isSetCode[i]+'</option>');
				$("#supplierCodeIsSet option[value='"+isSetCode[i]+"']").remove();
			}
		}
	});
	
	// 產生密碼按鈕
	$("#createPwd_btn").on("click", function(){
		getNewPwd();
	});
	
	// 清除按鈕
	$("#clean_btn").on("click", function(){
		$("#newPwd").val("");
		$("#newPwd2").val("");
	});
	
	// 存檔按鈕
	$("#btn_data_save").on("click", function(){
		var checkFormRes = checkFormData("thisForm",false);
		var supplierId = $("#supplierIdList").val();
		var newPwd = $("#newPwd").val();
		var dialog_buttons = {}; 
		
		if(supplierId == ""){
			dialogShortMesg("請選擇帳號");
		}
		else if(checkFormRes){
			var count = 0;
			$("#supplierCodeIsSet option").each(function () {
//				console.log("each:   "+$(this).val());
				count ++;
			});
			if(count == 0){
				$("#supplierCodeIsSet").append('<option value="noCodeIsSelected"></option>');
			}			
			
			if(newPwd != ""){
				dialog_buttons["確定"] = function(){
					$(this).dialog( "close" );
					saveData();
				};
				dialog_buttons["取消"] = function(){
					$(this).dialog( "close" );
				};
				dialogConfirm("密碼欄位已填寫，是否確認更改密碼？",dialog_buttons);
			}
			else{
				saveData();
			}
		}		
	});
	
	//帳號 輸入框 清空
	$("#allSupplierIdTags").change(function () {
		if($(this).val().trim() == ""){
			$("#supplierIdList").val("");
		}
	});
	
	// 取得所有帳號(客服角色登入) autocomplete
	$("#allSupplierIdTags").autocomplete({
	    source: function(request, response) {
	    	var thisForm = document.getElementById("thisForm");
	    	var formData = new FormData(thisForm);
	    	formData.append("supplierCode", request.term.toLowerCase());
	    	$.ajax({
	    	    url: "/APCSM/system/supplierCode_edit/getAllSupplierId",
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
	    		    			label : item.USER_ID + "-" + item.USER_NAME,
	    		    	        value: item.USER_ID
	    		    	    });
	    		    	});
	    		        response(choices);
	    	    	} else {
	    	    		dialogShortMesg(data.msg);
	    	    	}
	    	    }
	    	});
	    },
	    select: function (event, ui) {
	    	$("#allSupplierIdTags").val(ui.item.label);	    	
	    	$("#supplierIdList").val(ui.item.value);
	    	$("#supplierCodeNotSet option").remove();
			$("#supplierCodeIsSet option").remove();
			$("#email").val("");
			$("#newPwd").val("");
			$("#newPwd2").val("");
			getSupplierCodeIsSet();
			getSupplierCodeNotSet();
			getUserEmail();
	    	return false;
	    }
	});	
});

// 該帳號已設定廠編
function getSupplierCodeIsSet(){
	var thisForm = document.getElementById("thisForm");
	var formData = new FormData(thisForm);		
	$.ajax({
		url: "/APCSM/system/supplierCode_edit/getSupplierCodeIsSet",
		data: formData,
	    cache: false,
	    contentType: false,
	    processData: false,
	    type: "POST",
		success: function(data) {
			if(data.length != 0){				
				for(var i = 0 ; i < data.length ; i++){
	        		$("#supplierCodeIsSet").append('<option value='+ data[i].SUPPLIER_ID +'>'+data[i].SUPPLIER_ID+'</option>');
	        	}
			}				
		}
	});
}

// 該帳號未設定廠編
function getSupplierCodeNotSet(){
	var thisForm = document.getElementById("thisForm");
	var formData = new FormData(thisForm);		
	$.ajax({
		url: "/APCSM/system/supplierCode_edit/getSupplierCodeNotSet",
		data: formData,
	    cache: false,
	    contentType: false,
	    processData: false,
	    type: "POST",
		success: function(data) {
			console.log("data"+data);
			if(data.length != 0){
				for(var i = 0 ; i < data.length ; i++){
	        		$("#supplierCodeNotSet").append('<option value='+ data[i].SUPPLIER_ID +'>'+data[i].SUPPLIER_ID+'</option>');
	        	}
			}				
		}
	});
}

// 該帳號email
function getUserEmail(){
	var thisForm = document.getElementById("thisForm");
	var formData = new FormData(thisForm);
	$.ajax({
		url: "/APCSM/system/supplierCode_edit/getUserEmail",
		data: formData,
	    cache: false,
	    contentType: false,
	    processData: false,
	    type: "POST",
		success: function(data) {
			$("#email").val(data.email);
		}
	});
}

// 產生密碼
function getNewPwd(){
	var thisForm = document.getElementById("thisForm");
	var formData = new FormData(thisForm);
	$.ajax({
		url: "/APCSM/system/supplierCode_edit/getNewPwd",
		data: formData,
	    cache: false,
	    contentType: false,
	    processData: false,
	    type: "POST",
		success: function(data) {
			$("#newPwd").val(data.newPwd);
			$("#newPwd2").val(data.newPwd);
		}
	});
}

// 存檔
function saveData(){
	$("#supplierCodeIsSet option").prop('selected',true);
	$("#thisForm").attr("action","/APCSM/system/supplierCode_edit/goSetCode");
	$("#thisForm").submit();
	viewLoading();
}