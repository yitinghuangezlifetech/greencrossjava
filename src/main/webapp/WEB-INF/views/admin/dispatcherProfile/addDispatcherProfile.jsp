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
									${sessionScope.funcName}-派遣人員新增修改
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
						<form id="dispatcherProfileForm">
							<input type="hidden" id="id" value="${dispatcherProfileDto.id}">
							<div class="container-fluid">
								<div class="row">
									<div class="col-sm-12">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=empId>員工編號*</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" id="empId" name="empId"
													value="${dispatcherProfileDto.id}" style="width: 100%;" disabled>
												<font class='errText form-text' id='err_empId' color='red'></font>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proSpec>姓名*</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="name"
													value="${dispatcherProfileDto.name}" style="width: 100%;">
												<font class='errText form-text' id='err_name' color='red'></font>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proSpec>所屬公司*</label>
											</div>

											<div class="col-sm-12">
												<select id='compId' name='compId'>
													<option value="">無</option>
													<c:forEach items="${compIdSelectList}" var="current">
														<option value="${current.id}">${current.name}</option>
													</c:forEach>
												</select> <input type="hidden" id="selectCompId"
													value="${dispatcherProfileDto.compId}">
											</div>
											<div class="col-sm-12">
												<font class='errText form-text' id='err_compId' color='red'></font>
											</div>

										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for='whId'>倉別*</label>
											</div>
											<div class="col-sm-12">
												<select id='whId' name='whId'>
													<option value="">請選擇</option>
													
													<c:forEach items="${whIdSelectList}" var="current">
														<option value="${current.id}">${current.name}</option>
													</c:forEach>
												</select>
												
												<input type="hidden" id="selectWhId" value="${dispatcherProfileDto.whId}">
											</div>
											
											<div class="col-sm-12">
												<font class='errText form-text' id='err_whId' color='red'></font>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proInPrice>身分證字號*</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="idNumber"
													value="${dispatcherProfileDto.idNumber}" style="width: 100%;">
												<font class='errText form-text' id='err_idNumber'
													color='red'></font>
											</div>											
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proSellPrice>性別*</label>
											</div>
											<div class="col-sm-12">
												<select id='sex' name='sex'>
													<option value="">未提供</option>
													<option value="1">男</option>
													<option value="0">女</option>
												</select>
												<input type="hidden"  id="selectSex"
													value="${dispatcherProfileDto.sex}">
												<font class='errText form-text' id='err_proSellPrice'
													color='red'></font>
											</div>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proSpec>出生年月日*(Ex:1988/09/03)</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="birthday"
													value="${dispatcherProfileDto.birthday}" style="width: 100%;">
												<font class='errText form-text' id='err_birthday' color='red'></font>
											</div>
										</div>
									</div>
								</div>	
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proSellPrice>通訊地址</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="addr"
													value="${dispatcherProfileDto.addr}" style="width: 100%;">
												<font class='errText form-text' id='err_addr'
													color='red'></font>
											</div>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proInPrice>連絡電話*</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="phone"
													value="${dispatcherProfileDto.phone}" style="width: 100%;">
												<font class='errText form-text' id='err_phone'
													color='red'></font>
											</div>
										</div>
									</div>
								</div>	
								
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proSellPrice>計時計件</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="timingOrOiece"
													value="${dispatcherProfileDto.timingOrOiece}" style="width: 100%;">
												<font class='errText form-text' id='err_addr'
													color='red'></font>
											</div>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proInPrice>最高學歷</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="education"
													value="${dispatcherProfileDto.education}" style="width: 100%;">
												<font class='errText form-text' id='err_phone'
													color='red'></font>
											</div>
										</div>
									</div>
								</div>	
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proSellPrice>堆高機證照</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="stackerLicense"
													value="${dispatcherProfileDto.stackerLicense}" style="width: 100%;">
												<font class='errText form-text' id='err_addr'
													color='red'></font>
											</div>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proInPrice>證照有效期限</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="stackerLicenseLimitDate"
													value="${dispatcherProfileDto.stackerLicenseLimitDate}" style="width: 100%;">
												<font class='errText form-text' id='err_phone'
													color='red'></font>
											</div>
										</div>
									</div>
								</div>	
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proSellPrice>駕駛電拖</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="electricTrailer"
													value="${dispatcherProfileDto.electricTrailer}" style="width: 100%;">
												<font class='errText form-text' id='err_addr'
													color='red'></font>
											</div>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proInPrice>勞保</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="laborPortection"
													value="${dispatcherProfileDto.laborPortection}" style="width: 100%;">
												<font class='errText form-text' id='err_phone'
													color='red'></font>
											</div>
										</div>
									</div>
								</div>		
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proSellPrice>健保</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="healthInsurance"
													value="${dispatcherProfileDto.healthInsurance}" style="width: 100%;">
												<font class='errText form-text' id='err_addr'
													color='red'></font>
											</div>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proInPrice>居留證號</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="resPermitNumber"
													value="${dispatcherProfileDto.resPermitNumber}" style="width: 100%;">
												<font class='errText form-text' id='err_phone'
													color='red'></font>
											</div>
										</div>
									</div>
								</div>		
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proSellPrice>居留證有效期限</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="resPermitLimitDate"
													value="${dispatcherProfileDto.resPermitLimitDate}" style="width: 100%;">
												<font class='errText form-text' id='err_addr'
													color='red'></font>
											</div>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proInPrice>工作證號</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="workPermitNumber"
													value="${dispatcherProfileDto.workPermitNumber}" style="width: 100%;">
												<font class='errText form-text' id='err_phone'
													color='red'></font>
											</div>
										</div>
									</div>
								</div>	
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proSellPrice>工作證有效期限</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="workPermitNumberLimitDate"
													value="${dispatcherProfileDto.workPermitNumberLimitDate}" style="width: 100%;">
												<font class='errText form-text' id='err_addr'
													color='red'></font>
											</div>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proInPrice>學生證</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="studentPemitNumber"
													value="${dispatcherProfileDto.studentPemitNumber}" style="width: 100%;">
												<font class='errText form-text' id='err_phone'
													color='red'></font>
											</div>
										</div>
									</div>
								</div>	
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proSellPrice>學生證有效期限</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="studentPemitNumberLimitDate"
													value="${dispatcherProfileDto.studentPemitNumberLimitDate}" style="width: 100%;">
												<font class='errText form-text' id='err_addr'
													color='red'></font>
											</div>
										</div>
									</div>
									<!--  
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proInPrice>照片</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="photoPath"
													value="${dispatcherProfileDto.photoPath}" style="width: 100%;">
												<font class='errText form-text' id='err_phone'
													color='red'></font>
											</div>
										</div>
									</div>
									-->
									
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proInPrice>照片</label>
											</div>
											<div class="col-sm-12">
												<input name="files" id="files" type="file" />
								<!--<img src="<c:url value = "/img/delete.png"/>" class="deleteImg"/ onclick='removePict("#:name#");'>-->
								<!-- <img src="D:/sysupload/dispatcherProfilePic/00100129840514.jpg" />-->
								<c:if test="${dispatcherProfileDto.photoPath != null}">
									<img src="<c:url value = "/admin/dispatcherProfile/ajaxGetDisPatcherPic"/>/${dispatcherProfileDto.id}" />
								</c:if>
												<input type="hidden" id="fileId" name="fileId" value="">
												<font class='errText form-text' id='err_fileId' color='red'></font>
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
												<input type="hidden" id="status" name="status"
													value="${dispatcherProfiletDto.status}"> <font
													class='errText form-text' id='err_status' color='red'></font>
											</div>
										</div>

									</div>
								</div>
								-->
							</div>
							<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
							<input type="hidden" id="id" name="id" value="${dispatcherProfileDto.id}">
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
												$('#dispatcherProfileForm')
														.attr('action',
																'<c:url value = "/admin/dispatcherProfile/list"/>');
												$('#dispatcherProfileForm').attr('method',
														'post');
												$('#dispatcherProfileForm').submit();
											});

							$("#files").kendoUpload({
								
				                async: {
				                    saveUrl: "<c:url value = "/admin/dispatcherProfile/upload"/>",
				                    removeUrl: "<c:url value = "/admin/arItemUpload/ajaxRemoveFile"/>",
				                    autoUpload: true
				                },
				                validation: {
				                    allowedExtensions: [".jpg", ".png"]
				                },
				                upload: function (e) {
				                    e.data = 
				                    { 
				                    	
				                    		id: $("#id").val() 
				                    		
				                    };
				                },				                
				                success: onSuccess,
				                remove: onRemove,
								multiple: false
				            });
							
					        function onSuccess(e) {
					        	
								$('#dispatcherProfileForm')
								.attr('action',
										'<c:url value = "/admin/dispatcherProfile/addDispatcherProfile"/>');
						$('#dispatcherProfileForm').attr('method',
								'post');
						$('#dispatcherProfileForm').submit();
					        	
					        	console.log(e.data);
					            console.log("Success (" + e.operation + ") :: " );
					            console.log(e);
					            if(e.operation=='upload'){
					            	//console.log('fileId'+e.response.data.fileId);
					            	//$('#fileId').val(e.response.data.fileId);
					            	
					            }else{
					            	var upload = $("#files").data("kendoUpload");
					            	//$('#fileId').val('');
					    		    //upload.removeAllFiles();
					            }
					        }
					        
					        function onRemove(e) {
// 					        	e.data = 
// 					            { 
					            	
// 					        			fileId: $("#fileId").val() 
					            		
// 					            };
					        }
					        
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


							$('#compId').val($('#selectCompId').val());
							$('#sex').val($('#selectSex').val());
							$('#whId').val($('#selectWhId').val());
							$('#compId').kendoDropDownList();
							$('#sex').kendoDropDownList();
							$('#whId').kendoDropDownList();   
							
							
							init();
						});


		function init(){
			if($('#id').val()!=''){
				$('#empId').attr('disabled',true);
			}
		}
		
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
						url : '<c:url value = "/admin/dispatcherProfile/ajaxSaveDispatcherProfile"/>',
						data : $("#dispatcherProfileForm").serialize(),// serializes the form's elements.
						success : function(result) {
							if (checkAjaxResp(result)) {
								//$('#id').val(result.data.prodId);
								//$('#prodPicProNo').val(result.data.prodId);
								checkMode();
								$('#dispatcherProfileForm').attr('action','<c:url value = "/admin/dispatcherProfile/list"/>');
								$('#dispatcherProfileForm').attr('method','post');
								$('#dispatcherProfileForm').submit();
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