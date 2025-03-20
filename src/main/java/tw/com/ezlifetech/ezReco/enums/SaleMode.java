package tw.com.ezlifetech.ezReco.enums;

public enum SaleMode {

	DISCOUNT("DIS","折扣促銷"),
	BUY_N_GET_M_FREE("BNGMF","買N送M"),
	BUY_A_GET_B_FREE("BAGBF","買A送B"),
	FULL_GIFT("FG","滿額贈"),
	;
	
	
	private String code;
	private String value;
	
	SaleMode(String code,String value){
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	

	public String getValue() {
		return value;
	}

	
	
	
}
