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
						<font>直購項目</font>
					</div>
					<div class="col-1">
						<i class="fas fa-angle-right"></i>
					</div>
					<div id="step2" class="col-3  step-after">
						<font>訂單確認</font>
					</div>
					<div class="col-1">
						<i class="fas fa-angle-right"></i>
					</div>
					<div id="step3" class="col-3  step-after">
						<font>完成訂單</font>
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
										<font>商品名稱/資訊</font>
									</div>
									<div class="col-xl-2 text-center">
										<font class="hide-prod-title">單價</font>
									</div>
									<div class="col-xl-2 text-center">
										<font class="hide-prod-title">數量</font>
									</div>
									<div class="col-xl-2 text-center">
										<font class="hide-prod-title">小計</font>
									</div>
									<div class="col-xl-2 text-center">
										
									</div>
								</div>
							</div>
							<div class="card-body" id="carItem">

								<div class="row align-items-center">
									<div class="col-xl-4 mar-bottom">
										<div class="row align-items-center">
											<div class="col-4 text-center">
												<a href="/WebSiteSample/home/store/product/${prodNo}"><img
													class="shopping-car-img"
													src="/WebSiteSample/ajaxGetProductPic/${prodPict}"></a>
											</div>
											<div class="col-xl-8 col-6 ">
												<div class="row">
													<div class="col-12 shopping-car-name">
														&nbsp;&nbsp;<font>${prodName}</font>
													</div>
													<div class="col-12 shopping-car-content">
														&nbsp;&nbsp;<font>${prodSpec}</font>
													</div>
												</div>
											</div>
											<div class="col-2 show-prod-less">
												<font class="shoppingCarDivRemove"></font>
											</div>
										</div>
									</div>
									<div class="col-xl-2 col-6 text-center mar-bottom">
										<div class="row align-items-center">
											<div class="col-12 show-prod-less shopcar-item-bg">
												<font class="show-prod-less">每件</font>
											</div>
											<div class="col-xl-12 col-9  align-items-center">
												新台幣<font class="shopping-car-price">${prodPrice}</font>元
											</div>
											<div class="col-3 show-prod-less align-items-center">
												<div class="row align-items-center">
													<div class="col-12 shopcar-fix-height"></div>
												</div>
											</div>
										</div>
									</div>
									<div class="col-xl-2 col-6 text-center mar-bottom">
										<div class="row align-items-center">
											<div class="col-12 show-prod-less shopcar-item-bg">
												<font class="show-prod-less">數量</font>
											</div>
										</div>
										<div class="input-group align-items-center shopcar-fix-height">

											<div class="input-group-prepend">
												<button class="btn btn-outline-secondary shoppingCarDivSub"
													onclick="subBut()" type="button">-</button>
											</div>
											<input class="form-control shoppingCarDivNumberInput"
												type="text" id="orderNumber" name="orderNumber"
												value="${prodOrderNumber}" style="text-align: center;"
												onkeyup="return ValidateNumber($(this),value)"
												onblur="changeNumber($(this))">
											<div class="input-group-append">
												<button class="btn btn-outline-secondary shoppingCarDivAdd"
													onclick="addBut()" type="button">+</button>
											</div>
										</div>
									</div>
									<div class="col-xl-2 text-xl-center mar-bottom">
										<div class="row align-items-center">
											<div class="col-12 shopcar-item-bg show-prod-less">
												<font class="show-prod-less">小計</font>
											</div>
											<div class="col-12  text-right">
												新台幣<font class="shopping-car-price" id='prodTotalPrice'>${prodTotalPrice}</font>元
											</div>
											<c:if test="${not empty DISPs}">
											<div class="col-12 text-right">
												<font class="shopping-car-dis">含優惠折扣${DISPs}%</font>
											</div>
											</c:if>
										</div>

									</div>
									<div
										class="col-xl-2 text-center shopping-car-name hide-prod-title">
										<font class="shoppingCarDivRemove"></font>
									</div>
								</div>
								<div class="row">
									<div class="col-12">
										<hr />
									</div>
								</div>


							</div>
							<div class="card-header shopping-car-card-header" id='giftTopDiv'>
							<c:if test="${isZero eq 'N'}">
							
								<div class="row">
									<div class="col-xl-4 col-7 text-left text-xl-center">
										<font>贈品名稱/資訊</font>
									</div>
									<div class="col-xl-5 col-5 text-center">
										<font class="hide-prod-title">贈品說明</font>
									</div>
									<div class="col-xl-3 text-center">
										<font class="hide-prod-title">數量</font>
									</div>
									
								</div>
							
							</c:if>
							</div>  
							<div class="card-body" id="giftListDiv">
								<c:forEach items="${giftList}" var="data">
									<div class="row align-items-center">
										<div class="col-xl-4 col-12">
											<div class="row align-items-center">
												<div class="col-4 text-center">
													<a
														href="/WebSiteSample/home/store/product/${data.giftProNo}"><img
														class="shopping-car-img"
														src="/WebSiteSample/ajaxGetProductPic/${data.giftpict}"></a>
												</div>
												<div class="col-xl-8 col-7 ">
													<div class="row">
														<div class="col-12 shopping-car-name align-items-center">
															<font>${data.giftName}</font>
														</div>
														<div
															class="col-12 shopping-car-content align-items-center">
															<font>${data.giftSpec}</font>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="col-xl-5 text-center col-12">
											<div class="row align-items-center">
												<div class="col-12 show-prod-less shopcar-item-bg">
													<font class="show-prod-less">贈品說明</font>
												</div>
												<div class="col-xl-12 col-12  align-items-center">
													${data.giftContent}</div>
											</div>

										</div>
										<div class="col-xl-3 text-center col-12">
											<div class="row align-items-center">
												<div class="col-12 show-prod-less shopcar-item-bg">
													<font class="show-prod-less">數量</font>
												</div>
												<div class="col-xl-12 col-12  align-items-center">
													${data.giftNumber}</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-12">
											<hr />
										</div>
									</div>
								</c:forEach>
							</div>
							
							<div class="card-body" id="orderInfo">
								<form id="orderFrom">
									<input type="hidden" id="orderFromProNo" name="proNo" value="${prodNo}">
									<input type="hidden" id="orderFromOrderNumber" name="orderNumber" value="${prodOrderNumber}">
									<div class="row">
										<div class="col-xl-12 text-center">
											<font>訂單資訊</font>
										</div>
										<div class="col-xl-12 text-center">
											<hr />
										</div>
									</div>
									<div class="row align-items-center mar-bottom">
										<div class="col-xl-1 text-xl-right">
											<font>收件人</font>
										</div>
										<div class="col-xl-5 text-center">
											<input class="form-control" type="text" name="recipient"
												value="">
										</div>
										<div class="col-sm-6"></div>
										<div class="col-sm-1"></div>
										<div class="col-sm-9">
											<font class='errText form-text' id='err_recipient'
												color='red'></font>
										</div>
									</div>
									<div class="row align-items-center mar-bottom">
										<div class="col-xl-1 text-xl-right">
											<font>寄件地址</font>
										</div>
										<div class="col-xl-9 text-center">
											<input class="form-control" type="text" name="sendAddress"
												value="">
										</div>
										<div class="col-sm-2"></div>
										<div class="col-sm-1"></div>
										<div class="col-sm-9">
											<font class='errText form-text' id='err_sendAddress'
												color='red'></font>
										</div>
									</div>
									<div class="row align-items-center mar-bottom">
										<div class="col-xl-1 text-xl-right">
											<font>備註</font>
										</div>
										<div class="col-xl-9 text-center">
											<input class="form-control" type="text" name="memo" value="">
										</div>
										<div class="col-sm-2"></div>
										<div class="col-sm-1"></div>
										<div class="col-sm-9">
											<font class='errText form-text' id='err_memo' color='red'></font>
										</div>
									</div>
									<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
								</form>
							</div>
							<div class="card-footer shopping-car-card-footer">
								<div class="row align-items-center">
									<div
										class='col-xl-8 col-3 text-right shopping-car-name mar-bottom'>
										<font>總計</font>
									</div>
									<div class='col-xl-2 col-9 text-right mar-bottom'>
										新台幣<font id='allPrice' class='shopping-car-price'>${allPrice}</font>元
									</div>
									<div class="col-xl-2 col-12 mar-bottom">
										<button type="button" id="createOrder" class="btn btn-success"
											style="width: 100%;">
											<i class="fas fa-check-circle"> 成立訂單</i>
										</button>
									</div>
								</div>
							</div>

						</div>
						<br />
						<div id="step3Div" class="card">
							<div class="card-body">
								<div class="row align-items-center">
									<div class="col-12 text-center">
										<font>訂單成立成功！</font>
									</div>
									<div class="col-12 text-center">
										<font>您可以至會員中心『訂單管理』功能查看訂單詳情。</font>
									</div>
								</div>
							</div>
						</div>
						<br /> <br />
					</div>
				</div>
			</div>
			<div class="col-xl-1 col-lg-1 col-md-1 col-sm-12 col-12"></div>
		</div>

	</div>

	<form id ="directFrom">
		<input type="hidden" id="directProNo" name="proNo" value="${prodNo}">
		<input type="hidden" id="directOrderNumber" name="orderNumber" value="${prodOrderNumber}">
		<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
	</form>

	<script>
		$(document).ready(function() {
			$('#orderInfo,#step3Div').hide();
			global.nowStep = '1';

			$('#createOrder').click(function(e) {
				nextStep();
			});

		});

		function nextStep() {
			if (global.nowStep == '1') {
				goToStep2();
			} else if (global.nowStep == '2') {
				goToStep3();
			}
		}

		function goToStep2() {
			$('#step1').removeClass("step-now");
			$('#step1').addClass("step-after");

			$('#step2').removeClass("step-after");
			$('#step2').addClass("step-now");

			$('.shoppingCarDivSub').hide();
			$('.shoppingCarDivAdd').hide();

			$('.shoppingCarDivNumberInput').attr('disabled', 'disabled');
			$('.shoppingCarDivRemove').hide();

			$('#createOrder').html('<i class="fas fa-check-circle"> 確定訂單</i> ');
			$('#createOrder').removeClass("btn-success");
			$('#createOrder').addClass("btn-danger");

			$('#orderInfo').show();

			global.nowStep = '2';
		}

		function goToStep3() {

			createOrderItem();

		}

		function createOrderItem() {
			$
					.ajax({
						type : "POST",
						url : '<c:url value = "/home/store/ajaxCreateOrderItem"/>',
						data : $("#orderFrom").serialize(),// serializes the form's elements.
						success : function(result) {
							if (checkAjaxResp(result)) {
								console.log(result);

								$('#step2').removeClass("step-now");
								$('#step2').addClass("step-after");

								$('#step3').removeClass("step-after");
								$('#step3').addClass("step-now");

								$('#shoppingCarDiv').hide();
								global.nowStep = '3';

								$('#step3Div').show();
								setCarInNumber('0');
							}

						}
					});
		}

		function changeNumber(e) {
			if (($(e).val().trim()) != '') {
				$('#directOrderNumber').val($(e).val());
				ajaxGetPrice();
			} else {
				$('#orderNumber').val('${prodOrderNumber}');
				$('#directOrderNumber').val('${prodOrderNumber}');
				ajaxGetPrice();
			}

		}

		function subBut() {
			var orderNumber = parseInt($('#orderNumber').val());
			if ((orderNumber - 1) < 1) {
				return;
			}
			orderNumber--;

			$('#orderNumber').val(orderNumber);
			$('#directOrderNumber').val(orderNumber);
			ajaxGetPrice();
		}

		function addBut() {
			var orderNumber = parseInt($('#orderNumber').val());

			orderNumber++;
			$('#orderNumber').val(orderNumber);
			$('#directOrderNumber').val(orderNumber);
			ajaxGetPrice();
		}

		function ajaxGetPrice() {

			$('#orderFromOrderNumber').val($('#directOrderNumber').val());
			$.ajax({
				type : "POST",
				url : '<c:url value = "/home/store/ajaxGetProductPrice"/>',
				data : $("#directFrom").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {
						console.log(result);
						$('#allPrice').text(result.data.allPrice);
						$('#prodTotalPrice').text(result.data.prodTotalPrice);

						var giftHtmlCode = '';
						$.each(result.data.giftList, function(i, obj) {

							giftHtmlCode = giftHtmlCode + giftItemHtml(obj);
						});
						if (giftHtmlCode != '') {
							$('#giftListDiv').html(giftHtmlCode);
							$('#giftTopDiv').show();
						} else {
							$('#giftListDiv').html(giftHtmlCode);
							$('#giftTopDiv').hide();
						}

					}

				}
			});
		}

		function giftItemHtml(obj) {
			var itemHtml = '	<div class="row align-items-center">                                                                                                                         '
					+ '		<div class="col-xl-4 col-12">                                                                                                                                   '
					+ '			<div class="row align-items-center">                                                                                                                 '
					+ '				<div class="col-4 text-center">                                                                                                                  '
					+ '					<a href="/WebSiteSample/home/store/product/'+obj.giftProNo+'"><img class="shopping-car-img" src="/WebSiteSample/ajaxGetProductPic/'+obj.giftpict+'"></a>  '
					+ '				</div>                                                                                                                                           '
					+ '				<div class="col-xl-8 col-7 ">                                                                                                                    '
					+ '					<div class="row">                                                                                                                            '
					+ '						<div class="col-12 shopping-car-name align-items-center">                                                                                '
					+ '							<font>'
					+ obj.giftName
					+ '</font>                                                                                                               '
					+ '						</div>                                                                                                                                   '
					+ '						<div class="col-12 shopping-car-content align-items-center">                                                                             '
					+ '							<font>'
					+ obj.giftSpec
					+ '</font>                                                                                                                    '
					+ '						</div>                                                                                                                                   '
					+ '					</div>                                                                                                                                       '
					+ '				</div>                                                                                                                                           '
					+ '			</div>                                                                                                                                               '
					+ '		</div>                                                                                                                                                   '
					+ '		<div class="col-xl-5 text-center col-12">                                                                                                                 '
					+ '			<div class="row align-items-center">                                                                                                                 '
					+ '				<div class="col-12 show-prod-less shopcar-item-bg">                                                                                              '
					+ '					<font class="show-prod-less">贈品說明</font>                                                                                                 '
					+ '				</div>                                                                                                                                           '
					+ '				<div class="col-xl-12 col-12  align-items-center">                         					                                                     '
					+ '					'
					+ obj.giftContent
					+ '                   				                                                                                                 '
					+ '				</div>                                                                                                                                           '
					+ '			</div>                                                                                                                                               '
					+ '			                                                                                                                                                     '
					+ '		</div>                                                                                                                                                   '
					+ '		<div class="col-xl-3 text-center col-12">                                                                                                                 '
					+ '			<div class="row align-items-center">                                                                                                                 '
					+ '				<div class="col-12 show-prod-less shopcar-item-bg">                                                                                              '
					+ '					<font class="show-prod-less">數量</font>                                                                                                     '
					+ '				</div>                                                                                                                                           '
					+ '				<div class="col-xl-12 col-12  align-items-center">                         					                                                     '
					+ '					'
					+ obj.giftNumber
					+ '                   				                                                                                                         '
					+ '				</div>                                                                                                                                           '
					+ '			</div>                                                                                                                                               '
					+ '		                                                                                                                                                         '
					+ '		                                                                                                                                                         '
					+ '			                                                                                                                                                     '
					+ '		</div>                                                                                                                                                   '
					+ '	</div>                                                                                                                                                       '
					+ '	<div class="row">                                                                     '
					+ '		<div class="col-12">                                                              '
					+ '			<hr />                                                                        '
					+ '		</div>                                                                            '
					+ '	</div>                                                                                ';

			return itemHtml;
		}
	</script>


</body>

</html>