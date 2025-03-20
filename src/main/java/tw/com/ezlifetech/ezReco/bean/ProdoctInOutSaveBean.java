package tw.com.ezlifetech.ezReco.bean;

import tw.com.ezlifetech.ezReco.common.bean.CommonBean;

public class ProdoctInOutSaveBean  extends CommonBean{
	public static final String beanFormName = "prodoctInOutSaveBean";
	
	private String securityId;//驗證碼
	private String fromSearch;
	
	
	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public String getFromSearch() {
		return fromSearch;
	}

	public void setFromSearch(String fromSearch) {
		this.fromSearch = fromSearch;
	}
}
