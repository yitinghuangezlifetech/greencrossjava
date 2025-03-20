package tw.com.ezlifetech.ezReco.util.ar;

import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.DateUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import tw.com.ezlifetech.ezReco.util.ar.spi.VuforiaException;
import tw.com.ezlifetech.ezReco.util.ar.spi.VuforiaTargetExecution;



/**
 * 取得 Image Target 資訊 基礎類別
 * 
 * @author PaulChen
 */
public abstract class AbstractGetTargetExecution implements VuforiaTargetExecution {

	// Server Keys
	protected String accessKey = null;
	protected String secretKey = null;
	@SuppressWarnings("unused")
	protected String targetId = null;
	
	/**
	 * 建構子
	 * 
	 * @param accessKey
	 * @param secretKey
	 */
	protected AbstractGetTargetExecution(String accessKey, String secretKey) {
		this(accessKey, secretKey, null);
	}
	
	/**
	 * 建構子
	 * 
	 * @param accessKey
	 * @param secretKey
	 * @param targetId
	 */
	protected AbstractGetTargetExecution(String accessKey, String secretKey, String targetId) {
		this.accessKey = accessKey;
		this.secretKey = secretKey;
		this.targetId  = targetId;
	} 
	
	/**
	 * 設定 Http headers
	 * 
	 * @param request
	 */
	private void setHeaders(HttpUriRequest request) {
		SignatureBuilder sb = new SignatureBuilder();
		request.setHeader(new BasicHeader("Date", DateUtils.formatDate(new Date()).replaceFirst("[+]00:00$", "")));
		request.setHeader("Authorization", "VWS " + accessKey + ":" + sb.tmsSignature(request, secretKey));
	}
	
	public String doExecute() {
		String url = getUrl();
		HttpUriRequest req = getRequest(url);
		setHeaders(req);
		
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
			HttpResponse response = httpClient.execute(req);
			String result = EntityUtils.toString(response.getEntity());

			return result;
		} catch (IOException e) {
			VuforiaException exc = new VuforiaException(e);
			throw exc;
		}
	}
	
	/**
	 * 取得 Http request method.
	 * 
	 * @param url
	 * @return
	 */
	protected abstract HttpUriRequest getRequest(String url); 
	
	/**
	 * 取得 URL 路徑
	 * 
	 * @return
	 */
	protected abstract String getUrl();
	
}
