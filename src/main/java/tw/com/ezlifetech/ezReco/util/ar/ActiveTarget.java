package tw.com.ezlifetech.ezReco.util.ar;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

import tw.com.ezlifetech.ezReco.util.ar.conf.Settings;
import tw.com.ezlifetech.ezReco.util.ar.spi.VuforiaException;



/**
 * 啟用 / 停用 雲端辨識物件 操作類別
 * 
 * @author PaulChen
 */
public class ActiveTarget extends AbstractOperateTargetExecution {

	private boolean isActive;
	
	ActiveTarget(String accessKey, String secretKey, String targetId, boolean isActive) {
		super(accessKey, secretKey, targetId);
		
		this.isActive = isActive;
	}
	
	@Override
	protected HttpUriRequest getRequest(String url) {
		HttpPut put = new HttpPut(url);
		
		Map<String, Object> jsonMp = new HashMap<>();
		jsonMp.put("active_flag", Boolean.valueOf(isActive));
		
		String jsonStr = JsonUtil.toJson(jsonMp);
		
		try {
			put.setEntity(new StringEntity(jsonStr));
		} catch (UnsupportedEncodingException e) {
			VuforiaException exc = new VuforiaException(e);
			throw exc;
		}
	
		return put;
	}

	@Override
	protected String getUrl() {
		StringBuilder path = new StringBuilder();
		path.append(Settings.VUFORIA_VWS_URL);
		path.append("/targets/");
		path.append(targetId);
		
		return path.toString();
	}

}
