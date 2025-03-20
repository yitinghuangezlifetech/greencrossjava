package tw.com.ezlifetech.ezReco.model;
// Generated 2019/11/26 �U�� 09:59:35 by Hibernate Tools 4.3.5.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import tw.com.ezlifetech.ezReco.common.model.GenericEntity;

/**
 * WebPagesDl generated by hbm2java
 */
@Entity
@Table(name = "web_pages_dl")
public class WebPagesDl extends GenericEntity<String> {

	private String id;
	private String pageId;
	private String pageDivName;
	private String pageDivNameEn;
	private String pageDiv;
	private String pageSortNo;
	private String pageHtml;
	private String pageHtmlEn;
	private String status;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;
	private String systemMemo;

	public WebPagesDl() {
	}

	public WebPagesDl(String id, String pageId, String pageHtml, String pageHtmlEn, String createUser,
			Date createTime) {
		this.id = id;
		this.pageId = pageId;
		this.pageHtml = pageHtml;
		this.pageHtmlEn = pageHtmlEn;
		this.createUser = createUser;
		this.createTime = createTime;
	}

	public WebPagesDl(String id, String pageId, String pageDivName, String pageDivNameEn, String pageDiv,
			String pageSortNo, String pageHtml, String pageHtmlEn, String status, String createUser, Date createTime,
			String updateUser, Date updateTime, String systemMemo) {
		this.id = id;
		this.pageId = pageId;
		this.pageDivName = pageDivName;
		this.pageDivNameEn = pageDivNameEn;
		this.pageDiv = pageDiv;
		this.pageSortNo = pageSortNo;
		this.pageHtml = pageHtml;
		this.pageHtmlEn = pageHtmlEn;
		this.status = status;
		this.createUser = createUser;
		this.createTime = createTime;
		this.updateUser = updateUser;
		this.updateTime = updateTime;
		this.systemMemo = systemMemo;
	}

	@Id

	@Column(name = "id", unique = true, nullable = false, length = 30)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "page_id", nullable = false, length = 30)
	public String getPageId() {
		return this.pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	@Column(name = "page_div_name", length = 50)
	public String getPageDivName() {
		return this.pageDivName;
	}

	public void setPageDivName(String pageDivName) {
		this.pageDivName = pageDivName;
	}

	@Column(name = "page_div_name_en", length = 50)
	public String getPageDivNameEn() {
		return this.pageDivNameEn;
	}

	public void setPageDivNameEn(String pageDivNameEn) {
		this.pageDivNameEn = pageDivNameEn;
	}

	@Column(name = "page_div", length = 150)
	public String getPageDiv() {
		return this.pageDiv;
	}

	public void setPageDiv(String pageDiv) {
		this.pageDiv = pageDiv;
	}

	@Column(name = "page_sort_no", length = 10)
	public String getPageSortNo() {
		return this.pageSortNo;
	}

	public void setPageSortNo(String pageSortNo) {
		this.pageSortNo = pageSortNo;
	}

	@Column(name = "page_html", nullable = false)
	public String getPageHtml() {
		return this.pageHtml;
	}

	public void setPageHtml(String pageHtml) {
		this.pageHtml = pageHtml;
	}

	@Column(name = "page_html_en", nullable = false)
	public String getPageHtmlEn() {
		return this.pageHtmlEn;
	}

	public void setPageHtmlEn(String pageHtmlEn) {
		this.pageHtmlEn = pageHtmlEn;
	}

	@Column(name = "status", length = 10)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "create_user", nullable = false, length = 30)
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false, length = 29)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "update_user", length = 30)
	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time", length = 29)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "system_memo", length = 100)
	public String getSystemMemo() {
		return this.systemMemo;
	}

	public void setSystemMemo(String systemMemo) {
		this.systemMemo = systemMemo;
	}

}
