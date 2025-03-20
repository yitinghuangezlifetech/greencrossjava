package tw.com.ezlifetech.ezReco.enums;

public enum QuestTypes {
	
	CONTACT_US("cu","聯絡我們"),
	PROUD_QUEST("pq","產品詢問單"),
	
;
	
	private String code;
	private String name;
	
	QuestTypes(String code,String name){
		this.code= code;
		this.name= name;
	}
	
	
	public String getCode() {
		return this.code;
		
	}
	
	public String getName() {
		return this.name;
		
	}
}
