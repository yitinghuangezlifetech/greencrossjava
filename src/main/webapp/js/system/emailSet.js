$(function() {

	// 重填
	$("#resetBtn").on("click", function(){
		// 使用focus讓checkData功能被觸發，使input的剩餘字數回復
		// 使用blur讓input框失焦
		$("input[type='text']").val("").focus().blur();
		$("select").val("");
		$(":checked").prop("checked",false);
	});
	//新增email資料維護-填寫頁面
	$("#btn_add").add(".supply_add").click(function(){
		// 點選未設定廠編
		var supplierCode = $(this).attr("supply");
		if(supplierCode != '')
			$("#supplierCode").val(supplierCode);
		
		$("#docId").val("");
		$("#thisForm").attr("action","/APCSM/system/emailSet/goCreate");
		$("#thisForm").submit();
		viewLoading();
	});

	//新增email資料維護-儲存
	$("#btn-submit").click(function(){
		if(checkFormData("thisForm")){
			// 將mailType組成字串後往後傳
			$("#thisForm").attr("action","/APCSM/system/emailSet/merge");
			$("#thisForm").submit();
			viewLoading();
		}
	});
	
	//修改
	$(".deptUpd").click(function(){
		$("#docId").val($(this).attr("docId"));
		$("#thisForm").attr("action","/APCSM/system/emailSet/goModify");
		$("#thisForm").submit();
		viewLoading();
	});
	
	//刪除email資料維護
	$(".ajaxDelEmailSet").click(function(){
		var docId=$(this).attr("docId");
		var dialog_buttons = {}; 
		dialog_buttons["確定"] = function(){
			$(this).dialog( "close" );
			doDelEmailSet(docId);
		};
		dialog_buttons["取消"] = function(){
			$(this).dialog( "close" );
		};
		dialogConfirm("確定要刪除嗎？",dialog_buttons);
	});
	
	//刪除email資料維護
	function doDelEmailSet(docId){
		var formData = new FormData();
		formData.append("docId", docId);
		$.ajax({
		    url: '/APCSM/system/emailSet/remove',
		    data: formData,
		    cache: false,
		    contentType: false,
		    processData: false,
		    type: 'POST',
		    success: function(data){
		    	if(data.msg != ""){
		    		dialogShortMesg(data.msg);
		    		location.href="/APCSM/system/emailSet/query_sup"; // 重導此頁
		    	}
		    }
		});
	}
	
	//查看email資料維護
	$(".emailSetShow").click(function(){
		docId=$(this).attr("docId");
		docType=$(this).attr("docType");
		$(this).css("font-weight","normal");//變回細體
		$("#dialog").html("Loading...").dialog({
			modal:true,
			width:'70%',
			position:[200,0],
			title:"email資料維護內容",
			open:function(){
				$(this).load('/APCSM/system/emailSet/show?id='+docId+'&docType='+docType);
			}	
		}); 
	});
	
	// 批次修改email資料-儲存
	$("#btn-submit-batch").click(function(){
		if(checkFormData("batchForm")){
			$("#batchForm").attr("action","/APCSM/system/emailSet/mergeBatch");
			$("#batchForm").submit();
			viewLoading();
		}
	});
	// 取消(批次修改)
	$("#cancelBtn").click(function (){
		$("#dialog").dialog('close');
	});
	
	// 查詢(非供應商)
	$("#btn_data_search").on("click",function(){
		if($("#userId").val() == ""){
			dialogShortMesg("請輸入帳號");
		}
		else{
			$("#thisForm").attr("action","/APCSM/system/emailSetInfo/getEmailInfo");
			$("#thisForm").submit();
			viewLoading();
		}		
	});
});
