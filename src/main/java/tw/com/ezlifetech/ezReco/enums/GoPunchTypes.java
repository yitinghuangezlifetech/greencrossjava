package tw.com.ezlifetech.ezReco.enums;

public enum GoPunchTypes {
	
	ON_DUTY("on","上班"),
	OFF_DUTY("off","下班"),
	ABSENCE("abs","缺勤"),
	;
	
	private String code;
	private String text;
	
	GoPunchTypes(String code,String text){
		this.code=code;
		this.text=text;
	}

	public String getCode() {
		return code;
	}

	public String getText() {
		return text;
	}
	
	
	
}
