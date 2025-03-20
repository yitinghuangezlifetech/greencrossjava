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
					<div class="card-header">${sessionScope.funcName}-功能</div>
					<div class="card-body">
						<div class="container-fluid">
							<div class="row">
								<div class="col-sm-12">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="roleType">角色名稱</label>
										</div>
										<div class="col-sm-12">
											<input type="text" class="form-control input-sm"
												value="${roleDto.roleName}" disabled="disabled">
										</div>
									</div>
								</div>

							</div>
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="roleType">角色類型</label>
										</div>
										<div class="col-sm-12">
											<input type="text" class="form-control input-sm"
												value="${roleDto.roleType}" disabled="disabled">
										</div>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="roleType">角色編號</label>
										</div>
										<div class="col-sm-12">
											<input type="text" class="form-control input-sm"
												value="${roleDto.roleNo}" disabled="disabled">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="roleType">角色ID</label>
										</div>
										<div class="col-sm-12">
											<input type="text" class="form-control input-sm"
												value="${roleDto.id}" disabled="disabled">
										</div>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="roleType">角色狀態</label>
										</div>
										<div class="col-sm-12">
											<input type="text" class="form-control input-sm"
												value="${roleDto.useState}" disabled="disabled">
										</div>
									</div>
								</div>
							</div>
							<hr/>
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
	</div>
	
	<form id='ajaxForm'>
		<input type='hidden' id='id' name='id' value="${roleDto.id}">
		<input type='hidden' id='roleType' name='roleType' value="${roleDto.roleType}"> 
		<input type='hidden' id='roleNo' name='roleNo' value="${roleDto.roleNo}"> 
		<input type='hidden' id='roleName' name='roleName' value="${roleDto.roleName}"> 
		<input type='hidden' id='useState' name='useState' value="${roleDto.useState}"> 
		<input type='hidden' id='funcNo' name='funcNo' value="">
		<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
	</form>

	<script>
		$(document)
				.ready(
						function() {

							global.dataSource = new kendo.data.TreeListDataSource(
									{
										transport : {
											read : {
												type : "POST",
												url : '<c:url value = "/admin/roleManagerAdmin/ajaxRoleFunGridList"/>?roleType=${roleDto.roleType}&roleNo=${roleDto.roleNo}',
												dataType : "jsonp",
												complete : function(jqXHR,
														textStatus) {
													console.log(textStatus,
															"read");
													expandAll();
												}
											}
										}
									});

							$("#grid")
									.kendoTreeList(
											{
												columns : [
														{
															template : "#if(rfid!=''){#"
																	+ "<button type='button' class='btn btn-danger btn-sm' onclick='cancle(\"#: func_id #\")'>取消</button>&nbsp;"
																	+ "#}else{#"
																	+ "<button type='button' class='btn btn-success btn-sm' onclick='apply(\"#: func_id #\")'>套用</button>&nbsp;"
																	+ "#}#",
															field : "bAction",
															title : "動作",
															width : 150
														},
														{
															field : "func_id",
															title : "功能ID",
															width : 170
														},
														{
															field : "func_name",
															title : "功能名稱",
															width : 170
														},
														{
															field : "use_state",
															title : "功能狀態",
															width : 150
														} ],
												dataSource : global.dataSource,

												sortable : true
											});

						});

		function expandAll() {
			var treeList = $("#grid").data("kendoTreeList");
			var rows = $("tr.k-treelist-group", treeList.tbody);
			$.each(rows, function(idx, row) {
				treeList.expand(row);
			});
		}

		function cancle(func_id) {
			$('#funcNo').val(func_id);
			$
					.ajax({
						type : "POST",
						url : '<c:url value = "/admin/roleManagerAdmin/ajaxCancelFunc"/>',
						data : $("#ajaxForm").serialize(),// serializes the form's elements.
						success : function(result) {
							if (checkAjaxResp(result)) {

							} else {

							}
							reflashGird();
						}
					});
		}
		function apply(func_id) {
			$('#funcNo').val(func_id);
			$
					.ajax({
						type : "POST",
						url : '<c:url value = "/admin/roleManagerAdmin/ajaxApplyFunc"/>',
						data : $("#ajaxForm").serialize(),// serializes the form's elements.
						success : function(result) {
							if (checkAjaxResp(result)) {

							} else {

							}
							reflashGird();
						}
					});
		}

		function reflashGird() {
			global.dataSource.read();

		}
	</script>
</body>

</html>

