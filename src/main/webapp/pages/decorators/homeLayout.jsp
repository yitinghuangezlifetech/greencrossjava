<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/common-util.tld" prefix="pi"%>

<!DOCTYPE HTML>
<html lang="zxx" class="no-js">
<head>
<!-- meta character set -->
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
	${sessionScope.SEOmetaData}
<!-- Site Title -->
<title>台灣綠十字股份有限公司</title>

<!-- <link
	href="https://fonts.googleapis.com/css?family=Poppins:100,200,400,300,500,600,700"
	rel="stylesheet">-->
<!--
			CSS
			============================================= -->

<link rel="stylesheet" href='<c:url value="/css/homePageGreen/bootstrap.min.css"/>'/>
<link rel="stylesheet" href='<c:url value="/css/homePageGreen/font-awesome/css/all.min.css"/>'/>
<link rel="stylesheet" href="<c:url value="/css/homePageGreen/flaticon.css"/>"/>
<link rel="stylesheet" href="<c:url value="/css/homePageGreen/owl.carousel.css"/>"/>
<link rel="stylesheet" href="<c:url value="/css/homePageGreen/style.css"/>"/>
<link rel="stylesheet" href="<c:url value="/css/homePageGreen/animate.css"/>"/>
<link rel="stylesheet" href="<c:url value="/css/homePageGreen/main.css"/>"/> 




<script type="text/javascript" src="<c:url value='/js/homePageGreen/jquery-3.2.1.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/homePageGreen/bootstrap.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/homePageGreen/owl.carousel.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/homePageGreen/circle-progress.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/homePageGreen/main.js' />?ver=1"></script>










<c:import url="/pages/commons/common.jsp" />
</head>
<body>
	<!-- Header section -->
	<header class="header-section <c:choose><c:when test="${sessionScope.isShowHeaderSectionIndex == 'Y'}"> header-section-index </c:when></c:choose>">
		<div class="container">
			<!-- Site Logo -->
			<a href="<c:url value = "/"/>" class="site-logo">
				<img src="<c:url value='/img/green/logo.png' />" alt="">
			</a>
			<!-- responsive -->
			<div class="nav-switch">
				<i class="fa fa-bars"></i>
			</div>
			<!-- Main Menu -->
			<ul class="main-menu">
				<li><a href="<c:url value = "/home/aboutus/list"/>"><c:choose><c:when test="${sessionScope.local == 'en'}">About TGCC</c:when><c:otherwise>公司介紹</c:otherwise></c:choose></a></li>
				<!--li><a href="<c:url value = "/home/product/list"/>">產品介紹</a>
					<ul>
						<li><a href="product.html">醫療器材</a></li>
						<li><a href="product.html">藥品</a></li>
					</ul>
				</li-->
				<li class="dropdown">
					<a  href="<c:url value = "/home/product/list"/>"><c:choose><c:when test="${sessionScope.local == 'en'}">Products</c:when><c:otherwise>產品介紹</c:otherwise></c:choose></a>
					<ol class="dropdown-menu" width="100%">
					
						<c:forEach items="${sessionScope.menuProdList}" var="currentChild">  
						
							<li><a href="<c:url value = "/home/product/list"/>?productClassId=${currentChild.pClassId}">${currentChild.pClassName}</a></li>
						
						</c:forEach>
					
						<!--a href="<c:url value = "/home/product/list"/>"><c:choose><c:when test="${sessionScope.local == 'en'}">Product Description</c:when><c:otherwise>產品介紹</c:otherwise></c:choose></a-->
					</ol>
				</li>
				<li><a href="<c:url value = "/home/news/newsList"/>"><c:choose><c:when test="${sessionScope.local == 'en'}">News</c:when><c:otherwise>最新消息</c:otherwise></c:choose></a></li>
				<li><a href="<c:url value = "/home/education/educationList"/>"><c:choose><c:when test="${sessionScope.local == 'en'}">Patient Education</c:when><c:otherwise>衛教資訊</c:otherwise></c:choose></a></li>
				<li><a href="<c:url value = "/home/contactus/list"/>"><c:choose><c:when test="${sessionScope.local == 'en'}">Contact Us</c:when><c:otherwise>聯絡我們</c:otherwise></c:choose></a></li>
				<li><a href="#" onclick="changeLanguage('<c:choose><c:when test="${sessionScope.local == 'en'}">tw</c:when><c:otherwise>en</c:otherwise></c:choose>');">
						<c:choose>
							<c:when test="${sessionScope.local == 'en'}">
							中文
							</c:when>
							<c:otherwise>
							English
							</c:otherwise>
						</c:choose>
					</a>
				</li>
			</ul>
		</div>
	</header>
	<!-- Header section end -->


	




	

	
	<decorator:body />	
	
	

	<!-- Footer section -->
	<footer class="footer-section <c:choose><c:when test="${sessionScope.isShowHeaderSectionIndex == 'Y'}"> index </c:when></c:choose>">
		<div class="overlay">
			<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1000 50" preserveAspectRatio="none">
				<path class="elementor-shape-fill" d="M500.2,15.7L0,0v50h1000V0L500.2,15.7z"/>
			</svg>
		</div>
		<div class="container">
			<div class="row contact">
				<div class="col-lg-4">
					<p><span><c:choose><c:when test="${sessionScope.local == 'en'}">TEL</c:when><c:otherwise>電話</c:otherwise></c:choose></span><br/>+886 2 2596-0277 (<c:choose><c:when test="${sessionScope.local == 'en'}">Rep. Line</c:when><c:otherwise>代表號</c:otherwise></c:choose>) </p>
					<p><span><c:choose><c:when test="${sessionScope.local == 'en'}">FAX</c:when><c:otherwise>傳真</c:otherwise></c:choose></span><br/>+886 2 2585-7466 </p>
				</div>
				<div class="col-lg-4">
					<p><span><c:choose><c:when test="${sessionScope.local == 'en'}">Toll free</c:when><c:otherwise>免付費電話</c:otherwise></c:choose></span><br/>0800-266-266</p>
					<p><span><c:choose><c:when test="${sessionScope.local == 'en'}">E-Mail</c:when><c:otherwise>電郵</c:otherwise></c:choose></span><br/><a href="mailto:tgcc@greencross.com.tw">tgcc@greencross.com.tw</a></p>
				</div>
				<div class="col-lg-4">
					<p><span><c:choose><c:when test="${sessionScope.local == 'en'}">Address</c:when><c:otherwise>地址</c:otherwise></c:choose></span><br/><c:choose><c:when test="${sessionScope.local == 'en'}">6F., No. 244, Sec. 3, Chengde Rd., Datong Dist., Taipei City 103, Taiwan (R.O.C.)</c:when><c:otherwise>103台北市大同區承德路三段244號6樓</c:otherwise></c:choose></p>
				</div>
			</div>
		</div>
		<div class="container">
			<ul class="footer-menu">
				<li><a href="http://officemail.cloudmax.com.tw/" target="_blank"><c:choose><c:when test="${sessionScope.local == 'en'}">Mailbox</c:when><c:otherwise>員工信箱</c:otherwise></c:choose></a></li>
				<li><a href="<c:url value = "/home/product/list"/>"><c:choose><c:when test="${sessionScope.local == 'en'}">Products</c:when><c:otherwise>產品介紹</c:otherwise></c:choose></a></li>
				<li><a href="<c:url value = "/home/aboutus/list"/>"><c:choose><c:when test="${sessionScope.local == 'en'}">About TGCC</c:when><c:otherwise>公司介紹</c:otherwise></c:choose></a></li>
				<li><a href="javascript:void(0)"><c:choose><c:when test="${sessionScope.local == 'en'}">News</c:when><c:otherwise>最新消息</c:otherwise></c:choose></a></li>
				<li><a href="javascript:void(0)"><c:choose><c:when test="${sessionScope.local == 'en'}">Patient Education</c:when><c:otherwise>衛教資訊</c:otherwise></c:choose></a></li>
				<li><a href="<c:url value = "/home/contactus/list"/>"><c:choose><c:when test="${sessionScope.local == 'en'}">Contact Us</c:when><c:otherwise>聯絡我們</c:otherwise></c:choose></a></li>
				<li><a href="#" onclick="changeLanguage('<c:choose><c:when test="${sessionScope.local == 'en'}">tw</c:when><c:otherwise>en</c:otherwise></c:choose>');">
						<c:choose>
							<c:when test="${sessionScope.local == 'en'}">
							中文
							</c:when>
							<c:otherwise>
							English
							</c:otherwise>
						</c:choose>
					</a>
				</li>
			</ul>
			<div class="copyright">
				台灣綠十字股份有限公司 版權所有 &copy;<script>document.write(new Date().getFullYear());</script> Taiwan Green Cross Co., Ltd. All Rights Reserved
			</div>
		</div>
	</footer>
	<!-- Footer top section end -->


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