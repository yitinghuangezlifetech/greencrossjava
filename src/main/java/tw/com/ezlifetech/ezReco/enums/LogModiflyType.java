package tw.com.ezlifetech.ezReco.enums;

public enum LogModiflyType {
	ADD("add","新增"),
	UPD("upd","更新"),
	REM("rem","刪除"),
	;
	
	private String code;
	private String name;
	
	LogModiflyType(String code,String name){
		this.code=code;
		this.name=name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	
}
