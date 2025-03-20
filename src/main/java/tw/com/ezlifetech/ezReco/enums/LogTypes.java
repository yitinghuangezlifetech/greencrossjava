package tw.com.ezlifetech.ezReco.enums;

public enum LogTypes {

	INFO("info","資訊",false),
	DEBUG("debug","除錯",false),
	WARNING("warning","警告",true),
	ERROR("error","錯誤",true),
	
	;
	private String typeCode;
	private String typeName;
	private boolean isWarningLog;
	
	
	LogTypes(String typeCode,String typeName,boolean isWarningLog){
		this.typeCode = typeCode;
		this.typeName = typeName;
		this.isWarningLog = isWarningLog;
	}


	public String getTypeCode() {
		return typeCode;
	}


	public String getTypeName() {
		return typeName;
	}


	public boolean isWarningLog() {
		return isWarningLog;
	}


	
	
	
	
	
}
