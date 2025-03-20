<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

  

<!-- KendoIU CSS -->
<link rel="stylesheet" href='<c:url value="/css/kendo/kendo.common.min.css"/>?${sessionScope.randomId}'/>
<link rel="stylesheet" href='<c:url value="/css/kendo/kendo.default.min.css"/>?${sessionScope.randomId}'/>
<link rel="stylesheet" href='<c:url value="/css/kendo/kendo.default.mobile.min.css"/>?${sessionScope.randomId}'/>
<!-- KendoIU CSS END -->

<!-- KendoIU JS -->
<script type="text/javascript" src="<c:url value='/js/kendo/kendo.all.min.js' />?${sessionScope.randomId}"></script>
<!-- KendoIU JS END -->
<!-- Google reCAPTCHA JS -->
<script type="text/javascript" src="https://www.google.com/recaptcha/api.js?${sessionScope.randomId}"></script>
<!-- Google reCAPTCHA JS END -->

<script type="text/javascript" src="<c:url value='/js/myJS.js' />?${sessionScope.randomId}"></script>
<script type="text/javascript" src="<c:url value='/js/shopping-car.js' />?${sessionScope.randomId}"></script>

<!-- sweetalert -->
<script type="text/javascript" src="<c:url value='/js/sweetAlert/sweetalert2.all.min.js' />?${sessionScope.randomId}"></script>

<!-- sockjs -->
<script src="<c:url value='/js/sockjs/sockjs-0.3.min.js' />?${sessionScope.randomId}"></script>

<!-- uiblock -->
<script src="<c:url value='/js/jquery-ui-block/jquery.blockUI.js' />?${sessionScope.randomId}"></script>


<!-- custom CSS -->
<link rel="stylesheet" href='<c:url value="/css/style.css"/>?${sessionScope.randomId}'/>

<!-- fontawesome -->
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css?${sessionScope.randomId}" integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ" crossorigin="anonymous">

<script type="text/javascript">	
	var global = {};
	<c:choose>
		<c:when test="${sessionScope.isLogin == 'N'}">
			global.isLogin='N';
		</c:when>
		<c:otherwise>
			global.isLogin='Y';
		</c:otherwise>
	</c:choose>
	
	
</script>



