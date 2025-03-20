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
						${sessionScope.funcName}-帳號功能設定
					</div>
					<div class="card-body">
						<div class="row ">
							<div class="col-sm-6">
								<div class="form-group ">
									<div class="col-sm-12">
										<label for="vcode">帳號</label>
									</div>
									<div class="col-sm-12">
										<input type="text" id='idStr' class="form-control input-sm"
											value="${userDto.id}" disabled="disabled">  
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group ">
									<div class="col-sm-12">
										<label for="vcodeName">使用者名稱</label>
									</div>
									<div class="col-sm-12">
										<input type="text" id='userNameStr'
											class="form-control input-sm" value="${userDto.userName}"
											disabled="disabled">
									</div>
								</div>
							</div>
						</div>
						<div class="row ">
							<div class="col-sm-6">
								<div class="form-group ">
									<div class="col-sm-12">
										<label for="idStr">使用者角色</label>
									</div>
									<div class="col-sm-12">
										<input type="text" id='idStr' class="form-control input-sm"
											value="${userDto.roleName}" disabled="disabled">
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




	<form id='managerAccountForm'>
		<input type="hidden" id='userId' name='userId' value="${userDto.id}">
		<input type="hidden" id='rfuId' name='id' value=""> 
		<input type="hidden" id='funcNo' name='funcNo' value="">
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
												url : '<c:url value = "/admin/accountManagerAdmin/ajaxUserFunGridList"/>?userId=${userDto.id}',
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
															template : "#if(rfu_id!=''){#"
																	+ "<button type='button' class='btn btn-danger btn-sm' onclick='cancle(\"#: rfu_id #\")'>取消</button>&nbsp;"
																	+ "#}else{#"
																	+ "<button type='button' class='btn btn-success btn-sm' onclick='apply(\"#: func_id #\")'>套用</button>&nbsp;"
																	+ "#}#",
															field : "bAction",
															title : "動作",
															width : 100
														},
														{
															field : "func_id",
															title : "功能ID",
															width : 150
														},
														{
															field : "func_name",
															title : "功能名稱",
															width : 170
														},
														{
															template : "#if(rfid!=''){#"
																	+ "<span class='glyphicon glyphicon-ok'></span>已套用"
																	+ "#}else{#"
																	+ "<span class='glyphicon glyphicon-minus'></span>未套用"
																	+ "#}#",
															field : "bIsRoleUse",
															title : "角色套用狀態",
															width : 100
														},
														{
															field : "use_state",
															title : "功能狀態",
															width : 100
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

		function cancle(rufId) {
			$('#rfuId').val(rufId);
			$('#funcNo').val('');
			$
					.ajax({
						type : "POST",
						url : '<c:url value = "/admin/accountManagerAdmin/ajaxCancelFunc"/>',
						data : $("#managerAccountForm").serialize(),// serializes the form's elements.
						success : function(result) {
							if (checkAjaxResp(result)) {

							} else {

							}
							reflashGird();
						}
					});
		}

		function apply(funcId) {
			$('#rfuId').val('');
			$('#funcNo').val(funcId);
			$
					.ajax({
						type : "POST",
						url : '<c:url value = "/admin/accountManagerAdmin/ajaxApplyFunc"/>',
						data : $("#managerAccountForm").serialize(),// serializes the form's elements.
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