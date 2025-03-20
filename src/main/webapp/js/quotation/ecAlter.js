$(document).ready(function() {
	//查詢
	$("#btn_search").on("click", function() {
		$("#thisForm").attr("action", "/APCSM/quotation/ecAlter/query");
		$("#thisForm").submit();
		viewLoading();
	});
	
	//新增EC修改頁
	$("#btn_pick").on("click", function() {
		$("#thisForm").attr("action", "/APCSM/quotation/ecAlter/goPick");
		$("#thisForm").submit();
		viewLoading();
	});
	
	//新增EC修改頁 查詢
	$("#btn_pick_search").on("click", function() {
		$("#thisForm").attr("action", "/APCSM/quotation/ecAlter/queryPick");
		$("#thisForm").submit();
		viewLoading();
	});
	
	//新增EC修改頁 回上頁
	$("#btn_backQuery").on("click", function() {
		$("#thisForm").attr("action", "/APCSM/quotation/ecAlter");
		$("#thisForm").submit();
		viewLoading();
	});
	
	//上一頁
	$("Button[name='btn_back']").on("click", function(event) {
		btnBackClick();
	});
	
	//下一頁
	$("Button[name='btn_forward']").on("click", function(event) {
		btnForwardClick();
	});
	
	if ($("#longDescription").length > 0) {
		CKEDITOR.replace("longDescription",{
			
		});
	}
	
	//送出
	$("#btn_save").on("click", function(e) {
		e.preventDefault();
		var flag = validateAll();		
		if (!flag) return false;
		$("#mode").val("save");
		merge();
	});
	
	//網購資訊	Yahoo商城等...下拉
	$("#addYahoo").click(function() {
		var html = selectEcHtml("yahoo");
		$("#yahooBlock").append(html);
	});
	
	$(document).on("change", "select[name='yahooSelect']", function() {
		var id = $(this).attr("id").substring(0, 6);
		var num = $(this).attr("id").substring(6, 7);
		getProfEcCategoryList("yahoo", $(this).val(), id + (parseInt(num) + 1));
		setEcSelect(id, num);
	});
	
	$("#addEshop").click(function() {
		var html = selectEcHtml("shopee");
		$("#eshopBlock").append(html);
	});	
	
	$(document).on("change", "select[name='eshopSelect']", function() {
		var id = $(this).attr("id").substring(0, 6);
		var num = $(this).attr("id").substring(6, 7);
		getProfEcCategoryList("eshop", $(this).val(), id + (parseInt(num) + 1));
		setEcSelect(id, num);
	});
	
	$("#addShopee").click(function() {
		var html = selectEcHtml("shopee");
		$("#shopeeBlock").append(html);
	});	
	
	$(document).on("change", "select[name='shopeeSelect']", function() {
		var id = $(this).attr("id").substring(0, 7);
		var num = $(this).attr("id").substring(7, 8);
		getProfEcCategoryList("shopee", $(this).val(), id + (parseInt(num) + 1));
		setEcSelect(id, num);
	});
	
	$("#addComsed").click(function() {
		var html = selectEcHtml("comsed");
		$("#comsedBlock").append(html);
	});	
	
	$(document).on("change", "select[name='comsedSelect']", function() {
		var id = $(this).attr("id").substring(0, 7);
		var num = $(this).attr("id").substring(7, 8);
		getProfEcCategoryList("comsed", $(this).val(), id + (parseInt(num) + 1));
		setEcSelect(id, num);
	});
	
	//disabled 審核頁籤
	if ($("#mode").val() == "false") {
		$("#menu5 .checkData").each(function() {
			var id = $(this).attr("id");
			$("[id='" + id+ "']").prop("disabled", true);
			//$("#" + id).prop("disabled", true);
		});
	}

});

//條碼
function goModify(id, roleNo, sortIndex){
	$("#menuId").val("");
	$("#thisForm").attr("action","/APCSM/quotation/ecAlter/goModify?id=" + id + "&roleNo=" + roleNo + "&sortIndex=" + sortIndex);
	$("#thisForm").submit();
	viewLoading();
}

//新增EC修改頁 修改
function copyProductInfoToQuotation(quotationId, itemCode) {
	$("#menuId").val("");
	$("#thisForm").attr("action","/APCSM/quotation/ecAlter/copyProductInfoToQuotation?quotationId=" + quotationId + "&itemCode=" + itemCode);
	$("#thisForm").submit();
	viewLoading();
}

//點擊menuXX
function changeMenu(n) {
	var menuId = $("#menuId").val();
	/*
	if (menuId == "menu5") {
		if (!validate()) {
			//event.preventDefault();
			//event.stopPropagation();
			return false;
		}
	}
	 */
	$("#menuId").val("menu" + n);
}

//頁面檢核
function validateAll() {	
	var flag = true;
	$("#menuId").val("menu5");
	flag = validate();
	if (!flag) return false;
	return flag;
}

function validate() {
	var menuId = $("#menuId").val();
	var flag = checkFormData(menuId);
	if (menuId == "menu5" && flag) {
		flag = validateMenu5();
	}
	return flag;
}


function validateMenu5() {	
	return true;
}

//送出EC修改
function merge() {
	$("#htmlDescription").val(CKEDITOR.instances.longDescription.getData());
	$("#thisForm").attr("action", "/APCSM/quotation/ecAlter/merge");
	$("#thisForm").submit();
	viewLoading();
}

//上一頁
function btnBackClick() {
	var menuId = $("#menuId").val();
	/*
	if (menuId == "menu5") {
		var flag = validate();
		if (!flag) return false;
	}	
	 */
	menuId = "menu" + (parseInt(menuId.replace("menu", "")) - 1);
	$("#menuId").val(menuId);
	setMenuClass();
}

//下一頁
function btnForwardClick() {
	var menuId = $("#menuId").val();
	menuId = "menu" + (parseInt(menuId.replace("menu", "")) + 1);
	$("#menuId").val(menuId);
	setMenuClass();
}

function setMenuClass() {
	var menuId = $("#menuId").val();
	for (i = 1; i <= 7; i++) {
		if (i == parseInt(menuId.replace("menu", ""))) {
			$("#menu" + i).removeAttr("class").addClass("tab-pane fade in active");
			$("#li" + i).removeAttr("class").addClass("active");			
		} else {
			$("#menu" + i).removeAttr("class").addClass("tab-pane fade");
			$("#li" + i).removeAttr("class").addClass("");
		}		
	}
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
