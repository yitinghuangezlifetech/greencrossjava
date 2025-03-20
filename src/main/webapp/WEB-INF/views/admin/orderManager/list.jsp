<%@ page language="java" contentType="text/html;  charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/common-util.tld" prefix="pi"%>
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-12">
				<div class="card">
					<div class="card-body">
						<div class="container-fluid">
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="roleType">訂單編號</label>
										</div>
										<div class="col-sm-12">
											<input type="text" class="input" name="orderNo" id="orderNo" value="" style="width: 100%;">
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
					<div class="card-header">
						<div class="card-body">
							<div id="grid"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<form id="prodForm">
		<input type="hidden" id="id" name="id" value="">
		<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
	</form>
	<script>
		$(document).ready(function() {
			$('#addBtn').click(function(e) {
				$('#id').val('');
				$('#prodForm').attr('action','<c:url value = "/admin/orderManager/ajaxItemList"/>');
				$('#prodForm').attr('method','post');
				$('#prodForm').submit();
			});
			
			$('#btn_search').click(function(e) {
				global.dataSource.read();
			});
			global.dataSource = new kendo.data.DataSource(
					{
						transport : {
							read : {
								type : "POST",
								url : '<c:url value = "/admin/orderManager/ajaxItemList"/>',
								dataType : "jsonp",
								data : function() {
									return {
										orderNo : $('#orderNo').val()
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
									template : "<button type='button' class='btn btn-success btn-sm' onclick='edit(\"#:order_no#\")'>編輯</button>&nbsp;"
											+ "<button type='button' class='btn btn-danger btn-sm' onclick='remove(\"#:id#\")'>刪除</button>&nbsp;",
									field : "id",
									title : "動作",
									width : 150
								},
								{
									field : "order_no",
									title : "訂單編號",
									width : 200
								},
								{
									field : "pro_no",
									title : "訂單代號",
									width : 200
								},
								{
									field : "order_number",
									title : "訂單號碼",
									width : 300
								},
								{
									field : "create_user	",
									title : "創建者",
									width : 300
								},
								{
									field : "create_time",
									title : "創建時間",
									width : 100
								},
								],
						dataSource : global.dataSource,
						sortable : true,
						pageable : true
					});
			
			
		});
		
		
		function edit(orderNo){
			$('#order_no').val(orderNo);
			$('#prodForm').attr('action','<c:url value = "/admin/orderManager/editProductList"/>');
			$('#prodForm').attr('method','post');
			$('#prodForm').submit();
		}
		
		function remove(id){
			warningConfirm(
					'確定刪除嗎?',
					'本次操作將刪除該商品所有相關資料，是否繼續？',
					'是',
					'否',
					function(confirm) {
						if (confirm) {
							//ajaxRemove
							$('#id').val(id);
							$.ajax({
								type : "POST",
								url : '<c:url value = "/admin/orderManager/ajaxRemove"/>',
								data : $("#prodForm").serialize(),// serializes the form's elements.
								success : function(result) {
									if (checkAjaxResp(result)) {
										global.dataSource.read();
									}

								}
							});
						}
					});
		}
	</script>
