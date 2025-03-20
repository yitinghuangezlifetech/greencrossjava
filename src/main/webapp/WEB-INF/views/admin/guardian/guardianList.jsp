<%@ page language="java" contentType="text/html;  charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/common-util.tld" prefix="pi"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>最新消息</title>

</head>

<body>

	<div class="container-fluid">

		<div class="row">
			<div class="col-sm-12">
				<div class="card">
					<div class="card-header text-right">
						
						<button type="button" id="addBtn" class="btn btn-primary ">
							<i class="fas fa-plus"> 新增</i>
						</button>
					</div>
					<div class="card-body">
						<div class="container-fluid">
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="roleType">標題</label>
										</div>
										<div class="col-sm-12">
											<input type="text" class="input" name="title" id="title" value="" style="width: 100%;">
										</div>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="roleType">內容</label>
										</div>
										<div class="col-sm-12">
											<input type="text" class="input" name="content" id="content" value="" style="width: 100%;">
										</div>
									</div>
								</div>
							</div>
							<!--  
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="roleType">商品狀態</label>
										</div>
										<div class="col-sm-12">
											<input type="radio" name="status" id="status_all" value="" checked> <label for="status_all">&nbsp;全部 &nbsp; </label>
											<input type="radio" name="status" id="status_A" value="A"><label for="status_A">&nbsp;啟用 &nbsp;</label>
											<input type="radio" name="status" id="status_S" value="S"><label for="status_S">&nbsp;停用</label> 
										</div>
									</div>
								</div>
							</div>
							-->
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



	<form id="guardianForm">
		<input type="hidden" id="id" name="id" value="">
		<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
	</form>




	<script>
		$(document).ready(function() {
			$('#addBtn').click(function(e) {
				$('#id').val('');
				$('#guardianForm').attr('action','<c:url value = "/admin/guardian/addGuardian"/>');
				$('#guardianForm').attr('method','post');
				$('#guardianForm').submit();
			});
			
			$('#btn_search').click(function(e) {
				global.dataSource.read();
			});
			global.dataSource = new kendo.data.DataSource(
					{
						transport : {
							read : {
								type : "POST",
								url : '<c:url value = "/admin/guardian/ajaxHtGridList"/>',
								dataType : "jsonp",
								data : function() {
									return {
										guardianType : 'N',
										title : $("#title").val(),
										content : $("content").val()
										//status :$('input[name=status]:checked').val(),
										//proName : $('#proName').val(),
										//proCode : $('#proCode').val()
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
									template : "<button type='button' class='btn btn-success btn-sm' onclick='editGuardian(\"#:id#\")'>編輯</button>&nbsp;"
											+ "<button type='button' class='btn btn-danger btn-sm' onclick='removeGuardian(\"#:id#\")'>刪除</button>&nbsp;",
									field : "action",
									title : "動作",
									width : 60
								},
								{
									field : "title",
									title : "標題",
									width : 70
								},
								{
									field : "content",
									title : "內容",
									width : 150
								},
								{
									field : "contact",
									title : "聯絡人資訊",
									width : 50
								},
								{
									field : "email",
									title : "聯絡人信箱",
									width : 80
								},
								{
									field : "tel",
									title : "聯絡人手機",
									width : 60
								},
// 								{
// 									field : "create_time",
// 									title : "建立時間",
// 									width : 250
// 								},
// 								{
// 									field : "update_user",
// 									title : "最後更新者",
// 									width : 150
// 								},
// 								{
// 									field : "update_time",
// 									title : "最後更新時間",
// 									width : 250
// 								} 
								],
						dataSource : global.dataSource,
						sortable : true,
						pageable : true
					});
			
			
		});
		
		
		function editGuardian(id){
			$('#id').val(id);
			$('#guardianForm').attr('action','<c:url value = "/admin/guardian/editGuardian"/>');
			$('#guardianForm').attr('method','post');
			$('#guardianForm').submit();
		}
		
		function removeGuardian(id){
			warningConfirm(
					'確定刪除嗎?',
					'本次操作將刪除該消息所有相關資料，是否繼續？',
					'是',
					'否',
					function(confirm) {
						if (confirm) {
							//ajaxRemove
							$('#id').val(id);
							$
							.ajax({
								type : "POST",
								url : '<c:url value = "/admin/guardian/ajaxRemove"/>',
								data : $("#guardianForm").serialize(),// serializes the form's elements.
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
</body>

</html>