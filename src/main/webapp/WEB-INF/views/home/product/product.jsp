<%@ page language="java" contentType="text/html;  charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/common-util.tld" prefix="pi"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Spring 4 MVC -HelloWorld</title>
<script type="text/javascript"
	src="<c:url value='/js/homePage/jquery.magnific-popup.min.js' />?${sessionScope.randomId}"></script>
<script type="text/javascript"
	src="<c:url value='/js/homePage/slick.js' />?${sessionScope.randomId}"></script>
<script type="text/javascript"
	src="<c:url value='/js/homePage/waypoints.min.js' />?${sessionScope.randomId}"></script>
</head>

<body>
	<!-- Page info section -->
	<section class="page-info-section set-bg" data-setbg="<c:url value = "../../img/green/page-info-bg/5.jpg"/>">
		<div class="container">
			<h2><c:choose><c:when test="${sessionScope.local == 'en'}">Product Information</c:when><c:otherwise>產品介紹</c:otherwise></c:choose></h2>
		</div>
	</section>
	<!-- Page info section end -->
	
	<section class="product-section spad">
		<div class="container-fluid">
			<div class="row">
				<div class="col-12">
					<br /> <br />
				</div>
			</div>
			<div class="row">
				<div class="col-xl-2 col-lg-2 col-md-1 col-sm-12 col-12"></div>
				<div class="col-xl-8 col-lg-8 col-md-10 col-sm-12 col-12">
					<div class="row">
						<div class="col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12">
							<div class="row">
								<div class="col-12 slide-img">
									<div id="carouselExampleIndicators" class="carousel slide"
										data-ride="carousel">
	
										<div class="carousel-inner">
	
											<c:choose>
												<c:when test="${havePict == 'N'}">
													<div class="carousel-item active">
														<img class="d-block w-100 slide-img"
															src='<c:url value = "/ajaxGetProductPic/nopict"/>'
															alt="First slide">
													</div>
												</c:when>
											</c:choose>
											<c:forEach items="${productDto.productPicDtoList}"
												var="current">
												<div class="carousel-item ${current.first ? 'active' : ''}">
													<img class="d-block w-100 slide-img"
														src='<c:url value = "/ajaxGetProductPic/${current.id}"/>'
														alt="First slide">
												</div>
											</c:forEach>
	
	
										</div>
										<a class="carousel-control-prev"
											href="#carouselExampleIndicators" role="button"
											data-slide="prev">
											<div class="carousel-bg-icon">
												<span class="carousel-control-prev-icon carousel-bg-in"
													aria-hidden="true"></span> <span class="sr-only">Previous</span>
											</div>
										</a> <a class="carousel-control-next"
											href="#carouselExampleIndicators" role="button"
											data-slide="next">
											<div class="carousel-bg-icon">
												<span class="carousel-control-next-icon carousel-bg-in"
													aria-hidden="true"></span> <span class="sr-only">Next</span>
											</div>
	
										</a>
									</div>
								</div>
								<div class="col-12 mar-bottom">
									<ol class="carousel-bg-ol">
										<c:choose>
											<c:when test="${havePict == 'N'}">
												<li data-target="#carouselExampleIndicators"
													data-slide-to="0" class="active"><img
													class="carousel-bg-li-img"
													src='<c:url value = "/ajaxGetProductPic/nopict"/>'></li>
											</c:when>
										</c:choose>
										<c:forEach items="${productDto.productPicDtoList}"
											var="current">
											<li data-target="#carouselExampleIndicators"
												data-slide-to="${current.picIndex}"
												${current.first ? 'class="active"' : ''}><img
												class="carousel-bg-li-img"
												src='<c:url value = "/ajaxGetProductPic/${current.id}"/>'></li>
										</c:forEach>
									</ol>
								</div>
							</div>
	
	
						</div>
						<div class="col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12">
							<div class="card" style="width: 100%; min-height: 360px;">
								<div class="row">
									<div class="col-12 mar-bottom">
										<div class="card-header">
											<font class="prodPage-name">${productDto.proName}</font>
										</div>
									</div>
									<div
										class="col-xl-4 col-lg-6 col-md-6 col-sm-5 col-5 mar-bottom"
										style="text-align: right;">
										<font class="prodPage-title"><c:choose><c:when test="${sessionScope.local == 'en'}">Specifications</c:when><c:otherwise>商品規格</c:otherwise></c:choose>：</font>
									</div>
									<div
										class="col-xl-8 col-lg-6 col-md-6 col-sm-7 col-7 mar-bottom">
										<font class="prodPage-content">${productDto.proSpec}</font>
									</div>
	
									<div
										class="col-xl-4 col-lg-6 col-md-6 col-sm-5 col-5 mar-bottom"
										style="text-align: right;">
										<font class="prodPage-title"><c:choose><c:when test="${sessionScope.local == 'en'}">Product code</c:when><c:otherwise>商品代號</c:otherwise></c:choose>：</font>
									</div>
									<div
										class="col-xl-8 col-lg-6 col-md-6 col-sm-7 col-7 mar-bottom">
										<font class="prodPage-content"> ${productDto.proCode}</font>
									</div>
									<div
										class="col-xl-4 col-lg-6 col-md-6 col-sm-5 col-5 mar-bottom"
										style="text-align: right;">
										<font class="prodPage-title"><c:choose><c:when test="${sessionScope.local == 'en'}">Categories</c:when><c:otherwise>商品分類</c:otherwise></c:choose>：</font>
									</div>
									<div
										class="col-xl-8 col-lg-6 col-md-6 col-sm-7 col-7 mar-bottom">
	
										<c:forEach items="${productDto.productClassDtoList}"
											var="current">
											<font>${current.className}&nbsp;</font>
										</c:forEach>
	
									</div>
									<!--<div
										class="col-xl-4 col-lg-6 col-md-6 col-sm-5 col-5 mar-bottom"
										style="text-align: right;">
										<font class="prodPage-title"><c:choose><c:when test="${sessionScope.local == 'en'}">Prilce</c:when><c:otherwise>商品售價</c:otherwise></c:choose>：</font>
									</div>
									<div
										class="col-xl-8 col-lg-6 col-md-6 col-sm-7 col-7 mar-bottom">
										<c:choose><c:when test="${sessionScope.local == 'en'}">NT $</c:when><c:otherwise>新台幣</c:otherwise></c:choose> <font class="prodPage-price">${productDto.proSellPrice}</font>
										<c:choose><c:when test="${sessionScope.local == 'en'}">.</c:when><c:otherwise>元</c:otherwise></c:choose>
									</div>-->
	
									<!-- <div
										class="col-xl-4 col-lg-6 col-md-6 col-sm-5 col-5 mar-bottom"
										style="text-align: right;">
										<font class="prodPage-title"><c:choose><c:when test="${sessionScope.local == 'en'}">Quantity</c:when><c:otherwise>購買數量</c:otherwise></c:choose>：</font>
									</div> 
									<div class="col-xl-8 col-lg-6 col-md-6 col-sm-7 col-7 mar-bottom">
	
										<div class="row">
											<div class="col-xl-6 col-lg-6 col-md-10 col-sm-10 col-10">
												<div class="input-group mb-3">
													<div class="input-group-prepend">
														<button class="btn btn-outline-secondary" id="subBut" type="button">-</button>
													</div>
													<input class="form-control" type="text" id="orderNumber" name="orderNumber" value="1"
														style="text-align: center;" onkeyup="return ValidateNumber($(this),value)">
													<div class="input-group-append">
														<button class="btn btn-outline-secondary" id="addBut" type="button">+</button>
													</div>
												</div>
											</div>
										</div>
									</div>-->
		
									<!-- <div class="col-xl-1 col-lg-1 col-md-1"></div>
									<div
										class="col-xl-5 col-lg-5 col-md-5 col-sm-12 col-12 mar-bottom "
										style="text-align: right;">
										<button type="button" id="addtoShoppingCarBtn" class="btn btn-warning"
											style="width: 100%;">
											<i class="fas fa-cart-plus"> <c:choose><c:when test="${sessionScope.local == 'en'}">add to Shopping Cart</c:when><c:otherwise>加入購物車</c:otherwise></c:choose></i>
										</button>
									</div>
	
									<div
										class="col-xl-5 col-lg-5 col-md-5 col-sm-12 col-12 mar-bottom ">
										<button type="button" id="buyNow" class="btn btn-danger"
											style="width: 100%;">
											<i class="fas fa-cart-plus"> <c:choose><c:when test="${sessionScope.local == 'en'}">Direct purchase</c:when><c:otherwise>直接購買</c:otherwise></c:choose></i>
										</button>
									</div>
									<div class="col-xl-1 col-lg-1 col-md-1"></div>-->
									
									<div class="col-12">
										<br/>
									</div>
									
									<div class="col-xl-1 col-lg-1 col-md-1"></div>
									<div
										class="col-xl-10 col-lg-10 col-md-10 col-sm-12 col-12 mar-bottom ">
										<button type="button" id="qaBut" class="btn btn-danger"
											style="width: 100%;">
											<i class="fas fa-cart-plus"> <c:choose><c:when test="${sessionScope.local == 'en'}">Product inquiry</c:when><c:otherwise>產品詢問單</c:otherwise></c:choose></i>
										</button>
									</div>
									<div class="col-xl-1 col-lg-1 col-md-1"></div>
								</div>
							</div>
	
						</div>
					</div>
					<div class="row">
						<div class="col-12"> 
							<hr/>  
						</div>
					</div>
					<div class="row"> 
						<div class="col-xl-6 col-lg-6 col-12">
							<div class="row"> 
								<div class="col-6"> 
									<button type="button" id="PIBut" class="btn btn-default"
											style="width: 100%;">
											<i class="fas fa-info-circle"> <c:choose><c:when test="${sessionScope.local == 'en'}">Product information</c:when><c:otherwise>產品資訊</c:otherwise></c:choose></i>
										</button>
								</div>
								<div class="col-6"> 
									<button type="button" id="PSBut" class="btn btn-default"
											style="width: 100%;">
											<i class="fas fa-file-contract"> <c:choose><c:when test="${sessionScope.local == 'en'}">Product specification</c:when><c:otherwise>產品參數</c:otherwise></c:choose></i>
									</button>
								</div>
							</div>
						</div>
					
						<div class="col-12">
							<div id="prodHtmlDiv" class = "prodHtml">
								<c:out value="${productDto.proHtml}" escapeXml="false"/> 
							</div>
							 
						</div>
						
						<div class="col-12">
							<div id="prodParamHtmlDiv" class = "prodParamHtml">
								<c:out value="${productDto.proParamHtml}" escapeXml="false"/> 
							</div>
						</div>
					</div>
				</div>
				<div class="col-xl-2 col-lg-2 col-md-1 col-sm-12 col-12"></div>
				<div class="col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12">
					<br /> <br /> <br /> <br />
				</div>
			</div>
			<div class="row">
				<div class="col-12"></div>
			</div>
		</div>
	
	</section>
	

	<form id='prodFrom'></form>


	<form id='shoppingCarFrom'>
		<input type="hidden" id="shoppingCarId" name="id" value="">
		<c:choose>
			<c:when test="${sessionScope.isLogin == 'N'}">
				<input type="hidden" id="shoppingUserId" name="userId" value="">
			</c:when>
			<c:otherwise>
				<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
				<input type="hidden" id="shoppingUserId" name="userId" value="${sessionScope.userProf.id}">
			</c:otherwise>
		</c:choose>
		
		<input type="hidden" id="shoppingProNo" name="proNo" value="${productDto.id}">
		<input type="hidden" id="shoppingOrderNumber" name="orderNumber" value="1">
		
		
	</form>
	
	<div id="qAwindow">
		<div class="panel panel-bordered">
		<form id="qaForm">
		
			<input type="hidden" id="prodId" name="prodId" value="${productDto.id}">
			
			<header class="panel-heading">
				<h3 class="panel-title"><c:choose><c:when test="${sessionScope.local == 'en'}">Product inquiry</c:when><c:otherwise>商品詢問單</c:otherwise></c:choose></h3>
			</header>
			<div class='panel-body'>
				<table class="table table-condensed">
				
					<tr>
						<td align='center' valign="middle" style="border-top:1px solid #FFF;">
							<label for="questName"><c:choose><c:when test="${sessionScope.local == 'en'}">Product Name</c:when><c:otherwise>產品名稱</c:otherwise></c:choose></label>
						</td>
						<td style="border-top:1px solid #FFF;">
							<input type="text"  class='input'  value="${productDto.proName}" style = "width:90%;" readonly> 
						</td>
					</tr>
				
					<tr>
						<td align='center' valign="middle" >
							<label for="questName"><c:choose><c:when test="${sessionScope.local == 'en'}">Name</c:when><c:otherwise>姓名</c:otherwise></c:choose></label>
						</td>
						<td >
							<input type="text" id="questName" class='input' name="questName" value="" style = "width:90%;"> 
						</td>
					</tr>
					<tr>
						<td align='center' valign="middle" style="border-top:1px solid #FFF;">
							<label for="questMail">E-mail</label>
						</td>
						<td style="border-top:1px solid #FFF;">
							<input type="text" id="questMail" class='input' name="questMail" value="" style = "width:90%;"> 
						</td>
					</tr>
					<tr>
						<td align='center' valign="middle" style="border-top:1px solid #FFF;">
							<label for="questTel"><c:choose><c:when test="${sessionScope.local == 'en'}">Contact number</c:when><c:otherwise>連絡電話</c:otherwise></c:choose></label>
						</td>
						<td style="border-top:1px solid #FFF;">
							<input type="text" id="questTel" class='input' name="questTel" value="" style = "width:90%;"> 
						</td>
					</tr>
					<tr>  
						<td align='center' valign="middle" style="border-top:1px solid #FFF;">
							<label for="questTitle"><c:choose><c:when test="${sessionScope.local == 'en'}">Subject</c:when><c:otherwise>主旨</c:otherwise></c:choose></label>
						</td>
						<td style="border-top:1px solid #FFF;">
							<input type="text" id="questTitle" class='input' name="questTitle" value="" style = "width:90%;"> 
						</td>
					</tr>
					<tr>  
						<td align='center' valign="middle" style="border-top:1px solid #FFF;">
							<label for="questContent"><c:choose><c:when test="${sessionScope.local == 'en'}">Message</c:when><c:otherwise>內容</c:otherwise></c:choose></label>
						</td>
						<td style="border-top:1px solid #FFF;">
							<textarea  id="questContent" class='input' name="questContent" placeholder="" style = "width:90%;" rows="10"></textarea>
						</td>
					</tr>
					
					<tr>  
						<td>
						</td>
						<td align='center' valign="middle"  >
							<button  type="button" class="site-btn" id="sendQuestBut"><c:choose><c:when test="${sessionScope.local == 'en'}">Submit!</c:when><c:otherwise>送出</c:otherwise></c:choose></button>
						</td>
					</tr>
				</table>
				
			</div>
		</form>
		
		</div>
	
	</div>
	
	
	<script>
		$(document).ready(function() {
			
			$('#prodParamHtmlDiv').hide();
			
			
			$('#PIBut').click(function(e) {
				$('#prodHtmlDiv').show();
				$('#prodParamHtmlDiv').hide();
			});
			
			$('#PSBut').click(function(e) {
				$('#prodHtmlDiv').hide();
				$('#prodParamHtmlDiv').show();
			});
			
			$('#subBut').click(function(e) {
				subNum();
			});
			
			$('#addBut').click(function(e) {
				addNum();
			});
			
			$('#orderNumber').blur(function() {
				 if(($('#orderNumber').val().trim())==''){
					 $('#orderNumber').val('1');
				 }
			});
			
			$('#addtoShoppingCarBtn').click(function(e) {
				addToShopCar();
			});
			
			$('#buyNow').click(function(e) {
				buyNow();
			});
			
			
			
			
			
			
			global.qAwindow = $("#qAwindow").kendoWindow({
                width: "60%",
                title: "<c:choose><c:when test="${sessionScope.local == 'en'}">Product inquiry</c:when><c:otherwise>商品詢問單</c:otherwise></c:choose>",
                visible: false,
                actions: [
                    "Close"
                ],
                close: onClose,
                open: adjustSize
            }).data("kendoWindow");
			
			$('#qaBut').click(function(e) {
				global.qAwindow.center().open();
			});
						
			$('#sendQuestBut').click(function(e) {
				ajaxSendMessage();
			});
			
		});  
		
		function adjustSize() {
            // For small screens, maximize the window when it is shown.
            // You can also make the check again in $(window).resize if you want to
            // but you will have to change the way to reference the widget and then
            // to use $("#theWindow").data("kendoWindow").
            // Alternatively, you may want to .center() the window.

            if ($(window).width() < 800 || $(window).height() < 600) {
                this.maximize();
            }else{
            	$("#qAwindow").data("kendoWindow").setOptions({width : "60%"});
            }
        }
		
		function ajaxSendMessage(){
			$.ajax({
				type : "POST",
				url : '<c:url value = "/home/product/ajaxSendQuest"/>',
				data : $("#qaForm").serialize(),// serializes the form's elements.
				success : function(result) {
					console.log(result);
					if (checkAjaxResp(result)) {
						$('#questName').val('');
						$('#questMail').val('');
						$('#questTitle').val('');
						$('#questContent').val('');
						$('#questTel').val('');
						global.qAwindow.center().close();
						
					} 
				}
			});
		}
		
		function subNum(){
			var orderNumber = parseInt($('#orderNumber').val());
			if((orderNumber-1)<1){
				return;
			}
			orderNumber--;
			$('#orderNumber').val(''+orderNumber);
		}
		
		function addNum(){
			var orderNumber = parseInt($('#orderNumber').val());
			
			orderNumber++;
			$('#orderNumber').val(''+orderNumber);
			
		}
		
		
		function addToShopCar(){
			$('#shoppingOrderNumber').val($('#orderNumber').val());
			addToShoppingCar($('#shoppingCarFrom'));
		}
		
		function buyNow(){
			
			if(global.isLogin=='N'){
				
				swal({ 
					  title: "<c:choose><c:when test="${sessionScope.local == 'en'}">Please login first</c:when><c:otherwise>請先登入</c:otherwise></c:choose>", 
					  text: "<c:choose><c:when test="${sessionScope.local == 'en'}">Please log in to the system before purchasing the product.</c:when><c:otherwise>購買商品前，請先登入系統</c:otherwise></c:choose>",
					  type: 'warning',
					  confirmButtonText:'<c:choose><c:when test="${sessionScope.local == 'en'}">Got it</c:when><c:otherwise>知道了</c:otherwise></c:choose>！'
					});
				
				return;
			}
			$('#shoppingOrderNumber').val($('#orderNumber').val());
			$('#shoppingCarFrom').attr('action', '<c:url value = "/home/store/directPurchase"/>');
			$('#shoppingCarFrom').attr('method', 'post');
			$('#shoppingCarFrom').submit();
		}
		
		
		function onClose() {
            
        }
	</script>


</body>

</html>