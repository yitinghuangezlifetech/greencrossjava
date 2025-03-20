<%@ page language="java" contentType="text/html;  charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/common-util.tld" prefix="pi"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Spring 4 MVC -HelloWorld</title>

</head>

<body>

	<div class="container-fluid">

		<div class="row">
			<div class="col-sm-12">
				<form id="inputForm">

					<div class="card">
						<div class="card-header"></div>
						<div class="card-body">
							<div class="container-fluid">
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for="searchName">建單日期</label>
											</div>
											<div class="col-sm-12">
												<input type="text" id='sDate' name='sDate' class="input" value="" onkeydown="return false;" style="width: 40%;">
													~
												<input type="text" id='eDate' name='eDate' class="input" value="" onkeydown="return false;" style="width: 40%;">
											</div>
										</div>
									</div>
								</div>
								
								
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group ">
											<div class="col-sm-12">
												<label for="searchName">篩選處理狀態</label>
											</div>
											<div class="col-sm-12">
												<input type="checkbox" id="status_N" name="status_N" value="N">&nbsp;&nbsp;未處理
												&nbsp;&nbsp;<input type="checkbox" id="status_Y" name="status_Y" value="Y">&nbsp;&nbsp;已處理
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
										<button type="button" id="btn_search" class="btn btn-primary ">
											<i class="fas fa-search"></i>&nbsp;&nbsp;搜尋
										</button>
									</div>
								</div>
							</div>
						</div>


					</div>
				</form>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div class="card">
					<div class="card-header"></div>
					<div class="card-body">
						<div class="container-fluid">
							<div class="row">
								<div class="col-sm-12">
									<div id = 'grid'></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
	</div>
	<div id="qAwindow">
		<div class="panel panel-bordered">
			<form id="qaForm">
				<input type="hidden" id="qaId" name="id" value="">
				<header class="panel-heading">
					<h3 class="panel-title"></h3>
				</header>
				<div class='panel-body'>
					<table class="table table-condensed">
						<tr>
							<td align='center' valign="middle" style="border-top:1px solid #FFF;">
								<label for="">詢問者名稱</label>
							</td>
							<td style="border-top:1px solid #FFF;">
								<input type="text" id="questName" class='input'  value="" style = "width:90%;" readonly> 
							</td>
						</tr>
						<tr>
							<td align='center' valign="middle" style="border-top:1px solid #FFF;">
								<label for="">詢問者電子郵件</label>
							</td>
							<td style="border-top:1px solid #FFF;">
								<input type="text" id="questMail" class='input'  value="" style = "width:90%;" readonly> 
							</td>
						</tr>
						<tr>
							<td align='center' valign="middle" style="border-top:1px solid #FFF;">
								<label for="">主旨</label>
							</td>
							<td style="border-top:1px solid #FFF;">
								<input type="text" id="questTitle" class='input'  value="" style = "width:90%;" readonly> 
							</td>
						</tr>
						<tr>
							<td align='center' valign="middle" style="border-top:1px solid #FFF;">
								<label for="">內容</label>
							</td>
							<td style="border-top:1px solid #FFF;">
								<textarea  id="questContent" class='input'  placeholder="" style = "width:90%;" rows="10" readonly></textarea>
							</td>
						</tr>
						<tr>
							<td align='center' valign="middle" >
								<label for="">是否已處理</label>
							</td>
							<td>
								<input type="text" id="isAns" class='input'  value="" style = "width:90%;" readonly> 
							</td>
						</tr>
						<tr>
							<td align='center' valign="middle" >
								<button type="button" id="btn_statusY" class="btn btn-warning ">
									<i class="fas fa-check-circle"></i>&nbsp;&nbsp;標記為已處理
								</button>
							</td>
							<td align='center' valign="middle">
								<button type="button" id="btn_statusN" class="btn btn-danger ">
									<i class="fas fa-times"></i>&nbsp;&nbsp;標記為未處理
								</button>
							</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
	
	</div>


	




	<script>
		$(document).ready(function() {
			global.status_N = "N";
			global.status_Y = "N";
			global.dataSource = new kendo.data.DataSource(
					{
						transport : {
							read : {
								type : "POST",
								url : '<c:url value = "/admin/contactUsQuestMgr/ajaxGetGridList"/>',
								dataType : "jsonp",
								data : function() {
									return {
										sDate:$('#sDate').val(),
										eDate:$('#eDate').val(),
										statusY:global.status_Y,
										statusN:global.status_N
									};
								}
							}
						},
						pageSize : 30
					});
			$("#grid")
			.kendoGrid(
					{
						columns : [
								{
									template : "<button type='button' class='btn btn-success btn-sm' onclick='edit(\"#:id#\")'>詳情</button>&nbsp;"
										,
									field : "action",
									title : "動作",
									width : 75
								},
								{
									field : "quest_title",
									title : "標題",
									width : 300
								},
								{
									field : "quest_name",
									title : "詢問者姓名",
									width : 150
								},
								{
									field : "quest_mail",
									title : "詢問者電子郵件",
									width : 150
								},
								{
									
									template : "#if(is_ans=='Y'){#"+
											"是"+
										"#}else{#"+
											"否"+
										"#}#",
									field : "is_ans",
									title : "是否處理",
									width : 100
								},
								{
									field : "create_time",
									title : "建立時間",
									width : 200
								} ],
						dataSource : global.dataSource,
						sortable : true,
						pageable : true
					});
			$("#sDate,#eDate").kendoDatePicker({
				// defines the start view
				start : "year",
				// display month and year in the input
				format : "yyyy/MM/dd"
			});
			
			global.qAwindow = $("#qAwindow").kendoWindow({
                width: "90%",
                title: "",
                visible: false,
                actions: [
                    "Close"
                ],
                close: onClose
            }).data("kendoWindow");
			
			$("#status_N").change(function() {
			    if(this.checked) {
			    	global.status_N = "Y";
			    }else{
			    	global.status_N = "N";
			    }
			});
			
			
			$("#status_Y").change(function() {
				 if(this.checked) {
				    	global.status_Y = "Y";
				    }else{
				    	global.status_Y = "N";
				    }
			});
			
			$('#btn_search').click(function(e) {
				search();
			});
			
			$('#btn_statusY').click(function(e) {
				chgStatus('Y');
			});
			
			$('#btn_statusN').click(function(e) {
				chgStatus('N');
			});
			
		});
		
		
		function search(){
			global.dataSource.read();
			
		}
		
		function chgStatus(status){
			$.ajax({
				type : "POST",
				url : '<c:url value = "/admin/contactUsQuestMgr/ajaxChgQuestStatus"/>',
				data : {
					id : $('#qaId').val(),
					status : status
					
				},// serializes the form's elements.
				success : function(result) {
					console.log(result);
					if (checkAjaxResp(result)) {
						
						global.qAwindow.center().close();
						search();
					} 
				}
			});
		}
		function edit(id){
			$('#qaId').val(id);
			$.ajax({
				type : "POST",
				url : '<c:url value = "/admin/contactUsQuestMgr/ajaxGetQuest"/>',
				data : $("#qaForm").serialize(),// serializes the form's elements.
				success : function(result) {
					console.log(result);
					if (checkAjaxResp(result)) {
						$('#questName').val(result.data.questName);
						$('#questMail').val(result.data.questMail);
						$('#questTitle').val(result.data.questTitle);
						$('#questContent').val(result.data.questContent);
						//$('#questTel').val('');
						if("Y"==result.data.isAns){
							$('#isAns').val('是');
						}else {
							$('#isAns').val('否');
						}
						global.qAwindow.center().open();
						
					} 
				}
			});
			
			
			
		}
		
		function onClose() {
            
        }
	</script>
</body>

</html>