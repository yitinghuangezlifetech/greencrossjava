package tw.com.ezlifetech.ezReco.bean;

import tw.com.ezlifetech.ezReco.common.bean.CommonBean;

public class SystemParamConfigBean extends CommonBean{
	public static final String beanFormName = "systemParamConfigBean";

	
	private String searchName;


	public String getSearchName() {
		return searchName;
	}


	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
}
