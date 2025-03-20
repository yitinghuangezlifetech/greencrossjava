package tw.com.ezlifetech.ezReco.bean;

import tw.com.ezlifetech.ezReco.common.bean.CommonBean;

public class QuestMgrBean extends CommonBean {

	private String sDate;
	private String eDate;
	private String statusY;
	private String statusN;
	private String questType;
	private String id;
	private String status;
	public String getsDate() {
		return sDate;
	}
	public void setsDate(String sDate) {
		this.sDate = sDate;
	}
	public String geteDate() {
		return eDate;
	}
	public void seteDate(String eDate) {
		this.eDate = eDate;
	}
	public String getStatusY() {
		return statusY;
	}
	public void setStatusY(String statusY) {
		this.statusY = statusY;
	}
	public String getStatusN() {
		return statusN;
	}
	public void setStatusN(String statusN) {
		this.statusN = statusN;
	}
	public String getQuestType() {
		return questType;
	}
	public void setQuestType(String questType) {
		this.questType = questType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
