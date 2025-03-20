<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/common-util.tld" prefix="pi"%>

<!DOCTYPE HTML>
<html lang="zxx" class="no-js">
<head>
<!-- Mobile Specific Meta -->
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Favicon-->
<link rel="shortcut icon" href="img/elements/fav.png">
<!-- Author Meta -->
<meta name="author" content="CodePixar">
<!-- Meta Description -->
<meta name="description" content="">
<!-- Meta Keyword -->
<meta name="keywords" content="">
<!-- meta character set -->
<meta charset="UTF-8">
<!-- Site Title -->
<title>台灣綠十字股份有限公司</title>

<link
	href="https://fonts.googleapis.com/css?family=Poppins:100,200,400,300,500,600,700"
	rel="stylesheet">
<!--
			CSS
			============================================= -->
<link rel="stylesheet"
	href='<c:url value="/css/homePage/linearicons.css"/>?${sessionScope.randomId}' />
<link rel="stylesheet"
	href='<c:url value="/css/homePage/owl.carousel.css"/>?${sessionScope.randomId}' />
<link rel="stylesheet"
	href='<c:url value="/css/homePage/font-awesome.min.css"/>?${sessionScope.randomId}' />
<link rel="stylesheet"
	href='<c:url value="/css/homePage/nice-select.css"/>?${sessionScope.randomId}' />
<link rel="stylesheet"
	href='<c:url value="/css/homePage/magnific-popup.css"/>?${sessionScope.randomId}' />
<link rel="stylesheet"
	href='<c:url value="/css/homePage/bootstrap.css"/>?${sessionScope.randomId}' />
<link rel="stylesheet"
	href='<c:url value="/css/homePage/main.css"/>?${sessionScope.randomId}' />

<script type="text/javascript"
	src="<c:url value='/js/homePage/vendor/jquery-2.2.4.min.js' />?${sessionScope.randomId}"></script>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"
	integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4"
	crossorigin="anonymous"></script>

<script type="text/javascript"
	src="<c:url value='/js/homePage/vendor/bootstrap.min.js' />?${sessionScope.randomId}"></script>
<script type="text/javascript"
	src="<c:url value='/js/homePage/jquery.ajaxchimp.min.js' />?${sessionScope.randomId}"></script>
<script type="text/javascript"
	src="<c:url value='/js/homePage/jquery.sticky.js' />?${sessionScope.randomId}"></script>
<script type="text/javascript"
	src="<c:url value='/js/homePage/owl.carousel.min.js' />?${sessionScope.randomId}"></script>
<script type="text/javascript"
	src="<c:url value='/js/homePage/jquery.nice-select.min.js' />?${sessionScope.randomId}"></script>
<script type="text/javascript"
	src="<c:url value='/js/homePage/jquery.magnific-popup.min.js' />?${sessionScope.randomId}"></script>
<script type="text/javascript"
	src="<c:url value='/js/homePage/main.js' />?${sessionScope.randomId}"></script>
<script type="text/javascript"
	src="<c:url value='/js/homePage/jquery.counterup.min.js' />?${sessionScope.randomId}"></script>


<script type="text/javascript"
	src="<c:url value='/js/homePage/jquery.magnific-popup.min.js' />?${sessionScope.randomId}"></script>
<script type="text/javascript"
	src="<c:url value='/js/homePage/slick.js' />?${sessionScope.randomId}"></script>
<script type="text/javascript"
	src="<c:url value='/js/homePage/waypoints.min.js' />?${sessionScope.randomId}"></script>






<c:import url="/pages/commons/common.jsp" />
</head>
<body>
	<!-- Start banner Area -->
	<section class="generic-banner relative">
		<!-- Start Header Area -->
		<header class="default-header" >
			<nav class="navbar navbar-expand-lg  navbar-light">
				<div class="container">
					<a class="navbar-brand" href="<c:url value = "/"/>"> 
						<img src="<c:url value='/img/logo.png' />" alt="" style="height: 35px;position: relative;top: -5px;">
					</a>
					<button class="navbar-toggler" type="button" data-toggle="collapse"
						data-target="#navbarSupportedContent"
						aria-controls="navbarSupportedContent" aria-expanded="false"
						aria-label="Toggle navigation">
						<span class="text-white lnr lnr-menu"></span>
					</button>
   
					<div
						class="collapse navbar-collapse justify-content-end align-items-center"
						id="navbarSupportedContent">
						<ul class="navbar-nav">
							<li><a href="<c:url value = "/"/>"><c:choose><c:when test="${sessionScope.local == 'en'}">Home</c:when><c:otherwise>首頁</c:otherwise></c:choose></a></li>
							<li><a href="<c:url value = "/home/aboutus/list"/>"><c:choose><c:when test="${sessionScope.local == 'en'}">About Us</c:when><c:otherwise>關於我們</c:otherwise></c:choose></a></li>
							<li><a href="<c:url value = "/home/store/list"/>"><c:choose><c:when test="${sessionScope.local == 'en'}">Mall</c:when><c:otherwise>商城</c:otherwise></c:choose></a></li>
							<c:choose>
								<c:when test="${sessionScope.isLogin == 'N'}">
								</c:when>
								<c:otherwise>
										<li><a href="<c:url value = "/home/shoppingCar/inCar"/>"><i class="fas fa-cart-arrow-down"></i>&nbsp;<c:choose><c:when test="${sessionScope.local == 'en'}">shopping cart</c:when><c:otherwise>購物車</c:otherwise></c:choose>&nbsp;(&nbsp;<font id="shoppingCarInNumber">${sessionScope.userProf.inShoppingCarNum}</font>&nbsp;)</a></li>
								</c:otherwise>	
							</c:choose>
							
							
							
							
							<li class="dropdown"><a class="dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown"><c:choose><c:when test="${sessionScope.local == 'en'}">Member Centre</c:when><c:otherwise>會員中心</c:otherwise></c:choose> </a>
								<div class="dropdown-menu">
									<c:choose>
										<c:when test="${sessionScope.isLogin == 'N'}">
											<a class="dropdown-item" href="<c:url value = "/admin/login"/>"><c:choose><c:when test="${sessionScope.local == 'en'}">Login</c:when><c:otherwise>登入</c:otherwise></c:choose></a>
										</c:when>
										<c:otherwise>
											<a class="dropdown-item" href="<c:url value = "/admin/home"/>"><c:choose><c:when test="${sessionScope.local == 'en'}">Member Centre</c:when><c:otherwise>會員中心</c:otherwise></c:choose></a>
											<a class="dropdown-item" href="<c:url value = "/home/loginOut"/>"><c:choose><c:when test="${sessionScope.local == 'en'}">Logout</c:when><c:otherwise>登出</c:otherwise></c:choose></a>
										</c:otherwise>
										
									</c:choose>
								</div>
							</li>
							<li class="dropdown"><a class="dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown"> <c:choose><c:when test="${sessionScope.local == 'en'}">Language</c:when><c:otherwise>語言</c:otherwise></c:choose>：</a>
								<div class="dropdown-menu">
								<a class="dropdown-item" href="#" onclick="changeLanguage('tw');">中文</a>
								<a class="dropdown-item" href="#" onclick="changeLanguage('en');">English</a>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</nav>
		</header>
		<!-- End Header Area -->

	</section>

	<c:choose>
		<c:when test="${sessionScope.isHomeBannerShow == 'Y'}">
			<section class="default-banner active-blog-slider">
				<div class="item-slider relative"
					style="background: url(<c:url value='/img/slider1.jpg' />);background-size: cover;">
					<div class="overlay" style="background: rgba(0, 0, 0, .3)"></div>
					<div class="container">
						<div
							class="row fullscreen justify-content-center align-items-center">
							<div class="col-md-10 col-12">
								<div class="banner-content text-center">
									<h4 class="text-white mb-20 text-uppercase">Discover the
										Colorful World</h4>
									<h1 class="text-uppercase text-white">New Adventure</h1>
									<p class="text-white">
										Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed
										do eiusmod temp <br> or incididunt ut labore et dolore
										magna aliqua. Ut enim ad minim.
									</p>
									<a href="#" class="text-uppercase header-btn">Discover Now</a>
								</div>
							</div>

						</div>
					</div>
				</div>
				<div class="item-slider relative"
					style="background: url(<c:url value='/img/slider2.jpg' />);background-size: cover;">
					<div class="overlay" style="background: rgba(0, 0, 0, .3)"></div>
					<div class="container">
						<div
							class="row fullscreen justify-content-center align-items-center">
							<div class="col-md-10 col-12">
								<div class="banner-content text-center">
									<h4 class="text-white mb-20 text-uppercase">Discover the
										Colorful World</h4>
									<h1 class="text-uppercase text-white">New Trip</h1>
									<p class="text-white">
										Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed
										do eiusmod temp <br> or incididunt ut labore et dolore
										magna aliqua. Ut enim ad minim.
									</p>
									<a href="#" class="text-uppercase header-btn">Discover Now</a>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="item-slider relative"
					style="background: url(<c:url value='/img/slider3.jpg' />);background-size: cover;">
					<div class="overlay" style="background: rgba(0, 0, 0, .3)"></div>
					<div class="container">
						<div
							class="row fullscreen justify-content-center align-items-center">
							<div class="col-md-10 col-12">
								<div class="banner-content text-center">
									<h4 class="text-white mb-20 text-uppercase">Discover the
										Colorful World</h4>
									<h1 class="text-uppercase text-white">New Experience</h1>
									<p class="text-white">
										Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed
										do eiusmod temp <br> or incididunt ut labore et dolore
										magna aliqua. Ut enim ad minim.
									</p>
									<a href="#" class="text-uppercase header-btn">Discover Now</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
		</c:when>
		<c:otherwise>
			
		</c:otherwise>
	</c:choose>

	<!-- End banner Area -->

	<!-- About Generic Start -->
	<div class="main-wrapper">
		<decorator:body />

		<c:choose>
			<c:when test="${sessionScope.isHomefooterShow == 'Y'}">
				<!-- start footer Area -->
		<footer class="footer-area section-gap">
			<div class="container">
				<div class="row">
					<div class="col-lg-5 col-md-6 col-sm-6">
						<div class="single-footer-widget">
							<h6>關於我們</h6>
							<!--p>Lorem ipsum dolor sit amet, consectetur adipisicing elit,
								sed do eiusmod tempor incididunt ut labore dolore magna aliqua.
							</p>
							< Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
							<p class="footer-text">
								Copyright &copy;
								<script>
									document.write(new Date().getFullYear());
								</script>
								All rights reserved by <a
									href="http://www.ezlifetech.com.tw" target="_blank">ezlifetech</a> 
								<br>This template is made with <i class="fa fa-heart-o" aria-hidden="true"></i> by <a	href="https://colorlib.com" target="_blank">Colorlib</a>
							</p>
							<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
						</div>
					</div>
					<div class="col-lg-5  col-md-6 col-sm-6">
						<div class="single-footer-widget">
							<h6>Newsletter</h6>
							<p>Stay update with our latest</p>
							<div class="" id="mc_embed_signup">

								<form target="_blank" novalidate="true"
									action="https://spondonit.us12.list-manage.com/subscribe/post?u=1462626880ade1ac87bd9c93a&amp;id=92a4423d01"
									method="get" class="form-inline">

									<div class="d-flex flex-row">

										<input class="form-control" name="EMAIL"
											placeholder="Enter Email" onfocus="this.placeholder = ''"
											onblur="this.placeholder = 'Enter Email '" required=""
											type="email">


										<button class="click-btn btn btn-default">
											<i class="fa fa-long-arrow-right" aria-hidden="true"></i>
										</button>
										<div style="position: absolute; left: -5000px;">
											<input name="b_36c4fd991d266f23781ded980_aefe40901a"
												tabindex="-1" value="" type="text">
										</div>

										<!-- <div class="col-lg-4 col-md-4">
												<button class="bb-btn btn"><span class="lnr lnr-arrow-right"></span></button>
											</div>  -->
									</div>
									<div class="info"></div>
								</form>
							</div>
						</div>
					</div>
					<div class="col-lg-2 col-md-6 col-sm-6 social-widget">
						<div class="single-footer-widget">
							<a href="https://www.facebook.com/ezlifetech.tw/"><h6>追蹤我們</h6></a> 
							<p>讓我們一起在社群網站上互動吧！</p>
							<!-- div class="footer-social d-flex align-items-center">
								<i class="fa fa-facebook"></i>
								<a href="#"><i class="fa fa-twitter"></i></a> 
								<a href="#"><i class="fa fa-dribbble"></i></a> 
								<a href="#"><i class="fa fa-behance"></i></a>
							</div -->
						</div>
					</div>
				</div>
			</div>
		</footer>
		<!-- End footer Area -->
			</c:when>
			<c:otherwise>
				<br />
			</c:otherwise>
		</c:choose>
		

	</div>
	<!--<script src="js/vendor/jquery-2.2.4.min.js"></script>-->


<script type="text/javascript">	
   
    $(document).ready(
    		function() {
    			
    		}
    );
    function changeLanguage(local){
    	$.ajax({
			type : "POST",
			url : '<c:url value = "/home/ajaxChangeLocal"/>',
			data :{
				
				local : local
				
				
				
			},// serializes the form's elements.
			success : function(result) {
				 location.reload();

			}
		});
    }
	
	</script>


</body>
</html>