package tw.com.ezlifetech.ezReco.enums;

public enum LogActionCode {

	CLICK("click","點擊"),
	NEW_SAVE("newSave","新儲存"),
	SAVE("save","儲存"),
	UPDATE("update","更新"),
	DELETE("del","刪除"),
	
	;
	private String code;
	private String actionName;
	
	LogActionCode(String code,String actionName){
		this.code = code;
		this.actionName = actionName;
	}

	public String getCode() {
		return code;
	}

	public String getActionName() {
		return actionName;
	}

	
}
