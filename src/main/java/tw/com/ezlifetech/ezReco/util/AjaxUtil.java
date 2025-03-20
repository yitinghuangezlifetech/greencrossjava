package tw.com.ezlifetech.ezReco.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;

public class AjaxUtil {

	private final static String SUCCESS = "success";
	private final static String FAIL = "fail";
	private final static String VALIDATE_FAIL = "validateFail";
	private final static String NOT_LOGIN = "notLogin";
	
	
	public static String notLogin() {
		return bindMessage(NOT_LOGIN,"");
	}
	
	public static String success(JsonElement value) {
		return bindMessage(SUCCESS,null,value);
	}
	
	public static String success(AjaxReturnBean bean) {
		bean.setRespones(SUCCESS);
		return bindMessage(bean);
	}
	
	public static String success(String message) {
		return bindMessage(SUCCESS,message);
	}
	
	public static String success() {
		return success("");
	}
	
	public static String fail(String message) {
		return bindMessage(FAIL,message);
	}
	
	public static String fail(AjaxReturnBean bean) {
		bean.setRespones(FAIL);
		return bindMessage(bean);
	}
	
	public static String validateFail(JsonElement value) {
		return bindMessage(VALIDATE_FAIL,value,value);
	}
	
	

	public static String fail() {
		return fail("");
	}
	
	private static String bindMessage(String respones, String message) {
		JsonObject jo = new JsonObject();
		jo.addProperty("respones", respones);
		if(!message.trim().equals("")) {
			jo.addProperty("mesg", message);
		}
		return jo.toString();
	}
	
	private static String bindMessage(String respones, JsonElement message, JsonElement value) {
		JsonObject jo = new JsonObject();
		jo.addProperty("respones", respones);
		jo.add("mesg", message);
		jo.add("data", value);
		
		return jo.toString();
	}
	
	private static String bindMessage(AjaxReturnBean bean) {
		JsonObject jo = new JsonObject();
		jo.addProperty("respones", bean.getRespones());
		if(bean.getMessage()!=null && !"".equals(bean.getMessage().trim())) {
			jo.addProperty("mesg", bean.getMessage());
		}
		if(bean.getValue()!=null)
			jo.add("data", bean.getValue());
		return jo.toString();
	}
}
