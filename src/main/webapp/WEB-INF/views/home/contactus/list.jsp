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
	<section class="page-info-section set-bg" data-setbg="<c:url value = "../../img/green/page-info-bg/3.jpg"/>">
		<div class="container">
			<h2><c:choose><c:when test="${sessionScope.local == 'en'}">Contact Us</c:when><c:otherwise>聯絡我們</c:otherwise></c:choose></h2>
		</div>
	</section>
	<!-- Page info section end -->
	
	<!-- Contact section -->
	<section class="contact-section spad">
		<div class="container">
			<div class="row">
				<div class="col-lg-6">
					<div class="contact-text">
						<!-- <h3>Get in touch</h3> -->
						<div class="contact-info">
							<div class="ci-image set-bg" data-setbg="<c:url value = "../../img/green/logo_small.png"/>"></div>
							<div class="ci-content">
								<p><c:choose><c:when test="${sessionScope.local == 'en'}">Address：6F., No. 244, Sec. 3, Chengde Rd., Datong Dist., Taipei City 103, Taiwan (R.O.C.)</c:when><c:otherwise>地址：103台北市大同區承德路三段244號6樓</c:otherwise></c:choose></p>
								<p><c:choose><c:when test="${sessionScope.local == 'en'}">Toll free：0800-266-266</c:when><c:otherwise>免付費電話：0800-266-266</c:otherwise></c:choose></p>
								<p><c:choose><c:when test="${sessionScope.local == 'en'}">TEL：+886 2 2596-0277 (Rep. Line)</c:when><c:otherwise>電話：+886 2 2596-0277 (代表號)</c:otherwise></c:choose></p>
								<p><c:choose><c:when test="${sessionScope.local == 'en'}">FAX：+886 2 2585-7466</c:when><c:otherwise>傳真：+886 2 2585-7466</c:otherwise></c:choose></p>
								<p>E-mail：tgcc@greencross.com.tw</p>
							</div>
						</div>
					</div>
					<form id="inputForm" class="contact-form">
						<input type="text" id="questName" name="questName" placeholder="<c:choose><c:when test="${sessionScope.local == 'en'}">Name</c:when><c:otherwise>姓名</c:otherwise></c:choose>">
						<input type="text" id="questMail" name="questMail"placeholder="E-mail">
						<input type="text" id="questTitle" name="questTitle" placeholder="<c:choose><c:when test="${sessionScope.local == 'en'}">Subject</c:when><c:otherwise>主旨</c:otherwise></c:choose>">
						<textarea  id="questContent" name="questContent" placeholder="<c:choose><c:when test="${sessionScope.local == 'en'}">Message</c:when><c:otherwise>內容</c:otherwise></c:choose>"></textarea>
						<button  type="button" class="site-btn" id="sendQuestBut"><c:choose><c:when test="${sessionScope.local == 'en'}">Submit!</c:when><c:otherwise>送出</c:otherwise></c:choose></button>
					</form>
					
				</div>
			</div>
		</div>
		<div class="map" id="map-canvas">
			<iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3613.8549679902335!2d121.51646471544731!3d25.072904342883675!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3442a9493e7b683b%3A0xf018bb5e4dcf14bc!2zMTAz5Y-w5YyX5biC5aSn5ZCM5Y2A5om_5b636Lev5LiJ5q61MjQ06JmfNg!5e0!3m2!1szh-TW!2stw!4v1574056818400!5m2!1szh-TW!2stw" width="100%" height="100%" frameborder="0" style="border:0;" allowfullscreen=""></iframe>
		</div>
	</section>
	<!-- Contact section end -->
	<script>
	$(document).ready(function() {
		$('#sendQuestBut').click(function(e) {
			ajaxSendMessage();
		});
		
		
		
	});
	
	
	
	function ajaxSendMessage(){
		$.ajax({
			type : "POST",
			url : '<c:url value = "/home/contactus/ajaxSendQuest"/>',
			data : $("#inputForm").serialize(),// serializes the form's elements.
			success : function(result) {
				console.log(result);
				if (checkAjaxResp(result)) {
					$('#questName').val('');
					$('#questMail').val('');
					$('#questTitle').val('');
					$('#questContent').val('');
				} 
			}
		});
	}
	</script>
</body>

</html>