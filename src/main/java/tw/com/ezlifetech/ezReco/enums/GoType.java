package tw.com.ezlifetech.ezReco.enums;

public enum GoType {
	GoToWork("S","上班"),
	GoOffWork("E","下班");

	String typeCode;
	String typeValue;
	
	GoType(String typeCode,String typeValue){
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
