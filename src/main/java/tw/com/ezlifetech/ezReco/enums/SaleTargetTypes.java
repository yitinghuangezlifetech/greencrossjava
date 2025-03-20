package tw.com.ezlifetech.ezReco.enums;

public enum SaleTargetTypes {
	PRODUCT_CLASS("productClass","商品分類"),
	PRODUCT("product","單一商品"),
	;
	
	private String code;
	private String value;
	
	SaleTargetTypes(String code,String value){
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
