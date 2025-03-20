$(function() {
	//公告日期=系統日期變粗體紅色，當有點擊過變細體色字
	$(".sysdateRed").each(function(){
		if($(this).html().replace(/\r\n|\n/g,"") === getSystemDate()){
			$(this).parent().parent().find(".bulletinShow").css("color","red");
		}
		if($(this).attr("clickFlag") == ""){
			$(this).parent().parent().find(".bulletinShow").css("font-weight","bold");
		}
	});
	
});