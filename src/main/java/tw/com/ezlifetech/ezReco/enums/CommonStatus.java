package tw.com.ezlifetech.ezReco.enums;

import tw.com.ezlifetech.ezReco.util.StringUtil;

public enum CommonStatus {

	TMP("T","暫存"),
	WAIT_TO_BE_PROCESSED("E","待處理"),
	PROCESSED("P","處理中"),
	SEND("S","已傳送"),
	RETURN("R","退回"),
	RESEND("RS","退回重送"),
	DELETE("D","已刪除"),
	FINISH("F","已完成"),
	AGREE("A","同意"),
	REFUSE("RF","拒絕"),
	;
	
	private String statusCode;
	private String ststusText;
	
	CommonStatus(String statusCode,String ststusText){
		this.statusCode=statusCode;
		this.ststusText=ststusText;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public String getStstusText() {
		return ststusText;
	}
	
	
	
	public static String getStstusTextByCode(String code) {
		String res ="";
		if(StringUtil.isBlank(code)) {
			return res;
		}else {
			
			for(CommonStatus c : CommonStatus.values()) {
				if(code.equals(c.getStatusCode())) {
					res = c.getStstusText();
					break;
				}
			}
		}
		
		
		
		return res;
	}
}
