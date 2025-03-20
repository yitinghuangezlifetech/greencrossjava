package tw.com.ezlifetech.ezReco.bean;

import java.util.Map;

import tw.com.ezlifetech.ezReco.common.bean.CommonBean;

public class ArReturnBean extends CommonBean{
	public static final String beanFormName = "arReturnBean";
	
	
	private boolean isSuccessful;
	private String orgResp;
	private Map resValues ;
	
	public boolean isSuccessful() {
		return isSuccessful;
	}
	public void setSuccessful(boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}
	public String getOrgResp() {
		return orgResp;
	}
	public void setOrgResp(String orgResp) {
		this.orgResp = orgResp;
	}
	public Map getResValues() {
		return resValues;
	}
	public void setResValues(Map resValues) {
		this.resValues = resValues;
	}
	
	

}
