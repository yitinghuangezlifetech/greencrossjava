<%@ page language="java" contentType="text/html;  charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/common-util.tld" prefix="pi"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增消息</title>
<!-- 
<script src="ckeditor/ckeditor.js"></script> 
 -->
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
									${sessionScope.funcName}
								</div>
								<div class="col-sm-6 text-right">
									<button type="button" id="backBtn" class="btn btn-default ">
										<i class="fas fa-chevron-left"> 回衛教資訊列表頁</i>
									</button>
									<button type="button" id="saveBtn" class="btn btn-success ">
										<i class="fas fa-save" id="topSaveText"> 儲存</i>
									</button>
								</div>
							</div>
						</div>
					</div>
					<div class="card-body">
						<form id="guardianForm">
							<div class="container-fluid">
							
								<div class="row">
									<div class="col-sm-12">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for="classId">分類</label>
											</div>
											<div class="col-sm-12">
												<select id='classId' name='classId'  value="${guardianDto.classId}"></select>
												<input type="hidden" id="productName" name="productName" />
												<input type="hidden" id="hiddenClassId" name="hiddenClassId" value="${guardianDto.classId}"/>
											</div>
										</div>
									</div>
								</div>							
							
								<div class="row">
									<div class="col-sm-12">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proName>標題*</label>
											</div>
											<div class="col-sm-12">
											
											<input type="hidden" name="newsType" value="N" /> 
											
											<input type="hidden" name="status" value="A" /> 
												<input type="text" class="input" name="title"
													value="${guardianDto.title}" style="width: 100%;">
												<font class='errText form-text' id='err_title' color='red'></font>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proName>標題(英文)</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="titleEn"
													value="${guardianDto.titleEn}" style="width: 100%;">
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
													value="${guardianDto.content}" style="width: 100%;">
												<font class='errText form-text' id='err_content' color='red'></font>
											</div>
										</div>
									</div>
								</div>
 								<div class="row">
									<div class="col-sm-12">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proSpec>內容(英文)</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="contentEn"
													value="${guardianDto.contentEn}" style="width: 100%;">
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
													value="${guardianDto.contact}" style="width: 100%;">
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
													value="${guardianDto.email}" style="width: 100%;">
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
													value="${guardianDto.tel}" style="width: 100%;">
												<font class='errText form-text' id='err_proInPrice'
													color='red'></font>
											</div>
										</div>
									</div>
								</div>
								
								<textarea id="html" name="html" rows="10" cols="30" style="height:440px" aria-label="editor">
									${guardianDto.html}
								</textarea>								
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
						 <!--	
							<textarea id="about"  name="about"  cols="50" rows="12" ></textarea>
							  <script type="text/javascript">
      							 CKEDITOR.replace("about");
      							 //CKEDITOR.replace( 'about', {});
      			    		 </script>		
      			    	 -->	 						
							<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
							<input type="hidden" id="id" name="id" value="${guardianDto.id}">
							
							
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<form id="prodPicForm">
		<input type="hidden" id="prodPicId" name="id" value="">
		<input type="hidden" id="prodPicProNo" name="proNo" value="${guardianDto.id}">
		<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
	</form>

		<div  class="row"  id="proPicDiv">
			<div class="col-sm-12">
				<div class="card">
					<div class="card-header ">
						<div class="container-fluid">
							<div class="row"></div>
						</div>
					</div>

					<div class="card-body">
						<div class="container-fluid">
							<div class="row">
								<div class="col-sm-12">
									<div class="demo-section k-content wide">
										<div class="wrapper">
											<div id="products"></div>
											<div class="dropZoneElement">
												<div class="textWrapper">
													<p>
														<span>+</span>新增圖片
													</p>
													<p class="dropImageHereText">拖拉圖片到這裡上傳</p>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12">
										<input name="asyncFiles" id="asyncFiles" type="file" />
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
		</div>

<script>
		$(document).ready(
				function() {
					initProdPict();
					prodClassSelSetting(function(){
						var classId = $('#classId').kendoDropDownList().data("kendoDropDownList");
						classId.value($("#hiddenClassId").val());
						
					});
					
					$("#html").kendoEditor({ resizable: {
                        content: true,
                        toolbar: true
                    }});
					
							$('#saveBtn').click(function(e) {
								save();
							});
							
							$('#backBtn')
									.click(
											function(e) {
												$('#guardianForm')
														.attr('action',
																'<c:url value = "/admin/guardian/guardianList"/>');
												$('#guardianForm').attr('method',
														'post');
												$('#guardianForm').submit();
											});

							});
		
		function save() {
			if($("#classId").val() == '') {
				alert('請選擇分類');
				return;
			}
			$.ajax({
						type : "POST",
						url : '<c:url value = "/admin/guardian/ajaxSaveHt"/>',
						data : $("#guardianForm").serialize(),// serializes the form's elements.
						success : function(result) {
 							if (checkAjaxResp(result)) {
// 								$('#id').val(result.data.prodId);
// 								$('#prodPicProNo').val(result.data.prodId);
// 								checkMode();

 							}

						}
					});
		}
		
		function prodClassSelSetting(successfulFunction){
			
			
			$
			.ajax({
				type : "POST",
				url : '<c:url value = "/admin/productClassManager/ajaxGetAllProdClass"/>',
				data : $("#inputForm").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {
						
						$('#classId').html('');
						var htmlCode = '<option value="">請選擇</option>'
								+ '<option value="000000">主分類</option>';
						$.each(result.data, function(i, obj) {
							htmlCode = htmlCode
									+ '<option value="'+obj.classId+'">'
									+ obj.className + '</option>';
						});
						$('#classId').html(htmlCode);
						
						successfulFunction();
						//$("#hiddenClassId").val()

					}

				}
			});
		}
	</script>
 
 
 <script>
 
 
	function initProdPict(){
		getPict(function(result){
			if (checkAjaxResp(result)){
				global.initialFiles=result.data;
			}
			 
			global.template = kendo.template($("#template").html());
			
			$("#products").html(kendo.render(global.template, global.initialFiles));
			 $("#asyncFiles").kendoUpload({
	                async: {
	                    saveUrl: '<c:url value = "/admin/guardian/upload"/>',
	                    removeUrl: "remove",
	                    autoUpload: true
	                },
	                validation: {
	                    allowedExtensions: [".jpg", ".jpeg", ".png", ".bmp", ".gif"]
	                },
	                upload: function (e) {
	                    e.data = 
	                    { 
	                    	
	                    		proNo: $("#id").val() 
	                    		
	                    };
	                },
	                success: onSuccess,
	                showFileList: false,
	                dropZone: ".dropZoneElement"
	            });

	            function onSuccess(e) {
	               /* if (e.operation == "upload") {
	                    for (var i = 0; i < e.files.length; i++) {
	                        var file = e.files[i].rawFile;

	                        if (file) {
	                            var reader = new FileReader();

	                            reader.onloadend = function () {
	                                $("<div class='product'><img src=" + this.result + " /></div>").appendTo($("#products"));
	                            };

	                            reader.readAsDataURL(file);
	                        }
	                    }
	                }*/
	                
	                if (checkAjaxResp(e.response)){
	                	reloadPict();
	                }
	                
	            }
		});
		
		
	}
 
	
    function onSuccess(e) {
        console.log("Success (" + e.operation + ") :: " );
        console.log(e);
        if(e.operation=='upload'){
        	console.log('fileId'+e.response.data.fileId);
        	$('#fileId').val(e.response.data.fileId);
        	
        }else{
        	var upload = $("#files").data("kendoUpload");
        	$('#fileId').val('');
		    upload.removeAllFiles();
        }
    }
    
    function onRemove(e) {
    	e.data = 
        { 
        	
    			fileId: $("#fileId").val() 
        		
        };
    }        function onSuccess(e) {
        console.log("Success (" + e.operation + ") :: " );
        console.log(e);
        if(e.operation=='upload'){
        	console.log('fileId'+e.response.data.fileId);
        	$('#fileId').val(e.response.data.fileId);
        	
        }else{
        	var upload = $("#files").data("kendoUpload");
        	$('#fileId').val('');
		    upload.removeAllFiles();
        }
    }
    
    function onRemove(e) {
    	e.data = 
        { 
        	
    			fileId: $("#fileId").val() 
        		
        };
    }
    
	function reloadPict(){
		getPict(function (result){
			if (checkAjaxResp(result)){
				global.initialFiles=result.data;
			}
			global.template = kendo.template($("#template").html());
			$("#products").html(kendo.render(global.template, global.initialFiles));
		});
	}
	
	
	
	function getPict(callBack){
		$
		.ajax({
			type : "POST",
			url : '<c:url value = "/admin/guardian/ajaxGetAllProdPic"/>',
			data : $("#guardianForm").serialize(),// serializes the form's elements.
			success : function(result) {
				if (checkAjaxResp(result)) {
					
					callBack(result);

				}

			}
		});
	}
	
	function removePict(pictName){	
		warningConfirm(
				'確定刪除嗎?',
				'本次操作將刪除該商品照所有相關資料，是否繼續？',
				'是',
				'否',
				function(confirm) {
					if (confirm) {
						$('#prodPicId').val(pictName);
						$
						.ajax({
							type : "POST",
							url : '<c:url value = "/admin/guardian/ajaxRemovePic"/>',
							data : $("#prodPicForm").serialize(),// serializes the form's elements.
							success : function(result) {
								if (checkAjaxResp(result)) {
									
									reloadPict();

								}

							}
						});
					}
				});
	}
	
	function setMain(pictName){
		$('#prodPicId').val(pictName);
		$
		.ajax({
			type : "POST",
			url : '<c:url value = "/admin/guardian/ajaxSetMainPic"/>',
			data : $("#prodPicForm").serialize(),// serializes the form's elements.
			success : function(result) {
				if (checkAjaxResp(result)) {
					
					reloadPict();

				}

			}
		});
	}
	
	function getPictUrl(pictName){
		$('#copyText').val('<c:url value = "/ajaxGetProductPic"/>/'+pictName);
		global.formModel.center().open();
		$('#copyHide').hide();
	}
 </script>
 
       	<script type="text/x-kendo-template" id="template">
        <div class="product">
			<div class="container-fluid">	
				<div class="row">
					<div class="col-sm-12 text-center">
            			<img src="<c:url value = "/admin/guardian/ajaxGetGuardianPic"/>/#= name #" alt="#: name # image" />
						<img src="<c:url value = "/img/delete.png"/>" class="deleteImg"/ onclick='removePict("#:name#");'>
					</div>
				</div>
				<div class="row" style="margin: 5px 0 0 0;">
					<div class="col-sm-12 text-center">
						#if(isMain=='N'){#
						<button type='button' class='btn btn-success btn-sm' onclick='setMain("#:name#");'>設為主圖</button>  
						#}else{#
						<button type='button' class='btn btn-danger btn-sm'  disabled >已設為主圖</button> 
						#}#
					</div>
				</div>				
			</div>
        </div>       
    </script>   
</body>
</html>