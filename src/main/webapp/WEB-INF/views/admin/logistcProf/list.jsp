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
								<button type="button" id="btn_reflash" class="btn btn-primary ">
									<i class="fas fa-sync-alt"> 重新整理</i>
								</button>
								 <button type="button" id="addBtn" class="btn btn-primary ">
									<i class="fas fa-plus"> 新增協力廠商</i>
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

	  


	<div>
		<div id="formModal">
			<div class="panel panel-bordered">
				<form id="inputForm">
					<input type="hidden" id="id" name="id" value="">
					<div class='panel-body'>
						<table class="table table-condensed">
							<tr>
								<td><label for="comId">廠商公司</label></td>
								<td>
									<select id="comId" name="comId">
										<option value="">請選擇</option>
										<c:forEach items="${compIdSelectList}" var="current">
											<option value="${current.id}">${current.name}</option>
										</c:forEach>
									</select>
									<font class='errText form-text' id='err_comId' color='red'></font>
								</td>
							</tr>
							
							<tr>
								<td><label for="wcsId">合約</label></td>
								<td>
									<select id="wcsId" name="wcsId">
										<option value="">請選擇</option>
									</select>
									<input type="hidden" id="selectWcsId"  value="">
									<font class='errText form-text' id='err_wcsId' color='red'></font>
								</td>
							</tr>
							
							<tr>
								<td><label for="whId">倉庫</label></td>
								<td>
									<select id="whId" name="whId">
										<option value="">請選擇</option>
										<c:forEach items="${whIdSelectList}" var="current">
											<option value="${current.id}">${current.name}</option>
										</c:forEach>
									</select>
									<font class='errText form-text' id='err_whId' color='red'></font>
								</td>
							</tr>
							<tr>
								<td><label for="whDlId">棚別</label></td>
								<td>
									<select id="whDlId" name="whDlId">
										<option value="">請選擇</option>
									</select>
									<input type="hidden" id="selectWhDlId"  value="">
									<font class='errText form-text' id='err_whDlId' color='red'></font>
								</td>
							</tr>
							
							
							
							<tr>
								<td><label for="strength">編制</label></td>
								<td>
									<input type="text" class="input" name="strength" id="strength" value="" style="width: 100%;">
									<font class='errText form-text' id='err_strength' color='red'></font>
								</td>
							</tr>
						</table>
					</div>
					<div class="panel-footer text-right">
						<button type="button" id="formModelSend" class="btn btn-primary ">
							<span class="glyphicon glyphicon-ok"></span>送出
						</button>
						<button type="button" id="formModelClose" class="btn btn-primary ">
							<span class="glyphicon glyphicon-remove"></span>關閉
						</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	


	<script>
		$(document).ready(function() {

			
			$('#addBtn').click(function(e) {
				$('#id').val('');
				addData();
				
			});
			
			$('#formModelClose').click(function(e) {
				
				closeDlWindows();
			});
			
			
			$('#formModelSend').click(function(e) {
				
				saveData();
				
			});
			
			$('#btn_reflash').click(function(e) {
				
				global.dataSource.read();
				
			});
			
			// grid Start
			global.dataSource = new kendo.data.DataSource(
					{
						transport : {
							read : {
								type : "POST",
								url : '<c:url value = "/admin/logistcProf/ajaxGridList"/>',
								dataType : "jsonp",
								data : function() {
									return {
										nona:'nona'
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
											
										
										+ "<button type='button' class='btn btn-danger btn-sm' onclick='remove(\"#:id#\")'>刪除</button>&nbsp;",
										
										
									field : "action",
									title : "動作",
									width : 200
								},
								{
									field : "com_id",
									title : "公司代號",
									width : 200
								},
								{
									field : "com_name",
									title : "公司名稱",
									width : 200
								},
								{
									field : "wh_id",
									title : "倉庫代號",
									width : 200
								},
								{
									field : "wh_name",
									title : "倉庫名稱",
									width : 200
								},
								{
									field : "wh_dl_id",
									title : "棚別代號",
									width : 200
								},
								{
									field : "dl_name",
									title : "棚別名稱",
									width : 200
								},
								{
									field : "config_name",
									title : "合約名稱",
									width : 200
								},
								{
									field : "strength",
									title : "編制",
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
			
			
			global.formModel = $("#formModal").kendoWindow({

				modal : true,
				title : false,
				visible : false,
				iframe : false,
			}).data("kendoWindow");
			
			$('#comId').kendoDropDownList({
				change: function(e) {
					global.wcsdataSource.read();
					$('#selectWcsId').val('');
				}
			});
			$('#whId').kendoDropDownList({
				change: function(e) {
					global.dldataSource.read();
					$('#selectWhDlId').val('');
				}
			});
			
			
			global.wcsdataSource = new kendo.data.DataSource(
					{
						transport: {
	                        read: {
	                            dataType: "jsonp",
	                            url: '<c:url value = "/admin/logistcProf/ajaxWcsSelectList"/>',
	                            data : function() {
									return {
										comId:$('#comId').val()
									};
								}
	                        }
	                    }
					});
			
			$('#wcsId').kendoDropDownList({
				autoBind: false,
				dataTextField: "config_name",
                dataValueField: "id",
                dataSource: global.wcsdataSource,
                dataBound: function(e) {
                	$("#wcsId").data('kendoDropDownList').value($('#selectWcsId').val());
                }
			});
			
			
			
			global.dldataSource = new kendo.data.DataSource(
					{
						transport: {
	                        read: {
	                            dataType: "jsonp",
	                            url: '<c:url value = "/admin/logistcProf/ajaxDlSelectList"/>',
	                            data : function() {
									return {
										topId:$('#whId').val()
									};
								}
	                        }
	                    }
					});
			
			$('#whDlId').kendoDropDownList({
				autoBind: false,
				dataTextField: "dl_name",
                dataValueField: "dl_id",
                dataSource: global.dldataSource,
                dataBound: function(e) {
                	$("#whDlId").data('kendoDropDownList').value($('#selectWhDlId').val());
                }
			});
			
			
			
			
			
		});

		function saveData(){
			$.ajax({
				type : "POST",
				url : '<c:url value = "/admin/logistcProf/ajaxSave"/>',
				data : $("#inputForm").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {
						global.dataSource.read();
						closeDlWindows();
						resetWindow();
					}

				}
			});	
		}
		
		function addData(){
			
			resetWindow();
			openDlWindows();
		}
	
		
		function resetWindow(){
			$("#comId").data('kendoDropDownList').value('');
			$('#selectWcsId').val('');
			global.wcsdataSource.read();
			$("#whId").data('kendoDropDownList').value('');
			$('#selectWhDlId').val('');
			global.dldataSource.read();
			$('#strength').val('');  
		}
		
		function edit(id){
			$('#id').val(id);
			$.ajax({
				type : "POST",
				url : '<c:url value = "/admin/logistcProf/ajaxGetRefData"/>',
				data : $("#inputForm").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {
						$("#comId").data('kendoDropDownList').value(result.data.comId);
						$('#selectWcsId').val(result.data.wcsId);
						global.wcsdataSource.read();
						$("#whId").data('kendoDropDownList').value(result.data.whId);
						$('#selectWhDlId').val(result.data.whDlId);
						global.dldataSource.read();
						$('#strength').val(result.data.strength);  
						openDlWindows();
						
					}

				}
			});	
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
								url : '<c:url value = "/admin/logistcProf/ajaxRemove"/>',
								data : $("#inputForm").serialize(),// serializes the form's elements.
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
		
		
		function openDlWindows(){
			
			global.formModel.center().open();
		}
		
		function closeDlWindows(){
			
			global.formModel.center().close();
		}

	</script>
</body>

</html>