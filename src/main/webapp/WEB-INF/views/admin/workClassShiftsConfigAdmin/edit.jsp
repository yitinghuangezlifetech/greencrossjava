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
									<!-- <button type="button" id="btn_save" class="btn btn-primary ">
										<i class="fas fa-sync-alt">儲存</i>
									</button> -->
									
									<button type="button" id="btn_agree" class="btn btn-primary ">
										<i class="fas fa-sync-alt">同意</i>
									</button>
									
									<button type="button" id="btn_ref" class="btn btn-primary ">
										<i class="fas fa-sync-alt">拒絕</i>
									</button>
									
									<button type="button" id="btn_return" class="btn btn-primary ">
										<i class="fas fa-sync-alt">退回</i>
									</button>
									
								</div>
							</div>
						</div>
						<form id="inputForm">
							<input type="hidden" id="id" name="id" value="${workClassShiftHtDto.id}">
							<div class="card-body">
								
								<div class="row">
									<div class="col-sm-12">
										<label for=proSpec>合約名稱*</label>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12">
										<input type="text" class="input" name="configName" value="${workClassShiftHtDto.configName}" style="width: 100%;" disabled>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12">
										<font class='errText form-text' id='err_configName' color='red'></font>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12">
										<hr/>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12">
										<label for=compId>所屬公司</label>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12">
										<select id = 'compId' name='compId' disabled>
											<option value="">無</option>
											<c:forEach items="${compIdSelectList}" var="current">
												<option value="${current.id}">${current.name}</option>
											</c:forEach>
										</select>
										<input type="hidden" id="selectCompId"  value="${workClassShiftHtDto.compId}">
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12">
										<font class='errText form-text' id='err_compId' color='red'></font>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12">
										<hr/>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12">
										<label for=proSpec>合約負責人帳號</label>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12">
										<select id = 'principalId' name='principalId' disabled>
											<option value="">無</option>
										</select>
										<input type="hidden" id="selectPrincipalId"  value="${workClassShiftHtDto.principalId}">
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12">
										<hr/>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12">
										<label for=proSpec>現場主管帳號</label>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12">
										<select id = 'siteSuperId' name='siteSuperId' disabled>
											<option value="">無</option>
										</select>
										<input type="hidden" id="selectSiteSuperId"  value="${workClassShiftHtDto.siteSuperId}">
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<br/>
		<div id='detailDiv' class="container-fluid">
			<div class="row">
				<div class="col-sm-12">
					<div class="card">
						<div class="card-header">
							<div class="row">
								<div class="col-sm-6">
									<label >合約明細設定</label>
								</div>
								<div class="col-sm-6 text-right">
									<!-- <button type="button" id="addBtn" class="btn btn-primary ">
										<i class="fas fa-plus"> 新增班別</i>
									</button> -->
								</div>
							</div>
						</div>
						<div class="card-body">
							<div class="row">
								<div class="col-sm-12">
									<div id='grid'>
										
									</div>
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
					<form id="dlInputForm">
						<input type="hidden" name="htId" value="${workClassShiftHtDto.id}">
						<input type="hidden" id="dlId" name="id" value="">
						<input type="hidden" id="contract_type" name="contract_type" value="">
						<header class="panel-heading">
							<h3 class="panel-title">合約明細</h3>
						</header>
						<div class='panel-body'>
							<table class="table table-condensed">
								
								<tr >
									<td><label for="className">合約明細名稱*</label></td>
									<td><input type="text" id="className" class='input' name="className" value="" disabled> 
										<font class='errText form-text' id='err_className' color='red'></font>
									</td>
								</tr>
								<tr >
									<td><label for="sdateTime">開始時間*</label></td>
									<td><input type="text" id="sdateTime" class='input' name="sdateTime" value=""  disabled> 
										<font class='errText form-text' id='err_sdateTime' color='red'></font>
									</td>
								</tr>
								<tr>
									<td><label for="edateTime">結束時間*</label></td>
									<td><input type="text" id="edateTime" class='input' name="edateTime" value="" disabled> 
										<font class='errText form-text' id='err_edateTime' color='red'></font>
									</td>
								</tr>
								<tr>
									<td><label for="className">計時/計件*</label></td>
									<td>
									<select id='contractType' name ='contractType' disabled>
										<option value="">請選擇</option>
										<option value="time">計時</option>
										<option value="package">計件</option>
									</select> 
										<font class='errText form-text' id='err_contractType' color='red'></font>
									</td>
								</tr>
								<tr class='timesTr'>
									<td><label for="salary">每單位時間薪水*</label></td>
									<td><input type="text" id="salary" class='input' name="salary" value="" disabled> 
										<font class='errText form-text' id='err_salary' color='red'></font>
									</td>
								</tr>
								<tr class='timesTr'>
									<td><label for="extendIn">延長工時2小時以內*</label></td>
									<td><input type="text" id="extendIn" class='input' name="extendIn" value="" disabled > 
										<font class='errText form-text' id='err_extendIn' color='red'></font>
									</td>
								</tr>
								<tr class='timesTr'>
									<td><label for="extendOut">延長工時2小時以上*</label></td>
									<td><input type="text" id="extendOut" class='input' name="extendOut" value="" disabled> 
										<font class='errText form-text' id='err_extendOut' color='red'></font>
									</td>
								</tr>
								<tr class='timesTr'>
									<td><label for="nightAdd">夜間加計*</label></td>
									<td><input type="text" id="nightAdd" class='input' name="nightAdd" value="" disabled> 
										<font class='errText form-text' id='err_nightAdd' color='red'></font>
									</td>
								</tr>
								<tr class='packageTr'>
									<td><label for="remuneration">勞務報酬單價*</label></td>
									<td><input type="text" id="remuneration" class='input' name="remuneration" value="" disabled> 
										<font class='errText form-text' id='err_remuneration' color='red'></font>
									</td>
								</tr>
								
								<tr class='timesTr'>
									<td><label for="unit">單位</label></td>
									<td>
										<select id = 'unit' name='unit' disabled>
											<option value="">請選擇</option>
											<option value="1hour">時</option>
										</select>
										<font class='errText form-text' id='err_unit' color='red'></font>
									</td>
								</tr>
							</table>
						</div>
						<div class="panel-footer text-right">
							<!-- <button type="button" id="formModelSend" class="btn btn-primary ">
								<span class="glyphicon glyphicon-ok"></span>送出
							</button> -->
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
			init();
			global.formModel = $("#formModal").kendoWindow({

				modal : true,
				title : false,
				visible : false,
				iframe : false,
			}).data("kendoWindow");
			
			$("#sdateTime").kendoTimePicker({
			    format: "HH:mm",
			    interval: 15,
			    parseFormats: ["HHmm","HH:mm"] //format also will be added to parseFormats
			});
			$("#edateTime").kendoTimePicker({
			    format: "HH:mm",
			    interval: 15,
			    parseFormats: ["HHmm","HH:mm"] //format also will be added to parseFormats
			});
			
			$('#compId').kendoDropDownList();
			$('#unit').kendoDropDownList();
			$('#principalId').kendoDropDownList();
			$('#siteSuperId').kendoDropDownList();
			$('#contractType').kendoDropDownList();
			
			
			$('#btn_agree').click(function(e) {
				agreeBut();
			});
			
			$('#btn_return').click(function(e) {
				
				returnBut();
			});
			
			$('#btn_ref').click(function(e) {
				
				refBut();
			});
			
			$('#formModelSend').click(function(e) {
				saveDlDate();
			});
			
			$('#formModelClose').click(function(e) {
				
				closeDlWindows();
			});
			
			$('#contractType').change(function(){
				  contTypeChange();
			});
			
			// grid Start
			global.dataSource = new kendo.data.DataSource(
					{
						transport : {
							read : {
								type : "POST",
								url : '<c:url value = "/admin/workClassShiftsConfigAdmin/ajaxDlGridList"/>',
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
									template : "<button type='button' class='btn btn-success btn-sm' onclick='edit(\"#:id#\")'>查看</button>&nbsp;"
									+"#if(is_main=='Y'){#"
										+"<button type='button' class='btn btn-warning btn-sm' '>合約主要明細</button>&nbsp;"
									+"#}else{#"
										
									+"#}#",
									field : "action",
									title : "動作",
									width : 200
								},
								{
									field : "class_name",
									title : "班別名稱",
									width : 200
								},
								{
									field : "sdate_time",
									title : "開始時間",
									width : 200
								},
								{
									field : "edate_time",
									title : "結束時間",
									width : 200
								},
								{
									field : "contract_type_text",
									title : "計時/計件",
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

		
		function agreeBut(){
			$.ajax({
				type : "POST",
				url : '<c:url value = "/admin/workClassShiftsConfigAdmin/ajaxAgree"/>',
				data : $("#inputForm").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {

						
					}

				}
			});
		}
		
		function returnBut(){
			$.ajax({
				type : "POST",
				url : '<c:url value = "/admin/workClassShiftsConfigAdmin/ajaxReturn"/>',
				data : $("#inputForm").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {

					
					}

				}
			});
		}
		
		function refBut(){
			$.ajax({
				type : "POST",
				url : '<c:url value = "/admin/workClassShiftsConfigAdmin/ajaxRef"/>',
				data : $("#inputForm").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {

					
					}

				}
			});
		}
		
		function contTypeChange(){
			if($('#contractType').val()=='time'){
				  $('.timesTr').show();
				  $('.packageTr').hide();
			  }else if($('#contractType').val()=='package'){
				  $('.timesTr').hide();
				  $('.packageTr').show();
			  }else{
				  $('.timesTr').hide();
				  $('.packageTr').hide();
			  }
			global.formModel.center();
		}
		
		
		
		function edit(dlId){
			$('#dlId').val(dlId);
			$.ajax({
				type : "POST",
				url : '<c:url value = "/admin/workClassShiftsConfigAdmin/ajaxGetDl"/>',
				data : $("#dlInputForm").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {
						
						$('#dlId').val(result.data.id);
						$('#className').val(result.data.className);
						$('#sdateTime').val(result.data.sdateTime);
						$('#edateTime').val(result.data.edateTime);
						$('#salary').val(result.data.salary);
						$("#unit").data('kendoDropDownList').value(result.data.unit);
						$('#contractType').data('kendoDropDownList').value(result.data.contractType);
						$('#extendIn').val(result.data.extendIn);
						$('#extendOut').val(result.data.extendOut);
						$('#nightAdd').val(result.data.nightAdd);
						$('#remuneration').val(result.data.remuneration);
						
						contTypeChange();
						openDlWindows();
					}

				}
			});
		}
		
		
		function detailDivShowHide(){
			if($('#id').val()==''){
				$('#detailDiv').hide();
			}else{
				$('#detailDiv').show();
			}
		}
	
		function init(){
			detailDivShowHide();
			if($('#id').val()==''){
				
			}else{
				$('#compId').val($('#selectCompId').val());
				$('#principalId').val($('#selectPrincipalId').val());
				$('#siteSuperId').val($('#selectSiteSuperId').val());
			}
		}
		
		function openDlWindows(){
			
			global.formModel.center().open();
		}
		
		function closeDlWindows(){
			resetDlForm();
			global.formModel.center().close();
		}
		
		
		function resetDlForm(){
			$('#dlId').val('');
			$('#className').val('');
			$('#sdateTime').val('');
			$('#edateTime').val('');
			$('#salary').val('');
			$("#unit").data('kendoDropDownList').value("");
			$("#contractType").data('kendoDropDownList').value("");
			
			contTypeChange();
		}

	</script>
</body>

</html>