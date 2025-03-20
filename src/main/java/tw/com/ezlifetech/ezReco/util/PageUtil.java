package tw.com.ezlifetech.ezReco.util;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.common.bean.CommonBean;
import tw.com.ezlifetech.ezReco.common.dto.CommonDto;



public class PageUtil {

	/**
	 *
	 * for KendoUI Grid
	 * 將內容轉成 KendoUI Grid 用的 DataSource
	 * 
	 **/
	public static String transToGridDataSource(List<Map<String, Object>> list,CommonBean bean) {
		String callback = (bean.getCallback()==null || bean.getCallback().equals(""))?"":bean.getCallback();
		JsonArray ja = new JsonArray();
		for(Map<String, Object> map :list) {
			JsonObject jo = new JsonObject();
			for(String key :map.keySet()) {
				jo.addProperty(key, map.get(key)==null?"":map.get(key).toString());
			}
			ja.add(jo);
		}
		
		return callback+"("+ja.toString()+")";
	}
	
	public static String transToGridDataSource(List<Map<String, Object>> list,CommonDto dto) {
		String callback = (dto.getCallback()==null || dto.getCallback().equals(""))?"":dto.getCallback();
		JsonArray ja = new JsonArray();
		for(Map<String, Object> map :list) {
			JsonObject jo = new JsonObject();
			for(String key :map.keySet()) {
				jo.addProperty(key, map.get(key)==null?"":map.get(key).toString());
			}
			ja.add(jo);
		}
		
		return callback+"("+ja.toString()+")";
	}
	
	
	public static String transToGridDataSource(JsonElement je,CommonBean bean) {
		String callback = (bean.getCallback()==null || bean.getCallback().equals(""))?"":bean.getCallback();
		
		return callback+"("+je.toString()+")";
	}
	
	public static String transToGridDataSource(JsonElement je,CommonDto dto) {
		String callback = (dto.getCallback()==null || dto.getCallback().equals(""))?"":dto.getCallback();
		
		return callback+"("+je.toString()+")";
	}
}
