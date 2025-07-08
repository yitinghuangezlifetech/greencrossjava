<!DOCTYPE html>
<%@ page language="java"	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/common-util.tld" prefix="pi"%>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Bootstrap Material Admin by Bootstrapious.com</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="robots" content="all,follow">
    <!-- Bootstrap CSS-->
    <link rel="stylesheet" href='<c:url value="/css/bootstrap/bootstrap.min.css"/>?${sessionScope.randomId}'/>
    <!-- Font Awesome CSS-->
    <link rel="stylesheet" href='<c:url value="/css/font-awesome/css/font-awesome.min.css"/>?${sessionScope.randomId}'/>
    <!-- Fontastic Custom icon font-->
     <link rel="stylesheet" href='<c:url value="/css/adminPage/fontastic.css"/>?${sessionScope.randomId}'/>
    <!-- Google fonts - Poppins -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:300,400,700">
    
    <!-- theme stylesheet-->
    <link rel="stylesheet" href='<c:url value="/css/adminPage/style.default.css"/>?${sessionScope.randomId}'/>
    
    <!-- Custom stylesheet - for your changes-->
    <link rel="stylesheet" href='<c:url value="/css/adminPage/custom.css"/>?${sessionScope.randomId}'/>
    
    <!-- Favicon-->
    <link rel="shortcut icon" href='<c:url value="/img/favicon.ico"/>?${sessionScope.randomId}'>
    <!-- Tweaks for older IEs--><!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script><![endif]-->
    <script type="text/javascript" src="<c:url value='/js/jQuery/jquery-3.3.1.min.js' />"></script>
   	<script type="text/javascript" src="<c:url value='/js/popper/umd/popper.min.js' />?${sessionScope.randomId}"></script>
	<script type="text/javascript" src="<c:url value='/js/bootstrap/bootstrap.min.js' />?${sessionScope.randomId}"></script>
    <script type="text/javascript" src="<c:url value='/js/jquery-cookie/jquery.cookie.js' />?${sessionScope.randomId}"></script>
    <script type="text/javascript" src="<c:url value='/js/chart-js/Chart.min.js' />?${sessionScope.randomId}"></script>
    <script type="text/javascript" src="<c:url value='/js/jquery-validation/jquery.validate.min.js' />?${sessionScope.randomId}"></script>
    <script type="text/javascript" src="<c:url value='/js/adminPage/front.js' />?${sessionScope.randomId}"></script>
      
    <c:import url="/pages/commons/common.jsp" />
    
    
    
  </head>
  <body>
  
    <div class="page login-page">
      <div class="container d-flex align-items-center">
        <div class="form-holder has-shadow">
          <div class="row">
            <!-- Logo & Information Panel-->
            <div class="col-lg-6">
              <div class="info d-flex align-items-center">
                <div class="content">
                  <div class="logo">
                    <h1>企業會員登入</h1>
                  </div>
                  <p>Login in...</p>
                </div>
              </div>
            </div>
            <!-- Form Panel    -->
            <div class="col-lg-6 bg-white">
              <div class="form d-flex align-items-center">
                <div class="content">
                  <form name="thisForm" id="thisForm" action="<c:url value='/admin/loginCheck' />" method="post" class="form-validate">
                    <div class="form-group">
                      <input id="login-username" type="text"  id="userId" name="loginUserId" required data-msg="Please enter your username" class="input-material">
                      <label for="login-username" class="label-material">帳號</label>
                    </div>
                    <div class="form-group">
                      <input id="login-password" type="password" id="userPwd" name="loginUserPp" required data-msg="Please enter your password" class="input-material">
                      <label for="login-password" class="label-material">密碼</label>
                    </div>
                    <div class="form-group">
                    		<c:choose>
								<c:when test="${sessionScope.useReCAPTCHA == 'Y'}">
									${reCAPTCHACode}
								</c:when>
								<c:otherwise>
									<input type="text" id="securityId" name="securityId" placeholder="輸入驗證碼" class="form-control checkData" dataType="string" notNull="Y" limit="5" maxlength="5" />
									<img id="securityImg" src="securityImage" style="border:1px solid black;">
								</c:otherwise>
							</c:choose>
							<span id="loginMsg"><font color='red'>${errorMsg}</font></span>
                    </div>
                    
                    <a id="login"  href='javascript:loginHandler()'  class="btn btn-primary" >登入</a>
                    <!-- This should be submit button but I replaced it with <a> for demo purposes-->
                  </form><a href="#" class="forgot-pass">忘記密碼?</a><br><small>沒有帳號嗎？ </small><a href="register.html" class="signup">註冊</a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="copyrights text-center">
        <p>Design by <a href="https://bootstrapious.com/admin-templates" class="external">Bootstrapious</a>
          <!-- Please do not remove the backlink to us unless you support further theme's development at https://bootstrapious.com/donate. It is part of the license conditions. Thank you for understanding :)-->
        </p>
      </div>
    </div>    
    <script type="text/javascript">
	$(document).ready(function() {
		
		$("#userId").focus();
	});
	function loginHandler(){
		
			$("#thisForm").submit();
		
	}
	//按enter鍵觸發登入
	$(document).keypress(function(event) {
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if(keycode == '13') {
			loginHandler();
		}
	});
</script>
  </body>
</html>