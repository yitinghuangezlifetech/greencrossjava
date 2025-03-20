package tw.com.ezlifetech.ezReco.bean;

import tw.com.ezlifetech.ezReco.common.bean.CommonBean;

public class StoreBean extends CommonBean {
	public static final String beanFormName = "storeBean";

	
	private String dataSize;
	private String onePageDataSize;
	private String nowPage;
	private String totalPage;
	
	private String productClassId;
	private String searchProductText;
	
	
	private String proNo;
	private String orderNumber;
	
	private String questName;
	private String questMail;
	private String questTitle;
	private String questContent;
	private String questTel;
	private String prodId;
	
	
	public String getDataSize() {
		return dataSize;
	}
	public void setDataSize(String dataSize) {
		this.dataSize = dataSize;
	}
	public String getOnePageDataSize() {
		return onePageDataSize;
	}
	public void setOnePageDataSize(String onePageDataSize) {
		this.onePageDataSize = onePageDataSize;
	}
	public String getNowPage() {
		return nowPage;
	}
	public void setNowPage(String nowPage) {
		this.nowPage = nowPage;
	}
	public String getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}
	public String getProductClassId() {
		return productClassId;
	}
	public void setProductClassId(String productClassId) {
		this.productClassId = productClassId;
	}
	public String getSearchProductText() {
		return searchProductText;
	}
	public void setSearchProductText(String searchProductText) {
		this.searchProductText = searchProductText;
	}
	public String getProNo() {
		return proNo;
	}
	public void setProNo(String proNo) {
		this.proNo = proNo;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
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
	public String getQuestTel() {
		return questTel;
	}
	public void setQuestTel(String questTel) {
		this.questTel = questTel;
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	
	
	
	
}
