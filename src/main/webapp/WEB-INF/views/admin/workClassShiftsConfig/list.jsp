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
								<button type="button" id="btn_search" class="btn btn-primary ">
									<i class="fas fa-sync-alt"> 重新整理</i>
								</button>
								<button type="button" id="addBtn" class="btn btn-primary ">
									<i class="fas fa-plus"> 新增</i>
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

	<form id='editForm'>
		<input type="hidden" id="id" name="id" value="">
		<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
	</form>


	


	<script>
		$(document).ready(function() {

			
			$('#addBtn').click(function(e) {
				addData();
				
			});
			
			
			
			// grid Start
			global.dataSource = new kendo.data.DataSource(
					{
						transport : {
							read : {
								type : "POST",
								url : '<c:url value = "/admin/workClassShiftsConfig/ajaxHtGridList"/>',
								dataType : "jsonp",
								data : function() {
									return {
										id:$('#id').val()
									};
								}
							}
						},
						pageSize : 10
					});
			
			$("#grid").kendoGrid(
					{
						columns : [
								{
									template : "<button type='button' class='btn btn-success btn-sm' onclick='edit(\"#:id#\")'>編輯</button>&nbsp;"
											
										+"#if(status =='E' || status =='R'){#"
										+ "<button type='button' class='btn btn-danger btn-sm' onclick='remove(\"#:id#\")'>刪除</button>&nbsp;"
										+"#}#",
										
									field : "action",
									title : "動作",
									width : 200
								},
								{
									field : "config_name",
									title : "合約名稱",
									width : 200
								},
								{
									field : "comp_name",
									title : "所屬公司",
									width : 200
								},
								{
									field : "status_text",
									title : "合約狀態",
									width : 200
								},
								{
									field : "create_user",
									title : "建立者",
									width : 150
								},
								{
									field : "create_time",
									title : "建立時間",
									width : 200
								},
								{
									field : "update_user",
									title : "最後更新者",
									width : 150
								},
								{
									field : "update_time",
									title : "最後更新時間",
									width : 200
								} ],
						dataSource : global.dataSource,
						sortable : true,
						pageable : true
					});
			// grid End
			
			
			
			
		});

		
		
		function addData(){
			$('#id').val('');
			$('#editForm').attr('action','<c:url value = "/admin/workClassShiftsConfig/edit"/>');
			$('#editForm').attr('method','post');
			$('#editForm').submit();
		}
	
		
		function edit(id){
			$('#id').val(id);
			$('#editForm').attr('action','<c:url value = "/admin/workClassShiftsConfig/edit"/>');
			$('#editForm').attr('method','post');
			$('#editForm').submit();
		}
		
		function remove(id){
			
			
			warningConfirm(
					'確定刪除嗎?',
					'本次操作將刪除該設定所有相關資料，是否繼續？',
					'是',
					'否',
					function(confirm) {
						if (confirm) {
							$('#id').val(id);
							$.ajax({
								type : "POST",
								url : '<c:url value = "/admin/workClassShiftsConfig/ajaxRemoveHt"/>',
								data : $("#editForm").serialize(),// serializes the form's elements.
								success : function(result) {
									if (checkAjaxResp(result)) {
										global.dataSource.read();
										$('#id').val('');
									}

								}
							});	
						}
					});
		}

	</script>
</body>

</html>