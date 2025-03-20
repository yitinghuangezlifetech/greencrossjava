<%@ page language="java" contentType="text/html;  charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/common-util.tld" prefix="pi"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>台灣綠十字股份有限公司</title>

</head>  

<body>

	<!-- Page info section -->
	<section class="page-info-section set-bg" data-setbg="<c:url value = "../../img/green/page-info-bg/5.jpg"/>">
		<div class="container">
			<h2><c:choose><c:when test="${sessionScope.local == 'en'}">Company Profile</c:when><c:otherwise>公司介紹</c:otherwise></c:choose></h2>
		</div>
	</section>
	<!-- Page info section end -->
	
	<!-- Intro section -->
	<section class="intro-section spad">
		<div class="container">
			<div class="row">
				<div class="col-lg-12 intro-text">
					${webHTML}
				</div>
				<!-- div class="col-lg-12">
					<img src="<c:url value='/img/green/intro2.jpg' />" alt="">
				</div -->
			</div>
		</div>
	</section>
	<!-- Intro section end -->
	
</body>

</html>