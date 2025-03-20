package tw.com.ezlifetech.ezReco.dto;

import java.util.Date;

import com.ibm.icu.text.SimpleDateFormat;

import tw.com.ezlifetech.ezReco.common.dto.CommonDto;

public class GoHtDto extends CommonDto{
	public static final String dtoName = "goHtDto";
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private String id;
	private String go_id;
	private String goSysType;
	private String goStatus;
	private String userId;
	private String userName;
	private String logisticId;
	private String comId;
	private String whId;
	private String wcsId;
	private String contractType;
	private Date SGoSystime;
	private Date SGoTime;
	private String SPlace;
	private String SGps;
	private String SAddr;
	private String SCreateUser;
	private Date SCreateTime;
	private Date EGoSystime;
	private Date EGoTime;
	private String EPlace;
	private String EGps;
	private String EAddr;
	private String ECreateUser;
	private Date ECreateTime;
	private String goDis;
	private Date goTime;
	private String goSalary;
	private String goGeneralHr;
	private String goGeneralSal;
	private String goOvtHr;
	private String goOvtSal;
	private String updateUser;
	private Date updateTime;
	private String systemMemo;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGo_id() {
		return go_id;
	}
	public void setGo_id(String go_id) {
		this.go_id = go_id;
	}
	public String getGoSysType() {
		return goSysType;
	}
	public void setGoSysType(String goSysType) {
		this.goSysType = goSysType;
	}
	public String getGoStatus() {
		return goStatus;
	}
	public void setGoStatus(String goStatus) {
		this.goStatus = goStatus;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLogisticId() {
		return logisticId;
	}
	public void setLogisticId(String logisticId) {
		this.logisticId = logisticId;
	}
	public String getComId() {
		return comId;
	}
	public void setComId(String comId) {
		this.comId = comId;
	}
	public String getWhId() {
		return whId;
	}
	public void setWhId(String whId) {
		this.whId = whId;
	}
	public String getWcsId() {
		return wcsId;
	}
	public void setWcsId(String wcsId) {
		this.wcsId = wcsId;
	}
	public String getContractType() {
		return contractType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	public Date getSGoSystime() {
		return SGoSystime;
	}
	public void setSGoSystime(Date sGoSystime) {
		SGoSystime = sGoSystime;
	}
	public Date getSGoTime() {
		return SGoTime;
	}
	public void setSGoTime(Date sGoTime) {
		SGoTime = sGoTime;
	}
	public String getSPlace() {
		return SPlace;
	}
	public void setSPlace(String sPlace) {
		SPlace = sPlace;
	}
	public String getSGps() {
		return SGps;
	}
	public void setSGps(String sGps) {
		SGps = sGps;
	}
	public String getSAddr() {
		return SAddr;
	}
	public void setSAddr(String sAddr) {
		SAddr = sAddr;
	}
	public String getSCreateUser() {
		return SCreateUser;
	}
	public void setSCreateUser(String sCreateUser) {
		SCreateUser = sCreateUser;
	}
	public Date getSCreateTime() {
		return SCreateTime;
	}
	public void setSCreateTime(Date sCreateTime) {
		SCreateTime = sCreateTime;
	}
	public Date getEGoSystime() {
		return EGoSystime;
	}
	public void setEGoSystime(Date eGoSystime) {
		EGoSystime = eGoSystime;
	}
	public Date getEGoTime() {
		return EGoTime;
	}
	public void setEGoTime(Date eGoTime) {
		EGoTime = eGoTime;
	}
	public String getEPlace() {
		return EPlace;
	}
	public void setEPlace(String ePlace) {
		EPlace = ePlace;
	}
	public String getEGps() {
		return EGps;
	}
	public void setEGps(String eGps) {
		EGps = eGps;
	}
	public String getEAddr() {
		return EAddr;
	}
	public void setEAddr(String eAddr) {
		EAddr = eAddr;
	}
	public String getECreateUser() {
		return ECreateUser;
	}
	public void setECreateUser(String eCreateUser) {
		ECreateUser = eCreateUser;
	}
	public Date getECreateTime() {
		return ECreateTime;
	}
	public void setECreateTime(Date eCreateTime) {
		ECreateTime = eCreateTime;
	}
	public String getGoDis() {
		return goDis;
	}
	public void setGoDis(String goDis) {
		this.goDis = goDis;
	}
	public Date getGoTime() {
		return goTime;
	}
	public void setGoTime(Date goTime) {
		this.goTime = goTime;
	}
	public String getGoSalary() {
		return goSalary;
	}
	public void setGoSalary(String goSalary) {
		this.goSalary = goSalary;
	}
	public String getGoGeneralHr() {
		return goGeneralHr;
	}
	public void setGoGeneralHr(String goGeneralHr) {
		this.goGeneralHr = goGeneralHr;
	}
	public String getGoGeneralSal() {
		return goGeneralSal;
	}
	public void setGoGeneralSal(String goGeneralSal) {
		this.goGeneralSal = goGeneralSal;
	}
	public String getGoOvtHr() {
		return goOvtHr;
	}
	public void setGoOvtHr(String goOvtHr) {
		this.goOvtHr = goOvtHr;
	}
	public String getGoOvtSal() {
		return goOvtSal;
	}
	public void setGoOvtSal(String goOvtSal) {
		this.goOvtSal = goOvtSal;
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

	
}
