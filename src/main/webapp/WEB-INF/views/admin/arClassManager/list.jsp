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
						<button type="button" id="sortBtn" class="btn btn-primary ">
							<i class="fas fa-sort-amount-down"> 排序管理</i>
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
					<h3 class="panel-title">商品分類新修視窗</h3>
					</header>
					<div class='panel-body'>

						<table class="table table-condensed">
							<tr>
								<td><label for="className">商品分類名稱</label></td>
								<td><input type="text" id="className" class='input' name="className" value=""> 
								<font class='errText form-text' id='err_className' color='red'></font></td>
							</tr>
							<tr>
								<td><label for="classParentId">上層商品分類</label></td>
								<td><select id='classParentId' name='classParentId'>

								</select> 
								<font class='errText form-text' id='err_classParentId' color='red'></font></td>
							</tr>
							
							<tr>
								<td><label for="status">狀態</label></td>
								<td><input name="status" id="status" /></td>
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
		$(document).ready(function() {
			
			
			global.dataSource = new kendo.data.TreeListDataSource({
				transport : {
					read : {
						type : "POST",
						url : '<c:url value = "/admin/productClassManager/ajaxProdClassGridList"/>',
						dataType : "jsonp",
						complete: function(jqXHR, textStatus) { 
							console.log(textStatus, "read"); 
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
									template : "<button type='button' class='btn btn-success btn-sm' onclick='edit(\"#:class_serno#\")'>編輯</button>&nbsp;"
											+ "<button type='button' class='btn btn-danger btn-sm' onclick='remove(\"#:class_serno#\")'>刪除</button>&nbsp;",
									field : "action",
									title : "動作",
									width : 250
								},
								{
									field : "class_name",
									title : "分類名稱",
									width : 200
								},
								{
									field : "status",
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
						sortable : true
					});
			
			$('#addBtn').click(function(e) {
				$('#id').val('');
				$('#className').val('');
				global.switchInstance.check(true);
				prodClassSelSetting(function(){
					$('#classParentId').kendoDropDownList();
					global.formModel.center().open();
					
				});
				
			});
			
			$('#sortBtn').click(function(e) {
				
			});
			
			$('#formModelSend').click(function(e) {
				saveform()
			});
			
			
			$('#formModelClose').click(function(e) {
				closeFormModel();
			});
			
			$('#btn_search').click(function(e) {
				reflashGird();
			});
			
			global.formModel = $("#formModal").kendoWindow({

				modal : true,
				title : false,
				visible : false,
				iframe : false,
			}).data("kendoWindow");
			
			global.formSortModal = $("#formSortModal").kendoWindow({

				modal : true,
				title : false,
				visible : false,
				iframe : false,
			}).data("kendoWindow");
			
			
			$("#status").kendoMobileSwitch({
				onLabel : "啟用",
				offLabel : "停用"
			});
			global.switchInstance = $("#status").data("kendoMobileSwitch");
			global.switchInstance.check(true);
		});
		
		
		function closeFormModel() {
			global.formModel.center().close();
		}
		
		function saveform() {
			$.ajax({
				type : "POST",
				url : '<c:url value = "/admin/productClassManager/ajaxSaveProdClass"/>',
				data : $("#inputForm").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {
						
						closeFormModel();
						reflashGird();

					}

				}
			});
		}
		
		function reflashGird(){
			global.dataSource.read();
		}
		
		function edit(id){
			$('#id').val(id);
			prodClassSelSetting(function(){
				$.ajax({
					type : "POST",
					url : '<c:url value = "/admin/productClassManager/ajaxGetProdClassById"/>',
					data : $("#inputForm").serialize(),// serializes the form's elements.
					success : function(result) {
						if (checkAjaxResp(result)) {

							$('#className').val(result.data.className);
							$('#classParentId').val(result.data.classParentId);
							$('#classParentId').kendoDropDownList();
							if (result.data.status == 'Y') {
								global.switchInstance.check(true);
							} else {
								global.switchInstance.check(false);
							}

							global.formModel.center().open();

						}

					}
				});
			});
			
		}
		
		function remove(id){
			warningConfirm(
					'確定刪除嗎?',
					'本次操作將刪除該商品分類所有相關資料，是否繼續？',
					'是',
					'否',
					function(confirm) {
						if (confirm) {
							$('#id').val(id);
							$
									.ajax({
										type : "POST",
										url : '<c:url value = "/admin/productClassManager/ajaxRemoveProdClass"/>',
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
		
		function prodClassSelSetting(successfulFunction){
			
			
			$
			.ajax({
				type : "POST",
				url : '<c:url value = "/admin/productClassManager/ajaxGetAllProdClass"/>',
				data : $("#inputForm").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {
						
						$('#classParentId').html('');
						var htmlCode = '<option value="">請選擇</option>'
								+ '<option value="000000">主分類</option>';
						$.each(result.data, function(i, obj) {
							htmlCode = htmlCode
									+ '<option value="'+obj.classId+'">'
									+ obj.className + '</option>';
						});
						$('#classParentId').html(htmlCode);
						
						successfulFunction();
						

					}

				}
			});
		}
		function expandAll(){
			var treeList = $("#grid").data("kendoTreeList");
			var rows = $("tr.k-treelist-group", treeList.tbody);
			  $.each(rows, function(idx, row) {
			    treeList.expand(row);
			  });
		}
		
		
	</script>
</body>

</html>