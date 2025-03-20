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
									${sessionScope.funcName}-協力廠商新增修改
								</div>
								<div class="col-sm-6 text-right">
									<button type="button" id="backBtn" class="btn btn-default ">
										<i class="fas fa-chevron-left"> 回列表頁瀏覽</i>
									</button>
									<button type="button" id="saveBtn" class="btn btn-success ">
										<i class="fas fa-save" id="topSaveText">儲存</i>
									</button>
								</div>
							</div>
						</div>
					</div>
					<div class="card-body">
						<form id="comProfHtForm">
							<div class="container-fluid">
								<div class="row">
									<div class="col-sm-12">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=comCode>廠編*</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="comCode"
													value="${comProfHtDto.comCode}" style="width: 100%;">
												<font class='errText form-text' id='err_comCode' color='red'></font>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proName>統編*</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="ban"
													value="${comProfHtDto.ban}" style="width: 100%;">
												<font class='errText form-text' id='err_ban' color='red'></font>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proSpec>公司名稱*</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="comName"
													value="${comProfHtDto.comName}" style="width: 100%;">
												<font class='errText form-text' id='err_comName' color='red'></font>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proSpec>連絡電話*</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="comTel"
													value="${comProfHtDto.comTel}" style="width: 100%;">
												<font class='errText form-text' id='err_comTel' color='red'></font>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proSellPrice>Email*</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="comEmail"
													value="${comProfHtDto.comEmail}" style="width: 100%;">
												<font class='errText form-text' id='err_comEmail'
													color='red'></font>
											</div>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proInPrice>公司地址*</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="comAdr"
													value="${comProfHtDto.comAdr}" style="width: 100%;">
												<font class='errText form-text' id='err_proInPrice'
													color='red'></font>
											</div>
										</div>
									</div>
								</div>	
								<!-- 																					
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=statusLab>啟用狀態*</label>
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

											</div>
										</div>

									</div>
								</div>
								-->
							</div>
							<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
							<input type="hidden" id="id" name="id" value="${comProfHtDto.id}">
						</form>
					</div>
				</div>

			</div>
		</div>
	



	</div>



	<form id="backForm">
		<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
	</form>





	<script>
		$(document)
				.ready(
						function() {				
							
							$('#saveBtn').click(function(e) {
								save();
							});
							$('#status_act').click(function(e) {
								console.log('A');
								$('#status').val("A");
							});
							$('#status_stp').click(function(e) {
								console.log('S');
								$('#status').val("S");
							});
							
							$('#formModelClose').click(function(e) {
								global.formModel.center().close();
							});
							
							$('#formModelCopy').click(function(e) {
								var copyText = $("#copyText");
								copyText.select();
								document.execCommand("Copy");
								$('#copyHide').show();
							});
							
							
							$('#backBtn')
									.click(
											function(e) {
												$('#comProfHtForm')
														.attr('action',
																'<c:url value = "/admin/comProf/list"/>');
												$('#comProfHtForm').attr('method',
														'post');
												$('#comProfHtForm').submit();
											});

// 							if ($('#status').val() == 'A') {
// 								$('#status_act').addClass('active');
// 							} else if ($('#status').val() == 'E') {
// 								$('#status_stp').addClass('active');
// 							}


// 							$("#proHtml").kendoEditor(
// 									{
// 										tools : [ "bold", "italic",
// 												"underline", "strikethrough",
// 												"justifyLeft", "justifyCenter",
// 												"justifyRight", "justifyFull",
// 												"insertUnorderedList",
// 												"insertOrderedList", "indent",
// 												"outdent", "createLink",
// 												"unlink", "insertImage",
// 												"subscript", "superscript",
// 												"tableWizard", "createTable",
// 												"addRowAbove", "addRowBelow",
// 												"addColumnLeft",
// 												"addColumnRight", "deleteRow",
// 												"deleteColumn", "viewHtml",
// 												"formatting",
// 												"cleanFormatting", "fontName",
// 												"fontSize", "foreColor",
// 												"backColor", {
// 													name : "InsertVideo",
// 													tooltip : "InsertVideo",
// 													exec : insertVideo
// 												} ],
// 										resizable : {
// 											content : true,
// 											toolbar : true
// 										}
// 									});
							
// 							global.formModel = $("#formModal").kendoWindow({

// 								modal : true,
// 								title : false,
// 								visible : false,
// 								iframe : false,
// 							}).data("kendoWindow");
							
// 							initProdPict();

							
// 							checkMode();
						});


		
		function prodClassSelSetting(successfulFunction) {
			$.ajax({
						type : "POST",
						url : '<c:url value = "/admin/productManager/ajaxGetAllProdClass"/>',
						data : $("#inputForm").serialize(),// serializes the form's elements.
						success : function(result) {
							if (checkAjaxResp(result)) {

								$('#prodClass').html('');
								var htmlCode = '';
								$.each(result.data,
										function(i, obj) {
											htmlCode = htmlCode
													 + '<option value="'+obj.classId+'">'
													 + obj.className
													 + '</option>';
										}
								);
								$('#prodClass').html(htmlCode);

								successfulFunction();

							}

						}
					});
		}

		function save() {
					$.ajax({
						type : "POST",
						url : '<c:url value = "/admin/comProf/ajaxSaveComProfHt"/>',
						data : $("#comProfHtForm").serialize(),// serializes the form's elements.
						success : function(result) {
							if (checkAjaxResp(result)) {
								//$('#id').val(result.data.prodId);
								//$('#prodPicProNo').val(result.data.prodId);
								checkMode();
								$('#comProfHtForm').attr('action','<c:url value = "/admin/comProf/list"/>');
								$('#comProfHtForm').attr('method','post');
								$('#comProfHtForm').submit();
							}

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
				$('#proHtmlDiv').show();
				$('#proPicDiv').show();
			} else {
				$('#proHtmlDiv').hide();
				$('#proPicDiv').hide();

			}
		}
		


		
		function setMain(pictName){
			$('#prodPicId').val(pictName);
			$
			.ajax({
				type : "POST",
				url : '<c:url value = "/admin/productManager/ajaxSetMainPic"/>',
				data : $("#prodPicForm").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {
						
						reloadPict();

					}

				}
			});
		}
		

	</script>

	<script type="text/x-kendo-template" id="template">
        <div class="product">
			<div class="container-fluid">	
				<div class="row">
					<div class="col-sm-12 text-center">
            			<img src="<c:url value = "/admin/productManager/ajaxGetProductPic"/>/#= name #" alt="#: name # image" />
						<img src="<c:url value = "/img/delete.png"/>" class="deleteImg"/ onclick='removePict("#:name#");'>
					</div>
				</div>
				<div class="row" style="margin: 5px 0 0 0;">
					<div class="col-sm-12 text-center">
						#if(isMain=='N'){#
						<button type='button' class='btn btn-success btn-sm' onclick='setMain("#:name#");'>設為主圖</button>  
						#}else{#
						<button type='button' class='btn btn-danger btn-sm'  disabled >已設為主圖</button> 
						<button type='button' id='addBtn' onclick="openUploadArWindow();" class='btn btn-success btn-sm'>上傳辨識圖</button> 
						#}#
					</div>
				</div>				
				<div class="row" style="margin: 5px 0 0 0;">
					<div class="col-sm-12 text-center" >
						<button type='button' class='btn btn-success btn-sm' onclick='getPictUrl("#:name#");'>取得圖片連結</button>
					</div>
				</div>
			</div>
        </div>       
    </script>


	<script type="text/x-kendo-template" id="insertVideo-template">
    <div>
        <label for="videoUrl">Enter a URL from YouTube or Vimeo:</label>
        <input type="text" id="videoUrl" name="videoUrl"/>
             
        <div class="insertVideo-actions">
            <button class="k-button insertVideo-insert">Insert</button>
            <button class="k-button insertVideo-cancel">Cancel</button>
        </div>
    </div>
</script>

	<script type="text/x-kendo-template" id="youTube-template">
    <iframe class="youtube-player" type="text/html"  src="http://www.youtube.com/embed/#= source #?fs=1&feature=oembed&wmode=transparent" frameborder="0" allowfullscreen></iframe>
</script>

	<script>
		function insertVideo(e) {

			var editor = $(this).data("kendoEditor");

			var dialog = $($("#insertVideo-template").html()).find(
					".insertVideo-insert").click(
					function() {

						var media = testUrlForMedia(dialog.element
								.find("input").val());
						if (media) {
							var template = kendo
									.template($("#youTube-template").html());

							editor.exec("insertHTML", {
								value : template({
									source : media.id
								})
							});
						}

						dialog.close();
					}).end().find(".insertVideo-cancel").click(function() {
				dialog.close();
			}).end().kendoWindow({
				modal : true,
				title : "Insert Video",
				deactivate : function() {
					dialog.destroy();
				}
			}).data("kendoWindow");

			dialog.center().open();

		}

		
		
        function saveform() {
			$.ajax({
				type : "POST",
				url : '<c:url value = "/admin/arItemUpload/ajaxComProfHtGridList"/>',
				data : $("#inputForm").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {
						console.log(result);
						global.formModalAr.close();	
						//closeFormModel();
						//reflashGrid();

					}

				}
			});
		}

        
        function editform() {
			$.ajax({
				type : "POST",
				url : '<c:url value = "/admin/arItemUpload/ajaxFastArHtDlSave"/>',
				data : $("#editForm").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {
						console.log(result);
						global.editFormModalAr.close();	
						//closeFormModel();
						//reflashGrid();

					}

				}
			});
		}
        
		function openUploadArWindow() {
			global.formModelAr.center().open();
		}
		
        function openUploadArWindow(){
        	if($("#editFormHtId").val() != '' && $("#editFormHtId").val() != null) {
              	$('#editFormId').val($("#editFormHtId").val());

            	$.ajax({
    				type : "POST",
    				url : '<c:url value = "/admin/arItemUpload/ajaxGetArHtDlsInfo"/>',
    				data : $("#editForm").serialize(),// serializes the form's elements.
    				success : function(result) {
    					if (checkAjaxResp(result)) {
    						console.log(result.data);
    						//global.selectList2.value(result.data.dlList[0].action);
    						$("#editFormHtId").val(result.data.dlList[0].htId);
    						$("#editFormDlId").val(result.data.dlList[0].id);
    						$("#editForm_itemName").val(result.data.itemName);
    						$("#editForm_itemContent").val(result.data.itemContent);
    						//$("#editForm_var").val(result.data.dlList[0].var);
    						
    						//$("#editForm_imagePath").append('<img alt="" src="'+result.data.dlList[0].imgPath+'" />');
    						//$("#editForm_imagePath").append('<img alt="" src="'+result.data.dlList[0].imgPath+'" />');	
    						//$("#editForm_imagePath").append('<img src="file:/D:/upload/arImgUpload/1555769741677347_SKH.jpg">');
    						//$("#editForm_imagePath").append('<img src="D:\upload\arImgUpload\1555753494063235_SKH.jpg"/>');

    			            if($('#editForm_action').val()=='imaheUpload'){
    			          	   $('#editForm_uploadtr').show();
    			          	   $('#editForm_setvartr').hide();
    			             }else{
    			          	   $('#editForm_uploadtr').hide();
    			          	   $('#editForm_setvartr').show();
    			             }
    						//global.editFormModal.center().open();
    					}

    				}
    			});  
            	global.editFormModalAr.center().open();	
        	}
        	else {
        		global.formModelAr.center().open();	
        	}       	
        }
	</script>

</body>

</html>