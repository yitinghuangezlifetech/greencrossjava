package tw.com.ezlifetech.ezReco.util.ar;

import java.io.StringWriter;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON 處理工具
 * 
 * @author PaulChen
 */
public class JsonUtil {

	private static ObjectMapper mapper = getObjectMapper();

	/**
	 * 將傳入物件轉成JSON字串
	 * 
	 * @param src
	 *            待轉換JSON物件
	 * @return String JSON字串
	 */
	public static String toJson(Object src) {
		try (StringWriter jsonStringWriter = new StringWriter()) {
			mapper.writeValue(jsonStringWriter, src);
			String jsonStr = jsonStringWriter.toString();

			return jsonStr;
		} catch (Exception e) {
		}

		return StringUtils.EMPTY;
	}

	/**
	 * 
	 * 
	 * @param json
	 * @return
	 */
	public static <T> T fromJson(String json, Class<T> clazz) {
	 	try {
	 		byte[] content = json.getBytes();
			T t = mapper.readValue(content, clazz);
			return t;
		} catch (Exception e) {
		}
	 	
	 	// 轉換失敗
	 	return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public static ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper;
	}

}
