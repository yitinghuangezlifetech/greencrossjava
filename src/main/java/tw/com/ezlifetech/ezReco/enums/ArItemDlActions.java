package tw.com.ezlifetech.ezReco.enums;

public enum ArItemDlActions {
	
	WEB("web","網站轉導","web"),
	YOUTUBE("youtube","YouTube影片","video"),
	IMAGE_URL("imageUrl","外部圖片顯示","img"),
	IMAGE_UPLOAD("imaheUpload","上傳圖片","img"),
	;
	
	private String code;
	private String value;
	private String action;
	
	

	ArItemDlActions(String code,String value,String action){
		this.code=code;
		this.value=value;
		this.action=action;
	}

	public String getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}
	
	public String getAction() {
		return action;
	}
	
	
	
}
