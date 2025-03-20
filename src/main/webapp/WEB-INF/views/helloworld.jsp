<%@ page language="java" contentType="text/html;  charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/common-util.tld" prefix="pi"%>
<!DOCTYPE html>
<html lang="zxx">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Spring 4 MVC -HelloWorld</title>

</head>

<body>
<!-- Hero section -->
	<section class="hero-section pc">
		<div class="hero-slider owl-carousel">
			<!-- item -->
			<div class="hs-item set-bg text-white" data-setbg="img/green/banner-1.jpg">
				<div class="overlay"></div>
				<div class="container">
					<div class="row">
						<div class="col-xl-7">
							<h2><c:choose><c:when test="${sessionScope.local == 'en'}">Latest medical technology<br/> to enhance quality of life</c:when><c:otherwise>綠十字醫療科技<br/>每一天生活寫意</c:otherwise></c:choose></h2>
							<p><c:choose><c:when test="${sessionScope.local == 'en'}">Taiwan Green Cross Corporation/Green Cross Biotech </c:when><c:otherwise>綠十字醫療科技，每一天生活寫意</c:otherwise></c:choose></p>
							<br/>
							<!-- a href="#" class="site-btn">了解更多</a -->
						</div>
					</div>
				</div>
			</div>
			<!-- item -->
			<div class="hs-item set-bg text-white" data-setbg="img/green/banner-2.jpg">
				<div class="overlay"></div>
				<div class="container">
					<div class="row">
						<div class="col-xl-7">
							<h2><c:choose><c:when test="${sessionScope.local == 'en'}">We support every family<br/> with our products and services.</c:when><c:otherwise>美好家庭<br/>綠十字相挺</c:otherwise></c:choose></h2>
							<p><c:choose><c:when test="${sessionScope.local == 'en'}">Taiwan Green Cross Corporation/Green Cross Biotech </c:when><c:otherwise>美好家庭，綠十字相挺</c:otherwise></c:choose></p>
							<br/>
							<!-- a href="#" class="site-btn">了解更多</a -->
						</div>
					</div>
				</div>
			</div>
			<div class="hs-item set-bg text-white" data-setbg="img/green/banner-3.jpg">
				<div class="overlay"></div>
				<div class="container">
					<div class="row">
						<div class="col-xl-7">
							<h2><c:choose><c:when test="${sessionScope.local == 'en'}">Decent sleep is the<br/> foundation of your health.</c:when><c:otherwise>睡的安心<br/>是健康的最佳基金</c:otherwise></c:choose></h2>
							<p><c:choose><c:when test="${sessionScope.local == 'en'}">Taiwan Green Cross Corporation/Green Cross Biotech </c:when><c:otherwise>睡的安心，是健康的最佳基金</c:otherwise></c:choose></p>
							<br/>
							<!-- a href="#" class="site-btn">了解更多</a -->
						</div>
					</div>
				</div>
			</div>
			<div class="hs-item set-bg text-white" data-setbg="img/green/banner-4.jpg">
				<div class="overlay"></div>
				<div class="container">
					<div class="row">
						<div class="col-xl-7">
							<h2><c:choose><c:when test="${sessionScope.local == 'en'}">We would like to take good care of your body condition.</c:when><c:otherwise>您的身體 我們在意</c:otherwise></c:choose></h2>
							<p><c:choose><c:when test="${sessionScope.local == 'en'}">Taiwan Green Cross Corporation/Green Cross Biotech </c:when><c:otherwise>您的身體 我們在意</c:otherwise></c:choose></p>
							<br/>
							<!-- a href="#" class="site-btn">了解更多</a -->
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- Hero section end -->
	<!-- Hero section -->
	<section class="hero-section mobile">
		<div class="hero-slider owl-carousel">
			<!-- item -->
			<div class="hs-item set-bg text-white" data-setbg="img/green/banner.jpg">
				<!--div class="overlay"></div-->
				<div class="container">
					<div class="row">
						<div class="col-xl-7">
							<h2><c:choose><c:when test="${sessionScope.local == 'en'}">TAIWAN GREEN CROSS CO., LTD</c:when><c:otherwise>台灣綠十字股份有限公司</c:otherwise></c:choose></h2>
							<p><c:choose><c:when test="${sessionScope.local == 'en'}">Welcome to our official website, we offer you professional medical products</c:when><c:otherwise>歡迎來到我們的官方網站，我們提供您專業的醫療產品</c:otherwise></c:choose></p>
							<!-- a href="#" class="site-btn"><c:choose><c:when test="${sessionScope.local == 'en'}">TAIWAN GREEN CROSS Products</c:when><c:otherwise>綠十字產品</c:otherwise></c:choose></a -->
						</div>
					</div>
				</div>   
			</div>
			<!-- item -->
		</div>

		<div class="overlay">
			<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1000 100" preserveAspectRatio="none">
				<path class="elementor-shape-fill" d="M500.2,15.7L0,0v50h1000V0L500.2,15.7z"/>
			</svg>
		</div>
	</section>
	<!-- Hero section end -->
	${webHTML}
	<a id="mobile-email" href="http://officemail.cloudmax.com.tw/" target="_blank">
		<i class="fas fa-envelope"></i>
	</a>
</body>

</html>