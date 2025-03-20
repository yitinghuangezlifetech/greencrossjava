// 弱掃 Client Cross Frame Scripting Attack
if (top != self) top.location=encodeURI(self.location);

function trim(s) {
    return s.replace( /^\s*/, "" ).replace( /\s*$/, "" );
}

function getContextPath() {
  var contextPath = "/" + location.pathname.split("/")[1];
  return contextPath;
}

/**
 * 每頁顯示筆數
 * @returns {Number}
 */
function getPageLength(){
	var pageLength = 20;
	return pageLength;
}

/**
 * 判斷有幾位小數
 * 有使用到的: insertMerchandise.js, promotionPermit.js, promotionResetMerchandise4Medifirst.js,quotationResetMerchandise4Cmm.js
 * quotationResetMerchandise4Medifirst.js, promotionPermit4hbx.js, insertMerchandise4Ikl.js
 * 
 * @param {Object} number
 */ 
function checkDotDigital(number) {
	var num = number.toString();
	var dotIndexFirst = num.indexOf(".");
	var dotDigital=0;
	if (dotIndexFirst>=0){
    	dotDigital = num.length - dotIndexFirst - 1;
    }
    return dotDigital; 	
}
//判斷日期
function checkDates(value,notNull,fieldName){
	if(value == null ) value="";
	if (trim(value).length == 0 ){
		//是否允許為空
		if (notNull){	
			addHtmlNew($("#"+fieldName),"必填");
			return false;
		}else{
			return true;
		}		
	}

	if (isNaN(value)) {
		addHtmlNew($("#"+fieldName),"請輸入日期格式(yyyymmdd)");
		return false;
	}	
	if (value.length!=8) {
		addHtmlNew($("#"+fieldName),"請輸入日期格式(yyyymmdd)");
		return false;
	}	

	var yy=parseInt(value.substring(0,4),10);
	var mm=parseInt(value.substring(4,6),10);
	var dd=parseInt(value.substring(6,8),10);
	var md=31;
	if (mm==4 || mm==6 || mm==9 || mm==11){
		md=30;
	}
	if (mm==2){
		if ( ((yy % 4 == 0) && (yy % 100 != 0)) || (yy % 400 == 0) ){
			md = 29;
		} else {
			md = 28;
		}
	}
	if (mm<1 || mm>12 || dd<1 || dd>md) {
		addHtmlNew($("#"+fieldName),"日期格式錯誤");
		return false;
	}
	return true;
}

//判斷中英文長度，中文3byte @20150824 by anson
function getByteLength(str) {
	return str.length;
}
//訊息提示視窗(黃燈)
function dialogMessage(message){
	img="dialog-waring.png";
	backgroundColor="#FFFFE0";
	//dialogHtml(img,message,backgroundColor);
	dialogShortMesg(message,false);
}

//訊息顯示統一控制
function dialogHtml(img,message,backgroundColor){
	img='<img src="/APMSW/image/'+img+'" style="width:80px;vertical-align:middle">';
	var html="<table><tr><td>"+img+"</td><td>"+message+"</td></tr></table>";
	$(".dialog-message").css({background:backgroundColor}).html(html);
	$(".dialog-message").dialog({
		modal: true
	});
}
//訊息提示視窗(黃燈)
function dialogConfirm(message,dialog_buttons){
	$(".dialog-message").css({background:"#FFFFE0"}).html(message);
	$(".dialog-message").dialog({
		title:'提示訊息',
		modal: true,
		buttons:dialog_buttons
	});
}

//土司共用訊息
function toastError(msg){
	//toastr.error("<span class='toastrMsg'>"+msg+"</span>");
	dialogShortMesg(msg);
}
function toastSuccess(msg){
	//toastr.success("<span class='toastrMsg'>"+msg+"</span>");
	dialogShortMesg(msg);
}
function toastWarning(msg){
	//toastr.warning("<span class='toastrMsg'>"+msg+"</span>");
	dialogShortMesg(msg);
}

//Initialize the ESAPI
org.owasp.esapi.ESAPI.initialize();

/**
 * 訊息提示(倒數n秒自動關閉)
 * @param msg 訊息內容
 * @param isCountdown 是否需要倒數計時後自動關閉
 * @param countdownTimes 倒數秒數
 */
function dialogShortMesg(msg,isCountdown,countdownTimes){
	p_isCountdown=true;//還原預設值
	p_countdownTimes=3;//還原預設值
	if(isCountdown != null) p_isCountdown=isCountdown;//如果有傳入值，以傳入值為主
	if(countdownTimes != null)	p_countdownTimes=countdownTimes;//如果有傳入值，以傳入值為主
	
	//待處理
	//$("#dialog-short .content").html($ESAPI.encoder().encodeForHTML(msg));//訊息內容
	$("#dialog-short .content").html(msg);
	
	$("#dialog-short .showTime").html("");//移除倒數文字
	$("#dialog-short").show("blind",dialogShortMesgCallback());//執行
}
var p_isCountdown=true;//預設值-是否要倒數計程
var p_countdownTimes=3;//預設值-倒數的秒數
function dialogShortMesgCallback() {
	if(p_isCountdown){
		if(p_countdownTimes==0)//如果倒數為0則淡出視窗
			$("#dialog-short").fadeOut();
		else
			setTimeout(dialogShortMesgCallback,1000);//每一秒重新呼叫一次
		$("#dialog-short .showTime").html(p_countdownTimes+" second close");
		p_countdownTimes-=1;
	}
};
//關閉訊息視窗
function closeDialogBtn(){
	$("#dialog-short").hide();
}

//阻斷a href="#" 事件，讓畫面不會跳到最上面
function blockingEvent(event){
	event.preventDefault ? event.preventDefault() : event.returnValue = false;
}
/**
 * 表單檢核：
 * 在要檢核的欄位裡加上屬性：dataType="型態" limit="最大長度或整數長度" decimal="小數位" notNull="不得為空" canZero="是否可以輸入0" greatThen 要大於特定數字
 * 非數字：cssClass="form checkData" dataType="string"  limit="100" notNull="Y" i18n="xxx"
 * 數字：cssClass="form checkData" dataType="number"  limit="16,4" ...  canZero="Y"
 * 日期：cssClass="form checkData" dataType="date"  ... 
 * 下拉選單 cssClass="form2 checkData" dataType="select" ...
 * 核選鈕 cssClass="form2 input type=hidden checkboxGroupClass="class name" checkData" dataType="checkbox" ...
 * 電話 cssClass="form checkData" dataType="tel"  ...
 * 信箱 cssClass="form checkData" dataType="mail" ...
 * @param formName 指定要檢核哪個form的資料，不然會跟dialog form的衝突
 * @param isShowMesg true:顯示錯誤幾個的訊息 false:不顯示
 */
function checkFormData(formName,isShowMesg) {
	removeAlertMsg();//移除錯誤訊息
	check_result=true;
	$("#"+formName+" .checkData").each(function(){
		var type=$(this).attr("dataType");
		var id=$(this).attr("id");
		var errMsg=id+"ErrMsg";
		var maxlength=$(this).attr("limit");
		var value=$(this).val()==null?"":$(this).val();
		var i18nKey=$(this).attr("i18n");
		var labName=$(this).attr("labName");//特異性資料才會用到
		var notNull=$(this).attr("notNull") == "Y"?true:false;
		var decimal=$(this).attr("decimal");//小數位
		var disabled=$(this).attr("disabled");
		var integer="";//整數長度
		var canZero=$(this).attr("canZero") == "Y"?true:false;//是否可為0
		var greatThen=$(this).attr("greatThen");//需大於多少 只有數字型態會用到
		var groupClass=$(this).attr("groupClass");//checkbox或radio時才會用到, check群組的class name
		var limitMatch = $(this).attr("limitMatch") == "Y"?true:false;//字數是否需等於limit最大字數
		
		if(typeof(disabled) === "undefined"){//被disabled的物件不必檢查
			if(type == "string" || type == "tel" || type == "mail"){
				if (trim(value).length == 0) {
					// 是否允許為空
					if (notNull) {
						addHtmlNew($(this),"必填");
						check_result = false;
					}
				}else{
					//maxLen=0表示不需要檢核長度
					if (maxlength != 0 && value.length > maxlength) {
						addHtmlNew($(this),"資料過長");
						check_result = false;
					}
					if(type == "tel" && !isTel(value)){
						addHtmlNew($(this),"格式不正確");
						check_result = false;
					}
					if(type == "mail" && !isEmail(value)){
						addHtmlNew($(this),"格式不正確");
						check_result = false;
					}
				}
			}
			if(type == "number"){
				//整數位=最大長度-1-小數位
				if(decimal != "0" && decimal != null)
					integer=maxlength-1-decimal;
				else
					integer=maxlength;
				//先自動去除千分位逗號
				value=value.replace(",","");
				//若整位位沒有值需自動補0
				if(value.indexOf(".")==0)
					value="0"+value;
				//將轉換後的值回填於畫面上
				$(this).val(value);
				maxlength=integer;
				if (trim(value).length == 0) {
					// 是否允許為空
					if (notNull) {
						addHtmlNew($(this),"必填");
						check_result = false;
					}
				}else{
					if(!canZero && value != ""){
						if(value == "0"){
							addHtmlNew($(this),"不可為0");
							check_result =false;
						}
					}
					if(typeof greatThen !== "undefined"){
						if (greatThen !="" && value != "") {
							if (value<greatThen) {
								addHtmlNew($(this),"需大於"+greatThen);
								check_result =false;
							}
						}
					}
					//判斷長度需等於limit
					if(limitMatch){
						if(value.length != maxlength){
							addHtmlNew($(this),"需為"+maxlength+"碼");
							check_result = false;
						}
					}
					// 判斷輸入之字串是否為數字
					if (isNaN(value)) {
						addHtmlNew($(this),"需為數字");
						check_result = false;
					}
		
					var num = checkDotDigital(value);
					// 判斷有幾位小數
					if (num > decimal) {
						if(decimal==0) {
							addHtmlNew($(this),"需為整數");
						} else {
							addHtmlNew($(this),"不可超過"+decimal+"位小數");
						}
						check_result = false;
					}
		
					// 判斷長度
					var lens = value.length;
					//如果有小數位的話，整數位的長度應取該串字長度-1再減小數位
					if (num > 0) {
						lens = lens - 1 - num; // 1:小數點 num:小數
					}
					//lens經由上一步驟判斷後，可計算「整數位」的長度是否正確
					if (lens > maxlength) {
						addHtmlNew($(this),"不可超過"+maxlength+"位整數");
						check_result = false;
					}
				}
			}
			if(type == "date"){
				if(!checkDates(value, notNull,  $(this).attr("id")))
					check_result = false;
			}
			if(type == "select"){
				if((value == "" || value == null) && notNull){
					addHtmlNew($(this),"必填");
					check_result = false;
				}	
			}
			//}
			//checkbox有分單一或群組
			if(type == "checkbox"){
				if (notNull) {
					if(groupClass == undefined){
						if(!$(this).is(":checked")){
							addHtmlNew($(this).parent(),"必填");
							check_result = false;
						}
					}else{
						if($("."+groupClass+":checked").length == 0){
							addHtmlNew($(this).parent(),"至少選一個");
							check_result = false;
						}
					}
				}
			}
			if(type == "radio"){
				if (notNull) {
					if($("."+groupClass+":checked").length == 0){
						addHtmlNew($(this).parent(),"至少選一個");
						check_result = false;
					}

				}
			}
		}
	});
	checkHasErrorMsg(isShowMesg);
	return check_result;
}
//檢查畫面是否有檢核錯誤，將畫面捲到有錯的第一個位置，以及彈跳提示
//isShowMesg:true:顯示錯誤幾個的訊息
function checkHasErrorMsg(isShowMesg){
	var checkResult=true;
	if($(".errorMsg").length > 0){
		$(".errorMsg").eq(0).prev().focus();//如果prev()物件是disalbed或非input select選項時就沒 辦法focus
		if(isShowMesg)
			dialogMessage("畫面欄位共有"+$(".errorMsg").length +"個不正確，請確認！");
		checkResult=false;
	}
	return checkResult;
}

//有套datepicker的欄位產生日期選項
$(document).ready(getDatapicker);
function getDatapicker(){
	$(".datepicker").each(function(){
		$(this).datepicker({
			dateFormat: 'yymmdd', 
			showOn: 'both', 
			autoSize: true,
			changeYear: true,
			changeMonth: true,
			showOtherMonths: true, 
		    showWeeks: true,
		    firstDay: 0, 
		    yearRange: 'c-20:c+5',
		    showButtonPanel: true, 
		    buttonImageOnly: true, 
		    buttonImage: '/APCSM/img/icon_date.gif'
		});
	});
}

//有套datepicker的欄位產生時間選項
$(document).ready(function(){
	$(".timepicker").click(function(){
		$(this).val("");//要先清空不然時間元件會出不來
		//接著綁定
		$(this).datetimepicker({
			language:  'zh-TW',
	        weekStart: 1,
	        todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 1,
			minView: 0,
			maxView: 1,
			forceParse: 0
		});
		//再focus自己一次使它跳出時間元件
		$(this).focus();
	});
});

function getTimepicker(){
	$(".timepicker").each(function(){
		
		$(this).datetimepicker({
			language:  'fr',
	        weekStart: 1,
	        todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 1,
			minView: 0,
			maxView: 1,
			forceParse: 0
		});
	});
}
// 限制結束日期的選擇不可小於起始日期
$(document).ready(function(){
	getStartDateAndEndDate(".startDate", ".endDate");
});
function getStartDateAndEndDate(startDate, endDate){
	$(startDate).datepicker({
		defaultDate: "+1w",
        changeMonth: true,
        numberOfMonths: 3
	}).on("change", function(){
	    $(endDate).datepicker( "option", "minDate", getDate( this ) );
	});
	
	$(endDate).datepicker({
		defaultDate: "+1w",
        changeMonth: true,
        numberOfMonths: 3
	}).on("change", function () {
	    $(startDate).datepicker( "option", "maxDate", getDate( this ) );
	});
}
function getDate( element ) {
	var date;
    try {
    	date = $.datepicker.parseDate( "yymmdd", element.value );
   	} catch( error ) {
    	date = null;
    }
 	return date;
}
/*
 * 月份加減
 * params date (格式:yyyymm,例:201802)
 * params num 加減數 (例:next,prev)
 * params type 加或減
 * return date 
 * */
function chgMonth(date,num,type){
	var d=new Date(date.substring(0,4)+'-'+date.substring(4,6)+'-01');
	
	if(type == 'next')
		d.setMonth(d.getMonth()+num);
	else
		d.setMonth(d.getMonth()-num);
	
	date = d.getFullYear()+''+paddingLeft(String(d.getMonth()+1),2);
	
	return date;
}
/**取得系統時間**/
function getSystemTimeId(){
	timeId=new Date();
	return timeId.getTime();
}
//取得request param
function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}
//取得i18n資訊，javascript適用
function getI18nJsMsg(id) {
	var msg = id;
	$.ajax({
        contentType: "application/json",
        async:false,
        url: '/APCSM/ajax/getI18nJsMsg',
        data: {
            i18nId: id
        }, 
        dataType: 'json',
        success: function(result) {
        	msg = result.message;
        }
    });
	return msg;
}

//取得i18n資訊，dispalyTag適用
function getI18nDisMsg(bundel, id) {
	var msg = id;
	$.ajax({
        contentType: "application/json",
        async:false,
        url: '/APCSM/ajax/getI18nDisMsg',
        data: {
        	i18nBundle: bundel,
            i18nId: id
        }, 
        dataType: 'json',
        success: function(result) {
        	msg = result.message;
        }
    });
	return msg;
}

//移除錯誤提示訊息
function removeAlertMsg() {
	$("span[id$=ErrMsg]").html("");
	$(".errorMsg").remove();
}
/**
 * 讓錯誤提示消息
 * @param t this物件
 */
function doRequiredHandler(t){
	$("#"+t.attr("id")+"ErrMsg").html("");
	$("#"+t.attr("name")+"ErrMsg").html("");
	
}

//初始化綁定
$(function(){
	inputLimit();//提示剩餘字數
	appendRedStar();//必填欄位加上紅色星號(not contains已加過星號的就不用再加了)
});
//加星號
function appendRedStar(){
	$(".redStar:not(:contains('*'))").each(function(){
		$(this).prepend("<font color='#F97400' class='redStarFlag'>*</font>");
	});
}
//輸入長度提示
function inputLimit(){
	$(".inputLimit").each(function(){
		//避免因為重複呼叫造成已綁定過的再次綁定，故先判斷是否是只有數字沒有中文字時才需要綁定
		if(!isNaN($(this).html())){
			$(this).parent().prev().limit($(this).html(),$(this));
		}
		//第一次綁定時在數字的前後自動加上中文字，排除 disabled物件及已經被綁過的
		if($(this).parent().prev().val() == "" && !$(this).parent().prev().prop("disabled") && !isNaN($(this).html()))
			   $(this).prepend("剩").append("個字");
	});
}
//欄位提示說明
$(document).ready(function(){
	$(".tipDescIcon").click(function(){
		$("#dialog-message").dialog({
			title:$(this).attr("title"),
			show: { effect: "blind" },
		    width: "60%",
		    buttons:null
		}).html($(this).attr("tipMsg")).css("background-color","white");
	});
});
//輸入訊息視窗
function dialogInputMemo(message,dialog_buttons){
	var img='<img src="/APMSW/image/dialog-question.png" style="width:80px;vertical-align:middle">';
	var html="<table><tr><td>"+img+"</td><td>"+message+"<input id='memo' type='text' />";
	$(".dialog-message").css({background:"#FFFFE0"}).html(html);
	$(".dialog-message").dialog({
		title:"說明",
		modal: true,
		buttons:dialog_buttons
	});
}
//顯示loading畫面
function viewLoading() {
	$("#dialogLoading").modal();
}
//關閉
function viewLoadingClose(){
	$("#dialogLoading").modal("hide");
}
/*在某個物件後面加上錯誤訊息提示*/
function addHtmlNew(t,str){
	$(t).after("<span class='errorMsg'>"+str+"</span>");
}
//檢查email格式
function isEmail(mail)
{
	result=false;
	if(/(\S)+[@]{1}(\S)+[.]{1}(\w)+/.test(mail)) 
		result= true;
	return result;
}
//電話號碼檢核，第1碼可接受+號，若其它地方出現+號則不正確，可以有"(" ")" "-"符號
function isTel(str){ 
	result=true;
	if(str != ""){
		var reg=/^([0-9]|[\+\-\#\(\)])+$/g; 
		//是否有+號，若有需在第一位
		if(str.indexOf("+") > 0)
			result=false;
		//需符合可接受數字及符號
		if(!reg.test(str))
			result=false;
	}
	return result; 
} 
//判斷是否為整數
function isInteger(obj){
	return obj % 1 ===0;
}
//除法：arg1除以arg2的精確結果
function accDiv(arg1, arg2) {
    var t1 = 0, t2 = 0, r1, r2;
    try { t1 = arg1.toString().split(".")[1].length } catch (e) { }
    try { t2 = arg2.toString().split(".")[1].length } catch (e) { }
    with (Math) {
        r1 = Number(arg1.toString().replace(".", ""))
        r2 = Number(arg2.toString().replace(".", ""))
        t3=(r1 / r2) * pow(10, t2 - t1);
        //判斷是否有小數位，且長度大於小數2位，只取到小數2位
        dot = checkDotDigital(t3);
        if(dot > 2){
        	var totalLength=t3.toString().length;
        	t3=t3.toString().substring(0,totalLength-dot+2);
        }
        	
        return t3;
    }
}
//乘法：arg1乘以 arg2的精確結果
function accMul(arg1, arg2) {
    var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
    try { m += s1.split(".")[1].length } catch (e) { }
    try { m += s2.split(".")[1].length } catch (e) { }
    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
}
//加法：arg1加 arg2的精確結果
function accAdd(arg1, arg2) {
    var r1, r2, m, c;
    try { r1 = arg1.toString().split(".")[1].length } catch (e) { r1 = 0 }
    try { r2 = arg2.toString().split(".")[1].length } catch (e) { r2 = 0 }
    c = Math.abs(r1 - r2);
    m = Math.pow(10, Math.max(r1, r2))
    if (c > 0) {
        var cm = Math.pow(10, c);
        if (r1 > r2) {
            arg1 = Number(arg1.toString().replace(".", ""));
            arg2 = Number(arg2.toString().replace(".", "")) * cm;
        }
        else {
            arg1 = Number(arg1.toString().replace(".", "")) * cm;
            arg2 = Number(arg2.toString().replace(".", ""));
        }
    }
    else {
        arg1 = Number(arg1.toString().replace(".", ""));
        arg2 = Number(arg2.toString().replace(".", ""));
    }
    return (arg1 + arg2) / m;
}

//減法：arg1減 arg2的精確結果
function accSub(arg1, arg2) {
    var r1, r2, m, c;
    try { r1 = arg1.toString().split(".")[1].length } catch (e) { r1 = 0 }
    try { r2 = arg2.toString().split(".")[1].length } catch (e) { r2 = 0 }
    c = Math.abs(r1 - r2);
    m = Math.pow(10, Math.max(r1, r2))
    if (c > 0) {
        var cm = Math.pow(10, c);
        if (r1 > r2) {
            arg1 = Number(arg1.toString().replace(".", ""));
            arg2 = Number(arg2.toString().replace(".", "")) * cm;
        }
        else {
            arg1 = Number(arg1.toString().replace(".", "")) * cm;
            arg2 = Number(arg2.toString().replace(".", ""));
        }
    }
    else {
        arg1 = Number(arg1.toString().replace(".", ""));
        arg2 = Number(arg2.toString().replace(".", ""));
    }
    return (arg1 - arg2) / m;
}

//重新整理Table的Tr class
function refreshTableClass() {
	
	$("table #disList").each(function(tableIndex, tableElement){
		$(tableElement).find("tbody tr").each(function(index, element){
			if(index % 2 == 0) {
				$(element).attr({"class":"odd"});
			} else {
				$(element).attr({"class":"even"});
			}
		});
	});
}

// 向左補0
function paddingLeft(str,lenght) {
	if(str.length >= lenght)
		return str;
	else
		return paddingLeft("0" + str, lenght);
}

//向右補0
function paddingRight(str,lenght) {
	if(str.length >= lenght)
		return str;
	else
		return paddingRight(str + "0", lenght);
}


//審核流程頁面
function showFlowStatus(quotationId, flowType, trackId){
	var w=$(window).width();
	var h=$(window).height();
	var param = {
		"quotation.id": quotationId,
		"quotation.flowType": flowType,
		"quotation.trackId": trackId
	};
	$("#dialog").dialog({
		title:'審核流程',
		modal:true,
		width: w-50,
		height: h,
		position:['center',20] ,
		open:function(){
			$(this).load("/APCSM/showFlowStatus", param, function(){
				console.log("showFlowStatus!");
			});
		}		
	});
}

//匯出報表
function exportReport(url, reportType, param){
	if(param.reportType == null || param.reportType == undefined || param.reportType != reportType){
		param.reportType = reportType;
	}

	document.cookie = "fileDownload=true; path=/";
	$.fileDownload(url, {
		//preparingMessageHtml: "處理中",
		failMessageHtml : "執行失敗",
		httpMethod : "GET",
		prepareCallback: function(url) {
			viewLoading();
		},
		successCallback: function (url) {},
		data : param
	}).done(function() {
		setTimeout(viewLoadingClose, 1000);
	});
}

//紀錄使用者是否有看公告、下載附件、點訂單、退貨單、收費單…等紀錄
function recordClick(sourceType, sourceId){
	$.ajax({
	    url: "/APCSM/common/recordClick",
	    type: "POST",		    
	    dataType: "json",
	    data: {
	    	"sourceType": sourceType,
	    	"sourceId": sourceId
	    },
	    async: false,
	    success: function(dataMap) {
	    }
	});
}

//按鈕共用
$(document).ready(function(){
	// 回上一頁
	$("#btn_back").click(function(){
		history.go(-1);
	});
	// 重填按鈕
	$("#btn_reset").click(function(){
		$(".queryForm :input").val("");
	});
	
	//訊息提示(倒數n秒自動關閉)
	$("#closeSohrtMesg").click(function(){
		$("#dialog-short").hide();
		p_countdownTimes=0;//若視窗關閉應該倒數秒數設為0，這樣setTimeout就不會一直執行，或影響其它呼叫者
		blockingEvent();
	});
	
	//燈箱
	if($(".fancybox").length > 0)
		  $(".fancybox").fancybox();
	
	//提示
	$("[data-toggle='tooltip']").tooltip();
	
	
	// 補足IE11 Support endWith
	String.prototype.endsWith = function(searchStr, Position) {
	      // This works much better than >= because
	      // it compensates for NaN:
	      if (!(Position < this.length))
	        Position = this.length;
	      else
	        Position |= 0; // round position
	      return this.substr(Position - searchStr.length,  searchStr.length) === searchStr;
	};
	
	//讓條碼欄位關閉輸入法，讓條碼機可以正常掃入
	$(".checkProduct").focus(function(){
		this.style.imeMode='disabled';
	});

	//10碼廠編 輸入框 清空
	$("#supplierIdTags").change(function () {
		if($(this).val().trim() == ""){
			$("#supplierId").val("");
		}
	});
	
	//10碼廠編 輸入框 autocomplete
	$(".supplierIdAuto").autocomplete({
	    source: function(request, response) {
	        var formData = new FormData();
	        formData.append("supplierCode", request.term.toLowerCase());
	    	$.ajax({
	    	    url: "/APCSM/common/getSupplierCodeList",
	    	    data: formData,
	    	    cache: false,
	    	    contentType: false,
	    	    processData: false,
	    	    type: "POST",
	    	    success: function(data) {
	    	    	if (data.msg == "") {
	    	    		var choices = [];
	    	    		var items = data.supplierCodeList;
	    		    	$.each(items, function (i, item) {
	    		    		choices.push({ 
	    		    			label : item.SUPPLIER_ID + "-" +item.SUPPLIER_NAME_BRIEF+"(" +item.SUPPLIER_ID_SUB + ")" ,
	    		    	        value: item.SUPPLIER_ID
	    		    	    });
	    		    	});
	    		        response(choices);
	    	    	} else {
	    	    		dialogShortMesg(data.msg);
	    	    	}
	    	    }
	    	});
	    },
	    select: function (event, ui) {
	    	$(this).val(ui.item.label);	    	
	    	$("#supplierId").val(ui.item.value);
	    	return false;
	    }
	});
		 
});