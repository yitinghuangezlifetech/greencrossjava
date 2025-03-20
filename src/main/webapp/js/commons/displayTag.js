
$(document).ready(function(){ 
	$("#disList td:empty").html("&nbsp;");//將null處填上空白
	$("#lab_totalNum").html($("#totalNum").val());//顯示總筆數
	
	selPageSizeHandler();//處理每頁顯示筆數
	displaySelPageHandler();//處理顯示第幾頁
 });

//處理每頁顯示筆數
function selPageSizeHandler(){
	var nowPageSize=$("#nowPageSize").val();//目前每頁顯示xx筆
	genSelPageSize();//產生每頁顯示xx筆下拉選單
	//綁定onchange事件
	$("#sel_page_size").change(function(){
		doDisplayTagSubmit();
	});
	$("#sel_page_size").val(nowPageSize);//讓下拉選單預設目前每頁顯示xx筆
}

//產生每頁顯示xx筆下拉選單
function genSelPageSize(){
	$("#sel_page_size").append("<option value='5'>5</option>");
	$("#sel_page_size").append("<option value='10'>10</option>");
	$("#sel_page_size").append("<option value='20'>20</option>");
	$("#sel_page_size").append("<option value='30'>30</option>");
	$("#sel_page_size").append("<option value='40'>40</option>");
	$("#sel_page_size").append("<option value='50'>50</option>");
	$("#sel_page_size").append("<option value='60'>60</option>");
	$("#sel_page_size").append("<option value='70'>70</option>");
	$("#sel_page_size").append("<option value='80'>80</option>");
	$("#sel_page_size").append("<option value='90'>90</option>");
	$("#sel_page_size").append("<option value='100'>100</option>");
	$("#sel_page_size").append("<option value='500'>500</option>");
}

//處理顯示第幾頁
function displaySelPageHandler(){
	var nowPage=$("#nowPage").val();//現在在第幾頁
	displayTag_Selpage();//產生頁碼下拉選單
	$("#displaySelPage").val(nowPage);//讓下拉選單預設在目前頁數
	$("#displaySelPage").change(changePage);//綁定onchange事件
}

//產生頁碼的下拉選單
function displayTag_Selpage(){
	var pagesize=$("#totalPage").val();
	if(pagesize != null){
		pagesize=pagesize.replace(",","");
		for(var i=1;i<=pagesize;++i){
			$("#displaySelPage").append("<option value='" + i + "'>" + i + "</option>");
		}
	}
}

//跳頁(下拉選單)
function changePage(){
	var pagenum=$("#displaySelPage").val();
	var p=getPageParameterName();
	$("#display_pagnum").html("<input type='hidden' name='" + p + "' value='" + pagenum + "'><input type='hidden' name='willPage' value='" + p + "'>");
	doDisplayTagSubmit();
}
//取得目前頁次變數名稱
function getPageParameterName(){
	var url=$("#disurl").val();
	var d=url.indexOf('d-');
	var p =url.indexOf('-p');
	var dp=url.substring(d+2,p);
	var p="d-" + dp + "-p";
	return p;
}
//第一頁
function firstPage(){
	var pagenum=1;
	var p=getPageParameterName();
	$("#display_pagnum").html("<input type='hidden' name='" + p + "' value='" + pagenum + "'><input type='hidden' name='willPage' value='" + p + "'>");
	doDisplayTagSubmit();
}
//上一頁
function prevPage(){
	var pagenum=parseInt($("#displaySelPage").val())-1;
	var p=getPageParameterName();
	$("#display_pagnum").html("<input type='hidden' name='" + p + "' value='" + pagenum + "'><input type='hidden' name='willPage' value='" + p + "'>");
	doDisplayTagSubmit();
}
//下一頁
function nextPage(){
	
	var pagenum=parseInt($("#displaySelPage").val())+1;
	var p=getPageParameterName();
	$("#display_pagnum").html("<input type='hidden' name='" + p + "' value='" + pagenum + "'><input type='hidden' name='willPage' value='" + p + "'>");
	doDisplayTagSubmit();
}
//最後一頁
function lastPage(){
	var pagenum=$("#totalPage").val();
	var p=getPageParameterName();
	$("#display_pagnum").html("<input type='hidden' name='" + p + "' value='" + pagenum + "'><input type='hidden' name='willPage' value='" + p + "'>");
	doDisplayTagSubmit();
}
//跳頁(輸入框)
function displayTag_changePage(){
	var p=getPageParameterName();
	var pagenum=$("#pagenum").val();
	if(pagenum ==""){
		alert("請輸入要前往的頁數");
		$("#pagenum").focus();
		return false;
	}else{
		$("#display_pagnum").html("<input type='hidden' name='" + p + "' value='" + pagenum + "'>");
		doDisplayTagSubmit();
	}
}

//送出
function doDisplayTagSubmit(){
	viewLoading();
	var formId=$("#nowPage").parents("form").attr("id");
	$("#"+formId).submit();
}
//diaplaytag 使用
function getI18nDisTag(val){
	document.write(getI18nDisMsg("Messages", val));
}
//取得i18n
function getI18nComm(val){
	document.write(getI18nMsg('conf.i18n.common', val));
}