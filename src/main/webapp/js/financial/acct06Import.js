$(document).ready(function(){
	
	// 收費匯入
	$("#btn_importPallet").click(function(){
		$("#thisForm").attr("action","/APCSM/financial/acct06Set/goImport");
		$("#thisForm").submit();
		viewLoading();
	});
	
	// 收費匯入上傳
	$("#btn_import_pallet_data").click(function(){
		if(checkFormData("thisForm")){
			var fullPath = $("#uploadFile").val();
			var startIndex = (fullPath.indexOf("\\") >= 0 ? fullPath.lastIndexOf("\\") : fullPath.lastIndexOf("/"));
			var fileName = fullPath.substring(startIndex);
			fileName = fileName.substring(1);
			$("#importFileName").val(fileName);
			
			var fileLowerName = fileName.toLowerCase();
			console.log("fileLowerName:   "+fileLowerName);
			if(!fileLowerName.endsWith(".xls") && !fileLowerName.endsWith(".xlsx")){
				dialogShortMesg("只接受格式為.xls或.xlsx的檔案");
				$("#uploadFile").val("");
			}
			else{
				console.log("import");
				$("#thisForm").attr("action","/APCSM/financial/acct06Set/importPallet");
				$("#thisForm").submit();
				viewLoading();
			}
		}
		
	});	
	
	
	
	
	
});

// 主檔明細(收費)
function showPalletDetial(id){
	$("#headId").val(id);
	$("#thisForm").attr("action","/APCSM/financial/acct06Set/detail");
	$("#thisForm").submit();
	viewLoading();
}


// 待確認廠商
function showNonComfirmSupplier(id){
	$("#dialog").html("Loading...").dialog({
		modal:true,
		width:'50%',
		position:[300,0],
		title: "待確認廠商",
		open:function(){
			$(this).load('/APCSM/financial/acct06Set/showNonConfirm?acct06ProfHead.id='+id+'&'+getSystemTimeId());
		}
	});
}

//已確認廠商
function showComfirmSupplier(id){
	$("#dialog").html("Loading...").dialog({
		modal:true,
		width:'50%',
		position:[300,0],
		title: "已確認廠商",
		open:function(){
			$(this).load('/APCSM/financial/acct06Set/showConfirm?acct06ProfHead.id='+id+'&'+getSystemTimeId());
		}
	});
}