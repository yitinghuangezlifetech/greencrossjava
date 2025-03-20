



function addToShoppingCar(form){
	
	if(global.isLogin=='N'){
		
		swal({ 
			  title: "請先登入", 
			  text: "加入購物車前，請先登入系統",
			  type: 'warning',
			  confirmButtonText:'知道了！'
			});
		
		return;
	}
	
	$.ajax({
		type : "POST",
		url : getContextPath()+'/home/shoppingCar/ajaxAddShoppingCar',
		data : form.serialize(),// serializes the form's elements.
		success : function(result) {
			if (checkAjaxResp(result)) {
				console.log(result);
				updateCarInNumber();
				
			}
			
		}
	});
}


function updateCarInNumber(){
	var num = parseInt($('#shoppingCarInNumber').text())+1;
	$('#shoppingCarInNumber').text(num+'');
}

function updateCarInNumberSub(){
	var num = parseInt($('#shoppingCarInNumber').text())-1;
	$('#shoppingCarInNumber').text(num+'');
}

function setCarInNumber(num){
	$('#shoppingCarInNumber').text(num);
}
