package tw.com.ezlifetech.ezReco.enums;

public enum ProdLang {
	ALL("all","全部"),
	CHINESE("ch","中文"),
	ENGLISH("en","英文"),
	;
	
	private String code;
	private String value;
	
	ProdLang(String code,String value){
		this.code=code;
		this.value=value;
	}

	public String getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}
	
	
}
