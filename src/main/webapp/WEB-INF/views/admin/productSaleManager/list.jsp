<%@ page language="java" contentType="text/html;  charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/common-util.tld" prefix="pi"%>
	<div class="container-fluid">

		<div class="row">
			<div class="col-sm-12">
				<div class="card">
					<div class="card-body">
						<div class="container-fluid">
							<div class="row">
								<div class="col-sm-12">
									<label for="saleMode">特賣模式</label>
								</div>
								<div class='col-12'>
									<select id='saleMode' name='saleMode'>
										 <option value="">全部</option>
										 <option value="DIS">折扣促銷</option>
										 <option value="BNGMF">買N送M</option>
										 <option value="BAGBF">買A送B</option>
										 <option value="FG">滿額贈</option>
									</select>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12">
									<label for="saleName">特賣名稱</label>
								</div>
								<div class='col-sm-6'>
									<input type="text" id="saleName" class='input' name="saleName" value="" style="width: 100%;">
								</div>
							</div>
							<div class="row">
								<div class='col-sm-6'>
									<div class="row">
										<div class="col-sm-12">
											<label for="roleType">特賣起日</label>
										</div>
										<div class='col-sm-12'>
											<input type="text" id="saleSdateS" class='input' name="saleSdateS" value="${sDate}" style="width: 100%;" onkeydown="return false;">
										</div>
									</div>
								</div>
								<div class='col-sm-6'>
									<div class="row">
										<div class="col-sm-12">
											<label for="roleType">特賣迄日</label>
										</div>
										<div class='col-sm-12'>
											<input type="text" id="saleEdateS" class='input' name="saleEdateS" value="${eDate}" style="width: 100%;" onkeydown="return false;">
										</div>
									</div>
								</div>
								
							</div>
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
					<div class="card-header text-right">
						<button type="button" id="btn_add" class="btn btn-primary ">
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
	<form id="prodForm">
		<input type="hidden" id="id" name="id" value="">
	</form>
	<input type="hidden" id="defsaleMode" value="">
	<input type="hidden" id="defsaleName" value="">
	<input type="hidden" id="defsaleSdateS" value="${sDate}">
	<input type="hidden" id="defsaleEdateS" value="${eDate}">
	
	<script>
		$(document).ready(function() {
			
			$('#btn_add').click(function(e) {
				$('#id').val('');
				$('#prodForm').attr('action','<c:url value = "/admin/productSaleManager/addSale"/>');
				$('#prodForm').attr('method','post');
				$('#prodForm').submit();
			});
			
			$('#btn_search').click(function(e) {
				$('#defsaleMode').val($('#saleMode').val());
				$('#defsaleName').val($('#saleName').val());
				$('#defsaleSdateS').val($('#saleSdateS').val());
				$('#defsaleEdateS').val($('#saleEdateS').val());
				
				
				global.dataSource.read();
			});
			
			global.dataSource = new kendo.data.DataSource(
				{
					transport : {
						read : {
								url : '<c:url value = "/admin/productSaleManager/ajaxProductSaleGridList"/>',
								dataType : "jsonp",
								data:
									function() {
										return {
											saleName:$('#saleName').val(),
											saleMode:$('#saleMode').val(),
											saleSdateS:$('#saleSdateS').val(),
											saleEdateS:$('#saleEdateS').val()
										}
									}
						}
					},
				pageSize : 10
			});
			$("#grid").kendoGrid({
					columns : [
						{
							template : "<button type='button' class='btn btn-success btn-sm' onclick='edit(\"#:id#\")'>編輯</button>&nbsp;"
									+ "<button type='button' class='btn btn-danger btn-sm' onclick='remove(\"#:id#\")'>刪除</button>&nbsp;",
							field : "action",
							title : "動作",
							width : 150
						},
						{
							field : "sale_name",
							title : "特賣名稱",
							width : 250
						},
						{
							field : "sale_modes",
							title : "特賣模式",
							width : 100
						},
						{
							field : "sale_sdates",
							title : "特賣起日",
							width : 100
						},
						{
							field : "sale_edates",
							title : "特賣訖日",
							width : 100
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
				pageable : true,
				sortable : true
			});
			
			 setupDatePicker();   
			 
			 $("#saleMode").kendoDropDownList();
		});
		function edit(id){
			$('#id').val(id);
			$('#prodForm').attr('action','<c:url value = "/admin/productSaleManager/addSale"/>');
			$('#prodForm').attr('method','post');
			$('#prodForm').submit();
		}
		
		function remove(id){
			$('#id').val(id);
			$.ajax({
				type : "POST",
				url : '<c:url value = "/admin/productSaleManager/ajaxSaleRemove"/>',
				data : $("#prodForm").serialize(),// serializes the form's elements.
				success : function(result) {
					if (checkAjaxResp(result)) {
						$('#saleMode').data("kendoDropDownList").value($('#defsaleMode').val());
						$('#saleName').val($('#defsaleName').val());
						$('#saleSdateS').val($('#defsaleSdateS').val());
						$('#saleEdateS').val($('#defsaleEdateS').val());
						global.dataSource.read();
					}
				}
			});
		}
		
		
		function setupDatePicker(){
			global.start = $("#saleSdateS").kendoDatePicker({
	            change: startChange,
	            start: "year",
	            format: "yyyy/MM/dd"
	        }).data("kendoDatePicker");

			global.end = $("#saleEdateS").kendoDatePicker({
	            change: endChange,
	            start: "year",
	            format: "yyyy/MM/dd"
	        }).data("kendoDatePicker");

			global.start.max(global.end.value());
			global.end.min(global.start.value());
		}
		
		function startChange() {
	        var startDate = global.start.value(),
	        endDate = global.end.value();

	        if (startDate) {
	            startDate = new Date(startDate);
	            startDate.setDate(startDate.getDate());
	            global.end.min(startDate);
	        } else if (endDate) {
	        	global.start.max(new Date(endDate));
	        } else {
	            endDate = new Date();
	            global.start.max(endDate);
	            global.end.min(endDate);
	        }
	    }

	    function endChange() {
	        var endDate = global.end.value(),
	        startDate = global.start.value();

	        if (endDate) {
	            endDate = new Date(endDate);
	            endDate.setDate(endDate.getDate());
	            global.start.max(endDate);
	        } else if (startDate) {
	        	global.end.min(new Date(startDate));
	        } else {
	            endDate = new Date();
	            global.start.max(endDate);
	            global.end.min(endDate);
	        }
	    }
		
	</script>
