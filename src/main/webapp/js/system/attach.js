$(function() {
	
	//增加下一個附件
	$("#addAttach").click(function(){
		$("#attachBlock").append('<br/><input type="file" name="files" class="bulletinFile" >');
	});
	
	//刪除公告附件
	$(".ajaxDelBulFile").click(function(){
		var attachId=$(this).attr("attachId");
		var dialog_buttons = {}; 
		dialog_buttons["確定"] = function(){
			$(this).dialog( "close" );
			doDelBulletinFile(attachId);
		};
		dialog_buttons["取消"] = function(){
			$(this).dialog( "close" );
		};   
		dialogConfirm("確定要刪除嗎？",dialog_buttons);
	});

	//刪除公告附件
	function doDelBulletinFile(attachId){
		var formData = new FormData();
		formData.append("attachId", attachId);
		$.ajax({
		    url: '/APCSM/system/bulletin/attachDelete',
		    data: formData,
		    cache: false,
		    contentType: false,
		    processData: false,
		    type: 'POST',
		    success: function(data){
		    	$(".attachId_"+attachId).parent().remove();
		    	if(data.msg != "")
		    		toastSuccess(data.msg);
		    }
		});
	}
	
	//檢查上傳附件大小
	// $(document).on('change', element ,function(){} 此寫法適用於動態增加的物件
	$(document).on('change', '.bulletinFile',function(){  //選取類型為file且值發生改變的
		$("#msg_bl").empty();
		var file = this.files[0]; //定義file=發生改的file
		name = file.name; //name=檔案名稱
		size = file.size; //size=檔案大小
		type = file.type; //type=檔案型態

		//console.log(name, type);
		if(file.size > 5000000) { //畫面擋3MB實際上可以吃5M
			$("#msg_bl").append("檔案上限3MB</br>");
		}//else if(file.type != "application/pdf" ){ 
		//	$("#msg_bl").append("檔案格式需為PDF</br>");
		//}
		var strArray = new Array("application/pdf", "application/msword", "application/vnd.ms-excel", "application/vnd.ms-powerpoint",
				"application/vnd.openxmlformats-officedocument.wordprocessingml.document", 
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", 
				"application/vnd.openxmlformats-officedocument.presentationml.presentation");
		var flag = false;
		$.each(strArray, function(index, value) {
			if (type == value) {
				flag = true;
				return false;
			}
	    });
		if (!flag) $("#msg_bl").append("檔案格式需為Office檔案或者PDF</br>");
		
		if($("#msg_bl").text() != ""){
			$("#dialogMsg").modal('show');
			$(this).replaceWith($("#bulletinFileOrig").clone(true)
					.attr("class","bulletinFile") // bulletinFileOrig原物件為隱藏且沒class，故clone了之後要再把相關設定加回來
					.css("display","block"));
		}
	});
});