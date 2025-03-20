package tw.com.ezlifetech.ezReco.bean;

import tw.com.ezlifetech.ezReco.common.bean.CommonBean;

public class ArMetaDataBean extends CommonBean{
	public static final String beanFormName = "arMetaDataBean";
	
	
	private String recognID;
	private String applicateID;
	private String applicateCate;
	private String topic;
	private String urlPath;
	private String desc;
	private String action;
	public String getRecognID() {
		return recognID;
	}
	public void setRecognID(String recognID) {
		this.recognID = recognID;
	}
	public String getApplicateID() {
		return applicateID;
	}
	public void setApplicateID(String applicateID) {
		this.applicateID = applicateID;
	}
	public String getApplicateCate() {
		return applicateCate;
	}
	public void setApplicateCate(String applicateCate) {
		this.applicateCate = applicateCate;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getUrlPath() {
		return urlPath;
	}
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	
	
	
	

}
