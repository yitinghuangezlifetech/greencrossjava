$(function(){
	// 重填
	$("#resetBtn").on("click", function(){
		$("input[type='text']").val("");
		$("select").val("");
	});
	//查詢按鈕
	$("#btn_search").click(function(){
		$("#thisForm").attr("action","/APCSM/system/suitDateExclude/query");
		$("#thisForm").submit();
		viewLoading();
	}); 
	
	//新增生效日排外-填寫頁面
	$("#btn_add").click(function(){
		$("#thisForm").attr("action","/APCSM/system/suitDateExclude/goCreate");
		$("#thisForm").submit();
		viewLoading();
	});
	// 設定按鈕
	$("#btn-submit").on("click", function(){
		if(checkForm()){
			var thisForm = document.getElementById("thisForm");
			var formData = new FormData(thisForm);
			$.ajax({
				url: "/APCSM/system/suitDateExclude/checkSuitDate",
				data: formData,
			    cache: false,
			    contentType: false,
			    processData: false,
			    type: "POST",
				success: function(data) {				
					if(data.msg == "duplicate"){
						dialogShortMesg("生效日已存在，請重新輸入");
					}
					else{
						$("#thisForm").attr("action","/APCSM/system/suitDateExclude/setExclude");
						$("#thisForm").submit();
						viewLoading();
					}
				}
			});
		}
	});
});

// 檢查日期
function checkForm(){
	var type = $("#type").val();
	var dayOfWeek = getDayOfWeek(str2Date($("#code").val()));

	if(type == ""){
		dialogShortMesg("請選擇生效日排外類別");
		return false;
	}
	else if($("#code").val() <= addDay(getSystemDateToEight(),14)){
		dialogShortMesg("日期不可小於未來14天內");
		return false;
	}
	else if(type == 'newPrd' && dayOfWeek != 1 && dayOfWeek != 3 ){
		dialogShortMesg("新品生效日的日期需為星期一或星期三");
		return false;
	}
	else if(type == 'price' && dayOfWeek != 3 && dayOfWeek != 6 ){
		dialogShortMesg("零售價生效日的日期需為星期三或星期六");
		return false;
	}
	else{
		return true;
	}
}

// 修改按鈕
function editExclude(id){
	$("#profExcludeId").val(id);
	$("#thisForm").attr("action","/APCSM/system/suitDateExclude/goCreate");
	$("#thisForm").submit();
	viewLoading();
}

// 刪除按鈕
function deleteExclude(type, code, id){
	var dialog_buttons = {}; 
	
	dialog_buttons["確定"] = function(){
		$(this).dialog( "close" );
		goDelete(type, code, id);
	};
	dialog_buttons["取消"] = function(){
		$(this).dialog( "close" );
	};
	dialogConfirm("是否確認刪除",dialog_buttons);
}

// 刪除
function goDelete(id){
	$("#profExcludeId").val(id);
	$("#thisForm").attr("action","/APCSM/system/suitDateExclude/deleteExclude");
	$("#thisForm").submit();
	viewLoading();	
}
