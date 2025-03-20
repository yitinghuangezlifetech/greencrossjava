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
					<div class="card-header">
						<button type="button" id="btn_search" class="btn btn-primary ">
							<i class="fas fa-sync-alt"> 重新整理</i>
						</button>
					</div>
					<div class="card-body">
						<div class="row">
							<div class="col-sm-12">
								<div id="grid"></div>
							</div>
						</div>
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
					<h3 class="panel-title">子帳號角色編輯視窗</h3>
					</header>
					<div class='panel-body'>
						<table class="table table-condensed">
							<tr>
								<td><label for="roleName">角色</label></td>
								<td><select id='roleId' name='roleId'></select> <font
									class='errText form-text' id='err_roleId' color='red'></font></td>
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

	<form id='managerAccountForm'>
		<input type="hidden" id='userId' name='id' value="">
		<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
	</form>

	<script>
		$(document)
				.ready(
						function() {

							global.dataSource = new kendo.data.DataSource(
									{
										transport : {
											read : {
												url : '<c:url value = "/admin/subAccountManager/ajaxSubAccountGridList"/>',
												dataType : "jsonp"
											}
										},
										pageSize : 10
									});

							$("#grid")
									.kendoGrid(
											{
												columns : [
														{
															template : "<button type='button' class='btn btn-success btn-sm' onclick='edit(\"#: user_id #\")'>編輯角色</button>&nbsp;"
																	+ "<button type='button' class='btn btn-success btn-sm' onclick='userFunc(\"#: user_id #\")'>帳號功能</button>&nbsp;",
															field : "action",
															title : "動作",
															width : 200
														},
														{
															field : "user_id",
															title : "子帳號",
															width : 200
														},
														{
															field : "user_name",
															title : "子帳號名稱",
															width : 200
														},
														{
															field : "role_name",
															title : "角色",
															width : 150
														},
														{
															field : "user_status",
															title : "狀態",
															width : 100
														},
														{
															field : "user_start_date",
															title : "帳號啟用時間",
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

							$('#formModelClose').click(function(e) {
								closeModel();
							});

							$('#formModelSend').click(function(e) {
								saveRoleSettimg();
							});

							$('#btn_search').click(function(e) {
								reflashGird();
							});

						});

		function edit(userId) {
			getSelectList(function() {
				openModel();
				$('#id').val(userId);
				$('#roleId').kendoDropDownList();

			});
		}

		function openModel() {
			$('#id').val('');
			global.formModel.center().open();
		}

		function closeModel() {
			$('#id').val('');
			global.formModel.center().close();
		}

		//roleId
		function getSelectList(successful) {
			$
					.ajax({
						type : "POST",
						url : '<c:url value = "/admin/subAccountManager/ajaxSubAccountRoleList"/>',
						data : $("#inputForm").serialize(),// serializes the form's elements.
						success : function(result) {
							if (checkAjaxResp(result)) {
								console.log(result);
								$('#roleId').html('');
								var htmlCode = '<option value="">請選擇</option>';
								$
										.each(
												result.data,
												function(i, obj) {
													htmlCode = htmlCode
															+ '<option value="'+obj.role_id+'">'
															+ obj.role_name
															+ '</option>';
												});
								$('#roleId').html(htmlCode);
								successful();

							}

						}
					});
		}

		function saveRoleSettimg() {
			$
					.ajax({
						type : "POST",
						url : '<c:url value = "/admin/subAccountManager/ajaxApplyRoleSetting"/>',
						data : $("#inputForm").serialize(),// serializes the form's elements.
						success : function(result) {
							if (checkAjaxResp(result)) {
								closeModel();
								reflashGird();

							}

						}
					});
		}

		function reflashGird() {
			var grid = $("#grid").data('kendoGrid');
			grid.dataSource.page(1);
			global.dataSource.read();
		}

		function userFunc(userId) {
			$('#userId').val(userId);
			$('#managerAccountForm').attr('action',
					'<c:url value = "/admin/subAccountManager/userFuncList"/>');
			$('#managerAccountForm').attr('method', 'post');
			$('#managerAccountForm').submit();
		}
	</script>
</body>

</html>