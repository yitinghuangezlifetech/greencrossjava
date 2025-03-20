package tw.com.ezlifetech.ezReco.util.ar;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import tw.com.ezlifetech.ezReco.util.ar.conf.Settings;

/**
 * 取得特定雲端辨識物件 操作類別
 * 
 * @author PaulChen
 */
public class GetTarget extends AbstractGetTargetExecution {

	/**
	 * 建構子
	 * 
	 * @param accessKey
	 * @param secretKey
	 * @param targetId
	 */
	GetTarget(String accessKey, String secretKey, String targetId) {
		super(accessKey, secretKey, targetId);
	}
	
	@Override
	protected HttpUriRequest getRequest(String url) {
		HttpGet req = new HttpGet(url);
		return req;
	}

	@Override
	protected String getUrl() {
		StringBuilder builder = new StringBuilder();
		builder.append(Settings.VUFORIA_VWS_URL);
		builder.append("/targets/");
		builder.append(targetId);
		
		return builder.toString();
	}

}
