package tw.com.ezlifetech.ezReco.bean;

import java.util.Date;

import tw.com.ezlifetech.ezReco.common.bean.CommonBean;

public class GoReportBean extends CommonBean {

	public static final String beanFormName = "goReportBean";

	private String sCreateTime;
	private String eGoSystime;
	private String userId;		
	private String comId;
	private String whId;
	private String attendance;
	


	public String getsCreateTime() {
		return sCreateTime;
	}

	public void setsCreateTime(String sCreateTime) {
		this.sCreateTime = sCreateTime;
	}

	public String geteGoSystime() {
		return eGoSystime;
	}

	public void seteGoSystime(String eGoSystime) {
		this.eGoSystime = eGoSystime;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAttendance() {
		return attendance;
	}

	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}

	
}
