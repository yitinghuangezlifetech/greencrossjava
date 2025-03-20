package tw.com.ezlifetech.ezReco.bean;

import tw.com.ezlifetech.ezReco.common.bean.CommonBean;

public class AccountManagerAdminBean extends CommonBean {
	public static final String beanFormName = "accountManagerAdminBean";
	private String vcode;
	private String vcodeName;
	
	
	public String getVcode() {
		return vcode;
	}
	public void setVcode(String vcode) {
		this.vcode = vcode;
	}
	public String getVcodeName() {
		return vcodeName;
	}
	public void setVcodeName(String vcodeName) {
		this.vcodeName = vcodeName;
	}
}
