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
										<label for="vcode">統編 : </label>
										<span>${comHtDto.ban}</span>
									</div>
								</div>
							</div>
							<div class="col-sm-6  text-left">
								<div class="form-group ">
									<div class="col-sm-12">
										<label for="vcodeName">公司名稱 : </label>
										<span>${comHtDto.comName}</span>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6  text-left">
								<div class="form-group ">
									<div class="col-sm-12">
										<label for="vcode">倉編</label>
									</div>
									<div class="col-sm-12">
										<input type="text" id='vcode' name='vcode'
											class="input" value="" style="width: 80%;">
									</div>
								</div>
							</div>
							<div class="col-sm-6  text-left">
								<div class="form-group ">
									<div class="col-sm-12">
										<label for="vcodeName">棚編</label>
									</div>
									<div class="col-sm-12">
										<input type="text" id='vcodeName' name='vcodeName'
											class="input" value="" style="width: 80%;">
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12  text-right">
								<button type="button" id="btn_search" class="btn btn-primary ">
									<span class="glyphicon glyphicon-search">搜尋</span>
								</button>
								<button type="button" id="btn_add" class="btn btn-primary ">
									<span class="glyphicon glyphicon-plus">新增</span>
								</button>
							</div>
						</div>
					</div>
					<div class="card-body">
						<div class="row">
							<div class="col-sm-12">
								<div id="grid"></div>
								<div id="grid2"></div>
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
					<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
					<input type="hidden" id="formModalId" name="formModalId" value="">
					<input type="hidden" id="id" name="id" value="${id}">
					<input type="hidden" id="create_user" name="create_user" value="${create_user}">
					<input type="hidden" id="create_time" name="create_time" value="${create_time}">
					<input type="hidden" id="whComId" name="whComId" value="20190623131341951">
					<header class="panel-heading">
						<h3 class="panel-title">新增倉庫</h3>
					</header>
					<div class='panel-body'>
						<table class="table table-condensed">
							<tr>
								<td><label for="whId">倉庫代號</label></td>
								<td><input type="text" class="input" id="whId"
									name="whId" value=""><font
									class='errText form-text' id='err_whId' color='red'></font></td>
							</tr>

							<tr>
								<td><label for="whName">倉庫名稱</label></td>
								<td><input type="text" class="input" id="whName"
									name="whName" value=""><font class='errText form-text'
									id='err_whName' color='red'></font></td>
							</tr>

							<tr>
								<td><label for="whAddr">地址</label></td>
								<td><input type="text" class="input" id="whAddr"
									name="whAddr" value=""><font class='errText form-text'
									id='err_whAddr' color='red'></font></td>
							</tr>
							
							<tr>
								<td><label for="status">狀態</label></td>
								<td><input name="status" class="form-control"
									id="status" /></td>
							</tr>
						</table>
						
						<footer class="panel-footer text-right">
						<button type="button" id="formModalSend" class="btn btn-primary ">
							<span class="glyphicon glyphicon-ok"></span>確定
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
		<div id="dlFormModal">
			<div class="panel panel-bordered">
				<form id="dlInputForm">
					<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
					<!--input type="hidden" id="formModalId" name="id" value="" -->
					<input type="hidden" id="topId" name="topId" value="${id}">
					<input type="hidden" id="id" name="id" value="${id}">
					<input type="hidden" id="type" name="type" value="1">
					<input type="hidden" id="whComId" name="whComId" value="20190623131341951">
					<input type="hidden" id="create_user" name="create_user" value="${create_user}">
					<input type="hidden" id="create_time" name="create_time" value="${create_time}">
					<!-- input type="hidden" id="whComId" name="whComId" value="${whComId}" -->
					<header class="panel-heading">
					<h3 class="panel-title">功能新修視窗</h3>
					</header>
					<div class='panel-body'>
						<table class="table table-condensed">
							<tr>
								<td><label for="whId">倉庫代號</label></td>
								<td><input type="text" class="input" id="whId"
									name="whId" value="${whId}" readOnly><font
									class='errText form-text' id='err_whId' color='red'></font></td>
							</tr>

							<tr>
								<td><label for="whName">倉庫名稱</label></td>
								<td><input type="text" class="input" id="whName"
									name="whName" value="${wh_name}" readOnly><font class='errText form-text'
									id='err_whName' color='red'></font></td>
							</tr>

							<tr>
								<td><label for="dlId">棚編</label></td>
								<td><input type="text" class="input" id="dlId"
									name="dlId" value=""><font class='errText form-text'
									id='err_dlId' color='red'></font></td>
							</tr>
							
							<tr>
								<td><label for="dlName">棚別</label></td>
								<td><input type="text" class="input" id="dlName"
									name="dlName" value=""><font class='errText form-text'
									id='err_dlName' color='red'></font></td>
							</tr>

							<tr>
								<td><label for="dutyNum">編制</label></td>
								<td><input type="text" class="input" id="dutyNum"
									name="dutyNum" value=""><font class='errText form-text'
									id='err_dutyNum' color='red'></font></td>
							</tr>
							
							<tr>
								<td><label for="useState">狀態</label></td>
								<td><input name="useState" class="form-control"
									id="useState" /></td>
							</tr>
						</table>
						<footer class="panel-footer text-right">
						<button type="button" id="dlFormModalSend" class="btn btn-primary ">
							<span class="glyphicon glyphicon-ok"></span>確定
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

	<script>
		$(document)
				.ready(
						function() {
							
							//倉庫資料
							global.dataSource = new kendo.data.DataSource(
									{
										transport : {
											read : {
												url : '<c:url value = "/admin/wareHouseProf/ajaxHtGridList"/>',
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
												template : "<button type='button' class='btn btn-success btn-sm' onclick='addDl(\"#:id #\",\"#:wh_id#\", \"#:wh_name#\")'>新增棚別</button>&nbsp;"
															+ "<button type='button' class='btn btn-success btn-sm' onclick='edit(\"#:id #\",\"#:wh_id#\", \"#:wh_name#\", \"#:wh_addr#\")'>編輯</button>&nbsp;"	 
															+ "<button type='button' class='btn btn-danger btn-sm' onclick='remove(\"#: id #\")'>停用</button>&nbsp;",
												field : "action",
												title : "動作",
												width : 200
											},
											{
												field : "ban",
												title : "統編",
												width : 150
											},
											{
												field : "com_name",
												title : "公司名稱",
												width : 150
											},
											{
												field : "wh_id",
												title : "倉編",
												width : 100
											},
											{
												field : "wh_name",
												title : "倉名",
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
										sortable : true,
										detailTemplate:kendo.template($("#level2").html()),
										detailExpand: function(e){
								            e.sender.tbody.find('.k-detail-row').each(function(idx, item){
								              if(item !== e.detailRow[0]){
								                e.sender.collapseRow($(item).prev());
								              }
								            })
								         },
								         detailInit: function(e) {
								        	 e.detailRow.find(".f1_l2_grid").kendoGrid({
								        		 columns : [  
								        			 {
															template : "<button type='button' class='btn btn-success btn-sm' onclick=''>編輯</button>&nbsp;"
																	 + "<button type='button' class='btn btn-danger btn-sm' onclick='remove(\"#: id #\")'>停用</button>&nbsp;",
															field : "action",
															title : "動作",
															width : 150
														},
														{
															field : "wh_id",
															title : "倉庫代號",
															width : 150
														},
														{
															field : "wh_name",
															title : "倉庫名稱",
															width : 150
														},
														{
															field : "dl_id",
															title : "棚編",
															width : 100
														},
														{
															field : "dl_name",
															title : "棚別",
															width : 100
														},
														//{
														//	field : "dutyNum",
														//	title : "編制,
														//	width : 100
														//},
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
														}
														],
													dataSource: {
														transport : {
															read : {
																url : '<c:url value = "/admin/wareHouseProf/ajaxDlGridList"/>', // 目標ajax網址
																dataType : "jsonp",
																data : function() {
																	return {
																		topId : e.data.id
																	};
																}
															}
														}
								         			},
								         			sortable : true
								        	 });
								        	 
								         }
									});

							
							
							
							
							global.formModel = $("#formModal").kendoWindow({

								modal : true,
								title : false,
								visible : false,
								iframe : false,
								width:500
							}).data("kendoWindow");
							
							global.dlFormModel = $("#dlFormModal").kendoWindow({

								modal : true,
								title : false,
								visible : false,
								iframe : false,
								width:500
							}).data("kendoWindow");

							$('#btn_add').click(function(e) {

								global.formModel.center().open();
							});

							$('#btn_search').click(function(e) {
								reflashGird();
							});

							$('#formModalClose').click(function(e) {
								closeFormModel();
							});
							$('#dlFormModalClose').click(function(e) {
								closeDlFormModel();
							});
							
							$('#formModalSend').click(function(e) {
								ajaxFormModal();
							});
							$('#dlFormModalSend').click(function(e) {
								ajaxdlFormModal();
							});
							
							$("#status").kendoMobileSwitch({
								onLabel : "啟用",
								offLabel : "停用"
							});
							global.switchInstance = $("#status").data(
									"kendoMobileSwitch");
							global.switchInstance.check(true);
							
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
			$('#wh_id').val("");
			$('#wh_name').val("");
			$('#wh_addr').val("");


			$('#num_txt1').html('剩餘可輸入100字');
			var switchInstance = $("#status").data("kendoMobileSwitch");
			global.switchInstance.check(true);
			global.formModel.center().close();

		}

		function closeDlFormModel() {
			$('.errText').text('');
			$('#id').val("");
			$('#wh_id').val("");
			$('#wh_name').val("");
			$('#dl_id').val("");
			$('#dl_name').val("");
			
			$('#num_txt1').html('剩餘可輸入100字');
			var switchInstance = $("#useState").data("kendoMobileSwitch");
			global.switchInstance.check(true);
			global.dlFormModel.center().close();

		}
		
		function ajaxFormModal() {

			$.ajax({
				type : "POST",
				url : '<c:url value = "/admin/wareHouseProf/ajaxSaveHt"/>',
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
		

		function ajaxdlFormModal() {

			$.ajax({
				type : "POST",
				url : '<c:url value = "/admin/wareHouseProf/ajaxSaveDl"/>',
				data : $("#dlInputForm").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {
						closeDlFormModel();
						reflashGird();

					} else {

					}

				}
			});
		}

		function edit(id,whId,wh_name,wh_addr) {
					$('#inputForm input[name=id]').val(id);
					$('#inputForm input[name=whId]').val(whId);
					$('#inputForm input[name=whName]').val(wh_name);
					$('#inputForm input[name=whAddr]').val(wh_addr);	
					$('#inputForm input[name=create_user]').val(create_user);
					$('#inputForm input[name=create_time]').val(create_time);	
					global.formModel.center().open();

		}

		function addDl(id,whId,wh_name) {
			$('#dlInputForm input[name=topId]').val(id);
			$('#dlInputForm input[name=whId]').val(whId);
			$('#dlInputForm input[name=whName]').val(wh_name);
			global.dlFormModel.center().open();
		}
		
		function editDl(id,whId,wh_name) {
			$('#dlInputForm input[name=topId]').val(id);
			$('#dlInputForm input[name=whId]').val(whId);
			$('#dlInputForm input[name=whName]').val(wh_name);
			$('#dlInputForm input[name=create_user]').val(create_user);
			$('#dlInputForm input[name=create_time]').val(create_time);	
			global.dlFormModel.center().open();
		}
		function remove(id) {
			if(confirm("本次操作將停用該倉庫所有相關資料，是否繼續？")){
				$('#id').val(id);
				$('#status').val("");
				$.ajax({
					type : "POST",
					url : '<c:url value = "/admin/wareHouseProf/ajaxRemoveHt"/>',
					data : $("#inputForm").serialize(),// serializes the form's elements.
					success : function(result) {
						if (checkAjaxResp(result)) {
							reflashGird();
						}

					}
				});
			}

		}
		
		function removeDl(roleId) {
			/*if(confirm("本次操作將刪除該角色所有相關資料，是否繼續？")){
				$('#id').val(roleId);
				$.ajax({
					type : "POST",
					url : '<c:url value = "/roleManager/ajaxRemoveRoleById"/>',
					data : $("#inputForm").serialize(),// serializes the form's elements.
					success : function(result) {
						if (checkAjaxResp(result)) {
							reflashGird();
						}

					}
				});
			}*/

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
										url : '<c:url value = "/admin/roleManager/ajaxRemoveRoleById"/>',
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
					'<c:url value = "/admin/roleManager/funcRoleList"/>');
			$('#inputForm').attr('method', 'post');
			$('#inputForm').submit();
		}
	</script>
	<script id="level2" type="text/x-kendo-template">
  		<div class="f1_l2_grid row_color_1">
  		</div>
	</script>
</body>

</html>