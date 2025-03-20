<%@ page language="java" contentType="text/html;  charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/common-util.tld" prefix="pi"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Spring 4 MVC -HelloWorld</title>

</head>

<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-12">
				<br />
			</div>
		</div>
		<div class="row">
			<div class="col-xl-1 col-lg-1 col-md-1 col-sm-12 col-12"></div>

			<div class="col-xl-10 col-lg-10 col-md-10 col-sm-12 col-12">
				<div class="row">
					<div class="col-sm-1"></div>
					<div id="step1" class="col-3  step-now">
						<font><c:choose><c:when test="${sessionScope.local == 'en'}">shopping cart</c:when><c:otherwise>購物車</c:otherwise></c:choose></font>
					</div>
					<div  class="col-1">
						<i class="fas fa-angle-right"></i>
					</div>
					<div id="step2" class="col-3  step-after">
						<font><c:choose><c:when test="${sessionScope.local == 'en'}">Order Confirmation</c:when><c:otherwise>訂單確認</c:otherwise></c:choose></font>
					</div>
					<div class="col-1">
						<i class="fas fa-angle-right"></i>
					</div>
					<div id="step3" class="col-3  step-after">
						<font><c:choose><c:when test="${sessionScope.local == 'en'}">Order completed</c:when><c:otherwise>訂單完成</c:otherwise></c:choose></font>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<hr />
					</div>
				</div>

				<div class="row">
					<div class="col-12"></div>
				</div>
			</div>

			<div class="col-xl-1 col-lg-1 col-md-1 col-sm-12 col-12"></div>
		</div>

		<div class="row shopping-car-page-content">
			<div class="col-xl-1 col-lg-1 col-md-1 col-sm-12 col-12"></div>
			<div class="col-xl-10 col-lg-10 col-md-10 col-sm-12 col-12">
				<div class="row">
					<div class="col-12">
						<br />
						<div id="shoppingCarDiv" class="card">
							<div class="card-header shopping-car-card-header">
								<div class="row">
									<div class="col-xl-4 col-12 text-left text-xl-center">   
										<font><c:choose><c:when test="${sessionScope.local == 'en'}">Product Name/Information</c:when><c:otherwise>商品名稱/資訊</c:otherwise></c:choose></font>
									</div>
									<div class="col-xl-2 text-center">
										<font class="hide-prod-title"><c:choose><c:when test="${sessionScope.local == 'en'}">unit price</c:when><c:otherwise>單價</c:otherwise></c:choose></font>
									</div>
									<div class="col-xl-2 text-center">
										<font class="hide-prod-title"><c:choose><c:when test="${sessionScope.local == 'en'}">Quantity</c:when><c:otherwise>數量</c:otherwise></c:choose></font>
									</div>
									<div class="col-xl-2 text-center">
										<font class="hide-prod-title"><c:choose><c:when test="${sessionScope.local == 'en'}">subtotal</c:when><c:otherwise>小計</c:otherwise></c:choose></font>
									</div>
									<div class="col-xl-2 text-center">
										<font class="hide-prod-title shoppingCarDivRemove"><c:choose><c:when test="${sessionScope.local == 'en'}">Give up buying</c:when><c:otherwise>放棄購買</c:otherwise></c:choose></font>
									</div>
								</div>
							</div>
							<div class="card-body" id="carItem"> 
								<div class="row">
									<div class="col-xl-12 text-center">
										<font><c:choose><c:when test="${sessionScope.local == 'en'}">There are currently no items in the shopping cart</c:when><c:otherwise>目前購物車內沒有商品</c:otherwise></c:choose>。</font> 
									</div> 
								</div>
							</div>
							<div class="card-header shopping-car-card-header" id='giftTopDiv'>
								<div class="row">
									<div class="col-xl-4 col-7 text-left text-xl-center">
										<font><c:choose><c:when test="${sessionScope.local == 'en'}">Gift Name/Information</c:when><c:otherwise>贈品名稱/資訊</c:otherwise></c:choose></font>
									</div>
									<div class="col-xl-5 col-5 text-center">
										<font class="hide-prod-title"><c:choose><c:when test="${sessionScope.local == 'en'}">Gift description</c:when><c:otherwise>贈品說明</c:otherwise></c:choose></font>
									</div>
									<div class="col-xl-3 text-center">
										<font class="hide-prod-title"><c:choose><c:when test="${sessionScope.local == 'en'}">Quantity</c:when><c:otherwise>數量</c:otherwise></c:choose></font>
									</div>
									
								</div>
							</div>  
							<div class="card-body" id="giftListDiv"> 
								
							</div>
							
							<div class="card-body" id="orderInfo"> 
								<form id ="orderFrom">
									<div class="row">
										<div class="col-xl-12 text-center">
											<font><c:choose><c:when test="${sessionScope.local == 'en'}">Order information</c:when><c:otherwise>訂單資訊</c:otherwise></c:choose></font>
										</div> 
										<div class="col-xl-12 text-center">
											<hr/>
										</div>
									</div>
									<div class="row align-items-center mar-bottom">
										<div class="col-xl-1 text-xl-right">
											<font><c:choose><c:when test="${sessionScope.local == 'en'}">Recipient</c:when><c:otherwise>收件人</c:otherwise></c:choose></font>
										</div> 
										<div class="col-xl-5 text-center">
											<input class="form-control" type="text"  name="recipient" value="" >
										</div>
										<div class="col-sm-6">
										</div>
										<div class="col-sm-1">
										</div>
										<div class="col-sm-9">
											<font class='errText form-text' id='err_recipient' color='red'></font>
										</div>
									</div>
									<div class="row align-items-center mar-bottom">
										<div class="col-xl-1 text-xl-right">
											<font><c:choose><c:when test="${sessionScope.local == 'en'}">mailing address</c:when><c:otherwise>寄件地址</c:otherwise></c:choose></font>
										</div>
										<div class="col-xl-9 text-center">
											<input class="form-control" type="text"  name="sendAddress" value="" >
										</div>
										<div class="col-sm-2">
										</div>
										<div class="col-sm-1">
										</div>
										<div class="col-sm-9">
											<font class='errText form-text' id='err_sendAddress' color='red'></font>
										</div>
									</div> 
									<div class="row align-items-center mar-bottom">
										<div class="col-xl-1 text-xl-right">
											<font><c:choose><c:when test="${sessionScope.local == 'en'}">Memo</c:when><c:otherwise>備註</c:otherwise></c:choose></font>
										</div>
										<div class="col-xl-9 text-center">
											<input class="form-control" type="text"  name="memo" value="" >
										</div>
										<div class="col-sm-2">
										</div>
										<div class="col-sm-1">
										</div>
										<div class="col-sm-9">
											<font class='errText form-text' id='err_memo' color='red'></font>
										</div>
									</div>
								</form>
							</div>
							 <div class="card-footer shopping-car-card-footer">
							 	<div class="row align-items-center">
							 		<div class = 'col-xl-8 col-3 text-right shopping-car-name mar-bottom'>
							 			<font><c:choose><c:when test="${sessionScope.local == 'en'}">total</c:when><c:otherwise>總計</c:otherwise></c:choose></font>
							 		</div>
							 		<div class = 'col-xl-2 col-9 text-right mar-bottom'>
							 			<c:choose><c:when test="${sessionScope.local == 'en'}">NT $</c:when><c:otherwise>新台幣</c:otherwise></c:choose><font id='allPrice' class='shopping-car-price'>0</font><c:choose><c:when test="${sessionScope.local == 'en'}">.</c:when><c:otherwise>元</c:otherwise></c:choose>
							 		</div>
							 		<div class="col-xl-2 col-12 mar-bottom">
										<button type="button" id="createOrder" class="btn btn-success"
										style="width: 100%;">
										<i class="fas fa-check-circle"> <c:choose><c:when test="${sessionScope.local == 'en'}">Order set up</c:when><c:otherwise>訂單成立</c:otherwise></c:choose></i> 
										</button> 
									</div>
							 	</div>
							 </div>
							
						</div>
						<br />
						<div id="step3Div" class="card">
							<div class="card-body" > 
								<div class="row align-items-center">
									<div class="col-12 text-center">
										<font><c:choose><c:when test="${sessionScope.local == 'en'}">The order was successfully established</c:when><c:otherwise>訂單成立成功</c:otherwise></c:choose>！</font>
									</div>
									<div class="col-12 text-center">
										<font><c:choose><c:when test="${sessionScope.local == 'en'}">You can go to the Member Center "Order Management" function to view the order details.</c:when><c:otherwise>您可以至會員中心『訂單管理』功能查看訂單詳情</c:otherwise></c:choose>。</font>
									</div>
								</div>
							</div>
						</div>
						<br />
						<br />
					</div>
				</div>
			</div>
			<div class="col-xl-1 col-lg-1 col-md-1 col-sm-12 col-12"></div>
		</div>
		
	</div>
	
	
	
	
	<form id ="shopCarFrom">
		<input type="hidden" id="shopCarFromId" name="id" value="">
		<input type="hidden" id="shopCarFromActionCode" name="actionCode" value="">
		<input type="hidden" id="shopCarFromOrderNumber" name="orderNumber" value="">
		<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
	</form>
	
	<script>
		$(document).ready(function() {
			$('#orderInfo,#step3Div').hide();
			global.nowStep='1';
			getShoppingCar();
			
			$('#createOrder').click(function(e) {
				nextStep();
			}); 
			
			
		});
		
		function nextStep(){
			if(global.nowStep=='1'){
				goToStep2();
			}else if(global.nowStep=='2'){
				goToStep3();
			}
		}
		
		
		function goToStep2(){
			$('#step1').removeClass("step-now");
			$('#step1').addClass("step-after");
			
			$('#step2').removeClass("step-after");
			$('#step2').addClass("step-now");
			
			$('.shoppingCarDivSub').hide();
			$('.shoppingCarDivAdd').hide();
			
			$('.shoppingCarDivNumberInput').attr('disabled', 'disabled');
			$('.shoppingCarDivRemove').hide();
			
			$('#createOrder').html('<i class="fas fa-check-circle"> <c:choose><c:when test="${sessionScope.local == 'en'}">Order Confirmation</c:when><c:otherwise>確定訂單</c:otherwise></c:choose></i> ');
			$('#createOrder').removeClass("btn-success");
			$('#createOrder').addClass("btn-danger");
			
			$('#orderInfo').show();
			  
			global.nowStep='2';
		}
		
		function goToStep3(){
			
			createOrderItem();
			
			
			
		}
		
		function createOrderItem(){
			$.ajax({
				type : "POST",
				url : '<c:url value = "/home/shoppingCar/ajaxCreateOrderItem"/>',
				data : $("#orderFrom").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {
						console.log(result);
						
						$('#step2').removeClass("step-now");
						$('#step2').addClass("step-after");
						 
						$('#step3').removeClass("step-after");
						$('#step3').addClass("step-now");
						
						$('#shoppingCarDiv').hide();
						global.nowStep='3';
						
						$('#step3Div').show();
						setCarInNumber('0');
					}
					
				}
			});
		}

		function getShoppingCar() {
			$.ajax({
				type : "POST",
				url : '<c:url value = "/home/shoppingCar/ajaxGetAllShoppingCar"/>',
				data : $("#shopCarFrom").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {
						console.log(result);
						var htmlCode =''; 
						$.each(result.data.shopCarList,function(i, obj) {
								
								htmlCode=htmlCode+shoppingCarItemHtml(obj);
						});
						$('#allPrice').text(result.data.allPrice);
						if(htmlCode!='')
							$('#carItem').html(htmlCode);
						else{
							$('#carItem').html('<div class="row"> <div class="col-xl-12 text-center"> <font><c:choose><c:when test="${sessionScope.local == 'en'}">There are currently no items in the shopping cart</c:when><c:otherwise>目前購物車內沒有商品</c:otherwise></c:choose>。</font></div></div>');
							$('#createOrder').attr('disabled', 'disabled');
						}
						
						var giftHtmlCode='';
						$.each(result.data.giftList,function(i, obj) {
							
							giftHtmlCode=giftHtmlCode+giftItemHtml(obj);
						});
						if(giftHtmlCode!=''){
							$('#giftListDiv').html(giftHtmlCode);
							$('#giftTopDiv').show();
						}else{
							$('#giftListDiv').html(giftHtmlCode);
							$('#giftTopDiv').hide();
						}	
					}
					
				}
			});
		}
		
		function giftItemHtml(obj){
			var itemHtml=
			'	<div class="row align-items-center">                                                                                                                         '+
			'		<div class="col-xl-4 col-12">                                                                                                                                   '+
			'			<div class="row align-items-center">                                                                                                                 '+
			'				<div class="col-4 text-center">                                                                                                                  '+
			'					<a href="/WebSiteSample/home/store/product/'+obj.giftProNo+'"><img class="shopping-car-img" src="/WebSiteSample/ajaxGetProductPic/'+obj.giftpict+'"></a>  '+
			'				</div>                                                                                                                                           '+
			'				<div class="col-xl-8 col-7 ">                                                                                                                    '+
			'					<div class="row">                                                                                                                            '+
			'						<div class="col-12 shopping-car-name align-items-center">                                                                                '+
			'							<font>'+obj.giftName+'</font>                                                                                                               '+
			'						</div>                                                                                                                                   '+
			'						<div class="col-12 shopping-car-content align-items-center">                                                                             '+
			'							<font>'+obj.giftSpec+'</font>                                                                                                                    '+
			'						</div>                                                                                                                                   '+
			'					</div>                                                                                                                                       '+
			'				</div>                                                                                                                                           '+
			'			</div>                                                                                                                                               '+
			'		</div>                                                                                                                                                   '+
			'		<div class="col-xl-5 text-center col-12">                                                                                                                 '+
			'			<div class="row align-items-center">                                                                                                                 '+
			'				<div class="col-12 show-prod-less shopcar-item-bg">                                                                                              '+
			'					<font class="show-prod-less"><c:choose><c:when test="${sessionScope.local == 'en'}">Gift description</c:when><c:otherwise>贈品說明</c:otherwise></c:choose></font>                                                                                                 '+
			'				</div>                                                                                                                                           '+
			'				<div class="col-xl-12 col-12  align-items-center">                         					                                                     '+
			'					'+obj.giftContent+'                   				                                                                                                 '+
			'				</div>                                                                                                                                           '+
			'			</div>                                                                                                                                               '+
			'			                                                                                                                                                     '+
			'		</div>                                                                                                                                                   '+
			'		<div class="col-xl-3 text-center col-12">                                                                                                                 '+
			'			<div class="row align-items-center">                                                                                                                 '+
			'				<div class="col-12 show-prod-less shopcar-item-bg">                                                                                              '+
			'					<font class="show-prod-less"><c:choose><c:when test="${sessionScope.local == 'en'}">Quantity</c:when><c:otherwise>數量</c:otherwise></c:choose></font>                                                                                                     '+
			'				</div>                                                                                                                                           '+
			'				<div class="col-xl-12 col-12  align-items-center">                         					                                                     '+
			'					'+obj.giftNumber+'                   				                                                                                                         '+
			'				</div>                                                                                                                                           '+
			'			</div>                                                                                                                                               '+
			'		                                                                                                                                                         '+
			'		                                                                                                                                                         '+
			'			                                                                                                                                                     '+
			'		</div>                                                                                                                                                   '+
			'	</div>                                                                                                                                                       '+
			'	<div class="row">                                                                     '+
			'		<div class="col-12">                                                              '+
			'			<hr />                                                                        '+
			'		</div>                                                                            '+
			'	</div>                                                                                ';
			
			return itemHtml;
		}
		
		
		function shoppingCarItemHtml(obj){
			var itemHtml=							
			'	<div class="row align-items-center">                                                  '+
			'		<div class="col-xl-4 mar-bottom">                                                 '+
			'			<div class="row align-items-center">                                          '+
			'				<div class="col-4 text-center">                                           '+
			'					<a href="/WebSiteSample/home/store/product/'+obj.prono+'"><img           '+
			'						class="shopping-car-img"                                          '+
			'						src="/WebSiteSample/ajaxGetProductPic/'+obj.pict+'"></a>            '+
			'				</div>                                                                    '+
			'				<div class="col-xl-8 col-6 ">                                                      '+
			'					<div class="row">                                                     '+
			'						<div class="col-12 shopping-car-name">                            '+
			'							&nbsp;&nbsp;<font>'+obj.proname+'</font>                            '+
			'						</div>                                                            '+
			'						<div class="col-12 shopping-car-content">                         '+
			'							&nbsp;&nbsp;<font>'+obj.prospec+'</font>                                 '+
			'						</div>                                                            '+
			'					</div>                                                                '+
			'				</div>                                                                    '+
			'               <div class="col-2 show-prod-less">                                       '+
			'			        <font class="shoppingCarDivRemove" onclick="removeBut(\''+obj.shopid+'\')"><i class="far fa-trash-alt"></i></font>                                 '+
			'               </div>                                                                    '+
			'			</div>                                                                        '+
			'		</div>                                                                            '+
			'		<div class="col-xl-2 col-6 text-center mar-bottom">                               '+
			'			<div class="row align-items-center">                                          '+
			'				<div class="col-12 show-prod-less shopcar-item-bg">                       '+
			'					<font class="show-prod-less"><c:choose><c:when test="${sessionScope.local == 'en'}">Price per item</c:when><c:otherwise>每件</c:otherwise></c:choose></font>                              '+
			'				</div>                                                                    '+
			'				<div class="col-xl-12 col-9  align-items-center">                         '+
			'					<c:choose><c:when test="${sessionScope.local == 'en'}">NT $</c:when><c:otherwise>新台幣</c:otherwise></c:choose><font class="shopping-car-price">'+obj.proprice+'</font><c:choose><c:when test="${sessionScope.local == 'en'}">.</c:when><c:otherwise>元</c:otherwise></c:choose>                   '+
			'				</div>                                                                    '+
			'				<div class="col-3 show-prod-less align-items-center">                     '+
			'					<div class="row align-items-center">                                  '+
			'						<div class="col-12 shopcar-fix-height"></div>                     '+
			'					</div>                                                                '+
			'				</div>                                                                    '+
			'			</div>                                                                        '+
			'		</div>                                                                            '+
			'		<div class="col-xl-2 col-6 text-center mar-bottom">                               '+
			'			<div class="row align-items-center">                                          '+
			'				<div class="col-12 show-prod-less shopcar-item-bg">                       '+
			'					<font class="show-prod-less"><c:choose><c:when test="${sessionScope.local == 'en'}">Quantity</c:when><c:otherwise>數量</c:otherwise></c:choose></font>                              '+
			'				</div>                                                                    '+
			'			</div>                                                                        '+
			'			<div class="input-group align-items-center shopcar-fix-height">               '+
            '                                                                                         '+
			'				<div class="input-group-prepend">                                         '+
			'					<button class="btn btn-outline-secondary shoppingCarDivSub"   onclick="subBut(\''+obj.shopid+'\')"               '+
			'						type="button">-</button>                                          '+
			'				</div>                                                                    '+
			'				<input class="form-control shoppingCarDivNumberInput" type="text" id="orderNumber'+obj.shopid+'"                  '+
			'					name="orderNumber" value="'+obj.ordernumber+'" style="text-align: center;"              '+
			'					onkeyup="return ValidateNumber($(this),value)" onblur="changeNumber($(this),\''+obj.shopid+'\')">                       '+
			'				<div class="input-group-append">                                          '+
			'					<button class="btn btn-outline-secondary shoppingCarDivAdd"   onclick="addBut(\''+obj.shopid+'\')"                '+
			'						type="button">+</button>                                          '+
			'				</div>                                                                    '+
			'			</div>                                                                        '+
			'		</div>                                                                            '+
			'		<div class="col-xl-2 text-xl-center mar-bottom">                                  '+
			'			<div class="row align-items-center">                                          '+
			'				<div class="col-12 shopcar-item-bg show-prod-less">                       '+
			'					<font class="show-prod-less"><c:choose><c:when test="${sessionScope.local == 'en'}">subtotal</c:when><c:otherwise>小計</c:otherwise></c:choose></font>                              '+
			'				</div>                                                                    '+
			'				<div class="col-12  text-right">                                          '+
			'					<c:choose><c:when test="${sessionScope.local == 'en'}">NT $</c:when><c:otherwise>新台幣</c:otherwise></c:choose><font class="shopping-car-price">'+obj.totalPrice+'</font><c:choose><c:when test="${sessionScope.local == 'en'}">.</c:when><c:otherwise>元</c:otherwise></c:choose>                   '+
			'				</div>                                                                    '+
			(obj.DISPs!=''?'<div class="col-12  text-right">                                          '+
			'					<font class="shopping-car-dis"><c:choose><c:when test="${sessionScope.local == 'en'}">Including discounts</c:when><c:otherwise>含優惠折扣</c:otherwise></c:choose>'+obj.DISPs+'%</font>           '+
			'				</div>                                                                    '
			:'')+
			'			</div>                                                                        '+
            '                                                                                         '+
			'		</div>                                                                            '+
			'		<div                                                                              '+
			'			class="col-xl-2 text-center shopping-car-name hide-prod-title">               '+
			'			<font class="shoppingCarDivRemove" onclick="removeBut(\''+obj.shopid+'\')"><i class="far fa-trash-alt"></i></font>                                 '+
			'		</div>                                                                            '+
			'	</div>                                                                                '+
			'	<div class="row">                                                                     '+
			'		<div class="col-12">                                                              '+
			'			<hr />                                                                        '+
			'		</div>                                                                            '+
			'	</div>                                                                                ';
			
			return itemHtml;
		}
		
		function subBut(shopid){
			var orderNumber = parseInt($('#orderNumber'+shopid).val());
			if((orderNumber-1)<1){
				return;
			}
			orderNumber--;
			
			$('#shopCarFromId').val(shopid);
			$('#shopCarFromActionCode').val('sub');
			$('#shopCarFromOrderNumber').val(orderNumber);
			saveShopCar();
		}
		
		function addBut(shopid){
			var orderNumber = parseInt($('#orderNumber'+shopid).val());
			
			orderNumber++;
			$('#shopCarFromId').val(shopid);
			$('#shopCarFromActionCode').val('add');
			$('#shopCarFromOrderNumber').val(orderNumber);
			saveShopCar();
		}
		
		function changeNumber(e, shopid){
			$('#shopCarFromId').val(shopid);
			$('#shopCarFromActionCode').val('chg');
			$('#shopCarFromOrderNumber').val($(e).val());
			saveShopCar();
		}
		
		function saveShopCar(){
			$.ajax({
				type : "POST",
				url : '<c:url value = "/home/shoppingCar/ajaxUpdateShoppingCar"/>',
				data : $("#shopCarFrom").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {
						
						getShoppingCar();
						
					}
					
				}
			});
		}
		
		function removeBut(shopid){
			$('#shopCarFromId').val(shopid);
			$('#shopCarFromActionCode').val('rmv');
			$.ajax({
				type : "POST",
				url : '<c:url value = "/home/shoppingCar/ajaxRemoveShoppingCar"/>',
				data : $("#shopCarFrom").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {
						
						updateCarInNumberSub();
						getShoppingCar();
					}
					
				}
			});
		}
	</script>
</body>

</html>