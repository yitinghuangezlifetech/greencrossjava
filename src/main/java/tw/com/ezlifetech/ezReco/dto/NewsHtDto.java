package tw.com.ezlifetech.ezReco.dto;

import tw.com.ezlifetech.ezReco.common.dto.CommonDto;

public class NewsHtDto extends CommonDto{
	public static final String dtoName = "newsHtDto";
	
	private String id;
	
	private String title;
	
	private String titleEn;
	
	private String content;
	
	private String contentEn;
	
	private String newsType;
	
	private String status;
	
	private String activeTimeS; 
	
	private String activeTimeE;

	private String showTimeS; 
	
	private String showTimeE;
	
	private String tel;
	
	private String email;
	
	private String html;
	
	private String contact;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getNewsType() {
		return newsType;
	}

	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getActiveTimeS() {
		return activeTimeS;
	}

	public void setActiveTimeS(String activeTimeS) {
		this.activeTimeS = activeTimeS;
	}

	public String getActiveTimeE() {
		return activeTimeE;
	}

	public void setActiveTimeE(String activeTimeE) {
		this.activeTimeE = activeTimeE;
	}

	public static String getDtoname() {
		return dtoName;
	}

	public String getShowTimeS() {
		return showTimeS;
	}

	public void setShowTimeS(String showTimeS) {
		this.showTimeS = showTimeS;
	}

	public String getShowTimeE() {
		return showTimeE;
	}

	public void setShowTimeE(String showTimeE) {
		this.showTimeE = showTimeE;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTitleEn() {
		return titleEn;
	}

	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}

	public String getContentEn() {
		return contentEn;
	}

	public void setContentEn(String contentEn) {
		this.contentEn = contentEn;
	}
		
	
}
