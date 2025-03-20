package tw.com.ezlifetech.ezReco.bean;

import tw.com.ezlifetech.ezReco.common.bean.CommonBean;

public class LoginBean  extends CommonBean{
	public static final String beanFormName = "loginBean";
	
	//登入資訊
	private String loginUserId;
	private char[] loginUserPp;
	private String securityId;//驗證碼
	private String lang;//語系
	
	
	public String getLoginUserId() {
		return loginUserId;
	}
	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}
	public char[] getLoginUserPp() {
		return loginUserPp;
	}
	public void setLoginUserPp(char[] loginUserPp) {
		this.loginUserPp = loginUserPp;
	}
	public String getSecurityId() {
		return securityId;
	}
	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public static String getBeanformname() {
		return beanFormName;
	}
	
	
}
