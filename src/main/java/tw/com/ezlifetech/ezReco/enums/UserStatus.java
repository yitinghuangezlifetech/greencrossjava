package tw.com.ezlifetech.ezReco.enums;

public enum UserStatus {
	ALIVE("A","啟動"),
	STOP("S","停用"),
	;
	
	private String statusCode;
	private String statusValue;
	
	UserStatus(String statusCode,String statusValue){
		this.statusCode=statusCode;
		this.statusValue=statusValue;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public String getStatusValue() {
		return statusValue;
	}
	
	
}
