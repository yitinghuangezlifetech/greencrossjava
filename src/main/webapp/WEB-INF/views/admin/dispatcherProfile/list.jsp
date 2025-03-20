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
						<!-- 
						<button type="button" id="addBtn" class="btn btn-primary ">
							<i class="fas fa-plus"> 新增</i>
						</button>
						-->
					</div>
					<div class="card-body">
						<div class="container-fluid">
						<!--  
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="roleType">所屬協力廠廠商代號</label>
										</div>
										<div class="col-sm-12">
											<input type="text" class="input" name="proCode" id="proCode" value="" style="width: 100%;">
										</div>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="roleType">倉編</label>
										</div>
										<div class="col-sm-12">
											<input type="text" class="input" name="proName" id="proName" value="" style="width: 100%;">
										</div>
									</div>
								</div>
							</div>
						-->	
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="roleType">姓名</label>
										</div>
										<div class="col-sm-12">
											<input type="text" class="input" name="name" id="name" value="" style="width: 100%;">
										</div>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="form-group ">

									</div>
								</div>
							</div>						
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="roleType">員工編號</label>
										</div>
										<div class="col-sm-12">
											<input type="text" class="input" name="compId" id="compId" value="" style="width: 100%;">
										</div>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="roleType">身分證字號</label>
										</div>
										<div class="col-sm-12">
											<input type="text" class="input" name="idNumber" id="idNumber" value="" style="width: 100%;">
										</div>
									</div>
								</div>
							</div>							
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="roleType">啟用狀態</label>
										</div>
										<div class="col-sm-12">
											<input type="radio" name="status" id="status_all" value="" checked> <label for="status_all">&nbsp;全部 &nbsp; </label>
											<input type="radio" name="status" id="status_A" value="A"><label for="status_A">&nbsp;啟用 &nbsp;</label>
											<input type="radio" name="status" id="status_S" value="S"><label for="status_S">&nbsp;停用</label> 
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="card-footer text-right">
						<button type="button" id="addBtn" class="btn btn-primary ">
							<i class="fas fa-plus"> 新增</i>
						</button>					
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



	<form id="dispatcherProfileForm">
		<input type="hidden" id="id" name="id" value="">
		<!--
		<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
		
	</form>




	<script>
		$(document).ready(function() {
			$('#addBtn').click(function(e) {
				$('#id').val('');
				$('#dispatcherProfileForm').attr('action','<c:url value = "/admin/dispatcherProfile/addDispatcherProfile"/>');
				$('#dispatcherProfileForm').attr('method','post');
				$('#dispatcherProfileForm').submit();
			});
			
			$('#btn_search').click(function(e) {
				global.dataSource.read();
			});
			
			global.dataSource = new kendo.data.DataSource(
					{
						transport : {
							read : {
								type : "POST",
								url : '<c:url value = "/admin/dispatcherProfile/ajaxDispatcherProfileGridList"/>',
								dataType : "jsonp",
								data : function() {
									return {
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
									template : "<button type='button' class='btn btn-success btn-sm' onclick='edit(\"#:id#\")'>編輯</button>&nbsp;"
											+ "<button type='button' class='btn btn-danger btn-sm' onclick='remove(\"#:id#\")'>刪除</button>&nbsp;",
									field : "action",
									title : "動作",
									width : 150
								},
								{
									field : "id",
									title : "員工編號",
									width : 200
								},
								{
									field : "name",
									title : "姓名",
									width : 300
								},
								{
									field : "id_number",
									title : "身分證字號",
									width : 300
								},
								{
									field : "birthday",
									title : "生日",
									width : 100
								},
								{
									field : "sex",
									title : "性別",
									width : 150
								},
								{
									field : "addr",
									title : "地址",
									width : 250
								}],
						dataSource : global.dataSource,
						sortable : true,
						pageable : true
					});
			
			
		});
		
		function edit(id){
			$('#id').val(id);
			$('#dispatcherProfileForm').attr('action','<c:url value = "/admin/dispatcherProfile/addDispatcherProfile"/>');
			$('#dispatcherProfileForm').attr('method','post');
			$('#dispatcherProfileForm').submit();
		}
		
		function remove(id){
			warningConfirm(
					'確定刪除嗎?',
					'本次操作將刪除該派遣人員所有相關資料，是否繼續？',
					'是',
					'否',
					function(confirm) {
						if (confirm) {
							//ajaxRemove
							$('#id').val(id);
							$
							.ajax({
								type : "POST",
								url : '<c:url value = "/admin/dispatcherProfile/ajaxRemove"/>',
								data : $("#dispatcherProfileForm").serialize(),// serializes the form's elements.
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