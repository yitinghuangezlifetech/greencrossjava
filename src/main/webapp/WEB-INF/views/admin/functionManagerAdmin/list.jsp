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
							<div class="col-sm-12  text-left">
								主功能 <select id='mainFuncSel'>
									<option value="000000">全部主功能</option>
									<c:forEach items="${funcSelMapList}" var="current">
										<option value="${current.funcId}">${current.funcName}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12  text-right" style='margin-top: 20px;'>
								<button type="button" id="btn_search" class="btn btn-primary ">
									<i class="fas fa-sync-alt"> 重新整理</i>
								</button>
								<button type="button" id="addBtn" class="btn btn-primary ">
									<i class="fas fa-plus"> 新增</i>
								</button>
								<button type="button" id="sortBtn" class="btn btn-primary ">
									<i class="fas fa-sort-amount-down"> 排序</i>
								</button>
							</div>
						</div>


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
					<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
					<input type="hidden" id="formModalId" name="id" value="">
					<header class="panel-heading">
					<h3 class="panel-title">功能新修視窗</h3>
					</header>
					<div class='panel-body'>
						<table class="table table-condensed">
							<tr>
								<td><label for="funcNo">功能ID</label></td>
								<td><input type="text" class="input" id="funcNo"
									name="funcNo" value=""><font class='errText form-text'
									id='err_funcNo' color='red'></font></td>
							</tr>


							<tr>
								<td><label for="funcName">功能名稱</label></td>
								<td><input type="text" class="input" id="funcName"
									name="funcName" value=""><font
									class='errText form-text' id='err_funcName' color='red'></font></td>
							</tr>

							<tr>
								<td><label for="parentId">上層功能</label></td>
								<td><select id='parentId' name='parentId'>

								</select><font class='errText form-text' id='err_parentId' color='red'></font></td>
							</tr>

							<tr>
								<td><label for="funcPict">功能圖示</label></td>
								<td><input type="text" class="input" id="funcPict"
									name="funcPict" value=""><font
									class='errText form-text' id='err_funcPict' color='red'></font>
									<a href="https://fontawesome.com/icons?d=gallery&m=free"
									target="_blank">圖示網址</a></td>
							</tr>

							<tr>
								<td><label for="funcUri">功能URI</label></td>
								<td><input type="text" class="input" id="funcUri"
									name="funcUri" value=""><font class='errText form-text'
									id='err_funcUri' color='red'></font></td>
							</tr>

							<tr>
								<td><label for="useState">狀態</label></td>
								<td><input name="useState" class="form-control"
									id="useState" /></td>
							</tr>
						</table>
						<footer class="panel-footer text-right">
						<button type="button" id="formModalSend" class="btn btn-primary ">
							<span class="glyphicon glyphicon-ok"></span>送出
						</button>
						<button type="button" id="formModalClose" class="btn btn-primary ">
							<span class="glyphicon glyphicon-remove"></span>關閉
						</button>
						</footer>
					</div>
				</form>



			</div>

		</div>
	</div>


	<div>
		<div id="formSortModal">

			<div class="panel panel-bordered">
				<form id="inputSortForm">
					<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
					<input type="hidden" id="sortParentId" name="parentId" value="">
					<header class="panel-heading">
					<h3 class="panel-title">功能排序編輯視窗</h3>
					</header>
					<div class='panel-body'>
						<div id="sortable-handlers"></div>
						<footer class="panel-footer text-right">
						<button type="button" id="formSortModalSend"
							class="btn btn-primary ">
							<span class="glyphicon glyphicon-ok"></span>送出
						</button>
						<button type="button" id="formSortModalClose"
							class="btn btn-primary ">
							<span class="glyphicon glyphicon-remove"></span>關閉
						</button>
						</footer>
					</div>
				</form>

			</div>
		</div>
	</div>

	<form id='removeForm'>
		<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
		<input type="hidden" id="removeFuncId" name="id" value="">
	</form>


	<script>
		$(document)
				.ready(
						function() {
							$('#mainFuncSel').kendoDropDownList();

							$("#useState").kendoMobileSwitch({
								onLabel : "啟用",
								offLabel : "停用"
							});

							global.dataSource = new kendo.data.DataSource(
									{
										transport : {
											read : {
												type : "POST",
												url : '<c:url value = "/admin/functionManagerAdmin/ajaxFuncGridList"/>',
												dataType : "jsonp",
												data : function() {
													return {
														parentId : $(
																'#mainFuncSel')
																.val()
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
															template : "<button type='button' class='btn btn-success btn-sm' onclick='edit(\"#:func_id#\")'>編輯</button>&nbsp;"
																	+ "<button type='button' class='btn btn-danger btn-sm' onclick='remove(\"#:func_id#\")'>刪除</button>&nbsp;",
															field : "action",
															title : "動作",
															width : 150
														},
														{
															field : "func_id",
															title : "功能ID",
															width : 150
														},
														{
															field : "func_name",
															title : "功能名稱",
															width : 200
														},
														{
															field : "func_parent_name",
															title : "上層功能名稱",
															width : 200
														},
														{
															field : "func_uri",
															title : "功能網址",
															width : 300
														},
														{
															field : "use_state",
															title : "狀態",
															width : 150
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

							//mainFuncSel
							$('#mainFuncSel').on('change', function() {
								reflashGird();

							});

							global.formSortModal = $("#formSortModal")
									.kendoWindow({

										modal : true,
										title : false,
										visible : false,
										iframe : false,
									}).data("kendoWindow");

							//formModal
							global.formModal = $("#formModal").kendoWindow({

								modal : true,
								title : false,
								visible : false,
								iframe : false,
							}).data("kendoWindow");

							$('#addBtn').click(function(e) {
								openAddWindown();
							});

							//sortBtn
							$('#sortBtn').click(function(e) {
								openSortWindows();
							});

							$('#btn_search').click(function(e) {
								reflashGird();
							});

							$('#formSortModalClose').click(function(e) {
								global.formSortModal.center().close();
							});

							$('#formModalClose').click(function(e) {
								global.formModal.center().close();
							});

							//formSortModalSend
							$('#formSortModalSend').click(function(e) {
								saveformSort();
							});

							$('#formModalSend').click(function(e) {
								saveform();
							});
							global.switchInstance = $("#useState").data(
									"kendoMobileSwitch");
						});

		function openAddWindown() {
			$('#formModalId').val('');

			$('#funcNo').attr('disabled', false);
			$('#funcNo').val('');
			$('#funcName').val('');
			$('#funcUri').val('');
			$
					.ajax({
						type : "POST",
						url : '<c:url value = "/admin/functionManagerAdmin/ajaxGetMainFuncList"/>',
						data : $("#inputForm").serialize(),// serializes the form's elements.
						success : function(result) {
							if (checkAjaxResp(result)) {
								console.log(result);
								$('#parentId').html('');
								var htmlCode = '<option value="">請選擇</option>'
										+ '<option value="000000">主功能</option>';
								$.each(result.data, function(i, obj) {
									htmlCode = htmlCode
											+ '<option value="'+obj.funcId+'">'
											+ obj.funcName + '</option>';
								});
								$('#parentId').html(htmlCode);
								$('#parentId').kendoDropDownList();
								global.switchInstance.check(true);
								global.formModal.center().open();

							}

						}
					});
		}

		function saveform() {
			$
					.ajax({
						type : "POST",
						url : '<c:url value = "/admin/functionManagerAdmin/ajaxSaveFunc"/>',
						data : $("#inputForm").serialize(),// serializes the form's elements.
						success : function(result) {
							if (checkAjaxResp(result)) {
								global.formModal.center().close();
								reflashGird();

							}

						}
					});
		}

		function saveformSort() {
			$
					.ajax({
						type : "POST",
						url : '<c:url value = "/admin/functionManagerAdmin/ajaxSaveSort"/>',
						data : $("#inputSortForm").serialize(),// serializes the form's elements.
						success : function(result) {
							if (checkAjaxResp(result)) {
								global.formSortModal.center().close();
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

		function openSortWindows() {
			$('#sortParentId').val($('#mainFuncSel').val());
			$
					.ajax({
						type : "POST",
						url : '<c:url value = "/admin/functionManagerAdmin/ajaxFuncListByParentId"/>',
						data : $("#inputSortForm").serialize(),// serializes the form's elements.
						success : function(result) {
							if (checkAjaxResp(result)) {

								$('#sortable-handlers').html('');
								var htmlCode = '';
								$
										.each(
												result.data,
												function(i, obj) {
													htmlCode = htmlCode
															+ '<div class="item">';
													htmlCode = htmlCode
															+ ' <span class="handler">&nbsp;</span>';
													htmlCode = htmlCode
															+ ' <span>'
															+ obj.funcName
															+ '</span>';
													htmlCode = htmlCode
															+ '<input type="hidden" name="sortFuncId" value="'+obj.funcId+'">';
													htmlCode = htmlCode
															+ '</div>';
												});

								$('#sortable-handlers').html(htmlCode);

								$("#sortable-handlers").kendoSortable(
										{
											handler : ".handler",
											hint : function(element) {
												return element.clone()
														.addClass("hint");
											}
										});
								global.formSortModal.center().open();

							}

						}
					});
		}

		function remove(funcId) {
			warningConfirm(
					'確定刪除嗎?',
					'本次操作將刪除該功能，是否繼續？',
					'是',
					'否',
					function(confirm) {
						if (confirm) {
							$('#removeFuncId').val(funcId);
							$
									.ajax({
										type : "POST",
										url : '<c:url value = "/admin/functionManagerAdmin/ajaxRemoveFunc"/>',
										data : $("#removeForm").serialize(),// serializes the form's elements.
										success : function(result) {
											if (checkAjaxResp(result)) {
												global.formSortModal.center()
														.close();
												reflashGird();

											}

										}
									});
						}
					});
		}

		function edit(funcId) {
			$('#formModalId').val(funcId);
			$
					.ajax({
						type : "POST",
						url : '<c:url value = "/admin/functionManagerAdmin/ajaxGetFuncById"/>',
						data : $("#inputForm").serialize(),// serializes the form's elements.
						success : function(result) {
							if (checkAjaxResp(result)) {
								console.log(result);
								var inresult = result;
								$
										.ajax({
											type : "POST",
											url : '<c:url value = "/admin/functionManagerAdmin/ajaxGetMainFuncList"/>',
											data : $("#inputForm").serialize(),// serializes the form's elements.
											success : function(result) {
												if (checkAjaxResp(result)) {
													console.log(result);
													$('#parentId').html('');
													var htmlCode = '<option value="">請選擇</option>'
															+ '<option value="000000">主功能</option>';
													$
															.each(
																	result.data,
																	function(i,
																			obj) {
																		htmlCode = htmlCode
																				+ '<option value="'+obj.funcId+'">'
																				+ obj.funcName
																				+ '</option>';
																	});
													$('#parentId').html(
															htmlCode);
													$('#parentId')
															.val(
																	inresult.data.parentId);
													global.formModal.center()
															.open();
													$('#parentId')
															.kendoDropDownList();

													$('#funcNo').val(
															inresult.data.id);
													$('#funcNo').attr(
															'disabled', true);
													$('#funcName')
															.val(
																	inresult.data.funcName);
													$('#funcUri')
															.val(
																	inresult.data.funcUri);
													$('#funcPict')
															.val(
																	inresult.data.funcPict);
													if (inresult.data.useState == 'Y') {
														global.switchInstance
																.check(true);
													} else {
														global.switchInstance
																.check(false);
													}

												}

											}
										});

							}

						}
					});
		}
	</script>
</body>

</html>