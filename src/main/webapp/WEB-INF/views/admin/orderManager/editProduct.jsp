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
									${sessionScope.funcName}新增修改
								</div>
								<div class="col-sm-6 text-right">
									<button type="button" id="backBtn" class="btn btn-default ">
										<i class="fas fa-chevron-left"> 回訂單瀏覽</i>
									</button>
									<button type="button" id="saveBtn" class="btn btn-success ">
										<i class="fas fa-save" id="topSaveText"> 新增</i>
									</button>
								</div>
							</div>
						</div>
					</div>
					<div class="card-body">
						<form id="prodForm">
							<div class="container-fluid">
								<div class="row">
									<div class="col-sm-12">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proName>商品名稱*</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="proName"
													value="${productDto.proName}" style="width: 100%;">
												<font class='errText form-text' id='err_proName' color='red'></font>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for=proSpec>商品代號*</label>
											</div>
											<div class="col-sm-12">
												<input type="text" class="input" name="proCode"
													value="${productDto.proCode}" style="width: 100%;">
												<font class='errText form-text' id='err_proCode' color='red'></font>
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
							</div>
							<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
							<input type="hidden" id="id" name="id" value="${productDto.id}">
						</form>
					</div>
				</div>

			</div>
		</div>

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
	



	</div>


	<div>
		<div id="formModal">
			<div class='panel-body'>
				<table class="table table-condensed">
					<tr>
						<td>
							圖片網址:<input type="text" class="input"  id="copyText" value="" readonly>
						</td>
					</tr>
					<tr>
						<td>
							※ 您可以複製上列網址至商品描述區插入圖片 
						</td>
					</tr>
					
					<tr id='copyHide'>
						<td>
							<font color ='red'>已複製圖片網址</font>
						</td>
					</tr>
				</table>
			
				<footer class="panel-footer text-right">
					<button type="button" id="formModelCopy" class="btn btn-primary ">
						<span class="glyphicon glyphicon-ok"></span>複製到剪貼簿
					</button>
					<button type="button" id="formModelClose" class="btn btn-primary ">
						<span class="glyphicon glyphicon-remove"></span>關閉
					</button>
				</footer>
			</div>
		</div>
	</div>

	<form id="backForm">
		<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
	</form>


	<form id="prodPicForm">
		<input type="hidden" id="prodPicId" name="id" value="">
		<input type="hidden" id="prodPicProNo" name="proNo" value="${productDto.id}">
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
												$('#prodForm')
														.attr('action',
																'<c:url value = "/admin/orderManager"/>');
												$('#prodForm').attr('method',
														'post');
												$('#prodForm').submit();
											});

							if ($('#status').val() == 'A') {
								$('#status_act').addClass('active');
							} else if ($('#status').val() == 'E') {
								$('#status_stp').addClass('active');
							}

							prodClassSelSetting(function() {
								global.multiselect = $("#prodClass")
										.kendoMultiSelect({
											autoClose : false
										}).data("kendoMultiSelect");
								//var array = ${classSel};
								//global.multiselect.value(array);
							});

							$("#proHtml").kendoEditor(
									{
										tools : [ "bold", "italic",
												"underline", "strikethrough",
												"justifyLeft", "justifyCenter",
												"justifyRight", "justifyFull",
												"insertUnorderedList",
												"insertOrderedList", "indent",
												"outdent", "createLink",
												"unlink", "insertImage",
												"subscript", "superscript",
												"tableWizard", "createTable",
												"addRowAbove", "addRowBelow",
												"addColumnLeft",
												"addColumnRight", "deleteRow",
												"deleteColumn", "viewHtml",
												"formatting",
												"cleanFormatting", "fontName",
												"fontSize", "foreColor",
												"backColor", {
													name : "InsertVideo",
													tooltip : "InsertVideo",
													exec : insertVideo
												} ],
										resizable : {
											content : true,
											toolbar : true
										}
									});
							
							global.formModel = $("#formModal").kendoWindow({

								modal : true,
								title : false,
								visible : false,
								iframe : false,
							}).data("kendoWindow");
							
							initProdPict();

							
							checkMode();
						});

		function prodClassSelSetting(successfulFunction) {
			$
					.ajax({
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
			$
					.ajax({
						type : "POST",
						url : '<c:url value = "/admin/orderManager/ajaxSaveRefOrderProd"/>',
						data : $("#prodForm").serialize(),// serializes the form's elements.
						success : function(result) {
							if (checkAjaxResp(result)) {
								$('#id').val(result.data.prodId);
								$('#prodPicProNo').val(result.data.prodId);
								checkMode();

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
		
		
		
		function initProdPict(){
			getPict(function(result){
				if (checkAjaxResp(result)){
					global.initialFiles=result.data;
				}
				 
				global.template = kendo.template($("#template").html());
				
				$("#products").html(kendo.render(global.template, global.initialFiles));
				 $("#asyncFiles").kendoUpload({
		                async: {
		                    saveUrl: '<c:url value = "/admin/productManager/upload"/>',
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
				url : '<c:url value = "/admin/productManager/ajaxGetAllProdPic"/>',
				data : $("#prodForm").serialize(),// serializes the form's elements.
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
								url : '<c:url value = "/admin/productManager/ajaxRemovePic"/>',
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
				url : '<c:url value = "/admin/productManager/ajaxSetMainPic"/>',
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

		function testUrlForMedia(pastedData) {
			var success = false;
			var media = {};
			if (pastedData.match('https://(www.)?youtube|youtu\.be')) {
				if (pastedData.match('embed')) {
					youtube_id = pastedData.split(/embed\//)[1].split('"')[0];
				} else {
					youtube_id = pastedData.split(/v\/|v=|youtu\.be\//)[1]
							.split(/[?&]/)[0];
				}
				media.type = "youtube";
				media.id = youtube_id;
				success = true;
			} else if (pastedData.match('https://(player.)?vimeo\.com')) {
				vimeo_id = pastedData.split(/video\/|http:\/\/vimeo\.com\//)[1]
						.split(/[?&]/)[0];
				media.type = "vimeo";
				media.id = vimeo_id;
				success = true;
			} else if (pastedData.match('https://player\.soundcloud\.com')) {
				soundcloud_url = unescape(pastedData.split(/value="/)[1]
						.split(/["]/)[0]);
				soundcloud_id = soundcloud_url.split(/tracks\//)[1]
						.split(/[&"]/)[0];
				media.type = "soundcloud";
				media.id = soundcloud_id;
				success = true;
			}
			if (success) {
				return media;
			} else {
				alert("No valid media id detected");
			}
			return false;
		}
	</script>

</body>

</html>