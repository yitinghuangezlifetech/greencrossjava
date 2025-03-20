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
			<h2><c:choose><c:when test="${sessionScope.local == 'en'}">Patient Education</c:when><c:otherwise>衛教資訊</c:otherwise></c:choose></h2>
		</div>
	</section>
	<!-- Page info section end -->

<br>
<%-- 
	<section class="product-section spad">
		<div class="container">
			<div class="row">  
			
			
				<div class="col-xl-3 col-lg-3 col-md-4 sort">
					<h4><c:choose><c:when test="${sessionScope.local == 'en'}">Eduation Class</c:when><c:otherwise>衛教分類</c:otherwise></c:choose></h4>
					<c:forEach items="${prodClassTreeMap}" var="current">  
						<ul>  
							<li>
								<a href="javascript:selectClass('${current.classId}'); ">${current.className}</a>
								<ul>
									<c:forEach items="${current.cList}" var="currentChild">  
						   				<li ><a href="javascript:selectClass('${currentChild.classId}');">${currentChild.className}</a></li>
									</c:forEach>
								</ul>
							</li>
						</ul>
					</c:forEach>    
				</div>
				
				<div class="col-xl-9 col-lg-9 col-md-8 col-sm-12 pdt-box" id="test5">
		  			<div class="row">
							<div class="col-lg-12">
								<div class="pdt-sort-info" id="prodClassDescDiv">
									<article id="prodClassDesc">
										
									</article>
								</div>
							</div>
						</div>
		  			<div  class="row">
		  				<div class="col-12 text-center">
		  					<font id='noDataShow' style="width: 100%; font-size: 20px;" class=""><i class="fas fa-exclamation-triangle"></i>&nbsp;<c:choose><c:when test="${sessionScope.local == 'en'}">No results.</c:when><c:otherwise>查無資料</c:otherwise></c:choose></font>
		  				</div>
		  			</div>
		  			
		  			
					<div id="prodShowPl" class="row">
						
						  
					</div>
			   		
					<br/>
				</div>
			</div>    
		
		</div>
	</section>
--%>


<c:if test="${guardianList != null}">
<section class="product-section spad" id="test6">            
    <div class="container">
        <div class="media-container-row">



				<div class="col-xl-3 col-lg-3 col-md-4 sort">
					<h4><c:choose><c:when test="${sessionScope.local == 'en'}">Eduation Class</c:when><c:otherwise>衛教分類</c:otherwise></c:choose></h4>
					<c:forEach items="${prodClassTreeMap}" var="current">  
						<ul>  
							<li>
								<a href="javascript:selectClass('${current.classId}'); ">${current.className}</a>
								<ul>
									<c:forEach items="${current.cList}" var="currentChild">  
						   				<li ><a href="javascript:selectClass('${currentChild.classId}');">${currentChild.className}</a></li>
									</c:forEach>
								</ul>
							</li>
						</ul>
					</c:forEach>    
				</div>        
        
            <div class="col-12 col-md-8">
            	<c:choose>
            		<c:when test="${sessionScope.local == 'en'}">
            		<c:forEach items="${guardianList}" var="guardian">
            		<c:if test="${(guardian.content == null && guardian.title == null) || (guardian.content == '' || guardian.title == '')}">
                <div class="media-container-row">
                 
                    <div class="mbr-figure" style="width:70%;">
                      <!-- <img src="<c:url value ='/img/background1.jpg'/>" alt="Mobirise">-->  
                      <c:if test="${guardian.main_pic_no != null}">
                      <img src="<c:url value = "/home/education/ajaxGetEducationPic"/>/${guardian.main_pic_no}" alt="#: name # image" />
                  		</c:if>
                    </div>
                    
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                    <div class="media-content">
                        <div class="mbr-section-text">
                            <p class="mbr-text mb-0 mbr-fonts-style display-7">
                               <b>${guardian.title_en}</b>
                               <br><a href="<c:url value = '/home/education/editEducation'/>?id=${guardian.id}">${guardian.content_en}</a>
                            </p>
                        </div>
                    </div>
                </div>
                <br><br>
                </c:if>
                </c:forEach>
                </c:when>                
                <c:otherwise>
				<c:forEach items="${guardianList}" var="guardian">
				<c:if test="${(guardian.content_en == null && guardian.title_en == null) || (guardian.content_en == '' || guardian.title_en == '')}">
                <div class="media-container-row">
                  
                    <div class="mbr-figure" style="width:70%;">
                      <!--<img src="<c:url value ='/img/background1.jpg'/>" alt="Mobirise">--> 
                      <c:if test="${guardian.main_pic_no != null}">
                       <img src="<c:url value = "/home/education/ajaxGetEducationPic"/>/${guardian.main_pic_no}" alt="#: name # image" />
                    	</c:if>
                    </div>
                   
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                    <div class="media-content">
						<div class="mbr-section-text">
							<p class="mbr-text mb-0 mbr-fonts-style display-7">
								<b>${guardian.title}</b> <br>${guardian.content} 
								<a href="<c:url value = '/home/education/editEducation'/>?id=${guardian.id}">
									<button type="button" id="backBtn"
										class="btn btn-default ">
										<i class="fas fa-chevron-left">繼續閱讀</i>
									</button>
								</a>
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
<form id="guardianForm">
	<input type="hidden" name="classId" id="classId" />
</form>
<script>
$(document).ready(function() {
	$('#test5').hide();
});
function resetForm(){
	$('#nowPage').val("1");
	$('#nowPageInput').val("1");
	$('#productClassId').val("");
	$('#searchProductText').val("");
}

function selectClass(classId){
	 resetForm();
	 $('#searchProductText').val('');
	 $('#productClassId').val(classId.trim());
	 getProduct(classId);
	 
}

function getProduct(classId){
	$("#classId").val(classId);
	$('#guardianForm')
	.attr('action',
			'<c:url value = "/home/education/educationList"/>');
$('#guardianForm').attr('method',
	'post');
$('#guardianForm').submit();

// 	$('#test5').show();
// 	$("#test6").hide();
// 	global.canChangePage=false;
// 	loading();
// 	$.ajax({
// 		type : "POST",
// 		url : '<c:url value = "/home/guardian/guardianList"/>',
// 		data : $("#prodFrom").serialize(),// serializes the form's elements.
// 		success : function(result) {
// 			if (checkAjaxResp(result)) {
// 				console.log(result.data.dataSize);
// 				cleanProduct();
// 				$('#totalPage').val(result.data.totalPage);
// 				$('#totalPageInput').val(result.data.totalPage);
// 				$('#dataSize').val(result.data.dataSize);
//   				$('#dataSizeInput').text(result.data.dataSize);
// 				$.each(result.data.data,
// 						function(i, obj) {
// 							addProduct(obj);
// 						}
//    				);
// 				if(result.data.dataSize=='0'){
// 					$('#noDataShow').show();
// 				}else{
// 					$('#noDataShow').hide();
// 				}
// 				global.canChangePage=true;
				
// 				if(result.data.classDesc ==''){
// 					$('#prodClassDesc').html('');
					
// 					$('#prodClassDescDiv').hide();
// 				}else{
// 					$('#prodClassDesc').html(result.data.classDesc);
// 					$('#prodClassDescDiv').show();
// 				}
				
				
				
				
// 			}
// 			loadFinish();
// 		}
// 	});
}


function loading(){
	$.blockUI({ message: '<h3> <c:choose><c:when test="${sessionScope.local == 'en'}">Data searching</c:when><c:otherwise>資料搜尋中</c:otherwise></c:choose>...</h3>' });
}

function loadFinish(){
	$.unblockUI();
}
</script>
</body>
</html>