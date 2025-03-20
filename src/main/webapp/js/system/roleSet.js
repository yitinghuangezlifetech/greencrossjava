$(document).ready(function(){
	// 人員權限查詢
	$("#btn_userSearch").on("click", function(){
		$("#thisForm").attr("action","/APCSM/system/roleSet/query");
		$("#thisForm").submit();
		viewLoading();
	});
	
	// 使用者權限儲存
	$("#btn_userSave").on("click", function(){
		var errorMsg="";
		$(".funcDetail").each(function(){
			if($(this).is(":checked")){
				if($(this).parent().next().find("select").val() == ""){
					errorMsg+="["+$(this).next().html() + "]需選擇權限<br>";
					$(this).focus();
				}
			}
		});
		if(errorMsg == ""){
			$("#thisForm").attr("action","/APCSM/system/roleSet/update");
			$("#thisForm").submit();
			viewLoading();
		}else{
			toastError(errorMsg);
		}
	});
	//角色權限查詢
	$("#btn_roleFunSet").click(function(){
		$("#thisForm").attr("action","/APCSM/system/roleFunSet/query");
		$("#thisForm").submit();
		viewLoading();
	});
	//角色權限儲存
	$("#btn_roleFunSave").click(function(){
		var errorMsg="";
		$(".funcDetail").each(function(){
			if($(this).is(":checked")){
				if($(this).parent().next().find("select").val() == ""){
					errorMsg+="["+$(this).next().html() + "]需選擇權限<br>";
					$(this).focus();
				}
			}
		});
		if(errorMsg == ""){
			$("#thisForm").attr("action","/APCSM/system/roleFunSet/update");
			$("#thisForm").submit();
			viewLoading();
		}else{
			toastError(errorMsg);
		}
	});
	//角色權限選單變動時重新查詢
	$("#roleNo").change(function(){
		$("#thisForm").submit();
		viewLoading();
	});
	
//	//選單初始化，沒有功能權限時，下拉選單需要disabled
//	$(".funcDetail").each(function(){
//		if(!$(this).is(":checked")){
//			$(this).parent().next().find("select").prop("disabled",true);
//		}
//	});
//	//子功能被勾選時，父功能也要被勾選
//	$(".funcDetail").click(function(){
//		if($(this).is(":checked")){
//			$(this).parent().parent().parent().find(".funcHead").prop("checked",true);
//			$(this).parent().next().find("select").prop("disabled",false);
//		}else{
//			//功能被取消勾選時要disabled
//			$(this).parent().next().find("select").val("").prop("disabled",true);
//		}
//	});
	
	$(".funcHead").click(function() {
		var id = $(this).attr("id");
		$(".funcChild").each(function() {
			var cid = $(this).attr("id");
			if (cid.indexOf(id)>=0) {
				if ($("#" + id).is(":checked")) {
					$("#" + cid).prop("checked", true);
				} else {
					$("#" + cid).prop("checked", false);
				}
			}
		});
		
		$(".funcDetail").each(function() {
			var cid = $(this).attr("id");
			if (cid.indexOf(id)>=0) {
				if ($("#" + id).is(":checked")) {
					$("#" + cid).prop("checked", true);
					$("#" + cid + "_S").val(cid+"_W");
					$("#" + cid + "_S").prop("disabled", false);
				} else {
					$("#" + cid).prop("checked", false);
					$("#" + cid + "_S").val("");
					$("#" + cid + "_S").prop("disabled", true);
				}
			}
		});
	});
	
	$(".funcChild").click(function() {
		var id = $(this).attr("id");
		var array = id.split("_");
		$(".funcDetail").each(function() {
			var cid = $(this).attr("id");
			if (cid.indexOf(id)>=0) {
				if ($("#" + id).is(":checked")) {
					$("#" + cid).prop("checked", true);
					$("#" + cid + "_S").val(cid+"_W");
					$("#" + cid + "_S").prop("disabled", false);
				} else {
					$("#" + cid).prop("checked", false);
					$("#" + cid + "_S").val("");
					$("#" + cid + "_S").prop("disabled", true);					
				}
			}
		});
		
		var flag = false;			
		$(".funcDetail").each(function() {
			var cid = $(this).attr("id");
			if (cid.indexOf(array[0])>=0 && $("#" + cid).is(":checked")) {
				flag = true;
				return false;
			}
		});
		$("#" + array[0]).prop("checked", flag);
	});
	
	$(".funcDetail").click(function() {
		var id = $(this).attr("id");
		var array = id.split("_");
		if ($(this).is(":checked")) {
			$("#" + id + "_S").val(id+"_W");
			$("#" + id + "_S").prop("disabled", false);
		} else {
			$("#" + id + "_S").val("");
			$("#" + id + "_S").prop("disabled", true);
		}
		
		var flag = false;
		$(".funcDetail").each(function() {
			var cid = $(this).attr("id");
			if (cid.indexOf(array[0])>=0 && $("#" + cid).is(":checked")) {
				flag = true;
				return false;
			}
		});
		$("#" + array[0]).prop("checked", flag);
		
		flag = false;
		var ccid = id.substring(0, id.lastIndexOf("_"));
		$(".funcDetail").each(function() {
			var cid = $(this).attr("id");
			if (cid.indexOf(ccid)>=0 && $("#" + cid).is(":checked")) {
				flag = true;
				return false;
			}
		});
		$("#" + ccid).prop("checked", flag);
	});
	
});

//設定使用者權限
function roleSetEdit(userId){
	$("#userId").val(userId);
	$("#thisForm").attr("action","/APCSM/system/roleSet/edit");
	$("#thisForm").submit();
	viewLoading();
}

//匯出權限表
function exportExcel(){
	exportReport("/APCSM/system/roleFunSet/exportExcel", "xls", {
		"user.userId": $("#userId").val(),
		"user.userName": $("#userName").val(),
		"user.roleGroup": $("#role").val(),		
		"user.userStatus": $("#userStatus").val(),
		"user.haslogin": $("#haslogin").val()
	});
}