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
							<div class="col-sm-6  text-left">

								<c:choose>
									<c:when test="${isEditMode == 'Y'}">
										${sessionScope.funcName}-帳號編輯
									</c:when>
									<c:otherwise>
										${sessionScope.funcName}-帳號新增
									</c:otherwise>
								</c:choose>

							</div>
							<div class="col-sm-6  text-right">
								<button type="button" id="btn_save" class="btn btn-primary ">
									<span class="glyphicon glyphicon-ok"></span>儲存
								</button>
							</div>
						</div>
					</div>
					<div class="card-body">
						<form id='inputForm'>

							<div class="row ">
								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="idstr">使用者帳號*</label>
										</div>
										<div class="col-sm-12">
											<input type="hidden" id="id" name="id" value="${userDto.id}">
											<input type="text" id='idStr' name='idStr'
												class="input editModeDisable" value="${userDto.id}"
												style="width: 100%;">
										</div>
										<div class="col-sm-12">
											<font class='errText form-text' id='err_id' color='red'></font>
										</div>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="userName">使用者名稱*</label>
										</div>
										<div class="col-sm-12">

											<input type="text" id='userName' name='userName'
												class="input" value="${userDto.userName}"
												style="width: 100%;">
										</div>
										<div class="col-sm-12">
											<font class='errText form-text' id='err_userName' color='red'></font>
										</div>
									</div>
								</div>
							</div>

							<div class="row ">
								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="parentUserIdStr">使用者主帳號</label>
										</div>
										<div class="col-sm-12">
											<input type="hidden" id="parentUserId" name="parentUserId"
												value="${userDto.parentUserId}"> <input type="text"
												id='parentUserIdStr' name='parentUserIdStr'
												class="input editModeDisable"
												value="${userDto.parentUserId}" style="width: 100%;">
										</div>
										<div class="col-sm-12">
											<font class='errText form-text' id='err_parentUserId'
												color='red'></font>
										</div>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="roleId">使用者角色*</label>
										</div>
										<div class="col-sm-12">
											<input type="hidden" id='oldRoleId' name='oldRoleId'
												value="${userDto.roleId}"> <select id='roleId'
												name='roleId'>
												<option value="">請選擇</option>
												<c:forEach items="${roleSelList}" var="current">
													<option value="${current.role_id}">${current.role_name}</option>
												</c:forEach>
											</select>
										</div>
										<div class="col-sm-12">
											<font class='errText form-text' id='err_roleId' color='red'></font>
										</div>
									</div>
								</div>
							</div>

							<div class="row ">
								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="ban">使用者統編*</label>
										</div>
										<div class="col-sm-12">
											<input type="text" id='ban' name='ban' class="input"
												value="${userDto.ban}" style="width: 100%;">
										</div>
										<div class="col-sm-12">
											<font class='errText form-text' id='err_ban' color='red'></font>
										</div>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="col-sm-12">
										<label for="comId">所屬公司主檔*</label>
									</div>
									<div class="col-sm-12">
										<select id = 'comId' name='comId'>
											<option value="">無</option>
											<c:forEach items="${compIdSelectList}" var="current">
												<option value="${current.id}">${current.name}</option>
											</c:forEach>
										</select>
										<input type="hidden" id="selectComId"  value="${userDto.comId}">
									</div>
									<div class="col-sm-12">
										<font class='errText form-text' id='err_comId' color='red'></font>
									</div>
								</div>
							</div>
							

							<div class="row ">
								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="userStatus">帳號狀態*</label>
										</div>
										<div class="col-sm-12">
											<input type="hidden" id='oldUserStatus' name='oldUserStatus'
												class="input" value="${userDto.userStatus}"
												style="width: 100%;"> <select id='userStatus'
												name='userStatus'>
												<c:forEach items="${userStatusList}" var="current">
													<option value="${current.statusCode}">${current.statusValue}</option>
												</c:forEach>
											</select>
										</div>
										<div class="col-sm-12">
											<font class='errText form-text' id='err_userStatus'
												color='red'></font>
										</div>
									</div>
								</div>

							</div>

							<div class="row ">
								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="userStartDateStr">帳號開始日期*</label>
										</div>
										<div class="col-sm-12">
											<input type="text" id='userStartDateStr'
												name='userStartDateStr' class="input"
												value="${userDto.userStartDateStr}"
												onkeydown="return false;" style="width: 100%;">
										</div>
										<div class="col-sm-12">
											<font class='errText form-text' id='err_userStartDateStr'
												color='red'></font>
										</div>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="userCloseDateStr">帳號結束日期</label>
										</div>
										<div class="col-sm-12">
											<input type="text" id='userCloseDateStr'
												name='userCloseDateStr' class="input"
												value="${userDto.userCloseDateStr}"
												onkeydown="return false;" style="width: 100%;">
										</div>
										<div class="col-sm-12">
											<font class='errText form-text' id='err_userCloseDateStr'
												color='red'></font>
										</div>
									</div>
								</div>
							</div>

							<hr />

							<div class="row ">
								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="contact">聯絡人</label>
										</div>
										<div class="col-sm-12">
											<input type="text" id='contact' name='contact' class="input"
												value="${userDto.contact}" style="width: 100%;">
										</div>
										<div class="col-sm-12">
											<font class='errText form-text' id='err_contact' color='red'></font>
										</div>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="userEmail">電子郵件</label>
										</div>
										<div class="col-sm-12">
											<input type="text" id='userEmail' name='userEmail'
												class="input" value="${userDto.userEmail}"
												style="width: 100%;">
										</div>
										<div class="col-sm-12">
											<font class='errText form-text' id='err_userEmail'
												color='red'></font>
										</div>
									</div>
								</div>
							</div>

							<div class="row ">
								<div class="col-sm-4">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="userTel">電話</label>
										</div>
										<div class="col-sm-12">
											<input type="text" id='userTel' name='userTel' class="input"
												value="${userDto.userTel}" style="width: 100%;">
										</div>
										<div class="col-sm-12">
											<font class='errText form-text' id='err_userTel' color='red'></font>
										</div>
									</div>
								</div>

								<div class="col-sm-2">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="userExt">分機</label>
										</div>
										<div class="col-sm-12">
											<input type="text" id='userExt' name='userExt' class="input"
												value="${userDto.userExt}" style="width: 100%;">
										</div>
										<div class="col-sm-12">
											<font class='errText form-text' id='err_userExt' color='red'></font>
										</div>
									</div>
								</div>

								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="userFax">傳真</label>
										</div>
										<div class="col-sm-12">
											<input type="text" id='userFax' name='userFax' class="input"
												value="${userDto.userFax}" style="width: 100%;">
										</div>
										<div class="col-sm-12">
											<font class='errText form-text' id='err_userFax' color='red'></font>
										</div>
									</div>
								</div>
							</div>

							<div class="row ">
								<div class="col-sm-12">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="userAddr">地址</label>
										</div>
										<div class="col-sm-12">
											<input type="text" id='userAddr' name='userAddr'
												class="input" value="${userDto.userAddr}"
												style="width: 100%;">
										</div>
										<div class="col-sm-12">
											<font class='errText form-text' id='err_userAddr' color='red'></font>
										</div>
									</div>
								</div>

							</div>

							<hr />

							<div class="row ">
								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="pwdKeeper">密碼保管人*</label>
										</div>
										<div class="col-sm-12">
											<input type="text" id='pwdKeeper' name='pwdKeeper'
												class="input" value="${userDto.pwdKeeper}"
												style="width: 100%;">
										</div>
										<div class="col-sm-12">
											<font class='errText form-text' id='err_pwdKeeper'
												color='red'></font>
										</div>
									</div>
								</div>

								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<label for="pwdKeeperEmail">密碼保管人電子郵件*</label>
										</div>
										<div class="col-sm-12">
											<input type="text" id='pwdKeeperEmail' name='pwdKeeperEmail'
												class="input" value="${userDto.pwdKeeperEmail}"
												style="width: 100%;">
										</div>
										<div class="col-sm-12">
											<font class='errText form-text' id='err_pwdKeeperEmail'
												color='red'></font>
										</div>
									</div>
								</div>
							</div>

							<div class="row ">
								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<c:choose>
												<c:when test="${isEditMode == 'Y'}">
													<label for="userPwd">帳號更新密碼</label>
												</c:when>
												<c:otherwise>
													<label for="userPwd">帳號密碼*</label>
												</c:otherwise>
											</c:choose>

										</div>
										<div class="col-sm-12">
											<input type="password" id='userPwd' name='userPwd'
												class="input" value="" style="width: 100%;">
										</div>
										<div class="col-sm-12">
											<font class='errText form-text' id='err_userPwd' color='red'></font>
										</div>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="form-group ">
										<div class="col-sm-12">
											<c:choose>
												<c:when test="${isEditMode == 'Y'}">
													<label for="userPwdAgain">帳號更新密碼確認</label>
												</c:when>
												<c:otherwise>
													<label for="userPwd">帳號密碼確認*</label>
												</c:otherwise>
											</c:choose>

										</div>
										<div class="col-sm-12">
											<input type="password" id='userPwdAgain' name='userPwdAgain'
												class="input" value="" style="width: 100%;">
										</div>
										<div class="col-sm-12">
											<font class='errText form-text' id='err_userPwdAgain'
												color='red'></font>
										</div>
									</div>
								</div>
							</div>


							<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
							<input type="hidden" id='isEditMode' name='isEditMode'
								value='${isEditMode}'>

						</form>
					</div>
				</div>
			</div>
		</div>
	</div>





	<form id='managerAccountForm'>
		<input type="hidden" id='userId' name='id' value="">
		<input type="hidden" name="checkSessionUserId" value="${sessionScope.userProf.id}"> <!-- 檢查資料輸入時與存檔時的userID 是否一致(新、刪、修都要在Form中加這段) -->
	</form>


	<script>
		$(document).ready(function() {
			var isEditMode = $('#isEditMode').val();
			if (isEditMode == 'Y') {
				$('.editModeDisable').attr('disabled', true);

				$('#roleId').val($('#oldRoleId').val());
				$('#userStatus').val($('#oldUserStatus').val());
				$('#comId').val($('#selectComId').val());

			}

			$('#roleId').kendoDropDownList();
			$('#userStatus').kendoDropDownList();
			$('#comId').kendoDropDownList();
			$("#userStartDateStr,#userCloseDateStr").kendoDatePicker({
				// defines the start view
				start : "year",
				// display month and year in the input
				format : "yyyy/MM/dd"
			});

			$('#btn_save').click(function(e) {
				save();
			});
			
			

		});

		function save() {

			$
					.ajax({
						type : "POST",
						url : '<c:url value = "/admin/accountManagerAdmin/ajaxSaveAccount"/>',
						data : $("#inputForm").serialize(),// serializes the form's elements.
						success : function(result) {
							if (checkAjaxResp(result)) {
								$('#managerAccountForm')
										.attr('action',
												'<c:url value = "/admin/accountManagerAdmin/list"/>');
								$('#managerAccountForm').attr('method', 'post');
								$('#managerAccountForm').submit();

							}

						}
					});
		}
	</script>
</body>

</html>