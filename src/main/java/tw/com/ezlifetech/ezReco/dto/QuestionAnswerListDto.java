package tw.com.ezlifetech.ezReco.dto;

import java.util.Date;

import tw.com.ezlifetech.ezReco.common.dto.CommonDto;

public class QuestionAnswerListDto extends CommonDto{
	
	public static final String dtoName = "questionAnswerListDto";

	private String id;
	private String questType;
	private String status;
	private String refId;
	private String questName;
	private String questMail;
	private String questPhone;
	private String questTitle;
	private String questContent;
	private String isAns;
	private Date ansTime;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;
	private String systemMemo;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQuestType() {
		return questType;
	}
	public void setQuestType(String questType) {
		this.questType = questType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
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
	public String getQuestPhone() {
		return questPhone;
	}
	public void setQuestPhone(String questPhone) {
		this.questPhone = questPhone;
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
	public String getIsAns() {
		return isAns;
	}
	public void setIsAns(String isAns) {
		this.isAns = isAns;
	}
	public Date getAnsTime() {
		return ansTime;
	}
	public void setAnsTime(Date ansTime) {
		this.ansTime = ansTime;
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
	
	
	
}
