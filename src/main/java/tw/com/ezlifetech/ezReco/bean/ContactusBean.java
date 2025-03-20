package tw.com.ezlifetech.ezReco.bean;

import tw.com.ezlifetech.ezReco.common.bean.CommonBean;

public class ContactusBean extends CommonBean {

	public static final String beanFormName = "contactusBean";
	
	private String questName;
	private String questMail;
	private String questTitle;
	private String questContent;
	
	
	public String getQuestName() {
		return questName;
	}
	public void setQuestName(String questName) {
		this.questName = questName;
	}
	public String getQuestMail() {
		return questMail;
	}
	public void setQuestMail(String questMail) {
		this.questMail = questMail;
	}
	public String getQuestTitle() {
		return questTitle;
	}
	public void setQuestTitle(String questTitle) {
		this.questTitle = questTitle;
	}
	public String getQuestContent() {
		return questContent;
	}
	public void setQuestContent(String questContent) {
		this.questContent = questContent;
	}
	
	
	
	
	

}
