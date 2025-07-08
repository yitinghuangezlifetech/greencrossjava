<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib	uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/common-util.tld" prefix="pi"%>

<!DOCTYPE HTML>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>台灣綠十字管理後台</title>
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
    <link rel="stylesheet" href='<c:url value="/css/adminPage/style.green.css"/>?${sessionScope.randomId}'/>
    
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
  	<span id="popupNotification"></span>
    <div class="page">
      <!-- Main Navbar-->
      <header class="header">
        <nav class="navbar">
          <!-- Search Box-->
          <div class="search-box">
            <button class="dismiss"><i class="icon-close"></i></button>
            <form id="searchForm" action="#" role="search">
              <input type="search" placeholder="What are you looking for..." class="form-control">
            </form>
          </div>
          <div class="container-fluid">
            <div class="navbar-holder d-flex align-items-center justify-content-between">
              <!-- Navbar Header-->
              <div class="navbar-header">
                <!-- Navbar Brand --><a href="index.html" class="navbar-brand d-none d-sm-inline-block">
                  <div class="brand-text d-none d-lg-inline-block"><img src='<c:url value="/img/tgccLogo.png"/>' width='250px'></div>
                  <div class="brand-text d-none d-sm-inline-block d-lg-none"><img src='<c:url value="/img/tgccLogoSmall.png"/>'></div></a>
                <!-- Toggle Button--><a id="toggle-btn" href="#" class="menu-btn active"><span></span><span></span><span></span></a>
              </div>
              <!-- Navbar Menu -->
              <ul class="nav-menu list-unstyled d-flex flex-md-row align-items-md-center">
               
                
                <!-- Logout    -->
                <li class="nav-item"><a href="<c:url value="/admin/loginOut"/>" class="nav-link logout"> <span class="d-none d-sm-inline">後台登出</span><i class="fas fa-sign-out-alt"></i></a></li>
              </ul>
            </div>
          </div>    
        </nav>
      </header>
      <div class="page-content d-flex align-items-stretch"> 
        <!-- Side Navbar -->
        <nav class="side-navbar">
          <!-- Sidebar Header-->
          <div class="sidebar-header d-flex align-items-center">
            <div class="avatar"><img src='<c:url value="/img/user.png"/>?${sessionScope.randomId}' alt="..." class="img-fluid rounded-circle"></div>
            <div class="title">
              <h1 class="h4">${sessionScope.userProf.userName}</h1>
              <p>user Info</p>
            </div>
          </div>
          <!-- Sidebar Navidation Menus--><span class="heading">功能選單</span>
          <ul class="list-unstyled">
                    <li><a href='<c:url value="/admin/home"/>'> <i class="fas fa-home"></i>後台首頁 </a></li>
                    
                    ${sessionScope.menuTree}   
                    <li><a href="<c:url value="/"/>"> <i class="fas fa-arrow-circle-left"></i>回首頁</a></li>
          </ul>
          
        </nav>
        <div class="content-inner">
          <!-- Page Header-->
          <header class="page-header">
            <div class="container-fluid">
              <h2 class="no-margin-bottom">${sessionScope.funcName}</h2>
            </div>
          </header>
          <!-- Breadcrumb-->
          <div class="breadcrumb-holder container-fluid">
            <ul class="breadcrumb">
              <li class="breadcrumb-item"><a href="<c:url value="/admin/home"/>">後台首頁</a></li>
              <li class="breadcrumb-item active">${sessionScope.funcName}            </li>
            </ul>
          </div>
          <div>
          <br/>
          </div>
          
          <decorator:body />
		  <!-- Page Footer-->
          <footer class="main-footer">
            <div class="container-fluid">
              <div class="row">
                <div class="col-sm-6">
                  <p>Your company &copy; 2017-2019</p>
                </div>
                <div class="col-sm-6 text-right">
                  <p>Design by <a href="https://bootstrapious.com/admin-templates" class="external">Bootstrapious</a></p>
                  <!-- Please do not remove the backlink to us unless you support further theme's development at https://bootstrapious.com/donate. It is part of the license conditions. Thank you for understanding :)-->
                </div>
              </div>
            </div>
          </footer>
        </div>
      </div>
    </div>
    <!-- JavaScript files-->
   
    
    
    <script type="text/javascript">	
    var funcId='${sessionScope.funcId}';
    $(document).ready(
    		function() {
    			if(funcId.trim()!='')
    				$('#'+funcId).addClass("active");
    			//$('#a009000').attr("aria-expanded","true");
    			//$('#list-group-7').addClass("show");
    		}
    );
    
	//執行功能表
	function doAction(act,funcId,funcName){
		
		window.location.href = "<c:url value='/redirectAction'/>?act="+act+"&funcId="+funcId+"&funcName="+encodeURI(encodeURI(funcName));
	}
	</script>

  </body>
</html>