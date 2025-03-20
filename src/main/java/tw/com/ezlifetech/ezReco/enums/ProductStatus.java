package tw.com.ezlifetech.ezReco.enums;

public enum ProductStatus {
	ALIVE("A","啟用"),
	STOP("S","停用"),
	;
	
	private String code;
	private String value;
	
	ProductStatus(String code,String value){
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
