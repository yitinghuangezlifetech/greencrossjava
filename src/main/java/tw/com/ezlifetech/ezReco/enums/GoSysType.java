package tw.com.ezlifetech.ezReco.enums;

public enum GoSysType {
	Logistic("A","物流平台"),
	Gordon("B","高登物流專案");

	String typeCode;
	String typeValue;
	
	GoSysType(String typeCode,String typeValue){
		this.typeCode=typeCode;
		this.typeValue=typeValue;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public String getTypeValue() {
		return typeValue;
	}
	
}
