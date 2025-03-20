$(function() {
	$("#btn_save").click(function(){
		if(checkLogic()){//因有動態必填，則此邏輯要先判斷，不可與checkFormData一同判斷，否則無效
			if(checkFormData("thisForm")){
				$("#thisForm").submit();
				viewLoading();
			}
		}
	});
	//檢查其它邏輯
	function checkLogic(){
		var checkResult=true;
		if($("#agentBeginDate").val() > $("#agentEndDate").val()){
			dialogShortMesg("代理人日期(迄)需大於等於代理人日期(起)");
			checkResult = false;
		}
		// 若代理人有填，則代理人日期起迄則必填
		if($("#agentId").val() != ''){
			$("#agentBeginDate").attr("notNull","Y");
			$("#agentEndDate").attr("notNull","Y");
		}
		// 若代理人日期起迄其中一欄有填，則代理人日期起迄與代理人皆為必填
		if($("#agentBeginDate").val() != '' || $("#agentEndDate").val() != ''){
			$("#agentBeginDate").attr("notNull","Y");
			$("#agentEndDate").attr("notNull","Y");
			$("#agentId").attr("notNull","Y");
		}
		// 若代理人日期起迄與代理人皆沒填，則可儲存
		if($("#agentId").val() == '' && $("#agentBeginDate").val() == '' && $("#agentEndDate").val() == ''){
			$("#agentBeginDate").attr("notNull","N");
			$("#agentEndDate").attr("notNull","N");
			$("#agentId").attr("notNull","N");
		}
		return checkResult;
	}
});
