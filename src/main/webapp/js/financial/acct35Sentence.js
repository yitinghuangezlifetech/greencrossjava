$(document).ready(function(){
	
	// 設定按鈕
	$("#btn_data_save").on("click", function(){		
		if(checkForm()){
			$("#thisForm").attr("action","/APCSM/financial/acct35Sentence/setAcct35Sentence");
			$("#thisForm").submit();
			viewLoading();
		}
	});
});

// 檢查
function checkForm(){
	var sentence = $("#sentence").val();
	var flowType = $("#flowType").val();
	var ascription = $("#ascription").val();
	
	if(sentence == ""){
		dialogShortMesg("請輸入例句");
		return false;
	}
	else if(flowType == ""){
		dialogShortMesg("請選擇類型");
		return false;
	}
	else if(ascription == ""){
		dialogShortMesg("請選擇歸屬");
		return false;
	}
	else{
		return true;
	}
}

// 修改按鈕
function editAcct35Sentence(sentence, flowType, id, ascription){
	$("#sentence").val(sentence);
	$("#flowProfSentenceId").val(id);
	console.log("ascription:   "+ascription);
		if($("#flowType").val() != flowType){
			$("#flowType option[value="+flowType+"]").removeAttr("selected").attr("selected", "selected");
		}
		if($("#ascription").val() != ascription){
			$("#ascription option[value="+ascription+"]").removeAttr("selected").attr("selected", "selected");
		}
}

// 刪除按鈕
function deleteAcct35Sentence(sentence, flowType, id){
	var dialog_buttons = {}; 
	
	dialog_buttons["確定"] = function(){
		$(this).dialog( "close" );
		goDelete(sentence, flowType, id);
	};
	dialog_buttons["取消"] = function(){
		$(this).dialog( "close" );
	};
	dialogConfirm("是否確認刪除",dialog_buttons);
}

// 刪除
function goDelete(sentence, flowType, id){
	$("#flowProfSentenceId").val(id);
	$("#thisForm").attr("action","/APCSM/financial/acct35Sentence/deleteAcct35Sentence");
	$("#thisForm").submit();
	viewLoading();	
}
