/**
 * Calendar
 */
var _date_cal = null;
function showCalendar(elementName) {
    if (_date_cal == null) {
        _date_cal = new Epoch('_date_cal','popup', document.getElementById(elementName));
    }
    var element = elementName;
    if (typeof elementName == "string") {
        element = document.getElementById(elementName);
    }
    _date_cal.setTarget(element);
    _date_cal.show();

}

function setToday(elementId){
    var today = new Date();
    var year = today.getFullYear();
    var month = today.getMonth()+1;
    if (month < 10){
        month = "0"+month;
    }
    var day = today.getDate();
    if (day < 10){
        day = "0"+day;
    }
    var formatedDate = year+""+month+""+day;
    document.getElementById(elementId).value = formatedDate;
}

function checkDate(date){
    if(date!=""){
        if(isNaN(date)|| date.length!=8){
            return true;
        }
    }
    return false;
}

function checkHour(hour){
    if(hour!=""){
        if(isNaN(hour) || hour > 23|| hour < 0){
            return true;
        }
    }
    return false;
}

function checkMin(min){
    if(min!=""){
        if(isNaN(min)|| min > 59|| min < 0){
            return true;
        }
    }
    return false;
}

/************* 日期檢查 *************
 1.日期字串格式必須為 yyyymmdd.
 2.必須搭配 isLeap() 來判斷閏年.
*************************************/
function dateValidate(dateStr)     
{   
    if(dateStr<0 || isNaN(dateStr) || (dateStr.length != 8))
	{
	    alert("日期格式錯誤, 請用yyyymmdd");
		return false;
	}

    var leap = 28;     
    if (isLeap(parseInt(dateStr.substring(0,4))))   
        leap = 29;     
    var mtmp = parseInt(dateStr.substring(4,6));
    if (dateStr.substring(4,6) == '08')     
        mtmp = 8;     
    if (dateStr.substring(4,6) == '09')     
        mtmp = 9;  

    if (mtmp < 1 || mtmp > 12)     
    { 
	    alert("月錯誤");
	    return false;     
	}     
    var dayInMonth = new Array(12);     
    dayInMonth[1] = 31;     
    dayInMonth[2] = leap;     
    dayInMonth[3] = 31;     
    dayInMonth[4] = 30;     
    dayInMonth[5] = 31;     
    dayInMonth[6] = 30;     
    dayInMonth[7] = 31;     
    dayInMonth[8] = 31;     
    dayInMonth[9] = 30;     
    dayInMonth[10] = 31;     
    dayInMonth[11] = 30;     
    dayInMonth[12] = 31; 
		
    var dtmp = parseInt(dateStr.substring(6)); 
	if (dateStr.substring(6) == '08')     
        dtmp = 8;     
    if (dateStr.substring(6) == '09')     
        dtmp = 9;  
    if(dtmp < 1 || dtmp > dayInMonth[mtmp])     
    {        
	    alert("日錯誤");
	    return false;    
	}		
	return true;
}


/************* 閏年判斷 **************/ 
function isLeap (Year)     
{     
    if (((Year % 4)==0) && ((Year % 100)!=0) || ((Year % 400)==0))     
        return true;     
    else     
        return false;
}


/************* 字串轉日期 *************
 1.日期字串格式必須為 yyyymmdd.
 2.必須搭配 dateValidate(), isLeap() 來先判斷日期是否正確.
*************************************/
function str2Date(str)
{
    if(dateValidate(str))
	{
	    var yyyy = str.substring(0, 4);
	    var mm = str.substring(4, 6)-1;
	    var dd = str.substring(6, 8);
		return new Date(yyyy, mm, dd);
	}
	else
	{
	    return false;
	}
}


/************* 日期轉字串 *************
 1.傳入的日期格式必須為 Date.
 2.產生的日期字串格式為 yyyymmdd.
*************************************/
function date2Str(date)
{
    var yyyy = date.getFullYear();
	var mm = date.getMonth()+1;
	if (mm < 10)
	    mm = "0"+mm;
	var dd = date.getDate();
	if (dd < 10)
	    dd = "0"+dd;
	return yyyy+""+mm+""+dd;
}


/************* 加減月份 *************
 1.必須搭配 dateValidate(), isLeap(), date2Str 來先判斷日期是否正確.
 2.日期字串格式必須為 yyyymmdd.
 3.產生的日期字串格式為 yyyymmdd.
*************************************/
function addMonth(baseDateStr, addedMonthNum)
{
    if(dateValidate(baseDateStr))
	{
	    var yyyy = baseDateStr.substring(0, 4);
	    var mm = baseDateStr.substring(4, 6)-1-(-addedMonthNum);
	    var dd = baseDateStr.substring(6, 8);
		var addedMonthDate = new Date(yyyy, mm, dd);
		return date2Str(addedMonthDate);
	}
	else
	{
	    return false;
	}
}



/************* 加減天數 *************
 1.必須搭配 dateValidate(), isLeap(), date2Str 來先判斷日期是否正確.
 2.日期字串格式必須為 yyyymmdd.
 3.產生的日期字串格式為 yyyymmdd.
*************************************/
function addDay(baseDateStr, addedDayNum)
{
    if(dateValidate(baseDateStr))
	{
	    var yyyy = baseDateStr.substring(0, 4);
	    var mm = baseDateStr.substring(4, 6)-1;
	    var dd = baseDateStr.substring(6, 8)-(-addedDayNum);
		var addedMonthDate = new Date(yyyy, mm, dd);
		return date2Str(addedMonthDate);
	}
	else
	{
	    return false;
	}
}


/************* 兩個日期的差距(也可當做檢查起迄日期) *************
 1.必須搭配 dateValidate(), isLeap(), str2Date 來先判斷日期是否正確.
 2.日期字串格式必須為 yyyymmdd.
 3.回傳的單位為"天"(dateStr2減dateStr1).
*************************************/
function daysDistance(dateStr1, dateStr2)
{
    if(dateValidate(dateStr1) && dateValidate(dateStr2))
	{
	    var date1 = Date.parse(str2Date(dateStr1));
		var date2 = Date.parse(str2Date(dateStr2));
		return Math.ceil((date2-date1)/(24*60*60*1000));
	}
	else
	{
	    return false;
	}
}


/************* 民國年的日期轉為西元年的日期(yyyymmdd) *************
 1.日期字串格式必須為 yymmdd, (yy為民國年).
 2.回傳的格式為yyyymmdd.
*************************************/
function twDate2Ad(dateStr)
{
    var yy = dateStr.substring(0, 2)-(-1911);
    var mm = dateStr.substring(2, 4);
    var dd = dateStr.substring(6);
	return yy+""+mm+""+dd;
}


/************* 西元年的日期(yyyymmdd)轉為民國年 *************
 1.日期字串格式必須為 yyyymmdd.
 2.回傳的格式為 yymmdd, (yy為民國年).
*************************************/
function ad2TwDate(dateStr)
{
    var yyyy = dateStr.substring(0, 4)-1911;
    var mm = dateStr.substring(4, 6);
    var dd = dateStr.substring(6);
	return yyyy+""+mm+""+dd;
}
//轉成yyyy/mm/dd格式
function toTenDate(dateStr)
{
	var conventDate="";
	if(dateStr != "" && dateStr != null){
		 var yyyy = dateStr.substring(0, 4);
	    var mm = dateStr.substring(4, 6);
	    var dd = dateStr.substring(6);
	    conventDate=yyyy+"/"+mm+"/"+dd;
	}
   
	return conventDate;
}
/************* 格式(yyyymmddhhsssss)轉yyyy/mm/dd hh:ss *************/

function dateFormat(date){
	if(date != null && date != ""){
		date=date.substring(0,4)+"/"+date.substring(4,6)+"/"+date.substring(6,8)+" "+date.substring(8,10)+":"+date.substring(10,12);
	}
	document.write(date);
}

/**取得系統時間**/
function getSystemTimeId(){
	timeId=new Date();
	return timeId.getTime();
}
//取得系統日
function getSystemDate(){
	return getFormatDate(new Date(),"yyyy/MM/dd");
}
function getSystemDateToEight(){
	return getFormatDate(new Date(),"yyyyMMdd");
}
//取得星期幾
function getDayOfWeek(date){
	if (date == undefined) {
		date = new Date();
	}
	return date.getDay();
}
//擴展Date的format方法
Date.prototype.format = function (format) {
	var o = {
		"M+": this.getMonth() + 1,
		"d+": this.getDate(),
		"h+": this.getHours(),
		"m+": this.getMinutes(),
		"s+": this.getSeconds(),
		"q+": Math.floor((this.getMonth() + 3) / 3),
		"S": this.getMilliseconds()
	}
	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	for (var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}
/**
*轉換日期物件為日期字串
* @param date 日期對象
* @param isFull 是否為完整的日期資料,
* 為true時, 格式如"2013-12-06 01:05:04"
* 為false時, 格式如 "2013-12-06"
* @return 符合要求的日期字串
*/
function getSmpFormatDate(date, isFull) {
	var pattern = "";
	if (isFull == true || isFull == undefined) {
		pattern = "yyyy-MM-dd hh:mm:ss";
	} else {
		pattern = "yyyy-MM-dd";
	}
	return getFormatDate(date, pattern);
}
/**
*轉換當前日期物件為日期字串
* @param date 日期對象
* @param isFull 是否為完整的日期資料,
* 為true時, 格式如"2013-12-06 01:05:04"
* 為false時, 格式如 "2013-12-06"
* @return 符合要求的日期字串
*/
function getSmpFormatNowDate(isFull) {
	return getSmpFormatDate(new Date(), isFull);
}
/**
*轉換long值為日期字串
* @param l long值
* @param isFull 是否為完整的日期資料,
* 為true時, 格式如"2013-12-06 01:05:04"
* 為false時, 格式如 "2013-12-06"
* @return 符合要求的日期字串
*/
function getSmpFormatDateByLong(l, isFull) {
	return getSmpFormatDate(new Date(l), isFull);
}
/**
*轉換long值為日期字串
* @param l long值
* @param pattern 格式字串,例如：yyyy-MM-dd hh:mm:ss
* @return 符合要求的日期字串
*/
function getFormatDateByLong(l, pattern) {
	return getFormatDate(new Date(l), pattern);
}
/**
*轉換日期物件為日期字串
* @param l long值
* @param pattern 格式字串,例如：yyyy-MM-dd hh:mm:ss
* @return 符合要求的日期字串
*/
function getFormatDate(date, pattern) {
	if (date == undefined) {
		date = new Date();
	}
	if (pattern == undefined) {
		pattern = "yyyy-MM-dd hh:mm:ss";
	}
	return date.format(pattern);
}

