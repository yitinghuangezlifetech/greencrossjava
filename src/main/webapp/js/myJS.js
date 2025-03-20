/**
 * 
 */


function getContextPath() {
	var res = "/" + location.pathname.split("/")[1];
	console.log("getContextPath:"+res);
	return res;
}


function textareaCount(textareaText, countText, countNum) {
	countText.html('剩餘可輸入' + countNum + '字');
	textareaText.on('keyup', function() {
		var txtval = textareaText.val().length;

		var str = parseInt(countNum - txtval);

		if (str >= 0) {
			countText.html('剩餘可輸入' + str + '字');
		} else {
			countText.html('剩餘可輸入0字');
			textareaText.val(textareaText.val().substring(0, countNum));
		}

	});
}

function checkAjaxResp(result) {
	$('.errText').text('');
	if (result.respones == 'success') {
		if (result.mesg != '' && result.mesg != null)
			showSuccessAlert('',result.mesg);
		return true;
	} else if (result.respones == 'fail') {
		if (result.mesg != '')
			showErrorAlert('',result.mesg);
		return false;
	} else if (result.respones == 'validateFail') {
		var bufferString = '<br/>';
		$.each(result.mesg, function(i, obj) {
			bufferString = bufferString + obj.mesg + '<br/>';
			$('#' + obj.errSpanId).text(obj.mesg);
		});
		showErrorAlert('',bufferString);
		return false;
	} else if (result.respones == 'notLogin') {
		location.href = getContextPath()+"/home/loginOut";
	}
}

function showInfoAlert(title,msg) {

	swal(title, msg, 'info');
}

function showErrorAlert(title,msg) {

	swal(title, msg, 'error');
}

function showSuccessAlert(title,msg) {

	swal(title, msg, 'success');
}

function warningConfirm(title,text,confirmButtonText,cancelButtonText,callback){
	swal({
		  title: title,
		  text: text,
		  type: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: confirmButtonText,
		  cancelButtonText: cancelButtonText
		}).then((result) => {
			callback(result.value);
		});
}

function ValidateNumber(e, pnumber){
    if (!/^\d+$/.test(pnumber))
    {
        $(e).val(/^\d+/.exec($(e).val()));
    }
    return false;
}



$(document).ready(
		function() {

			global.popupNotification = $("#popupNotification")
					.kendoNotification().data("kendoNotification");
			
			
})
