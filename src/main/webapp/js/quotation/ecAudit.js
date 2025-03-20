$(document).ready(function() {
	//查詢
	$("#btn_search").on("click", function() {
		$("#thisForm").attr("action", "/APCSM/quotation/ecAudit/query");
		$("#thisForm").submit();
		viewLoading();
	});
	
	
	
	//送出
	$("#btn_save_ec").on("click", function(e) {
		e.preventDefault();
		var flag = validateEc();		
		if (!flag) return false;
		$("#mode").val("save");
		mergeTemp("save");
	});
	
	
	
	//disabled 審核頁籤
	if ($("#mode").val() == "false") {
		$("#menu7 .checkData").each(function() {
			var id = $(this).attr("id");
			$("[id='" + id+ "']").prop("disabled", true);
		});
		$(".editForm .btn").hide();
	}

});

//條碼
function goModify(id, roleNo, sortIndex){
	$("#menuId").val("");
	$("#thisForm").attr("action","/APCSM/quotation/ecAudit/goModify?id=" + id + "&roleNo=" + roleNo + "&sortIndex=" + sortIndex);
	$("#thisForm").submit();
	viewLoading();
}

//點擊menuXX
function changeMenu(n) {
	var menuId = $("#menuId").val();
	if (menuId == "menu7") {
		if (!validateEc()) {
			return false;
		}
	}	
	$("#menuId").val("menu" + n);
}

//頁面檢查
function validateEc() {
	var menuId = $("#menuId").val();
	if($("#approveStatus").val() == 'N' || $("#approveStatus").val() == 'R'){
		$(".checkbox1").attr("notNull","N");
	}
	else{
		$(".checkbox1").attr("notNull","Y");
	}
	var flag = checkFormData(menuId);
	if (menuId == "menu7" && flag) {
		flag = validateMenu7();
	}
	return flag;
}

//審核頁檢核
function validateMenu7() {
	if($("#approveStatus").val() == 'N' || $("#approveStatus").val() == 'R'){
		dialogShortMesg("審核結果為不通過或不上EC時，審核意見必填。",false);
		return false;
	}
	return true;
}

//送出審核
function mergeTemp(method) {
	viewLoading();
	$("#htmlDescription").val(CKEDITOR.instances.longDescription.getData());
	var url = "/APCSM/quotation/ecAudit/mergeTemp";
	$("#thisForm").ajaxForm({url: url, type: "post"});
    $.post(url, $("#thisForm").serialize(), function(data) {    	
    	if (data.msg == "") {
    		if (method == "save")
    			location.href = "/APCSM/quotation/ecAudit/merge?barcode1=" + $("#barcode1").val();
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


