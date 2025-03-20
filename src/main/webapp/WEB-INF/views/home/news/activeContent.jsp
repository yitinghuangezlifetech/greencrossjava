<%@ page language="java" contentType="text/html;  charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/common-util.tld" prefix="pi"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>最新活動</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-12">
				<div class="card">
					<div class="card-header ">
						<div class="container-fluid">
							<div class="row">
								<div class="col-sm-6 text-left">
									${sessionScope.funcName}-最新活動
								</div>
								<div class="col-sm-6 text-right">
									<button type="button" id="backBtn" class="btn btn-default ">
										<i class="fas fa-chevron-left"> 回活動列表頁</i>
									</button>
								</div>
							</div>
						</div>
					</div>
					<div class="card-body">
						<form id="activeForm">
							<!-- A:活動 -->
							<input type="hidden" name="newsType" value="A" /> 
							<!-- A:啟用 -->
							<input type="hidden" name="status" value="A" /> 
							<div class="container-fluid">
<!--  							<div class="row">
									<div class="col-sm-12">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proName>標題*</label>
											</div>
											<div class="col-sm-12">
											
												<input type="text" class="input" name="title"
													value="${newsHtDto.title}" style="width: 100%;" readonly>
												<font class='errText form-text' id='err_title' color='red'></font>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proSpec>內容*</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="content"
													value="${newsHtDto.content}" style="width: 100%;" readonly>
												<font class='errText form-text' id='err_content' color='red'></font>
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
												<input type="text" class="input" name="contact"
													value="${newsHtDto.contact}" style="width: 100%;" readonly>
												<font class='errText form-text' id='err_proSpec' color='red'></font>
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
												<input type="text" class="input" name="email"
													value="${newsHtDto.email}" style="width: 100%;" readonly>
												<font class='errText form-text' id='err_proSellPrice'
													color='red'></font>
											</div>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proInPrice>手機電話</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="tel"
													value="${newsHtDto.tel}" style="width: 100%;" readonly>
												<font class='errText form-text' id='err_proInPrice'
													color='red'></font>
											</div>
										</div>
									</div>
								</div>								
						
								<div class="row">
									<div class="col-sm-12">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proSpec>商品規格*</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="proSpec"
													value="${productDto.proSpec}" style="width: 100%;">
												<font class='errText form-text' id='err_proSpec' color='red'></font>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proSellPrice>商品售價*</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="proSellPrice"
													value="${productDto.proSellPrice}" style="width: 100%;">
												<font class='errText form-text' id='err_proSellPrice'
													color='red'></font>
											</div>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proInPrice>商品進價</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="proInPrice"
													value="${productDto.proInPrice}" style="width: 100%;">
												<font class='errText form-text' id='err_proInPrice'
													color='red'></font>
											</div>
										</div>
									</div>
								</div>
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
							<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
							<input type="hidden" id="id" name="id" value="${newsHtDto.id}">
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
<script>
		$(document).ready(
				function() {
							$('#saveBtn').click(function(e) {
								save();
							});
							
							$('#backBtn')
									.click(
											function(e) {
												$('#activeForm')
														.attr('action',
																'<c:url value = "/home/news/activeList"/>');
												$('#activeForm').attr('method',
														'post');
												$('#activeForm').submit();
											});

							});
		
		function save() {
			$.ajax({
						type : "POST",
						url : '<c:url value = "/admin/news/ajaxSaveHt"/>',
						data : $("#activeForm").serialize(),// serializes the form's elements.
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