<%@ page language="java" contentType="text/html;  charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/common-util.tld" prefix="pi"%>
<div class="container-fluid">
	<form id='saleForm'>
		<div class="row">
			<div class="col-sm-12">
				<div class="card">
					<div class="card-header text-right">
						<div class='row'>
							<div class="col-6 text-left">
								<button type="button" id="btn_back" class="btn btn-primary ">
									<i class="fas fa-angle-left"> 返回</i>
								</button>
							</div>
							<div class="col-6 text-right">
								<button type="button" id="btn_save" class="btn btn-primary ">
									<i class="fas fa-plus"> 新增</i>
								</button>
							</div>
						</div>
					
					
						
					</div>
					<div class="card-body">
						<div class='row'>
							<div class='col-12'>
								<div class="form-group ">
									<div class="col-sm-12">
										<label for=saleMode>特賣模式</label>
									</div>
									<div class='col-sm-12'>
										<div class='row'>
											<div class='col-sm-3'>
												<input type="radio" name="saleMode" id="saleModeDIS" value="DIS">折扣促銷
											</div>
											<div class='col-sm-3'>
												<input type="radio" name="saleMode" id="saleModeBNGMF" value="BNGMF">買N送M
											</div>
											<div class='col-sm-3'>
												<input type="radio" name="saleMode" id="saleModeBAGBF" value="BAGBF">買A送B
											</div>
											<div class='col-sm-3'>
												<input type="radio" name="saleMode" id="saleModeFG" value="FG">滿額贈
											</div>
										</div>
									</div>
									<div class='col-sm-12'>
										<font class='errText form-text' id='err_saleMode' color='red'></font>
									</div>
								</div>
							</div>
						</div>
						<div class='row'>
							<div class='col-sm-6'>
								<div class="form-group ">
									<div class='col-12'>
										<label for=saleName>特賣名稱</label>
									</div>
									<div class='col-12'>
										<input type="text" class="input" name="saleName" id="saleName" value="${productSaleDto.saleName}"
											style="width: 100%;">
									</div>
									<div class='col-12'>
										<font class='errText form-text' id='err_saleName' color='red'></font>
									</div>
								</div>
							</div>
						</div>
						<div class='row'>
							<div class='col-sm-6'>
								<div class="form-group ">
									<div class='col-12'>
										<label for=saleName>特賣起日</label>
									</div>
									<div class='col-12'>
										<input type="text" class="input" name="saleSdateS" id="saleSdate" value="${productSaleDto.saleSdateS}"
											onkeydown="return false;" style="width: 100%;">
									</div>
									<div class='col-sm-12'>
										<font class='errText form-text' id='err_saleSdate' color='red'></font>
									</div>
								</div>
							</div>
							<div class='col-sm-6'>
								<div class="form-group ">
									<div class='col-12'>
										<label for=saleName>特賣迄日</label>
									</div>
									<div class='col-12'>
										<input type="text" class="input" name="saleEdateS" id="saleEdate" value="${productSaleDto.saleEdateS}"
											onkeydown="return false;" style="width: 100%;">
									</div>  
									<div class='col-sm-12'>
										<font class='errText form-text' id='err_saleEdate' color='red'></font>
									</div>
								</div>
							</div>
						</div>
						<div class='row'>
							<div class='col-sm-6'>
								<div class="form-group ">
									<div class='col-12'>
										<label for=saleTargetType>特賣對象類型</label>
									</div>
									<div class='col-12'>
										<select id="saleTargetType" name="saleTargetType" style="width: 100%;">
											<option value="">請選擇</option>
											<option value="productClass">商品分類</option>
											<option value="product">單一商品</option>
										</select>
									</div>
									<div class='col-sm-12'>
										<font class='errText form-text' id='err_saleTargetType' color='red'></font>
									</div>
								</div>
							</div>
						</div>
						<div class='row'>
							<div class='col-12'>
								<hr/>
							</div>
						</div>
						<div class='row targetDiv' id='productClassSelMDiv'>
							<div class='col-12'>
								<div class="form-group ">
									<div class='col-12'>
										<label for=productClassSelM>商品分類</label>
									</div>
									<div class='col-12'>
										<select id="productClassSelM" name="productClassSelM" style="width: 100%;">
										</select>
									</div>
									<div class='col-12'>
										<font class='errText form-text' id='err_productClassSelM' color='red'></font>
									</div>
								</div>
							</div>
						</div>
						<div class='row targetDiv' id='productSelMDiv'>
							<div class='col-12'>
								<div class="form-group ">
									<div class='col-12'>
										<label for=productSelM>單一商品</label>
									</div>
									<div class='col-12'>
										<select id="productSelM" name="productSelM" style="width: 100%;">
										</select>
									</div>
									<div class='col-12'>
										<font class='errText form-text' id='err_productSelM' color='red'></font>
									</div>
								</div>
							</div>
						</div>
						<div class='row targetDiv' id='productClassSelDiv'>
							<div class='col-12'>
								<div class="form-group ">
									<div class='col-12'>
										<label for=productClassSel>商品分類</label>
									</div>
									<div class='col-12'>
										<select id="productClassSel" name="productClassSel" style="width: 100%;">
										</select>
									</div>
									<div class='col-12'>
										<font class='errText form-text' id='err_productClassSel' color='red'></font>
									</div>
								</div>
							</div>
						</div>
						<div class='row targetDiv' id='productSelDiv'>
							<div class='col-12'>
								<div class="form-group ">
									<div class='col-12'>
										<label for=productSel>單一商品</label>
									</div>
									<div class='col-12'>
										<select id="productSel" name="productSel" style="width: 100%;">
										</select>
									</div>
									<div class='col-12'>
										<font class='errText form-text' id='err_productSel' color='red'></font>
									</div>
								</div>
							</div>
						</div>
						<div class='row targetDiv' id='modeHr'>
							<div class='col-12'>
								<hr/>
							</div>
						</div>
						<div class='row targetDiv' id='DISDiv'>
							<div class='col-sm-6'>
								<div class="form-group ">
									<div class='col-12'>
										<label for=disNumber>折扣數百分比(%)</label>
									</div>
									<div class='col-12'>
										<input type="text" class="input" name="disNumber" id="disNumber" value="${productSaleDto.disNumber}" style="width: 100%;" onkeyup="return ValidateNumber($(this),value)">
									</div>
									<div class='col-12'>
										<font class='errText form-text' id='err_disNumber' color='red'></font>
									</div>
								</div>
							</div>
						</div>
						
						<div class='row targetDiv' id='BNGMFDiv'>
							<div class='col-sm-6'>
								<div class="form-group ">
									<div class='col-12'>
										<label for=buyN>買N</label>
									</div>
									<div class='col-12'>
										<input type="text" class="input" name="buyN" id="buyN" value="${productSaleDto.buyN}" style="width: 100%;" onkeyup="return ValidateNumber($(this),value)">
									</div>
									<div class='col-12'>
										<font class='errText form-text' id='err_buyN' color='red'></font>
									</div>
								</div>
							</div>
							<div class='col-sm-6'>
								<div class="form-group ">
									<div class='col-12'>
										<label for=freeM>送M</label>
									</div>
									<div class='col-12'>
										<input type="text" class="input" name="freeM" id="freeM" value="${productSaleDto.freeM}" style="width: 100%;" onkeyup="return ValidateNumber($(this),value)">
									</div>
									<div class='col-12'>
										<font class='errText form-text' id='err_freeM' color='red'></font>
									</div>
								</div>
							</div>
						</div>
						
						<div class='row targetDiv' id='BAGBFDiv'>
							<div class='col-sm-6'>
								<div class="form-group ">
									<div class='col-12'>
										<label for=freeB>送B</label>
									</div>
									<div class='col-12'>
										<select id="freeB" name="freeB">
										</select>
										
									</div>
									<div class='col-12'>
										<font class='errText form-text' id='err_freeB' color='red'></font>
									</div>
								</div>
							</div>
						</div>
						
						<div class='row targetDiv' id='FGDiv'>
							<div class='col-sm-6'>
								<div class="form-group ">
									<div class='col-12'>
										<label for=fullNumber>滿額金額</label>
									</div>
									<div class='col-12'>
										<input type="text" class="input" name="fullNumber" id="fullNumber" value="${productSaleDto.fullNumber}" style="width: 100%;" onkeyup="return ValidateNumber($(this),value)">
									</div>
									<div class='col-12'>
										<font class='errText form-text' id='err_fullNumber' color='red'></font>
									</div>
								</div>
							</div>
							<div class='col-sm-6'>
								<div class="form-group ">
									<div class='col-12'>
										<label for=gift>贈送</label>
									</div>
									<div class='col-12'>
									
										<select id="gift" name="gift" style="width: 100%;">
										</select>
										
									</div>
									<div class='col-12'>
										<font class='errText form-text' id='err_gift' color='red'></font>
									</div>
								</div>
							</div>
						</div>
						
					</div>
				</div>
			</div>
		</div>
		
		
		<input type="hidden" id='saleId' name="id" value="${productSaleDto.id}"> 
		<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
	</form>
	
	<input type='hidden' id='saleModeValue'  value='${productSaleDto.saleMode}'>
	<input type='hidden' id='saleTargetTypeValue' value='${productSaleDto.saleTargetType}'>
	<input type='hidden' id='productSelValue' value='${productSaleDto.productSel}'>
	<input type='hidden' id='productClassSelValue' value='${productSaleDto.productClassSel}'>
	<input type='hidden' id='freeBValue' value='${productSaleDto.freeB}'>
	<input type='hidden' id='giftValue' value='${productSaleDto.gift}'>
</div>
<form id="prodForm"></form>
<script>
	$(document).ready(function() {
		
		
		
		targetDivShowHide();
		
		setupDatePicker();
		
		resetSaleTargetType();
		
		setKendoSel();
		
		setSaleModeOnClick();
		
		prodClassSelSetting(function(){
			global.productClassSelM = $("#productClassSelM")
			.kendoMultiSelect({
				autoClose : false
			}).data("kendoMultiSelect");
			var array = ${classSelM};
			global.productClassSelM.value(array);
			
			global.productClassSel = $("#productClassSel").kendoDropDownList();
			
			
			productSelSetting(function(){
				global.productSelM = $("#productSelM")
				.kendoMultiSelect({
					autoClose : false
				}).data("kendoMultiSelect");
				var array = ${productSelM};
				global.productSelM.value(array);
				
				global.productSel = $("#productSel").kendoDropDownList();
				
				$("#freeB").kendoDropDownList();
				$("#gift").kendoDropDownList();
				
				editModeSwitch();
			});
		});
		
		
		
		$('#btn_save').click(function(e) {
			saveSale();
		});
		
		$('#btn_back').click(function(e) {
			$('#saleForm')
					.attr('action',
							'<c:url value = "/admin/productSaleManager/list"/>');
			$('#saleForm').attr('method',
					'post');
			$('#saleForm').submit();
		});
		
	});
	
	function editModeSwitch(){
		var saleId = $('#saleId').val();
		if(saleId!=''){
			
			$('#btn_save').html('<i class="fas fa-check"> 儲存</i>');
			$('#btn_save').removeClass('btn-primary');
			$('#btn_save').addClass('btn-success');
			
			var $radios = $('input:radio[name=saleMode]');
		    if($radios.is(':checked') === false) {
		        $radios.filter('[value='+$('#saleModeValue').val()+']').prop('checked', true);
		    }
			
			
			$('#saleTargetType').data("kendoDropDownList").value($('#saleTargetTypeValue').val());
			targetDivShowHide();
			$('#productSel').data("kendoDropDownList").value($('#productSelValue').val());
			$('#productClassSel').data("kendoDropDownList").value($('#productClassSelValue').val());
			$('#freeB').data("kendoDropDownList").value($('#freeBValue').val());
			$('#gift').data("kendoDropDownList").value($('#giftValue').val());
			saleTargetTypeSetting();
			
		}
	}
	
	function saveSale(){
		$('#saleTargetType').removeAttr('disabled');
		$.ajax({
			type : "POST",
			url : '<c:url value = "/admin/productSaleManager/ajaxSaleSave"/>',
			data : $("#saleForm").serialize(),// serializes the form's elements.
			success : function(result) {
				if (checkAjaxResp(result)) {
					saleTargetTypeSetting();
					$('#saleId').val(result.data.saleId);
					$('#saleModeValue').val(result.data.saleModeValue);
					$('#saleTargetTypeValue').val(result.data.saleTargetTypeValue);
					$('#productSelValue').val(result.data.productSelValue);
					$('#productClassSelValue').val(result.data.productClassSelValue);
					$('#freeBValue').val(result.data.freeBValue);
					$('#giftValue').val(result.data.giftValue);
					
					
					editModeSwitch();
				}
			}
		});
	}
	
	function setSaleModeOnClick(){
		$('input[type=radio][name=saleMode]').change(function() {
			resetSaleTargetType();
			saleTargetTypeSetting();
		});
	}
	
	function saleTargetTypeSetting(){
		var saleModeValue = $('input[name=saleMode]:checked', '#saleForm').val();
		
		$('#saleTargetType').removeAttr('disabled');
		if (saleModeValue == 'DIS') {
			
		}
		else if (saleModeValue == 'BNGMF') {
			$('#saleTargetType').val('product');
			$('#saleTargetType').attr('disabled', 'disabled');
		}	
		else if (saleModeValue == 'BAGBF') {
		
		}	
		else if (saleModeValue == 'FG') {

		}
		setKendoSel();
		targetDivShowHide();
	}
	
	function targetDivShowHide(){
		$('.targetDiv').hide();
		var saleModeValue = $('input[name=saleMode]:checked', '#saleForm').val();
		
		if(typeof saleModeValue === "undefined"){
			return ;
		}else{
			
			var saleTargetTypeValue =$('#saleTargetType').val(); 
			if(saleTargetTypeValue==''){
				return ;
			}
			
			
			if (saleModeValue == 'DIS') {
				
				$('#DISDiv').show();
				
				if(saleTargetTypeValue=='productClass'){
					$('#productClassSelMDiv').show();
				}else{
					$('#productSelMDiv').show();
					
				}
				
			}else if(saleModeValue == 'BNGMF'){
				
				$('#BNGMFDiv').show();
				
				if(saleTargetTypeValue=='product'){
					$('#productSelDiv').show();
				}
				
			}else if(saleModeValue == 'BAGBF'){
				
				$('#BAGBFDiv').show();
				
				if(saleTargetTypeValue=='productClass'){
					$('#productClassSelMDiv').show();
				}else{
					$('#productSelMDiv').show();
					
				}
				
			}else if(saleModeValue == 'FG'){
				
				$('#FGDiv').show();
				
				if(saleTargetTypeValue=='productClass'){
					$('#productClassSelDiv').show();
				}else{
					$('#productSelDiv').show();
					
				}
				
			}
			
			$('#modeHr').show();
			
			
		}
		
		
	}
	
	
	function resetSaleTargetType(){
		$('#saleTargetType').html('<option value="">請選擇</option> <option value="productClass">商品分類</option> <option value="product">單一商品</option>');
	}
	
	function setKendoSel(){
		$("#saleTargetType").kendoDropDownList({
			change: onChange
		});
	}
	
	function setupDatePicker(){
		global.start = $("#saleSdate").kendoDatePicker({
            change: startChange,
            start: "year",
            format: "yyyy/MM/dd"
        }).data("kendoDatePicker");

		global.end = $("#saleEdate").kendoDatePicker({
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
    
    function onChange() {
    	targetDivShowHide();
	};
    
    function prodClassSelSetting(successfulFunction) {
		$.ajax({
			type : "POST",
			url : '<c:url value = "/admin/productSaleManager/ajaxGetAllProdClass"/>',
			data : $("#inputForm").serialize(),// serializes the form's elements.
			success : function(result) {
				if (checkAjaxResp(result)) {
					$('#productClassSelM,#productClassSel').html('');
					var htmlCode = '<option value="">請選擇</option>';
					$.each(result.data,
						function(i, obj) {
							htmlCode = htmlCode+ '<option value="'+obj.classId+'">'+ obj.className+ '</option>';
						}
					);
					$('#productClassSelM,#productClassSel').html(htmlCode);
					successfulFunction();
				}
			}
		});
	}
    
    function productSelSetting(successfulFunction) {
		$.ajax({
			type : "POST",
			url : '<c:url value = "/admin/productSaleManager/ajaxGetAllProduct"/>',
			data : $("#inputForm").serialize(),// serializes the form's elements.
			success : function(result) {
				if (checkAjaxResp(result)) {
					$('#productSelM,#productSel').html('');
					var htmlCode = '<option value="">請選擇</option>';
					$.each(result.data,
						function(i, obj) {
							
							htmlCode = htmlCode+ '<option value="'+obj.id+'">'+ obj.proCode+ '/'+obj.proName+'</option>';
						}
					);
					$('#productSelM,#productSel,#freeB,#gift').html(htmlCode);
					successfulFunction();
				}
			}
		});
	}
</script>
