package tw.com.ezlifetech.ezReco.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import tw.com.ezlifetech.ezReco.common.dto.CommonDto;
import tw.com.ezlifetech.ezReco.model.WebPagesDl;

public class WebPagesDlDto extends CommonDto{
	
	public static final String dtoName = "WebPagesDlDto";
	
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
	
	public WebPagesDlDto() {
		
	}
	public WebPagesDlDto(WebPagesDl webPagesDl) {
		BeanUtils.copyProperties(webPagesDl, this);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getPageDivName() {
		return pageDivName;
	}
	public void setPageDivName(String pageDivName) {
		this.pageDivName = pageDivName;
	}
	public String getPageDivNameEn() {
		return pageDivNameEn;
	}
	public void setPageDivNameEn(String pageDivNameEn) {
		this.pageDivNameEn = pageDivNameEn;
	}
	public String getPageDiv() {
		return pageDiv;
	}
	public void setPageDiv(String pageDiv) {
		this.pageDiv = pageDiv;
	}
	public String getPageSortNo() {
		return pageSortNo;
	}
	public void setPageSortNo(String pageSortNo) {
		this.pageSortNo = pageSortNo;
	}
	public String getPageHtml() {
		return pageHtml;
	}
	public void setPageHtml(String pageHtml) {
		this.pageHtml = pageHtml;
	}
	public String getPageHtmlEn() {
		return pageHtmlEn;
	}
	public void setPageHtmlEn(String pageHtmlEn) {
		this.pageHtmlEn = pageHtmlEn;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
