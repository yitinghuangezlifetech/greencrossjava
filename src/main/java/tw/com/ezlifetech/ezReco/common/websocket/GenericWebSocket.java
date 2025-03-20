package tw.com.ezlifetech.ezReco.common.websocket;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class GenericWebSocket {

	private static final String SUCCESSFUL="successful";
	private static final String FAIL="FAIL";
	
	
	
	public String respMessage(String message) {
		JsonObject jo = new JsonObject();
		jo.addProperty("message", message);
		return respMessage(jo,true);
	}
	
	
	public String respSuccessMessage(JsonObject jo) {
		return respMessage(jo,true);
	}
	
	
	public String respSuccessMessage(JsonArray ja) {
		return respMessage(ja,true);
	}
	
	
	public String respFailMessage(JsonObject jo) {
		return respMessage(jo,false);
	}
	
	public String respFailMessage(JsonArray ja) {
		return respMessage(ja,false);
	}
	
	
	public String respMessage(JsonElement data,boolean isSuccessful) {
		JsonObject jo = new JsonObject();
		jo.addProperty("isSuccessful", isSuccessful?SUCCESSFUL:FAIL);
		jo.add("data", data);
		
		return jo.toString();
	}
	
	
	
	
}
