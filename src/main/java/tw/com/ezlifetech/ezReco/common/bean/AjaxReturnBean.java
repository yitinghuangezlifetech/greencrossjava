package tw.com.ezlifetech.ezReco.common.bean;

import com.google.gson.JsonElement;

public class AjaxReturnBean {
	private String htId;
	private String respones;
	private String message;
	private JsonElement value;
	public String getRespones() {
		return respones;
	}
	public void setRespones(String respones) {
		this.respones = respones;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public JsonElement getValue() {
		return value;
	}
	public void setValue(JsonElement value) {
		this.value = value;
	}
	public String getHtId() {
		return htId;
	}
	public void setHtId(String htId) {
		this.htId = htId;
	}
	
	
}
