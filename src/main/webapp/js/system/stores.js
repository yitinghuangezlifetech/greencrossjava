$(document).ready(function(){
		
	//查詢按鈕
	$("#btn_data_search").on("click", function(){
	  	$("#thisForm").submit();
	  	viewLoading();
	});
	
	// 重填按鈕
	$("#resetBtn").on("click", function(){
		$("select").prop('selectedIndex', 0);
		$("#searchStoreNm").val("");
	});
		
	// 選縣市帶出區別
	$("#city").change(function(){
		 $("option:selected",this).each(function(){
		    $("#area option").remove();
		    $.ajax({
		        url: "/APCSM/system/storeInfo/getAreaByCity",
		        data: {"city" :this.value},
		        type: "post",
		        async: false, //啟用同步請求
		        success: function (data) {
		        	$("#area").append('<option value="">請選擇</option>');
			        
		        	for(var i = 0 ; i < data.length ; i++){
		        		$("#area").append('<option value='+ data[i] +'>'+data[i]+'</option>');
		        	}
		        }
		    });
		 });
	});
});
//地圖
function showMap(storeId, storeNm){
	$("#dialog").html("Loading...").dialog({
		modal:true,
		width:'70%',
		position:[200,0],
		title: storeNm,
		open:function(){
			$(this).load('/APCSM/system/storeInfo/showMap?storeInfo.id='+storeId+'&'+getSystemTimeId());
		}
	});
};