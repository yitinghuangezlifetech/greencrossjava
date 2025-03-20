package tw.com.ezlifetech.ezReco.util.ar;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import tw.com.ezlifetech.ezReco.util.ar.conf.Settings;



/**
 * 取得全部雲端辨識平台資訊 操作類別
 * 
 * @author PaulChen
 */
public class CloudSummary extends AbstractGetTargetExecution {

	/**
	 * 建構子
	 * 
	 * @param accessKey
	 * @param secretKey
	 */
	CloudSummary(String accessKey, String secretKey) {
		super(accessKey, secretKey);
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
		builder.append("/summary");
		
		return builder.toString();
	}

}
