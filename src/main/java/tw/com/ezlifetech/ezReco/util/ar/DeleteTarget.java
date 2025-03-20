package tw.com.ezlifetech.ezReco.util.ar;

import java.util.Date;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.DateUtils;
import org.apache.http.message.BasicHeader;

import tw.com.ezlifetech.ezReco.util.ar.conf.Settings;



/**
 * 刪除雲端辨識物件 操作類別
 * 
 * @author PaulChen
 */
public class DeleteTarget extends AbstractOperateTargetExecution {

	/**
	 * 建構子
	 * 
	 * @param accessKey
	 * @param secretKey
	 * @param targetId
	 */
	DeleteTarget(String accessKey, String secretKey, String targetId) {
		super(accessKey, secretKey, targetId);
	}
	
	@Override
	protected void setHeaders(HttpUriRequest request) {
		SignatureBuilder sb = new SignatureBuilder();
		request.setHeader(new BasicHeader("Date", DateUtils.formatDate(new Date()).replaceFirst("[+]00:00$", "")));
		request.setHeader("Authorization", "VWS " + accessKey + ":" + sb.tmsSignature(request, secretKey));
	}

	@Override
	protected HttpUriRequest getRequest(String url) {
		HttpDelete delete = new HttpDelete(url);
		return delete;
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
