package tw.com.ezlifetech.ezReco.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import tw.com.ezlifetech.ezReco.common.dto.CommonDto;
import tw.com.ezlifetech.ezReco.model.WebPages;

public class WebPagesDto extends CommonDto{
	
	public static final String dtoName = "WebPagesDto";
	
	private String id;
	private String pageName;
	private String pageNameEn;
	private String pageUrl;
	private String pageType;
	private String pageTitle;
	private String pageTitleEn;
	private String pageContent;
	private String pageContentEn;
	private String pageHtml;
	private String pageHtmlEn;
	private String pageSeo;
	private String pageGa;
	private String pageFb;
	private String pageHit;
	private String status;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;
	private String systemMemo;
	
	public WebPagesDto() {
		
	}
	public WebPagesDto(WebPages webPages) {
		BeanUtils.copyProperties(webPages, this);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public String getPageNameEn() {
		return pageNameEn;
	}
	public void setPageNameEn(String pageNameEn) {
		this.pageNameEn = pageNameEn;
	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public String getPageType() {
		return pageType;
	}
	public void setPageType(String pageType) {
		this.pageType = pageType;
	}
	public String getPageTitle() {
		return pageTitle;
	}
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
	public String getPageTitleEn() {
		return pageTitleEn;
	}
	public void setPageTitleEn(String pageTitleEn) {
		this.pageTitleEn = pageTitleEn;
	}
	public String getPageContent() {
		return pageContent;
	}
	public void setPageContent(String pageContent) {
		this.pageContent = pageContent;
	}
	public String getPageContentEn() {
		return pageContentEn;
	}
	public void setPageContentEn(String pageContentEn) {
		this.pageContentEn = pageContentEn;
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
	public String getPageSeo() {
		return pageSeo;
	}
	public void setPageSeo(String pageSeo) {
		this.pageSeo = pageSeo;
	}
	public String getPageGa() {
		return pageGa;
	}
	public void setPageGa(String pageGa) {
		this.pageGa = pageGa;
	}
	public String getPageFb() {
		return pageFb;
	}
	public void setPageFb(String pageFb) {
		this.pageFb = pageFb;
	}
	public String getPageHit() {
		return pageHit;
	}
	public void setPageHit(String pageHit) {
		this.pageHit = pageHit;
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
