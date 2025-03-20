package tw.com.ezlifetech.ezReco.enums;

public enum AritemStatus {
	ALIVE("A","啟動"),
	STOP("S","停用"),
	TMP("T","暫存"),
	;
	
	private String statusCode;
	private String statusValue;
	
	AritemStatus(String statusCode,String statusValue){
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
