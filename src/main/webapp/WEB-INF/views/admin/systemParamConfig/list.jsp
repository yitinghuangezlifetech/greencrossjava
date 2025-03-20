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
						
					</div>
					<div class="card-body">
						<div class="container-fluid">
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="searchName">參數名稱</label>
										</div>
										<div class="col-sm-12">
											<input type="text" class="input" name="searchName" id="searchName" value="" style="width: 100%;">
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="card-footer text-right">
						<button type="button" id="btn_search" class="btn btn-primary ">
							<i class="fas fa-search"> 搜尋</i>
						</button>
					</div>
				</div>

			</div>
		</div>
		
		<div class="row">
			<div class="col-sm-12">
				<div class="card">
					<div class="card-header text-right">
						<button type="button" id="addBtn" class="btn btn-primary ">
							<i class="fas fa-plus"> 新增</i>
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


			<div class="panel panel-bordered">
				<form id="inputForm">
					<input type="hidden" id="id" name="id" value="">
					<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
					<header class="panel-heading">
					<h3 class="panel-title">參數主檔新修視窗</h3>
					</header>
					<div class='panel-body'>

						<table class="table table-condensed">
							<tr>
								<td><label for="paramCode">參數代號</label></td>
								<td><input type="text" id="paramCode" class='input' name="paramCode" value=""> 
								<font class='errText form-text' id='err_paramCode' color='red'></font></td>
							</tr>
							
							<tr>
								<td><label for="paramName">參數名稱</label></td>
								<td><input type="text" id="paramName" class='input' name="paramName" value=""> 
								<font class='errText form-text' id='err_paramName' color='red'></font></td>
							</tr>
							
							<tr>
								<td><label for="paramContent">參數說明</label></td>
								<td><input type="text" id="paramContent" class='input' name="paramContent" value=""> 
								<font class='errText form-text' id='err_paramContent' color='red'></font></td>
							</tr>
							
							<tr>
								<td><label for="name1">參數1說明</label></td>
								<td><input type="text" id="name1" class='input' name="name1" value=""> 
								<font class='errText form-text' id='err_name1' color='red'></font></td>
							</tr>
							
							<tr>
								<td><label for="name2">參數2說明</label></td>
								<td><input type="text" id="name2" class='input' name="name2" value=""> 
								<font class='errText form-text' id='err_name2' color='red'></font></td>
							</tr>
							
							<tr>
								<td><label for="name3">參數3說明</label></td>
								<td><input type="text" id="name3" class='input' name="name3" value=""> 
								<font class='errText form-text' id='err_name3' color='red'></font></td>
							</tr>
							
							<tr>
								<td><label for="name4">參數4說明</label></td>
								<td><input type="text" id="name4" class='input' name="name4" value=""> 
								<font class='errText form-text' id='err_name4' color='red'></font></td>
							</tr>
							
							<tr>
								<td><label for="name5">參數5說明</label></td>
								<td><input type="text" id="name5" class='input' name="name5" value=""> 
								<font class='errText form-text' id='err_name5' color='red'></font></td>
							</tr>
							
							
						</table>
						<footer class="panel-footer text-right">
						<button type="button" id="formModelSend" class="btn btn-primary ">
							<span class="glyphicon glyphicon-ok"></span>送出
						</button>
						<button type="button" id="formModelClose" class="btn btn-primary ">
							<span class="glyphicon glyphicon-remove"></span>關閉
						</button>
						</footer>











					</div>

				</form>
			</div>

		</div>
	</div>

	<form id="dlForm">
		<input type="hidden" id="dlFormid" name="id" value="">
		<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
	</form>


	<script>
		$(document).ready(function() {
			
			$('#btn_search').click(function(e) {
				global.dataSource.read();
			});
			
			$('#addBtn').click(function(e) {
				add();
			});
			
			$('#formModelClose').click(function(e) {
				closeKendoWindow();
			});
			
			$('#formModelSend').click(function(e) {
				saveForm();
			});
			
			global.dataSource = new kendo.data.DataSource(
					{
						transport : {
							read : {
								type : "POST",
								url : '<c:url value = "/admin/systemParamConfig/ajaxSystemParamHtGridList"/>',
								dataType : "jsonp",
								data : function() {
									return {
										searchName:$('#searchName').val()
									};
								}
							}
						},
						pageSize : 10
					});
			$("#grid")
			.kendoGrid(
					{
						columns : [
								{
									template : "<button type='button' class='btn btn-success btn-sm' onclick='edit(\"#:id#\")'>編輯</button>&nbsp;"
											+"<button type='button' class='btn btn-info btn-sm' onclick='goToDl(\"#:id#\")'>參數明細</button>&nbsp;"
											+ "<button type='button' class='btn btn-danger btn-sm' onclick='remove(\"#:id#\")'>刪除</button>&nbsp;",
									field : "action",
									title : "動作",
									width : 200
								},
								{
									field : "param_name",
									title : "參數名稱",
									width : 200
								},
								{
									field : "param_code",
									title : "參數代號",
									width : 200
								},
								{
									field : "param_content",
									title : "參數說明",
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
									width : 200
								},
								{
									field : "update_user",
									title : "最後更新者",
									width : 150
								},
								{
									field : "update_time",
									title : "最後更新時間",
									width : 200
								} ],
						dataSource : global.dataSource,
						sortable : true,
						pageable : true
					});
			
			
			global.formModel = $("#formModal").kendoWindow({

				modal : true,
				title : false,
				visible : false,
				iframe : false,
			}).data("kendoWindow");
			
		});
		
		
		function saveForm(){
			$.ajax({
				type : "POST",
				url : '<c:url value = "/admin/systemParamConfig/ajaxSaveSystemParamHt"/>',
				data : $("#inputForm").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {
						closeKendoWindow();
						$('#searchName').val('');
						global.dataSource.read();
					}

				}
			});
		}
		
		
		function add(){
			resetForm();
			openKendoWindow();
		}
		
		function edit(id){
			$('#id').val(id);
			$.ajax({
				type : "POST",
				url : '<c:url value = "/admin/systemParamConfig/ajaxGetSystemParamHtById"/>',
				data : $("#inputForm").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {
						$('#id').val(result.data.id);
						$('#paramCode').val(result.data.paramCode);
						$('#paramName').val(result.data.paramName);
						$('#paramContent').val(result.data.paramContent);
						$('#name1').val(result.data.name1);
						$('#name2').val(result.data.name2);
						$('#name3').val(result.data.name3);
						$('#name4').val(result.data.name4);
						$('#name5').val(result.data.name5);
						
						$('#paramCode').prop('disabled', true);
						
						openKendoWindow();
					}

				}
			});
		}
		
		function goToDl(id){
			$('#dlFormid').val(id);
			$('#dlForm').attr('action','<c:url value = "/admin/systemParamConfig/detailList"/>');
			$('#dlForm').attr('method','post');
			$('#dlForm').submit();
		}
		
		function remove(id){
			warningConfirm(
				'確定刪除嗎?',
				'本次操作將刪除該參數主檔內，所有參數明相關資料，是否繼續？',
				'是',
				'否',
				function(confirm) {
					if (confirm) {
						$('#id').val(id);
						$.ajax({
							type : "POST",
							url : '<c:url value = "/admin/systemParamConfig/ajaxRemoveSystemParamHt"/>',
							data : $("#inputForm").serialize(),// serializes the form's elements.
							success : function(result) {
								if (checkAjaxResp(result)) {
									$('#searchName').val('');
									global.dataSource.read();
									resetForm();
								}

							}
						});
					}
			});
		}	
		
		function openKendoWindow(){
			global.formModel.center().open();
		}
		
		function closeKendoWindow(){
			resetForm();
			global.formModel.center().close();
		}
		
		function resetForm(){
			$('#id').val('');
			$('#paramCode').val('');
			$('#paramName').val('');
			$('#paramContent').val('');
			$('#name1').val('');
			$('#name2').val('');
			$('#name3').val('');
			$('#name4').val('');
			$('#name5').val('');
			$('.errText').text('');
			$('#paramCode').prop('disabled', false);
		}
		
		
	</script>
</body>

</html>