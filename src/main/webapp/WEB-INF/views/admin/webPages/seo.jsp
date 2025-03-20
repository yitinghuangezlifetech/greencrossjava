<%@ page language="java" contentType="text/html;  charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/common-util.tld" prefix="pi"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Spring 4 MVC -HelloWorld</title>

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
									${sessionScope.funcName}-新增修改
								</div>
								<div class="col-sm-6 text-right">
									<button type="button" id="saveBtn" class="btn btn-success ">
										<i class="fas fa-save" id="topSaveText"> 儲存</i>
									</button>
								</div>
							</div>
						</div>
					</div>
					<div class="card-body">
						<form id="webPagesForm">
							<input type="hidden" id="pageType" name="pageType" value="${webPagesDto.pageType}">
							<input type="hidden" id='pageSortNo' name='pageSortNo'  value="${webPagesDlSortNo}"  >
							<div class="container-fluid">
								
								<div class="col-sm-12">
									<div class="form-group ">
										<div class="row">
											<div class="col-sm-2">
												<label for=pageName>頁面名稱*</label>
											</div>
											<div class="col-sm-4">
												<input type="text" class="input" name="pageName"
													value="${webPagesDto.pageName}" style="width: 100%;">
												<font class='errText form-text' id='err_pageName' color='red'></font>
											</div>
											<div class="col-sm-2">
												<label for=pageNameEn>頁面名稱(英文)</label>
											</div>
											<div class="col-sm-4">
												<input type="text" class="input" name="pageNameEn"
													value="${webPagesDto.pageNameEn}" style="width: 100%;">
												<font class='errText form-text' id='err_pageNameEn' color='red'></font>
											</div>
										</div>
									</div>
									</div>
								
								<div class="col-sm-12">
									<div class="form-group ">
										<div class="row">
										</div>
									</div>
								</div>
								
								<div class="col-sm-12">
									<div class="form-group ">
										<div class="row">
											<div class="col-sm-2">
												<label for=pageTitle>頁面路徑*</label>
											</div>
											<div class="col-sm-4">
												<input type="text" class="input" name="pageUrl"
													value="${webPagesDto.pageUrl}" style="width: 100%;">
												<font class='errText form-text' id='err_pageUrl' color='red'></font>
											</div>
											<div class="col-sm-2">
												<label for=pageTitle>SEO關鍵字*</label>
											</div>
											<div class="col-sm-4">
												<input type="text" class="input" name="pageSeo"
													value="${webPagesDto.pageSeo}" style="width: 100%;">
												<font class='errText form-text' id='err_page_seo' color='red'></font>
											</div>
										</div>
									</div>
								</div>
								
								<div class="col-sm-12">
									<div class="form-group ">
										<div class="row">
											<div class="col-sm-2">
												<label for=pageTitle>頁面標題*</label>
											</div>
											<div class="col-sm-4">
												<input type="text" class="input" name="pageTitle"
													value="${webPagesDto.pageTitle}" style="width: 100%;">
												<font class='errText form-text' id='err_pageTitle' color='red'></font>
											</div>
											<div class="col-sm-2">
												<label for=pageTitleEn>頁面標題(英文)*</label>
											</div>
											<div class="col-sm-4">
												<input type="text" class="input" name="pageTitleEn"
													value="${webPagesDto.pageTitleEn}" style="width: 100%;">
												<font class='errText form-text' id='err_pageTitleEn' color='red'></font>
											</div>
										</div>
									</div>
								</div>
								<div class="col-sm-12">
									<div class="form-group ">
										<div class="row">
											<div class="col-sm-2">
												<label for=proSellPrice>GA分析</label>
											</div>
											<div class="col-sm-4">
												<input type="text" class="input" name="proSellPrice"
													value="${webPagesDto.pageTitleEn}" style="width: 100%;">
												<font class='errText form-text' id='err_proSellPrice'
													color='red'></font>
											</div>
											<div class="col-sm-2">
												<label for=proInPrice>臉書像素</label>
											</div>
											<div class="col-sm-4">
												<input type="text" class="input" name="proInPrice"
													value="${webPagesDto.pageTitleEn}" style="width: 100%;">
												<font class='errText form-text' id='err_proInPrice'
													color='red'></font>
											</div>
										</div>
									</div>
								</div>
								<!-- div class="col-sm-12">
									<div class="form-group ">
										<div class="row">
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
													value="${webPagesDto.status}"> <font
													class='errText form-text' id='err_status' color='red'></font>
											</div>
										</div>
									</div>
								</div -->
								<div class="col-sm-12">
									<div class="form-group ">
										<div class="row" id="pageContentDiv">
											<div class="col-sm-6">
												<label for=pageContent>頁面</label>
											</div>
											<div class="col-sm-6">
												<label for=pageContentEn>頁面(英文)</label>
											</div>
										</div>
									</div>
								</div>
								<div class="col-sm-12">
									<div class="form-group ">
										<div class="row" id="pageContentDiv">
											<div class="col-sm-6">
												<textarea id="pageContent" name="pageContent" rows="10" cols="30"
													style="height: 440px" aria-label="editor">
													${webPagesDto.pageContent}
												</textarea>
											</div>
											<div class="col-sm-6">
												<textarea id="pageContentEn" name="pageContentEn" rows="10" cols="30"
													style="height: 440px" aria-label="editor">
													${webPagesDto.pageContentEn}
												</textarea>
											</div>
										</div>
									</div>
								</div>
							</div>
							<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
							<input type="hidden" id="id" name="id" value="${webPagesDto.id}">
						</form>
					</div>
					<!-- KendoUI grid div -->
					<div class="container-fluid">
						<!-- button type="button" id="addBtn" class="btn btn-primary ">
							<span class="glyphicon glyphicon-plus">新增
						</button -->
						<div id="grid"></div>
					</div>
					<div class="form-group "></div>
					<!-- KendoUI grid div END -->
				</div>
			</div>
		</div>
	</div>


	<!-- KendoUI 跳窗 div -->
	<div id="formModal">
		<div class="panel panel-bordered">
			<form id="inputForm">
				<input type="hidden" id="id" name="id" value="">
				<input type="hidden" id='pageId' name='pageId'  value=""  >
				<!--input type="hidden" id='pageDiv' name='pageDiv'  value=""  >
				<input type="hidden" id='pageSortNo' name='pageSortNo'  value=""  -->
				<input type="hidden" id="checkSessionUserId" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
									
				<div class="container-fluid">
					<!-- 標題區 -->
					<div class="form-group ">
						<div class="col-sm-10 panel panel-bordered">
							<header class="panel-heading">
							<h3 class="panel-title">HTML編輯區</h3>
							</header>
						</div>
					</div>
					<!-- 新增或修改 -->
					<input type="hidden" class="form-control input-sm" id="modifyType" name="modifyType" value="">
					
					<!-- 輸入區 -->
					<div class="col-sm-12">
						<div class="form-group ">
							<div class="row">
								<div class="col-sm-2">
									<label for=pageTitle>區塊名稱*</label>
								</div>
								<div class="col-sm-3">
									<input type="text" class="input" name="pageDivName"
										value="${pageDivName}" style="width: 100%;">
									<font class='errText form-text' id='err_pageDivName' color='red'></font>
								</div>
								<div class="col-sm-2">
									<label for=pageTitleEn>區塊名稱(英文)*</label>
								</div>
								<div class="col-sm-3">
									<input type="text" class="input" name="pageDivNameEn"
										value="${pageDivNameEn}" style="width: 100%;">
									<font class='errText form-text' id='err_pageTitleEn' color='red'></font>
								</div>
							</div>
						</div>
					</div>
					
					<div class="col-sm-12">
						<div class="form-group ">
							<div class="row">
								<div class="col-sm-2">
									<label for=pageDiv>呈現區塊*</label>
								</div>
								<div class="col-sm-3">
									<input type="text" class="input" id='pageDiv' name="pageDiv"
										value="${pageDiv}" style="width: 100%;">
									<font class='errText form-text' id='err_pageDiv' color='red'></font>
								</div>
								<div class="col-sm-2">
									<label for=pageSortNo>排序</label>
								</div>
								<div class="col-sm-3">
									<input type="text" class="input" id='pageSortNo' name="pageSortNo"
										value="${pageSortNo}" style="width: 100%;">
									<font class='errText form-text' id='err_pageSortNo' color='red'></font>
								</div>
							</div>
						</div>
					</div>
					
					<div class="col-sm-12">
						<div class="form-group ">
							<div class="row" id="pageHtmlDiv">
								<div class="col-sm-11">
									<textarea id="pageHtml" name="pageHtml" rows="10" cols="30"
										style="height: 440px" aria-label="editor">
										${webPagesDto.pageHtml}
									</textarea>
								</div>
								<div class="col-sm-11">
									<textarea id="pageHtmlEn" name="pageHtmlEn" rows="10" cols="30"
										style="height: 440px" aria-label="editor">
										${webPagesDto.pageHtmlEn}
									</textarea>
								</div>
							</div>
						</div>
					</div>
					
					<!-- my_btn 區塊 -->
					<div class="form-group ">
						<div class="my_btn">
							<div class="customBtn right ">
								<ul>
									<button type="button" id="formModelSend" class="btn btn-primary ">
										<span class="glyphicon glyphicon-ok">確定
									</button>
									<button type="button" id="formModelClose"
										class="btn btn-primary ">
										<span class="glyphicon glyphicon-remove"></span>關閉
									</button>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<!-- KendoUI 跳窗 div END -->
	<form id="backForm">
		<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
	</form>


	<script>
		$(document).ready(function() {
			
			$('#addBtn').click(function(e){
				var pageSortNo = $('#webPagesForm input[name=pageSortNo]').val();
				pageSortNo++;
				var id = $('#id').val();
				$('#pageDiv').val("htmlDiv"+pageSortNo);
				$('#inputForm input[name=pageSortNo]').val(pageSortNo);
				pageSortNo--;
				$('#webPagesForm input[name=pageSortNo]').val(pageSortNo);
				//alert($('#webPagesForm input[name=pageSortNo]').val());
				add(id);
			});

			$('#saveBtn').click(function(e) {
				save();
			});
			
			
			

			
			global.formModel = $("#formModal").kendoWindow({
				modal : true,
				title : false,
				visible : false,
				iframe : false,
				width:1400
			}).data("kendoWindow");
			
			
			$('#formModelSend').click(function(e) {
				ajaxdlFormModal();
			});
			
			$('#formModelClose').click(function(e) {
				closeFormModal();
			});
			
			checkMode();
		});
    
		function add(id){
			$('#pageId').val(id);
			$('#modifyType').val('add');
			openFormModal();
		}
		
		// 打開 kendoWindow 
		function openFormModal(){
			global.formModel.center().open();
		}
		
		function save() {
			$.ajax({
				type : "POST",
				url : '<c:url value = "/admin/webPages/ajaxSaveWebPages"/>',
				data : $("#webPagesForm").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {
						$('#id').val(result.data.id);
						checkMode();
					}
				}
			});
		}
		
		function ajaxdlFormModal() {

			$.ajax({
				type : "POST",
				url : '<c:url value = "/admin/webPages/ajaxSaveWebPagesDl"/>',
				data : $("#inputForm").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {
						var pageSortNo = $('#webPagesForm input[name=pageSortNo]').val();
						pageSortNo++;
						$('#webPagesForm input[name=pageSortNo]').val(pageSortNo);
						closeFormModal();
						refreshGird();
					}
				}
			});
		}
		
		function refreshGird() {
			global.dataSource.read();
		}
		
		// 關閉 kendoWindow 
		function closeFormModal(){
			//var pageSortNo = $('#pageSortNo').val();
			//$('#pageSortNo').val(pageSortNo--);
			//alert($('#pageSortNo').val());
			
			global.formModel.center().close();
			resetForm();
		}
		
		function resetForm(){
			$('#inputForm input[name=id]').val('');
			$('#inputForm input[name=pageId]').val('');
			$('#inputForm input[name=pageDivName]').val('');
			$('#inputForm input[name=pageDivNameEn]').val('');
			$('#inputForm input[name=pageDiv]').val('');
			$('#inputForm input[name=pageSortNo]').val('');
			var pageHtml = $("#pageHtml").data("kendoEditor");
			pageHtml.value('');
			var pageHtmlEn = $("#pageHtmlEn").data("kendoEditor");
			pageHtmlEn.value('');
			
	
		}
		
		function edit(id){
			
			$('#inputForm input[name=id]').val(id);
			//alert($('#inputForm input[name=id]').val());
			$('#status').val();
			$('#modifyType').val('modify');
			$.ajax({   
				type : "POST",
				dataType:"json",
				url : '<c:url value = "/admin/webPages/ajaxGetWebPagesDlById"/>',
				data : $("#inputForm").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {
						$('#inputForm input[name=id]').val(result.data.id);
						$('#inputForm input[name=pageId]').val(result.data.pageId);
						$('#inputForm input[name=pageDivName]').val(result.data.pageDivName);
						$('#inputForm input[name=pageDivNameEn]').val(result.data.pageDivNameEn);
						$('#inputForm input[name=pageDiv]').val(result.data.pageDiv);
						$('#inputForm input[name=pageSortNo]').val(result.data.pageSortNo);
						var pageHtml = $("#pageHtml").data("kendoEditor");
						pageHtml.value(result.data.pageHtml);
						var pageHtmlEn = $("#pageHtmlEn").data("kendoEditor");
						pageHtmlEn.value(result.data.pageHtmlEn);
//						$('#paramCode').prop('disabled', true);
//						if(result.data.status == '1'){
//							global.switchInstance.check(true);
//						}else{
//							global.switchInstance.check(false);
//x`						}
						openFormModal();
					} 

				}
			});
		}
		
		function remove(id){
			warningConfirm(
					'確定刪除嗎?',
					'本次操作將刪除該法規屬性明細資料，可能影響部分系統運作，是否繼續？',
					'是',
					'否',
					function(confirm) {
						// 點擊是
						if (confirm) {			
							$('#inputForm input[name=id]').val(id);
							$('#status').val("");
							$.ajax({   
								type : "POST",
								dataType:"json",
								url : '<c:url value = "/admin/webPages/ajaxRemoveWebPagesDl"/>',
								data : $("#inputForm").serialize(),// serializes the form's elements.
								success : function(result) {
									if (checkAjaxResp(result)) {
										$('#searchName').val('');
										var pageSortNo = $('#webPagesForm input[name=pageSortNo]').val();
										pageSortNo--;
										$('#webPagesForm input[name=pageSortNo]').val(pageSortNo);
										global.dataSource.read();
										closeFormModal();
									} 

								}
							});
						}
					});	
			}
		function checkMode() {
			if ($('#id').val() != '') {
				global.isEditMode = true;
			} else {
				global.isEditMode = false;
			}

			if (global.isEditMode) {
				$('#topSaveText').html('儲存');
				$('#pageContentDiv').show();
				$('#pagePicDiv').show();
				$('#formModalx').show();
				
			} else {
				$('#pageContentDiv').hide();
				$('#pagePicDiv').hide();
				$('#formModalx').hide();

			}
		}
		
	</script>


</body>

</html>