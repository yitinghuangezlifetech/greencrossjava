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
		<form id="inputForm">
			<div class="row">
				<div class="col-sm-12">
					<div class="card">
						<div class="card-header">
							<div class="row">
								<div class="col-sm-12">
									<br>
								</div>
							</div>
						</div>
						<div class="card-body">
							<div class="container-fluid">
								<input type="hidden" id="id" name="id" value="${id}">
								<input type="hidden" id="goStatus" name="goStatus" value="${goStatus}">
					
								<div class="row">
									<div class="col-sm-4">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for="comId">協力廠商</label>
											</div>
											
											<div class="col-sm-12">
												 <input id="comId" name="comId"style="width: 100%;" />
											</div>
											<div class="col-sm-12">
												<font class='errText form-text' id='err_comId' color='red'></font>
											</div>
	
										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for="whId">倉別</label>
											</div>
											
											<div class="col-sm-12">
												 <input id="whId" name="whId" disabled="disabled" style="width: 100%;" />
											</div>
											<div class="col-sm-12">
												<font class='errText form-text' id='err_whId' color='red'></font>
											</div>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group ">
											<input type="hidden" id="userName" name="userName" value="">
											
											<div class="col-sm-12">
												<label for="userId">員工編號/姓名</label>
											</div>
											 <div class="col-sm-12">
												 <input id="userId" name="userId" disabled="disabled" style="width: 100%;" />
											</div>
											<div class="col-sm-12">
												<font class='errText form-text' id='err_userId' color='red'></font>
											</div>
										</div>
									</div>
								</div>
								
								<div class="row">
									<div class="col-sm-12 text-center">
										<hr/>
										<!-- img src="<c:url value = "/admin/go/ajaxGetDispatcherPic"/>/${userId}" alt="${userId} image" / -->
										<img id='picUrl'src="" />
									</div>
								</div>
								
								<div class="row butsDiv">
									<div class="col-sm-12 text-center">
										<button type="button" id="onDutyBtn" class="btn btn-group-lg btn-success ">
											<i class="fas fa-check-circle">&nbsp;&nbsp;上班打卡</i>
										</button>&nbsp;&nbsp;&nbsp;&nbsp;
										<button type="button" id="offDutyBtn" class="btn btn-group-lg btn-info ">
											<i class="fas fa-check">&nbsp;&nbsp;下班打卡</i>
										</button>&nbsp;&nbsp;&nbsp;&nbsp;
										<button type="button" id="absenceBtn" class="btn btn-group-lg btn-danger ">
											<i class="fas fa-circle-notch">&nbsp;&nbsp;缺勤</i>
										</button>
									</div>
								</div>
								<div class="row butsDiv">
									<div class="col-sm-12 text-center">
										<br/>
									</div>
								</div>
								<div class="row butsDiv">
									<div class="col-sm-12 text-center">
										<button type="button" id="btn_search" class="btn btn-primary ">
											<i class="fab fa-buromobelexperte">&nbsp;&nbsp;查詢打卡紀錄</i>
										</button>
									</div>
								</div>
							</div>
						</div>
						<div class="card-footer text-right">
								
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
		</form>
	</div>

	



	<script>
		$(document).ready(function() {
			
			$('.butsDiv').hide();
			
			initSelectBar();
			
			$('#onDutyBtn').click(function(e) {

				var goStatus = "S";
				ajaxSaveHt(goStatus);
				
			});
			
			$('#offDutyBtn').click(function(e) {
				
				var goStatus = "E";
				ajaxSaveHt(goStatus);
				
			});
			
			$('#absenceBtn').click(function(e) {
				
				
			});
			
			$('#btn_search').click(function(e) {
				
				
			});
			
		});
		
		//預設讀取選單  廠商 => 倉庫 => 人員 
		function initSelectBar(){
			
			global.comId = $("#comId").kendoDropDownList({
				change: function(e) {
					$('.butsDiv').hide();
				},
                optionLabel: "請選擇",
                dataTextField: "name",
                dataValueField: "id",
                dataSource: {
                    type: "jsonp",
                    serverFiltering: true,
                    transport: {
                    		read : {
								type : "POST",
								url : '<c:url value = "/admin/go/ajaxGetComIdSelectList"/>',
								dataType : "jsonp",
								data : function() {
									return {
										nona:'nona'
									};
								}
							}
                   	}
                }
            }).data("kendoDropDownList");
			
			global.whId = $("#whId").kendoDropDownList({
				change: function(e) {
					$('.butsDiv').hide();
				},
                autoBind: false,
                cascadeFrom: "comId",
                optionLabel: "請選擇",
                dataTextField: "wh_name",
                dataValueField: "wh_id",
                dataSource: {
                    type: "jsonp",
                    serverFiltering: true,
                    transport: {
                    		read : {
								type : "POST",
								url : '<c:url value = "/admin/go/ajaxGetWhIdSelectListByCompId"/>',
								dataType : "jsonp",
								data : function() {
									return {
										comId:$('#comId').val()   
									};
								}
							}
                   	}
                }
            }).data("kendoDropDownList");
			
			
			global.userId = $("#userId").kendoDropDownList({
				 change: function(e) {
						
						if($('#userId').val()!=''){
							var userId = $('#userId').val();
							$('.butsDiv').show();
							$('#userName').val($("#userId").data("kendoDropDownList").text());
							$('#picUrl img').css({'max-width':'300px','max-height': '300px;'});
							$("#picUrl").attr("src", "ajaxGetDispatcherPic/"+userId);
							
						}else{
							$('.butsDiv').hide();
						}
				 },
                 autoBind: false,
                 cascadeFrom: "whId",
                 optionLabel: "請選擇",
                 dataTextField: "name",
                 dataValueField: "id",
                 dataSource: {
                     type: "jsonp",
                     serverFiltering: true,
                     transport: {
                     		read : {
 								type : "POST",
 								url : '<c:url value = "/admin/go/ajaxGetDisIdSelectListByWhIdCompId"/>',
 								dataType : "jsonp",
 								data : function() {
 									return {
 										comId:$('#comId').val()  ,
 										whId:$('#whId').val()  ,
 										userName:$('#userName').val()  
 									};
 								}
 							}
                    	}
                 }
             }).data("kendoDropDownList");
			

			global.dataSource = new kendo.data.DataSource({
				transport : {
					read : {
						type : "POST",
						url : '<c:url value = "/admin/go/ajaxHtGridList"/>',
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
			
			
			$("#grid").kendoGrid({
						columns : [
								//{
								//	template : "<button type='button' class='btn btn-success btn-sm' onclick='edit(\"#:id#\")'>編輯</button>&nbsp;"
								//			+ "<button type='button' class='btn btn-danger btn-sm' onclick='remove(\"#:id#\")'>刪除</button>&nbsp;",
								//	field : "action",
								//	title : "動作",
								//	width : 150
								//},
								{
									field : "user_id",
									title : "員工編號",
									width : 200
								},
								{
									field : "user_name",
									title : "姓名",
									width : 300
								},
								{
									field : "s_go_systime",
									title : "首筆時間",
									width : 300
								},
								{
									field : "e_go_systime",
									title : "末筆時間",
									width : 300
								},
								{
									field : "go_time",
									title : "工時(時:分:秒)",
									width : 150
								},
								{
									field : "contract_type",
									title : "計件/計時",
									width : 250
								}],
						dataSource : global.dataSource,
						sortable : true,
						pageable : true
					});
			
			
		}

		function ajaxSaveHt(goStatus) {
			$('#goStatus').val(goStatus);
			$.ajax({
				type : "POST",
				url : '<c:url value = "/admin/go/ajaxSaveHt"/>',
				data : $("#inputForm").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {
						global.dataSource.read();
//						reflashGird();
					} else {

					}

				}
			});
		}
		
	</script>
</body>

</html>