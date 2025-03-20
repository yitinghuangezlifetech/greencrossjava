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
						
					</div>
					<div class="card-body">
						<div class="container-fluid">
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<label >參數代號</label>
										</div>
										<div class="col-sm-12">
											<input type="text" class="input"  value="${basicSysparamHtDto.paramCode}" style="width: 100%;" disabled>
										</div>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<label >參數名稱</label>
										</div>
										<div class="col-sm-12">
											<input type="text" class="input" value="${basicSysparamHtDto.paramName}" style="width: 100%;" disabled>
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-sm-12">
									<div class="form-group ">
										<div class="col-sm-12">
											<label >參數說明</label>
										</div>
										<div class="col-sm-12">
											<input type="text" class="input"  value="${basicSysparamHtDto.paramContent}" style="width: 100%;" disabled>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12">
									<hr/>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12">
									<div class="form-group ">
										<div class="col-sm-12">
											<label >參數1說明</label>
										</div>
										<div class="col-sm-12">
											<input type="text" class="input"  value="${basicSysparamHtDto.name1}" style="width: 100%;" disabled>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12">
									<div class="form-group ">
										<div class="col-sm-12">
											<label >參數2說明</label>
										</div>
										<div class="col-sm-12">
											<input type="text" class="input"  value="${basicSysparamHtDto.name2}" style="width: 100%;" disabled>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12">
									<div class="form-group ">
										<div class="col-sm-12">
											<label >參數3說明</label>
										</div>
										<div class="col-sm-12">
											<input type="text" class="input"  value="${basicSysparamHtDto.name3}" style="width: 100%;" disabled>
										</div>
									</div>
								</div>     
							</div>
							<div class="row">
								<div class="col-sm-12">
									<div class="form-group ">
										<div class="col-sm-12">
											<label >參數4說明</label>
										</div>
										<div class="col-sm-12">
											<input type="text" class="input"  value="${basicSysparamHtDto.name4}" style="width: 100%;" disabled>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12">
									<div class="form-group ">
										<div class="col-sm-12">
											<label >參數5說明</label>
										</div>
										<div class="col-sm-12">
											<input type="text" class="input"  value="${basicSysparamHtDto.name5}" style="width: 100%;" disabled>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					
				</div>

			</div>
		</div>
		
		<div class="row">
			<div class="col-sm-12">
				<div class="card">
					<div class="card-header text-right">
						<button type="button" id="addBtn" class="btn btn-primary ">
							<i class="fas fa-plus"> 新增</i>
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
					<input type="hidden" id="htId" name="htId" value="${basicSysparamHtDto.id}">
					<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
					<header class="panel-heading">
					<h3 class="panel-title">參數主檔新修視窗</h3>
					</header>
					<div class='panel-body'>

						<table class="table table-condensed">
							<tr>
								<td><label for="paramCode">參數代號</label></td>
								<td><input type="text"  class='input'  value="${basicSysparamHtDto.paramCode}" disabled> 
								<input type="hidden" id="paramCode" name="paramCode" value="${basicSysparamHtDto.paramCode}">
							</tr>
							
							
							<tr>
								<td><label for="isDelete">是否刪除</label></td>
								<td><input type="text"  class='input' id="isDeleteText" value="" disabled> 
								<input type="hidden" id="isDelete" name="isDelete" value="N">
							</tr>
							
							<tr>
								<td><label for="param1">參數1</label></td>
								<td><input type="text" id="param1" class='input' name="param1" value=""> 
								<font class='errText form-text' id='err_param1' color='red'></font></td>
							</tr>
							
							<tr>
								<td><label for="param2">參數2</label></td>
								<td><input type="text" id="param2" class='input' name="param2" value=""> 
								<font class='errText form-text' id='err_param2' color='red'></font></td>
							</tr>
							
							<tr>
								<td><label for="param3">參數3</label></td>
								<td><input type="text" id="param3" class='input' name="param3" value=""> 
								<font class='errText form-text' id='err_param3' color='red'></font></td>
							</tr>
							
							<tr>
								<td><label for="param4">參數4</label></td>
								<td><input type="text" id="param4" class='input' name="param4" value=""> 
								<font class='errText form-text' id='err_param4' color='red'></font></td>
							</tr>
							
							<tr>
								<td><label for="param5">參數5</label></td>
								<td><input type="text" id="param5" class='input' name="param5" value=""> 
								<font class='errText form-text' id='err_param5' color='red'></font></td>
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
			
			$('#addBtn').click(function(e) {
				add();
			});
			
			$('#formModelClose').click(function(e) {
				closeKendoWindow();
			});
			
			
			$('#formModelSend').click(function(e) {
				 saveForm();
			});
			
			global.dataSource = new kendo.data.DataSource(
					{
						transport : {
							read : {
								type : "POST",
								url : '<c:url value = "/admin/systemParamConfig/ajaxSystemParamDlGridList"/>',
								dataType : "jsonp",
								data : function() {
									return {
										id:$('#htId').val()
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
									template : "<button type='button' class='btn btn-success btn-sm' onclick='edit(\"#:id#\")'>編輯</button>&nbsp;"+
										"#if(is_delete=='N'){#"+
											 "<button type='button' class='btn btn-danger btn-sm' onclick='remove(\"#:id#\")'>刪除</button>&nbsp;"+
											"#}else{#"+
											"<button type='button' class='btn btn-info btn-sm' onclick='reAlive(\"#:id#\")'>恢復</button>&nbsp;"+
											"#}#",
											
									field : "action",
									title : "動作",
									width : 200
								},{
								template : 
									
									"#if(is_delete=='N'){#"+
										
										"<font color='green'>已啟用</font>"+
									"#}else{#"+
										"<font color='red'>已刪除</font>"+
									"#}#",
								field : "action",
								title : "動作",
								width : 200
								},
								{
									field : "param1",
									title : "參數1",
									width : 200
								},
								{
									field : "param2",
									title : "參數2",
									width : 200
								},
								{
									field : "param3",
									title : "參數3",
									width : 200
								},
								{
									field : "param4",
									title : "參數4",
									width : 200
								},
								{
									field : "param5",
									title : "參數5",
									width : 200
								}
								,{
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
			
			
			global.formModel = $("#formModal").kendoWindow({

				modal : true,
				title : false,
				visible : false,
				iframe : false,
			}).data("kendoWindow");
			
		});
		
		function saveForm(){
			$.ajax({
				type : "POST",
				url : '<c:url value = "/admin/systemParamConfig/ajaxSaveSystemParamDl"/>',
				data : $("#inputForm").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {
						closeKendoWindow();
						resetForm();
						global.dataSource.read();
					}

				}
			});
		}
		
		
		function add(){
			openKendoWindow();
		}
		
		function openKendoWindow(){
			global.formModel.center().open();
		}
		
		function closeKendoWindow(){
			resetForm();
			global.formModel.center().close();
		}
		
		
		function resetForm(){
			$('#id').val('');
			
			$('#param1').val('');
			$('#param2').val('');
			$('#param3').val('');
			$('#param4').val('');
			$('#param5').val('');
			$('.errText').text('');
		}
		
		function remove(id){
			
			$('#id').val(id);
			$('#isDelete').val('Y');
			$.ajax({
				type : "POST",
				url : '<c:url value = "/admin/systemParamConfig/ajaxRemoveSystemParamDl"/>',
				data : $("#inputForm").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {
						resetForm();
						global.dataSource.read();
					}

				}
			});
		}
		
		function reAlive(id){
			
			$('#id').val(id);
			$('#isDelete').val('N');
			$.ajax({
				type : "POST",
				url : '<c:url value = "/admin/systemParamConfig/ajaxRemoveSystemParamDl"/>',
				data : $("#inputForm").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {
						resetForm();
						global.dataSource.read();
					}

				}
			});
		}
		
		function edit(id){
			$('#id').val(id);
			$.ajax({
				type : "POST",
				url : '<c:url value = "/admin/systemParamConfig/ajaxGetSystemParamDlById"/>',
				data : $("#inputForm").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {
						resetForm();
						$('#id').val(result.data.id);
						$('#param1').val(result.data.param1);
						$('#param2').val(result.data.param2);
						$('#param3').val(result.data.param3);
						$('#param4').val(result.data.param4);
						$('#param5').val(result.data.param5);
						$('#isDelete').val(result.data.isDelete);
						$('#isDeleteText').val(result.data.isDeleteText);
						openKendoWindow();
					}

				}
			});
		}

	</script>
</body>

</html>