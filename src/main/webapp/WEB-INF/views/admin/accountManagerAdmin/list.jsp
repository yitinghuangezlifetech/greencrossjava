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
						<div class="row">
							<div class="col-sm-6  text-left">
								<div class="form-group ">
									<div class="col-sm-12">
										<label for="vcode">使用者帳號</label>
									</div>
									<div class="col-sm-12">
										<input type="text" id='vcode' name='vcode'
											class="input" value="" style="width: 100%;">
									</div>
								</div>
							</div>
							<div class="col-sm-6  text-left">
								<div class="form-group ">
									<div class="col-sm-12">
										<label for="vcodeName">使用者名稱</label>
									</div>
									<div class="col-sm-12">
										<input type="text" id='vcodeName' name='vcodeName'
											class="input" value="" style="width: 100%;">
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12  text-right">
								<button type="button" id="btn_search" class="btn btn-primary ">
									<span class="glyphicon glyphicon-search"></span>搜尋
								</button>
								<button type="button" id="btn_add" class="btn btn-primary ">
									<span class="glyphicon glyphicon-plus"></span>新增帳號
								</button>
							</div>
						</div>
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

												url : '<c:url value = "/admin/accountManagerAdmin/ajaxAccountGridList"/>?vcode='
														+ $('#vcode').val()
														+ '&vcodeName='
														+ $('#vcodeName').val(),
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
															template : "<button type='button' class='btn btn-success btn-sm' onclick='edit(\"#: user_id #\")'>管理帳號</button>&nbsp;"
																	+ "<button type='button' class='btn btn-success btn-sm' onclick='userFunc(\"#: user_id #\")'>帳號功能</button>&nbsp;",
															field : "action",
															title : "動作",
															width : 200
														},
														{
															field : "user_id",
															title : "帳號ID",
															width : 200
														},
														{
															field : "user_name",
															title : "帳號名稱",
															width : 200
														},
														{
															field : "ban",
															title : "統編",
															width : 200
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

							$('#btn_search').click(function(e) {
								reflashGird();
							});

							$('#btn_add')
									.click(
											function(e) {
												$('#userId').val('');
												$('#managerAccountForm')
														.attr('action',
																'<c:url value = "/admin/accountManagerAdmin/accountManager"/>');
												$('#managerAccountForm').attr(
														'method', 'post');
												$('#managerAccountForm')
														.submit();
											});

						});

		function edit(userId) {
			$('#userId').val(userId);
			$('#managerAccountForm')
					.attr('action',
							'<c:url value = "/admin/accountManagerAdmin/accountManager"/>');
			$('#managerAccountForm').attr('method', 'post');
			$('#managerAccountForm').submit();
		}

		function userFunc(userId) {
			$('#userId').val(userId);
			$('#managerAccountForm')
					.attr('action',
							'<c:url value = "/admin/accountManagerAdmin/userFuncList"/>');
			$('#managerAccountForm').attr('method', 'post');
			$('#managerAccountForm').submit();
		}

		function reflashGird() {
			var grid = $("#grid").data('kendoGrid');
			grid.dataSource.page(1);
			resetDataSourceUrl();
			global.dataSource.read();
		}

		function resetDataSourceUrl() {
			global.dataSource.transport.options.read.url = '<c:url value = "/admin/accountManagerAdmin/ajaxAccountGridList"/>?vcode='
					+ $('#vcode').val() + '&vcodeName=' + $('#vcodeName').val();
		}
	</script>
</body>

</html>