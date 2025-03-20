package tw.com.ezlifetech.ezReco.enums;

public enum ErrorMesgs {

	CAN_NOT_EMPTY("CNE","不可空白"," Cannot be blank"),
	PLEASE_SELECT("PS","請選擇{!label}",""),
	CAN_NOT_OUT_OF_RANGE("CNOOR","不可超過長度{!range}",""),
	REPEAT_ADD("RA","重複新增",""),
	OUT_OF_RANGE("OOR","長度超過{!range}",""),
	
	DATA_EXIST_SUBFUNC_CAN_NOT_REMOVE("DESCNR","尚有子功能資料存在，無法刪除",""),
	DATA_EXIST_CAN_NOT_REMOVE("DECNR","資料尚存在，無法刪除",""),
	DATA_NOT_EXIST_CAN_NOT_REMOVE("DNECNR","資料不存在，無法刪除",""),
	DATA_EXIST("DE","資料已存在",""),
	DATA_NOT_EXIST("DNE","資料不存在",""),
	DATA_FORMAT_NOT_MATCH ("DFNM","資料格式不正確"," data format is incorrect"),
	NOT_EQU("NEQ","不相同",""),
	NOT_NUMBER("NNUM","不為數字",""),
	PLZ_INPUT_NUMBER("PINUM","請輸入數字",""),
	MUST_BIGGER_THEN("MBT","必須大於{!label}",""),
	
	DATA_EXIST_IN_SHOPPING_CAR("DEISC","已存在購物車",""),
	DATA_CAN_NOT_UNDER_ZERO("DCNUZ","不可小於0",""), 
	DATA_EXIST_DL_CAN_NOT_REMOVE("DEDCNR","資料明細尚存在，無法刪除",""), 
	ONLY_CAN_INPUT_ENGLISH_NUMBER("OCIEN","只能輸入英文或數字",""),
	
	PLZ_UPLOAD_FILE("PUF","請上傳檔案",""),
	
	;
	
	private String code;
	private String doc;
	private String docEn;
	ErrorMesgs(String code,String doc,String docEn){
		this.code =code;
		this.doc =doc;
		this.docEn= docEn;
	}

	public String getCode() {
		return code;
	}

	public String getDoc() {
		return doc;
	}
	public String getDocEn() {
		return docEn;
	}
	
	public String getDoc(String lang) {
		return "en".equals(lang)?docEn:doc;
	}
	
}
