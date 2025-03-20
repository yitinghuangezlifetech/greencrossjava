package tw.com.ezlifetech.ezReco.enums;

public enum OrderItemStatus {
	EFFECTIVE("E","生效"),
	WAIT("W","處理中"),
	DELETE("D","已刪除"),
	FINISH("F","已完成"),
	;
	
	private String code;
	private String value;
	
	OrderItemStatus(String code,String value){
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
