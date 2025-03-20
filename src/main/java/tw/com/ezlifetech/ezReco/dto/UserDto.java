package tw.com.ezlifetech.ezReco.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.beans.BeanUtils;

import tw.com.ezlifetech.ezReco.common.dto.CommonDto;
import tw.com.ezlifetech.ezReco.model.User;
import tw.com.ezlifetech.ezReco.util.StringUtil;



public class UserDto extends CommonDto{
	
	public static final String dtoName = "userDto";
	
	private String id;
	private String userName;
	private String userNameEn;
	private String parentUserId;
	private String comId;
	private String roleType;
	private String roleNo;
	private String userStatus;
	private String ban;
	private String contact;
	private Date userStartDate;
	private Date userCloseDate;
	private String userStartDateStr;
	private String userCloseDateStr;
	private String userPwd;
	private String userPwdAgain;
	private Date changePwdTime;
	private Date loginTime;
	private Date loginErrTime;
	private String loginErrTimes;
	private String userAddr;
	private String userCellphone;
	private String userEmail;
	private String userFax;
	private String userTel;
	private String userExt;
	private String pwdKeeper;
	private String pwdKeeperEmail;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;
	private String systemMemo;
	
	    
	
	private String sessionId;
	
	
	
	private String userIp;
	
	
	private List<ProfFunctionDto> funcList;
	private String roleId;
	private String isEditMode;
	private String parentUserIdStr;
	private String idStr;
	private String roleName;
	private Integer inShoppingCarNum;
	
	public String getUserStartDateStr() {
		return userStartDateStr;
	}

	public void setUserStartDateStr(String userStartDateStr) {
		this.userStartDateStr = userStartDateStr;
	}

	public String getUserCloseDateStr() {
		return userCloseDateStr;
	}

	public void setUserCloseDateStr(String userCloseDateStr) {
		this.userCloseDateStr = userCloseDateStr;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getIsEditMode() {
		return isEditMode;
	}

	public void setIsEditMode(String isEditMode) {
		this.isEditMode = isEditMode;
	}

	public String getParentUserIdStr() {
		return parentUserIdStr;
	}

	public void setParentUserIdStr(String parentUserIdStr) {
		this.parentUserIdStr = parentUserIdStr;
	}

	public String getIdStr() {
		return idStr;
	}

	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public UserDto() {
		
	}
	
	public UserDto(User user) {
		BeanUtils.copyProperties(user, this);
	}
	

	


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getUserNameEn() {
		return userNameEn;
	}


	public void setUserNameEn(String userNameEn) {
		this.userNameEn = userNameEn;
	}


	


	public String getUserStatus() {
		return userStatus;
	}


	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}


	


	public String getUserEmail() {
		return userEmail;
	}


	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}


	public String getUserTel() {
		return userTel;
	}


	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}


	

	public String getUserFax() {
		return userFax;
	}


	public void setUserFax(String userFax) {
		this.userFax = userFax;
	}


	public String getUserAddr() {
		return userAddr;
	}


	public void setUserAddr(String userAddr) {
		this.userAddr = userAddr;
	}


	


	public String getRoleType() {
		return roleType;
	}


	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}


	public String getRoleNo() {
		return roleNo;
	}


	public void setRoleNo(String roleNo) {
		this.roleNo = roleNo;
	}


	public String getParentUserId() {
		return parentUserId;
	}


	public void setParentUserId(String parentUserId) {
		this.parentUserId = parentUserId;
	}


	


	public String getLoginErrTimes() {
		return loginErrTimes;
	}


	public void setLoginErrTimes(String loginErrTimes) {
		this.loginErrTimes = loginErrTimes;
	}


	public String getCreateUser() {
		return createUser;
	}


	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}


	

	public String getUpdateUser() {
		return updateUser;
	}


	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}





	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getUserStartDate() {
		return userStartDate;
	}

	public void setUserStartDate(Date userStartDate) {
		this.userStartDate = userStartDate;
	}

	public Date getUserCloseDate() {
		return userCloseDate;
	}

	public void setUserCloseDate(Date userCloseDate) {
		this.userCloseDate = userCloseDate;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public Date getChangePwdTime() {
		return changePwdTime;
	}

	public void setChangePwdTime(Date changePwdTime) {
		this.changePwdTime = changePwdTime;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLoginErrTime() {
		return loginErrTime;
	}

	public void setLoginErrTime(Date loginErrTime) {
		this.loginErrTime = loginErrTime;
	}

	public String getUserCellphone() {
		return userCellphone;
	}

	public void setUserCellphone(String userCellphone) {
		this.userCellphone = userCellphone;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getSystemMemo() {
		return systemMemo;
	}


	public void setSystemMemo(String systemMemo) {
		this.systemMemo = systemMemo;
	}


	public String getSessionId() {
		return sessionId;
	}


	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}


	public String getUserIp() {
		return userIp;
	}


	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}


	public String getUserPwdAgain() {
		return userPwdAgain;
	}

	public void setUserPwdAgain(String userPwdAgain) {
		this.userPwdAgain = userPwdAgain;
	}

	public String getBan() {
		return ban;
	}

	public void setBan(String ban) {
		this.ban = ban;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getUserExt() {
		return userExt;
	}

	public void setUserExt(String userExt) {
		this.userExt = userExt;
	}

	public String getPwdKeeper() {
		return pwdKeeper;
	}

	public void setPwdKeeper(String pwdKeeper) {
		this.pwdKeeper = pwdKeeper;
	}

	public String getPwdKeeperEmail() {
		return pwdKeeperEmail;
	}

	public void setPwdKeeperEmail(String pwdKeeperEmail) {
		this.pwdKeeperEmail = pwdKeeperEmail;
	}

	public List<ProfFunctionDto> getFuncList() {
		return funcList;
	}

	public void setFuncList(List<ProfFunctionDto> funcList) {
		this.funcList = funcList;
	}

	public Integer getInShoppingCarNum() {
		return inShoppingCarNum;
	}

	public void setInShoppingCarNum(Integer inShoppingCarNum) {
		this.inShoppingCarNum = inShoppingCarNum;
	}
	
	public String getOnwerId() {
		return StringUtil.isBlank(parentUserId)?id:parentUserId;
	}

	public String getComId() {
		return comId;
	}

	public void setComId(String comId) {
		this.comId = comId;
	}
	

	
}
