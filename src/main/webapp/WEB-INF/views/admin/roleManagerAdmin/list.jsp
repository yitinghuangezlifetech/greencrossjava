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
						<button type="button" id="btn_search" class="btn btn-primary ">
							<i class="fas fa-sync-alt"> 重新整理</i>
						</button>
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
					<h3 class="panel-title">角色管理新修視窗</h3>
					</header>
					<div class='panel-body'>

						<table class="table table-condensed">
							<tr>
								<td><label for="roleType">角色類別</label></td>
								<td><input type="text" id="roleType" class='input'
									name="roleType" value=""> <font
									class='errText form-text' id='err_roleType' color='red'></font></td>
							</tr>

							<tr>
								<td><label for="roleNo">角色編號</label></td>
								<td><input type="text" id="roleNo" class='input'
									name="roleNo" value=""> <font class='errText form-text'
									id='err_roleNo' color='red'></font></td>
							</tr>

							<tr>
								<td><label for="roleName">角色名稱</label></td>
								<td><input type="text" id="roleName" class='input'
									name="roleName" value=""> <font
									class='errText form-text' id='err_roleName' color='red'></font></td>
							</tr>

							<tr>
								<td><label for="useState">狀態</label></td>
								<td><input name="useState" id="useState" /></td>
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



	<script>
		$(document)
				.ready(
						function() {

							global.dataSource = new kendo.data.DataSource(
									{
										transport : {
											read : {
												url : '<c:url value = "/admin/roleManagerAdmin/ajaxRoleGridList"/>',
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
															template : "<button type='button' class='btn btn-success btn-sm' onclick='edit(\"#: role_id #\")'>編輯</button>&nbsp;"
																	+ "<button type='button' class='btn btn-info btn-sm' onclick='func(\"#: role_id #\")'>功能</button>&nbsp;"
																	+ "<button type='button' class='btn btn-danger btn-sm' onclick='remove(\"#: role_id #\")'>刪除</button>&nbsp;",
															field : "action",
															title : "動作",
															width : 200
														},
														{
															field : "role_id",
															title : "角色ID",
															width : 100
														},
														{
															field : "role_name",
															title : "角色名稱",
															width : 150
														},
														{
															field : "role_type",
															title : "角色類別",
															width : 100
														},
														{
															field : "role_no",
															title : "角色編號",
															width : 100
														},
														{
															field : "role_owner",
															title : "角色擁有人",
															width : 100
														},
														{
															field : "use_state",
															title : "狀態",
															width : 100
														},
														{
															field : "create_user",
															title : "建立者",
															width : 150
														},
														{
															field : "create_time",
															title : "建立時間",
															width : 150
														},
														{
															field : "update_user",
															title : "最後更新者",
															width : 150
														},
														{
															field : "update_time",
															title : "最後更新時間",
															width : 150
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

							$('#addBtn').click(function(e) {
								$('#roleType').attr('disabled', false);
								$('#roleNo').attr('disabled', false);
								$('#id').val('');
								global.formModel.center().open();
							});

							$('#btn_search').click(function(e) {
								reflashGird();
							});

							$('#formModelClose').click(function(e) {
								closeFormModel();
							});
							$('#formModelSend').click(function(e) {
								ajaxFormModal();
							});

							$("#useState").kendoMobileSwitch({
								onLabel : "啟用",
								offLabel : "停用"
							});
							global.switchInstance = $("#useState").data(
									"kendoMobileSwitch");
							global.switchInstance.check(true);

						});

		function reflashGird() {
			global.dataSource.read();

		}

		function closeFormModel() {
			$('.errText').text('');
			$('#id').val("");
			$('#roleType').val("");
			$('#roleNo').val("");
			$('#roleName').val("");
			var switchInstance = $("#useState").data("kendoMobileSwitch");
			global.switchInstance.check(true);
			global.formModel.center().close();

		}

		function ajaxFormModal() {
			$('#roleType').attr('disabled', false);
			$('#roleNo').attr('disabled', false);
			$.ajax({
						type : "POST",
						url : '<c:url value = "/admin/roleManagerAdmin/ajaxSaveRole"/>',
						data : $("#inputForm").serialize(),// serializes the form's elements.
						success : function(result) {
							if (checkAjaxResp(result)) {
								closeFormModel();
								reflashGird();

							} else {

							}

						}
					});
		}
		function edit(roleId) {
			$('#id').val(roleId);
			$.ajax({
						type : "POST",
						url : '<c:url value = "/admin/roleManagerAdmin/ajaxGetRoleById"/>',
						data : $("#inputForm").serialize(),// serializes the form's elements.
						success : function(result) {
							if (checkAjaxResp(result)) {

								$('#roleType').val(result.data.roleType);
								$('#roleType').attr('disabled', true);
								$('#roleNo').val(result.data.roleNo);
								$('#roleNo').attr('disabled', true);
								$('#roleName').val(result.data.roleName);
								if (result.data.useState == 'Y') {
									global.switchInstance.check(true);
								} else {
									global.switchInstance.check(false);
								}

								global.formModel.center().open();

							}

						}
					});

		}

		function remove(roleId) {
			warningConfirm(
					'確定刪除嗎?',
					'本次操作將刪除該角色所有相關資料，是否繼續？',
					'是',
					'否',
					function(confirm) {
						if (confirm) {
							$('#id').val(roleId);
							$
									.ajax({
										type : "POST",
										url : '<c:url value = "/admin/roleManagerAdmin/ajaxRemoveRoleById"/>',
										data : $("#inputForm").serialize(),// serializes the form's elements.
										success : function(result) {
											if (checkAjaxResp(result)) {
												reflashGird();
												showSuccessAlert("刪除！", "刪除成功");
											}

										}
									});
						}
					});

		}

		function func(roleId) {
			$('#id').val(roleId);
			$('#inputForm').attr('action',
					'<c:url value = "/admin/roleManagerAdmin/funcRoleList"/>');
			$('#inputForm').attr('method', 'post');
			$('#inputForm').submit();
		}
	</script>
</body>

</html>