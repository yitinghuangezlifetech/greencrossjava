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
					<div class="card-header text-right">
						<button type="button" id="addBtn" class="btn btn-primary ">
							<i class="fas fa-plus"> 快速新增</i>
						</button>
					</div>
					<div class="card-body">
						<div id="grid"></div>
					</div>
				</div>

			</div>
		</div>
	</div>

	<div>
		<div id="formModal">
			<form id="inputForm">
				<input type="hidden" id="htFormid" name="htId" value="">
				<input type="hidden" id="dlFormid" name="dlId" value="">
				<h3 class="panel-title">AR物件管理新修視窗</h3>
				<div class='panel-body'>
					<table class="table table-condensed">
						<tr>
							<td><label for="itemName">物件名稱*</label></td>
							<td>
								<input type="text" id="itemName" class='input' name="itemName" value=""> 
								<font class='errText form-text' id='err_itemName' color='red'></font>
							</td>
						</tr>
						<tr>
							<td><label for="itemContent">物件描述*</label></td>
							<td>
								<input type="text" id="itemContent" class='input' name="itemContent" value=""> 
								<font class='errText form-text' id='err_itemContent' color='red'></font>
							</td>
						</tr>
						
						<tr >
							<td><label for="itemName">辨識圖片上傳*</label></td>
							<td>
								<input name="arfiles" id="arfiles" type="file" />
								
								<input type="hidden" id="arfileId" name="arfileId" value="">
								<font class='errText form-text' id='err_arfileId' color='red'></font>
							</td>
						</tr>
					</table>
					<table class="table table-condensed">
						
						<tr>
							<td><label for="action">動作*</label></td>
							
							<td>
								<select id="action" name ="action"> 
									<option value="">請選擇</option>
									<c:forEach items="${actionList}" var="current">
										<option value="${current.code}">${current.text}</option>
									</c:forEach>
								</select>
								<font class='errText form-text' id='err_action' color='red'></font>
							</td>
						</tr>
						<tr id='uploadtr'>
							<td><label for="itemName">圖片上傳*</label></td>
							<td>
								<input name="files" id="files" type="file" />
								
								<input type="hidden" id="fileId" name="fileId" value="">
								<font class='errText form-text' id='err_fileId' color='red'></font>
							</td>
						</tr>
						<tr id='setvartr'>
							<td><label for="var">參數*</label></td>
							<td>
								<input type="text" id="var" class='input' name="var" value=""> 
								<font class='errText form-text' id='err_var' color='red'></font>
							</td>
						</tr>
					</table>
					
				</div>
				<div class="panel-footer text-right">
					<button type="button" id="formModelSend" class="btn btn-primary ">
						<span class="glyphicon glyphicon-ok"></span>建立
					</button>
					<button type="button" id="formModelClose" class="btn btn-primary ">
						<span class="glyphicon glyphicon-remove"></span>關閉
					</button>
				</div>
			</form>
		</div>
	</div>
	
	
	<div>
		<div id="editFormModal">
			<form id="editForm">
				<input type="hidden" id="editFormId" name="id" value="">
				<input type="hidden" id="editFormHtId" name="htId" value="">
				<input type="hidden" id="editFormDlId" name="dlId" value="">
				<h3 class="panel-title">AR物件管理新修視窗</h3>
				<div class='panel-body'>
					<table class="table table-condensed">
						<tr>
							<td><label for="editForm_itemName">物件名稱*</label></td>
							<td>
								<input type="text" id="editForm_itemName" class='input' name="itemName" value=""> 
								<font class='errText form-text' id='err_editForm_itemName' color='red'></font>
							</td>
						</tr>
						<tr>
							<td><label for="editForm_itemContent">物件描述*</label></td>
							<td>
								<input type="text" id="editForm_itemContent" class='input' name="itemContent" value=""> 
								<font class='errText form-text' id='err_editForm_itemContent' color='red'></font>
							</td>
						</tr>
						<!--
						<tr >
							<td><label for="itemName">辨識圖片</label></td>
							<td>
								
							</td>
						</tr>
						-->
						<tr >
							<td><label for="itemName">辨識圖片</label></td>
							<td id="editForm_imagePath">
								
								<input name="arfiles" id="editForm_arfiles" type="file" />
								
								<input type="hidden" id="editForm_arfileId" name="arfileId">
								<font class='errText form-text' id='err_arfileId' color='red'></font>
							</td>
						</tr>


					
					</table>
				<table class="table table-condensed">
						
						<tr>
							<td><label for="editForm_action">動作*</label></td>
							
							<td>
								<select id="editForm_action" name ="action"> 
									<option value="">請選擇</option>
									<c:forEach items="${actionList}" var="current">
										<option value="${current.code}">${current.text}</option>
									</c:forEach>
								</select>
								<font class='errText form-text' id='editForm_err_action' color='red'></font>
							</td>
						</tr>
						<tr id='editForm_uploadtr'>
							<td><label for="editForm_itemName">圖片上傳*</label></td>
							<td>
								<input name="files" id="editForm_files" type="file" />
								<font class='errText form-text' id='err_editForm_fileId' color='red'></font>
							</td>
						</tr>
						<tr id='editForm_setvartr'>
							<td><label for="editForm_var">參數*</label></td>
							<td>
								<input type="text" id="editForm_var" class='input' name="var" value=""> 
								<font class='errText form-text' id='err_editForm_var' color='red'></font>
							</td>
						</tr>
					</table>
				
				</div>
				<div class="panel-footer text-right">
					<button type="button" id="editForm_formModelSend" class="btn btn-primary ">
						<span class="glyphicon glyphicon-ok"></span>儲存
					</button>
					<button type="button" id="editForm_formModelClose" class="btn btn-primary ">
						<span class="glyphicon glyphicon-remove"></span>關閉
					</button>
				</div>
				<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
			</form>
		</div>
	</div>
	

	<form id="dlForm">
		<input type="hidden" id="dlFormid" name="id" value="">
		<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
	</form>

	<form id='removeForm'>
		<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
		<input type="hidden" id="removeId" name="htId" value="">
	</form>
	

	<script>
		$(document).ready(function() {
			
			
			global.dataSource = new kendo.data.DataSource(
					{
						transport : {
							read : {
								type : "POST",
								url : '<c:url value = "/admin/arItemUpload/ajaxArGridList"/>',
								dataType : "jsonp"
							}
						},
						pageSize : 20
					});

			$("#grid")
					.kendoGrid(
							{
								columns : [
										{
											template : "<button type='button' class='btn btn-success btn-sm' onclick='edit(\"#:id#\")'>編輯</button>&nbsp;"
													+ "<button type='button' class='btn btn-danger btn-sm' onclick='remove(\"#:id#\")'>刪除</button>&nbsp;",
											field : "action",
											title : "動作",
											width : 150
										},
										{
											field : "item_name",
											title : "物件名稱",
											width : 150
										},
										{
											field : "item_content",
											title : "物件說明",
											width : 200
										},
										{
											field : "status",
											title : "狀態",
											width : 300
										},
										{
											field : "create_user",
											title : "建立者",
											width : 150
										},
										{
											field : "create_time",
											title : "建立時間",
											width : 250
										},
										{
											field : "update_user",
											title : "最後更新者",
											width : 150
										},
										{
											field : "update_time",
											title : "最後更新時間",
											width : 250
										} ],
								dataSource : global.dataSource,
								pageable : true,
								sortable : true
							});
			
			
			global.formModel = $("#formModal").kendoWindow({

				modal : true,
				title : false,
				visible : false,
				iframe : false,
			}).data("kendoWindow");
			
			global.editFormModal = $("#editFormModal").kendoWindow({

				modal : true,
				title : false,
				visible : false,
				iframe : false,
			}).data("kendoWindow");
			
			global.selectList = $("#action").kendoDropDownList({
				 change: onChange,
				
			});
			
			
			global.selectList2 = $("#editForm_action").kendoDropDownList({
				 change: editOnChange,
				 dataTextField: "name",
				 dataValueField: "value",
			}).data("kendoDropDownList");

			
			$("#files").kendoUpload({
				
                async: {
                    saveUrl: "<c:url value = "/admin/arItemUpload/ajaxUploadFile"/>",
                    removeUrl: "<c:url value = "/admin/arItemUpload/ajaxRemoveFile"/>",
                    autoUpload: true
                },
                validation: {
                    allowedExtensions: [".jpg", ".png"]
                },
                success: onSuccess,
                remove: onRemove,
				multiple: false
            });
			
			$("#arfiles").kendoUpload({
				
                async: {
                    saveUrl: "<c:url value = "/admin/arItemUpload/ajaxArUploadFile"/>",
                    removeUrl: "<c:url value = "/admin/arItemUpload/ajaxRemoveFile"/>",
                    autoUpload: true
                },
                validation: {
                    allowedExtensions: [".jpg"]
                },
                success: onArSuccess,
                remove: onArRemove,
				multiple: false
            });

			$("#editForm_arfiles").kendoUpload({
				
                async: {
                    saveUrl: "<c:url value = "/admin/arItemUpload/ajaxArUploadFile"/>",
                    removeUrl: "<c:url value = "/admin/arItemUpload/ajaxRemoveFile"/>",
                    autoUpload: true
                },
                validation: {
                    allowedExtensions: [".jpg"]
                },
                success: editOnArSuccess,
                remove: editOnArRemove,
				multiple: false
            });
			
			$('#formModelSend').click(function(e) {
				saveform();
			});
			
			$('#editForm_formModelSend').click(function(e) {
				editform();
			});
			
			$('#addBtn').click(function(e) {
				resetModelEle();
				$('#id').val('');

				global.formModel.center().open();
			});
			
			$('#formModelClose').click(function(e) {
				closeFormModel();
			});
			
			$('#editForm_formModelClose').click(function(e) {
				editFormCloseFormModel();
			});

		});
		
		
		function resetModelEle(){
			$('#uploadtr').hide();
			$('#setvartr').hide();
			
			
			$('#action').data("kendoDropDownList").value("");
			
			
			var upload = $("#files").data("kendoUpload");
		    upload.removeAllFiles();
		    var upload = $("#arfiles").data("kendoUpload");
		    upload.removeAllFiles();
		}
		
		
		
		function closeFormModel() {
			$('.errText').text('');
			$('#id').val("");
			
			global.formModel.center().close();

		}
		
		function editFormCloseFormModel(){
			$('.errText').text('');
			$('#editFormId').val("");
			
			global.editFormModal.center().close();
		}
		
		
		function onChange() {
           
           if($('#action').val()=='imaheUpload'){
        	   $('#uploadtr').show();
        	   $('#setvartr').hide();
           }else{
        	   $('#uploadtr').hide();
        	   $('#setvartr').show();
           }
        };
		
        function editOnChange() {
        	
            if($('#editForm_action').val()=='imaheUpload'){
         	   $('#editForm_uploadtr').show();
         	   $('#editForm_setvartr').hide();
            }else{
         	   $('#editForm_uploadtr').hide();
         	   $('#editForm_setvartr').show();
            }
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
        }
        
        
        
        function onArSuccess(e) {
            console.log("Success (" + e.operation + ") :: " );
            console.log(e);
            if(e.operation=='upload'){
            	console.log('arfileId'+e.response.data.fileId);
            	$('#arfileId').val(e.response.data.fileId);
            	
            }else{
            	var upload = $("#arfiles").data("kendoUpload");
            	$('#arfileId').val('');
    		    upload.removeAllFiles();
            }
        }
		
        
        function onArRemove(e) {
        	e.data = 
            { 
            	
        			fileId: $("#arfileId").val() 
            		
            };
        }
 
        function editOnArSuccess(e) {
            console.log("Success (" + e.operation + ") :: " );
            console.log(e);
            if(e.operation=='upload'){
            	console.log('arfileId'+e.response.data.fileId);
            	$('#editForm_arfileId').val(e.response.data.fileId);
            	
            }else{
            	var upload = $("#editForm_arfiles").data("kendoUpload");
            	$('#editForm_arfileId').val('');
    		    upload.removeAllFiles();
            }
        }
		
        
        function editOnArRemove(e) {
        	e.data = 
            { 
            	
        			fileId: $("#arfileId").val() 
            		
            };
        }
        
        function saveform() {
			$.ajax({
				type : "POST",
				url : '<c:url value = "/admin/arItemUpload/ajaxFastArHtDlSave"/>',
				data : $("#inputForm").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {
						
						closeFormModel();
						reflashGrid();

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
						
						closeFormModel();
						reflashGrid();

					}

				}
			});
		}
        
        function reflashGrid(){
        	var grid = $("#grid").data('kendoGrid');
			grid.dataSource.page(1);
			global.dataSource.read();
        }
        
        
        function edit(id){
          	$('#editFormId').val(id);

        	$.ajax({
				type : "POST",
				url : '<c:url value = "/admin/arItemUpload/ajaxGetArHtDlsInfo"/>',
				data : $("#editForm").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {
						console.log(result.data);
						global.selectList2.value(result.data.dlList[0].action);
						$("#editFormHtId").val(result.data.dlList[0].htId);
						$("#editFormDlId").val(result.data.dlList[0].id);
						$("#editForm_itemName").val(result.data.itemName);
						$("#editForm_itemContent").val(result.data.itemContent);
						$("#editForm_var").val(result.data.dlList[0].var);
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
						global.editFormModal.center().open();
					}

				}
			});
        	
        }
        
        function remove(id){
        	warningConfirm(
					'確定刪除嗎?',
					'本次操作將刪除該筆資料，是否繼續？',
					'是',
					'否',
					function(confirm) {
						if (confirm) {
							$('#removeId').val(id);
							$('#htId').val(id);
							$
									.ajax({
										type : "POST",
										url : '<c:url value = "/admin/arItemUpload/ajaxRemoveHt"/>',
										data : $("#removeForm").serialize(),// serializes the form's elements.
										success : function(result) {
											if (checkAjaxResp(result)) {
												
												reflashGrid();

											}

										}
									});
						}
					});
        }
	</script>
</body>

</html>