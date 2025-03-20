package tw.com.ezlifetech.ezReco.dto;

import java.util.Date;

import tw.com.ezlifetech.ezReco.common.dto.CommonDto;

public class DispatcherProfileDto extends CommonDto{
	public static final String dtoName = "dispatcherProfileDto";
	
	private String id;
	private String compId;
	private String whId;
	private String idNumber;
	private String userId;
	private String name;	
	private String sex;
	private String addr;
	private String timingOrOiece;
	private String phone;
	private String education;
	private String stackerLicense;	
	private String electricTrailer;
	private String laborPortection;
	private String healthInsurance;
	private String resPermitNumber;
	private String workPermitNumber;	
	private String studentPemitNumber;	
	private String photoPath;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;
	private String systemMemo;
	private String empId;
	
	private String birthday;
	private String stackerLicenseLimitDate;
	private String resPermitLimitDate;
	private String workPermitNumberLimitDate;
	private String studentPemitNumberLimitDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCompId() {
		return compId;
	}
	public void setCompId(String compId) {
		this.compId = compId;
	}
	public String getWhId() {
		return whId;
	}
	public void setWhId(String whId) {
		this.whId = whId;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getTimingOrOiece() {
		return timingOrOiece;
	}
	public void setTimingOrOiece(String timingOrOiece) {
		this.timingOrOiece = timingOrOiece;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getStackerLicense() {
		return stackerLicense;
	}
	public void setStackerLicense(String stackerLicense) {
		this.stackerLicense = stackerLicense;
	}
	public String getElectricTrailer() {
		return electricTrailer;
	}
	public void setElectricTrailer(String electricTrailer) {
		this.electricTrailer = electricTrailer;
	}
	public String getLaborPortection() {
		return laborPortection;
	}
	public void setLaborPortection(String laborPortection) {
		this.laborPortection = laborPortection;
	}
	public String getHealthInsurance() {
		return healthInsurance;
	}
	public void setHealthInsurance(String healthInsurance) {
		this.healthInsurance = healthInsurance;
	}
	public String getResPermitNumber() {
		return resPermitNumber;
	}
	public void setResPermitNumber(String resPermitNumber) {
		this.resPermitNumber = resPermitNumber;
	}
	public String getWorkPermitNumber() {
		return workPermitNumber;
	}
	public void setWorkPermitNumber(String workPermitNumber) {
		this.workPermitNumber = workPermitNumber;
	}
	public String getStudentPemitNumber() {
		return studentPemitNumber;
	}
	public void setStudentPemitNumber(String studentPemitNumber) {
		this.studentPemitNumber = studentPemitNumber;
	}
	public String getPhotoPath() {
		return photoPath;
	}
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
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
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getStackerLicenseLimitDate() {
		return stackerLicenseLimitDate;
	}
	public void setStackerLicenseLimitDate(String stackerLicenseLimitDate) {
		this.stackerLicenseLimitDate = stackerLicenseLimitDate;
	}
	public String getResPermitLimitDate() {
		return resPermitLimitDate;
	}
	public void setResPermitLimitDate(String resPermitLimitDate) {
		this.resPermitLimitDate = resPermitLimitDate;
	}
	public String getWorkPermitNumberLimitDate() {
		return workPermitNumberLimitDate;
	}
	public void setWorkPermitNumberLimitDate(String workPermitNumberLimitDate) {
		this.workPermitNumberLimitDate = workPermitNumberLimitDate;
	}
	public String getStudentPemitNumberLimitDate() {
		return studentPemitNumberLimitDate;
	}
	public void setStudentPemitNumberLimitDate(String studentPemitNumberLimitDate) {
		this.studentPemitNumberLimitDate = studentPemitNumberLimitDate;
	}	
	
}
