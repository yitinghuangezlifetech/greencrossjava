<%@ page language="java" contentType="text/html;  charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/common-util.tld" prefix="pi"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>最新消息</title>
<!-- 
<script src="ckeditor/ckeditor.js"></script>
 -->
</head>
<body>

	<!-- Page info section -->
	<section class="page-info-section set-bg" data-setbg="<c:url value = "../../img/green/page-info-bg/5.jpg"/>">
		<div class="container">
			<h2><c:choose><c:when test="${sessionScope.local == 'en'}">News</c:when><c:otherwise>最新消息</c:otherwise></c:choose></h2>
		</div>
	</section>
	<!-- Page info section end -->
	
  <c:choose>
    <c:when test="${sessionScope.local == 'en'}">
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-12">
				<div class="card">
					<div class="card-header ">
						<div class="container-fluid">
							<div class="row">
								<div class="col-sm-6 text-left">
									<b>News</b>
								</div>
								<div class="col-sm-6 text-right">
									<button type="button" id="backBtn" class="btn btn-default ">
										<i class="fas fa-chevron-left"> Go Back</i>
									</button>
								</div>
							</div>
						</div>
					</div>
					<div class="card-body">
						<form id="newsForm">
							<div class="container-fluid">
								<div class="row">
									<div class="col-sm-12">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proName>Subject</label>
											</div>
											<div class="col-sm-12">
											<!-- N:消息 -->
											<input type="hidden" name="newsType" value="N" /> 
											<!-- A:啟用 -->
											<input type="hidden" name="status" value="A" /> 
											${newsHtDto.titleEn}
											<!--  
												<input type="text" class="input" name="title"
													value="${newsHtDto.title}" style="width: 100%;" disable>
												<font class='errText form-text' id='err_title' color='red'></font>
											-->	
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proSpec>Content</label>
											</div>
											<div class="col-sm-12">
											${newsHtDto.contentEn} 
											<!--
												<input type="text" class="input" name="content"
													value="${newsHtDto.content}" style="width: 100%;" disable>
												<font class='errText form-text' id='err_content' color='red'></font>
											-->
											</div>
										</div>
									</div>
								</div>
 								
								<div class="row">
									<div class="col-sm-12">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proSpec>Contact</label>
											</div>
											<div class="col-sm-12">
											${newsHtDto.contact}
											<!-- 
												<input type="text" class="input" name="contact"
													value="${newsHtDto.contact}" style="width: 100%;" disable>
												<font class='errText form-text' id='err_proSpec' color='red'></font>
											-->
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proSellPrice>Email</label>
											</div>
											<div class="col-sm-12">
											${newsHtDto.email}
											<!--
												<input type="text" class="input" name="email"
													value="${newsHtDto.email}" style="width: 100%;" disable>
												<font class='errText form-text' id='err_proSellPrice'
													color='red'></font>
											-->		
											</div>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proInPrice>cell phone</label>
											</div>
											<div class="col-sm-12">
											${newsHtDto.tel}
											<!--
												<input type="text" class="input" name="tel"
													value="${newsHtDto.tel}" style="width: 100%;" disable>
												<font class='errText form-text' id='err_proInPrice'
													color='red'></font>
											-->		
											</div>
										</div>
									</div>
								</div>
								
								<div>${newsHtDto.html}</div>
								<!--
								<div style="text-align:left;">This is a test !</div><img src="https://www.penghu-nsa.gov.tw/FileDownload/Album/NotSet/20161012162551758864338.jpg" alt="" width="500" height="400" />
								
								<textarea id="html" name="html" rows="10" cols="30" style="height:440px" aria-label="editor">
									${newsHtDto.html}
								</textarea>	
								 -->
<!--								
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=statusLab>商品狀態*</label>
											</div>
											<div class="col-sm-12">
												<div id="statusLab" class="btn-group btn-group-toggle"
													data-toggle="buttons">
													<label id="status_act" class="btn radioSelect"> <input
														type="radio"> <i class="fas fa-check">啟用</i>
													</label> <label id="status_stp" class="btn radioSelect redSel">
														<input type="radio"> <i class="fas fa-times">停用</i>
													</label>

												</div>
												<input type="hidden" id="status" name="status"
													value="${productDto.status}"> <font
													class='errText form-text' id='err_status' color='red'></font>
											</div>
										</div>

									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=prodClass>商品分類</label>
											</div>
											<div class="col-sm-12">
												<select id='prodClass' name='prodClass' multiple="multiple"
													data-placeholder="請選擇商品分類...">
												</select> <font class='errText form-text' id='err_prodClass'
													color='red'></font>
											</div>
										</div>

									</div>
								</div>
								<div class="row" id="proHtmlDiv">
									<div class="col-sm-12">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=prodClass>商品描述</label>
											</div>
											<div class="col-sm-12">

												<textarea id="proHtml" name="proHtml" rows="10" cols="30"
													style="height: 440px" aria-label="editor">
													${productDto.proHtml}
												</textarea>
											</div>
										</div>
									</div>
								</div>
-->								
							</div>
							<!--  -
							<textarea id="about"  name="about"  cols="50" rows="12" ></textarea>
							  <script type="text/javascript">
      							 CKEDITOR.replace("about");
      							 //CKEDITOR.replace( 'about', {});
      			    		 </script>		
      			    		  -->						
							<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
							<input type="hidden" id="id" name="id" value="${newsHtDto.id}">
						</form>
						<form id="nothing" action=""></form>
					</div>
				</div>
			</div>
		</div>
	</div>
	</c:when>
	<c:otherwise>
	
	
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-12">
				<div class="card">
					<div class="card-header ">
						<div class="container-fluid">
							<div class="row">
								<div class="col-sm-6 text-left">
									<b>最新消息</b>
								</div>
								<div class="col-sm-6 text-right">
									<button type="button" id="backBtn" class="btn btn-default ">
										<i class="fas fa-chevron-left"> 回消息列表頁</i>
									</button>
								</div>
							</div>
						</div>
					</div>
					<div class="card-body">
						<form id="newsForm">
							<div class="container-fluid">
								<div class="row">
									<div class="col-sm-12">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proName>標題</label>
											</div>
											<div class="col-sm-12">
											<!-- N:消息 -->
											<input type="hidden" name="newsType" value="N" /> 
											<!-- A:啟用 -->
											<input type="hidden" name="status" value="A" /> 
											${newsHtDto.title}
											<!--  
												<input type="text" class="input" name="title"
													value="${newsHtDto.title}" style="width: 100%;" disable>
												<font class='errText form-text' id='err_title' color='red'></font>
											-->	
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proSpec>內容</label>
											</div>
											<div class="col-sm-12">
											${newsHtDto.content} 
											<!--
												<input type="text" class="input" name="content"
													value="${newsHtDto.content}" style="width: 100%;" disable>
												<font class='errText form-text' id='err_content' color='red'></font>
											-->
											</div>
										</div>
									</div>
								</div>
 								
								<div class="row">
									<div class="col-sm-12">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proSpec>聯絡人資訊</label>
											</div>
											<div class="col-sm-12">
											${newsHtDto.contact}
											<!-- 
												<input type="text" class="input" name="contact"
													value="${newsHtDto.contact}" style="width: 100%;" disable>
												<font class='errText form-text' id='err_proSpec' color='red'></font>
											-->
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proSellPrice>信箱</label>
											</div>
											<div class="col-sm-12">
											${newsHtDto.email}
											<!--
												<input type="text" class="input" name="email"
													value="${newsHtDto.email}" style="width: 100%;" disable>
												<font class='errText form-text' id='err_proSellPrice'
													color='red'></font>
											-->		
											</div>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proInPrice>手機電話</label>
											</div>
											<div class="col-sm-12">
											${newsHtDto.tel}
											<!--
												<input type="text" class="input" name="tel"
													value="${newsHtDto.tel}" style="width: 100%;" disable>
												<font class='errText form-text' id='err_proInPrice'
													color='red'></font>
											-->		
											</div>
										</div>
									</div>
								</div>
								
								<div>${newsHtDto.html}</div>
								<!--
								<div style="text-align:left;">This is a test !</div><img src="https://www.penghu-nsa.gov.tw/FileDownload/Album/NotSet/20161012162551758864338.jpg" alt="" width="500" height="400" />
								
								<textarea id="html" name="html" rows="10" cols="30" style="height:440px" aria-label="editor">
									${newsHtDto.html}
								</textarea>	
								 -->
<!--								
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=statusLab>商品狀態*</label>
											</div>
											<div class="col-sm-12">
												<div id="statusLab" class="btn-group btn-group-toggle"
													data-toggle="buttons">
													<label id="status_act" class="btn radioSelect"> <input
														type="radio"> <i class="fas fa-check">啟用</i>
													</label> <label id="status_stp" class="btn radioSelect redSel">
														<input type="radio"> <i class="fas fa-times">停用</i>
													</label>

												</div>
												<input type="hidden" id="status" name="status"
													value="${productDto.status}"> <font
													class='errText form-text' id='err_status' color='red'></font>
											</div>
										</div>

									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=prodClass>商品分類</label>
											</div>
											<div class="col-sm-12">
												<select id='prodClass' name='prodClass' multiple="multiple"
													data-placeholder="請選擇商品分類...">
												</select> <font class='errText form-text' id='err_prodClass'
													color='red'></font>
											</div>
										</div>

									</div>
								</div>
								<div class="row" id="proHtmlDiv">
									<div class="col-sm-12">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=prodClass>商品描述</label>
											</div>
											<div class="col-sm-12">

												<textarea id="proHtml" name="proHtml" rows="10" cols="30"
													style="height: 440px" aria-label="editor">
													${productDto.proHtml}
												</textarea>
											</div>
										</div>
									</div>
								</div>
-->								
							</div>
							<!--  -
							<textarea id="about"  name="about"  cols="50" rows="12" ></textarea>
							  <script type="text/javascript">
      							 CKEDITOR.replace("about");
      							 //CKEDITOR.replace( 'about', {});
      			    		 </script>		
      			    		  -->						
							<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
							<input type="hidden" id="id" name="id" value="${newsHtDto.id}">
						</form>
						<form id="nothing" action=""></form>
					</div>
				</div>
			</div>
		</div>
	</div>	
	
	
	
	
	</c:otherwise>
</c:choose>	

<script>
		$(document).ready(
				function() {
					
					
					$("#html").kendoEditor({ resizable: {
                       //content: true,
                        //toolbar: true
                    }});
					
					
							$('#saveBtn').click(function(e) {
								save();
							});
							
							$('#backBtn')
									.click(
											function(e) {
// 												$('#newsForm')
// 														.attr('action',
// 																'<c:url value = "/home/news/newsList"/>');
// 												$('#newsForm').attr('method',
// 														'post');
// 												$('#newsForm').submit();
												$('#nothing')
												.attr('action',
														'<c:url value = "/home/news/newsList"/>');
										$('#nothing').attr('method',
												'post');
										$('#nothing').submit();
											});

							});
		
		function save() {
			$.ajax({
						type : "POST",
						url : '<c:url value = "/admin/news/ajaxSaveHt"/>',
						data : $("#newsForm").serialize(),// serializes the form's elements.
						success : function(result) {
 							if (checkAjaxResp(result)) {
// 								$('#id').val(result.data.prodId);
// 								$('#prodPicProNo').val(result.data.prodId);
// 								checkMode();

 							}

						}
					});
		}
		
	</script>
          
</body>
</html>