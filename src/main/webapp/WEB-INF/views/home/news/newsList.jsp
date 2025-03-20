<%@ page language="java" contentType="text/html;  charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/common-util.tld" prefix="pi"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html  >
<head>
  <!-- Site made with Mobirise Website Builder v4.10.15, https://mobirise.com -->
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="generator" content="Mobirise v4.10.15, mobirise.com">
  <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
  <link rel="shortcut icon" href="assets/images/logo2.png" type="image/x-icon">
  <meta name="description" content="Website Generator Description">

</head>
<body>

	<!-- Page info section -->
	<section class="page-info-section set-bg" data-setbg="<c:url value = "../../img/green/page-info-bg/5.jpg"/>">
		<div class="container">
			<h2><c:choose><c:when test="${sessionScope.local == 'en'}">News</c:when><c:otherwise>最新消息</c:otherwise></c:choose></h2>
		</div>
	</section>
	<!-- Page info section end -->

<br>
<c:if test="${newsList != null}">
<section class="mbr-section content6 cid-rDtsWXURM0" id="content6-u">            
    <div class="container">
        <div class="media-container-row">
            <div class="col-12 col-md-8">
            	<c:choose>
            		<c:when test="${sessionScope.local == 'en'}">
            		<c:forEach items="${newsList}" var="news">
            		<c:if test="${(news.content == null && news.title == null) || (news.content == '' || news.title == '')}">
                <div class="media-container-row">
                <!--
                    <div class="mbr-figure" style="width:70%;">
                      <img src="<c:url value ='/img/background1.jpg'/>" alt="Mobirise">  
                    </div>
                   --> 
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                    <div class="media-content">
                        <div class="mbr-section-text">
                            <p class="mbr-text mb-0 mbr-fonts-style display-7">
                               <b>${news.title_en}</b>
                               <br><a href="<c:url value = '/home/news/editNews'/>?id=${news.id}">${news.content_en}</a>
                            </p>
                        </div>
                    </div>
                </div>
                <br><br>
                </c:if>
                </c:forEach>
                </c:when>                
                <c:otherwise>
				<c:forEach items="${newsList}" var="news">
				<c:if test="${(news.content_en == null && news.title_en == null) || (news.content_en == '' || news.title_en == '')}">
                <div class="media-container-row">
                <!-- 
                    <div class="mbr-figure" style="width:70%;">
                      <img src="<c:url value ='/img/background1.jpg'/>" alt="Mobirise">  
                    </div>
                 -->   
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                    <div class="media-content">
                        <div class="mbr-section-text">
                            <p class="mbr-text mb-0 mbr-fonts-style display-7">
                               <b>${news.title}</b>
                               <br><a href="<c:url value = '/home/news/editNews'/>?id=${news.id}">${news.content}</a>
                            </p>
                        </div>
                    </div>
                </div>
                <br><br>   
                </c:if>   
                </c:forEach>          
                </c:otherwise>
                </c:choose>                                                     
            </div>
        </div>
    </div>
</section>
</c:if>
<br>

</body>
</html>