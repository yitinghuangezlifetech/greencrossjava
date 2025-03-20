<%@ page language="java" contentType="text/html;  charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/common-util.tld" prefix="pi"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>最新活動</title>

</head>

<body>

	<div class="container-fluid">


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



	<form id="activeForm">
		<input type="hidden" id="id" name="id" value="">
		<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
	</form>




	<script>
		$(document).ready(function() {
			$('#addBtn').click(function(e) {
				$('#id').val('');
				$('#activeForm').attr('action','<c:url value = "/home/news/addActive"/>');
				$('#activeForm').attr('method','post');
				$('#activeForm').submit();
			});
			
			$('#btn_search').click(function(e) {
				global.dataSource.read();
			});
			global.dataSource = new kendo.data.DataSource(
					{
						transport : {
							read : {
								type : "POST",
								url : '<c:url value = "/home/news/ajaxHtGridList"/>',
								dataType : "jsonp",
								data : function() {
									return {
										newsType : 'A',
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
									//template : "<button type='button' class='btn btn-success btn-sm' onclick='editNews(\"#:id#\")'>編輯</button>&nbsp;"
									//		+ "<button type='button' class='btn btn-danger btn-sm' onclick='removeNews(\"#:id#\")'>刪除</button>&nbsp;",
									template : "<a onclick='editNews(\"#:id#\")'>#:title#</a>&nbsp;",
									field : "title",
									title : "標題",
									width : 150
								},
								{
									template : "<a onclick='editNews(\"#:id#\")'>#:title#</a>&nbsp;",
									field : "content",
									title : "活動資訊",
									width : 200
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
// 									field : "pro_name",
// 									title : "商品名稱",
// 									width : 300
// 								},
// 								{
// 									field : "pro_spec",
// 									title : "商品規格",
// 									width : 300
// 								},
// 								{
// 									field : "status",
// 									title : "狀態",
// 									width : 100
// 								},
// 								{
// 									field : "create_user",
// 									title : "建立者",
// 									width : 150
// 								},
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
		
		
		function editNews(id){
			$('#id').val(id);
			$('#activeForm').attr('action','<c:url value = "/home/news/editNews"/>');
			$('#activeForm').attr('method','post');
			$('#activeForm').submit();
		}
		
		function removeNews(id){
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
								url : '<c:url value = "/home/news/ajaxRemove"/>',
								data : $("#activeForm").serialize(),// serializes the form's elements.
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