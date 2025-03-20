$(function() {
	// 重填
	$("#resetBtn").on("click", function(){
		$("input[type='text']").val("");
		$("select").val("");
	});
	//查詢按鈕
	$("#btn_search").click(function(){
		$("#thisForm").attr("action","/APCSM/system/bulletin/query");
		$("#thisForm").submit();
		viewLoading();
	}); 
	//查詢按鈕_供應商
	$("#btn_search_sup").click(function(){
		$("#thisForm").attr("action","/APCSM/system/bulletin/query_sup");
		$("#thisForm").submit();
		viewLoading();
	}); 
	
	//新增部門公告-填寫頁面
	$("#btn_add").click(function(){
		$("#docId").val("");
		$("#thisForm").attr("action","/APCSM/system/bulletin/goCreate");
		$("#thisForm").submit();
		viewLoading();
	});
	//新增部門公告-儲存
	$("#btn-submit").click(function(){
		if(checkFormData("thisForm") && checkLogic()){
			//$("#checkStoreList").val($('#storeTree1').jstree('get_selected'));
			$("#thisForm").attr("action","/APCSM/system/bulletin/merge");
			if ($("#showTop").is(":checked"))
				$("#showTop").val("Y");			
			if ($("#showLogin").is(":checked"))
				$("#showLogin").val("Y");	
			if ($("#checkAll").is(":checked"))
				$("#checkAll").val("Y");
			$("#thisForm").submit();
			viewLoading();
		}
	});
	
	//修改
	$(".deptUpd").click(function(){
		$("#docId").val($(this).attr("docId"));
		$("#thisForm").attr("action","/APCSM/system/bulletin/goModify");
		$("#thisForm").submit();
		viewLoading();
	});
	
	//刪除公告及附件
	$(".ajaxDelBulletin").click(function(){
		var docId=$(this).attr("docId");
		var dialog_buttons = {}; 
		dialog_buttons["確定"] = function(){
			$(this).dialog( "close" );
			doDelBulletin(docId);
		};
		dialog_buttons["取消"] = function(){
			$(this).dialog( "close" );
		};
		dialogConfirm("確定要刪除嗎？",dialog_buttons);
	});
	
	//刪除公告及附件
	function doDelBulletin(docId){
		var formData = new FormData();
		formData.append("docId", docId);
		$.ajax({
		    url: '/APCSM/system/bulletin/remove',
		    data: formData,
		    cache: false,
		    contentType: false,
		    processData: false,
		    type: 'POST',
		    success: function(data){
		    	$(".ajaxDelBulletin_"+docId).parents("tr").remove();
		    	if(data.msg != "")
		    		dialogShortMesg(data.msg);
		    }
		});
	}
	
	//查看公告內容
	$(".bulletinShow").click(function(){
		docId=$(this).attr("docId");
		docType = $(this).attr("docType");
		$(this).css("font-weight","normal");//變回細體
		$("#dialog").html("Loading...").dialog({
			modal:true,
			width:'70%',
			position:[200,0],
			title:"公告內容",
			open:function(){
				$(this).load('/APCSM/system/bulletin/show?id='+docId+'&'+getSystemTimeId());
			}	
		}); 
		
		if(docType != null && docType != ""){
			recordClick(docType, docId);
		}		
	});
	
	//檢查其它邏輯
	function checkLogic(){
		var checkResult=true;
		//檢查公告日期
		var today = date2Str(new Date());
		if($("#beginDate").val() < today){
			dialogShortMesg("公告日期(起)需大於等於系統日");
			checkResult = false;
		}else if($("#beginDate").val() > $("#endDate").val()){
			dialogShortMesg("公告日期(迄)需大於等於公告日期(起)");
			checkResult = false;
		}else if($("#checkAll").is(":not(:checked)") && $("#supplierCode").val() == "" ){
			dialogShortMesg("發佈對像至少有一個");
			checkResult = false;
		}
		
		return checkResult;
	}
	
	// 上傳CSV按鈕
	$("#csvBt").on("click", function(){
		$("#csvFile").click();
	});
	
	// 新增文件檔案選擇
	$(document).on("change","#csvFile",function(){  //選取類型為file且值發生改變的
		var _this = $(this);
		var file = this.files[0], //定義file=發生改的file
		name = file.name, //name=檔案名稱
		size = file.size, //size=檔案大小
		type = file.type; //type=檔案型態
		// 3MB = 3145728 byte
		// 20MB = 20971520 byte
		if(size > 3145728) { //假如檔案大小超過3MB
			dialogShortMesg("檔案內容需小於3MB!!");
			_this.val("");  //將檔案欄設為空白
			$("#fileName").val("");
		} else if(file.type != ("application/vnd.ms-excel" || "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) { // 假如檔案格式不等於 csv 或 excel
			var ext = _this.val().split('.').pop().toLowerCase();
			if($.inArray(ext, ['csv','xls','xlsx']) == -1) {
				dialogShortMesg("檔案格式不符合: CSV 或 Excel 格式");
				_this.val(""); //將檔案欄設為空
				$("#fileName").val("");
			}
		}else{
			//開始上傳檔案
			var formData = new FormData();
			var _file = $('#csvFile');
			formData.append("csvFile", _file[0].files[0]); // 上傳檔案
			if(_file.val() != "") {
				$.ajax({
				    //url: '/APCSM/uploadCsvExcelFile',
					url: '/APCSM/system/bulletin/uploadFile',
				    data: formData,
				    cache: false,
				    contentType: false,
				    processData: false,
				    type: 'POST',
				    success: function(data) {
				    	if (data.msg == "") {
				    		if (data.text != "") {
					    		$("#supplierCode").val(data.supplierText);
					    		$("#selsupplier").html(data.text);
					    		dialogShortMesg("匯入廠編資料成功");
				    		}
				    	} else {
				    		dialogShortMesg(data.msg,false);
				    	}
				    }
				});
			} else {
				dialogShortMesg("未選擇CSV檔案");
			}
		}
	});
	// 輸入廠編
	$("#supplierText").keypress(function(event) {
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if (keycode == '13') {
			var supplierText = $("#supplierText").val();
			var formData = new FormData();
			formData.append("supplierCode", $("#supplierCode").val());
			formData.append("supplierText", supplierText);
			$.ajax({
			    url: '/APCSM/system/bulletin/getSupplier',
			    data: formData,
			    cache: false,
			    contentType: false,
			    processData: false,
			    type: 'POST',
			    success: function(data) {
			    	if (data.msg == "") {
			    		if (data.text != "") {
				    		$("#selsupplier").append(data.text);
				    		var code = $("#supplierCode").val();
				    		if (code == "")
				    			$("#supplierCode").val(supplierText);
				    		else
				    			$("#supplierCode").val(code + "," + supplierText);				    		
			    		}
			    		$("#supplierText").val("");
			    	} else {
			    		dialogShortMesg(data.msg,false);
			    	}
			    }
			});
		}
	});
	// 刪除發佈對像
	$("#deleteBt").click(function(){
		var delop = $("#selsupplier").val();
		var supplierCode = $("#supplierCode").val();
		for(var i=0;i<delop.length;i++){
			if(delop[i].trim() != ""){
				$("#selsupplier option[value='"+delop[i]+"']").remove();
				supplierCode = supplierCode.replace(delop[i],"").replace(",,",",");
				if(supplierCode.indexOf(",") == 0)
					supplierCode = supplierCode.substring(1);
				if(supplierCode.lastIndexOf(",") == (supplierCode.length-1))
					supplierCode = supplierCode.substring(0,supplierCode.length-1);
			}
		}
		$("#supplierCode").val(supplierCode);
	});
	// 刪除發佈對像(全部)
	$("#delAll").click(function(){
		$("#supplierCode").val("");
		$("#selsupplier").empty();
	});
	// 發佈對像勾選全部
	$("#checkAll").change(function(){
		if($("#checkAll").prop('checked')){
			$("#csvBt").attr("disabled","");
			$("#supplierText").attr("disabled","").val("");
			$("#supplierCode").val("");
			$("#selsupplier").empty();
			$("#csvFile").replaceWith($("#bulletinFileOrig").clone(true)
					.attr({"class":"bulletinFile","id":"csvFile"}));
		}else{
			$("#csvBt").removeAttr("disabled");
			$("#supplierText").removeAttr("disabled");
		}
	});
	// 下載附件
	$(".dwnlattach").click(function (){
		recordClick("bulletinFile", $(this).attr("dwnlId"));
		$("#download").attr("action","/APCSM/system/bulletin/doDownload");
		$("#dwnlPath").val($(this).attr("dwnlPath"));
		$("#dwnlorigFileName").val($(this).attr("dwnlorigFileName"));
		$("#download").submit();
	});
});
