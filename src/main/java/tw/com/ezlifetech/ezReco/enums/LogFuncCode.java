package tw.com.ezlifetech.ezReco.enums;

public enum LogFuncCode {
	
	LOGIN("login","登入"),
	MAIN_MENU("mainMenu","主選單"),
	FUNCTION("function","功能"),
	ADMIN_FUNCTION("adminFunction","管理功能"),
	;
	
	private String code;
	private String funcName;
	
	LogFuncCode(String code,String funcName){
		this.code = code;
		this.funcName = funcName;
	}

	public String getCode() {
		return code;
	}

	public String getFuncName() {
		return funcName;
	}
	
	
}
