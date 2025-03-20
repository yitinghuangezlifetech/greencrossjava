package tw.com.ezlifetech.ezReco.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import tw.com.ezlifetech.ezReco.common.model.GenericEntity;

@Entity
@Table(name = "guardian", schema = "public")
public class Guardian extends GenericEntity<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String newsType;
	
	private String classId;
	
	private String title;
	
	private String titleEn;
	
	private String content;
	
	private String contentEn;
	
	private String status;
	
	private String showTimeS;
	
	private String showTimeE;
	
	private String activeTimeS;
	
	private String activeTimesE;
	
	private String groupType;
	
	private String html;
	
	private String contact;
	
	private String email;
	
	private String tel;
	
	private String createUser;
	
	private String createTime;
		
	private String updateUser;
	
	private String updateTime;

	private String mainPicNo;

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 30)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "main_pic_no")
	public String getMainPicNo() {
		return mainPicNo;
	}

	public void setMainPicNo(String mainPicNo) {
		this.mainPicNo = mainPicNo;
	}
	
	@Column(name = "news_type", nullable = false, length = 2)
	public String getNewsType() {
		return newsType;
	}

	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}

	@Column(name = "title", nullable = false, length = 60)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "content", nullable = false)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "status", nullable = false, length = 2)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "show_time_s")
	public String getShowTimeS() {
		return showTimeS;
	}

	public void setShowTimeS(String showTimeS) {
		this.showTimeS = showTimeS;
	}

	@Column(name = "show_time_e")
	public String getShowTimeE() {
		return showTimeE;
	}

	public void setShowTimeE(String showTimeE) {
		this.showTimeE = showTimeE;
	}

	@Column(name = "active_time_s")
	public String getActiveTimeS() {
		return activeTimeS;
	}

	public void setActiveTimeS(String activeTimeS) {
		this.activeTimeS = activeTimeS;
	}

	@Column(name = "active_time_e")
	public String getActiveTimesE() {
		return activeTimesE;
	}

	public void setActiveTimesE(String activeTimesE) {
		this.activeTimesE = activeTimesE;
	}

	@Column(name = "group_type")
	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	@Column(name = "html")
	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	@Column(name = "contact")
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "tel")
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "create_user", nullable = false, length = 30)
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Column(name = "create_time", nullable = false, length = 30)
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Column(name = "update_user", nullable = false, length = 30)
	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@Column(name = "update_time", nullable = false, length = 30)
	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "title_en")
	public String getTitleEn() {
		return titleEn;
	}

	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}

	@Column(name = "content_en")
	public String getContentEn() {
		return contentEn;
	}

	public void setContentEn(String contentEn) {
		this.contentEn = contentEn;
	}

	@Column(name = "class_id")
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
	
	
}
