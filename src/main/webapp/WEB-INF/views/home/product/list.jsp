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
			<h2><c:choose><c:when test="${sessionScope.local == 'en'}">Product Information</c:when><c:otherwise>產品訊息</c:otherwise></c:choose></h2>
		</div>
	</section>
	<!-- Page info section end -->
	
	<!-- Product section -->
	<section class="product-section spad">
		<div class="container">
			<div class="row">  
			
			
				<div class="col-xl-3 col-lg-3 col-md-4 sort">
					<h4><c:choose><c:when test="${sessionScope.local == 'en'}">Product Categories</c:when><c:otherwise>商品分類</c:otherwise></c:choose></h4>
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
				
				<div class="col-xl-9 col-lg-9 col-md-8 col-sm-12 pdt-box">
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
	
	
	<section class="intro-section spad">
		<div class="container">
			<!-- <div class="row" >
				<div class="col-sm-12">
					<br/>
					<div class="input-group col-md-12">
						
					      <input class="form-control py-2" type="search"  placeholder="<c:choose><c:when test="${sessionScope.local == 'en'}">Please enter the product name keyword....</c:when><c:otherwise>請輸入商品名稱關鍵字....</c:otherwise></c:choose>" value="" id="example-search-input">
					      <span class="input-group-append">
					        <button class="btn btn-outline-secondary" id="searchTextBut" type="button">
					            <i class="fa fa-search"></i>
					        </button>
					      </span>   
					</div>   
					<br/>    
				</div>   
				       
			</div>   -->
			   
			<!--<div class="row classDropdown">           
				<div class="col-sm-12">
					<br/>
					<div class=" form-group row">
							<label for="dropdowntree"  class="col-lg-2 col-md-2 col-sm-12 col-form-label autoRightLeft"><c:choose><c:when test="${sessionScope.local == 'en'}">Product Categories</c:when><c:otherwise>商品分類</c:otherwise></c:choose>：</label>
							<div class="col-lg-9 col-md-9 col-sm-12 "> 
								<input  id="dropdowntree" class="form-control" style="width: 100%;" />
							</div>
							<div class="col-lg-1 col-md-1 col-sm-12 "> 
							</div>   
					</div>
					<br/> 
				</div>
			</div>  -->
			   
			
			
			
			<div class="row">  
				<div class="col-12 d-flex justify-content-center mar-bottom">
					<button type="button" id="backPageBtn" class="btn btn-success ">
						<i class="fas fa-angle-left" id="topSaveText"> </i><c:choose><c:when test="${sessionScope.local == 'en'}">Previous page</c:when><c:otherwise>上一頁</c:otherwise></c:choose>
					</button>
					&nbsp;
			 		<input type="text" class="input" id="nowPageInput" value="1" style="width:60px;" onkeyup="return ValidateNumber($(this),value)">
					&nbsp;<font class="mar-top" style="text-align: center; font-size:16px;">/</font>&nbsp;
					<input type="text" class="input" id="totalPageInput" value="" style="width:60px;" disabled>
					&nbsp;  
			 		<button type="button" id="nextPageBtn" class="btn btn-success ">
						<c:choose><c:when test="${sessionScope.local == 'en'}">Next page</c:when><c:otherwise>下一頁</c:otherwise></c:choose><i class="fas fa-angle-right" id="topSaveText"> </i>
			 		</button>					
				</div>
				<div class="col-12 d-flex justify-content-center mar-bottom">
					<c:choose><c:when test="${sessionScope.local == 'en'}">Total&nbsp;</c:when><c:otherwise>共</c:otherwise></c:choose><font id='dataSizeInput'></font><c:choose><c:when test="${sessionScope.local == 'en'}">&nbsp;Datas,Display up to&nbsp;</c:when><c:otherwise>筆資料，每頁最多顯示</c:otherwise></c:choose><font id='onePageDataSizeInput'>${onePageDataSize}</font><c:choose><c:when test="${sessionScope.local == 'en'}">&nbsp;data per page</c:when><c:otherwise>筆</c:otherwise></c:choose>
				</div>
			</div>
		 	       
		</div>
	</section>
	
	    
	<form id='prodFrom'>
		<input type="hidden" id="dataSize" name="dataSize" value="">
		<input type="hidden" id="onePageDataSize" name="onePageDataSize" value="${onePageDataSize}">
		<input type="hidden" id="nowPage" name="nowPage" value="1">
		<input type="hidden" id="totalPage" name="totalPage" value="">
		<input type="hidden" id="productClassId" name="productClassId" value="${productClassId}">
		<input type="hidden" id="searchProductText" name="searchProductText" value="">
	</form>    
	
	
	<form id='shoppingCarFrom'>
		<input type="hidden" id="shoppingCarId" name="id" value="">
		<c:choose>
			<c:when test="${sessionScope.isLogin == 'N'}">
				<input type="hidden" id="shoppingUserId" name="userId" value="">
			</c:when>
			<c:otherwise>
				<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
				<input type="hidden" id="shoppingUserId" name="userId" value="${sessionScope.userProf.id}">
			</c:otherwise>
		</c:choose>
		
		<input type="hidden" id="shoppingProNo" name="proNo" value="">
		<input type="hidden" id="shoppingOrderNumber" name="orderNumber" value="1">
		
		
	</form>
	
	<script>
		$(document).ready(function() {
			$('#prodClassDescDiv').hide();
			$('#noDataShow').hide();
			global.canChangePage=false;
			var prodClassDropdowntree = ${prodClassDropdowntree};
			$("#dropdowntree").kendoDropDownTree({
                placeholder: "<c:choose><c:when test="${sessionScope.local == 'en'}">You can choose a product category here</c:when><c:otherwise>你可以在此選擇商品分類</c:otherwise></c:choose>...",  
                autoClose: true,
                dataSource: prodClassDropdowntree,
                change: onChange
            });
			
			getProduct();
			
			$('#nextPageBtn').click(function(e) {
				nextPage();
			});
			
			$('#backPageBtn').click(function(e) {
				 backPage();  
			});
			
			$('#searchTextBut').click(function(e) {
				search();
			});
			
			$('#nowPageInput').blur(function() {
				 if(($('#nowPageInput').val().trim())==''){
					 $('#nowPageInput').val($('#nowPage').val());
					 
				 }else{
					var nowPageInput = parseInt($('#nowPageInput').val());
					var totalPageInput  = parseInt($('#totalPageInput').val());
					if(nowPageInput>totalPageInput || nowPageInput<1){
						 $('#nowPageInput').val($('#nowPage').val());
						return;
					}
					$('#nowPage').val($('#nowPageInput').val());
					getProduct();
				 }
				
			});
		});
		
		
		
		function getProduct(){
			global.canChangePage=false;
			//loading();
			$.ajax({
				type : "POST",
				url : '<c:url value = "/home/product/ajaxGetProduct"/>',
				data : $("#prodFrom").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {
						console.log(result.data.dataSize);
						cleanProduct();
						$('#totalPage').val(result.data.totalPage);
						$('#totalPageInput').val(result.data.totalPage);
						$('#dataSize').val(result.data.dataSize);
		  				$('#dataSizeInput').text(result.data.dataSize);
						$.each(result.data.data,
								function(i, obj) {
									addProduct(obj);
								}
		   				);
						if(result.data.dataSize=='0'){
							$('#noDataShow').show();
						}else{
							$('#noDataShow').hide();
						}
						global.canChangePage=true;
						
						if(result.data.classDesc ==''){
							$('#prodClassDesc').html('');
							
							$('#prodClassDescDiv').hide();
						}else{
						
							$('#prodClassDesc').html(htmlDecode(result.data.classDesc));
							$('#prodClassDescDiv').show();
						}
						
						
						
						
					}
					loadFinish();
				}
			});
		}   
		  
		
		function addProduct(prod){
			//prodShowPl
			var url = '<c:url value = "/ajaxGetProductPic"/>';
			var temp = 
			'<div class="col-xl-4 col-lg-4 col-md-6 col-sm-12 productItem">                                                                         ' +
			'	<div class="card mar-bottom" style="width:100%;">                                                                                   ' +
			'		 <div class="card-body">                                                                                                        ' +
			'		   <div class="card-title">                                                                                                     ' +
			'			   <div class="row">                                                                                                        ' +
			'			   		<div class="col-xl-12 col-lg-12 col-md-12 col-sm-4 col-6 imgFullwhDiv"  >                                           ' +
			'			   			<a href=\'<c:url value = "/home/product/product"/>/'+prod.pro_no+'\'><img class="prodImg" src="'+url+'/'+prod.pro_main_pict+'"   ></a>                                                   ' +
			'			   		</div>                                                                                                              ' +
			'			   		<div class="col-xl-12 col-lg-12 col-md-12 col-sm-8 col-6"  >                                                        ' +
			'			   			<div class="row">                                                                                               ' +
			'			   				<div class="col-12 mar-top"  >                                                                              ' +
			'			   					<div class="prod-name" ><a href=\'<c:url value = "/home/product/product"/>/'+prod.pro_no+'\'  style="color:#000000;">'+prod.pro_name+'</a></div>                                                         ' +
			'			   				</div>                                                                                                      ' +
			'			   				<div class="col-12"  >                                                                              ' +
	 		'			   					<hr/>                                                         ' +
			'			   				</div>                                                                                                      ' +
			'			   				<div class="col-12"  >                                                                              ' +
			'			   					<div class="prod-spec"  ><c:choose><c:when test="${sessionScope.local == 'en'}"> Product specifications</c:when><c:otherwise>商品規格</c:otherwise></c:choose>：'+prod.pro_spec+'</div>                                                        ' +
			'			   				</div>                                                                                                      ' +			
			'			   				<div class="col-12"  >                                                                              ' +
			//'			   					<div class="prod-code"  ><c:choose><c:when test="${sessionScope.local == 'en'}"> Product code</c:when><c:otherwise>代號</c:otherwise></c:choose>：'+prod.pro_code+'</div>                                                        ' +
			'			   				</div>                                                                                                      ' +			
			'			   				<div class="col-12 "  >                                                                              ' +
			//'			   					<div class="prod-price" ><c:choose><c:when test="${sessionScope.local == 'en'}"> Prilce：NT $</c:when><c:otherwise>售價：新台幣 </c:otherwise></c:choose>'+prod.pro_sell_price+'<c:choose><c:when test="${sessionScope.local == 'en'}">. </c:when><c:otherwise>元</c:otherwise></c:choose></div>                                                ' +
			'			   				</div>                                                                                                      ' +
			'							<p class="card-text">                                                                                       ' +
			'								<div class="col-12 mar-top" >	                                                                        ' +
			//'									<button onclick="addToShopCar(\''+prod.pro_no+'\')" type="button"  class="btn btn-warning " style="width:100%;">                     ' +
			//'										<i class="fas fa-cart-plus"><c:choose><c:when test="${sessionScope.local == 'en'}">add to Shopping Cart</c:when><c:otherwise>加入購物車</c:otherwise></c:choose></i>                                                        ' +
			//'									</button>                                                                                           ' +
			'								</div>	  							                                                                    ' +
			'							</p>                                                                                                        ' +
			'				   		</div>			   			                                                                                    ' +
			'			   		</div>			   		                                                                                            ' +
			'			   </div>  			                                                                                                        ' +
			'		   </div>		                                                                                                                ' +
			'		 </div>                                                                                                                         ' +
			'	</div>                                                                                                                              ' +
			'</div>                                                                                                                                 ';
			
			var or = $('#prodShowPl').html()+temp;
			$('#prodShowPl').html(or);
		}
		
		function addToShopCar(proNo){
			$('#shoppingProNo').val(proNo);
			addToShoppingCar($('#shoppingCarFrom'));
		}
		
		function nextPage(){
			if(!global.canChangePage){
				return;
			}
			var nowPage= parseInt($('#nowPage').val());
			var totalPage= parseInt($('#totalPage').val());
			
			if((nowPage+1)>totalPage){
				return;
			}
			nowPage++;
	  		$('#nowPage').val(nowPage);
			$('#nowPageInput').val(nowPage);
			getProduct();   
		}
		
		function backPage(){
			if(!global.canChangePage){
				return;
			}
			var nowPage= parseInt($('#nowPage').val());
			var totalPage= parseInt($('#totalPage').val());
			
			if((nowPage-1)<1){
				return;
			}
			nowPage--;
			$('#nowPage').val(nowPage);
			$('#nowPageInput').val(nowPage);
			getProduct();
		}
		
		function cleanProduct(){
			$('#prodShowPl').html('');
		}
		
		
		function search(){
			resetForm();
			$('#searchProductText').val($('#example-search-input').val());
			getProduct();
		}
		
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
			 getProduct();
			 
		}
		
		function loading(){
			$.blockUI({ message: '<h3> <c:choose><c:when test="${sessionScope.local == 'en'}">Data searching</c:when><c:otherwise>資料搜尋中</c:otherwise></c:choose>...</h3>' });
		}
		
		function loadFinish(){
			$.unblockUI();
		}
		
		function onChange(){
			console.log($('#dropdowntree').val());
			selectClass($('#dropdowntree').val());
		}
		
		function htmlDecode(input)
		{
		  var doc = new DOMParser().parseFromString(input, "text/html");
		  return doc.documentElement.textContent;
		}

		var entityMap = {
				  '&': '&amp;',
				  '<': '&lt;',
				  '>': '&gt;',
				  '"': '&quot;',
				  "'": '&#39;',
				  '/': '&#x2F;',
				  '`': '&#x60;',
				  '=': '&#x3D;'
				};

				function escapeHtml (string) {
				  return String(string).replace(/[&<>"'`=\/]/g, function (s) {
				    return entityMap[s];
				  });
				}
	</script>
	
	
</body>

</html>